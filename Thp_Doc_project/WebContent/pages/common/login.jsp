<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.Date"%>

<%
	String check = request.getParameter("check");

	String id = request.getParameter("user_id");
	if (id == null)
		id = "";

	String pwd = request.getParameter("passwd");
	if (pwd == null)
		pwd = "";

	String idCheck = request.getParameter("idCheck");

	String ip = request.getHeader("X-Forwarded-For");
	if (ip == null || ip.length() == 0)
		ip = request.getRemoteAddr();

	idCheck = (idCheck == null ? "" : idCheck);

	String ROOT_IP = request.getServerName();
	String context_path = request.getContextPath();

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<script language="javascript" src="/js/liveany-cfrm.js"></script>
<script language="javascript" src="/js/liveany-validate.js"></script>
<script language="javascript" src="/js/liveany-common.js"></script>
<script language="javascript" src="/js/common/alertMessage.js"></script>
<script language="javascript">
</script>

<title></title>
<link rel="stylesheet" href="/css/common/basic.css" type="text/css"/>
<script language='javascript'>

	window.name="Tech";
	var win;

	function chkLogin(){

		if((frm.user_id.value).trim() == ''){

			frm.user_id.focus();
			alert('User ID を入力してください.');
			return;
		} else if((frm.passwd.value).trim() == ''){

			frm.passwd.focus();
			alert('Password を入力してください.');
			return;
		} else {
			var expDays = 365; // number of days the cookie should last
			var expDate = new Date();
			expDate.setTime(expDate.getTime() +  (24 * 60 * 60 * 1000 * expDays));
			SetCookie('TECH_ID_LOGIN', frm.user_id.value, expDate);

			frm.check.value = "check";
			frm.target="_self";
			frm.action= "/Tech3g_Doc_project/common/loginCheck.do";
			frm.submit();
		}
	}

	function keydown(){

		var ch = window.event.keyCode;
		if(ch==13){
			chkLogin();
		}else {
			return;
		}
	}

	function docLoad(){

		if(frm.user_id.value == "")
			readID();

		if(frm.user_id.value == "") {
			frm.user_id.focus();
		} else {
			frm.passwd.focus();
		}
	}

	function readID() {

		var expDays = 365; // number of days the cookie should last
		var expDate = new Date();
		expDate.setTime(expDate.getTime() +  (24 * 60 * 60 * 1000 * expDays));
		var idCheck = GetCookie('ID_SAV');
		var id = "";

		if (idCheck == 'yes'){
			id = GetCookie('TECH_ID_LOGIN');
			if (id == null) {
				if (location.search.length > 1)
					id = location.search.substring(1, location.search.length);
				else id = "";

				if (id != GetCookie('TECH_ID_LOGIN'))
					SetCookie('TECH_ID_LOGIN', id, expDate);
				if (idCheck != GetCookie('ID_SAV'))
					SetCookie('ID_SAV', idCheck, expDate);
			}
		} else {
			idCheck = "";
			id = "";
		}

		frm.user_id.value = id;
		frm.idCheck.value = idCheck;

		if (idCheck == "")
			frm.idCheck.checked = false;
		else
			frm.idCheck.checked = true;

		if(frm.user_id.value == "") {
			frm.user_id.focus();
		} else {
			frm.passwd.focus();
		}
	}

	function GetCookie (name) {

		var arg  = name + "=";
		var alen = arg.length;
		var clen = document.cookie.length;
		var i = 0;

		while (i < clen) {
			var j = i + alen;
			if (document.cookie.substring(i, j) == arg)
				return getCookieVal (j);

			i = document.cookie.indexOf(" ", i) + 1;
			if (i == 0) break;
		}
		return null;
	}

	function getCookieVal (offset) {

		var endstr = document.cookie.indexOf (";", offset);
		if (endstr == -1)
			endstr = document.cookie.length;

		return unescape(document.cookie.substring(offset, endstr));
	}

	function SetCookie (name, value) {

		var argv    = SetCookie.arguments;
		var argc    = SetCookie.arguments.length;
		var expires = (argc > 2) ? argv[2] : null;
		var path    = (argc > 3) ? argv[3] : null;
		var domain  = (argc > 4) ? argv[4] : null;
		var secure  = (argc > 5) ? argv[5] : false;

		document.cookie = name + "=" + escape (value) +
						((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
						((path == null) ? "" : ("; path=" + path)) +
						((domain == null) ? "" : ("; domain=" + domain)) +
						((secure == true) ? "; secure" : "");
	}

	function cConfirm(){

		var expDays = 365; // number of days the cookie should last
		var expDate = new Date();
		expDate.setTime(expDate.getTime() +  (24 * 60 * 60 * 1000 * expDays));

		if(frm.idCheck.checked == true){
			SetCookie('ID_SAV', 'yes', expDate);
		} else{
			SetCookie('ID_SAV', '', expDate);
		}
	}

	function winFocus(){

		if(win != null && win != 'undefined'){
			win.focus();
		}
	}

	String.prototype.trim = function(){
	    return this.replace(/(^\s*)|(\s*$)/g, "");
	}

</script>
</head>

<body>
<form name="frm" method="post">
<div id="server_cl" style="position:absolute; left:10px; top:10px; z-index:02;" >
</div>
<table style="width: 100%; height: 100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="center">
		<table width="768" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="4"><img src="<%= context_path %>/images/common/main_box01.gif" width="4" height="4"/></td>
				<td style="background: <%= context_path %>/images/common/main_box02.gif"></td>
				<td width="4"><img src="<%= context_path %>/images/common/main_box03.gif" width="4" height="4"/></td>
			</tr>
			<tr>
				<td style="background: <%= context_path %>/images/common/main_box04.gif"></td>
				<td style="padding: 8 0" align="center">

				<!--*************** Contents Table S ***************-->
				<table width="740" border="0" cellspacing="0" cellpadding="1">
					<tr>
						<td colspan="2" height="56" valign="top"><img src="<%= context_path %>/images/common/img_main.gif"/></td>
					</tr>
					<tr>
						<td colspan="2" height="1" bgcolor="#cecece"></td>
					</tr>
					<tr>
						<td colspan="2" height="7"></td>
					</tr>
					<tr>
						<td width="277" valign="top">

						<!----- Left Table S ----->
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="7"><img src="<%= context_path %>/images/common/main_ltop.gif" width="267" height="7"/></td>
							</tr>
							<tr>
								<td height="201" bgcolor="#f5f5f5" valign="top">

								<table width="241" border="0" cellspacing="0" cellpadding="0" style="margin-left: 10px">
									<tr>
										<td style="background: <%= context_path %>/images/common/main_login_bg.gif">
											<img src="<%= context_path %>/images/common/main_login.gif" width="123" height="72"/></td>
									</tr>
									<tr>
										<td height="12"></td>
									</tr>
									<tr>
										<td align="right">

										<!----- Login Table S ----->
										<table width="250" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="58"><img src="<%= context_path %>/images/common/main_id.gif" width="55" height="18"/></td>
												<td><input type="text" name="user_id" class="login"
														   value="<%=id%>" maxlength="15" onkeypress="keydown()"
														   style="width: 100%" onblur='this.className="login"'
														   onfocus='this.className="login_o"'/></td>
											</tr>
											<tr>
												<td colspan="2" height="2"></td>
											</tr>
											<tr>
												<td><img src="<%= context_path %>/images/common/main_pw.gif" width="55" height="18"/></td>
												<td><input type="password" name="passwd" class="login"
														   value="" style="width: 100%" maxlength="30"
														   onkeypress="keydown()" onblur='this.className="login"'
														   onfocus='this.className="login_o"'/></td>
											</tr>
											<tr>
												<td colspan="2" class="font_id" style="padding: 1 0 2 55">
													<input type="checkbox" name="idCheck" value="no"
														   <%=(idCheck.equalsIgnoreCase("no") ? "" : "checked")%>
														   onclick="cConfirm()"/>ID保存</td>
											</tr>
											<tr>
												<td></td>
												<td><a href="javascript:chkLogin();">
													<img src="<%= context_path %>/images/common/main_butt_login.gif" width="178" height="45" border="0"/></a></td>
											</tr>

										</table>

										<!----- Login Table E -----></td>
									</tr>
								</table>

								</td>
							</tr>
							<tr>
								<td height="7"><img src="<%= context_path %>/images/common/main_lbottom.gif" width="267" height="7"/></td>
							</tr>
						</table>

						<!----- Left Table E -----></td>
						<td width="463" valign="top"><img src="<%= context_path %>/images/common/sma-logo.jpg" /></td>
					</tr>
				</table>

				<!--*************** Contents Table E ***************--></td>
				<td style="background: <%= context_path %>/images/common/main_box05.gif"></td>
			</tr>
			<tr>
				<td><img src="<%= context_path %>/images/common/main_box06.gif" width="4" height="4"/></td>
				<td style="background: <%= context_path %>/images/common/main_box07.gif"></td>
				<td><img src="<%= context_path %>/images/common/main_box08.gif" width="4" height="4"/></td>
			</tr>
		</table>

		</td>
	</tr>
</table>
<input type="hidden" name="ip" value="<%=ip%>"/>
<input type="hidden" name="check" value=""/>

</form>
<script language="javascript">
readID();
</script>
<%
	if (check != null && check.equals("check")) {
		String result = (String) request.getAttribute("chkLogin");
		if (result != null && result.equals("passInit")) {
%>

<script language="javascript">

		//openModal("/passwdUpd_p.jsp", "passwdUpdate", "410", "200", "no");
		openWindow("/pages/common/passwdUpd_p.jsp", "passwdUpdate", "410", "160", "no");
		//if (parseInt(navigator.appVersion) >= 4) { win.focus(); }

</script>

<%
	} else {
%>

<script language="javascript">

		alert('使用者ID又は、パスワードが異なります。');
		frm.passwd.focus();

</script>

<%
	}

	} else {

		session.removeAttribute("USERINFO");
	}
%>
</body>
</html>
