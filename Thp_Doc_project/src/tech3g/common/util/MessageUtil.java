package tech3g.common.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

/**
 * <pre>
 * MessageUtilクラス。
 * メッセージに関するUtilクラス。
 * Springから提供するMessageSourceのAdapterクラス。
 *
 * </pre>
 */
public class MessageUtil {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
    /** messageSource */
    protected static MessageSource messageSource;

	//--------------------------------------------------- コンストラクタ
    /**
	 * コンストラクタ<br/>
	 * このオブジェクトはインスタンス化する必要がない。
	 */
	private MessageUtil()	{
	}
    // -------------------------------------------------- SetGet Methods
	/**
	 * messageSourceを設定する。<br/>
	 * @param messageSource messageSource
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

    //--------------------------------------------------- インスタンスメソッド

	/**
	 * 引数のメッセージキーでPropertiesに定義したメッセージを取得する。<br/>
	 * @param msg メッセージキー
	 * @return メッセージ
	 */
	public static String getMessage (String msg) {
		try {
			return messageSource.getMessage(msg, null, Locale.JAPANESE);
		} catch (NoSuchMessageException e) {
			return msg;
		}
	}

	/**
	 * 引数のメッセージキーとパラメータで
	 * Propertiesに定義したメッセージを取得する。<br/>
	 * @param msgCD メッセージキー
	 * @param args パラメータ
	 * @return メッセージ
	 */
	public static String getMessage (String msgCD, String[] args) {
		try {
			return messageSource.getMessage(msgCD, args, Locale.JAPANESE);
		} catch (NoSuchMessageException e) {
			return msgCD;
		}
	}

	/**
	 * 引数のメッセージキーとパラメータで
	 * Propertiesに定義したメッセージを取得する。<br/>
	 * @param msgCD メッセージキー
	 * @param args パラメータ
	 * @return メッセージ
	 */
	public static String getMessage (String msgCD, Object[] args) {
		try {
			return messageSource.getMessage(msgCD, args, Locale.JAPANESE);
		} catch (NoSuchMessageException e) {
			return msgCD;
		}
	}
}
