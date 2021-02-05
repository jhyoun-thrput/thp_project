
package tech3g.common.db;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.exceptions.SystemException;

/**
 * <pre>
 * DAOHelperクラス。
 * DAOから使用するUtilクラス
 * </pre>
 */
public class DAOHelper {

    //---------------------------------------------------定数
    //---------------------------------------------------インスタンス変数
    // ----------------------------------------------------- コンストラクタ
	/**
	 * コンストラクタ<br/>
	 * このクラスはインスタンス化はしない。
	 */
	private DAOHelper() {
		super();
	}
    //---------------------------------------------------インスタンスメソッド
	/**
	 * リストの中からMapを取得する。<br>
	 * ※もしデータがない場合は空のListOrderedMapを返却する。<br>
	 * @param list 検索結果リスト
	 * @return Map 結果
	 */
	public static Map getMap(List list) {
		return (Map) (list != null && list.size() > 0 ? list.get(0) : new ListOrderedMap());
	}



	/**
	 * カウント取得<br/>
	 * 引数のカラム名の値をリストからintで取得する。
	 * @param result 検索結果リスト
	 * @param nmCol カウントのカラム名
	 * @return カウント
	 */
	public static int getCnt(List list, String nmCol) {
		return Integer.parseInt(String.valueOf(((Map) list.get(0)).get(nmCol)));
	}


	/**
	 * カウント取得<br/>
	 * 検索結果リストから取得結果をintで取得する。<br/>
	 * カウントSQLで1つのカウントを取得する場合利用する。<br/>
	 *
	 * 注意）取得結果から1行目の1番目の取得項目をintに変換して返す。
	 * @param result 検索結果リスト
	 * @return カウント
	 */
	public static int getCnt(List list) {
		if (list != null && !list.isEmpty() && ((ListOrderedMap)list.get(0)) != null) {
			return Integer.parseInt(((ListOrderedMap)list.get(0)).getValue(0).toString());
		} else {
			throw new SystemException("取得結果がありません。カウント取得SQLを確認してください。");
		}
	}


	/**
	 * データの取得<br/>
	 * 検索結果リストから取得結果をStringで取得する。<br/>
	 *
	 * 注意）取得結果から1行目の1番目の取得項目を文字列に変換して返す。
	 * @param list 検索結果リスト
	 * @return カウント
	 */
	public static String getString(List list) {
		if (list != null && !list.isEmpty() && ((ListOrderedMap)list.get(0)) != null) {
			return (((ListOrderedMap)list.get(0)).getValue(0).toString());
		} else {
			return "";
		}
	}

	/**
	 * intでデータ取得<br/>
	 * @param list 検索結果リスト
	 * @return int値
	 */
	public static int getInt(List list) {
		if (list != null && !list.isEmpty() && ((ListOrderedMap)list.get(0)) != null) {
			return Integer.parseInt(((ListOrderedMap)list.get(0)).getValue(0).toString());
		} else {
			throw new SystemException("取得結果がありません。取得SQLを確認してください。");
		}
	}

}
