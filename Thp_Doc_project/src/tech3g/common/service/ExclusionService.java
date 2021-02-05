package tech3g.common.service;

import java.util.Map;


/**
 * <pre>
 * ExclusionServiceクラス。
 * 排他制御関連のサービスクラス
 * </pre>
 */
public interface ExclusionService {

	/**
	 * 排他チェック用の情報を取得する。<br/>
	 * @param sql 対象データ取得SQL
	 * @param value パラメータ値
	 * @return
	 */
	public Map srchExclusionInfo(String sql, String[] value);

}