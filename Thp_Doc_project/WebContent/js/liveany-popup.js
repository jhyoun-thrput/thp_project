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
 * @param useContext true | false ... default:true
 * @param resizable true | false ... default:false
 * @return
 */
function openPopup(width, height, popname, url, inParam, outParam, scrollYesOrNo, useContext, resizable) {

	if (isEmpty(popname)) {
		alert("プログラムバグ：openPopup()を呼出す際は、ポップアップ名は必ずセットしてください。");
		return;
	}

	// コンテキスト指定
	useContext = (useContext != null) ? useContext : true;
	if (useContext == true) {
		url = PATH_CONTEXT + url;
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
			+ width + "px"
			+ ",height=" + height+ "px";

	resizable = (resizable != null) ? resizable : false;
	if (resizable) {
		win_style = win_style + ",resizable"
	}

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

	if (_fwOpenChildHandle != null && _fwOpenChildHandle != undefined) {
		_fwOpenChildHandle.focus();
	}

}

/**
 *
 * ポップアップ(Modal)画面を開く。
 *
 * @param width 幅
 * @param height 高さ
 * @param popname ポップアップ名
 * @param url URL
 * @param inParam パラメータ配列（Array）
 * @param outParam 返却パラメータ配列（Array）
 * @param scrollYesOrNo スクロール yes | no
 * @param useContext true | false ... default:true
 * @return
 */
function openPopupModal(width, height, popname, url, inParam, outParam, scrollYesOrNo, useContext, resizable) {

	if (isEmpty(popname)) {
		alert("プログラムバグ：openPopupModal()を呼出す際は、ポップアップ名は必ずセットしてください。");
		return;
	}

	// コンテキスト指定
	useContext = (useContext != null) ? useContext : true;
	if (useContext == true) {
		url = PATH_CONTEXT + url;
	}

	// -------- outputパラメータの設定
	commonPopupParamArray[popname] = outParam;

	var left = (screen.width - width) / 2;
	var top = (screen.height - height) / 2;


	var win_style= "help=no;center=yes;status=no;menubar=no;directories=no;minimize=yes;dialogWidth=" + width + "px;" + "dialogHeight=" + (height + 28) + "px;";
	win_style = win_style + "scroll=" + scrollYesOrNo + ";";

	if (resizable) {
		win_style = win_style + "resizable;" // maximize=yes;
	}

	//alert(win_style);

	var modalParam = new Array();
	modalParam["url"] = url;
	modalParam["in"] = inParam;
	modalParam["out"] = outParam;
	modalParam["opener"] = this;

	window.showModalDialog(PATH_CONTEXT +"/pages/common/ModalWinReqPage.jsp", modalParam, win_style);

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

	if (window.dialogArguments != null) {
		return window.dialogArguments["out"];
	} else {
		if (target_obj != null && target_obj.commonPopupParamArray != null) {
			return target_obj.commonPopupParamArray[window.name];
		} else {
			return null;
		}
	}

}


/**
 * オブジェクトに値設定。
 * 対象オブジェクト値をセットする。
 *
 * @param resObj 対象オブジェクト
 * @param resValue セットする値
 */
function setOutputValue(obj, resValue) {

	alert(obj);
	alert(resValue);
	if (obj != null) {
		if (obj.length == undefined) {
			setObjectValue(obj, resValue);
		} else {
			for ( var i = 0; i < obj.length; i++) {
				if (obj[i]) {
					setObjectValue(obj[i], resValue);
				}
			}
		}
	}
}

/**
 * オブジェクト種類を判別し値を設定する。
 *
 * @param obj
 * @param resValue
 */
function setObjectValue(obj, resValue) {
	if (obj.tagName == "INPUT") {
		if (obj.type == "text" || obj.type == "hidden") {
			obj.value = resValue;

			var selObj = obj.form[obj.name + "_nm"];
			if (selObj != undefined && selObj != null && selObj.type == "select-one") {
				selObj.value = resValue;
			}

		} else if (obj.type == "radio" || obj.type == "checkbox") {
			if (obj.value == resValue) {
				obj.checked = true;
			}
		} else if (obj.type == "select-one") {
			obj.value = resValue;
		} else {
			obj.value = resValue;
		}
	} else if (obj.tagName == "OPTION") {
		if (obj.value == resValue || obj.text == resValue) {
			obj.selected = true;
		}
	} else if (obj.tagName == "TEXTAREA") {
		obj.value = resValue;
	} else {
		obj.value = resValue;
	}
}



