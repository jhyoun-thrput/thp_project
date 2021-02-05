package tech3g.common.service.logic;

import java.util.List;
import java.util.Map;

import tech3g.common.dao.COMNAuditDao;
import tech3g.common.dao.COMNExclusionDao;
import tech3g.common.dao.COMNSysvarDao;
import tech3g.common.exceptions.SystemException;
import tech3g.common.util.StrUtil;

/**
 * <pre>
 * CommonLogicクラス。
 * 共通の処理ロジックを実装する。
 * </pre>
 */
public class CommonLogic {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
    //--------------------------------------------------- コンストラクタ
    // -------------------------------------------------- SetGet Methods
    //--------------------------------------------------- インスタンスメソッド



	/**
	 * システム監視情報を登録する。<br/>
	 * @param comnAuditDao システム監視情報DAO
	 * @param userId ユーザーID
	 * @param svcCls Serviceのクラス
	 * @param svcMtd Serviceのメソッド
	 * @param qryKd クエリ
	 * @param qryId クエリID
	 * @param qryPmt
	 * @param hndgIp IPアドレス
	 */
	public static void createAuditInf(COMNAuditDao comnAuditDao, String userId, String svcCls,
			String svcMtd, String qryKd, String qryId, String qryPmt, String hndgIp) {

		// ------ 登録処理を行う。
		int reg = comnAuditDao.insertAuditInf(userId,
												StrUtil.cutStrUTF8Byte(svcCls, 200),
												StrUtil.cutStrUTF8Byte(svcMtd, 100),
												StrUtil.cutStrUTF8Byte(qryKd, 30),
												StrUtil.cutStrUTF8Byte(qryId, 100),
												StrUtil.cutStrUTF8Byte(qryPmt, 4000),
												hndgIp);
		// ------ 登録失敗時の処理
		if (reg < 1) {
			throw new SystemException("err.CO058", new String[] {"システム監視"});
		}

	}

	/**
	 * 排他チェック用の情報を取得する。<br/>
	 * @param comnExclusionDao 排他チェック処理のDAO
	 * @param sql   対象データ取得SQL
	 * @param value パラメータ値
	 * @return
	 */
	public static Map srchExclusionInfo(COMNExclusionDao comnExclusionDao, String sql, String[] value) {
		return comnExclusionDao.selectExclusionInfo(sql, value);
	}

	/**
	 * システム変数リスト取得<br/>
	 * 全てのシステム変数をリストとして取得する。
	 * @param comnSysvarDao システム変数のDao
	 * @return 全てのシステム変数リスト
	 */
	public static List srchSysValList(COMNSysvarDao comnSysvarDao) {
		return comnSysvarDao.selectSysvarList();
	}

	/**
	 * システム変数情報取得<br/>
	 * システム変数を取得する。
	 * @param comnSysvarDao システム変数のDao
	 * @return システム変数情報
	 */
	public static Map srchSysVal(COMNSysvarDao comnSysvarDao, String atnyCd, String sysVarId) {
		return comnSysvarDao.selectSysval(atnyCd, sysVarId);
	}

}
