package tech3g.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.ListOrderedMap;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.ApplicationException;
import tech3g.common.exceptions.SystemException;
import tech3g.common.service.ExclusionService;
import tech3g.common.web.BaseAction;

/**
 * <pre>
 * ExclusionUtilクラス。
 * 排他制御関連の処理を行う。
 * </pre>
 */
public class ExclusionUtil {

	//--------------------------------------------------- 定数
	/** 排他チェック項目 */
	private static final String[] CHECK_FIELD = {"UPT_DATE"};

    //--------------------------------------------------- インスタンス変数
	/** 排他制御サービス */
	private static ExclusionService exclusionService;

	//--------------------------------------------------- コンストラクタ
	/**
	 * コンストラクタ<br/>
	 * このオブジェクトはインスタンス化する必要がない。<br/>
	 */
	public ExclusionUtil() {
	}

	// -------------------------------------------------- SetGet Methods
	/**
	 * 排他制御サービスを設定する。<br/>
	 * @param exclusionService 排他制御サービス
	 */
	public void setExclusionService(ExclusionService exclusionService) {
		ExclusionUtil.exclusionService = exclusionService;
	}

	/**
	 * 排他情報を取得する。<br/>
	 * セッションより排他チェック用情報を格納している領域を取得。<br/>
	 * @param action Actionクラス
	 * @param request HttpServletRequest
	 * @return 排他情報
	 */
	public static Map getExclusionInfo(BaseAction action, HttpServletRequest request) {

		Map exclusion = (Map) action.getSessionAttr(request, getSessionKey(action));
		if (exclusion == null) {
			exclusion = new HashMap();
			action.setSessionAttr(request, getSessionKey(action), exclusion);
		}
		return exclusion;
	}

	/**
	 * 排他情報をクリアする。<br/>
	 * @param action Actionクラス
	 * @param request HttpServletRequest
	 * @return 排他情報
	 */
	public static void clearExclusionInfo(BaseAction action, HttpServletRequest request) {

		action.removeSessionAttr(request, getSessionKey(action));
	}

	/**
	 * 排他情報を設定する。<br/>
	 * @param exclusion 排他情報
	 * @param info 対象データ
	 * @param objectName オブジェクト名
	 * @param keyField キー項目
	 */
	public static void setExclusionInfo(Map exclusion, Map info, String objectName, String[] keyField) {

		List object = new ArrayList();
		exclusion.put(objectName, object);

		addExclusionInfo(exclusion, info, objectName, keyField);
	}

	/**
	 * 排他情報を設定する。<br/>
	 * @param exclusion 排他情報
	 * @param list 対象データ
	 * @param objectName オブジェクト名
	 * @param keyField キー項目
	 */
	public static void setExclusionInfo(Map exclusion, List list, String objectName, String[] keyField) {

		List object = new ArrayList();
		exclusion.put(objectName, object);

		int listSize = list.size();
		for (int i = 0; i < listSize; i++) {
			addExclusionInfo(exclusion, (Map) list.get(i), objectName, keyField);
		}
	}

	/**
	 * 排他情報を追加する。<br/>
	 * @param exclusion 排他情報
	 * @param info 対象データ
	 * @param objectName オブジェクト名
	 * @param keyField キー項目
	 */
	public static void addExclusionInfo(Map exclusion, Map info, String objectName, String[] keyField) {

		List object = (List) exclusion.get(objectName);
		if (object == null) {
			object = new ArrayList();
			exclusion.put(objectName, object);
		}

		Map record = new ListOrderedMap();
		for (int i = 0; i < keyField.length; i++) {
			record.put(keyField[i], info.get(keyField[i]));
		}
		for (int i = 0; i < CHECK_FIELD.length; i++) {
			record.put(CHECK_FIELD[i], info.get(CHECK_FIELD[i]));
		}
		object.add(record);
	}

	/**
	 * 排他情報を追加する。<br/>
	 * @param exclusion 排他情報
	 * @param list 対象データ
	 * @param objectName オブジェクト名
	 * @param keyField キー項目
	 */
	public static void addExclusionInfo(Map exclusion, List list, String objectName, String[] keyField) {

		int listSize = list.size();
		for (int i = 0; i < listSize; i++) {
			addExclusionInfo(exclusion, (Map) list.get(i), objectName, keyField);
		}
	}

	/**
	 * 排他情報を削除する。<br/>
	 * @param exclusion 排他情報
	 * @param objectName オブジェクト名
	 */
	public static void removeExclusionInfo(Map exclusion, String objectName) {

		exclusion.remove(objectName);
	}

	/**
	 * 排他チェック<br/>
	 * 更新前に格納した排他情報と比較する。<br/>
	 * @param exclusion 排他情報
	 * @param value 対象データのキー項目値
	 * @param objectName オブジェクト名
	 * @param keyField キー項目
	 * @param exception 例外
	 * @throws ApplicationException
	 */
	public static void checkExclusion(Map exclusion, String[] value, String objectName, String[] keyField, ApplicationException exception)
			throws ApplicationException {

		String sql = getExclusionCheckSQL(objectName, keyField);

		// 排他チェック用情報を取得
		Map result = exclusionService.srchExclusionInfo(sql, value);

		if (result.size() == 0) {
			throw exception;
		}

		// 該当データを比較
		Map record = getTargetInfo(exclusion, value, objectName, keyField);
		if (record == null) {
			return;
		}

		int fieldSize = CHECK_FIELD.length;
		for (int i = 0; i < fieldSize; i++) {
			if (!((String) record.get(CHECK_FIELD[i])).equals((String) result.get(CHECK_FIELD[i]))) {
				throw exception;
			}
		}
	}

	/**
	 * 排他チェック<br/>
	 * 更新前に格納した排他情報と比較する。<br/>
	 * @param exclusion 排他情報
	 * @param value 対象データのキー項目値
	 * @param objectName オブジェクト名
	 * @param keyField キー項目
	 * @throws ApplicationException
	 */
	public static void checkExclusion(Map exclusion, String[] value, String objectName, String[] keyField)
			throws ApplicationException {
		checkExclusion(exclusion, value, objectName, keyField, new ApplicationException("err.CO060"));
	}

	/**
	 * 排他チェック<br/>
	 * 更新前に格納した排他情報と比較する。<br/>
	 * @param exclusion 排他情報
	 * @param list 対象データ
	 * @param objectName オブジェクト名
	 * @param keyField キー項目
	 * @param exception 例外
	 * @throws ApplicationException
	 */
	public static void checkExclusion(Map exclusion, List list, String objectName, String[] keyField, ApplicationException exception)
			throws ApplicationException {

		int listSize = list.size();
		for (int i = 0; i < listSize; i++) {
			checkExclusion(exclusion, (String[]) list.get(i), objectName, keyField, exception);
		}
	}

	/**
	 * 排他チェック<br/>
	 * 更新前に格納した排他情報と比較する。<br/>
	 * @param exclusion 排他情報
	 * @param list 対象データ
	 * @param objectName オブジェクト名
	 * @param keyField キー項目
	 * @throws ApplicationException
	 */
	public static void checkExclusion(Map exclusion, List list, String objectName, String[] keyField)
			throws ApplicationException {

		int listSize = list.size();
		for (int i = 0; i < listSize; i++) {
			checkExclusion(exclusion, (String[]) list.get(i), objectName, keyField);
		}
	}

	/**
	 * セッションに格納するキーを取得する<br/>
	 * @param action Actionクラス
	 * @return
	 */
	private static String getSessionKey(BaseAction action) {

		return action.getPageID() + CommonConsts.KEY_EXCLUSION_INFO;
	}

	/**
	 * 排他チェック用SQLを作成する。<br/>
	 * @param objectName オブジェクト名
	 * @param keyField キー項目
	 * @return 排他チェック用SQL
	 */
	private static String getExclusionCheckSQL(String objectName, String[] keyField) {
		StringBuffer SQL = new StringBuffer(StrUtil.STR_BLANK);

		SQL.append("SELECT");
		for (int i = 0; i < CHECK_FIELD.length; i++) {
			SQL.append(" IFNULL( ");
			SQL.append((i == 0) ? " " : ", ");
			SQL.append(CHECK_FIELD[i]);
			SQL.append(" , '00000000') ");
		}
		SQL.append(" FROM " + objectName);
		SQL.append(" WHERE ");
		for (int i = 0; i < keyField.length; i++) {
			SQL.append((i == 0) ? "" : " AND ");
			SQL.append(keyField[i] + " = ?");
		}

		return SQL.toString();
	}

	/**
	 * 排他制御用情報から該当データを取得する。<br/>
	 * @param exclusion 排他情報
	 * @param value 対象データキー項目値
	 * @param objectName オブジェクト名
	 * @param keyField キー項目
	 * @return
	 */
	private static Map getTargetInfo(Map exclusion, String[] value, String objectName, String[] keyField) {

		List object = (List) exclusion.get(objectName);

		int fieldSize = keyField.length;

		// キー項目値を比較
		int recordSize = object.size();
		for (int i = 0; i < recordSize; i++) {

			Map record = (Map) object.get(i);
			boolean isResult = true;
			for (int j = 0; j < fieldSize; j++) {
				if (record.get(keyField[j]) == null) {
					throw new SystemException("排他情報に項目：" + keyField[j] + " が設定されていません。");
				}
				if (!value[j].equals(record.get(keyField[j]).toString())) {
					isResult = false;
					break;
				}
			}

			if (isResult) {
				return record;
			}
		}
		return null;
	}




	/**
	 * 排他情報のリロード（DBから）<br/>
	 *
	 * objectNameのテーブルからkeyFieldに該当するデータを取得（SELECT）し<br/>
	 * 排他情報をセッションに再設定する。<br/>
	 *
	 * 注意)
	 * 更新後、当画面を再表示する際、利用する。
	 * @param exclusion 排他情報
	 * @param value 対象データキー項目値
	 * @param objectName オブジェクト名
	 * @param keyField キー項目
	 */
	public static void reloadExclusionInfo (Map exclusion, String[] value, String objectName, String[] keyField) {

		// 排他情報の削除
		removeExclusionInfo(exclusion, objectName);

		String sql = getExclusionCheckSQL(objectName, keyField);

		// 排他チェック用情報を取得
		Map result = exclusionService.srchExclusionInfo(sql, value);

		if (result.size() != 0) {

			// PKの値を設定する。
			for (int i = 0; i < keyField.length; i++) {
				result.put(keyField[i], value[i]);
			}
			// リロードした排他情報を設定
			setExclusionInfo(exclusion, result, objectName, keyField);
		}
	}
}
