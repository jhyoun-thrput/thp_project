/**
 *
 */
package tech3g.fk.apps.doc.actions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.upload.FormFile;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.ApplicationException;
import tech3g.common.exceptions.MsgVO;
import tech3g.common.exceptions.SystemException;
import tech3g.common.util.ExclusionUtil;
import tech3g.common.util.FileUtil;
import tech3g.common.util.NumberUtil;
import tech3g.common.util.StrUtil;
import tech3g.common.web.BaseAction;
import tech3g.common.web.CommonParams;
import tech3g.common.web.ViewBean;
import tech3g.fk.apps.doc.TechDocUtil;
import tech3g.fk.apps.doc.services.Tech3gDocSearchService;

/**
 * @author tech3g
 *
 */
public class Tech3gDocDetailAction extends BaseAction {

	// --------------------------------------------------- 定数

	public static final String KEY_SESS_VB = "Tech3gDocDetailBean";

	public static final String KEY_BEAN_SERV = "Tech3gDocService";



	/** バリデーション判断用の処理名 */
	private static enum VALIDATE_CHECK {

		check_updateDocDetail
	}

	// --------------------------------------------------- インスタンスメソッド

	public String initTechDocDetail(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		Tech3gDocDetailBean viewBean = new Tech3gDocDetailBean();

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, KEY_SESS_VB);

		// カスタムviewBean設定
		mergeInput2ViewBean(params, viewBean);
		ListOrderedMap map = (ListOrderedMap) getSesnUserInfoMap(request);

		if (!map.isEmpty()) {
			viewBean.setAuth_flg(String.valueOf(map.get("user_admin")));
		}

		String pageNo = params.getParam(CommonConsts.KEY_PARAM_PAGENO); // 表示するページ数

		// -------------- 排他情報を取得
		Map<String, Object> exclusion = ExclusionUtil.getExclusionInfo(this, request);

		// -------------- ビジネスロジックの実行
		Tech3gDocSearchService service = (Tech3gDocSearchService) getBean(KEY_BEAN_SERV);
		service.initTechDocDetail(viewBean, exclusion, pageNo);

		// -------------- ビジネスロジックの実行
		setSessionAttr(request, KEY_SESS_VB, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	/**
	 * File Download Action
	 * @param request
	 * @param response
	 * @param params
	 * @param userMap
	 * @return
	 * @throws Exception
	 */
	public String downloadDocFile(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gDocDetailBean viewBean = new Tech3gDocDetailBean();

		// カスタムviewBean設定
		mergeInput2ViewBean(params, viewBean);

		String link = null;
		if (StrUtil.isEmpty(viewBean.getDoc_link())) {
			link = viewBean.getDoc_hist_link();
		}
		else if (StrUtil.isEmpty(viewBean.getDoc_hist_link())) {
			link = viewBean.getDoc_link();
		}
		File file = new File(link);

		if (!file.exists()) {
			throw new ApplicationException("ファイルが存在しません。");
		}

		// 該当ファイルのLock
		TechDocUtil.setLocked(file.getName());

		int extNo = file.getName().lastIndexOf(".") + 1;

		response.setContentType(FileUtil.getContentType(file.getName().substring(extNo)));
		response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(file.getName(), TechDocUtil.UTF_8) + "\"");

		BufferedOutputStream  bos = new BufferedOutputStream(response.getOutputStream());
		FileInputStream fis = new FileInputStream(file);
		try {
			int i, len=0;
			while((i=fis.read()) != -1) {
				bos.write(i);
				len++;
			}
		} catch (UnsupportedEncodingException e) {
			// Lock 解除
			TechDocUtil.clearLock();
			throw new SystemException("Encodingの設定が間違っています。設定を確認してください。");
		} catch (IOException e) {
			// Lock 解除
			TechDocUtil.clearLock();
			log.info(file.getName() + "を転送中、何かの理由で転送が中止されました。");
		} finally {
			bos.close();
			fis.close();

			// Lock 解除
			TechDocUtil.clearLock();
		}

		return null;
	}

	public String updateDocDetail(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gDocDetailBean viewBean = (Tech3gDocDetailBean) getSessionAttr(
												request, KEY_SESS_VB, Tech3gDocDetailBean.class);

		mergeInput2ViewBean(params, viewBean);									// 入力値のマージ
		String userId = (String) userMap.get(CommonConsts.USER_INF_USER_ID);	// ユーザーID
		FormFile file_path = params.getParamFile("doc_file_path");

		viewBean.setFile(file_path);

		// -------------- バリデーション
		validate(viewBean, VALIDATE_CHECK.check_updateDocDetail);

		// -------------- 排他情報を取得
		Map<String, Object> exclusion = ExclusionUtil.getExclusionInfo(this, request);

		// -------------- ビジネスロジックの実行
		Tech3gDocSearchService service = (Tech3gDocSearchService) getBean(KEY_BEAN_SERV);
		service.updateDocDetail(userId, viewBean, exclusion);

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, KEY_SESS_VB);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, "正常に更新されました。");

		return CommonConsts.FW_SUCCESS;
	}

	public String deleteDocDetail(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gDocDetailBean viewBean = (Tech3gDocDetailBean) getSessionAttr(
												request, KEY_SESS_VB, Tech3gDocDetailBean.class);

		mergeInput2ViewBean(params, viewBean);									// 入力値のマージ

		// -------------- ビジネスロジックの実行
		Tech3gDocSearchService service = (Tech3gDocSearchService) getBean(KEY_BEAN_SERV);
		service.deleteDocDetail(viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, "正常に削除されました。");

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
	public String redrawDetailSrch(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gDocDetailBean viewBean = (Tech3gDocDetailBean) getSessionAttr(request, KEY_SESS_VB, Tech3gDocDetailBean.class);
		mergeInput2ViewBean(params, viewBean);

		// -------------- 出力値のセット
		request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	public String returnDocSearch(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		//Tech3gDocDetailBean viewBean = (Tech3gDocDetailBean) getSessionAttr(request, KEY_SESS_VB, Tech3gDocDetailBean.class);

		removeSessionAttr(request, KEY_SESS_VB);

		// -------------- 出力値のセット
		//request.setAttribute(CommonConsts.VIEW_BEAN, viewBean);
		return CommonConsts.FW_SUCCESS;
	}

	/**
	 * 相関バリデーション、単体バリデーション処理<br/> 相関バリデーションやStruts Validationでチェックできないバリデーションを行う。
	 *
	 * @param viewBean 画面表示用のBean
	 * @param check バリデーション対象処理
	 */
	private void validate(Tech3gDocDetailBean viewBean, VALIDATE_CHECK check) throws ApplicationException {

		// ApplicationExceptionインスタンスの生成
		ApplicationException _rtnError = new ApplicationException();

		// 該当者検索際のバリデーションチェック
		if (check.equals(VALIDATE_CHECK.check_updateDocDetail)) {

			if (StrUtil.isEmpty(viewBean.getDoc_ver())) {
				//throw new ApplicationException("[ 文書 Ver ]は、必須項目です。");
				_rtnError.addErrorCode("[ 文書 Ver ]は、必須項目です。");
			}

			if (!StrUtil.isEmpty(viewBean.getDoc_ver())) {
				String ver = StrUtil.removeAll(viewBean.getDoc_ver(), ".");
				if (!NumberUtil.isNumber(ver)) {
					_rtnError.addErrorCode("[ 文書 Ver. ]には、[数字 ] 及び [.]のみ、入力してください。");
				}
			}

			if (viewBean.getFile().getFileSize() == 0) {
				_rtnError.addErrorCode("[ 添付ファイル ]は、必須項目です。");
			}

			if (!StrUtil.isEmpty(viewBean.getComment())) {
				int cmntLen = StrUtil.getByteLenUTF8(viewBean.getComment());
				if (cmntLen > 2000) {
					_rtnError.addErrorCode("[ コメント欄 ]には、2000byte 以上は入力できません。");
				}
			}
		}

		// エラー存在する場合に、当画面に該当を返す。
		if (!_rtnError.isEmpty()) {
			// throwを行う前に該当エラーを格納する。
			viewBean.setCurrntErrorList(_rtnError.getErrorCodeList());
			// 該当エラーのthrow
			throw _rtnError;
		}
	}

	/* (non-Javadoc)
	 * @see liveany.common.web.BaseAction#customMergeInput2ViewBean(liveany.common.web.CommonParams, liveany.common.web.ViewBean)
	 */
	@Override
	protected void customMergeInput2ViewBean(CommonParams params, ViewBean viewBean) {

		Tech3gDocDetailBean _innerBean = (Tech3gDocDetailBean) viewBean;

		// 当画面のバリデーションエラーチェック
		if (_innerBean.getCurrntErrorList() == null
					|| _innerBean.getCurrntErrorList().size() == 0) {

			_innerBean.setCurrntErrorList(new ArrayList<MsgVO>());
		}
	}

	/**
	 * ・遷移先画面に引き渡すパラメタの設定を行う。
	 * <br/>
	 * @param request HttpServletRequest
	 * @param viewBean 画面表示用のBean
	 */
	private void setParamas(HttpServletRequest request, Tech3gDocDetailBean viewBean) {
	}

}
