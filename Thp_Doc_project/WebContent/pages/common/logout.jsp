<%
	session.invalidate();
	String serverName = request.getRequestURL().toString();
	String serverTmp = request.getRequestURI().toString();
	String context_path = request.getContextPath();

	serverName = serverName.replaceAll(serverTmp, "");
	serverName = serverName + context_path;
	response.sendRedirect(serverName+"/index.html");
%>

