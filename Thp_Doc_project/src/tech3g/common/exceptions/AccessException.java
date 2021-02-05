package tech3g.common.exceptions;

import org.apache.commons.lang.exception.NestableException;

/**
 * <pre>
 * AccessExceptionクラス。
 * 不正なアクセス、認証エラーの場合発生する例外。
 * </pre>
 */
public class AccessException extends NestableException{

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
    //--------------------------------------------------- コンストラクタ
	/**
	 * コンストラクタ<br/>
	 */
	public AccessException() {
		super("AccessException");
	}

	/**
	 * コンストラクタ<br/>
	 * @param message メッセージ
	 */
	public AccessException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ<br/>
	 * @param cause 発生元の例外
	 */
	public AccessException(Throwable cause) {
		super(cause);
	}

	/**
	 * コンストラクタ<br/>
	 * @param message メッセージ
	 * @param cause 発生元の例外
	 */
	public AccessException(String message, Throwable cause) {
		super(message, cause);
	}

    // -------------------------------------------------- SetGet Methods
    //--------------------------------------------------- インスタンスメソッド

}