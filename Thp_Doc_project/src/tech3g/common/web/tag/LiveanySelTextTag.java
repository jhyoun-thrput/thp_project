package tech3g.common.web.tag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.FormTag;
import org.apache.struts.util.MessageResources;

import tech3g.common.constants.CommonConsts.SORT_TYPE;
import tech3g.common.util.StrUtil;

/**
 * <pre>
 * LiveanySelTextTagクラス。
 * </pre>
 *
 */
public class LiveanySelTextTag  extends LiveanyTextTag {

    //---------------------------------------------------定数
    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
        MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

    /** SelTextタグの要素区分：TEXT */
    protected final static char EMT_TEXT = 't';
    /** SelTextタグの要素区分：SELECT */
    protected final static char EMT_SELECT = 's';

    //---------------------------------------------------インスタンス変数

    /** mcode：マスタコードID  */
    protected String mcode;
    /** mcode : DECODEテーブルの表示する項目　
     * DCODE_NM1　　　　：1を指定する。
     * DCODE_NM2　　　　：2を指定する。
     * DCODE_NM3　　　　：3を指定する。
     * DCODE_ABBR_NM　　：4を指定する。
     * */
    protected String mode;
    /** isShowAll：使用可否（USE_YN）の全項目を表示するかのフラグ (true | false <b>Default<b/>)<br/>
     * trueの場合は該当MCODEIDの全てのデータを表示する。<br/>
     * falseの場合は、使用可否（USE_YN）が'Y'のデータのみ表示する。 */
    protected String isShowAll;
    /** range：表示したいデータを絞る場合 「:」区切りでコードを並べる<br/>
     * 例)01 ～09までのコートがある場合、01,03,05のみ表示したい: range="01:03:05" */
    protected String range;
    /** tusrstr：テキスト項目タグに文字列を追加定義する。 */
    protected String tusrstr;
    /** susrstr：ドロップダウン項目タグに文字列を追加定義する。 */
    protected String susrstr;
    /** sort ：整列の方式 (desc | asc <b>Default<b/>) */
    protected String sort;
    /** withid：キーもともに表示するかのフラグ (true | false <b>Default<b/>)*/
    protected String withid;
    /** first:1行目の表示文字列 */
    protected String first;
    /** sstyle：ドロップダウンのCSSスタイル */
    protected String sstyle;
    /** tstyle：テキストのCSSスタイル */
    protected String tstyle;
    /** tStyleClass：テキストのCSSスタイルクラス */
    protected String tStyleClass;
    /** sStyleClass：ドロップダウンのCSSスタイルクラス */
    protected String sStyleClass;

    /** list：コードのリスト */
    protected String list;

    // ------------------------------------------------------------- コンストラクタ
	/**
	 * Defaultコンストラクタ<br/>
	 */
	public LiveanySelTextTag() {
		super();
	}

    // --------------------------------------------------------- SetGet Methods


	/**
	 * mcodeを取得する。<br/>
	 * @return mcode
	 */
	public String getMcode() {
		return mcode;
	}

	/**
	 * mcodeを設定する。<br/>
	 * @param mcode mcode
	 */
	public void setMcode(String mcode) {
		this.mcode = mcode;
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
	 * isShowAllを取得する。<br/>
	 * @return isShowAll
	 */
	public String getIsShowAll() {
		return isShowAll;
	}

	/**
	 * isShowAllを設定する。<br/>
	 * @param isShowAll isShowAll
	 */
	public void setIsShowAll(String isShowAll) {
		this.isShowAll = isShowAll;
	}

	/**
	 * rangeを取得する。<br/>
	 * @return range
	 */
	public String getRange() {
		return range;
	}

	/**
	 * rangeを設定する。<br/>
	 * @param range range
	 */
	public void setRange(String range) {
		this.range = range;
	}

	/**
	 * tusrstrを取得する。<br/>
	 * @return tusrstr
	 */
	public String getTusrstr() {
		return tusrstr;
	}

	/**
	 * tusrstrを設定する。<br/>
	 * @param tusrstr tusrstr
	 */
	public void setTusrstr(String tusrstr) {
		this.tusrstr = tusrstr;
	}

	/**
	 * susrstrを取得する。<br/>
	 * @return susrstr
	 */
	public String getSusrstr() {
		return susrstr;
	}

	/**
	 * susrstrを設定する。<br/>
	 * @param susrstr susrstr
	 */
	public void setSusrstr(String susrstr) {
		this.susrstr = susrstr;
	}

	/**
	 * sortを取得する。<br/>
	 * @return sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * sortを設定する。<br/>
	 * @param sort sort
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * withidを取得する。<br/>
	 * @return withid
	 */
	public String getWithid() {
		return withid;
	}

	/**
	 * withidを設定する。<br/>
	 * @param withid withid
	 */
	public void setWithid(String withid) {
		this.withid = withid;
	}

	/**
	 * firstを取得する。<br/>
	 * @return first
	 */
	public String getFirst() {
		return first;
	}

	/**
	 * firstを設定する。<br/>
	 * @param first first
	 */
	public void setFirst(String first) {
		this.first = first;
	}

	/**
	 * sstyleを取得する。<br/>
	 * @return sstyle
	 */
	public String getSstyle() {
		return sstyle;
	}

	/**
	 * sstyleを設定する。<br/>
	 * @param sstyle sstyle
	 */
	public void setSstyle(String sstyle) {
		this.sstyle = sstyle;
	}

	/**
	 * tstyleを取得する。<br/>
	 * @return tstyle
	 */
	public String getTstyle() {
		return tstyle;
	}

	/**
	 * tstyleを設定する。<br/>
	 * @param tstyle tstyle
	 */
	public void setTstyle(String tstyle) {
		this.tstyle = tstyle;
	}

	/**
	 * tStyleClassを取得する。<br/>
	 * @return tStyleClass
	 */
	public String getTStyleClass() {
		return tStyleClass;
	}

	/**
	 * tStyleClassを設定する。<br/>
	 * @param styleClass tStyleClass
	 */
	public void setTStyleClass(String styleClass) {
		tStyleClass = styleClass;
	}

	/**
	 * sStyleClassを取得する。<br/>
	 * @return sStyleClass
	 */
	public String getSStyleClass() {
		return sStyleClass;
	}

	/**
	 * sStyleClassを設定する。<br/>
	 * @param styleClass sStyleClass
	 */
	public void setSStyleClass(String styleClass) {
		sStyleClass = styleClass;
	}

	/**
	 * listを取得する。<br/>
	 * @return list
	 */
	public String getList() {
		return list;
	}

	/**
	 * listを設定する。<br/>
	 * @param list list
	 */
	public void setList(String list) {
		this.list = list;
	}

	//---------------------------------------------------インスタンスメソッド
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {

		StringBuffer results = new StringBuffer();
		results.append("");

		// --------------- ソートの判定
		SORT_TYPE isort = SORT_TYPE.SORT_ASC;
		if (sort != null && sort.compareToIgnoreCase("desc") == 0)
			isort = SORT_TYPE.SORT_DESC;

		// --------  rangeを配列に変換して置く。
		String[] range_arr;
		if (range != null) {
			range_arr = range.split(":", 100);
		} else {
			range_arr = new String[0];
		}

		// -------- タイトルに表示する項目名を抽出
		String optionTitleKey = "dcode_nm1";
		if ("0".equals(getMode())) {
			optionTitleKey = "dcode_id";
		} else if ("1".equals(getMode())) {
			optionTitleKey = "dcode_nm1";
		} else if ("2".equals(getMode())) {
			optionTitleKey = "dcode_nm2";
		} else if ("3".equals(getMode())) {
			optionTitleKey = "dcode_nm3";
		} else if ("4".equals(getMode())) {
			optionTitleKey = "dcode_abbr_nm";
		}

		// --------------- リストの取得
		ArrayList<ListOrderedMap> rtn = new ArrayList<ListOrderedMap>();
		List<ListOrderedMap> cols = null;
		if (!StrUtil.isBlank(mcode)) {
			String[] mcode_item = mcode.split(";");
    		for (int i = 0; i < mcode_item.length; i++) {
    			if ("true".equalsIgnoreCase(isShowAll)) {
    				cols = null;
    			} else {
    				cols = null;
    			}
    			rtn.addAll(cols);
    		}
		}


		if ((StrUtil.isEmpty(mcode)) && (StrUtil.isEmpty(list))) {
			//throw new SystemException("selTextの「mcode」「list」どちらかは指定しなければなりません。(" + getProperty() + ")");
		}


		if ((StrUtil.isBlank(mcode)) && (!StrUtil.isBlank(list))) {
			cols = (List) TagUtils.getInstance().lookup(pageContext, name, list, null);
			if (cols != null) {
				rtn.addAll(cols);
			}
		}

		// -------------- valueの取得

		if (value == null) {
            Object value = TagUtils.getInstance().lookup(pageContext, name, property, null);
            if (value== null) {
            	value = "";
            }
            setValue(value.toString());
		}

        results.append("\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
        results.append("\t<tr>\n");
        results.append("\t\t<td>");

		// テキストボックス
        results.append(buildText(rtn, range_arr, optionTitleKey));

        results.append("\t\t</td>\n");
        results.append("\t\t<td width=\"1px\"></td>\n");
        results.append("\t\t<td>");

		// ドロップダウン(Select)
        results.append(buildSelect(rtn, range_arr, optionTitleKey));

        results.append("\t\t</td>\n");
        results.append("\t</tr>\n");
        results.append("</table>\n");

		TagUtils.getInstance().write(pageContext, results.toString());

        return (EVAL_BODY_TAG);
    }

    /**
     * Release any acquired resources.
     */
    @Override
	public void release() {
        super.release();
        name = Constants.BEAN_KEY;
        property = null;
        value = null;
    }

    /**
	 * テキストボックス（TEXT）のBODYを作成する。<br/>
     * @param dcodeLst 詳細コードリスト
     * @param range_arr range配列
     * @param optionTitleKey 表示ラベル
     * @return TextのHtmlソース
     * @throws JspException Jsp例外
     */
    private String buildText(List dcodeLst, String[] range_arr, String optionTitleKey) throws JspException {

    	StringBuffer results = new StringBuffer("<input");
        prepareAttribute(results, "type", "text");
        prepareAttribute(results, "name", getProperty());
        prepareAttribute(results, "accesskey", getAccesskey());
        prepareAttribute(results, "accept", getAccept());
        prepareAttribute(results, "maxlength", getMaxlength());
        prepareAttribute(results, "size", getCols());

        if (((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
    		prepareAttribute(results, "tabindex", "-1");
    	} else {
    	// disable Or readonly
    		prepareAttribute(results, "tabindex", getTabindex());
    	}

        results.append(" value=\"");
        if (!StrUtil.isEmpty(value)) {
            results.append(value);
        } else {
    		if (getFirst() == null
    				&& !((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {

    			if (dcodeLst != null && dcodeLst.size() > 0) {
    				Map decodeInf = (Map) dcodeLst.get(0);
    				results.append(decodeInf.get("dcode_id"));

    			} else {
    				if (!StrUtil.isEmpty(list)) {
    					List cols = (List) TagUtils.getInstance().lookup(pageContext, name, list, null);
	        				Map decodeInf = (Map) cols.get(0);
	        				results.append(decodeInf.get("dcode_id"));
    				}
    			}


    		}
        }
        results.append('"');

        // ----- イベント処理
        prepareMouseEvents(results);
        prepareKeyEvents(results, EMT_TEXT);
        prepareTextEvents(results);
        prepareFocusEvents(results);

        results.append(prepareStyles(EMT_TEXT));
        prepareOtherAttributes(results);
		if (tusrstr != null) {
			results.append(" ");
			results.append(tusrstr);
		}
        results.append(this.getElementClose());
        return results.toString();
	}

    /* (non-Javadoc)
     * @see liveany.common.web.tag.LiveanyTextTag#prepareTextEvents(java.lang.StringBuffer)
     */
    protected void prepareTextEvents(StringBuffer handlers) {
        prepareAttribute(handlers, "onselect", getOnselect());
        prepareAttribute(handlers, "onchange", StrUtil.concat("onblur(this);", getOnchange()));
    }

	/**
	 * ドロップダウン（SELECT）のBODYを作成する。<br/>
	 * @param dcodeLst 出力するDCODE情報リスト
	 * @return タグ文字列
	 * @throws JspException Jsp例外
	 */
	private String buildSelect(List dcodeLst, String[] range_arr, String optionTitleKey) throws JspException {

		StringBuffer str = new StringBuffer();

		if (getReadonly()) {
			if (!StrUtil.isEmpty(list)) {
				List cols = (List) TagUtils.getInstance().lookup(pageContext, name, list, null);
				if (cols != null) {
					Iterator it = cols.iterator();
					Map inf = null;
					while (it.hasNext()) {
						inf = (Map) it.next();
						if (getValue().equals(inf.get("dcode_id"))) {
							str.append(inf.get(optionTitleKey));
							break;
						}
					}
				}
			} else if (!StrUtil.isEmpty(mcode)) {
				String newValue = null;
				str.append(newValue);
			}
			return str.toString();
		}


		str.append("<select");
        prepareAttribute(str, "name", getProperty() + "_nm");
        prepareAttribute(str, "accesskey", getAccesskey());
//	        if (multiple != null) {
//	            results.append(" multiple=\"multiple\"");
//	        }
        // prepareAttribute(results, "size", getSize());

        if (((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
        	prepareAttribute(str, "tabindex", "-1");
    	} else {
    	// disable Or readonly
    		prepareAttribute(str, "tabindex", getTabindex());
    	}

        prepareMouseEvents(str);
        prepareKeyEvents(str, EMT_SELECT);
        // ------------ prepareTextEvents(str);
        prepareAttribute(str, "onselect", getOnselect());
        prepareAttribute(str, "onchange", StrUtil.concat("setTextField(this, '", getProperty(), "');", getOnchange()));


        // ------------ onblur, onfocus Event
        prepareAttribute(str, "onblur", getOnblur());
        prepareAttribute(str, "onfocus", getOnfocus());
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
            	str.append(" disabled=\"disabled\"");
            }
        }
        // Format Read Only
        if (doReadonly) {
            boolean formReadOnly =
                (formTag == null) ? false : formTag.isReadonly();

            if (formReadOnly || getReadonly()) {
            	str.append(" readonly=\"readonly\"");
            }
        }

        // -------- styles
        str.append(prepareStyles(EMT_SELECT));

        // -------- そのた
        prepareOtherAttributes(str);

        // -------- susrstr
		if (susrstr != null) {
			str.append(" ");
			str.append(susrstr);
		}
		str.append(">");

		// -------- １行目を追加
		if (getFirst() != null) {
			str.append("<option value=\'\'>");
			str.append(getFirst());
			str.append("</option>");
		}

		String dcdID = "";
		ListOrderedMap item = null;
		Iterator colsItr = dcodeLst.iterator();
		while (colsItr.hasNext()) {
			item = (ListOrderedMap) colsItr.next();
			dcdID = item.get("dcode_id").toString();

			// options start
			if (getRange() == null) {
				str.append("<option value=\'");
				str.append(dcdID);
				str.append("\'");

				// 選択処理
				if (getValue() != null) {
					if (getValue().equals(item.get("dcode_id")) || getValue().equals(item.get("dcode_nm"))) {
						str.append(" selected");
					}
				}

				str.append(" title='");
				str.append(item.get("dcode_nm1"));
				str.append("'");
				str.append(">");

				if (withid != null && withid.equalsIgnoreCase("true")) {
					str.append(dcdID);
					str.append(" - ");
				}

				str.append((String) item.get(optionTitleKey));
				str.append("</option >");

			} else {

				for (int i = 0; i < range_arr.length; i++) {
					if (dcdID.equals(range_arr[i])) {
						str.append("<option value=\'");
						str.append(dcdID);
						str.append("\'");

						// 選択処理
						if (getValue() != null) {
							if (getValue().equals(item.get("dcode_id"))
									|| getValue().equals(item.get("dcode_nm"))) {
								str.append(" selected");
							}
						}

						str.append(" title='");
						str.append(item.get("dcode_nm1"));
						str.append("'");
						str.append(">");

						if (withid != null && withid.equalsIgnoreCase("true")) {
							str.append(dcdID);
							str.append(" - ");
						}

						str.append((String) item.get(optionTitleKey));
						str.append("</option >");
					}
				}
			}
		}

		str.append("</select>");
		return str.toString();
	}


	/**
	 * id、style、class、title、alt等を定義する。<br/>
	 * <br/>
	 * @param type <code>EMT_TEXT</code> | <code>EMT_SELECT</code>
	 * @return inputタグの属性Htmlコード
	 * @throws JspException Jsp例外
	 */
	protected String prepareStyles(char type) throws JspException {
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
			prepareAttribute(styles, "style", type == EMT_TEXT ? getTstyle() : getSstyle());
		}
		if (errorsExist && (getErrorStyleClass() != null)) {
			prepareAttribute(styles, "class", getErrorStyleClass());
		} else {
	        if (((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
	        	prepareAttribute(styles, "class", StrUtil.concat(LiveanyTagUtil.getBaseStyle(getInputType(),
	        			getReadonly()), " ", type == EMT_TEXT ? getTStyleClass() : getSStyleClass()));
			} else {
				prepareAttribute(styles, "class", StrUtil.concat(LiveanyTagUtil.getBaseStyle(getInputType(),
						getReadonly()), " ", LiveanyTagUtil.setInputControl(getInputType()), " ",
						type == EMT_TEXT ? getTStyleClass() : getSStyleClass()));
			}
		}
		prepareAttribute(styles, "title", message(getTitle(), getTitleKey()));
		prepareAttribute(styles, "alt", message(getAlt(), getAltKey()));
		prepareInternationalization(styles);

		return styles.toString();
	}

    /**
     * onkeydown、onkeyup、onkeypressのキーイベントを定義する。<br/>
     * @param handlers StringBuffer
	 * @param type <code>EMT_TEXT</code> | <code>EMT_SELECT</code>
     */
    protected void prepareKeyEvents(StringBuffer handlers, char type) {

		if (type == EMT_TEXT) {
	    	if (((doDisabled && !getDisabled()) || (doReadonly && !getReadonly())) ) {
	    	    prepareAttribute(handlers, "onkeydown", getOnkeydown());
	    	} else {
	    		prepareAttribute(handlers, "onkeydown", getOnkeydown());
	    	}

	        prepareAttribute(handlers, "onkeyup", StrUtil.concat(getOnkeyup(), "setSelectField(this, '", getProperty() + "_nm" ,"');"));

	        // ---------- keydownイベント
	        if (((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
	        	prepareAttribute(handlers, "onkeypress", getOnkeypress());
	    	} else {
	    		prepareAttribute(handlers, "onkeypress",
	    				StrUtil.concat(
	    						LiveanyTagUtil.getKeyDownEvent(getInputType()),
	    						getOnkeypress(), ";",
	    						getPressEnterKey(EMT_TEXT))
	    		);
	    	}
		} else {
	        prepareAttribute(handlers, "onkeydown", getOnkeydown());
	        prepareAttribute(handlers, "onkeyup", getOnkeyup());
	        // ---------- keydownイベント
	        if (((doReadonly && getReadonly()) || (doDisabled && getDisabled()))) {
	        	prepareAttribute(handlers, "onkeypress", getOnkeypress());
	    	} else {
	    		prepareAttribute(handlers, "onkeypress",
	    				StrUtil.concat(
	    						LiveanyTagUtil.getKeyDownEvent(getInputType()),
	    						getOnkeypress(), ";",
	    						getPressEnterKey(EMT_SELECT))
	    		);
	    	}
		}
    }

	/* (non-Javadoc)
	 * @see liveany.common.web.tag.LiveanyTextTag#preTextOnblur()
	 */
	@Override
	protected String preTextOnblur() {
    	return StrUtil.concat("setSelectField(this, '", getProperty() + "_nm" ,"');", "chkSelTextValue(this, '", getProperty() + "_nm" ,"');");
    }

    /**
     * onpressEnterkeyイベントをonkeypressイベントに追加する。<br/>
	 * @param type <code>EMT_TEXT</code> | <code>EMT_SELECT</code>
     * @return Enterキーを押下した時のJavascript文字列
     */
    protected String getPressEnterKey(char type) {

        if (StrUtil.isEmpty(getOnEnterPress()) ) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();

        	if (((!getReadonly()) && (!getDisabled())) && getInputType() != null) {
        	// readonlyではない場合。


	            sb.append("if (window.event.keyCode != 13){return;}");
        		if (type == EMT_TEXT) {

        			sb.append(LiveanyTagUtil.getPaddingStr(getProperty(), getLpad(), getRpad(), getDpad(), getMaxlength()));
        			sb.append(LiveanyTagUtil.toCase(getIsUpperCase(), getIsLowerCase()));
        			sb.append(LiveanyTagUtil.getOnblurEventStr(getInputType()));
        			sb.append("setSelectField(this, '");
        			sb.append(getProperty() + "_nm");
        			sb.append("');");
        			sb.append("chkSelTextValue(this, '");
        			sb.append(getProperty() + "_nm");
        			sb.append("');");
        			sb.append("if(!");
        			sb.append(LiveanyTagUtil.getOnblurInputCheckEvent(getInputType(), getChaExt(), (!getPositive() ? "-" : "") + getDecimalFormat()));
        			sb.append(") {return;}");

        		} else {
    				sb.append(StrUtil.concat("if(!", LiveanyTagUtil.getOnblurInputCheckEvent(getInputType(), getChaExt(), getDecimalFormat()), ") {return;}"));
        		}

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

}
