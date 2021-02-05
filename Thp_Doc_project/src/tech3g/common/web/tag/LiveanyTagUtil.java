
package tech3g.common.web.tag;

import tech3g.common.constants.CommonConsts;
import tech3g.common.constants.HtmlConsts;
import tech3g.common.exceptions.SystemException;
import tech3g.common.util.FormatUtil;
import tech3g.common.util.StrUtil;

/**
 * <pre>
 * <liveany>カスタムタグの共通Utilクラス。
 * <liveany>主にカスタムタグにおいての文字列処理を行う。
 * </pre>
 *
 */
public class LiveanyTagUtil {

	/**
	 * 入力制御のJavaScript関数文字列作成<br/>
	 * 入力タイプによって、キーダウンEventに入力制御のJavaScript関数文字列を返却する。 <br/>
     * @param inputType 入力タイプ
	 * @return 入力制御のJavaScript関数文字列
	 */
	public static String getKeyDownEvent(String inputType) {

		if (StrUtil.isEmpty(inputType)) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer("");
			sb.append(HtmlConsts.FUN_CTL_INPUT);
			sb.append("(");
			sb.append("this");
			sb.append(",'");
			sb.append(inputType);
			sb.append("'");

			// 例外文字がある場合はセット
			sb.append(",");
			sb.append("'");
			sb.append("'");
			sb.append(");");
			return sb.toString();
		}
	}

    /**
     * 入力制御のJavaScript関数文字列作成<br/>
     * 入力タイプによって、キーダウンEventに入力制御のJavaScript関数文字列を返却する。 <br/>
     * @param inputType 入力タイプ
     * @param chExt 例外文字
     * @return 入力制御のJavaScript関数文字列
     */
    public static String getKeyDownEvent(String inputType, String chExt) {

        if (StrUtil.isEmpty(inputType)) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer("");
            sb.append(HtmlConsts.FUN_CTL_INPUT);
            sb.append("(");
            sb.append("this");
            sb.append(",'");
            sb.append(inputType);
            sb.append("'");

            // 例外文字がある場合はセット
            sb.append(",");
            sb.append("'");
	        if (!StrUtil.isEmpty(chExt)) {
	        	sb.append(chExt.replace("\'", "\\\'").replace("\"", "\\\""));
	        }
            sb.append("'");
            sb.append(");");
            return sb.toString();
        }
    }

	/**
	 * JavaScriptのPadding関数作成<br/>
	 *
	 * Padding設定により、JavaScriptのPadding関数文字列を作成し返却する。
	 *
	 * @param property 項目名
	 * @param lpad 左Padding設定
	 * @param rpad 右Padding設定
	 * @param dpad 数字タイプのPadding設定
	 * @param maxlength 項目の長さ
	 * @return JavaScriptのPadding関数文字列
	 */
	public static String getPaddingStr(String property, String lpad, String rpad, String dpad, String maxlength) {
        StringBuffer padStr = new StringBuffer("");
        if (lpad != null && rpad != null) {
            throw new SystemException("「lpad」、「rpad」を両方とも設定することは出来ません。");
        }

        if (!StrUtil.isEmpty(dpad)) {
            if (dpad.matches("[0-9]+")) {
            // 「繰返す文字:桁数」の形式で入力した場合
            	padStr.append("decimalPad(this, "+ dpad +", '"+ "0" +"');");
            } else if (dpad.matches("[\\S|\\s]{1}[\\:]{1}[0-9]+")) {
            	String[] spletPadStr = dpad.split(":");
            	padStr.append("decimalPad(this,"+ spletPadStr[1] +", '"+ spletPadStr[0] +"');");
            } else {
            	throw new SystemException("「dpad」属性の設定が間違っています。「桁数」形式で入力して下さい。（" +"画面項目「" + property + "」");
            }
        }

        if (lpad != null ){
            if (lpad.length() == 1) {
            // 繰返す文字のみ入力した場合
                if (maxlength == null) {
                    throw new SystemException("「lpad」属性を設定した場合は、「maxlength」を指定する必要があります。（" +"画面項目「" + property + "」");
                }
                padStr.append("lpad(this, "+ maxlength +", '"+ lpad +"');");
            } else if (lpad.matches("[\\S|\\s]{1}[\\:]{1}[0-9]+")) {
            // 「繰返す文字:桁数」の形式で入力した場合
            	String[] spletPadStr = lpad.split(":");
            	padStr.append("lpad(this, "+ spletPadStr[1] +", '"+ spletPadStr[0] +"');");
            } else {
            	throw new SystemException("「lpad」属性の設定が間違っています。1桁の文字又は「文字:桁数」形式で入力して下さい。（" +"画面項目「" + property + "」");
            }

        } else if (rpad != null){
            if (rpad.length() == 1) {
            // 繰返す文字のみ入力した場合
                if (maxlength == null) {
                    throw new SystemException("「rpad」属性を設定した場合は、「maxlength」を指定する必要があります。（" +"画面項目「" + property + "」");
                }
                padStr.append("lpad(this, "+ maxlength +", '"+ rpad +"');");
            } else if (rpad.matches("[\\S|\\s]{1}[\\:]{1}[0-9]+")) {
            // 「繰返す文字:桁数」の形式で入力した場合
            	String[] spletPadStr = rpad.split(":");
            	padStr.append("rpad(this, "+ spletPadStr[1] +", '"+ spletPadStr[0] +"');");
            } else {
            	throw new SystemException("「rpad」属性の設定が間違っています。1桁の文字(文字のみ)又は「文字:桁数」形式で入力して下さい。（" +"画面項目「" + property + "」");
            }
        }

        return padStr.toString();
	}


	/**
	 * JavaScriptの大文字小文字変換関数作成<br/>
	 * isUpperCaseやisLowerCaseが指定された場合、<br/>
	 * 大文字小文字変換のJavaScript関数文字列を返却する。<br/>
	 * @param isUpperCase 大文字に変換有無
	 * @param isLowerCase 小文字に変換有無
	 * @return 大文字小文字変換のJavaScript関数文字列
	 */
	public static String toCase(String isUpperCase, String isLowerCase) {

		if (isUpperCase != null && "true".equalsIgnoreCase(isUpperCase)) {
			return "toUpperCase(this);";
		}

		if (isLowerCase != null && "true".equalsIgnoreCase(isLowerCase)) {
			return "toLowerCase(this);";
		}

		return "";
	}

    /**
     * onblur時処理JavaScript関数文字列作成<br/>
     * onblurイベント発生時の関数文字列を返却する。<br/>
     * @param inputType 入力文字タイプ
     * @return 関数文字列
     */
    public static String getOnblurEventStr(String inputType) {

    	StringBuffer sb = new StringBuffer("");

    	if (CommonConsts.TY_HAN.equals(inputType)) {
    	// 半角
			sb.append(HtmlConsts.FUN_CONVERT2HAN);
			sb.append("(");
			sb.append("this");
			sb.append(",'");
			sb.append(inputType);
			sb.append("');");
    	} else if (CommonConsts.TY_HAN_SUJI.equals(inputType)) {
    	// 半角数字
			sb.append(HtmlConsts.FUN_CONVERT2HAN);
			sb.append("(");
			sb.append("this");
			sb.append(",'");
			sb.append(inputType);
			sb.append("');");
    	} else if (CommonConsts.TY_HAN_EIJI.equals(inputType)) {
    	// 半角英字
			sb.append(HtmlConsts.FUN_CONVERT2HAN);
			sb.append("(");
			sb.append("this");
			sb.append(",'");
			sb.append(inputType);
			sb.append("');");

    	} else if (CommonConsts.TY_HAN_EISU.equals(inputType)) {
    	// 半角英数
			sb.append(HtmlConsts.FUN_CONVERT2HAN);
			sb.append("(");
			sb.append("this");
			sb.append(",'");
			sb.append(inputType);
			sb.append("');");
        } else if (CommonConsts.TY_HAN_KANA.equals(inputType)) {
    	// 半角ｶﾅ
			sb.append(HtmlConsts.FUN_CONVERT2HAN);
			sb.append("(");
			sb.append("this");
			sb.append(",'");
			sb.append(inputType);
			sb.append("');");
    	} else if (CommonConsts.TY_ZEN.equals(inputType)) {
    	// 全角
			sb.append(HtmlConsts.FUN_CONVERT2ZEN);
			sb.append("(");
			sb.append("this");
			sb.append(",'");
			sb.append(inputType);
			sb.append("');");
    	} else if (CommonConsts.TY_ZEN_SUJI.equals(inputType)) {
    	// 全角数字
			sb.append(HtmlConsts.FUN_CONVERT2ZEN);
			sb.append("(");
			sb.append("this");
			sb.append(",'");
			sb.append(inputType);
			sb.append("');");
    	} else if (CommonConsts.TY_ZEN_EIJI.equals(inputType)) {
    	// 全角英字
			sb.append(HtmlConsts.FUN_CONVERT2ZEN);
			sb.append("(");
			sb.append("this");
			sb.append(",'");
			sb.append(inputType);
			sb.append("');");
//    	} else if ("zen_eiki".equals(inputType)) {
//    	// 全角数字 + 記号
//			sb.append(HtmlConsts.FUN_CONVERT2ZEN);
//			sb.append("(");
//			sb.append("this");
//			sb.append(",'");
//			sb.append(inputType);
//			sb.append("');");
    	} else if (CommonConsts.TY_ZEN_EISU.equals(inputType)) {
    	// 全角英数
			sb.append(HtmlConsts.FUN_CONVERT2ZEN);
			sb.append("(");
			sb.append("this");
			sb.append(",'");
			sb.append(inputType);
			sb.append("');");
    	} else if (CommonConsts.TY_ZEN_KANA.equals(inputType)) {
    	// 全角カナ
            sb.append(HtmlConsts.FUN_CONVERT2ZEN_KANA);
            sb.append("(");
            sb.append("this");
            sb.append(",'");
            sb.append(inputType);
            sb.append("');");
    	} else if (CommonConsts.TY_ZEN_HIRA.equals(inputType)) {
    	// 全角ひらがな
			sb.append(HtmlConsts.FUN_CONVERT2ZEN_HIRA);
			sb.append("(");
			sb.append("this");
			sb.append(",'");
			sb.append(inputType);
			sb.append("');");
    	} else if (CommonConsts.TY_DECIMAL.equals(inputType)) {
    	// 金額
			sb.append(HtmlConsts.FUN_CONVERT2HAN);
			sb.append("(");
			sb.append("this");
			sb.append(",'");
			sb.append(inputType);
			sb.append("');");
	        sb.append(HtmlConsts.FUN_FMT_DECIMAL);
            sb.append("(this);");
    	}

    	return sb.toString();
    }

	/**
	 * onBlur時入力チェックのJavaScript文字列作成<br/>
     * onBlur時入力チェックを行うJavaScript文字列を返却する。<br/>
     * @param inputType 入力タイプ
     * @param chaExt 例外文字
     * @return JavaScript文字列
     */
    public static String getOnblurInputCheckEvent(String inputType, String chaExt, String decimalFormat) {


        StringBuffer sb = new StringBuffer("");
        if (!StrUtil.isEmpty(inputType)) {
	        sb.append(HtmlConsts.FUN_CHK_INPUT);
	        sb.append("(");
	        sb.append("this");
	        sb.append(",");
	        sb.append("'");
	        sb.append(inputType);
	        sb.append("'");

	        // 例外文字がある場合はセット
	        sb.append(",");
	        sb.append("'");
	        if (!StrUtil.isEmpty(chaExt)) {
	        	sb.append(chaExt.replace("\'", "\\\'").replace("\"", "\\\""));
	        }
	        sb.append("'");

	        // 数値フォーマットチェックがある場合はセット
	        if (!StrUtil.isEmpty(decimalFormat)) {
	            sb.append(",");
	            sb.append("'");
	            sb.append(decimalFormat);
	            sb.append("'");
	        }

	        sb.append(")");
        }

        return sb.toString();
    }


    /**
     * onfocus時のJavaScript関数文字列作成。<br/>
     * onfocusイベント発生時の関数文字列を返却する。<br/>
     * @param inputType 入力タイプ
     */
    public static String getOnFocusEventStr(String inputType) {

        StringBuffer sb = new StringBuffer("");

        if (CommonConsts.TY_HAN.equals(inputType)) {
        // 半角

        } else if (CommonConsts.TY_HAN_SUJI.equals(inputType)) {
        // 半角数字

        } else if (CommonConsts.TY_HAN_EIJI.equals(inputType)) {
        // 半角英字

        } else if (CommonConsts.TY_HAN_EISU.equals(inputType)) {
        // 半角英数

        } else if (CommonConsts.TY_HAN_KANA.equals(inputType)) {
        // 半角ｶﾅ

        } else if (CommonConsts.TY_ZEN.equals(inputType)) {
        // 全角

        } else if (CommonConsts.TY_ZEN_SUJI.equals(inputType)) {
        // 全角数字

        } else if (CommonConsts.TY_ZEN_EIJI.equals(inputType)) {
        // 全角英字

        } else if (CommonConsts.TY_ZEN_EISU.equals(inputType)) {
        // 全角英数

        } else if (CommonConsts.TY_ZEN_KANA.equals(inputType)) {
        // 全角カナ

        } else if (CommonConsts.TY_ZEN_HIRA.equals(inputType)) {
        // 全角ひらがな

        } else if (CommonConsts.TY_DECIMAL.equals(inputType)) {
        // 金額
            sb.append(HtmlConsts.FUN_UN_FMT_DECIMAL);
            sb.append("(this);");
        }

        return sb.toString();
    }



    /**
     * フォーマッティング<br/>
     * 文字タイプにより、表示のフォーマッティング処理を行う。
     * @param inputType 入力文字タイプ
     * @param value 変換対象文字列
     * @return 変換後の文字列
     */
    public static String getFormatValue(String inputType, String value) {

    	if (CommonConsts.TY_HAN.equals(inputType)) {
    	// 半角
    		return value;
    	} else if (CommonConsts.TY_HAN_SUJI.equals(inputType)) {
    	// 半角数字
    		return value;
    	} else if (CommonConsts.TY_HAN_EIJI.equals(inputType)) {
    	// 半角英字
    		return value;
    	} else if (CommonConsts.TY_HAN_EISU.equals(inputType)) {
    	// 半角英数
    		return value;
    	} else if (CommonConsts.TY_HAN_KANA.equals(inputType)) {
    	// 半角ｶﾅ
    		return value;
    	} else if (CommonConsts.TY_ZEN.equals(inputType)) {
    	// 全角
    		return value;
    	} else if (CommonConsts.TY_ZEN_SUJI.equals(inputType)) {
    	// 全角数字
    		return value;
    	} else if (CommonConsts.TY_ZEN_EIJI.equals(inputType)) {
    	// 全角英字
    		return value;
    	} else if (CommonConsts.TY_ZEN_EISU.equals(inputType)) {
    	// 全角英数
    		return value;
    	} else if (CommonConsts.TY_ZEN_KANA.equals(inputType)) {
    	// 全角カナ
    		return value;
    	} else if (CommonConsts.TY_ZEN_HIRA.equals(inputType)) {
    	// 全角ひらがな
    		return value;
    	} else if (CommonConsts.TY_DECIMAL.equals(inputType)) {
    	// 金額
    		return FormatUtil.formatComma(value);
    	}

    	return value;
    }


    /**
     * 基本スタイルクラス作成<br/>
     * 指定した入力タイプとReadOnlyによって基本スタイルクラスを設定する。<br/>
     * @param inputType 入力タイプ
     * @param isReadonly boolean
     * @return スタイルクラス
     */
    public static String getBaseStyle(String inputType, boolean isReadonly) {

        if (CommonConsts.TY_DECIMAL.equals(inputType)) {

            if (isReadonly) {
                return HtmlConsts.NM_CLASS_INPUT_READONLY_R;
            }else {
                return StrUtil.concat(HtmlConsts.NM_CLASS_INPUT, " ", HtmlConsts.ALIGN_RIGHT);
            }
        } else {

            if (isReadonly) {
                return HtmlConsts.NM_CLASS_INPUT_READONLY;
            }else {
                return HtmlConsts.NM_CLASS_INPUT;
            }
        }
    }


    /**
     * IME-MODE設定<br/>
     * 文字タイプによってIME-MODEのClassName（CSS）を返却する。<br/>
     * @param inputType 入力文字タイプ
     * @return ClassName（CSS）
     */
    public static String setInputControl(String inputType) {

    	if (CommonConsts.TY_HAN.equals(inputType)) {
    	// 半角
    		return HtmlConsts.NM_CLASS_IME_DIS;
    	} else if (CommonConsts.TY_HAN_SUJI.equals(inputType)) {
    	// 半角数字
    		return HtmlConsts.NM_CLASS_IME_DIS;
    	} else if (CommonConsts.TY_HAN_EIJI.equals(inputType)) {
    	// 半角英字
    		return HtmlConsts.NM_CLASS_IME_DIS;
    	} else if ("han_eiki".equals(inputType)) {
    	// 半角数字 + 記号
    		return HtmlConsts.NM_CLASS_IME_DIS;
    	} else if (CommonConsts.TY_HAN_EISU.equals(inputType)) {
    	// 半角英数
    		return HtmlConsts.NM_CLASS_IME_DIS;
    	} else if (CommonConsts.TY_HAN_KANA.equals(inputType)) {
    	// 半角ｶﾅ
    		return HtmlConsts.NM_CLASS_IME_ACT;
    	} else if (CommonConsts.TY_ZEN.equals(inputType)) {
    	// 全角
    		return HtmlConsts.NM_CLASS_IME_ACT;
    	} else if (CommonConsts.TY_ZEN_SUJI.equals(inputType)) {
    	// 全角数字
    		return HtmlConsts.NM_CLASS_IME_ACT;
    	} else if (CommonConsts.TY_ZEN_EIJI.equals(inputType)) {
    	// 全角英字
    		return HtmlConsts.NM_CLASS_IME_ACT;
    	} else if (CommonConsts.TY_ZEN_EISU.equals(inputType)) {
    	// 全角英数
    		return HtmlConsts.NM_CLASS_IME_ACT;
    	} else if (CommonConsts.TY_ZEN_KANA.equals(inputType)) {
    	// 全角カナ
    		return HtmlConsts.NM_CLASS_IME_ACT;
    	} else if (CommonConsts.TY_ZEN_HIRA.equals(inputType)) {
    	// 全角ひらがな
    		return HtmlConsts.NM_CLASS_IME_ACT;
    	} else if (CommonConsts.TY_DECIMAL.equals(inputType)) {
    	// 金額
    		return HtmlConsts.NM_CLASS_IME_DIS;
    	}

    	return "";
    }



    /**
     * 日付変換処理のJavScript文字列作成<br/>
     * onblurのときに入力したDATEをFormatする。<br/>
     * @param format 日付タイプ
     * @return JavaScript文字列
     */
    public static String buildDateOnblurFun(String format) {

        StringBuffer sb = new StringBuffer("");

        sb.append(HtmlConsts.FUN_CONV_2_DT_FMT);
        sb.append("(");
        sb.append("this");
        sb.append(",");

        sb.append("'");
        if (StrUtil.isEmpty(format)) {
            sb.append(CommonConsts.FMT_JYY_MM_DD);
        } else {
            sb.append(format);
        }

        sb.append("')");

        return sb.toString();
    }


    /**
     * 日付変換処理のJavScript文字列作成<br/>
     * onfocusのときに入力したDATEをunFormatする。<br/>
     * @param property 項目名
     * @param format 表示の日付形式
     * @param inputFormat 入力の日付形式
     * @param isUseButton ボタン使用の有無
     * @return JavaScript文字列
     */
    public static String buildDateOnFocusFun(String property, String format, String inputFormat, boolean isUseButton) {

        StringBuffer sb = new StringBuffer("");

        String fmt = format;

        if (isUseButton
                &&
                (CommonConsts.FMT_NYY.equals(fmt)
                || CommonConsts.FMT_JYY.equals(fmt)
                || CommonConsts.FMT_JCCYY.equals(fmt)

                || CommonConsts.FMT_YYYY.equals(fmt)

                || CommonConsts.FMT_JCCYY_MM.equals(fmt)
                || CommonConsts.FMT_NYYMM.equals(fmt)
                || CommonConsts.FMT_JYY_MM.equals(fmt)

                || CommonConsts.FMT_YYYYMM.equals(fmt)
                || CommonConsts.FMT_YYYY_MM.equals(fmt))

                ) {
        // 入力フォーマットが年月日ではない場合は、useButtonを設定してはいけない。
            throw new SystemException("項目名："+ property + " >> dateTextのformatが「年月日」ではない場合は属性「useButton」に「true」は設定出来ません。");
        }

        if (!StrUtil.isEmpty(inputFormat)) {
        // 入力形式を設定した場合
            sb.append(HtmlConsts.FUN_CONV_2_DT_UNFMT);
            sb.append("(");
            sb.append("this");
            sb.append(",");
            sb.append("'");
            sb.append(inputFormat);
            sb.append("');");
        } else {
            if (CommonConsts.FMT_JYY_MM_DD.equals(fmt)
                    || CommonConsts.FMT_NYYMMDD.equals(fmt)
                    || CommonConsts.FMT_JCCYY_MM_DD.equals(fmt)) {
                sb.append(HtmlConsts.FUN_CONV_2_DT_UNFMT);
                sb.append("(");
                sb.append("this");
                sb.append(",");
                sb.append("'");
                sb.append(CommonConsts.FMT_NYYMMDD); // 和暦）4210309
                sb.append("');");

            } else if (CommonConsts.FMT_NYY.equals(fmt)
                    || CommonConsts.FMT_JYY.equals(fmt)
                    || CommonConsts.FMT_JCCYY.equals(fmt)) {
                sb.append(HtmlConsts.FUN_CONV_2_DT_UNFMT);
                sb.append("(");
                sb.append("this");
                sb.append(",");
                sb.append("'");
                sb.append(CommonConsts.FMT_NYY); // 和暦）421
                sb.append("');");

            } else if (CommonConsts.FMT_JCCYY_MM.equals(fmt)
                    || CommonConsts.FMT_NYYMM.equals(fmt)
                    || CommonConsts.FMT_JYY_MM.equals(fmt)) {
                sb.append(HtmlConsts.FUN_CONV_2_DT_UNFMT);
                sb.append("(");
                sb.append("this");
                sb.append(",");
                sb.append("'");
                sb.append(CommonConsts.FMT_NYYMM); // 和暦）42103
                sb.append("');");

            } else if (CommonConsts.FMT_YYYYMMDD.equals(fmt)
                    || CommonConsts.FMT_YYYY_MM_DD.equals(fmt)) {
                sb.append(HtmlConsts.FUN_CONV_2_DT_UNFMT);
                sb.append("(");
                sb.append("this");
                sb.append(",");
                sb.append("'");
                sb.append(CommonConsts.FMT_YYYYMMDD); // 西暦）20090309
                sb.append("');");

            } else if (CommonConsts.FMT_YYYY.equals(fmt)) {
                sb.append(HtmlConsts.FUN_CONV_2_DT_UNFMT);
                sb.append("(");
                sb.append("this");
                sb.append(",");
                sb.append("'");
                sb.append(CommonConsts.FMT_YYYY); // 西暦）2009
                sb.append("');");

            } else if (CommonConsts.FMT_YYYYMM.equals(fmt)
                    || CommonConsts.FMT_YYYY_MM.equals(fmt)) {
                sb.append(HtmlConsts.FUN_CONV_2_DT_UNFMT);
                sb.append("(");
                sb.append("this");
                sb.append(",");
                sb.append("'");
                sb.append(CommonConsts.FMT_YYYYMM); // 西暦）200903
                sb.append("');");

            } else if (CommonConsts.FMT_MM.equals(fmt) || CommonConsts.FMT_DD.equals(fmt) ) {

            }
        }
        return sb.toString();
    }

}
