package tech3g.common.web.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * UserSessionクラス。
 * </pre>
 */
public class UserSession implements Serializable {

	/** 共通エリア */
	private Map commonMap;

	/** ローカルエリア */
	private Map localMap;

	/**
	 * コンストラクタ<br/>
	 */
	public UserSession() {
		commonMap = new HashMap();
		localMap = new HashMap();
	}

	/**
	 * 共通エリアを取得する。<br/>
	 * @return 共通エリア
	 */
	public Map getCommonMap() {
		return commonMap;
	}

	/**
	 * 共通エリアを設定する。<br/>
	 * @param commonMap 共通エリア
	 */
	public void setCommonMap(Map commonMap) {
		this.commonMap = commonMap;
	}

	/**
	 * ローカルエリアを取得する。<br/>
	 * @return ローカルエリア
	 */
	public Map getLocalMap() {
		return this.localMap;
	}

	/**
	 * ローカルエリアを設定する。<br/>
	 * @param localMap ローカルエリア
	 */
	public void setLocalMap(Map localMap) {
		this.localMap = localMap;
	}

	/**
	 * 共通エリアから指定したキーのオブジェクトを取得する。<br/>
	 * @param key セッションキー
	 * @return
	 */
	public Object getCommon(String key) {
		return commonMap.get(key);
	}

	/**
	 * 共通エリアの指定したキーにオブジェクトを設定する。<br/>
	 * @param key セッションキー
	 * @param value
	 */
	public void putCommon(String key, Object value) {
		commonMap.put(key, value);
	}

	/**
	 * 共通エリアから指定したキーのオブジェクトを削除する。<br/>
	 * @param key セッションキー
	 */
	public void removeCommon(String key) {
		commonMap.remove(key);
	}

	/**
	 * 共通エリアのオブジェクトをクリアする。<br/>
	 */
	public void clearCommon() {
		commonMap.clear();
	}

	/**
	 * ローカルエリアから指定したキーのオブジェクトを取得する。<br/>
	 * @param key セッションキー
	 * @return
	 */
	public Object getLocal(String key) {
		return localMap.get(key);
	}

	/**
	 * ローカルエリアの指定したキーにオブジェクトを設定する。<br/>
	 * @param key セッションキー
	 * @param value
	 */
	public void putLocal(String key, Object value) {
		localMap.put(key, value);
	}

	/**
	 * ローカルエリアから指定したキーのオブジェクトを削除する。<br/>
	 * @param key セッションキー
	 */
	public void removeLocal(String key) {
		localMap.remove(key);
	}


	/**
	 * ローカルエリアのクリア。<br/>
	 * ローカルエリアのオブジェクトをクリアする。<br/>
	 *
	 * 注意）clearLocal()メソッドは、外部（基幹システム）で呼び出しているので
	 * メソッド名は変更しないこと。
	 *
	 */
	public void clearLocal() {
		localMap.clear();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("*** UserSession ***\n");
		sb.append("①local area Session keys:");
		sb.append(localMap.keySet() + "\n");
		sb.append("②common area Session keys :");
		sb.append(commonMap.keySet() + "\n");
		return sb.toString();
	}
}
