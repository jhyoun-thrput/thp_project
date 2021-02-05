package tech3g.common.dao.impl;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.dao.COAJSrchDao;
import tech3g.common.db.BaseDao;
import tech3g.common.db.DynaSqlParam;
import tech3g.common.web.CommonParams;
/**
 * <pre>
 * COAJSrchDaoImplクラス。
 * DetailコードDBアクセスクラス。
 * </pre>
 *
 * @version 修正履歴
 *          <ul>
 *          <li>2009/06/25 : 新規作成 (revised by Jang.D.Y)</li>
 *          </ul>
 * @author Jang.D.Y
 */
public class COAJSrchDaoImpl extends BaseDao implements COAJSrchDao {

	/* (non-Javadoc)
	 * @see liveany.common.dao.ajax.COAJSrchDao#getInfoByAjax(liveany.common.web.CommonParams)
	 */
	public List<ListOrderedMap> srchInfoByAjax(CommonParams param) {
		DynaSqlParam dsp = new DynaSqlParam();

		//Keyを取得しクエリのバインディング変数をセッティングする
		String keys [] = param.getNames();
		for (int i = 0; i < keys.length
				&& (!keys[i].equals("SQLID") || !keys[i].equals("ISLIST")); i++) {
			dsp.addParam(keys[i], param.getParam(keys[i]));
		}
		//検索クエリを実行するメッソードを呼び出す
		return  select(param.getParam("SQLID"), dsp);
	}
}
