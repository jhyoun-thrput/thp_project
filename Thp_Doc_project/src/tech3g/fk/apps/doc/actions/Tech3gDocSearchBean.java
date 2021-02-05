
package tech3g.fk.apps.doc.actions;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;

import tech3g.common.exceptions.MsgVO;
import tech3g.common.util.PageInfoVO;
import tech3g.common.web.ViewBean;


/**
 * <pre>
 * 児童台帳検索のViewBeanクラス。
 * </pre>
 * @author youn.j.h
 */
public class Tech3gDocSearchBean extends ViewBean {

	/** serialVersionUID */
	private static final long serialVersionUID = -5323533624756009276L;

	private String doc_code;

	private String doc_code_name;

	private int doc_index;

	private String doc_name;

	private String doc_type;

	private String doc_link;

	private String regi_user;

	private String upt_user;

	private String regi_date;

	private String upt_date;

	private String doc_ver;

	private String resultCnt;

	private String doc_row;


	/** input Param */

	private String p_doc_code;

	private String p_doc_name;

	private String p_regi_user;

	private String p_regi_date_from;

	private String p_regi_date_to;

	private String p_doc_type;

	private String p_doc_ver;

	private String p_doc_comment;

	private String errMsgKey;

	private String hidden_doc_link;

	private String downFilePath;


	/** radio property */
	private String select_index;

	public String getSelect_index() {
		return select_index;
	}

	public void setSelect_index(String select_index) {
		this.select_index = select_index;
	}

	private String doc_index_no;

	private String doc_code_no;


	public String getDoc_code_no() {
		return doc_code_no;
	}

	public void setDoc_code_no(String doc_code_no) {
		this.doc_code_no = doc_code_no;
	}

	/** Tech Doc Info List */
	private List<ListOrderedMap> techDocList;

	private List<?> docDetailList;

	private List<LabelValueBean> techDocCodeList;

	private List<LabelValueBean> userIdList;

	private List<ListOrderedMap> excelList;


    //********* Page variable Area *********

    public List<?> getDocDetailList() {
		return docDetailList;
	}

	public void setDocDetailList(List<?> docDetailList) {
		this.docDetailList = docDetailList;
	}

	/** 該当者リスとのページ情報 */
    private PageInfoVO techDocListPageInfo;

    //********* Errors variable Area *********


    /** バリデーションエラー格納変数 */
    private List<MsgVO> currntErrorList;


	/**
	 * @return doc_code
	 */
	public String getDoc_code() {
		return doc_code;
	}

	/**
	 * @param doc_code セットする doc_code
	 */
	public void setDoc_code(String doc_code) {
		this.doc_code = doc_code;
	}

	/**
	 * @return doc_code_name
	 */
	public String getDoc_code_name() {
		return doc_code_name;
	}

	/**
	 * @param doc_code_name セットする doc_code_name
	 */
	public void setDoc_code_name(String doc_code_name) {
		this.doc_code_name = doc_code_name;
	}

	/**
	 * @return doc_index
	 */
	public int getDoc_index() {
		return doc_index;
	}

	/**
	 * @param doc_index セットする doc_index
	 */
	public void setDoc_index(int doc_index) {
		this.doc_index = doc_index;
	}

	/**
	 * @return doc_name
	 */
	public String getDoc_name() {
		return doc_name;
	}

	/**
	 * @param doc_name セットする doc_name
	 */
	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}

	/**
	 * @return doc_type
	 */
	public String getDoc_type() {
		return doc_type;
	}

	/**
	 * @param doc_type セットする doc_type
	 */
	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}

	/**
	 * @return doc_link
	 */
	public String getDoc_link() {
		return doc_link;
	}

	/**
	 * @param doc_link セットする doc_link
	 */
	public void setDoc_link(String doc_link) {
		this.doc_link = doc_link;
	}

	/**
	 * @return regi_user
	 */
	public String getRegi_user() {
		return regi_user;
	}

	/**
	 * @param regi_user セットする regi_user
	 */
	public void setRegi_user(String regi_user) {
		this.regi_user = regi_user;
	}

	/**
	 * @return upt_user
	 */
	public String getUpt_user() {
		return upt_user;
	}

	/**
	 * @param upt_user セットする upt_user
	 */
	public void setUpt_user(String upt_user) {
		this.upt_user = upt_user;
	}

	/**
	 * @return regi_date
	 */
	public String getRegi_date() {
		return regi_date;
	}

	/**
	 * @param regi_date セットする regi_date
	 */
	public void setRegi_date(String regi_date) {
		this.regi_date = regi_date;
	}

	/**
	 * @return upt_date
	 */
	public String getUpt_date() {
		return upt_date;
	}

	/**
	 * @param upt_date セットする upt_date
	 */
	public void setUpt_date(String upt_date) {
		this.upt_date = upt_date;
	}

	/**
	 * @return doc_ver
	 */
	public String getDoc_ver() {
		return doc_ver;
	}

	/**
	 * @param doc_ver セットする doc_ver
	 */
	public void setDoc_ver(String doc_ver) {
		this.doc_ver = doc_ver;
	}

	/**
	 * @return p_doc_code
	 */
	public String getP_doc_code() {
		return p_doc_code;
	}

	/**
	 * @param p_doc_code セットする p_doc_code
	 */
	public void setP_doc_code(String p_doc_code) {
		this.p_doc_code = p_doc_code;
	}

	/**
	 * @return p_regi_user
	 */
	public String getP_regi_user() {
		return p_regi_user;
	}

	/**
	 * @param p_regi_user セットする p_regi_user
	 */
	public void setP_regi_user(String p_regi_user) {
		this.p_regi_user = p_regi_user;
	}

	/**
	 * @return p_doc_type
	 */
	public String getP_doc_type() {
		return p_doc_type;
	}

	/**
	 * @param p_doc_type セットする p_doc_type
	 */
	public void setP_doc_type(String p_doc_type) {
		this.p_doc_type = p_doc_type;
	}

	/**
	 * @return p_doc_ver
	 */
	public String getP_doc_ver() {
		return p_doc_ver;
	}

	/**
	 * @param p_doc_ver セットする p_doc_ver
	 */
	public void setP_doc_ver(String p_doc_ver) {
		this.p_doc_ver = p_doc_ver;
	}

	/**
	 * @return currntErrorList
	 */
	public List<MsgVO> getCurrntErrorList() {
		return currntErrorList;
	}

	/**
	 * @param currntErrorList セットする currntErrorList
	 */
	public void setCurrntErrorList(List<MsgVO> currntErrorList) {
		this.currntErrorList = currntErrorList;
	}

	/**
	 * @return techDocListPageInfo
	 */
	public PageInfoVO getTechDocListPageInfo() {
		return techDocListPageInfo;
	}

	/**
	 * @param techDocListPageInfo セットする techDocListPageInfo
	 */
	public void setTechDocListPageInfo(PageInfoVO techDocListPageInfo) {
		this.techDocListPageInfo = techDocListPageInfo;
	}

	/**
	 * @return p_doc_name
	 */
	public String getP_doc_name() {
		return p_doc_name;
	}

	/**
	 * @param p_doc_name セットする p_doc_name
	 */
	public void setP_doc_name(String p_doc_name) {
		this.p_doc_name = p_doc_name;
	}

	public String getResultCnt() {
		return resultCnt;
	}

	public void setResultCnt(String resultCnt) {
		this.resultCnt = resultCnt;
	}

	public List<ListOrderedMap> getTechDocList() {
		return techDocList;
	}

	public void setTechDocList(List<ListOrderedMap> techDocList) {
		this.techDocList = techDocList;
	}

	public String getErrMsgKey() {
		return errMsgKey;
	}

	public void setErrMsgKey(String errMsgKey) {
		this.errMsgKey = errMsgKey;
	}

	/**
	 * @return techDocCodeList
	 */
	public List<LabelValueBean> getTechDocCodeList() {
		return techDocCodeList;
	}

	/**
	 * @param techDocCodeList セットする techDocCodeList
	 */
	public void setTechDocCodeList(List<LabelValueBean> techDocCodeList) {
		this.techDocCodeList = techDocCodeList;
	}

	/**
	 * @return p_regi_date_from
	 */
	public String getP_regi_date_from() {
		return p_regi_date_from;
	}

	/**
	 * @param p_regi_date_from セットする p_regi_date_from
	 */
	public void setP_regi_date_from(String p_regi_date_from) {
		this.p_regi_date_from = p_regi_date_from;
	}

	/**
	 * @return p_regi_date_to
	 */
	public String getP_regi_date_to() {
		return p_regi_date_to;
	}

	/**
	 * @param p_regi_date_to セットする p_regi_date_to
	 */
	public void setP_regi_date_to(String p_regi_date_to) {
		this.p_regi_date_to = p_regi_date_to;
	}

	public String getDoc_index_no() {
		return doc_index_no;
	}

	public void setDoc_index_no(String doc_index_no) {
		this.doc_index_no = doc_index_no;
	}

	/**
	 * @return doc_row
	 */
	public String getDoc_row() {
		return doc_row;
	}

	/**
	 * @param doc_row セットする doc_row
	 */
	public void setDoc_row(String doc_row) {
		this.doc_row = doc_row;
	}

	/**
	 * @return hidden_doc_link
	 */
	public String getHidden_doc_link() {
		return hidden_doc_link;
	}

	/**
	 * @param hidden_doc_link セットする hidden_doc_link
	 */
	public void setHidden_doc_link(String hidden_doc_link) {
		this.hidden_doc_link = hidden_doc_link;
	}

	/**
	 * @return downFilePath
	 */
	public String getDownFilePath() {
		return downFilePath;
	}

	/**
	 * @param downFilePath セットする downFilePath
	 */
	public void setDownFilePath(String downFilePath) {
		this.downFilePath = downFilePath;
	}

	/**
	 * @return p_doc_comment
	 */
	public String getP_doc_comment() {
		return p_doc_comment;
	}

	/**
	 * @param p_doc_comment セットする p_doc_comment
	 */
	public void setP_doc_comment(String p_doc_comment) {
		this.p_doc_comment = p_doc_comment;
	}

	/**
	 * @return userIdList
	 */
	public List<LabelValueBean> getUserIdList() {
		return userIdList;
	}

	/**
	 * @param userIdList セットする userIdList
	 */
	public void setUserIdList(List<LabelValueBean> userIdList) {
		this.userIdList = userIdList;
	}

	/**
	 * @return excelList
	 */
	public List<ListOrderedMap> getExcelList() {
		return excelList;
	}

	/**
	 * @param excelList セットする excelList
	 */
	public void setExcelList(List<ListOrderedMap> excelList) {
		this.excelList = excelList;
	}

}
