package tech3g.common.web.session;


/**
 * <pre>
 * SessionInfoThreadManagerクラス。
 * </pre>
 *
 */
public class SessionInfoThreadManager {
	private static ThreadLocal<SessionInfoHolder> threadLocal = new ThreadLocal<SessionInfoHolder>() {
		@Override
		protected SessionInfoHolder initialValue() {
			return new SessionInfoHolder();
		}
	};

	public static SessionInfoDTO getSessionInfo() {
		return threadLocal.get().getSessionInfo();
	}

	public static void setSessionInfo(SessionInfoDTO sessionInfo) {
		threadLocal.get().setSessionInfo(sessionInfo);
	}

	private static class SessionInfoHolder {
		private SessionInfoDTO sessionInfo = new SessionInfoDTO();

		public SessionInfoDTO getSessionInfo() {
			return sessionInfo;
		}

		public void setSessionInfo(SessionInfoDTO sessionInfo) {
			this.sessionInfo = sessionInfo;
		}
	}
}
