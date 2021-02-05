package tech3g.common.web.tag;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.BaseHandlerTag;

import tech3g.common.util.StrUtil;

/**
 * <pre>
 * LiveanyTabElementTagクラス。
 * </pre>
 */
public class LiveanyTabElementTag extends BaseHandlerTag {

	/** タブ名 */
	protected String tabname;

	/** ラベル */
	protected String label;

	/** 実行処理 */
	protected String action;

	/**
	 * タブ名を取得する。<br/>
	 * @return タブ名
	 */
	public String getTabname() {
		return tabname;
	}

	/**
	 * タブ名を設定する。<br/>
	 * @param tabname タブ名
	 */
	public void setTabname(String tabname) {
		this.tabname = tabname;
	}

	/**
	 * ラベルを取得する。<br/>
	 * @return ラベル
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * ラベルを設定する。<br/>
	 * @param label ラベル
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 実行処理を取得する。<br/>
	 * @return 実行処理
	 */
	public String getAction() {
		return action;
	}

	/**
	 * 実行処理を設定する。<br/>
	 * @param action 実行処理
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		StringBuffer result = new StringBuffer("");

		// ナビゲータ
		if (getParent() instanceof LiveanyTabTag) {
			String active = getActiveParam();
			String action = getActionParam();

			result.append("\t\t\t<li" + active + action + ">");
			result.append("<a href=\"#" + tabname + "\">" + label + "</a>");
			result.append("</li>\n");

			((LiveanyTabTag) getParent()).addNavigator(result.toString());
			result = new StringBuffer("");
		}

		if (getParent() != null && (getParent().getParent() instanceof LiveanyTabTag)) {
			String active = getActiveParam();
			String action = getActionParam();

			result.append("\t\t\t<li" + active + action + ">");
			result.append("<a href=\"#" + tabname + "\">" + label + "</a>");
			result.append("</li>\n");

			((LiveanyTabTag) getParent().getParent()).addNavigator(result.toString());
			result = new StringBuffer("");
		}


		result.append("\t\t<div class=\"udTabCon\">\n");

		TagUtils.getInstance().write(pageContext, result.toString());
		return (EVAL_BODY_INCLUDE);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		StringBuffer result = new StringBuffer("");

		result.append("\t\t</div>\n");

		TagUtils.getInstance().write(pageContext, result.toString());
		return (EVAL_PAGE);
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.taglib.html.BaseHandlerTag#release()
	 */
	@Override
	public void release() {
		super.release();
		tabname = null;
		label = null;
		action = null;
	}

	/**
	 * 選択タブを取得する。<br/>
	 * @return
	 */
	private String getActiveParam() {
		String param = "";

		// ナビゲータ
		if (getParent() instanceof LiveanyTabTag) {
			String selectTab = ((LiveanyTabTag) getParent()).getCurrentTab();
			if ((!StrUtil.isBlank(tabname)) && (!StrUtil.isBlank(selectTab))) {
				if (tabname.equals(selectTab)) {
					param = " class=\"active\"";
				}
			}
		}

		if (getParent() != null && (getParent().getParent() instanceof LiveanyTabTag)) {
			String selectTab = ((LiveanyTabTag) getParent().getParent()).getCurrentTab();
			if ((!StrUtil.isBlank(tabname)) && (!StrUtil.isBlank(selectTab))) {
				if (tabname.equals(selectTab)) {
					param = " class=\"active\"";
				}
			}

		}

		return param;
	}

	/**
	 * 実行処理を取得する。<br/>
	 * @return
	 */
	private String getActionParam() {
		String param = "";

		if (!StrUtil.isBlank(action)) {
			param = " onclick=\"" + action + "\"";
		}
		return param;
	}
}
