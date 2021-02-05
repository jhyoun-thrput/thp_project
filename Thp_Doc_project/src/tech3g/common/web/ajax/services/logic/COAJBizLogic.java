package tech3g.common.web.ajax.services.logic;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.dao.COAJSrchDao;
import tech3g.common.util.StrUtil;
import tech3g.common.web.CommonParams;
import tech3g.common.web.ajax.json.JSONObject;
/**
 * <pre>
 * COAJBizLogicクラス。
 *
 * Ajaxに対するビジネスクラス。
 * 1.DAO、パラメータを受ける
 * 2.DAOクラスを呼び出してクエリを実行する
 * 3.クエリ結果を処理する。
 * </pre>
 */
public class COAJBizLogic {
	/**
	 * クエリ結果を受け、JsonObjectに格納し返還する<br/>
	 * @param COAJSrchTmpDao coajSrchTmpDao
	 * @param CommonParams param
	 * @return 実行結果を文字列に返還する
	 * @throws Exception すべての例外
	 */
	public static String getInfoByAjax(COAJSrchDao coajSrchTmpDao, CommonParams param) throws Exception{

		//DAOメッソードを呼び出す
		List<ListOrderedMap> resList = coajSrchTmpDao.srchInfoByAjax(param);

		//クエリ結果が一件もしくは二件以上の場合を区別して処理する
		String responseText = "";
		int size = resList.size();
		boolean isList = "true".equals(param.getParam("ISLIST", ""));

		if (isList && size > 0) {
			JSONObject jsonObject = new JSONObject();
			int i = 0;
			for (ListOrderedMap map : resList) {
				String sTmp = String.valueOf(i);
				sTmp = StrUtil.fillZero(sTmp, 3);
				jsonObject.put(sTmp, new JSONObject(map));
				i++;
			}
			responseText = jsonObject.toString(0);
		} else if (size > 0) {
			JSONObject jsonObject = new JSONObject(resList.get(0));
			responseText = jsonObject.toString(0);
		} else {
			responseText = "ZERO";
		}
		return responseText;
	}
}
