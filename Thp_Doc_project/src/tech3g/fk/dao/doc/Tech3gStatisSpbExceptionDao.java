/**
 *
 */
package tech3g.fk.dao.doc;

import tech3g.fk.dao.doc.dto.Tech3gSpbExcetionDTO;

/**
 * @author tech3g
 *
 */
public interface Tech3gStatisSpbExceptionDao {

	/** DAOの取得キー */
	public static String KEY_BEAN = "Tech3gStatisSpbExceptionDao";

	abstract String getObjectName();

	abstract String[] getKeyField();

	//******************** Method Zone ***********************//

	abstract int insertSpbException(String user_id, Tech3gSpbExcetionDTO dto);

}
