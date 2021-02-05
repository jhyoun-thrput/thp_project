/**
 *
 */
package tech3g.fk.apps.doc.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.upload.FormFile;

import tech3g.common.biz.BaseService;
import tech3g.common.exceptions.ApplicationException;
import tech3g.common.util.BeanUtil;
import tech3g.common.util.ExclusionUtil;
import tech3g.common.util.PageInfoVO;
import tech3g.common.util.PagingUtil;
import tech3g.common.util.StrUtil;
import tech3g.fk.apps.doc.Tech3gBizLogic;
import tech3g.fk.apps.doc.TechDocUtil;
import tech3g.fk.apps.doc.actions.Tech3gDocDetailBean;
import tech3g.fk.apps.doc.actions.Tech3gDocSearchBean;
import tech3g.fk.apps.doc.services.Tech3gDocSearchService;
import tech3g.fk.dao.doc.Tech3gSearchDao;
import tech3g.fk.dao.doc.Tech3gUserDao;
import tech3g.fk.dao.doc.dto.Tech3gDocDetailDTO;
import tech3g.fk.dao.doc.dto.Tech3gDocDetailHistoryDTO;

/**
 * @author tech3g
 *
 */
public class Tech3gDocSearchServiceImpl extends BaseService implements
		Tech3gDocSearchService {

	/** 1ページに表示する行の数 */
	private static int LIST_ROW_CNT = 30;

	public void initTechDocSearch(Tech3gDocSearchBean viewBean) {
		// Init Process Logic

		Tech3gSearchDao dao = (Tech3gSearchDao) getBean(Tech3gSearchDao.KEY_BEAN);

		Tech3gUserDao user_dao = (Tech3gUserDao) getBean(Tech3gUserDao.KEY_BEAN);

		// LabelList
		viewBean.setTechDocCodeList(Tech3gBizLogic.getTechDocCodeInfo(dao, ""));

		viewBean.setUserIdList(Tech3gBizLogic.getUserIdList(user_dao));

		viewBean.setTechDocListPageInfo(null);

		viewBean.setCurrntErrorList(null);
	}

	public void searchTechDoc(Tech3gDocSearchBean viewBean, String pageNo)
			throws ApplicationException {

		int cnt = 0;
		List<ListOrderedMap> result = new ArrayList<ListOrderedMap>();

		Tech3gSearchDao dao = (Tech3gSearchDao) getBean(Tech3gSearchDao.KEY_BEAN);

		// get Doc Count
		cnt = Tech3gBizLogic.getTech3gDocInfoCnt(dao, viewBean.getP_doc_code().trim(), makeLikeSql("b.doc_name", viewBean.getP_doc_name()), viewBean.getP_regi_user().trim(), viewBean
												.getP_regi_date_from().trim(), viewBean.getP_doc_type().trim(), null, makeLikeSql("b.comment", viewBean.getP_doc_comment()));

		// ------- ページング計算
		PageInfoVO pageInfo = PagingUtil.getPageInfo(cnt, LIST_ROW_CNT, pageNo);

		result = Tech3gBizLogic.getTech3gDocInfo(dao, viewBean.getP_doc_code().trim(), makeLikeSql("b.doc_name", viewBean.getP_doc_name()), viewBean.getP_regi_user().trim(),
														viewBean.getP_regi_date_from().trim(), viewBean.getP_doc_type().trim(),
														null, makeLikeSql("b.comment", viewBean.getP_doc_comment()), (pageInfo.getStartNum() - 1), pageInfo.getListRowCnt());

		viewBean.setTechDocList(result);

		viewBean.setTechDocListPageInfo(pageInfo);

		viewBean.setCurrntErrorList(null);

		viewBean.setResultCnt(String.valueOf(cnt));
	}

	public void cnfirmtechDoc(Tech3gDocSearchBean viewBean)
			throws ApplicationException {
	}

	/**
	 * 文書詳細画面の初期処理
	 */
	public void initTechDocDetail(Tech3gDocDetailBean viewBean, Map<String, Object> exclusion, String pageNo) {

		Tech3gSearchDao dao = (Tech3gSearchDao) getBean(Tech3gSearchDao.KEY_BEAN);

		int cnt = 0;
		List<ListOrderedMap> result = new ArrayList<ListOrderedMap>();
		ListOrderedMap map = Tech3gBizLogic.getDocDetailInfo(dao, viewBean.getDoc_code(), viewBean.getDoc_index());

		// 排他情報を格納
		ExclusionUtil.setExclusionInfo(exclusion, map, dao.getObjectName(),	dao.getKeyField());

		if (!StrUtil.isNull(map)) {

			// 取得されたデータをviewBeanにコピーする。
			BeanUtil.populateIgnoreEmpty(viewBean, map);
			viewBean.setDoc_link_value(String.valueOf(map.get("doc_link")));

			// get Doc Count
			cnt = Tech3gBizLogic.getTech3gDocHistInfoCnt(dao, viewBean.getDoc_code(), viewBean.getDoc_index());

			// ------- ページング計算
			PageInfoVO pageInfo = PagingUtil.getPageInfo(cnt, LIST_ROW_CNT, pageNo);

			result = Tech3gBizLogic.getTech3gDocHistInfo(dao, viewBean.getDoc_code(), viewBean.getDoc_index(), (pageInfo.getStartNum() - 1), pageInfo.getListRowCnt());

			viewBean.setDocHistlList(result);
		}
	}

	/**
	 * 文書詳細情報の更新処理
	 */
	public void updateDocDetail(String userId, Tech3gDocDetailBean viewBean, Map<String, Object> exclusion) throws ApplicationException {

		Tech3gDocDetailDTO dto = new Tech3gDocDetailDTO();
		Tech3gDocDetailHistoryDTO hisDto = new Tech3gDocDetailHistoryDTO();

		Tech3gSearchDao dao = (Tech3gSearchDao) getBean(Tech3gSearchDao.KEY_BEAN);

		 // 排他チェック
//		 ExclusionUtil.checkExclusion(exclusion, (new String[]{ viewBean.getDoc_code(), viewBean.getDoc_index() }),
//				 													dao.getObjectName(),
//				 													dao.getKeyField(),
//				 													new ApplicationException("err.CO060"));
		int upt_cnt = 0;
		int hisinsrtCnt = 0;

		String newDocname = null;
		String link = null;

		File path = new File(viewBean.getDoc_link());
		FormFile file = viewBean.getFile();

		if ((path.exists() && path.isFile()) && file.getFileSize() > 0) {

			if (path.getName().trim().equals(file.getFileName().trim())) {
				int subCnt = file.getFileName().lastIndexOf(".");
				newDocname = new StringBuffer().append(file.getFileName().substring(0, subCnt))
																		.append("_new")
																		.append(file.getFileName().substring(subCnt))
																		.toString();
			} else {
				newDocname = file.getFileName();
			}

			link = new StringBuffer().append(path.getParent()).append("\\").append(newDocname).toString();
			// ------ File Upload
			try {

				FileOutputStream fos = new FileOutputStream(new File(path.getParent(), newDocname));
				BufferedOutputStream bos = new BufferedOutputStream(fos);

				bos.write(file.getFileData());
				bos.flush();
				bos.close();
				fos.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		viewBean.setDoc_name(newDocname);
		viewBean.setDoc_link(link);
		BeanUtil.copyProperties(dto, viewBean);

		ListOrderedMap hisMap = Tech3gBizLogic.getDocDetailInfo(dao, viewBean.getDoc_code(), viewBean.getDoc_index());
		BeanUtil.populate(hisDto, hisMap);

		hisinsrtCnt = Tech3gBizLogic.insertDocDetailHistory(dao, userId, hisDto);

		if (hisinsrtCnt > 0) {
			upt_cnt = Tech3gBizLogic.updateDocDetail(dao, userId, dto);
		}

		if (log.isDebugEnabled()) {
			log.debug(" History inserted Cnt :  " + hisinsrtCnt);
			log.debug(" 更新 Cnt :  " + upt_cnt);
		}
	}

	public void deleteDocDetail(Tech3gDocDetailBean viewBean) {

		Tech3gSearchDao dao = (Tech3gSearchDao) getBean(Tech3gSearchDao.KEY_BEAN);

		if (!StrUtil.isEmpty(viewBean.getDoc_code()) && !StrUtil.isEmpty(viewBean.getDoc_index())) {
			int delCnt = Tech3gBizLogic.deleteDocInfo(dao, viewBean.getDoc_code(), viewBean.getDoc_index());

			if (delCnt > 0) {
				log.debug("Delete Doc Detail Info Count : " + delCnt);
				File file = new File(viewBean.getDoc_link());
				if (file.isFile()) {
					file.delete();
				}
			}
		}
		else {
			// Exception
		}
	}

	private static String makeLikeSql(String colName, String val) {

		boolean isAnd = false;
		StringBuffer sql = new StringBuffer();
		String[] strArray = null;
		String value = val.trim();

		if (!StrUtil.isEmpty(value)) {

			if (!value.contains(TechDocUtil.STR_SPLIT_CHAR) && value.contains(TechDocUtil._BLANK)) {
				strArray = val.split(TechDocUtil._BLANK);
			}
			else if  (value.contains(TechDocUtil.STR_SPLIT_CHAR)) {
				strArray = val.split(TechDocUtil.STR_SPLIT_CHAR);
				isAnd = true;
			}
			else {
				strArray = new String[] {value};
			}

			for (int index = 0; index < strArray.length; index++) {
				String str = strArray[index].trim();

				if (StrUtil.isEmpty(str)) {
					continue;
				}
				if (index == 0) {
					sql.append(" '%");
					sql.append(str);
					sql.append("%' ");
				}
				else {
					if  (isAnd) {
						sql.append(" and ");
						sql.append(colName);
						sql.append(" like ");
						sql.append(" '%");
						sql.append(str);
						sql.append("%' ");
					}
					else {
						sql.append(" or ");
						sql.append(colName);
						sql.append(" like ");
						sql.append(" '%");
						sql.append(str);
						sql.append("%' ");
					}
				}
			}
		}
		return sql.toString();
	}

	public void searchTechDocInfoExcel(Tech3gDocSearchBean viewBean, String pageNo) throws ApplicationException {

		List<ListOrderedMap> result = new ArrayList<ListOrderedMap>();

		Tech3gSearchDao dao = (Tech3gSearchDao) getBean(Tech3gSearchDao.KEY_BEAN);

		result = Tech3gBizLogic.getTechDocInfoData(dao, viewBean.getP_doc_code().trim(), makeLikeSql("b.doc_name", viewBean.getP_doc_name()), viewBean.getP_regi_user().trim(),
																																														viewBean.getP_regi_date_from().trim(), viewBean.getP_doc_type().trim(),
																																														null, makeLikeSql("b.comment", viewBean.getP_doc_comment()));
		viewBean.setExcelList(result);
	}
}
