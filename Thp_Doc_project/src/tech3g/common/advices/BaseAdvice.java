package tech3g.common.advices;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;

import anyframe.core.properties.IPropertiesService;

/**
 * <pre>
 * BaseAdviceクラス。
 * </pre>

 */
public class BaseAdvice {


    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
	/** log */
	protected Log log = LogFactory.getLog(this.getClass());

	/** loggerName */
	protected String loggerName = this.getClass().getName();

	/** messageSource */
	protected MessageSource messageSource;

	/** propertiesService */
	protected IPropertiesService propertiesService;

    //--------------------------------------------------- コンストラクタ

	/**
	 * <br/>
	 */
	public BaseAdvice() {
		super();
	}

	/**
	 * logを取得する。<br/>
	 * @return log
	 */
	public Log getLog() {
		return log;
	}

	/**
	 * logを設定する。<br/>
	 * @param log log
	 */
	public void setLog(Log log) {
		this.log = log;
	}

	/**
	 * loggerNameを取得する。<br/>
	 * @return loggerName
	 */
	public String getLoggerName() {
		return loggerName;
	}

	/**
	 * loggerNameを設定する。<br/>
	 * @param loggerName loggerName
	 */
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
		this.log = LogFactory.getLog(loggerName);
	}
	/**
	 * messageSourceを取得する。<br/>
	 * @return messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * messageSourceを設定する。<br/>
	 * @param messageSource messageSource
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * propertiesServiceを取得する。<br/>
	 * @return propertiesService
	 */
	public IPropertiesService getPropertiesService() {
		return propertiesService;
	}

	/**
	 * propertiesServiceを設定する。<br/>
	 * @param propertiesService propertiesService
	 */
	public void setPropertiesService(IPropertiesService propertiesService) {
		this.propertiesService = propertiesService;
	}

    // -------------------------------------------------- SetGet Methods

    //--------------------------------------------------- インスタンスメソッド
}
