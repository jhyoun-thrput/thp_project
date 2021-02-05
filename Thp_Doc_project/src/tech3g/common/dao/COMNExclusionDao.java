package tech3g.common.dao;

import java.util.Map;



/**
 * <pre>
 * COMNExclusionDaoクラス。
 * 排他チェック処理のDAO
 * </pre>
 */
public interface COMNExclusionDao {

	/** DAOの取得キー */
	public static String KEY_BEAN = "COMNExclusionDao";

	/**
	 * 排他チェック用の情報を取得する。<br/>
	 * @version 修正履歴
	 *          <ul>
	 *          <li>2009/10/08 : 新規作成 (revised by NAKAJIMA)</li>
	 *          </ul>
	 * @param sql
	 * @param value
	 */
	public Map selectExclusionInfo(String sql, String[] value);

}
