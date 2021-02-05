/*=======================================================================
 * liveany-valid.js
 *
 * バリデーションチェック関連JavaScript
 * =======================================================================*/

/**
 * 引数のオブジェクトの値がNULLまたはブランクであるか確認する。
 *
 * @param obj
 *            対象オブジェクト
 * @return boolean
 */
function isEmpty(str) {
	if ((str == null) || (str == undefined) || trim(str) == "") {
		return true;
	} else {
		return false;
	}
}

/**
 * 引数のオブジェクトの値がNULLまたはブランクであるか確認する。
 *
 * @param obj
 *            対象オブジェクト
 * @return boolean
 */
function isEmptyObj(obj) {

	/* if分のtrimがなぜか効かないのでコメントアウト中 */
	if ((obj == null) || (obj == undefined) || (trim("" + obj.value) == "")) {
		return true;
	} else {
		return false;
	}
}

/**
 *
 * 西暦の日付チェック
 *
 * @param str yyyyMMddの日付文字列
 * @return true | false
 */
function isDate(str) {

	if (str == null || str.trim() == '' || str.length != 8) {
		return false;
	}

	var vYear = str.substring(0, 4);
	var vMonth = str.substring(4, 6) - 1;
	var vDay = str.substring(6, 8);

	// 月,日のチェック
	if (vMonth >= 0 && vMonth <= 11 && vDay >= 1 && vDay <= 31) {
		var vDt = new Date(vYear, vMonth, vDay);
		if (isNaN(vDt)) {
			return false;
		} else if (vDt.getMonth() == vMonth
				&& vDt.getDate() == vDay) {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}


/**
 * うるう年（閏年）の判定
 * @param  str 文字列
 * @return true(うるう年) | false(うるう年ではない)
*/
function isLeapYear(year) {
	var nYear = new Number(year);
    return (nYear % 4 == 0 && nYear % 100 != 0) || nYear % 400 == 0;
}

/**
 * うるう日（閏日）の判定
 * @param  str 文字列
 * @return true(うるう日) | false(うるう日ではない)
*/
function isLeapDay(yyyymmdd) {
	if (isLeapYear(yyyymmdd.substr(0, 4)) && "0229" == yyyymmdd.substr(4, 8)) {
		return true;
	} else {
		return false;
	}
}



/**
 * 半角チェック
 * 文字列の中に半角以外の文字が含まれているか調べる
 * 　　　　 半角スペースも半角文字とみなす
 * @param  str 文字列
 * @return true(含まれていない) | false(含まれている)
*/
function isHan(str){
    for (var i=0; i<str.length; i++) {
        /* 1文字ずつ文字コードをエスケープし、その長さが4文字未満なら半角 */
        var len=escape(str.charAt(i)).length;
        if (len<4){
        }else{
            return false;
        }
    }
    return true;
}


/**
 * 数値チェック
 *
 * @param str 文字列
 * @return true(数値のみの場合) | false(数値以外が含まれている場合）
*/
function isNum(str){

	if (str == null) {
		return false;
	}

	str = "" + str ;


//	try {
//		new Number(str);
//	} catch(e) {
//		return false;
//	}

    if (str.match(/[^0-9]/g)){
        return false;
    }
    return true;
}

/**
 * 半角英字チェック
 * 文字列の中に半角英字（a-z,A-Z）以外の文字が含まれているか調べる
 *　　　　　半角スペースも半角英字とみなす
 * @param  str 文字列
 * @return true(含まれていない) | false(含まれている)
*/
function isHanAlpha(str){
    for(var i=0 ; i<str.length; i++){
        var code=str.charCodeAt(i);
        if ((65<=code && code<=90) || (97<=code && code<=122) ||
             str.substr(i,1)==' ') {
            /* 半角英字（a-z,A-Z）の文字コード範囲 */
            /* 半角スペースも許容 */
        }else{
            return false;
        }
    }
    return true;
}

/**
 * 半角英数チェック
 * 文字列の中に半角英字（a-z,A-Z）以外の文字が含まれているか調べる
 *　　　　　半角スペースも半角英字とみなす
 * @param  str 文字列
 * @return true(含まれていない) | false(含まれている)
*/
function isHanAlphaNum(str){
    for (var i=0 ; i<str.length; i++) {
        var code=str.charCodeAt(i);
        /* 文字コードでチェック */
        if ((48<=code && code<=57) ||
            (65<=code && code<=90) || (97<=code && code<=122) ||
             str.substr(i,1)==" ") {
            /* 半角数字（0-9）の文字コード範囲 */
            /* 半角英字（a-z,A-Z）の文字コード範囲 */
            /* 半角スペースも許容 */
        }else{
            return false;
        }
    }
    return true;

}




/**
 * 半角ｶﾅチェック
 * 半角ｶﾅチェックを行う。
 * @param  str 文字列
 * @return true(含まれていない) | false(含まれている)
*/
function isHanKana(str) {
	var result = true;
	var StrReg = "[^";
	StrReg += unescape("%uFF61");
	StrReg += "-" + unescape("%uFF9F");
	StrReg += " ]";
	var ObjReg = new RegExp(StrReg);

	return !ObjReg.test(str);
}


/**
 * 半角ｶﾅ、半角スペース、ローマ字チェック
 * 半角ｶﾅ、半角スペース、ローマ字チェックを行う。
 * @param  str 文字列
 * @return true(正常) | false(エラー)
*/
function isHanKanaAlpha(str) {
	var result = true;
	var StrReg = "[^";
	StrReg += unescape("%uFF61");
	StrReg += "-" + unescape("%uFF9F");
	StrReg += " A-Za-z]";
	var ObjReg = new RegExp(StrReg);

	return !ObjReg.test(str);
}




/**
 * 全角チェック
 * 文字列の中に全角以外の文字が含まれているか調べる
 * 　　　　 全角スペースも全角とみなす
 * @param obj テキストボックスオブジェクト
 * @return true(含まれていない) | false(含まれている)
*/
function isZen(str){

    for(var i=0; i<str.length; i++){
        /* 1文字ずつ文字コードをエスケープし、その長さが4文字以上なら全角 */
        var len=escape(str.charAt(i)).length;
        if(!(len>=4)){
        	 return false;
        }

    }
    return true;
}



/**
 * 入力値に全角数字があるのか確認する。
 *
 * @param str
 *            入力された値
 * @return {boolean}
 */
function isZenNum(str) {
	if (str.match(/[^０-９]/g)){
		return false;
	} else {
		return true;
	}
}


/**
 * 引数が全角英数であるかチェックする。
 * @param value 文字列
 * @return true | false
 */
function isZenAlpaNum(str) {

	if (str.match(/[^ａ-ｚＡ-Ｚ０-９]/g)){
		return false;
	} else {
		return true;
	}

}

/**
 * 引数が全角英字であるかチェックする。
 * @param value 文字列
 * @return true | false
 */
function isZenAlpa(value) {

	if (value.match(/[^ａ-ｚＡ-Ｚ]/g)){
		return false;
	} else {
		return true;
	}
}

/**
 * 数値チェックを行う。
 * @param str
 * @return
 */
function isDecimal(str){
	if (str){
		if (str.match(/^(\+|-)?[\d,]*\.?[\d]*$/)){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
}

/**
 *  全角カナチェック
 *  文字列の中に全角カナが含まれるか調べる
 * @param  obj テキストボックスオブジェクト
 * @return true(含まれている) | false(含まれていない)
*/
function isZenKana(str) {
	if (str.match(/[^ア-ンァ-ョ]/g)){
		return false;
	} else {
		return true;
	}
	// var zen="アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲンァィゥェォッャュョ";
}




/**
 * チェック対象文字列が全角ひらがなのみかチェックします。
 *
 * @param argValue チェック対象文字列
 * @return 全て全角ひらがなの場合はtrue、
 * 全角ひらがな以外の文字が含まれている場合はfalse
 */
function isZenHira(argValue) {
    if (argValue.match(/[^あ-ん|^ー]/g)) {
        return false;
    } else {
        return true;
    }
}



/**
 * 入力するkey codeが補助キーかを判断する。
 *
 * @return {boolean}
 */
function is_keycode_subkey() {
	var keynum = event.keyCode;
	return (	(keynum >= 0 && keynum <= 31) 			//
				//|| (keynum >= 33 && keynum <= 45)		//
				//|| (keynum >= 112 && keynum <= 123)		// F1 ～ F12
				//|| (keynum >= 91 && keynum <= 92) 		// 左Windowsキー, 右Windowsキー
				|| (keynum >= 144 && keynum <= 145));	// Num Lock ～ Scroll Lock
}

/**
 * 入力された文字が数字であるか判断する。
 *
 * @return {boolean}
 */
function is_keycode_number() {
	if (event.shiftKey)
		return false;
	if (event.ctrlKey)
		return true;
	return (((event.keyCode >= 48) && (event.keyCode <= 57)));
}

/**
 * 入力された文字が英文であるか判断する。
 *
 * @return {boolean}
 */
function is_keycode_alphabet() {
	return (((event.keyCode >= 65) && (event.keyCode <= 90))
			|| ((event.keyCode >= 97) && (event.keyCode <= 122)) );
}



/**
 * 入力された文字が数字や . で構成されているかを判断する。
 *
 * @return {boolean}
 */
function is_keycode_amount() {

	var obj = event.srcElement;
	if (!is_keycode_number() && event.keyCode != 46)
	// .(46)
		return false;

	var val = obj.value;
	if (event.keyCode == 46) {
		if (val.length <= 0 || val.indexOf('.') >= 0)
			return false;
	}
	if (val.indexOf('.') == 0)
		obj.value = "0" + val;

	return true;
}

/**
 * 入力された文字が 数字や ,. -で構成されているか判断する。
 *
 * 使用したキーコード  .(46)  ,(44)  -(45)
 * @return {boolean}
 */
function is_keycode_decimal() {
	var obj = event.srcElement;

	if (event.keyCode == 45) {
	// - の場合
		if (obj.value.length == 0 || (isDecimal(obj.value) && obj.value.indexOf('-') < 0)) {
			return true;
		} else {
			return false;
		}
	}

	if (!is_keycode_number() && event.keyCode != 44 && event.keyCode != 46)
		return false;

	var val = obj.value;
	if (event.keyCode == 44 && val.length <= 0)
		return false;
	else if (event.keyCode == 46
			|| event.keyCode == 44) {
		if (val.length <= 0 || val.indexOf('.') >= 0)
			return false;
	}

	if (val.indexOf('.') == 0)
		obj.value = "0" + val;

	return true;
}




