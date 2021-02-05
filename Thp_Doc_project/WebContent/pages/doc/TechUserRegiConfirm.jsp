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

	function closeAndRedraw() {

		// 親画面から渡されたObject取得
		var outParam = getOutputArray(this.window.opener);

		// 再表示のアクションを取得する。
		var executeUrl = outParam["on_close_func"];

		if (!isEmpty(executeUrl)) {
			eval("opener." + executeUrl + "();");
		}
		// 当画面を閉じる。
		this.window.close();
	}

</script>

<%-- ========================================================================================== Body--%>
<form name="frm"  method="post" action="<html:rewrite page='/doc/user/initUserRegi.do'/>" >

	<%-- *** 2度押しTokenチェック *** --%>
	<liveany:token/>

	<div id="container" style="width:550px;">
		<div id="content" class="heightLineParent">
			<%-- Contents開始 --%>
			<div class="w00">

				<liveany:title titleName="ユーザ登録"/>
				<liveany:title titleName="入力項目" level="2"/>
				<div>
					<logic:notEmpty name="viewBean" property="err_massge">
						<font color="red"> ${viewBean.err_massge} </font>
					</logic:notEmpty>
				</div>
				<table class="content_i">
					<tr>
						<td width="80" class="td_f">User ID</td>
						<td class="td_w" >
							<liveany:text styleId="id_user_id" name="viewBean" property="user_id" maxlength="60" size="40" styleClass="b" inputType="han" tabindex="2" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">User Name</td>
						<td class="td_w">
							<liveany:text styleId="id_user_name" name="viewBean" property="user_name" maxlength="100" size="40" inputType="ascii" tabindex="3" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">Admin Role</td>
						<td class="td_w">
							<c:if test="${viewBean.user_admin == '1'}">
								<font color="red">管理者</font>
							</c:if>
							<c:if test="${viewBean.user_admin == '0'}">
								<font color="blue">一般ユーザ</font>
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">User Mail</td>
						<td class="td_w">
							<liveany:text styleId="id_user_mail" name="viewBean" property="user_mail" maxlength="80" size="40" inputType="han" tabindex="4" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">連絡先</td>
						<td class="td_w">
							<liveany:text styleId="id_user_tel_no" name="viewBean" property="user_tel_no" maxlength="20" size="30" inputType="han_suji" tabindex="4" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">User IP</td>
						<td class="td_w">
							<liveany:text styleId="id_user_ip" name="viewBean" property="user_ip" maxlength="15" size="20" inputType="han" tabindex="4" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">登録者</td>
						<td class="td_w" width="80">
							${ viewBean.regi_user }
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">登録日</td>
						<td class="td_w">
							${ viewBean.regi_date }
						</td>
					</tr>
				</table>
<%-- ========================================================================================== ボタン部--%>
				<table align="right">
					<tr>
						<td align="right">
							<liveany:button id="close" action="javascript:closeAndRedraw();" type="blue_gray" label="閉じる"/>
						</td>
					</tr>
				</table>
			</div><%-- w00-1cnt END --%>
        </div><%-- Contents開始 --%>
  </div><%-- container開始 --%>

 </form>