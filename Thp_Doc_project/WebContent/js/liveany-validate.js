/**
 * formFieldに値が入力されたかを判断する。
 *
 * @param formField
 *            入力された内容がある Form Element object type オブジェクト
 * @param fieldLabel
 *            Form Element オブジェクトの画面に表示されるString type名
 * @return {boolean}
 */

function validRequired(formField, fieldLabel) {
	var result = true;
	if (formField.value == "") {
		alert('Please enter a value for the "' + fieldLabel + '" field.');
		result = false;
	}
	return result;
}

/**
 * formFieldの内容が電子郵便住所で有効かを判断する。
 *
 * @param formField
 *            入力された内容がある Form Element object type オブジェクト
 * @param fieldLabel
 *            Form Element オブジェクトの画面に表示されるString type名
 * @param required
 *            必須入力の可否を知らせるboolean typeの オブジェクト
 * @return {boolean}
 */

function validEmail(formField, fieldLabel, required) {
	alert(formField.value);
	var result = true;
	if (required && !validRequired(formField, fieldLabel))
		result = false;
	if (result
			&& ((formField.value.length < 3) || !is_EmailAddr(formField.value))) {
		alert("Please enter a complete email address in the form: "
				+ "yourname@yourdomain.com");
		result = false;
	}
	return result;
}

/**
 * formFieldの内容が数字で有効かを判断する。
 *
 * @param formField
 *            入力された内容がある Form Element object type オブジェクト
 * @param fieldLabel
 *            Form Element オブジェクトの画面に表示される String type名
 * @param required
 *            必須入力の可否を知らせるboolean typeの オブジェクト
 * @return {boolean}
 */

function validNum(formField, fieldLabel, required) {
	var result = true;
	if (required && !validRequired(formField, fieldLabel))
		result = false;
	if (result) {
		if (!is_AllDigits(formField.value)) {
			alert('Please enter a number for the "' + fieldLabel + '" field.');
			result = false;
		}
	}
	return result;
}

/**
 * formFieldの内容が整数で有効かを判断する。
 *
 * @param formField
 *            入力された内容がある Form Element object type オブジェクト
 * @param fieldLabel
 *            Form Element オブジェクトの画面に表示される String type名
 * @param required
 *            必須入力の可否を知らせるboolean typeのオブジェクト
 * @return {boolean}
 */

function validInt(formField, fieldLabel, required) {
	var result = true;
	if (required && !validRequired(formField, fieldLabel))
		result = false;
	if (result) {
		var num = parseInt(formField.value, 10);
		if (isNaN(num)) {
			alert('Please enter a number for the "' + fieldLabel + '" field.');
			result = false;
		}
	}
	return result;
}

/**
 * formFieldの内容が日時形態で有効かを判断する。
 *
 * @param formField
 *            入力された内容がある Form Element object type オブジェクト
 * @param fieldLabel
 *            Form Element オブジェクトの画面に表示される String type名
 * @param required
 *            必須入力の可否を知らせるboolean typeのオブジェクト
 * @return {boolean}
 */

function validDate(formField, fieldLabel, required) {
	var result = true;
	if (required && !validRequired(formField, fieldLabel))
		result = false;
	if (result) {
		var elems = formField.value.split("/");
		result = (elems.length == 3); // should be three components
		if (result) {
			var year = parseInt(elems[0], 10);
			var month = parseInt(elems[1], 10);
			var day = parseInt(elems[2], 10);
			result = is_AllDigits(elems[0]) && (month > 0) && (month < 13)
					&& is_AllDigits(elems[1]) && (day > 0) && (day < 32)
					&& is_AllDigits(elems[2])
					&& ((elems[2].length == 2) || (elems[2].length == 4));
		}

		if (!result) {
			alert('Please enter a date in the format YYYY/MM/DD for the "' + fieldLabel + '" field.');
		}
	}
	return result;
}

/**
 * formFieldの内容がひらがなで有効かを判断する。
 *
 * @param formField
 *            入力された内容がある Form Element object type オブジェクト
 * @param fieldLabel
 *            Form Element オブジェクトの画面に表示されるString type名
 * @param required
 *            必須入力の可否を知らせるboolean typeの オブジェクト
 * @return {boolean}
 */

function validHirakana(formField, fieldLabel, required) {
	var result = true;
	if (required && !validRequired(formField, fieldLabel))
		result = false;
	if (result) {
		var StrReg = "[^";
		StrReg += unescape("%u3041");
		StrReg += "-" + unescape("%u3093");
		StrReg += " ]";
		var ObjReg = new RegExp(StrReg);
		result = !ObjReg.test(formField.value);
		if (!result) {
			alert('Please enter a Hirakana for the "' + fieldLabel + '" field.');
		}
	}
	return result;
}

/**
 * formFieldの内容が半角カタカナで有効かを判断する。
 *
 * @param formField
 *            入力された内容がある Form Element object type オブジェクト
 * @param fieldLabel
 *            Form Element オブジェクトの画面に表示されるString type名
 * @param required
 *            必須入力の可否を知らせるboolean typeのオブジェクト
 * @return {boolean}
 */

function validHalfWidthKatakana(formField, fieldLabel, required) {
	var result = true;
	if (required && !validRequired(formField, fieldLabel))
		result = false;
	if (result) {
		var StrReg = "[^";
		StrReg += unescape("%uFF61");
		StrReg += "-" + unescape("%uFF9F");
		StrReg += " ]";
		var ObjReg = new RegExp(StrReg);

		result = !ObjReg.test(formField.value);

		if (!result) {
			alert('Please enter a half-width Katakana for the "' + fieldLabel + '" field.');
		}
	}
	return result;
}

/**
 * formFieldの内容が全角カタカナで有効かを判断する。
 *
 * @param formField
 *            入力された内容がある Form Element object type オブジェクト
 * @param fieldLabel
 *            Form Element オブジェクトの画面に表示されるString type 名
 * @param required
 *            必須入力の可否を知らせるboolean typeの オブジェクト
 * @return {boolean}
 */

function validFullWidthKatakana(formField, fieldLabel, required) {
	var result = true;
	if (required && !validRequired(formField, fieldLabel))
		result = false;

	if (result) {
		var StrReg = "[^";
		StrReg += unescape("%u30A1");
		StrReg += "-" + unescape("%u30F6");
		StrReg += " ]";
		var ObjReg = new RegExp(StrReg);
		result = !ObjReg.test(formField.value);
		if (!result) {
			alert('Please enter a full-width Katakana for the "' + fieldLabel + '" field.');
		}
	}
	return result;
}

/**
 * emailに 来る値が電子郵便住所で有効かを判断する。(validEmail 補助 ファンクション)
 *
 * @param email
 *            入力された値
 * @return {boolean}
 */
function is_EmailAddr(email) {
	var result = false;
	var theStr = new String(email);
	var index = theStr.indexOf("@");
	if (index > 0) {
		var pindex = theStr.indexOf(".", index);
		if ((pindex > index + 1) && (theStr.length > pindex + 1))
			result = true;
	}
	return result;
}

/**
 * strに来る値が数字形態で有効かを判断する。(validNum 補助 ファンクション)
 *
 * @param str
 *            入力された値
 * @return {boolean}
 */
function is_AllDigits(str) {
	return is_ValidCharSet(str, "0123456789");
}

/**
 * 入力値が特定文字(chars)のみになっているかを判断する。
 *
 * @param objValue
 *            入力された値
 * @param charset
 *            確認するキャラクターセット
 * @return {boolean}
 */
function is_ValidCharSet(objValue, charset) {
	var result = true;
	// Note: doesn't use regular expressions to avoid early Mac browser bugs
	for ( var i = 0; i < objValue.length; i++) {
		if (charset.indexOf(objValue.substr(i, 1)) < 0) {
			result = false;
			break;
		}
	}
	return result;
}

/**
 * 入力するフィールドが漢字かを判断する。
 *
 * @param obj
 *            入力された内容がある Form Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_kanji(obj) {
	var StrReg = "[^";
	StrReg += unescape("%u4E00");
	StrReg += "-" + unescape("%u9FA0");
	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += "　]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 文字列が全て全角英字で構成されているかを判断する。
 *
 * @param obj
 *            入力された内容がある Form Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_full_alphabet(obj) {
	var StrReg = "[^";
	StrReg += unescape("%uFF21");
	StrReg += "-" + unescape("%uFF5E");
	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += "　]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 文字列が全て半角英字で構成されているかを判断する。
 *
 * @param obj
 *            入力された内容がある Form Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_half_alphabet(obj) {
	var StrReg = "[^a-zA-Z";
	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += " ]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 文字列が全て数字や半角英字で構成されているかを判断する。
 *
 * @param obj
 *            入力された内容がある Form Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_number_half_alphabet(obj) {
	var StrReg = "[^a-zA-Z0-9";
	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += " ]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 文字列が全て数字で構成されているかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_number(obj) {
	var StrReg = "[^0-9";
	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += "]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 文字列が全て数字とコンマで構成されているかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_nc(obj) {
	var StrReg = "[^0-9,";
	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += "]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 文字列が全て数字と空白で構成されているかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_ns(obj) {
	var StrReg = "[^0-9 ";
	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += "]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 文字列が全て数字とマイナスで構成されているかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_negative_number(obj) {
	var StrReg = "[^0-9,-]";
	var ObjReg = new RegExp(StrReg);

	var str = obj.value;
	var rtn = !ObjReg.test(obj.value)

	if (str.indexOf("-") > 0)
		rtn = false;
	return rtn;
}

/**
 * 文字列が全て数字とマイナスで構成されているかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_telephone_number(obj) {
	var StrReg = "[^0-9,-]";
	var ObjReg = new RegExp(StrReg);

	var str = obj.value;
	var rtn = !ObjReg.test(obj.value)

	return rtn;
}

/**
 * 文字列が全て数字や小数点又はコンマで構成されているかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_ncf(obj) {
	var StrReg = "[^0-9,.";
	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += "]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 文字列が全て数字と '.'で構成されているかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_float_number(obj) {
	var StrReg1 = "[^0-9.";
	if (obj.cha_ext != null)
		StrReg1 += obj.cha_ext;
	StrReg1 += "]";
	var ObjReg1 = new RegExp(StrReg1);
	if (ObjReg1.test(obj.value)) {
		alert("1");
		return false;
	}

	/*
	 * var ObjReg2 = /[^0.]/;
	 *
	 * if (obj.value.length > 0 && obj.value != '0' && !ObjReg2.test(obj.value))
	 * return false;
	 */

	if (obj.value.length > 1 && obj.value.charAt(0) == '0'
			&& obj.value.charAt(1) != '.')
		return false;

	if (obj.value.length > 0 && obj.value.charAt(obj.value.length - 1) == '.')
		return false;

	var i = 0;
	var cnt = 0;
	for (i = 0; i < obj.value.length; i++) {
		if (obj.value.charAt(i) == '.') {
			if (cnt > 0)
				return false;
			else if (obj.value.indexOf('.') == 0)
				return false;
			cnt++;
		}
	}

	return is_maxlength(obj, obj.len_chk);
}

/**
 * 入力するフィールドが全角カタカナかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_full_katakana(obj) {
	var StrReg = "[^";
	StrReg += unescape("%u30A1");
	StrReg += "-" + unescape("%u30F6");
	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += "　]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 入力するフィールドが外字かを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_gaiji(obj) {
	var StrReg = "[^";
	StrReg += unescape("%ue000");
	StrReg += "-" + unescape("%ue757");
	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += "　]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 入力するフィールドが半角カタカナかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_half_katakana(obj) {
	var StrReg = "[^";
	StrReg += unescape("%uFF61");
	StrReg += "-" + unescape("%uFF9F");
	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += " ]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 入力するフィールドがひらがなかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_hirakana(obj) {
	var StrReg = "[^";
	StrReg += unescape("%u3041");
	StrReg += "-" + unescape("%u3093");
	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += "　]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 入力するフィールドが半角かを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_half_width(obj) {
	if (obj.value == null || obj.value.length <= 0)
		return true;

	var StrReg = "[^";
	// StrReg += "a-zA-Z0-9`~!@#%&;:_='.<>/^[$|()?+{},*\"\\-\\]\\\\";
	StrReg += unescape("%u0020");
	StrReg += "-" + unescape("%u007E");
	StrReg += unescape("%uFF61");
	StrReg += "-" + unescape("%uFF9F");

	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += " ]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 入力するフィールドが全角かどうかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_full_width(obj) {
	if (obj.value == null || obj.value.length <= 0)
		return true;

	var StrReg = "[";
	// StrReg += "a-zA-Z0-9`~!@#%&;:_='.<>/^[$|()?+{},*\"\\-\\]\\\\";
	StrReg += unescape("%u0020");
	StrReg += "-" + unescape("%u007E");
	StrReg += unescape("%uFF61");
	StrReg += "-" + unescape("%uFF9F");

	if (obj.cha_ext != null)
		StrReg += obj.cha_ext;
	StrReg += " ]";
	var ObjReg = new RegExp(StrReg);
	return !ObjReg.test(obj.value);
}

/**
 * 入力するフィールドの長さが最大の長さ以内かを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_maxlength(obj, len) {

	if (obj.value == null)
		return true;

	var value = obj.value.replace(/[,]/g, "");
	var pos1 = len.indexOf('.');
	var pos2 = value.indexOf('.');
	if (pos1 > 0) {
		var f = len.substring(pos1 + 1);
		var i = len.substring(0, pos1) - f;
		if (pos2 < 0) {
			if (i < value.length)
				return false;
		} else {
			if (i < pos2)
				return false;
			else if (f < value.length - pos2 - 1)
				return false;
		}

		return true;
	} else
		return (value.length <= len);
}

/**
 * 入力するフィールドが空白ではないかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_required(obj) {
	return !(obj.value == "");
}

/**
 * 入力するkey codeが全て数字で構成されているかを判断する。
 *
 * @return {boolean}
 */
function is_keycode_number() {
	var obj = event.srcElement;
	var ascii = get_ascii_from_keycode(event.shiftKey, event.keyCode);
	if (obj.cha_ext != null && ascii != null && is_InString(ascii, obj.cha_ext))
		return true;

	if (event.shiftKey)
		return false;

	if (event.ctrlKey)
		return true;

	return (((event.keyCode >= 48) && (event.keyCode <= 57)) || ((event.keyCode >= 96) && (event.keyCode <= 105)));
}

/**
 * 入力するkey codeが全て数字と , で構成されているかを判断する。
 *
 * @return {boolean}
 */
function is_keycode_nc() {
	var obj = event.srcElement;
	if (!is_keycode_number() && event.keyCode != 188)
		return false;

	var val = obj.value;
	if (event.keyCode == 188 && val.length <= 0)
		return false;

	return true;
}

/**
 * 入力するkey codeが全て数字とマイナスで構成されているかを判断する。
 *
 * @return {boolean}
 */
function is_keycode_nn() {

	var obj = event.srcElement;

	if (is_keycode_subkey() && !is_keycode_alt_ctrl())
		return false;

	if (!is_keycode_number() && event.keyCode != 189 && event.keyCode != 109)
		return false;

	var val = obj.value;
	if (event.keyCode == 189 && event.keyCode == 109)
		return false;

	return true;
}

/**
 * 入力するkey codeが 全て数字と ,. で構成されているかを判断する。
 *
 * @return {boolean}
 */
function is_keycode_ncf() {
	var obj = event.srcElement;
	if (!is_keycode_number() && event.keyCode != 188 && event.keyCode != 190
			&& event.keyCode != 110)
		return false;

	var val = obj.value;
	if (event.keyCode == 188 && val.length <= 0)
		return false;
	else if (event.keyCode == 190 || event.keyCode == 110
			|| event.keyCode == 188) {
		if (val.length <= 0 || val.indexOf('.') >= 0)
			return false;
	}

	if (val.indexOf('.') == 0)
		obj.value = "0" + val;

	return true;
}

/**
 * 入力するkey codeが全て数字と . で構成されているかを判断する。
 *
 * @return {boolean}
 */
function is_keycode_float_number() {
	var obj = event.srcElement;
	if (!is_keycode_number() && event.keyCode != 190 && event.keyCode != 110)
		return false;

	var val = obj.value;
	if (event.keyCode == 190 || event.keyCode == 110) {
		if (val.length <= 0 || val.indexOf('.') >= 0)
			return false;
	}
	if (val.indexOf('.') == 0)
		obj.value = "0" + val;

	return true;
}

/**
 * 入力するkey codeが全て英文で構成されているかを判断する。
 *
 * @return {boolean}
 */
function is_keycode_alphabet() {
	var obj = event.srcElement;
	var ascii = get_ascii_from_keycode(event.shiftKey, event.keyCode);
	if (obj.cha_ext != null && ascii != null && is_InString(ascii, obj.cha_ext))
		return true;

	return !((event.keyCode < 64) || (event.keyCode > 91));
}

/**
 * 入力するkey codeが補助キーかを判断する。
 *
 * @return {boolean}
 */
function is_keycode_subkey() {
	var keynum = event.keyCode;
	return ((keynum >= 0 && keynum <= 31) || (keynum >= 33 && keynum <= 46)
			|| (keynum >= 112 && keynum <= 123)
			|| (keynum >= 91 && keynum <= 93) || (keynum >= 144 && keynum <= 145));
}

/**
 * alt key 及び ctrl keyが 押された状態かを判断する。
 *
 * @return {boolean}
 */
function is_keycode_alt_ctrl() {
	return (event.altKey || event.ctrlKey);
}

/**
 * 入力するフィールドがdateかどうかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_date(obj) {
	var rslt = true;
	if (obj.value.length == 0)
		return true;

	var objval = obj.value;
	objval = trim(objval);
	objval = replaceAll(objval, ' ', '0');
	objval = replaceAll(objval, '.', '');
	objval = ad2jaSno(ja2ad(objval));

	if (rslt)
		rslt = (objval.length >= 3 && objval.length % 2 == 1);
	if (rslt)
		rslt = is_num(objval);
	if (rslt)
		rslt = is_ad_year(objval);
	if (rslt)
		rslt = is_ad_date(objval);
	return rslt;
}

/**
 * 入力するフィールドがYYY形式の年度かどうかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_date_YYY(obj) {
	var rslt = true;
	if (obj.value.length == 0)
		return true;
	else if (obj.value.length < 3)
		return false;

	var objval = obj.value;
	objval = trim(objval);
	objval = replaceAll(objval, ' ', '0');
	objval = replaceAll(objval, '.', '');
	objval = ad2jaSno(ja2ad(objval));

	if (rslt)
		rslt = (objval.length == 3);
	if (rslt)
		rslt = is_num(objval);
	if (rslt)
		rslt = is_ad_year(objval);
	if (rslt)
		rslt = is_ad_date(objval);
	return rslt;
}

/**
 * 入力するフィールドがYYYMM形式の日付かどうかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_date_YYYMM(obj) {
	var rslt = true;
	if (obj.value.length == 0)
		return true;

	var objval = obj.value;
	objval = trim(objval);
	objval = replaceAll(objval, ' ', '0');
	objval = replaceAll(objval, '.', '');
	objval = ad2jaSno(ja2ad(objval));

	if (rslt)
		rslt = (objval.length == 5);
	if (rslt)
		rslt = is_num(objval);
	if (rslt)
		rslt = is_ad_year(objval);
	if (rslt)
		rslt = is_ad_date(objval);
	return rslt;
}

/**
 * 入力するフィールドがYYYMMDD形式の日付かどうかを判断する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function is_obj_date_YYYMMDD(obj) {
	var rslt = true;
	if (obj.value.length == 0)
		return true;

	var objval = obj.value;
	objval = trim(objval);
	objval = replaceAll(objval, ' ', '0');
	objval = replaceAll(objval, '.', '');
	objval = ad2jaSno(ja2ad(objval));

	if (rslt)
		rslt = (objval.length == 7);
	if (rslt)
		rslt = is_num(objval);
	if (rslt)
		rslt = is_ad_year(objval);
	if (rslt)
		rslt = is_ad_date(objval);
	return rslt;
}

/**
 * is_obj_date 補助 ファンクション
 *
 * @param jdate
 *            入力された値
 * @return {boolean}
 */
function is_ad_year(jdate) {
	var f;
	var tmp_win = this.top;

	if (tmp_win.opener == null) {
		f = tmp_win.top.frames[0].frames[0].frmWareki;
	} else {
		tmp_win = tmp_win.opener;
		while (true) {
			if (tmp_win.parent != null)
				tmp_win = tmp_win.parent;
			if (tmp_win.opener == null) {
				f = tmp_win.top.frames[0].frames[0].frmWareki;
				break;
			} else {
				tmp_win = tmp_win.opener;
			}
		}
	}

	f = getWarekiFrame();

	var j_year_key = jdate.substring(0, 1);
	var j_year_value = jdate.substring(1, 3);
	var jdate_len = jdate.length;

	var len = f.wa_row.value;

	var sno = '';
	var jyy = '';
	var from_yy = '';
	var to_yy = '';
	var from_yymm = '';
	var to_yymm = '';
	var from_dt = '';
	var to_dt = '';
	var wa_max;

	if (len == 1) {
		sno = f.wa_sno.value;
		jyy = f.wa_jyy.value;
		from_yy = f.wa_from_yy.value;
		to_yy = f.wa_to_yy.value;
		from_yymm = f.wa_from_yymm.value;
		to_yymm = f.wa_to_yymm.value;
		from_dt = f.wa_from_dt.value;
		to_dt = f.wa_to_dt.value;

		if (jdate_len == 3) {
			if (j_year_key == sno || j_year_key == jyy) {
				if (parseInt(j_year_value, 10) <= (parseInt(to_yy, 10)
						- parseInt(from_yy, 10) + 1)) {
					return true;
				}
			}
		} else if (jdate_len == 5) {
			if (j_year_key == sno || j_year_key == jyy) {
				wa_max = parseInt(to_yymm.substring(0, 4), 10)
						- parseInt(from_yymm.substring(0, 4), 10) + 1;
				if (parseInt(j_year_value, 10) <= wa_max) {
					if (parseInt(j_year_value, 10) == wa_max
							&& parseInt(jdate.substring(3, 5), 10) > parseInt(
									to_dt.substring(4, 6), 10)) {
						return false;
					}
					if (parseInt(j_year_value, 10) == 1
							&& parseInt(jdate.substring(3, 5), 10) < parseInt(
									from_dt.substring(4, 6), 10)) {
						return false;
					}
					return true;
				}
			}
		} else {
			if (j_year_key == sno || j_year_key == jyy) {
				wa_max = parseInt(to_dt.substring(0, 4), 10)
						- parseInt(from_dt.substring(0, 4), 10) + 1;
				if (parseInt(j_year_value, 10) <= wa_max) {
					if (parseInt(j_year_value, 10) == wa_max
							&& parseInt(jdate.substring(3, 7), 10) > parseInt(
									to_dt.substring(4, 8), 10)) {
						return false;
					}
					if (parseInt(j_year_value, 10) == 1
							&& parseInt(jdate.substring(3, 7), 10) < parseInt(
									from_dt.substring(4, 8), 10)) {
						return false;
					}
					return true;
				}
			}
		}
	} else {
		for ( var i = 0; i < len; i++) {
			sno = f.wa_sno[i].value;
			jyy = f.wa_jyy[i].value;
			from_yy = f.wa_from_yy[i].value;
			to_yy = f.wa_to_yy[i].value;
			from_yymm = f.wa_from_yymm[i].value;
			to_yymm = f.wa_to_yymm[i].value;
			from_dt = f.wa_from_dt[i].value;
			to_dt = f.wa_to_dt[i].value;

			if (jdate_len == 3) {
				if (j_year_key == sno || j_year_key == jyy) {
					if (parseInt(j_year_value, 10) <= (parseInt(to_yy, 10)
							- parseInt(from_yy, 10) + 1)) {
						return true;
					}
				}
			} else if (jdate_len == 5) {
				if (j_year_key == sno || j_year_key == jyy) {
					wa_max = parseInt(to_yymm.substring(0, 4), 10)
							- parseInt(from_yymm.substring(0, 4), 10) + 1;
					if (parseInt(j_year_value, 10) <= wa_max) {
						if (parseInt(j_year_value, 10) == wa_max
								&& parseInt(jdate.substring(3, 5), 10) > parseInt(
										to_yymm.substring(4, 6), 10)) {
							return false;
						}
						if (parseInt(j_year_value, 10) == 1
								&& parseInt(jdate.substring(3, 5), 10) < parseInt(
										from_yymm.substring(4, 6), 10)) {
							return false;
						}
						return true;
					}
				}
			} else {
				if (j_year_key == sno || j_year_key == jyy) {
					wa_max = parseInt(to_dt.substring(0, 4), 10)
							- parseInt(from_dt.substring(0, 4), 10) + 1;
					if (parseInt(j_year_value, 10) <= wa_max) {
						if (parseInt(j_year_value, 10) == wa_max
								&& parseInt(jdate.substring(3, 7), 10) > parseInt(
										to_dt.substring(4, 8), 10)) {
							return false;
						}
						if (parseInt(j_year_value, 10) == 1
								&& parseInt(jdate.substring(3, 7), 10) < parseInt(
										from_dt.substring(4, 8), 10)) {
							return false;
						}
						return true;
					}
				}
			}
		}
	}
	return false;
}

/**
 * is_obj_date 補助 ファンクション
 *
 * @param jdate
 *            入力された値
 * @return {boolean}
 */
function is_ad_date(jdate) {

	if (jdate.length < 5)
		return true;

	if (jdate.length == 5) {
		return is_valid_month(jdate.substring(3, 5));
	}

	if (jdate.length == 7) {
		var j_year_key = jdate.substring(0, 1);
		var j_year_value = jdate.substring(1, 3);
		var init_year = get_ad_year(j_year_key);
		var ad_year = eval(init_year) + eval(j_year_value);
		ad_year = ad_year + "" + jdate.substring(3, 7);
		return is_valid_date(ad_year);
	}
}

/**
 * is_ad_date 補助 ファンクション
 *
 * @param j_year
 *            入力された値
 * @return {number}
 */
function get_ad_year(j_year) {
	var f;
	var tmp_win = this.top;

	if (tmp_win.opener == null) {
		f = tmp_win.top.frames[0].frames[0].frmWareki;
	} else {
		tmp_win = tmp_win.opener;
		while (true) {
			if (tmp_win.parent != null)
				tmp_win = tmp_win.parent;
			if (tmp_win.opener == null) {
				f = tmp_win.top.frames[0].frames[0].frmWareki;
				break;
			} else {
				tmp_win = tmp_win.opener;
			}
		}
	}

	f = getWarekiFrame();
	var len = f.wa_row.value;

	var sno = '';
	var jyy = '';
	var from_dt = '';

	if (len == 1) {
		sno = f.wa_sno.value;
		jyy = f.wa_jyy.value;
		from_dt = f.wa_from_dt.value;

		if (j_year == sno || j_year == jyy) {
			return parseInt(from_dt.substring(0, 4), 10) - 1;
		}
	} else {
		for ( var i = 0; i < len; i++) {
			sno = f.wa_sno[i].value;
			jyy = f.wa_jyy[i].value;
			from_dt = f.wa_from_dt[i].value;
			to_dt = f.wa_to_dt[i].value;

			if (j_year == sno || j_year == jyy) {
				return parseInt(from_dt.substring(0, 4), 10) - 1;
			}
		}
	}

	return 0;

}

/**
 * 有効な日かを確認する。
 *
 * @param yyyy
 *            年度(4桁)
 * @param mm
 *            月(2桁)
 * @param dd
 *            日(2桁)
 * @return {boolean}
 */
function is_valid_day(yyyy, mm, dd) {
	var m = parseInt(mm, 10) - 1;
	var d = parseInt(dd, 10);

	var end = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	if ((yyyy % 4 == 0 && yyyy % 100 != 0) || yyyy % 400 == 0)
		end[1] = 29;

	return (d >= 1 && d <= end[m]);
}

/**
 * 有効な日かを確認する。
 *
 * @param yyyy
 *            年度(4桁)
 * @param mm
 *            月(2桁)
 * @param dd
 *            日(2桁)
 * @return {boolean}
 */
function is_valid_dates(yyyymmdd) {

	if (isNaN(yyyymmdd))
		return false;

	if (yyyymmdd.length == 8) {
		var yyyy = yyyymmdd.substring(0, 4);
		var mm = yyyymmdd.substring(4, 6);
		var dd = yyyymmdd.substring(6, 8);

		var m = parseInt(mm, 10);

		if (m > 12)
			return false;

		var d = parseInt(dd, 10);

		var end = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
		if ((yyyy % 4 == 0 && yyyy % 100 != 0) || yyyy % 400 == 0)
			end[1] = 29;

		return (d >= 1 && d <= end[m - 1]);
	}

	return true;
}

/**
 * 有効な月かを確認する。
 *
 * @param mm
 *            月(2桁)
 * @return {boolean}
 */
function is_valid_month(mm) {
	var m = parseInt(mm, 10);

	return (m >= 1 && m <= 12);
}

/**
 * 有効な日付(Date)かを確認する。 (1866年 ~)
 *
 * @param objValue
 *            年月日('YYYYMMDD')
 * @return {boolean}
 */
function is_valid_date(objValue) {
	if (!is_num(objValue) || objValue.length < 8)
		return false;

	year = objValue.substring(0, 4);
	month = objValue.substring(4, 6);
	day = objValue.substring(6, 8);

	if (parseInt(year, 10) >= 1866 && is_valid_month(month)
			&& is_valid_day(year, month, day))
		return true;

	return false;
}

/**
 * 有効な時間(Time)かを確認する。
 *
 * @param objValue
 *            時分初('HHMIDD')
 * @return {boolean}
 */
function is_valid_time(objValue) {
	if (!is_num(objValue))
		return false;

	hour = objValue.substring(0, 2);
	min = objValue.substring(2, 4);
	if (objValue.length == 6)
		sec = objValue.substring(4, 6);
	else
		sec = 0;

	if (hour < 24 && min < 60 && sec < 60)
		return true;

	return false;
}

/**
 * 入力値に数字と . のみあるかを確認する。
 *
 * @param objValue
 *            入力された値
 * @return {boolean}
 */
function is_num(objValue) {
	var chars = "0123456789.";

	if (objValue == null || objValue == "")
		return false;

	return is_ValidCharSet(objValue, chars);
}

/**
 * 入力値に数字かを確認する。
 *
 * @param objValue
 * @return {boolean}
 */
function is_NumNoZ(objValue) {
	var chars = "0123456789";

	if (objValue == null || objValue == "" || objValue == "0")
		return false;

	return is_ValidCharSet(objValue, chars);
}

/**
 * 入力値にNULLかを確認する。
 *
 * @param objValue
 *            入力された値
 * @return {boolean}
 */
function is_null(objValue) {
	if (objValue == null || objValue == "")
		return true;

	return false;
}

/**
 * 入力値にスペース以外の意味ある値段があるのか確認する。
 *
 * @param objValue
 *            入力された値
 * @return {boolean}
 */
function is_Empty(objValue) {
	if (objValue == null || objValue.replace(/ /gi, "") == "")
		return true;

	return false;
}

/**
 * ascii コードが該当stringに含まれているかの可否を返還する。
 *
 * @param ascii
 *            ascii コード値
 * @param str
 *            入力された値
 * @return {boolean}
 */
function is_InString(ascii, str) {
	if (ascii == null || str == null)
		return false;

	var i = 0;
	for (i = 0; i < str.length; i++) {
		if (str.charCodeAt(i) == ascii)
			return true;
	}
	return false;
}

/**
 * 入力値に英語と数字があるのか確認する。
 *
 * @param objValue
 *            入力された値
 * @return {boolean}
 */
function is_AlphaOrNum(objValue) {
	var ch = "\0";
	var flag = true;

	for ( var i = 0, ch = objValue.charAt(i); (i < objValue.length) && (flag); ch = objValue
			.charAt(++i)) {
		if ((ch >= '0') && (ch <= '9'))
			flag = true;
		else if ((ch >= 'a') && (ch <= 'z'))
			flag = true;
		else if ((ch >= 'A') && (ch <= 'Z'))
			flag = true;
		else
			flag = false;
	}

	return flag;
}

/**
 * 入力値に英語と数字があるのか確認する。(必ず混用)
 *
 * @param objValue
 *            入力された値
 * @return {boolean}
 */
function is_AlphaNum(objValue) {
	var ch = "\0";
	var a_flag = false;
	var n_flag = false;
	var flag = true;

	for ( var i = 0, ch = objValue.charAt(i); (i < objValue.length) && (flag); ch = objValue
			.charAt(++i)) {
		if ((ch >= '0') && (ch <= '9'))
			n_flag = true;
		else if ((ch >= 'a') && (ch <= 'z'))
			a_flag = true;
		else if ((ch >= 'A') && (ch <= 'Z'))
			a_flag = true;
		else if (ch == ' ' || ch == '~' || ch == '`' || ch == '\\' || ch == '-'
				|| ch == '_' || ch == '|' || ch == '+' || ch == '='
				|| ch == ',' || ch == '.' || ch == '/' || ch == '<'
				|| ch == '>' || ch == '?' || ch == '!' || ch == '@'
				|| ch == '#' || ch == '$' || ch == '%' || ch == '^'
				|| ch == '&' || ch == '*' || ch == '(' || ch == ')'
				|| ch == '\"' || ch == '[' || ch == '(') {
			flag = false;
			n_flag = false;
			a_flag = false;
		}
	}
	if (n_flag && a_flag)
		flag = true;
	else
		flag = false;

	return flag;
}

/**
 * 入力値に英語があるのか確認する。
 *
 * @param objValue
 *            入力された値
 * @return {boolean}
 */
function is_Alpha(objValue) {
	var input = trim(objValue);
	var ch = "\0";
	var flag = true;

	if (input.length > 0) {
		if ((!(input.charAt(0) >= 'a') && (input.charAt(0) <= 'z'))
				|| (!(input.charAt(0) >= 'A') && (input.charAt(0) <= 'Z'))) {
			flag = false;
		}
	}
	for ( var i = 0, ch = input.charAt(i); (i < input.length) && (flag); ch = input
			.charAt(++i)) {
		if (!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')))
			flag = false;
	}

	return flag;
}

/**
 * 入力値が電話番号形式なのか確認する。
 *
 * @param objValue
 *            入力された値
 * @return {boolean}
 */
function is_TelNum(objValue) {
	var chars = "0123456789-()";

	if (objValue == null || objValue == "" || objValue == "0")
		return false;

	return containsCharsOnly(objValue, chars);
}

/**
 * Eventから来たkey codeをascii codeに変換する。(補助ファンクション)
 *
 * @param shift
 *            シフトキーの入力可否
 * @param keyCode
 *            入力したキーコード
 * @return {String}
 */
function get_ascii_from_keycode(shift, keyCode) {
	if (clientInformation.systemLanguage == 'ko') {
		var key = new Array(192, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 189,
				187, 219, 221, 220, 186, 222, 188, 190, 191, 32);
		var ascii = new Array(96, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 45,
				61, 91, 93, 92, 59, 39, 44, 46, 47, 32);
		var shiftAscii = new Array(126, 33, 64, 35, 36, 37, 94, 38, 42, 40, 41,
				95, 43, 123, 125, 124, 58, 34, 60, 62, 63, 32);
	} else if (clientInformation.systemLanguage == 'ja') {
		// var key = new
		// Array(49,50,51,52,53,54,55,56,57,186,187,188,189,190,191,193,219,220,221,222,255,32);
		var key = new Array(49, 50, 51, 52, 53, 54, 55, 56, 57, 187, 222, 188,
				189, 190, 191, 226, 192, 221, 219, 186, 220, 32);
		var ascii = new Array(49, 50, 51, 52, 53, 54, 55, 56, 57, 59, 94, 44,
				45, 46, 47, 92, 64, 93, 91, 58, 92, 32);
		var shiftAscii = new Array(33, 34, 35, 36, 37, 38, 39, 40, 41, 43, 126,
				60, 61, 62, 63, 95, 96, 125, 123, 42, 124, 32);
	} else {
		return null;
	}

	var i = 0;
	var ret = null;
	for (i = 0; i < key.length; i++) {
		if (key[i] == keyCode) {
			if (shift)
				ret = shiftAscii[i];
			else
				ret = ascii[i];
			break;
		}
	}
	return ret;
}

/**
 * maxlengthプロパティを確認する。(個別の呼び出しは行わない)
 *
 * @param obj
 *            入力された内容があるForm Elementオブジェクト
 * @param len
 *            maxlengthプロパティー
 * @return {boolean}
 */
function is_maxlength(obj, len) {

	if (obj.value == null)
		return true;

	var value = obj.value.replace(/[,]/g, "");
	var pos1 = len.indexOf('.');
	var pos2 = value.indexOf('.');
	if (pos1 > 0) {
		var f = len.substring(pos1 + 1);
		var i = len.substring(0, pos1) - f;
		if (pos2 < 0) {
			if (i < value.length)
				return false;
		} else {
			if (i < pos2)
				return false;
			else if (f < value.length - pos2 - 1)
				return false;
		}

		return true;
	} else
		return (value.length <= len);
}

/**
 * 入力値の存在可否を返還する。
 *
 * @param obj
 *            入力された内容があるForm Elementオブジェクト
 * @return {boolean}
 */
function is_required(obj) {
	return !(obj.value == "");
}

/**
 * 入力値の前後の空白文字を削除する。
 *
 * @param inputValue
 *            入力された値
 * @return {String}
 */
function trim(inputValue) {
	// leading space
	while ('' + inputValue.charAt(0) == ' ') {
		inputValue = inputValue.substring(1, inputValue.length)
	}
	// trailing space
	while ('' + inputValue.charAt(inputValue.length - 1) == ' ') {
		inputValue = inputValue.substring(0, inputValue.length - 1);
	}
	return inputValue;
}

/**
 * 入力値に全角数字があるのか確認する。
 *
 * @param objValue
 *            入力された値
 * @return {boolean}
 */
function is_ZenkakuNum(objValue) {
	var chars = "１２３４５６７８９０";

	if (objValue == null || objValue == "")
		return false;

	return containsCharsOnly(objValue, chars);
}

/**
 * 入力値に全角数字があるのか確認する。
 *
 * @param objValue
 *            入力された値
 * @return {boolean}
 */
function is_ZenkakuNumber(objValue) {
	var chars = "１２３４５６７８９０";

	return containsCharsOnly(objValue, chars);
}

/**
 * 入力値に該当文字があるのか確認する。
 *
 * @param objValue
 *            入力された値
 * @param chars
 *            該当文字
 * @return {boolean}
 */
function containsCharsOnly(objValue, chars) {
	for (inx = 0; inx < objValue.length; inx++) {
		if (chars.indexOf(objValue.charAt(inx)) == -1)
			return false;
	}
	return true;
}

function is_month_day(dt) {

	var arr = new Array(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	if (dt.length != 4) {
		return false;
	}

	var iMonth = Number(dt.substring(0, 2));
	var iDay = Number(dt.substring(2, 4));

	if (iMonth > 12) {
		return false;
	}

	if (iDay > arr[iMonth - 1] || iDay < 1) {
		return false;
	}

	return true;
}