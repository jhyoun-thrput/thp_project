/*=======================================================================
 * liveany-util.js
 *
 * 基本Util関連JavaScript
 *
 * @version 修正履歴
 *
 *          2009/05/23 : 新規作成 (revised by Noh.S.Y)
 *
 * @author Noh.S.Y
 * =======================================================================*/

/*******************************************************************************
 * 01．初期クラス名 ()
 ******************************************************************************/

// ****************************
// ***** 日付フォーマット *****
// ****************************
var FMT_NYY			 = "nyy";
var FMT_NYYMM		 = "nyymm";
var FMT_NYYMMDD      = "nyymmdd";
var FMT_YYYY         = "yyyy";
var FMT_YYYYMMDD     = "yyyymmdd";
var FMT_MM           = "mm";
var FMT_DD           = "dd";
var FMT_YYYYMM       = "yyyymm";
var FMT_YYYY_MM_DD   = "yyyy_mm_dd";
var FMT_YYYY_MM      = "yyyy_mm";
var FMT_JYY_MM_DD    = "jyy_mm_dd";
var FMT_JYY_MM       = "jyy_mm";
var FMT_JYY          = "jyy";
var FMT_JCCYY_MM_DD  = "jccyy_mm_dd";
var FMT_JCCYY_MM     = "jccyy_mm";
var FMT_JCCYY        = "jccyy";


// ************** 日付を表現するための正規表現 **************
/** 正規表現　JYY_MM_DD */
var REGEX_JYY_MM_DD = /[a-zA-Z]{1}[0-9]{2}[\.][0-9]{2}[\.][0-9]{2}$/;
/** 正規表現　YYYYMMDD */
var REGEX_YYYYMMDD = /^[0-9]{8}$/;
/** 正規表現　YYYY_MM_DD */
var REGEX_YYYY_MM_DD = /^[0-9]{4}[\/][0-9]{2}[\/][0-9]{2}$/;
/** 正規表現　NYYMMDD */
var REGEX_NYYMMDD = /^[0-9]{7}$/;
/** 正規表現　JCCYY_MM_DD */
var REGEX_JCCYY_MM_DD = /^[\W]+([0-9]{2}|元)[年][0-9]{2}[月][0-9]{2}[日]$/;
/** 正規表現　NYY */
var REGEX_NYY= /^[0-9]{3}$/;
/** 正規表現　YYYY */
var REGEX_YYYY= /^[0-9]{4}$/;
/** 正規表現　JCCYY */
var REGEX_JCCYY = /^[\W]+([0-9]{2}|元)[年]$/;
/** 正規表現　NYYMM */
var REGEX_NYYMM = /^[0-9]{5}$/;
/** 正規表現　JYY_MM */
var REGEX_JYY_MM = /[a-zA-Z]{1}[0-9]{2}[\.][0-9]{2}$/;
/** 正規表現　YYYYMM */
var REGEX_YYYYMM = /^[0-9]{6}$/;
/** 正規表現　YYYY_MM */
var REGEX_YYYY_MM = /^[0-9]{4}[\/][0-9]{2}$/;
/** 正規表現　JCCYY_MM */
var REGEX_JCCYY_MM = /^[\W]+([0-9]{2}|元)[年][0-9]{2}[月]$/;
/** 正規表現　JYY */
var REGEX_JYY = /[a-zA-Z]{1}[0-9]{2}$/;


/****************************************************************************** */

/**
 * 文字列の属性としてreplaceAll()を登録する。
 */
String.prototype.replaceAll = function (orgStr, repStr) {
	return this.split(orgStr).join(repStr);
}


/**
 * 文字列の属性としてtrim()を登録する。
 */
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g, "");
}

/**
 * 文字列から前後に空白を除去する。
 */
function trim(str) {

	var tmp = "" + str;

	// leading space
	while ('' + tmp.charAt(0) == ' ') {
		tmp = tmp.substring(1, tmp.length)
	}
	// trailing space
	while ('' + tmp.charAt(tmp.length - 1) == ' ') {
		tmp = tmp.substring(0, tmp.length - 1);
	}
	return tmp;
}



/**
 * 西暦(YYYYMMDD)へ変換処理を行う。
 * 引数の「dateStr」が下記の日付フォーマットであれば、西暦(YYYYMMDD)へ変換する。<br/>
 * <ul>
 *  <li> jyy_mm_dd        例) H21.03.09</li>
 *  <li> jyy_mm           例) H21.03</li>
 *  <li> jyy              例) H21</li>
 *  <li> nyymmdd          例) 4210309</li>
 *  <li> nyymm            例) 42103</li>
 *  <li> nyy              例) 421</li>
 *  <li> jccyy_mm_dd      例) 平成21年03月09日</li>
 *  <li> jccyy_mm         例) 平成21年03月</li>
 *  <li> jccyy            例) 平成21年</li>
 *  <li> yyyymmdd         例) 20090309</li>
 *  <li> yyyymm           例) 200903</li>
 *  <li> yyyy             例) 2009</li>
 *  <li> yyyy_mm_dd       例) 2009/03/09</li>
 *  <li> yyyy_mm          例) 2009/03</li>
 * </ul>
 * <br/>
 * 但し、①nullの場合は空白を返却する。<br/>
 *       ②日付フォーマットが正しくない場合は、「dateStr」をそのまま返却する。<br/>
 * @param dateStr 日付の文字列
 * @return 変換後の日付
 */
function conv2Seireki(dateStr) {
    var res = null;
    if (dateStr.match(REGEX_JYY_MM_DD)
            || dateStr.match(REGEX_YYYYMMDD)
            || dateStr.match(REGEX_YYYY_MM_DD)
            || dateStr.match(REGEX_NYYMMDD)
            || dateStr.match(REGEX_JCCYY_MM_DD)) {
    // 年月日
        res = conv2Date(dateStr, FMT_YYYYMMDD);
    } else if (dateStr.match(REGEX_NYY)
            || dateStr.match(REGEX_YYYY)
            || dateStr.match(REGEX_JYY)
            || dateStr.match(REGEX_JCCYY)) {
    // 年
        res = conv2Date(dateStr, FMT_YYYY);
    } else if (dateStr.match(REGEX_NYYMM)
            || dateStr.match(REGEX_JYY_MM)
            || dateStr.match(REGEX_YYYYMM)
            || dateStr.match(REGEX_YYYY_MM)
            || dateStr.match(REGEX_JCCYY_MM)) {
    // 年月
        res = conv2Date(dateStr, FMT_YYYYMM);
    }
    if (isEmpty(res)) {
        return dateStr;
    } else {
        return res;
    }
}

/**
 * 和暦(JYY_MM_DD)へ変換処理を行う。
 * 引数の「dateStr」が下記の日付フォーマットであれば、和暦(jyy_mm_dd)へ変換する。<br/>
 * <ul>
 *  <li> jyy_mm_dd        例) H21.03.09</li>
 *  <li> jyy_mm           例) H21.03</li>
 *  <li> jyy              例) H21</li>
 *  <li> nyymmdd          例) 4210309</li>
 *  <li> nyymm            例) 42103</li>
 *  <li> nyy              例) 421</li>
 *  <li> jccyy_mm_dd      例) 平成21年03月09日</li>
 *  <li> jccyy_mm         例) 平成21年03月</li>
 *  <li> jccyy            例) 平成21年</li>
 *  <li> yyyymmdd         例) 20090309</li>
 *  <li> yyyymm           例) 200903</li>
 *  <li> yyyy             例) 2009</li>
 *  <li> yyyy_mm_dd       例) 2009/03/09</li>
 *  <li> yyyy_mm          例) 2009/03</li>
 * </ul>
 * <br/>
 * 但し、①nullの場合は空白を返却する。<br/>
 *       ②日付フォーマットが正しくない場合は、「dateStr」をそのまま返却する。<br/>
 * @param dateStr 日付文字列
 * @return 変換後の文字列
 */
function conv2Wareki(dateStr) {
    var res = null;
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
        return dateStr;
    } else {
        return res;
    }
}




/**
 * 年、年月、年月日の判断。
 * 指定した日付が「年月日」の場合 'yyyymmdd'を返却
 * 指定した日付が「年月」の場合 'yyyymm'を返却
 * 指定した日付が「年」の場合 'yyyy'を返却
 * 日付が正しくない場合、ブランクを返却
 * @return
 */
function getDateType(dateStr) {
    var res = null;
    if (dateStr.match(REGEX_JYY_MM_DD)
            || dateStr.match(REGEX_YYYYMMDD)
            || dateStr.match(REGEX_YYYY_MM_DD)
            || dateStr.match(REGEX_NYYMMDD)
            || dateStr.match(REGEX_JCCYY_MM_DD)) {
    // 年月日
        return "yyyymmdd";
    } else if (dateStr.match(REGEX_NYY)
            || dateStr.match(REGEX_YYYY)
            || dateStr.match(REGEX_JYY)
            || dateStr.match(REGEX_JCCYY)) {
    // 年
        return "yyyy";
    } else if (dateStr.match(REGEX_NYYMM)
            || dateStr.match(REGEX_JYY_MM)
            || dateStr.match(REGEX_YYYYMM)
            || dateStr.match(REGEX_YYYY_MM)
            || dateStr.match(REGEX_JCCYY_MM)) {
    // 年月
        return "yyyymm";
    }
    return "";

}



/**
 * 日付変換を行う。
 *
 * 変換エラーの場合は空白を返却する。
 *
 * @param val 変換の日付対象文字列
 * @param fmt 日付フォーマット
 * @return 変換結果
 */
function conv2Date(val, fmt) {

	var f = getWarekiFrame();
	str = val.trim();

	var s_sno = ""; // 例) 4
	var s_yy = ""; // 例) 21
	var s_yyyy = ""; // 例) 2009
	var s_mm = ""; // 例) 01
	var s_dd = ""; // 例) 31

	var s_j = ""; // 例) H
	var s_j_cc = ""; // 例) 平成

	var i = 0; // loop index
	var result = ""; // 結果日付文字列

	if (isEmpty(str) || str == undefined) {
		return "";
	}

	// ----------------------------------------------------------------
	// *** 入力タイプによって計算
	// ----------------------------------------------------------------
	if (str.match(REGEX_NYYMMDD) || str.match(REGEX_NYYMM)
			|| str.match(REGEX_NYY)) {
		// nyy OR nyymm OR nyymmdd

		// ----- 日付の取得
		s_sno = str.substr(0, 1);
		s_yy = str.substr(1, 2);
		if (5 <= str.length) {
			// mmがあれば
			s_mm = str.substr(3, 2);
		}
		if (7 <= str.length) {
			// ddがあれば
			s_dd = str.substr(5);
		}

		// ----- SNO範囲チェック
		if (f.wa_row.value < s_sno || s_sno <= 0) {
			//alert("invalid_date1");
			return "";
			// throw "invalid_date";// 日付タイプではない。
		}

		// ----- s_yyyy, s_j, s_j_ccの取得
		if (f.wa_sno.length == undefined) {

		} else {

			for (i = f.wa_sno.length - 1; i >= 0; i--) {
				if (f.wa_sno[i].value == s_sno) {
					s_yyyy = "" + ((new Number(f.wa_from_yy[i].value)) + (new Number(s_yy))- 1);
					break;
				}
			}

			var idx = getWarekiInf(s_yyyy, s_mm, s_dd, f);
			if (idx == -1) {return "";}
			s_yy = s_yyyy - f.wa_from_yy[idx].value + 1;
			s_sno = f.wa_sno[idx].value;
			s_j = f.wa_jyy[idx].value;
			s_j_cc = f.wa_jyy_cc[idx].value;

		}

	} else if (str.match(REGEX_JYY_MM)
			|| str.match(REGEX_JYY)
			|| str.match(REGEX_JYY_MM_DD)) {
		// jyy_mm_dd OR jyy_mm OR jyy

		s_j = str.substr(0, 1);
		s_yy = str.substr(1, 2);

        if (6 <= str.length) {
            // mmがあれば
           s_mm = str.substring(4, 6);
        }
        if (9 <= str.length) {
            // ddがあれば
            s_dd = str.substring(7);
        }

		if (f.wa_sno.length == undefined) {

		} else {

			for (i = f.wa_sno.length - 1; i >= 0; i--) {
				if (f.wa_jyy[i].value == s_j) {
					s_yyyy = "" + ((new Number(f.wa_from_yy[i].value)) + (new Number(s_yy)) -1);
					break;
				}
			}

			var idx = getWarekiInf(s_yyyy, s_mm, s_dd, f);
			if (idx == -1) {return "";}
			s_yy = s_yyyy - f.wa_from_yy[idx].value + 1;
			s_sno = f.wa_sno[idx].value;
			s_j = f.wa_jyy[idx].value;
			s_j_cc = f.wa_jyy_cc[idx].value;

		}

	} else if (str.match(REGEX_YYYYMMDD) || str.match(REGEX_YYYYMM)
			|| str.match(REGEX_YYYY)
			|| str.match(REGEX_YYYY_MM_DD)
			|| str.match(REGEX_YYYY_MM)

	) {
		// yyyymmdd OR yyyymm OR yyyy

		str = replaceAll(str, "/", "");

		s_yyyy = str.substr(0, 4);

		if (6 <= str.length) {
			s_mm = str.substr(4, 2);
		}
		if (8 <= str.length) {
			s_dd = str.substr(6, 2);
		}

		if (f.wa_sno.length == undefined) {

		} else {

			var idx = getWarekiInf(s_yyyy, s_mm, s_dd, f);
			if (idx == -1) {return "";}
			s_yy = s_yyyy - f.wa_from_yy[idx].value + 1;
			s_sno = f.wa_sno[idx].value;
			s_j = f.wa_jyy[idx].value;
			s_j_cc = f.wa_jyy_cc[idx].value;

		}

	} else if (str.match(REGEX_JCCYY)
			|| str.match(REGEX_JCCYY_MM)
			|| str.match(REGEX_JCCYY_MM_DD)) {
		// 平成21年10月12日 OR 平成21年10月 OR 平成21年

//		var str_len = str.length;
//
//		s_j_cc = str.substring(0, str_len - 9);
//		s_yy = str.substring(str_len - 9, str_len - 7);
//		s_mm = str.substring(str_len - 6, str_len - 4);
//		s_dd = str.substring(str_len - 3, str_len - 1);

		if (str.indexOf("元年") >= 0) {
	        s_j_cc = str.substring(0, str.indexOf("元年"));
	        s_yy = "01";
		} else {
	        s_j_cc = str.substring(0, str.indexOf("年")-2);
	        s_yy = str.substring(str.indexOf("年")-2, str.indexOf("年"));
        }

        if (str.indexOf("月") > -1) {
            s_mm = str.substring(str.indexOf("月")-2, str.indexOf("月"));
        }
        if (str.indexOf("日") > -1) {
            s_dd = str.substring(str.indexOf("日")-2, str.indexOf("日"));
        }

		if (f.wa_sno.length == undefined) {

		} else {

			for (i = f.wa_sno.length - 1; i >= 0; i--) {
				if (f.wa_jyy_cc[i].value == s_j_cc) {
					s_yyyy = "" + ((new Number(f.wa_from_yy[i].value)) + (new Number(s_yy)) - 1);
					break;
				}
			}

			var idx = getWarekiInf(s_yyyy, s_mm, s_dd, f);
			if (idx == -1) {return "";}
			s_yy = s_yyyy - f.wa_from_yy[idx].value + 1;
			s_sno = f.wa_sno[idx].value;
			s_j = f.wa_jyy[idx].value;
			s_j_cc = f.wa_jyy_cc[idx].value;
		}

	} else {
		//alert("invalid_date4");
		return "";
		// throw "invalid_date"; // (入力形式が間違っている)
	}

	// ----------------------------------------------------------------
	// *** 日付チェック
	// ----------------------------------------------------------------
	if (fmt == FMT_NYY
			|| fmt == FMT_NYYMM
			|| fmt == FMT_NYYMMDD
			|| fmt == FMT_JYY_MM_DD
			|| fmt == FMT_JYY_MM
			|| fmt == FMT_JYY
			|| fmt == FMT_JCCYY_MM_DD
			|| fmt == FMT_JCCYY_MM
			|| fmt == FMT_JCCYY) {
		// 和暦の場合

		if (s_yy > 99) {
			//alert("invalid_date2");
			return "";
			// throw "invalid_date";//表現できない年度
		}

	} else if (fmt == FMT_YYYYMMDD
			|| fmt == FMT_YYYY
			|| fmt == FMT_YYYYMM
			|| fmt == FMT_MM
			|| fmt == FMT_DD
			|| fmt == FMT_YYYY_MM_DD
			|| fmt == FMT_YYYY_MM) {
	// 西暦の場合

	} else {
		alert("SystemError:conv2Date()関数引数（フォーマット）の指定が間違っています。" + fmt);
		return "";
		// throw "invalid_format"; // format設定エラー
	}


	var isOk = false;
	if (!isEmpty(s_mm) && !isEmpty(s_dd)) {
		isOk = isDate(s_yyyy + s_mm + s_dd);

        // 閏年エラーの無視処理 -- 情報政策課の要望 Start
        if (!isOk) {
        	if ((s_yyyy + s_mm + s_dd).match(/[0-9]{4}0229/)) {
        		isOk = true;
        	}
        }
        // 閏年エラーの無視処理 -- 情報政策課の要望 Start

	} else if (!isEmpty(s_mm) && isEmpty(s_dd)) {
		isOk = isDate(s_yyyy + s_mm + "01");
	} else {
		isOk = isDate(s_yyyy + "01" + "01");
	}

	if (!isOk) {
		//alert("invalid_date5");
		return "";
		// throw "invalid_date"; // 日付ではないです。
	}

	// ----------------------------------------------------------------
	// *** 返却タイプによって返却値の決定
	// ----------------------------------------------------------------
	if (fmt == FMT_NYY) {
		// 421
		if (s_sno == "" || s_yy == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {
			result = s_sno + fillCharStr(s_yy, 2, "0", true);
		}
	} else if (fmt == FMT_NYYMM) {
		// 42110
		if (s_sno == "" || s_yy == "" || s_mm == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {
			result = s_sno + fillCharStr(s_yy, 2, "0", true) + s_mm;
		}
	} else if (fmt == FMT_NYYMMDD) {
		// 4211022
		if (s_sno == "" || s_yy == "" || s_mm == "" || s_dd == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {
			result = s_sno + fillCharStr(s_yy, 2, "0", true) + s_mm + s_dd;
		}
	} else if (fmt == FMT_YYYY) {
		// 2009
		if ("" + s_yyyy == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {
			result = s_yyyy;
		}
	} else if (fmt == FMT_YYYYMMDD) {
		// 20090101
		if ("" + s_yyyy == "" || s_mm == "" || s_dd == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {
			result = s_yyyy + s_mm + s_dd;
		}

	} else if (fmt == FMT_MM) {
		// 10
		if ("" + s_mm == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {
			result = s_mm;
		}
	} else if (fmt == FMT_DD) {
		// 22
		if ("" + s_dd == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {
			result = s_dd;
		}
	} else if (fmt == FMT_YYYYMM) {
		// 200910
		if ("" + s_yyyy == "" || s_mm == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {
			result = s_yyyy + s_mm;
		}
	} else if (fmt == FMT_YYYY_MM_DD) {
		// 2009/10/22
		if ("" + s_yyyy == "" || s_mm == "" || s_dd == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {
			result = s_yyyy + "/" + s_mm + "/" + s_dd;
		}
	} else if (fmt == FMT_YYYY_MM) {
		// 2009/10
		if ("" + s_yyyy == "" || s_mm == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {
			result = s_yyyy + "/" + s_mm;
		}
	} else if (fmt == FMT_JYY_MM_DD) {
		// H21.10.22
		if (s_j == "" || s_yy == "" || s_mm == "" || s_dd == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {
			result = s_j + fillCharStr(s_yy, 2, "0", true) + "." + s_mm + "."
					+ s_dd;
		}
	} else if (fmt == FMT_JYY_MM) {
		// H21.10
		if (s_j == "" || s_yy == "" || s_mm == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {
			result = s_j + fillCharStr(s_yy, 2, "0", true) + "." + s_mm;
		}
	} else if (fmt == FMT_JYY) {
		// H21
		if (s_j == "" || s_yy == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {
			result = s_j + fillCharStr(s_yy, 2, "0", true);
		}

	} else if (fmt == FMT_JCCYY_MM_DD) {
		// 平成21年10月22日
		if (s_j_cc == "" || s_yy == "" || s_mm == "" || s_dd == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {

			if (s_yy == 1) {
				result = s_j_cc + "元" + "年" + s_mm + "月" + s_dd + "日";
			} else {
			result = s_j_cc + fillCharStr(s_yy, 2, "0", true) + "年" + s_mm
					+ "月" + s_dd + "日";
			}
		}
	} else if (fmt == FMT_JCCYY_MM) {
		// 平成21年10月
		if (s_j_cc == "" || s_yy == "" || s_mm == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {

			if (s_yy == 1) {
				result = s_j_cc + "元" + "年" + s_mm + "月";
			} else {
				result = s_j_cc + fillCharStr(s_yy, 2, "0", true) + "年" + s_mm + "月";
			}
		}
	} else if (fmt == FMT_JCCYY) {
		// 平成21年
		if (s_j_cc == "" || s_yy == "") {
			return "";
			// throw "invalid_format"; // format設定エラー
		} else {

			if (s_yy == 1) {
				result = s_j_cc + "元" + "年";
			} else {
				result = s_j_cc + fillCharStr(s_yy, 2, "0", true) + "年";
			}
		}
	} else {
		alert("SystemError:conv2Date()関数引数（フォーマット）の指定が間違っています。" + fmt);
		return "";
		// throw "invalid_format"; // format設定エラー
	}

	// --------- 返却処理
	// if (errkey != "") {
	// alert(errkey);
	// return str;
	// } else {
	return result;
	// }

}

/*
 * 指定した西暦の和暦情報のindexを取得する。
 * @param 年
 * @param 月
 * @param 日
 * @param f 和暦Form
 * @return 和暦情報の配列index
 */
function getWarekiInf(s_yyyy, s_mm, s_dd, f) {

	var str = "" + s_yyyy + "" + s_mm + "" + s_dd;

	for (i = f.wa_sno.length - 1; i >= 0; i--) {

		if (!isEmpty(s_yyyy) && !isEmpty(s_mm) && !isEmpty(s_dd)) {
			if (f.wa_from_dt[i].value <= str
					&& f.wa_to_dt[i].value >= str) {

				return i;
			}
		} else if (!isEmpty(s_yyyy) && !isEmpty(s_mm)) {
			if (f.wa_from_yymm[i].value <= str
					&& f.wa_to_yymm[i].value >= str) {
				return i ;
			}
		} else if (!isEmpty(s_yyyy)) {
			if (f.wa_from_yy[i].value <= str
					&& f.wa_to_yy[i].value >= str) {
				return i ;
			}
		}
	}
	return -1;
}



/**
 * 全角を入力した場合、半角に変換する。
 *
 * @param obj ｶﾅ入力フィールド
 */
function convert2Han(obj) {

	if (obj == null || obj.value == '') {
	} else {
		var kana_input = obj.value;
		kana_input = chgString(1, kana_input); // ひらがな -> 半角カタナカ
		kana_input = chgString(3, kana_input); // 全角カタカナ -> 半角カタナカ
		kana_input = chgString(6, kana_input); // 半角カタナカ（小） -> 半角カタナカ（大）
		kana_input = chgString(5, kana_input); // 全角数字 -> 半角数字
		kana_input = chgString(9, kana_input); // 全角英字 -> 半角英字
		obj.value = kana_input;
	}
}

/**
 * 半角を入力した場合、全角に変換する。
 *
 * @param obj 全角入力フィールド
 */
function convert2Zen(obj) {

	if (obj == null || obj.value == '') {
	} else {
		var zen_input = obj.value;
		zen_input = chgString(4, zen_input);
		zen_input = chgString(6, zen_input);
		zen_input = chgString(7, zen_input);
		zen_input = chgString(8, zen_input);
		obj.value = zen_input;
	}
}



/**
 * 半角かな、全角カナを全角ひらがなへ変換する。
 *
 * @param obj ひらがな入力フィールド
 */
function convert2ZenHira(obj) {

	if (obj == null || obj.value == '') {
	} else {
		var zen_input = obj.value;
		zen_input = chgString(6, zen_input);
		zen_input = chgString(10, zen_input);
		zen_input = chgString(11, zen_input);
		obj.value = zen_input;
	}
}

/**
 * 半角かな、全角カナを全角ひらがなへ変換する。
 *
 * @param obj ひらがな入力フィールド
 */
function convert2ZenKana(obj) {

	if (obj == null || obj.value == '') {
	} else {
		var zen_input = obj.value;
		zen_input = chgString(6, zen_input);
		zen_input = chgString(2, zen_input);
		zen_input = chgString(4, zen_input);
		obj.value = zen_input;
	}
}


/**
 * 入力した文字列を日程パターンで変換する。
 *
 * @param mode
 *            変換モード (
 *            1:ひらがな -> 半角カタナカ、
 *            2:ひらがな -> 全角カタカナ、
 *            3:全角カタカナ -> 半角カタカナ、
 *            4:半角カタカナ -> 全角カタカナ、
 *            5:全角数字 -> 半角数字、
 *            6:半角小文字 -> 半角大文字,
 *            7:半角数字 -> 全角数字
 *            8:半角英字 -> 全角英字
 *            9:全角英字 -> 半角英字)
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
			"る", "れ", "ろ", "わ", "ん", "　");

	var h_kat = new Array("ｶﾞ", "ｷﾞ", "ｸﾞ", "ｹﾞ", "ｺﾞ", "ｻﾞ", "ｼﾞ", "ｽﾞ", "ｾﾞ",
			"ｿﾞ", "ﾀﾞ", "ﾁﾞ", "ﾂﾞ", "ﾃﾞ", "ﾄﾞ", "ﾊﾞ", "ﾋﾞ", "ﾌﾞ", "ﾍﾞ", "ﾎﾞ",
			"ﾊﾟ", "ﾋﾟ", "ﾌﾟ", "ﾍﾟ", "ﾎﾟ", "ｦ", "ｧ", "ｨ", "ｩ", "ｪ", "ｫ", "ｬ",
			"ｭ", "ｮ", "ｯ", "ｰ", "ｱ", "ｲ", "ｳ", "ｴ", "ｵ", "ｶ", "ｷ", "ｸ", "ｹ",
			"ｺ", "ｻ", "ｼ", "ｽ", "ｾ", "ｿ", "ﾀ", "ﾁ", "ﾂ", "ﾃ", "ﾄ", "ﾅ", "ﾆ",
			"ﾇ", "ﾈ", "ﾉ", "ﾊ", "ﾋ", "ﾌ", "ﾍ", "ﾎ", "ﾏ", "ﾐ", "ﾑ", "ﾒ", "ﾓ",
			"ﾔ", "ﾕ", "ﾖ", "ﾗ", "ﾘ", "ﾙ", "ﾚ", "ﾛ", "ﾜ", "ﾝ", " ");

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

	var f_hira_s = new Array("ぁ", "ぅ", "ぇ", "ぉ", "ゃ", "ゅ", "ょ", "ぃ", "っ");

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

	var i = 0;
	if (mode == 1) { // ひらがな -> 半角カタナカ
		for ( i = 0; i < hir.length; i++) {
			str = replaceAll(str, hir[i], h_kat[i]);
		}
		str = chgString(6, str);
	} else if (mode == 2) { // ひらがな-> 全角カタカナ
		for (  i = 0; i < hir.length; i++) {
			str = replaceAll(str, hir[i], f_kat[i]);
		}
	} else if (mode == 3) { // 全角カタカナ -> 半角カタナカ
		for ( i = 0; i < f_kat.length; i++) {
			str = replaceAll(str, f_kat[i], h_kat[i]);
		}
	} else if (mode == 4) { // 半角カタナカ -> 全角カタカナ
		for ( i = 0; i < h_kat.length; i++) {
			str = replaceAll(str, h_kat[i], f_kat[i]);
		}
	} else if (mode == 5) { // 全角数字 -> 半角数字
		for ( i = 0; i < f_num.length; i++) {
			str = replaceAll(str, f_num[i], h_num[i]);
		}
	} else if (mode == 6) { // 半角カタカナ（小） -> 半角カタカナ（大）
		for ( i = 0; i < h_kat_s.length; i++) {
			str = replaceAll(str, h_kat_s[i], h_kat_l[i]);
		}
	} else if (mode == 7) { // 半角数字 -> 全角数字
		for ( i = 0; i < f_num.length; i++) {
			str = replaceAll(str, h_num[i], f_num[i]);
		}
	} else if (mode == 8) { // 半角英字 -> 全角英字
		for ( i = 0; i < h_eng.length; i++) {
			str = replaceAll(str, h_eng[i], f_eng[i]);
		}
	} else if (mode == 9) { // 全角英字 -> 半角英字
		for ( i = 0; i < f_eng.length; i++) {
			str = replaceAll(str, f_eng[i], h_eng[i]);
		}
	} else if (mode == 10) { // 全角かな ->　全角ひらがな
		for ( i = 0; i < f_kat.length; i++) {
			str = replaceAll(str, f_kat[i], hir[i]);
		}
	} else if (mode == 11) { // 半角ｶﾅ ->　全角ひらがな
		for ( i = 0; i < f_kat.length; i++) {
			str = replaceAll(str, h_kat[i], hir[i]);
		}
	}

	return str;
}


/**
 * 入力した文字列に含まれた特定文字(列)を全て異なる文字(列)に置き換え、返還する。
 *
 * @param str 入力された文字列
 * @param orgStr 変更する文字(列)
 * @param repStr 置き換え文字(列)
 * @return {String}
 */
function replaceAll(str, orgStr, repStr) {
	return str.split(orgStr).join(repStr);
}

/**
 * 対象オブジェクトが配列、配列ではない場合を対応し値をセットする。<br>
 *
 * @param resObj 対象オブジェクト
 * @param resValue セットする値
 */
function setValue(resObj, resValue) {
	if (resObj != null) {
		if (resObj.length == undefined) {

			if (resObj.type == "text" ) {
			// TEXT
				resObj.value = resValue;
			} else if (resObj.type == "radio" ) {
			// RADIO
				if (resObj.value == resValue) {
					resObj.checked = true;
				} else {
					resObj.checked = false;
				}
			} else {
				resObj.value = resValue;
			}

		} else if (resObj.length >= 0) {
			for ( var i = 0; i < resObj.length; i++) {
				if (resObj[i].type == "text" ) {
				// TEXT
					resObj[i].value = resValue;
				} else if (resObj[i].type == "radio" ) {
				// RADIO
					if (resObj[i].value == resValue) {
						resObj[i].checked = true;
						break;
					} else {
						resObj[i].checked = false;
					}
				} else {
					resObj.value = resValue;
				}
			}
		}

	}
}


/**
 * 入力値の指定桁数ほど指定文字で埋め込む。
 * @param str 対象文字列
 * @param slength 長さ
 * @param chars キャラクタ1桁
 * @param isLeft  true 左に埋め込む場合 | false 右に埋め込む場合
 * @return 処理後の文字列
 */
function fillCharStr(str, slength, chars, isLeft) {

	var stemp = "";
	var temp = "";
	if (str == null || str == undefined) {
		temp = "";
	} else {
		temp = "" + str;
	}


	for ( var i = 0; i < slength - temp.length; i++) {
		stemp = chars + stemp;
	}

	if (isLeft) {
		str = stemp + temp;
	} else {
		str = temp + stemp;
	}

	return str;
}

/**
 * 指定した長さほど左にchをPaddingして返却する。
 * もし、inputObjectの場合はそのvalueに設定する。
 * @param obj 文字列またはinputObject
 * @param slength 長さ
 * @param ch 文字
 * @return 変換後文字列
 */
function lpad(obj, slength, ch) {

	if (obj == null) {
		return "";
	}
	if (obj.value != undefined) {
		if (isEmpty(obj.value)) {
			return "";
		}
		obj.value = fillCharStr(obj.value, slength, ch, true);
		return obj.value;
	} else {
		return fillCharStr(obj, slength, ch, true);
	}

}

/**
 * 指定した長さほど右にchをPaddingして返却する。
 * もし、inputObjectの場合はそのvalueに設定する。
 * @param obj 文字列またはinputObject
 * @param slength 長さ
 * @param ch 文字
 * @return 変換後文字列
 */
function rpad(obj, slength, ch) {

	if (obj == null) {
		return "";
	}
	if (obj.value != undefined) {
		if (isEmpty(obj.value)) {
			return "";
		}
		obj.value = fillCharStr(obj.value, slength, ch, false);
		return obj.value;
	} else {
		return fillCharStr(obj, slength, ch, false);
	}
}


/**
 * 小数点以下のPadingを行う。
 * @param obj 処理対象OBJ
 * @param ch 文字
 * @param scale 小数点以下の桁数
 */
function decimalPad(obj, slength, ch) {

	var str = obj.value;

	if (str == "-") {
		str = "0";
	}



	if (isDecimal(str)){

		var firStr = "";
		var secStr = "0";

		if (str.indexOf(".") > -1) {
			firStr = str.split(".")[0];
			secStr = str.split(".")[1];
		} else {
			firStr = str;
		}

//		if (!isEmpty(firStr)) {
//			firStr = fillCharStr(firStr, precision, '0', true);
//		}
		//if (!isEmpty(secStr)) {
			secStr = fillCharStr(secStr, slength, ch, false);
		//}
		obj.value = firStr + "." + secStr;
	}
}


/**
 * 指定したObjectの値を大文字に変換する。
 * @param obj 処理対象Object
 */
function toUpperCase(obj) {
	obj.value = obj.value.toUpperCase();
}

/**
 * 指定したObjectの値を小文字に変換する。
 * @param obj 処理対象Object
 */
function toLowerCase(obj) {
	obj.value = obj.value.toLowerCase();
}


/**
 * システムDate（本日）を西暦で取得します。
 * @return
 */
/*
function getToday() {
	var toDay = new Date();
	var yyyy = toDay.getFullYear();
	var mm = toDay.getMonth() + 1;
	var dd = toDay.getDate();
	return yyyy + lpad(mm, 2, "0") + lpad(dd, 2, "0");
}
*/


/**
 * システムDate（本日）を和暦で取得します。
 * @return
 */
/*
function getTodayWareki() {
	return conv2Wareki(getToday());
}
*/


/**
 * 誕生日（西暦、和暦）から年齢を計算し返却する。
 *
 * @param birthday 誕生日（西暦、和暦）
 * @return 年齢
 */
/*
function getAge(birthday) {

	var birthday_b1 = conv2Seireki(birthday);

	if (!isDate(birthday_b1)) {
		return "";
	}

	// ---- 誕生日1日前
	birthday_b1 = getPreviousDay(birthday_b1);

	var bYear = birthday_b1.substring(0, 4);
	var bMonth = birthday_b1.substring(4, 6);
	var bDay = birthday_b1.substring(6, 8);


	var today = new Date();
	var yyyy = today.getFullYear();
	var mm = lpad(today.getMonth() + 1, 2, "0");
	var dd = lpad(today.getDate(), 2, "0");

	var age = yyyy - (new Number(bYear));

	if (("" + bMonth + "" + bDay)  >  ("" + mm + "" + dd)) {
		age--;
	}

	return age;
}
*/

/**
 * 指定した日付の前日を取得する。
 * @param yyyymmdd 西暦YYYYMMDD
 * @return 指定した日付の前日（西暦）
 */
function getPreviousDay(yyyymmdd) {

	var today = new Date(new Number(yyyymmdd.substring(0, 4)),
							new Number(yyyymmdd.substring(4, 6)) - 1,
							new Number(yyyymmdd.substring(6, 8)));

	today.setTime(today.getTime() - (60 * 1000 * 60 * 24));

	var yyyy = today.getFullYear();
	var mm = lpad(today.getMonth() + 1, 2, "0");
	var dd = lpad(today.getDate(), 2, "0");

	return yyyy + mm + dd;
}

/**
 * 指定した日付の翌日を取得する。
 * @param yyyymmdd 西暦YYYYMMDD
 * @return 指定した日付の翌日（西暦）
 */
function getNextDay(yyyymmdd) {

	var today = new Date(new Number(yyyymmdd.substring(0, 4)),
							new Number(yyyymmdd.substring(4, 6)) - 1,
							new Number(yyyymmdd.substring(6, 8)));

	today.setTime(today.getTime() + (60 * 1000 * 60 * 24));

	var yyyy = today.getFullYear();
	var mm = lpad(today.getMonth() + 1, 2, "0");
	var dd = lpad(today.getDate(), 2, "0");

	return yyyy + mm + dd;
}


/**
 * 該当objの該当idxにあたる、値を返却する。
 * 複数ではない場合は、objの値を返却する。
 *
 * @param obj 対象オブジェクト
 * @param idx インデックス
 * @return objのidxにあたる値
 */
function getValue(obj, idx) {

	if (idx == null || idx == undefined || (!isNum(idx) || idx < 0)) {
		return "";
	}

	if (obj != null && obj != undefined) {
		if (obj.length == undefined) {
			return obj.value;
		} else {
			return obj[idx].value;
		}
	}
	return "";
}

/**
 * 該当objの該当idxにあたる、objectを返却する。
 * 複数ではない場合は、そのままを返却する。
 *
 * @param obj 対象オブジェクト
 * @param idx インデックス
 * @return objのidxにあたる値
 */
function getObject(obj, idx) {

	if ((!isNum(idx) || idx < 0) && obj != null) {
		return obj;
	}

	if (obj != null) {
		if (obj.length == undefined) {
			return obj;
		} else {
			return obj[idx];
		}
	}
	return null;
}

/**
 *
 * Radio、CheckBoxから選択された項目の値を取得する。
 * 指定したobjのタイプがRadioの場合、価の文字列を返すが
 * CheckBoxの場合は選択した行の値を配列として作成し返却する。
 *
 * @param obj ラジオボタンオブジェクト
 * @return 選択されているRadioボタンの値
 */
function getCheckedValue(obj) {
	var res = null;
	if (obj != null) {

		var type = "";
		if (obj.length != undefined && obj.length > 0) {
		// 複数存在する場合
			if (obj[0].type == "radio") {
				type = "radio";
			} else if (obj[0].type == "checkbox") {
				type = "checkbox";
			}
		} else {
			if (obj.type == "radio") {
				type = "radio";
			} else if (obj.type == "checkbox") {
				type = "checkbox";
			}
		}

		if (type == "radio") {

			if (obj.length == undefined) {
				if (obj.checked) {
					res = obj.value;
				}
			} else {
				for (var i = 0; i < obj.length; i++) {
					if (obj[i].checked) {
						res = obj[i].value;
						break;
					}
				}
			}
			return res;
		} else if (type == "checkbox") {
			res = new Array();

			var arrayIdx = 0;
			if (obj.length == undefined) {
				if (obj.checked) {
					res[arrayIdx++] = obj.value;
				}
			} else {
				for (var i = 0; i < obj.length; i++) {
					if (obj[i].checked) {
						res[arrayIdx++] = obj[i].value;
					}
				}
			}

			return res;
		}
	}
}

/**
 *
 * Radio、CheckBoxから選択された項目のindexを取得する。
 * 指定したobjのタイプがRadioの場合、indexのみを返すが
 * CheckBoxの場合は選択した行の値を配列として作成し返却する。
 *
 * @param obj Radio又はCheckBoxオブジェクト
 * @return 選択されているRadioボタンの値
 */
function getCheckedIndex(obj) {

	if (obj != null) {

		var type = "";
		if (obj.length != undefined) {
		// 複数存在する場合
			if (obj[0].type == "radio") {
				type = "radio";
			} else if (obj[0].type == "checkbox") {
				type = "checkbox";
			}
		} else {
			if (obj.type == "radio") {
				type = "radio";
			} else if (obj.type == "checkbox") {
				type = "checkbox";
			}
		}

		var res = null;
		if (type == "radio") {
			if (obj.length == undefined) {
				if (obj.checked) {
					res = obj.value;
				}
			} else {
				for (var i = 0; i < obj.length; i++) {
					if (obj[i].checked) {
						res = obj[i].value;
						return getObjIndex(obj[i]);
					}
				}
			}
			return res;
		} else if (type == "checkbox") {

			var res = new Array();
			var arrayIdx = 0;

			if (obj.length == undefined) {
				if (obj.checked) {
					res[arrayIdx] = obj.value;
				}
			}

			for (var i = 0; i < obj.length; i++) {

				if (obj[i].checked) {
					res[arrayIdx++] = getObjIndex(obj[i]);
				}
			}

			return res;
		}
	}
	return null;
}



/**
 * Radio、CheckBoxのチェック設定
 * Radio、CheckBoxであれば、指定したインデックスのObjectを
 * チェック又はチェックを外す。
 * @param obj Radio又はCheckBoxオブジェクト
 * @param idx インデックス
 * @param flg true : チェック  |  false : チェックを外す
 */
function setCheckIndex(obj, idx, flg) {

	if (obj != null) {
		var _flg = flg;
		if (_flg == undefined) {
			_flg = true;
		}


		if (obj.length != undefined) {
		// 複数存在する場合
			for (var i = 0; i < obj.length; i++) {
				if (i == idx) {
					obj[i].checked = _flg;
				}
			}
		} else {
			if (idx == 0) {
				obj.checked = _flg;
			}
		}
	}
}

/**
 * Radio、CheckBoxのチェック設定
 * Radio、CheckBoxであれば、指定したValueに該当するObjectを
 * チェック又はチェックを外す。
 * @param obj Radio又はCheckBoxオブジェクト
 * @param value 値
 * @param flg true : チェック  |  false : チェックを外す
 */
function setCheckValue(obj, value, flg) {

	if (obj != null) {

		var _flg = flg;
		if (_flg == undefined) {
			_flg = true;
		}

		if (obj.length != undefined) {
		// 複数存在する場合
			for (var i = 0; i < obj.length; i++) {
				if (obj[i].value == value) {
					obj[i].checked = _flg;
				}
			}
		} else {
			if (obj.value == value) {
				obj.checked = _flg;
			}
		}
	}
}

/**
 * CheckBoxの一括チェック設定
 * 指定したCheckBoxの全てをチェック、又は全てのチェックを外す。
 * @param obj CheckBoxオブジェクト
 * @param flg true：チェック　|　false：チェックを外す
 */
function setCheckAll(obj, flg) {

	if (obj != null) {

		// パラメータ省略時の初期値設定
		var _flg = (flg != undefined) ? flg : true;

		var len  = (obj.length != undefined) ? obj.length : 1;
		for (var i = 0; i < len; i++) {
			setCheckIndex(obj, i, _flg);
		}
	}
}

/**
 * 指定したオブジェクトのindexを返却する。
 *
 * @param obj Radio又はCheckBoxオブジェクト
 * @return 選択されているRadio又はCheckBoxのindex
 */
function getObjIndex(obj) {

	if (obj.form == null) {
		var parent = document.getElementsByName(obj.styleId) ;
		var idx = -1;
		if (parent != null && parent.length != undefined && parent.type == undefined) {
			for (var i = 0 ; i < parent.length; i++) {
				if (parent[i] == obj) {
					idx = i;
					break;
				}
			}
			return idx;
		} else {
			return -1;
		}

	} else {
		var frmName = obj.form.name;

		var parent = null ;
		var idx = -1;
		eval("parent = document." + frmName + "." + obj.name +";");
		if (parent != null && parent.length != undefined && parent.type == undefined) {
			for (var i = 0 ; i < parent.length; i++) {
				if (parent[i] == obj) {
					idx = i;
					break;
				}
			}
			return idx;
		} else {
			return -1;
		}
	}
}

/**
 * ドロップダウンから選択されている項目のテキストを返却する。
 *
 * @param selObj ドロップダウンのオブジェクト
 * @return ドロップダウンのテキスト
 */
function getSelectedText(selObj) {
	if (selObj != null) {
		for ( var i = 0; i < selObj.length; i++) {
			if (selObj[i].selected) {
				return selObj[i].text;
			}
		}
	}
	return "";
}


/**
 * 指定オブジェクトのクラス名を全て取得する
 *
 * @param obj 対象オブジェクト
 */
function getClassName(obj) {
	return obj.className.split(" ");
}

/**
 * Classを設定する。
 *
 * @param oElm 対象オブジェクト
 * @param strClassName クラス名
 */
function addClassName(oElm, strClassName) {
	var strCurrentClass = oElm.className;
	if (!new RegExp(strClassName, "i").test(strCurrentClass)) {
		oElm.className = strCurrentClass
				+ ((strCurrentClass.length > 0) ? " " : "") + strClassName;
	}
}

/**
 * 対象オブジェクトから指定したクラス名を削除する。
 *
 * @param oElm 対象オブジェクト
 * @param strClassName クラス名
 */
function removeClassName(oElm, strClassName) {
	var oClassToRemove = new RegExp((strClassName + "\s?"), "i");
	oElm.className = oElm.className.replace(oClassToRemove, "").replace(
			/^\s?|\s?$/g, "");
}


/** 指定したオブジェクトのスタイルを反映または取消する。
 * @param obj 設定対象項目
 * @param style スタイル
 * @param flg 反映する場合true | 取消の場合false
 */
function setTextStyle(obj, style, flg) {
	if (obj != null) {
		if (obj.length == undefined) {
			if (flg) {
				addClassName(obj, style);
			} else {
				removeClassName(obj, style);
			}
		} else {
			for ( var i = 0; i < obj.length; i++) {
				if (flg) {
					addClassName(obj[i], style);
				} else {
					removeClassName(obj[i], style);
				}
			}
		}
	}
}

/**
 * 指定した文字列がNULLやundifinedの場合はブランクを返す。
 * そうではなければそのまま返す。
 * @param str 設定対象項目
 */
function clearNull(str) {
	if (isEmpty(str)) {
		return "";
	}
	return str;
}



/**
 * 同一のnameで「inputオブジェクト」が複数存在する場合(一覧の場合)
 * 指定した「inputオブジェクト」の該当indexを取得する。
 *
 * 注意）同一のnameで「inputオブジェクト」が1つ存在する場合は「undefined」を返却する。
 * @param obj inputオブジェクト
 */
function getIndex(obj) {

	if (obj == null || obj == undefined) {
		return null;
	}


	var frmName= obj.form.name;

	var parent = null ;
	eval("parent = document." + frmName + "." + obj.name +";");
	if (parent != null && parent.length != undefined && parent.type == undefined) {
		var idx = 0;
		for (var i = 0 ; i < parent.length; i++) {
			if (parent[i] == obj) {
				idx = i;
				break;
			}
		}
		return idx;
	} else {
		return undefined;
	}
}

/**
 * 郵便番号フォーマット変換<br/>
 * 7桁の郵便番号を"999-9999"フォーマットに変えて返却する。<br/>
 * @param zipCd 郵便番号
 * @return 999-9999フォーマット郵便番号
 */
function formatZip(zipCd) {

	if (isEmpty(zipCd)) {
		return "";
	}

	if ("-" == zipCd.trim()) {
		return "";
	}

	if (zipCd.trim().length != 7) {
		return zipCd.trim();
	}

	if (zipCd.match("/^[\s]*[0-9]*[\-][0-9]*$/")) {
		return zipCd;
	}

	var sb = "";

	if (zipCd.length <= 3) {
		sb = sb + zipCd;
		return sb;
	} else {
		sb = sb + zipCd.substring(0, 3);
		sb = sb + "-";
		sb = sb +  zipCd.substring(3);
		return sb;
	}
}

