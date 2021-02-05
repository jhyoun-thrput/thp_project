<%--
/*
 * file name : ModalWinReqPage.jsp
 * title     : ModalWindowRequestPage
 * desc      : モーダルウインドを開きPostでRequestをしてくれるページ
--%>
<%-- ========================================================================================== ヘッダ定義--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/pages/common/header.jsp"%>
<%-- ========================================================================================== 変数宣言--%>

<%-- ========================================================================================== javaScript && CSS 宣言--%>

<html>
<head>
<base target="_self"/>
</head>
<body>
	画面を表示中
</body>
</html>

<script language="javascript">

	var url = window.dialogArguments["url"];
	var inParam = window.dialogArguments["in"];
	var outParam = window.dialogArguments["out"];
	window.opener = window.dialogArguments["opener"];

	var frm = document.createElement("form");
	frm.action = url;
	frm.method = "POST";
	for ( var param in inParam) {
		var hidden = document.createElement("input");
		hidden.type = "hidden";
		hidden.name = param;
		hidden.value = inParam[param];
		frm.appendChild(hidden);
	}
	document.body.appendChild(frm);
	frm.submit();
	document.body.removeChild(frm);

</script>


