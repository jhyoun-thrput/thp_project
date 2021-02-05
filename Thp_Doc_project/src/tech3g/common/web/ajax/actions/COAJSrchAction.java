package tech3g.common.web.ajax.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tech3g.common.exceptions.ApplicationException;
import tech3g.common.exceptions.SystemException;
import tech3g.common.util.StrUtil;
import tech3g.common.web.BaseAction;
import tech3g.common.web.CommonParams;
import tech3g.common.web.ajax.services.COAJSrchService;
/**
 * <pre>
 * COAJSrchActionクラス。
 *
 * Ajaxを実行するActionクラス。
 * 1.画面からパラメータを受ける。
 * 2.該当クエリを実行する。
 * 3.クエリ結果を文字列に返還する。
 * </pre>
 *
 */
public class COAJSrchAction extends BaseAction{
	/**
	 * Ajaxを利用してデータ照会を行う<br/>
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param param 入力パラメータ
	 * @param userMap ログインユーザー情報
	 * @return なし
	 * @throws Exception すべての例外
	 */
	public void getInfoByAjax(HttpServletRequest request, HttpServletResponse response,
			CommonParams param, Map userMap) throws Exception {
		validate(param.getParam("SQLID"));
		COAJSrchService service = (COAJSrchService) getBean("COAJSrchService");
		String responseText = service.getInfoByAjax(param);
		response.setContentType("text/xml; charset=UTF-8");
		response.getWriter().print(responseText);
	}

	/**
	 * SQLIDがない場合はシステムエラーに処理する<br/>
	 * @param sqlid
	 * @throws ApplicationException
	 */
	private static void validate(String sqlid) throws ApplicationException {

		if(StrUtil.isEmpty(sqlid)){
			throw new SystemException("err.COO61");
		}

	}
}
