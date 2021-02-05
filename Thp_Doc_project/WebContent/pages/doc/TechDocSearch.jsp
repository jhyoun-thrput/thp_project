<%--
/*
 * file name : TechDocSearch.jsp
 * title     : 技術文書検索画面
 * date      : 2012/06/12
 * author    : youn.j.h
 * revisions : 2012/06/12 - 新規作成 (revised by youn.j.h)
 */
--%>
<%-- ========================================================================================== ヘッダ定義--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/pages/common/header.jsp"%>
<%-- ========================================================================================== 変数宣言--%>
<c:set var="viewBean" value="${requestScope.viewBean}" />
<c:set var="techDocCodeList" value="${viewBean.techDocCodeList}" />
<c:set var="userIdList" value="${viewBean.userIdList}" />

<%-- ========================================================================================== javaScript && CSS 宣言--%>

<link rel='stylesheet' href='<html:rewrite page='/js/cal/calendar_ahmax.css'/>' type='text/css' />
<script type="text/javascript" src="<html:rewrite page='/js/hn/liveany-hn-util.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/cal/jquery-1.4.4.min.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/cal/jquery.mousewheel.min.js'/>"></script>
<script type="text/javascript" src="<html:rewrite page='/js/cal/calendar_ahmax.js'/>"></script>

<script language="javascript">
	/**
	 * 画面がonloadされたときの処理を行う。
	 * bodyタグのonloadの際、実行される。
	 */
	function init() {
			// 初期表示フォーカス
		document.frm.p_doc_name.focus();

		// create Cal
		initCal("from_date");
	}

	/**
	 * 技術文書検索
	 */
	function searchTechDoc() {
		document.frm.action = "<html:rewrite page='/doc/searchTechDoc.do'/>";
		document.frm.submit();
	}

	/**
	 * 指定したページ番号の情報を表示する。
	 * @param pageNo ページ番号
	 */
	function goPage(pageNo) {
		// 当画面の現在ページNo
		document.frm.pageNo.value = pageNo;
		document.frm.action = "<html:rewrite page='/doc/searchTechDoc.do'/>";
		document.frm.submit();
	}

	function afterSrchHistClick() {
		// 検索」処理を行う
		searchTechDoc();
	}

	function selDocDetailInfo() {

		var inParam = new Array();
		var outParam = new Array();

		var sel = getCheckedIndex(document.frm.select_index);

		if (!chkRequired(sel, "選択")) {
			return;
		}

		var doc_index = getValue(document.frm.doc_index_no, sel);
		var doc_code = getValue(document.frm.doc_code_no, sel);

		document.frm.doc_index.value = doc_index;
		document.frm.doc_code.value = doc_code;

		document.frm.action = "<html:rewrite page='/doc/detail/initTechDocDetail.do'/>";
		document.frm.submit();
	}

	function insertPopUpInit() {

		var inParam = new Array();
		var outParam = new Array();

		// 子画面を閉じる際のアクションを指定
		outParam["on_close_func"] = 'refresh';

		//openPopupModal(800, 600, 'doc_info_regi', '/doc/regi/initTechDocRegi.do', inParam, outParam, true);
		openPopup(800, 640, 'doc_info_regi', '/doc/regi/initTechDocRegi.do', inParam, outParam, true);
	}

	function dowunloadDocFile(sel_cnt) {

		var link_cnt = sel_cnt;
		var filepath = getValue(document.frm.hidden_doc_link, link_cnt);

		if (filepath == '') {
			alertErrMsg(filepath, "File Path is abnormal!!");
		}

		document.frm.downFilePath.value = filepath;
		document.frm.action = "<html:rewrite page='/doc/downloadDocFile.do'/>";
		document.frm.submit();
		comEndPB();
	}
	
	function dowunloadDocExcelFormat() {

		document.frm.action = "<html:rewrite page='/doc/downloadExcelFormat.do'/>";
		document.frm.submit();
		comEndPB();
	}
	
	/**
	 * 画面を再描画する
	 */
	function refresh() {
		document.frm.action = "<html:rewrite page='/doc/searchTechDoc.do'/>";
		document.frm.submit();
	}

	/**
	 * 検索条件のクリア
	 */
	function clearSearchCondition() {
		document.frm.p_doc_code.value = "";
		document.frm.p_doc_name.value = "";
		document.frm.p_regi_date_from.value = "";
		document.frm.p_doc_type.value = "";
		document.frm.p_regi_user.value = "";
		document.frm.p_doc_comment.value = "";
		//document.frm.p_regi_date_to.value = "";
	}

</script>
<%-- ========================================================================================== Body Start--%>
<form name="frm" method="post" action="<html:rewrite page='/doc/initTechDoc.do'/>" >
<div id="container"><%-- container Start --%>
<div id="content" class="heightLineParent"><%-- Contents Start --%>
<div class="w00"><liveany:title titleName="技術文書検索" isHelpVisible="false" isManualVisible="false" screenId="/doc/initTechDoc" />
	<liveany:title titleName="検索条件"	level="2" />
	<div align="right">
			<font color="blue">※検索の際に、or 検索は、[space] で、And 検索は [ Coma( , ) ]入力後、続いて検索語を入力してください。</font>
	</div>
<table class="content_i">
	<tr>
		<td width="80" class="td_f">文書コード</td>
		<td class="td_w" colspan="2">
			<logic:notEmpty name="viewBean" property="techDocCodeList">
				<html:select name="viewBean" property="p_doc_code" styleClass="input" >
					<html:options collection="techDocCodeList" property="value" labelProperty="label" />
				</html:select>
			</logic:notEmpty>
			<logic:empty name="viewBean" property="techDocCodeList">
				&nbsp;
			</logic:empty>
			<!--<liveany:text name="viewBean" property="p_doc_code" maxlength="4" size="6" tabindex="1" onEnterPress="javascript:searchTechDoc();"/>-->
		</td>
		<td width="80" class="td_f">文書名</td>
		<td class="td_w" colspan="5"><liveany:text name="viewBean" property="p_doc_name" inputType="ascii" size="80" tabindex="2" onEnterPress="javascript:searchTechDoc();"/></td>
		<td width="60" rowspan="2" class="td_w" align="center">
			<liveany:button id="srch" action="javascript:searchTechDoc();" type="blue" label="検索" tabindex="8" />
			<liveany:button id="clear" action="javascript:clearSearchCondition();" type="blue" label="クリア" tabindex="9" />
		</td>
	</tr>
	<tr>
		<td width="80" class="td_f">登録日付</td>
		<td class="td_w" colspan="2">
			<liveany:text name="viewBean" property="p_regi_date_from" size="16" style="border:1px solid #bebebe; background:#ffffff; height:18px; padding:2 2 0 2;" styleId="from_date" readonly="true" />
		</td>
			<!--
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_w">
						<liveany:text name="viewBean" property="p_regi_date_from" size="10" styleId="from_date" />
					</td>
					<td width="">&nbsp;&nbsp;&nbsp;～&nbsp;&nbsp;&nbsp;</td>
					<td>
						<liveany:text name="viewBean" property="p_regi_date_to" size="10" styleId="to_date" />
					</td>
				</tr>
			</table> -->
		<td width="80" class="td_f">文書種類</td>
		<td class="td_w"><liveany:text name="viewBean" property="p_doc_type" inputType="han_eiji" maxlength="8" size="8" tabindex="6" onEnterPress="javascript:searchTechDoc();"/></td>
		<td width="60" class="td_f">登録者</td>
		<td class="td_w">
			<logic:notEmpty name="viewBean" property="userIdList">
				<html:select name="viewBean" property="p_regi_user" styleClass="input" >
					<html:options collection="userIdList" property="value" labelProperty="label" />
				</html:select>
			</logic:notEmpty>
			<logic:empty name="viewBean" property="userIdList">
				&nbsp;
			</logic:empty>
			<!--<liveany:text name="viewBean" property="p_regi_user" inputType="han" maxlength="150" size="12" tabindex="3" onEnterPress="javascript:searchTechDoc();"/>-->
			</td>
		<td width="80" class="td_f">Comment</td>
		<td class="td_w">
			<liveany:text name="viewBean" property="p_doc_comment" inputType="ascii" size="20" tabindex="7" onEnterPress="javascript:searchTechDoc();"/>
		</td>
	</tr>
</table>

<liveany:title titleName="検索結果リスト" level="2" />
<div align="right">
	<table cellpadding="0" cellspacing="0">
		<tr>
			<logic:notEmpty name="viewBean" property="resultCnt">
				<td class="b" valign="bottom">
					Search Count :  <font color="red">${viewBean.resultCnt}</font>  row
				</td>
			</logic:notEmpty>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>
					<liveany:button id="excel_down" action="javascript:dowunloadDocExcelFormat();" type="blue_gray" label="Excel Download" dcCheck="true" />
				</td>
				<td>&nbsp;</td>
				<td>
					<liveany:button id="doc_insert" action="javascript:insertPopUpInit();" type="blue_gray" label="新規登録" dcCheck="true" />
				</td>
		</tr>
	</table>
</div>

<table class="list_table" width="100%" cellpadding="0" cellspacing="1">
	<tr>
		<td><%-- **********タイトル********** Start --%>
			<table width="100%" class="list_head" cellpadding="0" cellspacing="1">
				<tr>
					<td width="30" class="head" rowspan="2"><b>No</b></td>
					<td width="30" class="head" rowspan="2"><b>選択</b></td>
					<td width="450" class="head"><b>文書名</b></td>
					<td width="90" class="head"><b>登録者</b></td>
					<td width="90" class="head"><b>登録日時</b></td>
					<td width="60" class="head"><b>Ver</b></td>
				</tr>
					<td width="450" class="head" ><b>Document Link Path</b></td>
					<td width="90" class="head"><b>更新者</b></td>
					<td width="90" class="head"><b>更新日時</b></td>
					<td width="60" class="head"><b>文書種類</b></td>
				<tr>
				</tr>
			</table>
		<%-- **********タイトル*********** End --%></td>
	</tr>
	<tr>
		<td class="list_body"><%-- **********一覧内容********** Start --%>
		<div style="height: 355;" class="list_body_div">0000000
		<table class="list_body_table" width="100%" cellpadding="0" cellspacing="1">
			<logic:notEmpty	name="viewBean" property="techDocList">
				<c:forEach var="techList" items='${viewBean.techDocList}' varStatus="status">
					<tr class="<c:if test="${status.index % 2 == 0}">line0</c:if><c:if test="${status.index % 2 != 0}">line1</c:if>"
											<%-- TR 行の色 --%> onmouseover="this.style.backgroundColor='FDF2E3'" onmouseout="this.style.backgroundColor=''">
						<td>
							<table class="list_table_plu_line">
								<label for="techList_${status.index}">
									<tr>
										<td width="30" class="c" rowspan="2">
											<liveany:text property="doc_row" value="${techList.rownum}" style="text-align:center;width:30px;" readonly="true" />
											<html:hidden name="viewBean" property="doc_index_no" value="${techList.doc_index}"/>
											<html:hidden name="viewBean" property="doc_code_no" value="${techList.doc_code}"/>
										</td>
										<td width="30" class="c" rowspan="2">
											<html:radio name="viewBean" property="select_index" value="${status.index}" style="text-align:center;width:30px;" styleId="id_tech_doc_sel_${status.index}" onclick="javascript:selDocDetailInfo();"/>
										</td>
										<td>
											<%--<liveany:text property="techList_doc_name" value="${techList.doc_name}" style="text-align:left;width:400px;" styleClass="b" readonly="true" />  --%>
											<%--<a href="javascript:dowunloadDocFile('${techList.doc_link}');" target="_self"><b>${ techList.doc_name }</b></a> --%>
											<%--<b><liveany:button action="javascript:dowunloadDocFile('${status.index}');" type="link" dcCheck="false" label="${ techList.doc_name}" /></b>  --%>
											<a href="javascript:dowunloadDocFile('${status.index}');">
												<liveany:text property="techList_doc_name" value="${techList.doc_name}"  styleClass="b" style="text-align:left; width:485px; color: #333333; cursor:hand; text-decoration:underline" readonly="true"/>
											</a>
											<html:hidden name="viewBean" property="hidden_doc_link" value="${techList.doc_link}" />
										</td>
										<td align="center">
											<liveany:text property="techList_regi_user" value="${techList.regi_user}" style="text-align:center;width:90px;" readonly="true" />
										</td>
										<td align="center">
											<liveany:text property="techList_regi_date" value="${techList.regi_date}"  style="text-align:center;width:90px;" readonly="true" />
										</td>
										<td align="center">
											<liveany:text property="techList_doc_type" value="${techList.doc_type}" style="text-align:center;width:60px;" readonly="true"/>
										</td>
									</tr>
									<tr>
										<td>
											<html:hidden name="viewBean" property="doc_link" value="${techList.doc_link}"/>
											<liveany:text property="techlist_doc_link" value="${techList.doc_link}" style="text-align:left;width:485px;" readonly="true" />
										</td>
										<td align="center">
											<liveany:text property="techList_upt_user" value="${techList.upt_user}" styleClass="b" style="text-align:center;width:90px;" readonly="true" />
										</td>
										<td align="center">
											<liveany:text property="techList_upt_date" value="${techList.upt_date}" styleClass="b" style="text-align:center;width:90px;" readonly="true" />
										</td>
										<td align="center">
											<liveany:text property="techList_doc_ver" value="${techList.doc_ver}" styleClass="b" style="text-align:center;width:50px;" readonly="true" />
										</td>
									</tr>
								</label>
							</table>
						</td>
					</tr>
				</c:forEach>
			</logic:notEmpty>
			<logic:empty	name="viewBean" property="techDocList">
				<tr class="line1" onmouseover="this.style.backgroundColor='FDF2E3'" onmouseout="this.style.backgroundColor=''">
					<td align="center">
						該当データがありません。再度、条件を絞り込んでください。
					</td>
				</tr>
			</logic:empty>
		</table>
		</div>
		<%-- **********一覧内容********** End --%></td>
	</tr>
	<%-- ページング定義 --%>
	<tr align="center">
	<logic:notEmpty name="viewBean" property="techDocListPageInfo">
		<td><liveany:page pageNo="${viewBean.techDocListPageInfo.pageNo}" listNo="${viewBean.techDocListPageInfo.listRowCnt}"
														totalCnt="${viewBean.techDocListPageInfo.totalCnt}" noPerIdx="10" funcNm="goPage" />
		</td>
	</logic:notEmpty>
	</tr>
<%--
	<tr>
		<td>
			<table align="right">
				<tr>
					<logic:notEmpty name="viewBean" property="techDocList">
						<td class="right">
							<liveany:button id="doc_cnrm" action="javascript:selDocDetailInfo();" type="blue_gray" label="文書詳細" dcCheck="true" />
						</td>
					</logic:notEmpty>

					<td class="td_w">
						<liveany:button id="doc_insert" action="javascript:insertPopUpInit();" type="blue_gray" label="新規登録" dcCheck="true" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
 --%>
</table>
</div>
</div>


<%-- Contents End --%></div>
<%-- container End --%>

 <%-- ==================== Hidden Value Area Start ==================== --%>

<html:hidden name="viewBean" property="doc_index"/>
<html:hidden name="viewBean" property="doc_code"/>
<html:hidden name="viewBean" property="downFilePath"/>

<%-- ==================== Hidden Value Area End ==================== --%>

</form>
<%-- ========================================================================================== Body End--%>
