<%-- ========================================================================================== Define --%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/pages/common/header.jsp"%>
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

	function returnDocSeach() {
		document.frm.action = "<html:rewrite page='/doc/returnToDocDetail.do'/>";
		document.frm.submit();
	}

	function dowunloadDocFile(link) {

		var filepath = link;

		if (filepath == '') {
			alertErrMsg(filepath, "File Path is abnormal!!");
		}

		document.frm.doc_hist_link.value = "";
		document.frm.doc_link.value = filepath;
		document.frm.action = "<html:rewrite page='/doc/detail/downloadDocFile.do'/>";
		document.frm.submit();
		comEndPB();
	}

	function downloadHistDocFile(sel_cnt) {

		var link_cnt = sel_cnt;
		var filepath = getValue(document.frm.doc_links, link_cnt);

		if (filepath == '') {
			alertErrMsg(filepath, "File Path is abnormal!!");
		}

		document.frm.doc_link.value = "";
		document.frm.doc_hist_link.value = filepath;
		document.frm.action = "<html:rewrite page='/doc/detail/downloadDocFile.do'/>";
		document.frm.submit();
		comEndPB();
	}

	/**
	 * 更新
	 */
	function updateTechDocInfo(){

		if (!confirm('更新しますか？')) {
			return;
		}

		document.frm.action = "<html:rewrite page='/doc/detail/updateDocDetail.do'/>";
		document.frm.submit();
	}

	/**
	 * 削除
	 */
	function deleteTechDocInfo(){

		if (!confirm('削除しますか？')) {
			return;
		}

		document.frm.action = "<html:rewrite page='/doc/detail/deleteDocDetail.do'/>";
		document.frm.submit();
	}

</script>
<%-- ========================================================================================== Body--%>
<form name="frm"  method="post" action="<html:rewrite page='/doc/initTechDocDetail.do'/>" enctype="multipart/form-data">

	<%-- *** 2度押しTokenチェック *** --%>
	<liveany:token/>
	<div id="container" >
		<div id="content" class="heightLineParent">
			<%-- Contents開始 --%>
			<div class="w00">

				<liveany:title titleName="技術文書詳細"/>
				<liveany:title titleName="詳細内容" level="2"/>
				<table class="content_i">
					<tr>
						<td width="70" class="td_f">文書分類名</td>
						<td width="120" class="td_w">
							<%--${viewBean.doc_code_name} --%>
							<liveany:text name="viewBean" property="doc_code_name" style="text-align:left;width:120px;" readonly="true" />
						</td>
						<td width="70" class="td_f">文書番号</td>
						<td width="50" class="td_w">
							<liveany:text name="viewBean" property="doc_index" maxlength="10" size="12" inputType="han_suji" tabindex="2" readonly="true"/>
						</td>
						<td width="70" class="td_f">文書名</td>
						<td class="td_w" style="text-align:left;width:430px;" colspan="3">
							<b><liveany:button action="javascript:dowunloadDocFile(document.frm.doc_link_value.value);" type="link" dcCheck="false" label="${ viewBean.doc_name }"/></b>
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">文書種類</td>
						<td class="td_w">
							${ viewBean.doc_type }
						</td>
						<td width="80" class="td_f"><font color="red">*</font>文書 Ver.</td>
						<td class="td_w">
							<liveany:text name="viewBean" property="doc_ver" maxlength="8" size="10" inputType="han" tabindex="2" styleClass="b" />
							<!-- ${ viewBean.doc_ver }  -->
						</td>
						<td width="80" class="td_f">登録者</td>
						<td class="td_w" width="60">
							${ viewBean.regi_user }
						</td>
						<td width="80" class="td_f">登録日</td>
						<td class="td_w" style="text-align:left;width:120px;">
							<liveany:dateText name="viewBean" property="regi_date" formName="frm" format="yyyy_mm_dd" maxlength="20" size="22" tabindex="11" readonly="true"/>
						</td>
					</tr>
				</table>
				<table class="content_i">
					<tr>
						<td width="80px" class="td_f"><font color="red">*</font>添付</td>
						<td width="730px" class="td_w">
							<html:file styleClass="input" property="doc_file_path" value="" size="70" tabindex="2"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="td_w">
							<liveany:textarea name="viewBean" property="comment" style="width:100%;height:150px;background-color: #DFF9EA; font-family:'MS Gothic'" tabindex="16" />
						</td>
					</tr>
				</table>
				<liveany:title titleName="文書履歴情報" level="2"/>
	<table class="list_table" width="100%" cellpadding="0" cellspacing="1">
	<tr>
		<td><%-- **********タイトル********** Start --%>
			<table width="100%" class="list_head" cellpadding="0" cellspacing="1">
				<tr>
					<td width="40" class="head" rowspan="2"><b>SEQ</b></td>
					<!-- <td width="40" class="head">コード</td>  -->
					<td width="430"  class="head"><b>文書名</b></td>
					<td width="90" class="head"><b>更新者</b></td>
					<td width="120" class="head"><b>更新日時</b></td>
					<td width="60" class="head"><b>文書種類</b></td>
					<td width="70" class="head"><b>文書  Ver.</b></td>
				</tr>
					<td class="head" colspan="5"><b>Document Link Path</b></td>
				<tr>
				</tr>
			</table>
		<%-- **********タイトル*********** End --%></td>
	</tr>
	<tr>
		<td class="list_body"><%-- **********一覧内容********** Start --%>
		<div style="height: 180;" class="list_body_div">
		<table class="list_body_table" width="100%" cellpadding="0" cellspacing="1">
			<logic:notEmpty	name="viewBean" property="docHistlList">
				<c:forEach var="histList" items='${viewBean.docHistlList}' varStatus="status">
					<tr class="<c:if test="${status.index % 2 == 0}">line0</c:if><c:if test="${status.index % 2 != 0}">line1</c:if>"
											<%-- TR 行の色 --%> onmouseover="this.style.backgroundColor='FDF2E3'" onmouseout="this.style.backgroundColor=''">
						<td>
							<table class="list_table_plu_line">
								<label for="techList_${status.index}">
									<tr>
										<td style="text-align:center;width:40px; class="c" rowspan="2">
											${histList.rownum}
										</td>
										<td style="text-align:left;width:430px;">
											<b><liveany:button action="javascript:downloadHistDocFile('${status.index}');" type="link"  dcCheck="true" label="${ histList.doc_name }"/></b>
											<html:hidden name="viewBean" property="doc_links" value="${histList.doc_link}"/>
										</td>
										<td style="text-align:center;width:90px;">
											<!--<liveany:text property="techList_regi_user" value="${techList.regi_user}" style="text-align:center;" readonly="true" />-->
											${histList.upt_user}
										</td>
										<td style="text-align:center;width:120px;">
											${histList.upt_date}
											<!--<liveany:dateText property="techList_regi_date" value="${techList.regi_date}" style="width:100%" listIndex="${status.index}" formName="frm" format="yyyy_mm_dd " useButton="false" readonly="true" />-->
										</td>
										<td style="text-align:center;width:60px;">
											${histList.doc_type}
											<!--<liveany:text property="techList_doc_type" value="${techList.doc_type}" style="text-align:center;" readonly="true"/>-->
										</td>
										<td style="text-align:center;width:70px;">
											<b>${histList.doc_ver}</b>
												<!--<liveany:text property="techList_doc_ver" value="${techList.doc_ver}" style="text-align:center;" readonly="true" />-->
											</td>
										</tr>
										<tr>
											<td colspan="5">
												${histList.doc_link}
											</td>
										</tr>
									</label>
								</table>
							</td>
						</tr>
					</c:forEach>
				</logic:notEmpty>
				<logic:empty	name="viewBean" property="docHistlList">
					<tr class="line1" onmouseover="this.style.backgroundColor='FDF2E3'" onmouseout="this.style.backgroundColor=''">
						<td align="center">
							履歴情報がありません。
						</td>
					</tr>
				</logic:empty>
			</table>
			</div>
			<%-- **********一覧内容********** End --%></td>
		</tr>
		<%-- ページング定義 --%>
		<tr align="center">
			<td><liveany:page pageNo="${viewBean.docHistPageInfo.pageNo}" listNo="${viewBean.docHistPageInfo.listRowCnt}"
															totalCnt="${viewBean.docHistPageInfo.totalCnt}" noPerIdx="10" funcNm="goPage" />
			</td>
		</tr>
	</table>

<%-- ========================================================================================== ボタン部--%>
	<table align="right">
		<tr>
		<logic:equal name="viewBean" property="auth_flg" value="1">
			<td align="right">
				<liveany:button id="update" action="javascript:deleteTechDocInfo();" type="blue_gray" label="削除" tabindex="17"/>
			</td>
		</logic:equal>
			<td align="right">
				<liveany:button id="update" action="javascript:updateTechDocInfo();" type="blue_gray" label="更新" tabindex="17"/>
			</td>
			<td align="right">
				<liveany:button id="close" action="javascript:returnDocSeach();" type="blue_gray" label="文書検索へ"/>
			</td>
		</tr>
	</table>
			</div><%-- w00-1cnt END --%>
        </div><%-- Contents開始 --%>
  </div><%-- container開始 --%>

  <html:hidden name="viewBean" property="doc_code"/>
  <html:hidden name="viewBean" property="doc_link"/>
  <html:hidden name="viewBean" property="doc_hist_link"/>
  <html:hidden name="viewBean" property="doc_link_value"/>

 </form>