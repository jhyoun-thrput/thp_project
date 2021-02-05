package tech3g.fk.apps.doc.actions;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.util.PageInfoVO;
import tech3g.common.web.ViewBean;

public class Tech3gUserManagerBean extends ViewBean {

	/**
	 *
	 */
	private static final long serialVersionUID = -3582495550880030513L;


	private String user_id;

	private String p_user_sel;

	private String user_name;

	private String user_mail;

	private String user_admin;

	private String user_tel_no;

	private String user_ip;

	private String regi_user;

	private String upt_user;

	private String regi_date;

	private String upt_date;

	private String selected_user_id;

	private String result_cnt;

	private String p_user_id;

	private List<ListOrderedMap> userList;

	/** ページ情報 */
    private PageInfoVO userListPageInfo;

	/**
	 * @return user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id セットする user_id
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return p_user_sel
	 */
	public String getP_user_sel() {
		return p_user_sel;
	}

	/**
	 * @param p_user_sel セットする p_user_sel
	 */
	public void setP_user_sel(String p_user_sel) {
		this.p_user_sel = p_user_sel;
	}

	/**
	 * @return user_name
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * @param user_name セットする user_name
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/**
	 * @return user_mail
	 */
	public String getUser_mail() {
		return user_mail;
	}

	/**
	 * @param user_mail セットする user_mail
	 */
	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}

	/**
	 * @return user_admin
	 */
	public String getUser_admin() {
		return user_admin;
	}

	/**
	 * @param user_admin セットする user_admin
	 */
	public void setUser_admin(String user_admin) {
		this.user_admin = user_admin;
	}

	/**
	 * @return user_tel_no
	 */
	public String getUser_tel_no() {
		return user_tel_no;
	}

	/**
	 * @param user_tel_no セットする user_tel_no
	 */
	public void setUser_tel_no(String user_tel_no) {
		this.user_tel_no = user_tel_no;
	}

	/**
	 * @return user_ip
	 */
	public String getUser_ip() {
		return user_ip;
	}

	/**
	 * @param user_ip セットする user_ip
	 */
	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
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
	 * @return selected_user_id
	 */
	public String getSelected_user_id() {
		return selected_user_id;
	}

	/**
	 * @param selected_user_id セットする selected_user_id
	 */
	public void setSelected_user_id(String selected_user_id) {
		this.selected_user_id = selected_user_id;
	}

	/**
	 * @return userList
	 */
	public List<ListOrderedMap> getUserList() {
		return userList;
	}

	/**
	 * @param userList セットする userList
	 */
	public void setUserList(List<ListOrderedMap> userList) {
		this.userList = userList;
	}

	/**
	 * @return userListPageInfo
	 */
	public PageInfoVO getUserListPageInfo() {
		return userListPageInfo;
	}

	/**
	 * @param userListPageInfo セットする userListPageInfo
	 */
	public void setUserListPageInfo(PageInfoVO userListPageInfo) {
		this.userListPageInfo = userListPageInfo;
	}

	/**
	 * @return result_cnt
	 */
	public String getResult_cnt() {
		return result_cnt;
	}

	/**
	 * @param result_cnt セットする result_cnt
	 */
	public void setResult_cnt(String result_cnt) {
		this.result_cnt = result_cnt;
	}

	/**
	 * @return p_user_id
	 */
	public String getP_user_id() {
		return p_user_id;
	}

	/**
	 * @param p_user_id セットする p_user_id
	 */
	public void setP_user_id(String p_user_id) {
		this.p_user_id = p_user_id;
	}



}
