<%--
/*
 * author    : Youn.J.H
 * revisions : 2012/08/01
 */
--%>
<%-- ========================================================================================== ヘッダ定義--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/pages/common/header.jsp"%>
<%-- ========================================================================================== 変数宣言--%>
<c:set var="viewBean" value="${requestScope.viewBean}" />
<%-- ========================================================================================== javaScript && CSS 宣言--%>
<script language="javascript">

	/**
	 * init
	 */
	function init() {
	}

	/**
	 * 検索
	 */
	function srchBatchSchd() {
		document.frm.action = "<html:rewrite page='/doc/batch/searchBatchList.do'/>";
		document.frm.submit();
	}

	/**
	 * 閉じる
	 */
	function closeExeBatch() {
		window.close();
	}

	/**
	 * バッチ一覧のページ遷移
	 */
	function goPage(pageNo) {
		document.frm.pageNo.value= pageNo;
		document.frm.action = "<html:rewrite page='/doc/user/searchUserManager.do'/>";
	    document.frm.submit();
	}

	function insertUserPopUp() {
		var inParam = new Array();
		var outParam = new Array();

		// 子画面を閉じる際のアクションを指定
		outParam["on_close_func"] = 'refresh';

		//openPopupModal(600, 350, 'user_regi', '/doc/user/initUserRegi.do', inParam, outParam, true);
		openPopup(600, 450, 'user_regi', '/doc/user/initUserRegi.do', inParam, outParam, true);
	}

	function updateUserPopUp() {

		var inParam = new Array();
		var outParam = new Array();

  		// 選択された行のラジオの値を取得する。
		var checkedIndex = getCheckedIndex(frm.p_user_sel);

		if (!chkRequired(checkedIndex, "選択")) {
			return;
		}

		//frm.selected_user_id.value = getValue(frm.p_user_id, checkedIndex);
		inParam["selected_user_id"] = getValue(frm.p_user_id, checkedIndex);

		// 子画面を閉じる際のアクションを指定
		outParam["on_close_func"] = 'refresh';
		openPopup(600, 450, 'user_regi', '/doc/user/initUserRegi.do', inParam, outParam, true);
	}

	function deleteUser() {
		if (!confirm('削除しますか?')) {
			return;
		}

  		// 選択された行のラジオの値を取得する。
		var checkedIndex = getCheckedIndex(frm.p_user_sel);

		if (!chkRequired(checkedIndex, "選択")) {
			return;
		}

		frm.selected_user_id.value = getValue(frm.p_user_id, checkedIndex);
		document.frm.action = "<html:rewrite page='/doc/user/deleteUser.do'/>";
		document.frm.submit();
	}

	function refresh() {
		document.frm.action = "<html:rewrite page='/doc/user/initUserManager.do'/>";
		document.frm.submit();
	}

</script>
<%-- ========================================================================================== Body Start--%>
<form name="frm"  method="post" action="<html:rewrite page='/doc/user/initUserManager.do'/>">

	<%-- *** 2度押しTokenチェック *** --%>
<liveany:token/>
<div id="container" style="width:800;"><%-- container Start --%>
<div id="content" class="heightLineParent"><%-- Contents Start --%>

	<liveany:title titleName="ユーザー管理画面" isHelpVisible="false" isManualVisible="false" screenId="/doc/user/initUserManager" />
	<liveany:title titleName="ユーザ一覧" level="2"/>
<div align="right">
	<table>
		<tr>
			<td class="right">
				<liveany:button id="bt_insert" action="javascript:insertUserPopUp();" type="blue_gray" label="ユーザー登録" dcCheck="true"/>
			</td>
		<logic:notEmpty name="viewBean" property="userList">
			<td class="right">
				<liveany:button id="bt_insert" action="javascript:updateUserPopUp();" type="blue_gray" label="ユーザー更新" dcCheck="true"/>
			</td>
			<td class="right">
				<liveany:button id="bt_insert" action="javascript:deleteUser();" type="blue_gray" label="ユーザー削除" dcCheck="true"/>
			</td>
		</logic:notEmpty>
		</tr>
	</table>
</div>
	<table class="list_table" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<%-- **********タイトル********** Start --%>
				<table width="100%" class="list_head" cellpadding="0" cellspacing="1">
					<tr>
						<td width="30" class="head" rowspan="2">No</td>
						<td width="30" class="head" rowspan="2">選択</td>
						<td width="150" class="head">User ID</td>
						<td width="200" class="head">Name</td>
						<td width="100" class="head" rowspan="2">Admin</td>
						<td width="120" class="head">連絡先</td>
						<td width="80" class="head">IP</td>
						<td width="80" class="head">登録者</td>
					</tr>
					<tr>
						<td width="150" class="head">User Pass</td>
						<td width="200" class="head">User Mail</td>
						<td width="120" class="head">登録日時</td>
						<td width="120" class="head">更新日時</td>
						<td width="80" class="head">更新者</td>
					</tr>
				</table>
				<%-- **********タイトル*********** End --%>
			</td>
		</tr>
		<tr>
			<td class="list_body">
				<%-- **********一覧内容********** Start --%>
				<div style="height: 440;" class="list_body_div">
					<table class="list_body_table" width="100%" cellpadding="0" cellspacing="1" >
						<c:forEach var="userList" items='${viewBean.userList}'  varStatus="status" >
							<tr class="<c:if test="${status.index % 2 == 0}">line0</c:if><c:if test="${status.index % 2 != 0}">line1</c:if>" 	<%-- TR 行の色 --%>
								onmouseover="this.style.backgroundColor='FDF2E3'" onmouseout="this.style.backgroundColor=''">
								<td>				<%-- TR マウス制御 --%>
									<table class="list_table_plu_line">
										<label for="id_userList_chk_${status.index}">
											<tr>
												<td width="30" class="c" rowspan="2">
													<liveany:text property="doc_row" value="${userList.rownum}" style="text-align:center;width:30px;" readonly="true" />
												</td>
												<td width="35" rowspan="2">
													<html:radio name="viewBean" property="p_user_sel" value="${status.index}" styleId="id_p_user_sel_${status.index}" style="width:100%" onclick="javascript:flase;" tabindex="5"/>
													<a id="idx_${status.index}" name="idx_${status.index}"/>
													<html:hidden name="viewBean" property="p_user_id" value="${userList.user_id}" />
												</td>
												<td width="150"><liveany:text property="userList_user_id" value="${userList.user_id}" styleClass="b" style="width:100%;" readonly="true"/></td>
												<td width="200"><liveany:text property="userList_user_name" value="${userList.user_name}" style="width:100%" readonly="true"/></td>

												<td width="100" rowspan="2" align="center">
													<c:if test="${userList.user_admin == '1'}">
														<font color="red">管理者</font>
													</c:if>
													<c:if test="${userList.user_admin != '1'}">
														<font color="blue">一般ユーザ</font>
													</c:if>
													<%--<liveany:text property="userList_admin" value="${userList.user_admin}" style="width:100%" readonly="true"/> --%>
												</td>
												<td width="120">
													<liveany:text property="userList_user_tel_no" value="${userList.user_tel_no}" style="width:100%" readonly="true"/>
												</td>
												<td width="80"><liveany:text property="userList_user_ip" value="${userList.user_ip}" style="width:100%;" readonly="true"/></td>
												<td width="80" align="center">
													${userList.regi_user}
												</td>
											</tr>
											<tr>
												<td width="150"><liveany:text property="userList_user_pass" value="${userList.user_pass}" style="width:100%;color:red;" readonly="true"/></td>
												<td width="200"><liveany:text property="userList_user_mail" value="${userList.user_mail}" style="width:100%" readonly="true"/></td>
												<td width="120">
													<liveany:text property="userList_regi_date" value="${userList.regi_date}" style="width:100%" readonly="true"/>
												</td>
												<td width="120">
													<liveany:text property="userList_upt_date" value="${userList.upt_date}" style="width:100%" readonly="true"/>
												</td>
												<td width="80" align="center">
													${userList.upt_user}
												</td>
											</tr>
										</label>
									</table>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<%-- **********一覧内容********** End --%>
			</td>
		</tr>
		<%-- ページング定義 --%>
		<tr align="center">
			<td><liveany:page pageNo="${viewBean.userListPageInfo.pageNo}" listNo="${viewBean.userListPageInfo.listRowCnt}" totalCnt="${viewBean.userListPageInfo.totalCnt}" noPerIdx="10"   funcNm="goPage"/></td>
		</tr>
	</table>
</div>
</div>

<html:hidden name="viewBean" property="selected_user_id"/>

</form>
<%-- ========================================================================================== Body End--%>

