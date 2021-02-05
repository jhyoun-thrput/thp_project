package tech3g.common.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionServlet;

import tech3g.common.constants.CommonConsts;
import tech3g.common.web.session.SessionInfoDTO;
import tech3g.common.web.session.SessionInfoThreadManager;

public class LiveAnyActionServlet extends ActionServlet {

	/** serialVersionUID */
	private static final long serialVersionUID = 3630984299257171292L;

	/* (non-Javadoc)
	 * @see org.apache.struts.action.ActionServlet#process(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		// CurrentActionURLの取得
		String _uri = request.getRequestURI();

		// ServletContextの初期化アクション
		String _initForwradUrl = "InitConfigXML.do";

		// セッションにログインユーザ情報を格納する。
		Map userInfo = (Map) request.getSession().getAttribute("USERINFO");
		if (userInfo != null) {
			String userId = (String) userInfo.get(CommonConsts.USER_INF_USER_ID);
			String ipAddr = (String) userInfo.get(CommonConsts.USER_INF_HNDG_IP);
			SessionInfoDTO sessionInfo = SessionInfoThreadManager
					.getSessionInfo();
			sessionInfo.setUserId(userId);
			sessionInfo.setIpAddr(ipAddr);
		}

		/*****************************************************************************
		 *
		 * ・概要：
		 * 　 Struts-Config.xmlファイルが更新された際、
		 * 	  サーバーの再起動なしで、変更内容を反映する処理を行う。</br>
		 *
		 * @version 修正履歴
         *          <ul>
         *          <li>2010/02/04 : 新規作成 (revised by Yun.J.H)</li>
         *          </ul>
         * @author Yun.J.H
		 *****************************************************************************/
		if (_uri.indexOf(_initForwradUrl) != -1) {

			// CurrentModule情報を削除する。
			removeModule();

			// モジュール情報の初期化(ServletContext)
			init();

			RequestDispatcher _dispatcher = request.getRequestDispatcher("/pages/common/InitConfigXml.jsp");

			_dispatcher.forward(request, response);
		}
		else {

			// 既存のプロセスを起動する。
			super.process(request, response);
		}
	}

	/**
	 * ・(struts-config)現在モジュール情報をServletContext情報から削除する。
	 * <br/>
	 * @version 修正履歴
	 *          <ul>
	 *          <li>2010/02/08 : 新規作成 (revised by Yun.j.h)</li>
	 *          </ul>
	 */
	private void removeModule() {

		// ServletContext取得
		ServletContext context = getServletContext();

		// contextから全モジュール情報を取得する。
		Enumeration<?> applications = context.getAttributeNames();

		List<String> contextAttributes = new ArrayList<String>();

		// サーバー起動時、ServletContextに格納された情報を削除し、
		// 変更された全モジュールを取得し、初期化処理を行う。
		while (applications.hasMoreElements()) {
			// 各モジュールの取得
			String attributeName = (String) applications.nextElement();

			// struts
			if (attributeName.startsWith("org.apache.struts.")) {
				contextAttributes.add(attributeName);
			}

			// TODO spring
//			if (attributeName.startsWith("org.springframework.")) {
//				contextAttributes.add(attributeName);
//			}
//
//			// TODO MessageBundle
//			if (attributeName.startsWith("h")) {
//				contextAttributes.add(attributeName);
//			}
		}

		// CurrentContextから既存モジュール情報を削除する。
		for (int i = 0; i < contextAttributes.size(); i++) {
 			context.removeAttribute((String) contextAttributes.get(i));
		}
	}
}
