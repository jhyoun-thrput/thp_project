package tech3g.common.util;

import java.math.BigDecimal;

import tech3g.common.exceptions.SystemException;

/**
 * <pre>
 * NumberUtilクラス。
 * 数字、金額関連のUTILクラス。
 * </pre>
 *
 */
public class NumberUtil {



	private static final String FIX_INTEGER_PATTERN = "\\d+";

    /**
	 * コンストラクタ<br/>
	 * このオブジェクトはインスタンス化する必要がない。
	 */
	private NumberUtil()	{
	}

//	/**
//     * 金額文字列をカンマ編集(3桁区切)する。<br/>
//     * @version 修正履歴
//     *          <ul>
//     *          <li>2009/07/24 : 新規作成 (revised by Jang.D.Y)</li>
//     *          </ul>
//     * @param str 金額文字列
//     */
//	public static String conMoneyType(String str) {
//
//		if (StrUtil.isEmpty(str)) {
//			return "";
//		} else {
//			return conMoneyType(StrUtil.remove(str, ","), 0);
//		}
//	}

//	/**
//     * 金額文字列をカンマ編集(3桁区切)する。<br/>
//     * 指定タイプにより小数以下の表示桁数を制御する。<br/>
//     * @version 修正履歴
//     *          <ul>
//     *          <li>2009/07/24 : 新規作成 (revised by Jang.D.Y)</li>
//     *          </ul>
//     * @param str 金額文字列
//     * @param dpoint 小数以下表示桁数
//     */
//	public static String conMoneyType(String str, int dpoint) {
//
//		String dformat = null;
//
//		switch (dpoint) {
//
//		case 0:
//			dformat = "###,###,###,###,###.############";
//			break;
//		case 1:
//			dformat = "###,###,###,###,##0.0";
//			break;
//		case 2:
//			dformat = "###,###,###,###,##0.00";
//			break;
//		case 3:
//			dformat = "###,###,###,###,##0.000";
//			break;
//		case 4:
//			dformat = "###,###,###,###,##0.0000";
//			break;
//		case 5:
//			dformat = "###,###,###,###,##0.00000";
//			break;
//		default:
//			throw new SystemException("引数[dpoint]が不正です。　引数[dpoint]：「" + dpoint + "」");
//		}
//
//		if (StrUtil.isBlank(str)) {
//			return str;
//		} else {
//			try {
//    			double change = Double.valueOf(str).doubleValue();
//    			DecimalFormat decimal = new DecimalFormat(dformat);
//    			return decimal.format(change);
//			} catch (Exception e) {
//				return str;
//			}
//		}
//	}

	/**
	 * 文字列をBigDecimalに変換<br/>
	 * パラメータの文字列をBigDecimalに変換して返却。
	 * そのとき、カンマは除去して変換。
	 *
	 * ※カンマつきの数字の文字列対応。
	 * @param num 文字列
	 * @return BigDecimal
	 */
	public static BigDecimal conv2BigDecimal(String num) {

		if (StrUtil.isEmpty(num)) {
			return null;
		}

		String number = StrUtil.remove(num, ",").trim();

		BigDecimal bd = null;

		try {
			bd = new BigDecimal(number);
		} catch(Exception e) {
			throw new SystemException("BigDecimalタイプに変換できませんでした。", e);
		}
		return bd;
	}

	/**
	 * 文字列をlongに変換<br/>
	 * nullの場合は0を返却。<br/>
	 *
	 * ※カンマ除去、小数点切り捨て
	 * <br/>
	 * @param num 数字文字列
	 * @return long
	 */
	public static long conv2Long(String num) {

		if (StrUtil.isEmpty(num)) {
			return 0;
		}

		// カンマ除去
		String number = StrUtil.remove(num, ",").trim();
		// 小数点切り捨て
		int idx = number.indexOf(".");
		if (idx > 0) {
			number = number.substring(0, idx);
		}

		long result;

		try {
			result = Long.valueOf(number);
		} catch (Exception e) {
			throw new SystemException("longタイプに変換できませんでした。", e);
		}
		return result;
	}

	/**
	 *  引数の文字列を数字への変換を行います。
	 *  引数がヌル又はブランクの場合は0を返却します。
	 *
	 * @param intStr
	 * @return int
	 */
	public static int conv2Int(String intStr){
		if (StrUtil.isEmpty(intStr)){
			return 0;
		}else{
			try{
				String intRes = StrUtil.removeAll(intStr, ",");
				return Integer.parseInt(intRes);
			}catch(Exception ne){
				throw new SystemException("intに変換できませんでした。", ne);
			}
		}
	}

	public static boolean isNumber(String str) {
		boolean result = false;

		if (!StrUtil.isNull(str) || !StrUtil.isEmpty(str)) {
			if (str.matches(FIX_INTEGER_PATTERN)) {
				result = true;
			}
		}
		return result;
	}
}
