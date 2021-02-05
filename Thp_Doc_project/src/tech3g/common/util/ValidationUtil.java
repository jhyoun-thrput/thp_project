package tech3g.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.StringTokenizer;

import org.apache.commons.validator.GenericValidator;

import tech3g.common.exceptions.SystemException;

/**
 * <pre>
 * ValidationUtilクラス。
 * バリデーションチェックのutilクラス。
 * インスタンス化は出来ない。
 * </pre>
 *
 */
public final class ValidationUtil extends GenericValidator{

    //--------------------------------------------------- 定数
	/** 半角ｶﾅ文字コード始め */
	private final static int HANKAKU_KANA_START = 65382;
	/** 半角ｶﾅ文字コード終わり */
	private final static int HANKAKU_KANA_END = 65439;
	/** 半角数字コード始め */
	private final static int NUMBER_START = 48;
	/** 半角数字コード終わり */
	private final static int NUMBER_END = 57;
	/** 半角英字（大文字）コード始め */
	private final static int ALPHABET_UPPER_START = 65;
	/** 半角英字（大文字）コード終わり */
	private final static int ALPHABET_UPPER_END = 90;
	/** 半角英字（小文字）コード始め */
	private final static int ALPHABET_LOWER_START = 97;
	/** 半角英字（小文字）コード終わり */
	private final static int ALPHABET_LOWER_END = 122;

	/** 全角数字コード始め */
	private final static int ZEN_NUM_START = 65296;
	/** 全角数字コード終わり */
	private final static int ZEN_NUM_END = 65305;

	/** 全角英字（大文字）コード始め */
	private final static int ZEN_ALPHABET_UPPER_START = 65313;
	/** 全角英字（大文字）コード終わり */
	private final static int ZEN_ALPHABET_UPPER_END = 65338;

	/** 全角英字（小文字）コード始め */
	private final static int ZEN_ALPHABET_LOWER_START = 65345;
	/** 全角英字（小文字）コード終わり */
	private final static int ZEN_ALPHABET_LOWER_END = 65370;

    /**
     * 全角カナのチェックに使用する文字列。
     */
    protected final static String ZEN_KANA_TABLE =
        "アイウヴエオァィゥェォカキクケコヵヶガギグゲゴサシスセソ"
            + "ザジズゼゾタチツテトダヂヅデドナニヌネノハヒフヘホ"
            + "バビブベボパピプペポマミムメモヤユヨャュョラリルレロ"
            + "ワヮヰヱヲッンー";


    //--------------------------------------------------- インスタンス変数
    //--------------------------------------------------- コンストラクタ
    /**
	 * コンストラクタ<br/>
	 * このオブジェクトはインスタンス化する必要がない。
	 */
	private ValidationUtil()	{
	}

    // -------------------------------------------------- SetGet Methods
    //--------------------------------------------------- インスタンスメソッド
	/**
	 * 文字が半角文字か判定する。<br/>
	 *
	 * @param chr 文字
	 * @return パラメータが半角文字である場合 true
	 */
	public static boolean isHankakuChar(char chr) {
		return ((0x0080 > chr) || (chr >= 0xff61 && chr <= 0xff9f));
	}

	/**
	 * 文字列が半角文字か判定する。<br/>
	 * 文字列がすべて半角であることをチェックする。
	 * @param str 文字列
	 * @return strの文字がすべて半角文字である場合true、その他の場合 false。strがnullの場合false
	 */
	public static boolean isHankaku(String str) {
		if (str == null) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (0x0080 < c && !(c >= 0xff61 && c <= 0xff9f)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字列が全角文字か判定する。<br/>
	 *
	 * @param str 文字列
	 * @return パラメータの文字列に１バイト文字が含まれていない場合true
	 */
	public static boolean isDoubleByte(String str) {
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 0x0020 && c <= 0x0080) {
				return false;
			} else if (c >= 0xff61 && c <= 0xff9f) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字列が半角数字か判定する。<br/>
	 *
	 * @param str 判定する文字列
	 * @return パラメータの文字列に半角数字以外含まれていない場合true
	 */
	public static boolean isNum(String str) {
		if (StrUtil.isEmpty(str))
			return false;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c < 0x0030 || c > 0x0039) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字列が日付形式「yyyy/MM」か判定する。<br/>
	 *
	 * @param str 文字列
	 * @return "yyyy/MM"形式の場合true
	 */
	public static boolean isRightDateFormat(String str) {
		final String delim = "/";
		int delimCnt = 0;
		if (StrUtil.isBlank(str))
			return false;

		// 半角数字か'/'のみ
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == 0x002f)
				delimCnt++;
			if (c != 0x002f && (c < 0x0030 || c > 0x0039))
				return false;
		}
		// '/'が1個以上はエラー
		if (delimCnt != 1)
			return false;

		StringTokenizer st = new StringTokenizer(str, delim);
		int count = st.countTokens();

		// '/'で区切った時に２つに区切られる
		if (count != 2)
			return false;

		// １つ目のトークンは半角４文字
		if (StrUtil.getByteLenUTF8(st.nextToken()) != 4)
			return false;

		// ２つ目のトークンは半角２or１文字
		int secondTokenCount = StrUtil.getByteLenUTF8(st.nextToken());
		if (secondTokenCount != 2 && secondTokenCount != 1)
			return false;
		return true;
	}

	/**
	 * 文字列が日付形式「yyyyMMdd」か判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isYYYYMMDD(String str) {
		return DateUtil.isYYYYMMDD(str);
	}


	/**
	 * 文字列が日付形式「yyyyMM」か判定する。<br/>
	 *
	 * @param str Date文字列
	 * @return true | false
	 */
	public static boolean isYYYYMM(String str) {
		return DateUtil.isYYYYMM(str);
	}


	/**
	 * 文字列が日付形式「yyyy」か判定する。<br/>
	 *
	 * @param str Date文字列
	 * @return true | false
	 */
	public static boolean isYYYY(String str) {
		return DateUtil.isYYYY(str);
	}

	/**
     * 文字列が日付形式「jyy_MM_dd」か判定する。<br/>
     * ※参考）JYY_MM_DD : 例）H21.03.09
	 * @param str 和暦タイプJYY_MM_DD
	 * @return true | false
	 */
	public static boolean isJYYMMDD(String str) {
		return DateUtil.isJYY_MM_DD(str);
	}

	/**
     * 文字列が日付形式「jyy_MM」か判定する。<br/>
     * ※参考）JYY_MM : 例）H21.03
	 * @param str 和暦タイプJYY_MM
	 * @return true | false
	 */
	public static boolean isJYYMM(String str) {
		return DateUtil.isJYY_MM(str);
	}

	/**
     * 文字列が日付形式「jyy」か判定する。<br/>
     * ※参考）JYY_MM : 例）H21
	 * @param jyy 和暦タイプJYY
	 * @return true | false
	 */
	public static boolean isJYY(String jyy) {
		return DateUtil.isJYY(jyy);
	}

	/**
	 * 文字列が半角カナ文字か判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isHankakuKanaOnly(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] < HANKAKU_KANA_START || chars[i] > HANKAKU_KANA_END)
				return false;
		}
		return true;
	}

	/**
	 * 文字列が半角カナ文字、又は半角スペースか判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isHankakuKanaSpace(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != ' ' && (chars[i] < HANKAKU_KANA_START || chars[i] > HANKAKU_KANA_END) )
				return false;
		}
		return true;
	}

	/**
	 * 半角ｶﾅ、半角スペース、ローマ字の判定<br/>
     * 文字列が半角カナ文字、又は半角スペース、ローマ字か判定する。<br/>
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isHankakuKanaAlphaSpace(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != ' '
				&& (chars[i] < HANKAKU_KANA_START || chars[i] > HANKAKU_KANA_END)
				&& (chars[i] < ALPHABET_UPPER_START || chars[i] > ALPHABET_UPPER_END)
				&& (chars[i] < ALPHABET_LOWER_START || chars[i] > ALPHABET_LOWER_END)
			) {
				return false;
			}
		}
		return true;
	}








	/**
	 * 文字列に半角カナ文字が含まれているか判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isHankakuKanaIncluded(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] >= HANKAKU_KANA_START && chars[i] <= HANKAKU_KANA_END)
				return true;
		}
		return false;
	}

	/**
	 * 文字列が半角カナ以外の半角文字か判定する。<br/>
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isAscii(String str) {
		if (!isHankaku(str))
			return false;
		else if (isHankakuKanaIncluded(str))
			return false;
		return true;
	}

	/**
	 * 文字列が半角数字、又は半角アルファベットか判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isNumberAlphabet(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] >= NUMBER_START && chars[i] <= NUMBER_END)
				continue;
			else if ((chars[i] >= ALPHABET_UPPER_START && chars[i] <= ALPHABET_UPPER_END)
					|| chars[i] >= ALPHABET_LOWER_START && chars[i] <= ALPHABET_LOWER_END)
				continue;
			return false;
		}
		return true;
	}

	/**
	 * 文字列が半角数字、又は半角大文字アルファベットか判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isNumberUpper(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] >= NUMBER_START && chars[i] <= NUMBER_END)
				continue;
			else if (chars[i] >= ALPHABET_UPPER_START && chars[i] <= ALPHABET_UPPER_END)
				continue;
			return false;
		}
		return true;
	}

	/**
	 * 文字列が半角数字、又は半角小文字アルファベットか判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isNumberLower(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] >= NUMBER_START && chars[i] <= NUMBER_END)
				continue;
			else if (chars[i] >= ALPHABET_LOWER_START && chars[i] <= ALPHABET_LOWER_END)
				continue;
			return false;
		}
		return true;
	}

	/**
	 * 文字列が半角アルファベットか判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isAlphabet(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if ((chars[i] < ALPHABET_UPPER_START || chars[i] > ALPHABET_UPPER_END)
					&& (chars[i] < ALPHABET_LOWER_START || chars[i] > ALPHABET_LOWER_END))
				return false;
		}
		return true;
	}

	/**
	 * 文字列が半角大文字アルファベットか判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isUpperCharacters(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] < ALPHABET_UPPER_START || chars[i] > ALPHABET_UPPER_END)
				return false;
		}
		return true;
	}

	/**
	 * 文字列が半角小文字アルファベットか判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isLowerChacracters(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] < ALPHABET_LOWER_START || chars[i] > ALPHABET_LOWER_END)
				return false;
		}
		return true;
	}

	/**
	 * 文字列が正数か判定する。<br/>
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isPositive(String str) {
		try {

			String rmvComma = StrUtil.remove(str, ",");

			BigDecimal num = new BigDecimal(rmvComma);
			int i = num.compareTo(new BigDecimal(0));
			if (i >= 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 文字列が１以下の小数か判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isPercent(String str) {
		try {
			BigDecimal num = new BigDecimal(str);
			int i = num.compareTo(new BigDecimal(1));
			if (i <= 0) {
				i = num.compareTo(new BigDecimal(-1));
				if (i >= 0) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 文字列の小数部桁数が指定桁数以上か判定する。<br/>
	 *
	 * @param str 文字列
	 * @param scale 桁数
	 * @return true | false
	 */
	public static boolean isMinScale(String str, int scale) {
		if (str.indexOf(".") == -1)
			return false;
		else if (str.length() - str.indexOf(".") - 1 >= scale)
			return true;
		else
			return false;
	}

	/**
	 * 文字列の小数部桁数が指定桁数以下か判定する。<br/>
	 *
	 * @param str 文字列
	 * @param scale 桁数
	 * @return true | false
	 */
	public static boolean isMaxScale(String str, int scale) {
		if (str.indexOf(".") == -1)
			return false;
		else if (str.length() - str.indexOf(".") - 1 <= scale)
			return true;
		else
			return false;
	}

	/**
	 * 文字列が全角文字か判定する。<br/>
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isZenkaku(String str) {
		return isDoubleByte(str);
	}

	/**
	 * 文字列が全角数字か判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isZenNumber(String str) {

		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] < ZEN_NUM_START || chars[i] > ZEN_NUM_END)
				return false;
		}
		return true;
	}

	/**
	 * 文字列が全角アルファベットか判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isZenAlphabet(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if ((chars[i] < ZEN_ALPHABET_UPPER_START || chars[i] > ZEN_ALPHABET_UPPER_END)
					&& (chars[i] < ZEN_ALPHABET_LOWER_START || chars[i] > ZEN_ALPHABET_LOWER_END))
				return false;
		}
		return true;
	}

	/**
	 * 文字列が全角数字、又は全角アルファベットか判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isZenNumAlphabet(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] >= ZEN_NUM_START && chars[i] <= ZEN_NUM_END)
				continue;
			else if ((chars[i] >= ZEN_ALPHABET_UPPER_START && chars[i] <= ZEN_ALPHABET_UPPER_END)
					|| chars[i] >= ZEN_ALPHABET_LOWER_START && chars[i] <= ZEN_ALPHABET_LOWER_END)
				continue;
			return false;
		}
		return true;
	}

	/**
	 * 文字列が全角カナか判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isZenKana(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (ZEN_KANA_TABLE.indexOf(chars[i])>= 0) {
				continue;
			}
			return false;
		}
		return true;
	}

	/**
	 * 文字列が全角ひらがなか判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isZenHira(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] < 0x3041 || chars[i] > 0x3094) {
				return false;
			}
		}
		return true;
	}

	/**
	 * フラグ項目か判定する。"0"か"1"を判定する。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isFlagType(String str) {
		return (str.equals("1") || str.equals("0"));
	}

	/**
	 * 文字列が指定文字数と一致するか判定する。<br/>
	 *
	 * @param str 文字列
	 * @param full 文字数
	 * @return true | false
	 */
	public static boolean isSpecifiedLength(String str, int full) {
		return str.length() == full;
	}

	/**
	 * 文字列がBigDecimalに変換できるか判定する。<br/>
	 * カンマがあればカンマを除去しチェックを行う。<br/>
	 *
	 * @param str 文字列
	 * @return true | false
	 */
	public static boolean isDecimalType(String str) {
		try {
			//-- カンマを取り除く
			String rmvComma = StrUtil.remove(str, ",");
			new BigDecimal(rmvComma);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 文字列が指定バイト(UTF-8)数以上か判定する。<br/>
	 *
	 * @param str 文字列
	 * @param min バイト数
	 * @return true | false
	 */
	public static boolean isMinBytes(String str, int min) {
		try {
			return (str.getBytes("UTF-8").length >= min);
		} catch (UnsupportedEncodingException e) {
			throw new SystemException(e);
		}
	}

	/**
	 * 文字列が指定指定バイト(UTF-8)以下か判定する。<br/>
	 *
	 * @param str 文字列
	 * @param max バイト数
	 * @return true | false
	 */
	public static boolean isMaxBytes(String str, int max)  {
		try {
			return (str.getBytes("UTF-8").length <= max);
		} catch (UnsupportedEncodingException e) {
			throw new SystemException(e);
		}

	}

	/**
	 * 文字列の整数部と小数部の桁数を判定する。<br/>
	 * @param str 文字列
	 * @param decFmt 形式(整数部.小数部)
	 * @return
	 */
	public static boolean isOkDecimalFormat(String str, String decFmt) {

		String decChkStr = StrUtil.remove(str, ",");

		String[] splitFormat = decFmt.split("\\.");
		int precision= Integer.parseInt(splitFormat[0]);
		int scale= Integer.parseInt(splitFormat[1]);

		String[] strSplit = decChkStr.split("\\.");
		String strPrecision= "" +  (new BigDecimal(strSplit[0])).toString();

		String strScale= "";

		if (strSplit.length > 1 && isNum(strSplit[1])) {
			strScale= "" + (new BigDecimal(strSplit[1])).toString();
		}

		if (strPrecision.length() > precision
				|| strScale.length() > scale) {
			return false;
		}

		return true;
	}
}
