package tech3g.common.web.listener;

import java.io.NotSerializableException;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import tech3g.common.web.session.SessionInfoDTO;
import tech3g.common.web.session.SessionInfoThreadManager;

import com.mchange.v2.ser.SerializableUtils;

/**
 * <pre>
 * SessionListenerクラス。
 * </pre>
 */
public class SessionListener extends BaseListener implements HttpSessionListener,
		HttpSessionAttributeListener, HttpSessionActivationListener {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeAdded(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeAdded(HttpSessionBindingEvent event) {
		StringBuilder stringBuilder = new StringBuilder();
		if (log.isDebugEnabled()) {
		// セッションにObjectをSetした時のログ出力
			stringBuilder.append("session attribute added -> ");
			stringBuilder.append("thread: ").append(Thread.currentThread());
			stringBuilder.append(", ");
			stringBuilder.append("instance: ").append(this.hashCode());
			stringBuilder.append(", ");
			stringBuilder.append("name: ");
			stringBuilder.append(event.getName());
			stringBuilder.append(", ");
			stringBuilder.append("session id: ");
			stringBuilder.append(event.getSession().getId());
			stringBuilder.append(", ");
			stringBuilder.append("soruce: ");
			stringBuilder.append(event.getSource());
			log.debug(stringBuilder.toString());

			logSessionObjSize(event.getValue());
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeRemoved(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeRemoved(HttpSessionBindingEvent event) {
		StringBuilder stringBuilder = new StringBuilder();
		if (log.isDebugEnabled()) {
			stringBuilder.append("session attribute removed -> ");
			stringBuilder.append("thread: ").append(Thread.currentThread());
			stringBuilder.append(", ");
			stringBuilder.append("instance: ").append(this.hashCode());
			stringBuilder.append(", ");
			stringBuilder.append("name: ");
			stringBuilder.append(event.getName());
			stringBuilder.append(", ");
			stringBuilder.append("session id: ");
			stringBuilder.append(event.getSession().getId());
			stringBuilder.append(", ");
			stringBuilder.append("soruce: ");
			stringBuilder.append(event.getSource());
			log.debug(stringBuilder.toString());
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeReplaced(javax.servlet.http.HttpSessionBindingEvent)
	 */
	public void attributeReplaced(HttpSessionBindingEvent event) {
		StringBuilder stringBuilder = new StringBuilder();
		if (log.isDebugEnabled()) {
			stringBuilder.append("session attribute replaced -> ");
			stringBuilder.append("name: ");
			stringBuilder.append(event.getName());
			stringBuilder.append(", ");
			stringBuilder.append("session id: ");
			stringBuilder.append(event.getSession().getId());
			stringBuilder.append(", ");
			stringBuilder.append("soruce: ");
			stringBuilder.append(event.getSource());
			log.debug(stringBuilder.toString());

			logSessionObjSize(event.getValue());
		}

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		StringBuilder stringBuilder = new StringBuilder();
		if (log.isDebugEnabled()) {
			stringBuilder.append("session created -> ");
			stringBuilder.append("session id: ");
			stringBuilder.append(event.getSession().getId());
			stringBuilder.append(", ");
			stringBuilder.append("soruce: ");
			stringBuilder.append(event.getSource());
			log.debug(stringBuilder.toString());
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		StringBuilder stringBuilder = new StringBuilder();
		if (log.isDebugEnabled()) {
			stringBuilder.append("session destroyed -> ");
			stringBuilder.append("session id: ");
			stringBuilder.append(event.getSession().getId());
			stringBuilder.append(", ");
			stringBuilder.append("soruce: ");
			stringBuilder.append(event.getSource());
			log.debug(stringBuilder.toString());
		}

		try {

			SessionInfoDTO sessionInfo = SessionInfoThreadManager.getSessionInfo();
			sessionInfo.setUserId(null);
			sessionInfo.setIpAddr(null);

			log.info("## [SessionListener] Thread ID : " + Thread.currentThread() + ", Session Info : " + sessionInfo
					+ ", User ID : " + sessionInfo.getUserId() + ", IP Address : " + sessionInfo.getIpAddr());

		} catch (Exception e) {
			log.error("sessionDestroyed中エラー",e);
//			MessageDTO messageDTO = MessageUtil.exceptionToMessageDTO(e);
//			if (log.isErrorEnabled()) {
//				log.error(messageDTO.getUserMessage(), e);
//			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionActivationListener#sessionDidActivate(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDidActivate(HttpSessionEvent event) {
		StringBuilder stringBuilder = new StringBuilder();
		if (log.isDebugEnabled()) {

			stringBuilder.append("session activated -> ");
			stringBuilder.append("session id: ");
			stringBuilder.append(event.getSession().getId());
			stringBuilder.append(", ");
			stringBuilder.append("soruce: ");
			stringBuilder.append(event.getSource());
			log.debug(stringBuilder.toString());
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionActivationListener#sessionWillPassivate(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionWillPassivate(HttpSessionEvent event) {
		StringBuilder stringBuilder = new StringBuilder();
		if (log.isDebugEnabled()) {
			stringBuilder.append("session passivated -> ");
			stringBuilder.append("session id: ");
			stringBuilder.append(event.getSession().getId());
			stringBuilder.append(", ");
			stringBuilder.append("soruce: ");
			stringBuilder.append(event.getSource());
			log.debug(stringBuilder.toString());
		}
	}

	/**
	 *
	 * @param obj
	 */
	private void logSessionObjSize(Object obj) {
		try {
			if (log.isDebugEnabled()) {
				byte[] aaa = SerializableUtils.toByteArray(obj);
				int size = aaa.length;
				if (aaa != null) {
					log.debug("------ セッションに格納するObjectのサイズ: [" + size + "] Byte");
				}

				if (size > 500000) {
					log.error("\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"
					        + "------------------------------------------------------------------\n"
					        + "あなたは500KByte以上のデータ("+ (size / 1000) +"Kbyte)をSessionに保持しようとしました。\n"
					        + "------------------------------------------------------------------\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n"
					        + "★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★\n");
				}

			}
		} catch (NotSerializableException e1) {
			log.warn("* Not Serializable Object :" + obj);
		}
	}
}
