package tech3g.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import org.apache.cactus.util.ChainedRuntimeException;
import org.apache.commons.lang.StringUtils;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.SystemException;

/**
 * <pre>
 * StrUtilクラス。
 * 文字列関連の処理やチェックなどを行う。
 * </pre>
 *
 */
public final class StrUtil extends StringUtils {

    //--------------------------------------------------- 定数

	/** 文字列：「ブランク」 */
	public static final String STR_BLANK = "";

	/** 文字：0 */
	public static final char CH_ZERO = '0';

	/** 行区切 */
	public static final String LINE_SEP =  System.getProperty("line.separator");

    //--------------------------------------------------- インスタンス変数
	//--------------------------------------------------- コンストラクタ
    /**
	 * コンストラクタ<br/>
	 * このオブジェクトはインスタンス化する必要がない。
	 */
	private StrUtil()	{
	}
    // -------------------------------------------------- SetGet Methods
    //--------------------------------------------------- インスタンスメソッド

	/**
	 * NULLの場合ブランクを返す。<br>
	 *
	 * @param str 対象文字列
	 * @return 結果
	 */
	public static String clearNull(String str) {
		if (isEmpty(str)) {
			return STR_BLANK;
		} else {
			return str;
		}
	}

	/**
	 * 引数の文字列を結合する。<br/>
	 * <br/>
	 * 1)nullの場合、ブランクで結合する。<br/>
	 *
	 * @param str1 文字列１
	 * @param str2 文字列２
	 * @return 順番に結合した文字列
	 */
	public static String concat(String str1, String str2) {
		StringBuffer sb = new StringBuffer(STR_BLANK);
		sb.append(clearNull(str1));
		sb.append(clearNull(str2));
		return sb.toString();
	}

	/**
	 * 引数の文字列を結合する。<br/>
	 * <br/>
	 * 1)nullの場合、ブランクで結合する。<br/>
	 *
	 * @param str1 文字列１
	 * @param str2 文字列２
	 * @param str3 文字列３
	 * @return 順番に結合した文字列
	 */
	public static String concat(String str1, String str2, String str3) {
		StringBuffer sb = new StringBuffer(STR_BLANK);
		sb.append(clearNull(str1));
		sb.append(clearNull(str2));
		sb.append(clearNull(str3));
		return sb.toString();
	}

	/**
	 * 引数の文字列を結合する。<br/>
	 * <br/>
	 * 1)nullの場合、ブランクで結合する。<br/>
	 *
	 * @param str1 文字列１
	 * @param str2 文字列２
	 * @param str3 文字列３
	 * @param str4 文字列４
	 * @return 順番に結合した文字列
	 */
	public static String concat(String str1, String str2, String str3, String str4) {
		StringBuffer sb = new StringBuffer(STR_BLANK);
		sb.append(clearNull(str1));
		sb.append(clearNull(str2));
		sb.append(clearNull(str3));
		sb.append(clearNull(str4));
		return sb.toString();
	}

	/**
	 * 引数の文字列を結合する。<br/>
	 * <br/>
	 * 1)nullの場合、ブランクで結合する。<br/>
	 *
	 * @param str1 文字列１
	 * @param str2 文字列２
	 * @param str3 文字列３
	 * @param str4 文字列４
	 * @param str5 文字列５
	 * @return 順番に結合した文字列
	 */
	public static String concat(String str1, String str2, String str3, String str4, String str5) {
		StringBuffer sb = new StringBuffer(STR_BLANK);
		sb.append(clearNull(str1));
		sb.append(clearNull(str2));
		sb.append(clearNull(str3));
		sb.append(clearNull(str4));
		sb.append(clearNull(str5));
		return sb.toString();
	}

	/**
	 * 引数の文字列を結合する。<br/>
	 * <br/>
	 * 1)nullの場合、ブランクで結合する。<br/>
	 * @param str1 文字列１
	 * @param str2 文字列２
	 * @param str3 文字列３
	 * @param str4 文字列４
	 * @param str5 文字列５
	 * @param str6 文字列６
	 * @return 順番に結合した文字列
	 */
	public static String concat(String str1, String str2, String str3, String str4, String str5,
			String str6) {
		StringBuffer sb = new StringBuffer(STR_BLANK);
		sb.append(clearNull(str1));
		sb.append(clearNull(str2));
		sb.append(clearNull(str3));
		sb.append(clearNull(str4));
		sb.append(clearNull(str5));
		sb.append(clearNull(str6));
		return sb.toString();
	}

	/**
	 * 引数の文字列を結合する。<br/>
	 * <br/>
	 * 1)nullの場合、ブランクで結合する。<br/>
	 *
	 * @param str1 文字列１
	 * @param str2 文字列２
	 * @param str3 文字列３
	 * @param str4 文字列４
	 * @param str5 文字列５
	 * @param str6 文字列６
	 * @param str7 文字列７
	 * @return 順番に結合した文字列
	 */
	public static String concat(String str1, String str2, String str3, String str4, String str5,
	        String str6, String str7) {
	    StringBuffer sb = new StringBuffer(STR_BLANK);
	    sb.append(clearNull(str1));
	    sb.append(clearNull(str2));
	    sb.append(clearNull(str3));
	    sb.append(clearNull(str4));
	    sb.append(clearNull(str5));
	    sb.append(clearNull(str6));
	    sb.append(clearNull(str7));
	    return sb.toString();
	}

	/**
	 * 引数の文字列を結合する。<br/>
	 * <br/>
	 * 1)nullの場合、ブランクで結合する。<br/>
	 *
	 * @param str1 文字列１
	 * @param str2 文字列２
	 * @param str3 文字列３
	 * @param str4 文字列４
	 * @param str5 文字列５
	 * @param str6 文字列６
	 * @param str7 文字列７
	 * @param str8 文字列８
	 * @return 順番に結合した文字列
	 */
	public static String concat(String str1, String str2, String str3, String str4, String str5,
			String str6, String str7, String str8) {
		StringBuffer sb = new StringBuffer(STR_BLANK);
		sb.append(clearNull(str1));
		sb.append(clearNull(str2));
		sb.append(clearNull(str3));
		sb.append(clearNull(str4));
		sb.append(clearNull(str5));
		sb.append(clearNull(str6));
		sb.append(clearNull(str7));
		sb.append(clearNull(str8));
		return sb.toString();
	}

	/**
	 * 引数の文字列を結合する。<br/>
	 * <br/>
	 * 1)nullの場合、ブランクで結合する。<br/>
	 *
	 * @param str1 文字列１
	 * @param str2 文字列２
	 * @param str3 文字列３
	 * @param str4 文字列４
	 * @param str5 文字列５
	 * @param str6 文字列６
	 * @param str7 文字列７
	 * @param str8 文字列８
	 * @param str9 文字列９
	 * @return 順番に結合した文字列
	 */
	public static String concat(String str1, String str2, String str3, String str4, String str5,
			String str6, String str7, String str8, String str9) {
		StringBuffer sb = new StringBuffer(STR_BLANK);
		sb.append(clearNull(str1));
		sb.append(clearNull(str2));
		sb.append(clearNull(str3));
		sb.append(clearNull(str4));
		sb.append(clearNull(str5));
		sb.append(clearNull(str6));
		sb.append(clearNull(str7));
		sb.append(clearNull(str8));
		sb.append(clearNull(str9));
		return sb.toString();
	}

	/**
	 * 文字列の開始インデックスから指定文字数分のみ取得する<br/>
	 *
	 * @param str 文字列
	 * @param starIdx 開始インデックス
	 * @param len 取得文字数
	 * @return 指定文字数の文字列
	 */
	public static final String mid(String str, int starIdx, int len) {
		return StringUtils.mid(str, starIdx, len);
	}

	/**
	 * 文字列先頭から指定文字数分のみ取得する<br/>
	 *
	 * @param str 文字列
	 * @param len 取得文字数
	 * @return 指定文字数の文字列
	 */
	public static final String left(String str, int len) {
		return StringUtils.left(str, len);
	}

	/**
	 * 文字列の最後から指定文字数分のみ取得する<br/>
	 *
	 * @param str 文字列
	 * @param len 取得文字数
	 * @return 指定文字数の文字列
	 */
	public static final String right(String str, int len) {
		return StringUtils.right(str, len);
	}

	/**
	 * 先頭から指定バイト数分の文字列を取得する。 commons.lang.StringUtilsのバイト長指定版。
	 *
	 * @param str 文字列
	 * @param bytes 取得バイト数
	 * @return 指定バイト数の文字列
	 */
	public static final String chopByByteLength(String str, int bytes) {
		int position = countByByteLength(str, bytes);
		return str.substring(0, position);
	}

	/**
	 * 前後のスペース(半角・全角)を除去した文字列を返却する。<br/>
	 * 指定された値がnullの場合はnullを返却する。 <br/>
	 *
	 * @param str 文字列
	 * @return 空白除去後の文字列
	 */
	public static final String trimIfPossible(final String str) {
		return (str == null) ? null : trim(str);
	}

	/**
	 * 前後の空白(半角スペース・全角スペース)を除去した文字列を返却する。 <br/>
	 *
	 * @param str 文字列
	 * @return 空白除去後の文字列
	 */
	public static final String trim(final String str) {

		final char CHAR_ZEN_SPACE = '　';	// 全角空白
		final char CHAR_HAN_SPACE = ' ';	// 半角空白

		int begin = 0;
		int end = str.length() - 1;

		CharacterIterator iter = new StringCharacterIterator(str);

		int count = 0;
		for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
			if (c != CHAR_ZEN_SPACE && c != CHAR_HAN_SPACE) {
				begin = iter.getIndex();
				break;
			}
			count++;
		}

		if (count == str.length()) {
			return STR_BLANK;
		}

		for (char c = iter.last(); c != CharacterIterator.DONE; c = iter.previous()) {
			if (c != CHAR_ZEN_SPACE && c != CHAR_HAN_SPACE) {
				end = iter.getIndex();
				break;
			}
		}

		if (begin < end + 1) {
			return str.substring(begin, end + 1);
		} else {
			return str;
		}
	}

	/**
	 * null判定メソッド<br/>
	 *
	 * @param val 判定するオブジェクト
	 * @return パラメータのオブジェクトがnullの場合true
	 */
	public static boolean isNull(Object val) {
		if (val == null) {
			return true;
		}
		return false;
	}

	/**
	 * 空文字判定メソッド<br/>
	 * @param val 判定する文字列
	 * @return パラメータの文字列がnullか空文字の場合true
	 */
	public static boolean isBlank(String val) {
		if (isNull(val)) {
			return true;
		}
		if (val.trim().equals(STR_BLANK)) {
			return true;
		}
		return false;
	}



	/**
	 * 文字列を指定バイト長で折り返す。指定バイト長毎に改行文字を挿入する。<br/>
	 *
	 * @param source 文字列
	 * @param maxLength 行幅(バイト数)
	 * @return
	 */
	public static final String wrapByByteLength(String source, int maxLength) {
		try {
			StringBuffer result = new StringBuffer();

			// 改行毎に文字列を読み込む
			BufferedReader br = new BufferedReader(new StringReader(source));

			// 行数分繰り返す
			String line;
			boolean read = false;
			while ((line = br.readLine()) != null) {
				if (read) {
					result.append(LINE_SEP);
				}

				int characterLength;
				boolean wraped = false;
				while ((characterLength = countByByteLength(line, maxLength)) != 0) {
					if (wraped) {
						result.append(LINE_SEP);
					}

					// 1行分の文字列を追加
					String appendString = line.substring(0, characterLength);
					result.append(appendString);

					// 残りの部分の文字列を取得
					line = line.substring(characterLength);
					wraped = true;
				}
				read = true;
			}

			return result.toString();

		} catch (IOException e) {
			throw new ChainedRuntimeException(e);
		}
	}

	/**
	 * 指定バイト数の文字数を取得する。
	 *
	 * @param source 文字列
	 * @param byteLength 指定バイト数
	 * @return 文字数
	 */
	private static final int countByByteLength(String source, int byteLength) {
		// 文字列がnullの場合から文字列を返す
		if (source == null)
			return 0;

		// 指定最大バイト長よりも、文字列のバイト数が短い場合
		if (source.getBytes().length <= byteLength) {
			// 文字列の長さを返す
			return source.length();
		}

		char[] characters = source.toCharArray();
		int stackSize = 0;
		int position;

		// 一文字ずつ繰り返す
		for (position = 0; position < characters.length; position++) {
			// 一文字のバイトサイズを取得する
			// TODO getBytesが実行環境により異なる可能性あり

			String character = (new Character(characters[position])).toString();
			stackSize += character.getBytes().length;

			if (stackSize > byteLength) {
				break;
			}
		}

		return position;
	}

	/**
	 * バイト数取得<br/>
	 * 注意：UTF-8文字のバイト数を返却する。
	 *
	 * @param val バイト数を取得する文字列
	 * @return 取得したバイト数 UnsupportedEncodingExceptionが発生した場合-1
	 * @throws NullPointerException パラメータがnullの場合
	 */
	public static int getByteLenUTF8(String val) {
		try {
			return val.getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			return -1;
		}
	}

	/**
	 * 文字列の前方(左)を指定した長さになるまで埋める。<br/>
	 *
	 * @param str 文字列
	 * @param len 長さ
	 * @param ch 文字
	 * @return 変換後文字列
	 */
	public static String lpad(String str, int len, char ch) {
		if (str == null) {
			str = STR_BLANK;
		}

		int strLen = len - str.length();
		StringBuffer sb = new StringBuffer(STR_BLANK);
		for (int i = 0; i < strLen; i++) {
			sb.append(ch);
		}

		return concat(sb.toString(), str);
	}

	/**
	 * 文字列の後方(右)を指定した長さになるまで埋める。<br/>
	 *
	 * @param str 文字列
	 * @param len 長さ
	 * @param ch 文字
	 * @return 変換後文字列
	 */
	public static String rpad(String str, int len, char ch) {
		if (str == null) {
			str = STR_BLANK;
		}

		int strLen = len - str.length();
		StringBuffer sb = new StringBuffer(STR_BLANK);
		for (int i = 0; i < strLen; i++) {
			sb.append(ch);
		}

		return concat(str, sb.toString());
	}

	/**
	 * 文字列の前方(左)を0で埋める。（文字列用）<br/>
	 *
	 * @param str 文字列
	 * @param len 長さ
	 * @return 処理後の文字列
	 */
	public static String fillZero(String str, int len) {
		return lpad(str, len, CH_ZERO);
	}

	/**
	 * 0を左に埋め込む。(数値用)<br/>
	 *
	 * @param num 数値
	 * @param len 長さ
	 * @return 処理後の文字列
	 */
	public static String fillZero(int num, int len) {
		return lpad(String.valueOf(num), len, CH_ZERO);
	}

	/**
	 * 指定した文字列で置換を行う。<br/>
	 *  StringBufferを使用しているためStringから提供するreplace()メソッドより速度が速い。
	 * @param str 文字列
	 * @param oldStr 置換キーワード
	 * @param newStr 変換後文字列
	 * @return 処理後の文字列
	 */
	public static String replace(String str, String oldStr, String newStr) {
		if (str == null)
			return null;

		StringBuffer dest = new StringBuffer(STR_BLANK);
		int len = oldStr.length();
		int srclen = str.length();
		int pos = 0;
		int oldpos = 0;

		while ((pos = str.indexOf(oldStr, oldpos)) >= 0) {
			dest.append(str.substring(oldpos, pos));
			dest.append(newStr);
			oldpos = pos + len;
		}

		if (oldpos < srclen)
			dest.append(str.substring(oldpos, srclen));

		return dest.toString();
	}



	/**
	 * 指定したバイト数の文字数を取得する。（UTF-8）<br/>
	 * 注意）キャラクタセットをUTF-8にするため、<br/>
	 * 全角は3バイト半角は1バイトとして扱われる。<br/>
	 *
	 * @param str 対象文字列
	 * @param bytes 指定バイト数
	 * @return 処理後の文字列
	 */
	public static String cutStrUTF8Byte(String str, int bytes) {

		if (isEmpty(str)) {
			return "";
		}
		String res = "";
		try {
			byte[] byteStr = str.getBytes("UTF-8");
			int len = byteStr.length;

			if (len > bytes) {
				res = new String(byteStr, 0, bytes, "UTF-8");
			} else {
				res = str;
			}
			return res;
		} catch (UnsupportedEncodingException e) {
			throw new SystemException("不明なキャラクタセットです。");
		}
	}

	/**
	 * 文字列中の指定文字をすべて除去する。<br/>
	 * 指定した文字列をすべて除去する。<br/>
	 * @param str 文字列
	 * @param strExt 除去対象
	 * @return 例外キャラクタをすべて削除した文字列
	 */
	public static String removeAll(String str, String strExt) {

		if (isEmpty(strExt)) {
			return str;
		} else {
			String res = str;

			int len   = isNull(res) ? 0 : res.length();
			int bytes = isNull(res) ? 0 : res.getBytes().length;

			if (len == bytes) {
				char[] charArr =  strExt.toCharArray();
				for (int i = 0; i < charArr.length; i++) {
					res = remove(res, charArr[i]) ;
				}
			} else {
				for (int i = 0; i < strExt.length(); i++) {
					res = remove(res, strExt.substring(i, i + 1));
				}
			}

			return res;
		}
	}


	/**
	 * フラグのon|offチェック<br/>
	 * @param flg フラグ
	 * @return 判断結果
	 */
	public static boolean isFlagOn(String flg){
		if (CommonConsts.FLG_ON.equals(flg) ||
				"on".equalsIgnoreCase(flg)) {
			return true;
		} else {
			return false;
		}
	}

}
