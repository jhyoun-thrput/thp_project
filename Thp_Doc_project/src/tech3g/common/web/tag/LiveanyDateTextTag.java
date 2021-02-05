package tech3g.common.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.FormTag;
import org.apache.struts.taglib.html.TextTag;

import tech3g.common.constants.CommonConsts;
import tech3g.common.constants.HtmlConsts;
import tech3g.common.util.DateUtil;
import tech3g.common.util.StrUtil;

/**
 * <pre>
 * LiveanyDateTextTagクラス。
 * Strutsのhtmlタグを拡張したカスタムタグ。
 * ほとんどの機能はStrutsのhtmlタグから継承している。
 *
 * </pre>
 *
 */
public class LiveanyDateTextTag extends TextTag {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
	/** フォームの名前 */
	private String formName;

	/** 日付の表示フォーマット */
	private String format;

	/** 日付の入力フォーマット */
	private String inputFormat;

	/** カレンダーボタンを使用するかどうか */
	private boolean useButton;

	/** Enterキーを押した時のJavaScriptイベント */
	private String onEnterPress;

	/** 一覧の入力項目の場合インデックス */
	private String listIndex;

	/** 日付変換 */
	private boolean addUpDownFunc = true ;

    //--------------------------------------------------- コンストラクタ
	/**
	 * <br/>
	 * @version 修正履歴
	 */
	public LiveanyDateTextTag() {
		super();
	}

    // -------------------------------------------------- SetGet Methods
    /**
	 * formNameを取得する。<br/>
	 * @return formName
	 */
	public String getFormName() {
		return formName;
	}

	/**
	 * formNameを設定する。<br/>
	 * @param formName formName
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}

	/**
	 * formatを取得する。<br/>
	 * @return format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * formatを設定する。<br/>
	 * @param format format
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * inputFormatを取得する。<br/>
	 * @return inputFormat
	 */
	public String getInputFormat() {
		return inputFormat;
	}

	/**
	 * inputFormatを設定する。<br/>
	 * @param inputFormat inputFormat
	 */
	public void setInputFormat(String inputFormat) {
		this.inputFormat = inputFormat;
	}

	/**
	 * useButtonを取得する。<br/>
	 * @return useButton
	 */
	public boolean getUseButton() {
		return useButton;
	}

	/**
	 * useButtonを設定する。<br/>
	 * @param useButton useButton
	 */
	public void setUseButton(boolean useButton) {
		this.useButton = useButton;
	}

	/**
	 * onEnterPressを取得する。<br/>
	 * @return onEnterPress
	 */
	public String getOnEnterPress() {
		return onEnterPress;
	}

	/**
	 * onEnterPressを設定する。<br/>
	 * @param onEnterPress onEnterPress
	 */
	public void setOnEnterPress(String onEnterPress) {
		this.onEnterPress = onEnterPress;
	}

	/**
	 * listIndexを取得する。<br/>
	 * @return listIndex
	 */
	public String getListIndex() {
		return listIndex;
	}

	/**
	 * listIndexを設定する。<br/>
	 * @param listIndex listIndex
	 */
	public void setListIndex(String listIndex) {
		this.listIndex = listIndex;
	}

    /**
	 * addUpDownFuncを取得する。<br/>
	 * @return addUpDownFunc
	 */
	public boolean getAddUpDownFunc() {
		return addUpDownFunc;
	}

	/**
	 * addUpDownFuncを設定する。<br/>
	 * @param addUpDownFunc addUpDownFunc
	 */
	public void setAddUpDownFunc(boolean addUpDownFunc) {
		this.addUpDownFunc = addUpDownFunc;
	}

	//--------------------------------------------------- インスタンスメソッド
	/**
     * Generate the required input tag. <p> Support for indexed property since
     * Struts 1.1
     *
     * @throws JspException if a JSP exception has occurred
     */
    @Override
	public int doStartTag() throws JspException {
        TagUtils.getInstance().write(this.pageContext, this.renderInputElement());

        return (EVAL_BODY_TAG);
    }


	/* (non-Javadoc)
	 * @see org.apache.struts.taglib.html.BaseFieldTag#renderInputElement()
	 */
	@Override
	protected String renderInputElement() throws JspException {

    	StringBuffer results = new StringBuffer("");

    	if (getUseButton() && !((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
            results.append("\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
            results.append("\t<tr>\n");
            results.append("\t\t<td>");
        }



        results.append("<input");
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
        if (!isXhtml()) {
            // prepareAttribute(results, "autocomplete", getAutocomplete());
        }
        prepareOtherAttributes(results);
        results.append(this.getElementClose());



        if (getUseButton() && !((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
        	results.append("\t\t</td>\n");

    		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
    		HttpServletRequest request =  (HttpServletRequest) pageContext.getRequest();

	        results.append("\t\t<td width=\"1px\"></td>\n");
	        results.append("\t\t<td>");
	       	results.append("<a id=\"b_dt_tx_");
	       	results.append(getProperty());
	       	results.append(StrUtil.clearNull(getListIndex()));
	       	results.append("\" href=\"javascript:");
	       	results.append(HtmlConsts.FUN_OPN_CAL);
	       	results.append("(");
	       	results.append(getFormName());
	       	results.append(".");
	       	results.append(getProperty());
       		results.append(", true");
	       	if (getListIndex() != null) {
	       		results.append(", ");
	       		results.append(getListIndex());
	       	}
	       	results.append(");\" ");

	       	if (getTabindex() != null) {
		       	results.append("tabindex='"+ getTabindex() +"'");
	       	}
	       	results.append("><img align=\"middle\" src=\"");
	       	results.append(request.getContextPath());
	       	results.append(response.encodeURL("/images/common/icon_calendar.gif"));
	       	results.append("\"/></a>");
	        results.append("\t\t</td>\n");
	        results.append("\t</tr>\n");
	        results.append("</table>\n");
        }
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

        // ---------- keyPressイベント
        if (((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
        // readonly or disabled
            prepareAttribute(handlers, "onkeydown", getOnkeydown());
        } else {
        	if (getAddUpDownFunc()) {
        		prepareAttribute(handlers, "onkeydown", getOnkeydown() + ";plusMinusDateCtl(this);");
        	} else {
        		 prepareAttribute(handlers, "onkeydown", getOnkeydown());
        	}
        }

        prepareAttribute(handlers, "onkeyup", getOnkeyup());

        // ---------- keyPressイベント
        if (((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
        // readonly or disabled
            prepareAttribute(handlers, "onkeypress", getOnkeypress());
        } else {
            prepareAttribute(handlers, "onkeypress",
                    StrUtil.concat(
                            //HtmlConsts.FUN_CTL_INPUT, "(this, '", CommonConsts.TY_HAN , "');",
                            getOnkeypress(), ";",
                            //"plusMinusDateCtl(this);",
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


        if (((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
            // readonly or disabled
	        prepareAttribute(handlers, "onblur", getOnblur());
    	} else {
    		prepareAttribute(handlers, "onblur",
    				StrUtil.concat("javascript:onBlur(this);", " " ,getOnblur(), ";", LiveanyTagUtil.buildDateOnblurFun(getFormat()), ";"));
    	}

        if (((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
            // readonly or disabled
    		prepareAttribute(handlers, "onfocus",  getOnfocus());
    	} else {
    		prepareAttribute(handlers, "onfocus",
    				StrUtil.concat("onFocus(this);",
    								getOnfocus(), ";",
    								LiveanyTagUtil.buildDateOnFocusFun(getProperty(),
    																	getFormat(),
    																	getInputFormat(),
    																	getUseButton())));
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


	/* (non-Javadoc)
	 * @see org.apache.struts.taglib.html.BaseHandlerTag#prepareStyles()
	 */
	@Override
	protected String prepareStyles() throws JspException {

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
	            prepareAttribute(styles, "class", StrUtil.concat(HtmlConsts.NM_CLASS_INPUT_READONLY, " ", getStyleClass()));
        	} else {
        		prepareAttribute(styles, "class",
        				StrUtil.concat(HtmlConsts.NM_CLASS_INPUT, " ", HtmlConsts.NM_CLASS_IME_DIS, " ", getStyleClass()));
        	}
        }

        prepareAttribute(styles, "title", message(getTitle(), getTitleKey()));
        prepareAttribute(styles, "alt", message(getAlt(), getAltKey()));
        prepareInternationalization(styles);

        return styles.toString();
    }


    /**
     * onpressEnterkeyイベントをonkeypressイベントに追加する。<br/>
     * @return 結果のjavascript文字列
     */
    protected String getPressEnterKey() {

        if (StrUtil.isEmpty(getOnEnterPress()) ) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();

        	if (((!getReadonly()) && (!getDisabled()))) {
        	// readonlyではない場合。
	            sb.append("if (window.event.keyCode != 13){return;}");
	            sb.append("if(!" + LiveanyTagUtil.buildDateOnblurFun(getFormat()) + ") {return;}");
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

//    protected String getImeCssClass() {
//
//    }

    /**
     * Render the value element
     *
     * @param results The StringBuffer that output will be appended to.
     */
    protected void prepareValue(StringBuffer results)
        throws JspException {
        results.append(" value=\"");

        String val = "";
        if (value != null) {

            if (CommonConsts.FMT_JYY_MM_DD.equals(format)
                    || CommonConsts.FMT_NYYMMDD.equals(format)
                    || CommonConsts.FMT_JCCYY_MM_DD.equals(format)
                    || CommonConsts.FMT_NYY.equals(format)
                    || CommonConsts.FMT_JYY.equals(format)
                    || CommonConsts.FMT_JCCYY.equals(format)
            	    || CommonConsts.FMT_JCCYY_MM.equals(format)
                    || CommonConsts.FMT_NYYMM.equals(format)
                    || CommonConsts.FMT_JYY_MM.equals(format)
            	    || CommonConsts.FMT_YYYYMMDD.equals(format)
                    || CommonConsts.FMT_YYYY_MM_DD.equals(format)
            	    || CommonConsts.FMT_YYYY.equals(format)
            	    || CommonConsts.FMT_YYYYMM.equals(format)
                    || CommonConsts.FMT_YYYY_MM.equals(format)
            	    || CommonConsts.FMT_MM.equals(format)
            	    || CommonConsts.FMT_DD.equals(format)) {
            	val = DateUtil.conv2Date(value, format);
            	if (StrUtil.isEmpty(val)) {
            		val = value;
            	}
            }

            results.append(this.formatValue(val));
        } else if (redisplay || !"password".equals(type)) {
            Object value =
                TagUtils.getInstance().lookup(pageContext, name, property, null);


            if (CommonConsts.FMT_JYY_MM_DD.equals(format)
                    || CommonConsts.FMT_NYYMMDD.equals(format)
                    || CommonConsts.FMT_JCCYY_MM_DD.equals(format)
                    || CommonConsts.FMT_NYY.equals(format)
                    || CommonConsts.FMT_JYY.equals(format)
                    || CommonConsts.FMT_JCCYY.equals(format)
            	    || CommonConsts.FMT_JCCYY_MM.equals(format)
                    || CommonConsts.FMT_NYYMM.equals(format)
                    || CommonConsts.FMT_JYY_MM.equals(format)
            	    || CommonConsts.FMT_YYYYMMDD.equals(format)
                    || CommonConsts.FMT_YYYY_MM_DD.equals(format)
            	    || CommonConsts.FMT_YYYY.equals(format)
            	    || CommonConsts.FMT_YYYYMM.equals(format)
                    || CommonConsts.FMT_YYYY_MM.equals(format)
            	    || CommonConsts.FMT_MM.equals(format)
            	    || CommonConsts.FMT_DD.equals(format)) {
            	val = DateUtil.conv2Date((String)value, format);
            	if (StrUtil.isEmpty(val)) {
            		val = (String) value;
            	}
            }


            results.append(this.formatValue(val));
        }
        results.append('"');
    }

    /**
     * Release any acquired resources.
     */
    public void release() {
        super.release();
    	formName = null;
    	format= null;
    	inputFormat= null;
    	useButton= true;
    	onEnterPress= null;
    	listIndex= null;
    	addUpDownFunc = true;
    }
}
