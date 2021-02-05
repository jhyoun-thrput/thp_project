/**
 *
 */
package tech3g.fk.apps.doc.actions;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.ApplicationException;
import tech3g.common.util.StrUtil;
import tech3g.common.web.BaseAction;
import tech3g.common.web.CommonParams;
import tech3g.common.web.ViewBean;
import tech3g.fk.apps.doc.services.Tech3gDocBatchService;

/**
 * @author tech3g
 *
 */
public class Tech3gDocBatchAction extends BaseAction {

	//--------------------------------------------------- 定数
	/** バッチ実行のViewBeanセッションキー */
	public static final String KEY_SESS_VB = "Tech3gDocBatchBean";

	/** バッチ実行のサービスBean取得キー */
	public static final String KEY_BEAN_SERV = "Tech3gDocBatchService";

	/** 最大設定できるｱｯﾌﾟﾛｰﾄﾞファイル数 */
	public static final int MAX_UPLOAD_FILE = 10;

	/** Defaultｱｯﾌﾟﾛｰﾄﾞファイル数 */
	public static final int DEF_UPLOAD_FILE = 0;

	/** バリデーション判断用の処理名 */
	private static enum VALIDATE_CHECK {
		/** 初期表示 */
		initBatch,
		/** 検索 */
		searchBatch,
		/** 実行 */
		exeBatch
	}

	//--------------------------------------------------- インスタンスメソッド

	public String initSubBatch(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gDocBatchBean viewBean = (Tech3gDocBatchBean) getSessionAttr(
				request, KEY_SESS_VB, Tech3gDocBatchBean.class);

		mergeInput2ViewBean(params, viewBean);

		// -------------- ビジネスロジックの実行
		String pageNo = params.getParam(CommonConsts.KEY_PARAM_PAGENO); // 表示するページ数
		Tech3gDocBatchService service = (Tech3gDocBatchService) getBean(KEY_BEAN_SERV);
		service.initExeBatch(viewBean, pageNo);

		validate(viewBean, null,VALIDATE_CHECK.initBatch);

		// -------------- ViewBeanをセッションに保持
		setSessionAttr(request, KEY_SESS_VB, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	public String initMasterBatch(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map userMap) throws Exception {

		Tech3gDocBatchBean viewBean = new Tech3gDocBatchBean();

		// -------------- ビジネスロジックの実行
		Tech3gDocBatchService service = (Tech3gDocBatchService) getBean(KEY_BEAN_SERV);
		service.initBtach(viewBean);

		//validate(viewBean, null, null, VALIDATE_CHECK.initBatch);

		// -------------- ViewBeanをセッションに保持
		setSessionAttr(request, KEY_SESS_VB, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	/**
	 * 詳細照会<br/>
	 * <br/>
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param params 入力パラメータ
	 * @param userMap ログインユーザー情報
	 * @return Forward 遷移先キーワード
	 * @throws Exception すべての例外
	 */
	public String showDetail(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gDocBatchBean viewBean = (Tech3gDocBatchBean) getSessionAttr(
				request, KEY_SESS_VB, Tech3gDocBatchBean.class);

		mergeInput2ViewBean(params, viewBean);									// 入力値のマージ

		// -------------- ビジネスロジックの実行
		Tech3gDocBatchService service = (Tech3gDocBatchService) getBean(KEY_BEAN_SERV);
		service.showBatchDetailInfo(viewBean.getSelected_bt_id(), viewBean.getSelected_bt_no(), viewBean);

		// -------------- 検索結果をセッションに保持
		setSessionAttr(request, KEY_SESS_VB, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	/**
	 * 実行<br/>
	 * <br/>
	 *
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param params 入力パラメータ
	 * @param userMap ログインユーザー情報
	 * @return Forward 遷移先キーワード
	 * @throws Exception すべての例外
	 */
	public String exeBatch(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gDocBatchBean viewBean = (Tech3gDocBatchBean) getSessionAttr(
											request, KEY_SESS_VB, Tech3gDocBatchBean.class);

		mergeInput2ViewBean(params, viewBean);									// 入力値のマージ
		String userId = (String) userMap.get(CommonConsts.USER_INF_USER_ID);	// ユーザーID

		// -------------- バリデーション
		validate(viewBean, null, VALIDATE_CHECK.exeBatch);

		// -------------- ビジネスロジックの実行
		Tech3gDocBatchService service = (Tech3gDocBatchService) getBean(KEY_BEAN_SERV);
		service.exeBatch(userId, viewBean);

		// 処理監視ONにする
		viewBean.setBtStatsRefresh(CommonConsts.CHECKBOX_ON);

		// -------------- ViewBeanをセッションに保持
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
	public String deleteBatchMaster(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gDocBatchBean viewBean = (Tech3gDocBatchBean) getSessionAttr(request, KEY_SESS_VB, Tech3gDocBatchBean.class);
		mergeInput2ViewBean(params, viewBean);

		// -------------- ビジネスロジックの実行
		Tech3gDocBatchService service = (Tech3gDocBatchService) getBean(KEY_BEAN_SERV);
		service.deleteBatchMaster(viewBean);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, KEY_SESS_VB);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, "正常に削除されました。");
		return CommonConsts.FW_SUCCESS;
	}

	/**
	 * 再描画<br/>
	 * 画面の入力値をマージし、バッチ実行を再描画する。
	 *
	 * @version 修正履歴
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param params 入力パラメータ
	 * @param userMap ログインユーザー情報
	 * @return Forward 遷移先キーワード
	 * @throws Exception すべての例外
	 */
	public String redrawExeBatch(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gDocBatchBean viewBean = (Tech3gDocBatchBean) getSessionAttr(
											request, KEY_SESS_VB, Tech3gDocBatchBean.class);

		mergeInput2ViewBean(params, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	/**
	 * 相関バリデーション、単体バリデーション処理<br/>
	 * 相関バリデーションやStruts Validationでチェックできないバリデーションを行う。
	 *
	 * @param viewBean 画面表示用のBean
	 * @param check バリデーション対象処理
	 */
	private void validate(Tech3gDocBatchBean viewBean,
							Map<String, String> conditionScrParam,
							VALIDATE_CHECK check) throws ApplicationException {

		File file = null;
		// ApplicationExceptionインスタンスの生成
		ApplicationException _rtnError = new ApplicationException();

		if (VALIDATE_CHECK.exeBatch == check) {
		// バッチ実行

			if (!StrUtil.isEmpty(viewBean.getSelectedBt_flg())) {

				// Tech Documents System Batch Validator
				if ("1".equals(viewBean.getSelectedBt_flg())) {

					if (!StrUtil.isEmpty(viewBean.getDoc_file_path())) {
						file = new File(viewBean.getDoc_file_path());
						if (file.isFile()) {
							_rtnError.addErrorCode("入力値は、Directoryのみ入力可能です。");
						}
						if (!file.exists()) {
							_rtnError.addErrorCode("入力された値に該当する Directoryが存在しません。");
						}
						if (!file.isDirectory()) {
							_rtnError.addErrorCode("入力された値は、Directoryではありません。 \n Directory Pathを入力してください。");
						}
					}

					if (StrUtil.isEmpty(viewBean.getDoc_file_path()) && StrUtil.isEmpty(viewBean.getSelected_bt_no())) {
						throw new ApplicationException("[ Scan Dicrectory ] 又は、 [ バッチ実行履歴情報の選択 ]の何れかを選択してください。");
					}
				}

				// Normalize Batch Validator
				else if ("2".equals(viewBean.getSelectedBt_flg())) {

					if (!StrUtil.isEmpty(viewBean.getDoc_file_path())) {
						file = new File(viewBean.getDoc_file_path());
						if (file.isDirectory()) {
							_rtnError.addErrorCode("入力値は、File Pathのみ入力可能です。");
						}
						if (!file.exists()) {
							_rtnError.addErrorCode("入力された値に該当する File が存在しません。");
						}
					}
					if (StrUtil.isEmpty(viewBean.getDoc_file_path()) && StrUtil.isEmpty(viewBean.getSelected_bt_no())) {
						throw new ApplicationException("[ Batch File Path ] 又は、 [ バッチ実行履歴情報の選択 ]の何れかを選択してください。");
					}
				}
			}
		}

		// エラー存在する場合に、当画面に該当を返す。
		if (!_rtnError.isEmpty()) {
			throw _rtnError;
		}
	}


	/* (non-Javadoc)
	 * @see liveany.common.web.BaseAction#customMergeInput2ViewBean(liveany.common.web.CommonParams, liveany.common.web.ViewBean)
	 */
	protected void customMergeInput2ViewBean(CommonParams params,
												ViewBean viewBean) {
		// ---- チェックボックスのマージ
		Tech3gDocBatchBean batchBean = (Tech3gDocBatchBean) viewBean;
		String btStatsRefresh = params.getParam("btStatsRefresh", CommonConsts.CHECKBOX_OFF);
		batchBean.setBtStatsRefresh(btStatsRefresh);
	}
}
