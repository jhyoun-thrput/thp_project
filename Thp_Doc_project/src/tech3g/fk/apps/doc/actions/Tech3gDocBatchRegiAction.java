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
import tech3g.fk.apps.doc.services.Tech3gDocRegiService;

public class Tech3gDocBatchRegiAction extends BaseAction {

	// --------------------------------------------------- 定数

	public static final String KEY_SESS_VB = "Tech3gDocBatchRegiBean";

	public static final String KEY_BEAN_SERV = "Tech3gDocBatchService";



	/** バリデーション判断用の処理名 */
	private static enum VALIDATE_CHECK {

		check_insertDocBatchRegi
	,	check_updateDocBatchRegi
	}

	// --------------------------------------------------- インスタンスメソッド

	public String initBatchRegi(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		String userId = (String) userMap.get(CommonConsts.USER_INF_USER_ID);	// ユーザーID
		Tech3gDocBatchRegiBean viewBean = new Tech3gDocBatchRegiBean();

		// カスタムviewBean設定
		mergeInput2ViewBean(params, viewBean);									// 入力値のマージ
		viewBean.setRegi_user(userId);

		// -------------- ビジネスロジックの実行
		Tech3gDocBatchService service = (Tech3gDocBatchService) getBean(KEY_BEAN_SERV);
		service.initBatchRegi(viewBean);

		setSessionAttr(request, KEY_SESS_VB, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	public String updateBtachRegi(HttpServletRequest request, HttpServletResponse response, CommonParams params,
			Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gDocBatchRegiBean viewBean = (Tech3gDocBatchRegiBean) getSessionAttr(
				request, KEY_SESS_VB, Tech3gDocBatchRegiBean.class);

		String userId = (String) userMap.get(CommonConsts.USER_INF_USER_ID); // ユーザーID
		mergeInput2ViewBean(params, viewBean); // 入力値のマージ

		// -------------- バリデーション
		validate(viewBean, VALIDATE_CHECK.check_updateDocBatchRegi);

		// -------------- ビジネスロジックの実行
		Tech3gDocBatchService service = (Tech3gDocBatchService) getBean(KEY_BEAN_SERV);
		service.updateBatchRegi(userId, viewBean);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, KEY_SESS_VB);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, "正常に更新されました。");
		return CommonConsts.FW_SUCCESS;
	}

	public String insertBatchRegi(HttpServletRequest request, HttpServletResponse response, CommonParams params,
												Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gDocBatchRegiBean viewBean = (Tech3gDocBatchRegiBean) getSessionAttr(
				request, KEY_SESS_VB, Tech3gDocBatchRegiBean.class);

		String userId = (String) userMap.get(CommonConsts.USER_INF_USER_ID); // ユーザーID
		mergeInput2ViewBean(params, viewBean); // 入力値のマージ

		// -------------- バリデーション
		validate(viewBean, VALIDATE_CHECK.check_insertDocBatchRegi);

		// -------------- ビジネスロジックの実行
		Tech3gDocBatchService service = (Tech3gDocBatchService) getBean(KEY_BEAN_SERV);
		service.insertBatchRegi(userId, viewBean);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, KEY_SESS_VB);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, "正常に登録されました。");
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
	public String batchConfirm(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gDocBatchRegiBean viewBean = (Tech3gDocBatchRegiBean) getSessionAttr(request, KEY_SESS_VB, Tech3gDocBatchRegiBean.class);
		mergeInput2ViewBean(params, viewBean);

		// -------------- ビジネスロジックの実行
		Tech3gDocBatchService service = (Tech3gDocBatchService) getBean(KEY_BEAN_SERV);
		service.batchConfirm(viewBean);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, KEY_SESS_VB);

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
	public String redrawBatchRegi(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gDocBatchRegiBean viewBean = (Tech3gDocBatchRegiBean) getSessionAttr(request, KEY_SESS_VB, Tech3gDocBatchRegiBean.class);
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
	private void validate(Tech3gDocBatchRegiBean viewBean, VALIDATE_CHECK check) throws ApplicationException {

		// ApplicationExceptionインスタンスの生成
		ApplicationException _rtnError = new ApplicationException();

		// 該当者検索際のバリデーションチェック
		if (check.equals(VALIDATE_CHECK.check_insertDocBatchRegi)) {

			if (StrUtil.isEmpty(viewBean.getBt_id())) {
				_rtnError.addErrorCode("[ Batch ID ]は、必須項目です。");
			}
			if (StrUtil.isEmpty(viewBean.getBt_param())) {
				_rtnError.addErrorCode("[ Batch Param ]は、必須項目です。");
			}

			if (!StrUtil.isEmpty(viewBean.getBt_flg())) {
				if ("1".equals(viewBean.getBt_flg())) {
					if (!StrUtil.isEmpty(viewBean.getBt_param())) {
						File file = new File(viewBean.getBt_param());
						if (!file.isDirectory()) {
							_rtnError.addErrorCode("[ Batch Param ]には、Dicrectory Pathのみ入力可能です。");
						}
					}
				}
				else if ("2".equals(viewBean.getBt_flg())) {
					if (!StrUtil.isEmpty(viewBean.getBt_param())) {
						File file = new File(viewBean.getBt_param());
						if (!file.isFile()) {
							_rtnError.addErrorCode("[ Batch Param ]には、Btach File Path のみ入力可能です。");
						}
					}
				}
			}

			if (!StrUtil.isEmpty(viewBean.getBt_name())) {
				int cmntLen = StrUtil.getByteLenUTF8(viewBean.getBt_name());
				if (cmntLen > 100) {
					_rtnError.addErrorCode("[ Batch Name ]には、100byte 以上は入力できません。");
				}
			}
		}
		else if (check.equals(VALIDATE_CHECK.check_updateDocBatchRegi)) {

			if (StrUtil.isEmpty(viewBean.getBt_param())) {
				_rtnError.addErrorCode("[ Batch Param ]は、必須項目です。");
			}

			if (!StrUtil.isEmpty(viewBean.getBt_flg())) {
				if ("1".equals(viewBean.getBt_flg())) {
					if (!StrUtil.isEmpty(viewBean.getBt_param())) {
						File file = new File(viewBean.getBt_param());
						if (!file.isDirectory()) {
							_rtnError.addErrorCode("[ Batch Param ]には、Dicrectory Pathのみ入力可能です。");
						}
					}
				}
				else if ("2".equals(viewBean.getBt_flg())) {
					if (!StrUtil.isEmpty(viewBean.getBt_param())) {
						File file = new File(viewBean.getBt_param());
						if (!file.isFile()) {
							_rtnError.addErrorCode("[ Batch Param ]には、Btach File Path のみ入力可能です。");
						}
					}
				}
			}

			if (!StrUtil.isEmpty(viewBean.getBt_name())) {
				int cmntLen = StrUtil.getByteLenUTF8(viewBean.getBt_name());
				if (cmntLen > 100) {
					_rtnError.addErrorCode("[ Batch Name ]には、100byte 以上は入力できません。");
				}
			}
		}

		// エラー存在する場合に、当画面に該当を返す。
		if (!_rtnError.isEmpty()) {
			// throwを行う前に該当エラーを格納する。
			throw _rtnError;
		}
	}

	/* (non-Javadoc)
	 * @see liveany.common.web.BaseAction#customMergeInput2ViewBean(liveany.common.web.CommonParams, liveany.common.web.ViewBean)
	 */
	@Override
	protected void customMergeInput2ViewBean(CommonParams params, ViewBean viewBean) {
	}

	/**
	 * ・遷移先画面に引き渡すパラメタの設定を行う。
	 * <br/>
	 * @param request HttpServletRequest
	 * @param viewBean 画面表示用のBean
	 */
	private void setParamas(HttpServletRequest request, Tech3gDocBatchRegiBean viewBean) {

//		if (!StrUtil.isEmpty(viewBean.getDoc_code_name())) {
//			giveParam(request, "doc_name", viewBean.getDoc_name());
//		}
	}


}
