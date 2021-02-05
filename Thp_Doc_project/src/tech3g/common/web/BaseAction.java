package tech3g.common.web;

import java.io.NotSerializableException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.springframework.context.MessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.struts.DispatchActionSupport;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.AccessException;
import tech3g.common.exceptions.ApplicationException;
import tech3g.common.exceptions.MsgVO;
import tech3g.common.exceptions.SystemException;
import tech3g.common.util.BeanUtil;
import tech3g.common.util.ExceptionUtil;
import tech3g.common.util.MessageUtil;
import tech3g.common.util.StrUtil;
import tech3g.common.web.session.UserSession;
import tech3g.common.web.tag.LiveanyTokenTag;
import anyframe.core.properties.IPropertiesService;

import com.mchange.v2.ser.SerializableUtils;

/**
 * <pre>
 * BaseActionクラス。
 * オンライン画面が基本的に継承するActionクラス。
 * </pre>
 */
public class BaseAction extends DispatchActionSupport {

	/** 画面遷移時、遷移先にパラメータを渡すために使用するMapのSessionキー */
	protected static final String KEY_FW_TRAN_PARAM_MAP = "fw_tran_param_map";

	/** log */
	protected Log log = LogFactory.getLog(this.getClass());

	/** propertiesService */
	protected IPropertiesService propertiesService;// = (IPropertiesService) getService("propertiesService");

	/** messageSource */
	protected MessageSource messageSource;// = (MessageSource) getMessageSourceAccessor();// = (MessageSource) getService("messageSource");

	/** loggerName */
	protected String loggerName = this.getClass().getName();

	/** webApplicationContext */
	protected WebApplicationContext webApplicationContext = getWebApplicationContext();

    /** 呼出するActionの引数タイプ */
    protected static Class[] types = {
    		HttpServletRequest.class, HttpServletResponse.class, CommonParams.class, Map.class};

	/**
	 * PropertiesServiceを取得する。<br/>
	 *
	 * @return
	 */
	public IPropertiesService getPropertiesService() {
		return propertiesService;
	}

	/**
	 * PropertiesServiceをセットする。<br/>
	 * @param propertiesService
	 */
	public void setPropertiesService(IPropertiesService propertiesService) {
		this.propertiesService = propertiesService;
	}

	/**
	 * Springから提供するメッセージサービスを取得する。<br/>
	 * @return
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * Springから提供するメッセージサービスを設定する。<br/>
	 * @param messageSource
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * Loggerの名前を取得する。<br/>
	 * @return loggerName
	 */
	public String getLoggerName() {
		return loggerName;
	}

	/**
	 * Loggerの名前を設定する。<br/>
	 * @param loggerName
	 */
	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
		this.log = LogFactory.getLog(loggerName);
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.actions.DispatchAction#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (isCancelled(request)) {
			ActionForward af = cancelled(mapping, form, request, response);
			if (af != null) {
				return af;
			}
		}

		String name = ((ServiceActionMapping) mapping).getMethod();

		// Prevent recursive calls
		if ("execute".equals(name) || "perform".equals(name)) {
			String message = messages.getMessage("dispatch.recursive", mapping.getPath());
			log.error(message);
			throw new ServletException(message);
		}

		// Invoke the named method, and return the result
		return dispatchMethod(mapping, form, request, response, name);
	}



	/* (non-Javadoc)
	 * @see org.apache.struts.actions.DispatchAction#dispatchMethod(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
	@Override
	protected ActionForward dispatchMethod(ActionMapping mapping,
        ActionForm form, HttpServletRequest request,
        HttpServletResponse response, String name)
        throws Exception {

    	// ---------------- Serviceの取得


        // ---------------- メソッドの取得
        Method method = null;
        try {
            method = getMethod(name);
        } catch (NoSuchMethodException e) {
			log.error("* NoSuchMethodExceptionが発生しました。", e);
			request.setAttribute(CommonConsts.KEY_SYS_EXC,
					ExceptionUtil.getErrorInfoVO(request.getRequestURI(), e,
							StrUtil.concat("Actionのメソッド定義が正しくありません。指定したメソッド「",
													name, "()」はありません。Struts-Configを確認してください。")));
			throw new SystemException(e);
        }

        ActionForward forward = null;

        try {


        	if (((ServiceActionMapping) mapping).getTokenCheck()) {
	        	// ２度押しチェック
				if (!LiveanyTokenTag.chkToken(request, mapping.getPath())) {
					log.info("２度押しチェックエラー");
					// エラーページへ遷移
					return (mapping.findForward("tokenErr"));
				}
        	}

        	// -------------- ユーザ情報を取得
        	Map map =  getSesnUserInfoMap(request);
        	String userID = (String) map.get(CommonConsts.USER_INF_USER_ID);
        	String user_ip = (String) map.get(CommonConsts.USER_INF_HNDG_IP);

        	// -------------- 入力値を取得
            CommonParams params = new CommonParams(request, form);


            // -------------- Actionのメソッドの起動
            Object[] args = {request, response, params, map};
    		long before = new Date().getTime();

    		String className = this.getClass().getSimpleName();

            log.info("*** Method: " + className + "." + name + "() Start *** "
            				+ "user_id: [" + userID + "] user_ip: [" + user_ip + "]");

            if (log.isDebugEnabled()) {
            	log.debug("==> パラメータ:" + params.toString());
            }
            String fowordStr = (String) method.invoke(this, args);
            log.info("*** Method: " + className +  "."+ name + "() End   *** "
            				+ "user_id: [" + userID + "]"
            				+ "-{executed in " + String.valueOf(((new Date().getTime())-before)) + "msec}");

            //clearTransParam(request);

            if (fowordStr == null) {
            	return null;
            }


            forward = mapping.findForward(fowordStr);

		} catch (Exception e) {

			// --------- invoke処理中発生した例外の場合はその例外を取得
			Exception ex = null;
			if (e instanceof InvocationTargetException) {
				InvocationTargetException ite = (InvocationTargetException) e;

				if (ite.getTargetException() instanceof Error) {
					Error er = (Error) ite.getTargetException();
					log.error("* SystemExceptionが発生しました。", er);
					throw new SystemException(er);
				} else {
					ex = (Exception) ite.getTargetException();
				}

			} else {
				ex = e;
			}

			// --------- 例外処理を行う。
			if (ex instanceof ApplicationException) {
			// 業務的な例外

				List errorCodeList = ((ApplicationException)ex).getErrorCodeList();	// エラーメッセージリスト
				if (errorCodeList == null || errorCodeList.size() == 0) {
					log.warn("★★★ ApplicationException(ビジネス例外)が発生しましたが、エラーメッセージがセットされていません。", ex);
				}

				setMsg(request, errorCodeList);

				if (log.isDebugEnabled()) {
					log.debug("* ApplicationException(ビジネス例外)", ex);
				}

				log.info(StrUtil.concat("* ApplicationException", ex.getStackTrace()[0].toString()));


                ActionForward af = mapping.findForward(CommonConsts.FW_FAILURE);
                if (af == null) {
                // --- Struts-ConfigのForward先にfailureを定義していない場合。
					SystemException se = new SystemException(
							"ApplicationExceptionが発生しましたが、Struts-ConfigにfailureのForward先が定義されていません。" +
							"\n<forward name=\"failure\" path=\"[遷移先]\"/>");
					ExceptionUtil.processSysException(request, se);
				}
                return af;
			} else if (ex instanceof SystemException) {
			// システム例外
				log.error("* SystemExceptionが発生しました。", ex);
				request.setAttribute(CommonConsts.KEY_SYS_EXC,
										ExceptionUtil.getErrorInfoVO(request.getRequestURI(), ex));
				throw ex;

			} else if (ex instanceof AccessException) {
			// 不正アクセス、またはSessionTimeoutの場合
				log.error("* AccessExceptionが発生しました。");
				return mapping.findForward(CommonConsts.FW_AUTHERR);
			} else {
			// その他エラー
				log.error("* Exceptionが発生しました。", ex);
				request.setAttribute(CommonConsts.KEY_SYS_EXC,
										ExceptionUtil.getErrorInfoVO(request.getRequestURI(), ex));
				throw new SystemException(ex);
			}
		}
        return forward;
    }

    /* (non-Javadoc)
     * @see org.apache.struts.actions.DispatchAction#getMethod(java.lang.String)
     */
	protected Method getMethod(String name) throws NoSuchMethodException {
    	// メソッドを取得
		synchronized (methods) {
			Method method = (Method) methods.get(name);

			if (method == null) {
				method = clazz.getMethod(name, types);
				methods.put(name, method);
			}
			return (method);
		}
	}

	/**
	 * ログインユーザー情報の取得<br/>
	 * セッションのユーザー情報から値を取得する。
	 *
	 * @param request HTTPリクエスト
	 * @return 取得Object
	 * @throws AccessException 不正なアクセスや、セッションタイプアウトの時発生する例外
	 */
    protected static Map getSesnUserInfoMap(HttpServletRequest request) throws AccessException {
		HttpSession session = request.getSession();
		Object usermap =  session.getAttribute("USERINFO");

		if (usermap == null) {
			throw new AccessException("セッションタイムアウトされました。又は、ログインしていません。ログインし直して下さい。");
		}
		return (Map)usermap;
	}

    public static String getUserAdmin(HttpServletRequest request) throws AccessException {

    	String adminFlg = "0";

    	Map userMap = getSesnUserInfoMap(request);
    	if (!userMap.isEmpty()) {
    		adminFlg = String.valueOf(userMap.get("user_admin"));
    	}
    	return adminFlg;
    }

    /**
     * メッセージの格納<br/>
	 * メッセージをRequestに格納する。 <br/>
     * @param request HttpRequest
     * @param errorList エラーメッセージが格納されているリスト
     */
    protected final void setMsg(HttpServletRequest request, List errorList) {
		ActionErrors msgs = new ActionErrors();

		Iterator itr = errorList.iterator();
		while (itr.hasNext()) {
			MsgVO errCode = (MsgVO) itr.next();
			log.info(StrUtil.concat("MessageKey = [", errCode.getCode(), "] Arguments = [",
					errCode.getArguments() != null ? toStringArry(errCode.getArguments()) : "", "]"));
			msgs.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(errCode.getCode(), errCode.getArguments()));
		}
		if (!msgs.isEmpty()) {
			saveErrors(request, msgs);
		}
	}

	/**
	 * 入力値をViewBeanにマージ<br/>
	 * 指定したパラメータを指定したViewBeanにマージする。 <br/>
	 * @param params 共通パラメータ
	 * @param viewBean ViewBean
	 * @throws SystemException populateが失敗した時に発生します。
	 */
	protected final void mergeInput2ViewBean(CommonParams params , ViewBean viewBean)
			throws SystemException {

		if (null == viewBean) {
			throw new SystemException("ViewBean is NULL");
		}

		BeanUtil.populate(viewBean, params.getParamMap());
		customMergeInput2ViewBean(params , viewBean);

	}

	/**
	 * mergeInput2ViewBean()でマージできない項目をマージする。<br/>
	 * <b>※mergeInput2ViewBeanでマージできない場合、<br/>
	 * 通常のActionクラスからOverrideし実現する。</b><br/>
	 * @param params 共通パラメータ
	 * @param viewBean ViewBean
	 */
	protected void customMergeInput2ViewBean(CommonParams params, ViewBean viewBean) {
		// Overrideしてください。
	}

	/**
	 * SpringのBeanを取得<br/>
	 * Bean IDを受け取りServiceを取得する。<br/>
	 * WebApplicationContextからBeanをLookUPする。<br/>
	 * @param beanID ビーンＩＤ
	 * @return サービス
	 */
	protected Object getBean(String beanID) {
		return getWebApplicationContext().getBean(beanID);
	}


	/**
	 * 確認メッセージを格納<br/>
	 * 登録、更新、削除など、処理の確認メッセージを表示した場合設定する。<br/>
	 * メッセージキーのみ設定する場合使用<br/>
	 *
	 * @param request HttpSrvletRequest
	 * @param key メッセージキー
	 */
	protected void setInfoMsg(HttpServletRequest request, String key) {
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, MessageUtil.getMessage(key,null));
	}

	/**
	 * 確認メッセージを格納<br/>
	 * 登録、更新、削除など、処理の確認メッセージを表示した場合設定する。<br/>
	 * メッセージに引数が一つの場合使用する。
	 *
	 * @param request HttpSrvletRequest
	 * @param key メッセージキー
	 * @param arg1 メッセージ引数１
	 */
	protected void setInfoMsg(HttpServletRequest request, String key, String arg1) {
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, MessageUtil.getMessage(key,
				new Object[] { arg1 }));
	}

	/**
	 * 確認メッセージを格納。<br/>
	 * 登録、更新、削除など、処理の確認メッセージを表示した場合設定する。<br/>
	 * メッセージに引数が二つの場合使用する。
	 *
	 * @param request HttpSrvletRequest
	 * @param key メッセージキー
	 * @param arg1 メッセージ引数１
	 * @param arg2 メッセージ引数２
	 */
	protected void setInfoMsg(HttpServletRequest request, String key, String arg1, String arg2) {
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, MessageUtil.getMessage(key,
				new Object[] { arg1, arg2 }));
	}

	/**
	 * 確認メッセージを格納<br/>
	 * 登録、更新、削除など、処理の確認メッセージを表示した場合設定する。<br/>
	 * メッセージに引数が三つの場合使用する。
	 *
	 * @param request HttpSrvletRequest
	 * @param key メッセージキー
	 * @param arg1 メッセージ引数１
	 * @param arg2 メッセージ引数２
	 * @param arg3 メッセージ引数３
	 */
	protected void setInfoMsg(HttpServletRequest request, String key, String arg1, String arg2,
			String arg3) {
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, MessageUtil.getMessage(key,
				new Object[] { arg1, arg2, arg3 }));
	}

	/**
	 * 確認メッセージを格納<br/>
	 * 登録、更新、削除など、処理の確認メッセージを表示した場合設定する。<br/>
	 * メッセージに引数が四つの場合使用する。
	 *
	 * @param request HttpSrvletRequest
	 * @param key メッセージキー
	 * @param arg1 メッセージ引数１
	 * @param arg2 メッセージ引数２
	 * @param arg3 メッセージ引数３
	 * @param arg4 メッセージ引数４
	 */
	protected void setInfoMsg(HttpServletRequest request, String key, String arg1, String arg2,
			String arg3, String arg4) {
		request.setAttribute(CommonConsts.KEY_REQ_CONF_MSG, MessageUtil.getMessage(key,
				new Object[] { arg1, arg2, arg3, arg4 }));
	}

	/**
	 * セッションの取得<br/>
	 * 福祉システム用セッションを取得する。<br/>
	 * @param request HttpServletRequest
	 * @return 福祉システム用セッション
	 */
	protected UserSession getUserSession(HttpServletRequest request) {

		UserSession session = (UserSession) request.getSession().getAttribute(CommonConsts.KEY_FUKUSI_SESSION);
		if (session == null) {
			session = new UserSession();
			setUserSession(request, session);
		}

		return session;
	}

	/**
	 * セッションの格納<br/>
	 * 福祉システム用セッションを格納する。<br/>
	 * @param request HttpServletRequest
	 * @param userSession 福祉システム用セッション
	 */
	protected void setUserSession(HttpServletRequest request, UserSession userSession) {
		request.getSession().setAttribute(CommonConsts.KEY_FUKUSI_SESSION, userSession);
	}

	/**
	 * 共通セッションからObject取得メソッド。<br/>
	 * 共通セッションから指定したキーのObjectを取得する。<br/>
	 * @param request HttpServletRequest
	 * @param key セッションキー
	 * @param type Classタイプ
	 * @return Sessionから取得したObject
	 */
	protected Object getCommonSessionAttr(HttpServletRequest request, String key) {
		return getUserSession(request).getCommon(key);
	}

	/**
	 * 共通セッションからObject取得メソッド。<br/>
	 * 共通セッションから指定したキーのObjectを取得する。<br/>
	 * もし、指定したキーのオブジェクトが存在しなければ（nullであれば）
	 * 指定したタイプのインスタンスを作成し返却する。
	 * @param request HttpServletRequest
	 * @param key セッションキー
	 * @param type Classタイプ
	 * @return Sessionから取得したObjectまたは、typeのクラスタイプインスタンス
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected Object getCommonSessionAttr(HttpServletRequest request, String key, Class type)
			throws InstantiationException, IllegalAccessException {
		Object obj = getUserSession(request).getCommon(key);

		if (obj != null) {
			return obj;
		} else {
			return type.newInstance();
		}
	}

	/**
	 * 共通セッション格納メソッド。<br/>
	 * 共通セッションに指定されたObjectを格納する。<br/>
	 * @param request HttpServletRequest
	 * @param key セッションキー
	 * @param obj 格納対象Object
	 */
	protected void setCommonSessionAttr(HttpServletRequest request, String key, Object obj) {
		getUserSession(request).putCommon(key, obj);
		logSessionObjSize(key, obj);
	}

	/**
	 * 共通セッション削除メソッド。<br/>
	 * 共通セッションに指定されたObjectを削除する。<br/>
	 * @param request HttpServletRequest
	 * @param key セッションキー
	 */
	protected void removeCommonSessionAttr(HttpServletRequest request, String key) {
		getUserSession(request).removeCommon(key);
	}

	/**
	 * セッションからObject取得メソッド。<br/>
	 * セッションから指定したキーのObjectを取得する。<br/>
	 * @param request HttpServletRequest
	 * @param key セッションキー
	 * @param type Classタイプ
	 * @return Sessionから取得したObject
	 */
	public Object getSessionAttr(HttpServletRequest request, String key) {
		return getUserSession(request).getLocal(key);
	}

	/**
	 * セッションからObject取得メソッド。<br/>
	 * セッションから指定したキーのObjectを取得する。<br/>
	 * もし、指定したキーのオブジェクトが存在しなければ（nullであれば）
	 * 指定したタイプのインスタンスを作成し返却する。
	 * @param request HttpServletRequest
	 * @param key セッションキー
	 * @param type Classタイプ
	 * @return Sessionから取得したObjectまたは、typeのクラスタイプインスタンス
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Object getSessionAttr(HttpServletRequest request, String key, Class type)
			throws InstantiationException, IllegalAccessException {
		Object obj = getUserSession(request).getLocal(key);

		if (obj != null) {
			return obj;
		} else {
			return type.newInstance();
		}
	}

	/**
	 * セッション格納メソッド。<br/>
	 * セッションに指定されたObjectを格納する。<br/>
	 * @param request HttpServletRequest
	 * @param key セッションキー
	 * @param obj 格納対象Object
	 */
	public void setSessionAttr(HttpServletRequest request, String key, Object obj) {
		getUserSession(request).putLocal(key, obj);
		logSessionObjSize(key, obj);
	}

	/**
	 * セッション削除メソッド。<br/>
	 * セッションに指定されたObjectを削除する。<br/>
	 * @param request HttpServletRequest
	 * @param key セッションキー
	 */
	public void removeSessionAttr(HttpServletRequest request, String key) {
		getUserSession(request).removeLocal(key);
	}

	/**
	 * 画面間パラメータを格納する。<br/>
	 * @param request HttpServletRequest
	 * @param key パラメータキー
	 * @param value 値
	 */
	protected void giveParam(HttpServletRequest request, String key, String value) {

		Map paramMap = (Map) getSessionAttr(request, KEY_FW_TRAN_PARAM_MAP);

		if (paramMap == null) {
			paramMap =  new HashMap();
			setSessionAttr(request, KEY_FW_TRAN_PARAM_MAP, paramMap);
		}

		paramMap.put(key, value);
	}

	/**
	 * 画面間パラメータを取得する。<br/>
	 * @param request HttpServletRequest
	 * @param key パラメータのキー
	 * @return 取得する文字列
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected String takeParam(HttpServletRequest request, String key) {

		Map tranParam = (Map) getSessionAttr(request, KEY_FW_TRAN_PARAM_MAP);

		if (tranParam == null) {
			return null;
		} else {
			String res = (String) tranParam.get(key);
			tranParam.remove(key);
			return res;
		}
	}

	/**
	 * 画面間パラメータをクリアする。<br/>
	 * @param request HttpServletRequest
	 */
	private void clearTransParam (HttpServletRequest request) {

		Map tranParam = (Map) getSessionAttr(request, KEY_FW_TRAN_PARAM_MAP);

		if (tranParam != null) {
			tranParam.clear();
		}
	}

	/**
	 * 画面遷移前に自画面のIDをスタックに格納する。<br/>
	 * @param request HttpServletRequest
	 * @throws Exception
	 */
	protected void pushPage(HttpServletRequest request) throws Exception {

		Stack page = (Stack) getSessionAttr(request, CommonConsts.KEY_PAGE_STACK);
		if (page == null) {
			page = new Stack();
			setSessionAttr(request, CommonConsts.KEY_PAGE_STACK, page);
		}

		page.push(getPageID());
	}

	/**
	 * 遷移前の画面IDをスタックより取得する。<br/>
	 * @param request HttpServletRequest
	 * @return 画面ID
	 * @throws Exception
	 */
	public String popPage(HttpServletRequest request) throws Exception {
		String id = "";

		Stack page = (Stack) getSessionAttr(request, CommonConsts.KEY_PAGE_STACK);
		if (page == null) {
			page = new Stack();
			setSessionAttr(request, CommonConsts.KEY_PAGE_STACK, page);
		}

		if (!page.empty()) {
			id = (String) page.pop();
		}

		return id;
	}

	/**
	 * スタックから指定IDまでの画面IDを削除する。<br/>
	 * @param request HttpServletRequest
	 * @param id 画面ID
	 * @return
	 * @throws Exception
	 */
	public String popPage(HttpServletRequest request, String id) throws Exception {

		Stack page = (Stack) getSessionAttr(request, CommonConsts.KEY_PAGE_STACK);
		if (page == null) {
			page = new Stack();
			setSessionAttr(request, CommonConsts.KEY_PAGE_STACK, page);
		}

		// 指定ページまでのスタックを削除
		if (!page.empty()) {
			if (page.contains(id)) {
				while (!id.equals((String) page.pop())) {
				}
			}
		}

		return id;
	}

	/**
	 * 最終遷移元の画面ID取得<br/>
	 * @param request HttpServletRequest
	 * @return 最終遷移元の画面ID
	 */
	public String getLastPage(HttpServletRequest request) {

		Stack page = (Stack) getSessionAttr(request, CommonConsts.KEY_PAGE_STACK);
		if (page == null) {
			page = new Stack();
			setSessionAttr(request, CommonConsts.KEY_PAGE_STACK, page);
		}

		// 指定ページまでのスタックを削除
		if (!page.empty()) {
			return (String) page.lastElement();
		} else {
			return null;
		}

	}

	/**
	 * 指定した画面の経由判定<br/>
	 * 指定した画面を経由してきたか判定する。
	 * @param request HttpServletRequest
	 * @param id 画面ID
	 * @return 指定した画面IDに該当する画面を経由した場合trueを返却
	 */
	public boolean containsPrePage(HttpServletRequest request, String id) {

		Stack page = (Stack) getSessionAttr(request, CommonConsts.KEY_PAGE_STACK);
		if (page == null) {
			page = new Stack();
			setSessionAttr(request, CommonConsts.KEY_PAGE_STACK, page);
		}

		// 指定ページまでのスタックを削除
		return page.contains(id);
	}



	/**
	 * 画面IDの取得<br/>
	 * クラス名より画面IDを取得する。<br/>
	 * @return 画面ID
	 */
	public String getPageID() {
		String id = getClass().toString();

		// パッケージ部を除去
		int idx = id.lastIndexOf(".");
		if (idx != -1) {
			id = id.substring(idx + 1);
		}

		// Actionクラスのサフィックスを除去
		idx = id.lastIndexOf("Action");
		if (idx != -1) {
			id = id.substring(0, idx);
		}

		return id;
	}

	/**
	 * ObjectをSerializeしObjectのサイズを測る。<br/>
	 * @param key セッションキー
	 * @param obj サイズを測る対象のOBJECT
	 */
	private void logSessionObjSize(String key, Object obj) {
		try {
			//if (log.isDebugEnabled()) {
				byte[] aaa = SerializableUtils.toByteArray(obj);
				int size = aaa.length;
				if (aaa != null) {
					if (log.isDebugEnabled()) {
						log.debug("------ セッションに格納するObjectのキー: [" + key + "] サイズ: [" + size + "] Byte");
					}
				}

				if (size > 500000) {
					log.error("\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"
					        + "------------------------------------------------------------------\n"
					        + "あなたは500KByte以上のデータ("+ (size / 1000) +"Kbyte)をSessionに保持しようとしました。\n"
					        + "------------------------------------------------------------------\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n");
				}

			//}
		} catch (NotSerializableException e1) {
			log.warn("* Not Serializable Object :" + obj);
		}
	}

    /**
     * Objectの内容を文字列で表して返却する。<br/>
     * @param obj objの配列
     * @return 結果文字列
     */
    private final static String toStringArry(Object[] po){
    	StringBuffer sb = new StringBuffer();
		for(int i = 0; i<  po.length; i++) {
    		sb.append("{");
    		sb.append(po[i]);
    		sb.append("} ");
		}
    	return sb.toString();
    }
}
