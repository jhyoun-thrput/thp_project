package tech3g.fk.apps.doc.actions;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import tech3g.common.web.ViewBean;

public class Tech3gDocBatchRegiBean extends ViewBean {

	/**
	 *
	 */
	private static final long serialVersionUID = 423263480276400145L;

	private String bt_id;

	private String bt_name;

	private String bt_param;

	private String regi_user;

	private String regi_date;

	private String upt_user;

	private String upt_date;

	private String p_bt_id;

	private String bt_m_bt_id;

	private String bt_flg;

	private List<LabelValueBean> batchFlgList;

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
	 * @return bt_param
	 */
	public String getBt_param() {
		return bt_param;
	}

	/**
	 * @param bt_param セットする bt_param
	 */
	public void setBt_param(String bt_param) {
		this.bt_param = bt_param;
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

	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tech3gDocBatchRegiBean [bt_id=" + bt_id + ", bt_name="
				+ bt_name + ", bt_param=" + bt_param + ", regi_user="
				+ regi_user + ", regi_date=" + regi_date + ", upt_user="
				+ upt_user + ", upt_date=" + upt_date + "]";
	}

	/**
	 * @return p_bt_id
	 */
	public String getP_bt_id() {
		return p_bt_id;
	}

	/**
	 * @param p_bt_id セットする p_bt_id
	 */
	public void setP_bt_id(String p_bt_id) {
		this.p_bt_id = p_bt_id;
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
	 * @return bt_flg
	 */
	public String getBt_flg() {
		return bt_flg;
	}

	/**
	 * @param bt_flg セットする bt_flg
	 */
	public void setBt_flg(String bt_flg) {
		this.bt_flg = bt_flg;
	}

	/**
	 * @return batchFlgList
	 */
	public List<LabelValueBean> getBatchFlgList() {
		return batchFlgList;
	}

	/**
	 * @param batchFlgList セットする batchFlgList
	 */
	public void setBatchFlgList(List<LabelValueBean> batchFlgList) {
		this.batchFlgList = batchFlgList;
	}

}
