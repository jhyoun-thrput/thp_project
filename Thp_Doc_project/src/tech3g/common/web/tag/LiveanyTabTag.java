package tech3g.common.web.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.BaseHandlerTag;

import tech3g.common.util.StrUtil;

/**
 * <pre>
 * LiveanyTabTagクラス。
 * </pre>
 *
 */
public class LiveanyTabTag extends BaseHandlerTag {

	/** Beanの格納キー*/
	protected String name;

    /** Beanの属性 */
	protected String property;

	/** ナビゲータ */
	private List<String> navigator = new ArrayList<String>();

	/** タグのBody */
	private String saveBody;

	/** 選択タブ */
	private String currentTab;

    /**
	 * Beanの格納キーを取得する。<br/>
	 * @return Beanの格納キー
	 */
	public String getName() {
		return name;
	}

	/**
	 * Beanの格納キーを設定する。<br/>
	 * @param name Beanの格納キー
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Beanの属性を取得する。<br/>
	 * @return Beanの属性
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * Beanの属性を設定する。<br/>
	 * @param property Beanの属性
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * 選択タブを取得する。<br/>
	 * @return 選択タブ
	 */
	public String getCurrentTab() {
		return currentTab;
	}

	/**
	 * 選択タブを設定する。<br/>
	 * @param currentTab 選択タブ
	 */
	public void setCurrentTab(String currentTab) {
		this.currentTab = currentTab;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		validate();
		setCurrentTab(getPropertyValue());
		return super.doStartTag();
	}

	/* (non-Javadoc)
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doAfterBody()
     */
    public int doAfterBody() throws JspException {
        if (bodyContent != null) {
            String value = bodyContent.getString();

            if (value == null) {
                value = "";
            }
            this.saveBody = value.trim();
        }
        return (SKIP_BODY);
    }

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		StringBuffer result = new StringBuffer("");

		result.append("\n");
		result.append("<div id=\"container\">\n");
		result.append("\t<div class=\"UdTabs\">\n");

		// ナビゲータ
		result.append("\t\t<ul class=\"UdTabNavi\">\n");
		for (int i = 0; i < navigator.size(); i++) {
			result.append(navigator.get(i));
		}
		result.append("\t\t</ul>\n");

		// Body
		if (saveBody != null) {
			result.append("\t\t<div class=\"tab_container\">\n");
            result.append(saveBody);
            result.append("\t\t</div>\n");
            saveBody = null;
        }

		result.append("\n");
		result.append("\t</div>\n");
		result.append("</div>\n");

		// hidden
		if ((!StrUtil.isBlank(name)) && (!StrUtil.isBlank(property))) {
			result.append("<input type=\"hidden\" name=\"" + property + "\" value=\"" + currentTab + "\">");
		}

		TagUtils.getInstance().write(pageContext, result.toString());
		return (EVAL_PAGE);
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.taglib.html.BaseHandlerTag#release()
	 */
	@Override
	public void release() {
		super.release();
		name = null;
		property = null;
		navigator = new ArrayList<String>();
		saveBody = null;
		currentTab = null;
	}


	/**
	 * 入力チェック<br/>
	 */
	private void validate() throws JspException {

		// name と property は両方入力 or 両方未入力
		if ((!StrUtil.isBlank(name)) && (StrUtil.isBlank(property))) {
			throw new JspException("propertyが入力されていません。");
		}
		if ((StrUtil.isBlank(name)) && (!StrUtil.isBlank(property))) {
			throw new JspException("nameが入力されていません。");
		}
	}

	/**
	 * ナビゲータを追加する。<br/>
	 * @param value ナビゲータ
	 */
	protected void addNavigator(String value) {
		navigator.add(value);
	}

	/**
	 * プロパティ値を取得する。<br/>
	 * @return
	 * @throws JspException
	 */
	private String getPropertyValue() throws JspException {
		Object value = null;

		if ((!StrUtil.isBlank(name)) && (!StrUtil.isBlank(property))) {
			value = TagUtils.getInstance().lookup(pageContext, name, property, null);
		}
        if (value== null) {
        	value = "";
        }
        return value.toString();
	}
}
