/**
 *
 */
package tech3g.fk.apps.doc.actions;

import java.util.List;

import org.apache.struts.upload.FormFile;

import tech3g.common.exceptions.MsgVO;
import tech3g.common.util.PageInfoVO;
import tech3g.common.web.ViewBean;
import tech3g.fk.dao.doc.dto.Tech3gDocDetailDTO;

/**
 * @author tech3g
 *
 */
public class Tech3gDocDetailBean extends ViewBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 1798391063629229959L;

	private String doc_code;

	private String doc_code_name;

	private String doc_index;

	private String doc_name;

	private String doc_type;

	private String doc_ver;

	private String doc_link;

	private String regi_user;

	private String upt_user;

	private String regi_date;

	private String upt_date;

	private String comment;

	private String errMsgKey;

	private String doc_links;

	private String doc_hist_link;

	private String doc_link_value;

	private String auth_flg;

	private Tech3gDocDetailDTO docDetailDto;

	private FormFile file;

    /** バリデーションエラー格納変数 */
    private List<MsgVO> currntErrorList;

    private PageInfoVO docHistPageInfo;

    private List<?> docHistlList;


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
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment セットする comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tech3gDocDetailBean [doc_code=" + doc_code + ", doc_code_name="
				+ doc_code_name + ", doc_index=" + doc_index + ", doc_name="
				+ doc_name + ", doc_type=" + doc_type + ", doc_ver=" + doc_ver
				+ ", doc_link=" + doc_link + ", regi_user=" + regi_user
				+ ", upt_user=" + upt_user + ", regi_date=" + regi_date
				+ ", upt_date=" + upt_date + ", comment=" + comment + "]";
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
	 * @return errMsgKey
	 */
	public String getErrMsgKey() {
		return errMsgKey;
	}

	/**
	 * @param errMsgKey セットする errMsgKey
	 */
	public void setErrMsgKey(String errMsgKey) {
		this.errMsgKey = errMsgKey;
	}

	/**
	 * @return doc_index
	 */
	public String getDoc_index() {
		return doc_index;
	}

	/**
	 * @param doc_index セットする doc_index
	 */
	public void setDoc_index(String doc_index) {
		this.doc_index = doc_index;
	}

	/**
	 * @return docDetailDto
	 */
	public Tech3gDocDetailDTO getDocDetailDto() {
		return docDetailDto;
	}

	/**
	 * @param docDetailDto セットする docDetailDto
	 */
	public void setDocDetailDto(Tech3gDocDetailDTO docDetailDto) {
		this.docDetailDto = docDetailDto;
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
	 * @return docHistPageInfo
	 */
	public PageInfoVO getDocHistPageInfo() {
		return docHistPageInfo;
	}

	/**
	 * @param docHistPageInfo セットする docHistPageInfo
	 */
	public void setDocHistPageInfo(PageInfoVO docHistPageInfo) {
		this.docHistPageInfo = docHistPageInfo;
	}

	/**
	 * @return docHistlList
	 */
	public List<?> getDocHistlList() {
		return docHistlList;
	}

	/**
	 * @param docHistlList セットする docHistlList
	 */
	public void setDocHistlList(List<?> docHistlList) {
		this.docHistlList = docHistlList;
	}

	/**
	 * @return doc_links
	 */
	public String getDoc_links() {
		return doc_links;
	}

	/**
	 * @param doc_links セットする doc_links
	 */
	public void setDoc_links(String doc_links) {
		this.doc_links = doc_links;
	}

	/**
	 * @return doc_hist_link
	 */
	public String getDoc_hist_link() {
		return doc_hist_link;
	}

	/**
	 * @param doc_hist_link セットする doc_hist_link
	 */
	public void setDoc_hist_link(String doc_hist_link) {
		this.doc_hist_link = doc_hist_link;
	}

	/**
	 * @return doc_link_value
	 */
	public String getDoc_link_value() {
		return doc_link_value;
	}

	/**
	 * @param doc_link_value セットする doc_link_value
	 */
	public void setDoc_link_value(String doc_link_value) {
		this.doc_link_value = doc_link_value;
	}

	/**
	 * @return auth_flg
	 */
	public String getAuth_flg() {
		return auth_flg;
	}

	/**
	 * @param auth_flg セットする auth_flg
	 */
	public void setAuth_flg(String auth_flg) {
		this.auth_flg = auth_flg;
	}
}
