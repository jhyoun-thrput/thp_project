package tech3g.common.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tech3g.common.util.DateUtil;
import tech3g.common.util.NumberUtil;

/**
 * <pre>
 * 静的SQLパラメータクラス。
 * Staticクエリにパラメータを設定するためのパラメータクラス。
 * </pre>
 */
public class StaticSqlParam implements SqlParam{

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
    /** paramList */
    private final List paramList;

    //--------------------------------------------------- コンストラクタ
    /**
     * コンストラクタ<br/>
     */
    public StaticSqlParam() {
    	super();
        paramList = new ArrayList();
    }

    // -------------------------------------------------- SetGet Methods
    //--------------------------------------------------- インスタンスメソッド
    /**
     * パラメータを追加する。<br/>
     * @param value
     */
    public void addParam(Object value) {
        paramList.add(value);
    }

    /**
     * 日付形式のSQLパラメータを追加する。<br/>
     * @param value パラメータ
     */
    public void addParamDate(String value) {
        paramList.add(DateUtil.conv2Seireki(value));
    }

	/**
	 * 数字のSQLパラメータを追加する。<br/>
     * @param value パラメータ
	 */
	public void addParamDecimal(String value) {
        paramList.add(NumberUtil.conv2BigDecimal(value));
	}



    /**
     * パラメータがすでに設定されているかチェックする。<br/>
     * @param o パラメータ
     * @return true | false
     */
    public boolean containsValue (Object o) {
        return paramList.contains(o);
    }

    /**
     * 該当インデックスのパラメータを取得する。<br/>
     * @param idx 取得するパラメータのインデックス
     * @return パラメータ
     */
    public Object getParam(int idx) {
        return paramList.get(idx);
    }


    /* (non-Javadoc)
     * @see liveany.common.dao.SqlParam#convObjArray()
     */
    public Object[] convObjArray() {

        return paramList.toArray(new Object[0]);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        Iterator it = paramList.iterator();
        Object value;

        sb.append("={");
        while (it.hasNext()) {
            value = it.next();
            sb.append(value.toString());
            sb.append(", ");

        }
        sb.append("} ");
        return sb.toString();
    }
}
