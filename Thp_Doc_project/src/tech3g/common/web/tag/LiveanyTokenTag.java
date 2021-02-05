package tech3g.common.web.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <pre>
 * LiveanyTokenTagクラス。
 * トークンを利用し
 * ブラウザのリフレッシュによる2度処理防止する。
 * </pre>
 */
public class LiveanyTokenTag extends TagSupport {

	/** トークンのキー名 */
	private static final String KEY_TOKEN = LiveanyTokenTag.class.getName() + ".token";

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.print(drawToken());
		} catch (Exception e) {
			throw new JspException(e);
		}
		return SKIP_BODY;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	/**
	 * トークンをDraw<br/>
	 * @return トークンHTML
	 */
	protected String drawToken() {
		StringBuffer results = new StringBuffer();
		results.append("<input type=\"hidden\" name=\"");
		results.append(KEY_TOKEN);
		results.append("\" value=\"");
		results.append(String.valueOf(System.currentTimeMillis()));
		results.append("\" />");
		return results.toString();
	}

    /**
     * トークンチェック<br/>
     * @param request HttpServletRequest
     * @param key
     * @return チェック判定結果(正常: true | エラー：false)
     */
    public static boolean chkToken(HttpServletRequest request , String key) {

        String scrTokenValue = request.getParameter(KEY_TOKEN);

        if (scrTokenValue == null) {
            return true;
        }

        HttpSession session = request.getSession();

        synchronized (session.getId().intern()) {

            Map tokenMap = (Map) session.getAttribute(KEY_TOKEN);

            if (tokenMap == null) {
                tokenMap = new HashMap();
                session.setAttribute(KEY_TOKEN , tokenMap);
            }

            String sesTokenValue = (String) tokenMap.get(key);

            // トランザクション・トークンを検証する
            if (sesTokenValue == null ) {
            // 新規のAction名の場合
                tokenMap.put(key , scrTokenValue);
                return true;
            } else {
                long lScrTokenValue = Long.parseLong(scrTokenValue);
                long lSesTokenValue = Long.parseLong(sesTokenValue);
                if (lSesTokenValue < lScrTokenValue) {
                // 正常
                    tokenMap.put(key , scrTokenValue);
                    return true;
                } else {
                // トークンエラー
                    return false;
                }
            }
        }
    }

}