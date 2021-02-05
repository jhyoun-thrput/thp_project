package tech3g.common.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import tech3g.common.exceptions.SystemException;
import tech3g.common.util.DateUtil;
import tech3g.common.util.StrUtil;



/**
 * <pre>
 * CommonParamsクラス。
 * 画面からのパラメータを管理するためのクラス。
 * Requestからパラメータを取得しこのクラスへ変換する。
 * </pre>
 *
 */
public class CommonParams {

	/** parameters */
	private Map parameters;

	private final Log log = LogFactory.getLog(CommonParams.class);

	/**
	 * コンストラクタ
	 * 共通パラメータインスタンスを作成する。<br/>
	 */
	public CommonParams() {
		parameters = new HashMap();
	}

	/**
	 * コンストラクタ<br/>
	 * Requestからパラメータを取得しパラメータマップへ格納する。 <br/>
	 *
	 * @param req
	 */
	public CommonParams(HttpServletRequest request, ActionForm form) {
		this();

		if (FileUpload.isMultipartContent(request)) {
		// パラメータがファイルの場合
			Map params = request.getParameterMap();
			Iterator it = params.keySet().iterator();
			Object key = null;
			Object value = null;
			while (it.hasNext()) {
				key = it.next();
				value = params.get(key);
				if (!parameters.containsKey(key)) {
					parameters.put(key, value);
				}
			}
			setFormValues(parameters, ((DynaActionForm) form).getMap());
		} else {
			Map params = request.getParameterMap();
			Iterator it = params.keySet().iterator();
			Object key = null;
			Object value = null;
			while (it.hasNext()) {
				key = it.next();
				value = params.get(key);
				parameters.put(key, value);
			}
		}
	}

	/**
	 * キーに該当する値を文字列の配列で返却する。<br/>
	 * 主に画面から同じ名前の項目が複数存在する場合使用<br/>
	 * @param key 項目キー
	 * @return 項目(値)の文字列配列
	 */
	public String[] getParamValues(String key) {
		String[] values = (String[])parameters.get(key);
		if (values == null) {
			return new String[0];
		}
		return values;
	}

	/**
	 * 複数のパラメータをセットする。
	 * @param key キー
	 * @param values 値
	 */
	public void setParamValues(String key, String[] values) {
		parameters.put(key, values);
	}


	/**
	 * キーに該当するパラメータを取得する。<br/>
	 * @param key キー
	 * @return 値
	 */
	public String getParam(String key) {
		String retValue = null;
		Object obj = parameters.get(key);

		if (obj == null) {
		    return null;
		}

		if (obj instanceof String[]) {
			String[] values = getParamValues(key);
			if (values != null) {
				if (values.length > 1) {
					retValue = "";
					for (int i = 0; i < values.length; i++)
						retValue += (retValue.length() > 0 ? "," : "") + values[i];
				} else if (values.length == 1) {
					retValue = values[0];
				}
			}
		} else {
			throw new SystemException("指定したキー項目のタイプは「String配列」ではありません。");
		}
		return retValue;
	}

	/**
	 * キーに該当するパラメータを取得する。<br/>
	 * もし値が存在しなければdefValueを返却する。<br/>
	 * @param key キー
	 * @param defValue Default値
	 * @return 値
	 */
	public String getParam(String key, String defValue) {
		String value = getParam(key);
		if (StrUtil.isEmpty(value)) {
			value = defValue;
		}

		return value;
	}

	/**
	 * パラメータを設定する。<br/>
	 * @param key キー
	 * @param value 値
	 * @return old値
	 */
	public String setParam(String key, String value) {
		String old = getParam(key);
		parameters.put(key, new String[] { value });
		return old;
	}

	/**
	 * 西暦に変換して取得<br/>
	 * @param key キー
	 * @return 値
	 */
	public String getParamSeireki(String key) {
		String value = getParam(key);
		if (StrUtil.isEmpty(value)) {
			return "";
		}
		return DateUtil.conv2Seireki(value);
	}

	/**
	 * 西暦に変換して取得<br/>
	 * @param key キー
	 * @param defValue Default値
	 * @return 値
	 */
	public String getParamSeireki(String key, String defValue) {

		String value = getParam(key);

		if (StrUtil.isEmpty(value)) {
			return defValue;
		}

		return DateUtil.conv2Seireki(value);
	}


	/**
	 * 西暦に変換して取得（複数）<br/>
	 *
	 * @param key キー
	 * @return 値
	 */
	public String[] getParamSeirekiValues(String key) {
		String[] value = getParamValues(key);
		if (value != null) {
    		for (int i = 0; i < value.length; i++) {
    			if (StrUtil.isEmpty(value[i])) {
    				value[i] = "";
    			} else {
    				value[i]  = DateUtil.conv2Seireki(value[i]);
    			}
    		}
		}
		return value;
	}


	/**
	 * 西暦に変換して取得（複数）<br/>
	 * @param key キー
	 * @param defValue Default値
	 * @return 値
	 */
	public String[] getParamSeirekiValues(String key, String defValue) {
		String[] value = getParamValues(key);
		if (value != null) {
			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					value[i] = defValue;
				} else {
					value[i]  = DateUtil.conv2Seireki(value[i]);
				}
			}
		}
		return value;
	}

	/**
	 * キーに該当するパラメータをbooleanで取得する。<br/>
	 * @param key キー
	 * @return 値
	 * @throws SystemException "true" "false"の文字列ではない場合の例外
	 */
	public boolean getParamAsBoolean(String key) throws SystemException {
		final String value = getParam(key);
		if (value.equalsIgnoreCase("true")) {
			return true;
		} else if (value.equalsIgnoreCase("false")) {
			return false;
		} else {
			throw new SystemException("Could not return a boolean value");
		}
	}

	/**
	 * キーに該当するパラメータをbooleanで取得する。<br/>
	 * もし値が存在しなければdefValueを返却する。<br/>
	 * @param key キー
	 * @param defValue Default値
	 * @return 値
	 * @throws SystemException "true" "false"の文字列ではない場合の例外
	 */
	public boolean getParamAsBoolean(String key, boolean defValue) throws SystemException {
		final String value = getParam(key);
		if (value == null || value.length() == 0) {
			return defValue;
		} else if (value.equalsIgnoreCase("true")) {
			return true;
		} else if (value.equalsIgnoreCase("false")) {
			return false;
		} else {
			throw new SystemException("Could not return a boolean value");
		}
	}

	/**
	 * キーに該当するパラメータをFloatで取得する。<br/>
	 * @param key キー
	 * @return 値
	 * @throws SystemException 数字変換できない場合の例外
	 */
	public float getParamAsFloat(String key) throws SystemException {
		try {
			return Float.parseFloat(StrUtil.removeAll(getParam(key), ","));
		} catch (final NumberFormatException e) {
			throw new SystemException("Could not return a float value", e);
		}
	}


	/**
	 * キーに該当するパラメータをFloatで取得する。<br/>
	 * もし値が存在しなければdefValueを返却する。<br/>
	 * @param key キー
	 * @param defValue Default値
	 * @return 値
	 */
	public float getParamAsFloat(String key, float defValue) {
		try {
			final String value = StrUtil.removeAll(getParam(key, null), ",");
			if (value == null) {
				return defValue;
			}

			return Float.parseFloat(value);
		} catch (final NumberFormatException pe) {
			return defValue;
		}
	}

	/**
	 * intタイプでパラメータを返却する。<br/>
	 * 値がない場合はDefaultの値を返却する。
	 * @param key キー
	 * @return 値
	 * @throws SystemException 数字変換できない場合の例外
	 */
	public int getParamAsInteger(String key) throws SystemException {
		try {
			return Integer.parseInt(StrUtil.removeAll(getParam(key), ","));
		} catch (final NumberFormatException e) {
			throw new SystemException("Could not return an integer value", e);
		}
	}

	/**
	 * intタイプでパラメータを返却する。<br/>
	 * 値がない場合はDefaultの値を返却する。
	 * @param key キー
	 * @param defValue Default値
	 * @return 値
	 */
	public int getParamAsInteger (String key, int defValue) {
		try {
			final String value = StrUtil.removeAll(getParam(key, null), ",");
			if (value == null) {
				return defValue;
			}

			return Integer.parseInt(value);
		} catch (final NumberFormatException e) {
			return defValue;
		}
	}

	/**
	 * Longタイプでパラメータを返却する。<br/>
	 * @param key キー
	 * @return longタイプのパラメータ値
	 * @throws SystemException 数字変換できない場合の例外
	 */
	public long getParamAsLong(String key) throws SystemException {
		try {
			return Long.parseLong(StrUtil.removeAll(getParam(key), ","));
		} catch (final NumberFormatException e) {
			throw new SystemException("Could not return a long value", e);
		}
	}

	/**
	 * Longタイプでパラメータを返却する。<br/>
	 * 値がない場合はDefaultの値を返却する。
	 * @param key
	 * @param defValue
	 * @return longタイプのパラメータ値
	 */
	public long getParamAsLong(String key, long defValue) {
		try {
			final String value = StrUtil.removeAll(getParam(key, null), ",");
			if (value == null) {
				return defValue;
			}
			return Long.parseLong(value);
		} catch (final NumberFormatException e) {
			return defValue;
		}
	}

	/**
	 * 指定したオブジェクトをセットする。<br/>
	 * @param key キー
	 * @param value 値
	 */
	public void setParamObj(String key, Object value) {
		parameters.put(key, value);
	}

	/**
	 * キーに該当するオブジェクトを取得する。<br/>
	 * @param key キー
	 * @param defValue default値
	 * @return
	 */
	public Object getParamObj(String key, Object defObj) {

		Object obj = parameters.get(key);

		if (obj == null) {
			obj = defObj;
		}
		return obj;
	}

	/**
	 * 指定したキーのパラメータが存在しているか確認する。<br/>
	 * @param key キー
	 * @return true | false
	 */
	public boolean containsKey(String key) {
		return parameters.containsKey(key);
	}

	/**
	 * 指定したパラメータを削除する。 <br/>
	 * @param key
	 */
	public void removeParam(String key) {
		parameters.remove(key);
	}

	/**
	 * パラメータ名を取得する。<br/>
	 * @return
	 */
	public String[] getNames() {
		ArrayList<String> names = new ArrayList<String>();
		Iterator<String> itr = parameters.keySet().iterator();
		while (itr.hasNext()) {
			names.add(itr.next());
		}
		return names.toArray(new String[0]);
	}

	/**
	 * ViewBeanを作成しマージして返却する。<br/>
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public Object merge2ViewBean(Class<? extends Object> clazz) throws Exception {
		Object retObj = clazz.newInstance();
		BeanUtils.populate(retObj, parameters);
		return retObj;
	}


	/**
	 * パラメータマップを返却する。<br/>
	 * @return
	 */
	public Map getParamMap() {
		return this.parameters;
	}

	/**
	 * パラメータがファイルの場合、処理を行う。<br/>
	 * @param prop パラメータマップ
	 * @return パラメータマップ
	 */
	protected Map setFormValues(Map params, Map prop) {

		Hashtable parameters = new Hashtable();
		Iterator it = prop.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = prop.get(key);
			if (value instanceof org.apache.struts.upload.FormFile) {
				params.put(key, value);
				log.debug("Muitipart-Request Parameters: key=" + key + " :value =" + value);
			}
		}
		return parameters;
	}

	/**
	 * ファイル取得<br/>
	 * キーに該当するパラメータファイルを取得する。<br/>
	 * @param key キー
	 * @return FormFile ファイル
	 */
	public FormFile getParamFile(String key) {
		Object obj = parameters.get(key);

		if (obj == null) {
		    return null;
		}
		return (FormFile)obj;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		StringBuffer sb = new StringBuffer();

		Iterator<String> it = parameters.keySet().iterator();
		String key = null;
		Object value = null;
		String[] valueStrArr = null;
		while (it.hasNext()) {
			key = it.next();
			value = parameters.get(key);

			if (value instanceof Object[]) {

				valueStrArr = (String[]) value;
				sb.append(key);
				sb.append("={");

				for (int i = 0; i < valueStrArr.length; i++) {
					sb.append(valueStrArr[i]);
					if (!(i == valueStrArr.length-1)) {
						sb.append(",");
					}

				}
	    		sb.append("} ");
			} else {
				sb.append(key);
				sb.append("={");
	    		sb.append(value.toString());
	    		sb.append("} ");
			}
		}

		return sb.toString();
	}

}