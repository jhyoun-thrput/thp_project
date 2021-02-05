package tech3g.common.dao;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.web.CommonParams;

/**
 * <pre>
 * COAJSrchDaoクラス。
 * </pre>
 */
public interface COAJSrchDao {
	/**
	 * クエリのバインディング変数をセッティングしクエリを実行する<br/>
	 */
	public List<ListOrderedMap> srchInfoByAjax(CommonParams param) ;
}
