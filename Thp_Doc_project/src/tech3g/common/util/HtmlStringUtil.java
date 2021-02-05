/**
 * Title       : COUTHtmlStringUtil.java
 * Copyright   : Copyright 2004 Samsung SDS Co.,
 * Author      : je.byun
 * Date	       : 2004/06/01
 * Company     : Samsung SDS Co., Ltd.
 * Description :
 */
package tech3g.common.util;

public class HtmlStringUtil {
	public static String checkNull(String str) {
		String strTmp;
		if (str == null)
			strTmp = "";
		else
			strTmp = str;
		return strTmp;
	}

	public static String checkNull2(String str) {
		String strTmp;
		if (str == null)
			strTmp = "0";
		else
			strTmp = str;
		return strTmp;
	}

	public static String null2nbsp(String str) {
		String strTmp;
		if (str == null || str.equals(""))
			strTmp = "&nbsp;";
		else
			strTmp = str;
		return strTmp;
	}

	public static String nl2br(String comment) {
		int length = comment.length();
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < length; ++i) {
			String comp = comment.substring(i, i + 1);
			if ("\r".compareTo(comp) == 0) {
				comp = comment.substring(++i, i + 1);
				if ("\n".compareTo(comp) == 0)
					buffer.append("<BR>\r");
				else
					buffer.append("\r");
			}
			if ("\0".compareTo(comp) == 0) {
				comp = comment.substring(++i, i + 1);
				if ("\n".compareTo(comp) == 0)
					buffer.append("<BR>\r");
				else
					buffer.append("\r");
			}

			buffer.append(comp);
		}
		return buffer.toString();
	}

	public static String addslash(String comment) {
		if (comment == null) {
			return "";
		}

		int length = comment.length();
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < length; ++i) {
			String comp = comment.substring(i, i + 1);
			if ("'".compareTo(comp) == 0) {
				buffer.append("\'");
			}

			buffer.append(comp);
		}
		return buffer.toString();
	}

	public static String html2spec(String comment) {
		if (comment == null) {
			return "";
		}

		int length = comment.length();
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < length; ++i) {
			String comp = comment.substring(i, i + 1);
			if ("<".compareTo(comp) == 0)
				buffer.append("&lt;");
			else if (">".compareTo(comp) == 0)
				buffer.append("&gt;");
			else
				buffer.append(comp);
		}
		return buffer.toString();
	}

	public static String spaceadd(String addstr) {
		if (addstr == null) {
			return "";
		}

		int length = addstr.length();
		StringBuffer buffer = new StringBuffer();

		for (int k = 0; k < length; ++k) {
			String comp = addstr.substring(k, k + 1);

			if (" ".compareTo(comp) == 0) {
				buffer.append("&nbsp;");
			} else {
				buffer.append(comp);
			}
		}
		return buffer.toString();
	}

	public static String add_br(String str) {
		String output = "";
		int temp = 1;

		for (int i = 0; i < str.length(); i++, temp++) {
			output += str.charAt(i);
			if (temp % 80 == 0) {
				output += "<BR>";
				temp = 1;
			}
		}
		return output;
	}

	public static String enter(String str) {
		String output = "";
		int temp = 1;

		for (int i = 0; i < str.length(); i++, temp++) {

			if (str.charAt(i) == 10)
				output += "<BR>";
			else
				output += str.charAt(i);
		}
		return output;
	}

}