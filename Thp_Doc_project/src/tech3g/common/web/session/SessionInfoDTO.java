package tech3g.common.web.session;

import java.io.Serializable;

/**
 * <pre>
 * ログインユーザ情報DTOクラス。
 * </pre>
 */
public class SessionInfoDTO implements Serializable {

	private static final long serialVersionUID = 4962679225518396315L;

	/** ユーザID */
	private String userId;
	/** IPアドレス */
	private String ipAddr;
	/** クラス名 */
	private String className;
	/** メソッド名 */
	private String methodName;

	private String user_admin;

	/**
	 * クラス名の取得<br/>
	 * @return クラス名
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * クラス名の設定<br/>
	 * @param className クラス名
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * メソッド名の取得<br/>
	 * @return メソッド名
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * メソッド名の設定<br/>
	 * @param methodName メソッド名
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * ユーザIDの取得<br/>
	 * @return ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * ユーザIDの設定<br/>
	 * @param userId ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * IPアドレスの取得<br/>
	 * @return IPアドレス
	 */
	public String getIpAddr() {
		return ipAddr;
	}

	/**
	 * IPアドレスの設定<br/>
	 * @param ipAddr IPアドレス
	 */
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	/**
	 * @return user_admin
	 */
	public String getUser_admin() {
		return user_admin;
	}

	/**
	 * @param user_admin セットする user_admin
	 */
	public void setUser_admin(String user_admin) {
		this.user_admin = user_admin;
	}
}
