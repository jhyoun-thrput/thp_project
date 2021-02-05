package tech3g.fk.apps.doc.services.impl;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.biz.BaseService;
import tech3g.common.exceptions.CommonException;
import tech3g.common.exceptions.RollbackCommonException;
import tech3g.common.util.Base64;
import tech3g.common.util.CommonParameters;
import tech3g.fk.apps.doc.Tech3gBizLogic;
import tech3g.fk.apps.doc.services.Tech3gDocUserService;
import tech3g.fk.dao.doc.Tech3gUserDao;

public class Tech3gDocUserServiceImpl extends BaseService implements Tech3gDocUserService {

	public boolean checkUserId(String intPass, String dbPass) throws CommonException {

		boolean result = false;

		if (intPass.equals(decode(dbPass))) {
			result = true;
		}
		return result;
	}

	public List<ListOrderedMap> getUserInfo(CommonParameters ps) throws CommonException {

		List<ListOrderedMap> list = new ArrayList<ListOrderedMap>();
		ListOrderedMap map = null;
		String inptPass = null;

		Tech3gUserDao dao = (Tech3gUserDao) getBean(Tech3gUserDao.KEY_BEAN);

		try {

			inptPass = ps.getParameter("passwd", "");
			map = Tech3gBizLogic.getTechUserInfo(dao, ps.getParameter("user_id", ""));

			if (checkUserId(inptPass, String.valueOf(map.get("user_pass")))) {

				map.put("passwd", decode(String.valueOf(map.get("user_pass"))));
				list.add(0, map);
			}
		}
		catch (Exception e) {
			log.error("Exception in COMNLoginCtrl getUserInfo() : " + e.getMessage());
			throw new RollbackCommonException("ECOMN00001", e);
		}
		return list;
	}

	public String updPasswd(CommonParameters ps) throws CommonException {
		return null;
	}

	public String encode(String str) throws CommonException {
		try {
			if (str != null && !str.equals("")) {
				str = URLEncoder.encode(str, "utf-8");
				str = Base64.encode(str.getBytes());
			}
		} catch (Exception e) {
			log.error("Exception in COMNUserCtrl encode() : " + e.getMessage());
			throw new RollbackCommonException("E99000000", e);
		}
		return str;
	}

	public String decode(String str) throws CommonException {
		try {

			if (str != null && !str.equals("")) {
				str = URLDecoder.decode(new String(Base64.decode(str)), "utf-8");
			}
		}
		catch (Exception e) {
			log.error("Exception in COMNUserCtrl decode() : " + e.getMessage());
			throw new RollbackCommonException("E99000000", e);
		}
		return str;
	}

}
