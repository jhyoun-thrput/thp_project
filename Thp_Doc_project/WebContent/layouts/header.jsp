<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.collections.map.ListOrderedMap"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Menu</title>

<style type="text/css" media="all">
<!--
@import
url('<html:rewrite page="/css/maven-base.css"/>');
@import
url('<html:rewrite page="/css/maven-theme.css"/>');
-->
</style>

<%@ page errorPage="/pages/common/SystemError.jsp" %>
<%
response.setHeader("cache-control","no-cache");
response.setHeader("expires","0");
response.setHeader("pragma","no-cache");

if (request.getAttribute(tech3g.common.constants.CommonConsts.KEY_SYS_EXC) != null) {
	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
}

String user_id = "";
String user_name = "";
String context_path = request.getContextPath();
ListOrderedMap map = (ListOrderedMap)session.getAttribute("USERINFO");

if (map != null ) {
	user_id = String.valueOf(map.get("user_id"));
	user_name = String.valueOf(map.get("user_name"));
}

%>

</head>
<body>
	<div id="banner">
		<hr />
		&nbsp;
		<html:link forward="home" target="_top" style="font-size:22pt"><img src="<%= request.getContextPath()%>/images/common/sam_log.jpg" width="155" height="45" border="0"/>
			&nbsp;<img src="<%= context_path %>/images/common/img_main.gif" width="540"/>
		</html:link>
		&nbsp;&nbsp;
		Welcome To [ <html:link action="/doc/user/initPassUpt.do" target="body"><img src="<%= request.getContextPath()%>/images/common/icoDevWel.gif" border="0"/>
			<font color="blue"><b><%= user_name %></b></font></html:link> ]
		<html:link action="/common/logout.do" target="_top" >[ Logout ]</html:link>
		<div class="clear">
			<hr />
		</div>
	</div>
	<div id="breadcrumbs">
		<div class="xright">
		</div>
		<div class="clear">
			<hr />
		</div>
	</div>
</body>
</html>