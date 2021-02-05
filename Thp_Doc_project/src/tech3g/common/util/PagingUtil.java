
package tech3g.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * PagingUtilクラス。
 * ページングに関するUtilクラス。
 *
 * 注意) インスタンス化せずにstatic methodを作成し使用すること。
 * </pre>
 */
public class PagingUtil {

	//--------------------------------------------------- 定数
	//--------------------------------------------------- インスタンス変数
	/** log */
	private static Log log = LogFactory.getLog(PagingUtil.class);
	//--------------------------------------------------- コンストラクタ
    /**
	 * コンストラクタ<br/>
	 * このオブジェクトはインスタンス化する必要がない。
	 */
	private PagingUtil()	{
	}
	// -------------------------------------------------- SetGet Methods
	//--------------------------------------------------- インスタンスメソッド

	/**
	 * ページング計算<br/>
	 * 全件数、リストの行数、表示するページを引数で受け取り<br/>
	 * ページング表示情報を計算し<code>PageInfoVO</code>オブジェクトとして返却する。<br/>
	 *
	 * @param totalCnt 全件数
	 * @param listRowCnt リストの行数
	 * @param pageNo 表示するページ
	 * @return ページング表示情報
	 */
	public static PageInfoVO getPageInfo(int totalCnt, int listRowCnt, String pageNo) {

		int currentPageNo = 1;

		// 数字ではないか0の場合は1ページをDefaultとしてする。
		if (StrUtil.isEmpty(pageNo) || "0".equals(currentPageNo) ) {
			currentPageNo = 1;
		} else {
			try {
				currentPageNo = Integer.parseInt(pageNo);
			} catch(NumberFormatException e) {
				currentPageNo = 1;
			}
		}

		// 検索開始インデックスと、終点インデックスを計算する。
		int startNum = 0;
		startNum = (currentPageNo - 1) * listRowCnt + 1;
		if (totalCnt > 0 && startNum > totalCnt) {
			currentPageNo--;
			startNum = (currentPageNo - 1) * listRowCnt + 1;
		}

		int endNum = startNum + listRowCnt - 1;

		if (log.isDebugEnabled()) {
			log.debug("Paging計算 ⇒ 開始行：" + startNum + ", 終了行：" + endNum);
		}

		return new PageInfoVO(listRowCnt, currentPageNo, totalCnt, startNum, endNum);
	}


}
