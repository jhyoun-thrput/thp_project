

package tech3g.common.dao.impl;

import java.util.List;
import java.util.Map;

import tech3g.common.dao.COMNSysvarDao;
import tech3g.common.db.BaseDao;
import tech3g.common.db.DAOHelper;
import tech3g.common.db.DynaSqlParam;


/**
 * <pre>
 * システム変数のDaoクラス。
 * </pre>
 *
 * @version 修正履歴
 *          <ul>
 *          <li>2010/01/21 : 新規作成 (revised by Noh.S.Y)</li>
 *          </ul>
 * @author Noh.S.Y
 */
public class COMNSysvarDaoImpl extends BaseDao implements COMNSysvarDao {

    /** オブジェクト名 */
    private final String OBJECT_NAME = "TCOMN_SYSVAR";

    /** キー項目 */
    private final String[] KEY_FIELD = {};

    /* (non-Javadoc)
     * @see liveany.fk.dao.co.mn.COMNSysvarDao#getObjectName()
     */
    public String getObjectName() {
        return OBJECT_NAME;
    }


    public String[] getKeyField() {
        return KEY_FIELD;
    }

    /* (non-Javadoc)
     * @see liveany.common.dao.COMNSysvarDao#selectSysvarList()
     */
    public List selectSysvarList () {

        // ------- パラメータを設定する。
        DynaSqlParam dsp = new DynaSqlParam();

        // -------検索を行う。
        return select("co.mn.selectSysvarList", dsp);
    }

	/* (non-Javadoc)
	 * @see liveany.common.dao.COMNSysvarDao#selectSysval(java.lang.String, java.lang.String)
	 */
	public Map selectSysval(String atnyCd, String sysVarId) {

        // ------- パラメータを設定する。
        DynaSqlParam dsp = new DynaSqlParam();
        dsp.addParam("atny_cd", atnyCd);
        dsp.addParam("sys_var_id", sysVarId);

        // -------検索を行う。
        return DAOHelper.getMap(select("co.mn.selectSysvar", dsp));
	}

}

