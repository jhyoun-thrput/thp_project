package tech3g.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.FormTag;
import org.apache.struts.taglib.html.TextTag;

import tech3g.common.util.StrUtil;

/**
 * <pre>
 * LiveanyTextTagクラス。
 * StrutsTextタグを継承し再定義する。
 * </pre>
 */
public class LiveanyTextTag extends TextTag {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
	/** 入力タイプ */
	protected String inputType;

	/** チェック例外文字 */
	protected String chaExt;

	/** Enterキーを押した時のJavaScriptイベント */
	protected String onEnterPress;

	/** lpad */
	protected String lpad;

	/** rpad */
	protected String rpad;

	/** 大文字に変換フラグ（onblur時） */
	protected String isUpperCase;

	/** 小文字に変換フラグ（onblur時） */
	protected String isLowerCase;

	/** 数値（小数点Padding）Padding */
	protected String dpad;

	/** 数値の入力形式 */
	protected String decimalFormat;

	/** 共通業務コード */
	protected String mcode;

    /** mcode : DECODEテーブルの表示する項目　
     * DCODE_NM1　　　　：1を指定する。
     * DCODE_NM2　　　　：2を指定する。
     * DCODE_NM3　　　　：3を指定する。
     * DCODE_ABBR_NM　　：4を指定する。
     * */
    protected String mode;

	/** 整数のみを許すかどうか */
	protected boolean positive = true;

	/** 個人のステータス */
	protected String indvStatus;


    //--------------------------------------------------- コンストラクタ
	/**
	 * コンストラクタ<br/>
	 */
	public LiveanyTextTag() {
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

    /**
     * Enterキーイベントを取得する。<br/>
     * @return
     */
    public String getOnEnterPress() {
		return onEnterPress;
	}

	/**
	 * Enterキーイベントを設定する。<br/>
	 * @param onEnterDown
	 */
	public void setOnEnterPress(String onEnterPress) {
		this.onEnterPress = onEnterPress;
	}

	/**
     * lpadを取得する。<br/>
     * @return lpad
     */
    public String getLpad() {
        return lpad;
    }

    /**
     * lpadを設定する。<br/>
     * @param lpad the lpad to set
     */
    public void setLpad(String lpad) {
        this.lpad = lpad;
    }

    /**
     * rpadを取得する。<br/>
     * @return rpad
     */
    public String getRpad() {
        return rpad;
    }

    /**
     * rpadを設定する。<br/>
     * @param rpad the rpad to set
     */
    public void setRpad(String rpad) {
        this.rpad = rpad;
    }

	/**
	 * isUpperCaseを取得する。<br/>
	 * @return isUpperCase
	 */
	public String getIsUpperCase() {
		return isUpperCase;
	}

	/**
	 * isUpperCaseを設定する。<br/>
	 * @param isUpperCase isUpperCase
	 */
	public void setIsUpperCase(String isUpperCase) {
		this.isUpperCase = isUpperCase;
	}

	/**
	 * isLowerCaseを取得する。<br/>
	 * @return isLowerCase
	 */
	public String getIsLowerCase() {
		return isLowerCase;
	}

	/**
	 * isLowerCaseを設定する。<br/>
	 * @param isLowerCase isLowerCase
	 */
	public void setIsLowerCase(String isLowerCase) {
		this.isLowerCase = isLowerCase;
	}

	/**
	 * dpadを取得する。<br/>
	 * @return dpad
	 */
	public String getDpad() {
		return dpad;
	}

	/**
	 * dpadを設定する。<br/>
	 * @param dpad dpad
	 */
	public void setDpad(String dpad) {
		this.dpad = dpad;
	}


	/**
	 * decimalFormatを取得する。<br/>
	 * @return decimalFormat
	 */
	public String getDecimalFormat() {
		return decimalFormat;
	}

	/**
	 * decimalFormatを設定する。<br/>
	 * @param decimalFormat decimalFormat
	 */
	public void setDecimalFormat(String decimalFormat) {
		this.decimalFormat = decimalFormat;
	}


	/**
	 * 共通業務コードを取得する。<br/>
	 * @return 共通業務コード
	 */
	public String getMcode() {
		return mcode;
	}

	/**
	 * 共通業務コードを設定する。<br/>
	 * @param mcode 共通業務コード
	 */
	public void setMcode(String mcode) {
		this.mcode = mcode;
	}



	/**
	 * positiveを取得する。<br/>
	 * @return positive
	 */
	public boolean getPositive() {
		return positive;
	}

	/**
	 * positiveを設定する。<br/>
	 * @param positive positive
	 */
	public void setPositive(boolean positive) {
		this.positive = positive;
	}


	/**
	 * modeを取得する。<br/>
	 * @return mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * modeを設定する。<br/>
	 * @param mode mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * indvStatusを取得する。<br/>
	 * @return indvStatus
	 */
	public String getIndvStatus() {
		return indvStatus;
	}

	/**
	 * indvStatusを設定する。<br/>
	 * @param indvStatus indvStatus
	 */
	public void setIndvStatus(String indvStatus) {
		this.indvStatus = indvStatus;
	}

    //--------------------------------------------------- インスタンスメソッド



	/* (non-Javadoc)
	 * @see org.apache.struts.taglib.html.BaseFieldTag#renderInputElement()
	 */
	@Override
	protected String renderInputElement()
        throws JspException {
        StringBuffer results = new StringBuffer("<input");

        prepareAttribute(results, "type", this.type);
        prepareAttribute(results, "name", prepareName());
        prepareAttribute(results, "accesskey", getAccesskey());
        prepareAttribute(results, "accept", getAccept());
        prepareAttribute(results, "maxlength", getMaxlength());
        prepareAttribute(results, "size", getCols());

        if (((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
            // readonly or disabled
        	prepareAttribute(results, "tabindex", "-1");
    	} else {
    		prepareAttribute(results, "tabindex", getTabindex());
    	}

        prepareValue(results);


        results.append(this.prepareEventHandlers());
        results.append(this.prepareStyles());
//        if (!isXhtml()) {
//            prepareAttribute(results, "autocomplete", getAutocomplete());
//        }
        prepareOtherAttributes(results);
        results.append(this.getElementClose());

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
    						LiveanyTagUtil.getKeyDownEvent(getInputType(), getChaExt()),
    						getOnkeypress(), ";",
    						getPressEnterKey())
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

        // ---- OnBlurイベント定義
    	if (((!getReadonly()) && (!getDisabled()))) {
	        prepareAttribute(handlers, "onblur",
	        								StrUtil.concat("javascript:onBlur(this);", LiveanyTagUtil.getPaddingStr(getProperty(), getLpad(), getRpad(), getDpad(), getMaxlength()),
	        										LiveanyTagUtil.toCase(getIsUpperCase(), getIsLowerCase()),
	        										preTextOnblur(),
	        										LiveanyTagUtil.getOnblurEventStr(getInputType()),
	        										LiveanyTagUtil.getOnblurInputCheckEvent(getInputType(), getChaExt(), (!getPositive() ? "-" : "") + getDecimalFormat()), ";",
	        								        getOnblur()));
    	} else {
    		prepareAttribute(handlers, "onblur",  getOnblur());
    	}

    	// ---- OnFocusイベント定義
    	if (((!getReadonly()) && (!getDisabled()))) {
    		prepareAttribute(handlers, "onfocus",  StrUtil.concat(LiveanyTagUtil.getOnFocusEventStr(getInputType()), getOnfocus(), ";onFocus(this);"));
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
     * Render the value element
     *
     * @param results The StringBuffer that output will be appended to.
     */
    protected void prepareValue(StringBuffer results)
        throws JspException {
        results.append(" value=\"");

        if (value != null) {

        	if (!StrUtil.isEmpty(mcode)) {
        	// 名称変換

        		String newValue = null;
        		if ("0".equals(getMode())) {
        			newValue = value;
        		} else if ("1".equals(getMode())) {
        			newValue = value;
        		} else if ("2".equals(getMode())) {
        			newValue = value;
        		} else if ("3".equals(getMode())) {
        			newValue = value;
        		} else if ("4".equals(getMode())) {
        			newValue = value;
        		} else {
        			newValue = value;
        		}
        		if (!StrUtil.isEmpty(newValue)) {
        			value = newValue;
        		}
        	}

            results.append(this.formatValue(LiveanyTagUtil.getFormatValue(getInputType(),value)));

        } else if (redisplay || !"password".equals(type)) {
            Object value =
                TagUtils.getInstance().lookup(pageContext, name, property, null);

            if (value != null) {

            	if (!StrUtil.isEmpty(mcode)) {
				// 名称変換

	        		String newValue = null;
	        		if ("0".equals(getMode())) {
	        			newValue = (String)value;
	        		} else if ("1".equals(getMode())) {
	        			newValue = (String)value;
	        		} else if ("2".equals(getMode())) {
	        			newValue = (String)value;
	        		} else if ("3".equals(getMode())) {
	        			newValue = (String)value;
	        		} else if ("4".equals(getMode())) {
	        			newValue = (String)value;
	        		} else {
	        			newValue = (String)value;
	        		}
					if (!StrUtil.isEmpty(newValue)) {
						value = newValue;
					}
				}

            	results.append(this.formatValue(LiveanyTagUtil.getFormatValue(getInputType(), value.toString())));
            } else {
            	results.append(this.formatValue(value));
            }
        }

        results.append('"');
    }


    /**
     * onpressEnterkeyイベントをonkeypressイベントに追加する。<br/>
     * @return Enterキーを押下した時のJavascript文字列
     */
    protected String getPressEnterKey() {

        if (StrUtil.isEmpty(getOnEnterPress()) ) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();

        	if (((!getReadonly()) && (!getDisabled())) && getInputType() != null) {
        	// readonlyではない場合。


	            sb.append("if (window.event.keyCode != 13){return;}");

				sb.append(StrUtil.concat(LiveanyTagUtil.getPaddingStr(getProperty(), getLpad(), getRpad(), getDpad(), getMaxlength()),
											LiveanyTagUtil.toCase(getIsUpperCase(), getIsLowerCase()),
											LiveanyTagUtil.getOnblurEventStr(getInputType()),
											"if(!", LiveanyTagUtil.getOnblurInputCheckEvent(getInputType(), getChaExt(), (!getPositive() ? "-" : "") + getDecimalFormat()), ") {return;}"));

	            sb.append("isPressEnterKey");
	            sb.append("(");
	            sb.append("this");
	            sb.append(",");
	            sb.append("\'");
	            sb.append(getOnEnterPress().replace("\'", "\\\'").replace("\"", "\\\""));
	            sb.append("\');");
			}
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
            	prepareAttribute(styles, "class", StrUtil.concat(LiveanyTagUtil.getBaseStyle(getInputType(), getReadonly()),
            													" ", getStyleClass(),
            													" ", getIndvStatus()));
        	} else {
        		prepareAttribute(styles, "class",
					        				StrUtil.concat(LiveanyTagUtil.getBaseStyle(getInputType(), getReadonly()),
															" " , LiveanyTagUtil.setInputControl(getInputType()),
															" ", getStyleClass(),
															" ", getIndvStatus()));
        	}
        }
        prepareAttribute(styles, "title", message(getTitle(), getTitleKey()));
        prepareAttribute(styles, "alt", message(getAlt(), getAltKey()));
        prepareInternationalization(styles);

        return styles.toString();
    }

	/**
	 * onblurの前に実行するJavaScriptがあれば定義する。<br/>
	 * @return onblurの前に実行するJavaScript
	 */
	protected String preTextOnblur() {
		return "";
	}



}
