package tech3g.common.web.tag;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.BaseHandlerTag;
/**
 * <pre>
 * LiveanyTitleTagクラス。
 * </pre>
 *
 */
public class LiveanyTitleTag extends BaseHandlerTag {

	/** タイトル */
	protected String titleName = "";
	/** レベル */
	protected String level = "";
	/** ヘルプボタン表示可否 */
	protected boolean isHelpVisible = false;
	/** マニュアルボタン表示可否 */
	protected boolean isManualVisible = false;
	/** 画面ID　*/
	protected String screenId = "";

	/**
	 * 画面タイトルを取得する。<br/>
	 * @return タイトル
	 */
	public String getTitleName() {
		return titleName;
	}
	/**
	 * 画面タイトルを設定する。<br/>
	 * @param titleName タイトル
	 */
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	/**
	 * レベルを取得する。<br/>
	 * @return レベル
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * レベルを設定する。<br/>
	 * @param level レベル
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * ヘルプボタン表示可否を取得する。<br/>
	 * @return 入力タイプ
	 */
	public boolean isIsHelpVisible() {
		return isHelpVisible;
	}
	/**
	 * ヘルプボタン表示可否を設定する。<br/>
	 * @param inputType
	 */
	public void setIsHelpVisible(boolean isHelpVisible) {
		this.isHelpVisible = isHelpVisible;
	}
	/**
	 * マニュアルボタンを取得する。<br/>
	 * @return 入力タイプ
	 */
	public boolean isIsManualVisible() {
		return isManualVisible;
	}
	/**
	 * マニュアルボタンを設定する。<br/>
	 * @param inputType
	 */
	public void setIsManualVisible(boolean isManualVisible) {
		this.isManualVisible = isManualVisible;
	}
	/**
	 * 画面IDを取得する。<br/>
	 * @return 画面ID
	 */
	public String getScreenId() {
		return screenId;
	}
	/**
	 * 画面IDを設定する。<br/>
	 * @param screenId 画面ID
	 */
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	/**
	 * 画面に表示する文字列を作成する。<br/>
	 * @return タグクラスの常数を返還する
	 * @throws JspException
	 */
	@Override
	public int doStartTag() throws JspException {

		StringBuffer sb = new StringBuffer("");

		if ("2".equals(level)) {
			sb.append("<h2>" + titleName + "</h2>");
		}
		else if ("3".equals(level)) {
			sb.append("<h3>" + titleName + "</h3>");
		}
		else {
			sb.append("<div class=\"w00-1\">");
			sb.append("	<div class=\"w00-1bar\">");
			sb.append("		<div class=\"w00-1t\">");
			sb.append("     <h1>" + titleName + "</h1>");
			if(isManualVisible){
				sb.append("      <span>");
				sb.append("	<a href=\"javascript:showManual('" + screenId + "')\" noDcCheck=\"true\" class=\"manual\"></a>");
				sb.append("      </span>");
			}
			if(isHelpVisible){
				sb.append("      <span>");
				sb.append("	<a href=\"javascript:showHelp('" + screenId + "')\"  noDcCheck=\"true\" class=\"help\"></a>");
				sb.append("      </span>");
			}
			sb.append("		</div>");
			sb.append("	</div>");
			sb.append("</div>");
		}

		TagUtils.getInstance().write(pageContext, sb.toString());
		return (EVAL_BODY_BUFFERED);
	}
}
