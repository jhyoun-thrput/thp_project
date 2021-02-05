var err_msg_dat = '日付入力のエラー';
var err_msg_req = '必須入力のエラー';
var err_msg_chr = '文字タイプのエラー';
var err_msg_max = '長さのエラー';

/**
 * f_nm名を持つformにcfrm Attributeにallとsubmitに該当するElementに対して定義した整合性を処理する。
 * 
 * @param f_nm
 *            フォーム名
 * @param flag
 *            必修項目チェックフラグ
 * @return {boolean}
 */
function submit_cfrm(f_nm, flag) {

	var obj_f = eval("document." + f_nm);

	var rslt = true;

	for ( var i = 0; i < obj_f.length; i++) {

		if (obj_f.elements[i].cfrm == 'all'
				|| obj_f.elements[i].cfrm == 'submit') {

			if (cfrm_submit(obj_f.elements[i], flag)) {

			} else {

				return false;

			}

		}

	}

	return rslt;

}

/**
 * item名を該当するElementに対して定義した整合性を処理する。(submit_cfrm 補助 ファンクション)
 * 
 * @param item
 *            フォームエレメント
 * @param flag
 *            必修項目チェックフラグ
 * @return {boolean}
 */
function cfrm_submit(item, flag) {

	var rslt = true;

	var error_name = "";

	if (rslt && item.cfrm_type != null) {

		if (item.cfrm_type == 'jdt') {

			if (!sub_jdt(item)) {

				rslt = false;

				error_name = err_msg_dat;

			}

		} else if (item.cfrm_type == 'jdty') {

			if (!sub_jdty(item)) {

				rslt = false;

				error_name = err_msg_dat;

			}

		} else if (item.cfrm_type == 'jdtm') {

			if (!sub_jdtm(item)) {

				rslt = false;

				error_name = err_msg_dat;

			}

		} else if (item.cfrm_type == 'jdtd') {

			if (!sub_jdtd(item)) {

				rslt = false;

				error_name = err_msg_dat;

			}
		}

	}

	if (flag == null) {

		if (rslt && item.req_chk != null) {

			if (!sub_req(item)) {

				rslt = false;

				error_name = err_msg_req;

			}
		}
	}

	if (rslt && item.cha_chk != null) {

		if (!sub_cha(item)) {

			rslt = false;

			var lan_nm = get_lan_nm_by_lan_code(item.cha_chk, item.cha_ext);

			error_name = err_msg_chr + '(' + lan_nm + ')';

		}

	}

	if (rslt && item.len_chk != null) {

		if (!sub_max(item)) {

			rslt = false;

			error_name = err_msg_max + ' ' + item.len_chk;

		}

	}

	if (rslt) {

		return rslt;

	} else {

		var this_label = item.lbl;

		if (this_label == null)
			this_label = item.name;

		if (item.no_alert == null)
			alert(this_label + ' : ' + error_name);

		if (item.err_nxt != null) {

			if (item.err_nxt != 'null') {

				try {

					var tmp_obj = eval(item.err_nxt);

					if (tmp_obj != null) {

						tmp_obj.focus();

						tmp_obj.select();

					}

				} catch (e) {

				}

			}

		} else {

			try {

				item.focus();

				item.select();

			} catch (e) {

			}

		}

		return rslt;

	}

}

/**
 * f_nm 名を持つformにcfrm Attributeにallとsubmitに該当するElementについて定義した整合性に対する
 * eventをマッピングする。
 * 
 * @param f_nm
 *            フォーム名
 * @return {boolean}
 */
function ss_cfrm(f_nm) {

	var obj_f = eval(f_nm);

	for ( var i = 0; i < obj_f.length; i++) {

		if (obj_f.elements[i].cha_chk != null) {

			sub_i_ime(obj_f.elements[i]);

		}

		/* tab-order setting */

		if (obj_f.elements[i].tab_nxt != null) {

			obj_f.elements[i].onkeydown = sub_nxt;

		}

		/* length setting */

		if (obj_f.elements[i].cfrm_type != null) {

			if (obj_f.elements[i].cfrm_type == 'jdt') {

				if (obj_f.elements[i].wrk_slt == null) {

					// obj_f.elements[i].maxLength = 7;

					obj_f.elements[i].style.imeMode = 'disabled';

				} else {

					var tmp_obj = eval(obj_f.elements[i].wrk_slt);

					try {

						// obj_f.elements[i].maxLength = 6;

						obj_f.elements[i].style.imeMode = 'disabled';

					} catch (e) {

					}

				}

			}

			if (obj_f.elements[i].cfrm_type == 'jdty') {

				if (obj_f.elements[i].wrk_slt == null) {

					// obj_f.elements[i].maxLength = 7;

					obj_f.elements[i].style.imeMode = 'disabled';

				} else {

					var tmp_obj = eval(obj_f.elements[i].wrk_slt);

					try {

						// obj_f.elements[i].maxLength = 6;

						obj_f.elements[i].style.imeMode = 'disabled';

					} catch (e) {

					}

				}

			}

			if (obj_f.elements[i].cfrm_type == 'jdtm') {

				if (obj_f.elements[i].wrk_slt == null) {

					// obj_f.elements[i].maxLength = 7;

					obj_f.elements[i].style.imeMode = 'disabled';

				} else {

					var tmp_obj = eval(obj_f.elements[i].wrk_slt);

					try {

						// obj_f.elements[i].maxLength = 6;

						obj_f.elements[i].style.imeMode = 'disabled';

					} catch (e) {

					}

				}

			}

			if (obj_f.elements[i].cfrm_type == 'jdtd') {

				if (obj_f.elements[i].wrk_slt == null) {

					// obj_f.elements[i].maxLength = 7;

					obj_f.elements[i].style.imeMode = 'disabled';

				} else {

					var tmp_obj = eval(obj_f.elements[i].wrk_slt);

					try {

						// obj_f.elements[i].maxLength = 6;

						obj_f.elements[i].style.imeMode = 'disabled';

					} catch (e) {

					}

				}

			}

		}

		/* ime-mode setting */

		if (obj_f.elements[i].ime_set != null) {

			sub_e_ime(obj_f.elements[i]);

		}

		/* maxlength setting */

		if (obj_f.elements[i].len_chk != null) {

			set_max(obj_f.elements[i]);

		}

		/* validation */

		if (obj_f.elements[i].cfrm == 'all'
				|| obj_f.elements[i].cfrm == 'default') {

			obj_f.elements[i].onkeydown = cfrm_onkeydown;

			obj_f.elements[i].onblur = cfrm_onblur;

		} else {
			obj_f.elements[i].onblur = cfrm_addblur_only;
		}

	}

}

/**
 * item 名に該当するElementのime_set attribueを通じてime-modeを明示的に設定する。(ss_cfrm 補助 ファンクション)
 * 
 * @param item
 *            フォームエレメント
 * @return
 */
function sub_e_ime(item) {

	if (item.ime_set == 'disabled') {

		item.style.imeMode = 'disabled';

	} else if (item.ime_set == 'active') {

		item.style.imeMode = 'active';

	} else if (item.ime_set == 'inactive') {

		item.style.imeMode = 'inactive';

	}

}

/**
 * item 名に該当するElementのcha_chk attribueを通じてime-modeを黙示的に設定する。(ss_cfrm 補助 ファンクション)
 * 
 * @param item
 *            フォームエレメント
 * @return
 */
function sub_i_ime(item) {

	if (item.cha_chk == 'HE'

	|| item.cha_chk == 'N'

	|| item.cha_chk == 'NC'

	|| item.cha_chk == 'NCF'

	|| item.cha_chk == 'NHE'

	|| item.cha_chk == 'NF') {

		item.style.imeMode = 'disabled';

	} else if (item.cha_chk == 'HK'

	|| item.cha_chk == 'FE'

	|| item.cha_chk == 'KJ'

	|| item.cha_chk == 'FK'

	|| item.cha_chk == 'H'

	|| item.cha_chk == 'FW') {

		item.style.imeMode = 'active';

	} else if (item.cha_chk == 'HW') {

		item.style.imeMode = 'inactive';

	}

}

/**
 * item 名に該当するElementのlen_chk attribueを通じてmaxLengthを設定する。(ss_cfrm 補助 ファンクション)
 * 
 * @param item
 *            フォームエレメント
 * @return
 */
function set_max(item) {

	if (item.len_chk != null) {

		var length = item.len_chk;

		if (item.cha_chk != null) {

			if (item.cha_chk == 'NF') {

				var pos = item.len_chk.indexOf('.');

				if (pos >= 0)

					length = eval(item.len_chk.substring(0, pos)) + 1;

			} else if (item.cha_chk == 'NC') {

				length = eval(length) + (eval(length) / 4);

			} else if (item.cha_chk == 'NCF') {

				pos = item.len_chk.indexOf('.');

				if (pos >= 0) {

					var t = eval(item.len_chk.substring(0, pos));

					var f = eval(item.len_chk.substring(pos));

					var i = t - f;

					length = i + (i / 4) + f + 1;

				} else {

					length = eval(length) + (eval(length) / 4);

				}

			}

		}

		item.maxLength = length;

	}

}

/**
 * item名に該当するElementのtab_nxt attribueを通じてタブキーを押す時、次に異動するElementを設定する。(ss_cfrm 補助
 * ファンクション)
 * 
 * @param from
 *            現在位置
 * @param to
 *            フォーカスを移動するエリートメント
 * @return
 */
function sub_nxt(from, to) {

	if (from == null)
		from = this;

	if (to == null)
		to = from.tab_nxt;

	if (event.keyCode == "09") {

		event.returnValue = false;

		var obj = eval(to);

		if (obj != null) {

			obj.focus();

			try {

				obj.select();

			} catch (e) {

			}

		}

	}

}

/**
 * f_nm名を持つformにonkeydownイベントについて定義した整合性に対する処理結果を伝達する。(ss_cfrm 補助 ファンクション)
 * 
 * @return {boolean}
 */
function cfrm_onkeydown() {

	var item = this;

	var rslt = true;

	if (item.tab_nxt != null) {

		if (event.keyCode == "09") {

			event.returnValue = false;

			var obj = eval(item.tab_nxt);

			if (obj != null) {

				obj.focus();

				try {

					obj.select();

				} catch (e) {

				}

			}

		}

	}

	if (is_keycode_subkey())
		return true;

	if (item.len_chk != null && item.len_chk.indexOf('.') < 0) {

		if (item.crt_len == null)

			rslt = sub_max(item);

	}

	if (!rslt) {

		if (!is_keycode_subkey())
			event.returnValue = false;

	}

	if (is_keycode_subkey())
		return true;

	if (rslt) {

		if (item.cha_chk == 'N') {

			rslt = is_keycode_number();

			if (!rslt)
				event.returnValue = false;

		} else if (item.cha_chk == 'HE') {

			rslt = is_keycode_alphabet();

			if (!rslt)
				event.returnValue = false;

		} else if (item.cha_chk == 'NHE') {

			if (!is_keycode_alphabet() &&

			!is_keycode_number())
				event.returnValue = false;

		} else if (item.cha_chk == 'NF') {

			rslt = is_keycode_float_number();

			if (!rslt)
				event.returnValue = false;

		} else if (item.cha_chk == 'NC') {

			rslt = is_keycode_nc();

			if (!rslt)
				event.returnValue = false;

		} else if (item.cha_chk == 'NCF') {

			rslt = is_keycode_ncf();

			if (!rslt)
				event.returnValue = false;

		} else if (item.cha_chk == 'NN' || item.cha_chk == 'TN') {

			rslt = is_keycode_nn();

			if (!rslt)
				event.returnValue = false;
		}

	}

	if (rslt && item.cfrm_type != null) {

		if (item.cfrm_type == 'jdt') {

			// rslt = is_keycode_number();

			if (!rslt || is_keycode_subkey())
				event.returnValue = false;

		}

		if (item.cfrm_type == 'jdty') {

			// rslt = is_keycode_number();

			if (!rslt || is_keycode_subkey())
				event.returnValue = false;

		}

		if (item.cfrm_type == 'jdtm') {

			// rslt = is_keycode_number();

			if (!rslt || is_keycode_subkey())
				event.returnValue = false;

		}

		if (item.cfrm_type == 'jdtd') {

			// rslt = is_keycode_number();

			if (!rslt || is_keycode_subkey())
				event.returnValue = false;

		}

	}

	if (item.add_key_down != null) {

		eval(item.add_key_down);

	}

	return rslt;

}

/**
 * f_nm名を持つformにonblurイベントについて定義した整合性に対する処理結果を伝達する。(ss_cfrm 補助 ファンクション)
 * 
 * @return {boolean}
 */
function cfrm_onblur() {

	var item = this;

	var rslt = true;

	var error_name = "";

	if (item.cfrm_type != null && rslt) {

		if (item.cfrm_type == 'jdt') {

			if (!sub_jdt(item)) {

				rslt = false;

				error_name = err_msg_dat;

			}

		}

		if (item.cfrm_type == 'jdty') {

			if (!sub_jdty(item)) {

				rslt = false;

				error_name = err_msg_dat;

			}

		}

		if (item.cfrm_type == 'jdtm') {

			if (!sub_jdtm(item)) {

				rslt = false;

				error_name = err_msg_dat;

			}

		}

		if (item.cfrm_type == 'jdtd') {

			if (!sub_jdtd(item)) {

				rslt = false;

				error_name = err_msg_dat;

			}

		}

	}

	if (item.add_pre_blur != null && rslt) {

		eval(item.add_pre_blur);

	}

	if (item.len_chk != null && rslt) {

		rslt = sub_max(item);

		error_name = err_msg_max + ' ' + item.len_chk;

	}

	/*
	 * 
	 * if (item.req_chk!=null && rslt){
	 * 
	 * rslt = sub_req(item);
	 * 
	 * error_name = err_msg_req; }
	 * 
	 */

	if (rslt && item.cha_chk != null) {

		rslt = sub_cha(item);

		var lan_nm = get_lan_nm_by_lan_code(item.cha_chk, item.cha_ext);

		error_name = err_msg_chr + '(' + lan_nm + ')';

	}

	if (rslt) {

		if (item.add_blur != null) {

			eval(item.add_blur);

		}

		return rslt;

	} else {

		var this_label = item.lbl;

		if (this_label == null)
			this_label = item.name;

		if (item.no_alert == null)
			alert(this_label + ' : ' + error_name);

		if (item.err_nxt != null) {

			if (item.err_nxt != 'null') {

				try {

					var tmp_obj = eval(item.err_nxt);

					if (tmp_obj != null) {

						tmp_obj.focus();

						tmp_obj.select();

					}

				} catch (e) {

				}

			}

		} else {

			try {

				item.focus();

				item.select();

			} catch (e) {

			}

		}

		return rslt;

	}

}

/**
 * f_nm名を持つformにaddblurイベントについて定義した整合性に対する処理結果を伝達する。(ss_cfrm 補助 ファンクション)
 * 
 * @return {boolean}
 */
function cfrm_addblur_only() {

	var item = this;

	var rslt = true;

	var error_name = "";

	if (item.add_pre_blur != null && rslt) {

		eval(item.add_pre_blur);

	}

	if (rslt) {

		if (item.add_blur != null) {

			eval(item.add_blur);

		}

		return rslt;

	} else {

		var this_label = item.lbl;

		if (this_label == null)
			this_label = item.name;

		if (item.no_alert == null)
			alert(this_label + ' : ' + error_name);

		if (item.err_nxt != null) {

			if (item.err_nxt != 'null') {

				try {

					var tmp_obj = eval(item.err_nxt);

					if (tmp_obj != null) {

						tmp_obj.focus();

						tmp_obj.select();

					}

				} catch (e) {

				}

			}

		} else {

			try {

				item.focus();

				item.select();

			} catch (e) {

			}

		}

		return rslt;

	}

}

/**
 * valに 該当する入力文字全体の名称を持ってくる。(cfrm_onblur 補助 ファンクション)
 * 
 * @param val
 *            cha_chkプロパティー値
 * @param ext
 *            chk_extプロパティー値
 * @return {String}
 */
function get_lan_nm_by_lan_code(val, ext) {

	var name = "";

	switch (val) {

	case "HE":

		name = "半角英語";

		break;

	case "FE":

		name = "全角英語";

		break;

	case "N":

		name = "正数";

		break;

	case "NC":

		name = "正数及びコンマ";

		break;

	// 改善目録 NO-09 ： 2007.06.08 金東壎： 追加 START

	case "NS":

		name = "正数及びスぺース";

		break;

	// 改善目録 NO-09 ： 2007.06.08 金東壎： 追加 END

	case "NCF":

		name = "実数及びコンマ";

		break;

	case "HK":

		name = "半角カタカナ";

		break;

	case "FK":

		name = "全角カタカナ";

		break;

	case "H":

		name = "ひらがな";

		break;

	case "KJ":

		name = "漢字";

		break;

	case "NHE":

		name = "半角英語及び正数";

		break;

	case "NF":

		name = "実数";

		break;

	case "HW":

		name = "半角文字";

		break;

	case "FW":

		name = "全角文字";

		break;

	}

	if (ext != null && ext.length > 0)

		name = name + ',' + ext;

	return name;

}

/**
 * objに最大の長さが超過されたかを判断する。(補助 ファンクション)
 * 
 * @param obj
 *            フォームエレメント
 * @return {boolean}
 */
function sub_max(obj) {

	if (obj == null)
		obj = this;

	if (obj.crt_len != null) {

		// alert(obj.value.length + " : " + obj.len_chk);

		return (obj.value.length == 0 || obj.value.length == obj.len_chk);

	} else {

		return is_maxlength(obj, obj.len_chk);

	}

}

/**
 * objに値が入力されたかを判断する。(補助 ファンクション)
 * 
 * @param obj
 *            フォームエレメント
 * @return {boolean}
 */
function sub_req(obj) {

	if (obj == null)
		obj = this;

	if (obj.req_chk != 'true')
		return true;

	if (is_required(obj)) {

		return true;

	} else {

		if (event != null)
			event.returnValue = false;

		return false;

	}

}

/**
 * obj 名に該当するElementの cha_chk attribueを通じて適切な整合性を検査する。(cfrm_onblur, cfrm_submit
 * 補助 ファンクション)
 * 
 * @param obj
 *            フォームエレメント
 * @return {boolean}
 */
function sub_cha(obj) {

	if (obj == null)
		obj = this;

	var arg = obj.cha_chk;

	switch (arg) {

	case "HE":

		return is_obj_half_alphabet(obj);

		// break;

	case "FE":

		return is_obj_full_alphabet(obj);

		// break;

	case "N":

		return is_obj_number(obj);

		// break;

	case "NC":

		return is_obj_nc(obj);

		// break;

		// 改善目録 NO-09 ： 2007.06.08 金東壎： 追加 START

	case "NS":

		return is_obj_ns(obj);

		// break;

		// 改善目録 NO-09 ： 2007.06.08 金東壎： 追加 END

	case "NCF":

		return is_obj_ncf(obj);

		// break;

	case "HK":

		return is_obj_half_katakana(obj);

		// break;

	case "FK":

		return is_obj_full_katakana(obj);

		// break;

	case "H":

		return is_obj_hirakana(obj);

		// break;

	case "KJ":

		return is_obj_kanji(obj);

		// break;

	case "NHE":

		return is_obj_number_half_alphabet(obj);

		// break;

	case "NF":

		return is_obj_float_number(obj);

		// break;

	case "HW":

		return is_obj_half_width(obj);

		// break;

	case "FW":

		return is_obj_full_width(obj);

		// break;

	case "NN":

		return is_obj_negative_number(obj);

		// break;

	case "GJ":

		return is_obj_gaiji(obj);

	case "TN":

		return is_obj_telephone_number(obj);

	}

	return true;

}

/**
 * objが日時形態で入力されたかを判断する。(補助 ファンクション)
 * 
 * @param obj
 *            フォームエレメント
 * @return {boolean}
 */
function sub_jdt(obj) {

	var rslt = true;

	if (obj == null)
		obj = this;

	if (obj.value == "")
		return true;

	if (obj.wrk_slt != null) {

		var tmp_obj = eval(obj.wrk_slt);

		var pre_obj = obj.value;

		obj.value = tmp_obj.value + obj.value;

		rslt = is_obj_date(obj);

		obj.value = pre_obj;

	} else {

		rslt = is_obj_date(obj);

	}

	return rslt;

}

/**
 * objが年形態で入力されたかを判断する。(補助 ファンクション)
 * 
 * @param obj
 *            フォームエレメント
 * @return {boolean}
 */
function sub_jdty(obj) {

	var rslt = true;

	if (obj == null)
		obj = this;

	if (obj.value == "")
		return true;

	if (obj.wrk_slt != null) {

		var tmp_obj = eval(obj.wrk_slt);

		var pre_obj = obj.value;

		obj.value = tmp_obj.value + obj.value;

		rslt = is_obj_date_YYY(obj);

		obj.value = pre_obj;

	} else {

		rslt = is_obj_date_YYY(obj);

	}

	return rslt;

}

/**
 * objが月形態で入力されたかを判断する。(補助 ファンクション)
 * 
 * @param obj
 *            フォームエレメント
 * @return {boolean}
 */
function sub_jdtm(obj) {

	var rslt = true;

	if (obj == null)
		obj = this;

	if (obj.value == "")
		return true;

	if (obj.wrk_slt != null) {

		var tmp_obj = eval(obj.wrk_slt);

		var pre_obj = obj.value;

		obj.value = tmp_obj.value + obj.value;

		rslt = is_obj_date_YYYMM(obj);

		obj.value = pre_obj;

	} else {

		rslt = is_obj_date_YYYMM(obj);

	}

	return rslt;

}

/**
 * objが日形態で入力されたかを判断する。(補助 ファンクション)
 * 
 * @param obj
 *            フォームエレメント
 * @return {boolean}
 */
function sub_jdtd(obj) {

	var rslt = true;

	if (obj == null)
		obj = this;

	if (obj.value == "")
		return true;

	if (obj.wrk_slt != null) {

		var tmp_obj = eval(obj.wrk_slt);

		var pre_obj = obj.value;

		obj.value = tmp_obj.value + obj.value;

		rslt = is_obj_date_YYYMMDD(obj);

		obj.value = pre_obj;

	} else {

		rslt = is_obj_date_YYYMMDD(obj);

	}

	return rslt;

}

function setValueforCfrm(form_nm, cha_chk, value) {

	var f = eval("document." + form_nm + ".elements");

	for (i = 0; i < f.length; i++) {
		if (f[i].cha_chk != null) {
			if (f[i].cha_chk.toUpperCase() == cha_chk.toUpperCase())
				f[i].value = value;
		}
	}
}
