package tech3g.common.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tech3g.common.exceptions.CommonException;
import tech3g.common.exceptions.RollbackCommonException;
import tech3g.common.util.CommonParameters;
import tech3g.common.web.BaseAction;
import tech3g.common.web.session.SessionInfoDTO;
import tech3g.common.web.session.SessionInfoThreadManager;
import tech3g.fk.apps.doc.services.Tech3gDocUserService;

/**
 * <pre>
 * LoginCheckAction ログイン管理のActionClass
 * </pre>
 */
public class LoginCheckAction extends BaseAction {


	public static final String KEY_BEAN_SERV = "Tech3gDocUserService";

	/**
	 * <pre>
	 * ログイン管理の。認証有無
	 * </pre>
	 * @param ActionMapping
	 *            mapping, ActionForm form, HttpServletRequest req,
	 *            HttpServletResponse res
	 * @return　ActionForward
	 * @throws CommonException
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		String forwardTag = "fail";
		String exceptionCode = "E9904403";
		List<ListOrderedMap> list = null;


		try {
			HttpSession session = req.getSession();

			CommonParameters param = new CommonParameters(req);
			//LoginService loginService = (LoginService) getWebApplicationContext().getBean("commonLoginService");
			Tech3gDocUserService loginService = (Tech3gDocUserService) getWebApplicationContext().getBean(KEY_BEAN_SERV);

			param.setParameter("session_id", req.getSession().getId());
			list = loginService.getUserInfo(param);

			if (list != null && list.size() == 1) {
				forwardTag = "success";
				ListOrderedMap hm = list.get(0);
				String user_id = String.valueOf(hm.get("user_id"));
				String pass = String.valueOf(hm.get("passwd"));

				if (user_id.trim().equals(pass.trim())) {
					forwardTag = "passUpt";
					giveParam(req, "login_msg", "same");
					giveParam(req, "login_errMsg", "*User ID と User Password が同一です。\n Password を変更してください。");
				}
				hm.put("new_hndg_ip", param.getParameter("ip", ""));
				session.setAttribute("USERINFO", list.get(0));

				SessionInfoDTO sessionInfo = SessionInfoThreadManager
						.getSessionInfo();
				sessionInfo.setUserId(String.valueOf(list.get(0).get("user_id")));
				sessionInfo.setIpAddr(String.valueOf(list.get(0).get("user_ip")));
				sessionInfo.setUser_admin(String.valueOf(list.get(0).get("user_admin")));
			}

		} catch (RollbackCommonException e) {
			throw e;
		} catch (Exception e) {
			throw new RollbackCommonException(messageSource, exceptionCode);
		}
		return mapping.findForward(forwardTag);
	}
}