package tech3g.fk.apps.doc.actions;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.ApplicationException;
import tech3g.common.exceptions.MsgVO;
import tech3g.common.exceptions.SystemException;
import tech3g.common.util.DateUtil;
import tech3g.common.util.FileUtil;
import tech3g.common.util.StrUtil;
import tech3g.common.web.BaseAction;
import tech3g.common.web.CommonParams;
import tech3g.common.web.ViewBean;
import tech3g.fk.apps.doc.TechDocUtil;
import tech3g.fk.apps.doc.services.Tech3gDocSearchService;

/**
 * <pre>
 * </pre>
 *
 * @author youn.j.h
 */
public class Tech3gDocSearchAction extends BaseAction {

	// --------------------------------------------------- 定数

	public static final String KEY_SESS_VB = "Tech3gDocSearchBean";

	public static final String KEY_BEAN_SERV = "Tech3gDocService";


	/** バリデーション判断用の処理名 */
	private static enum VALIDATE_CHECK {

		check_searchTechDoc

	}


	// --------------------------------------------------- インスタンスメソッド

	public String initTechDocSearch(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 該当ViewBeanをセッションから削除する。
		removeSessionAttr(request, KEY_SESS_VB);
		Tech3gDocSearchBean viewBean = new Tech3gDocSearchBean();

		// カスタムviewBean設定
		mergeInput2ViewBean(params, viewBean);

		// -------------- ビジネスロジックの実行
		Tech3gDocSearchService service = (Tech3gDocSearchService) getBean(KEY_BEAN_SERV);
		service.initTechDocSearch(viewBean);

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
	public String searchTechDoc(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gDocSearchBean viewBean = (Tech3gDocSearchBean) getSessionAttr(request, KEY_SESS_VB, Tech3gDocSearchBean.class);

		mergeInput2ViewBean(params, viewBean); // 入力値のマージ
		String pageNo = params.getParam(CommonConsts.KEY_PARAM_PAGENO); // 表示するページ数
		getParams(request, viewBean);

		// -------------- バリデーション
		validate(viewBean, VALIDATE_CHECK.check_searchTechDoc);

		// -------------- ビジネスロジックの実行
		Tech3gDocSearchService service = (Tech3gDocSearchService) getBean(KEY_BEAN_SERV);
		service.searchTechDoc(viewBean, pageNo);

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
	public String redrawRgSrch(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得及びマージ
		Tech3gDocSearchBean viewBean = (Tech3gDocSearchBean) getSessionAttr(request, KEY_SESS_VB, Tech3gDocSearchBean.class);
		mergeInput2ViewBean(params, viewBean);

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

		Tech3gDocSearchBean viewBean = new Tech3gDocSearchBean();
		// カスタムviewBean設定
		mergeInput2ViewBean(params, viewBean);

		File file = new File(viewBean.getDownFilePath());

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
			log.info(file.getName() + "を転送中、何かの理由で転送が中止されました。");
			// Lock 解除
			TechDocUtil.clearLock();
		} finally {
			bos.close();
			fis.close();
			// Lock 解除
			TechDocUtil.clearLock();
		}
		return null;
	}

	public String downloadExcelFormat(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map userMap) throws Exception {

		Tech3gDocSearchBean viewBean = (Tech3gDocSearchBean) getSessionAttr(request, KEY_SESS_VB, Tech3gDocSearchBean.class);
		// カスタムviewBean設定
		mergeInput2ViewBean(params, viewBean);

		String pageNo = params.getParam(CommonConsts.KEY_PARAM_PAGENO); // 表示するページ数
		String fileName = "Tech_doc_Info_result_" + DateUtil.getCurrentTimeStr() +".xls";

		// -------------- バリデーション
		validate(viewBean, VALIDATE_CHECK.check_searchTechDoc);

		// -------------- ビジネスロジックの実行
		Tech3gDocSearchService service = (Tech3gDocSearchService) getBean(KEY_BEAN_SERV);
		service.searchTechDocInfoExcel(viewBean, pageNo);

		response.setContentType(FileUtil.getContentType("xls"));
		response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, TechDocUtil.UTF_8) + "\"");

		createDocInfoToExcelData(response, viewBean.getExcelList());

		return null;
	}

	public String returnToDocDetail(HttpServletRequest request, HttpServletResponse response,
			CommonParams params, Map<String, Object> userMap) throws Exception {

		// -------------- 入力値の取得
		Tech3gDocSearchBean viewBean = (Tech3gDocSearchBean) getSessionAttr(request, KEY_SESS_VB, Tech3gDocSearchBean.class);

		int currentPageNo = viewBean.getTechDocListPageInfo().getPageNo();
		String pageNo = String.valueOf(currentPageNo);

		mergeInput2ViewBean(params, viewBean);

		// -------------- ビジネスロジックの実行
		Tech3gDocSearchService service = (Tech3gDocSearchService) getBean(KEY_BEAN_SERV);
		service.searchTechDoc(viewBean, pageNo);

		viewBean.getTechDocListPageInfo().setPageNo(currentPageNo);

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
	private void validate(Tech3gDocSearchBean viewBean, VALIDATE_CHECK check)
			throws ApplicationException {

		// ApplicationExceptionインスタンスの生成
		ApplicationException _rtnError = new ApplicationException();

		// 該当者検索際のバリデーションチェック
		if (check.equals(VALIDATE_CHECK.check_searchTechDoc)) {

			// せめて一つは検索条件として入力したかチェックする。
			if (StrUtil.isEmpty(viewBean.getP_doc_code())
					&& StrUtil.isEmpty(viewBean.getP_doc_name())
					&& StrUtil.isEmpty(viewBean.getP_doc_type())
					&& StrUtil.isEmpty(viewBean.getP_regi_date_from())
					&& StrUtil.isEmpty(viewBean.getP_doc_ver())
					&& StrUtil.isEmpty(viewBean.getP_regi_user())
					&& StrUtil.isEmpty(viewBean.getP_doc_comment())) {

				throw new ApplicationException("err.CO045");
			}

			if (!StrUtil.isEmpty(viewBean.getP_regi_date_from())) {
				String inptdate =  StrUtil.removeAll(viewBean.getP_regi_date_from(), "-");
				String current_date = DateUtil.getToday();
				if (!DateUtil.isYYYYMMDD(inptdate)) {
					_rtnError.addErrorCode("[YYYY-MM-DD] 形式で、入力してください。");
				}

				if (Integer.valueOf(inptdate) >  Integer.valueOf(current_date)) {
					_rtnError.addErrorCode("本日 [ " + viewBean.getP_regi_date_from() + "] より、以後の日付は入力できません。");
				}
			}
		}

		// エラー存在する場合に、当画面に該当を返す。
		if (!_rtnError.isEmpty()) {
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

		Tech3gDocSearchBean _innerBean = (Tech3gDocSearchBean) viewBean;

		// 当画面のバリデーションエラーチェック
		if (_innerBean.getCurrntErrorList() == null
					|| _innerBean.getCurrntErrorList().size() == 0) {

			_innerBean.setCurrntErrorList(new ArrayList<MsgVO>());
		}
	}

	private void getParams(HttpServletRequest request, Tech3gDocSearchBean viewBean) {

		String doc_name = takeParam(request, "doc_name");
		String currentTime = takeParam(request, "p_regi_date_from");

		if (!StrUtil.isEmpty(doc_name)) {
			viewBean.setP_doc_name(doc_name);
			viewBean.setP_doc_code("");
		}
		if (!StrUtil.isEmpty(currentTime)) {
			viewBean.setP_regi_date_from(currentTime);
		}
	}

	/**
	 * ・遷移先画面に引き渡すパラメタの設定を行う。
	 * <br/>
	 * @param request HttpServletRequest
	 * @param viewBean 画面表示用のBean
	 */
	private void setParamas(HttpServletRequest request, Tech3gDocSearchBean viewBean) {
	}

	private void createDocInfoToExcelData(HttpServletResponse response, List<ListOrderedMap> list) throws IOException, WriteException, ApplicationException {

		ListOrderedMap dataMap = null;

		if (!list.isEmpty()) {
			WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = workbook.createSheet("TechDocList_" + DateUtil.getCurrentTimeStr(), 0);

			WritableCellFormat numFormat = new WritableCellFormat();
			WritableCellFormat titleFormat = new WritableCellFormat();
			WritableCellFormat dataFormat = new WritableCellFormat();
			WritableCellFormat dataFormat2 = new WritableCellFormat();

			// Number Format Define
			numFormat.setAlignment(Alignment.CENTRE);
			numFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			numFormat.setBorder(Border.BOTTOM, jxl.format.BorderLineStyle.HAIR);

			// Title Format Define
			titleFormat.setAlignment(Alignment.CENTRE);
			titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			titleFormat.setBorder(Border.BOTTOM,  jxl.format.BorderLineStyle.MEDIUM);
			titleFormat.setBackground(Colour.PALE_BLUE);

			// Data Format Define
			dataFormat.setAlignment(Alignment.CENTRE);
			dataFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			dataFormat.setBorder(Border.BOTTOM,  jxl.format.BorderLineStyle.HAIR);
			
			// Data Format Define#2
			dataFormat2.setAlignment(Alignment.LEFT);
			dataFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
			dataFormat2.setBorder(Border.BOTTOM, jxl.format.BorderLineStyle.HAIR);

			// Tilte
			jxl.write.Label numLable = new jxl.write.Label(0, 0, "No", titleFormat);
			jxl.write.Label docName = new jxl.write.Label(1, 0, "Doc Name", titleFormat);
			jxl.write.Label regiUser = new jxl.write.Label(2, 0, "Regi User", titleFormat);
			jxl.write.Label regiDate = new jxl.write.Label(3, 0, "Regi Date", titleFormat);
			jxl.write.Label uptUser = new jxl.write.Label(4, 0, "Update User", titleFormat);
			jxl.write.Label uptDate = new jxl.write.Label(5, 0, "Update Date", titleFormat);
			jxl.write.Label docType = new jxl.write.Label(6, 0, "Doc Type", titleFormat);
			jxl.write.Label docVer = new jxl.write.Label(7, 0, "Doc Version", titleFormat);
			
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 70);
			sheet.setColumnView(2, 10);
			sheet.setColumnView(3, 21);
			sheet.setColumnView(4, 10);
			sheet.setColumnView(5, 21);
			sheet.setColumnView(6, 8);
			sheet.setColumnView(7, 10);

			// Title Set
			sheet.addCell(numLable);
			sheet.addCell(docName);
			sheet.addCell(regiUser);
			sheet.addCell(regiDate);
			sheet.addCell(uptUser);
			sheet.addCell(uptDate);
			sheet.addCell(docType);
			sheet.addCell(docVer);

			Label num = null;
			Label name = null;
			Label rgUser = null;
			Label rgDate = null;
			Label upUser = null;
			Label upDate = null;
			Label type = null;
			Label ver = null;

			for (int index = 0; index < list.size();index++) {
				dataMap = list.get(index);
				int rowIndex = index + 1;

				// get Doc Info Data
				num = new Label(0, rowIndex, String.valueOf(dataMap.get("rownum")), numFormat);
				name = new Label(1, rowIndex, String.valueOf(dataMap.get("doc_name")), dataFormat2);
				rgUser = new Label(2, rowIndex, String.valueOf(dataMap.get("regi_user")==null?"":dataMap.get("regi_user")), dataFormat);
				rgDate = new Label(3, rowIndex, String.valueOf(dataMap.get("regi_date")==null?"":dataMap.get("regi_date")), dataFormat);
				upUser = new Label(4, rowIndex, String.valueOf(dataMap.get("upt_user")==null?"":dataMap.get("upt_user")), dataFormat);
				upDate = new Label(5, rowIndex, String.valueOf(dataMap.get("upt_date")==null?"":dataMap.get("upt_date")), dataFormat);
				type = new Label(6, rowIndex, String.valueOf(dataMap.get("doc_type")), dataFormat);
				ver = new Label(7, rowIndex, String.valueOf(dataMap.get("doc_ver")), dataFormat);

				// Set Data
				sheet.addCell(num);
				sheet.addCell(name);
				sheet.addCell(rgUser);
				sheet.addCell(rgDate);
				sheet.addCell(upUser);
				sheet.addCell(upDate);
				sheet.addCell(type);
				sheet.addCell(ver);
			}

			workbook.write();
			workbook.close();
		}
		else {
			throw new ApplicationException("*Download Data が存在しません。");
		}
	}
}
