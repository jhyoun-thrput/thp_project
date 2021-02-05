package tech3g.common.web.ajax.services;

import tech3g.common.web.CommonParams;

public interface COAJSrchService {
	/**
	 * ビジネスクラスのめっソードを呼び出してその結果を返還する。<br/>
	 * @param CommonParams param
	 * @return 実行結果を文字列に返還する
	 * @throws Exception すべての例外
	 */
	public String getInfoByAjax(CommonParams param) throws Exception;
}

