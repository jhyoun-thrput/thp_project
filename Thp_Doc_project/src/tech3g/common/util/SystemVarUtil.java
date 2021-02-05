package tech3g.common.util;

import tech3g.common.service.SystemVarService;

/**
 * <pre>
 * システム変数Utilクラス。
 * 環境別に設定するシステム変数を取得するUtil。
 * TCOMN_SYSVARテーブルのデータをIDで取得できる。
 * </pre>
 *
 */
public class SystemVarUtil {

	/** システム変数のService */
	private static SystemVarService systemVarService;

	/**
	 * システム変数Serviceを取得する。<br/>
	 * @return systemVarService システム変数Service
	 */
	public SystemVarService getSystemVarService() {
		return systemVarService;
	}

	/**
	 * システム変数Serviceを設定する。<br/>
	 * @param systemVarService システム変数Service
	 */
	public void setSystemVarService(SystemVarService systemVarService) {
		this.systemVarService = systemVarService;
	}



	/**
	 * システム変数の取得<br/>
	 * システム変数IDをキーにしシステム変数の値を取得する。
	 * @version 修正履歴
	 * @param atnyCd 自治体コード
	 * @param id システム変数ID
	 * @return 値
	 */
	public static String getSysVar(String atnyCd, String id) {
		return (String)systemVarService.srchSystemVar(atnyCd, id);
	}


}
