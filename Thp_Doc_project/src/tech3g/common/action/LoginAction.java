package tech3g.common.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tech3g.common.exceptions.RollbackCommonException;
import tech3g.common.service.LoginService;
import tech3g.common.service.SysInfoService;
import tech3g.common.util.CommonParameters;
import tech3g.common.web.BaseDispatchActionSupport;

public class LoginAction extends BaseDispatchActionSupport {


	/**
	 * Init Login prepare
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ActionForward prepare(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		return mapping.findForward("prepareLogin");
	}

	public ActionForward regLoginImgAcInfo(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		String exceptionCode = "E9904202";
		String strCELog = "RollbackCommonException in COMNLoginImgRegAction execute() : ";
		String strELog = "Exception in COMNLoginImgRegAction execute() : ";

		try {
			CommonParameters ps = new CommonParameters(req);
			SysInfoService sysInfoService = (SysInfoService) getWebApplicationContext().getBean("commonSysInfoService");
			sysInfoService.regLoginImg(ps);

		} catch (RollbackCommonException e) {
			log.error(strCELog + e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error(strELog + e.getMessage());
			throw new RollbackCommonException(exceptionCode, e);
		}
		return mapping.findForward("regLoginImgAcInfo");
	}

	public ActionForward getLoginImgSltAcInfo(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		String exceptionCode = "E9904201";
		String resultKey = "image_idx";
		String strCELog = "COERException in COMNLoginImgSltAction execute() : ";
		String strELog = "Exception in COMNLoginImgSltAction execute() : ";

		try {
			SysInfoService sysInfoService = (SysInfoService) getWebApplicationContext().getBean("commonSysInfoService");

			String result = sysInfoService.getLoginImgIdx();
			req.setAttribute(resultKey, result);

		} catch (RollbackCommonException e) {
			log.error(strCELog + e.getMessage());
			throw e;
		} catch (Exception ex) {
			log.error(strELog + ex.getMessage());
			throw new RollbackCommonException(exceptionCode, ex);
		}
		return mapping.findForward("getLoginImgSltAcInfo");
	}

	public ActionForward fwdLoginStat(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		String forwardTag = "fwdLoginStat";
		return mapping.findForward(forwardTag);
	}

	public ActionForward getLoginStat(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		String forwardTag = "getLoginStat";

		try {

			CommonParameters ps = new CommonParameters(req);
			LoginService service = (LoginService) getWebApplicationContext().getBean("commonLoginService");
			List<ListOrderedMap> list = null;

			String cond_info = ps.getParameter("cond_info");
			String cond_gubun = ps.getParameter("cond_gubun");

			if (cond_info != null && cond_gubun != null) {
				if ("1".equals(cond_info) && "1".equals(cond_gubun)) {
					list = service.getLoginHstDeptCnt(ps);
				} else if ("1".equals(cond_info) && "2".equals(cond_gubun)) {
					list = service.getLoginHstUserCnt(ps);
				} else if ("2".equals(cond_info) && "1".equals(cond_gubun)) {
					list = service.getLoginHstDeptTime(ps);
				} else if ("2".equals(cond_info) && "2".equals(cond_gubun)) {
					list = service.getLoginHstUserTime(ps);
				}
			}

			req.setAttribute("result", list);

		} catch (Exception e) {
			throw new RollbackCommonException();
		}

		return mapping.findForward(forwardTag);
	}
}
