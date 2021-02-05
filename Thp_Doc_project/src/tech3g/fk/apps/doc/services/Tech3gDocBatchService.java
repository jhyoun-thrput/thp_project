/**
 *
 */
package tech3g.fk.apps.doc.services;

import java.util.Map;

import tech3g.common.exceptions.ApplicationException;
import tech3g.fk.apps.doc.actions.Tech3gDocBatchBean;
import tech3g.fk.apps.doc.actions.Tech3gDocBatchRegiBean;

/**
 * @author tech3g
 *
 */
public interface Tech3gDocBatchService {

	public void initBtach(Tech3gDocBatchBean viewBean);

	public void initExeBatch(Tech3gDocBatchBean viewBean, String pageNo);

	public void showBatchDetailInfo(String btId, String btNo, Tech3gDocBatchBean viewBean);

	public void exeBatch(String userId, Tech3gDocBatchBean viewBean) throws Exception;

	public Map downloadLogFile(String btId, String btNo) throws ApplicationException;

	public void initBatchRegi(Tech3gDocBatchRegiBean viewBean);

	public void insertBatchRegi(String user_id, Tech3gDocBatchRegiBean viewBean) throws ApplicationException;

	public void updateBatchRegi(String user_id, Tech3gDocBatchRegiBean viewBean);

	public void batchConfirm(Tech3gDocBatchRegiBean viewBean);

	public void deleteBatchMaster(Tech3gDocBatchBean viewBean);
}
