/**
 *
 */
package tech3g.fk.dao.doc.impl;

import tech3g.common.db.BaseDao;
import tech3g.common.db.DynaSqlParam;
import tech3g.fk.dao.doc.Tech3gStatisSpbExceptionDao;
import tech3g.fk.dao.doc.dto.Tech3gSpbExcetionDTO;

/**
 * @author tech3g
 *
 */
public class Tech3gStatisSpbExceptionDaoImpl extends BaseDao implements Tech3gStatisSpbExceptionDao {


	/** オブジェクト名 */
	private final String OBJECT_NAME = "STATIS_SPB_EXCEPTION";

	/** キー項目 */
	private final String[] KEY_FIELD = { "STATIS_FLG", "OCCR_TIME" };

	/* (非 Javadoc)
	 * @see tech3g.fk.dao.doc.Tech3gStatisSpbExceptionDao#getObjectName()
	 */
	public String getObjectName() {
		return OBJECT_NAME;
	}

	/* (非 Javadoc)
	 * @see tech3g.fk.dao.doc.Tech3gStatisSpbExceptionDao#getKeyField()
	 */
	public String[] getKeyField() {
		return KEY_FIELD;
	}

	/* (非 Javadoc)
	 * @see tech3g.fk.dao.doc.Tech3gStatisSpbExceptionDao#insertSpbException(java.lang.String, tech3g.fk.dao.doc.dto.Tech3gSpbExcetionDTO)
	 */
	public int insertSpbException(String user_id, Tech3gSpbExcetionDTO dto) {

        checkReqParam(new String[][] {
			 		{"統計区分", 	dto.getStatis_flg()}
			 	,	{"例外発生日時", 	dto.getOccr_time()}
        });

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("statis_flg",				dto.getStatis_flg());
		dsp.addParamDate("occr_time",	dto.getOccr_time());
		dsp.addParam("bs_name",				dto.getBs_name());
		dsp.addParam("bs_no",					dto.getBs_no());
		dsp.addParam("bs_type",				dto.getBs_type());
		dsp.addParam("bs_ip",					dto.getBs_ip());
		dsp.addParam("statis_name",		dto.getStatis_name());
		dsp.addParam("comment",			dto.getComment());
		dsp.addParam("user_id",				user_id);

		return insert("statis.insertSpbException", dsp);
	}

}
