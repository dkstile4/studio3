package com.aptana.git.core.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.team.core.RepositoryProvider;
import org.eclipse.team.core.TeamException;

import com.aptana.git.core.GitPlugin;
import com.aptana.git.core.GitRepositoryProvider;

public class GitRepository
{

	private static final String MERGE_HEAD_FILENAME = "MERGE_HEAD"; //$NON-NLS-1$
	private static final String COMMIT_MSG_FILENAME = "COMMIT_EDITMSG"; //$NON-NLS-1$
	private static final String COMMIT_FILE_ENCODING = "UTF-8"; //$NON-NLS-1$
	private static final String HEAD = "HEAD"; //$NON-NLS-1$

	public static final String GIT_DIR = ".git"; //$NON-NLS-1$

	private List<GitRevSpecifier> branches;
	Map<String, List<GitRef>> refs;
	private URI fileURL;
	private GitRevSpecifier _headRef;
	private GitIndex index;
	private boolean hasChanged;
	GitRevSpecifier currentBranch;

	private static Set<IGitRepositoryListener> listeners = new HashSet<IGitRepositoryListener>();

	private static Map<String, SoftReference<GitRepository>> cachedRepos = new HashMap<String, SoftReference<GitRepository>>(
			3);

	public static void addListener(IGitRepositoryListener listener)
	{
		synchronized (listeners)
		{
			listeners.add(listener);
		}
	}

	public static void removeListener(IGitRepositoryListener listener)
	{
		synchronized (listeners)
		{
			listeners.remove(listener);
		}
	}

	/**
	 * Used to retrieve a git repository for a project. Will return null if Eclipse team provider is not hooked up!
	 * 
	 * @param project
	 * @return
	 */
	public static GitRepository getAttached(IProject project)
	{
		if (project == null)
			return null;

		RepositoryProvider provider = RepositoryProvider.getProvider(project, GitRepositoryProvider.ID);
		if (provider == null)
			return null;

		return getUnattachedExisting(project.getLocationURI());
	}

	/**
	 * Used solely for grabbing an existing repository when attaching Eclipse team stuff to a project!
	 * 
	 * @param path
	 * @return
	 */
	public static synchronized GitRepository getUnattachedExisting(URI path)
	{
		if (GitExecutable.instance().path() == null)
			return null;

		SoftReference<GitRepository> ref = cachedRepos.get(path.getPath());
		if (ref == null || ref.get() == null)
		{
			URI gitDirURL = gitDirForURL(path);
			if (gitDirURL == null)
				return null;
			// Check to see if any cached repo has the same git dir
			for (SoftReference<GitRepository> reference : cachedRepos.values())
			{
				if (reference == null || reference.get() == null)
					continue;
				GitRepository cachedRepo = reference.get();
				if (cachedRepo.fileURL.getPath().equals(gitDirURL.getPath()))
				{
					// Same git dir, so cache under our new path as well
					cachedRepos.put(path.getPath(), reference);
					return cachedRepo;
				}
			}
			// no cache for this repo or any repo sharing same git dir
			ref = new SoftReference<GitRepository>(new GitRepository(gitDirURL));
			cachedRepos.put(path.getPath(), ref);
		}
		// TODO What if the underlying .git dir was wiped while we still had the object cached?
		return ref.get();
	}

	public static URI gitDirForURL(URI repositoryURL)
	{
		if (GitExecutable.instance() == null)
			return null;

		String repositoryPath = repositoryURL.getPath();

		if (isBareRepository(repositoryPath))
			return repositoryURL;

		// Use rev-parse to find the .git dir for the repository being opened
		String newPath = GitExecutable.instance().outputForCommand(repositoryPath, "rev-parse", "--git-dir"); //$NON-NLS-1$ //$NON-NLS-2$
		if (newPath.equals(GIT_DIR))
			return new File(repositoryPath, GIT_DIR).toURI();
		if (newPath.length() > 0)
			return new File(newPath).toURI();

		return null;
	}

	private GitRepository(URI fileURL)
	{
		this.fileURL = fileURL;
		this.branches = new ArrayList<GitRevSpecifier>();
		reloadRefs();
		readCurrentBranch();
	}

	public String workingDirectory()
	{
		if (fileURL.getPath().endsWith("/" + GIT_DIR + "/")) //$NON-NLS-1$ //$NON-NLS-2$
			return fileURL.getPath().substring(0, fileURL.getPath().length() - 6);
		else if (GitExecutable.instance().outputForCommand(fileURL.getPath(), "rev-parse", "--is-inside-work-tree") //$NON-NLS-1$ //$NON-NLS-2$
				.equals("true")) //$NON-NLS-1$
			return GitExecutable.instance().path(); // FIXME This doesn't seem right....

		return null;
	}

	public Set<String> localBranches()
	{
		return branches(GitRef.HEAD_TYPE);
	}

	public Set<String> remoteBranches()
	{
		return branches(GitRef.REMOTE_TYPE);
	}

	public Set<String> allBranches()
	{
		return branches(GitRef.HEAD_TYPE, GitRef.REMOTE_TYPE);
	}

	private Set<String> branches(String... types)
	{
		if (types == null || types.length == 0)
			return Collections.emptySet();
		Set<String> validTypes = new HashSet<String>(Arrays.asList(types));
		Set<String> allBranches = new HashSet<String>();
		for (GitRevSpecifier revSpec : branches)
		{
			if (!revSpec.isSimpleRef())
				continue;
			GitRef ref = revSpec.simpleRef();
			if (ref == null || ref.type() == null)
				continue;
			for (String string : types)
			{
				if (ref.type().equals(string))
					break;
			}
			if (!validTypes.contains(ref.type()))
				continue;
			allBranches.add(ref.shortName());
		}
		return allBranches;
	}

	public boolean switchBranch(String branchName)
	{
		if (branchName == null)
			return false;
		String oldBranchName = currentBranch.simpleRef().shortName();
		Map<Integer, String> result = GitExecutable.instance().runInBackground(workingDirectory(), "checkout", //$NON-NLS-1$
				branchName);
		if (result.keySet().iterator().next().intValue() != 0)
			return false;
		_headRef = null;
		readCurrentBranch();
		fireBranchChangeEvent(oldBranchName, branchName);
		return true;
	}

	private void readCurrentBranch()
	{
		this.currentBranch = addBranch(headRef());
	}

	public String parseReference(String parent)
	{
		Map<Integer, String> result = GitExecutable.instance().runInBackground(workingDirectory(), "rev-parse", //$NON-NLS-1$
				"--verify", parent); //$NON-NLS-1$
		int exitValue = result.keySet().iterator().next();
		if (exitValue != 0)
			return null;
		return result.values().iterator().next();
	}

	private static boolean isBareRepository(String path)
	{
		String output = GitExecutable.instance().outputForCommand(path, "rev-parse", "--is-bare-repository"); //$NON-NLS-1$ //$NON-NLS-2$
		return "true".equals(output); //$NON-NLS-1$
	}

	private boolean reloadRefs()
	{
		_headRef = null;
		boolean ret = false;

		refs = new HashMap<String, List<GitRef>>();

		String output = GitExecutable.instance().outputForCommand(fileURL.getPath(), "for-each-ref", //$NON-NLS-1$
				"--format=%(refname) %(objecttype) %(objectname) %(*objectname)", "refs"); //$NON-NLS-1$ //$NON-NLS-2$
		List<String> lines = StringUtil.componentsSeparatedByString(output, "\n"); //$NON-NLS-1$

		for (String line : lines)
		{
			// If its an empty line, skip it (e.g. with empty repositories)
			if (line.length() == 0)
				continue;

			List<String> components = StringUtil.componentsSeparatedByString(line, " "); //$NON-NLS-1$

			// First do the ref matching. If this ref is new, add it to our ref list
			GitRef newRef = GitRef.refFromString(components.get(0));
			GitRevSpecifier revSpec = new GitRevSpecifier(newRef);
			if (!addBranch(revSpec).equals(revSpec))
				ret = true;

			// Also add this ref to the refs list
			addRef(newRef, components);
		}

		// Add an "All branches" option in the branches list
		addBranch(GitRevSpecifier.allBranchesRevSpec());
		addBranch(GitRevSpecifier.localBranchesRevSpec());

		return ret;
	}

	private GitRevSpecifier addBranch(GitRevSpecifier rev)
	{
		if (rev.parameters().isEmpty())
			rev = headRef();

		// First check if the branch doesn't exist already
		for (GitRevSpecifier r : branches)
			if (rev.equals(r))
				return r;

		// willChangeValueForKey("branches");
		branches.add(rev);
		// didChangeValueForKey("branches");
		return rev;
	}

	public GitRevSpecifier headRef()
	{
		if (_headRef != null)
			return _headRef;

		String branch = parseSymbolicReference(HEAD);
		if (branch != null && branch.startsWith(GitRef.REFS_HEADS))
			_headRef = new GitRevSpecifier(GitRef.refFromString(branch));
		else
			_headRef = new GitRevSpecifier(GitRef.refFromString(HEAD));

		return _headRef;
	}

	private String parseSymbolicReference(String reference)
	{
		String ref = GitExecutable.instance().outputForCommand(workingDirectory(), "symbolic-ref", "-q", reference); //$NON-NLS-1$ //$NON-NLS-2$
		if (ref.startsWith(GitRef.REFS))
			return ref;

		return null;
	}

	private void addRef(GitRef ref, List<String> components)
	{
		String type = components.get(1);

		String sha;
		if (type.equals(GitRef.TAG_TYPE) && components.size() == 4)
			sha = components.get(3);
		else
			sha = components.get(2);

		List<GitRef> curRefs = refs.get(sha);
		if (curRefs != null)
		{
			curRefs.add(ref);
		}
		else
		{
			curRefs = new ArrayList<GitRef>();
			curRefs.add(ref);
			refs.put(sha, curRefs);
		}
	}

	/**
	 * get the name of the current branch as a string
	 * 
	 * @return
	 */
	public String currentBranch()
	{
		if (currentBranch == null)
			return null;
		return currentBranch.simpleRef().shortName();
	}

	public synchronized GitIndex index()
	{
		if (index == null)
		{
			index = new GitIndex(this, workingDirectory());
			index.refresh(false); // Don't want to call back to fireIndexChangeEvent yet!
		}
		return index;
	}

	void fireBranchChangeEvent(String oldBranchName, String newBranchName)
	{
		BranchChangedEvent e = new BranchChangedEvent(this, oldBranchName, newBranchName);
		for (IGitRepositoryListener listener : listeners)
			listener.branchChanged(e);
	}

	void fireIndexChangeEvent(Collection<ChangedFile> changedFiles)
	{
		IndexChangedEvent e = new IndexChangedEvent(this, changedFiles);
		for (IGitRepositoryListener listener : listeners)
			listener.indexChanged(e);
	}

	private static void fireRepositoryAddedEvent(GitRepository repo, IProject project)
	{
		RepositoryAddedEvent e = new RepositoryAddedEvent(repo, project);
		for (IGitRepositoryListener listener : listeners)
			listener.repositoryAdded(e);
	}

	public boolean hasMerges()
	{
		return new File(fileURL.getPath(), MERGE_HEAD_FILENAME).exists();
	}

	boolean executeHook(String name)
	{
		return executeHook(name, new String[0]);
	}

	boolean executeHook(String name, String... arguments)
	{
		String hookPath = fileURL.getPath();
		if (!hookPath.endsWith(File.separator))
			hookPath += File.separator;
		hookPath += "hooks" + File.separator + name; //$NON-NLS-1$
		File hook = new File(hookPath);
		if (!hook.exists() || !hook.isFile())
			return true;

		try
		{
			Method method = File.class.getMethod("canExecute", null); //$NON-NLS-1$
			if (method != null)
			{
				Boolean canExecute = (Boolean) method.invoke(hook, null);
				if (!canExecute)
					return true;
			}
		}
		catch (Exception e)
		{
			// ignore
		}

		Map<String, String> env = new HashMap<String, String>();
		env.put(GitEnv.GIT_DIR, fileURL.getPath());
		env.put(GitEnv.GIT_INDEX_FILE, fileURL.getPath() + File.separator + "index"); //$NON-NLS-1$

		int ret = 1;
		Map<Integer, String> result = ProcessUtil.runInBackground(hookPath, workingDirectory(), env, arguments);
		ret = result.keySet().iterator().next();
		return ret == 0;
	}

	String commitMessageFile()
	{
		return new File(fileURL.getPath(), COMMIT_MSG_FILENAME).getAbsolutePath();
	}

	void writetoCommitFile(String commitMessage)
	{
		File commitMessageFile = new File(commitMessageFile());
		OutputStream out = null;
		try
		{
			out = new FileOutputStream(commitMessageFile);
			out.write(commitMessage.getBytes(COMMIT_FILE_ENCODING));
			out.flush();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			try
			{
				if (out != null)
					out.close();
			}
			catch (IOException e)
			{
				// ignore
			}
		}
	}

	public void lazyReload()
	{
		if (!hasChanged)
			return;

		reloadRefs();
		hasChanged = false;
	}

	void hasChanged()
	{
		hasChanged = true;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof GitRepository))
			return false;
		GitRepository other = (GitRepository) obj;
		return fileURL.getPath().equals(other.fileURL.getPath());
	}

	@Override
	public int hashCode()
	{
		return fileURL.getPath().hashCode();
	}

	/**
	 * Return the list of commits the local copy of a branch is ahead of the remote tracking branch.
	 * 
	 * @param branchName
	 * @return null if there's no tracking remote branch
	 */
	public String[] commitsAhead(String branchName)
	{
		GitRef remote = remoteTrackingBranch(branchName);
		if (remote == null)
			return null;
		return index().commitsBetween(remote.ref(), GitRef.REFS_HEADS + branchName);
	}

	public ChangedFile getChangedFileForResource(IResource resource)
	{
		String workingDirectory = workingDirectory();
		if (!workingDirectory.endsWith("/")) //$NON-NLS-1$
		{
			workingDirectory += "/"; //$NON-NLS-1$
		}
		for (ChangedFile changedFile : index().changedFiles())
		{
			String fullPath = workingDirectory + changedFile.getPath();
			if (resource.getLocationURI().getPath().equals(fullPath))
			{
				return changedFile;
			}
		}
		return null;
	}

	/**
	 * Return the list of commits the local copy of a branch is behind the remote tracking branch.
	 * 
	 * @param branchName
	 * @return null if there's no tracking remote branch
	 */
	public String[] commitsBehind(String branchName)
	{
		GitRef remote = remoteTrackingBranch(branchName);
		if (remote == null)
			return null;
		return index().commitsBetween(GitRef.REFS_HEADS + branchName, remote.ref());
	}

	/**
	 * Generates a brand new git repository in the specified location.
	 */
	public static void create(String path)
	{
		if (path == null)
			return;
		if (path.endsWith(File.separator + GIT_DIR))
		{
			path = path.substring(0, path.length() - GIT_DIR.length());
		}

		URI existing = gitDirForURL(new File(path).toURI());
		if (existing != null)
			return;

		GitExecutable.instance().runInBackground(path, "init"); //$NON-NLS-1$
	}

	/**
	 * Given an existing repo on disk, we wrap it with our model and hook it up to the eclipse team provider.
	 * 
	 * @param project
	 * @param m
	 * @return
	 */
	public static GitRepository attachExisting(IProject project, IProgressMonitor m) throws CoreException
	{
		if (m == null)
			m = new NullProgressMonitor();
		GitRepository repo = GitRepository.getUnattachedExisting(project.getLocationURI());
		m.worked(40);
		if (repo == null)
			return null;

		try
		{
			RepositoryProvider.map(project, GitRepositoryProvider.ID);
			m.worked(10);
			fireRepositoryAddedEvent(repo, project);
			m.worked(50);
		}
		catch (TeamException e)
		{
			throw new CoreException(new Status(IStatus.ERROR, GitPlugin.getPluginId(), e.getMessage(), e));
		}
		return repo;
	}

	public boolean isDirty()
	{
		return !index().changedFiles().isEmpty();
	}

	/**
	 * Determine if the passed in branch has a remote tracking branch.
	 * 
	 * @param branchName
	 * @return
	 */
	public boolean trackingRemote(String branchName)
	{
		return remoteTrackingBranch(branchName) != null;
	}

	/**
	 * Returns the remote tracking branch name for the branch passed in. Returns null if there is none.
	 * 
	 * @param branchName
	 * @return
	 */
	public GitRef remoteTrackingBranch(String branchName)
	{
		String output = GitExecutable.instance().outputForCommand(workingDirectory(), "config", "--get-regexp", //$NON-NLS-1$ //$NON-NLS-2$
				"^branch\\." + branchName + "\\.remote"); //$NON-NLS-1$ //$NON-NLS-2$
		if (output == null || output.trim().length() == 0)
			return null;
		String remoteSubname = output.substring(14 + branchName.length()).trim();
		return GitRef.refFromString(GitRef.REFS_REMOTES + remoteSubname + "/" + branchName); //$NON-NLS-1$
	}

	/**
	 * @param branchName
	 *            Name of the new branch
	 * @param track
	 *            Whether this branch should track the start point
	 * @param startPoint
	 *            branch name, commit id, or ref/tag to create the branch off of. Null/empty assumes "HEAD"
	 * @return
	 */
	public boolean createBranch(String branchName, boolean track, String startPoint)
	{
		List<String> args = new ArrayList<String>();
		args.add("branch"); //$NON-NLS-1$
		if (track)
			args.add("--track"); //$NON-NLS-1$
		args.add(branchName);
		if (startPoint != null && startPoint.trim().length() > 0)
			args.add(startPoint);

		Map<Integer, String> result = GitExecutable.instance().runInBackground(workingDirectory(),
				args.toArray(new String[args.size()]));
		return result.keySet().iterator().next() == 0;
	}

	public boolean validBranchName(String branchName)
	{
		Map<Integer, String> result = GitExecutable.instance().runInBackground(workingDirectory(), "check-ref-format", //$NON-NLS-1$
				GitRef.REFS_HEADS + branchName);
		return result.keySet().iterator().next() == 0;
	}

	public boolean deleteFile(String filePath)
	{
		Map<Integer, String> result = GitExecutable.instance().runInBackground(workingDirectory(), "rm", filePath); //$NON-NLS-1$
		if (result.keySet().iterator().next() != 0)
			return false;
		index().refresh();
		return true;
	}

	public boolean deleteFolder(String folderPath)
	{
		Map<Integer, String> result = GitExecutable.instance().runInBackground(workingDirectory(), "rm", "-r", //$NON-NLS-1$ //$NON-NLS-2$
				folderPath);
		if (result.keySet().iterator().next() != 0)
			return false;
		index().refresh();
		return true;
	}

	public boolean moveFile(String source, String dest)
	{
		Map<Integer, String> result = GitExecutable.instance().runInBackground(workingDirectory(), "mv", source, dest); //$NON-NLS-1$
		if (result.keySet().iterator().next() != 0)
			return false;
		index().refresh();
		return true;
	}

	public String relativePath(IResource theResource)
	{
		String workingDirectory = workingDirectory();
		String resourcePath = theResource.getLocationURI().getPath();
		if (resourcePath.startsWith(workingDirectory))
		{
			resourcePath = resourcePath.substring(workingDirectory.length());
			if (resourcePath.startsWith("/") || resourcePath.startsWith("\\")) //$NON-NLS-1$ //$NON-NLS-2$
				resourcePath = resourcePath.substring(1);
		}
		// What if we have some trailing slash or something?
		if (resourcePath.length() == 0)
		{
			resourcePath = currentBranch();
		}
		return resourcePath;
	}

	/**
	 * Returns the set of short names for all simple refs that are local or remote branches or tags.
	 * 
	 * @return
	 */
	public Set<String> allSimpleRefs()
	{
		Set<String> allRefs = new HashSet<String>();
		for (GitRevSpecifier revSpec : branches)
		{
			if (!revSpec.isSimpleRef())
				continue;
			GitRef ref = revSpec.simpleRef();
			if (ref == null || ref.type() == null)
				continue;
			allRefs.add(ref.shortName());
		}
		return allRefs;
	}

	/**
	 * Used when the user disconnects the project from the repository. We should notify listeners that the repo has been
	 * unattached. We should also flush the cached copy.
	 * 
	 * @param p
	 */
	public static void removeRepository(IProject p)
	{
		GitRepository repo = getUnattachedExisting(p.getLocationURI());
		if (repo != null)
			cachedRepos.remove(p.getLocationURI().getPath());

		RepositoryRemovedEvent e = new RepositoryRemovedEvent(repo, p);
		for (IGitRepositoryListener listener : listeners)
			listener.repositoryRemoved(e);
	}

	public boolean hasUnresolvedMergeConflicts()
	{
		List<ChangedFile> changedFiles = index().changedFiles();
		if (changedFiles.isEmpty())
			return false;
		for (ChangedFile changedFile : changedFiles)
		{
			if (changedFile.hasUnmergedChanges() && changedFile.hasUnstagedChanges())
				return true;
		}
		return false;
	}
}
