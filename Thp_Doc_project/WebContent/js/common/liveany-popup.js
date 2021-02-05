/*=======================================================================
 * liveany-popup.js
 *
 * POPUP関連JavaScript
 *
 * =======================================================================*/

	// ポップアップを開く時の共通OutPutパラメータ配列
	var commonPopupParamArray =  new Array();


/**
 *
 * ポップアップ画面を開く。
 *
 * @param width 幅
 * @param height 高さ
 * @param popname ポップアップ名
 * @param url URL
 * @param inParam パラメータ配列（Array）
 * @param outParam 返却パラメータ配列（Array）
 * @param scrollYesOrNo スクロール yes | no
 * @return
 */
function openPopup(width, height, popname, url, inParam, outParam, scrollYesOrNo) {


	if (isEmpty(popname)) {

		alert("プログラムバグ：openPopup()を呼出す際は、ポップアップ名は必ずセットしてください。");

		return;
	}

	// -------- outputパラメータの設定
	commonPopupParamArray[popname] = outParam;

	var left = (screen.width - width) / 2;
	var top = (screen.height - height) / 2;

	var win_style = "charset=UTF-8,location=no,directories=no,status=no,menubar=no,scrollbars="
			+ scrollYesOrNo
			+ ",left="
			+ left
			+ ",top="
			+ top
			+ ",width="
			+ width
			+ ",height=" + height;
	_fwOpenChildHandle = window.open("", popname, win_style);
	var frm = document.createElement("form");
	frm.target = popname;
	frm.action = url;
	frm.method = "POST";
	for ( var param in inParam) {
		var hidden = document.createElement("input");
		hidden.type = "hidden";
		hidden.name = param;
		hidden.value = inParam[param];
		frm.appendChild(hidden);
	}
	document.body.appendChild(frm);
	frm.submit();
	document.body.removeChild(frm);
	_fwOpenChildHandle.focus();
}


/**
 * 指定したtarget_objで設定したoutputパラメータArrayを取得します。
 *
 * 例）var outputParamArray = getOutputArray(opner);
 *     var outputParamArray = getOutputArray(opner.ifrm);
 * @param target_obj
 * @return
 */
function getOutputArray(target_obj) {
	return target_obj.commonPopupParamArray[window.name];

}


/**
 * 対象オブジェクト値をセットする。
 *
 * @param resObj 対象オブジェクト
 * @param resValue セットする値
 */
function setOutputValue(resObj, resValue) {

	alert(resObj);
	alert(resValue);
	if (resObj != null) {
		if (resObj.length == undefined) {
			resObj.value = resValue;
		} else {
			for ( var i = 0; i < resObj.length; i++) {

				if (resObj[i]) {
					resObj[i].value = resValue;
				}

			}
		}
	}
}





