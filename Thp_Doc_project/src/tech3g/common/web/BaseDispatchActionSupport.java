package tech3g.common.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.struts.DispatchActionSupport;

import anyframe.core.properties.IPropertiesService;

/**
 * <pre>
 * BaseDispatchActionSupportクラス。
 * </pre>
 */
public class BaseDispatchActionSupport extends DispatchActionSupport {

	/** log */
	protected Log log = LogFactory.getLog(this.getClass());

	/** propertiesService */
	protected IPropertiesService propertiesService;

	/** messageSource */
	protected MessageSource messageSource;

	/** loggerName */
	protected String loggerName = this.getClass().getName();

	/** webApplicationContext */
	protected WebApplicationContext webApplicationContext = getWebApplicationContext();

	/**
	 * PropertiesServiceの取得<br/>
	 * @return PropertiesService
	 */
	public IPropertiesService getPropertiesService() {
		return propertiesService;
	}

	/**
	 * PropertiesServiceを設定<br/>
	 * @param propertiesService
	 */
	public void setPropertiesService(IPropertiesService propertiesService) {
		this.propertiesService = propertiesService;
	}

	/**
	 * MessageSourceの取得<br/>
	 * @return
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * MessageSourceを設定<br/>
	 * @param messageSource
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * loggerNameの取得<br/>
	 * @return
	 */
	public String getLoggerName() {
		return loggerName;
	}

	/**
	 * loggerNameを設定<br/>
	 * @version 修正履歴
	 * @param loggerName
	 */
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
		this.log = LogFactory.getLog(loggerName);
	}
}
