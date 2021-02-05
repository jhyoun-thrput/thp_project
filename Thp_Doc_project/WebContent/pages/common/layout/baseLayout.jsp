<%--
/*
 * file name : baseLayout.jsp
 * title     : 共通基本レイアウト
 */
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/pages/common/header.jsp"%>
<%@ page errorPage="/pages/common/SystemError.jsp" %>
<%
response.setHeader("cache-control","no-cache");
response.setHeader("expires","0");
response.setHeader("pragma","no-cache");


if (request.getAttribute(tech3g.common.constants.CommonConsts.KEY_SYS_EXC) != null) {
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
}

%>

<!--
画面：<tiles:getAsString name="screenName" />
パス：<tiles:getAsString name="body" />
-->
<html:html>
<head>
<title><tiles:getAsString name="screenName" /></title>
<base target="_self"/>
<script type="text/javascript" src="<html:rewrite page='/js/liveany-popup-apps.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/liveany-fw.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/liveany-valid.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/liveany-util.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/liveany-popup.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/liveany-tab.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/common/jsMap.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/common/json.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/common/multiAddrPop.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/ho/liveany-ho-popup-apps.js'/>"></script> <%-- 共通業務ポップアップ --%>

<link rel="StyleSheet" href="<html:rewrite page='/css/common/liveany-common.css'/>" type="text/css" />

<div id="divProcessing" style="position:absolute; width:20px; height:20px; display:none; overflow-x:hidden; overflow-y:hidden;">
	<img src="<html:rewrite page='/images/common/processing_circle.gif'/>" />
</div>

<script type="text/javascript">

	comShowPB();

	/**
	 * bodyのonload時の処理を行う。
	 */
	function invokeOnLoad() {
		//comEndPB();
		if(this.init != undefined) {
			init();
		}

	}
</script>
</head>
<body onload="javascript:if (this.invokeOnLoad != undefined) { invokeOnLoad();}" >


	<tiles:insert attribute="header" />
	<tiles:insert attribute="body" />
	<tiles:insert attribute="footer" />


</body>

</html:html>


<script language="javascript">
//window の Load イベントを取得する。




var customOonload = window.onload;
window.onload = function () {
	customOonload();
	check2Click();
};
addLoadEvent(ud_tab);
comEndPB();

</script>

<c:if test="${ConfirmMsg != null}">
	<script language="javascript">
		alert('◎情報：　' + '<c:out value="${ConfirmMsg}"/>');
	</script>
</c:if>

<%-- =====================================================================================--%>
<%-- *** ★★★<html:errors/>の設定においての注意事項                          ★★★ *** --%>
<%-- *** ★★★isAlertを「true」で定義する場合は「footer」の下に移して下さい。 ★★★ *** --%>
<%-- *** ★★★isAlertを「false」で定義する場合は「header」の上に移して下さい。★★★ *** --%>
<%-- =====================================================================================--%>
<liveany:errors isAlert="true" />
