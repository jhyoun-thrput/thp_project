/**  */
package tech3g.common.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.dao.COMNExclusionDao;
import tech3g.common.db.BaseDao;
import tech3g.common.db.DAOHelper;


/**
 * <pre>
 * COMNExclusionDaoImplクラス。
 * 排他チェック処理のDAO
 * </pre>
 *
 * @version 修正履歴
 *          <ul>
 *          <li>2009/10/08 : 新規作成 (revised by NAKAJIMA)</li>
 *          </ul>
 * @author NAKAJIMA
 */
public class COMNExclusionDaoImpl extends BaseDao implements COMNExclusionDao {

	/** 項目タイプ */
	private static final String FIELD_TYPE = "VARCHAR";

	/* (non-Javadoc)
	 * @see liveany.fk.dao.co.mn.COMNExclusionDao#checkExclusionInfo(java.lang.String, java.lang.String[])
	 */
	public Map selectExclusionInfo(String sql, String[] values) {

		int valueSize = values.length;
		String[] types = new String[valueSize];
		for (int i = 0; i < valueSize; i++) {
			types[i] = FIELD_TYPE;
		}

		return DAOHelper.getMap((List<ListOrderedMap>) selectBySQL(sql, types, values));
	}
}
