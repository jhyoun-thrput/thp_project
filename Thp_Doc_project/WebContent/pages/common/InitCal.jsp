<%-- ========================================================================================== ヘッダ定義--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/pages/common/header.jsp"%>
<%-- ========================================================================================== 変数宣言--%>
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
		initCal("");
	}
</script>
