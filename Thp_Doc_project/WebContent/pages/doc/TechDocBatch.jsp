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
<c:set var="batchDto" value="${viewBean.batchDto}" />
<%-- ========================================================================================== javaScript && CSS 宣言--%>
<script language="javascript">

	/**
	 * 画面がonloadされたときの処理を行う。
	 * bodyタグのonloadの際、実行される。
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
	 * 詳細照会
	 */
	function showDetail() {

  		// 選択された行のラジオの値を取得する。
		var checkedIndex = getCheckedIndex(frm.btList_chk);

		// 指定した配列のindex番目の値を取得する。
		frm.selected_bt_no.value = getValue(frm.btList_btNo, checkedIndex);
		frm.selected_bt_id.value = getValue(frm.bt_id, checkedIndex);

		document.frm.action = "<html:rewrite page='/doc/batch/searchBatchInfo.do'/>";
		document.frm.submit();
	}

	/**
	 * 詳細照会
	 */
	function showBtachHistory() {

  		// 選択された行のラジオの値を取得する。
		var checkedIndex = getCheckedIndex(frm.bt_m_sel);

		// 指定した配列のindex番目の値を取得する。
		frm.bt_m_bt_id.value = getValue(frm.param_bt_id, checkedIndex);
		frm.doc_file_path.value = getValue(frm.param_bt_param, checkedIndex);

		document.frm.action = "<html:rewrite page='/doc/batch/initBatch.do'/>";
		document.frm.submit();
	}

	/**
	 * 実行
	 */
	function exeBatch() {

		if (!confirm('実行しますか?')) {
			return;
		}

  		// 選択された行のラジオの値を取得する。
		var checkedIndex = getCheckedIndex(frm.bt_m_sel);

		if (!chkRequired(checkedIndex, "選択")) {
			return;
		}

		frm.bt_m_bt_id.value = getValue(frm.param_bt_id, checkedIndex);
		frm.doc_file_path.value = getValue(frm.param_bt_param, checkedIndex);
		frm.selectedBt_flg.value = getValue(frm.param_bt_flg, checkedIndex);

		document.frm.action = "<html:rewrite page='/doc/batch/executeBatch.do'/>";
		document.frm.submit();

		showProgress("P");
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
		document.frm.action = "<html:rewrite page='/doc/batch/initBatch.do'/>";
	    document.frm.submit();
	}

	/**
	 * 検索条件のクリア
	 */
	function clear() {
		document.frm.hndg_dt_start.value = "";
		document.frm.hndg_dt_end.value = "";
		document.frm.sno.value = "";
	}

	/**
	 * 検索条件のクリア
	 */
	function doDownload(fileCl,fileName) {
		document.frm.dwn_file_cl.value = fileCl;
		document.frm.dwn_file_nm.value = fileName;
		document.frm.action = "<html:rewrite page='/ho/cm/ExeBatch/downloadFile.do'/>";
		document.frm.submit();
	}


	/**
	 * 処理終了監視を行う。
	 */
	var interval = null;
	function refreshStatus() {

		// ----- 一覧からバッチが選択されているかチェック
		if (isEmpty(frm.dtl_bt_status.value)) {
			alert("バッチを選択して下さい。");
			frm.btStatsRefresh.checked = false;
			return;
		}

		// ----- バッチが終了しているかチェック
		if (frm.dtl_bt_status.value == 'F' || frm.dtl_bt_status.value == 'S') {
			alert("選択したバッチは既に終了しています。");
			//if (interval != null) {
			//	interval = null;
			//}
			frm.btStatsRefresh.checked = false;
			return;
		} else if (frm.dtl_bt_status.value == 'W') {
			showProgress(frm.dtl_sttd.value);
		} else if (frm.dtl_bt_status.value == 'P') {
			showProgress(frm.dtl_bt_status.value);
		}

		// ----- 監視処理起動
		if(frm.btStatsRefresh.checked) {
			if(interval != null) {
				window.clearInterval(interval);
			}
			interval = window.setInterval("getBtDetailInfoAjax()", 5 * 1000);
		} else {
			offProgress();
			window.clearInterval(interval);
		}
	}

	/**
	 * Ajaxでバッチ終了を照会する。
	 */
	function getBtDetailInfoAjax() {

		// ----- 監視処理起動
		if(!frm.btStatsRefresh.checked) {
			offProgress();
			window.clearInterval(interval);
			return;
		}

		// sql実行するためのパラメータ
		var param = paramMap();
		param.put("bt_id", frm.dtl_bt_id.value);
		param.put("bt_no", frm.dtl_bt_no.value);

		// callback関数に渡すパラメータ
		var callBackParam = paramMap();

		refAjaxJson(getBtDetailInfoAjaxCallBack, "doc.searchBatchAjax", param, callBackParam);
	}

	/**
	 * getBtDetailInfoAjaxのCallBack
	 */
	function getBtDetailInfoAjaxCallBack(responseText, callBackParam) {

		if (isZero(responseText)) {
			if (null != callBackParam) {
				while (callBackParam.hasNext()) {
					callBackParam.next();
					setOutputValue(callBackParam.getValue(), "");
				}
			}
			return;
		} else {
			try {
				json = new JSONUtil(responseText).getJSON();
				if (null != callBackParam) {

					var btStatus = clearNull(eval("json.stt_cl"));
					if (btStatus == 'S') {
					// 処理成功
						offProgress();
						frm.btStatsRefresh.checked = false;
						alert(" ○ 正常終了\n--------------\n" + clearNull(eval("json.msg")));
						document.frm.action = "<html:rewrite page='/ho/cm/ExeBatch/refreshBatchInfo.do'/>";
						document.frm.submit();
					} else if (btStatus == 'F') {
					// 処理失敗
						offProgress();
						frm.btStatsRefresh.checked = false;
						alert(" × 処理失敗\n---------------\n" + clearNull(eval("json.msg")));
						document.frm.action = "<html:rewrite page='/ho/cm/ExeBatch/refreshBatchInfo.do'/>";
						document.frm.submit();
					} else if (btStatus == 'P' && frm.dtl_sttd.value == 'W') {
					// 「待機中」から「実行中」にステータスが変わった時
						var hndg_bg_dttm = clearNull(eval("json.hndg_bg_dttm"));
						frm.dtl_hndg_bg_dttm.value = conv2Wareki(hndg_bg_dttm.substr(0, 8)) + " " + hndg_bg_dttm.substr(8, 2) + ":" + hndg_bg_dttm.substr(10, 2) + ":" +  hndg_bg_dttm.substr(12);
						document.getElementById('status_td').innerHTML = "<b>実行中</b>";
						showProgress(btStatus);
					} else if (btStatus == 'W') {
					// 待機中
						showProgress(btStatus);
					}

					frm.dtl_sttd.value = btStatus;

					//alert(clearNull(eval("json.stt_cl")));
					//setOutputValue(, );
				}
			} catch (e) {
				alert("情報取得に失敗しました。");
				frm.btStatsRefresh.checked = false;
				return;
			}
		}
	}

	/**
	 * バッチ進行メッセージ表示
	 */
	function showProgress(status) {
		var obj = document.getElementById('divBtProcessing');
		if (obj != null) {
			var _x = 350;
			var _y = 180;
			obj.style.left = _x;
			obj.style.top = _y;
			obj.style.display = "block";
		}

		if (status == "W") {
			obj.innerHTML = "待機中";
		} else if (status == "P") {
			obj.innerHTML = "<font color='blue'>実行中</font>";
		} else if (status == "S") {
			obj.innerHTML = "<font color='blue'>成功</font>";
		} else if (status == "F") {
			obj.innerHTML = "<font color='red'>失敗</font>";
		}
		comShowPB();
	}

	/**
	 * バッチ進行メッセージの非表示
	 */
	function offProgress() {
		var obj = document.getElementById('divBtProcessing');
		if (obj != null) {
			obj.style.display = "none";
		}

		var p = parent;
		var obj = null;
		for (var i = 0; i < 3; i++) {
			obj = p.document.getElementById('divBtProcessing');
			if (obj != null) {
				obj.style.display = "none";
			}
			p = p.parent;
		}
		comEndPB();
	}

	/**
	 * 運用ログダウンロード
	 */
	function downloadLogFile() {
		document.frm.action = "<html:rewrite page='/ho/cm/ExeBatch/downloadLogFile.do'/>";
		document.frm.submit();
	}

	function insertBatchPopUp() {

		var inParam = new Array();
		var outParam = new Array();

		// 子画面を閉じる際のアクションを指定
		outParam["on_close_func"] = 'refresh';

		openPopupModal(550, 300, 'doc_batch_regi', '/doc/batchRegi/initBatchRegi.do', inParam, outParam, true);
	}

	function updateBatchPopUp() {

		var inParam = new Array();
		var outParam = new Array();

  		// 選択された行のラジオの値を取得する。
		var checkedIndex = getCheckedIndex(frm.bt_m_sel);

		if (!chkRequired(checkedIndex, "選択")) {
			return;
		}

		inParam["p_bt_id"] = getValue(frm.param_bt_id, checkedIndex);

		// 子画面を閉じる際のアクションを指定
		outParam["on_close_func"] = 'refresh';

		openPopupModal(550, 300, 'doc_batch_update', '/doc/batchRegi/initBatchRegi.do', inParam, outParam, true);
	}

	function deleteBatchMaster() {
		if (!confirm('削除しますか?')) {
			return;
		}

  		// 選択された行のラジオの値を取得する。
		var checkedIndex = getCheckedIndex(frm.bt_m_sel);

		if (!chkRequired(checkedIndex, "選択")) {
			return;
		}

		frm.bt_m_bt_id.value = getValue(frm.param_bt_id, checkedIndex);

		document.frm.action = "<html:rewrite page='/doc/batch/deleteBatchMaster.do'/>";
		document.frm.submit();
	}

	function refresh() {
		document.frm.action = "<html:rewrite page='/doc/batch/initMasterBatch.do'/>";
		document.frm.submit();
	}

</script>
<%-- ========================================================================================== Body Start--%>
<form name="frm"  method="post" enctype="multipart/form-data" action="<html:rewrite page='/doc/batch/initBatch.do'/>">

	<%-- *** 2度押しTokenチェック *** --%>
<liveany:token/>
<div id="container"><%-- container Start --%>
<div id="content" class="heightLineParent"><%-- Contents Start --%>
	<liveany:title titleName="バッチ管理画面" isHelpVisible="false" isManualVisible="false" screenId="/doc/batch/initBatch" />
	<liveany:title titleName="バッチ一覧" level="2"/>
	<table class="list_table" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<%-- **********タイトル********** Start --%>
				<table width="100%" class="list_head" cellpadding="0" cellspacing="1">
					<tr>
						<td width="25" class="head">選択</td>
						<td width="80" class="head">Batch ID</td>
						<td width="100" class="head">Batch 区分</td>
						<td width="180" class="head">Batch Name</td>
						<%--<td width="200" class="head">Batch Link</td> --%>
						<td width="200" class="head">Batch Param</td>
						<td width="110" class="head">登録日時</td>
						<td class="head">登録者</td>
					</tr>
				</table>
				<%-- **********タイトル*********** End --%>
			</td>
		</tr>
		<tr>
			<td class="list_body">
				<%-- **********一覧内容********** Start --%>
				<div style="height: 130;" class="list_body_div">
					<table class="list_body_table" width="100%" cellpadding="0" cellspacing="1" >
						<c:forEach var="mBtList" items='${viewBean.masterBtList}'  varStatus="status" >
							<tr class="<c:if test="${status.index % 2 == 0}">line0</c:if><c:if test="${status.index % 2 != 0}">line1</c:if>" 	<%-- TR 行の色 --%>
								onmouseover="this.style.backgroundColor='FDF2E3'" onmouseout="this.style.backgroundColor=''"> 					<%-- TR マウス制御 --%>
								<label for="id_mBtList_chk_${status.index}">
									<td width="25">
										<html:radio name="viewBean" property="bt_m_sel" value="${status.index}" styleId="id_bt_m_sel_${status.index}" style="width:100%" onclick="javascript:showBtachHistory();" tabindex="5"/>
										<a id="idx_${status.index}" name="idx_${status.index}"/>
										<html:hidden name="viewBean" property="param_bt_id" value="${mBtList.bt_id}" />
									</td>
									<td width="80"><liveany:text property="mBtList_bt_id" value="${mBtList.bt_id}" style="width:100%" readonly="true"/></td>
									<td width="100" align="center">
										<c:if test="${mBtList.bt_flg == '1'}">
											<font color="blue"><b>文書システム</b></font>
										</c:if>
										<c:if test="${mBtList.bt_flg == '2'}">
											<font color="red"><b>一般システム</b></font>
										</c:if>
										<c:if test="${mBtList.bt_flg == '3'}">
											<b>その他</b>
										</c:if>
											<html:hidden name="viewBean" property="param_bt_flg" value="${mBtList.bt_flg }"/>
									</td>
									<td width="180"><liveany:text property="mBtList_bt_name" value="${mBtList.bt_name}"  size="35" readonly="true"/></td>
									<%--<td width="200"><liveany:text property="mBtList_bt_link" value="${mBtList.bt_link}" style="width:100%" readonly="true"/></td> --%>
									<td width="200">
										<liveany:text property="mBtList_bt_param" value="${mBtList.bt_param}" size="38" readonly="true"/>
											<html:hidden name="viewBean" property="param_bt_param" value="${mBtList.bt_param}" />
									</td>
									<td width="110"><liveany:text property="mBtList_regi_date" value="${mBtList.regi_date}" style="width:100%" readonly="true"/></td>
									<td><liveany:text property="mBtList_regi_user" value="${mBtList.regi_user}" style="width:100%" readonly="true"/></td>
								</label>
							</tr>
						</c:forEach>
					</table>
				</div>
				<%-- **********一覧内容********** End --%>
			</td>
		</tr>
	</table>
	<div align="right">
		<table align="right">
			<tr>
				<td class="right">
					<liveany:button id="bt_insert" action="javascript:insertBatchPopUp();" type="blue_gray" label="バッチ登録" dcCheck="true"/>
				</td>
			<logic:notEmpty name="viewBean" property="masterBtList">
				<td class="right">
					<liveany:button id="bt_insert" action="javascript:updateBatchPopUp();" type="blue_gray" label="バッチ更新" dcCheck="true"/>
				</td>
				<td class="right">
					<liveany:button id="bt_insert" action="javascript:deleteBatchMaster();" type="blue_gray" label="バッチ削除" dcCheck="true"/>
				</td>
			</logic:notEmpty>
				<td class="td_w" valign="bottom">
					<liveany:button id="bt_execute" action="javascript:exeBatch();" type="blue" label="バッチ実行" tabindex="8"/>
				</td>
			</tr>
		</table>
	</div>

<logic:notEmpty name="viewBean" property="btList">
	<liveany:title titleName="バッチ実行履歴情報" level="2"/>
	<table class="list_table" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<%-- **********タイトル********** Start --%>
				<table width="100%" class="list_head" cellpadding="0" cellspacing="1">
					<tr>
						<td width="25" class="head">選択</td>
						<td width="100" class="head">BATCH ID</td>
						<td width="55" class="head">実行番号</td>
						<td width="80" class="head">実行ユーザ</td>
						<td width="130" class="head">予定時間</td>
						<td width="130" class="head">処理時間</td>
						<td width="130" class="head">終了時間</td>
						<td class="head">状態</td>
					</tr>
				</table>
				<%-- **********タイトル*********** End --%>
			</td>
		</tr>
		<tr>
			<td class="list_body">
				<%-- **********一覧内容********** Start --%>
				<div style="height: 140px;" class="list_body_div">
					<table class="list_body_table" width="100%" cellpadding="0" cellspacing="1" >

						<c:forEach var="btList" items='${viewBean.btList}'  varStatus="status" >
							<tr class="<c:if test="${status.index % 2 == 0}">line0</c:if><c:if test="${status.index % 2 != 0}">line1</c:if>" 	<%-- TR 行の色 --%>
								onmouseover="this.style.backgroundColor='FDF2E3'" onmouseout="this.style.backgroundColor=''"> 					<%-- TR マウス制御 --%>
								<label for="id_btList_chk_${status.index}">
									<td width="25">
										<html:radio name="viewBean" property="btList_chk" value="${status.index}" styleId="id_btList_chk_${status.index}" style="width:100%" onclick="showDetail();" tabindex="5"/>
										<a id="idx_${status.index}" name="idx_${status.index}"/>
										<html:hidden name="viewBean" property="bt_id" value="${btList.bt_id}" />
									</td>
									<td width="100"><liveany:text property="btList_btId" value="${btList.bt_id}" style="width:100%" readonly="true"/></td>
									<td width="55"><liveany:text property="btList_btNo" value="${btList.bt_no}" style="width:100%" readonly="true"/></td>
									<td width="80"><liveany:text property="btList_user_name" value="${btList.regi_user}"  title="${btList.regi_user}" style="width:100%" readonly="true"/></td>
									<td width="130"><liveany:text property="btList_bt_sched_dttm" value="${btList.bt_sched_dttm}" style="width:100%" readonly="true"/></td>
									<td width="130"><liveany:text property="btList_bt_begin_date" value="${btList.bt_begin_date}" style="width:100%" readonly="true"/></td>
									<td width="130"><liveany:text property="btList_bt_end_date" value="${btList.bt_end_date}" style="width:100%" readonly="true"/></td>
									<td>
										<c:if test="${btList.bt_status == 'S'}">
											<font color="blue"><b>成功</b></font>
										</c:if>
										<c:if test="${btList.bt_status == 'F'}">
											<font color="red"><b>失敗</b></font>
										</c:if>
										<c:if test="${btList.bt_status == 'P'}">
											<b>実行中</b>
										</c:if>
										<c:if test="${btList.bt_status == 'W'}">
											<b>待機中</b>
										</c:if>
									</td>
								</label>
							</tr>
						</c:forEach>
					</table>
				</div>
				<%-- **********一覧内容********** End --%>
			</td>
		</tr>
		<%-- ページング定義 --%>
		<tr align="center">
			<td><liveany:page pageNo="${viewBean.btListPagingInf.pageNo}" listNo="${viewBean.btListPagingInf.listRowCnt}" totalCnt="${viewBean.btListPagingInf.totalCnt}" noPerIdx="10"   funcNm="goPage"/></td>
		</tr>
	</table>

	<liveany:title titleName="バッチ実行詳細情報" level="2"/>
	<!--
	<table width="100%" cellpadding="0" cellspacing="0" border="0">
		<tr align="right">
			<td class="td_w r">
				<html:checkbox name="viewBean" property="btStatsRefresh" value="1" styleId="id_btStatsRefresh" onclick="refreshStatus();"/><label for="id_btStatsRefresh">5秒ごとにバッチ状態監視</label>
			</td>
		</tr>
	</table>
	 -->
	<table class="content_i" width="100%">
		<tr>
			<td width="70" class="td_f">Batch ID</td>
			<td width="120" class="td_w">
				<liveany:text name="viewBean" property="dtl_bt_id" value="${ batchDto.dtl_bt_id }" readonly="true" style="width: 100%"/>
			</td>
			<td width="60" class="td_f">実行番号</td>
			<td width="30" class="td_w">
				<liveany:text name="viewBean" property="dtl_bt_no" value="${ batchDto.dtl_bt_no }" readonly="true" style="width: 100%"/>
			</td>
			<td width="60" class="td_f">処理結果</td>
			<td width="45" class="td_w" id="status_td" align="center">
				<c:if test="${batchDto.dtl_bt_status == 'S'}"><font color="blue"><b>成功</b></font></c:if>
				<c:if test="${batchDto.dtl_bt_status == 'F'}"><font color="red"><b>失敗</b></font></c:if>
				<c:if test="${batchDto.dtl_bt_status == 'P'}"><b>実行中</b></c:if>
				<c:if test="${batchDto.dtl_bt_status == 'W'}"><b>待機中</b></c:if>
			</td>
			<html:hidden property="dtl_bt_status" value="${batchDto.dtl_bt_status}" />
			<td width="70" class="td_f">予定日時</td>
			<td class="td_w" colspan="2">
				<liveany:text name="viewBean" property="dtl_bt_sched_dttm" value="${batchDto.dtl_bt_sched_dttm}" style="width: 120px" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td  width="55" class="td_f">パラメータ</td>
			<td  width="340" class="td_w" colspan="4" >
				<c:if test="${batchDto.dtl_bt_param != ''}">
					<liveany:text name="viewBean" property="dtl_bt_param" value="${batchDto.dtl_bt_param}" style="width: 100%" readonly="true"/>
				</c:if>
				<c:if test="${batchDto.dtl_bt_param == ''}">
					なし
				</c:if>
			</td>
			<td width="35" class="td_f">開始</td>
			<td class="td_w">
				<liveany:text name="viewBean" property="dtl_begin_date" value="${batchDto.dtl_begin_date}" style="width: 120px" readonly="true"/>
			</td>
			<td width="35" class="td_f">終了</td>
			<td class="td_w">
				<liveany:text name="viewBean" property="dtl_end_date" value="${batchDto.dtl_end_date}" style="width: 120px" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td width="55" class="td_f">メッセージ</td>
			<td class="td_w"  colspan="8" >
				<liveany:textarea name="viewBean" property="dtl_bt_msg" value="${batchDto.dtl_bt_msg}" style="width:100%;height:50px;background-color: #DFF9EA; font-family:'MS Gothic'; font-size: 9pt" readonly="true"/>
			</td>
		</tr>

	</table>
</logic:notEmpty>

	<div id="divBtProcessing" style="position:absolute; vertical-align:middle; border:3pt; display:none; text-align:center; background-color:#FFFF93; border:1px solid #CCC; margin-top:5px; margin-top: 5px; border-collapse:collapse; padding:1px; font-size:20pt; width:100px; height:35px;">
		待機中
	</div>
	</div>
	</div>

<html:hidden name="viewBean" property="selected_bt_id"/>
<html:hidden name="viewBean" property="selected_bt_no"/>
<html:hidden name="viewBean" property="selectedBt_flg"/>
<html:hidden name="viewBean" property="doc_file_path"/>
<html:hidden name="viewBean" property="bt_m_bt_id"/>

</form>
<%-- ========================================================================================== Body End--%>

