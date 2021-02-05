package tech3g.common.service;

/**
 * <pre>
 * システム変数ServiceのInterface。
 * </pre>
 */
public interface SystemVarService {

	/**
	 * システム変数の取得<br/>
	 * @param atnyCd 自治体コード
	 * @param id システム変数ID
	 * @return 値
	 */
	public Object srchSystemVar(String atnyCd, String id);

}
