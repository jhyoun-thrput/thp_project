package tech3g.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.ErrorInfoVO;
import tech3g.common.exceptions.SystemException;

/**
 * <pre>
 * ExceptionUtilクラス。
 * 例外に関するUtilクラス。
 * </pre>
 *
 */
public class ExceptionUtil {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
	//--------------------------------------------------- コンストラクタ
    /**
	 * コンストラクタ<br/>
	 * このオブジェクトはインスタンス化する必要がない。
	 */
	private ExceptionUtil()	{
	}
    // -------------------------------------------------- SetGet Methods
    //--------------------------------------------------- インスタンスメソッド

	/**
	 * 例外からメッセージ、StackTrace、発生時間などで<br/>
	 * ErrorInfoVOインスタンスを作成し返却する。<br/>
	 * @param e 例外
	 * @return ErrorInfoVO
	 */
	public static ErrorInfoVO getErrorInfoVO (String uri, Throwable e)  {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		if (e != null) {
    		try {
    			e.printStackTrace(pw);

    //			Exception ex = e;
    //			if (ex.getCause() != null) {
    //				pw.write("Cause--------------------------------------------------------------------------\n");
    //				ex.getCause().printStackTrace(pw);
    //			}

    		} finally {
    			try {
    				sw.close();
    				pw.close();
    			} catch (IOException e1) {
    				throw new SystemException(e1.getMessage());
    			}
    		}
		}
		return new ErrorInfoVO(e.getMessage(), DateUtil.getCurrentTimeStr2(), sw.toString(), uri);
	}


	/**
	 * 例外からメッセージ、StackTrace、発生時間などで<br/>
	 * ErrorInfoVOインスタンスを作成し返却する。<br/>
	 * @param e 例外
	 * @param msg メッセージ
	 * @return ErrorInfoVO
	 */
	public static ErrorInfoVO getErrorInfoVO (String uri, Throwable e, String msg)  {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		if (e != null) {
    		try {
    			e.printStackTrace(pw);

    //			Exception ex = e;
    //			if (ex.getCause() != null) {
    //				pw.write("Cause--------------------------------------------------------------------------\n");
    //				ex.getCause().printStackTrace(pw);
    //			}

    		} finally {
    			try {
    				sw.close();
    				pw.close();
    			} catch (IOException e1) {
    				throw new SystemException(e1.getMessage());
    			}
    		}
		}
		return new ErrorInfoVO(msg, DateUtil.getCurrentTimeStr2(), sw.toString(), uri);
	}


	/**
	 * Exceptionのメッセージなどを取り出し、Requestに格納する。<br/>
	 * @param request HttpServletRequest
	 * @param se 例外
	 * @throws Throwable 例外
	 */
	public static void processSysException(HttpServletRequest request, Exception se) throws Exception {
		request.setAttribute(CommonConsts.KEY_SYS_EXC, getErrorInfoVO(request.getRequestURI(), se));
		throw se;
	}


}
