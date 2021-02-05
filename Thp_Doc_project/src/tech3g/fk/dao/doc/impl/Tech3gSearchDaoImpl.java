/**
 *
 */
package tech3g.fk.dao.doc.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.db.BaseDao;
import tech3g.common.db.DAOHelper;
import tech3g.common.db.DynaSqlParam;
import tech3g.fk.dao.doc.Tech3gSearchDao;
import tech3g.fk.dao.doc.dto.Tech3gDocDetailDTO;
import tech3g.fk.dao.doc.dto.Tech3gDocDetailHistoryDTO;
import tech3g.fk.dao.doc.dto.Tech3gRegiDocDTO;

/**
 * @author tech3g
 *
 */
public class Tech3gSearchDaoImpl extends BaseDao implements Tech3gSearchDao {

	/** オブジェクト名 */
	private final String OBJECT_NAME = "TECH3G_DOC_INFO";

	/** キー項目 */
	private final String[] KEY_FIELD = { "DOC_CODE", "DOC_INDEX" };

	private static final String serach_sql = "doc.techDocSearch";

	private static final String search_cnt_sql = "doc.techDocSearchCnt";

	private static final String search_doc_code_sql = "doc.techDocCodeSearch";

	private static final String search_doc_code_link_sql = "doc.techDocCodeLink";

	private static final String search_doc_index_sql = "doc.techDocInfoIndex";

	private static final String search_doc_detail_sql = "doc.techDocDetail";

	private static final String search_doc_hist_detail_sql = "doc.techDocHistSearch";

	private static final String search_doc_hist_cnt_sql = "doc.techDocHistSearchCnt";

	private static final String update_doc_detail_sql = "doc.updateDocDetail";

	private static final String insert_doc_detail_sql = "doc.insertDocDetail";

	private static final String insert_doc_detail_batch_sql = "doc.insertDocDetailBatch";

	private static final String insert_doc_code_sql = "doc.insertDocCode";

	private static final String delete_doc_code_sql = "doc.deleteTechDocCode";

	private static final String delete_doc_info_sql = "doc.deleteTechDocDetail";

	private static final String insert_doc_detail_history_sql = "doc.insertDocDetailHistory";



	/**
	 *
	 */
	public Tech3gSearchDaoImpl() {
		super();
		log.debug(this.getClass().getName());
	}

	/* (非 Javadoc)
	 * @see tech3g.fk.dao.doc.Tech3gSearchDao#getObjectName()
	 */
	public String getObjectName() {
		return this.OBJECT_NAME;
	}

	/* (非 Javadoc)
	 * @see tech3g.fk.dao.doc.Tech3gSearchDao#getKeyField()
	 */
	public String[] getKeyField() {
		return this.KEY_FIELD;
	}

	/* (非 Javadoc)
	 * @see tech3g.fk.dao.doc.Tech3gSearchDao#searchDocList(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<?> searchDocList(String doc_code, String code_name,
			String regi_user, String regi_date, String doc_type, String doc_ver, String doc_comment, int startNo, int endNo) {
		// 戻り値の初期化
		List<?> result = null;

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code", 			doc_code);
		dsp.addParam("doc_name", 			code_name);
		dsp.addParam("doc_type", 			doc_type);
		dsp.addParam("regi_user", 			regi_user);
		dsp.addParam("regi_date", 			regi_date);
		dsp.addParam("doc_ver", 			doc_ver);
		dsp.addParam("comment", 			doc_comment);
		dsp.addParam("startNo",				startNo);
		dsp.addParam("endNo", 				endNo);

		result = select(serach_sql, dsp);

		return result;
	}

	/* (非 Javadoc)
	 * @see tech3g.fk.dao.doc.Tech3gSearchDao#searchDocCnt(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int searchDocCnt(String doc_code, String code_name,
			String regi_user, String regi_date, String doc_type, String doc_ver, String doc_comment) {
		int serachCnt = 0;

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code", 			doc_code);
		dsp.addParam("doc_name", 			code_name);
		dsp.addParam("doc_type", 			doc_type);
		dsp.addParam("regi_user", 			regi_user);
		dsp.addParam("regi_date", 			regi_date);
		dsp.addParam("doc_ver", 			doc_ver);
		dsp.addParam("comment", 			doc_comment);

		serachCnt = DAOHelper.getCnt(select(search_cnt_sql, dsp), "CNT");

		return serachCnt;
	}

	public List<?> searchDocCodeLabelList(String doc_code_flg) {

		List<?> result = null;

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code_flg",	doc_code_flg);

		result = select(search_doc_code_sql, dsp);

		return result;
	}

	public Map searchDocDetail(String doc_code, String doc_index) {

        checkReqParam(new String[][] {
				 	{"文書コード", 	doc_code}
				,	{"文書 No", 		doc_index}
       });

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code",	doc_code);
		dsp.addParamDecimal("doc_index",	doc_index);

	       // -------検索を行う。
	    return DAOHelper.getMap(select(search_doc_detail_sql, dsp));
	}

	/**
	 *
	 */
	public int updateDocDetail(String user_id, Tech3gDocDetailDTO dto) {

        checkReqParam(new String[][] {
			 	{"文書コード", 	dto.getDoc_code()}
			,	{"文書 No", 		dto.getDoc_index()}
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

		return update(update_doc_detail_sql, dsp);
	}

	/**
	 *
	 */
	public int insertDocDetail(String user_id, Tech3gRegiDocDTO dto) {

        checkReqParam(new String[][] {
			 	{"文書コード", 	dto.getDoc_code()}
			,	{"文書 No", 		dto.getDoc_index()}
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

		return insert(insert_doc_detail_sql, dsp);
	}

	public int insertDocDetailBatch(String user_id, Tech3gRegiDocDTO dto) {

        checkReqParam(new String[][] {
			 	{"文書コード", 	dto.getDoc_code()}
			,	{"文書 Link", 		dto.getDoc_link()}
			,	{"文書 Ver", 		dto.getDoc_ver()}
        });

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code",			dto.getDoc_code());
		dsp.addParam("doc_name",			dto.getDoc_name());
		dsp.addParam("doc_type",			dto.getDoc_type());
		dsp.addParam("doc_link",			dto.getDoc_link());
		dsp.addParam("user_id",				user_id);
		dsp.addParam("doc_ver",				dto.getDoc_ver());
		dsp.addParam("comment",				dto.getComment());

		return insert(insert_doc_detail_batch_sql, dsp);
	}

	/**
	 *
	 */
	public int insertDocDetailHistory(String user_id, Tech3gDocDetailHistoryDTO dto) {

        checkReqParam(new String[][] {
			 	{"文書コード", 	dto.getDoc_code()}
			,	{"文書 No", 		dto.getDoc_index()}
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

		return insert(insert_doc_detail_history_sql, dsp);
	}

	/**
	 *
	 */
	public List<?> searchDocHistList(String doc_code, String doc_index, int startNo, int endNo) {
		// 戻り値の初期化
		List<?> result = null;

        checkReqParam(new String[][] {
			 	{"文書コード", 	doc_code}
			,	{"文書 No", 		doc_index}
        });

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code", 			doc_code);
		dsp.addParam("doc_index", 			doc_index);
		dsp.addParam("startNo",				startNo);
		dsp.addParam("endNo", 				endNo);

		result = select(search_doc_hist_detail_sql, dsp);

		return result;
	}

	public int searchDocHistCnt(String doc_code, String doc_index) {

		int serachCnt = 0;

        checkReqParam(new String[][] {
			 	{"文書コード", 	doc_code}
			,	{"文書 No", 		doc_index}
        });

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code", 			doc_code);
		dsp.addParam("doc_index", 			doc_index);

		serachCnt = DAOHelper.getCnt(select(search_doc_hist_cnt_sql, dsp), "CNT");

		return serachCnt;
	}

	public Map searchDocCodeLink(String doc_code) {
		checkReqParam(new String[][] {
						{ "文書コード", doc_code }
		});

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code", doc_code);

		// -------検索を行う。
		return DAOHelper.getMap(select(search_doc_code_link_sql, dsp));

	}

	public Map searchDocIndex(String doc_code) {
		checkReqParam(new String[][] { { "文書コード", doc_code } });

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code", doc_code);

		// -------検索を行う。
		return DAOHelper.getMap(select(search_doc_index_sql, dsp));
	}

	public List<ListOrderedMap> getSelectedDocInfoList(String doc_code, String index_field) {
		checkReqParam(new String[][] {
								{ "文書コード", doc_code }
							,	{ "index_field", index_field }
		});

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code", doc_code);
		dsp.addParam("index_field", index_field);
		// -------検索を行う。
		return select("doc.selectedDocInfoList", dsp);
	}

	public int insertDocCode(String user_id, Tech3gRegiDocDTO dto) {

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

		return insert(insert_doc_code_sql, dsp);
	}

	public int deleteDocCode(String doc_code) {

		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code",	doc_code);

		return delete(delete_doc_code_sql, dsp);
	}

	public int deleteDocInfo(String doc_code, String doc_index) {

		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code",	doc_code);
		dsp.addParam("doc_index",	doc_index);

		return delete(delete_doc_info_sql, dsp);
	}

	public int isTechDocCodeLink(String doc_code_link, String doc_root_path) {
		int serachCnt = 0;

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code_link",	doc_code_link);
		dsp.addParam("doc_root_path",	doc_root_path);

		serachCnt = DAOHelper.getCnt(select("doc.isTechDocCodeLink", dsp), "CNT");

		return serachCnt;
	}

	public String getMaxTechDocCode() {

		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code",	"");

        // -------検索を行う。
        return DAOHelper.getString(select("doc.maxTechDocCode", dsp));
	}

	public String getSelectDocCode(String doc_code_link) {

        checkReqParam(new String[][] {
			 	{"文書 Link", 	doc_code_link}
        });

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code_link",	doc_code_link);

        // -------検索を行う。
        return DAOHelper.getString(select("doc.selectDocCode", dsp));
	}

	public List<ListOrderedMap> getDocCodeList() {

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code",	"");

		return select("doc.searchDocCodeList", dsp);
	}

	public List<ListOrderedMap> searchDocInfoList(String doc_code, String code_name,
																			String regi_user, String regi_date, String doc_type,
																			String doc_ver, String doc_comment) {


		List<ListOrderedMap> result = null;

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("doc_code", 			doc_code);
		dsp.addParam("doc_name", 			code_name);
		dsp.addParam("doc_type", 			doc_type);
		dsp.addParam("regi_user", 			regi_user);
		dsp.addParam("regi_date", 			regi_date);
		dsp.addParam("doc_ver", 			doc_ver);
		dsp.addParam("comment", 			doc_comment);

		result = select("doc.searchDocInfoExcel", dsp);

		return result;
	}
}
