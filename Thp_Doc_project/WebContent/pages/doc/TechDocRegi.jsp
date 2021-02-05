<%-- ========================================================================================== Define --%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/pages/common/header.jsp"%>
<%-- ========================================================================================== 変数宣言--%>
<c:set var="viewBean" value="${requestScope.viewBean}" />
<c:set var="techDocCodeList" value="${viewBean.techDocCodeList}" />
<%-- ========================================================================================== javaScrip && CSS 宣言--%>

<script language="javascript">

	/**
	 * 画面がonloadされたときの処理を行う。
	 * bodyタグのonloadの際、実行される。
	 */
	function init(){
	}

	/**
	 * Insert
	 */
	function insertTechDocInfo(){

		if (!confirm('登録しますか？')) {
			return;
		}
		document.frm.action = "<html:rewrite page='/doc/regi/insertDocRegi.do'/>";
		document.frm.submit();
	}

	/**
	 * DocIndex
	 */
	function getCurrentDocIndex(){
		document.frm.action = "<html:rewrite page='/doc/regi/getDocInfoIndex.do'/>";
		document.frm.submit();
	}

	/**
	 * DocIndex
	 */
	function clearFile(){
		document.frm.action = "<html:rewrite page='/doc/regi/clearFile.do'/>";
		document.frm.submit();
	}

</script>

<%-- ========================================================================================== Body--%>
<form name="frm"  method="post" action="<html:rewrite page='/doc/initTechDocRegi.do'/>" enctype="multipart/form-data">

	<%-- *** 2度押しTokenチェック *** --%>
	<liveany:token/>

	<div id="container" >
		<div id="content" class="heightLineParent">
			<%-- Contents開始 --%>
			<div class="w00">

				<liveany:title titleName="技術文書登録"/>
				<liveany:title titleName="登録詳細" level="2"/>

				<table class="content_i">
					<tr>
						<td width="70" class="td_f"><font color="red">*</font>文書分類</td>
						<td class="td_w" style="text-align:left;width:120px;">
							<logic:notEmpty name="viewBean" property="techDocCodeList">
								<html:select styleId="id_doc_code" name="viewBean" property="doc_code" styleClass="input" >
									<html:options collection="techDocCodeList" property="value" labelProperty="label" />
								</html:select>
							</logic:notEmpty>
							<logic:empty name="viewBean" property="techDocCodeList">
								&nbsp;
							</logic:empty>
						</td>
						<td width="70" class="td_f">文書名</td>
						<td class="td_w" style="text-align:left;width:430px;" colspan="5">
							<b>${ viewBean.doc_name }</b>
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
						<td class="td_w" width="80">
							${ viewBean.regi_user }
						</td>
						<td width="80" class="td_f">登録日</td>
						<td class="td_w" style="text-align:left;width:110px;">
							${ viewBean.regi_date }
						</td>
					</tr>
				</table>
				<table class="content_i">
				<!--
					<tr>
						<td width="80px" class="td_f"><font color="red">*</font>添付</td>
						<td width="730px" class="td_w">
							<html:file styleClass="input" property="doc_file_path" value="" size="70" tabindex="2"/>
						</td>
					</tr>
					 -->
					<tr>
						<td colspan="2" class="td_w">
							<liveany:textarea name="viewBean" property="comment" style="width:100%;height:80px;background-color: #DFF9EA; font-family:'MS Gothic'" tabindex="16" />
						</td>
					</tr>
				</table>
				<div>
<%-- ========================================================================================== ボタン部--%>
				<table align="right">
					<tr>
						<td align="right">
							<liveany:button id="update" action="javascript:insertTechDocInfo();" type="blue_gray" label="登録" tabindex="17"/>
						</td>
						<td align="right">
							<liveany:button id="close" action="javascript:window.close();" type="blue_gray" label="閉じる"/>
						</td>
					</tr>
				</table>
			</div><%-- w00-1cnt END --%>
        </div><%-- Contents開始 --%>
  </div><%-- container開始 --%>

  <html:hidden name="viewBean" property="doc_code" />
  <html:hidden name="viewBean" property="doc_link" />
  <html:hidden name="viewBean" property="upload_cnt" />


 </form>

<div style="height :200px;">
 <%@ include file="/pages/drag/file_upload_page.jsp" %>
 </div>
