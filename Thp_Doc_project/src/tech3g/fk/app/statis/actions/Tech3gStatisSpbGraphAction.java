/**
 *
 */
package tech3g.fk.app.statis.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtilities;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.ApplicationException;
import tech3g.common.web.BaseAction;
import tech3g.common.web.CommonParams;
import tech3g.fk.app.statis.services.Tech3gStatisGraphService;

/**
 * @author tech3g
 *
 */
public class Tech3gStatisSpbGraphAction extends BaseAction {

	public static final String KEY_SESS_VB = "Tech3gStatisSpbGraphBean";

	public static final String KEY_BEAN_SERV = "Tech3gStatisGraphService";

	/** バリデーション判断用の処理名 */
	private static enum VALIDATE_CHECK {

		check_init
	}

	// --------------------------------------------------- インスタンスメソッド

	public String initSpbGraph(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, KEY_SESS_VB);
		Tech3gStatisSpbGraphBean viewBean = new Tech3gStatisSpbGraphBean();

		// カスタムviewBean設定
		mergeInput2ViewBean(params, viewBean);

		// -------------- ビジネスロジックの実行
		Tech3gStatisGraphService service = (Tech3gStatisGraphService) getBean(KEY_BEAN_SERV);
		service.initSpbGraph(viewBean);

		ChartUtilities.writeChartAsPNG(response.getOutputStream(), viewBean.getChat(), 400, 400);
		// -------------- ビジネスロジックの実行
		setSessionAttr(request, KEY_SESS_VB, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}


	/**
	 *
	 * @param request
	 * @param response
	 * @param params
	 * @param userMap
	 * @return
	 * @throws Exception
	 */
	public String redrawSpbGraph(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gStatisSpbGraphBean viewBean = (Tech3gStatisSpbGraphBean) getSessionAttr(request, KEY_SESS_VB, Tech3gStatisSpbGraphBean.class);
		mergeInput2ViewBean(params, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}



	/**
	 * 相関バリデーション、単体バリデーション処理<br/> 相関バリデーションやStruts Validationでチェックできないバリデーションを行う。
	 *
	 * @param viewBean 画面表示用のBean
	 * @param check バリデーション対象処理
	 */
	private void validate(Tech3gStatisSpbGraphBean viewBean, VALIDATE_CHECK check) throws ApplicationException {

		// ApplicationExceptionインスタンスの生成
		ApplicationException _rtnError = new ApplicationException();

		if (check.equals(VALIDATE_CHECK.check_init)) {

		}

		if (!_rtnError.isEmpty()) {
			throw _rtnError;
		}
	}

	private void getParams(HttpServletRequest request, Tech3gStatisSpbGraphBean viewBean) {
	}

	/**
	 * ・遷移先画面に引き渡すパラメタの設定を行う。
	 * <br/>
	 * @param request HttpServletRequest
	 * @param viewBean 画面表示用のBean
	 */
	private void setParamas(HttpServletRequest request, Tech3gStatisSpbGraphBean viewBean) {
	}
}
