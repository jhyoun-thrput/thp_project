package tech3g.common.db;

/**
 * <pre>
 * SqlParamInterface。
 * sqlを実行するためのパラメータ規約を定義するInterface。
 * </pre>
 */
public interface SqlParam {

    /**
     * Anyframeで定義しているタイプでパラメータを返却する。<br/>
     * @return
     */
    public Object[] convObjArray();

}
