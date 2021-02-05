package tech3g.common.service.impl;

import java.util.Map;

import tech3g.common.biz.BaseService;
import tech3g.common.dao.COMNExclusionDao;
import tech3g.common.service.ExclusionService;
import tech3g.common.service.logic.CommonLogic;

/**
 * <pre>
 * ExclusionServiceImplクラス。
 * 排他制御関連のサービスクラス
 * </pre>
 */
public class ExclusionServiceImpl extends BaseService implements ExclusionService {

	//--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
	/** 排他チェック処理DAO */
	private  COMNExclusionDao comnExclusionDao;

    //--------------------------------------------------- コンストラクタ
    // -------------------------------------------------- SetGet Methods
	/**
	 * 排他チェック処理DAOを取得する。<br/>
	 * @return 排他チェック処理DAO
	 */
	public COMNExclusionDao getComnExclusionDao() {
		return comnExclusionDao;
	}

	/**
	 * 排他チェック処理DAOを設定する。<br/>
	 * @param comnExclusionDao 排他チェック処理DAO
	 */
	public void setComnExclusionDao(COMNExclusionDao comnExclusionDao) {
		this.comnExclusionDao = comnExclusionDao;
	}

    //--------------------------------------------------- インスタンスメソッド
	/* (non-Javadoc)
	 * @see liveany.common.service.ExclusionService#srchExclusionInfo(java.lang.String, java.lang.String[])
	 */
	public Map srchExclusionInfo(String sql, String[] value) {
		return CommonLogic.srchExclusionInfo(comnExclusionDao, sql, value);
	}

}
