package tech3g.fk.dao.doc.dto;

import tech3g.common.db.BaseDTO;

public class Tech3gBatchRegiDTO extends BaseDTO {

	/**
	 *
	 */
	private static final long serialVersionUID = -2689925615957156266L;


	private String bt_id;

	private String bt_name;

	private String bt_param;

	private String bt_link;

	private String regi_date;

	private String regi_user;

	private String upt_date;

	private String upt_user;

	private String bt_flg;

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
	 * @return bt_link
	 */
	public String getBt_link() {
		return bt_link;
	}

	/**
	 * @param bt_link セットする bt_link
	 */
	public void setBt_link(String bt_link) {
		this.bt_link = bt_link;
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

	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tech3gBatchRegiDTO [bt_id=" + bt_id + ", bt_name=" + bt_name
				+ ", bt_param=" + bt_param + ", bt_link=" + bt_link
				+ ", regi_date=" + regi_date + ", regi_user=" + regi_user
				+ ", upt_date=" + upt_date + ", upt_user=" + upt_user + "]";
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

}
