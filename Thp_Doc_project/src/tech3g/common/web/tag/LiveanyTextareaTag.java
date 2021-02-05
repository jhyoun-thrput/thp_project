package tech3g.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.FormTag;
import org.apache.struts.taglib.html.TextareaTag;

import tech3g.common.constants.CommonConsts;
import tech3g.common.constants.HtmlConsts;
import tech3g.common.util.StrUtil;

/**
 * <pre>
 * LiveanyTextTagクラス。
 * StrutsTextタグを再定義する。
 * </pre>
 */
public class LiveanyTextareaTag extends TextareaTag {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
	/** 入力タイプ */
	protected String inputType;

	/** チェック例外文字 */
	protected String chaExt;



    //--------------------------------------------------- コンストラクタ
	/**
	 * コンストラクタ<br/>
	 */
	public LiveanyTextareaTag() {
		super();
	}

    // -------------------------------------------------- SetGet Methods
	/**
	 * 入力タイプを取得する。<br/>
	 * @return 入力タイプ
	 */
	public String getInputType() {
		return inputType;
	}



	/**
	 * 入力タイプを設定する。<br/>
	 * @param inputType
	 */
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

    /**
     * chaExtを取得する。<br/>
     * @return chaExt
     */
    public String getChaExt() {
        return chaExt;
    }

    /**
     * chaExtを設定する。<br/>
     * @param chaExt the chaExt to set
     */
    public void setChaExt(String chaExt) {
        this.chaExt = chaExt;
    }


    //--------------------------------------------------- インスタンスメソッド

    /* (non-Javadoc)
     * @see org.apache.struts.taglib.html.TextareaTag#renderTextareaElement()
     */
    protected String renderTextareaElement()
    throws JspException {
        StringBuffer results = new StringBuffer("<textarea");

        prepareAttribute(results, "name", prepareName());
        prepareAttribute(results, "accesskey", getAccesskey());
        prepareAttribute(results, "cols", getCols());
        prepareAttribute(results, "rows", getRows());

        if (((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
            // readonly or disabled
        	prepareAttribute(results, "tabindex", "-1");
    	} else {
    		prepareAttribute(results, "tabindex", getTabindex());
    	}

        results.append(this.prepareEventHandlers());
        results.append(this.prepareStyles());

        prepareOtherAttributes(results);
        results.append(">");
        results.append(this.renderData());
        results.append("</textarea>");

        return results.toString();
    }

	/* (non-Javadoc)
	 * @see org.apache.struts.taglib.html.BaseHandlerTag#prepareMouseEvents(java.lang.StringBuffer)
	 */
	@Override
    protected void prepareMouseEvents(StringBuffer handlers) {
        prepareAttribute(handlers, "onclick", getOnclick());
        prepareAttribute(handlers, "ondblclick", getOndblclick());
        prepareAttribute(handlers, "onmouseover", getOnmouseover());
        prepareAttribute(handlers, "onmouseout", getOnmouseout());
        prepareAttribute(handlers, "onmousemove", getOnmousemove());
        prepareAttribute(handlers, "onmousedown", getOnmousedown());
        prepareAttribute(handlers, "onmouseup", getOnmouseup());
    }

	/* (non-Javadoc)
	 * @see org.apache.struts.taglib.html.BaseHandlerTag#prepareKeyEvents(java.lang.StringBuffer)
	 */
	@Override
    protected void prepareKeyEvents(StringBuffer handlers) {

    	prepareAttribute(handlers, "onkeydown", getOnkeydown());
        prepareAttribute(handlers, "onkeyup", getOnkeyup());

        // ---------- keydownイベント
        if (((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
            // readonly or disabled
        	prepareAttribute(handlers, "onkeypress", getOnkeypress());
    	} else {
    		prepareAttribute(handlers, "onkeypress",
    				StrUtil.concat(
    						getKeyDownEvent(),
    						getOnkeypress(), ";")
    		);
    	}
    }

	/* (non-Javadoc)
	 * @see org.apache.struts.taglib.html.BaseHandlerTag#prepareTextEvents(java.lang.StringBuffer)
	 */
	@Override
    protected void prepareTextEvents(StringBuffer handlers) {
        prepareAttribute(handlers, "onselect", getOnselect());
        prepareAttribute(handlers, "onchange", getOnchange());
    }

	/* (non-Javadoc)
	 * @see org.apache.struts.taglib.html.BaseHandlerTag#prepareFocusEvents(java.lang.StringBuffer)
	 */
	@Override
    protected void prepareFocusEvents(StringBuffer handlers) {

    	// ---------------  Lpad Rpadの設定


        // ---- OnBlurイベント定義
		if (((!getReadonly()) && (!getDisabled()))) {
	        prepareAttribute(handlers, "onblur",
	        								StrUtil.concat("javascript:onBlur(this);",
	        										preTextOnblur(),
	        								        getOnblurEventStr(getInputType()),
	        								        getOnblurInputCheckEvent(getInputType(), getChaExt()), ";",
	        								        getOnblur()));
    	} else {
    		prepareAttribute(handlers, "onblur",  getOnblur());
    	}

    	// ---- OnFocusイベント定義
    	if (((!getReadonly()) && (!getDisabled()))) {
    		prepareAttribute(handlers, "onfocus",  StrUtil.concat(getOnFocusEventStr(getInputType()), getOnfocus(), ";onFocus(this);"));
    	} else {
    		prepareAttribute(handlers, "onfocus",  getOnfocus());
    	}

        // Get the parent FormTag (if necessary)
        FormTag formTag = null;

        if ((doDisabled && !getDisabled()) || (doReadonly && !getReadonly())) {
            formTag =
                (FormTag) pageContext.getAttribute(Constants.FORM_KEY,
                    PageContext.REQUEST_SCOPE);
        }

        // Format Disabled
        if (doDisabled) {
            boolean formDisabled =
                (formTag == null) ? false : formTag.isDisabled();

            if (formDisabled || getDisabled()) {
                handlers.append(" disabled=\"disabled\"");
            }
        }

        // Format Read Only
        if (doReadonly) {
            boolean formReadOnly =
                (formTag == null) ? false : formTag.isReadonly();

            if (formReadOnly || getReadonly()) {
                handlers.append(" readonly=\"readonly\"");
            }
        }
    }


	/**
     * onBlur時入力チェックを行うJavaScript文字列を返却する。<br/>
     * @param inputType 入力タイプ
     * @param chaExt 例外文字
     * @return JavaScript文字列
     */
    protected String getOnblurInputCheckEvent(String inputType, String chaExt) {

        StringBuffer sb = new StringBuffer("");
        sb.append(HtmlConsts.FUN_CHK_INPUT);
        sb.append("(");
        sb.append("this");
        sb.append(",");
        sb.append("'");
        sb.append(inputType);
        sb.append("'");

        if (!StrUtil.isEmpty(chaExt)) {
            sb.append(",");
            sb.append("'");
            sb.append(chaExt.replace("\'", "\\\'").replace("\"", "\\\""));
            sb.append("'");
        }

        sb.append(")");
        //sb.append(";");

        return sb.toString();
    }

    /**
     * 入力タイプによって、キーダウンEventに入力制御のJavaScript関数文字列を返却する。 <br/>
     * @return 入力制御のJavaScript関数文字列
     */
    protected String getKeyDownEvent() {

        if (StrUtil.isEmpty(getInputType())) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer("");
            sb.append(HtmlConsts.FUN_CTL_INPUT);
            sb.append("(");
            sb.append("this");
            sb.append(",'");
            sb.append(getInputType());
            sb.append("'");
            if (!StrUtil.isEmpty(getChaExt())) {
                sb.append(",");
                sb.append("'");
                sb.append(getChaExt().replace("\'", "\\\'").replace("\"", "\\\""));
                sb.append("'");
            }
            sb.append(");");
            return sb.toString();
        }
    }


    /* (non-Javadoc)
     * @see org.apache.struts.taglib.html.BaseHandlerTag#prepareStyles()
     */
    @Override
	protected String prepareStyles()
        throws JspException {
        StringBuffer styles = new StringBuffer();

        boolean errorsExist = doErrorsExist();
        if (errorsExist && (getErrorStyleId() != null)) {
            prepareAttribute(styles, "id", getErrorStyleId());
        } else {
            prepareAttribute(styles, "id", getStyleId());
        }
        if (errorsExist && (getErrorStyle() != null)) {
            prepareAttribute(styles, "style", getErrorStyle());
        } else {
            prepareAttribute(styles, "style", getStyle());
        }
        if (errorsExist && (getErrorStyleClass() != null)) {
            prepareAttribute(styles, "class", getErrorStyleClass());
        } else {
            if (((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
                // readonly or disabled
            	prepareAttribute(styles, "class", StrUtil.concat(getBaseStyle(getInputType(), getReadonly()), " ", getStyleClass()));
        	} else {
        		prepareAttribute(styles, "class",
        				StrUtil.concat(getBaseStyle(getInputType(), getReadonly()), " " , setInputControl(getInputType()), " ", getStyleClass()));
        	}
        }
        prepareAttribute(styles, "title", message(getTitle(), getTitleKey()));
        prepareAttribute(styles, "alt", message(getAlt(), getAltKey()));
        prepareInternationalization(styles);

        return styles.toString();
    }

    /**
     * 文字タイプによってIME-MODEのClassName（CSS）を返却する。<br/>
     * @param inputType 入力文字タイプ
     * @return ClassName（CSS）
     */
    protected static String setInputControl(String inputType) {

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
//    	else if (CommonConsts.TY_ZIP.equals(inputType)) {
//    	// 郵便番号
//    		return HtmlConsts.NM_CLASS_IME_DIS;
//    	} else if (CommonConsts.TY_MAIL.equals(inputType)) {
//    	// メールアドレス
//    		return HtmlConsts.NM_CLASS_IME_DIS;
//    	}

    	return "";
    }

    /**
     * onblurイベント発生時の関数文字列を返却する。<br/>
     * @param className
     * @return 関数文字列
     */
    protected static String getOnblurEventStr(String inputType) {

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
     * onfocusイベント発生時の関数文字列を返却する。<br/>
     * @param inputType 入力タイプ
     */
    protected static String getOnFocusEventStr(String inputType) {

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
     * 指定した入力タイプとReadOnlyによって基本スタイルクラスを設定する。<br/>
     * @param inputType 入力タイプ
     * @param isReadonly boolean
     * @return スタイルクラス
     */
    protected static String getBaseStyle(String inputType, boolean isReadonly) {

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
	 * onblurの前に実行するJavaScriptがあれば定義する。<br/>
	 * @return onblurの前に実行するJavaScript
	 */
	protected String preTextOnblur() {
		return "";
	}


}
