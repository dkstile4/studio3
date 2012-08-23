/* The following code was generated by JFlex 1.4.3 on 23/08/12 13:28 */

// $codepro.audit.disable
/**
 * Aptana Studio
 * Copyright (c) 2005-2012 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the GNU Public License (GPL) v3 (with exceptions).
 * Please see the license.html included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.editor.js.parsing;

import java.io.Reader;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;

import beaver.Symbol;
import beaver.Scanner;

import com.aptana.editor.js.parsing.lexer.JSTokenType;
import com.aptana.editor.js.parsing.JSTokenTypeSymbol;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 23/08/12 13:28 from the specification file
 * <tt>X:/studio3/plugins/com.aptana.editor.js/parsing/JSPartitioning.flex</tt>
 */
public class JSPartitioningFlexScanner extends Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int REGEX = 4;
  public static final int DIVISION = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2, 2
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\13\1\11\1\2\1\36\1\37\1\1\16\13\4\0\1\11\1\43"+
    "\1\41\1\0\1\12\1\4\1\44\1\35\1\52\1\53\1\34\1\23"+
    "\1\54\1\46\1\21\1\27\1\15\11\20\1\55\1\51\1\3\1\42"+
    "\1\5\1\6\1\0\4\17\1\22\1\17\21\12\1\16\2\12\1\24"+
    "\1\26\1\25\1\47\1\12\1\0\4\32\1\33\1\32\21\30\1\31"+
    "\2\30\1\7\1\45\1\10\1\50\6\13\1\40\32\13\2\0\4\12"+
    "\4\0\1\12\2\0\1\13\7\0\1\12\4\0\1\12\5\0\27\12"+
    "\1\0\37\12\1\0\u013f\12\31\0\162\12\4\0\14\12\16\0\5\12"+
    "\11\0\1\12\21\0\130\13\5\0\23\13\12\0\1\12\13\0\1\12"+
    "\1\0\3\12\1\0\1\12\1\0\24\12\1\0\54\12\1\0\46\12"+
    "\1\0\5\12\4\0\202\12\1\0\4\13\3\0\105\12\1\0\46\12"+
    "\2\0\2\12\6\0\20\12\41\0\46\12\2\0\1\12\7\0\47\12"+
    "\11\0\21\13\1\0\27\13\1\0\3\13\1\0\1\13\1\0\2\13"+
    "\1\0\1\13\13\0\33\12\5\0\3\12\15\0\4\13\14\0\6\13"+
    "\13\0\32\12\5\0\13\12\16\13\7\0\12\14\4\0\2\12\1\13"+
    "\143\12\1\0\1\12\10\13\1\0\6\13\2\12\2\13\1\0\4\13"+
    "\2\12\12\14\3\12\2\0\1\12\17\0\1\13\1\12\1\13\36\12"+
    "\33\13\2\0\3\12\60\0\46\12\13\13\1\12\u014f\0\3\13\66\12"+
    "\2\0\1\13\1\12\20\13\2\0\1\12\4\13\3\0\12\12\2\13"+
    "\2\0\12\14\21\0\3\13\1\0\10\12\2\0\2\12\2\0\26\12"+
    "\1\0\7\12\1\0\1\12\3\0\4\12\2\0\1\13\1\12\7\13"+
    "\2\0\2\13\2\0\3\13\11\0\1\13\4\0\2\12\1\0\3\12"+
    "\2\13\2\0\12\14\4\12\15\0\3\13\1\0\6\12\4\0\2\12"+
    "\2\0\26\12\1\0\7\12\1\0\2\12\1\0\2\12\1\0\2\12"+
    "\2\0\1\13\1\0\5\13\4\0\2\13\2\0\3\13\13\0\4\12"+
    "\1\0\1\12\7\0\12\14\2\13\3\12\14\0\3\13\1\0\11\12"+
    "\1\0\3\12\1\0\26\12\1\0\7\12\1\0\2\12\1\0\5\12"+
    "\2\0\1\13\1\12\10\13\1\0\3\13\1\0\3\13\2\0\1\12"+
    "\17\0\2\12\2\13\2\0\12\14\1\0\1\12\17\0\3\13\1\0"+
    "\10\12\2\0\2\12\2\0\26\12\1\0\7\12\1\0\2\12\1\0"+
    "\5\12\2\0\1\13\1\12\6\13\3\0\2\13\2\0\3\13\10\0"+
    "\2\13\4\0\2\12\1\0\3\12\4\0\12\14\1\0\1\12\20\0"+
    "\1\13\1\12\1\0\6\12\3\0\3\12\1\0\4\12\3\0\2\12"+
    "\1\0\1\12\1\0\2\12\3\0\2\12\3\0\3\12\3\0\10\12"+
    "\1\0\3\12\4\0\5\13\3\0\3\13\1\0\4\13\11\0\1\13"+
    "\17\0\11\14\11\0\1\12\7\0\3\13\1\0\10\12\1\0\3\12"+
    "\1\0\27\12\1\0\12\12\1\0\5\12\4\0\7\13\1\0\3\13"+
    "\1\0\4\13\7\0\2\13\11\0\2\12\4\0\12\14\22\0\2\13"+
    "\1\0\10\12\1\0\3\12\1\0\27\12\1\0\12\12\1\0\5\12"+
    "\2\0\1\13\1\12\7\13\1\0\3\13\1\0\4\13\7\0\2\13"+
    "\7\0\1\12\1\0\2\12\4\0\12\14\22\0\2\13\1\0\10\12"+
    "\1\0\3\12\1\0\27\12\1\0\20\12\4\0\6\13\2\0\3\13"+
    "\1\0\4\13\11\0\1\13\10\0\2\12\4\0\12\14\22\0\2\13"+
    "\1\0\22\12\3\0\30\12\1\0\11\12\1\0\1\12\2\0\7\12"+
    "\3\0\1\13\4\0\6\13\1\0\1\13\1\0\10\13\22\0\2\13"+
    "\15\0\60\12\1\13\2\12\7\13\4\0\10\12\10\13\1\0\12\14"+
    "\47\0\2\12\1\0\1\12\2\0\2\12\1\0\1\12\2\0\1\12"+
    "\6\0\4\12\1\0\7\12\1\0\3\12\1\0\1\12\1\0\1\12"+
    "\2\0\2\12\1\0\4\12\1\13\2\12\6\13\1\0\2\13\1\12"+
    "\2\0\5\12\1\0\1\12\1\0\6\13\2\0\12\14\2\0\2\12"+
    "\42\0\1\12\27\0\2\13\6\0\12\14\13\0\1\13\1\0\1\13"+
    "\1\0\1\13\4\0\2\13\10\12\1\0\42\12\6\0\24\13\1\0"+
    "\2\13\4\12\4\0\10\13\1\0\44\13\11\0\1\13\71\0\42\12"+
    "\1\0\5\12\1\0\2\12\1\0\7\13\3\0\4\13\6\0\12\14"+
    "\6\0\6\12\4\13\106\0\46\12\12\0\51\12\7\0\132\12\5\0"+
    "\104\12\5\0\122\12\6\0\7\12\1\0\77\12\1\0\1\12\1\0"+
    "\4\12\2\0\7\12\1\0\1\12\1\0\4\12\2\0\47\12\1\0"+
    "\1\12\1\0\4\12\2\0\37\12\1\0\1\12\1\0\4\12\2\0"+
    "\7\12\1\0\1\12\1\0\4\12\2\0\7\12\1\0\7\12\1\0"+
    "\27\12\1\0\37\12\1\0\1\12\1\0\4\12\2\0\7\12\1\0"+
    "\47\12\1\0\23\12\16\0\11\14\56\0\125\12\14\0\u026c\12\2\0"+
    "\10\12\12\0\32\12\5\0\113\12\3\0\3\12\17\0\15\12\1\0"+
    "\4\12\3\13\13\0\22\12\3\13\13\0\22\12\2\13\14\0\15\12"+
    "\1\0\3\12\1\0\2\13\14\0\64\12\40\13\3\0\1\12\3\0"+
    "\2\12\1\13\2\0\12\14\41\0\3\13\2\0\12\14\6\0\130\12"+
    "\10\0\51\12\1\13\126\0\35\12\3\0\14\13\4\0\14\13\12\0"+
    "\12\14\36\12\2\0\5\12\u038b\0\154\12\224\0\234\12\4\0\132\12"+
    "\6\0\26\12\2\0\6\12\2\0\46\12\2\0\6\12\2\0\10\12"+
    "\1\0\1\12\1\0\1\12\1\0\1\12\1\0\37\12\2\0\65\12"+
    "\1\0\7\12\1\0\1\12\3\0\3\12\1\0\7\12\3\0\4\12"+
    "\2\0\6\12\4\0\15\12\5\0\3\12\1\0\7\12\17\0\4\13"+
    "\30\0\2\36\5\13\20\0\2\12\23\0\1\12\13\0\4\13\6\0"+
    "\6\13\1\0\1\12\15\0\1\12\40\0\22\12\36\0\15\13\4\0"+
    "\1\13\3\0\6\13\27\0\1\12\4\0\1\12\2\0\12\12\1\0"+
    "\1\12\3\0\5\12\6\0\1\12\1\0\1\12\1\0\1\12\1\0"+
    "\4\12\1\0\3\12\1\0\7\12\3\0\3\12\5\0\5\12\26\0"+
    "\44\12\u0e81\0\3\12\31\0\11\12\6\13\1\0\5\12\2\0\5\12"+
    "\4\0\126\12\2\0\2\13\2\0\3\12\1\0\137\12\5\0\50\12"+
    "\4\0\136\12\21\0\30\12\70\0\20\12\u0200\0\u19b6\12\112\0\u51a6\12"+
    "\132\0\u048d\12\u0773\0\u2ba4\12\u215c\0\u012e\12\2\0\73\12\225\0\7\12"+
    "\14\0\5\12\5\0\1\12\1\13\12\12\1\0\15\12\1\0\5\12"+
    "\1\0\1\12\1\0\2\12\1\0\2\12\1\0\154\12\41\0\u016b\12"+
    "\22\0\100\12\2\0\66\12\50\0\15\12\3\0\20\13\20\0\4\13"+
    "\17\0\2\12\30\0\3\12\31\0\1\12\6\0\5\12\1\0\207\12"+
    "\2\0\1\13\4\0\1\12\13\0\12\14\7\0\32\12\4\0\1\12"+
    "\1\0\32\12\12\0\132\12\3\0\6\12\2\0\6\12\2\0\6\12"+
    "\2\0\3\12\3\0\2\12\3\0\2\12\22\0\3\13\4\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\2\12\1\13\1\14\1\15\1\16\1\17"+
    "\1\20\2\1\1\21\1\22\1\23\1\24\1\25\1\26"+
    "\1\27\1\30\1\31\1\32\1\33\1\34\2\1\1\35"+
    "\1\1\1\35\2\0\1\36\2\0\1\37\1\40\1\41"+
    "\1\42\1\0\1\12\2\0\1\43\1\44\1\45\1\0"+
    "\1\46\1\0\2\47\1\1\1\50\1\47\1\0\2\51"+
    "\1\1\1\51\1\52\1\53\1\54\1\55\1\56\1\57"+
    "\1\60\1\61\1\62\1\63\1\64\3\0\1\64\1\65"+
    "\2\0\1\66\1\67\1\0\1\12\1\0\1\12\2\45"+
    "\3\0\1\70\1\0\1\71\1\72\1\73\1\74\1\0"+
    "\1\75\2\0\1\76\1\0\1\76\1\77";

  private static int [] zzUnpackAction() {
    int [] result = new int[111];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\56\0\134\0\212\0\270\0\346\0\u0114\0\u0142"+
    "\0\212\0\u0170\0\212\0\u019e\0\u01cc\0\u01fa\0\u0228\0\u0256"+
    "\0\212\0\212\0\u0284\0\u02b2\0\u02e0\0\u030e\0\u033c\0\u036a"+
    "\0\u0398\0\u03c6\0\u03f4\0\u0422\0\212\0\212\0\212\0\212"+
    "\0\212\0\212\0\u0450\0\u0170\0\u047e\0\u04ac\0\u04da\0\u0450"+
    "\0\u0170\0\u0508\0\u0536\0\u0564\0\212\0\212\0\u0592\0\212"+
    "\0\u05c0\0\u05ee\0\u061c\0\u064a\0\212\0\212\0\u0678\0\u06a6"+
    "\0\212\0\u06d4\0\u0702\0\212\0\u0730\0\212\0\u06d4\0\u075e"+
    "\0\u078c\0\212\0\u07ba\0\u075e\0\212\0\u07e8\0\u0816\0\212"+
    "\0\212\0\212\0\212\0\212\0\212\0\212\0\212\0\u0844"+
    "\0\u0872\0\u08a0\0\u0844\0\212\0\u08ce\0\u08fc\0\u092a\0\212"+
    "\0\u0958\0\u0986\0\u0986\0\u064a\0\u09b4\0\212\0\u09e2\0\u0a10"+
    "\0\u0730\0\212\0\u07ba\0\212\0\212\0\212\0\u0a3e\0\u0a6c"+
    "\0\212\0\u0a9a\0\u0ac8\0\u0ac8\0\u0af6\0\212\0\212";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[111];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\2\5\1\6\1\7\1\10\1\11\1\12\1\13"+
    "\1\5\1\14\1\4\1\15\1\16\2\14\1\15\1\17"+
    "\1\14\1\20\1\21\1\22\1\4\1\23\4\14\1\24"+
    "\1\25\1\4\1\5\1\4\1\26\1\27\1\30\1\31"+
    "\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41"+
    "\1\42\1\4\2\5\1\43\3\4\1\44\1\4\1\5"+
    "\15\4\1\45\5\4\1\46\1\4\1\5\1\4\1\46"+
    "\15\4\2\5\1\43\3\4\1\44\1\4\1\5\15\4"+
    "\1\47\5\4\1\46\1\4\1\5\1\4\1\46\14\4"+
    "\57\0\2\5\1\50\3\0\1\51\1\0\1\5\25\0"+
    "\1\5\21\0\1\52\1\53\1\0\1\54\33\0\1\55"+
    "\55\0\1\56\20\0\1\57\34\0\1\60\17\0\1\61"+
    "\63\0\7\14\1\0\1\14\5\0\4\14\4\0\1\14"+
    "\31\0\2\15\2\0\1\15\1\62\1\63\10\0\1\63"+
    "\36\0\2\15\1\64\1\0\1\15\1\62\1\63\6\0"+
    "\1\64\1\0\1\63\36\0\2\62\2\0\1\62\60\0"+
    "\1\65\16\0\1\66\42\0\1\67\4\0\1\70\63\0"+
    "\1\71\13\0\1\72\1\73\1\74\23\72\1\75\6\72"+
    "\1\76\3\77\15\72\1\100\1\101\1\102\23\100\1\103"+
    "\7\100\3\104\1\105\14\100\42\0\1\106\55\0\1\107"+
    "\55\0\1\110\1\0\1\111\53\0\1\112\2\0\1\113"+
    "\52\0\1\114\3\0\1\115\51\0\1\116\17\0\1\53"+
    "\1\0\1\54\111\0\1\117\41\0\1\4\27\0\1\120"+
    "\2\0\21\120\1\121\1\120\1\122\1\0\12\120\1\123"+
    "\13\120\42\0\1\124\13\0\4\53\1\125\51\53\6\54"+
    "\1\126\47\54\5\0\1\127\34\0\1\130\13\0\4\61"+
    "\1\131\51\61\14\0\2\62\2\0\1\62\1\0\1\63"+
    "\10\0\1\63\36\0\2\132\2\0\1\132\2\0\1\133"+
    "\22\0\1\133\24\0\1\134\1\0\2\134\1\0\1\134"+
    "\7\0\2\134\22\0\1\67\1\135\1\136\53\67\34\137"+
    "\1\140\21\137\1\72\1\73\1\74\23\72\1\141\6\72"+
    "\1\76\3\77\15\72\2\0\1\142\53\0\56\72\1\100"+
    "\1\101\1\102\23\100\1\143\7\100\3\104\1\105\14\100"+
    "\2\0\1\144\53\0\56\100\42\0\1\145\55\0\1\146"+
    "\13\0\1\120\2\0\21\120\1\121\1\120\1\122\1\147"+
    "\26\120\1\121\2\0\22\121\1\120\1\150\27\121\1\120"+
    "\2\0\53\120\4\53\1\125\1\5\50\53\5\54\1\5"+
    "\1\126\47\54\42\0\1\151\13\0\4\61\1\131\3\61"+
    "\1\5\45\61\14\0\2\132\2\0\1\132\37\0\1\136"+
    "\53\0\34\137\1\152\21\137\27\153\1\154\4\153\1\155"+
    "\21\153\30\0\4\147\22\0\1\121\2\0\53\121\27\137"+
    "\1\156\4\137\1\152\21\137\34\153\1\155\50\153\1\157"+
    "\4\153\1\155\21\153";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2852];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\1\11\4\1\1\11\1\1\1\11\5\1\2\11"+
    "\12\1\6\11\5\1\2\0\1\1\2\0\2\11\1\1"+
    "\1\11\1\0\1\1\2\0\2\11\1\1\1\0\1\11"+
    "\1\0\1\1\1\11\1\1\1\11\1\1\1\0\1\1"+
    "\1\11\2\1\1\11\2\1\10\11\3\0\1\1\1\11"+
    "\2\0\1\1\1\11\1\0\1\1\1\0\2\1\1\11"+
    "\3\0\1\11\1\0\3\11\1\1\1\0\1\11\2\0"+
    "\1\1\1\0\2\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[111];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
    // last token used for look behind. Also needed when implementing the ITokenScanner interface
    private Symbol _lastToken;

    public JSPartitioningFlexScanner()
    {
        this((Reader) null);
    }

    public Symbol getLastToken()
    {
        return _lastToken;
    }

    private Symbol newToken(JSTokenType id, Object value)
    {
        return new JSTokenTypeSymbol(id, yychar, yychar + yylength() - 1, value);
    }

    public Symbol nextToken() throws java.io.IOException, Scanner.Exception
    {
        try
        {
            // get next token
            _lastToken = yylex();
        } 
        catch (Scanner.Exception e)
        {
            if (e instanceof ForceReturnException)
            {
                //Ok, we have a 'forced return', meaning we should consume everything until the end
                //of the file and return the token that was forced.
                ForceReturnException forceReturnException = (ForceReturnException) e;
                int start = yychar;
                boolean eof;
                do
                {
                    eof = zzRefill();
                }
                while (!eof);

                _lastToken = new JSTokenTypeSymbol((JSTokenType) forceReturnException.type, start, start + zzEndRead
                        - 1, "");
                yyclose();
            }
            else
            {
                int end = yychar + yylength() - 1;
                _lastToken = new JSTokenTypeSymbol(JSTokenType.EOF, yychar, end, "");
            }
        }

        return _lastToken;
    }

    private boolean isValidDivisionStart()
    {
        if (_lastToken != null)
        {
            switch (((JSTokenTypeSymbol) _lastToken).token)
            {
                case IDENTIFIER:
                case NUMBER:
                case REGEX:
                case STRING:
                case RPAREN:
                case PLUS_PLUS:
                case MINUS_MINUS:
                case RBRACKET:
                case RCURLY:
                case FALSE:
                case NULL:
                case THIS:
                case TRUE:
                    return true;
            }
        }

        return false;
    }


    public void setSource(String source)
    {
        yyreset(new StringReader(source));

        // clear last token
        _lastToken = null;
    }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public JSPartitioningFlexScanner(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public JSPartitioningFlexScanner(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 1738) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public Symbol yylex() throws java.io.IOException, Scanner.Exception {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 2: 
          { /* ignore */
          }
        case 64: break;
        case 25: 
          { return newToken(JSTokenType.LPAREN, "");
          }
        case 65: break;
        case 3: 
          { return newToken(JSTokenType.LESS, "");
          }
        case 66: break;
        case 7: 
          { return newToken(JSTokenType.LCURLY, "");
          }
        case 67: break;
        case 5: 
          { return newToken(JSTokenType.GREATER, "");
          }
        case 68: break;
        case 44: 
          { return newToken(JSTokenType.EXCLAMATION_EQUAL, "");
          }
        case 69: break;
        case 27: 
          { return newToken(JSTokenType.COMMA, "");
          }
        case 70: break;
        case 31: 
          { return newToken(JSTokenType.LESS_EQUAL, "");
          }
        case 71: break;
        case 51: 
          { return newToken(JSTokenType.CARET_EQUAL, "");
          }
        case 72: break;
        case 23: 
          { return newToken(JSTokenType.TILDE, "");
          }
        case 73: break;
        case 32: 
          { return newToken(JSTokenType.PERCENT_EQUAL, "");
          }
        case 74: break;
        case 38: 
          { return newToken(JSTokenType.STAR_EQUAL, "");
          }
        case 75: break;
        case 26: 
          { return newToken(JSTokenType.RPAREN, "");
          }
        case 76: break;
        case 61: 
          { return newToken(JSTokenType.GREATER_GREATER_GREATER_EQUAL, "");
          }
        case 77: break;
        case 49: 
          { return newToken(JSTokenType.MINUS_EQUAL, "");
          }
        case 78: break;
        case 20: 
          { return newToken(JSTokenType.PIPE, "");
          }
        case 79: break;
        case 8: 
          { return newToken(JSTokenType.RCURLY, "");
          }
        case 80: break;
        case 59: 
          { return newToken(JSTokenType.EXCLAMATION_EQUAL_EQUAL, "");
          }
        case 81: break;
        case 21: 
          { return newToken(JSTokenType.MINUS, "");
          }
        case 82: break;
        case 47: 
          { return newToken(JSTokenType.PIPE_EQUAL, "");
          }
        case 83: break;
        case 43: 
          { return newToken(JSTokenType.EQUAL_EQUAL, "");
          }
        case 84: break;
        case 16: 
          { return newToken(JSTokenType.STAR, "");
          }
        case 85: break;
        case 24: 
          { return newToken(JSTokenType.SEMICOLON, "");
          }
        case 86: break;
        case 62: 
          { return newToken(JSTokenType.MULTILINE_COMMENT, "");
          }
        case 87: break;
        case 60: 
          { yybegin(YYINITIAL);
                        return newToken(JSTokenType.REGEX, "");
          }
        case 88: break;
        case 11: 
          { return newToken(JSTokenType.DOT, "");
          }
        case 89: break;
        case 13: 
          { return newToken(JSTokenType.LBRACKET, "");
          }
        case 90: break;
        case 1: 
          { // make sure we reset the lexer state for next (potential) scan
                yybegin(YYINITIAL);
                throw new Scanner.Exception("Unexpected character '" + "" + "' around offset " + yychar);
          }
        case 91: break;
        case 53: 
          { return newToken(JSTokenType.LESS_LESS_EQUAL, "");
          }
        case 92: break;
        case 35: 
          { return newToken(JSTokenType.PLUS_PLUS, "");
          }
        case 93: break;
        case 18: 
          { return newToken(JSTokenType.EXCLAMATION, "");
          }
        case 94: break;
        case 63: 
          { return newToken(JSTokenType.SDOC, "");
          }
        case 95: break;
        case 52: 
          { yybegin(YYINITIAL);
                        return newToken(JSTokenType.FORWARD_SLASH_EQUAL, "");
          }
        case 96: break;
        case 45: 
          { return newToken(JSTokenType.AMPERSAND_EQUAL, "");
          }
        case 97: break;
        case 9: 
          { return newToken(JSTokenType.IDENTIFIER, "");
          }
        case 98: break;
        case 55: 
          { return newToken(JSTokenType.GREATER_GREATER_EQUAL, "");
          }
        case 99: break;
        case 50: 
          { return newToken(JSTokenType.MINUS_MINUS, "");
          }
        case 100: break;
        case 48: 
          { return newToken(JSTokenType.PIPE_PIPE, "");
          }
        case 101: break;
        case 34: 
          { return newToken(JSTokenType.GREATER_EQUAL, "");
          }
        case 102: break;
        case 36: 
          { return newToken(JSTokenType.PLUS_EQUAL, "");
          }
        case 103: break;
        case 30: 
          { return newToken(JSTokenType.LESS_LESS, "");
          }
        case 104: break;
        case 57: 
          // lookahead expression with fixed lookahead length
          yypushback(2);
          { return newToken(JSTokenType.STRING_DOUBLE, "");
          }
        case 105: break;
        case 42: 
          { return newToken(JSTokenType.STRING_DOUBLE, "");
          }
        case 106: break;
        case 41: 
          // lookahead expression with fixed lookahead length
          yypushback(1);
          { return newToken(JSTokenType.STRING_DOUBLE, "");
          }
        case 107: break;
        case 29: 
          { yybegin(YYINITIAL);
                        return newToken(JSTokenType.FORWARD_SLASH, "");
          }
        case 108: break;
        case 22: 
          { return newToken(JSTokenType.CARET, "");
          }
        case 109: break;
        case 4: 
          { return newToken(JSTokenType.PERCENT, "");
          }
        case 110: break;
        case 37: 
          { return newToken(JSTokenType.SINGLELINE_COMMENT, "");
          }
        case 111: break;
        case 6: 
          { return newToken(JSTokenType.QUESTION, "");
          }
        case 112: break;
        case 19: 
          { return newToken(JSTokenType.AMPERSAND, "");
          }
        case 113: break;
        case 56: 
          // lookahead expression with fixed lookahead length
          yypushback(2);
          { return newToken(JSTokenType.STRING_SINGLE, "");
          }
        case 114: break;
        case 40: 
          { return newToken(JSTokenType.STRING_SINGLE, "");
          }
        case 115: break;
        case 39: 
          // lookahead expression with fixed lookahead length
          yypushback(1);
          { return newToken(JSTokenType.STRING_SINGLE, "");
          }
        case 116: break;
        case 54: 
          { return newToken(JSTokenType.GREATER_GREATER_GREATER, "");
          }
        case 117: break;
        case 15: 
          { char c = '\0';
                        char c2 = '\0';
                        try{
                            c = yycharat(1);
                        }catch(RuntimeException e){}
                        
                        try{
                            c2 = yycharat(2);
                        }catch(RuntimeException e){}
                        
                        // If we actually have a /* but didn't match it, this means we have a comment
                        // until the end of the file.
                        if(c == '*'){
                            if(c2 == '*')
                            {
                                throw new ForceReturnException(0, 0, "Forcing SDOC to end of document.", JSTokenType.SDOC);
                            }
                            else
                            {
                                throw new ForceReturnException(0, 0, "Forcing MULTILINE_COMMENT to end of document.", JSTokenType.MULTILINE_COMMENT);
                            }
                            
                        }else{
                            yypushback(1);
                            if (isValidDivisionStart())
                            {
                                yybegin(DIVISION);
                            }
                            else
                            {
                                yybegin(REGEX);
                            }
                        }
          }
        case 118: break;
        case 28: 
          { return newToken(JSTokenType.COLON, "");
          }
        case 119: break;
        case 17: 
          { return newToken(JSTokenType.EQUAL, "");
          }
        case 120: break;
        case 14: 
          { return newToken(JSTokenType.RBRACKET, "");
          }
        case 121: break;
        case 33: 
          { return newToken(JSTokenType.GREATER_GREATER, "");
          }
        case 122: break;
        case 10: 
          { return newToken(JSTokenType.NUMBER, "");
          }
        case 123: break;
        case 12: 
          { return newToken(JSTokenType.PLUS, "");
          }
        case 124: break;
        case 46: 
          { return newToken(JSTokenType.AMPERSAND_AMPERSAND, "");
          }
        case 125: break;
        case 58: 
          { return newToken(JSTokenType.EQUAL_EQUAL_EQUAL, "");
          }
        case 126: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
              {     return newToken(JSTokenType.EOF, "end-of-file");
 }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
