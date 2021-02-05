package tech3g.common.util;

import java.io.UnsupportedEncodingException;

import tech3g.common.exceptions.SystemException;

/**
 * <pre>
 * PadUtilクラス。
 * 桁埋関連の処理を行う。
 * </pre>
 */
public class PadUtil {

	/**
	 * 文字列の前方(左)を指定したバイト数になるまで埋める。<br/>
	 * @param str 文字列
	 * @param encoding エンコード
	 * @param ch 指定文字
	 * @param length バイト数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String padStr(String str, String encoding, String ch, int length) throws UnsupportedEncodingException {
		String res = StrUtil.trim(str);
		if (StrUtil.isEmpty(res)) {
			res = appendRight("",  length, ch);
		} else {

			res = res.trim();
			if (res.getBytes(encoding).length > length) {
				throw new SystemException("文字列のバイト数が指定したバイト数を超えています。文字列：「"+ str + "」指定したバイト数：「" + length + "」");
			}
			int size = (length - res.getBytes(encoding).length) / ch.getBytes(encoding).length;
			res = appendRight(res, size, ch);
			res = res + appendRight("", length - res.getBytes(encoding).length, " ");
		}
		return res;
	}

	/**
	 * 文字列の前方(左)を指定したバイト数になるまで0を埋める。<br/>
	 * @param str 文字列
	 * @param encoding エンコード
	 * @param length バイト数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String padNumber(String str, String encoding, int length) throws UnsupportedEncodingException {

		String res = StrUtil.trim(str);

		if (StrUtil.isEmpty(res)) {
			return appendLeft("", length, "0");
		} else {

			if (res.getBytes().length > length) {
				throw new SystemException("文字列のバイト数が指定したバイト数を超えています。文字列：「"+ str + "」指定したバイト数：「" + length + "」");
			}

			if (!ValidationUtil.isNum(res)) {
				throw new SystemException("文字列「"+ str +"」は数字ではありません。");
			}

			int size = (length -str.getBytes(encoding).length) / "0".getBytes(encoding).length;
			res = appendLeft(res, size, "0");
		}
		return res;
	}

	/**
	 * 文字列に符号を付け、前方(左)にを指定したバイト数になるまで0を埋める。<br/>
	 * @param str 文字列
	 * @param encoding エンコード
	 * @param length バイト数
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String padSignNumber(String str, String encoding, int length) throws UnsupportedEncodingException {
		String res = StrUtil.trim(str);
		if (StrUtil.isEmpty(res)) {
			return "+" + appendLeft("", length-1, "0");
		} else {
			if (str.length() > length) {
				throw new SystemException("文字列のバイト数がが指定したバイト数を超えています。文字列：「"+ str + "」指定したバイト数：「" + length + "」");
			}

			if (!ValidationUtil.isNum(res)) {
				throw new SystemException("文字列「"+ str +"」は数字ではありません。");
			}

			int size = (length -str.getBytes(encoding).length) / "0".getBytes(encoding).length;

			if (str.startsWith("-") || str.startsWith("+")) {
				res = str.substring(0, 1) + appendLeft(res, size-1, "0");
			} else {
				res ="+" + appendLeft(res, size-1, "0");
			}
		}
		return res;
	}

	/**
	 * 文字列の後方(右)を指定したバイト数になるまで埋める。<br/>
	 *
	 * @param str 対象文字列
	 * @param len 長さ
	 * @param ch 文字
	 * @return 変換後文字列
	 * @throws UnsupportedEncodingException
	 */
	public static String appendRight(String str, int len, String ch) {
		if (str == null) {
			str = "";
		}

		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < len; i++) {
			sb.append(ch);
		}
		return str + sb.toString();
	}

	/**
	 * 文字列の前方(左)を指定したバイト数になるまで埋める。<br/>
	 *
	 * @param str 対象文字列
	 * @param len 長さ
	 * @param ch 文字
	 * @return 変換後文字列
	 * @throws UnsupportedEncodingException
	 */
	public static String appendLeft(String str, int len, String ch) {
		if (str == null) {
			str = "";
		}

		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < len; i++) {
			sb.append(ch);
		}
		return  sb.toString() + str;
	}
}
