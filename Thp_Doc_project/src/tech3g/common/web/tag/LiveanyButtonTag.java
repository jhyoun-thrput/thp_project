
package tech3g.common.web.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;

import tech3g.common.util.StrUtil;

/**
 * <pre>
 * LiveanyButtonTagクラス。
 * </pre>
 *
 */
public class LiveanyButtonTag extends TagSupport {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
	/** ボタンのタイプ [blur : brown : bluegray (default):link ] */
	protected String type;
	/** ボタンの押下時に実行するJavaScript関数名 */
	protected String action;
	/** タブインデックス */
	protected String tabindex;
	/** ボタン名 */
	protected String label;
	/** 幅 */
	protected String width;
	/** ボタンの活性化 */
	protected String disabled;
	/** id */
	protected String id;
	/** ボタンの押下時に、進行中のイメージを表示するかどうか (true | false)  */
	protected String showProgress;

	/** ダブルクリックチェックをするかどうか (true | false) */
	protected String dcCheck;


	//--------------------------------------------------- コンストラクタ

	/**
	 * コンストラクター<br/>
	 */
	public LiveanyButtonTag() {
		super();
	}

    // -------------------------------------------------- SetGet Methods

	/**
	 * typeを取得する。<br/>
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * typeを設定する。<br/>
	 * @param type type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * actionを取得する。<br/>
	 * @return action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * actionを設定する。<br/>
	 * @param action action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * tabindexを取得する。<br/>
	 * @return tabindex
	 */
	public String getTabindex() {
		return tabindex;
	}

	/**
	 * tabindexを設定する。<br/>
	 * @param tabindex tabindex
	 */
	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	/**
	 * labelを取得する。<br/>
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * labelを設定する。<br/>
	 * @param label label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * widthを取得する。<br/>
	 * @return width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * widthを設定する。<br/>
	 * @param width width
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * disabledを取得する。<br/>
	 * @return disabled
	 */
	public String getDisabled() {
		return disabled;
	}

	/**
	 * disabledを設定する。<br/>
	 * @param disabled disabled
	 */
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	/**
	 * idを取得する。<br/>
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * idを設定する。<br/>
	 * @param id id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * showProgressを取得する。<br/>
	 * @return showProgress
	 */
	public String getShowProgress() {
		return showProgress;
	}

	/**
	 * showProgressを設定する。<br/>
	 * @param showProgress showProgress
	 */
	public void setShowProgress(String showProgress) {
		this.showProgress = showProgress;
	}




    //--------------------------------------------------- インスタンスメソッド

	/**
	 * ダブルクリックチェック設定の取得<br/>
	 * @return ダブルクリックチェック設定の取得
	 */
	public String getDcCheck() {
		return dcCheck;
	}

	/**
	 * ダブルクリックチェック設定の返却<br/>
	 * @param dcCheck ダブルクリックチェック設定の取得
	 */
	public void setDcCheck(String dcCheck) {
		this.dcCheck = dcCheck;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {

		HttpServletRequest request =  (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

		StringBuffer results = new StringBuffer();
		if ("blue".equalsIgnoreCase(type)) {
		// 検索ボタン
			getTypeBlue(results, request, response);
		} else if ("brown".equalsIgnoreCase(type)) {
		// 茶色ボタン
			getTypeBrown(results, request, response);
		} else if ("link".equalsIgnoreCase(type)) {
			getTypeLink(results, request, response);
		} else {
			getTypeBlueGray(results, request, response);
		}

		TagUtils.getInstance().write(pageContext, results.toString());
        return (EVAL_PAGE);
    }






	/**
	 * リンクの作成<br/>
	 * @param results
	 * @param request
	 * @param response
	 */
	private void getTypeLink(StringBuffer results, HttpServletRequest request, HttpServletResponse response) {


		results.append("<a ");

		prepareAttribute(results, "id", id);
		prepareAttribute(results, "name", id);

		if ("true".equalsIgnoreCase(disabled)) {
			prepareAttribute(results, "hrefBk", StrUtil.concat(action, getShowProgressStr()) );
			prepareAttribute(results, "tabIndexBk", tabindex);
		} else {
			prepareAttribute(results, "href", StrUtil.concat(action, getShowProgressStr()));
			prepareAttribute(results, "tabindex", tabindex);
		}

		if ("false".equalsIgnoreCase(dcCheck)) {
			prepareAttribute(results, "noDcCheck", "true");
		}

		results.append(">");
		results.append(label);
		results.append("</a>");

	}

	/**
	 * ブルーグレーボタンの作成<br/>
	 * @param results
	 * @param request
	 * @param response
	 */
	private void getTypeBlueGray(StringBuffer results, HttpServletRequest request, HttpServletResponse response) {
		results.append("\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top: 7px;\">\n");
		results.append("\t<tr>\n");

		results.append("\t\t<td>");
		results.append("<img src=\"");
		results.append(request.getContextPath());
		results.append(response.encodeURL("/images/common/butt_gleft.gif"));
		results.append("\" width=\"2\" height=\"19\">");
		results.append("</td>\n");

		results.append("\t\t<td class=\"dblue\" align=\"center\"");
		prepareAttribute(results, "width", width);
		results.append(">");

		results.append("<a ");

		prepareAttribute(results, "id", id);
		prepareAttribute(results, "name", id);

		if ("true".equalsIgnoreCase(disabled)) {
			prepareAttribute(results, "hrefBk", StrUtil.concat(action, getShowProgressStr()) );
			prepareAttribute(results, "tabIndexBk", tabindex);
		} else {
			prepareAttribute(results, "href", StrUtil.concat(action, getShowProgressStr()));
			prepareAttribute(results, "tabindex", tabindex);
		}


		if ("false".equalsIgnoreCase(dcCheck)) {
			prepareAttribute(results, "noDcCheck", "true");
		}

		// ----- プログレスを表示
//		if ("true".equalsIgnoreCase(showProgress)) {
//			prepareAttribute(results, "onclick", "javascript:comShowPB();" );
//		}

		results.append(">");
		results.append(label);
		results.append("</a>");

		results.append("</td>\n");

		results.append("\t\t<td>");
		results.append("<img src=\"");

		results.append(request.getContextPath());
		results.append(response.encodeURL("/images/common/butt_gright.gif"));


		results.append("\" width=\"2\" height=\"19\">");
		results.append("</td>\n");

		results.append("\t</tr>\n");
		results.append("</table>\n");
	}


	/**
	 * ブルーボタンの作成<br/>
	 * @param results
	 * @param request
	 * @param response
	 */
	private void getTypeBlue(StringBuffer results, HttpServletRequest request, HttpServletResponse response) {
		results.append("\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top: 1px;\">\n");
		results.append("\t<tr>\n");

		results.append("\t\t<td>");
		results.append("<img src=\"");
		results.append(request.getContextPath());
		results.append(response.encodeURL("/images/common/butt_sleft.gif"));
		results.append("\" width=\"2\" height=\"19\">");
		results.append("</td>\n");

		results.append("\t\t<td class=\"search\" align=\"center\"");
		prepareAttribute(results, "width", width);
		results.append(">");

		results.append("<a ");

		prepareAttribute(results, "id", id);
		prepareAttribute(results, "name", id);

		if ("true".equalsIgnoreCase(disabled)) {
			prepareAttribute(results, "hrefBk", StrUtil.concat(action, getShowProgressStr()));
			prepareAttribute(results, "tabIndexBk", tabindex);
		} else {
			prepareAttribute(results, "href", StrUtil.concat(action, getShowProgressStr()));
			prepareAttribute(results, "tabindex", tabindex);
		}

		if ("false".equalsIgnoreCase(dcCheck)) {
			prepareAttribute(results, "noDcCheck", "true");
		}

		// ----- プログレスを表示
//		if ("true".equalsIgnoreCase(showProgress)) {
//			prepareAttribute(results, "onclick", "javascript:comShowPB();" );
//		}

		results.append(">");
		results.append(label);
		results.append("</a>");

		results.append("</td>\n");

		results.append("\t\t<td>");
		results.append("<img src=\"");

		results.append(request.getContextPath());
		results.append(response.encodeURL("/images/common/butt_sright.gif"));


		results.append("\" width=\"2\" height=\"19\">");
		results.append("</td>\n");

		results.append("\t</tr>\n");
		results.append("</table>\n");
	}



	/**
	 * ブラウンボタンの作成<br/>
	 * @param results
	 * @param request
	 * @param response
	 */
	private void getTypeBrown(StringBuffer results, HttpServletRequest request, HttpServletResponse response) {
		results.append("\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		results.append("\t<tr>\n");

		results.append("\t\t<td>");
		results.append("<img src=\"");
		results.append(request.getContextPath());
		results.append(response.encodeURL("/images/common/butt_bleft.gif"));
		results.append("\" width=\"2\" height=\"19\">");
		results.append("</td>\n");

		results.append("\t\t<td class=\"brown\" align=\"center\"");
		prepareAttribute(results, "width", width);
		results.append(">");

		results.append("<a ");

		prepareAttribute(results, "id", id);
		prepareAttribute(results, "name", id);

		if ("true".equalsIgnoreCase(disabled)) {
			prepareAttribute(results, "hrefBk", StrUtil.concat(action, getShowProgressStr()));
			prepareAttribute(results, "tabIndexBk", tabindex);
		} else {
			prepareAttribute(results, "href", StrUtil.concat(action, getShowProgressStr()));
			prepareAttribute(results, "tabindex", tabindex);
		}


		if ("false".equalsIgnoreCase(dcCheck)) {
			prepareAttribute(results, "noDcCheck", "true");
		}

		// ----- プログレスを表示
//		if ("true".equalsIgnoreCase(showProgress)) {
//			prepareAttribute(results, "onclick", "javascript:comShowPB();" );
//		}

		results.append(">");
		results.append(label);
		results.append("</a>");

		results.append("</td>\n");

		results.append("\t\t<td>");
		results.append("<img src=\"");

		results.append(request.getContextPath());
		results.append(response.encodeURL("/images/common/butt_bright.gif"));


		results.append("\" width=\"2\" height=\"19\">");
		results.append("</td>\n");

		results.append("\t</tr>\n");
		results.append("</table>\n");
	}





    /**
     * handlersに属性コードを組み立て格納する。<br/>
     * 注意) 属性の値がなければコートを格納しない。
     * @param handlers StringBuffer
     * @param name 属性名
     * @param value 属性の値
     */
    protected void prepareAttribute(StringBuffer handlers, String name, Object value) {
		if (value != null) {
			handlers.append(" ");
			handlers.append(name);
			handlers.append("=\"");
			handlers.append(value);
			handlers.append("\"");
		}
	}

    /**
     * 処理中のイメージを表示する関数。<br/>
     * @return プログレス表示関数文字列
     */
    protected String getShowProgressStr() {
    	if ("true".equalsIgnoreCase(showProgress)) {
    		return ";javascript:comShowPB();";
    	} else {
    		return "";
    	}
    }
}
