package tech3g.common.advices;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import tech3g.common.dao.COMNAuditDao;
import tech3g.common.service.logic.CommonLogic;
import tech3g.common.util.StrUtil;
import tech3g.common.web.session.SessionInfoDTO;
import tech3g.common.web.session.SessionInfoThreadManager;
import anyframe.core.query.IQueryService;
import anyframe.core.query.QueryServiceException;

/**
 * <pre>
 * ServiceAuditAdviceクラス。
 * システム監視を行うAdvice
 * DBアクセス（select）が行われたときにだれがどういう情報を検索したかログで残す。
 * </pre>
 */
public class ServiceAuditAdvice extends BaseAdvice implements ApplicationContextAware{

	private static String QUERYSERVICE_FIND = "find";
	private static String QUERYSERVICE_FIND_BYSQL = "findBySQL";

	ApplicationContext ctx;

	/**  システム監視情報Dao */
	private COMNAuditDao comnAuditDao;


	/**
	 * comnAuditDaoを設定する。<br/>
	 * @param comnAuditDao comnAuditDao
	 */
	public void setComnAuditDao(COMNAuditDao comnAuditDao) {
		this.comnAuditDao = comnAuditDao;
	}

	/**
	 * <br/>
	 */
	public ServiceAuditAdvice() {
		super();
	}

	/**
	 * APPであるか確認する。<br/>
	 * @param clsName
	 * @return
	 */
	private boolean chkAuditExceptCase(String clsName) {

		String[] chkList = { "liveany.fk" };
		boolean result = false;

		for (int i = 0; i < chkList.length; i++) {
			if (clsName!= null && clsName.startsWith(chkList[i])) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * QueryServiceによってDBアクセスが行われたらDBに記録する。<br/>
	 * @version 修正履歴
	 * @param joinPoint joinPoint
	 */
	public void auditQuery(JoinPoint joinPoint) {

		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();

		SessionInfoDTO sessionInfo = SessionInfoThreadManager.getSessionInfo();
		String userId = sessionInfo.getUserId();
		String ipAddr = sessionInfo.getIpAddr();
		String serviceClassName = sessionInfo.getClassName();
		String serviceMethodName = sessionInfo.getMethodName();

		if (!chkAuditExceptCase(serviceClassName)) {
			return;
		}

		if (StrUtil.isEmpty(userId)) {
			userId = "unknown";
		}

		String queryId = "";
		String queryStr = "";
		StringBuffer paramStr = new StringBuffer();

		if (methodName.startsWith(QUERYSERVICE_FIND_BYSQL)) {
		// findBySQL
			queryStr = (String) args[0];
			Object[] param = (Object[]) args[2];
			for (int i = 0; i < param.length; i++) {
				paramStr.append(param[i].toString());
				paramStr.append((i < param.length - 1 ? "|" : ""));
			}
		} else if (methodName.equals(QUERYSERVICE_FIND)) {
		// find
			queryId = (String) args[0];
			try {
				IQueryService queryService = (IQueryService) joinPoint.getTarget();
				queryStr = queryService.getStatement(queryId);
			} catch (QueryServiceException e) {
				queryStr = "<unknown-query>";
			}
			Object[] param = (Object[]) args[1];

			Object[] tmp = null;
			for (int i = 0; i < param.length; i++) {

				if (param[i] instanceof Object[]) {
					tmp = (Object[]) param[i];
					if (tmp != null) {
						paramStr.append(tmp[0].toString());
						paramStr.append("=");
						paramStr.append(tmp[1]);
						paramStr.append("|");
					}
				} else {
					if (param[i] != null) {
						paramStr.append(param[i].toString());
						paramStr.append((i < param.length - 1 ? "|" : ""));
					}
				}

			}


		}

		try {

			if (!StrUtil.isEmpty(userId)) {
				// システム監視情報を登録する。
				CommonLogic.createAuditInf(comnAuditDao, userId, serviceClassName,
						serviceMethodName, methodName, queryId,
						paramStr.substring(0, Math.min(1000, paramStr.length())), ipAddr);
			}

		} catch (Exception e) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(StrUtil.LINE_SEP);
			stringBuilder.append("Cannot create service audit log...");
			stringBuilder.append(StrUtil.LINE_SEP);
			stringBuilder.append("	user id : " + userId);
			stringBuilder.append(StrUtil.LINE_SEP);
			stringBuilder.append("	ip address : " + ipAddr);
			stringBuilder.append(StrUtil.LINE_SEP);
			stringBuilder.append("	service class : " + serviceClassName);
			stringBuilder.append(StrUtil.LINE_SEP);
			stringBuilder.append("	service method : " + serviceMethodName);
			stringBuilder.append(StrUtil.LINE_SEP);
			stringBuilder.append("	query method : " + methodName);
			stringBuilder.append(StrUtil.LINE_SEP);
			stringBuilder.append("	query id : ").append(queryId);
			stringBuilder.append(StrUtil.LINE_SEP);
			stringBuilder.append("	query string : ").append(queryStr);
			stringBuilder.append(StrUtil.LINE_SEP);
			stringBuilder.append("	query param : ").append(paramStr);
			log.error(stringBuilder.toString(), e);
		}
	}

	/**
	 * サービスImplが呼ばれたらClass名とメソッド名を保持する。<br/>
	 * @param joinPoint joinPoint
	 */
	public void auditService(JoinPoint joinPoint) {
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();

		SessionInfoDTO sessionInfo = SessionInfoThreadManager.getSessionInfo();
		sessionInfo.setClassName(className);
		sessionInfo.setMethodName(methodName);
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}
}
