/**
 *
 */
package tech3g.fk.apps.doc.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.biz.BaseService;
import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.ApplicationException;
import tech3g.common.util.BeanUtil;
import tech3g.common.util.DateUtil;
import tech3g.common.util.PageInfoVO;
import tech3g.common.util.PagingUtil;
import tech3g.common.util.StrUtil;
import tech3g.fk.apps.doc.Tech3gBizLogic;
import tech3g.fk.apps.doc.TechDocUtil;
import tech3g.fk.apps.doc.actions.Tech3gUserManagerBean;
import tech3g.fk.apps.doc.actions.Tech3gUserRegiBean;
import tech3g.fk.apps.doc.services.Tech3gUserManagerService;
import tech3g.fk.dao.doc.Tech3gUserDao;
import tech3g.fk.dao.doc.dto.Tech3gUserManagerDTO;

/**
 * @author tech3g
 *
 */
public class Tech3gUserManagerServiceImpl  extends BaseService implements Tech3gUserManagerService {

	public void initUserManager(Tech3gUserManagerBean viewBean, String pageNo) throws Exception {
		this.searchUserManager(viewBean, pageNo);
	}

	public void searchUserManager(Tech3gUserManagerBean viewBean, String pageNo) throws Exception {

		List<ListOrderedMap> result = new ArrayList<ListOrderedMap>();

		Tech3gUserDao dao = (Tech3gUserDao) getBean(Tech3gUserDao.KEY_BEAN);

		int cnt = Tech3gBizLogic.getUserListCnt(dao, viewBean.getUser_id());

		// ------- ページング計算
		PageInfoVO pageInfo = PagingUtil.getPageInfo(cnt, 10, pageNo);

		result = Tech3gBizLogic.searchUserList(dao, viewBean.getUser_id(), (pageInfo.getStartNum() - 1), pageInfo.getListRowCnt());

		viewBean.setUserList(result);

		viewBean.setUserListPageInfo(pageInfo);

		viewBean.setResult_cnt(String.valueOf(cnt));
	}

	public void deleteUserManager(String user_id, Tech3gUserManagerBean viewBean) throws ApplicationException {

		Tech3gUserDao dao = (Tech3gUserDao) getBean(Tech3gUserDao.KEY_BEAN);

		if (!StrUtil.isEmpty(viewBean.getSelected_user_id())) {
			int delCnt = Tech3gBizLogic.deleteUser(dao, viewBean.getSelected_user_id());

			if (delCnt > 0) {
				log.debug("User Delete Count : " + delCnt);
			}
		}
		else {
			throw new ApplicationException("User Delete 処理が失敗されました。");
		}
	}


	//****************** User Regi Zone **************************************

	public void initUserRegi(Tech3gUserRegiBean viewBean) {

		Tech3gUserDao dao = null;

		if (!StrUtil.isEmpty(viewBean.getSelected_user_id())) {
			dao = (Tech3gUserDao) getBean(Tech3gUserDao.KEY_BEAN);

			ListOrderedMap map = Tech3gBizLogic.getTechUserInfo(dao, viewBean.getSelected_user_id());
			if (!map.isEmpty()) {
				BeanUtil.populateIgnoreEmpty(viewBean, map);
				viewBean.setUser_ip(String.valueOf(map.get("hndg_ip")));
			}
		}
		else {
			viewBean.setRegi_date(DateUtil.getCurrentTimeStr2());
		}
	}

	public void insertUserManager(String user_id, Tech3gUserRegiBean viewBean) throws ApplicationException {

		Tech3gUserDao dao = (Tech3gUserDao) getBean(Tech3gUserDao.KEY_BEAN);
		Tech3gUserManagerDTO dto = new Tech3gUserManagerDTO();

		int cnt = Tech3gBizLogic.getUserListCnt(dao, viewBean.getUser_id());

		if (cnt > 0) {
			viewBean.setErr_massge("既に、登録済みの[ USER ID ]です。");
			throw new ApplicationException("* USER_ID : [ " + viewBean.getUser_id() + " ] は、既に登録されております。\n 再度、入力してください。");
		}

		BeanUtil.copyProperties(dto, viewBean);
		if (!StrUtil.isEmpty(viewBean.getAdmin_val())) {
			dto.setUser_admin(viewBean.getAdmin_val());
		}
		else {
			dto.setUser_admin("0");
		}
		int insrtCnt = Tech3gBizLogic.insertUser(dao, user_id, dto);

		if (insrtCnt > 0) {
			log.debug("User Insert Count : " + insrtCnt);
		}
	}

	public void updateUserManager(String user_id, Tech3gUserRegiBean viewBean) throws Exception {

		Tech3gUserDao dao = (Tech3gUserDao) getBean(Tech3gUserDao.KEY_BEAN);
		Tech3gUserManagerDTO dto = new Tech3gUserManagerDTO();

		BeanUtil.copyProperties(dto, viewBean);

		ListOrderedMap map = Tech3gBizLogic.getTechUserInfo(dao, viewBean.getUser_id());

		if (!map.isEmpty()) {
			String current_pass = TechDocUtil.decode(String.valueOf(map.get("user_pass")));
			String adminFlg = String.valueOf(map.get("user_admin"));

			if (!StrUtil.isEmpty(viewBean.getNew_pass())) {
				if (!viewBean.getCurrent_pass().trim().equals(current_pass.trim())) {
					viewBean.setErr_massge("*入力されたCurrent User Passが現状のPASSと異なります。");
					throw new ApplicationException();
				}
				dto.setUser_pass(viewBean.getNew_pass());
			}
			else {
				dto.setUser_pass(null);
			}

			if (StrUtil.isEmpty(viewBean.getAdmin_val())) {
				viewBean.setAdmin_val(adminFlg);
			}
			if (!viewBean.getAdmin_val().equals(adminFlg)) {
				dto.setUser_admin(viewBean.getAdmin_val());
			}
			else {
				dto.setUser_admin(null);
			}
		}
		else {
			viewBean.setErr_massge("*処理中、エラーは発生しました。");
			throw new ApplicationException();
		}



		int uptCnt = Tech3gBizLogic.updateUser(dao, user_id, dto);

		if (uptCnt > 0) {
			log.debug("User Info Update Count :" + uptCnt);
		}
	}

	public void checkUserRegi(String user_id, Tech3gUserRegiBean viewBean)
			throws ApplicationException {

		Tech3gUserDao dao = (Tech3gUserDao) getBean(Tech3gUserDao.KEY_BEAN);

		if (!StrUtil.isEmpty(user_id)) {
			int cnt = Tech3gBizLogic.getUserListCnt(dao, user_id);

			if (cnt > 0) {
				viewBean.setErr_massge("既に、登録済みの[ USER ID ]です。");
				throw new ApplicationException("* USER_ID : [ " + user_id + " ] は、既に登録されております。\n 再度、入力してください。");
			}
			else {
				viewBean.setErr_massge("該当 USER ID [ " + user_id + " ] は、使用可能です。");
			}
			viewBean.setCheck_flg(CommonConsts.FLG_ON);
		}
	}

	public void userConfirm(Tech3gUserRegiBean viewBean) {

		Tech3gUserDao dao = (Tech3gUserDao) getBean(Tech3gUserDao.KEY_BEAN);
		ListOrderedMap map = Tech3gBizLogic.getTechUserInfo(dao, viewBean.getUser_id());

		if (!map.isEmpty()) {
			BeanUtil.populateIgnoreEmpty(viewBean, map);
		}
	}

	public void initPassUpr(String user_id, Tech3gUserRegiBean viewBean) {

		Tech3gUserDao dao = (Tech3gUserDao) getBean(Tech3gUserDao.KEY_BEAN);
		ListOrderedMap map = Tech3gBizLogic.getTechUserInfo(dao, user_id);

		if (!map.isEmpty()) {
			BeanUtil.populateIgnoreEmpty(viewBean, map);
			viewBean.setErr_massge("User ID と Password が同一ですので、Password を変更してください。");
		}
	}
}
