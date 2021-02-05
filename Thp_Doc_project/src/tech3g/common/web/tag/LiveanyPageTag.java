package tech3g.common.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.struts.taglib.html.BaseHandlerTag;
/**
 * <pre>
 * LiveanyPageTagクラス。
 * </pre>
 *
 */
public class LiveanyPageTag extends BaseHandlerTag {

	/** 入力タイプ */
	private String pageNo;
	private String listNo;
	private String totalCnt;
	private String noPerIdx;
	private String funcNm;

	private int pageNoInt;
	private int listNoInt;
	private int totalCntInt;

	private int totPage;
	private int lgPage;
	private int lgTotPage;
	private int firstNo;
	private int lastNo;
	private int noPerIdxInt;

	private String form;
	private String target;
	private String action;
	private String type;

	private String showProgress;

	/**
	 * Script関数名を設定する。<br/>
	 * @param inputType
	 */
	public void setFuncNm(String string) {
		this.funcNm = string;
	}

	/**
	 * ページグループ番号を設定する。<br/>
	 * @param inputType
	 */
	public void setListNo(String string) {
		this.listNo = string;
	}

	/**
	 * 画面に表示するページ番号数を設定する。<br/>
	 * @param inputType
	 */
	public void setNoPerIdx(String string) {
		this.noPerIdx = string;
	}

	/**
	 * パラメータページ番号もしくは基本ページ番号を設定する。<br/>
	 * @param inputType
	 */
	public void setPageNo(String string) {
		this.pageNo = string;
	}

	/**
	 * 全体のデータ件数を設定する。<br/>
	 * @param inputType
	 */
	public void setTotalCnt(String string) {
		this.totalCnt = string;
	}

	/**
	 * 要請したアクションを設定する。<br/>
	 * @param inputType
	 */
	public void setAction(String string) {
		action = string;
	}

	/**
	 * Form名を設定する。<br/>
	 * @param inputType
	 */
	public void setForm(String string) {
		form = string;
	}

	/**
	 * Script関数を設定する。<br/>
	 * @param inputType
	 */
	public void setType(String string) {
		type = string;
	}

	/**
	 * 要請したターゲットを設定する。<br/>
	 * @param inputType
	 */
	public void setTarget(String string) {
		target = string;
	}

	/**
	 * showProgressを取得する。<br/>
	 * @return showProgress
	 */
	public String getShowProgress() {
		return showProgress;
	}

	/**
	 * showProgressを設定する。<br/>
	 * @param showProgress showProgress
	 */
	public void setShowProgress(String showProgress) {
		this.showProgress = showProgress;
	}

	/**
	 * 画面に表示するコードを作成するめっソードを呼び出す。<br/>
	 * @return タグクラスの常数を返還する
	 * @throws JspException
	 */
	public String genIndex() {

		String genStr = "";
		if (pageNo != null && !pageNo.equals("")) {
			pageNoInt = Integer.parseInt(pageNo);
		} else {
			pageNoInt = 1;
		}

		if (listNo != null && !listNo.equals("")) {
			listNoInt = Integer.parseInt(listNo);
		} else {
			listNoInt = 10;
		}

		if (totalCnt != null && !totalCnt.equals("")) {
			totalCntInt = Integer.parseInt(totalCnt);
		}

		if (noPerIdx != null && !noPerIdx.equals("")) {
			noPerIdxInt = Integer.parseInt(noPerIdx);
		}

		if (listNoInt == 0 || totalCntInt == 0) {
		// 1ページの表示件数画0の場合
			StringBuffer sb = new StringBuffer();
			sb.append("<input type='hidden' name='pageNo'/> \n");
			sb.append("<input type='hidden' name='currentPageNo' value='" + pageNoInt + "'/> \n");
			sb.append("<input type='hidden' name='listNo' value='" + listNoInt + "'/> \n");
			sb.append("<input type='hidden' name='totalCnt' value='" + totalCnt + "'/>  \n");
			return sb.toString();
		}

		if (type != null && type.equals("script")) {
			genStr = writeScript();
		} else {
			computePage();
			genStr = writePage();
		}
		return genStr;
	}

	/**
	 * 画面に表示するJavascriptコードを作成し文字列に格納する<br/>
	 * @return 画面に表示するJavascriptコードを返還する
	 * @throws JspException
	 */
	public String writeScript() {

		StringBuffer sb = new StringBuffer();

		try {
			sb.append(" function " + funcNm + "(pno) { \n");
			if (action != null && action.trim().equals("")) {
				sb.append("		document." + form + ".action = '" + action + "'; \n");
			}
			sb.append("		document." + form + ".pageNo.value=pno; \n");
			if (target != null && target.trim().equals("")) {
				sb.append("		document." + form + ".target = '" + target + "'; \n");
			}
			sb.append("		document." + form + ".submit(); \n");
			sb.append("	} \n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 画面に表示するHTMLコードを作成し文字列に格納する<br/>
	 * @return 画面に表示するHTMLコードを返還する
	 * @throws JspException
	 */
	public String writePage() {
		StringBuffer sb = new StringBuffer();
		try {
			if (this.totalCntInt != 0 && this.totPage != 1) {
				sb.append("<table border=0 cellspacing=0 cellpadding=0> \n");
				sb.append("<tr><td> \n");
				if (this.lgPage != 1) {
					sb.append(" <a href=\"JavaScript:" + this.funcNm + "('" + ((this.lgPage - 1) * this.noPerIdxInt) + "');" + getShowProgressStr() + "\"> \n");
					sb.append("<img src=\"/Tech3g_Doc_project/images/common/page_first.gif\" border=\"0\" width=18 height=13 align=\"absmiddle\"> \n");
					sb.append("</a> \n");
				}
				sb.append("</td> \n");
				sb.append("<td> \n");
				if (this.pageNoInt > 1) {
					sb.append("<a href=\"JavaScript:" + this.funcNm + "('" + (this.pageNoInt - 1) + "');" + getShowProgressStr() + "\"> \n");
					sb.append("<img src=\"/Tech3g_Doc_project/images/common/page_pre.gif\" border=\"0\" width=14 height=13 align=\"absmiddle\"> \n");
					sb.append("</a> \n");
				}
				sb.append("</td> \n");
				sb.append("<td> \n");
				for (int i = (this.lgPage - 1) * this.noPerIdxInt + 1;
								i <= this.lgPage * this.noPerIdxInt && i <= this.totPage; i++) {
					if (i == this.pageNoInt) {
						sb.append("<font color=FF6600><strong>[" + i + "]</strong></font> \n");
					} else {
						sb.append("<a href=\"JavaScript:" + this.funcNm + "('" + i + "');" + getShowProgressStr() + "\">[" + i + "]</a> \n");
					}
				}
				sb.append("</td> \n");
				sb.append("<td> \n");
				if (this.pageNoInt < this.totPage) {
					sb.append("<a href=\"JavaScript:" + this.funcNm + "('" + (this.pageNoInt + 1) + "');" + getShowProgressStr() + "\"> \n");
					sb.append("<img src=\"/Tech3g_Doc_project/images/common/page_next.gif\" border=\"0\" width=14 height=13 align=\"absmiddle\"> \n");
					sb.append("</a> \n");
				}
				sb.append("</td> \n");
				sb.append("<td> \n");
				if (this.lgPage != this.lgTotPage) {
					sb.append("<a href=\"JavaScript:" + funcNm + "('" + (this.lgPage * this.noPerIdxInt + 1) + "');" + getShowProgressStr() + "\"> \n");
					sb.append("<img src=\"/Tech3g_Doc_project/images/common/page_last.gif\" border=\"0\" width=18 height=13 align=\"absmiddle\"> \n");
					sb.append("</a> \n");
				}
				sb.append("</td> \n");
				sb.append("</tr> \n");
				sb.append("</table> \n");
			} else {
				sb.append("&nbsp;");
			}


			sb.append("<input type='hidden' name='pageNo'/> \n");
			sb.append("<input type='hidden' name='currentPageNo' value='" + pageNoInt + "'/> \n");
			sb.append("<input type='hidden' name='listNo' value='" + listNoInt + "'/> \n");
			sb.append("<input type='hidden' name='totalCnt' value='" + totalCnt + "'/> \n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/**
	 * 画面に表示するページ番号を計算する<br/>
	 * @return
	 * @throws
	 */
	public void computePage() {
		this.firstNo = 0;
		this.lastNo = 0;
		this.totPage = this.totalCntInt / this.listNoInt;
		if (this.totalCntInt % this.listNoInt != 0) {
			this.totPage = this.totPage + 1;
		}
		this.lgPage = (this.pageNoInt / this.noPerIdxInt);
		if (this.pageNoInt % this.noPerIdxInt != 0) {
			this.lgPage = this.lgPage + 1;
		}
		this.lgTotPage = (this.totPage / this.noPerIdxInt);
		if (this.totPage % this.noPerIdxInt != 0) {
			this.lgTotPage = this.lgTotPage + 1;
		}
		this.firstNo = (this.pageNoInt - 1) * this.listNoInt + 1;
		if (this.pageNoInt == this.totPage) {
			this.lastNo = this.totalCntInt;
		} else {
			this.lastNo = this.pageNoInt * this.listNoInt;
		}
	}

	/**
	 * 画面に表示するコードを作成するめっソードを呼び出す。<br/>
	 * @return タグクラスの常数を返還する
	 * @throws JspException
	 */
	@Override
	public int doEndTag() throws javax.servlet.jsp.JspTagException {
		try {
			pageContext.getOut().write(genIndex());
		} catch (java.io.IOException e) {
			throw new JspTagException("IO Error : " + e.getMessage());
		}
		return EVAL_PAGE;
	}

    /**
     * 処理中のイメージを表示する関数。<br/>
     * @return プログレス表示関数文字列
     */
    protected String getShowProgressStr() {
    	if ("true".equalsIgnoreCase(showProgress)) {
    		return ";comShowPB();";
    	} else {
    		return "";
    	}
    }
}
