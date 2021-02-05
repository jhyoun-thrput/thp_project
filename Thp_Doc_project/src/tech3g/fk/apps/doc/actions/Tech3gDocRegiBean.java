/**
 *
 */
package tech3g.fk.apps.doc.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

import tech3g.common.web.ViewBean;

/**
 * @author tech3g
 *
 */
public class Tech3gDocRegiBean extends ViewBean {

	/**
	 *
	 */
	private static final long serialVersionUID = -2215600216973926556L;

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

	private List<LabelValueBean> techDocCodeList;

	private FormFile file;

	private String chunk;

	private String chunks;

	private String id;

	private String name;

	private String index_field;

	private int upload_cnt;

	private String error_msg;

	private HttpServletRequest req;

	private List<FormFile> uploadFiles;

	private List<ListOrderedMap> techDocList;

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
	 * @return chunk
	 */
	public String getChunk() {
		return chunk;
	}

	/**
	 * @param chunk セットする chunk
	 */
	public void setChunk(String chunk) {
		this.chunk = chunk;
	}

	/**
	 * @return chunks
	 */
	public String getChunks() {
		return chunks;
	}

	/**
	 * @param chunks セットする chunks
	 */
	public void setChunks(String chunks) {
		this.chunks = chunks;
	}

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id セットする id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return req
	 */
	public HttpServletRequest getReq() {
		return req;
	}

	/**
	 * @param req セットする req
	 */
	public void setReq(HttpServletRequest req) {
		this.req = req;
	}

	/**
	 * @return uploadFiles
	 */
	public List<FormFile> getUploadFiles() {
		return uploadFiles;
	}

	/**
	 * @param uploadFiles セットする uploadFiles
	 */
	public void setUploadFiles(List<FormFile> uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	/**
	 * @return index_field
	 */
	public String getIndex_field() {
		return index_field;
	}

	/**
	 * @param index_field セットする index_field
	 */
	public void setIndex_field(String index_field) {
		this.index_field = index_field;
	}

	/**
	 * @return techDocList
	 */
	public List<ListOrderedMap> getTechDocList() {
		return techDocList;
	}

	/**
	 * @param techDocList セットする techDocList
	 */
	public void setTechDocList(List<ListOrderedMap> techDocList) {
		this.techDocList = techDocList;
	}

	/**
	 * @return error_msg
	 */
	public String getError_msg() {
		return error_msg;
	}

	/**
	 * @param error_msg セットする error_msg
	 */
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	/**
	 * @return upload_cnt
	 */
	public int getUpload_cnt() {
		return upload_cnt;
	}

	/**
	 * @param upload_cnt セットする upload_cnt
	 */
	public void setUpload_cnt(int upload_cnt) {
		this.upload_cnt = upload_cnt;
	}
}
