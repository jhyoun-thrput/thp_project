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

	/**
	 * 親画面の再表示を行う
	 */
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
<form name="frm"  method="post" action="<html:rewrite page='/doc/initTechDocRegi.do'/>" >

	<%-- *** 2度押しTokenチェック *** --%>
	<liveany:token/>

	<div id="container">
		<div id="content" class="heightLineParent">
			<%-- Contents開始 --%>
			<div class="w00">

				<liveany:title titleName="登録確認"/>
				<liveany:title titleName="登録内訳" level="2"/>
<table class="list_table" width="100%" cellpadding="0" cellspacing="1">
	<tr>
		<td><%-- **********タイトル********** Start --%>
			<table width="100%" class="list_head" cellpadding="0" cellspacing="1">
				<tr>
					<td width="30" class="head" rowspan="3"><b>No</b></td>
					<td width="390" class="head"><b>文書名</b></td>
					<td width="90" class="head"><b>登録者</b></td>
					<td width="90" class="head"><b>登録日時</b></td>
					<td width="60" class="head"><b>文書種類</b></td>
					<td width="60" class="head"><b>Ver</b></td>
				</tr>
				<tr>
					<td class="head" colspan="6"><b>Document Link Path</b></td>
				</tr>
				<tr>
						<td class="head" colspan="6">Comment</td>
				</tr>
			</table>
		<%-- **********タイトル*********** End --%></td>
	</tr>
	<tr>
		<td class="list_body"><%-- **********一覧内容********** Start --%>
		<div style="height: 450px;" class="list_body_div">
		<table class="list_body_table" width="100%" cellpadding="0" cellspacing="1">
				<c:forEach var="techList" items='${viewBean.techDocList}' varStatus="status">
					<tr class="<c:if test="${status.index % 2 == 0}">line0</c:if><c:if test="${status.index % 2 != 0}">line1</c:if>"
											<%-- TR 行の色 --%> onmouseover="this.style.backgroundColor='FDF2E3'" onmouseout="this.style.backgroundColor=''">
						<td>
							<table class="list_table_plu_line">
								<label for="techList_${status.index}">
									<tr>
										<td width="30" class="c" rowspan="3">
											<liveany:text property="doc_row" value="${techList.rownum}" style="text-align:center;width:30px;" readonly="true" />
										</td>
										<td>
												<liveany:text property="techList_doc_name" value="${techList.doc_name}"  styleClass="b" style="text-align:left; width:410px; color: #333333; cursor:hand; text-decoration:underline" readonly="true"/>
										</td>
										<td align="center">
											<liveany:text property="techList_regi_user" value="${techList.regi_user}" style="text-align:center;width:90px;" readonly="true" />
										</td>
										<td align="center">
											<liveany:text property="techList_regi_date" value="${techList.regi_date}" styleClass="b" style="text-align:center;width:90px;" readonly="true" />
										</td>
										<td align="center">
											<liveany:text property="techList_doc_type" value="${techList.doc_type}" style="text-align:center;width:60px;" readonly="true"/>
										</td>
										<td align="center">
											<liveany:text property="techList_doc_ver" value="${techList.doc_ver}" styleClass="b" style="text-align:center;width:50px;" readonly="true" />
										</td>
									</tr>
									<tr>
										<td colspan="6">
											<liveany:text property="techlist_doc_link" value="${techList.doc_link}" style="text-align:left;width:100%;" readonly="true" />
										</td>
									</tr>
									<tr>
										<td colspan="6">
												<liveany:textarea  property="techlist_doc_comment" value="${techList.comment}" style="width:100%; font-family:'MS Gothic'" readonly="true"/>
										</td>
									</tr>
								</label>
							</table>
						</td>
					</tr>
				</c:forEach>
		</table>

<%-- ========================================================================================== ボタン部--%>

			</div><%-- w00-1cnt END --%>
        </div><%-- Contents開始 --%>
  </div><%-- container開始 --%>
  <div>
 				<table align="right">
				<tr>
					<td align="right">
						<liveany:button id="close" action="javascript:closeAndRedraw();" type="blue_gray" label="閉じる"/>
					</td>
				</tr>
			</table>
  </div>
<html:hidden name="viewBean" property="doc_name"/>

 </form>