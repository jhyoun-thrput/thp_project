package tech3g.fk.dao.doc;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.fk.dao.doc.dto.Tech3gDocDetailDTO;
import tech3g.fk.dao.doc.dto.Tech3gDocDetailHistoryDTO;
import tech3g.fk.dao.doc.dto.Tech3gRegiDocDTO;

public interface Tech3gSearchDao {

	/** DAOの取得キー */
	public static String KEY_BEAN = "Tech3gSearchDao";

	abstract String getObjectName();

	abstract String[] getKeyField();


	//******************** Method Zone ***********************//

	abstract List<?> searchDocList(String doc_code, String code_name, String regi_user, String regi_date, String doc_type, String doc_ver, String doc_comment, int startNo, int endNo);

	abstract List<ListOrderedMap> searchDocInfoList(String doc_code, String code_name, String regi_user, String regi_date, String doc_type, String doc_ver, String doc_comment);

	abstract List<?> searchDocHistList(String doc_code, String doc_index, int startNo, int endNo);

	abstract Map searchDocDetail(String doc_code, String doc_index);

	abstract Map searchDocCodeLink(String doc_code);

	abstract int searchDocCnt(String doc_code, String code_name, String regi_user, String regi_date, String doc_type, String doc_ver, String doc_comment);

	abstract int searchDocHistCnt(String doc_code, String doc_index);

	abstract List<?> searchDocCodeLabelList(String doc_code_flg);

	abstract List<ListOrderedMap> getDocCodeList();

	abstract int updateDocDetail(String user_id, Tech3gDocDetailDTO dto);

	abstract int insertDocDetail(String user_id, Tech3gRegiDocDTO dto);

	abstract int insertDocDetailBatch(String user_id, Tech3gRegiDocDTO dto);

	abstract int insertDocCode(String user_id, Tech3gRegiDocDTO dto);

	abstract int deleteDocCode(String doc_code);

	abstract int deleteDocInfo(String doc_code, String doc_index);

	abstract int insertDocDetailHistory(String user_id, Tech3gDocDetailHistoryDTO dto);

	abstract Map searchDocIndex(String doc_code);

	abstract int isTechDocCodeLink(String doc_code_link, String doc_root_path);

	abstract String getMaxTechDocCode();

	abstract String getSelectDocCode(String doc_code_link);

	abstract List<ListOrderedMap> getSelectedDocInfoList(String doc_code, String index_field);
}
