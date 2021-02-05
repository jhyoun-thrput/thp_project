/*=======================================================================
 * liveany-fw.js
 *
 * formチェック及びFrameWorkの関連JavaScript
 *
 * @version 修正履歴
 *
 *          2009/05/23 : 新規作成 (revised by Noh.S.Y)
 *
 * @author Noh.S.Y
 * =======================================================================*/

/*******************************************************************************
 * 00．環境設定パラメータ
 ******************************************************************************/
var PATH_CONTEXT = "/Tech3g_Doc_project"; // コンテキストパス

/*******************************************************************************
 * 00．初期設定パラメータ
 ******************************************************************************/
var g_onFocusClass = "input_o";
var g_onErrorClass = "input_er";

/*******************************************************************************
 * 01．初期クラス名 ()
 ******************************************************************************/

// **************************
// ***** 入力文字タイプ *****
// **************************
var TY_HAN = "han"; // 半角
var TY_HAN_SUJI = "han_suji"; // 半角数字
var TY_HAN_EIJI = "han_eiji"; // 半角英字
var TY_HAN_EISU = "han_eisu"; // 半角英数
var TY_HAN_KANA = "han_kana"; // 全角カナ

var TY_ZEN = "zen"; // 全角
var TY_ZEN_SUJI = "zen_suji"; // 全角数字
var TY_ZEN_EIJI = "zen_eiji"; // 全角英字
var TY_ZEN_EISU = "zen_eisu"; // 全角英数
var TY_ZEN_KANA = "zen_kana"; // 全角カナ
var TY_ZEN_HIRA = "zen_hira"; // 全角ひらがな

var TY_DECIMAL = "decimal"; // 金額
var TY_ZIP = "zip"; // 郵便番号
var TY_MAIL = "mail"; // メールアドレス


/****************************************************************************** */

// ------------------------------------------------------------
/**
 * onfocus時の処理を定義する。
 *
 * １．エラークラスが設定していれば削除する。
 * ２．オンフォーカス用のスタイルクラスを設定する。
 * ３．入力値をセレクトする。
 *
 * @param targetElm 対象オブジェクト
 */
function onFocus(targetElm) {
	removeClassName(targetElm, g_onErrorClass);
	addClassName(targetElm, g_onFocusClass);
	try {
		targetElm.select();
	} catch(e) {
	}
}

/**
 * onblur時の処理を定義する 。
 * １．オンフォーカス用のスタイルクラスを削除する。
 *
 * @param targetElm 対象オブジェクト
 */
function onBlur(targetElm) {
	removeClassName(targetElm, g_onFocusClass);
}

/**
 * ClientSideの単体バリデーションエラーが発生したときの処理を行う。
 *
 * １．エラースタイルクラスが設定していれば削除する。
 *
 * @param targetElm 対象オブジェクト
 */
function onError(targetElm) {
	addClassName(targetElm, g_onErrorClass);

	// TODO エラー時、値のクリアを臨時解除
	// targetElm.value = "";
	//targetElm.focus();
}


/**
 * エラーメッセージの表示
 * エラーメッセージを表示し、対象項目オブジェクトにフォーカスを戻す。
 *
 * @param targetElm 対象項目オブジェクト
 * @msg エラーメッセージ
 */
function alertErrMsg(targetElm, msg) {
	if (targetElm != null) {
		onError(targetElm);
	}
	alert(msg);
	// TODO エラー時、値のクリアを臨時解除
	//targetElm.value = "";
	//targetElm.focus();
}



/**
 *
 * KEYDOWNイベントが発生したときに 入力タイプによって処理を行う。
 *
 * @param obj 処理対象のオブジェクト
 * @param inputType 入力タイプ
 *
 */
function ctl_onkeydown(obj, inputType, chaExt) {

	var rslt = true;
	// var inputType = param_array[0];

	if (is_keycode_subkey()) {
		return true;
	}

	// 例外文字は入力可能にする。
	if (!isEmpty(chaExt)) {
		for (var i=0; i< chaExt.length; i++) {

			if (String.fromCharCode(event.keyCode) == chaExt.charAt(i)) {
				return rslt;
			}
		}
	}

	if (inputType == TY_HAN) {
		// 半角

	} else if (inputType == TY_HAN_SUJI) {
		// 半角数字
		rslt = is_keycode_number();
		if (!rslt)
			event.returnValue = false;

	} else if (inputType == TY_HAN_EIJI) {
		// 半角英字
		rslt = is_keycode_alphabet();
		if (!rslt)
			event.returnValue = false;

	} else if (inputType == TY_HAN_EISU) {
		// 半角英数
		if (!is_keycode_alphabet() && !is_keycode_number()) {
			event.returnValue = false;
			rslt = false;
		}

	} else if (inputType == TY_HAN_KANA) {
		// 半角カナ

	} else if (inputType == TY_ZEN) {
		// 全角

	} else if (inputType == TY_ZEN_SUJI) {
		// 全角数字

	} else if (inputType == TY_ZEN_EIJI) {
		// 全角英字

	} else if (inputType == TY_ZEN_EISU) {
		// 全角英数

	} else if (inputType == TY_ZEN_KANA) {
		// 全角カナ

	} else if (inputType == TY_ZEN_HIRA) {
		// 全角ひらがな

	} else if (inputType == TY_DECIMAL) {
		// 数値、金額
		rslt = is_keycode_decimal();
		if (!rslt)
			event.returnValue = false;

	} else if (inputType == TY_ZIP) {
		// 郵便番号

	} else if (inputType == TY_MAIL) {
		// メールアドレス

	}

	return rslt;
}

/**
 * onblur時に入力チェック（単体バリデーション）を行う。
 *
 * @param obj 処理対象のオブジェクト
 * @param inputType 入力タイプ
 * @param chaExt
 */
function chk_onblur_input(obj, inputType, chaExt, format) {

	var str = obj.value;

	var is_err = false;
	var err_msg = "";

	if (str == "") {
		return true;
	}


	// 例外文字を除去する。
	if (!isEmpty(chaExt)) {
		for (var i=0; i< chaExt.length; i++) {
			str = str.replaceAll(chaExt.charAt(i),'');
		}
	}

	str = str.replaceAll('\n\r','');
	str = str.replaceAll('\n','');
	str = str.replaceAll('\r','');

	if (inputType == TY_HAN) {
	// 半角
		if (!isHan(str)) {
			is_err = true;
			err_msg = "半角以外の文字が含まれています。「" + obj.value + "」\n半角で入力して下さい。";
		}
	} else if (inputType == TY_HAN_SUJI) {
	// 半角数字
		if (!isNum(str)) {
			is_err = true;
			err_msg = "半角数字以外の文字が含まれています。「" + obj.value + "」\n半角数字で入力して下さい。";
		}
	} else if (inputType == TY_HAN_EIJI) {
	// 半角英字
		if (!isHanAlpha(str)) {
			is_err = true;
			err_msg = "半角英字以外の文字が含まれています。「" + obj.value + "」\n半角英字で入力して下さい。";
		}

	} else if (inputType == TY_HAN_EISU) {
	// 半角英数
		if (!isHanAlphaNum(str)) {
			is_err = true;
			err_msg = "半角英数以外の文字が含まれています。「" + obj.value + "」\n半角英数で入力して下さい。";
		}
	} else if (inputType == TY_HAN_KANA) {
	// 半角カナ
		if (!(isHanKanaAlpha(str))) {
			is_err = true;
			err_msg = "半角ｶﾅ以外の文字が含まれています。「" + obj.value + "」\n半角ｶﾅで入力して下さい。";
		}
	} else if (inputType == TY_ZEN) {
	// 全角
		if (!isZen(str)) {
			is_err = true;
			err_msg = "全角以外の文字が含まれています。「" + obj.value + "」\n全角で入力して下さい。";
		}
	} else if (inputType == TY_ZEN_SUJI) {
	// 全角数字
		if (!isZenNum(str)) {
			is_err = true;
			err_msg = "全角数字以外の文字が含まれています。「" + obj.value + "」\n全角数字で入力して下さい。";
		}
	} else if (inputType == TY_ZEN_EIJI) {
	// 全角英字
		if (!isZenAlpa(str)) {
			is_err = true;
			err_msg = "全角英字以外の文字が含まれています。「" + obj.value + "」\n全角英字で入力して下さい。";
		}

	} else if (inputType == TY_ZEN_EISU) {
	// 全角英数
		if (!isZenAlpaNum(str)) {
			is_err = true;
			err_msg = "全角英数以外の文字が含まれています。「" + obj.value + "」\n全角英数で入力して下さい。";
		}
	} else if (inputType == TY_ZEN_KANA) {
	// 全角カナ
		if (!isZenKana(str)) {
			is_err = true;
			err_msg = "全角カナ以外の文字が含まれています。「" + obj.value + "」\n全角カナで入力して下さい。";
		}

	} else if (inputType == TY_ZEN_HIRA) {
	// 全角ひらがな
		if (!isZenHira(str)) {
			is_err = true;
			err_msg = "ひらがな以外の文字が含まれています。「" + obj.value + "」\nひらがなで入力して下さい。";
		}
	} else if (inputType == TY_DECIMAL) {
	// 数値・金額
		if (!isDecimal(str)) {
			is_err = true;
			err_msg = "数値で入力してください。「" + obj.value + "」";
		}


		str = replaceAll(str, ",", "");

		// 整数チェック
		if ( !is_err && !isEmpty(format) && "-" != format.substring(0, 1)) {
			var nVal = new Number(str);
			if (nVal < 0) {
				is_err = true;
				err_msg = "0以上で入力してください。";
			}
		}


		// 数値・金額長さチェック
		if ( !is_err && !isEmpty(format)) {

			str = replaceAll(str, "-", "");

			var splitFormat = format.split(".");
			var precision= new Number(replaceAll(splitFormat[0], "-", "") );
			var scale= new Number(splitFormat[1]);

			var strSplit = str.split(".");
			var strPrecision= "" + (new Number(strSplit[0]));

			var strScale= "";
			if (strSplit.length > 0 && isNum(strSplit[1])) {
				strScale= "" + (strSplit[1]);
			}

			if (strPrecision.length > precision
					|| strScale.length > scale) {
				is_err = true;
				err_msg = "数値の形式が正しくありません。"+ "(整数："+  precision +"桁、小数点以下"+ scale +"桁)" + "「" + obj.value + "」";
			}
		}

	}

	if (is_err) {

		var tmpBuffer = obj.onblur;
		obj.onblur = null;
		alertErrMsg(obj, err_msg);
		obj.onblur = tmpBuffer;
		return false;
	}
	return true;
}

/**
 * onfocus時の日付フォーマットを変更。
 * onfocus時に呼出し、引数のパラメータで日付フォーマットを変更
 * @param obj 日付項目object
 * @param fmt 日付タイプ
 */
function conv2DateOnFocus(obj, fmt) {

	var str = obj.value;

	if (str != "") {
		 str = conv2Date(str, fmt);

		 if (str != "") {
			obj.value = str;
			obj.select();
		 }
	}
}

/**
 * onblur時の日付フォーマットを変更
 * onblur時に呼出し、引数の日付タイプで日付フォーマットを変更
 * @param obj 日付項目object
 * @param fmt 日付タイプ
 */
function conv2DateOnBlur(obj, fmt) {

	if (obj.value == "") {
		return true;
	} else {

		var res = conv2Date(obj.value, fmt);
		if (res != "") {

			if (fmt == FMT_JYY_MM_DD
			    || fmt == FMT_YYYYMMDD
			    || fmt == FMT_YYYY_MM_DD
			    || fmt == FMT_NYYMMDD
			    || fmt == FMT_JCCYY_MM_DD) {
				var yyyyMMDD = conv2Date(obj.value, FMT_YYYYMMDD);
				if (!isLeapYear(yyyyMMDD.substr(0, 4)) && "0229" == yyyyMMDD.substr(4, 8)) {
					if (confirm("うるう年ではない年の2月29日ですが、よろしいですか。")) {
						obj.value = res;
					} else {
						obj.value = "";
						obj.focus();
					}
				} else {
					obj.value = res;
				}

			} else {
				obj.value = res;
			}

		} else {
			obj.value = obj.value;
			alertErrMsg(obj, "日付形式ではありません。" + "「" + obj.value + "」");
			return false;
		}
	}

	return true;

	// try {
	// obj.value = conv2Date(obj.value, fmt);
	// } catch(e) {
	// onError(obj);
	// alert("日付形式ではありません。" + "「" +obj.value + "」");
	// return false;
	// }
	// }
	// return true;
}

/**
 * 日付のUpDown
 * 指定した日付項目にフォーカスを置き、Up矢印ボタンを押下した場合1日前の日付に変換し設定。
 * 指定した日付項目にフォーカスを置き、Down矢印ボタンを押下した場合1日後の日付に変換し設定。
 *
 * @param obj 日付（年月日）項目のObject
 */
function plusMinusDateCtl(obj) {
	if (event.keyCode == 40) {
	// down
		var dt = conv2Seireki(obj.value);
		var dataType = getDateType(dt);

		if (dataType == "yyyymmdd" && isDate(dt)) {
		// 年月日
			obj.value = getPreviousDay(dt);
			obj.select();
		} else if (dataType == "yyyymm" && isDate(dt + "01")) {
		// 年月
			var year = dt.substring(0, 4);
			var month = dt.substring(4, 6);

			if (month == "01") {
				obj.value = (year -1) + "12";
			} else {
				obj.value = "" + year + "" + fillCharStr( "" +(new Number(month) -1), 2, "0", true) ;
			}
			obj.select();
		} else if (dataType == "yyyy" && isDate(dt + "0101")) {
		// 年
			obj.value = fillCharStr( "" +(new Number(dt) - 1), 4, "0", true);
			obj.select();
		}

	} else if (event.keyCode == 38) {
	// up
		var dt = conv2Seireki(obj.value);
		var dataType = getDateType(dt);

		if (dataType == "yyyymmdd" && isDate(dt)) {
		// 年月日
			obj.value = getNextDay(dt);
			obj.select();
		} else if (dataType == "yyyymm" && isDate(dt + "01")) {
		// 年月
			var year = dt.substring(0, 4);
			var month = dt.substring(4, 6);

			if (month == "12") {
				obj.value =  ""+ (new Number(year) + 1) + "01";
			} else {
				obj.value = "" + year + "" + fillCharStr( "" +(new Number(month) +1), 2, "0", true) ;
			}
			obj.select();
		} else if (dataType == "yyyy" && isDate(dt + "0101")) {
		// 年
			obj.value = fillCharStr( "" +(new Number(dt) + 1), 4, "0", true);
			obj.select();
		}
	}
}


/**
 * 日付の西暦変換。
 * 指定したオブジェクトの値が日付形式であれば和暦に変換
 * onfocus時に呼出す。
 * @param obj 日付項目object
 */
function conv2SeirekiObj(obj) {

	var str = obj.value;

	if (str != "") {
		 str = conv2Seireki(str);

		 if (str != "") {
			obj.value = str;
			obj.select();
		 }
	}
}

/**
 * 日付の和暦変換
 * 指定したオブジェクトの値が日付形式であれば和暦に変換
 * 日付形式が正しくなければエラーメッセージを表示する。
 * @param obj 日付項目object
 */
function conv2WarekiObj(obj) {

	if (obj.value == "") {
		return true;
	} else {
    	var res = "";
    	var dateStr = obj.value;
	    if (dateStr.match(REGEX_JYY_MM_DD)
	            || dateStr.match(REGEX_YYYYMMDD)
	            || dateStr.match(REGEX_YYYY_MM_DD)
	            || dateStr.match(REGEX_NYYMMDD)
	            || dateStr.match(REGEX_JCCYY_MM_DD)) {
	    // 年月日
	        res = conv2Date(dateStr, FMT_JYY_MM_DD);
	    } else if (dateStr.match(REGEX_NYY)
	            || dateStr.match(REGEX_YYYY)
	            || dateStr.match(REGEX_JYY)
	            || dateStr.match(REGEX_JCCYY)) {
	    // 年
	        res = conv2Date(dateStr, FMT_JYY);
	    } else if (dateStr.match(REGEX_NYYMM)
	            || dateStr.match(REGEX_JYY_MM)
	            || dateStr.match(REGEX_YYYYMM)
	            || dateStr.match(REGEX_YYYY_MM)
	            || dateStr.match(REGEX_JCCYY_MM)) {
	    // 年月
	        res = conv2Date(dateStr, FMT_JYY_MM);
	    }

	    if (isEmpty(res)) {
	        alertErrMsg(obj, "日付形式ではありません。" + "「" + dateStr + "」");
	    } else {
	       obj.value = res;
	    }

	}

	return true;
}






/**
 * トップフレームの和暦フレームを返還する。
 *
 * @return 共通日付フォーム
 */
function getWarekiFrame() {
	var f;
	var tmp_win = getPrimaryWindow();

	if (tmp_win.frames[0].frmWareki == null) {
		f = tmp_win.frames[0].frames[0].frmWareki;
	} else {
		f = tmp_win.frames[0].frmWareki;
	}

	return f;
}

/**
 * 最上のウィンドーオブジェクトを返却する。
 *
 * @return 最上のウィンドーオブジェクト
 */
function getPrimaryWindow() {
	var tmp_win = this;
	var i = 0;

	while (i < 100) {

		if (tmp_win.parent.name != "") {
			tmp_win = tmp_win.parent;
		}

		if (tmp_win.opener != null) {
			tmp_win = tmp_win.opener;
		}
		i++;

		if (tmp_win.name == "liveany")
			break;
	}

	return tmp_win;

}

/**
 * 数値の入力形式へ変換する。
 * @param obj 対象object
 */
function unformatDecimal(obj){
	var str = obj.value.replace(/,/g,"");
	if ( str.indexOf(".") != -1){
		var suf = str.split(".")[1];
		if ( suf.length < 1 ) {
			str = str.split(".")[0];
		}
	}
	obj.value = str;
}

/**
 * 数値の表示形式へ変換する。
 * @param obj 対象object
 */
function formatDecimal(obj){

	var str = obj.value;

	if (str.substring(0,1) == ".") {
		str = "0" + str;
	}

	if (str.substring(str.length - 1,str.length) == ".") {
		str = str.substring(0,str.length - 1);
	}

	str = str.replace(/,/g,"");

	if (isDecimal(str)){
		var firStr = str.split(".")[0];
		firStr = "" + (new Number(firStr));
		var secStr = "";
		var cnt = 0;
		for(var i=firStr.length; i>=0 ;i--){
			secStr = firStr.charAt(i) + secStr;
			if (cnt==3 && i>=1){
				secStr = "," + secStr;
				cnt = 0;
			}
			cnt++;
		}
		if (str.indexOf(".") != -1){
			str = secStr + "." + str.split(".")[1];
		}else{
			str = secStr;
		}
	}
	obj.value = str.replace(/-,/g,"-");
}


/**
 * 入力されたキーがEnterキーであるか確認する。
 *
 * もしEnterキーであれば指定した文字列のJavasScriptを実行する。
 *
 * @return {boolean}
 */
function isPressEnterKey(obj, functionStr) {
	if (window.event.keyCode == 13) {
		if (!isEmpty(functionStr)) {
			eval(functionStr + ";");
		}
	}
}



/*
 * コード付きコンボボックスに値を設定する。
 */
function setSelTextValue (textObj, value) {

	if (textObj != null && textObj != undefined) {
		textObj.value = value;
		setSelectField(textObj, textObj.name + "_nm");
	}
}


/**
 * SelTextのコード存在チェック
 * 入力したコードがSelectに存在するかチェックする。
 * 存在しない場合、エラーメッセージを表示する。
 *
 * @txtObj テキストのObject
 * @selectName ドロップダウンのObjectName
 */
function chkSelTextValue(txtObj, selectName) {
	var frmName= txtObj.form.name;


	if (txtObj.value != "") {
		var parent = null ;
		eval("parent = document." + frmName + "." + txtObj.name +";");
		if (parent != null && parent.length != undefined && parent.type == undefined) {
			var idx = 0;
			for (var i = 0 ; i < parent.length; i++) {
				if (parent[i] == txtObj) {
					idx = i;
					break;
				}
			}
			eval("var val =" + frmName+"."+selectName+"["+ idx +"].value;");

			if (val ==  '') {
			// もし、TEXTに入力した値に該当する値がSELECTに存在しない場合はブランクでリセットする。
				alert('該当する値がありません。');
				txtObj.value = "";
				txtObj.focus();
			}
		} else {
			eval("var val =" + frmName+"."+selectName+".value;");
			if (val ==  '') {
			// もし、TEXTに入力した値に該当する値がSELECTに存在しない場合はブランクでリセットする。
				alert('該当する値がありません。');
				txtObj.value = "";
				txtObj.focus();
			}
		}
	}

}

/**
 * 指定されたtext項目にselect項目の値をセットする
 * @param selObj Selectオブジェクト
 * @param textName Textフィールドの項目名
 */
function setTextField(selObj, textName) {
	var frmName= selObj.form.name;

	var parent = null ;
	eval("parent = document." + frmName + "." + selObj.name +";");
	if (parent != null && parent.length != undefined && parent.type == undefined) {
		var idx = 0;
		for (var i = 0 ; i < parent.length; i++) {
			if (parent[i] == selObj) {
				idx = i;
				break;
			}
		}
		eval(frmName+"."+textName+"["+ idx +"].value = selObj.value;");
	} else {
		eval(frmName+"."+textName+".value = selObj.value;");
	}
}


/**
 * 指定されたselect項目にtext項目の値をセットする。。
 * @param textObj テキストオブジェクト
 * @param selName Selectフィールドの項目名
 */
function setSelectField(textObj, selName) {
	var frmName= textObj.form.name;

	var parent = null ;
	eval("parent = document." + frmName + "." + textObj.name +";");
	if (parent != null && parent.length != undefined && parent.type == undefined) {
		var idx = 0;
		for (var i = 0 ; i < parent.length; i++) {
			if (parent[i] == textObj) {
				idx = i;
				break;
			}
		}
		eval("var selTxtObj = " + frmName+"."+selName+"["+ idx +"]");
		if (selTxtObj != null && selTxtObj != undefined) {
			selTxtObj.value = textObj.value;
		}
	} else {
		eval("var selTxtObj = " + frmName+"."+selName);
		if (selTxtObj != null && selTxtObj != undefined) {
			selTxtObj.value = textObj.value;
		}
	}
}

/**
 * InputBoxの値でSelectBoxの選択値が変更
 * @param objText テキストボックスオブジェクト
 * @param objSel セレクトボックスオブジェクト
 * @return
 */
function setSelectNm(objText, objSel) {
	var chk = 0;

	for (i = 0; i < objSel.length; i++) {
		if (objSel[i].value == objText.value) {
			objSel[i].selected = true;
			chk++;
		}
	}

	if (objText.value.length > 0 && chk == 0) {
		objText.select();
		alert('該当する値がありません。');
	}
}

/**
 * SelectBoxのリストをクリアする
 * @param objSel セレクトボックスオブジェクト
 */
function clearSelect(objSel) {

	var size = objSel.length;

	for (var i = 0; i < size; i++) {
		objSel.options[0] = null;
	}

	objSel.options[0] = new Option("", "");
}


//-----------------------------------------------------------------------
//**** 住所コード関連の関数　開始
//------------------------------------------------------------------------

/**
 * カスタムタグ liveany:dateText のボタンから住所検索画面を開く関数。
 * @param vil_ano_cd
 * @param sub_ano1
 * @param sub_ano2
 * @param sub_ano3
 * @param sub_ano4
 * @param ccAdName
 * @param ccAadName
 * @param zipCdName
 * @param adClName
 */
function openSAddrSrchWin(	vil_ano_cd, 	// 住所コード(町字コード)
							sub_ano1,	// 本番
							sub_ano2,	// 枝番
							sub_ano3,	// 小枝番
							sub_ano4,	// 小小枝番
					    	ccAdName,	// 「住所」
					    	ccAadName,	// 「方書」
					    	fullAddr,   // 「住所」+ 「方書」
					    	zipCdName,	// 「郵便番号」
					    	zipCdHpName,// 「郵便番号」ハイフン付き
					    	adClName,	// 「住所区分」
							elmtSlCdName,// 小学校コード
							mddlSlCdName,// 中学校コード
							voteArCdName,// 投票区コード
							admnArCdName,// 行政区コード
							fcltCntName ,// 国保資格_施設のカウント
					    	idx
							) {

	arrMultiAddrRst = new Array();


	arrMultiAddrRst["vil_ano_cd"] = "";		 									// 住所コード(町字コード)
	arrMultiAddrRst["sub_ano1"] = "";											// 本番
	arrMultiAddrRst["sub_ano2"] = "";											// 枝番
	arrMultiAddrRst["sub_ano3"] = "";											// 小枝番
	arrMultiAddrRst["sub_ano4"] = "";											// 小小枝番
	arrMultiAddrRst['vil_ano'] = "";       										// 漢字住所の一部分
	arrMultiAddrRst["all_vil_ano"] = "";										// 「住所」
	arrMultiAddrRst["add_ad"] = "";												// 「方書」
	arrMultiAddrRst["jip_cd"] = "";												// 「郵便番号」

	arrMultiAddrRst['conn1'] = "";         										// 本番の名称
	arrMultiAddrRst['conn2'] = "";          									// 枝番の名称
	arrMultiAddrRst['conn3'] = "";          									// 小枝番の名称
	arrMultiAddrRst['conn4'] = "";         			 							// 小小枝番の名称


	if (adClName != null) {
		if (getValue(adClName, idx) == "0") {
			arrMultiAddrRst['inout_flag'] = "1"; 	// 「住所区分」：市内
		} else if (getValue(adClName, idx) == "1") {
			arrMultiAddrRst['inout_flag'] = "2"; 	// 「住所区分」：市外
		}
	}

	if (getValue(vil_ano_cd, idx).length > 5) {
		arrMultiAddrRst['view_flag'] = '2';	// 市外モード
	} else {
		arrMultiAddrRst['view_flag'] = '1'; // 市内モード
	}


	arrMultiAddrRst['on_close_fun'] = "closeSAddrSrchWin"; 	// 閉じるときに実行する関数

	arrMultiAddrRst['obj_vil_ano_cd'] = getObject(vil_ano_cd, idx); 		// 住所コード(町字コード)
	arrMultiAddrRst['obj_sub_ano1'] = getObject(sub_ano1, idx); 			// 本番
	arrMultiAddrRst['obj_sub_ano2'] = getObject(sub_ano2, idx); 			// 枝番
	arrMultiAddrRst['obj_sub_ano3'] = getObject(sub_ano3, idx); 			// 小枝番
	arrMultiAddrRst['obj_sub_ano4'] = getObject(sub_ano4, idx); 			// 小小枝番

	arrMultiAddrRst['obj_all_vil_ano'] = getObject(ccAdName, idx); 			// 「住所」
	arrMultiAddrRst['obj_add_ad'] = getObject(ccAadName, idx); 				// 「方書」
	arrMultiAddrRst['obj_full_addr'] = getObject(fullAddr, idx); 			// 「住所」+「方書」
	arrMultiAddrRst['obj_jip_cd'] = getObject(zipCdName, idx); 				// 「郵便番号」
	arrMultiAddrRst['obj_jip_cd_hp'] = getObject(zipCdHpName, idx); 		// 「郵便番号」ハイフン付き
	arrMultiAddrRst['obj_inout_flag'] = getObject(adClName, idx); 			// 「住所区分」

	arrMultiAddrRst['obj_elmt_sl_cd'] = getObject(elmtSlCdName, idx); 		// 小学校コード
	arrMultiAddrRst['obj_mddl_sl_cd'] = getObject(mddlSlCdName, idx); 		// 中学校コード
	arrMultiAddrRst['obj_vote_ar_cd'] = getObject(voteArCdName, idx); 		// 投票区コード
	arrMultiAddrRst['obj_admn_ar_cd'] = getObject(admnArCdName, idx);		// 行政区コード

	arrMultiAddrRst['obj_fclt_cnt'] = getObject(fcltCntName, idx); 			// 国保資格_施設のカウント

	multiAddrPopup();
}





/**
 * 入力した住所コードが市内、市外か確認し入力項目の表示を制御する。
 *
 * １．入力した住所コードが市内の場合 <BR>
 * ①市内専用のプルダウンを表示する。<BR>
 * <BR>
 * ２．入力した住所コードが市外の場合 <BR>
 * ①市外住所表示用の項目を表示し、市内専用のプルダウンを非表示にする。<BR>
 * ②AJAXを利用し市外の住所を取得する。 <BR>
 * ３．入力しなった場合<BR>
 * ①市内専用のプルダウンを表示する。<BR>
 * <BR>
 *
 * @param txtObj 住所コードのテキスト項目OBJ
 * @param selObj 住所項目のセレクト項目OBJ
 * @param desObj 住所項目のテキスト項目OBJ
 * @param divSinai 市内DIV
 * @param divSigai 市外DIV
 * @param atnyCd 自治体コード
 */
function ctlAddrInput(txtObj, selName, desName, divSinai, divSigai, atnyCd) {

	var tx = txtObj.value;
	var frmName = txtObj.form.name;

	var selObj = null;
	var desObj = null;

	var parent = null ;
	var idx = -1;
	eval("parent = document." + frmName + "." + txtObj.name +";");
	if (parent != null && parent.length != undefined && parent.type == undefined) {
		for (var i = 0 ; i < parent.length; i++) {
			if (parent[i] == txtObj) {
				idx = i;
				break;
			}
		}
		selObj = eval(frmName+ "." + selName+"["+ idx +"]");
		desObj = eval(frmName+ "." + desName+"["+ idx +"]");
	} else {
		selObj = eval(frmName+ "." + selName);
		desObj = eval(frmName+ "." + desName);
	}

	var isSinai = false;

	if (tx != "") {
		isSinai = isSinaiAddrCd(tx, selObj);
		ctlAddrVisable(isSinai, divSinai, divSigai, idx);
		if (!isSinai) {
		// 市外
			getSigaiAddrNm(atnyCd, tx, desObj);
		} else {
		// 市内
			selObj.value = tx;
			desObj.value = getSelectedText(selObj);
		}
	} else {
		selObj.value = "";
		ctlAddrVisable(true, divSinai, divSigai, idx);
	}
}

/**
* txが市内の住所コードであるかチェックする。
*
* @param tx 住所コード
* @param selObj 市内プルダウンOBJ
* @return boolean
*/
function isSinaiAddrCd(tx, selObj) {

	var isSinai = false;
	if (selObj.length == undefined) {

		if (selObj.value == tx) {
			isSinai = true;
		}
	} else {
		for ( var i = 0; i < selObj.length; i++) {
			if (selObj[i].value == tx) {
				isSinai = true;
				break;
			}
		}
	}
	return isSinai;
}

/**
* 住所項目の表示制御を行う
*
* @param isSinai true:市内, false:市外
* @param divSinaiName 市内DIV
* @param divSigaiName 市外DIV
* @return boolean
*/
function ctlAddrVisable(isSinai, divSinaiName, divSigaiName, idx) {

	var divSinai = null;
	var divSigai = null;
	if (idx != null && idx > -1) {
		divSinai = document.getElementsByName(divSinaiName)[idx];
		divSigai = document.getElementsByName(divSigaiName)[idx];
	} else {
		divSinai = document.getElementById(divSinaiName);
		divSigai = document.getElementById(divSigaiName);
	}


	if (divSinai.style.display != "none" || divSigai.style.display != "none" ) {
		if (isSinai) {
			//if (divSinai.style.display != "none") {
				divSigai.style.display = "none";
				divSinai.style.display = "block";
			//}
		} else {
			divSigai.style.display = "block";
			divSinai.style.display = "none";
		}
	}
}

/**
* 市外の住所コードに該当する住所文字列を取得する。
*
* @param atny_cd
* @param ad_cd
* @return
*/
function getSigaiAddrNm(atny_cd, ad_cd, desObj) {

	// sql実行するためのパラメータ
	var param = paramMap();
	param.put("ad_cd", ad_cd);

	// callback関数に渡すパラメータ
	var callBackParam = paramMap();
	callBackParam.put("tx_saddr", desObj);

	refAjaxJson(getSigaiAddrNmCallBack, "co.mn.selectSinaiAddrNm", param, callBackParam);
}

/**
* 市外の住所コードに該当する住所文字列を取得する。CALLBACK
*
* @param responseText
* @return
*/
function getSigaiAddrNmCallBack(responseText, callBackParam) {

	var txSAddrObj = callBackParam.get("tx_saddr");

	if (isZero(responseText)) {
		txSAddrObj.value = "";
		return;
	} else {
		try {
			json = new JSONUtil(responseText).getJSON();
			txSAddrObj.value = json.cc_nm1 + json.cc_nm2 + json.cc_nm3
					+ json.cc_nm4;
		} catch (e) {
			txSAddrObj.value ="";
			alert("住所取得が失敗しました。");
			return;
		}
	}
}

function doClearAddr(formName, addrCd) {
	var addrCdObj =  eval(formName + "."+ addrCd);
	addrCdObj.value = "";
	eval(formName+".tx_saddr_name_" + addrCd +  ".value = '';");
	eval(formName+".sel_saddr_name_" + addrCd +  ".value = '';");
}


//-----------------------------------------------------------------------
//**** 住所コード関連の関数 終了
//------------------------------------------------------------------------



function comShowPB() {
	var obj = document.getElementById('divProcessing');
	if (obj != null) {
		var _x = 400;
		var _y = document.body.clientHeight / 2 + document.body.scrollTop - 30;
		obj.style.left = _x;
		obj.style.top = _y;
		obj.style.display = "block";
	}

//	var obj = document.getElementById('divProcessing');
//	document.body.removeChild(obj);
//	obj = document.createElement("div");
//	obj.id = "divProcessing";
//	//obj.style = "position:absolute; width:20px; height:20px; display:none; overflow-x:hidden; overflow-y:hidden;"
//	var img = document.createElement("img");
//	img.src = "/fukusi/images/common/processing_circle.gif";
//	obj.appendChild(img);

//	var _x = 400;
//	var _y = document.body.clientHeight / 2 + document.body.scrollTop - 30;
//	obj.style.left = _x;
//	obj.style.top = _y;
//	obj.style.display = "block";

//	document.body.appendChild(obj);






}
/**
 * <pre>
 * 処理中イメージの表示を中止する。
 * <p>
 * </p>
 * </pre>
 *
 * @author Jeon.H.S
 * @version 修正履歴
 *          <ul>
 *          <li>2008/11/22 : 最初作成 (revised by Jeon.H.S)
 *          </ul>
 * @param
 * @return
 */
function comEndPB() {
	var obj = document.getElementById('divProcessing');
	if (obj != null) {
		obj.style.display = "none";
	}

	var p = parent;
	var obj = null;
	for (var i = 0; i < 3; i++) {
		obj = p.document.getElementById('divProcessing');
		if (obj != null) {
			obj.style.display = "none";
		}
		p = p.parent;
	}
}


/**
 * <pre>
 * 処理中イメージ表示する。
 * <p>
 * </p>
 * </pre>
 * @return
 */
function comShowPC() {
	var obj = document.getElementById('divProcessing');
	if (obj != null) {
		var _x = 400;
		var _y = document.body.clientHeight / 2 + document.body.scrollTop - 30;
		obj.style.left = _x;
		obj.style.top = _y;
		obj.style.display = "";
	}
}

function comShowPBwithPos(left, top) {
	var obj = document.getElementById('divProcessing');
	if (obj != null) {
		obj.style.display = "";
		obj.style.left = left;
		obj.style.top = top;
	}
}




// ------------------------------------------------------------------------ 二度押し
// Start
/**
 * 二度押し防止
 */
function check2Click() {

	var i;
	var item = null;
	// 全リンクのクリックイベントを submittableObject_Click で取得する。
	for (i = 0; i < document.links.length; i++) {
		item = document.links[i]
		Object.Aspect.around(item, "onclick", checkLoading);
	}

	// 全ボタンのクリックイベントを submittableObject_Click で取得する。
	for (i = 0; i < document.forms[0].elements.length; i++) {
		item = document.forms[0].elements[i]
		if (item.type == "button" || item.type == "submit"
				|| item.type == "reset") {
			Object.Aspect.around(item, "onclick", checkLoading);
		} else if (item.type == "a") {
			Object.Aspect.around(item, "href", checkLoading);
		}
	}

	Object.Aspect.around(document.forms[0], "submit", showPC);

	return true;
}


var showPC = function (invocation) {

	var obj = document.getElementById('divProcessing');


	if (obj != null) {
		var _x = 400;
		var _y = document.body.clientHeight / 2 + document.body.scrollTop - 30;
		obj.style.left = _x;
		obj.style.top = _y;
		obj.style.display = "block";
	}





	if (obj.addEventListener) {
	// FireFox
		return  invocation.proceed();
	} else if (obj.attachEvent) {
	// IE
		return invocation.method();
	}






//	alert(windowArray.length);
//	if (windowArray != null && windowArray.length != 0) {
//		for (var i = 0; i < windowArray.length; i++) {
//
//			if (windowArray[i].windowArray != null && windowArray[i].windowArray.length != 0) {
//
//				for (var j = 0; j < windowArray.length; j++) {
//					if (windowArray[i].windowArray[j] != null && !windowArray[i].windowArray[j].closed) {
//						windowArray[i].windowArray[j].close();
//					}
//				}
//			}
//
//			if (windowArray[i] != null && !windowArray[i].closed) {
//				windowArray[i].close();
//			}
//		}
//	}

}



// 2度押し抑止アスペクト
var checkLoading = function(invocation) {

	if (invocation.target.noDcCheck !="true" && isDocumentLoading()) {
		//alert("処理中です・・・。");
		//comEndPB();
		//return false;
	}
	var result =  invocation.proceed();

	if (document.readyState != "complete") {
		comEndPB();
	}

	return result;
}

// 画面描画が終わったかどうか
function isDocumentLoading() {
	return (document.readyState != null && document.readyState != "complete");
}

// 画面描画の強制終了
function closeDocumentLoading() {
	document.readyState = "complete";
}

// アスペクト用
Object.Aspect = {
	_around : function(target, methodName, aspect) {
		var method = target[methodName];
		target[methodName] = function() {
			var invocation = {
				"target" :this,
				"method" :method,
				"methodName" :methodName,
				"arguments" :arguments,
				"proceed" : function() {
					if (!method) {
						return true;
					}
					return method.apply(target, this.arguments);
				}
			};
			return aspect.apply(null, [ invocation ]);
		};
	},
	around : function(target, methodName, aspect) {
		this._around(target, methodName, aspect);
	}
}
// ------------------------------------------------------------------------ 二度押し
// End


/**
 * オンラインヘルプファイルを表示する。
 *
 * @param sc_id 画面ID
 */
function showHelp(sc_id) {

	var path = sc_id.substring(0, 6);
	path = path.toLowerCase();

	if (sc_id.substring(0, 6) == '/commo') {
		path = '/common';
	}

	window.open('/common/Menu.do?method=getManualName'
			  + '&flag=help&v_path=' + path + '&sc_id=' + sc_id,
			  	'',
				'width='  + (window.screen.width / 100 * 50) + ', height=' + window.screen.height
			+ ', resizable=1, scrollbars=1, toolbar=0, status=0, menubar=0, top=0'
			+ ', left='   + (window.screen.width - (window.screen.width / 100 * 50)));
}

/**
 * オンラインマニュアルファイルを表示する。
 *
 * @param sc_id 画面ID
 */
function showManual(sc_id) {

	var path = sc_id.substring(0, 6);
	path = path.toLowerCase();

	if (sc_id.substring(0, 6) == '/commo') {
		path = '/common';
	}

	window.open('/common/Menu.do?method=getManualName'
			  + '&flag=manual&v_path=' + path + '&sc_id=' + sc_id,
				'',
				'width=' + (window.screen.width / 100 * 50)	+ ', height=' + window.screen.height
			  + ', resizable=1, scrollbars=1, toolbar=0, status=0, menubar=0, top=0,'
			  + 'left=' + (window.screen.width - (window.screen.width / 100 * 50)));
}


/**
 * 検索履歴を照会してレイヤに表示する。
 * 自動で該当オブジェクトの位置を求めてセットする。
 *
 * @param objname 個人番号照会オブジェクト名
 * @param xPlus   自動にセットされる位置からのプラスX位置（必須ではない）
 * @param yPlus   自動にセットされる位置からのプラスY位置（必須ではない）
 * @param height  高さ（必須ではない）
 * @return
 */
function showSrchHist(objname, xPlus, yPlus, height) {

	xPlus  = xPlus  != null ? Number(xPlus)  : 0;
	yPlus  = yPlus  != null ? Number(yPlus)  : 0;
	height = height != null ? Number(height) : 158;

	var obj = eval(objname);

	frmSrchHist.objname.value = objname;
	frmSrchHist.target = "searchHistory";
	frmSrchHist.action = "/pages/common/pg/copgSrchHist.jsp";
	frmSrchHist.submit();

	document.getElementById("layerSrchHist").style.left = event.clientX + (event.offsetX * -1)
														+ document.body.scrollLeft - 1 + xPlus;
	document.getElementById("layerSrchHist").style.top = event.clientY + (event.offsetY * -1)
													   + document.body.scrollTop + 16 + yPlus;
	document.getElementById("view_td").height = height + 2;

	document.getElementById("layerSrchHistList").style.height = height;
	document.getElementById("layerSrchHist").style.visibility = "visible";

	var onblurSrchHist = function() {
		if ((event.clientX > event.clientX + (event.offsetX * -1) + document.body.scrollLeft - 1 + xPlus)
		 && (event.clientX < event.clientX + (event.offsetX * -1) + document.body.scrollLeft - 1 + xPlus + 211)
		 && (event.clientY > event.clientY + (event.offsetY * -1) + document.body.scrollTop + 16 + yPlus)
		 && (event.clientY < event.clientY + (event.offsetY * -1) + document.body.scrollTop + 16 + yPlus + height)) {
		}
		else {
			document.getElementById("layerSrchHist").style.visibility = "hidden";
		}
	}
	setEventListener(obj, "blur", onblurSrchHist);
}

/**
 * 検索履歴照会画面を隠す。
 *
 * @return
 */
function hideSrchHist() {

	document.getElementById("layerSrchHist").style.visibility = "hidden";
}

/**
 * 検索履歴に個人番号と氏名を追加する。
 *
 * @param icno 個人番号
 * @param nm 氏名
 * @return
 */
function updateSrchHist(icno, nm) {

	if (isEmpty(icno)) {
		return;
	}
	frmSrchHist.icno.value = icno;
	frmSrchHist.kana_nm.value = nm;
	frmSrchHist.target = "sessionUpdate";
	frmSrchHist.action = "/common/User.do?method=updSrchHisInfo";
	frmSrchHist.submit();
}

/**
 * EventListener関数 （cfrm_ini補助関数） イベント呼び出し関数。IE＋FireFox対応
 *
 * @param elm 対象オブジェクト
 * @param type 対象イベント
 * @param listener イベント関数
 * @return
 */
function setEventListener(elm, type, listener) {

	if (elm.addEventListener) {
	// FireFox
		elm.addEventListener(type, function(event) {
			listener()
		}, false);
	} else if (elm.attachEvent) {
	// IE
		elm.attachEvent('on' + type, function (event) {
			listener()
		});
	}
}

/**
 * 必須入力チェック（単体バリデーション）を行う。
 *
 * @param str	チェック対象文字列
 * @param name	対象項目名
 */
function chkRequired(str, name) {

	if (isEmpty(str)) {
		alert(name + "は必須項目です。");
		return false;
	}

	return true;
}

/**
 * カスタムタグ liveany:atena のボタンより宛名検索画面を開く。
 *
 * @param objJumin		個人番号項目
 * @param objName		漢字氏名項目
 * @param objKana		カナ氏名項目
 * @param objYubin		郵便番号項目
 * @param objAddr		住所項目
 * @param objBirth		生年月日項目
 * @param objAge		年齢項目
 * @param objSex		性別項目
 * @param objTno		電話番号項目
 * @param closeFunc		宛名検索画面-選択ボタン押下後実行処理
 */
function openAtenaSrchWin(objJumin, objName, objKana, objYubin, objAddr, objBirth, objAge, objSex, objTno, closeFunc) {

    // --- 1.ポップアップ画面呼び出すパラメータ設定
    var inParam = new Array();
    inParam["icno"] = objJumin.value;	// 個人番号

    // --- 2.戻り値の設定
    var outParam = new Array();
    outParam["icno"]     = objJumin;	// 個人番号項目
    outParam["cc_nm"]    = objName;		// 漢字氏名項目
    outParam["kana_nm"]  = objKana;		// カナ氏名項目
    outParam["zip"]      = objYubin;	// 郵便番号項目
    outParam["cc_ad"]    = objAddr;		// 住所項目
    outParam["bdt"]      = objBirth;	// 生年月日項目
    outParam["age"]      = objAge;		// 年齢項目
    outParam["sex_nm"]   = objSex;		// 性別項目
    outParam["tno"]      = objTno;		// 電話番号項目

    // 宛名検索画面クローズ時実行処理
    if (closeFunc != null) {
    	outParam["on_close_func"] = closeFunc;
    }

    // --- 3.子画面を呼び出す
    openAtenaSrch(inParam, outParam);
}




/*
 * 個人ステータスによりCSSスタイル設定。<br/>
 *
 * 1. DV・ストーカー保護対象者のスタイル設定
 *
 *
 * @param style適用対象のObject
 * @param indvStatus 個人ステータス
*/
function setIndvStatus(obj, indvStatus) {

	if (!isEmpty(indvStatus)) {
		var spIndvStatus = indvStatus.split(" ");
		var isDVStk = false;   // DV・ストーカー保護対象であるかどうか
		for ( var i = 0; i < spIndvStatus.length; i++) {
			if (spIndvStatus[i] == "dvstk") {
				isDVStk = true;
			}
		}

		if (isDVStk) {
		// 個人ステータスがDV・ストーカー保護対象であれば
			setTextStyle(obj, "dvstk", true); // CSSスタイル設定
		} else {
			setTextStyle(obj, "dvstk", false);
		}
	} else {
		setTextStyle(obj, "dvstk", false);
	}
}


/*
 * iFrameにsubmitする。<br/>
 *
 * @param target target名
 * @param url URL
 * @param inParam パラメータ配列（Array）
 * @param useContext true | false ... default:true
*/
function submitIfrm(target, url, inParam, useContext) {

	if (isEmpty(target)) {
		alert("プログラムバグ：submitIfrm()を呼出す際は、Target名は必ずセットしてください。");
		return;
	}

	// コンテキスト指定
	useContext = (useContext != null) ? useContext : true;
	if (useContext == true) {
		url = PATH_CONTEXT + url;
	}

	var frm = document.createElement("form");
	frm.target = target;
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
}

/*
 * 指定した項目のReadonly制御<br/>
 *
 * @param obj 対象オブジェクト
 * @param isReadonly readonly有無
*/
function setReadonly(obj, isReadonly) {

	if (isReadonly) {
		removeClassName(obj, 'input');
		addClassName(obj, 'input_readonly');

		obj.readOnly = 'readonly';

		if (obj.tabIndexBk == undefined || obj.tabIndexBk == null
				|| obj.tabIndexBk == "") {
			obj.tabIndexBk = obj.tabIndex;
		}
		obj.tabIndex = '-1';


		if (obj.onfocusBk == undefined || obj.onfocusBk == null
				|| obj.onfocusBk == "") {
			obj.onfocusBk = obj.onfocus;
		}
		obj.onfocus = '';

		if (obj.onblurBk == undefined || obj.onblurBk == null
				|| obj.onblurBk == "") {
			obj.onblurBk = obj.onblur;
		}
		obj.onblur = '';
	} else {
		removeClassName(obj, 'input_readonly');
		addClassName(obj, 'input');
		obj.readOnly = '';

		if (obj.tabIndexBk != undefined && obj.tabIndexBk != "") {
			obj.tabIndex = obj.tabIndexBk;
		}

		obj.setAttribute("onfocus", obj.onfocusBk);
		obj.setAttribute("onblur", obj.onblurBk);

	}

	// Selectの制御
	var frmName= obj.form.name;

	eval("var pa = document." + frmName + "." + obj.name + "_nm");
	if (pa != undefined && pa != null) {

		if (pa != null && pa.length != undefined && pa.type == undefined) {
			var idx = 0;
			for (var i = 0 ; i < pa.length; i++) {
				if (pa[i] == obj) {
					idx = i;
					break;
				}
			}
			eval("var selObj = document." +frmName+"."+obj.name + "_nm" + "["+ idx +"]");
			if (selObj != undefined && selObj.type == "select-one") {
				selObj.disabled = isReadonly;
			}

		} else {

			eval("var selObj = document." +frmName+"."+obj.name + "_nm");
			if (selObj != undefined && selObj.type == "select-one") {
				selObj.disabled = isReadonly;
			}
		}
	}

	// 日付ボタン制御
	var dateButton = document.getElementById("b_dt_tx_" + obj.name);
	if (dateButton != undefined) {
		if (dateButton != null && dateButton.length != undefined && dateButton.type == undefined) {

			var idx = 0;
			eval("var pa1 = document." + frmName + "." + obj.name + "_nm");
			for (var i = 0 ; i < pa1.length; i++) {
				if (pa1[i] == obj) {
					idx = i;
					break;
				}
			}

			if (isReadonly) {
				dateButton[idx].style.display = "none";
			} else {
				dateButton[idx].style.display = "block";
			}
		} else {
			if (isReadonly) {
				dateButton.style.display = "none";
			} else {
				dateButton.style.display = "block";
			}
		}
	}
}

