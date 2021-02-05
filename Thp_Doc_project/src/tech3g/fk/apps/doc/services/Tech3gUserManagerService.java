/**
 *
 */
package tech3g.fk.apps.doc.services;

import tech3g.common.exceptions.ApplicationException;
import tech3g.fk.apps.doc.actions.Tech3gUserManagerBean;
import tech3g.fk.apps.doc.actions.Tech3gUserRegiBean;

/**
 * @author tech3g
 *
 */
public interface Tech3gUserManagerService {

	abstract void initUserManager(Tech3gUserManagerBean viewBean, String pageNo) throws Exception;

	abstract void initUserRegi(Tech3gUserRegiBean viewBean);

	abstract void initPassUpr(String user_id, Tech3gUserRegiBean viewBean);

	abstract void searchUserManager(Tech3gUserManagerBean viewBean, String pageNo) throws Exception;

	abstract void insertUserManager(String user_id, Tech3gUserRegiBean viewBean) throws ApplicationException;

	abstract void updateUserManager(String user_id, Tech3gUserRegiBean viewBean) throws Exception;

	abstract void deleteUserManager(String user_id, Tech3gUserManagerBean viewBean) throws ApplicationException;

	abstract void checkUserRegi(String user_id, Tech3gUserRegiBean viewBean) throws ApplicationException;

	abstract void userConfirm(Tech3gUserRegiBean viewBean);

}
