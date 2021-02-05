/*=======================================================================
 * liveany-popup-apps.js
 *
 * 共通モジュール関連JavaScript
 * =======================================================================*/


//--------------------------------------------------------------
// * カレンダー画面の呼出関数 START
//--------------------------------------------------------------
/**
 *
 * 共通カレンダーポップアップ画面を開く。
 *
 * isSeirekiがtrueの場合は西暦(例：20090523)Formatに返却。
 * isSeirekiがfalseの場合は和暦(例：4210523)Formatに返却。
 *
 * ※基幹システムのカレンダー画面を呼び出す。
 *
 * @param dtObj 日付項目Object
 * @param isSeireki 日付タイプ{Boolean}   (true: 西暦 |  false:和暦 )
 */
function openCalen(dtObj, isSeireki, index) {

	var in_date = "";
	var objName = "";
	var frmName = "";

	if (dtObj.length == undefined
			|| (dtObj.length == undefined  && index == 0)) {
		in_date = dtObj.value;
		objName = dtObj.name;
		frmName = dtObj.form.name;
	} else {
	// 指定したOBJが配列の場合
		in_date = dtObj[index].value;
		objName = dtObj[index].name + "[" + index + "]";
		frmName = dtObj[index].form.name;
	}

	var ty_dt = "";
	try {
		if (!isSeireki) {
			ty_dt = "JA";
			in_date = conv2Date(in_date, 'nyymmdd');
		} else {
			ty_dt = "AD";
			in_date = conv2Date(in_date, 'yyyymmdd');
		}
	} catch (e) {
	}

	// --- ポップアップ画面呼び出すパラメータ設定
	var inParam = new Array();
	inParam["co_clndr1"] = objName;
	inParam["co_clndr2"] = '';
	inParam["co_ad_flag"] = ty_dt;
	inParam["co_f_nm"] = frmName;
	inParam["in_date"] = in_date;
	if (!isEmpty(in_date) && in_date.length == 8) {
		inParam["sel_year"] = in_date.substr(0, 4);
		inParam["sel_month"] = in_date.substr(4, 2);
		inParam["sel_day"] = in_date.substr(6, 4);

	}

	// --- 3.子画面を呼び出す
	openPopup(250, 236, 'initCal', '/Tech3g_Doc_project/common/cal.do',inParam, null, "no", false);
}
//--------------------------------------------------------------
// * カレンダー画面の呼出関数 END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 姓名検索画面の呼出関数 START
//--------------------------------------------------------------
/**
 * 姓名検索画面を開く。
 *
 * ※ 必要ないない項目は設定しなくてもいい。
 *
 * 下記の仕様でJavascriptのArrayを作成し引数に設定する。
 *
 * ●inParamの仕様
 * inParam["cc_nm"]             : 漢字名
 * inParam["kana_nm"]           : ｶﾅ名
 * inParam["cc_nm1"]            : 姓(漢字)
 * inParam["cc_nm2"]            : 名(漢字)
 * inParam["kana_nm1"]          : 姓(ｶﾅ)
 * inParam["kana_nm2"]          : 名(ｶﾅ)
 * inParam["ex_kana_nm"]        : その他　姓名(ｶﾅ)
 * inParam["ex_cc_nm"]          : その他　姓名(漢字)
 *
 * ●outParamの仕様
 * outParam["cc_nm"]             : 漢字名のObject
 * outParam["kana_nm"]           : ｶﾅ名のObject
 * outParam["cc_nm1"]            : 姓(漢字)のObject
 * outParam["cc_nm2"]            : 名(漢字)のObject
 * outParam["kana_nm1"]          : 姓(ｶﾅ)のObject
 * outParam["kana_nm2"]          : 姓(ｶﾅ)のObject
 * outParam["ex_kana_nm"]        : その他(ｶﾅ)のObject
 * outParam["ex_cc_nm"]          : その他(漢字)のObject
 * outParam["on_close_js_func"]  : ポップアップをCloseするとき、呼び出される親画面の関数名
 *
 * @param inParam 画面を開く時のパラメータが設定されている配列
 * @param outParam 画面を閉じ返却値をもらうパラメータが設定されている配列
 */
function openPnameSrch(inParam, outParam) {
	openPopup(820, 320, 'comPname', '/common/PName.do?method=getPNameListAcInfo',inParam, outParam, "no", false);
}

/**
 * 姓名検索画面を開く。
 * 姓名検索画面を開き、漢字氏名を取得します。
 *
 * 使用例)
 * <code>
 * 	openPnameSrch1(form.cc_nm);
 * 	openPnameSrch1(form.cc_nm, 1);
 * 	openPnameSrch1(form.cc_nm, 1, "onClosePname");
 * </code>
 *
 * @param ccNmObj 漢字氏名のObject
 * @param idx インデックス　項目が複数の場合のみindexを指定する。
 * @param onCloseJsFunc ポップアップをCloseするとき、呼び出される親画面の関数名
 */
function openPnameSrch1(ccNmObj, idx, onCloseJsFunc) {
	// --- ポップアップ画面呼び出すパラメータ設定
	var inParam = new Array();
	inParam["cc_nm"] = getValue(ccNmObj, idx);

	// --- 返却値をセットするObjectを設定
	var outParam = new Array();
	outParam["cc_nm"] = getObject(ccNmObj, idx);
	outParam["on_close_js_func"] = onCloseJsFunc;

	// --- 3.子画面を呼び出す
	openPnameSrch(inParam, outParam);
}

/**
 * 姓名検索画面を開く。
 * 姓名検索画面を開き、漢字氏名、ｶﾅ氏名を取得します。
 *
 * ※ 必要ないない項目は設定しなくてもいい。
 *
 * 使用例)
 * <code>
 * 	openPnameSrch2(form.cc_nm, form.kana_nm);
 * 	openPnameSrch2(form.cc_nm, form.kana_nm, "onClosePname");
 * </code>
 *
 * @param ccNmObj 漢字氏名のObject
 * @param kanaNmObj ｶﾅ氏名のObject
 * @param idx インデックス　項目が複数の場合のみindexを指定する。
 * @param onCloseJsFunc ポップアップをCloseするとき、呼び出される親画面の関数名
 */
function openPnameSrch2(ccNmObj,  kanaNmObj, idx, onCloseJsFunc) {
	// --- ポップアップ画面呼び出すパラメータ設定
	var inParam = new Array();
	inParam["kana_nm"] = getValue(kanaNmObj, idx);
	inParam["cc_nm"] = getValue(ccNmObj, idx);

	// --- 返却値をセットするObjectを設定
	var outParam = new Array();
	outParam["cc_nm"] = getObject(ccNmObj, idx);
	outParam["kana_nm"] = getObject(kanaNmObj, idx);
	outParam["on_close_js_func"] = onCloseJsFunc;

	// --- 子画面を呼び出す
	openPnameSrch(inParam, outParam);
}

/**
 * 姓名検索画面を開く。
 * 姓名検索画面を開き、漢字氏名、ｶﾅ氏名、姓(漢字)、名(漢字)、姓(ｶﾅ)、姓(ｶﾅ)、その他(ｶﾅ)、その他(漢字)を取得します。
 *
 * ※必要ないない項目は設定しなくてもいい。
 *
 * @param ccNmObj 漢字氏名のObject
 * @param kanaNmObj ｶﾅ氏名のObject
 * @param ccNm1Obj 姓(漢字)のObject
 * @param ccNm1Obj 名(漢字)のObject
 * @param kanaNm1Obj 姓(ｶﾅ)のObject
 * @param kanaNm2Obj 姓(ｶﾅ)のObject
 * @param exCcNmObj その他(ｶﾅ)のObject
 * @param exKanaNmObj その他(漢字)のObject
 * @param idx インデックス　項目が複数の場合のみindexを指定する。
 * @param onCloseJsFunc ポップアップをCloseするとき、呼び出される親画面の関数名
 */
function openPnameSrch3(ccNmObj, kanaNmObj, ccNm1Obj, ccNm2Obj, kanaNm1Obj,
		kanaNm2Obj, exCcNmObj, exKanaNmObj, idx, onCloseJsFunc) {
	// --- ポップアップ画面呼び出すパラメータ設定
	var inParam = new Array();
	inParam["cc_nm"] = getValue(ccNmObj, idx);
	inParam["kana_nm"] = getValue(kanaNmObj, idx);
	inParam["cc_nm1"] = getValue(ccNm1Obj, idx);
	inParam["cc_nm2"] = getValue(ccNm2Obj, idx);
	inParam["kana_nm1"] = getValue(kanaNm1Obj, idx);
	inParam["kana_nm2"] = getValue(kanaNm2Obj, idx);
	inParam["ex_cc_nm"] = getValue(exCcNmObj, idx);
	inParam["ex_kana_nm"] = getValue(exKanaNmObj, idx);

	// --- 返却値をセットするObjectを設定
	var outParam = new Array();
	outParam["cc_nm"] = getObject(ccNmObj, idx);
	outParam["kana_nm"] = getObject(kanaNmObj, idx);
	outParam["cc_nm1"] = getObject(ccNm1Obj, idx);
	outParam["cc_nm2"] = getObject(ccNm2Obj, idx);
	outParam["kana_nm1"] = getObject(kanaNm1Obj, idx);
	outParam["kana_nm2"] = getObject(kanaNm2Obj, idx);
	outParam["ex_cc_nm"] = getObject(exCcNmObj, idx);
	outParam["ex_kana_nm"] = getObject(exKanaNmObj, idx);
	outParam["on_close_js_func"] = onCloseJsFunc;

	// --- 子画面を呼び出す
	openPnameSrch(inParam, outParam);
}

//--------------------------------------------------------------
// * 姓名検索画面の呼出関数 END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 宛名検索画面の呼出関数 START
//--------------------------------------------------------------
/**
 * 宛名検索画面を開く。
 * 宛名検索画面を開きます。
 *
 * ●inParamの仕様
 * 	    個人番号   ：inParam["icno"]
 *	    カナ名     ：inParam["kana_nm"]
 *	    住民区分   ：inParam["rr_cl_cd"]
 *	    生年月日   ：inParam["bdt"]
 *	    漢字名     ：inParam["cc_nm"]
 *	    性別区分   ：inParam["sex_cl"]
 *	    住所コード ：inParam["ad_cd"]
 *	    本番       ：inParam["ano"]
 *	    枝番       ：inParam["sano"]
 *	    小枝番     ：inParam["slno"]
 *	    小小枝番   ：inParam["sslno"]
 *
 * ●outParamの仕様
 *
 *
 *
 * ●使用例
 *
 *
 *
 * @param inParam ポップアップ画面を開くときのパラメータ配列
 * @param outParam ポップアップ画面から渡されるパラメータ配列
 */
function openAtenaSrch(inParam, outParam) {
	// --- 宛名検索画面を呼び出す
	openPopup(810, 635, 'HOBCAdBasisSrch', '/ho/bc/AdBasisSrch/initAdBasisSrch.do',inParam, outParam, "no");
}
//--------------------------------------------------------------
// * 宛名検索画面の呼出関数 END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 送付先詳細情報取得画面の呼出関数 START
//--------------------------------------------------------------
/**
 * 送付先詳細情報取得画面を開く。
 *
 * ※ 必要ない項目は設定しなくてもいい。
 *
 * 下記の仕様でJavascriptのArrayを作成し引数に設定する。
 *
 * ●inParamの仕様
 * inParam["ino"]              : 個人番号
 * inParam["gyomu_cd"]         : 業務コード
 * inParam["jigyo_cd"]         : 事業コード
 *
 * ●outParamの仕様
 * outParam["ad"]                : 住所のObject
 * outParam["aad"]               : 方書のObject
 * outParam["zip"]               : 郵便番号のObject
 * outParam["sofusaki_cc_nm"]    : 送付先漢字名のObject
 * outParam["sofusaki_kana_nm"]  : 送付先カナ名のObject
 * outParam["shiyo_start_dt"]    : 使用開始日のObject
 * outParam["shiyo_end_dt"]      : 使用終了日のObject
 * outParam["joho_kyoyu_kbn"]    : 情報共有区分のObject
 * outParam["joho_kyoyu_kbn_nm"] : 情報共有区分(名称)のObject
 * outParam["rmrk"]              : 備考のObject
 * outParam["on_close_func"]     :取得後に実行するJavaScript関数名
 *
 * @param inParam 画面を開く時のパラメータが設定されている配列
 * @param outParam 画面を閉じ返却値をもらうパラメータが設定されている配列
 */
function openSendDestGn(inParam, outParam) {

	// ------ 個人番号必須入力チェック
	if (!chkRequired(inParam["ino"], "個人番号")) {
		return;
	}

	// ------ 業務コード必須入力チェック
	if (!chkRequired(inParam["gyomu_cd"], "業務コード")) {
		return;
	}

	// ------ 事業コード必須入力チェック
	if (!chkRequired(inParam["jigyo_cd"], "事業コード")) {
		return;
	}

	openPopup(830, 370, 'HOBCSendDestGn', '/ho/bc/SendDestGn/initSendDestGn.do',inParam, outParam, "no");
}
//--------------------------------------------------------------
// * 送付先詳細情報取得画面の呼出関数 END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 緊急連絡先情報照会画面の呼出関数 START
//--------------------------------------------------------------
/**
 * 緊急連絡先情報照会画面を開く。
 *
 * ※ 必要ない項目は設定しなくてもいい。
 *
 * 下記の仕様でJavascriptのArrayを作成し引数に設定する。
 *
 * ●inParamの仕様
 * inParam["kojin_no"]         : 個人番号
 * inParam["gyomu_cd"]         : 業務コード
 * inParam["jigyo_cd"]         : 事業コード
 *
 * @param inParam 画面を開く時のパラメータが設定されている配列
 * @param outParam 画面を閉じ返却値をもらうパラメータが設定されている配列
 */
function openCntDestGn(inParam) {

	// ------ 個人番号必須入力チェック
	if (!chkRequired(inParam["ino"], "個人番号")) {
		return;
	}

	// ------ 業務コード必須入力チェック
	if (!chkRequired(inParam["gyomu_cd"], "業務コード")) {
		return;
	}

	// ------ 事業コード必須入力チェック
	if (!chkRequired(inParam["jigyo_cd"], "事業コード")) {
		return;
	}

	openPopup(830, 672, 'HOBCCntDestGn', '/ho/bc/CntDestGn/initCntDestGn.do',inParam, null, "no");
}

//--------------------------------------------------------------
// * 緊急連絡先情報照会画面の呼出関数 END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 医療保険者詳細情報取得画面の呼出関数 START
//--------------------------------------------------------------
/**
 * 医療保険者詳細情報取得画面を開く。
 *
 * ※ 必要ない項目は設定しなくてもいい。
 *
 * 下記の仕様でJavascriptのArrayを作成し引数に設定する。
 *
 * ●inParamの仕様
 * inParam["hokensha_cd"]				:保険者コード
 * inParam["hokensha_cc_nm"]             :保険者漢字名
 * inParam["hokensha_kana_nm"]           :保険者カナ名
 * inParam["hoken_shu"]                  :保険種別
 *
 * ●outParamの仕様
 * outParam["hokensha_cd"]               :保険者コード
 * outParam["hokensha_cc_nm"]            :保険者漢字名
 * outParam["hokensha_kana_nm"]          :保険者カナ名
 * outParam["shibu_nm"]                  :支部名
 * outParam["hoken_shu"]                 :保険種別
 * outParam["hoken_shu_nm"]              :保険種別(名称)
 * outParam["daihyo_sha_cc_nm"]          :代表者漢字氏名
 * outParam["daihyo_sha_kana_nm"]        :代表者カナ氏名
 * outParam["ad"]                        :所在地
 * outParam["zip"]                       :郵便番号
 * outParam["tno"]                       :電話番号
 * outParam["fax_no"]                    :FAX番号
 * outParam["start_dt"]                  :開始日
 * outParam["haishi_dt"]                 :廃止日
 * outParam["tokutei_kenko_kenshin"]     :特定健康健診
 * outParam["tokutei_kenko_kenshin_nm"]  :特定健康健診(名称)
 * outParam["tokutei_hoken_shido"]       :特定保健指導
 * outParam["tokutei_hoken_shido_nm"]    :特定保健指導(名称)
 * outParam["fuka_kyufu_cnt"]            :付加給付件数
 * outParam["on_close_func"]             :取得後に実行するJavaScript関数名
 *
 * @param inParam 画面を開く時のパラメータが設定されている配列
 * @param outParam 画面を閉じ返却値をもらうパラメータが設定されている配列
 */
function openMediIsPnGn(inParam, outParam) {
	openPopup(830, 620, 'HOBCMediIsPnGn', '/ho/bc/MediIsPnGn/initMediIsPnGn.do', inParam, outParam, "no");
}
//--------------------------------------------------------------
// * 医療保険者詳細情報取得画面の呼出関数END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 相談管理詳細情報取得画面の呼出関数 START
//--------------------------------------------------------------
/**
 * 相談管理詳細情報取得画面を開く。
 *
 * ※ 必要ない項目は設定しなくてもいい。
 *
 * 下記の仕様でJavascriptのArrayを作成し引数に設定する。
 *
 * ●inParamの仕様
 * inParam["gyomu_cd"]                :業務コード
 * inParam["taisho_sha_kojin_no"]     :個人番号
 * inParam["taisho_sha_sngp"]         :生年月日
 * inParam["taisho_sha_kanji_shime"]  :漢字氏名
 * inParam["taisho_sha_kana_shime"]   :カナ氏名
 * inParam["sodan_hi"]                :相談日
 * inParam["screen_type"]             :画面タイプ
 *
 * ●outParamの仕様
 * outParam["taisho_sha_kojin_no"]      :個人番号
 * outParam["taisho_sha_sngp"]          :生年月日
 * outParam["taisho_sha_kanji_shime"]   :漢字氏名
 * outParam["taisho_sha_kana_shime"]    :カナ氏名
 * outParam["taisho_sha_jusho"]         :住所
 * outParam["taisho_sha_telno"]         :電話番号
 * outParam["shimin_kb"]                :市民区分
 * outParam["shimin_kb_nm"]             :市民区分(名称)
 * outParam["service_shinse_kahi"]      :サービス申請
 * outParam["service_shinse_kahi_nm"]   :サービス申請(名称)
 * outParam["sodan_hi"]                 :相談日
 * outParam["tanto_sha"]                :担当者
 * outParam["tanto_sha_nm"]             :担当者(名称)
 * outParam["sodan_kb"]                 :相談区分
 * outParam["sodan_kb_nm"]              :相談区分(名称)
 * outParam["sodan_title"]              :相談タイトル
 * outParam["sodan_naiyo"]              :相談内容
 * outParam["shien_kekaku"]             :支援計画
 * outParam["sodan_sha"]                :相談者
 * outParam["sodan_hoho"]               :相談方法
 * outParam["sodan_hoho_nm"]            :相談方法(名称)
 * outParam["sodan_jokyo"]              :相談状況
 * outParam["kinkyu_kb"]                :緊急区分
 * outParam["kinkyu_kb_nm"]             :緊急区分(名称)
 * outParam["sodan_keiro"]              :相談経路
 * outParam["gyomu_cd"]                 :業務コード
 * outParam["gyomu_cd_nm"]              :業務コード(名称)
 * outParam["jigyo_cd"]                 :事業コード
 * outParam["jigyo_cd_nm"]              :事業コード(名称)
 * outParam["sinno"]                    :申請番号
 * outParam["tsunagi_saki"]             :つなぎ先
 * outParam["kezoku_tanto_sha"]         :継続担当者
 * outParam["kezoku_tanto_sha_nm"]      :継続担当者(名称)
 * outParam["kezoku_hi"]                :継続日
 * outParam["kezoku_hoho"]              :継続方法
 * outParam["kezoku_hoho_nm"]           :継続方法(名称)
 * outParam["kezoku_naiyo"]             :継続内容
 * outParam["jikai_yoyaku_kahi"]        :次回予約
 * outParam["jikai_yoyaku_kahi_nm"]     :次回予約(名称)
 * outParam["jikai_yoyaku_hi"]          :次回予約日
 * outParam["jikai_tanto_sha"]          :次回担当者
 * outParam["jikai_tanto_sha_nm"]       :次回担当者(名称)
 * outParam["monitaringu_kahi"]         :モニタリング
 * outParam["monitaringu_kahi_nm"]      :モニタリング(名称)
 * outParam["monitaringu_hi"]           :モニタリング日
 * outParam["monitaringu_kakunin_saki"] :モニタリング確認先
 * outParam["on_close_func"]            :取得後に実行するJavaScript関数名
 *
 * @param inParam 画面を開く時のパラメータが設定されている配列
 * @param outParam 画面を閉じ返却値をもらうパラメータが設定されている配列
 */
function openCnstGn(inParam, outParam) {

	// ------ 業務コード必須入力チェック
	if (!chkRequired(inParam["gyomu_cd"], "業務コード")) {
		return;
	}

	// ------ 事業コード必須入力チェック
	if (!chkRequired(inParam["jigyo_cd"], "事業コード")) {
		return;
	}

	// ------ 画面タイプ追加
	inParam["screen_type"] = "1";

	openPopup(830, 630, 'HOBCCnstGn', '/ho/bc/CnstSrch/initCnstSrch.do',inParam, outParam, "no");
}
//--------------------------------------------------------------
// * 相談管理詳細情報取得画面の呼出関数END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 住民票照会画面の呼出関数 START
//--------------------------------------------------------------
/**
 *
 * 住民票照会画面を開く。
 *
 * ※基幹システムの住民票照会画面を呼び出す。
 *
 * @param ino 個人番号
 */
function openRsdtTag(ino) {

	// ------ 個人番号必須入力チェック
	if (!chkRequired(ino, "個人番号")) {
		return;
	}

	// ------ ポップアップ画面へのパラメータ設定
	var inParam = new Array();
	inParam["check_ino"] = ino;
	inParam["kbn"]       = "3";

	// --- 3.子画面を呼び出す
	openPopup(1000, 580, 'srchHisPopup', '/re/cw/recwCnIss.do?method=goHisIq', inParam, null, "no", false);
}
//--------------------------------------------------------------
// * 住民票照会画面の呼出関数END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 課税情報照会画面の呼出関数（個人） START
//--------------------------------------------------------------
/**
 *
 * 課税情報照会画面を開く。
 *
 * ※基幹システムの課税情報照会画面を呼び出す。
 *
 * @param tt_yy 課税年度
 * @param ino   個人番号
 */
function openTaxInfo(tt_yy, ino) {

	// ------ 課税年度必須入力チェック
	if (!chkRequired(tt_yy, "課税年度")) {
		return;
	}

	// ------ 個人番号必須入力チェック
	if (!chkRequired(ino, "個人番号")) {
		return;
	}

	// ------ 課税年度の日付チェック
	var str = conv2Date(tt_yy, FMT_JYY);
	if (isEmpty(str)) {
		alert("課税年度が正しくないです。");
		return;
	}

	// ------ ポップアップ画面へのパラメータ設定
	var inParam = new Array();
	inParam["tt_yy"]       = str;
	inParam["ino"]         = ino;
	inParam["screen_type"] = "1";

	// --- 3.子画面を呼び出す
	openPopup(850, 820, 'IXLMTaxInq', '/ix/lm/getTaxInfo.do?method=getTaxInfo', inParam, null, "yes", false);
}
//--------------------------------------------------------------
// * 課税情報照会画面の呼出関数（個人）END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 課税情報照会画面の呼出関数（世帯） START
//--------------------------------------------------------------
/**
 *
 * 課税情報照会(世帯)画面を開く。(返却値なし）
 *
 * @param tt_yy 課税年度
 * @param ino   個人番号
 */
function openSetaiTaxInfo(inParam) {

	// ------ ポップアップ画面へのパラメータ設定
	inParam["screen_type"] = "0";

	// --- 3.子画面を呼び出す
	openPopup(830, 520, 'HshIXTOInq', '/ho/bc/HshIXTOInq/initHshIXTOInq.do', inParam, null, "no");
}

/**
 *
 * 課税情報照会(世帯)画面を開く。(返却値有り）
 *
 * @param tt_yy 課税年度
 * @param ino   個人番号
 */
function openSetaiTaxInfoExistOutparam(inParam, outParam) {

	// ------ ポップアップ画面へのパラメータ設定
	inParam["screen_type"] = "1";

	// --- 3.子画面を呼び出す
	openPopup(830, 520, 'HshIXTOInq', '/ho/bc/HshIXTOInq/initHshIXTOInq.do', inParam, outParam, "no");
}
//--------------------------------------------------------------
// * 課税情報照会画面の呼出関数（世帯）END
//--------------------------------------------------------------





//--------------------------------------------------------------
// * 給付情報照会画面の呼出関数 START
//--------------------------------------------------------------
/**
 *
 * 給付情報照会画面を開く。
 *
 * ※基幹システムの給付情報照会画面を呼び出す。
 *
 * @param ino 個人番号
 */
function openBnftInfo(ino) {

	// ------ 個人番号必須入力チェック
	if (!chkRequired(ino, "個人番号")) {
		return;
	}

	// ------ ポップアップ画面へのパラメータ設定
	var inParam = new Array();
	inParam["ino"]         = ino;
	inParam["screen_type"] = "1";

	// --- 3.子画面を呼び出す
	openPopup(830, 355, 'BnftInfoInq', '/we/np/BnftTakePnMng.do?method=getBnftInfoInq', inParam, null, "no", false);
}
//--------------------------------------------------------------
// * 給付情報照会画面の呼出関数END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 介護保険者情報照会画面の呼出関数 START
//--------------------------------------------------------------
/**
 *
 * 介護保険者情報照会画面を開く。
 *
 * @param ino 個人番号
 */
function openTendIsInfo(ino) {

	// ------ 個人番号必須入力チェック
	if (!chkRequired(ino, "個人番号")) {
		return;
	}

	// ------ ポップアップ画面へのパラメータ設定
	var inParam = new Array();
	inParam["ino"]         = ino;

	// --- 3.子画面を呼び出す
	openPopup(830, 300, 'TendIsInfoInq', '/ho/bc/TendIsInq/initTendIsInq.do', inParam, null, "no");
}
//--------------------------------------------------------------
// * 介護保険者情報照会画面の呼出関数END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 金融機関情報取得画面の呼出関数 START
//--------------------------------------------------------------
/**
 * 金融機関情報取得画面を開く。
 *
 * ※ 必要ない項目は設定しなくてもいい。
 *
 * 下記の仕様でJavascriptのArrayを作成し引数に設定する。
 *
 * ●inParamの仕様
 * inParam["frm_nm"]        : フォーム名
 * inParam["f_cd"]          : 金融機関コードObject名
 * inParam["f_nm"]          : 金融機関名Object名
 * inParam["f_seltext"]     : 金融機関名Object名(コンボボックスの場合)
 * inParam["f_text"]        : 金融機関名Object名(コンボボックス以外の場合)
 * inParam["b_cd"]          : 金融機関支店コードObject名
 * inParam["b_nm"]          : 金融機関支店名Object名
 * inParam["on_close_func"] : 金融機関情報取得後の実行処理名
 *
 * @param inParam 画面を開く時のパラメータが設定されている配列
 * @param outParam 画面を閉じ返却値をもらうパラメータが設定されている配列
 */
function openFiIstGn(inParam) {

	// ------ フォーム名設定
	if (isEmpty(inParam["frm_nm"])) {
		inParam["frm_nm"] = "frm";
	}

	// ------ 金融機関名Object名の再設定
	if (!isEmpty(inParam["f_seltext"])) {
		inParam["f_nm"] = inParam["f_seltext"];		// 金融機関名がコンボボックスの場合
	} else {
		inParam["f_nm"] = inParam["f_cd"];			// 金融機関名がコンボボックス以外の場合
	}

	// ------ 金融機関支店名のObjectがコンボボックスの場合
	if (document.getElementsByName(inParam["b_nm"])[0].tagName == "SELECT") {

		var b_nm = getSelectedText(document.getElementsByName(inParam["b_nm"])[0]);
		var h_id = inParam["b_nm"] + "_hidden";
		inParam["b_nm"] = h_id;

		// ------ 金融機関支店名用hiddenの存在確認
		if (document.getElementById(h_id) == null) {
			var b_hidden = document.createElement("input");
			b_hidden.type   = "hidden";
			b_hidden.id     = h_id;
			b_hidden.value  = b_nm
			document.forms[inParam["frm_nm"]].appendChild(b_hidden);
		} else {
			document.getElementById(h_id).value = b_nm;
		}
	}

	openPopup(710, 400, 'bankInfo', '/bo/ac/BOACAcInfoMng.do?method=fwdBankInfo', inParam, null, "no", false);
}
//--------------------------------------------------------------
// * 金融機関情報取得画面の呼出関数END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 医療機関情報取得画面の呼出関数 START
//--------------------------------------------------------------
/**
 * 医療機関情報取得画面を開く。
 *
 * ※ 必要ない項目は設定しなくてもいい。
 *
 * 下記の仕様でJavascriptのArrayを作成し引数に設定する。
 *
 * ●inParamの仕様
 * inParam["mi_cd"]					 :医療機関コード
 * inParam["mi_kana"]                 :医療機関カナ
 * inParam["mi_nm"]                   :医療機関名
 * inParam["seiho_shitei_kbn"]        :生活保護指定区分
 * inParam["seishin_tsuin_hndg_kbn"]  :精神通院処理区分
 * inParam["kosei_shitei_kbn"]        :更生指定区分
 * inParam["kosei_shitei_dt"]         :更生指定日
 *
 * ●outParamの仕様
 * outParam["mi_cd"]                  :医療機関コード
 * outParam["mi_ots_cl"]              :医療機関外区分
 * outParam["mi_nm"]                  :医療機関名
 * outParam["mi_kana"]                :医療機関カナ名
 * outParam["zip"]                    :郵便番号
 * outParam["ad"]                     :住所
 * outParam["open_pn_nm"]             :開設者氏名
 * outParam["crl_pn_nm"]              :管理者氏名
 * outParam["appo_dt"]                :指定日
 * outParam["appo_te_end_dt"]         :指定期間終了日
 * outParam["ablt_dt"]                :廃止日
 * outParam["mv_ctr_mi_cd"]           :移転先医療機関コード
 * outParam["enll_rsn_cd"]            :登録理由コード
 * outParam["rmrk"]                   :備考
 * outParam["ty"]                     :種別
 * outParam["ty_nm"]                  :種別(名称)
 * outParam["mngt_cl"]                :経営区分
 * outParam["mngt_cl_nm"]             :経営区分(名称)
 * outParam["sff_no"]                 :職員番号
 * outParam["blng_cd"]                :所属コード
 * outParam["seiho_shitei_kbn"]       :生活保護指定区分
 * outParam["seiho_shitei_dt"]        :生活保護指定日
 * outParam["seishin_tsuin_hndg_kbn"] :精神通院処理区分
 * outParam["seishin_tsuin_shitei_dt"]:精神通院指定日
 * outParam["kosei_shitei_kbn"]       :更生指定区分
 * outParam["kosei_shitei_dt"]        :更生指定日
 * outParam["kyuchi"]                 :級地
 * outParam["kyuchi_nm"]              :級地(名称)
 * outParam["toki_kasan_kbn"]         :冬季加算区分
 * outParam["toki_kasan_kbn_nm"]      :冬季加算区分(名称)
 * outParam["on_close_func"]          :取得後に実行するJavaScript関数名
 *
 * @param inParam 画面を開く時のパラメータが設定されている配列
 * @param outParam 画面を閉じ返却値をもらうパラメータが設定されている配列
 */
function openMediIstGn(inParam, outParam) {
	openPopup(830, 435, 'HOBCMediIstGn', '/ho/bc/MediIstSrch/initMediIstSrch.do',inParam, outParam, "no");
}
//--------------------------------------------------------------
// * 医療機関情報取得画面の呼出関数 医療機関情報取得画面の呼出関数END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 医療従事者の呼出関数 START
//--------------------------------------------------------------
/**
 *
 * @param inParam
 * inParam["iryokikan_cd"]
 * inParam["iryokikan_nm"]
 * inParam["iryokikan_kana_nm"]
 * inParam["juji_sha_nm"]
 * @param outParam
 * outParam["iryokikan_cd"]     		;					// 医療機関コード
 *	outParam["kanjime"]              		;					// 医療機関名
 * outParam["kaname"]  					;					// 医療機関カナ名
 * outParam["shurui_nm"]  				;					// 種類名
 * outParam["shurui_cd"]     			;					// 種類コード
 * outParam["addr"]         				;					// 医療機関住所
 * outParam["is_no"]      					;					// 医者番号
 * outParam["is_shime"]              	;					// 医者名
 * outParam["is_sikaku_nm"]       		;					// 医者資格
 * outParam["is_sikaku_cd"]   			;					// 医者資格コード
 * outParam["is_kamoku_nm"]     		;					// 医者科目
 * outParam["is_kamoku_cd"]        	;					// 医者科目コード
 * @return
 */
function openDctrSrch(inParam, outParam) {
	openPopup(830, 610, 'HOBCDctrListGn', '/ho/bc/DctrListp/initDctrListp.do',inParam, outParam, "no");
}
//--------------------------------------------------------------
// * 医療従事者の呼出関数 END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * メモ管理の呼出関数 START
//--------------------------------------------------------------
/**
 * メモ管理画面を開く。
 *
 * 下記の仕様でJavascriptのArrayを作成し引数に設定する。
 *
 * ●inParamの仕様
 * inParam["gyomu_cd"]   : 業務区分
 * inParam["jigyo_cd"]   : 事業区分
 * inParam["ino"]        : 個人番号
 *
 * ●outParamの仕様
 * なし
 *
 * @param inParam	ポップアップ画面起動時の受渡パラメータ
 */
function openMemoMnt(inParam, outParam) {
    openPopup(700, 550, 'HOBCMemoMnt', '/ho/bc/MemoMnt/initMemoMnt.do',inParam, outParam, "no", true);
}
//--------------------------------------------------------------
// * メモ管理の呼出関数 END
//--------------------------------------------------------------


















/**
 *
 * @param inParam
 * inParam["gyomu_cd"]     		;			//業務コード
 * inParam["shisetsu_cd"]     	;			//	施設コード
 * inParam["shubetsu_cd"]     	;			//種別コード
 * inParam["shisetsu_nm"]     	;			//	施設名
 * inParam["jigyosha_nm"]     	;			//事業者名
 * @param outParam
 * outParam["gyomu_cd"]          ;				// 業務コード
 * outParam["shisetu_cd"]         ;				// 施設コード
 * outParam["shubetsu_cd"]          ;				// 施設種別
 * outParam["shubetsu_nm"]          ;				// 施設種別
 * outParam["shisetu_nm"]        ;				// 施設名
 * outParam["toki_kasan_kbn"]    ;				//冬季加算区分
 * outParam["toki_kasan_nm"]    ;				//冬季加算区分
 * outParam["bumjo_no"] 			;			//分場番号
 * outParam["kyuchi"]             	;			// 給地
 * outParam["kyuchi_nm"]             	;			// 給地
 * outParam["shisetsu_ad"]             	;			// 施設住所
 * @return
 */
function openFcltSrchGn(inParam, outParam) {
	openPopup(830, 600, 'HOBCAccListGn', '/ho/bc/FcltSrchP/initFcltSrchP.do',inParam, outParam, "no,scrollbars");
}

function openBizPnGn(inParam, outParam){
	openPopup(830, 600, 'HOBCMemoListGn', '/ho/bc/BizPnSrchP/initBizPnSrchP.do',inParam, outParam, "no,scrollbars");
}















//--------------------------------------------------------------
// * 続柄ポップアップ画面の呼出関数 START
//--------------------------------------------------------------
/**
 *
 * 続柄ポップアップ画面の呼出す画面を開く。
 * @param inParam
 * @param outParam
*/
function openZokugaraCdListSrch(inParam, outParam) {
	openPopup(530, 400, 'HOBCZokugaraPopupSrch', '/ho/bc/ZokugaraPopupSrch/initZokugaraSrch.do',inParam, outParam, "no");
}
//--------------------------------------------------------------
// * 続柄ポップアップ画面の呼出関数 END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 事業者ポップアップ画面の呼出関数 START
//--------------------------------------------------------------
/**
 *
 * 事業者ポップアップ画面の呼出す画面を開く。
 * @param inParam
 * @param outParam
 */
function openJigyoshaSrch(outParam) {
	openPopup(825, 740, 'HOBCBizPnSrchp', '/ho/bc/BizPnSrchp/initBizPnSrch.do',null, outParam, "no");
}
//--------------------------------------------------------------
// * 事業者ポップアップ画面の呼出関数END
//--------------------------------------------------------------







//--------------------------------------------------------------
// * 施設ポップアップ画面の呼出関数 START
//--------------------------------------------------------------
/**
 *
 * 施設ポップアップ画面の呼出す画面を開く。
 * @param inParam
 * @param outParam
 */
function openShisetsuSrch(inParam, outParam) {
	openPopup(825, 600, 'FcltSrchPop', '/ho/bc/FcltSrchp/initFcltSrchPop.do',inParam, outParam, "no");
}
//--------------------------------------------------------------
// * 施設ポップアップ画面の呼出関数 END
//--------------------------------------------------------------




//--------------------------------------------------------------
// * 福祉情報照会画面の呼出関数 START
//--------------------------------------------------------------
/**
 *
 * 福祉情報照会画面を開く。
 *
 * ※基幹システムの福祉情報照会画面を呼び出す。
 *
 * @param ino 個人番号
 */
function openFukusiInfoInq(ino) {

	// ------ 個人番号必須入力チェック
	if (!chkRequired(ino, "個人番号")) {
		return;
	}

	// ------ ポップアップ画面へのパラメータ設定
	var inParam = new Array();
	inParam["s_ino"] = ino;

	// --- 3.子画面を呼び出す
	openPopup(825, 580, 'SrchFukusiInfo', '/ho/bc/WlfInfoInq/initWlfInfoInq.do',inParam, null, "yes");
}
//--------------------------------------------------------------
// * 福祉情報照会画面の呼出関数END
//--------------------------------------------------------------



//--------------------------------------------------------------
// * データダウンロード画面の呼出関数 ENDの呼出関数 START
//--------------------------------------------------------------
/*
 *
 * データダウンロード画面を開く
 * @param inParam
 * @param outParam
 */
function openDataDownload(inParam) {
	    openPopup(860, 560, 'HOBCDataDownoad', '/ho/bc/DataDownload/initDataDownload.do',inParam, null, "yes", true, true);
}
//--------------------------------------------------------------
// * データダウンロード画面の呼出関数 ENDの呼出関数 START
//--------------------------------------------------------------





//--------------------------------------------------------------
// * バッチ実行画面の呼出関数 ENDの呼出関数 START
//--------------------------------------------------------------
/*
 * バッチ実行画面を開く
 * @param inParam InParam
 */
function openBatchExe(inParam) {
    openPopup(620, 550, 'ExeBatch', '/ho/cm/ExeBatch/initExeBatch.do',inParam, null, "yes", true, true);
}

/*
 * バッチ実行のIFramを呼出す
 * @param iframName iframe名
 * @param inParam InParam
 */
function showBtExeIfrm(iframName, inParam) {
	submitIfrm(iframName, "/ho/cm/ExeBatch/initExeBatch.do" ,inParam, true);
}
//--------------------------------------------------------------
// * バッチ実行画面の呼出関数 ENDの呼出関数 START
//--------------------------------------------------------------



