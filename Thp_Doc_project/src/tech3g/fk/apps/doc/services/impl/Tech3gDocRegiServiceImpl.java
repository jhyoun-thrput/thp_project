package tech3g.fk.apps.doc.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.upload.FormFile;

import tech3g.common.biz.BaseService;
import tech3g.common.util.BeanUtil;
import tech3g.common.util.StrUtil;
import tech3g.fk.apps.doc.Tech3gBizLogic;
import tech3g.fk.apps.doc.actions.Tech3gDocRegiBean;
import tech3g.fk.apps.doc.services.Tech3gDocRegiService;
import tech3g.fk.dao.doc.Tech3gSearchDao;
import tech3g.fk.dao.doc.dto.Tech3gRegiDocDTO;

public class Tech3gDocRegiServiceImpl extends BaseService implements Tech3gDocRegiService {

	private static List<FormFile> uploadFiles = new ArrayList<FormFile>();

	public void initTechDocRegi(Tech3gDocRegiBean viewBean) {

		Tech3gSearchDao dao = (Tech3gSearchDao) getBean(Tech3gSearchDao.KEY_BEAN);

		// LabelList
		viewBean.setTechDocCodeList(Tech3gBizLogic.getTechDocCodeInfo(dao, ""));
		viewBean.setDoc_ver("1.0.0");	// default value
	}

	public void insertDocRegi(String userId, Tech3gDocRegiBean viewBean) throws Exception {

		Tech3gRegiDocDTO dto = new Tech3gRegiDocDTO();

		Tech3gSearchDao dao = (Tech3gSearchDao) getBean(Tech3gSearchDao.KEY_BEAN);

		int insrt_cnt = 0;

		String newDocname = null;
		String docCodePath = null;
		String doc_index = null;
		String link = null;
		File tempFile = null;
		FormFile file = null;
		StringBuffer index_buffer = new StringBuffer();

		if (!StrUtil.isEmpty(viewBean.getDoc_code())) {
			if (!uploadFiles.isEmpty()) {
				log.debug("Upload FileList Size : " + uploadFiles.size());

				List <FormFile> upload_file = uploadFiles;
				for (int index = 0; index < upload_file.size(); index++) {
					file = upload_file.get(index);

					docCodePath = Tech3gBizLogic.getDocCodeLink(dao, viewBean.getDoc_code());
					 doc_index = Tech3gBizLogic.getCurrentDocInfoIndex(dao, viewBean.getDoc_code());
					newDocname = file.getFileName();

					if (!StrUtil.isEmpty(docCodePath)) {

						link = new StringBuffer().append(docCodePath).append("\\").append(file.getFileName()).toString();

						tempFile = new File(link);

						if (tempFile.exists() && tempFile.isFile()) {
							tempFile.delete();
							if (tempFile.getName().trim().equals(file.getFileName().trim())) {

								int subCnt =  file.getFileName().lastIndexOf(".");
								newDocname = new StringBuffer().append(file.getFileName().substring(0, subCnt))
																					.append("_new")
																					.append(file.getFileName().substring(subCnt))
																					.toString();

								link = new StringBuffer().append(docCodePath).append("\\").append(newDocname).toString();
							}
						}

						// ------ File Upload
						try {

							FileOutputStream fos = new FileOutputStream(new File(docCodePath, newDocname));
							BufferedOutputStream bos = new BufferedOutputStream(fos);

							bos.write(file.getFileData());
							bos.flush();
							bos.close();
							fos.close();

							viewBean.setDoc_name(newDocname);
							viewBean.setDoc_link(link);
							viewBean.setDoc_type(link.substring(link.lastIndexOf(".") + 1));
							viewBean.setDoc_index(doc_index);
							BeanUtil.copyProperties(dto, viewBean);

							insrt_cnt = Tech3gBizLogic.insertDocDetail(dao, userId, dto);

							if (log.isDebugEnabled()) {
								log.debug(" inserted Cnt :  " + insrt_cnt);
							}

							if (index == 0) {
								index_buffer.append(doc_index);
							} else {
								index_buffer.append(",");
								index_buffer.append(doc_index);
							}
						}
						catch (Exception e) {
							deleteFile(upload_file, docCodePath, newDocname);
							e.printStackTrace();
							throw e;
						}
					}
				}
			}
			uploadFiles.clear();
			viewBean.setIndex_field(index_buffer.toString());
		}
	}

	public void searchDocIndex(Tech3gDocRegiBean viewBean) {

		Tech3gSearchDao dao = (Tech3gSearchDao) getBean(Tech3gSearchDao.KEY_BEAN);

		ListOrderedMap map = Tech3gBizLogic.getDocInfoIndex(dao, viewBean.getDoc_code());

		if (!map.isEmpty()) {
			viewBean.setDoc_index(String.valueOf(map.get("doc_index")));
		}
	}

	public void confrmDocInfo(Tech3gDocRegiBean viewBean) {

		Tech3gSearchDao dao = (Tech3gSearchDao) getBean(Tech3gSearchDao.KEY_BEAN);
		List<ListOrderedMap> list = Tech3gBizLogic.getSelectedDocInfoList(dao, viewBean.getDoc_code(), viewBean.getIndex_field());

		viewBean.setTechDocList(list);
	}

	public void multiFileUpload(Tech3gDocRegiBean viewBean) throws Exception {

		if (viewBean.getFile().getFileSize() > 0 && !uploadFiles.contains(viewBean.getFile())) {
			uploadFiles.add(viewBean.getFile());
		}
		else {
			uploadFiles.clear();
		}
		viewBean.setUpload_cnt(uploadFiles.size());
		viewBean.setUploadFiles(uploadFiles);
	}

	public List<FormFile> getMultiFilesList() {
		return this.uploadFiles;
	}

	public void clearMultiFiles(Tech3gDocRegiBean viewBean) {

		if (uploadFiles.size() > 0 ) {
			uploadFiles.clear();
		}
		viewBean.setUploadFiles(null);
	}

	private static void deleteFile(List<FormFile> uploadList,  String path, String name) {
		File file = null;

		for (int index = 0; index < uploadList.size(); index++) {
			file = new File(path, uploadList.get(index).getFileName());

			if (file.exists() && file.isFile()) {
				file.delete();
			}
		}
	}
}
