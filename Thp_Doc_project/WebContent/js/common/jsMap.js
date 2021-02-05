/**
 * jsMap関数を宣言してリターンする。
 * 
 * @param flg
 *            重複可否(true-可能、false-不可)
 * @return jsMap関数
 */
function paramMap(flg) {
	return new jsMap(flg);
}

/**
 * JAVAのMapの性格をそのまま、表現したJavaScript用Map。 使用方法はJavaと同様なので、説明は省略する。
 * 
 * @param flg
 *            重複可否(true-可能、false-不可)
 */
function jsMap(flg) {

	flg = flg == null ? false : flg;

	this.array = new Array();
	this.pointer = -1;

	this.put = function(key, value) {
		var index = this.getIndex(key);
		if (flg || index < 0) {
			var newArr = new Array(key, value);
			this.array[this.array.length] = newArr;
		} else {
			this.array[index][1] = value;
		}
	}

	this.get = function(key) {
		for ( var i in this.array) {
			if (this.array[i][0] == key) {
				return this.array[i][1];
			}
		}
		return null;
	}

	this.getIndex = function(key) {
		for ( var i in this.array) {
			if (this.array[i][0] == key) {
				return i;
			}
		}
		return -1;
	}

	this.hasNext = function() {
		if (this.array.length > this.pointer + 1) {
			return true;
		} else {
			return false;
		}
	}

	this.next = function() {
		this.pointer++;
	}

	this.getKey = function() {
		return this.array[this.pointer][0];
	}

	this.getValue = function() {
		return this.array[this.pointer][1];
	}

	this.size = function() {
		return this.array.length;
	}

	this.isFirst = function() {
		return this.pointer < 1;
	}

	this.isLast = function() {
		return this.pointer >= this.array.length;
	}
}

/**
 * param=value形式のURLのパラメータを返還する。 urlの'?'を付かずに返還。
 * 
 * @param param
 *            パラメータ
 * @return param=value形式のURLのパラメータ
 */
function getParameter(param) {
	return getParameter(param, false);
}

/**
 * param=value形式のURLのパラメータを返還する。
 * 
 * @param param
 *            パラメータ
 * @param isFirst
 *            true:'?'の付き false:'&'の付き
 * @return param=value形式のURLのパラメータ
 */
function getParameter(param, isFirst) {
	var parameter = "";
	while (param.hasNext()) {
		param.next();
		parameter += isFirst && param.isFirst() ? "?" : "&";
		parameter += param.getKey();
		parameter += "=";
		parameter += toUTF8(String(param.getValue()));
	}
	return parameter;
}

/**
 * UTF8に変更。
 * 
 * @param szInput
 *            対象文字列
 * @return
 */
function toUTF8(szInput) {
	if (szInput == undefined) {
		return "";
	}
	var szRet = "";
	try {
		var wch;
		var uch = "";
		for ( var x = 0; x < szInput.length; x++) {
			wch = szInput.charCodeAt(x);
			if (!(wch & 0xFF80)) {
				szRet += "%" + wch.toString(16);
			} else if (!(wch & 0xF000)) {
				uch = "%" + (wch >> 6 | 0xC0).toString(16) + "%"
						+ (wch & 0x3F | 0x80).toString(16);
				szRet += uch;
			} else {
				uch = "%" + (wch >> 12 | 0xE0).toString(16) + "%"
						+ (((wch >> 6) & 0x3F) | 0x80).toString(16) + "%"
						+ (wch & 0x3F | 0x80).toString(16);
				szRet += uch;
			}
		}

	} catch (e) {
	}
	return (szRet);
}

/**
 * urlにパラメータを付けて返還する。
 * 
 * @param url
 * @param param
 *            パラメータ
 * @return 最終のURL
 */
function getParamUrl(url, param) {
	if (url != null && param != null) {
		url += getParameter(param, url.indexOf("?") < 0);
	}
	return url;
}

/**
 * selectオブジェクトにparamのリストを適用する。 selectedに指定した値と一致するパラメータが存在すると、 selected処理する。
 * 
 * @param objName
 *            オブジェクト名
 * @param param
 *            selectリスト
 * @param selected
 *            selectedに指定する文字列
 */
function setSelectBox(objName, param, selected) {

	// seleteオブジェクト
	var obj = document.getElementsByName(objName)[0];

	// 既存optionを全て削除
	cleanOptions(obj);

	var i = 0;
	while (param.hasNext()) {
		param.next();
		obj.options[i] = new Option(param.getValue(), param.getKey(), false,
				param.getKey() == selected);
		i++;
	}
}

/**
 * 既存optionを全て削除処理する。
 * 
 * @param obj
 *            オブジェクト
 */
function cleanOptions(obj) {
	if (obj == undefined) {
		return;
	}
	var size = obj.length;
	for ( var i = 0; i < size; i++) {
		obj.options[i] = null;
	}
}