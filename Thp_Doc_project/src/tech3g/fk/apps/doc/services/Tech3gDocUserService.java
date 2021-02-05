package tech3g.fk.apps.doc.services;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.exceptions.CommonException;
import tech3g.common.util.CommonParameters;

public interface Tech3gDocUserService {

	abstract boolean checkUserId(String intPass, String dbPass) throws CommonException;

	abstract List<ListOrderedMap> getUserInfo(CommonParameters ps) throws CommonException;

	abstract String updPasswd(CommonParameters ps) throws CommonException;

	abstract String encode(String str) throws CommonException;

	abstract String decode(String str) throws CommonException;
}
