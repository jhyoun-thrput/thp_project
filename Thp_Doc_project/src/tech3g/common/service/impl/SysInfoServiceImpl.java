package tech3g.common.service.impl;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.exceptions.RollbackCommonException;
import tech3g.common.service.SysInfoService;
import tech3g.common.util.CommonParameters;

/**
 * @version 1.0 2004-08-11
 * @author comm
 * @since 1.0
 */
public class SysInfoServiceImpl extends BaseServiceImpl implements
		SysInfoService {

	@SuppressWarnings("unchecked")
	public List<ListOrderedMap> getSysInfoList() throws RollbackCommonException {

		String queryId = "co_mn_sys_info_select_list";
		String errorCd = "E9904201";
		String strELog = "Exception in COMNSysInfoCtrl getSysInfoList() : ";
		Object[] args = null;

		try {
			// List<ListOrderedMap> dummyList = new List<ListOrderedMap>();
			// for (int i = 0; i < 10 ;i++){
			// HashMap dummyItem = new HashMap();
			// dummyItem.put("id","0000"+i);
			// dummyItem.put("name","testNm"+i);
			// dummyItem.put("value","testVal"+i);
			// dummyList.add(dummyItem);
			// }
			// return dummyList;
			args = new Object[0];

			List<ListOrderedMap> list = (List<ListOrderedMap>) queryService
					.find(queryId, args);

			return list;

		} catch (Exception e) {
			log.error(strELog + e.getMessage());
			throw new RollbackCommonException(errorCd, e);
		}
	}

	@SuppressWarnings("unchecked")
	public ListOrderedMap getSysInfoById(CommonParameters ps)
			throws RollbackCommonException {

		String queryId = "co_mn_sys_info_select";
		String errorCd = "E9904201";
		String strELog = "Exception in COMNSysInfoCtrl getSysInfoById() : ";

		int idx = ps.getParameterAsInteger("idx", 0);
		String[] key = ps.getParameterValues("item_id");

		Object[] args = null;

		try {
			// System.out.println(" sys_info_id : " + key[idx]);
			// ListOrderedMap dummyItem = new ListOrderedMap();
			// dummyItem.put("id",key[idx]);
			// dummyItem.put("name","testNm1");
			// dummyItem.put("value","testVal1");
			// return dummyItem;
			args = new Object[] { key[idx] };
			List<ListOrderedMap> list = (List<ListOrderedMap>) queryService
					.find(queryId, args);
			ListOrderedMap result = list.get(0);
			return result;
		} catch (Exception e) {
			log.error(strELog + e.getMessage());
			throw new RollbackCommonException(errorCd, e);
		}
	}

	public int regSysInfo(CommonParameters ps) throws Exception {

		String queryId = "co_mn_sys_info_insert";
		String errorCd = "E9904202";
		String strELog = "Exception in COMNSysInfoCtrl regBoardInfo() : ";

		Object[] args = null;

		String atny_cd = ps.getParameter("atny_cd");
		String sysInfoId = ps.getParameter("sys_info_id");
		String sysInfoName = ps.getParameter("sys_info_name");
		String sysInfoValue = ps.getParameter("sys_info_value");

		try {
			List<ListOrderedMap> list = (List<ListOrderedMap>) queryService
			.find("co_mn_sys_info_select", new Object[]{ sysInfoId });

			int result = 0;

			if(list.size() > 0) {
				result = 9;
			} else {
				args = new Object[] { atny_cd, sysInfoId, sysInfoName, sysInfoValue };
				result = queryService.create(queryId, args);
			}

			return result;

		} catch (Exception e) {
			log.error(strELog + e.getMessage());
			throw new RollbackCommonException(errorCd, e);
		}
	}

	public void updSysInfo(CommonParameters ps) throws Exception {
		String queryId = "co_mn_sys_info_update";
		String errorCd = "E9904203";
		String strELog = "Exception in COMNSysInfoCtrl updSysInfo() : ";

		Object[] args = null;

		String atny_cd = ps.getParameter("atny_cd");
		String sysInfoId = ps.getParameter("sys_info_id");
		String sysInfoName = ps.getParameter("sys_info_name");
		String sysInfoValue = ps.getParameter("sys_info_value");

		try {
			args = new Object[] { sysInfoName, sysInfoValue, sysInfoId, atny_cd };
			queryService.update(queryId, args);

		} catch (Exception e) {
			log.error(strELog + e.getMessage());
			throw new RollbackCommonException(errorCd, e);
		}
	}

	public void delSysInfo(CommonParameters ps) throws Exception {

		String queryId = "co_mn_sys_info_delete";
		String errorCd = "E9904204";
		String strELog = "Exception in COMNSysInfoCtrl delSysInfo() : ";

		String[] idx = ps.getParameterValues("idx");
		String[] key = ps.getParameterValues("item_id");

		Object[] args = null;

		try {
			for (int i = 0; i < idx.length; i++) {
				args = new Object[] { key[Integer.parseInt(idx[i])] };
				queryService.remove(queryId, args);
			}
		} catch (Exception e) {
			log.error(strELog + e.getMessage());
			throw new RollbackCommonException(errorCd, e);
		}
	}

	@SuppressWarnings("unchecked")
	public String getLoginImgIdx() throws RollbackCommonException {

		String queryId = "co_mn_sys_info_select";
		String errorCd = "E9904201";
		String strELog = "Exception in COMNSysInfoCtrl getLoginImgIdx() : ";

		Object[] args = null;

		try {
			// System.out.println(" get Login Image Index");
			// return "2";
			args = new Object[] { "9001" };
			List<ListOrderedMap> list = (List<ListOrderedMap>) queryService
					.find(queryId, args);
			ListOrderedMap map = list.get(0);
			String result = (String) map.get("sys_var_val");

			return result;

		} catch (Exception e) {
			log.error(strELog + e.getMessage());
			throw new RollbackCommonException(errorCd, e);
		}
	}

	@SuppressWarnings("unchecked")
	public String getSkinImgIdx() throws RollbackCommonException {

		String queryId = "co_mn_sys_info_select";
		String errorCd = "E9904201";
		String strELog = "Exception in COMNSysInfoCtrl getSkinImgIdx() : ";

		Object[] args = null;

		try {
			args = new Object[] { "9002" };
			List<ListOrderedMap> list = (List<ListOrderedMap>) queryService
					.find(queryId, args);
			ListOrderedMap map = list.get(0);
			String result = (String) map.get("sys_var_val");

			return result;
		} catch (Exception e) {
			log.error(strELog + e.getMessage());
			throw new RollbackCommonException(errorCd, e);
		}
	}

	@SuppressWarnings("unchecked")
	public String getSkinImgNm() throws RollbackCommonException {

		String queryId = "co_mn_sys_info_select";
		String errorCd = "E9904201";
		String strELog = "Exception in COMNSysInfoCtrl getSkinImgNm() : ";

		Object[] args = null;

		try {
			args = new Object[] { "9002" };
			List<ListOrderedMap> list = (List<ListOrderedMap>) queryService
					.find(queryId, args);
			ListOrderedMap map = list.get(0);
			String result = (String) map.get("sys_var_val");

			return result;
		} catch (Exception e) {
			log.error(strELog + e.getMessage());
			throw new RollbackCommonException(errorCd, e);
		}
	}

	public void regLoginImg(CommonParameters ps) throws Exception {

		String queryId = "co_mn_sys_info_update";
		String errorCd = "E9904202";
		String strELog = "Exception in COMNSysInfoCtrl regBoardInfo() : ";

		Object[] args = null;

		String sysInfoId = "9001";
		String sysInfoName = "loginImg";
		String sysInfoValue = ps.getParameter("idx");
		String atny_cd = ps.getParameter("atny_cd");

		try {
			args = new Object[] { sysInfoName, sysInfoValue, sysInfoId, atny_cd };
			queryService.update(queryId, args);
		} catch (Exception e) {
			log.error(strELog + e.getMessage());
			throw new RollbackCommonException(errorCd, e);
		}
	}

	public void regSkinImg(CommonParameters ps) throws Exception {

		String queryId = "co_mn_sys_info_update";
		String errorCd = "E9904202";
		String strELog = "Exception in COMNSysInfoCtrl regSkinImg() : ";

		Object[] args = null;

		String sysInfoId = "9002";
		String sysInfoName = "skinImg";
		String sysInfoValue = ps.getParameter("idx");
		String atny_cd = ps.getParameter("atny_cd");

		try {
			args = new Object[] { sysInfoName, sysInfoValue, sysInfoId, atny_cd };
			queryService.update(queryId, args);
		} catch (Exception e) {
			log.error(strELog + e.getMessage());
			throw new RollbackCommonException(errorCd, e);
		}
	}
}