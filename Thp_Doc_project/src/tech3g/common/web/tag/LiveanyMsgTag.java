package tech3g.common.web.tag;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.bean.MessageTag;

import tech3g.common.util.MessageUtil;


/**
 * <pre>
 * LiveanyMsgTagクラス。
 * メッセージを表示するためのカスタムタグ。
 * StrutsのMessageTagタグをoverRideする。
 * </pre>
 */
public class LiveanyMsgTag extends MessageTag {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
    //--------------------------------------------------- コンストラクタ


    //--------------------------------------------------- インスタンスメソッド
    /**
     * Process the start tag.
     *
     * @throws JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        String key = this.key;

        if (key == null) {
            // Look up the requested property value
            Object value =
                TagUtils.getInstance().lookup(pageContext, name, property, scope);

            if ((value != null) && !(value instanceof String)) {
                JspException e =
                    new JspException(messages.getMessage("message.property", key));

                TagUtils.getInstance().saveException(pageContext, e);
                throw e;
            }

            key = (String) value;
        }

        // Construct the optional arguments array we will be using
        Object[] args = new Object[] { arg0, arg1, arg2, arg3, arg4 };

        // Retrieve the message string we are looking for
        String message = MessageUtil.getMessage(key, args);

        if (message == null) {
            JspException e =
                new JspException(messages.getMessage("message.message",  "\"" + key + "\"",  "\"" + ""));
            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        TagUtils.getInstance().write(pageContext, message);

        return (SKIP_BODY);
    }

}
