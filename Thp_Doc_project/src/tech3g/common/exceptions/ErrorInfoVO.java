package tech3g.common.exceptions;

/**
 * <pre>
 * ErrorInfoVOクラス。
 * エラーの情報ＶＯ
 * </pre>
 */
public class ErrorInfoVO {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
	/** message */
	private String message;
	/** 発生時間 */
	private String time;
	/** 詳細情報 */
	private String detail;

	/** uri */
	private String uri;


    //--------------------------------------------------- コンストラクタ
	/**
	 * コンストラクタ<br/>
	 */
	public ErrorInfoVO() {
		super();
	}
	/**
	 * コンストラクタ<br/>
	 * @param detail 詳細情報
	 * @param message
	 * @param time 発生時間
	 */
	public ErrorInfoVO(String message, String time, String detail) {
		this();
		this.detail = detail;
		this.message = message;
		this.time = time;
	}

	/**
	 * コンストラクタ<br/>
	 * @param detail 詳細情報
	 * @param message
	 * @param time 発生時間
	 */
	public ErrorInfoVO(String message, String time, String detail, String uri) {
		this();
		this.detail = detail;
		this.message = message;
		this.time = time;
		this.uri = uri;
	}

    // -------------------------------------------------- SetGet Methods
	/**
	 * messageを取得する。<br/>
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * messageを設定する。<br/>
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * timeを取得する。<br/>
	 * @return time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * timeを設定する。<br/>
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * detailを取得する。<br/>
	 * @return detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * detailを設定する。<br/>
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * uriを取得する。<br/>
	 * @return uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * uriを設定する。<br/>
	 * @param uri uri
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

    //--------------------------------------------------- インスタンスメソッド

}
