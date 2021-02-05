package tech3g.fk.apps.doc.actions;

import tech3g.common.web.ViewBean;

public class Tech3gUserRegiBean extends ViewBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 314816391448655352L;

	private String user_id;

	private String user_name;

	private String user_pass;

	private String confrm_pass;

	private String current_pass;

	private String new_pass;

	private String user_mail;

	private String user_admin;

	private String user_tel_no;

	private String user_ip;

	private String regi_user;

	private String upt_user;

	private String regi_date;

	private String upt_date;

	private String err_massge;

	private String check_flg;

	private String selected_user_id;

	private String login_user_admin;

	private String admin_val;

	private String samePass;

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
	 * @return user_pass
	 */
	public String getUser_pass() {
		return user_pass;
	}

	/**
	 * @param user_pass セットする user_pass
	 */
	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
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
	 * @return confrm_pass
	 */
	public String getConfrm_pass() {
		return confrm_pass;
	}

	/**
	 * @param confrm_pass セットする confrm_pass
	 */
	public void setConfrm_pass(String confrm_pass) {
		this.confrm_pass = confrm_pass;
	}

	/**
	 * @return err_massge
	 */
	public String getErr_massge() {
		return err_massge;
	}

	/**
	 * @param err_massge セットする err_massge
	 */
	public void setErr_massge(String err_massge) {
		this.err_massge = err_massge;
	}

	/**
	 * @return check_flg
	 */
	public String getCheck_flg() {
		return check_flg;
	}

	/**
	 * @param check_flg セットする check_flg
	 */
	public void setCheck_flg(String check_flg) {
		this.check_flg = check_flg;
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
	 * @return current_pass
	 */
	public String getCurrent_pass() {
		return current_pass;
	}

	/**
	 * @param current_pass セットする current_pass
	 */
	public void setCurrent_pass(String current_pass) {
		this.current_pass = current_pass;
	}

	/**
	 * @return new_pass
	 */
	public String getNew_pass() {
		return new_pass;
	}

	/**
	 * @param new_pass セットする new_pass
	 */
	public void setNew_pass(String new_pass) {
		this.new_pass = new_pass;
	}

	/**
	 * @return login_user_admin
	 */
	public String getLogin_user_admin() {
		return login_user_admin;
	}

	/**
	 * @param login_user_admin セットする login_user_admin
	 */
	public void setLogin_user_admin(String login_user_admin) {
		this.login_user_admin = login_user_admin;
	}

	/**
	 * @return admin_val
	 */
	public String getAdmin_val() {
		return admin_val;
	}

	/**
	 * @param admin_val セットする admin_val
	 */
	public void setAdmin_val(String admin_val) {
		this.admin_val = admin_val;
	}

	/**
	 * @return samePass
	 */
	public String getSamePass() {
		return samePass;
	}

	/**
	 * @param samePass セットする samePass
	 */
	public void setSamePass(String samePass) {
		this.samePass = samePass;
	}

}
