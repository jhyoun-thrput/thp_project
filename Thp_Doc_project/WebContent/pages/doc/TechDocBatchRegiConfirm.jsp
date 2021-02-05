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
<form name="frm"  method="post" action="<html:rewrite page='/doc/batchRegi/initBatchRegi.do'/>" >
	<div id="container" style="width:500px;">
		<div id="content" class="heightLineParent">
			<%-- Contents開始 --%>
			<div class="w00">

				<liveany:title titleName="バッチ内容確認"/>
				<liveany:title titleName="内容確認" level="2"/>
				<table class="content_i">
					<tr>
						<td width="80" class="td_f"><font color="red">*</font>Batch ID</td>
						<td class="td_w" >
							<liveany:text styleId="id_bt_id" name="viewBean" property="bt_id" maxlength="12" size="20" styleClass="b" tabindex="2" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">Batch 区分</td>
						<td class="td_w"  width="100">
							<c:if test="${viewBean.bt_flg == '1'}">
								<font color="blue"><b>文書システム</b></font>
							</c:if>
							<c:if test="${viewBean.bt_flg == '2'}">
								<font color="red"><b>一般システム</b></font>
							</c:if>
							<c:if test="${viewBean.bt_flg == '3'}">
								<b>その他</b>
							</c:if>
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f">Batch Name</td>
						<td class="td_w">
							<liveany:text styleId="id_bt_name" name="viewBean" property="bt_name" maxlength="100" size="70" inputType="ascii" tabindex="3" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td width="80" class="td_f"><font color="red">*</font>Batch Param</td>
						<td class="td_w">
							<liveany:text styleId="id_bt_param" name="viewBean" property="bt_param" maxlength="100" size="70" inputType="ascii" tabindex="4" readonly="true"/>
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