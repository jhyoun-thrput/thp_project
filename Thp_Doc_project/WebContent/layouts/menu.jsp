<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="org.apache.commons.collections.map.ListOrderedMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/liveany-logic.tld" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Menu</title>
<style type="text/css" media="all">
<!--
@import
url(<html:rewrite
page=
"/css/maven-base.css"
/>);
@import
url(<html:rewrite
page=
"/css/maven-theme.css"
/>);
-->
</style>

</head>

<%

String auth_flg = "0";
ListOrderedMap map = (ListOrderedMap)session.getAttribute("USERINFO");

if (map != null) {
	auth_flg = String.valueOf(map.get("user_admin"));
}

%>
<body class="composite">
	<div id="leftColumn">
		<div id="navcolumn">
			<h5>
				<a style="font-size:12pt">☑Tech Group</a>
			</h5>
			<br/>
			<ul>
				<li>
					<font color="red">*</font>&nbsp;<html:link action="/doc/initTechDoc.do" target="body" style="font-size:9pt; text-decoration:underline;">Documents System</html:link>
				</li>
			</ul>
			<br/>
<%
	if ("1".equals(auth_flg)) {
%>
			<ul>
				<li>
					<font color="red">*</font>&nbsp;<html:link action="/doc/batch/initMasterBatch.do" target="body" style="font-size:9pt; text-decoration:underline;">Batch Manager</html:link>
				</li>
			</ul>
			<br/>
			<ul>
				<li>
					<font color="red">*</font>&nbsp;<html:link action="/doc/user/initUserManager.do" target="body" style="font-size:9pt; text-decoration:underline;">User Manager</html:link>
				</li>
				<%--<li><html:link action="/doc/drugAndDrop.do" target="body" style="font-size:9pt; text-decoration:underline;">Drug And Drop</html:link></li> --%>
			</ul>
			<br/>
			<h5>
				<a style="font-size:12pt">☑Tech Statistics</a>
			</h5>
			<br/>
			<ul>
				<li>
					<font color="red">*</font>&nbsp;<html:link  action="/statis/initSpbGraph.do"  target="body" style="font-size:9pt; text-decoration:underline;">SPB Statistics</html:link>
				</li>
				<%--<li><html:link action="/doc/drugAndDrop.do" target="body" style="font-size:9pt; text-decoration:underline;">Drug And Drop</html:link></li> --%>
			</ul>
<%
	}
%>
				&nbsp;
			<ul>
			</ul>
		</div>
	</div>
</body>
</html>