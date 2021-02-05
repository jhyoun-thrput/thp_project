package tech3g.common.action;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.CommonException;
import tech3g.common.exceptions.RollbackCommonException;
import tech3g.common.util.CommonParameters;
import tech3g.common.web.BaseAction;

/**
 * <pre>
 * InitMenuAction ログイン管理のActionClass
 * </pre>
 */
public class InitMenuAction extends BaseAction {

	/**
	 * <pre>
	 * ログイン管理
	 * </pre>
	 *
	 * @param ActionMapping
	 *            mapping, ActionForm form, HttpServletRequest request,
	 *            HttpServletResponse response
	 * @return　ActionForward
	 * @throws CommonException
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String forwardTag = "success";
		String exceptionCode = "E9901006";

		try {
			HttpSession hSession = request.getSession();
			Map userInfo = (Map) hSession.getAttribute("USERINFO");			// ユーザー情報

			CommonParameters ps = new CommonParameters();

			if (userInfo == null) {
				return mapping.findForward("fail");
			} else {
				ps.setParameter("userid", (String) userInfo.get("user_id"));
			}

			String super_user = (String) userInfo.get("user_admin"); // ユーザーがSuperユーザーであるかどうか。

			hSession.setAttribute("user_admin", super_user);

		} catch (Exception e) {
			throw new RollbackCommonException(messageSource, exceptionCode);
		}
		return mapping.findForward(forwardTag);
	}

	/**
	 * Log out
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		session.invalidate();

		return mapping.findForward(CommonConsts.FW_SUCCESS);
	}

	public ActionForward drug(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return mapping.findForward(CommonConsts.FW_SUCCESS);
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward(CommonConsts.FW_SUCCESS);
	}

	/**
	 * <pre>
	 * ログイン管理
	 * </pre>
	 * @param List
	 *            <Map> menuList, List<Map> pidArr
	 * @return　ActionForward
	 */
	@SuppressWarnings("unchecked")
	private Map initMenuSet(List<Map> menuList, List<Map> pidArr) throws Exception {

		Map rslt = new ListOrderedMap();

		for (int i = 0; i < pidArr.size(); i++) {
			String pid = (String) (pidArr.get(i)).get("menu_pid");
			if (pid != null) {

				List<Map> tmpArr = new ArrayList();
				for (int j = 0; j < menuList.size(); j++) {
					Map tmpHsh = menuList.get(j);
					if (pid.equals(tmpHsh.get("menu_pid"))) {
						tmpArr.add(tmpHsh);
					}
				}
				rslt.put(pid, tmpArr);
			}
		}
		return rslt;
	}
}