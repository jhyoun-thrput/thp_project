package tech3g.common.biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

import anyframe.core.cache.ICacheService;
import anyframe.core.idgen.IIdGenerationService;
import anyframe.core.properties.IPropertiesService;

/**
 * <pre>
 * BaseServiceImplクラス。
 * このフレームワークを利用するすべてのServiceが継承する親クラス。
 * Serviceの共通機能を実装する。
 * ※SpringのApplicationContextAwareを実装する。
 * </pre>
 */
public class BaseService implements ApplicationContextAware {

    //---------------------------------------------------定数
    //---------------------------------------------------インスタンス変数

	/** メッセージソース */
	protected MessageSource messageSource;

	/** log */
	protected Log log = LogFactory.getLog(this.getClass());

	/** loggerName */
	protected String loggerName = this.getClass().getName();

	/** プロパティを管理するためのサービス */
	protected IPropertiesService propertiesService;

	/** 唯一のIDを生成するためのサービス */
	protected IIdGenerationService idGenerationService;

	/** キャッシュ機能を利用するためのサービス */
	protected ICacheService cacheService;

	/** ApplicationContext */
	protected ApplicationContext ctx;

    // ----------------------------------------------------- コンストラクタ
	/**
	 * コンストラクタ<br/>
	 */
	public BaseService() {
		super();
	}

    // --------------------------------------------------- SetGet Methods
	/**
	 * loggerNameを取得する。<br/>
	 * @return loggerName
	 */
	public String getLoggerName() {
		return loggerName;
	}

	/**
	 * loggerNamewを設定する。<br/>
	 * @param loggerName
	 */
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
		this.log = LogFactory.getLog(loggerName);
	}

	/**
	 * メッセージソースを取得する。<br/>
	 * @return メッセージソース
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * メッセージソースを設定する。<br/>
	 * @param messageSource メッセージソース
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * 	Propertiesサービスを取得する。<br/>
	 * @return
	 */
	public IPropertiesService getPropertiesService() {
		return propertiesService;
	}

	/**
	 * Propertiesサービスを設定する。<br/>
	 * @param propertiesService
	 */
	public void setPropertiesService(IPropertiesService propertiesService) {
		this.propertiesService = propertiesService;
	}

	/**
	 * IIdGenerationServiceを取得する。<br/>
	 * @return
	 */
	public IIdGenerationService getIdGenerationService() {
		return idGenerationService;
	}

	/**
	 * IIdGenerationServiceを設定する。<br/>
	 * @param sequenceIdGenerationService
	 */
	public void setIdGenerationService(
			IIdGenerationService sequenceIdGenerationService) {
		this.idGenerationService = sequenceIdGenerationService;
	}

	/**
	 * cacheServiceを取得する。<br/>
	 * @return cacheService
	 */
	public ICacheService getCacheService() {
		return cacheService;
	}

	/**
	 * cacheServiceを設定する。<br/>
	 * @param cacheService the cacheService to set
	 */
	public void setCacheService(ICacheService cacheService) {
		this.cacheService = cacheService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;

	}

    //---------------------------------------------------インスタンスメソッド
	/**
	 * Beanを取得します。<br/>
	 * @param beanID BeanのID
	 * @return Bean
	 */
	protected Object getBean(String beanID) {
		return ctx.getBean(beanID);
	}
}
