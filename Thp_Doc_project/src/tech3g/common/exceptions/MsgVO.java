package tech3g.common.exceptions;

/**
 * <pre>
 * MsgVOクラス。
 * </pre>
 */
public class MsgVO {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
	/** code */
	private final String code;
    /** arguments */
    private Object[] arguments;

    //--------------------------------------------------- コンストラクタ
    /**
     * コンストラクタ<br/>
     * @param errorCode エラーコード
     */
    public MsgVO(String errorCode) {
        this.code = errorCode;
    }

    /**
     * コンストラクタ<br/>
     * @param errorCode エラーコード
     * @param arg1 パラメータ１
     */
    public MsgVO(String errorCode, Object arg1) {
        this.code = errorCode;
        arguments = new Object[1];
        arguments[0] = arg1;
    }

    /**
     * コンストラクタ<br/>
     * @param errorCode エラーコード
     * @param arg1 パラメータ1
     * @param arg2 パラメータ2
     */
    public MsgVO(String errorCode, Object arg1, Object arg2) {
        this.code = errorCode;
        arguments = new Object[2];
        arguments[0] = arg1;
        arguments[1] = arg2;
    }

    /**
     * コンストラクタ<br/>
     * @param errorCode エラーコード
     * @param arg1 パラメータ1
     * @param arg2 パラメータ2
     * @param arg3 パラメータ3
     */
    public MsgVO(String errorCode, Object arg1, Object arg2, Object arg3) {
        this.code = errorCode;
        arguments = new Object[3];
        arguments[0] = arg1;
        arguments[1] = arg2;
        arguments[2] = arg3;
    }

    /**
     * コンストラクタ<br/>
     * @param errorCode エラーコード
     * @param arg1 パラメータ1
     * @param arg2 パラメータ2
     * @param arg3 パラメータ3
     * @param arg4 パラメータ4
     */
    public MsgVO(String errorCode, Object arg1, Object arg2, Object arg3, Object arg4) {
        this.code = errorCode;
        arguments = new Object[4];
        arguments[0] = arg1;
        arguments[1] = arg2;
        arguments[2] = arg3;
        arguments[3] = arg4;
    }

    /**
     * コンストラクタ<br/>
     *
     * @param errorCode エラーコード
     * @param args パラメータ配列
     */
    public MsgVO(String errorCode, Object[] args) {
        this.code = errorCode;
        arguments = args;
    }

    // -------------------------------------------------- SetGet Methods
    /**
     * エラーコードを返却する。<br/>
     * @return エラーコード
     */
    public String getCode() {
        return code;
    }

    /**
     * パラメータを返却する。<br/>
     * @return パラメータ配列
     */
    public Object[] getArguments() {
        return arguments;
    }

    //--------------------------------------------------- インスタンスメソッド
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
	@Override
	public String toString() {
        StringBuffer sb = new StringBuffer("{");
        sb.append("code : '" + code + "' ,");
        sb.append("arguments : ");
        if (arguments == null) {
            sb.append("'null'");
        } else {
            for (int i = 0; i < arguments.length; i++) {
                sb.append("'" + arguments[i] + "' ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
