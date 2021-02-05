package tech3g.common.service;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.exceptions.CommonException;
import tech3g.common.util.CommonParameters;

/**
 * <pre>
 * Ajax(JSON)のサービスインターフェース
 * </pre>
 */
public interface JsonService {

	/**
	 * <pre>
	 * ajaxで取得したデータをList形式で返還する。
	 * ”SQLID”というパラメータが必須でSQLのIDを指定する。
	 * </pre>
	 *
	 * @param param
	 *            パラメータ
	 * @return　結果List
	 * @throws CommonException
	 */
	public List<ListOrderedMap> getCommonInfo(CommonParameters param)
			throws CommonException;
}
