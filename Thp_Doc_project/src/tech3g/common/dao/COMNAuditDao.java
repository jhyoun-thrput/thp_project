package tech3g.common.dao;



/**
 * <pre>
 * COMNAuditDaoクラス。
 * システム監視テーブルのDAO
 * </pre>
 */
public interface COMNAuditDao {

	/** DAOの取得キー */
	public static String KEY_BEAN = "COMNAuditDao";

	 /**
	 * システム監視情報を登録する。<br/>
	 * @param userId
	 * @param svcCls
	 * @param svcMtd
	 * @param qryKd
	 * @param qryId
	 * @param qryPmt
	 * @param hndgIp
	 * @return
	 */
	public int insertAuditInf(String userId, String svcCls, String svcMtd, String qryKd, String qryId, String qryPmt, String hndgIp);






}
