/**
 *
 */
package tech3g.fk.apps.doc.services;

import java.util.Map;

import tech3g.common.exceptions.ApplicationException;
import tech3g.fk.apps.doc.actions.Tech3gDocDetailBean;
import tech3g.fk.apps.doc.actions.Tech3gDocSearchBean;

/**
 * @author tech3g
 *
 */
public interface Tech3gDocSearchService {

	abstract void initTechDocSearch(Tech3gDocSearchBean viewBean);

	abstract void initTechDocDetail(Tech3gDocDetailBean viewBean, Map<String, Object> exclusion, String pageNo);

	abstract void updateDocDetail(String userId, Tech3gDocDetailBean viewBean, Map<String, Object> exclusion) throws ApplicationException;

	abstract void searchTechDoc(Tech3gDocSearchBean viewBean, String pageNo) throws ApplicationException;

	abstract void searchTechDocInfoExcel(Tech3gDocSearchBean viewBean, String pageNo) throws ApplicationException;

	abstract void cnfirmtechDoc(Tech3gDocSearchBean viewBean) throws ApplicationException;

	abstract void deleteDocDetail(Tech3gDocDetailBean viewBean);

}
