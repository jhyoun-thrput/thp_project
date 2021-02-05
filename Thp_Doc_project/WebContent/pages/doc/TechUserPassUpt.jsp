<%-- ========================================================================================== Define --%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/pages/common/header.jsp"%>
<script type="text/javascript" src="<html:rewrite page='/js/ho/liveany-ho-ajax.js'/>"></script>
<%-- ========================================================================================== 変数宣言--%>
<c:set var="viewBean" value="${requestScope.viewBean}" />
<%-- ========================================================================================== javaScrip && CSS 宣言--%>

<script language="javascript">

	/**
	 * 画面がonloadされたときの処理を行う。
	 * bodyタグのonloadの際、実行される。
	 */
	function init(){
	}

	function valid_email(val) {
		// email input pattern
		 pattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		 if (!isEmpty(val.value)) {
			 if(!pattern.test(val.value)) {
				 alert("E-mail 形式ではありません。再度、確認してください。");
				 return false;
			 }
		 }
	}

	function isIpValid(val) {
		// ip input pattern
		var ipPattern = /^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})$/;
		var ipArray = "";
		if (!isEmpty(val.value)) {
			ipArray = val.value.match(ipPattern);
			if (ipArray == null) {
				  alert("IP の入力形式ではありません。\n [ XXX.XXX.XXX.XXX ] 形式で入力してください。");
				  return false;
			 }
			 else {
				  for (i = 0; i < 4; i++) {
					  thisSegment = ipArray[i];
					  if (thisSegment > 255) {
						  alert("IP の入力範囲外です。\n IP の 入力範囲は、[255.255.255.255]内で入力してください。 ");
						  return false;
					  }
					  if ((i == 0) && (thisSegment > 255)) {
						  alert("IP の入力範囲外です。\n IP の 入力範囲は、[255.255.255.255]内で入力してください。 ");
						  return false;
					  }
				  }
			 }
		}
	}

	/**
	 * Pass Update
	 */
	function updatePass(){
		if (!confirm('変更しますか？')) {
			return;
		}
		document.frm.action = "<html:rewrite page='/doc/user/passUpdate.do'/>";
		document.frm.submit();
	}

	function loginUpdatePass(){
		if (!confirm('変更しますか？')) {
			return;
		}
		document.frm.action = "<html:rewrite page='/doc/user/loginPassUpt.do'/>";
		document.frm.submit();
	}

</script>

<%-- ========================================================================================== Body--%>
<form name="frm"  method="post" action="<html:rewrite page='/doc/user/initPassUpt.do'/>" >

	<%-- *** 2度押しTokenチェック *** --%>
	<liveany:token/>

	<div id="container" style="width:550;">
		<div id="content" class="heightLineParent">
			<%-- Contents開始 --%>
			<div class="w00">

				<liveany:title titleName="Password 変更"/>
				<liveany:title titleName="入力項目" level="2"/>
				<div>
					<logic:notEmpty name="viewBean" property="err_massge">
						<font color="red"> ${viewBean.err_massge} </font>
					</logic:notEmpty>
				</div>
				<table class="content_i">
					<tr>
						<td width="110" class="td_f"><font color="red">*</font>User ID</td>
						<td class="td_w" >
							<liveany:text styleId="id_user_id" name="viewBean" property="user_id" maxlength="60" size="40" styleClass="b" inputType="han" tabindex="2" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td width="110" class="td_f">User Name</td>
						<td class="td_w">
							<liveany:text styleId="id_user_name" name="viewBean" property="user_name" maxlength="100" size="40" inputType="ascii" tabindex="3"/>
						</td>
					</tr>
					<tr>
						<td width="110" class="td_f"><font color="red">*</font>Current User Pass</td>
						<td class="td_w">
							<%--<liveany:text styleId="id_user_param" name="viewBean" property="user_pass" maxlength="60" size="30" inputType="password" tabindex="4"/> --%>
							<input name="current_pass" type="password" class="input" maxlength="60" size="40" tabindex="4"/>
						</td>
					</tr>
					<tr>
						<td width="110" class="td_f"><font color="red">*</font>New User Pass</td>
						<td class="td_w">
							<%--<liveany:text styleId="id_user_param" name="viewBean" property="user_pass" maxlength="60" size="30" inputType="password" tabindex="4"/> --%>
							<input name="new_pass" type="password" class="input" maxlength="60" size="40" tabindex="4"/>
						</td>
					</tr>
					<tr>
						<td width="110" class="td_f"><font color="red">*</font>Confirm New Pass</td>
						<td class="td_w">
							<%--<liveany:text styleId="id_user_param" name="viewBean" property="user_pass" maxlength="60" size="30" inputType="password" tabindex="4"/> --%>
							<input name="confrm_pass" type="password" class="input" maxlength="60" size="40" tabindex="5"/>
							<br/><font color="blue">※確認のため、再度パスワードを入力してください。</font>
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">Admin Role</td>
						<td class="td_w">
							<c:if test="${viewBean.user_admin == '1'}">
								<font color="red">管理者</font>
							</c:if>
							<c:if test="${viewBean.user_admin != '1'}">
								<font color="blue">一般ユーザ</font>
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">User Mail</td>
						<td class="td_w">
							<liveany:text styleId="id_user_mail" name="viewBean" property="user_mail" maxlength="80" size="40" inputType="han" tabindex="7" onblur="javascript:valid_email(frm.user_mail);" />
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">連絡先</td>
						<td class="td_w">
							<liveany:text styleId="id_user_tel_no" name="viewBean" property="user_tel_no" maxlength="20" size="30" inputType="han_suji" tabindex="8" />
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">User IP</td>
						<td class="td_w">
							<liveany:text styleId="id_user_ip" name="viewBean" property="user_ip" maxlength="15" size="20" inputType="han" tabindex="9" onblur="javascript:isIpValid(frm.user_ip);" />
						</td>
					</tr>
				</table>
<%-- ========================================================================================== ボタン部--%>
				<table align="right">
					<tr>
						<td align="right">
							<logic:equal name="viewBean" property="samePass" value="same">
								<liveany:button id="insert" action="javascript:loginUpdatePass();" type="blue" label="Update" tabindex="17"/>
							</logic:equal>
							<logic:notEqual name="viewBean" property="samePass" value="same">
								<liveany:button id="insert" action="javascript:updatePass();" type="blue" label="Update" tabindex="17"/>
							</logic:notEqual>
						</td>
					</tr>
				</table>
			</div><%-- w00-1cnt END --%>
        </div><%-- Contents開始 --%>
  </div><%-- container開始 --%>
 </form>