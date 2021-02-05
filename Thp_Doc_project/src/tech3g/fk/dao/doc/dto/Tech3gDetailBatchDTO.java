package tech3g.fk.dao.doc.dto;

import tech3g.common.db.BaseDTO;

public class Tech3gDetailBatchDTO extends BaseDTO {

	/**
	 *
	 */
	private static final long serialVersionUID = -8727283614624105107L;



	private String dtl_bt_id;

	private String dtl_bt_no;

	private String dtl_bt_sched_dttm;

	private String bt_name;

	private String dtl_begin_date;

	private String dtl_end_date;

	private String dtl_bt_param;

	private String dtl_bt_status;

	private String bt_hist_no;

	private String regi_date;

	private String regi_user;

	private String upt_date;

	private String upt_user;

	private String dtl_bt_msg;

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
	 * @return bt_hist_no
	 */
	public String getBt_hist_no() {
		return bt_hist_no;
	}

	/**
	 * @param bt_hist_no セットする bt_hist_no
	 */
	public void setBt_hist_no(String bt_hist_no) {
		this.bt_hist_no = bt_hist_no;
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

	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tech3gDetailBatchDTO [getDtl_bt_id()=" + getDtl_bt_id()
				+ ", getDtl_bt_no()=" + getDtl_bt_no()
				+ ", getDtl_bt_sched_dttm()=" + getDtl_bt_sched_dttm()
				+ ", getBt_name()=" + getBt_name() + ", getDtl_begin_date()="
				+ getDtl_begin_date() + ", getDtl_end_date()="
				+ getDtl_end_date() + ", getDtl_bt_param()="
				+ getDtl_bt_param() + ", getDtl_bt_status()="
				+ getDtl_bt_status() + ", getBt_hist_no()=" + getBt_hist_no()
				+ ", getRegi_date()=" + getRegi_date() + ", getRegi_user()="
				+ getRegi_user() + ", getUpt_date()=" + getUpt_date()
				+ ", getUpt_user()=" + getUpt_user() + ", getDtl_bt_msg()="
				+ getDtl_bt_msg() + "]";
	}

}
