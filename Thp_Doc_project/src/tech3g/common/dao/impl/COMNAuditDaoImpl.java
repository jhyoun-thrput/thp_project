/**  */
package tech3g.common.dao.impl;

import tech3g.common.dao.COMNAuditDao;
import tech3g.common.db.BaseDao;
import tech3g.common.db.StaticSqlParam;

/**
 * <pre>
 * COMNAuditDaoImplクラス。
 * システム監視情報のDAOクラス。
 * </pre>
 *
 * @version 修正履歴
 *          <ul>
 *          <li>2009/07/10 : 新規作成 (revised by Noh.S.Y)</li>
 *          </ul>
 * @author Noh.S.Y
 */
public class COMNAuditDaoImpl extends BaseDao implements COMNAuditDao {


	/* (non-Javadoc)
	 * @see liveany.fk.dao.co.mn.COMNAuditDao#insertAuditInf(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int insertAuditInf(String userId, String svcCls, String svcMtd, String qryKd,
			String qryId, String qryPmt, String hndgIp) {

		// ------ 登録を行う。
		StaticSqlParam ssp = new StaticSqlParam();
		ssp.addParam(userId);
		ssp.addParam(svcCls);
		ssp.addParam(svcMtd);
		ssp.addParam(qryKd);
		ssp.addParam(qryId);
		ssp.addParam(qryPmt);
		ssp.addParam(hndgIp);
		return insert("co.mn.insertAuditInf", ssp);
	}

}
