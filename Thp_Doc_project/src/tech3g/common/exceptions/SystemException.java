package tech3g.common.exceptions;

import org.apache.commons.lang.exception.NestableRuntimeException;

import tech3g.common.util.MessageUtil;

/**
 * <pre>
 * SystemExceptionクラス。
 * ネットワークエラー、プログラムバグなどのシステム障害が発生した場合に
 * 発生する例外。
 * </pre>
 */
public class SystemException extends NestableRuntimeException{

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
	//--------------------------------------------------- コンストラクタ
	/**
	 * コンストラクタ<br/>
	 * @param msg エラーメッセージキーまたはメッセージ
	 * @param e 原因になる例外
	 */
	public SystemException(String msg, Throwable e) {
		super(MessageUtil.getMessage(msg), e);
	}

	/**
	 * コンストラクタ<br/>
	 * @param e 原因になる例外
	 */
	public SystemException(Throwable e) {
		super("System例外が発生しました。" ,e);
	}

	/**
	 * コンストラクタ<br/>
	 * @param msg エラーメッセージキーまたはメッセージ
	 */
	public SystemException(String msg) {
		super(MessageUtil.getMessage(msg));
	}

	/**
	 * コンストラクタ<br/>
	 * @param msg エラーメッセージキー
	 * @param arg0 引数0
	 */
	public SystemException(String msg, String arg0) {
		super(MessageUtil.getMessage(msg, new String[] { arg0 }));
	}

	/**
	 * コンストラクタ<br/>
	 * @param msg エラーメッセージキー
	 * @param arg0 引数0
	 * @param arg1 引数1
	 */
	public SystemException(String msg, String arg0, String arg1) {
		super(MessageUtil.getMessage(msg, new String[] { arg0, arg1 }));
	}

	/**
	 * コンストラクタ<br/>
	 * @param msg エラーメッセージキー
	 * @param arg0 引数0
	 * @param arg1 引数1
	 * @param arg2 引数2
	 */
	public SystemException(String msg, String arg0, String arg1, String arg2) {
		super(MessageUtil.getMessage(msg, new String[] { arg0, arg1, arg2 }));
	}

	/**
	 * コンストラクタ<br/>
	 * @param msg エラーメッセージキー
	 * @param arg0 引数0
	 * @param arg1 引数1
	 * @param arg2 引数2
	 * @param arg3 引数3
	 */
	public SystemException(String msg, String arg0, String arg1, String arg2, String arg3) {
		super(MessageUtil.getMessage(msg, new String[] { arg0, arg1, arg2, arg3 }));
	}

	/**
	 * コンストラクタ<br/>
	 * @param msg エラーメッセージキーまたはメッセージ
	 * @param args パラメータ
	 */
	public SystemException(String msg, String[] args) {
		super(MessageUtil.getMessage(msg, args));
	}

	/**
	 * コンストラクタ<br/>
	 * @param msg エラーメッセージキーまたはメッセージ
	 * @param args パラメータ
	 * @param e 原因
	 */
	public SystemException(String msg, String[] args, Throwable e) {
		super(MessageUtil.getMessage(msg, args), e);
	}
    // -------------------------------------------------- SetGet Methods
    //--------------------------------------------------- インスタンスメソッド
}
