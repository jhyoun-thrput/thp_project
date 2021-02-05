package tech3g.common.util;

import java.io.Serializable;

/**
 * <pre>
 * PageInfoVOクラス。
 * </pre>
 *
 */
public class PageInfoVO implements Serializable {

	/** 表示するページNo, */
	private int pageNo;

	/** 総件数 */
	private int totalCnt;

	/** 1ページの表示件数 */
	private int listRowCnt;

	/** 表示開始位置(件数) */
	private int startNum;

	/** 表示終了位置(件数) */
	private int endNum;

	/**
	 * コンストラクタ<br/>
	 * @param listRowCnt 1ページの表示件数
	 * @param pageNo 表示するページNo,
	 * @param totalCnt 総件数
	 * @param startNum 表示開始位置(件数)
	 * @param endNum 表示終了位置(件数)
	 */
	public PageInfoVO(int listRowCnt, int pageNo, int totalCnt, int startNum, int endNum) {
		this.listRowCnt = listRowCnt;
		this.pageNo = pageNo;
		this.totalCnt = totalCnt;
		this.startNum = startNum;
		this.endNum = endNum;
	}

	/**
	 * 表示するページNo,を取得する。<br/>
	 * @return 表示するページNo,
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 表示するページNo,を設定する。<br/>
	 * @param pageNo 表示するページNo,
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 総件数を取得する。<br/>
	 * @return 総件数
	 */
	public int getTotalCnt() {
		return totalCnt;
	}

	/**
	 * 総件数を設定する。<br/>
	 * @param totalCnt 総件数
	 */
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	/**
	 * 1ページの表示件数を取得する。<br/>
	 * @return 1ページの表示件数
	 */
	public int getListRowCnt() {
		return listRowCnt;
	}

	/**
	 * 1ページの表示件数を設定する。<br/>
	 * @param listRowCnt 1ページの表示件数
	 */
	public void setListRowCnt(int listRowCnt) {
		this.listRowCnt = listRowCnt;
	}


	/**
	 * 表示開始位置(件数)を取得する。<br/>
	 * @return 表示開始位置(件数)
	 */
	public int getStartNum() {
		return startNum;
	}

	/**
	 * 表示開始位置(件数)を設定する。<br/>
	 * @param startNum 表示開始位置(件数)
	 */
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	/**
	 * 表示終了位置(件数)を取得する。<br/>
	 * @return 表示終了位置(件数)
	 */
	public int getEndNum() {
		return endNum;
	}

	/**
	 * 表示終了位置(件数)を設定する。<br/>
	 * @param endNum 表示終了位置(件数)
	 */
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}

}
