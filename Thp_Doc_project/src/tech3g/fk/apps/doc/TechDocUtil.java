/**
 *
 */
package tech3g.fk.apps.doc;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.jfree.util.Log;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.ApplicationException;
import tech3g.common.exceptions.CommonException;
import tech3g.common.exceptions.MsgVO;
import tech3g.common.exceptions.RollbackCommonException;
import tech3g.common.exceptions.SystemException;
import tech3g.common.util.Base64;
import tech3g.common.util.DateUtil;
import tech3g.common.util.FileUtil;
import tech3g.common.util.NumberUtil;
import tech3g.common.util.StrUtil;
import tech3g.common.util.ValidationUtil;

/**
 * @author tech3g
 *
 */
public class TechDocUtil {

	//*************** Constance Zone *********************************
	/** Shift_JIS encoding type */
	public static final String SHIFT_ENCODING = "Shift_JIS";

	/** MS932 encoding type*/
	public static final String FD_FILE_ENCODING = "MS932";

	public static final String UTF_8 = "UTF-8";

	/** Zero Counter */
	public static final int ZERO_COUNT = 0;

	/** パーセントの最大値（100%） */
	public static final int MAX_PERCENT = 100;

	/** アップデートSEQの基本(初期)値 */
	public static final String UPDATE_SEQ_DEFAULT = "0";

	/** split文字 */
	public static final String STR_SPLIT_CHAR = ",";

	/** 控除カラム名join文字 */
	public static final String KOJO_JOIN_CHAR = "+";

	/** ページ番号初期値 */
	public static final String PAGE_NO_DEFAULT = "1";

	/** テーブルスクロール初期値 */
	public static final String TABLE_SCROLL_DEFAULT = "0";

	/** UNDER_LINE */
	public static final String UNDER_LINE_FIX = "_";

	public static final String _BLANK = " ";

	/** ファイルの開閉有無 */
	private static boolean isLocked = false;

	/**
	 * String系リスト複写
	 * <br/>
	 * @param list 複写元のリスト
	 * @return 複写されたリスト
	 */
	public static List<Map<String, String>> copyList(List<Map<String, String>> list) {
		List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
		for (Map<String, String> map : list) {
			Map<String, String> lom = new HashMap<String, String>();
			lom.putAll(map);
			temp.add(lom);
		}
		return temp;
	}

	/**
	 * Object系リスト複写
	 * <br/>
	 * @param list 複写元のリスト
	 * @return 複写されたリスト
	 */
	public static List<Map<String, Object>> copyObjList(List<Map<String, Object>> list) {
		List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			Map<String, Object> lom = new HashMap<String, Object>();
			lom.putAll(map);
			temp.add(lom);
		}
		return temp;
	}

	/**
	 * String系マップ複写
	 * <br/>
	 * @param map 複写元のマップ
	 * @return 複写されたマップ
	 */
	public static Map<String, String> copyMap(Map<String, String> map) {
		Map<String, String> temp = new HashMap<String, String>();
		temp.putAll(map);
		return temp;
	}

	/**
	 * Object系マップ複写
	 * <br/>
	 * @param map 複写元のマップ
	 * @return 複写されたマップ
	 */
	public static Map<String, Object> copyMapObj(Map<String, Object> map) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.putAll(map);
		return temp;
	}

	/**
	 * ListOrderedMap系マップ複写
	 * <br/>
	 * @param orderMap
	 * @return
	 */
	public static ListOrderedMap copyListOrderMap(ListOrderedMap orderMap) {
		ListOrderedMap _orderMap = new ListOrderedMap();
		_orderMap.putAll(orderMap);

		return _orderMap;
	}

	/**
	 * 金額文字列をBigDecimalに変換
	 * <br/>
	 * @param obj 金額文字列
	 * @return 金額（BigDecimal, null又は空白の場合は0を返す）
	 */
	public static BigDecimal convMoney(Object obj) {
		String str = String.valueOf(obj);
		if (obj == null || str.equals("null") || StrUtil.isBlank(str)) {
			str = "0";
		}
		return NumberUtil.conv2BigDecimal(str);
	}

	/**
	 * Objectを文字列に変換（nullの場合空白を返す）
	 * <br/>
	 * @param obj
	 * @return 文字列
	 */
	public static String convToStr(Object obj) {
		return String.valueOf(obj).equals("null")? StrUtil.STR_BLANK:String.valueOf(obj);
	}

	/**
	 * ・String文字列をint形式で変換して返す。(Emptyの場合、「0」を返す)
	 * <br/>
	 * @param str
	 * @return 正数値
	 */
	public static int convToInteger(String str) {
		return (StrUtil.isEmpty(str)?ZERO_COUNT:Integer.parseInt(str));
	}

	/**
	 * ・該当画面でバリデーションえエラーが生じた場合、
	 * 　該当項目にフォーカスを返すためのエラー作成モジュール
	 * <br/>
	 * <pre>
	 * 		エラーメッセージVO['エラーメッセージ', 'エラー変数名']
	 * </pre>
	 * ・上記のように、メッセージと該当項目変数が両方とも存在する場合のみ、加工処理を行う
	 *
	 * @param errList
	 * @return 加工済エラーメッセージ「err1, err2」
	 */
	public static String getErrMsgs(List<MsgVO> errList) {

		StringBuffer _errMsg = new StringBuffer();

		// バリデーションエラーメッセージを格納
		List<MsgVO> _innerErrList = errList;

		if (!_innerErrList.isEmpty()) {
			for (int index = 0; index < _innerErrList.size(); index++) {
				// リストから、該当エラーメッセージを取得
				Object[] _errMsgVO =((MsgVO) _innerErrList.get(index)).getArguments();

				if (_errMsgVO.length >= 2) {
					String _tempValue = (String) _errMsgVO[_errMsgVO.length - 1];
					if (!StrUtil.isEmpty(_tempValue)) {
						_errMsg.append(_tempValue);
						_errMsg.append(",");
					}
				}
			}
    	}

		// 加工済エラーメッセージを返す。
		return (_errMsg.toString());
	}


	/**
	 * ・指定されたヘッダー項目をファイル作成用のヘッダ部を作成する。
	 * <br/>
	 * @param headerTile ヘッダー項目「出力順」
	 * @return ヘッダーデータ
	 */
	public static StringBuffer makeFileHeaderData(String[] headerTitle) {

		String[] _header = headerTitle;
		StringBuffer _headerBuffer = new StringBuffer();

		if ((StrUtil.isNull(_header)) || (_header.length <= ZERO_COUNT)) {
			return _headerBuffer;
		}

		for (int index = 0; index < _header.length; index++) {

			_headerBuffer.append(_header[index]);

			if ((index + 1) != _header.length) {
				_headerBuffer.append(STR_SPLIT_CHAR);
			}
			else {
				_headerBuffer.append(StrUtil.LINE_SEP);
			}
		}

		return _headerBuffer;
	}

	/**
	 * ・指定された検索結果を基に、ファイル作成用のデータ部を作成する。
	 * <br/>
	 * @param list 検索結果リスト「出力順」
	 * @return ファイルのボティーデータ
	 */
	public static StringBuffer makeFileBodyData(List<ListOrderedMap> list) {

		List<String> _valueList = null;
		StringBuffer _dataBuffer = new StringBuffer();

		if (list.isEmpty()) {
			return _dataBuffer;
		}

		for (int index = 0; index < list.size(); index++) {

			// 該当SEQ値リストを取得
			_valueList =  list.get(index).valueList();
			for (int v_index = 0; v_index < _valueList.size(); v_index++) {

				String _value = String.valueOf(_valueList.get(v_index));

				// 生年月日の加工(西暦 -> 和暦)
				if (_value.length() == 8 && ValidationUtil.isNum(_value)) {
					if ((ValidationUtil.isYYYYMMDD(_value))) {
						_value = DateUtil.conv2Wareki(_value);		// 日付返還：YYYYMMDD -> JYYMMDD
					}
				}
				_dataBuffer.append("\"");
				_dataBuffer.append(_value);
				_dataBuffer.append("\"");
				_dataBuffer.append(STR_SPLIT_CHAR);
			}

			// 改行
			_dataBuffer.append(StrUtil.LINE_SEP);
		}

		return _dataBuffer;
	}

	/**
	 * ・指定されたパラメタを基に、該当ファイルを作成する。
	 * <br/>
	 * @param filePath 該当ファイル経路
	 * @param encodingType 文字タイプコード 【'Shift_JIS', 'MS932', 'UTF-8'】
	 * @param fileContntsData ファイルに書き込む内容
	 * @return ファイル作成有無
	 * @throws ApplicationException
	 */
	public static boolean createFile(String filePath, String encodingType, String fileContntsData) throws ApplicationException {

		// 戻り値の初期化
		boolean _rtnResult = false;

		if (StrUtil.isEmpty(filePath) || StrUtil.isEmpty(encodingType) || StrUtil.isEmpty(fileContntsData)) {
			return _rtnResult;
		}

		try {

			File _file = new File(filePath);

			// 既存ファイルが存在する場合、当ファイルを削除後、再作成する。
			if (_file.exists()) {
				_file.delete();
			}

			// 指定された文字コードタイプで、該当ファイルの作成を行う。
			FileUtil.writeFile(_file.getPath(), fileContntsData.getBytes(encodingType));

			// 作成が成功した場合のみ、Trueを返す。
			_rtnResult = true;

		} catch (UnsupportedEncodingException e) {
			Log.warn(e);
			throw new ApplicationException("err.CO037", "文字コードタイプ");

		} catch (java.io.FileNotFoundException e) {
			Log.warn(e);
			throw new ApplicationException("err.HN007");

		} catch (IOException e) {
			Log.warn(e);
			throw new SystemException(CommonConsts.KEY_MSG_FAILURE, new String[] {"ファイル作成"});
		}

		return _rtnResult;
	}

	/**
	 * ・該当施設の延長ファイルを作成する際、既存の同一施設ファイルは削除する。
	 * 　最新で作成されたファイルのみ、保存しておく。但し、同一施設のファイルのみ削除処理を行う。
	 * <br/>
	 * @param dirPath Directory Path
	 * @param currntFileNm 削除対象ファイル名
	 * @return ファイル削除件数
	 */
	public static int deleteEnchoFile(String dirPath, String currntFileNm) {

		int _rtnCnt = ZERO_COUNT;

		if (StrUtil.isEmpty(dirPath) || StrUtil.isEmpty(currntFileNm)) {
			return _rtnCnt;
		}

		File[] _fileList = null;
		File _file = new File(dirPath);

		if (_file.isDirectory()) {
			_fileList = _file.listFiles();

			for (int index = 0; index < _fileList.length; index++) {
				File _innerFile = _fileList[index];

				if (_innerFile.getName().indexOf(currntFileNm) != -1) {
					_innerFile.delete();
					++_rtnCnt;
				}
			}
		}

		return _rtnCnt;
	}

	/**
	 * ・指定されたコードリストをラベルコードリストに変換する。
	 * <br/>
	 * @param list コードリスト
	 * @return ラベルコードリスト
	 */
	public static List<LabelValueBean> convertLabelList(List<ListOrderedMap> list) {

		List<LabelValueBean> rtnList = new ArrayList<LabelValueBean>();
		LabelValueBean tempBaen = new LabelValueBean();
		tempBaen.setLabel("-----------------");
		LabelValueBean bean = null;

		rtnList.add(tempBaen);

		if (!list.isEmpty()) {
			for (int index = 0; index < list.size(); index++) {

				ListOrderedMap _map = list.get(index);
				bean = new LabelValueBean();

				bean.setLabel(String.valueOf(_map.get("doc_code_name")));
				bean.setValue(String.valueOf(_map.get("doc_code")));
				rtnList.add(bean);
			}
		}
		return rtnList;
	}

	public static List<LabelValueBean> getUserIdList(List<ListOrderedMap> list) {

		List<LabelValueBean> rtnList = new ArrayList<LabelValueBean>();
		LabelValueBean tempBaen = new LabelValueBean();
		tempBaen.setLabel("-----------------");
		LabelValueBean bean = null;

		rtnList.add(tempBaen);

		if (!list.isEmpty()) {
			for (int index = 0; index < list.size(); index++) {

				ListOrderedMap _map = list.get(index);
				bean = new LabelValueBean();

				bean.setLabel(String.valueOf(_map.get("user_id")));
				bean.setValue(String.valueOf(_map.get("user_id")));
				rtnList.add(bean);
			}
		}
		return rtnList;
	}

	public static void setLocked(String fileName) throws ApplicationException {

		if (isLocked) {
			throw new ApplicationException("err.CO008", fileName, "取込処理");
		}
		isLocked = true;	// ロックかける。
	}
	/**
	 * ・該当ファイルのロックを解除する。
	 * <br/>
	 */
	public static void clearLock() {

		if (isLocked) {
			isLocked = false;		// ロックの解除
		}
	}

	public static String encode(String str) {
		try {
			if (str != null && !"".equals(str)) {
				str = URLEncoder.encode(str, "utf-8");
				str = Base64.encode(str.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String decode(String str) throws CommonException {
		try {

			if (str != null && !str.equals("")) {
				str = URLDecoder.decode(new String(Base64.decode(str)), "utf-8");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RollbackCommonException("E99000000", e);
		}
		return str;
	}
}
