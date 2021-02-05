/**
 *
 */
package tech3g.fk.dao.doc;

import java.util.List;
import java.util.Map;

import tech3g.fk.dao.doc.dto.Tech3gUserManagerDTO;

/**
 * @author tech3g
 *
 */
public interface Tech3gUserDao {

	/** DAOの取得キー */
	public static String KEY_BEAN = "Tech3gUserDao";

	abstract String getObjectName();

	abstract String[] getKeyField();

	abstract Map searchUserInfo(String user_id);

	abstract List searchUserList(String user_id, int startNo, int endNo);

	abstract int searchUserCnt(String user_id);

	abstract int insertUserInfo(String user_id, Tech3gUserManagerDTO dto);

	abstract int updateUserInfo(String user_id, Tech3gUserManagerDTO dto);

	abstract int deleteUserInfo(String user_id);
}
