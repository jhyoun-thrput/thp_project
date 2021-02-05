package tech3g.common.exceptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.exception.NestableException;

/**
 * <pre>
 * ApplicationExceptionクラス。
 * ビジネスエラー（業務的なエラー）を処理する例外。
 *
 * エラーメッセージとメッセージのパラメータを格納しthrowする
 *
 * </pre>
 */
public class ApplicationException extends NestableException {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
	/** 発生元の例外 */
	protected Throwable cause;

	/** エラーコードリスト */
	private final List errorCodeList;

    //--------------------------------------------------- コンストラクタ
	/**
	 * コンストラクタ<br/>
	 * @version 修正履歴
	 */
	public ApplicationException() {
		super("ApplicationException(ビジネス例外)");
		errorCodeList = new ArrayList();
	}

	/**
	 * コンストラクタ<br/>
	 * メッセージを格納し例外を作成する。<br/>
	 * @param errorCode メッセージキーまたは、メッセージ
	 */
	public ApplicationException(String errorCode) {
		this();
		addErrorCode(errorCode);
	}


	/**
	 * コンストラクタ<br/>
	 * メッセージを格納し例外を作成する。<br/>
	 * @param errorCode メッセージキーまたは、メッセージ
	 * @param arg0 メッセージのパラメータ１
	 */
	public ApplicationException(String errorCode, String arg0) {
		this();
		addErrorCode(errorCode, arg0);
	}

	/**
	 * コンストラクタ<br/>
	 * メッセージを格納し例外を作成する。<br/>
	 * @param errorCode メッセージキーまたは、メッセージ
	 * @param arg0 メッセージのパラメータ1
	 * @param arg1 メッセージのパラメータ2
	 */
	public ApplicationException(String errorCode, String arg0, String arg1) {
		this();
		addErrorCode(errorCode, arg0, arg1);
	}

	/**
	 * コンストラクタ<br/>
	 * メッセージを格納し例外を作成する。<br/>
	 * @param errorCode メッセージキーまたは、メッセージ
	 * @param arg0 メッセージのパラメータ1
	 * @param arg1 メッセージのパラメータ2
	 * @param arg2 メッセージのパラメータ3
	 */
	public ApplicationException(String errorCode, String arg0, String arg1, String arg2) {
		this();
		addErrorCode(errorCode, arg0, arg1, arg2);
	}


	/**
	 * コンストラクタ<br/>
	 * メッセージを格納し例外を作成する。<br/>
	 * @param errorCode メッセージキーまたは、メッセージ
	 * @param arg0 メッセージのパラメータ1
	 * @param arg1 メッセージのパラメータ2
	 * @param arg2 メッセージのパラメータ3
	 * @param arg3 メッセージのパラメータ4
	 */
	public ApplicationException(String errorCode, String arg0, String arg1, String arg2, String arg3) {
		this();
		addErrorCode(errorCode, arg0, arg1, arg2, arg3);
	}



	/**
	 * コンストラクタ<br/>
	 * メッセージを格納し例外を作成する。<br/>
	 * @param errorCode メッセージキーまたは、メッセージ
	 * @param cause 発生元の例外
	 */
	public ApplicationException(String errorCode, Throwable cause) {
		this(errorCode);
		this.cause = cause;
	}

    // -------------------------------------------------- SetGet Methods
    //--------------------------------------------------- インスタンスメソッド

	/**
	 * エラーコードをエラーメッセージリストへ追加する。<br/>
	 * @param code エラーコード
	 */
	public void addErrorCode(String code) {
		errorCodeList.add(new MsgVO(code));
	}

	/**
	 * エラーコードと引数１をエラーメッセージリストへ追加する。<br/>
	 * @param code エラーコード
	 * @param arg0 引数１
	 */
	public void addErrorCode(String code, Object arg0) {
		errorCodeList.add(new MsgVO(code, arg0));
	}

	/**
	 * エラーコードと引数１、引数２をエラーメッセージリストへ追加する。<br/>
	 * @param code エラーコード
	 * @param arg0 引数１
	 * @param arg1 引数２
	 */
	public void addErrorCode(String code, Object arg0, Object arg1) {
		errorCodeList.add(new MsgVO(code, arg0, arg1));
	}

	/**
	 * エラーコードと引数１、引数２、引数３をエラーメッセージリストへ追加する。<br/>
	 *
	 * @param code エラーコード
	 * @param arg0 引数１
	 * @param arg1 引数２
	 * @param arg2 引数３
	 */
	public void addErrorCode(String code, Object arg0, Object arg1, Object arg2) {
		errorCodeList.add(new MsgVO(code, arg0, arg1, arg2));
	}

	/**
	 * エラーコードと引数１、引数２、引数３、引数４をエラーメッセージリストへ追加する。<br/>
	 * @param code エラーコード
	 * @param arg0 引数１
	 * @param arg1 引数２
	 * @param arg2 引数３
	 * @param arg3 引数４
	 */
	public void addErrorCode(String code, Object arg0, Object arg1, Object arg2, Object arg3) {
		errorCodeList.add(new MsgVO(code, arg0, arg1, arg2, arg3));
	}

	/**
	 * エラーコードと引数配列をエラーメッセージリストへ追加する。<br/>
	 * @param code エラーコード
	 * @param args 引数配列
	 */
	public void addErrorCode(String code, Object[] args) {
		errorCodeList.add(new MsgVO(code, args));
	}

	/**
	 * エラーリストから指定したコードのメッセージを削除する。<br/>
	 * @param code エラーコード
	 */
	public void removeErrorCode(String code) {
		errorCodeList.remove(new MsgVO(code));
	}

	/**
	 * <br/>
	 * @return エラーコードリスト
	 */
	public List getErrorCodeList() {
		return errorCodeList;
	}

	/**
	 * <br/>
	 * @return エラーコードリスト
	 */
	public List getCodeList() {
		List codeList = new ArrayList();
		Iterator it = errorCodeList.iterator();
		while (it.hasNext()) {
			MsgVO code = (MsgVO) it.next();
			codeList.add(code.getCode());
		}
		return codeList;
	}

	/**
	 * コードリストが空であるか判定する。<br/>
	 * @return boolean
	 */
	public boolean isEmpty() {
		if (this.errorCodeList.isEmpty()) {
			return true;
		}
		return false;
	}

}
