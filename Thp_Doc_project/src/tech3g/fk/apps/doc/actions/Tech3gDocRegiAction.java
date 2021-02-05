/**
 *
 */
package tech3g.fk.apps.doc.actions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.ApplicationException;
import tech3g.common.util.DateUtil;
import tech3g.common.util.NumberUtil;
import tech3g.common.util.StrUtil;
import tech3g.common.web.BaseAction;
import tech3g.common.web.CommonParams;
import tech3g.common.web.ViewBean;
import tech3g.fk.apps.doc.services.Tech3gDocRegiService;

/**
 * @author tech3g
 *
 */
public class Tech3gDocRegiAction extends BaseAction  {


	// --------------------------------------------------- 定数

	public static final String KEY_SESS_VB = "Tech3gDocRegiBean";

	public static final String KEY_BEAN_SERV = "Tech3gDocRegiService";



	/** バリデーション判断用の処理名 */
	private static enum VALIDATE_CHECK {

			check_insertDocRegi
		,	check_uploadFile
	}

	// --------------------------------------------------- インスタンスメソッド

	public String initTechDocRegi(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		Tech3gDocRegiBean viewBean = new Tech3gDocRegiBean();

		// カスタムviewBean設定
		mergeInput2ViewBean(params, viewBean);									// 入力値のマージ

		// -------------- ビジネスロジックの実行
		Tech3gDocRegiService service = (Tech3gDocRegiService) getBean(KEY_BEAN_SERV);
		service.initTechDocRegi(viewBean);

		setSessionAttr(request, KEY_SESS_VB, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}


	public String insertDocRegi(HttpServletRequest request, HttpServletResponse response, CommonParams params,
															Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gDocRegiBean viewBean = (Tech3gDocRegiBean) getSessionAttr(
												request, KEY_SESS_VB, Tech3gDocRegiBean.class);

		String userId = (String) userMap.get(CommonConsts.USER_INF_USER_ID);	// ユーザーID
		mergeInput2ViewBean(params, viewBean);									// 入力値のマージ

		// 添付ファイルの個数
		String upCnt = takeParam(request, "upload_cnt");

		if (!StrUtil.isEmpty(upCnt)) {
			viewBean.setUpload_cnt(Integer.valueOf(upCnt));
		}

		// -------------- バリデーション
		validate(viewBean, VALIDATE_CHECK.check_insertDocRegi);

		// -------------- ビジネスロジックの実行
		Tech3gDocRegiService service = (Tech3gDocRegiService) getBean(KEY_BEAN_SERV);
		service.insertDocRegi(userId, viewBean);

		setParamas(request, viewBean);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, KEY_SESS_VB);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	public String uploadMultiFiles(HttpServletRequest request, HttpServletResponse response, CommonParams params,
																						Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gDocRegiBean viewBean = new Tech3gDocRegiBean();
		mergeInput2ViewBean(params, viewBean); // 入力値のマージ

		// -------------- バリデーション
		//validate(viewBean, VALIDATE_CHECK.check_uploadFile);

		// -------------- ビジネスロジックの実行
		Tech3gDocRegiService service = (Tech3gDocRegiService) getBean(KEY_BEAN_SERV);
		service.multiFileUpload(viewBean);

		giveParam(request, "upload_cnt",  String.valueOf(viewBean.getUpload_cnt()));

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	public String clearFile(HttpServletRequest request, HttpServletResponse response, CommonParams params,
																						Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gDocRegiBean viewBean = (Tech3gDocRegiBean) getSessionAttr(
																							request, KEY_SESS_VB, Tech3gDocRegiBean.class);
		mergeInput2ViewBean(params, viewBean); // 入力値のマージ

		// -------------- ビジネスロジックの実行
		Tech3gDocRegiService service = (Tech3gDocRegiService) getBean(KEY_BEAN_SERV);
		service.clearMultiFiles(viewBean);

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
	public String redrawDocRegi(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gDocRegiBean viewBean = (Tech3gDocRegiBean) getSessionAttr(request, KEY_SESS_VB, Tech3gDocRegiBean.class);
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
	public String docDetailInfo(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gDocRegiBean viewBean = (Tech3gDocRegiBean) getSessionAttr(request, KEY_SESS_VB, Tech3gDocRegiBean.class);
		mergeInput2ViewBean(params, viewBean);
		getParam(request, viewBean);

		// -------------- ビジネスロジックの実行
		Tech3gDocRegiService service = (Tech3gDocRegiService) getBean(KEY_BEAN_SERV);
		service.confrmDocInfo(viewBean);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, KEY_SESS_VB);

		setParamas(request, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, "正常に登録されました。");
		return CommonConsts.FW_SUCCESS;
	}



	public String getDocInfoIndex(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gDocRegiBean viewBean = (Tech3gDocRegiBean) getSessionAttr(request, KEY_SESS_VB, Tech3gDocRegiBean.class);
		mergeInput2ViewBean(params, viewBean);

		// -------------- ビジネスロジックの実行
		Tech3gDocRegiService service = (Tech3gDocRegiService) getBean(KEY_BEAN_SERV);
		service.searchDocIndex(viewBean);

		setSessionAttr(request, KEY_SESS_VB, viewBean);

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
	private void validate(Tech3gDocRegiBean viewBean, VALIDATE_CHECK check) throws ApplicationException {

		// ApplicationExceptionインスタンスの生成
		ApplicationException _rtnError = new ApplicationException();
		// -------------- ビジネスロジックの実行
		Tech3gDocRegiService service = (Tech3gDocRegiService) getBean(KEY_BEAN_SERV);

		// 該当者検索際のバリデーションチェック
		if (check.equals(VALIDATE_CHECK.check_insertDocRegi)) {

			if (StrUtil.isEmpty(viewBean.getDoc_code())) {
				_rtnError.addErrorCode("[ 文書分類名 ]は、必須項目です。");
			}

			if (StrUtil.isEmpty(viewBean.getDoc_ver())) {
				_rtnError.addErrorCode("[ 文書 Ver ]は、必須項目です。");
			}

			if (!StrUtil.isEmpty(viewBean.getDoc_ver())) {
				String ver = StrUtil.removeAll(viewBean.getDoc_ver(), ".");
				if (!NumberUtil.isNumber(ver)) {
					_rtnError.addErrorCode("[ 文書 Ver. ]には、[数字 ] 及び [.]のみ、入力してください。");
				}
			}

			if (viewBean.getUpload_cnt() == 0) {
				_rtnError.addErrorCode("[ 添付ファイル ]は、必須項目です。");
				_rtnError.addErrorCode("* ファイルを添付して、Start Uploadを行ってください。");
			}
			if (viewBean.getUpload_cnt() > 0) {
				if (viewBean.getUpload_cnt() > 100) {
					_rtnError.addErrorCode("* 添付ファイルは、最大100個までです。");
				}
			}

			if (!StrUtil.isEmpty(viewBean.getComment())) {
				int cmntLen = StrUtil.getByteLenUTF8(viewBean.getComment());
				if (cmntLen > 2000) {
					_rtnError.addErrorCode("[ コメント欄 ]には、2000byte 以上は入力できません。");
				}
			}
		}
		else if (check.equals(VALIDATE_CHECK.check_uploadFile)) {

//			if (StrUtil.isEmpty(viewBean.getDoc_code())) {
//				throw new ApplicationException("[ 文書分類名 ]は、必須項目です。");
//			}
		}

		// エラー存在する場合に、当画面に該当を返す。
		if (!_rtnError.isEmpty()) {
			// throwを行う前に該当エラーを格納する。
			service.clearMultiFiles(viewBean);
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
	 *
	 * @param request
	 * @param viewBean
	 */
	private void getParam(HttpServletRequest request, Tech3gDocRegiBean viewBean) {
			viewBean.setIndex_field(takeParam(request, "index_field"));
	}

	/**
	 * ・遷移先画面に引き渡すパラメタの設定を行う。
	 * <br/>
	 * @param request HttpServletRequest
	 * @param viewBean 画面表示用のBean
	 */
	private void setParamas(HttpServletRequest request, Tech3gDocRegiBean viewBean) {

//		if (!StrUtil.isEmpty(viewBean.getDoc_code_name())) {
//			giveParam(request, "doc_name", viewBean.getDoc_name());
//		}

		String currentTime = DateUtil.geCurrentDateStr2();
		giveParam(request, "p_regi_date_from", currentTime);

		if (!StrUtil.isEmpty(viewBean.getIndex_field())) {
			giveParam(request, "index_field", viewBean.getIndex_field());
		}
	}

}
