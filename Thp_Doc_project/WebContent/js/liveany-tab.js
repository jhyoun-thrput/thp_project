/*=======================================================================
 * liveany-tab.js
 *
 * TAB関連JavaScript
 * =======================================================================*/

/**
 * オンロードにファンクションを設定
 * @param func
 * @return
 */
function addLoadEvent(func) {
	var oldonload = window.onload;
	// オンロードが設定されていなかったらファンクションを設定
	if (typeof window.onload != 'function') {
		window.onload = func;
	} else {
		// すでにonloadが設定されていた場合はそれの後に設定
		window.onload = function() {
			oldonload();
			func();
		}
	}
}

/**
 * クラス名よりエレメントを取得する。
 * @param oElm
 * @param strTagName
 * @param strClassName
 * @return
 */
function getElementsByClassName(oElm, strTagName, strClassName) {
	/*oElm.allはIE専用？*/
	var arrElements = (strTagName == "*" && oElm.all) ? oElm.all : oElm.getElementsByTagName(strTagName);
	var arrReturnElements = new Array();
	strClassName = strClassName.replace(/\-/g, "\\-");/*「/\-/」は「\-」が含まれる文字列を「g」全て検索*/
	var oRegExp = new RegExp("(^|\\s)" + strClassName + "(\\s|$)");/*「\s（空白文字）」：単一行を表す*/

	var oElement;
	for (var i = 0; i < arrElements.length; i++) {
		oElement = arrElements[i];
		if (oRegExp.test(oElement.className)) {		/*strClassNameとマッチしたかどうかを真偽値*/
			arrReturnElements.push(oElement);		/*i個目のstrTagNameタグの内容を全て格納*/
		}
	}
	return (arrReturnElements)
}

/**
 * クラス名を追加する。
 * IEではクラス名のセット・リセットにバグがあるため関数を作成
 * @param oElm
 * @param strClassName
 * @return
 */
function addClassName(oElm, strClassName){
	var strCurrentClass = oElm.className;
	if (!new RegExp(strClassName, "i").test(strCurrentClass)) {
		oElm.className = strCurrentClass + ((strCurrentClass.length > 0)? " " : "") + strClassName;
	}
}

/**
 * クラス名を削除する。
 * @param oElm
 * @param strClassName
 * @return
 */
function removeClassName(oElm, strClassName){
	var oClassToRemove = new RegExp((strClassName + "\s?"), "i");
	oElm.className = oElm.className.replace(oClassToRemove, "").replace(/^\s?|\s?$/g, "");
}

/**
 * タブ作成
 * @return
 */
function ud_tab() {
	if(!document.getElementsByTagName) return false;
	if(!document.getElementById) return false;

	var UdTabs = getElementsByClassName(document, "div", "UdTabs");
	for (var i = 0; i < UdTabs.length; i++) {
		var UdTab_Id = "udtbID" + i;
		var active_tab = 0;

		// divタグにidをふる。
		UdTabs[i].setAttribute("id", "udtbID" + i);

		// ulタグ分析開始
		var UdUl = getElementsByClassName(UdTabs[i], "ul", "UdTabNavi");
		for (var j = 0; j < UdUl.length; j++) {
			var Uda = UdUl[j].getElementsByTagName("a");
			var Udli = UdUl[j].getElementsByTagName("li");
			for (var k = 0; k < Udli.length; k++) {
				Uda[k].setAttribute("href", "#" + UdTab_Id + "_a" + k);
				Uda[k].setAttribute("id", UdTab_Id + "_a" + k);
				Uda[k].onclick = function() {
					SetCurrent(this);
					return false;
				}
				if (Udli[k].className == "active") {
					active_tab = k;
				}
			}
			// 初期選択
  			Udli[active_tab].className = "active";
		}

		// タブの内容セット処理
		var UdTabCon = getElementsByClassName(UdTabs[i], "div", "udTabCon");
		for (var l = 0; l < UdTabCon.length; l++) {
			if (UdTabCon[l].className == "udTabCon") {
				UdTabCon[l].setAttribute("id", UdTab_Id + "_div_" + [l]);
				UdTabCon[l].className = "UdTabCon";
			}
		}
		// 初期選択
		UdTabCon[active_tab].className = "UdTabCon activeTab";
	}
	return true;
}

/**
 * タブ内容の非表示設定処理
 * @param elm
 * @return
 */
function SetCurrent(elm) {
	var thisTabID = elm.parentNode.parentNode.parentNode.getAttribute("id");
	var regExpAnchor = thisTabID + "_a";
	var thisLinkPosition = elm.getAttribute("id").replace(regExpAnchor, "");

	// タブのアンカー変更処理
	var otherLinks = elm.parentNode.parentNode.getElementsByTagName("li");
	for (var n = 0; n < otherLinks.length; n++) {
		removeClassName(otherLinks[n], "active");			// 全てのエレメントから削除
	}
	addClassName(elm.parentNode, "active");					//「thisLinkPosition」に追加

	// 指定タブの内容に変更処理
	var otherDivs = document.getElementById(thisTabID).getElementsByTagName("div");
	for (var i = 0; i < otherDivs.length; i++) {
			removeClassName(otherDivs[i], "activeTab");		// 全てのエレメントから削除
	}
	addClassName(document.getElementById(thisTabID + "_div_" + thisLinkPosition), "activeTab");
}