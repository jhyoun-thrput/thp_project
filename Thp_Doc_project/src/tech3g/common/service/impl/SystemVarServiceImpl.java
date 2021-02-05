package tech3g.common.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tech3g.common.biz.BaseService;
import tech3g.common.dao.COMNSysvarDao;
import tech3g.common.exceptions.SystemException;
import tech3g.common.service.SystemVarService;
import tech3g.common.service.logic.CommonLogic;

import com.opensymphony.oscache.base.NeedsRefreshException;


/**
 * <pre>
 * システム変数ServiceImplクラス。
 * </pre>
 */
public class SystemVarServiceImpl extends BaseService implements SystemVarService {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数

	/** システム変数のDao */
	private COMNSysvarDao comnSysvarDao;

    /** キャッシュ有無 */
    private boolean useCache;

    //--------------------------------------------------- コンストラクタ
    // -------------------------------------------------- SetGet Methods
	/**
	 * キャッシュ有無を取得する。<br/>
	 * @return キャッシュ有無
	 */
	public boolean getUseCache() {
		return useCache;
	}
	/**
	 * キャッシュ有無を設定する。<br/>
	 * @param useCache キャッシュ有無
	 */
	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}


	/**
	 * システム変数のDaoを取得する。<br/>
	 * @return システム変数のDao
	 */
	public COMNSysvarDao getComnSysvarDao() {
		return comnSysvarDao;
	}
	/**
	 * システム変数のDaoを設定する。<br/>
	 * @param comnSysvarDao システム変数のDao
	 */
	public void setComnSysvarDao(COMNSysvarDao comnSysvarDao) {
		this.comnSysvarDao = comnSysvarDao;
	}



    //--------------------------------------------------- インスタンスメソッド
	/**
	 * サービスを初期化する。<br/>
	 * システム変数データをロードしキャッシュして置く。<br/>
	 */
	public void initSystemVarService() {
    	if (log.isDebugEnabled()) {
    		log.debug("システム変数サービスの初期化...開始");
    	}

    	// *** キャッシュがTRUEであればロードする。
    	if (getUseCache()) {
    		cacheService.putInCache("SYS_VAL_POOL", getSysValPool());
    	}

    	if (log.isDebugEnabled()) {
    		log.debug("コシステム変数サービスの初期化...終了");
    	}
	}



	/**
	 * システム変数Poolの取得<br/>
	 *
	 * @return システム変数Pool
	 */
	private Map getSysValPool() {
		Map pool = new HashMap();
		List list = CommonLogic.srchSysValList(comnSysvarDao);

		Iterator it = list.iterator();

		while (it.hasNext()) {
			Map info = (Map) it.next();
			pool.put(info.get("sys_var_id"), info.get("sys_var_val"));
		}
		return pool;
	}





	/* (non-Javadoc)
	 * @see liveany.common.service.SystemVarService#srchSystemVar(java.lang.String, java.lang.String)
	 */
	public Object srchSystemVar(String atnyCd, String id) {

		if (getUseCache()) {
			try {

				// ----- キャッシュからシステム変数情報を取得
				Map sysValPool = (Map) cacheService.getFromCache("SYS_VAL_POOL");
				return sysValPool.get(id);
			} catch (NeedsRefreshException e) {
				log.warn("システム変数情報のリフレッシュが要求されました。");
				initSystemVarService();
				throw new SystemException("何らかの理由でシステム変数情報のキャッシュリフレッシュが要求されました。画面を開きなおして下さい。");
			}
		} else {
			return CommonLogic.srchSysVal(comnSysvarDao, atnyCd, id).get("sys_var_val");
		}
	}

}
