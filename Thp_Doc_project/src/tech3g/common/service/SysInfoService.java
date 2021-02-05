package tech3g.common.service;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.exceptions.CommonException;
import tech3g.common.util.CommonParameters;

public interface SysInfoService {

	public List<ListOrderedMap> getSysInfoList() throws CommonException;

	public ListOrderedMap getSysInfoById(CommonParameters ps)
			throws CommonException;

	public int regSysInfo(CommonParameters ps) throws Exception;

	public void updSysInfo(CommonParameters ps) throws Exception;

	public void delSysInfo(CommonParameters ps) throws Exception;

	public String getLoginImgIdx() throws CommonException;

	public String getSkinImgIdx() throws CommonException;

	public String getSkinImgNm() throws CommonException;

	public void regLoginImg(CommonParameters ps) throws Exception;

	public void regSkinImg(CommonParameters ps) throws Exception;
}