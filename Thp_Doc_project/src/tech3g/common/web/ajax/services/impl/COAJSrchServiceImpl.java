package tech3g.common.web.ajax.services.impl;

import tech3g.common.biz.BaseService;
import tech3g.common.dao.COAJSrchDao;
import tech3g.common.web.CommonParams;
import tech3g.common.web.ajax.services.COAJSrchService;
import tech3g.common.web.ajax.services.logic.COAJBizLogic;

/**
 * <pre>
 * COAJSrchServiceImplクラス。
 * </pre>
 *
 */
public class COAJSrchServiceImpl extends BaseService implements
		COAJSrchService {

	/** coajSrchTmpDao */
	private COAJSrchDao coajSrchDao;

	/**
	 * getCoajSrchTmpDaoを取得する。<br/>
	 *
	 * @return warekiService
	 */
	public COAJSrchDao getCoajSrchDao() {
		return coajSrchDao;
	}

	/**
	 * coajSrchTmpDaoを設定する。<br/>
	 *
	 * @param coajSrchTmpDao
	 *            coajSrchTmpDao
	 */
	public void setCoajSrchDao(COAJSrchDao coajSrchDao) {
		this.coajSrchDao = coajSrchDao;
	}

	/* (non-Javadoc)
	 * @see liveany.common.web.ajax.services.COAJSrchService#getInfoByAjax(liveany.common.web.CommonParams)
	 */
	public String getInfoByAjax(CommonParams param) throws Exception {
		return COAJBizLogic.getInfoByAjax(coajSrchDao, param);
	}
}
