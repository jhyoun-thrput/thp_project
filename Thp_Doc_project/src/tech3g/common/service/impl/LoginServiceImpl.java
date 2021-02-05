package tech3g.common.service.impl;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.exceptions.CommonException;
import tech3g.common.exceptions.RollbackCommonException;
import tech3g.common.service.LoginService;
import tech3g.common.util.Base64;
import tech3g.common.util.CommonParameters;

public class LoginServiceImpl extends BaseServiceImpl implements LoginService {

	@SuppressWarnings("unchecked")
	public boolean checkUserId(CommonParameters ps) throws CommonException {
		boolean result = false;
		List<ListOrderedMap> list = null;

		try {
			list = (List<ListOrderedMap>) queryService.find("co.doc.searchUserInfo", new Object[] { ps
					.getParameter("user_id") });
			if (list.size() > 0 && (ps.getParameter("passwd", "")).equals(decode((String) (list.get(0)).get("passwd"))))
				result = true;
		} catch (Exception e) {
			log.error("Excepton in COMNLoginCtrl checkUserId() : " + e.getMessage());
			throw new RollbackCommonException("ECOMN00001", e);
		}
		return result;
	}

	public String decode(String str) throws CommonException {
		try {
			if (str != null && !str.equals("")) {
				str = URLDecoder.decode(new String(Base64.decode(str)), "utf-8");
			}
		} catch (Exception e) {
			log.error("Exception in COMNUserCtrl decode() : " + e.getMessage());
			throw new RollbackCommonException("E99000000", e);
		}
		return str;
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

	@SuppressWarnings("unchecked")
	public List<ListOrderedMap> getUserInfo(CommonParameters ps) throws CommonException {
		List<ListOrderedMap> list = null;
		ListOrderedMap hm = null;

		try {
			if (checkUserId(ps)) {
				list = (List<ListOrderedMap>) queryService.find("co_mn_user_info", new Object[] { ps.getParameter(
						"user_id", "") });

				hm = list.get(0);
				hm.put("passwd", decode((String) hm.get("passwd")));
				list.set(0, hm);

			}
		} catch (Exception e) {
			log.error("Exception in COMNLoginCtrl getUserInfo() : " + e.getMessage());
			throw new RollbackCommonException("ECOMN00001", e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ListOrderedMap> getSrchHst(CommonParameters ps) throws CommonException {

		List<ListOrderedMap> list = null;

		try {
			list = (List<ListOrderedMap>) queryService.find("co_mn_search_history_select", new Object[] { ps
					.getParameter("user_id") });

		} catch (Exception e) {
			log.error("Exception in LoginService getSrchHst() : " + e.getMessage());
			throw new RollbackCommonException("ECOMN00001", e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public String getNewLoginTime(String user_id) throws CommonException {
		String result = "";

		try {

			List<ListOrderedMap> list = (List<ListOrderedMap>) queryService.find("co_mn_login_time_select",
					new Object[] { user_id });

			result = (String) list.get(0).get("new_login_dttm");

		} catch (Exception e) {
			log.error("Exception in LoginService getNewLoginTime() : " + e.getMessage());
			throw new RollbackCommonException("ECOMN00001", e);
		}
		return result;
	}

	public boolean regLastLoginTime(CommonParameters ps) throws CommonException {
		boolean result = false;

		try {
			if (queryService.create("co_mn_login_time_history", new Object[] { ps.getParameter("user_id"),
					ps.getParameter("session_id"), ps.getParameter("ip") }) > 0) {
				if (queryService.update("co_mn_login_time_update", new Object[] { ps.getParameter("ip"),
						ps.getParameter("user_id") }) > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			log.error("Exception in COMNLoginCtrl regLastLoginTime() : " + e.getMessage());
			throw new RollbackCommonException("ECOMN00001", e);
		}
		return result;
	}

	public String updPasswd(CommonParameters ps) throws CommonException {
		String result = "";

		try {
			if (checkUserId(ps)) {

				queryService.create("co_mn_user_history", new Object[] { ps.getParameter("user_id"),
						ps.getParameter("chg_user_id"), ps.getParameter("user_id") });

				if (queryService.update("co_mn_passwd_update", new Object[] { encode(ps.getParameter("pass1")),
						ps.getParameter("user_id") }) > 0) {
					result = "1111";
				} else {
					result = "2222";
				}
			} else {
				result = "9999";
			}

		} catch (Exception e) {
			log.error("Exception in COMNLoginCtrl updPasswd() : " + e.getMessage());
			throw new RollbackCommonException("ECOMN00001", e);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<ListOrderedMap> getLoginHstDeptCnt(CommonParameters ps) throws CommonException {

		List<ListOrderedMap> list = null;

		try {
			list = (List<ListOrderedMap>) queryService.find("co_mn_login_hst_dept_cnt", new Object[] { "dt="
					+ ps.getParameter("cond_dt") });

		} catch (Exception e) {
			throw new RollbackCommonException();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ListOrderedMap> getLoginHstDeptTime(CommonParameters ps) throws CommonException {

		List<ListOrderedMap> list = null;

		try {
			list = (List<ListOrderedMap>) queryService.find("co_mn_login_hst_dept_time", new Object[] { "dt="
					+ ps.getParameter("cond_dt") });

		} catch (Exception e) {
			throw new RollbackCommonException();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ListOrderedMap> getLoginHstUserCnt(CommonParameters ps) throws CommonException {

		List<ListOrderedMap> list = null;

		try {
			list = (List<ListOrderedMap>) queryService.find("co_mn_login_hst_user_cnt", new Object[] { "dt="
					+ ps.getParameter("cond_dt") });

		} catch (Exception e) {
			throw new RollbackCommonException();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ListOrderedMap> getLoginHstUserTime(CommonParameters ps) throws CommonException {

		List<ListOrderedMap> list = null;

		try {
			list = (List<ListOrderedMap>) queryService.find("co_mn_login_hst_user_time", new Object[] { "dt="
					+ ps.getParameter("cond_dt") });

		} catch (Exception e) {
			throw new RollbackCommonException();
		}
		return list;
	}

}
