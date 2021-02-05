package tech3g.common.db;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * BatchSqlParamListクラス。
 * </pre>
 */
public class BatchSqlParamList {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
    /** params */
    private final ArrayList params = new ArrayList();
    /** args */
    private final ArrayList args = new ArrayList();

    //--------------------------------------------------- コンストラクタ
    /**
     * Defaultコンストラクタ<br/>
     */
    public BatchSqlParamList() {
    	super();
    }
    //--------------------------------------------------- インスタンスメソッド
    /**
     * <br/>
     * @param param
     */
    public void addParam(Object param) {
        args.add(param);
    }

    /**
     * <br/>
     */
    public void flush() {
        params.add(args.toArray(new Object[0]));
        args.clear();
    }

    /**
     * <br/>
     * @return
     */
    public List getParams() {
        return params;
    }

}
