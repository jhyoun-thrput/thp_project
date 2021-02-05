/**
 * エラーメッセージ処理後フォーカスを次に移動。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @param msg
 *            alert message
 * @return
 */
function errMsg(obj, msg) {
	alert(msg);
	obj.select();
	obj.focus();
}

/**
 * Enter Keyを確認する。
 *
 * @return {boolean}
 */
function isEnterKey() {
	if (window.event.keyCode == 13) {
		return true;
	}

	return false;
}

/**
 * 文字列で tokenしてリターンする。
 *
 * @param str
 *            入力値
 * @param delim
 *            区分字
 * @return {array}
 */
function makeToken(str, delim) {
	var array = str.split(delim);
	return array;
}

/**
 * 入力された値段の両方の空白をとり除く。
 *
 * @param inputValue
 *            入力値
 * @return {string}
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
 * Enter keyで methodを行う。
 *
 * @param obj
 *            Form Element object type オブジェクト
 * @param method
 *            行うmethod
 * @return
 */
function exeMethodWithEnterKey(obj, method) {
	keynum = event.keyCode;

	if (keynum == 13) {
		eval(method);
		if (obj.getAttribute("submit") != "")
			window.event.keyCode = 9;
	}
}

/**
 * Tab keyを key-inする。
 *
 * @return
 */
function nextFocus() {
	window.event.keyCode = 9;
}

/**
 * Del key 入力時二つのformfieldを初期化する。
 *
 * @param obj1
 *            Form Element object type オブジェクト
 * @param obj2
 *            Form Element object type オブジェクト
 * @return
 */
function delKey(obj1, obj2) {
	if (event.keyCode == 46) {
		obj1.value = "";
		obj2.value = "";
	}
}

/**
 * 右側マウスボタン使用を禁止する。
 *
 * @return
 */
function chkRightButton() {
	if ((event.button == 2) || (event.button == 3)) {
		alert("右側マウスボタンは使うことができません。");
	}
}

/**
 * ウィンドウの中央に Popup窓を開く。
 *
 * @param url
 *            表示する画面のURL
 * @param winname
 *            ウィンドウ名
 * @param width
 *            ウィンドウの幅
 * @param height
 *            ウィンドウの高さ
 * @param scroll
 *            スクロールの生成可否(yes/no/auto)
 * @return
 */
function openWindow(url, winname, width, height, scroll) {
	var winx = (screen.width - width) / 2;
	var winy = (screen.height - height) / 2;
	var settings = "height=    " + height + ", ";
	settings += "width=     " + width + ", ";
	settings += "top=       " + winy + ", ";
	settings += "left=      " + winx + ", ";
	settings += "scrollbars=" + scroll + ", ";
	settings += "resizable =yes";

	var win = window.open(url, winname, settings);
	win.opener = this;
	return win;
}

/**
 * ウィンドウの中央に Modal Popup窓を開く。
 *
 * @param url
 *            表示する画面のURL
 * @param width
 *            ウィンドウの幅
 * @param height
 *            ウィンドウの高さ
 * @param scroll
 *            スクロールの生成可否(yes/no/auto)
 * @return
 */
function openModal(url, width, height, scroll) {
	var winx = (screen.width - width) / 2;
	var winy = (screen.height - height) / 2;
	var settings = "dialogHeight:    " + height + "px; ";
	settings += "dialogWidth:     " + width + "px; ";
	settings += "dialogTop:       " + winy + "px; ";
	settings += "dialogLeft:      " + winx + "px; ";
	settings += "scroll    :" + scroll + "; ";
	settings += "resizable :no; ";
	settings += "help      :no; ";
	settings += "status    :no; ";
	settings += "unadorned:yes";

	returnValue = showModalDialog(url, arguments, settings);

	return returnValue;
}

/**
 * 全体大きさブラウザーを開く。
 *
 * @param url
 *            表示する画面のURL
 * @return
 */
function openFullScreen(url) {
	var newWin;
	newWin = window.open(url, "", "fullScreen");
	self.opener = self;
	window.close();
}

/**
 * 日付入力の時日付の dash('-')を削除する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return
 */
function delDateDelim(obj) {
	obj.value = obj.value.replace(/-/gi, "");
}

/**
 * 日付入力の時日付の dash('-')を削除する。
 *
 * @param dateValue
 *            入力値
 * @return {string}
 */
function delDateDelimiter(dateValue) {
	if (dateValue.value) {
		dateValue = dateValue.value;
	}

	objvalue = dateValue.replace(/-/gi, "");

	return objvalue;
}

/**
 * 日付入力の時日付の dash('-')を追加する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return
 */
function addDateDelim(obj) {
	var varDate = obj.value;

	obj.value = addDateDelimiter(varDate);
}

/**
 * 日付入力の時日付の dash('-')を追加して返還する。
 *
 * @param dateValue
 *            入力値('YYYYMMDD')
 * @return {string}
 */
function addDateDelimiter(dateValue) {
	var value = dateValue;

	if (value.length != 8)
		return value;

	year = value.substring(0, 4);
	month = value.substring(4, 6);
	day = value.substring(6, 8);

	return year + "-" + month + "-" + day;
}

/**
 * 日付入力の時日付の slash('/')を追加して返還する。
 *
 * @param objValue
 *            入力値('YYYYMMDD')
 * @return
 */
function addDateDelimiterSlash(objValue) {
	year = objValue.substring(0, 4);
	month = objValue.substring(4, 6);
	day = objValue.substring(6, 8);

	return year + "/" + month + "/" + day;
}
/**
 * 時間入力の時時間の Colon(':')を削除する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return
 */
function delTimeDelim(obj) {
	obj.value = obj.value.replace(/:/gi, "");
}

/**
 * 時間入力の時時間の Colon(':')を追加する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return
 */
function addTimeDelim(obj) {
	var varTime = obj.value;

	if ((varTime != null) && (varTime != "")) {
		hour = varTime.substring(0, 2);
		min = varTime.substring(2, 4);
		if (obj.value.length == 6) {
			sec = varTime.substring(4, 6);
			obj.value = hour + ":" + min + ":" + sec;
		} else
			obj.value = hour + ":" + min;
	}
}

/**
 * 時間入力の時時間の Colon(':')を削除する。
 *
 * @param objValue
 *            入力値
 * @return {string}
 */
function delTimeDelimiter(objValue) {
	objValue = objValue.replace(/:/gi, "");

	return objValue;
}

/**
 * 時間入力の時時間の Colon(':')を追加する。
 *
 * @param objValue
 *            入力値
 * @return {string}
 */
function addTimeDelimiter(objValue) {
	if ((objValue != null) && (objValue != "")) {
		hour = objValue.substring(0, 2);
		min = objValue.substring(2, 4);
		sec = objValue.substring(4, 6);

		objValue = hour + ":" + min + ":" + sec;
	}
	return objValue;
}

/**
 * 通話入力の時通話の comma('、')を削除する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {number}
 *
 */
function delCurrencyDelim(obj) {
	var cur = trim(obj.value.replace(/,/gi, ""));

	return cur;
}

/**
 * 通話入力の時通話の comma('、')を追加する。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @return {string}
 */
function addCurrencyDelim(obj) {
	var str = trim(obj.value.replace(/,/gi, ""));

	var index = 0;
	var negative = 0;

	if (str.indexOf('-') == 0) {
		str = str.replace(/-/gi, "");
	} else
		negative = -1;

	var strLength = str.length;

	if (str.indexOf('.') == -1)
		index = strLength - 1;
	else
		index = str.indexOf('.') - 1;

	var res = "";
	var p = 0;
	for (i = index; i >= 0; i--, p++) {
		if ((p != 0) && ((p % 3) == 0))
			res = "," + res;

		res = str.charAt(i) + res;
	}

	if (negative == 0)
		res = "-" + res;

	return res;
}

/**
 * 現在時間('HH')を返し
 *
 * @return {number}
 */
function getHour() {
	var cDate = new Date();

	return cDate.getHours();
}

/**
 * 現在時間('MI')を返し
 *
 * @return {number}
 */
function getMinute() {
	var cDate = new Date();

	return cDate.getMinutes();
}

/**
 * 現在時間('SS')を返し
 *
 * @return {number}
 */
function getSecond() {
	var cDate = new Date();

	return cDate.getSeconds();
}

/**
 * 現在時間を 'HHMIDD' 形式で返し
 *
 * @return {string}
 */
function getCurrentTime() {
	var h = getHour();
	var m = getMinute();
	var s = getSecond();

	if (h < 10)
		h = "0" + h;
	if (m < 10)
		m = "0" + m;
	if (s < 10)
		s = "0" + s;

	return h + "" + m + "" + s;
}

/**
 * 入力された日付値{'YYYYMMDD'}を特定時間位 +、- して日付値('YYYYMMDD')を返し
 *
 * @param tda
 *            日付値
 * @param plan
 *            変更する日数
 * @return {string}
 */
function addDate(tda, plan) {
	year = tda.substring(0, 4);
	month = tda.substring(4, 6);
	day = tda.substring(6, 8);

	dt = year + "/" + month + "/" + day;

	var cDate = new Date(dt);
	cTime = cDate.getTime() + plan * 1000 * 3600 * 24;
	var bTime = new Date();
	bTime.setTime(cTime);

	bY = (bTime.getYear() < 100) ? "19" + bTime.getYear() : bTime.getYear();
	bM = bTime.getMonth() + 1;
	bD = bTime.getDate();

	if (bM < 10)
		bM = "0" + bM;
	if (bD < 10)
		bD = "0" + bD;

	bDate = bY + "" + bM + "" + bD;

	return bDate;
}

/**
 * 個人番号 Check Digitを確認する。
 *
 * @param inoValue
 *            入力された内容があるForm Element object type オブジェクト
 * @return {boolean}
 */
function chkIno(inoValue) {
	var ino = inoValue.value;
	var sum = 0;
	var weight = new Array(2, 7, 6, 5, 4, 3, 2);
	var divisor = 11;
	var remainder = 0;
	var checkdigit = 0;

	if (ino.length < 8) {
		alert("個人番号の入力が違いました。�");
		return false;
	}

	for ( var i = 0; i <= 6; i++) {
		sum += ino.substr(i, 1) * weight[i];
	}

	remainder = sum % divisor;
	checkdigit = divisor - remainder;

	if (checkdigit != ino.substr(7, 1)) {
		alert("個人番号の入力が違いました。");
		return false;
	}

	return true;
}

/**
 * 世代番号 Check Digitを確認する。
 *
 * @param hsh_noValue
 *            入力値
 * @return {boolean}
 */
function chkHshno(hsh_noValue) {
	var hsh_no = "0" + hsh_noValue.value;
	var sum = 0;
	var weight = new Array(2, 7, 6, 5, 4, 3, 2);
	var divisor = 11;
	var remainder = 0;
	var checkdigit = 0;

	if (hsh_no.length < 8) {
		alert("世帯番号の入力が違いました。");
		return false;
	}

	for ( var i = 0; i <= 6; i++) {
		sum += hsh_no.substr(i, 1) * weight[i];
	}

	remainder = sum % divisor;
	checkdigit = divisor - remainder;

	if (checkdigit != hsh_no.substr(7, 1)) {
		alert("世帯番号の入力が違いました。");
		return false;
	}

	return true;
}

/**
 * 入力した文字列を日程パターンで変換する。
 *
 * @param mode
 *            変換モード (1:ひらがな -> 半角カタナカ、 2:ひらがな -> 全角カタカナ、 3:全角カタカナ -> 半角カタカナ、
 *            4:半角カタカナ -> 全角カタカナ、 5:全角数字 -> 半角数字、 6:半角小文字 -> 半角大文字, 7:半角数字 ->
 *            全角数字)
 * @param str
 *            入力値
 * @return {string}
 */
function chgString(mode, str) {

	var hir = new Array("が", "ぎ", "ぐ", "げ", "ご", "ざ", "じ", "ず", "ぜ", "ぞ", "だ",
			"ぢ", "づ", "で", "ど", "ば", "び", "ぶ", "べ", "ぼ", "ぱ", "ぴ", "ぷ", "ぺ",
			"ぽ", "を", "ぁ", "ぃ", "ぅ", "ぇ", "ぉ", "ゃ", "ゅ", "ょ", "っ", "ー", "あ",
			"い", "う", "え", "お", "か", "き", "く", "け", "こ", "さ", "し", "す", "せ",
			"そ", "た", "ち", "つ", "て", "と", "な", "に", "ぬ", "ね", "の", "は", "ひ",
			"ふ", "へ", "ほ", "ま", "み", "む", "め", "も", "や", "ゆ", "よ", "ら", "り",
			/* US-09-0423 START */
			/*
			 * "る", "れ", "ろ", "わ", "ん", " ");
			 */
			"る", "れ", "ろ", "わ", "ん", "　", "（", "）");
	/* US-09-0423 END */

	var h_kat = new Array("ｶﾞ", "ｷﾞ", "ｸﾞ", "ｹﾞ", "ｺﾞ", "ｻﾞ", "ｼﾞ", "ｽﾞ", "ｾﾞ",
			"ｿﾞ", "ﾀﾞ", "ﾁﾞ", "ﾂﾞ", "ﾃﾞ", "ﾄﾞ", "ﾊﾞ", "ﾋﾞ", "ﾌﾞ", "ﾍﾞ", "ﾎﾞ",
			"ﾊﾟ", "ﾋﾟ", "ﾌﾟ", "ﾍﾟ", "ﾎﾟ", "ｦ", "ｧ", "ｨ", "ｩ", "ｪ", "ｫ", "ｬ",
			"ｭ", "ｮ", "ｯ", "ｰ", "ｱ", "ｲ", "ｳ", "ｴ", "ｵ", "ｶ", "ｷ", "ｸ", "ｹ",
			"ｺ", "ｻ", "ｼ", "ｽ", "ｾ", "ｿ", "ﾀ", "ﾁ", "ﾂ", "ﾃ", "ﾄ", "ﾅ", "ﾆ",
			"ﾇ", "ﾈ", "ﾉ", "ﾊ", "ﾋ", "ﾌ", "ﾍ", "ﾎ", "ﾏ", "ﾐ", "ﾑ", "ﾒ", "ﾓ",
			/* US-09-0423 START */
			/* "ﾔ", "ﾕ", "ﾖ", "ﾗ", "ﾘ", "ﾙ", "ﾚ", "ﾛ", "ﾜ", "ﾝ", " "); */
			"ﾔ", "ﾕ", "ﾖ", "ﾗ", "ﾘ", "ﾙ", "ﾚ", "ﾛ", "ﾜ", "ﾝ", " ", "(", ")");
	/* US-09-0423 END */

	var f_kat = new Array("ガ", "ギ", "グ", "ゲ", "ゴ", "ザ", "ジ", "ズ", "ゼ", "ゾ",
			"ダ", "ヂ", "ヅ", "デ", "ド", "バ", "ビ", "ブ", "ベ", "ボ", "パ", "ピ", "プ",
			"ペ", "ポ", "ヲ", "ァ", "ィ", "ゥ", "ェ", "ォ", "ャ", "ュ", "ョ", "ッ", "ー",
			"ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク", "ケ", "コ", "サ", "シ", "ス",
			"セ", "ソ", "タ", "チ", "ツ", "テ", "ト", "ナ", "ニ", "ヌ", "ネ", "ノ", "ハ",
			"ヒ", "フ", "ヘ", "ホ", "マ", "ミ", "ム", "メ", "モ", "ヤ", "ユ", "ヨ", "ラ",
			"リ", "ル", "レ", "ロ", "ワ", "ン", "　");

	var h_num = new Array("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"*", " ");

	var f_num = new Array("０", "１", "２", "３", "４", "５", "６", "７", "８", "９",
			"＊", "　");

	var h_kat_s = new Array("ｧ", "ｩ", "ｪ", "ｫ", "ｬ", "ｭ", "ｮ", "ｨ", "ｯ");

	var h_kat_l = new Array("ｱ", "ｳ", "ｴ", "ｵ", "ﾔ", "ﾕ", "ﾖ", "ｲ", "ﾂ");

	var h_eng = new Array("a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
			"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
			"x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z", " ");

	var f_eng = new Array("ａ", "ｂ", "ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ",
			"ｋ", "ｌ", "ｍ", "ｎ", "ｏ", "ｐ", "ｑ", "ｒ", "ｓ", "ｔ", "ｕ", "ｖ", "ｗ",
			"ｘ", "ｙ", "ｚ", "Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ",
			"Ｋ", "Ｌ", "Ｍ", "Ｎ", "Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ", "Ｖ", "Ｗ",
			"Ｘ", "Ｙ", "Ｚ", "　");

	if (mode == 1) { // ひらがな -> 半角カタナカ
		for ( var i = 0; i < hir.length; i++) {
			str = replaceAll(str, hir[i], h_kat[i]);
		}
		str = chgString(6, str);
	} else if (mode == 2) { // ひらがな-> 全角カタカナ
		for ( var i = 0; i < hir.length; i++) {
			str = replaceAll(str, hir[i], f_kat[i]);
		}
	} else if (mode == 3) { // 全角カタカナ -> 半角カタナカ
		for ( var i = 0; i < f_kat.length; i++) {
			str = replaceAll(str, f_kat[i], h_kat[i]);
		}
	} else if (mode == 4) { // 半角カタナカ -> 全角カタカナ
		for ( var i = 0; i < h_kat.length; i++) {
			str = replaceAll(str, h_kat[i], f_kat[i]);
		}
	} else if (mode == 5) { // 全角数字 -> 半角数字
		for ( var i = 0; i < f_num.length; i++) {
			str = replaceAll(str, f_num[i], h_num[i]);
		}
	} else if (mode == 6) { // 半角小文字 -> 半角大文字
		for ( var i = 0; i < h_kat_s.length; i++) {
			str = replaceAll(str, h_kat_s[i], h_kat_l[i]);
		}
	} else if (mode == 7) { // 半角数字 -> 全角数字
		for ( var i = 0; i < f_num.length; i++) {
			str = replaceAll(str, h_num[i], f_num[i]);
		}
	} else if (mode == 8) { // 半角英字 -> 全角英字
		for ( var i = 0; i < h_eng.length; i++) {
			str = replaceAll(str, h_eng[i], f_eng[i]);
		}
	} else if (mode == 9) { // 全角英字 -> 半角英字
		for ( var i = 0; i < f_eng.length; i++) {
			str = replaceAll(str, f_eng[i], h_eng[i]);
		}
	}

	return str;
}

/**
 * 文字列以前部半角英語で構成されているか判断する。
 *
 * @param StrSrc
 *            入力値
 * @return {boolean}
 */
function chkAlphabet(StrSrc) {

	return /^[a-zA-Z ]+$/.test(StrSrc);
}

/**
 * 文字列が全部半角数字で構成されているかを判断する。
 *
 * @param StrSrc
 *            入力値
 * @return {boolean}
 */
function chkNumber(StrSrc) {

	var ObjReg = /[^0-9\-]/;

	return !ObjReg.test(StrSrc);
}

/**
 * 文字列が全部半角英字あるいは半角数字で構成されているのか判断する。
 *
 * @param StrSrc
 *            入力値
 * @return {boolean}
 */
function chkAlphabetNumber(StrSrc) {

	var ObjReg = /[^a-zA-Z0-9\- ]/;

	return !ObjReg.test(StrSrc);
}

/**
 * 文字列が全体半角記号で構成されているのか判断する。
 *
 * @param StrSrc
 *            入力値
 * @return {boolean}
 */
function chkKigo(StrSrc) {

	var StrReg = "[^";
	StrReg += unescape("%u0021"); /* 0021 = "!" */
	StrReg += "-\\" + unescape("%u002F"); /* 002F = "/" */
	StrReg += unescape("%u003A"); /* 003A = ":" */
	StrReg += "-" + unescape("%u0040"); /* 0040 = "@" */
	StrReg += "\\" + unescape("%u005B"); /* 005B = "[" */
	StrReg += "-" + unescape("%u0060"); /* 0060 = "`" */
	StrReg += unescape("%u007B"); /* 007B = "{" */
	StrReg += "-" + unescape("%u007E"); /* 007E = "~" */
	StrReg += " ]";
	var ObjReg = new RegExp(StrReg);

	return !ObjReg.test(StrSrc);
}

/**
 * 文字列が全体半角カタカナで構成されているか判断する。
 *
 * @param StrSrc
 *            入力値
 * @return {boolean}
 */
function chkHanKana(StrSrc) {

	var StrReg = "[^";
	StrReg += unescape("%uFF61"); /* FF61 = "? */
	StrReg += "-" + unescape("%uFF9F"); /* FF9F = "? */
	StrReg += " ]";
	var ObjReg = new RegExp(StrReg);

	return !ObjReg.test(StrSrc);
}

/**
 * 文字列が皆半角文字で構成されているかを判断する。
 *
 * @param StrSrc
 *            入力値
 * @return {boolean}
 */
function chkHankaku(StrSrc) {

	var StrReg = "[^";
	StrReg += unescape("%u0001"); /* 0001 = */
	StrReg += "-" + unescape("%u007E"); /* 007E = "~" */
	StrReg += unescape("%uFF61"); /* FF61 = "? */
	StrReg += "-" + unescape("%uFF9F"); /* FF9F = "? */
	StrReg += " ]";
	var ObjReg = new RegExp(StrReg);

	return !ObjReg.test(StrSrc);
}

/**
 * 文字列が皆全角文字で構成されているかどうかを判断する。
 *
 * @param StrSrc
 *            入力値
 * @return {boolean}
 */
function chkZenkaku(StrSrc) {

	var StrReg = "[";
	StrReg += unescape("%u0001"); /* 0001 = */
	StrReg += "-" + unescape("%u007E"); /* 007E = "~" */
	StrReg += unescape("%uFF61"); /* FF61 = "? */
	StrReg += "-" + unescape("%uFF9F"); /* FF9F = "? */
	StrReg += " ]";
	var ObjReg = new RegExp(StrReg);

	return !ObjReg.test(StrSrc);
}

/**
 * 文字列が皆半角大文字で構成されているか判断する。
 *
 * @param StrSrc
 *            入力値
 * @return {boolean}
 */
function chkUpperCase(StrSrc) {

	var ObjReg = /[^A-Z ]/;

	return !ObjReg.test(StrSrc);
}

/**
 * 文字列が皆半角小文字で構成されているかを判断する。
 *
 * @param StrSrc
 *            入力値
 * @return {boolean}
 */
function chkLowerCase(StrSrc) {

	var ObjReg = /[^a-z ]/;

	return !ObjReg.test(StrSrc);
}

/**
 * 入力値が日付形式(YYYY/MM/DD)なのかチェック
 *
 * @param StrDate
 *            入力値(YY/MM/DD)
 * @return {boolean}
 */
function chkDate(StrDate) {

	var StrMatch = StrDate.match(/^(\d{4})\/(\d{2})\/(\d{2})$/);
	if (StrMatch == null) {
		return false;
	}

	var StrYear = "1" + RegExp.$1;

	var ObjDate = new Date(StrYear, RegExp.$2 - 1, RegExp.$3);

	if (RegExp.$3 != ObjDate.getDate()) {
		return false;
	}
	if (RegExp.$2 != ObjDate.getMonth() + 1) {
		return false;
	}
	if (StrYear != ObjDate.getFullYear()) {
		return false;
	}

	return true;
}

/**
 * 日付けが開始~終了期間内に含まれているかを確認する。
 *
 * @param StrDate
 *            入力値(YY/MM/DD)
 * @param StrStartDate
 *            開始日(YY/MM/DD)
 * @param StrEndDate
 *            終了日(YY/MM/DD)
 * @return {boolean}
 *
 */
function chkInDate(StrDate, StrStartDate, StrEndDate) {

	var NumDateStart = chkCompDate(StrDate, StrStartDate);
	if (NumDateStart == 1) {
		return false;
	}

	var NumDateEnd = chkCompDate(StrDate, StrEndDate);
	if (NumDateEnd == -1) {
		return false;
	}

	return true;
}

/**
 * 二つの日付けを比べる。
 *
 * @param StrBefDate
 *            入力値1(YY/MM/DD)
 * @param StrAftDate
 *            入力値2(YY/MM/DD)
 * @return {number} 1/-1/0
 */
function chkCompDate(StrBefDate, StrAftDate) {

	var ObjBefDate = StrBefDate.split("/");
	var ObjAftDate = StrAftDate.split("/");

	if ((ObjBefDate[0] - 0) < (ObjAftDate[0] - 0)) {
		return 1;
	} else if ((ObjAftDate[0] - 0) < (ObjBefDate[0] - 0)) {
		return -1;
	}

	if ((ObjBefDate[1] - 0) < (ObjAftDate[1] - 0)) {
		return 1;
	} else if ((ObjAftDate[1] - 0) < (ObjBefDate[1] - 0)) {
		return -1;
	}

	if ((ObjBefDate[2] - 0) < (ObjAftDate[2] - 0)) {
		return 1;
	} else if ((ObjAftDate[2] - 0) < (ObjBefDate[2] - 0)) {
		return -1;
	}

	return 0;
}

/**
 * 入力値が時間形式(HH:MI:SS)なのかチェック
 *
 * @param StrTime
 *            入力値(HH:MI:SS)
 * @return {boolean}
 */
function chkTime(StrTime) {

	var StrMatch = StrTime.match(/^(\d{2}):(\d{2}):(\d{2})$/);
	if (StrMatch == null) {
		return false;
	}

	var ObjTime = new Date("1900", "1", "1", RegExp.$1, RegExp.$2, RegExp.$3);

	if (RegExp.$3 != ObjTime.getSeconds()) {
		return false;
	}

	if (RegExp.$2 != ObjTime.getMinutes()) {
		return false;
	}

	if (RegExp.$1 != ObjTime.getHours()) {
		return false;
	}

	return true;
}

/**
 * 時間が開始 ~ 終了期間内に含まれているのかに対して確認する。
 *
 * @param StrTime
 *            入力値(HH:MI:SS)
 * @param StrStartTime
 *            開始時間(HH:MI:SS)
 * @param StrEndTime
 *            終了時間(HH:MI:SS)
 * @return {boolean}
 */
function chkInTime(StrTime, StrStartTime, StrEndTime) {

	var NumTimeStart = chkCompTime(StrTime, StrStartTime);
	if (NumTimeStart == 1) {
		return false;
	}

	var NumTimeEnd = chkCompTime(StrTime, StrEndTime);
	if (NumTimeEnd == -1) {
		return false;
	}

	return true;
}

/**
 * 二時間を比べる。
 *
 * @param StrBefTime
 *            入力値1(HH:MI:DD)
 * @param StrAftTime
 *            入力値2(HH:MI:DD)
 * @return {number} 1/-1/0
 */
function chkCompTime(StrBefTime, StrAftTime) {

	var ObjBefTime = StrBefTime.split(":");
	var ObjAftTime = StrAftTime.split(":");

	if (ObjBefTime[0] < ObjAftTime[0]) {
		return 1;
	} else if (ObjBefTime[0] > ObjAftTime[0]) {
		return -1;
	}

	if (ObjBefTime[1] < ObjAftTime[1]) {
		return 1;
	} else if (ObjBefTime[1] > ObjAftTime[1]) {
		return -1;
	}

	if (ObjBefTime[2] < ObjAftTime[2]) {
		return 1;
	} else if (ObjBefTime[2] > ObjAftTime[2]) {
		return -1;
	}

	return 0;
}

/**
 * 日付け、時間が開始 ~ 終了期間内に含まれているのかに対して確認する。
 *
 * @param StrDate
 *            入力日付(YY/MM/DD)
 * @param StrTime
 *            入力時間(HH:MI:SS)
 * @param StrStartDate
 *            開始日付(YY/MM/DD)
 * @param StrStartTime
 *            開始時間(HH:MI:SS)
 * @param StrEndDate
 *            終了日付(YY/MM/DD)
 * @param StrEndTime
 *            終了時間(HH:MI:SS)
 * @return {boolean}
 */
function chkInDateTime(StrDate, StrTime, StrStartDate, StrStartTime,
		StrEndDate, StrEndTime) {

	var NumDateTimeStart = chkCompDateTime(StrDate, StrTime, StrStartDate,
			StrStartTime);
	if (NumDateTimeStart == 1) {
		return false;
	}

	var NumDateTimeEnd = chkCompDateTime(StrDate, StrTime, StrEndDate,
			StrEndTime);
	if (NumDateTimeEnd == -1) {
		return false;
	}

	return true;
}

/**
 * 二つの日付、時間を比べる。
 *
 * @param StrBefDate
 *            入力日付1
 * @param StrBefTime
 *            入力時間1
 * @param StrAftDate
 *            入力日付2
 * @param StrAftTime
 *            入力時間2
 * @return {boolean}
 */
function chkCompDateTime(StrBefDate, StrBefTime, StrAftDate, StrAftTime) {

	var NumCompDate = chkCompDate(StrBefDate, StrAftDate);

	if (NumCompDate == 1) {
		return 1;
	}

	if (NumCompDate == -1) {
		return -1;
	}

	var NumCompTime = chkCompTime(StrBefTime, StrAftTime);

	if (NumCompTime == 1) {
		return 1;
	}

	if (NumCompTime == -1) {
		return -1;
	}

	return 0;
}

/**
 * 入力値の左側に全体桁数位特定文字で満たす。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @param slength
 *            全体文字長さ
 * @param chars
 *            処理文字
 * @return {String}
 */
function fillCharLeft(obj, slength, chars) {

	var stemp = "";
	if (obj.value != "") {
		for ( var i = 0; i < slength - obj.value.length; i++) {
			stemp = chars + stemp;
		}
		obj.value = stemp + obj.value;
	}

}

/**
 * 入力値の左側に全体桁数位特定文字で満たす。
 *
 * @param obj
 *            入力された内容があるForm Element object type オブジェクト
 * @param slength
 *            全体文字長さ
 * @param chars
 *            処理文字
 * @return {String}
 */
function fillCharLeftValue(value, slength, chars) {

	var stemp = "";
	if (value != "") {
		for ( var i = 0; i < slength - value.length; i++) {
			stemp = chars + stemp;
		}
		return stemp + value;
	}

	return "";
}

/**
 * 入力値の左側に特定文字を削除する。
 *
 * @param value
 *            入力された内容があるForm Element object type オブジェクト
 * @param chars
 *            処理文字
 * @return {String}
 */
function delCharLeftValue(value, chars) {

	var posNotChar = 0;
	if (value != "") {
		for ( var i = 0; i < value.length; i++) {
			if (value.charAt(i) != chars) {
				posNotChar = i;
				break;
			}
		}
		return value.substring(posNotChar);
	}

	return "";

}

/**
 * 和暦を西暦に変更する。
 *
 * @param str
 *            和暦
 * @return {String}
 */
function ja2ad(str) { //
	if (str == null || str == '') {
		return "";
	}

	var f = getWarekiFrame();
	/*
	 * var f; var tmp_win = getPrimaryWindow(); if (tmp_win.frames[0].frmWareki ==
	 * null) { f = tmp_win.frames[0].frames[0].frmWareki; } else { f =
	 * tmp_win.frames[0].frmWareki; }
	 */
	str += "";
	var wareki = parseInt(str.substring(1, 3), 10);
	var str_sno = str.substring(0, 1);
	var str_len = str.length;

	// alert(f.name);

	var len = f.wa_row.value;

	var sno = '';
	var jyy = '';
	var from_dt = '';
	var to_yy = '';
	var prd = '';

	var check = false;

	if (len == 1) {
		sno = f.wa_sno.value;
		jyy = f.wa_jyy.value;
		from_dt = f.wa_from_dt.value;
		to_yy = f.wa_to_yy.value;
		prd = (parseInt(to_yy, 10)) - (parseInt(from_dt.substring(0, 4), 10));

		if (str_sno == sno || str_sno == jyy) {
			// if (wareki > prd)
			// wareki = prd;
			wareki = wareki + (parseInt(from_dt.substring(0, 4), 10) - 1);
			check = true;
		} else {
			check = false;
		}

	} else {
		for ( var i = 0; i < len; i++) {
			sno = f.wa_sno[i].value;
			jyy = f.wa_jyy[i].value;
			from_dt = f.wa_from_dt[i].value;
			to_yy = f.wa_to_yy[i].value;
			prd = 1 + (parseInt(to_yy, 10))
					- (parseInt(from_dt.substring(0, 4), 10));

			if (str_sno == sno || str_sno == jyy) {
				// if (wareki > prd)
				// wareki = prd;
				wareki = wareki + (parseInt(from_dt.substring(0, 4), 10) - 1);
				check = true;
				break;
			} else {
				check = false;
			}
		}
	}

	if (!check) {
		wareki = 'エラー';
	}

	if (str_len == 3)
		return wareki;
	else if (str_len == 5)
		return wareki + str.substring(3, 5);
	else
		return wareki + str.substring(3, 7);
}

/**
 * 西暦を西暦に変更する。
 *
 * @param str
 *            和暦
 * @return {String}
 */
function ad2ja(str) { //

	if (str == null || str == '') {
		return "";
	}

	var f = getWarekiFrame();
	/*
	 * var f; var tmp_win = getPrimaryWindow(); f =
	 * tmp_win.frames[0].frames[0].frmWareki;
	 */

	str += "";
	var seireki = parseInt(str.substring(0, 4), 10);
	var seireki_dt = parseInt(str, 10);
	var seireki1 = '';
	var str_len = str.length;
	var len = f.wa_row.value;

	var sno = "";
	var jyy = "";
	var from_yy = "";
	var from_yymm = "";
	var from_dt = "";
	var to_yy = "";
	var to_yymm = "";
	var to_dt = "";

	if (len == 1) {
		sno = f.wa_sno.value;
		jyy = f.wa_jyy.value;
		from_yy = f.wa_from_yy.value;
		from_yymm = f.wa_from_yymm.value;
		from_dt = f.wa_from_dt.value;
		to_yy = f.wa_to_yy.value;
		to_yymm = f.wa_to_yymm.value;
		to_dt = f.wa_to_dt.value;

		if (str_len == 4) {
			if (seireki_dt >= parseInt(from_yy, 10)
					&& seireki_dt <= parseInt(to_yy, 10)) {
				seireki = seireki - (parseInt(from_yy, 10) - 1);
				seireki1 = new String(jyy);
			}
		} else if (str_len == 6) {
			if (seireki_dt >= parseInt(from_yymm, 10)
					&& seireki_dt <= parseInt(to_yymm, 10)) {
				seireki = seireki
						- (parseInt(from_yymm.substring(0, 4), 10) - 1);
				seireki1 = new String(jyy);
			}
		} else {
			if (seireki_dt >= parseInt(from_dt, 10)
					&& seireki_dt <= parseInt(to_dt, 10)) {
				seireki = seireki - (parseInt(from_dt.substring(0, 4), 10) - 1);
				seireki1 = new String(jyy);
			}
		}
	} else {
		for ( var i = 0; i < len; i++) {
			sno = f.wa_sno[i].value;
			jyy = f.wa_jyy[i].value;
			from_yy = f.wa_from_yy[i].value;
			from_yymm = f.wa_from_yymm[i].value;
			from_dt = f.wa_from_dt[i].value;
			to_yy = f.wa_to_yy[i].value;
			to_yymm = f.wa_to_yymm[i].value;
			to_dt = f.wa_to_dt[i].value;

			if (str_len == 4) {
				if (seireki_dt >= parseInt(from_yy, 10)
						&& seireki_dt <= parseInt(to_yy), 10) {
					seireki = seireki - (parseInt(from_yy, 10) - 1);
					seireki1 = new String(jyy);
					break;
				}
			} else if (str_len == 6) {
				if (seireki_dt >= parseInt(from_yymm, 10)
						&& seireki_dt <= parseInt(to_yymm, 10)) {
					seireki = seireki
							- (parseInt(from_yymm.substring(0, 4), 10) - 1);
					seireki1 = new String(jyy);
					break;
				}
			} else {
				if (seireki_dt >= parseInt(from_dt, 10)
						&& seireki_dt <= parseInt(to_dt, 10)) {
					seireki = seireki
							- (parseInt(from_dt.substring(0, 4), 10) - 1);
					seireki1 = new String(jyy);
					break;
				}
			}
		}
	}

	if (seireki < 10)
		seireki = "0" + seireki;

	if (str_len == 4)
		return seireki1 + seireki;
	else if (str_len == 6)
		return seireki1 + seireki + str.substring(4, 6);
	else
		return seireki1 + seireki + str.substring(4, 8);
}

/**
 * 最上のウィンドーオブジェクトを返還する。
 *
 * @return {Object}
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
 * トップフレームの和暦フレームを返還する。
 *
 * @return {Object}
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
 * 西暦を和暦に変更する。
 *
 * @param str
 *            西暦
 * @return {String}
 */
function ad2jaSno(str) { //

	getPrimaryWindow();

	if (str == null || str == '') {
		return "";
	}

	var f = getWarekiFrame();
	/*
	 * var f; var tmp_win = getPrimaryWindow(); f =
	 * tmp_win.frames[0].frames[0].frmWareki;
	 */

	str += "";
	var seireki = parseInt(str.substring(0, 4), 10);
	var seireki_dt = parseInt(str, 10);
	var seireki1 = '';
	var str_len = str.length;

	var len = f.wa_row.value;

	var sno = "";
	var jyy = "";
	var from_yy = "";
	var from_yymm = "";
	var from_dt = "";
	var to_yy = "";
	var to_yymm = "";
	var to_dt = "";

	if (len == 1) {
		sno = f.wa_sno.value;
		jyy = f.wa_jyy.value;
		from_yy = f.wa_from_yy.value;
		from_yymm = f.wa_from_yymm.value;
		from_dt = f.wa_from_dt.value;
		to_yy = f.wa_to_yy.value;
		to_yymm = f.wa_to_yymm.value;
		to_dt = f.wa_to_dt.value;

		if (str_len == 4) {
			if (seireki_dt >= parseInt(from_yy, 10)
					&& seireki_dt <= parseInt(to_yy, 10)) {
				seireki = seireki - (parseInt(from_yy, 10) - 1);
				seireki1 = new String(sno);
			}
		} else if (str_len == 6) {
			if (seireki_dt >= parseInt(from_yymm, 10)
					&& seireki_dt <= parseInt(to_yymm, 10)) {
				seireki = seireki
						- (parseInt(from_yymm.substring(0, 4), 10) - 1);
				seireki1 = new String(sno);
			}
		} else {
			if (seireki_dt >= parseInt(from_dt, 10)
					&& seireki_dt <= parseInt(to_dt, 10)) {
				seireki = seireki - (parseInt(from_dt.substring(0, 4), 10) - 1);
				seireki1 = new String(sno);
			}
		}
	} else {
		for ( var i = 0; i < len; i++) {
			sno = f.wa_sno[i].value;
			jyy = f.wa_jyy[i].value;
			from_yy = f.wa_from_yy[i].value;
			from_yymm = f.wa_from_yymm[i].value;
			from_dt = f.wa_from_dt[i].value;
			to_yy = f.wa_to_yy[i].value;
			to_yymm = f.wa_to_yymm[i].value;
			to_dt = f.wa_to_dt[i].value;

			if (str_len == 4) {
				if (seireki_dt >= parseInt(from_yy, 10)
						&& seireki_dt <= parseInt(to_yy, 10)) {
					seireki = seireki - (parseInt(from_yy, 10) - 1);
					seireki1 = new String(sno);
					break;
				}
			} else if (str_len == 6) {
				if (seireki_dt >= parseInt(from_yymm, 10)
						&& seireki_dt <= parseInt(to_yymm, 10)) {
					seireki = seireki
							- (parseInt(from_yymm.substring(0, 4), 10) - 1);
					seireki1 = new String(sno);
					break;
				}
			} else {
				if (seireki_dt >= parseInt(from_dt, 10)
						&& seireki_dt <= parseInt(to_dt, 10)) {
					seireki = seireki
							- (parseInt(from_dt.substring(0, 4), 10) - 1);
					seireki1 = new String(sno);
					break;
				}
			}
		}
	}

	if (seireki < 10)
		seireki = "0" + seireki;

	if (str_len == 4)
		return seireki1 + seireki;
	else if (str_len == 6)
		return seireki1 + seireki + str.substring(4, 6);
	else
		return seireki1 + seireki + str.substring(4, 8);
}

/**
 * 入力しyyyymmdd,yyyymm,yyyy,yyymmdd,yyymm,yyy形式をnyymmdd,nyymm,nyyに変更して返還する。
 *
 * @param str
 *            日付形式の入力値
 * @return {String}
 */

function dateJNF(str) {

	var org_str = str;
	if (chkNumber(str)) {

		if (str == null || str == '') {
			return "";
		}

		var f = getWarekiFrame();
		/*
		 * var f; var tmp_win = getPrimaryWindow(); f =
		 * tmp_win.frames[0].frames[0].frmWareki;
		 */

		if (str.length == 3 || str.length == 5 || str.length == 7)
			str = ja2ad(str);

		if (isNaN(str))
			return "";

		/*
		 * if (!is_valid_dates(str)) return "";
		 */
		str += "";
		var seireki = parseInt(str.substring(0, 4), 10);
		var seireki_dt = parseInt(str, 10);

		var seireki1 = '';
		var str_len = str.length;

		var len = f.wa_row.value;

		var sno = "";
		var jyy = "";
		var from_yy = "";
		var from_yymm = "";
		var from_dt = "";
		var to_yy = "";
		var to_yymm = "";
		var to_dt = "";

		if (len == 1) {
			sno = f.wa_sno.value;
			jyy = f.wa_jyy.value;
			from_yy = f.wa_from_yy.value;
			from_yymm = f.wa_from_yymm.value;
			from_dt = f.wa_from_dt.value;
			to_yy = f.wa_to_yy.value;
			to_yymm = f.wa_to_yymm.value;
			to_dt = f.wa_to_dt.value;

			if (str_len == 4) {
				if (seireki_dt >= parseInt(from_yy, 10)
						&& seireki_dt <= parseInt(to_yy, 10)) {
					seireki = seireki - (parseInt(from_yy, 10) - 1);
					seireki1 = new String(jyy);
				}
			} else if (str_len == 6) {
				if (seireki_dt >= parseInt(from_yymm, 10)
						&& seireki_dt <= parseInt(to_yymm, 10)) {
					seireki = seireki
							- (parseInt(from_yymm.substring(0, 4), 10) - 1);
					seireki1 = new String(jyy);
				}
			} else {
				if (seireki_dt >= parseInt(from_dt, 10)
						&& seireki_dt <= parseInt(to_dt, 10)) {
					seireki = seireki
							- (parseInt(from_dt.substring(0, 4), 10) - 1);
					seireki1 = new String(jyy);
				}
			}
		} else {
			for ( var i = 0; i < len; i++) {
				sno = f.wa_sno[i].value;
				jyy = f.wa_jyy[i].value;
				from_yy = f.wa_from_yy[i].value;
				from_yymm = f.wa_from_yymm[i].value;
				from_dt = f.wa_from_dt[i].value;
				to_yy = f.wa_to_yy[i].value;
				to_yymm = f.wa_to_yymm[i].value;
				to_dt = f.wa_to_dt[i].value;

				if (str_len == 3) {
					if (seireki_dt >= parseInt(from_yy, 10)
							&& seireki_dt <= parseInt(to_yy, 10)) {
						seireki = seireki - (parseInt(from_yy, 10) - 1);
						seireki1 = new String(jyy);
						break;
					}
				} else if (str_len == 4) {
					if (seireki_dt >= parseInt(from_yy, 10)
							&& seireki_dt <= parseInt(to_yy, 10)) {
						seireki = seireki - (parseInt(from_yy, 10) - 1);
						seireki1 = new String(jyy);
						break;
					}
				} else if (str_len == 6) {
					if (seireki_dt >= parseInt(from_yymm, 10)
							&& seireki_dt <= parseInt(to_yymm, 10)) {
						seireki = seireki
								- (parseInt(from_yymm.substring(0, 4), 10) - 1);
						seireki1 = new String(jyy);
						break;
					}
				} else {
					if (seireki_dt >= parseInt(from_dt, 10)
							&& seireki_dt <= parseInt(to_dt, 10)) {
						seireki = seireki
								- (parseInt(from_dt.substring(0, 4), 10) - 1);
						seireki1 = new String(jyy);
						break;
					}
				}
			}
		}
		if (seireki < 10)
			seireki = "0" + seireki;
		if (str_len == 4)
			return seireki1 + seireki;
		else if (str_len == 6)
			return seireki1 + seireki + str.substring(4, 6);
		else
			return seireki1 + seireki + str.substring(4, 8);
	} else
		return str;
}

/**
 * 日付形式の入力値に'.'を追加
 *
 * @param objValue
 *            yyymm, yyymmdd 形式の入力値
 * @return {String}
 */
function addDateDelimiterDotJA(objValue) {
	if (objValue.length == 7) {
		year = objValue.substring(0, 3);
		month = objValue.substring(3, 5);
		day = objValue.substring(5, 7);
		return year + "." + month + "." + day;
	} else if (objValue.length == 5) {
		year = objValue.substring(0, 3);
		month = objValue.substring(3, 5);
		return year + "." + month;
	} else
		return objValue;
}

/**
 * 入力しyyyymmdd,yyyymm,yyyy,yyymmdd,yyymm,yyy形式をnyy. m. d,nyy. m,nyyに変更して返還する。
 *
 * @param str
 *            日付形式の入力値
 * @return {String}
 */
function dateJNF2(str) {

	str = addDateDelimiterDotJA(dateJNF(str));

	if (str.substring(4, 5) == "0")
		str = str.substring(0, 4) + " " + str.substring(5);
	if (str.substring(7, 8) == "0")
		str = str.substring(0, 7) + " " + str.substring(8);

	return str;
}

/**
 * 入力しyyyymmdd,yyyymm,yyyy,yyymmdd,yyymm,yyy形式をnyy.mmdd,nyy.mm,nyyに変更する。
 *
 * @param str
 *            入力された内容があるForm Elementオブジェクト
 * @return
 */
function updDateJNFwithZero(obj) {

	var objOrigin = obj.value;

	if (objOrigin.length > 2 && objOrigin.substring(0, 3) == "999") {
		return;
	}

	if (obj.value != "") {

		var seireki = ja2ad(obj.value);

		if (seireki.length == 4)
			seireki = seireki + "0101";
		else if (seireki.length == 6)
			seireki = seireki + "01";

		if (!is_valid_dates(seireki)) {
			obj.value = objOrigin;
			obj.focus();
			obj.select();
			return;
		}

		var str = addDateDelimiterDotJA(dateJNF(obj.value));
		if (str == "") {
			alert("入力に誤りがあります。");
			obj.value = objOrigin;
			obj.focus();
			obj.select();
		} else
			obj.value = str;
	} else {
		obj.value = "";
	}
}

/**
 * 入力しyyyymmdd,yyyymm,yyyy,yyymmdd,yyymm,yyy形式をnyy. m. d,nyy. m,nyyに変更する。
 *
 * @param obj
 *            入力された内容があるForm Elementオブジェクト
 * @return
 */
function updDateJNFwithSpace(obj) {
	var str = dateJNF2(obj.value);
	obj.value = str;
}

/**
 * 入力しyyy.mm.dd,yyy.mm,yyy,形式をnyymmｄd,nyymm,nyyに変更する。
 *
 * @param obj
 *            入力された内容があるForm Elementオブジェクト
 * @return
 */
function updJNF2WA(obj) {

	var str = obj.value;

	if (str.length > 2 && str.substring(0, 3) == "999") {
		return;
	}

	var strOrigin = str;
	str = replaceAll(str, ".", "");
	str = replaceAll(str, " ", "0");

	var dstr = ad2jaSno(ja2ad(str))
	if (dstr.indexOf("NaN") < 0) {
		obj.value = dstr;

	}
	obj.select();
}

/**
 * 入力した文字列に含まれた特定文字(列)を全て異なる文字(列)に置き換え、返還する。
 *
 * @param str
 *            入力された文字列
 * @param orgStr
 *            変更する文字(列)
 * @param repStr
 *            置き換え文字(列)
 * @return {String}
 */
function replaceAll(str, orgStr, repStr) {
	return str.split(orgStr).join(repStr);
}

/**
 * [selText]タグライブラリ参照関数(個別の呼び出しは行わない)
 *
 * @param objText
 *            テキストボックスオブジェクト
 * @param objSel
 *            セレクトボックスオブジェクト
 * @return
 */
function setText(objText, objSel) {

	objText.value = objSel.value;
}

/**
 * [selText]タグライブラリ参照関数(個別の呼び出しは行わない)
 *
 * @param objText
 *            テキストボックスオブジェクト
 * @param objSel
 *            セレクトボックスオブジェクト
 * @return
 */
function setSelect(objText, objSel) {

	objSel.value = objText.value;
}

/**
 * InputBoxの値でSelectBoxの選択値が変更
 *
 * @param objText
 *            テキストボックスオブジェクト
 * @param objSel
 *            セレクトボックスオブジェクト
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
 * 4200521形式の入力値を平成20年05月21日の形式で変更するメソード
 *
 * @param str
 *            日付形式の入力値
 * @return {String}
 */
function dateJNFNm(str) {

	if (chkNumber(str)) {

		if (str == null || str == '') {
			return "";
		}

		var f = getWarekiFrame();
		/*
		 * var f; var tmp_win = getPrimaryWindow(); f =
		 * tmp_win.frames[0].frames[0].frmWareki;
		 */

		if (str.length == 3 || str.length == 5 || str.length == 7)
			str = ja2ad(str);

		str += "";
		var seireki = parseInt(str.substring(0, 4), 10);
		var seireki_dt = parseInt(str, 10);
		var seireki1 = '';
		var str_len = str.length;

		var len = f.wa_row.value;

		var sno = "";
		var jyy_cc = "";
		var from_yy = "";
		var from_yymm = "";
		var from_dt = "";
		var to_yy = "";
		var to_yymm = "";
		var to_dt = "";

		if (len == 1) {
			sno = f.wa_sno.value;
			jyy_cc = f.wa_jyy_cc.value;
			from_yy = f.wa_from_yy.value;
			from_yymm = f.wa_from_yymm.value;
			from_dt = f.wa_from_dt.value;
			to_yy = f.wa_to_yy.value;
			to_yymm = f.wa_to_yymm.value;
			to_dt = f.wa_to_dt.value;

			if (str_len == 4) {
				if (seireki_dt >= parseInt(from_yy, 10)
						&& seireki_dt <= parseInt(to_yy, 10)) {
					seireki = seireki - (parseInt(from_yy, 10) - 1);
					seireki1 = new String(jyy_cc);
				}
			} else if (str_len == 6) {
				if (seireki_dt >= parseInt(from_yymm, 10)
						&& seireki_dt <= parseInt(to_yymm, 10)) {
					seireki = seireki
							- (parseInt(from_yymm.substring(0, 4), 10) - 1);
					seireki1 = new String(jyy_cc);
				}
			} else {
				if (seireki_dt >= parseInt(from_dt, 10)
						&& seireki_dt <= parseInt(to_dt, 10)) {
					seireki = seireki
							- (parseInt(from_dt.substring(0, 4), 10) - 1);
					seireki1 = new String(jyy_cc);
				}
			}
		} else {
			for ( var i = 0; i < len; i++) {
				sno = f.wa_sno[i].value;
				jyy_cc = f.wa_jyy_cc[i].value;
				from_yy = f.wa_from_yy[i].value;
				from_yymm = f.wa_from_yymm[i].value;
				from_dt = f.wa_from_dt[i].value;
				to_yy = f.wa_to_yy[i].value;
				to_yymm = f.wa_to_yymm[i].value;
				to_dt = f.wa_to_dt[i].value;

				if (str_len == 3) {
					if (seireki_dt >= parseInt(from_yy, 10)
							&& seireki_dt <= parseInt(to_yy, 10)) {
						seireki = seireki - (parseInt(from_yy, 10) - 1);
						seireki1 = new String(jyy_cc);
						break;
					}
				} else if (str_len == 4) {
					if (seireki_dt >= parseInt(from_yy, 10)
							&& seireki_dt <= parseInt(to_yy, 10)) {
						seireki = seireki - (parseInt(from_yy, 10) - 1);
						seireki1 = new String(jyy_cc);
						break;
					}
				} else if (str_len == 6) {
					if (seireki_dt >= parseInt(from_yymm, 10)
							&& seireki_dt <= parseInt(to_yymm, 10)) {
						seireki = seireki
								- (parseInt(from_yymm.substring(0, 4), 10) - 1);
						seireki1 = new String(jyy_cc);
						break;
					}
				} else {
					if (seireki_dt >= parseInt(from_dt, 10)
							&& seireki_dt <= parseInt(to_dt, 10)) {
						seireki = seireki
								- (parseInt(from_dt.substring(0, 4), 10) - 1);
						seireki1 = new String(jyy_cc);
						break;
					}
				}
			}
		}
		if (seireki < 10)
			seireki = "0" + seireki;
		if (str_len == 4)
			return seireki1 + seireki;
		else if (str_len == 6)
			return seireki1 + seireki + "年" + str.substring(4, 6) + "月";
		else
			return seireki1 + seireki + "年" + str.substring(4, 6) + "月"
					+ str.substring(6, 8) + "日";
	} else
		return str;
}

/**
 * 4200521形式の入力値を平成20年05月21日の形式で変更する。
 *
 * @param obj
 *            入力された内容があるForm Elementオブジェクト
 * @return
 */
function updDateJNFNmwithZero(obj) {
	var str = dateJNFNm(obj.value);
	obj.value = str;
}

/**
 * 通貨形式のデータに区分する記号を入れる。
 *
 * @param obj
 *            入力された内容があるForm Elementオブジェクト
 * @return
 */
function addCurrencyDelimObj(obj) {
	if (obj.value != '') {
		var str = trim(obj.value.replace(/,/gi, ""));
		var negative = 0;

		if (str.indexOf('-') == 0) {
			str = str.replace(/-/gi, "");
		} else
			negative = -1;

		var strLength = str.length;
		var index = 0;

		if (str.indexOf('.') == -1)
			index = strLength - 1;
		else
			index = str.indexOf('.') - 1;

		var minus = "";
		if (negative > -1)
			minus = "-";

		var res = "";
		var p = 0;
		for (i = index; i >= 0; i--, p++) {
			if ((p != 0) && ((p % 3) == 0))
				res = "," + res;

			res = str.charAt(i) + res;
		}

		obj.value = minus + res + str.substr(index + 1);
	}
}

/**
 * 通貨形式のデータに区分する記号を入れて返還する。
 *
 * @param objvalue
 *            入力値
 * @return
 */
function addCurrencyDelimValue(objvalue) {
	var str = trim(objvalue.replace(/,/gi, ""));
	var negative = 0;

	if (str.indexOf('-') == 0) {
		str = str.replace(/-/gi, "");
	} else
		negative = -1;

	var strLength = str.length;
	var index = 0;

	if (str.indexOf('.') == -1)
		index = strLength - 1;
	else
		index = str.indexOf('.') - 1;

	var minus = "";
	if (negative > -1)
		minus = "-";

	var res = "";
	var p = 0;
	for (i = index; i >= 0; i--, p++) {
		if ((p != 0) && ((p % 3) == 0))
			res = "," + res;

		res = str.charAt(i) + res;
	}

	return res + minus + res + str.substr(index + 1);
}

/**
 * 通貨形式のデータに区分する記号を除去する。
 *
 * @param obj
 *            入力された内容があるForm Elementオブジェクト
 * @return
 */
function delCurrencyDelimObj(obj) {
	if (obj.value != '') {
		obj.value = obj.value.replace(/,/gi, "")
		obj.select();
	}
}

/**
 * 通貨形式のデータに区分する記号を入れ、空白が入力された場合は0で処理する。
 *
 * @param obj
 *            入力された内容があるForm Elementオブジェクト
 * @return
 */
function ChgCurrencyBase(obj) {
	var temp = obj.value.replace(/,/gi, "");
	if (trim(temp).length == 0) {
		obj.value = "0";
	} else {
		obj.value = parseInt(temp, 10);
		addCurrencyDelimObj(obj);
	}
}

/**
 * 通貨形式のデータに区分する記号を入れ、空白が入力された場合は空白で処理する。
 *
 * @param obj
 *            入力された内容があるForm Elementオブジェクト
 * @return
 */
function ChgCurrencyNull(obj) {
	var temp = obj.value.replace(/,/gi, "");
	if (trim(temp).length == 0) {
		obj.value = "";
	} else {
		obj.value = parseInt(temp, 10);
		addCurrencyDelimObj(obj);
	}
}

/**
 * 通貨形式のデータに区分する記号を入れ、空白が入力された場合は0で処理する。(サブキー使用禁止)
 *
 * @param obj
 *            入力された内容があるForm Elementオブジェクト
 * @return
 */
function ChgCurrency(obj) {
	if (!is_keycode_subkey()) {
		var temp = obj.value.replace(/,/gi, "");
		if (trim(temp).length == 0) {
			obj.value = "0";
		} else {
			obj.value = parseInt(temp, 10);
			addCurrencyDelimObj(obj);
		}
	}
}

/**
 * 通貨形式のデータに区分する記号を入れ、空白が入力された場合は0で処理する。(サブキー使用禁止)
 *
 * @param obj
 *            入力された内容があるForm Elementオブジェクト
 * @return
 */
function ChgCurrencyZero(obj) {
	if (!is_keycode_subkey()) {
		var temp = obj.value.replace(/,/gi, "");
		if (trim(temp).length == 0) {
			obj.value = "0";
		} else {
			addCurrencyDelimObj(obj);
		}
	}
}

/**
 * フォーム内の全ての通貨形式のデータに区分する記号を入れ、空白が入力された場合は0で処理する。
 *
 * @param f_nm
 *            フォーム名
 * @return
 */
function ChgCurrencyAll(f_nm) {
	var obj_f = eval("document." + f_nm);
	for ( var i = 0; i < obj_f.length; i++) {
		if (obj_f.elements[i].cha_chk == "NC") {
			ChgCurrencyBase(obj_f.elements[i]);
		}
	}
}

/**
 * フォーム内の全オブジェクトのtabIndexを確認し、tabIndexが0より少ない、かつ同じ値のオブジェクトはフォーカスの移動を禁止する。
 *
 * @param f_nm
 *            フォーム名
 * @return
 */
function tabSetter() {
	for ( var i = 0; i < document.all.length; i++) {
		if (!document.all[i].tabIndex || document.all[i].tabIndex <= 0) {
			document.all[i].tabIndex = "-1";
		}

	}
}

/**
 * 和暦を1年後の西暦に変更して返還する。
 *
 * @param str
 *            和暦
 * @return {String}
 */
function ja2adNext(str) { //
	if (str == null || str == '') {
		return "";
	}

	var f = getWarekiFrame();
	/*
	 * var f; var tmp_win = getPrimaryWindow(); if (tmp_win.frames[0].frmWareki ==
	 * null) { f = tmp_win.frames[0].frames[0].frmWareki; } else { f =
	 * tmp_win.frames[0].frmWareki; }
	 */
	str += "";
	var wareki = parseInt(str.substring(1, 3), 10);
	var str_sno = str.substring(0, 1);
	var str_len = str.length;

	var len = f.wa_row.value;

	var sno = '';
	var jyy = '';
	var from_dt = '';
	var to_yy = '';
	var prd = '';

	var check = false;

	if (len == 1) {
		sno = f.wa_sno.value;
		jyy = f.wa_jyy.value;
		from_dt = f.wa_from_dt.value;
		to_yy = f.wa_to_yy.value;
		prd = (parseInt(to_yy, 10)) - (parseInt(from_dt.substring(0, 4), 10));

		if (str_sno == sno || str_sno == jyy) {
			if (wareki > prd)
				wareki = prd;
			wareki = wareki + (parseInt(from_dt.substring(0, 4), 10) - 1);
			check = true;
		} else {
			check = false;
		}

	} else {
		for ( var i = 0; i < len; i++) {
			sno = f.wa_sno[i].value;
			jyy = f.wa_jyy[i].value;
			from_dt = f.wa_from_dt[i].value;
			to_yy = f.wa_to_yy[i].value;
			prd = 1 + (parseInt(to_yy, 10))
					- (parseInt(from_dt.substring(0, 4), 10));

			if (str_sno == sno || str_sno == jyy) {
				if (wareki > prd)
					wareki = prd + 1;
				wareki = wareki + (parseInt(from_dt.substring(0, 4), 10) - 1);
				check = true;
				break;
			} else {
				check = false;
			}
		}
	}

	if (!check) {
		wareki = 'エラー';
	}

	if (str_len == 3)
		return wareki;
	else if (str_len == 5)
		return wareki + str.substring(3, 5);
	else
		return wareki + str.substring(3, 7);
}

/**
 * 入力年度の1年前に移動する。
 *
 * @param Obj
 *            入力された内容があるForm Elementオブジェクト
 * @return {boolean}
 */
function goPreYY(Obj) {
	var today = new Date();
	var tY = today.getYear();
	var temp;

	if (Obj.value == null || Obj.value == "")
		temp = tY;
	else
		temp = parseInt(ja2ad(Obj.value)) - 1;

	if (temp < 1868) {
		alert(getErrMsg('EJCO027', '年度', '101', ad2jaSno(tY)));
		return false;
	} else {
		Obj.value = dateJNF(temp);
		return true;
	}

}

/**
 * 入力年度の1年後に移動する。(現年度を超過しない。)
 *
 * @param Obj
 *            入力された内容があるForm Elementオブジェクト
 * @return {boolean}
 */
function goNextYY(Obj) {
	var today = new Date();
	var tY = today.getYear();
	var temp;

	if (Obj.value == null || Obj.value == "")
		temp = tY;
	else
		temp = parseInt(ja2adNext(Obj.value)) + 1;

	if (temp > tY) {
		alert(getErrMsg('EJCO027', '年度', '101', ad2jaSno(tY)));
		return false;
	} else {
		Obj.value = dateJNF(temp);
		return true;
	}
}

/**
 * 入力年度の1年後に移動する。(パラメータの値によって現年度を超過することもある。)
 *
 * @param Obj
 *            入力された内容があるForm Elementオブジェクト
 * @param chk
 *            現年度超過チェック
 * @return {boolean}
 */
function goNextYY(Obj, chk) {
	var today = new Date();
	var tY = today.getYear();
	var temp;

	if (Obj.value == null || Obj.value == "")
		temp = tY;
	else
		temp = parseInt(ja2adNext(Obj.value)) + 1;

	if (temp > tY & chk != null) {
		alert(getErrMsg('EJCO027', '年度', '101', ad2jaSno(tY)));
		return false;
	} else {
		Obj.value = dateJNF(temp);
		return true;
	}
}

/**
 * 入力年度が入力可能な年度かを確認する。
 *
 * @param Obj
 *            入力された内容があるForm Elementオブジェクト
 * @return {boolean}
 */
function chkYY(Obj) {
	var ObjValue = Obj.value;
	if (ObjValue != '') {
		var now = ad2jaSno(getCurrentDate());
		var n_yy = now.substr(0, 3);
		if (ObjValue > n_yy || ObjValue < 101) {
			alert(getErrMsg('EJCO027', '年度', '101', ad2jaSno(n_yy)));
			Obj.focus();
			return false;
		} else
			return true;
	} else
		return false;
}

/**
 * 入力年度が入力可能な年度かを確認し、yyyy,yyy形式をnyyに変更する。
 *
 * @param Obj
 * @return
 */
function chkYear(Obj) {
	if (chkYY(Obj)) {
		updDateJNFwithZero(Obj);
	}
}

/**
 * 入力日付が入力可能な日付かを確認する。
 *
 * @param Obj
 *            入力された内容があるForm Elementオブジェクト
 * @return {boolean}
 */
function chkDateJa(Obj) {
	if (Obj.value != '') {
		var StrDate = Obj.value;
		var StrMatch = StrDate.match(/^(\d{3})(\d{2})(\d{2})$/);
		if (StrMatch == null) {
			alert(getErrMsg('EJCO052', '日時', '7', ''));
			Obj.value = '';
			Obj.focus();
			return false;
		}

		StrDate = ja2ad(StrDate);
		StrYear = StrDate.substr(0, 4);

		var ObjDate = new Date(StrYear, RegExp.$2 - 1, RegExp.$3);

		if (RegExp.$3 != ObjDate.getDate()) {
			alert(getErrMsg('EJCO050', '日時', '', ''));
			Obj.focus();
			return false;
		}
		if (RegExp.$2 != ObjDate.getMonth() + 1) {
			alert(getErrMsg('EJCO050', '日時', '', ''));
			Obj.focus();
			return false;
		}
		if (StrYear != ObjDate.getFullYear()) {
			alert(getErrMsg('EJCO050', '日時', '', ''));
			Obj.focus();
			return false;
		}

		return true;
	} else
		return false;
}

/**
 * 入力日付が入力可能な日付かを確認するyyyymmdd,yyyymm,yyyy,yyymmdd,yyymm,yyy形式をnyy.mmdd,nyy.mm,nyyに変更する。
 *
 * @param Obj
 *            入力された内容があるForm Elementオブジェクト
 * @return {boolean}
 */
function chkDateValue(Obj) {
	if (chkDateJa(Obj)) {
		updDateJNFwithZero(Obj);
	}
}

// 検索履歴関連関数//////////////////////////////////////////////////////

/**
 * 検索履歴に個人番号と氏名を追加する。
 *
 * @param icno
 *            個人番号
 * @param kana_nm
 *            氏名
 * @return
 */
function updSessSrchHist(icno, kana_nm) {

	frmSrchHist.icno.value = icno;
	frmSrchHist.kana_nm.value = kana_nm;
	frmSrchHist.target = "sessionUpdate";
	frmSrchHist.action = "/common/User.do?method=updSrchHisInfo";
	frmSrchHist.submit();
}

/**
 * 検索履歴を照会してレイヤに表示する。
 *
 * @param objname
 *            個人番号照会オブジェクト名
 * @param left
 *            レイヤが表示される画面でのx座標
 * @param top
 *            レイヤが表示される画面でのy座標
 * @return
 */
function showSrchHist(objname, left, top) {

	showSrchHist3(objname, left, top, 190);

	/*
	 * var obj = eval(objname);
	 *
	 * frmSrchHist.objname.value = objname; frmSrchHist.target =
	 * "searchHistory"; frmSrchHist.action =
	 * "/pages/common/pg/copgSrchHist.jsp"; frmSrchHist.submit();
	 *
	 * document.getElementById("layerSrchHist").style.left = left;
	 * document.getElementById("layerSrchHist").style.top = top;
	 * document.getElementById("layerSrchHist").style.visibility = "visible";
	 */
}

/**
 * 検索履歴を照会してレイヤに表示する。
 *
 * @param objname
 *            個人番号照会オブジェクト名
 * @param left
 *            レイヤが表示される画面でのx座標
 * @param top
 *            レイヤが表示される画面でのy座標
 * @return
 */
function showSrchHist3(objname, left, top, height) {

	var obj = eval(objname);

	frmSrchHist.objname.value = objname;
	frmSrchHist.target = "searchHistory";
	frmSrchHist.action = "/pages/common/pg/copgSrchHist.jsp";
	frmSrchHist.submit();

	document.getElementById("layerSrchHist").style.left = left;
	document.getElementById("layerSrchHist").style.top = top;

	document.getElementById("view_td").height = height + 2;
	document.getElementById("layerSrchHist2").style.height = height;
	document.getElementById("searchHistory").style.height = height;
	document.getElementById("layerSrchHist").style.visibility = "visible";

	obj.onblurx = function() {
		if (event.clientX > left && event.clientX < left + 211
				&& event.clientY > top && event.clientY < top + height) {
		} else {
			document.getElementById("layerSrchHist").style.visibility = "hidden";
		}
	}

	var regExp = /;this.onblurx()/;
	if (obj.add_blur) {
		if (obj.add_blur.search(regExp) == -1)
			obj.add_blur += ";this.onblurx()";
	} else {
		obj.add_blur = "this.onblurx()"
	}
}

/**
 * （共通liveany-common.jsの関数-showSrchHist()の他のバージョン） 検索履歴を照会してレイヤに表示する。
 * showSrchHist2()はx,y位置を絶対値はではなく、自動で該当オブジェクトの位置を求めてセットする。
 *
 * @param objname
 *            個人番号照会オブジェクト名
 * @param xPlus
 *            自動にセットされる位置からのプラスX位置（必須ではない）
 * @param yPlus
 *            自動にセットされる位置からのプラスY位置（必須ではない）
 * @return
 */
function showSrchHist2(objname, xPlus, yPlus) {

	showSrchHist4(objname, xPlus, yPlus, 158);

	/*
	 * xPlus = xPlus != null ? Number(xPlus) : 0; yPlus = yPlus != null ?
	 * Number(yPlus) : 0;
	 *
	 * var obj = eval(objname);
	 *
	 * frmSrchHist.objname.value = objname; frmSrchHist.target =
	 * "searchHistory"; frmSrchHist.action =
	 * "/pages/common/pg/copgSrchHist.jsp"; frmSrchHist.submit();
	 *
	 * document.getElementById("layerSrchHist").style.left = event.clientX +
	 * (event.offsetX * -1) + document.body.scrollLeft - 1 + xPlus;
	 * document.getElementById("layerSrchHist").style.top = event.clientY +
	 * (event.offsetY * -1) + document.body.scrollTop + 16 + yPlus;
	 * document.getElementById("layerSrchHist").style.visibility = "visible";
	 */

}

/**
 * （共通liveany-common.jsの関数-showSrchHist()の他のバージョン） 検索履歴を照会してレイヤに表示する。
 * showSrchHist2()はx,y位置を絶対値はではなく、自動で該当オブジェクトの位置を求めてセットする。
 *
 * @param objname
 *            個人番号照会オブジェクト名
 * @param xPlus
 *            自動にセットされる位置からのプラスX位置（必須ではない）
 * @param yPlus
 *            自動にセットされる位置からのプラスY位置（必須ではない）
 * @return
 */
function showSrchHist4(objname, xPlus, yPlus, height) {

	xPlus = xPlus != null ? Number(xPlus) : 0;
	yPlus = yPlus != null ? Number(yPlus) : 0;

	var obj = eval(objname);

	frmSrchHist.objname.value = objname;
	frmSrchHist.target = "searchHistory";
	frmSrchHist.action = "/pages/common/pg/copgSrchHist.jsp";
	frmSrchHist.submit();

	document.getElementById("layerSrchHist").style.left = event.clientX
			+ (event.offsetX * -1) + document.body.scrollLeft - 1 + xPlus;
	document.getElementById("layerSrchHist").style.top = event.clientY
			+ (event.offsetY * -1) + document.body.scrollTop + 16 + yPlus;
	document.getElementById("view_td").height = height + 2;

	document.getElementById("layerSrchHist2").style.height = height;
	document.getElementById("layerSrchHist").style.visibility = "visible";

	obj.onblurx = function() {
		if (event.clientX > event.clientX + (event.offsetX * -1)
				+ document.body.scrollLeft - 1 + xPlus
				&& event.clientX < event.clientX + (event.offsetX * -1)
						+ document.body.scrollLeft - 1 + xPlus + 211
				&& event.clientY > event.clientY + (event.offsetY * -1)
						+ document.body.scrollTop + 16 + yPlus
				&& event.clientY < event.clientY + (event.offsetY * -1)
						+ document.body.scrollTop + 16 + yPlus + height) {
		} else {
			document.getElementById("layerSrchHist").style.visibility = "hidden";
		}
	}

	var regExp = /;this.onblurx()/;
	if (obj.add_blur) {
		if (obj.add_blur.search(regExp) == -1)
			obj.add_blur += ";this.onblurx()";
	} else {
		obj.add_blur = "this.onblurx()"
	}
}

/**
 * 検索履歴照会画面を隠す。
 *
 * @return
 */
function hideSrchHist() {

	document.getElementById("layerSrchHist").style.visibility = "hidden";

}

// オンラインへループ関連関数//////////////////////////////////////////////////////

/**
 * オンラインヘルプファイルを表示する。
 *
 * @param sc_id
 *            画面ID
 * @return
 */
function view_help(sc_id) {

	var path = sc_id.substring(0, 6);
	path = path.toLowerCase();

	if (sc_id.substring(0, 6) == '/commo')
		path = '/common';

	window
			.open(
					'/common/Menu.do?method=getManualName&flag=help&v_path='
							+ path + '&sc_id=' + sc_id,
					'',
					'width='
							+ (window.screen.width / 100 * 50)
							+ ', height='
							+ window.screen.height
							+ ', resizable=1, scrollbars=1, toolbar=0, status=0, menubar=0, top=0, left='
							+ (window.screen.width - (window.screen.width / 100 * 50)));
}

/**
 * オンラインマニュアルファイルを表示する。
 *
 * @param sc_id
 *            画面ID
 * @return
 */
function view_manual(sc_id) {

	var path = sc_id.substring(0, 6);
	path = path.toLowerCase();

	if (sc_id.substring(0, 6) == '/commo')
		path = '/common';

	window
			.open(
					'/common/Menu.do?method=getManualName&flag=manual&v_path='
							+ path + '&sc_id=' + sc_id,
					'',
					'width='
							+ (window.screen.width / 100 * 50)
							+ ', height='
							+ window.screen.height
							+ ', resizable=1, scrollbars=1, toolbar=0, status=0, menubar=0, top=0, left='
							+ (window.screen.width - (window.screen.width / 100 * 50)));
}

/**
 * ファイルアップロード機能で使用する。
 *
 * @param list_target
 *            アップロードフィルリスト
 * @param max
 *            アップロードファイルの最大値
 * @return
 */
function MultiSelector(list_target, max) {

	// Where to write the list
	this.list_target = list_target;
	// How many elements?
	this.count = 0;
	// How many elements?
	this.id = 0;
	// Is there a maximum?
	if (max) {
		this.max = max;
	} else {
		this.max = -1;
	}
	;

	/**
	 * Add a new file input element
	 */
	this.addElement = function(element) {

		// Make sure it's a file input element
		if (element.tagName == 'INPUT' && element.type == 'file') {

			// Element name -- what number am I?
			element.name = 'file';

			// Add reference to this object
			element.multi_selector = this;

			// What to do when a file is selected
			element.onchange = function() {

				// New file input
				var new_element = document.createElement('input');
				new_element.type = 'file';
				new_element.style.width = '0px';
				new_element.style.height = '18px';
				new_element.style.filter = 'alpha(opacity=0)';
				// new_element.setAttribute('style','width:0;height:18;filter:alpha(opacity=0)');

				// Add new element
				this.parentNode.insertBefore(new_element, this);

				// Apply 'update' to element
				this.multi_selector.addElement(new_element);

				// Update list
				this.multi_selector.addListRow(this);

				// Hide this
				this.style.position = 'absolute';
				this.style.left = '-1000px';

			};
			// If we've reached maximum number, disable input element
			if (this.max != -1 && this.count >= this.max) {
				element.disabled = true;
			}
			;

			// File element counter
			this.count++;
			// Most recent element
			this.current_element = element;

		} else {
			// This can only be applied to file input elements!
			alert('Error: not a file input element');
		}
		;

	};

	/**
	 * Add a new row to the list of files
	 */
	this.addListRow = function(element) {

		// Row div
		var new_row = document.createElement('div');

		// Delete button
		var new_row_button = document.createElement('input');
		new_row_button.type = 'button';
		new_row_button.value = 'Delete';

		// References
		new_row.element = element;

		// Delete function
		new_row_button.onclick = function() {

			// Remove element from form
			this.parentNode.element.parentNode
					.removeChild(this.parentNode.element);

			// Remove this row from the list
			this.parentNode.parentNode.removeChild(this.parentNode);

			// Decrement counter
			this.parentNode.element.multi_selector.count--;

			// Re-enable input element (if it's disabled)
			this.parentNode.element.multi_selector.current_element.disabled = false;

			return false;
		};

		// Set row value
		new_row.innerHTML = element.value;

		// Add button
		new_row.appendChild(new_row_button);

		// Add it to the list
		this.list_target.appendChild(new_row);

	};

};

/**
 * 収納状況を照会するポップアップ
 *
 * @param tt_yy
 *            課税年度
 * @param wnoti_no
 *            通知書番号
 * @param tit_cd
 *            税目コード
 * @return
 */
function getRcptInfoPopup(tt_yy, wnoti_no, tit_cd) {
	openWindow("", "RCCORcptInfoUI_p", "800", "350", "no");
	document.frm.action = "/rc/co/ReInfoMng.do?method=viewRcptInfo&p_tt_yy="
			+ tt_yy + "&p_wnoti_no=" + wnoti_no + "&p_tit_cd=" + tit_cd;
	document.frm.target = "RCCORcptInfoUI_p";
	document.frm.submit();
}

function chgDateTimeFormat(obj) {

	var str = obj.value;
	var rtn = "";

	if (str.length == 18) {
		strDate = str.substring(0, 9);
		strTime = str.substring(10, 18);

		strDate = replaceAll(strDate, ".", "");
		strDate = replaceAll(strDate, " ", "0");
		strDate = ad2jaSno(ja2ad(strDate));

		strTime = replaceAll(strTime, ":", "");

		if (strDate.indexOf("NaN") < 0) {
			rtn = strDate + strTime;
		}
	} else {
		rtn = str;
	}

	obj.value = rtn;
	obj.select();

}

function dateTime2JNF(obj) {

	var dt = obj.value;
	var rtn = "";

	if (dt.length == 13) {
		rtn = rtn + addDateDelimiterDotJA(dateJNF(dt.substring(0, 7)));
		rtn = rtn + " " + dt.substring(7, 9);
		rtn = rtn + ":" + dt.substring(9, 11);
		rtn = rtn + ":" + dt.substring(11, 13);
	} else if (dt.length == 14) {
		rtn = rtn + addDateDelimiterDotJA(dateJNF(dt.substring(0, 8)));
		rtn = rtn + " " + dt.substring(7, 9);
		rtn = rtn + ":" + dt.substring(9, 11);
		rtn = rtn + ":" + dt.substring(11, 13);
	} else {
		rtn = dt;
	}

	obj.value = rtn;
}

/**
 * <pre>
 * 処理中イメージをCircleする。
 * <p>
 * </p>
 * </pre>
 *
 * @param
 * @return
 */
function comShowPB() {
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
/**
 * <pre>
 * 処理中イメージを表示する。
 * <p>
 * </p>
 * </pre>
 *
 * @param
 * @return
 */
function comEndPB() {
	var obj = document.getElementById('divProcessing');
	if (obj != null) {
		obj.style.display = "none";
	}
}
