
package tech3g.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import tech3g.common.exceptions.SystemException;

/**
 * <pre>
 * FormatUtilクラス。
 * 文字列関連のフォーマットUtilクラス。
 *
 * </pre>
 */
public class FormatUtil {


	/**
	 * カンマ編集。<br/>
	 *
	 * 整数のカンマ編集を行う。
	 * 注意）指定した文字列が数字ではない場合はそのまま返却する。
	 *
	 * @param num カンマ編集文字列
	 * @return カンマ編集した文字列
	 */
	public static String formatComma(String num) {

		try {

			if (StrUtil.isEmpty(num)) {
				return num;
			}

			if (!ValidationUtil.isDecimalType(num)) {
				return num;
			}

			String[] splitNum = num.split("[.]");

			DecimalFormat f = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.JAPAN);
			f.setPositivePrefix("");
			f.setNegativePrefix("-");

			if (splitNum.length > 1) {
				return StrUtil.concat(f.format(new BigDecimal(splitNum[0])), "." ,splitNum[1]);
			} else {
				return f.format(new BigDecimal(splitNum[0]));
			}

		} catch (Exception e) {
			return num;
		}
	}

	/**
	 * カンマ編集の解除<br/>
	 * @param num 数値の文字列
	 * @return 結果文字列
	 */
	public static String unformatComma(String num) {

		return StrUtil.remove(num, ",");

	}

	/**
	 * 郵便番号フォーマット変換<br/>
	 * 7桁の郵便番号を"999-9999"フォーマットに変えて返却する。<br/>
	 *
	 * @param zipCd 郵便番号
	 * @return 999-9999フォーマット郵便番号
	 */
	public static String formatZip(String zipCd) {

		if (StringUtils.isEmpty(zipCd)) {
			return "";
		}

		if ("-".equals(zipCd.trim())) {
			return "";
		}

		if (zipCd.trim().length() != 7) {
			return zipCd.trim();
		}

		if (zipCd.matches("[\\s]*[0-9]*[\\-][0-9]*")) {
			return zipCd;
		}

		StringBuffer sb = new StringBuffer("");

		if (zipCd.length() <= 3) {
			sb.append(zipCd);
			return sb.toString();
		} else {
			sb.append(zipCd.substring(0, 3));
			sb.append("-");
			sb.append(zipCd.substring(3));
			return sb.toString();
		}
	}

	/**
	 * 郵便番号フォーマット解除<br/>
	 * @param zipCd 999-9999フォーマットの郵便番号
	 * @return 9999999フォーマットの郵便番号
	 */
	public static String unformatZip (String zipCd) {
		if (StringUtils.isEmpty(zipCd)) {
			return "";
		}

		String zip = zipCd;

		zip = StrUtil.remove(zip, "〒");
		zip = StrUtil.remove(zip, "-");

		return zip;
	}

	/**
	 * 住所と方書の結合<br/>
	 * 「住所 + "　" + 方書」の形式で結合し返却する。
	 * @param ccAd 住所文字列
	 * @param ccAad 方書文字列
	 * @return 「住所 + "　" + 方書」
	 */
	public static String concatAddr(String ccAd, String ccAad) {

		if (StrUtil.isEmpty(ccAd)) {
			throw new SystemException("引数指定が正しくありません。");
		}

		if (StrUtil.isEmpty(ccAad)) {
		// 方書なし
			return ccAd;
		} else {
			return StrUtil.concat(ccAd, "　", ccAad);
		}
	}

	/**
	 * 住所と方書の結合<br/>
	 * Mapから該当キーの住所と方書を「住所 + "　" + 方書」の形式で結合し返却する。
	 * @param map 住所と方書が格納されているMap
	 * @param ccAdKey 住所のMapキー
	 * @param ccAadKey 方書のMapキー
	 * @return 「住所 + "　" + 方書」
	 */
	public static String concatAddr(Map map, String ccAdKey, String ccAadKey) {

		if (map == null || StrUtil.isEmpty(ccAdKey)) {
			throw new SystemException("引数指定が正しくありません。");
		}

		if (StrUtil.isEmpty(MapUtils.getString(map, ccAadKey))) {
		// 方書なし
			return MapUtils.getString(map, ccAdKey);
		} else {
			return StrUtil.concat(MapUtils.getString(map, ccAdKey), "　", MapUtils.getString(map, ccAadKey));
		}
	}

}
