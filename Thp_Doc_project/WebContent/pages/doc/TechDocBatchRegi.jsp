<%-- ========================================================================================== Define --%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/pages/common/header.jsp"%>
<script type="text/javascript" src="<html:rewrite page='/js/ho/liveany-ho-ajax.js'/>"></script>
<%-- ========================================================================================== 変数宣言--%>
<c:set var="viewBean" value="${requestScope.viewBean}" />
<c:set var="btFlgList" value="${viewBean.batchFlgList}" />
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
	function insertBatch(){
		if (!confirm('登録しますか？')) {
			return;
		}
		document.frm.action = "<html:rewrite page='/doc/batchRegi/insertBatch.do'/>";
		document.frm.submit();
	}

	/**
	 * Update
	 */
	function updateBatch(){
		if (!confirm('更新しますか？')) {
			return;
		}
		document.frm.action = "<html:rewrite page='/doc/batchRegi/updateBatch.do'/>";
		document.frm.submit();
	}

	/**
	 * DocIndex
	 */
	function getCurrentDocIndex(){
		document.frm.action = "<html:rewrite page='/doc/regi/getDocInfoIndex.do'/>";
		//document.frm.submit();
	}

	function setBt_flg() {


	}

</script>

<%-- ========================================================================================== Body--%>
<form name="frm"  method="post" action="<html:rewrite page='/doc/batchRegi/initBatchRegi.do'/>" >

	<%-- *** 2度押しTokenチェック *** --%>
	<liveany:token/>

	<div id="container" style="width:500px;">
		<div id="content" class="heightLineParent">
			<%-- Contents開始 --%>
			<div class="w00">

				<liveany:title titleName="バッチ登録"/>
				<liveany:title titleName="入力項目" level="2"/>
				<table class="content_i">
					<tr>
						<td width="80" class="td_f"><font color="red">*</font>Batch ID</td>
						<td class="td_w" >
							<logic:empty name="viewBean" property="p_bt_id">
								<liveany:text styleId="id_bt_id" name="viewBean" property="bt_id" maxlength="12" size="20" styleClass="b" inputType="han_eisu" tabindex="2"/>
							</logic:empty>
							<logic:notEmpty name="viewBean" property="p_bt_id">
								<liveany:text styleId="id_bt_id" name="viewBean" property="bt_id" maxlength="12" size="20" styleClass="b" tabindex="2" readonly="true"/>
							</logic:notEmpty>
						</td>
					</tr>
					<tr>
							<td width="80" class="td_f">Batch 区分</td>
							<td class="td_w">
							<logic:notEmpty name="viewBean" property="batchFlgList">
								<html:select name="viewBean" property="bt_flg" styleClass="input" >
									<html:options collection="btFlgList" property="value" labelProperty="label" />
								</html:select>
							</logic:notEmpty>
							<logic:empty name="viewBean" property="batchFlgList">
								&nbsp;
							</logic:empty>
							</td>
					</tr>
					<tr>
						<td width="80" class="td_f">Batch Name</td>
						<td class="td_w">
							<liveany:text styleId="id_bt_name" name="viewBean" property="bt_name" maxlength="100" size="70" inputType="ascii" tabindex="3"/>
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f"><font color="red">*</font>Batch Param</td>
						<td class="td_w">
							<liveany:text styleId="id_bt_param" name="viewBean" property="bt_param" maxlength="100" size="70" inputType="ascii" tabindex="4"/>
							<br/>
									<font color="blue">※文書管理バッチ： Directory Path Only</font><br/>
									<font color="red">※一般又は、その他バッチ： Batch File Path Only</font>
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
						<logic:empty name="viewBean" property="p_bt_id">
							<liveany:button id="insert" action="javascript:insertBatch();" type="blue_gray" label="登録" tabindex="17"/>
						</logic:empty>
						<logic:notEmpty name="viewBean" property="p_bt_id">
							<liveany:button id="update" action="javascript:updateBatch();" type="blue_gray" label="更新" tabindex="17"/>
						</logic:notEmpty>
						</td>
						<td align="right">
							<liveany:button id="close" action="javascript:window.close();" type="blue_gray" label="閉じる"/>
						</td>
					</tr>
				</table>
			</div><%-- w00-1cnt END --%>
        </div><%-- Contents開始 --%>
  </div><%-- container開始 --%>

 </form>