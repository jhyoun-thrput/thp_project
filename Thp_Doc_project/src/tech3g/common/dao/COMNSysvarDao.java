

package tech3g.common.dao;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * システム変数のDaoImplクラス。
 * </pre>
 */
public interface COMNSysvarDao {

    /** DAOの取得キー */
    public static String KEY_BEAN = "COMNSysvarDao";

	/**
	 * オブジェクト名を取得する。<br/>
	 * @return オブジェクト名
	 */
	public String getObjectName();

	/**
	 * キー項目を取得する。<br/>
	 * @version 修正履歴
	 * @return キー項目
	 */
	public String[] getKeyField();

    /**
     * システム変数のリスト取得<br/>
     * システム変数をListで取得する。<br/>
     * @return システム変数のMap情報
     */
    public List selectSysvarList ();

    /**
     * システム変数の取得<br/>
     * @param atnyCd 自治体コード
     * @param sysVarId システム変数
     * @return システム変数情報Map
     */
    public Map selectSysval(String atnyCd, String sysVarId);



}

