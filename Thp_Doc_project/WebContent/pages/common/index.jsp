<%
	String checkIp = request.getParameter("checkip");
	if(checkIp == null) checkIp = "Y";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>URASOE CITY INFORMATION PROCESSING SYSTEM</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<frameset rows="83,*" cols="*" frameborder="no" border="0" framespacing="0">
	<frame src="/pages/common/top_.jsp?checkip=<%=checkIp%>" name="top" scrolling="no" noresize >
	<frameset cols="170,*" frameborder="no" border="0" framespacing="0">
		<frame src="/common/MyMenu.do?method=getMyMenuList" name="left" scrolling="auto" noresize >
		<frame src="/pages/common/main.html" name="page">
	</frameset>
</frameset>

<noframes>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
</body>
</noframes>
</html>
