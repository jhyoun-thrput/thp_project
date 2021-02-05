package tech3g.common.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tech3g.common.util.DateUtil;
import tech3g.common.util.NumberUtil;
import tech3g.common.util.StrUtil;

/**
 * <pre>
 * CallSqlParamクラス。
 * CallableStatmentを利用する時のSQLパラメータクラス
 *
 * 注意) DynaQueryはサポートしていない。
 * </pre>
 */
public class CallSqlParam implements SqlParam {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
    /** paramMap */
    private final Map<String, Object> paramMap;

    //--------------------------------------------------- コンストラクタ
    /**
     * コンストラクタ<br/>
     */
    public CallSqlParam() {
    	super();
        paramMap = new HashMap<String, Object>();
    }

    // -------------------------------------------------- SetGet Methods
    //--------------------------------------------------- インスタンスメソッド
    /**
     * SQLパラメータを追加する。<br/>
     * @param key パラメータキー
     * @param value パラメータ
     */
    public void addParam(String key, Object value) {
        paramMap.put(key, value);
    }

    /**
     * 日付形式のSQLパラメータを追加する。<br/>
     *
     * @param key パラメータキー
     * @param value パラメータ
     */
    public void addParamDate(String key, String value) {
        paramMap.put(key, DateUtil.conv2Seireki(value));
    }

	/**
	 * 数字のSQLパラメータを追加する。<br/>
     * @param key パラメータキー
     * @param value パラメータ
	 */
	public void addParamDecimal(String key, String value) {

		if (StrUtil.isEmpty(value)) {
			paramMap.put(key, value);
		} else {
			paramMap.put(key, NumberUtil.conv2BigDecimal(value));
		}
	}


    /**
     * 指定したキーのパラメータが存在するかチェックする。<br/>
     * @param key パラメータキー
     * @return boolean
     */
    public boolean containsKey (String key) {
        return paramMap.containsKey(key);
    }

    /**
     * 指定したキーのパラメータを取得する。<br/>
     * @param key パラメータキー
     * @return パラメータ
     */
    public Object getParam(String key) {
        return paramMap.get(key);
    }

    /**
     * パラメータをQueryServiceのダイナミッククエリから<br/>
     * 使用できるタイプへ変換する。<br/>
     *
     * @return Objectの配列
     */
    public Object[] convObjArray() {
        List list = new ArrayList();
        Iterator<String> it = paramMap.keySet().iterator();
        String key = null;
        while (it.hasNext()) {
            key = it.next();
            list.add(new Object[] {key, clearNull(paramMap.get(key))});
        }
        return list.toArray(new Object[0][0]);
    }

    public Map getParamMap() {
    	return paramMap;
    }


    /**
     * 指定したObjectがNullの場合ブランクを返却する。<br/>
     * @param obj オブジェクト
     * @return チェック結果
     */
    private Object clearNull (Object obj) {
    	if (obj == null) {
    		return "";
    	} else {
    		return obj;
    	}
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator<String> it = paramMap.keySet().iterator();
        String key = null;
        while (it.hasNext()) {
            key = it.next();
            sb.append(key);
            sb.append("={");
            sb.append(paramMap.get(key));
            sb.append("} ");
        }
        return sb.toString();
    }

}
