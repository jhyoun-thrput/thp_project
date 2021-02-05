package tech3g.common.web.listener;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tech3g.common.util.StrUtil;

/**
 * <pre>
 * JSTLConstantsLoaderクラス。
 * アプリケーション起動時に、JSTLで使用する定数をServletContextに登録するクラス。
 * </pre>
 *
 */
public class JSTLConstantsLoader  implements ServletContextListener {

	/** log */
	private final Log log = LogFactory.getLog(JSTLConstantsLoader.class);


	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {

		ClassLoader loader = JSTLConstantsLoader.class.getClassLoader();
        ServletContext context = sce.getServletContext();
		String constants = context.getInitParameter("jstl_constants_config");
		StringTokenizer st = new StringTokenizer(constants, ",");

		try {
			if (log.isDebugEnabled()) {
				log.debug("JSTL定数ロード---Start");
			}

			Map compareMap = new HashMap(); // 同一のクラス名宣言のチェックを行うための
			while (st.hasMoreTokens()) {

				String constant = StrUtil.replace(st.nextToken().trim(), "	", "");

				if (StrUtil.isEmpty(constant)) {
					continue;
				}

				if (log.isDebugEnabled()) {
					log.debug("定数クラス:" + constant+":");
				}

				String cName = "";
				if (constant.lastIndexOf('.') > -1) {
					cName = cName.substring(cName.lastIndexOf('.') + 1);
				}

				if (compareMap.containsKey(cName)) {
					throw new IllegalArgumentException("web.xmlのJSTLConstantsに同一のクラス名の登録は出来ません。"
							+ constant);
				}

				Class c = loader.loadClass(constant);

				registJSTLConsts(c, context);
			}
			if (log.isDebugEnabled()) {
				log.debug("JSTL定数ロード---End");
			}
		} catch (ClassNotFoundException e) {
			log.error("web.xmlのJSTLConstantsに指定したクラスが存在しません。", e);
		} catch (IllegalArgumentException e) {
			log.error("web.xmlのJSTLConstantsロード中エラーが発生しました。", e);
		} catch (IllegalAccessException e) {
			log.error("web.xmlのJSTLConstantsロード中エラーが発生しました。", e);
		}
	}

	/**
	 * 指定したクラスのPuplic定数をServletContextに登録する。<br/>
	 *
	 * @param c 定数クラス
	 * @throws IllegalArgumentException 不正なパラメータを指定した場合。
	 * @throws IllegalAccessException 不正な接近の場合。
	 */
	protected void registJSTLConsts(Class c, ServletContext context) throws IllegalArgumentException,
			IllegalAccessException {

		String cName = c.getName();
		if (cName.lastIndexOf('.') > -1) {
			cName = cName.substring(cName.lastIndexOf('.') + 1);
		}

		Map beanMap = new HashMap();
		for (Field f : c.getFields()) {
			if (f.getDeclaringClass() != c) {
				continue;
			}

			int mod = f.getModifiers();
			if (Modifier.isPublic(mod) && Modifier.isStatic(mod)) {
				String key = f.getName();
				Object value = f.get(key);
				beanMap.put(key, value);

				if (log.isDebugEnabled()) {
					log.debug(cName + "." + key);
				}
			}
		}

		if (beanMap.size() > 0) {
			context.setAttribute(cName, beanMap);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
	}
}