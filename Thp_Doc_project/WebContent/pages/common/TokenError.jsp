<%--
/*
 * file name : TokenError.jsp
 * title     : 2度押しエラー画面
--%>
<%-- ========================================================================================== Define --%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/pages/common/header.jsp"%>

<html>
<head>
<title>TokenError</title>

<%--<meta http-equiv="refresh" content="600;URL=javascript:gotoLogin();"/> --%>
<script type="text/javascript" src="<html:rewrite page='/js/liveany-popup-apps.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/liveany-fw.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/liveany-valid.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/liveany-util.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/liveany-popup.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/liveany-tab.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/common/jsMap.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/common/json.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/common/multiAddrPop.js'/>"></script>

<link rel="StyleSheet" href="<html:rewrite page='/css/common/liveany-common.css'/>" type="text/css" />
<script language="javascript">


</script>
</head>
<body>
<div align="center">
<br/><br/><br/><br/><br/><br/>

<div style="font-size: 15pt;"><font color="blur">２度押しエラー</font></div>

●ボタンが２度押されたか、又はF5ボタンなどで画面を最新表示しました。
<br/>
<br/>
<br/>

</div>



<form name="frm" method="post" action="">
	<%-- EmptyForm --%>
</form>

</body>
</html>