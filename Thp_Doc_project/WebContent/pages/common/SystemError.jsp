<%--
/*
 * file name : SystemError.jsp
 * title     : システムエラー画面
--%>
<%-- ========================================================================================== Define --%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page isErrorPage="true"%>
<%@ include file="/pages/common/header.jsp"%>
<%@page import="tech3g.common.util.DateUtil"%>

<%@page import="tech3g.common.exceptions.ErrorInfoVO"%>
<%@page import="tech3g.common.util.StrUtil"%>
<%@page import="tech3g.common.util.ExceptionUtil"%>
<html>

<%
	Object errorObj = request.getAttribute("SYSTEM_EXCEPTION");

	if (errorObj == null) {
		ErrorInfoVO errorVO = new ErrorInfoVO();
		Exception e = (Exception) request.getAttribute("javax.servlet.error.exception");

		if (e != null) {
			errorVO.setMessage("Internal 500 Errorが発生しました。\n" + StrUtil.clearNull((String) request.getAttribute("javax.servlet.error.message")));
			errorVO.setTime(DateUtil.getCurrentTimeStr2());
			errorVO.setDetail("");
			errorVO.setUri(StrUtil.clearNull((String)request.getAttribute("javax.servlet.error.request_uri")));
			request.setAttribute("SYSTEM_EXCEPTION", errorVO);
		}

		if (exception != null) {
			errorVO = ExceptionUtil.getErrorInfoVO(StrUtil.clearNull((String)request.getAttribute("javax.servlet.error.request_uri")),
					exception, "Internal 500 Errorが発生しました。\n" + exception.getMessage());
			request.setAttribute("SYSTEM_EXCEPTION", errorVO);
		}
	}
%>

<head>
	<title>SystemError</title>
	<link rel="StyleSheet" href="<html:rewrite page='/css/common/liveany-common.css'/>" type="text/css" />
</head>

<body>

<div style="font-size: 25pt;"><img  src="<html:rewrite page='/images/common/err_b_ueki.png'/>"/><b>SystemError</b></div>
<hr/>
<div style="font-size: 11pt;color: #FF3300"><b>システムエラーが発生しました。
											<br/>

<br/>

<table class="content_i" width="810px">
	<tr>
		<td class="td_f" width="100px">発生時間</td>
		<td class="td_w" >
			<c:out value="${SYSTEM_EXCEPTION.time}"/>
		</td>
	</tr>
	<tr>
		<td class="td_f" width="100px">メッセージ</td>
		<td class="td_w" >
			<pre><c:out value="${SYSTEM_EXCEPTION.message}"/></pre>
		</td>
	</tr>
	<tr>
		<td class="td_f" width="100px">URL</td>
		<td class="td_w" >
			<c:out value="${SYSTEM_EXCEPTION.uri}"/>
		</td>
	</tr>

	<tr>
		<td class="td_f" width="100px">StackTrace</td>
		<td class="td_w" style="word-break:break-all;" >
<!-- ============================================================================= Start -->
<pre>
<c:out value="${SYSTEM_EXCEPTION.detail}"/>
</pre>
<!-- ============================================================================= End  -->
		</td>
	</tr>
</table>
</body>
</html>
