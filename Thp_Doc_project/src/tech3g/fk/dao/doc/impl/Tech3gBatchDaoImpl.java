/**
 *
 */
package tech3g.fk.dao.doc.impl;

import java.util.List;
import java.util.Map;

import tech3g.common.constants.CommonConsts;
import tech3g.common.db.BaseDao;
import tech3g.common.db.DAOHelper;
import tech3g.common.db.DynaSqlParam;
import tech3g.common.util.DateUtil;
import tech3g.common.util.StrUtil;
import tech3g.fk.dao.doc.Tech3gBatchDao;
import tech3g.fk.dao.doc.dto.Tech3gBatchDTO;
import tech3g.fk.dao.doc.dto.Tech3gBatchRegiDTO;
import tech3g.fk.dao.doc.dto.Tech3gRegiDocDTO;

/**
 * @author tech3g
 *
 */
public class Tech3gBatchDaoImpl extends BaseDao implements Tech3gBatchDao {

    /** オブジェクト名 */
    private final String OBJECT_NAME = "TECH3G_BATCH_INFO";

    /** キー項目 */
    private final String[] KEY_FIELD = {"BT_ID", "BT_NO"};

    /* (non-Javadoc)
     * @see liveany.fk.dao.ho.cm.HOCMDbtSchdDao#getObjectName()
     */
    public String getObjectName() {
        return OBJECT_NAME;
    }

    /* (non-Javadoc)
     * @see liveany.fk.dao.ho.cm.HOCMDbtSchdDao#getKeyField()
     */
    public String[] getKeyField() {
        return KEY_FIELD;
    }

	public Map selectBatchInfo(String btId, String bt_no) {

        // ------ 必須パラメータチェック
        checkReqParam(new String[][] {
					{"Batch ID", 		btId}
				,	{"Batch 一連番号", 	bt_no}
        });

        // ------- パラメータを設定する。
        DynaSqlParam dsp = new DynaSqlParam();
        dsp.addParam("bt_id", 	btId);
        dsp.addParam("bt_no", 	bt_no);

        // -------検索を行う。
        return DAOHelper.getMap(select("doc.searchBatchInfo", dsp));
	}

	public int insertBatchInfo(String userId, Tech3gBatchDTO dto) {

        // ------ 必須パラメータチェック
        checkReqParam(new String[][] {
					{"Batch ID", 		dto.getBt_id()}
        });

        // ------- パラメータを設定する。
        DynaSqlParam dsp = new DynaSqlParam();
        dsp.addParam("bt_id", 				dto.getBt_id());
        dsp.addParam("bt_sched_dttm", 		StrUtil.isEmpty(dto.getBt_sched_dttm())?null:dto.getBt_sched_dttm()); // 予定時間
        dsp.addParam("bt_param", 			dto.getBt_param());
        dsp.addParam("user_id", 			userId);
        dsp.addParamDecimal("bt_hist_no", 	dto.getBt_hist_no());  // 前処理
        dsp.addParam("bt_flg",					dto.getBt_flg());
        dsp.addParam("bt_status", 			CommonConsts.BT_STT_PROCESS);

		// ------- 登録を行う。
		return insert("doc.insertBatchInfo", dsp);
	}

	public List selectBatchInfoList(String btId, String bt_no, String begin_date, String end_date, int startNum, int endNum) {

        // ------- パラメータを設定する。
        DynaSqlParam dsp = new DynaSqlParam();
        dsp.addParam("bt_id", 			 btId);
        dsp.addParam("bt_no", 			 bt_no);
        dsp.addParam("bt_begin_date", 	 StrUtil.isEmpty(begin_date) ? "" : DateUtil.conv2Seireki(begin_date) + "000000");
        dsp.addParam("bt_end_date", 	 StrUtil.isEmpty(end_date) ? "" : DateUtil.conv2Seireki(end_date) + "999999");
        dsp.addParam ("startNo", 		 startNum);                                   // 開始行
        dsp.addParam ("endNo", 		 	 endNum);                                     // 終了行

        // -------検索を行う。
        return select("doc.searchBatchList", dsp);
	}

	public int selectBatchInfoCount(String btId, String bt_no, String begin_date, String end_date) {

        // ------- パラメータを設定する。
        DynaSqlParam dsp = new DynaSqlParam();
        dsp.addParam("bt_id", 			 btId);
        dsp.addParam("bt_no", 			 bt_no);
        dsp.addParam("bt_begin_date", 	 StrUtil.isEmpty(begin_date) ? "" : DateUtil.conv2Seireki(begin_date) + "000000");
        dsp.addParam("bt_end_date", 	 StrUtil.isEmpty(end_date) ? "" : DateUtil.conv2Seireki(end_date) + "999999");

        // -------検索を行う。
        return DAOHelper.getCnt(select("doc.searchBatchListCnt", dsp));
	}

	public String selectBatchMaxBtNo(String btId) {

        // ------ 必須パラメータチェック
        checkReqParam(new String[][] {
					{"Batch ID", 		btId}
        });

        // ------- パラメータを設定する。
        DynaSqlParam dsp = new DynaSqlParam();
        dsp.addParam("bt_id", btId);

        // -------検索を行う。
        return DAOHelper.getString(select("doc.searchBatchMaxBtNo", dsp));
	}

	public Map selectBatchInfoDesc() {

        // ------- パラメータを設定する。
        DynaSqlParam dsp = new DynaSqlParam();

        // -------検索を行う。
        return DAOHelper.getMap(select("doc.searchBatchInfoDesc", dsp));
	}

	public int updateBatchInfo(String userId, Tech3gBatchDTO dto) {

        // ------ 必須パラメータチェック
        checkReqParam(new String[][] {
					{"Batch ID", 		dto.getDtl_bt_id()}
				,	{"Batch 一連番号", 	dto.getDtl_bt_no()}
        });

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("bt_id",			dto.getDtl_bt_id());
		dsp.addParam("bt_no",			dto.getDtl_bt_no());
		dsp.addParam("bt_msg",			dto.getBt_msg());
		dsp.addParam("bt_status",		dto.getBt_status());
		dsp.addParam("user_id",			userId);

		return update("doc.updateBatchInfo", dsp);
	}

	public int mergeDocCode(String user_id, Tech3gRegiDocDTO dto) {
        checkReqParam(new String[][] {
			 	{"文書コード", 	dto.getDoc_code()}
        });

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code",			dto.getDoc_code());
		dsp.addParam("doc_code_name",		dto.getDoc_name());
		dsp.addParam("doc_code_link",		dto.getDoc_link());
		dsp.addParam("user_id",				user_id);
		dsp.addParam("comment",				dto.getComment());
		dsp.addParam("doc_root_path",				dto.getDoc_root_path());

		return insert("doc.mergeBatchCode", dsp);
	}

	public int mergeDocInfo(String user_id, Tech3gRegiDocDTO dto) {
        checkReqParam(new String[][] {
			 	{"文書コード", 	dto.getDoc_code()}
			,	{"文書 Link", 		dto.getDoc_link()}
			,	{"文書 Ver", 		dto.getDoc_ver()}
        });

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code",			dto.getDoc_code());
		dsp.addParamDecimal("doc_index",	dto.getDoc_index());
		dsp.addParam("doc_name",			dto.getDoc_name());
		dsp.addParam("doc_type",			dto.getDoc_type());
		dsp.addParam("doc_link",			dto.getDoc_link());
		dsp.addParam("user_id",				user_id);
		dsp.addParam("doc_ver",				dto.getDoc_ver());
		dsp.addParam("comment",				dto.getComment());

		return update("doc.mergeBatchInfo", dsp);
	}

	public String getTechBtId(String btId) {
		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("bt_id",	btId);

		return DAOHelper.getString(select("doc.getTechBtId", dsp));
	}

	public String getMaxBtNo(String btId) {
		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("bt_id",	btId);

		return DAOHelper.getString(select("doc.getMaxBtId", dsp));
	}

	public List selectMasterBatchList(String btId) {

        // ------- パラメータを設定する。
        DynaSqlParam dsp = new DynaSqlParam();
        dsp.addParam("bt_id", btId);

        // -------検索を行う。
        return select("doc.searchBatchMaster", dsp);
	}

	public Map selectMasterBatchInfo(String btId) {

        checkReqParam(new String[][] {
			 	{"Batch ID", 	btId}
        });

        // ------- パラメータを設定する。
        DynaSqlParam dsp = new DynaSqlParam();
        dsp.addParam("bt_id", btId);

        // -------検索を行う。
        // -------検索を行う。
        return DAOHelper.getMap(select("doc.searchBatchMaster", dsp));
	}

	public Map techDocInfoMap(String doc_code, String doc_link) {

		checkReqParam(new String[][] {
				{ "文書 Link", doc_code },
				{ "文書 Link", doc_link }
		});

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code", doc_code);
		dsp.addParam("doc_link", doc_link);

		// -------検索を行う。
		return DAOHelper.getMap(select("doc.techDocInfoMap", dsp));
	}

	public int insertBatchMaster(String user_id, Tech3gBatchRegiDTO dto) {
        checkReqParam(new String[][] {
			 	{"Batch ID", 		dto.getBt_id()}
			,	{"Batch Param", 	dto.getBt_param()}
        });

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("bt_id",			dto.getBt_id());
		dsp.addParam("bt_name",			dto.getBt_name());
		dsp.addParam("bt_param",		dto.getBt_param());
		dsp.addParam("user_id",			user_id);
		dsp.addParam("bt_flg",				 dto.getBt_flg());


		return insert("doc.insertBatchMaster", dsp);
	}

	public int updateBatchMaster(String user_id, Tech3gBatchRegiDTO dto) {
        checkReqParam(new String[][] {
			 	{"Batch ID", 		dto.getBt_id()}
			,	{"Batch Param", 	dto.getBt_param()}
        });

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("bt_id",			dto.getBt_id());
		dsp.addParam("bt_name",			dto.getBt_name());
		dsp.addParam("bt_param",		dto.getBt_param());
		dsp.addParam("user_id",			user_id);
		dsp.addParam("bt_flg",				dto.getBt_flg());

		return update("doc.updateBatchMaster", dsp);
	}

	public int deleteBatchMaster(String bt_id) {
        checkReqParam(new String[][] {
			 	{"Batch ID", 		bt_id}
        });

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("bt_id",		bt_id);

		return delete("doc.deleteBatchMaster", dsp);
	}

	public Map searchDocCodeRootPath(String doc_root_path) {

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_root_path",			doc_root_path);

        // -------検索を行う。
        return DAOHelper.getMap(select("doc.getRootPath", dsp));
	}
}
