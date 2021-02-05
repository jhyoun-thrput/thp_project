package tech3g.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tech3g.common.exceptions.RollbackCommonException;
import tech3g.common.service.SysInfoService;
import tech3g.common.util.CommonParameters;
import tech3g.common.web.BaseDispatchActionSupport;

/**
 * <pre>
 * LoginImgAction ログイン画面管理のActionClass
 * </pre>
 */
public class LoginImgAction extends BaseDispatchActionSupport {

	/**
	 * <pre>
	 * ログイン画面管理の登録機能。
	 * </pre>
	 * @param ActionMapping
	 *            mapping, ActionForm form, HttpServletRequest req,
	 *            HttpServletResponse res
	 * @return　ActionForward
	 */
	public ActionForward regLoginImgAcInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		String forwardTag = "regLoginImgAcInfo";
		String exceptionCode = "E9904402";

		try {
			CommonParameters ps = new CommonParameters(req);
			ListOrderedMap userInfo = (ListOrderedMap) req.getSession()
					.getAttribute("USERINFO");
			if (userInfo != null) {
				ps.setParameter("atny_Cd", (String) userInfo.get("atny_cd"));
			}
			SysInfoService sysInfoService = (SysInfoService) getWebApplicationContext()
					.getBean("commonSysInfoService");
			sysInfoService.regLoginImg(ps);

		} catch (RollbackCommonException e) {
			throw e;
		} catch (Exception e) {
			throw new RollbackCommonException(messageSource, exceptionCode);
		}
		return mapping.findForward(forwardTag);
	}

	/**
	 * <pre>
	 * ログイン画面管理の取り消し機能。
	 * </pre>
	 * @param ActionMapping
	 *            mapping, ActionForm form, HttpServletRequest req,
	 *            HttpServletResponse res
	 * @return　ActionForward
	 */
	public ActionForward getLoginImgSltAcInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {

		String forwardTag = "getLoginImgSltAcInfo";
		String exceptionCode = "E9904401";

		try {
			SysInfoService sysInfoService = (SysInfoService) getWebApplicationContext()
					.getBean("commonSysInfoService");

			String result = sysInfoService.getLoginImgIdx();
			req.setAttribute("image_idx", result);

		} catch (RollbackCommonException e) {
			throw e;
		} catch (Exception ex) {
			throw new RollbackCommonException(messageSource, exceptionCode);
		}
		return mapping.findForward(forwardTag);
	}
}
