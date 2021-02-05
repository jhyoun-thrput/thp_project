<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/pages/common/header.jsp"%>
<form name="frmSrchHist" method="post" style="height:0; margin:0;">
<input type="hidden" name="icno"/>
<input type="hidden" name="kana_nm"/>
<input type="hidden" name="objname"/>
</form>
<div id="layerSrchHist" style="position:absolute; z-index:1; visibility:hidden; left=-1000; top=-1000;">
	<table bgcolor="#ffffff" width="230" border="0" cellpadding="0" cellspacing="0" class="layer_border">
		<tr>
			<td id="view_td" valign="top" style="padding:0px">			
				<div id="layerSrchHistList" style="position:absolute; z-index:2; width:100%;">
				<iframe id="searchHistory" name="searchHistory" src="" border="0" frameborder="0" width="100%" scrolling="auto"></iframe>
				</div>			
			</td>
		</tr>
		<tr>
			<td class="layer_line"></td>
		</tr>
		<tr>
			<td class="layer_close">
			<a href="javascript:hideSrchHist();"><font class="layer_link">閉じる</font>
			<img src="/images/common/icon_close.gif" width="11" height="11" align="middle"/></a></td>
		</tr>
	</table>
</div>		
<iframe name="sessionUpdate" src="" scrolling="no" border="0" frameborder="0" width="100%" height="0"></iframe>
