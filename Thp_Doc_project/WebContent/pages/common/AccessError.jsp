<%--
/*
 * file name : AccessError.jsp
 * title     : 認証チェックエラー画面
--%>
<%-- ========================================================================================== Define --%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/pages/common/header.jsp"%>

<html>
<head>
<title>AccessError</title>

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

/**
 * ログイン画面へ遷移する。
 **/
function gotoLogin() {

		if (!opener) {
			frm.target = "_top";
			frm.action = "/Tech3g_Doc_project/common/InitMenu.do";
			// /layouts/framesetLayout.jsp
			frm.submit();
		} else {
			window.opener.parent.document.location.target = "_top";
			window.opener.parent.document.location.href = "/Tech3g_Doc_project/common/InitMenu.do";
			window.close();
		}
}

</script>
</head>
<body>
<div align="center">
<br/><br/><br/><br/><br/><br/>

<div style="font-size: 15pt;"><font color="blur">認証エラー</font></div>

●ログインしていないか、セッションタイムアウトされました。<br/>
再度ログインして下さい。
<br/>

<br/>
※セッションタイムアウト：30分以上操作しない場合、自動的にログアウトされます。
<br/>
<br/>

<liveany:button action="javascript:gotoLogin();" type="blue_gray" label="ログイン画面へ"/>
</div>



<form name="frm" method="post" action="">
	<%-- EmptyForm --%>
</form>

</body>
</html>