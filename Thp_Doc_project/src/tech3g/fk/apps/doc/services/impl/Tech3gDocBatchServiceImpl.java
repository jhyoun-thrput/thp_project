/**
 *
 */
package tech3g.fk.apps.doc.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;

import tech3g.common.biz.BaseService;
import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.ApplicationException;
import tech3g.common.util.BeanUtil;
import tech3g.common.util.DateUtil;
import tech3g.common.util.FileUtil;
import tech3g.common.util.PageInfoVO;
import tech3g.common.util.PagingUtil;
import tech3g.common.util.StrUtil;
import tech3g.fk.apps.doc.Tech3gBizLogic;
import tech3g.fk.apps.doc.actions.Tech3gDocBatchBean;
import tech3g.fk.apps.doc.actions.Tech3gDocBatchRegiBean;
import tech3g.fk.apps.doc.services.Tech3gDocBatchService;
import tech3g.fk.dao.doc.Tech3gBatchDao;
import tech3g.fk.dao.doc.Tech3gSearchDao;
import tech3g.fk.dao.doc.Tech3gStatisSpbExceptionDao;
import tech3g.fk.dao.doc.dto.Tech3gBatchDTO;
import tech3g.fk.dao.doc.dto.Tech3gBatchRegiDTO;
import tech3g.fk.dao.doc.dto.Tech3gRegiDocDTO;
import tech3g.fk.dao.doc.dto.Tech3gSpbExcetionDTO;

/**
 * @author tech3g
 *
 */
public class Tech3gDocBatchServiceImpl extends BaseService implements
		Tech3gDocBatchService {

	// Tech3g Documents Main Code List
	private static ArrayList<File> parentDocLinkList = new ArrayList<File>();
	private static ArrayList<File> techDocLinkList = new ArrayList<File>();


	/** バリデーション判断用の処理名 */
	private static enum VALIDATE_CHECK {
		/** 検索 */
		searchBatchList,
		/** 実行 */
		exeBatch,
		/** ファイルダウンロード */
		downloadLogFile
	}

	public void initExeBatch(Tech3gDocBatchBean viewBean, String pageNo) {

		int cnt = 0;
		List<ListOrderedMap> list = null;
		Tech3gBatchDao dao = (Tech3gBatchDao) getBean(Tech3gBatchDao.KEY_BEAN);

		// get Count
		cnt = Tech3gBizLogic.searchBatchInfoCnt(dao, viewBean.getBt_m_bt_id().trim(), "", "", "");

		// ------- ページング計算
		PageInfoVO pageInfo = PagingUtil.getPageInfo(cnt, 10, pageNo);

		list = Tech3gBizLogic.searchBatchInfoList(dao, viewBean.getBt_m_bt_id().trim(), "", "", "",
				(pageInfo.getStartNum() - 1), pageInfo.getListRowCnt());

		viewBean.setBtList(list);
		viewBean.setBtListPagingInf(pageInfo);
		viewBean.setBatchDto(null);
	}

	public void showBatchDetailInfo(String btId, String btNo,
			Tech3gDocBatchBean viewBean) {

		Tech3gBatchDao dao = (Tech3gBatchDao) getBean(Tech3gBatchDao.KEY_BEAN);

		ListOrderedMap map = (ListOrderedMap) Tech3gBizLogic.getBtachInfo(dao, btId, btNo);

		Tech3gBatchDTO dto = new Tech3gBatchDTO();

		// 取得されたデータをDTOにコピーする。
		BeanUtil.populateIgnoreEmpty(dto, map);

		viewBean.setBatchDto(dto);

	}

	public Map downloadLogFile(String btId, String btNo)
			throws ApplicationException {
		return null;
	}

	public void exeBatch(String userId, Tech3gDocBatchBean viewBean) throws Exception {

		String doc_root_path = null;
		String doc_code_link = null;
		String inputPath = null;
		String inputDocInfoPath = null;

		int docInsrtCnt = 0;
		int docInfoCnt = 0;
		int docCodeInsrtSum = 0;
		int insrtCntToT = 0;
		int insertBtCnt = 0;
		int uptbatchCnt = 0;
		int parentFileCnt = 0;
//		int delDocCodeCnt = 0;
//		int delDocInfoCnt = 0;

		File path = null;
		String indexCode = null;
		boolean isFullScanInserting = false;
		boolean isNewDocCodeInserting = false;
		boolean isDocInfoInserting = false;
		boolean isLogFileDelete = false;


		ArrayList<File> docLinks = null;
		ArrayList<File> parentDocLinks = null;
		List<ListOrderedMap> docCodeList = null;

		Tech3gSearchDao dao = (Tech3gSearchDao) getBean(Tech3gSearchDao.KEY_BEAN);
		Tech3gBatchDao btDao = (Tech3gBatchDao) getBean(Tech3gBatchDao.KEY_BEAN);
		Tech3gStatisSpbExceptionDao sDao =(Tech3gStatisSpbExceptionDao) getBean(Tech3gStatisSpbExceptionDao.KEY_BEAN);

		if (!StrUtil.isEmpty(viewBean.getSelectedBt_flg())) {

			// Tech Documents Batch
			if ("1".equals(viewBean.getSelectedBt_flg())) {

				try {

					if (!StrUtil.isEmpty(viewBean.getDoc_file_path())) {
						path = new File(viewBean.getDoc_file_path());
						inputPath = path.getPath();

						if (path.exists() && path.isDirectory()) {
							docCodeList = Tech3gBizLogic.getDocCodeList(dao);

							for (int index  = 0; index < docCodeList.size(); index++) {
								ListOrderedMap map = docCodeList.get(index);

								doc_root_path = String.valueOf(map.get("doc_root_path"));
								doc_code_link = String.valueOf(map.get("doc_code_link"));

								if (inputPath.contains(doc_root_path)) {
									if (inputPath.equals(doc_root_path)) {
										// Full Inserting
										//delDocCodeCnt = Tech3gBizLogic.deleteDocCode(dao, "");
//										if (delDocCodeCnt > 0) {
//											delDocInfoCnt = Tech3gBizLogic.deleteDocInfo(dao, "", "");
//											log.debug("Delete Doc Code Count : " + delDocCodeCnt);
//											log.debug("Delete Doc Code Info Count : " + delDocInfoCnt);
//										}
										isFullScanInserting = true;
										break;
									}

									if (inputPath.contains(doc_code_link)) {
										// Doc Info Insering
										isDocInfoInserting = true;
										inputDocInfoPath = doc_code_link;
										break;
									}

									if ((index + 1) == docCodeList.size()) {
										// New Doc Code Insering
										isNewDocCodeInserting = true;
										break;
									}
								}

								// New RootPath Full Inserting
								if ((index + 1) == docCodeList.size()) {
									isFullScanInserting = true;
								}
							}

							// Start Batch History
							insertBtCnt = Tech3gBizLogic.registBatchInfo(btDao, userId, getBatchInfo(true, btDao, viewBean.getBt_m_bt_id(), "", ""));

							parentDocLinkList.clear();
							parentDocLinks = getParentDocLink(viewBean.getDoc_file_path());
							if (parentDocLinks != null && parentDocLinks.size() > 0) {
								for (int index = 0; index < parentDocLinks.size(); index++) {

									String pName = parentDocLinks.get(index).getName();
									String pLink = parentDocLinks.get(index).getPath();
									String rootPath =  parentDocLinks.get(index).getParent();

									if (isFullScanInserting) {
										if (Tech3gBizLogic.isTechDocCodeLink(dao, pLink, inputPath)) {
											indexCode = Tech3gBizLogic.getSelectDocCode(dao, pLink);
										}
										else {
											indexCode = getCurrentDocCode(dao);
										}
										// TechDoc Code Inserting
										docInsrtCnt = registTechDocCode(btDao, userId, indexCode, pName, pLink, rootPath);
									}
									if (isNewDocCodeInserting) {
										indexCode = getCurrentDocCode(dao);
										docInsrtCnt = registTechDocCode(btDao, userId, indexCode, pName, pLink, rootPath);
									}
									if (isDocInfoInserting) {
										indexCode = Tech3gBizLogic.getSelectDocCode(dao, inputDocInfoPath);
									}
									if (index == 0) {
										parentFileCnt = registParentFiles(path, btDao, userId, indexCode);
										if (log.isDebugEnabled()) {
											log.debug(" Total parentFile Count : [ " + parentFileCnt + " ]");
										}
									}

									// TechDoc Info Inserting
									if (docInsrtCnt > 0) {
										docCodeInsrtSum++;
									}
									if (log.isDebugEnabled()) {
										log.debug("[ Time : " + new Date() + "] [ Insert Cnt : " + docCodeInsrtSum + " [ Tech3g Doc Code : " + indexCode + " ] [ Parent Directory Name : " + pName + " ]");
									}

									// Tech Doc Code に紐付くDocument info Linkを取得する。
									techDocLinkList.clear();
									docLinks = searchDocLink(parentDocLinks.get(index));

									if (docLinks != null && docLinks.size() > 0) {
										int docInfoInsrtSum = 0;
										for (int child_index = 0; child_index < docLinks.size(); child_index++) {

											// inserting Child Doc Info
											docInfoCnt = registTechDocInfo(btDao, userId, indexCode, docLinks.get(child_index));

											if (docInfoCnt > 0) {
												docInfoInsrtSum++;

												if (log.isDebugEnabled()) {
													log.debug(" [ Time : " + new Date() + "] [ InsertCnt : " + docInfoInsrtSum + " ] [ Parent Directory Name : " + pName + " ] [ Tech3g Child Doc Name : " + docLinks.get(child_index).getName() + " ] ");
												}
											}
										}
										insrtCntToT += docInfoInsrtSum;
									}
								}
								if (log.isDebugEnabled()) {
									log.debug(" Total Initial parentFileCnt Count : [ " + parentFileCnt + " ]");
									log.debug(" Total Parent Doc Code Inserted Count : [ " + docCodeInsrtSum + " ]");
									log.debug(" Total Document Inserted Count : [ " + insrtCntToT + " ]");
									log.debug(" Batch Initial Inserted Count : [ " + insertBtCnt + " ]");
								}

//								if (parentDocLinks.size() == docCodeInsrtSum) {
								uptbatchCnt = Tech3gBizLogic.updateBatchInfo(btDao, userId, getBatchInfo(false, btDao, viewBean.getBt_m_bt_id(), CommonConsts.BT_STT_SUCCESS, ""));
								log.debug(" Batch Success updated Count : [ " + uptbatchCnt + " ]");
//								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (insertBtCnt > 0) {
						uptbatchCnt = Tech3gBizLogic.updateBatchInfo(btDao, userId, getBatchInfo(false, btDao, viewBean.getBt_m_bt_id(), CommonConsts.BT_STT_FAIL, e.getMessage()));
					}

					if (log.isDebugEnabled()) {
						log.debug(" Batch Fail Updated Count : [ " + uptbatchCnt + " ]" );
					}
					throw e;
				}
			}
			// 一般 Batch
			else if ("2".equals(viewBean.getSelectedBt_flg())) {

				FileChannel fc = null;
				MappedByteBuffer byteBuffer = null;
				Scanner scan = null;

				Process bt_proc  = null;
				BufferedReader br = null;
				File logFile = null;
				File logDir = null;
				String pattern = "getSPBMoetermFullLog";
				String logPath = null;

				if (!StrUtil.isEmpty(viewBean.getDoc_file_path())) {

					// Start Batch History
					insertBtCnt = Tech3gBizLogic.registBatchInfo(btDao, userId, getBatchInfo(true, btDao, viewBean.getBt_m_bt_id(), "", ""));
					try {

						bt_proc =  Runtime.getRuntime().exec(viewBean.getDoc_file_path());
						br = new BufferedReader(new InputStreamReader(bt_proc.getInputStream()));

						String line = "";
						while ((line = br.readLine()) != null) {
							log.debug(" ## Batch Execute Log : " +  line);
							System.out.println("## Batch Execute Log : " + line);
						}

						int result = bt_proc.exitValue();
						if (result == 0) {
							// Success
							logPath = "C:\\macro\\spb_exception_tool\\log";
							logDir = new File(logPath);

							if (logDir.isDirectory()) {
								File[] logList = logDir.listFiles();
								for (int index = 0; index < logList.length; index++) {
									logFile = logList[index];

									if (logFile.isFile() &&  (logFile.getName().contains(pattern) && logFile.getName().lastIndexOf("txt") > 0 )) {

										fc = new FileInputStream(logFile.getPath()).getChannel();
										byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
										Charset charset = Charset.forName(CommonConsts.SYS_ENC);
										CharsetDecoder decoder = charset.newDecoder();
										CharBuffer charBuffer = decoder.decode(byteBuffer);
										scan = new Scanner(charBuffer).useDelimiter(CommonConsts.NEW_LINE);

										// Inserting Spb Exception
										int excpCnt = regiSpbExceptionData(sDao, scan, userId);
										log.debug("### Total Spb Exception Inserted Count : " + excpCnt );

										if (excpCnt > 0) {
											isLogFileDelete = true;
										}
									}
									else {
										deleteLogFiles(logPath);
										throw new ApplicationException("該当 File が存在しません。！！");
									}
								}
							}
							else {
								deleteLogFiles(logPath);
								throw new ApplicationException("該当 Directory が存在しません。！！");
							}
							uptbatchCnt = Tech3gBizLogic.updateBatchInfo(btDao, userId, getBatchInfo(false, btDao, viewBean.getBt_m_bt_id(), CommonConsts.BT_STT_SUCCESS, ""));
							log.debug(" Batch Success updated Count : [ " + uptbatchCnt + " ]");
						}
						else {
							// Fail
							uptbatchCnt = Tech3gBizLogic.updateBatchInfo(btDao, userId, getBatchInfo(false, btDao, viewBean.getBt_m_bt_id(), CommonConsts.BT_STT_FAIL, ""));
							log.debug(" Batch Fail Updated Count : [ " + uptbatchCnt + " ]" );
						}
					}
					catch (Exception e) {
						e.printStackTrace();
						deleteLogFiles(logPath);
						if (insertBtCnt > 0) {
							uptbatchCnt = Tech3gBizLogic.updateBatchInfo(btDao, userId, getBatchInfo(false, btDao, viewBean.getBt_m_bt_id(), CommonConsts.BT_STT_FAIL, e.getMessage()));
						}
						if (log.isDebugEnabled()) {
							log.debug(" Batch Fail Updated Count : [ " + uptbatchCnt + " ]" );
						}
						throw e;
					}
					finally {
						if (scan != null) {
							scan.close();
						}
						if (fc != null) {
							fc.close();
						}
					}
					if (isLogFileDelete) {
						deleteLogFiles(logPath);
					}
				}
			}
			// その他 Batch
			else {
				// add Logic
			}
		}
	}

	/**
	 * Parent Directory を取得する。
	 *
	 * @param dirPath
	 * @return parent link list
	 */
	private static ArrayList<File> getParentDocLink(String dirPath) {

		File parentDir = new File(dirPath);
		File[] fileList = null;

		if (parentDir.exists() && parentDir.isDirectory()) {
			fileList = parentDir.listFiles();

			if (fileList.length > 0) {
				for (int index = 0; index < fileList.length; index++) {
					if (fileList[index].exists() && fileList[index].isDirectory()) {
						parentDocLinkList.add(fileList[index]);
					}
				}
				if (parentDocLinkList.isEmpty()) {
					parentDocLinkList.add(parentDir);
				}
			}
		}
		return parentDocLinkList;
	}

	private static int registParentFiles(File parentPath, Tech3gBatchDao dao, String userId, String doc_code) throws Exception {

		int result = 0;
		File[] fileList = null;

		if (parentPath.exists() && parentPath.isDirectory()) {
			fileList = parentPath.listFiles();

			if (fileList.length > 0) {
				for (int i = 0; i < fileList.length; i++) {
					if (fileList[i].isFile()) {
						result += registTechDocInfo(dao, userId, doc_code, fileList[i]);
					}
				}
			}
		}
		return result;
	}
	/**
	 *
	 * @param dao
	 * @param userId
	 * @param indexNo
	 * @param parentName
	 * @param parentLink
	 * @return
	 */
	private static int registTechDocCode(Tech3gBatchDao dao, String userId,
			String doc_code, String parentName, String parentLink, String docRootLink) throws Exception {

		int insrtNo = 0;

		Tech3gRegiDocDTO dto = new Tech3gRegiDocDTO();
		String msg = new StringBuffer().append("Tech Doc Code Inserting [ doc_code : ")
				.append(doc_code + " ] [ " + parentName + " ] \n")
				.append(" Inserted Time [ " + DateUtil.getCurrentTimeStr2() + " ] \n")
				.append(" Execute User [ " + userId + " ] ").toString();

		dto.setDoc_code(doc_code);
		dto.setDoc_name(parentName);
		dto.setDoc_link(parentLink);
		dto.setRegi_user(userId);
		dto.setDoc_root_path(docRootLink);
		dto.setComment(msg);

		// Inserting TechDocCode
		//insrtNo = Tech3gBizLogic.insertDocCode(dao, userId, dto);
		insrtNo = Tech3gBizLogic.mergeDocCode(dao, userId, dto);

		return insrtNo;
	}

	/**
	 *  Inserting Child Doc Info
	 * @param indexNo
	 * @param childDocInfo
	 * @return Inserted Cnt
	 */
	private static int registTechDocInfo(Tech3gBatchDao dao, String userId, String doc_code, File childDocInfo) throws Exception {

		int insrtNo = 0;
		String ver_default = "1.0.0";
		String doc_type = null;
		Tech3gRegiDocDTO dto = new Tech3gRegiDocDTO();

		String msg = new StringBuffer().append("Tech Doc Detail Info Inserting [ doc_code : ")
		.append(doc_code + " ] [ " + childDocInfo.getName() + " ] \n")
		.append(" Inserted Time [ " + DateUtil.getCurrentTimeStr2() + " ] \n")
		.append(" Execute User [ " + userId + " ] ").toString();

		if (childDocInfo.getName().lastIndexOf(".") < 0) {
			doc_type = "unknown";
		}
		else {
			doc_type = childDocInfo.getName().substring(childDocInfo.getName().lastIndexOf(".")+1);
			if (doc_type.length() > 40) {
				doc_type = "unKnown";
			}
		}

		dto.setDoc_code(doc_code);
		dto.setDoc_name(childDocInfo.getName());
		dto.setDoc_link(childDocInfo.getPath());
		dto.setDoc_index(Tech3gBizLogic.getCurrentDocIndex(dao, dto.getDoc_code(), dto.getDoc_link()));
		dto.setDoc_type(doc_type);
		dto.setRegi_user(userId);
		dto.setDoc_ver(ver_default);
		dto.setComment(msg);

		if (FileUtil.isContentType(doc_type.trim())) {
			// Inserting Child Doc Info
			//insrtNo = Tech3gBizLogic.insertDocDetailBatch(dao, userId, dto);
			insrtNo = Tech3gBizLogic.mergeDocInfo(dao, userId, dto);
		}
		return insrtNo;
	}

	/**
	 * 指定されたPath配下のすべてのファイル情報を取得する。
	 * @param root
	 */
	private static ArrayList<File> searchDocLink(File root) {

		// File
		if (root.isFile()) {
			techDocLinkList.add(root);
		}

		// Directory
		else {
			File[] files = root.listFiles();
			for (int index = 0; index < files.length; index++) {
				if (files[index].isDirectory()) {

					// reCall Method
					searchDocLink(files[index]);
				} else {

					if (files[index].isFile()) {

						// Input the ArrayList is Only File
						techDocLinkList.add(files[index]);
					}
				}
			}
		}
		return techDocLinkList;
	}

	private static Tech3gBatchDTO getBatchInfo(boolean isInsrt, Tech3gBatchDao dao, String bt_id, String status, String errMsg) {

		Tech3gBatchDTO dto = new Tech3gBatchDTO();
		ListOrderedMap map = null;
		String maxBtNo = null;

		if (isInsrt) {

			map = Tech3gBizLogic.getMasterBatchInfo(dao, bt_id);
		}
		else {
			maxBtNo = Tech3gBizLogic.getMaxBtNo(dao, bt_id);
			if (!StrUtil.isEmpty(maxBtNo)) {
				map = (ListOrderedMap) Tech3gBizLogic.getBtachInfo(dao, bt_id, maxBtNo);
			}
		}
		BeanUtil.populateIgnoreEmpty(dto, map);

		if (!StrUtil.isEmpty(status)) {
			if (CommonConsts.BT_STT_SUCCESS.equals(status)) {
				dto.setBt_status(CommonConsts.BT_STT_SUCCESS);
				dto.setBt_msg("Doc Batch Execute Complete ");
			}
			else if (CommonConsts.BT_STT_FAIL.equals(status)) {
				dto.setBt_status(CommonConsts.BT_STT_FAIL);
				dto.setDtl_bt_status(CommonConsts.BT_STT_FAIL);
				if (!StrUtil.isEmpty(errMsg)) {
					dto.setBt_msg(errMsg);
					dto.setDtl_bt_msg(errMsg);
				}
			}
		}
		return dto;
	}

	public void initBtach(Tech3gDocBatchBean viewBean) {

		List<ListOrderedMap> list = null;
		Tech3gBatchDao dao = (Tech3gBatchDao) getBean(Tech3gBatchDao.KEY_BEAN);

		list = Tech3gBizLogic.searchMasterBatchList(dao, viewBean.getBt_id());

		viewBean.setMasterBtList(list);
	}

	private static String getCurrentDocCode(Tech3gSearchDao dao) {

		String rtnCode = null;

		int maxDocCode = Integer.parseInt(Tech3gBizLogic.getMaxTechDocCode(dao));

		if (maxDocCode < 10) {
			rtnCode = new StringBuffer().append("0").append(maxDocCode).toString();
		} else {
			rtnCode = new StringBuffer().append(maxDocCode).toString();
		}
		return rtnCode;
	}


	public void deleteBatchMaster(Tech3gDocBatchBean viewBean) {
		Tech3gBatchDao dao = (Tech3gBatchDao) getBean(Tech3gBatchDao.KEY_BEAN);

		int delCnt = Tech3gBizLogic.deleteBatchMaster(dao, viewBean.getBt_m_bt_id());
	}

	//**************************** Batch Regi Zone **************************************

	public void initBatchRegi(Tech3gDocBatchRegiBean viewBean) {

		Tech3gBatchDao dao = (Tech3gBatchDao) getBean(Tech3gBatchDao.KEY_BEAN);

		if (StrUtil.isEmpty(viewBean.getP_bt_id())) {

			// Insert
			viewBean.setRegi_date(DateUtil.getCurrentTimeStr2());
		}
		else {
			ListOrderedMap map = Tech3gBizLogic.getMasterBatchInfo(dao, viewBean.getP_bt_id());

			if (!map.isEmpty()) {
				BeanUtil.populateIgnoreEmpty(viewBean, map);
			}
		}
		viewBean.setBatchFlgList(getBatchFlgList());
	}

	public void insertBatchRegi(String user_id, Tech3gDocBatchRegiBean viewBean) throws ApplicationException {

		Tech3gBatchDao dao = (Tech3gBatchDao) getBean(Tech3gBatchDao.KEY_BEAN);

		int insrtCnt = 0;

		Tech3gBatchRegiDTO dto = new Tech3gBatchRegiDTO();
		BeanUtil.copyProperties(dto, viewBean);

		ListOrderedMap map = Tech3gBizLogic.getMasterBatchInfo(dao, dto.getBt_id().trim());

		if (!map.isEmpty()) {
			throw new ApplicationException("入力した Batch ID [" + map.get("bt_id") + "]は、既に登録済みです。 ");
		}

		insrtCnt = Tech3gBizLogic.insertBatchMaster(dao, user_id, dto);

		if (insrtCnt > 0) {
			log.debug("Inserted Batch Master Count : " + insrtCnt);
		}
	}

	public void updateBatchRegi(String user_id, Tech3gDocBatchRegiBean viewBean) {

		Tech3gBatchDao dao = (Tech3gBatchDao) getBean(Tech3gBatchDao.KEY_BEAN);

		Tech3gBatchRegiDTO dto = new Tech3gBatchRegiDTO();
		BeanUtil.copyProperties(dto, viewBean);

		int uptCnt = Tech3gBizLogic.updateBatchMaster(dao, user_id, dto);

		if (uptCnt > 0) {
			log.debug("Updated Batch Master Count : " + uptCnt);
		}
	}

	public void batchConfirm(Tech3gDocBatchRegiBean viewBean) {

		Tech3gBatchDao dao = (Tech3gBatchDao) getBean(Tech3gBatchDao.KEY_BEAN);
		ListOrderedMap map = Tech3gBizLogic.getMasterBatchInfo(dao, viewBean.getBt_id());

		if (!map.isEmpty()) {
			BeanUtil.populateIgnoreEmpty(viewBean, map);
		}
	}

	private List<LabelValueBean> getBatchFlgList() {

		List<LabelValueBean> btFlgList = new ArrayList<LabelValueBean>();
		LabelValueBean bean = null;
		String[] bt_flg_names = {"文書システム", "一般システム", "その他"};

		for (int index = 0; index < bt_flg_names.length; index++ ) {

			bean = new LabelValueBean();
			bean.setLabel(bt_flg_names[index]);
			bean.setValue(String.valueOf(index + 1));

			btFlgList.add(bean);
		}
		return btFlgList;
	}

	private static int regiSpbExceptionData(Tech3gStatisSpbExceptionDao dao, Scanner scan, String user_id) {

		final String FIX_EXCEPTION = "EXCEPTION";
		final String FIX_SPB_IP = "SPB_IP:";
		final String FIX_SPB_NAME = "SPB_NAME:";
		final String FIX_TIME_STAMP = "Time Stamp:";

		Tech3gSpbExcetionDTO dto = null;

		String line = "";
		String bs_ip = "";
		String bs_name = "";
		String time_stamp = "";
		int insertCnt = 0;

		boolean isException = false;

		if (!StrUtil.isNull(scan)) {
			while (scan.hasNext()) {
				line = scan.next();

				if (!StrUtil.isEmpty(line)) {
					if (line.contains(FIX_SPB_IP)) {
						bs_ip = line.replace(FIX_SPB_IP, "").trim();
					}
					else if (line.contains(FIX_SPB_NAME)) {
						bs_name = line.replace(FIX_SPB_NAME, "").trim();
					}
					else if (line.contains(FIX_EXCEPTION)) {
						isException = true;
					}

					if (isException) {
						if (line.contains(FIX_TIME_STAMP)) {
							time_stamp = line.replace(FIX_TIME_STAMP, "").trim();

							dto = new Tech3gSpbExcetionDTO();
							dto.setBs_ip(bs_ip);
							dto.setBs_name(bs_name);
							dto.setBs_no(bs_name.substring(0, bs_name.indexOf("_")));
							dto.setBs_type("LS5");
							dto.setOccr_time(time_stamp);
							dto.setStatis_flg("001");		// 001 : SPB Exception Code in Morterm
							dto.setStatis_name("SPB Paging/HO Exception");
							dto.setRegi_user(user_id);

							// Inserting SPB Exception data
							insertCnt += Tech3gBizLogic.insertSpbException(dao, user_id, dto);
							isException = false;
						}
					}
				}
			}
		}
		return insertCnt;
	}
	
	private void deleteLogFiles(String dirPath) {
		
		File fileDirs = null;
		File[] fileList = null;
		
		if (!StrUtil.isEmpty(dirPath)) {
			fileDirs = new File(dirPath);
			if (fileDirs.exists() && fileDirs.isDirectory()) {
				fileList = fileDirs.listFiles();
				for (int index = 0; index < fileList.length; index++) {
					File file = fileList[index];
					
					if (file.isFile()) {
						file.delete();
					}
				}
			}
		}
	}
}
