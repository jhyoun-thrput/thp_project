/**
 *
 */
package tech3g.fk.apps.doc.actions;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import tech3g.common.util.PageInfoVO;
import tech3g.common.web.ViewBean;
import tech3g.fk.dao.doc.dto.Tech3gBatchDTO;

/**
 * @author tech3g
 *
 */
public class Tech3gDocBatchBean extends ViewBean {

	/**
	 *
	 */
	private static final long serialVersionUID = -7573294502820111579L;

	/** バッチID */
    private String bt_id;

	/** バッチ名 */
    private String bt_name;

	/** 処理区分 */
    private String bt_status;

	/** 備考 */
    private String param_memo;

	/** 処理日From */
    private String bt_begin_date;

	/** 終了日To */
    private String bt_end_date;

	/** 実行番号 */
    private String bt_no;

	/** スケジュール一覧選択 */
    private String btList_chk;

    private String dtl_bt_id;

	/** 実行番号 */
    private String dtl_bt_no;

	/** 処理結果 */
    private String dtl_bt_status;

    private String dtl_bt_sched_dttm;

	/** 開始時間 */
    private String dtl_begin_date;

	/** 終了時間 */
    private String dtl_end_date;

    /** パラメータ*/
    private String dtl_bt_param;

	/** メッセージ */
    private String dtl_bt_msg;

    private String selected_bt_id;

    private String selected_bt_no;

    private String selectedBt_flg;

    private FormFile file;

    private String doc_file_path;

    private String bt_m_sel;

    private String bt_m_bt_id;


    private String param_bt_id;

    private String param_bt_param;

    private String param_bt_flg;


	/** スケジュール一覧 */
    private List btList;

    private List masterBtList;

    /** スケジュール一覧のページング情報 */
    private PageInfoVO btListPagingInf;

    /** btParamMasterMap */
    private Map btParamMasterMap;

    /** パラメータHTML */
    private String paramHtml;

    private int uploadFile;

    /** バッチ作業ファイル一覧 */
    private List btFileList;

    /** バッチ終了監視 */
    private String btStatsRefresh;


    /** 初期表示時に設定したパラメータ値のMap情報 */
    private Map initInputParamValueMap;

    private Tech3gBatchDTO batchDto;


	/**
	 * @return bt_id
	 */
	public String getBt_id() {
		return bt_id;
	}


	/**
	 * @param bt_id セットする bt_id
	 */
	public void setBt_id(String bt_id) {
		this.bt_id = bt_id;
	}


	/**
	 * @return bt_name
	 */
	public String getBt_name() {
		return bt_name;
	}


	/**
	 * @param bt_name セットする bt_name
	 */
	public void setBt_name(String bt_name) {
		this.bt_name = bt_name;
	}


	/**
	 * @return bt_status
	 */
	public String getBt_status() {
		return bt_status;
	}


	/**
	 * @param bt_status セットする bt_status
	 */
	public void setBt_status(String bt_status) {
		this.bt_status = bt_status;
	}


	/**
	 * @return param_memo
	 */
	public String getParam_memo() {
		return param_memo;
	}


	/**
	 * @param param_memo セットする param_memo
	 */
	public void setParam_memo(String param_memo) {
		this.param_memo = param_memo;
	}


	/**
	 * @return bt_begin_date
	 */
	public String getBt_begin_date() {
		return bt_begin_date;
	}


	/**
	 * @param bt_begin_date セットする bt_begin_date
	 */
	public void setBt_begin_date(String bt_begin_date) {
		this.bt_begin_date = bt_begin_date;
	}


	/**
	 * @return bt_end_date
	 */
	public String getBt_end_date() {
		return bt_end_date;
	}


	/**
	 * @param bt_end_date セットする bt_end_date
	 */
	public void setBt_end_date(String bt_end_date) {
		this.bt_end_date = bt_end_date;
	}


	/**
	 * @return bt_no
	 */
	public String getBt_no() {
		return bt_no;
	}


	/**
	 * @param bt_no セットする bt_no
	 */
	public void setBt_no(String bt_no) {
		this.bt_no = bt_no;
	}


	/**
	 * @return btList_chk
	 */
	public String getBtList_chk() {
		return btList_chk;
	}


	/**
	 * @param btList_chk セットする btList_chk
	 */
	public void setBtList_chk(String btList_chk) {
		this.btList_chk = btList_chk;
	}


	/**
	 * @return dtl_bt_no
	 */
	public String getDtl_bt_no() {
		return dtl_bt_no;
	}


	/**
	 * @param dtl_bt_no セットする dtl_bt_no
	 */
	public void setDtl_bt_no(String dtl_bt_no) {
		this.dtl_bt_no = dtl_bt_no;
	}


	/**
	 * @return dtl_bt_status
	 */
	public String getDtl_bt_status() {
		return dtl_bt_status;
	}


	/**
	 * @param dtl_bt_status セットする dtl_bt_status
	 */
	public void setDtl_bt_status(String dtl_bt_status) {
		this.dtl_bt_status = dtl_bt_status;
	}


	/**
	 * @return dtl_begin_date
	 */
	public String getDtl_begin_date() {
		return dtl_begin_date;
	}


	/**
	 * @param dtl_begin_date セットする dtl_begin_date
	 */
	public void setDtl_begin_date(String dtl_begin_date) {
		this.dtl_begin_date = dtl_begin_date;
	}


	/**
	 * @return dtl_end_date
	 */
	public String getDtl_end_date() {
		return dtl_end_date;
	}


	/**
	 * @param dtl_end_date セットする dtl_end_date
	 */
	public void setDtl_end_date(String dtl_end_date) {
		this.dtl_end_date = dtl_end_date;
	}


	/**
	 * @return dtl_bt_param
	 */
	public String getDtl_bt_param() {
		return dtl_bt_param;
	}


	/**
	 * @param dtl_bt_param セットする dtl_bt_param
	 */
	public void setDtl_bt_param(String dtl_bt_param) {
		this.dtl_bt_param = dtl_bt_param;
	}


	/**
	 * @return dtl_bt_msg
	 */
	public String getDtl_bt_msg() {
		return dtl_bt_msg;
	}


	/**
	 * @param dtl_bt_msg セットする dtl_bt_msg
	 */
	public void setDtl_bt_msg(String dtl_bt_msg) {
		this.dtl_bt_msg = dtl_bt_msg;
	}


	/**
	 * @return btList
	 */
	public List getBtList() {
		return btList;
	}


	/**
	 * @param btList セットする btList
	 */
	public void setBtList(List btList) {
		this.btList = btList;
	}


	/**
	 * @return btListPagingInf
	 */
	public PageInfoVO getBtListPagingInf() {
		return btListPagingInf;
	}


	/**
	 * @param btListPagingInf セットする btListPagingInf
	 */
	public void setBtListPagingInf(PageInfoVO btListPagingInf) {
		this.btListPagingInf = btListPagingInf;
	}


	/**
	 * @return btParamMasterMap
	 */
	public Map getBtParamMasterMap() {
		return btParamMasterMap;
	}


	/**
	 * @param btParamMasterMap セットする btParamMasterMap
	 */
	public void setBtParamMasterMap(Map btParamMasterMap) {
		this.btParamMasterMap = btParamMasterMap;
	}


	/**
	 * @return paramHtml
	 */
	public String getParamHtml() {
		return paramHtml;
	}


	/**
	 * @param paramHtml セットする paramHtml
	 */
	public void setParamHtml(String paramHtml) {
		this.paramHtml = paramHtml;
	}


	/**
	 * @return uploadFile
	 */
	public int getUploadFile() {
		return uploadFile;
	}


	/**
	 * @param uploadFile セットする uploadFile
	 */
	public void setUploadFile(int uploadFile) {
		this.uploadFile = uploadFile;
	}


	/**
	 * @return btFileList
	 */
	public List getBtFileList() {
		return btFileList;
	}


	/**
	 * @param btFileList セットする btFileList
	 */
	public void setBtFileList(List btFileList) {
		this.btFileList = btFileList;
	}


	/**
	 * @return btStatsRefresh
	 */
	public String getBtStatsRefresh() {
		return btStatsRefresh;
	}


	/**
	 * @param btStatsRefresh セットする btStatsRefresh
	 */
	public void setBtStatsRefresh(String btStatsRefresh) {
		this.btStatsRefresh = btStatsRefresh;
	}


	/**
	 * @return initInputParamValueMap
	 */
	public Map getInitInputParamValueMap() {
		return initInputParamValueMap;
	}


	/**
	 * @param initInputParamValueMap セットする initInputParamValueMap
	 */
	public void setInitInputParamValueMap(Map initInputParamValueMap) {
		this.initInputParamValueMap = initInputParamValueMap;
	}


	/**
	 * @return selected_bt_id
	 */
	public String getSelected_bt_id() {
		return selected_bt_id;
	}


	/**
	 * @param selected_bt_id セットする selected_bt_id
	 */
	public void setSelected_bt_id(String selected_bt_id) {
		this.selected_bt_id = selected_bt_id;
	}

	/**
	 * @return dtl_bt_id
	 */
	public String getDtl_bt_id() {
		return dtl_bt_id;
	}


	/**
	 * @param dtl_bt_id セットする dtl_bt_id
	 */
	public void setDtl_bt_id(String dtl_bt_id) {
		this.dtl_bt_id = dtl_bt_id;
	}


	/**
	 * @return dtl_bt_sched_dttm
	 */
	public String getDtl_bt_sched_dttm() {
		return dtl_bt_sched_dttm;
	}


	/**
	 * @param dtl_bt_sched_dttm セットする dtl_bt_sched_dttm
	 */
	public void setDtl_bt_sched_dttm(String dtl_bt_sched_dttm) {
		this.dtl_bt_sched_dttm = dtl_bt_sched_dttm;
	}


	/**
	 * @return selected_bt_no
	 */
	public String getSelected_bt_no() {
		return selected_bt_no;
	}


	/**
	 * @param selected_bt_no セットする selected_bt_no
	 */
	public void setSelected_bt_no(String selected_bt_no) {
		this.selected_bt_no = selected_bt_no;
	}


	/**
	 * @return file
	 */
	public FormFile getFile() {
		return file;
	}


	/**
	 * @param file セットする file
	 */
	public void setFile(FormFile file) {
		this.file = file;
	}


	/**
	 * @return doc_file_path
	 */
	public String getDoc_file_path() {
		return doc_file_path;
	}


	/**
	 * @param doc_file_path セットする doc_file_path
	 */
	public void setDoc_file_path(String doc_file_path) {
		this.doc_file_path = doc_file_path;
	}


	/**
	 * @return bt_m_sel
	 */
	public String getBt_m_sel() {
		return bt_m_sel;
	}


	/**
	 * @param bt_m_sel セットする bt_m_sel
	 */
	public void setBt_m_sel(String bt_m_sel) {
		this.bt_m_sel = bt_m_sel;
	}


	/**
	 * @return masterBtList
	 */
	public List getMasterBtList() {
		return masterBtList;
	}


	/**
	 * @param masterBtList セットする masterBtList
	 */
	public void setMasterBtList(List masterBtList) {
		this.masterBtList = masterBtList;
	}


	/**
	 * @return bt_m_bt_id
	 */
	public String getBt_m_bt_id() {
		return bt_m_bt_id;
	}


	/**
	 * @param bt_m_bt_id セットする bt_m_bt_id
	 */
	public void setBt_m_bt_id(String bt_m_bt_id) {
		this.bt_m_bt_id = bt_m_bt_id;
	}


	/**
	 * @return param_bt_id
	 */
	public String getParam_bt_id() {
		return param_bt_id;
	}


	/**
	 * @param param_bt_id セットする param_bt_id
	 */
	public void setParam_bt_id(String param_bt_id) {
		this.param_bt_id = param_bt_id;
	}


	/**
	 * @return param_bt_param
	 */
	public String getParam_bt_param() {
		return param_bt_param;
	}


	/**
	 * @param param_bt_param セットする param_bt_param
	 */
	public void setParam_bt_param(String param_bt_param) {
		this.param_bt_param = param_bt_param;
	}


	/**
	 * @return batchDto
	 */
	public Tech3gBatchDTO getBatchDto() {
		return batchDto;
	}


	/**
	 * @param batchDto セットする batchDto
	 */
	public void setBatchDto(Tech3gBatchDTO batchDto) {
		this.batchDto = batchDto;
	}


	/**
	 * @return param_bt_flg
	 */
	public String getParam_bt_flg() {
		return param_bt_flg;
	}


	/**
	 * @param param_bt_flg セットする param_bt_flg
	 */
	public void setParam_bt_flg(String param_bt_flg) {
		this.param_bt_flg = param_bt_flg;
	}


	/**
	 * @return selectedBt_flg
	 */
	public String getSelectedBt_flg() {
		return selectedBt_flg;
	}


	/**
	 * @param selectedBt_flg セットする selectedBt_flg
	 */
	public void setSelectedBt_flg(String selectedBt_flg) {
		this.selectedBt_flg = selectedBt_flg;
	}
}
