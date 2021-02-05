package tech3g.common.web;

import org.apache.struts.action.ActionMapping;

/**
 * <pre>
 * ServiceActionMappingクラス。
 * ActionMappingの拡張したクラス。
 * </pre>
 */
public class ServiceActionMapping extends ActionMapping{

	/** メソッド名 */
	private String method;

	/** トークンチェックするかどうか */
	private boolean tokenCheck = true;

	/**
	 * メソッド名の取得<br/>
	 * @return
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * <br/>
	 * @param method
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * tokenCheckを取得する。<br/>
	 * @return tokenCheck
	 */
	public boolean getTokenCheck() {
		return tokenCheck;
	}

	/**
	 * tokenCheckを設定する。<br/>
	 * @param tokenCheck tokenCheck
	 */
	public void setTokenCheck(boolean tokenCheck) {
		this.tokenCheck = tokenCheck;
	}

}
