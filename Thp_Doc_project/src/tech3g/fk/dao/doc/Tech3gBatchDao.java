package tech3g.fk.dao.doc;

import java.util.List;
import java.util.Map;

import tech3g.fk.dao.doc.dto.Tech3gBatchDTO;
import tech3g.fk.dao.doc.dto.Tech3gBatchRegiDTO;
import tech3g.fk.dao.doc.dto.Tech3gRegiDocDTO;

public interface Tech3gBatchDao {

    /** DAOの取得キー */
    public static String KEY_BEAN = "Tech3gBatchDao";

    abstract Map selectBatchInfo (String btId, String bt_no);

    abstract Map selectBatchInfoDesc();

    abstract int insertBatchInfo (String userId, Tech3gBatchDTO dto);

    abstract int updateBatchInfo (String userId, Tech3gBatchDTO dto);

    abstract List selectBatchInfoList (String btId, String bt_no, String begin_date, String end_date, int startNum, int endNum);

    abstract List selectMasterBatchList (String btId);

    abstract Map selectMasterBatchInfo(String btId);

    abstract int selectBatchInfoCount (String btId, String bt_no, String begin_date, String end_date);

    abstract String selectBatchMaxBtNo(String btId);

    abstract int mergeDocCode(String user_id, Tech3gRegiDocDTO dto);

    abstract int mergeDocInfo(String user_id, Tech3gRegiDocDTO dto);

    abstract int insertBatchMaster(String user_id, Tech3gBatchRegiDTO dto);

    abstract int updateBatchMaster(String user_id, Tech3gBatchRegiDTO dto);

    abstract String getTechBtId(String btId);

    abstract String getMaxBtNo(String btId);

	abstract Map techDocInfoMap(String doc_code, String doc_link);

	abstract Map searchDocCodeRootPath(String doc_root_path);

	abstract int deleteBatchMaster(String bt_id);

}
