package tech3g.common.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tech3g.common.exceptions.RollbackCommonException;
import tech3g.common.service.JsonService;
import tech3g.common.util.CommonParameters;
import tech3g.common.util.StrUtil;
import tech3g.common.web.BaseDispatchActionSupport;
import tech3g.common.web.ajax.json.JSONObject;

/**
 * <pre>
 * Ajax（JSON）の処理Action。
 * Jsp画面からjavascriptを通して呼出、DBから結果を取得してmap形式で結果を返還する。
 * 結果の件数が0件の場合は&quot;ERROR&quot;という結果文字列を返還、画面から判断してエラーメッセージを表示する。
 * </pre>
 */
public class JsonAction extends BaseDispatchActionSupport {

	/** ユーザー情報AttributeのID */
	private static final String UserInfo = "USERINFO";

	/**
	 * <pre>
	 * Bからデータを取得する。
	 * ”SQLID”というパラメータは必須でする。
	 * </pre>
	 * @param ActionMapping
	 *            mapping, ActionForm form, HttpServletRequest request,
	 *            HttpServletResponse response
	 * @throws Exception
	 */
	public ActionForward getMasterInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			CommonParameters param = getUserInfo(request, new CommonParameters(
					request));

			// SQL(queryId)が存在しない場合、エラー
			if (StrUtil.isEmpty(param.getParameter("SQLID"))) {
				throw new RollbackCommonException(messageSource, "COMN0005");
			}

			JsonService service = (JsonService) getWebApplicationContext()
					.getBean("commonJson");

			// 指定したSQL文を実行、List形式の結果を取得する。
			List<ListOrderedMap> list = service.getCommonInfo(param);

			String responseText = "";
			int size = list.size();
			boolean isList = "true".equals(param.getParameter("ISLIST", ""));

			// リスト形式の場合
			if (isList && size > 0) {
				JSONObject jsonObject = new JSONObject();
				int i = 0;
				for (ListOrderedMap map : list) {
					String sTmp = String.valueOf(i);
					for (int iLen = sTmp.length(); iLen < 3; iLen++) {
						sTmp = "0" + sTmp;
					}
					jsonObject.put(sTmp, new JSONObject(map));
					i++;
				}
				responseText = jsonObject.toString(0);
			}
			// リスト形式ではなく1件以上の場合
			// (但し、最上位のデータだけ返還)
			else if (size > 0) {
				JSONObject jsonObject = new JSONObject(list.get(0));
				responseText = jsonObject.toString(0);
			}
			// 結果が0件の場合は"ZERO"という文字列を返還
			else {
				responseText = "ZERO";
			}

			response.setContentType("text/xml; charset=UTF-8");
			response.getWriter().print(responseText);

		} catch (Exception e) {
			log.error(e);
			throw new RollbackCommonException(messageSource, "COMN0005");
		}

		return null;
	}

	/**
	 * <pre>
	 * ユーザー情報のMapからユーザー情報を、Actionのパラメータオブジェクトに入れ込む。
	 * </pre>
	 * @param HttpServletRequest
	 *            request, CommonParameters param
	 * @return パラメータ
	 * @throws Exception
	 */
	private CommonParameters getUserInfo(HttpServletRequest request,
			CommonParameters param) throws Exception {
		ListOrderedMap usermap = getUserInfoMap(request);
		if (usermap != null) {
			MapIterator mapIter = usermap.mapIterator();
			while (mapIter.hasNext()) {
				mapIter.next();
				param.setParameter((String) mapIter.getKey(), (String) mapIter
						.getValue());
			}
		}
		return param;
	}

	/**
	 * <pre>
	 * sessionからユーザー情報を取得する。
	 * </pre>
	 * @param HttpServletRequest
	 *            request
	 * @return　ユーザー情報のMap
	 * @throws Exception
	 */
	private ListOrderedMap getUserInfoMap(HttpServletRequest request)
			throws Exception {
		return (ListOrderedMap) request.getSession().getAttribute(UserInfo);
	}
}