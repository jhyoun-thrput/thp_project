var cobc_local_addr_rslt_arr = new Array();

var cobc_local_addr_set_arr = new Array();
cobc_local_addr_set_arr['txt_ano'] = "";
cobc_local_addr_set_arr['txt_symb'] = "";
cobc_local_addr_set_arr['txt_othr_1'] = "";
cobc_local_addr_set_arr['txt_othr_2'] = "";
cobc_local_addr_set_arr['txt_rslt'] = "";
cobc_local_addr_set_arr['add_ad'] = "";

function cobc_local_addr_cd_pop() {
	var winy = (screen.height - 420) / 2;
	var winx = (screen.width - 560) / 2;
	var win = window
			.open('/common/AddrCode.do?method=getLocalAddrSrchList',
					'localAddrCd', 'height=310,width=560,top=' + winy
							+ ',left=' + winx);
	win.focus();
}

function set_local_addr_value(arr) {
	cobc_local_addr_rslt_arr['addr'] = arr['addr'];
	cobc_local_addr_rslt_arr['addr1'] = arr['addr1'];
	cobc_local_addr_rslt_arr['vil_ano_cd'] = arr['vil_ano_cd'];
	cobc_local_addr_rslt_arr['vil_ano'] = arr['vil_ano'];
	cobc_local_addr_rslt_arr['jip_cd'] = arr['jip_cd'];
	cobc_local_addr_rslt_arr['ano'] = arr['ano'];
	cobc_local_addr_rslt_arr['symb'] = arr['symb'];
	cobc_local_addr_rslt_arr['othr_1'] = arr['othr_1'];
	cobc_local_addr_rslt_arr['othr_2'] = arr['othr_2'];
	cobc_local_addr_rslt_arr['add_ad'] = arr['add_ad'];
	cobc_local_addr_rslt_arr['jip_cd'] = arr['jip_cd'];
	cobc_local_addr_rslt_arr['elmt_sl_cd'] = arr['elmt_sl_cd'];
	cobc_local_addr_rslt_arr['mddl_sl_cd'] = arr['mddl_sl_cd'];
	cobc_local_addr_rslt_arr['vote_ar_cd'] = arr['vote_ar_cd'];
	cobc_local_addr_rslt_arr['elmt_sl_cd_prtc'] = arr['elmt_sl_cd_prtc'];
	cobc_local_addr_rslt_arr['mddl_sl_cd_prtc'] = arr['mddl_sl_cd_prtc'];
	cobc_local_addr_rslt_arr['vote_ar_cd_prtc'] = arr['vote_ar_cd_prtc'];
}