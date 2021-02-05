<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%

String context = request.getContextPath();

%>

	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
		<title>Drag and Drop Applet Demo</title>
	</head>

	<body>
        Drag and Drop Applet Demo Image Uploader<br /><br />

        <applet code="<%= context %>/applet/DNDApplet.class" width="200" height="150">
        </app.00000let>
        <br/>
    </body>
</html>