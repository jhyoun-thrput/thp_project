package tech3g.common.web.tag;

import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.ErrorsTag;

import tech3g.common.util.MessageUtil;

/**
 * <pre>
 * LiveanyErrorsTagクラス。
 * </pre>
 *
 */
public class LiveanyErrorsTag extends ErrorsTag {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
	/** メッセージ表示をjavaScriptのAlertで表示するかどうか */
	private boolean isAlert = true;

    //--------------------------------------------------- コンストラクタ
    // -------------------------------------------------- SetGet Methods
    /**
     * isAlertを取得する。<br/>
     * @return メッセージ表示をjavaScriptのAlertで表示するかどうか
     */
    public boolean getIsAlert() {
		return isAlert;
	}

	/**
	 * isAlertを設定する。<br/>
	 * @param isAlert メッセージ表示をjavaScriptのAlertで表示するかどうか
	 */
	public void setIsAlert(boolean isAlert) {
		this.isAlert = isAlert;
	}

    //--------------------------------------------------- インスタンスメソッド


	/* (non-Javadoc)
	 * @see org.apache.struts.taglib.html.ErrorsTag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
        // Were any error messages specified?
        ActionMessages errors = null;

        try {
            errors =
                TagUtils.getInstance().getActionMessages(pageContext, name);
        } catch (JspException e) {
            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        if ((errors == null) || errors.isEmpty()) {
            return (EVAL_BODY_INCLUDE);
        }

        boolean headerPresent =
            TagUtils.getInstance().present(pageContext, bundle, locale,
                getHeader());

        boolean footerPresent =
            TagUtils.getInstance().present(pageContext, bundle, locale,
                getFooter());

        boolean prefixPresent =
            TagUtils.getInstance().present(pageContext, bundle, locale,
                getPrefix());

        boolean suffixPresent =
            TagUtils.getInstance().present(pageContext, bundle, locale,
                getSuffix());

        // Render the error messages appropriately
        StringBuffer results = new StringBuffer();
        StringBuffer msgs = new StringBuffer();
        boolean headerDone = false;
        String message = null;
        Iterator reports =
            (property == null) ? errors.get() : errors.get(property);


        if (isAlert) {
        // javaScriptのAlertでメッセージを表示する場合。
        // この場合は、HeaderとFooterを設定しても、表示しない。

	        while (reports.hasNext()) {
	            ActionMessage report = (ActionMessage) reports.next();

	            if (report.isResource()) {
	                message =
	                    TagUtils.getInstance().message(pageContext, bundle, locale,
	                        report.getKey(), report.getValues());
	            } else {
	                message = report.getKey();
	            }

	            if (message == null) {
	            	message = MessageUtil.getMessage(report.getKey(), report.getValues());
	            }

	            //msgs.append("*");
	            msgs.append(message);
            	msgs.append("\\n");
	        }

	        results.append("<script language=\"javascript\">\n");
	        results.append("alert(\"");
	        results.append(msgs.toString());
	        results.append("\");\n");

	        results.append("</script>\n");

	        TagUtils.getInstance().write(pageContext, results.toString());

        } else {
        // 普通の場合（strutsのHTMLタグと同じ仕様で表示する場合）

	        while (reports.hasNext()) {
	            ActionMessage report = (ActionMessage) reports.next();

	            if (!headerDone) {
	                if (headerPresent) {
	                    message =
	                        TagUtils.getInstance().message(pageContext, bundle,
	                            locale, getHeader());

	                    results.append(message);
	                }

	                headerDone = true;
	            }

	            if (prefixPresent) {
	                message =
	                    TagUtils.getInstance().message(pageContext, bundle, locale,
	                        getPrefix());
	                results.append(message);
	            }

	            if (report.isResource()) {
	                message =
	                    TagUtils.getInstance().message(pageContext, bundle, locale,
	                        report.getKey(), report.getValues());
	            } else {
	                message = report.getKey();
	            }

	            if (message != null) {
	                results.append(message);
	            }

	            if (suffixPresent) {
	                message =
	                    TagUtils.getInstance().message(pageContext, bundle, locale,
	                        getSuffix());
	                results.append(message);
	            }
	        }

	        if (headerDone && footerPresent) {
	            message =
	                TagUtils.getInstance().message(pageContext, bundle, locale,
	                    getFooter());
	            results.append(message);
	        }

	        TagUtils.getInstance().write(pageContext, results.toString());
        }

        return (EVAL_BODY_INCLUDE);
    }

}
