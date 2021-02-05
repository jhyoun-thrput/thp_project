package tech3g.common.service;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.exceptions.CommonException;
import tech3g.common.util.CommonParameters;

public interface LoginService {

	public boolean checkUserId(CommonParameters ps) throws CommonException;

	public boolean regLastLoginTime(CommonParameters ps) throws CommonException;

	public List<ListOrderedMap> getUserInfo(CommonParameters ps) throws CommonException;

	public List<ListOrderedMap> getSrchHst(CommonParameters ps) throws CommonException;

	public String updPasswd(CommonParameters ps) throws CommonException;

	public String encode(String str) throws CommonException;

	public String decode(String str) throws CommonException;

	public List<ListOrderedMap> getLoginHstDeptCnt(CommonParameters ps) throws CommonException;

	public List<ListOrderedMap> getLoginHstDeptTime(CommonParameters ps) throws CommonException;

	public List<ListOrderedMap> getLoginHstUserCnt(CommonParameters ps) throws CommonException;

	public List<ListOrderedMap> getLoginHstUserTime(CommonParameters ps) throws CommonException;
}
