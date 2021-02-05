/**  */
package tech3g.fk.apps.doc;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.jfree.util.Log;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.ApplicationException;
import tech3g.common.exceptions.CommonException;
import tech3g.common.exceptions.SystemException;
import tech3g.fk.dao.doc.Tech3gBatchDao;
import tech3g.fk.dao.doc.Tech3gSearchDao;
import tech3g.fk.dao.doc.Tech3gStatisSpbExceptionDao;
import tech3g.fk.dao.doc.Tech3gUserDao;
import tech3g.fk.dao.doc.dto.Tech3gBatchDTO;
import tech3g.fk.dao.doc.dto.Tech3gBatchRegiDTO;
import tech3g.fk.dao.doc.dto.Tech3gDocDetailDTO;
import tech3g.fk.dao.doc.dto.Tech3gDocDetailHistoryDTO;
import tech3g.fk.dao.doc.dto.Tech3gRegiDocDTO;
import tech3g.fk.dao.doc.dto.Tech3gSpbExcetionDTO;
import tech3g.fk.dao.doc.dto.Tech3gUserManagerDTO;

/**
 * <pre>
 * Tech3gBizLogicクラス。s
 * </pre>
 *
 * @author Yun.j.h
 */
public class Tech3gBizLogic {

	public static List<ListOrderedMap> getTech3gDocInfo(Tech3gSearchDao dao,
													String doc_code, String code_name, String regi_user, String regi_date, String doc_type, String doc_ver, String doc_comment, int startNo, int endNo) {

		List<ListOrderedMap> result = (List<ListOrderedMap>) dao.searchDocList(doc_code, code_name, regi_user, regi_date, doc_type, doc_ver, doc_comment, startNo, endNo);

		return result;
	}

	public static List<ListOrderedMap> getTechDocInfoData(Tech3gSearchDao dao,
																												String doc_code, String code_name, String regi_user,
																												String regi_date, String doc_type, String doc_ver,
																												String doc_comment) {

		List<ListOrderedMap> result = (List<ListOrderedMap>) dao.searchDocInfoList(doc_code, code_name, regi_user, regi_date, doc_type, doc_ver, doc_comment);

		return result;
	}

	public static List<ListOrderedMap> getTech3gDocHistInfo(Tech3gSearchDao dao, String doc_code, String doc_index, int startNo, int endNo) {

		List<ListOrderedMap> result = (List<ListOrderedMap>) dao.searchDocHistList(doc_code, doc_index, startNo, endNo);

		return result;
	}

	public static int getTech3gDocInfoCnt(Tech3gSearchDao dao, String doc_code, String code_name, String regi_user, String regi_date,
													String doc_type, String doc_ver, String doc_comment) {

		int resultCnt = 0;

		resultCnt = dao.searchDocCnt(doc_code, code_name, regi_user, regi_date, doc_type, doc_ver, doc_comment);

		return resultCnt;
	}

	public static int getTech3gDocHistInfoCnt(Tech3gSearchDao dao, String doc_code, String doc_index) {

		int resultCnt = 0;

		resultCnt = dao.searchDocHistCnt(doc_code, doc_index);

		return resultCnt;
	}

	public static List<ListOrderedMap> getDocCodeList(Tech3gSearchDao dao) {
		List<ListOrderedMap> rtnList = dao.getDocCodeList();
		return rtnList;
	}

	public static List<LabelValueBean> getTechDocCodeInfo(Tech3gSearchDao dao, String doc_code_flg) {

		List<LabelValueBean> result = TechDocUtil.convertLabelList((List<ListOrderedMap>) dao.searchDocCodeLabelList(doc_code_flg));

		return result;
	}

	public static List<LabelValueBean> getUserIdList(Tech3gUserDao dao) {

		List<LabelValueBean> result = TechDocUtil.getUserIdList((List<ListOrderedMap>) dao.searchUserList("", 0, 1000));

		return result;
	}

	public static ListOrderedMap getDocDetailInfo(Tech3gSearchDao dao, String doc_code, String doc_index) {

		ListOrderedMap result = (ListOrderedMap) dao.searchDocDetail(doc_code, doc_index);

		return result;
	}

	public static List<ListOrderedMap> getSelectedDocInfoList(Tech3gSearchDao dao, String doc_code, String index_field) {

		List<ListOrderedMap> result = dao.getSelectedDocInfoList(doc_code, index_field);

		return result;
	}

	public static ListOrderedMap getDocInfoIndex(Tech3gSearchDao dao, String doc_code) {

		ListOrderedMap result = (ListOrderedMap) dao.searchDocIndex(doc_code);

		return result;
	}

	public static String getCurrentDocInfoIndex(Tech3gSearchDao dao, String doc_code) {

		String rtn_index = "";

		ListOrderedMap result = (ListOrderedMap) dao.searchDocIndex(doc_code);

		if (!result.isEmpty()) {
			rtn_index = String.valueOf(result.get("doc_index"));
		}
		return rtn_index;
	}

	public static String getDocCodeLink(Tech3gSearchDao dao, String doc_code) {

		String docCodeLink = null;
		ListOrderedMap result = (ListOrderedMap) dao.searchDocCodeLink(doc_code);

		if (!result.isEmpty()) {
			docCodeLink = (String) result.get("doc_code_link");
		}
		return docCodeLink;
	}

	public static String getDocCodeRootPath(Tech3gBatchDao dao, String doc_root_path) {

		String docRootPath = null;
		ListOrderedMap result = (ListOrderedMap) dao.searchDocCodeRootPath(doc_root_path);

		if (!result.isEmpty()) {
			docRootPath = (String) result.get("doc_root_path");
		}
		return docRootPath;
	}

	public static int updateDocDetail(Tech3gSearchDao dao, String user_id, Tech3gDocDetailDTO dto) {

		int uptCnt = 0;

		uptCnt = dao.updateDocDetail(user_id, dto);

    	//更新チェックを行う。
    	if (uptCnt <= 0) {
    		 throw new SystemException(CommonConsts.KEY_MSG_FAILURE, new String[] {"文書更新"});
    	}
		return uptCnt;
	}

	public static int insertDocDetail(Tech3gSearchDao dao, String user_id, Tech3gRegiDocDTO dto) {

		int insertCnt = 0;

		insertCnt = dao.insertDocDetail(user_id, dto);

    	//登録チェックを行う。
    	if (insertCnt <= 0) {
    		 throw new SystemException(CommonConsts.KEY_MSG_FAILURE, new String[] {"文書登録"});
    	}
		return insertCnt;
	}

	public static int insertDocDetailBatch(Tech3gSearchDao dao, String user_id, Tech3gRegiDocDTO dto) {

		int insertCnt = 0;

		insertCnt = dao.insertDocDetailBatch(user_id, dto);

    	//登録チェックを行う。
    	if (insertCnt <= 0) {
    		 throw new SystemException(CommonConsts.KEY_MSG_FAILURE, new String[] {"文書登録"});
    	}
		return insertCnt;
	}

	public static int insertDocCode(Tech3gSearchDao dao, String user_id, Tech3gRegiDocDTO dto) {

		int insertCnt = 0;

		insertCnt = dao.insertDocCode(user_id, dto);

    	//登録チェックを行う。
    	if (insertCnt <= 0) {
    		 throw new SystemException(CommonConsts.KEY_MSG_FAILURE, new String[] {"文書登録"});
    	}

		return insertCnt;
	}

	public static int insertDocDetailHistory(Tech3gSearchDao dao, String user_id, Tech3gDocDetailHistoryDTO dto) {

		int insertCnt = 0;

		insertCnt = dao.insertDocDetailHistory(user_id, dto);

		return insertCnt;
	}

	public static int deleteDocCode(Tech3gSearchDao dao, String doc_code) {

		int rtnCnt = 0;

		rtnCnt = dao.deleteDocCode(doc_code);

		if (rtnCnt > 0) {
			Log.debug("Doc Code Delete Count : " + rtnCnt);
		}
		return rtnCnt;
	}

	public static int deleteDocInfo(Tech3gSearchDao dao, String doc_code, String doc_index) {

		int rtnCnt = 0;

		rtnCnt = dao.deleteDocInfo(doc_code, doc_index);

		if (rtnCnt > 0) {
			Log.debug("Doc Info Delete Count : " + rtnCnt);
		}
		return rtnCnt;
	}

	public static boolean isTechDocCodeLink(Tech3gSearchDao dao, String doc_code_link, String doc_root_path) {

		boolean result = false;

		int rtnCnt = dao.isTechDocCodeLink(doc_code_link, doc_root_path);

		if (rtnCnt > 0) {
			result = true;
		}
		return result;
	}

	public static String getMaxTechDocCode(Tech3gSearchDao dao) {
		return (dao.getMaxTechDocCode());
	}

	public static String getSelectDocCode(Tech3gSearchDao dao, String doc_code_link) {
		return (dao.getSelectDocCode(doc_code_link));
	}


	//***************************** USER INFO ZONE *****************************

	public static ListOrderedMap getTechUserInfo(Tech3gUserDao dao, String user_id) {

		ListOrderedMap result = (ListOrderedMap) dao.searchUserInfo(user_id);

		return result;
	}


	public static List<ListOrderedMap> searchUserList(Tech3gUserDao dao, String user_id, int startNo, int endNo) throws CommonException {

		List<ListOrderedMap> userList = dao.searchUserList(user_id, startNo, endNo);

		for (int index = 0; index < userList.size(); index++) {
			ListOrderedMap map = userList.get(index);

			String pass = TechDocUtil.decode(String.valueOf(map.get("user_pass")));
			map.put("user_pass", pass);
		}

		return userList;
	}

	public static int getUserListCnt(Tech3gUserDao dao, String user_id) {

		int cnt = dao.searchUserCnt(user_id);

		return cnt;
	}

	public static int insertUser(Tech3gUserDao dao, String user_id, Tech3gUserManagerDTO dto) {

		int insertCnt = 0;

		insertCnt = dao.insertUserInfo(user_id, dto);

    	//登録チェックを行う。
    	if (insertCnt <= 0) {
    		 throw new SystemException(CommonConsts.KEY_MSG_FAILURE, new String[] {"ユーザ登録"});
    	}
		return insertCnt;
	}

	public static int updateUser(Tech3gUserDao dao, String user_id, Tech3gUserManagerDTO dto) {

		int uptCnt = 0;

		uptCnt = dao.updateUserInfo(user_id, dto);

    	//登録チェックを行う。
    	if (uptCnt <= 0) {
    		 throw new SystemException(CommonConsts.KEY_MSG_FAILURE, new String[] {"ユーザ更新"});
    	}
		return uptCnt;
	}

	public static int deleteUser(Tech3gUserDao dao, String user_id) {
		int delCnt = 0;

		delCnt = dao.deleteUserInfo(user_id);
		if (delCnt > 0) {
			Log.debug("Btach Master Delete Count : " + delCnt);
		}
		return delCnt;
	}

	//***************************** Batch INFO ZONE *****************************

    public static Map getBtachInfo (Tech3gBatchDao dao, String btId, String btNo) {

		// -------- 検索を行う
		Map info = dao.selectBatchInfo(btId,  btNo);

		return info==null?new ListOrderedMap():info;
    }


	public static String getCurrentDocIndex(Tech3gBatchDao dao, String doc_code, String doc_link) throws ApplicationException {

		String result = "";
		ListOrderedMap map = (ListOrderedMap) dao.techDocInfoMap(doc_code, doc_link);

		if (!map.isEmpty()) {
			if (map.size() > 3) {
				throw new ApplicationException("該当データは、Uniqueではありません。");
			}
			result = String.valueOf(map.get("doc_index"));
		}
		return result;
	}

	public static List searchBatchInfoList(Tech3gBatchDao dao, String btId, String btNo, String startDt, String endDt,
											int start, int end) {

		// ------ 検索を行う
		List list = dao.selectBatchInfoList(btId, btNo, startDt, endDt, start, end);

		return list;
	}

	public static List searchMasterBatchList(Tech3gBatchDao dao, String btId) {

		// ------ 検索を行う
		List list = dao.selectMasterBatchList(btId);

		return list;
	}

	public static ListOrderedMap getMasterBatchInfo(Tech3gBatchDao dao, String btId) {

		ListOrderedMap result = (ListOrderedMap) dao.selectMasterBatchInfo(btId);

		return result;
	}

	public static int searchBatchInfoCnt (Tech3gBatchDao dao,
												String btId, String btNo, String startDt, String endDt) {
		return dao.selectBatchInfoCount(btId, btNo, startDt, endDt);
	}

	public static int registBatchInfo(Tech3gBatchDao dao, String userId, Tech3gBatchDTO dto) {

		int insCnt = dao.insertBatchInfo(userId, dto);
		// ----- 登録チェック
		if (insCnt <= 0) {
			throw new SystemException(CommonConsts.KEY_MSG_FAILURE, new String[] {"バッチスケジュール登録"});
		}
		return insCnt;
	}

	public static int updateBatchInfo(Tech3gBatchDao dao, String userId, Tech3gBatchDTO dto) {

		int uptCnt = dao.updateBatchInfo(userId, dto);
		// ----- 更新チェック
		if (uptCnt <= 0) {
			throw new SystemException(CommonConsts.KEY_MSG_FAILURE, new String[] {"バッチスケジュール更新"});
		}
		return uptCnt;
	}

	public static String getTechBatchId(Tech3gBatchDao dao, String btId) {

		String result = dao.getTechBtId(btId);

		return result;
	}

	public static String getMaxBtNo(Tech3gBatchDao dao, String btId) {

		String result = dao.getMaxBtNo(btId);

		return result;
	}

	public static int mergeDocCode(Tech3gBatchDao dao, String user_id, Tech3gRegiDocDTO dto) {

		int insertCnt = 0;

		insertCnt = dao.mergeDocCode(user_id, dto);

    	//登録チェックを行う。
    	if (insertCnt <= 0) {
    		 throw new SystemException(CommonConsts.KEY_MSG_FAILURE, new String[] {"文書登録"});
    	}

		return insertCnt;
	}

	public static int mergeDocInfo(Tech3gBatchDao dao, String user_id, Tech3gRegiDocDTO dto) {

		int insertCnt = 0;

		insertCnt = dao.mergeDocInfo(user_id, dto);

    	//登録チェックを行う。
    	if (insertCnt <= 0) {
    		 throw new SystemException(CommonConsts.KEY_MSG_FAILURE, new String[] {"文書登録"});
    	}
		return insertCnt;
	}

	public static int insertBatchMaster(Tech3gBatchDao dao, String user_id, Tech3gBatchRegiDTO dto) {

		int insertCnt = 0;

		insertCnt = dao.insertBatchMaster(user_id, dto);

    	//登録チェックを行う。
    	if (insertCnt <= 0) {
    		 throw new SystemException(CommonConsts.KEY_MSG_FAILURE, new String[] {"バッチ登録"});
    	}
		return insertCnt;
	}

	public static int updateBatchMaster(Tech3gBatchDao dao, String user_id, Tech3gBatchRegiDTO dto) {

		int uptCnt = 0;

		uptCnt = dao.updateBatchMaster(user_id, dto);

    	//登録チェックを行う。
    	if (uptCnt <= 0) {
    		 throw new SystemException(CommonConsts.KEY_MSG_FAILURE, new String[] {"バッチ更新"});
    	}
		return uptCnt;
	}

	public static int deleteBatchMaster(Tech3gBatchDao dao, String bt_id) {
		int delCnt = 0;

		delCnt = dao.deleteBatchMaster(bt_id);
		if (delCnt > 0) {
			Log.debug("Btach Master Delete Count : " + delCnt);
		}
		return delCnt;
	}

	//***************************** Tech Statis ZONE *****************************

	public static int insertSpbException(Tech3gStatisSpbExceptionDao dao, String user_id, Tech3gSpbExcetionDTO dto) {

		int insrtCnt =dao.insertSpbException(user_id, dto);

		if (insrtCnt > 0) {
			Log.debug("Statis Spb Exception Insrted Count : " + insrtCnt);
		}
		return insrtCnt;
	}
}
