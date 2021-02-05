/** XMLHttpRequest */
var xmlHttp = null;

/**
 * XMLHttpRequestを取得する。
 */
function createXMLHttpRequest() {
	if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	} else if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
	}
}

/**
 * AjaxControl関数。 urlは共通で用いるAction経路を指定すること。 コールバック関数名及びSQLIDがNULLの場合は実行しない。
 *
 * @param callBack
 *            コールバック関数
 * @param sqlId
 *            SQLID
 * @param parameter
 *            パラメータ
 * @param isList
 *            List形式か
 */
function doJSON(callBack, sqlId, parameter, isList) {

	if (callBack == null || sqlId == null) {
		return;
	}

	var url = "/Tech3g_Doc_project/co/aj/ajaxSrchService.do?method=getInfoByAjax";
	parameter.put("SQLID", sqlId);
	parameter.put("ISLIST", String(isList));
	url = getParamUrl(url, parameter);

	createXMLHttpRequest();
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = function() {
		handleStateChange(callBack);
	}
	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xmlHttp.send();
}

/**
 * 各画面から呼出す共通関数。
 *
 * @param callBack
 *            コールバック関数
 * @param sqlId
 *            SQLID
 * @param parameter
 *            パラメータ
 * @param isList
 *            List形式か
 */
function refAjaxJson(callBack, sqlId, parameter, isList) {
	isList = isList != null ? isList : false;
	doJSON(callBack, sqlId, parameter, isList);
}

/**
 * XHRのreadyState状態が変わることに 結果が正常の場合、コールバックが呼出。
 *
 * @param callBack
 *            コールバック関数
 */
function handleStateChange(callBack) {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			parseResults(callBack);
		}
	}
}

/**
 * XHRの呼出が正常の場合、コールバック関数が実行される。
 *
 * @param callBack
 *            コールバック関数
 */
function parseResults(callBack) {
	callBack(xmlHttp.responseText);
}

/**
 * SQLの実行結果が0件の場合は”ZERO”という文字列が返還され、 結果が0件の場合はtrueをリターン。
 *
 * @param responseText
 *            結果文字列
 * @return true:0件、false:0件外
 */
function isZero(responseText) {
	return responseText == "ZERO";
}

/**
 * 関数宣言
 */
function JSONUtil() {
	this.json = {};
}

/**
 * ajax呼出後、SQL文結果をJSON形式で返還。 受け取ったJSON結果をセットして用いる。
 *
 * @param jsonString
 */
function JSONUtil(jsonString) {
	this.json = {};
	this.setJSONString(jsonString);
	this.size = getJsonSize(jsonString);
}

JSONUtil.prototype.setJSONString = function(jsonString) {
	this.json = eval('(' + jsonString + ')');
}

JSONUtil.prototype.getJSON = function() {
	return this.json;
}

JSONUtil.prototype.getSize = function() {
	return this.size;
}

/**
 * responseTextの結果数を求める。
 *
 * @param jsonString
 *            json結果
 * @return count
 */
function getJsonSize(jsonString) {
	var str = String(jsonString);
	var size = str.length;
	var cnt = 0;
	var index = 0;

	// jsonの最初”{”は含まないように、iは1から開始
	for ( var i = 1; i < size; i++) {
		index = str.indexOf("{", i);
		if (index > -1) {
			cnt++;
			i = index;
		}
	}
	return cnt;
}