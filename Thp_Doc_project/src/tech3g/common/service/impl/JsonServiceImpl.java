package tech3g.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.exceptions.CommonException;
import tech3g.common.exceptions.RollbackCommonException;
import tech3g.common.service.JsonService;
import tech3g.common.util.CommonParameters;

/**
 * <pre>
 * AJAXの技術を利用、データベースからデータを取得し、JSON方式に結果を返還する。
 * </pre>
 */
public class JsonServiceImpl extends BaseServiceImpl implements JsonService {

	/** ajax呼出により、SQLのID名 */
	private static final String SQLID = "SQLID";

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * anyframe.government.common.services.JsonService#getCommonInfo(anyframe
	 * .government.common.utils.CommonParameters)
	 */
	@SuppressWarnings( { "unchecked" })
	public List<ListOrderedMap> getCommonInfo(CommonParameters param)
			throws CommonException {
		List<ListOrderedMap> infoMapList = new ArrayList<ListOrderedMap>();
		try {
			String queryId = param.getParameter(SQLID);
			Object[] queryParam = queryParams(param);
			infoMapList = (List<ListOrderedMap>) queryService.find(queryId,
					queryParam);
		} catch (Exception e) {
			throw new RollbackCommonException(messageSource, "COMN0005");
		}
		return infoMapList;
	}

	/**
	 * <pre>
	 * CommonParametersからパラメータを取得し、Dynamic刑のSQLの利用のためのObject[]に
	 * パラメータをセットする。
	 *
	 * </pre>
	 * @param param
	 *            パラメータ
	 * @return Dynamic刑のSQLパラメータ
	 */
	public static Object[] queryParams(CommonParameters param) {

		String[] names = param.getNames();
		int paramSize = names.length;

		if (paramSize < 1) {
			return null;
		}

		Object[] objs = new Object[paramSize];

		for (int i = 0; i < paramSize; i++) {
			objs[i] = getKeyValStr(names[i].toUpperCase(), param.getParameter(
					names[i], ""));
		}

		return objs;
	}

	/**
	 * <pre>
	 * key=value(String)形式でリターンする。
	 * </pre>
	 * @param key
	 *            対象キー
	 * @param value
	 *            値
	 * @return 調整値
	 */
	public static String getKeyValStr(String key, String value) {
		return new StringBuffer(key).append("=").append(value).toString();
	}
}