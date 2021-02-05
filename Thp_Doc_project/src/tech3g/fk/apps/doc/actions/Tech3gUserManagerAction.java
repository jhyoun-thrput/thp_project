package tech3g.fk.apps.doc.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.ApplicationException;
import tech3g.common.util.StrUtil;
import tech3g.common.web.BaseAction;
import tech3g.common.web.CommonParams;
import tech3g.common.web.ViewBean;
import tech3g.fk.apps.doc.services.Tech3gUserManagerService;

public class Tech3gUserManagerAction extends BaseAction {

	// --------------------------------------------------- 定数

	public static final String KEY_SESS_VB = "Tech3gUserManagerBean";

	public static final String REGI_KEY_SESS_VB = "Tech3gUserRegiBean";

	public static final String KEY_BEAN_SERV = "Tech3gUserManagerService";


	// Eng, Decimal, [-, _]
	public static final String user_id_pattern = "^[a-zA-Z0-9_-]*$";

	/** バリデーション判断用の処理名 */
	private static enum VALIDATE_CHECK {

		check_initUser
	,	check_userId
	,	check_insertUser
	,	check_updateUser
	,	check_passUpt

	}

	// --------------------------------------------------- インスタンスメソッド

	public String initUserManager(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, KEY_SESS_VB);
		Tech3gUserManagerBean viewBean = new Tech3gUserManagerBean();

		// カスタムviewBean設定
		mergeInput2ViewBean(params, viewBean);
		String pageNo = params.getParam(CommonConsts.KEY_PARAM_PAGENO); // 表示するページ数

		// -------------- ビジネスロジックの実行
		Tech3gUserManagerService service = (Tech3gUserManagerService) getBean(KEY_BEAN_SERV);
		service.initUserManager(viewBean, pageNo);

		// -------------- ビジネスロジックの実行
		setSessionAttr(request, KEY_SESS_VB, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	public String initPassUpt(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, REGI_KEY_SESS_VB);
		Tech3gUserRegiBean viewBean = new Tech3gUserRegiBean();

		// カスタムviewBean設定
		mergeInput2ViewBean(params, viewBean);
		getParams(request, viewBean);

		String userId = (String) userMap.get(CommonConsts.USER_INF_USER_ID);	// ユーザーID
		viewBean.setSelected_user_id(userId);

		// -------------- ビジネスロジックの実行
		Tech3gUserManagerService service = (Tech3gUserManagerService) getBean(KEY_BEAN_SERV);
		service.initUserRegi(viewBean);

		// -------------- ビジネスロジックの実行
		setSessionAttr(request, REGI_KEY_SESS_VB, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	public String initUserRegi(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, REGI_KEY_SESS_VB);
		Tech3gUserRegiBean viewBean = new Tech3gUserRegiBean();

		// カスタムviewBean設定
		mergeInput2ViewBean(params, viewBean);



		String userId = (String) userMap.get(CommonConsts.USER_INF_USER_ID);	// ユーザーID
		viewBean.setRegi_user(userId);
		viewBean.setUpt_user(userId);
		viewBean.setLogin_user_admin(getUserAdmin(request));

		// -------------- ビジネスロジックの実行
		Tech3gUserManagerService service = (Tech3gUserManagerService) getBean(KEY_BEAN_SERV);
		service.initUserRegi(viewBean);

		// -------------- ビジネスロジックの実行
		setSessionAttr(request, REGI_KEY_SESS_VB, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	public String checkUserRegi(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		Tech3gUserRegiBean viewBean = (Tech3gUserRegiBean) getSessionAttr(request, REGI_KEY_SESS_VB, Tech3gUserRegiBean.class);
		mergeInput2ViewBean(params, viewBean);

		validate(viewBean, VALIDATE_CHECK.check_userId);

		// -------------- ビジネスロジックの実行
		Tech3gUserManagerService service = (Tech3gUserManagerService) getBean(KEY_BEAN_SERV);
		service.checkUserRegi(viewBean.getUser_id(), viewBean);

		// -------------- ビジネスロジックの実行
		setSessionAttr(request, REGI_KEY_SESS_VB, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	public String updateUserRegi(HttpServletRequest request, HttpServletResponse response, CommonParams params,
			Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gUserRegiBean viewBean = (Tech3gUserRegiBean) getSessionAttr(
											request, REGI_KEY_SESS_VB, Tech3gUserRegiBean.class);

		String userId = (String) userMap.get(CommonConsts.USER_INF_USER_ID); // ユーザーID
		mergeInput2ViewBean(params, viewBean); // 入力値のマージ
		viewBean.setLogin_user_admin(getUserAdmin(request));

		// -------------- バリデーション
		validate(viewBean, VALIDATE_CHECK.check_updateUser);

		// -------------- ビジネスロジックの実行
		Tech3gUserManagerService service = (Tech3gUserManagerService) getBean(KEY_BEAN_SERV);
		service.updateUserManager(userId, viewBean);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, REGI_KEY_SESS_VB);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, "正常に更新されました。");
		return CommonConsts.FW_SUCCESS;
	}

	public String updatePassword(HttpServletRequest request, HttpServletResponse response, CommonParams params,
			Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gUserRegiBean viewBean = (Tech3gUserRegiBean) getSessionAttr(
											request, REGI_KEY_SESS_VB, Tech3gUserRegiBean.class);

		String userId = (String) userMap.get(CommonConsts.USER_INF_USER_ID); // ユーザーID
		mergeInput2ViewBean(params, viewBean); // 入力値のマージ
		viewBean.setUser_id(userId);
		viewBean.setLogin_user_admin(getUserAdmin(request));

		// -------------- バリデーション
		validate(viewBean, VALIDATE_CHECK.check_passUpt);

		// -------------- ビジネスロジックの実行
		Tech3gUserManagerService service = (Tech3gUserManagerService) getBean(KEY_BEAN_SERV);
		service.updateUserManager(userId, viewBean);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, REGI_KEY_SESS_VB);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, "正常に変更されました。");
		return CommonConsts.FW_SUCCESS;
	}

	public String loginPassUpt(HttpServletRequest request, HttpServletResponse response, CommonParams params,
			Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gUserRegiBean viewBean = (Tech3gUserRegiBean) getSessionAttr(
											request, REGI_KEY_SESS_VB, Tech3gUserRegiBean.class);

		String userId = (String) userMap.get(CommonConsts.USER_INF_USER_ID); // ユーザーID
		mergeInput2ViewBean(params, viewBean); // 入力値のマージ
		viewBean.setUser_id(userId);
		viewBean.setLogin_user_admin(getUserAdmin(request));

		// -------------- バリデーション
		validate(viewBean, VALIDATE_CHECK.check_passUpt);

		// -------------- ビジネスロジックの実行
		Tech3gUserManagerService service = (Tech3gUserManagerService) getBean(KEY_BEAN_SERV);
		service.updateUserManager(userId, viewBean);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, REGI_KEY_SESS_VB);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, "正常に変更されました。");
		return CommonConsts.FW_SUCCESS;
	}

	public String insertUserRegi(HttpServletRequest request, HttpServletResponse response, CommonParams params,
												Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gUserRegiBean viewBean = (Tech3gUserRegiBean) getSessionAttr(
				request, REGI_KEY_SESS_VB, Tech3gUserRegiBean.class);

		String userId = (String) userMap.get(CommonConsts.USER_INF_USER_ID); // ユーザーID
		mergeInput2ViewBean(params, viewBean); // 入力値のマージ
		viewBean.setLogin_user_admin(getUserAdmin(request));

		// -------------- バリデーション
		validate(viewBean, VALIDATE_CHECK.check_insertUser);

		// -------------- ビジネスロジックの実行
		Tech3gUserManagerService service = (Tech3gUserManagerService) getBean(KEY_BEAN_SERV);
		service.insertUserManager(userId, viewBean);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, REGI_KEY_SESS_VB);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, "正常に登録されました。");
		return CommonConsts.FW_SUCCESS;
	}

	public String deleteUser(HttpServletRequest request, HttpServletResponse response, CommonParams params,
			Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gUserManagerBean viewBean = (Tech3gUserManagerBean) getSessionAttr(
				request, KEY_SESS_VB, Tech3gUserManagerBean.class);

		mergeInput2ViewBean(params, viewBean); // 入力値のマージ
		String userId = (String) userMap.get(CommonConsts.USER_INF_USER_ID); // ユーザーID

		// -------------- ビジネスロジックの実行
		Tech3gUserManagerService service = (Tech3gUserManagerService) getBean(KEY_BEAN_SERV);
		service.deleteUserManager(userId, viewBean);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, KEY_SESS_VB);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, "正常に削除されました。");
		return CommonConsts.FW_SUCCESS;
	}

	public String userConfirm(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gUserRegiBean viewBean = (Tech3gUserRegiBean) getSessionAttr(request, REGI_KEY_SESS_VB, Tech3gUserRegiBean.class);
		mergeInput2ViewBean(params, viewBean);

		// -------------- ビジネスロジックの実行
		Tech3gUserManagerService service = (Tech3gUserManagerService) getBean(KEY_BEAN_SERV);
		service.userConfirm(viewBean);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, REGI_KEY_SESS_VB);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	public String userPassUptConfirm(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gUserRegiBean viewBean = (Tech3gUserRegiBean) getSessionAttr(request, REGI_KEY_SESS_VB, Tech3gUserRegiBean.class);
		mergeInput2ViewBean(params, viewBean);

		// -------------- ビジネスロジックの実行
		Tech3gUserManagerService service = (Tech3gUserManagerService) getBean(KEY_BEAN_SERV);
		service.userConfirm(viewBean);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, REGI_KEY_SESS_VB);

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
	public String searchUserManager(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gUserManagerBean viewBean = (Tech3gUserManagerBean) getSessionAttr(request, KEY_SESS_VB, Tech3gUserManagerBean.class);

		mergeInput2ViewBean(params, viewBean); // 入力値のマージ
		String pageNo = params.getParam(CommonConsts.KEY_PARAM_PAGENO); // 表示するページ数

		// -------------- バリデーション
		//validate(viewBean, VALIDATE_CHECK.);

		// -------------- ビジネスロジックの実行
		Tech3gUserManagerService service = (Tech3gUserManagerService) getBean(KEY_BEAN_SERV);
		service.searchUserManager(viewBean, pageNo);

		// -------------- 検索結果をセッションに保持
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
	public String redrawUserManager(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gUserManagerBean viewBean = (Tech3gUserManagerBean) getSessionAttr(request, KEY_SESS_VB, Tech3gUserManagerBean.class);
		mergeInput2ViewBean(params, viewBean);

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
	public String redrawPassUpt(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gUserRegiBean viewBean = (Tech3gUserRegiBean) getSessionAttr(request, REGI_KEY_SESS_VB, Tech3gUserRegiBean.class);
		mergeInput2ViewBean(params, viewBean);

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
	public String redrawUserRegi(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gUserRegiBean viewBean = (Tech3gUserRegiBean) getSessionAttr(request, REGI_KEY_SESS_VB, Tech3gUserRegiBean.class);
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
	private void validate(Object obj, VALIDATE_CHECK check) throws ApplicationException {

		Tech3gUserManagerBean mngeBean = null;
		Tech3gUserRegiBean regiBean = null;
		boolean beanFlg = false;

		if (obj instanceof Tech3gUserManagerBean) {
			mngeBean = (Tech3gUserManagerBean) obj;
			beanFlg = true;
		}
		else if (obj instanceof Tech3gUserRegiBean) {
			regiBean = (Tech3gUserRegiBean) obj;
		}

		// ApplicationExceptionインスタンスの生成
		ApplicationException _rtnError = new ApplicationException();
		StringBuffer errorMsg = new StringBuffer();

		if (beanFlg) {
			// User Manager Zone
		}
		else {
			// User Regi Zone

			if (StrUtil.isEmpty(regiBean.getUser_id())) {
				//_rtnError.addErrorCode("USER_IDは、必須項目です。");
				errorMsg.append("*USER_IDは、必須項目です。");
				errorMsg.append("<br/>");
			}

			// 該当者検索際のバリデーションチェック
			if (check.equals(VALIDATE_CHECK.check_userId)) {

				if (!regiBean.getUser_id().matches(user_id_pattern)) {
					//_rtnError.addErrorCode("User ID 形式ではありません。\n User IDには [英語、数字、ハイフン、アンダーバー ]のみ、入力可能です。");
					errorMsg.append("*User ID 形式ではありません。\n User IDには [英語、数字、ハイフン、アンダーバー ]のみ、入力可能です。");
					errorMsg.append("<br/>");
				}
				if (!StrUtil.isEmpty(regiBean.getUser_id()) && regiBean.getUser_id().length() <= 3 ) {
					//_rtnError.addErrorCode("*User ID は、３文字以上に入力してください。");
					errorMsg.append("*User ID は、３文字以上に入力してください。");
					errorMsg.append("<br/>");
				}
			}
			else if (check.equals(VALIDATE_CHECK.check_insertUser)) {

				if (!StrUtil.isEmpty(regiBean.getUser_id())) {
					if (StrUtil.isEmpty(regiBean.getCheck_flg())) {
						errorMsg.append("*User Check を先に実行してください。 ");
						regiBean.setErr_massge(errorMsg.toString());
						throw new ApplicationException();
					}
				}
				if (StrUtil.isEmpty(regiBean.getUser_pass())) {
					errorMsg.append("*USER PASSは、必須項目です。");
					errorMsg.append("<br/>");
				}
				if (StrUtil.isEmpty(regiBean.getConfrm_pass())) {
					errorMsg.append("*Confirm PASSは、必須項目です。");
					errorMsg.append("<br/>");
				}
				if (!regiBean.getUser_id().matches(user_id_pattern)) {
					//_rtnError.addErrorCode("User ID 形式ではありません。\n User IDには [英語、数字、ハイフン、アンダーバー ]のみ、入力可能です。");
					errorMsg.append("*User ID 形式ではありません。\n User IDには [英語、数字、ハイフン、アンダーバー ]のみ、入力可能です。");
					errorMsg.append("<br/>");
				}
				if (!StrUtil.isEmpty(regiBean.getUser_id()) && regiBean.getUser_id().length() <= 3 ) {
					//_rtnError.addErrorCode("*User ID は、３文字以上に入力してください。");
					errorMsg.append("*User ID は、３文字以上に入力してください。");
					errorMsg.append("<br/>");
				}
				if (!StrUtil.isEmpty(regiBean.getUser_pass())) {
					if (regiBean.getUser_pass().length() <= 3) {
						errorMsg.append("*User Pass は、３文字以上に入力してください。");
						errorMsg.append("<br/>");
					}
					if (!StrUtil.isEmpty(regiBean.getConfrm_pass())) {
						if (!regiBean.getUser_pass().trim().equals(regiBean.getConfrm_pass().trim())) {
							errorMsg.append("*User Pass と Confirm Pass が異なります。");
							errorMsg.append("<br/>");
						}
					}
				}
			}
			else if (check.equals(VALIDATE_CHECK.check_updateUser)) {

				if (!StrUtil.isEmpty(regiBean.getCurrent_pass())) {
					if (StrUtil.isEmpty(regiBean.getNew_pass())) {
						errorMsg.append("*New User PASSは、必須項目です。");
						errorMsg.append("<br/>");
					}
					if (StrUtil.isEmpty(regiBean.getConfrm_pass())) {
						errorMsg.append("*Confirm PASSは、必須項目です。");
						errorMsg.append("<br/>");
					}
				}
				else if (!StrUtil.isEmpty(regiBean.getNew_pass())) {
					if (StrUtil.isEmpty(regiBean.getCurrent_pass())) {
						errorMsg.append("*Current PASSは、必須項目です。");
						errorMsg.append("<br/>");
					}
					if (StrUtil.isEmpty(regiBean.getConfrm_pass())) {
						errorMsg.append("*Confirm PASSは、必須項目です。");
						errorMsg.append("<br/>");
					}
				}
				else if (!StrUtil.isEmpty(regiBean.getConfrm_pass())) {
					if (StrUtil.isEmpty(regiBean.getCurrent_pass())) {
						errorMsg.append("*Current PASSは、必須項目です。");
						errorMsg.append("<br/>");
					}
					if (StrUtil.isEmpty(regiBean.getConfrm_pass())) {
						errorMsg.append("*Confirm PASSは、必須項目です。");
						errorMsg.append("<br/>");
					}
				}

				if (!StrUtil.isEmpty(regiBean.getCurrent_pass()) && !StrUtil.isEmpty(regiBean.getNew_pass())) {
					if (regiBean.getCurrent_pass().trim().equals(regiBean.getNew_pass().trim())) {
						errorMsg.append("*Current PASSとNew User Pass は、同一に設定できません。");
						errorMsg.append("<br/>");
					}
				}

				if (!StrUtil.isEmpty(regiBean.getNew_pass())) {
					if (regiBean.getNew_pass().length() <= 3) {
						errorMsg.append("*New User Pass は、３文字以上に入力してください。");
						errorMsg.append("<br/>");
					}
					if (!StrUtil.isEmpty(regiBean.getConfrm_pass())) {
						if (!regiBean.getNew_pass().trim().equals(regiBean.getConfrm_pass().trim())) {
							errorMsg.append("*New User Pass と Confirm Pass が異なります。");
							errorMsg.append("<br/>");
						}
					}
				}
			}
			else if (check.equals(VALIDATE_CHECK.check_passUpt)) {
				if (StrUtil.isEmpty(regiBean.getCurrent_pass())) {
					errorMsg.append("*Current PASSは、必須項目です。");
					errorMsg.append("<br/>");
				}
				if (StrUtil.isEmpty(regiBean.getNew_pass())) {
					errorMsg.append("*New User PASSは、必須項目です。");
					errorMsg.append("<br/>");
				}
				if (StrUtil.isEmpty(regiBean.getConfrm_pass())) {
					errorMsg.append("*Confirm PASSは、必須項目です。");
					errorMsg.append("<br/>");
				}
				if (!StrUtil.isEmpty(regiBean.getCurrent_pass()) && !StrUtil.isEmpty(regiBean.getNew_pass())) {
					if (regiBean.getCurrent_pass().trim().equals(regiBean.getNew_pass().trim())) {
						errorMsg.append("*Current PASSとNew User Pass は、同一に設定できません。");
						errorMsg.append("<br/>");
					}
				}
				if (!StrUtil.isEmpty(regiBean.getNew_pass())) {
					if (regiBean.getNew_pass().length() <= 3) {
						errorMsg.append("*New User Pass は、３文字以上に入力してください。");
						errorMsg.append("<br/>");
					}
					if (!StrUtil.isEmpty(regiBean.getConfrm_pass())) {
						if (!regiBean.getNew_pass().trim().equals(regiBean.getConfrm_pass().trim())) {
							errorMsg.append("*New User Pass と Confirm Pass が異なります。");
							errorMsg.append("<br/>");
						}
					}
				}
			}

			if (errorMsg.length() > 0) {
				regiBean.setErr_massge(errorMsg.toString());
			}
		}

		// エラー存在する場合に、当画面に該当を返す。
		if (!_rtnError.isEmpty() || errorMsg.length() > 0) {
			// throwを行う前に該当エラーを格納する。
			//viewBean.setCurrntErrorList(_rtnError.getErrorCodeList());
			// 該当エラーのthrow
			throw _rtnError;
		}
	}

	/* (non-Javadoc)
	 * @see liveany.common.web.BaseAction#customMergeInput2ViewBean(liveany.common.web.CommonParams, liveany.common.web.ViewBean)
	 */
	@Override
	protected void customMergeInput2ViewBean(CommonParams params, ViewBean viewBean) {
	}

	private void getParams(HttpServletRequest request, Tech3gUserRegiBean viewBean) {

		if (!StrUtil.isEmpty(takeParam(request, "login_msg"))) {
			viewBean.setSamePass("same");
		}
		if (!StrUtil.isEmpty(takeParam(request, "login_errMsg"))) {
			viewBean.setErr_massge(takeParam(request, "login_errMsg"));
		}
	}

	/**
	 * ・遷移先画面に引き渡すパラメタの設定を行う。
	 * <br/>
	 * @param request HttpServletRequest
	 * @param viewBean 画面表示用のBean
	 */
	private void setParamas(HttpServletRequest request, Tech3gUserManagerBean viewBean) {
	}

}
