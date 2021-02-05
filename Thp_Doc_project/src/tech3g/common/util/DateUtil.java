package tech3g.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.SystemException;

/**
 * <pre>
 * DateUtilクラス。
 * 日付関連のUTILクラス。
 * </pre>
 */
public final class DateUtil {

    //--------------------------------------------------- 定数

    // ************** 日付を表現するための正規表現 **************
    /** 正規表現　JYY_MM_DD */
    public static final String REGEX_JYY_MM_DD = "[a-zA-Z]{1}[0-9]{2}[\\.][0-9]{2}[\\.][0-9]{2}";
    /** 正規表現　YYYYMMDD */
    public static final String REGEX_YYYYMMDD = "^[0-9]{8}$";
    /** 正規表現　YYYY_MM_DD */
    public static final String REGEX_YYYY_MM_DD = "^[0-9]{4}[\\/][0-9]{2}[\\/][0-9]{2}$";
    /** 正規表現　NYYMMDD */
    public static final String REGEX_NYYMMDD = "^[0-9]{7}$";
    /** 正規表現　JCCYY_MM_DD */
    public static final String REGEX_JCCYY_MM_DD = "^[\\W]+([0-9]{2}|元)[年][0-9]{2}[月][0-9]{2}[日]$";
    /** 正規表現　NYY */
    public static final String REGEX_NYY = "^[0-9]{3}$";
    /** 正規表現　YYYY */
    public static final String REGEX_YYYY= "^[0-9]{4}$";
    /** 正規表現　JCCYY */
    public static final String REGEX_JCCYY = "^[\\W]+([0-9]{2}|元)[年]$";
    /** 正規表現　NYYMM */
    public static final String REGEX_NYYMM = "^[0-9]{5}$";
    /** 正規表現　JYY_MM */
    public static final String REGEX_JYY_MM = "[a-zA-Z]{1}[0-9]{2}[\\.][0-9]{2}";
    /** 正規表現　YYYYMM */
    public static final String REGEX_YYYYMM = "^[0-9]{6}$";
    /** 正規表現　YYYY_MM */
    public static final String REGEX_YYYY_MM = "^[0-9]{4}[\\/][0-9]{2}$";
    /** 正規表現　JCCYY_MM */
    public static final String REGEX_JCCYY_MM = "^[\\W]+([0-9]{2}|元)[年][0-9]{2}[月]$";
    /** 正規表現　JYY */
    public static final String REGEX_JYY = "[a-zA-Z]{1}[0-9]{2}";
    /** 正規表現　JYY_MM_DD_HH_MM_SS */
    public static final String REGEX_JYY_MM_DD_HH_MM_SS = "[a-zA-Z]{1}[0-9]{2}[\\.][0-9]{2}[\\.][0-9]{2}[\\ ][0-9]{2}[\\:][0-9]{2}[\\:][0-9]{2}";
    /** 正規表現　NYYMMDDHHMMSS */
    public static final String REGEX_NYYMMDDHHMMSS = "^[0-9]{13}$";
    /** 正規表現　JCCYY_MM_DD_HH_MM_SS */
    public static final String REGEX_JCCYY_MM_DD_HH_MM_SS = "^[\\W]+([0-9]{2}|元)[年][0-9]{2}[月][0-9]{2}[日][0-9]{2}[時][0-9]{2}[分][0-9]{2}[秒]$";
    /** 正規表現　YYYYMMDDHHMMSS */
    public static final String REGEX_YYYYMMDDHHMMSS = "^[0-9]{14}$";
    /** 正規表現　YYYY_MM_DD_HH_MM_SS */
    public static final String REGEX_YYYY_MM_DD_HH_MM_SS = "^[0-9]{4}[\\/][0-9]{2}[\\/][0-9]{2}[\\ ][0-9]{2}[\\:][0-9]{2}[\\:][0-9]{2}$";
    /** 正規表現　HHMMSS */
    public static final String REGEX_HHMMSS = "^[0-9]{6}$";
    /** 正規表現　HH_MM_SS */
    public static final String REGEX_HH_MM_SS = "^[0-9]{2}[\\:][0-9]{2}[\\:][0-9]{2}$";
    /** 正規表現　JCCHH_MM_SS */
    public static final String REGEX_JCCHH_MM_SS = "^[0-9]{2}[時][0-9]{2}[分][0-9]{2}[秒]$";


	// ************** その他 **************
	/** 1日を秒(1000)で換算した数字 */
	private static final long ONE_DAY_SEC = 60 * 1000 * 60 * 24;

	/** ログ */
	private static Logger log = LogManager.getLogger(DateUtil.class);

    //--------------------------------------------------- インスタンス変数
	//--------------------------------------------------- コンストラクタ

    /**
	 * コンストラクタ<br/>
	 * このオブジェクトはインスタンス化する必要がない。
	 */
	private DateUtil()	{
	}

    // -------------------------------------------------- SetGet Methods



    //--------------------------------------------------- インスタンスメソッド


	/**
	 * 西暦(YYYYMMDD, YYYYMM, YYYY)へ変換処理。<br/>
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
	 * @return 西暦(YYYYMMDD, YYYYMM, YYYY)
	 */
	public static String conv2Seireki(String dateStr) {
        String res = null;

        if (StrUtil.isEmpty(dateStr) ) {
        	return "";
        }


        if (dateStr.matches(REGEX_JYY_MM_DD)
                || dateStr.matches(REGEX_YYYYMMDD)
                || dateStr.matches(REGEX_YYYY_MM_DD)
                || dateStr.matches(REGEX_NYYMMDD)
                || dateStr.matches(REGEX_JCCYY_MM_DD)) {
        // 年月日
            res = conv2Date(dateStr, CommonConsts.FMT_YYYYMMDD);
        } else if (dateStr.matches(REGEX_NYY)
                || dateStr.matches(REGEX_YYYY)
                || dateStr.matches(REGEX_JYY)
                || dateStr.matches(REGEX_JCCYY)) {
        // 年
            res = conv2Date(dateStr, CommonConsts.FMT_YYYY);
        } else if (dateStr.matches(REGEX_NYYMM)
                || dateStr.matches(REGEX_JYY_MM)
                || dateStr.matches(REGEX_YYYYMM)
                || dateStr.matches(REGEX_YYYY_MM)
                || dateStr.matches(REGEX_JCCYY_MM)) {
        // 年月
            res = conv2Date(dateStr, CommonConsts.FMT_YYYYMM);
        } else if (dateStr.matches(REGEX_JYY_MM_DD_HH_MM_SS)
        		|| dateStr.matches(REGEX_YYYYMMDDHHMMSS)
        		|| dateStr.matches(REGEX_YYYY_MM_DD_HH_MM_SS)
        		|| dateStr.matches(REGEX_NYYMMDDHHMMSS)
        		|| dateStr.matches(REGEX_JCCYY_MM_DD_HH_MM_SS)) {
        // 年月日時分秒
        	res = conv2DateTime(dateStr, CommonConsts.FMT_YYYYMMDDHHMMSS);
        }
        if (StrUtil.isEmpty(res)) {
            return dateStr;
        } else {
            return res;
        }
    }

    /**
     * 和暦(JYY_MM_DD, JYY_MM, JYY)へ変換処理。<br/>
     * 引数の「dateStr」が下記の日付フォーマットであれば、和暦(JYY_MM_DD)へ変換する。<br/>
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
     * @return 和暦(JYY_MM_DD, JYY_MM, JYY)
     */
    public static String conv2Wareki(String dateStr) {
        String res = null;

        if (StrUtil.isEmpty(dateStr) ) {
        	return "";
        }

        if (dateStr.matches(REGEX_JYY_MM_DD)
                || dateStr.matches(REGEX_YYYYMMDD)
                || dateStr.matches(REGEX_YYYY_MM_DD)
                || dateStr.matches(REGEX_NYYMMDD)
                || dateStr.matches(REGEX_JCCYY_MM_DD)) {
        // 年月日
            res = conv2Date(dateStr, CommonConsts.FMT_JYY_MM_DD);
        } else if (dateStr.matches(REGEX_NYY)
                || dateStr.matches(REGEX_YYYY)
                || dateStr.matches(REGEX_JYY)
                || dateStr.matches(REGEX_JCCYY)) {
        // 年
            res = conv2Date(dateStr, CommonConsts.FMT_JYY);
        } else if (dateStr.matches(REGEX_NYYMM)
                || dateStr.matches(REGEX_JYY_MM)
                || dateStr.matches(REGEX_YYYYMM)
                || dateStr.matches(REGEX_YYYY_MM)
                || dateStr.matches(REGEX_JCCYY_MM)) {
        // 年月
            res = conv2Date(dateStr, CommonConsts.FMT_JYY_MM);
        } else if (dateStr.matches(REGEX_JYY_MM_DD_HH_MM_SS)
        		|| dateStr.matches(REGEX_YYYYMMDDHHMMSS)
        		|| dateStr.matches(REGEX_YYYY_MM_DD_HH_MM_SS)
        		|| dateStr.matches(REGEX_NYYMMDDHHMMSS)
        		|| dateStr.matches(REGEX_JCCYY_MM_DD_HH_MM_SS)) {
        // 年月日時分秒
        	res = conv2DateTime(dateStr, CommonConsts.FMT_JYY_MM_DD_HH_MM_SS);
        }
        if (StrUtil.isEmpty(res)) {
            return dateStr;
        } else {
            return res;
        }
    }


	/**
	 * 日付の変換処理。<br/>
     * 指定したフォーマットへ変換処理を行う。
     * 引数の「dateStr」が下記の日付フォーマットであれば、指定したフォーマット「format」へ変換する。<br/>
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
     *       ②日付フォーマットが正しくない場合は、空白を返却する。<br/>
	 * @param dateStr 日付文字列
	 * @param format 変換結果のフォーマット
	 * @return 変換結果
	 */
	public static String conv2Date(String dateStr, String format) {

	    String s_sno = "";  // 例) 4
	    String s_yy = "";   // 例) 21
	    String s_yyyy = ""; // 例) 2009
	    String s_mm = "";   // 例) 01
	    String s_dd = "";   // 例) 31

	    String s_j = "";    // 例) H
	    String s_j_cc = ""; // 例) 平成

	    String result = ""; // 結果日付文字列

	    if (StrUtil.isEmpty(dateStr)) {
	        return "";
	    }

	    String str = StrUtil.trim(dateStr);

	    // ----------------------------------------------------------------
	    // *** 入力タイプによって計算
	    // ----------------------------------------------------------------
	    if (str.matches(REGEX_NYYMMDD) || str.matches(REGEX_NYYMM)
	            || str.matches(REGEX_NYY)) {
	        // nyy OR nyymm OR nyymmdd

	        // ----- 日付の取得
	        s_sno = str.substring(0, 1);
	        s_yy = str.substring(1, 3);


	        if (5 <= str.length()) {
	            // mmがあれば
	            s_mm = str.substring(3, 5);
	        }
	        if (7 <= str.length()) {
	            // ddがあれば
	            s_dd = str.substring(5);
	        }

	        Map mst = new HashMap();

	        // ----- SNO範囲チェック
	        if (mst == null) {
	            return "";
	            // throw "invalid_date";//  s_snoに該当する和暦マスタはありません。（s_snoが1,2,3,4...ではなくマスタに存在しない和暦の場合）
	        }

	        // ----- s_yyyy, s_j, s_j_ccの取得
	        s_yyyy = String.valueOf(Integer.parseInt((String)mst.get("from_yy")) + Integer.parseInt(s_yy) -1);

	        mst = getWarekiMap(s_yyyy, s_mm, s_dd);
			s_yy = String.valueOf(Integer.parseInt(s_yyyy) - Integer.parseInt((String)mst.get("from_yy")) + 1) ;
			s_sno = (mst.get("sno")).toString();
			s_j = (String) mst.get("jyy");
			s_j_cc =(String) mst.get("jyy_cc");

	    } else if (str.matches(REGEX_JYY_MM_DD)
	            || str.matches(REGEX_JYY)
	            || str.matches(REGEX_JYY_MM)) {
	        // jyy_mm_dd OR jyy_mm OR jyy

	        s_j = str.substring(0, 1);
            s_yy = str.substring(1, 3);

            if (6 <= str.length()) {
            // mmがあれば
                s_mm = str.substring(4, 6);
            }
            if (9 <= str.length()) {
            // ddがあれば
                s_dd = str.substring(7);
            }

            Map mst = new HashMap();

            if (mst == null) {
            	return ""; // s_jに該当する和暦マスタはありません。（s_jがH,S,M,T...ではなくマスタに存在しない和暦の場合）
            }

            s_yyyy = String.valueOf(Integer.parseInt((String) mst.get("from_yy")) + Integer.parseInt(s_yy) - 1);

	        mst = getWarekiMap(s_yyyy, s_mm, s_dd);
			s_yy = String.valueOf(Integer.parseInt(s_yyyy) - Integer.parseInt((String)mst.get("from_yy")) + 1) ;
			s_sno = (mst.get("sno")).toString();
			s_j = (String) mst.get("jyy");
			s_j_cc =(String) mst.get("jyy_cc");


	    } else if (str.matches(REGEX_YYYYMMDD) || str.matches(REGEX_YYYYMM)
	            || str.matches(REGEX_YYYY)
	            || str.matches(REGEX_YYYY_MM_DD)
	            || str.matches(REGEX_YYYY_MM)) {
	        // yyyymmdd OR yyyymm OR yyyy

	        str = StrUtil.replace(str, "/", "");

	        s_yyyy = str.substring(0, 4);

	        if (6 <= str.length()) {
	            s_mm = str.substring(4, 6);
	        }
	        if (8 <= str.length()) {
	            s_dd = str.substring(6, 8);
	        }

	        Map mst = getWarekiMap(s_yyyy, s_mm, s_dd);
	        if (mst.isEmpty()) {
	        	return "";
	        }
			s_yy = String.valueOf(Integer.parseInt(s_yyyy) - Integer.parseInt((String)mst.get("from_yy")) + 1) ;
			s_sno = (mst.get("sno")).toString();
			s_j = (String) mst.get("jyy");
			s_j_cc =(String) mst.get("jyy_cc");

	    } else if (str.matches(REGEX_JCCYY)
	            || str.matches(REGEX_JCCYY_MM)
	            || str.matches(REGEX_JCCYY_MM_DD)) {
	        // 平成21年10月12日 OR 平成21年10月 OR 平成21年

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

	        Map mstJCC = new HashMap();
            if (mstJCC == null) {
            	return ""; // s_j_ccに該当する和暦マスタはありません。（s_j_ccが平成、昭和、明治、大正...ではなくマスタに存在しない和暦の場合）
            }

	        s_yyyy = String.valueOf(Integer.parseInt((String)mstJCC.get("from_yy")) + Integer.parseInt(s_yy) -1);

	        Map mst = getWarekiMap(s_yyyy, s_mm, s_dd);
			s_yy = String.valueOf(Integer.parseInt(s_yyyy) - Integer.parseInt((String)mst.get("from_yy")) + 1) ;
			s_sno = (mst.get("sno")).toString();
			s_j = (String) mst.get("jyy");
			s_j_cc =(String) mst.get("jyy_cc");

	    } else {
	        return ""; // (入力形式が間違っている)
	    }

	    // ----------------------------------------------------------------
	    // *** 日付チェック
	    // ----------------------------------------------------------------
	    boolean isOk = false;
	    if (!StrUtil.isEmpty(s_mm) && !StrUtil.isEmpty(s_dd)) {
	        isOk = isYYYYMMDD(StrUtil.concat(s_yyyy, s_mm, s_dd));

	        // 閏年エラーの無視処理 -- 情報政策課の要望 Start
	        if (!isOk) {
	        	if ("[0-9]{4}0229".matches(StrUtil.concat(s_yyyy, s_mm, s_dd))) {
	        		isOk = true;
	        		log.warn("★該当年度はうるう年です★(" + StrUtil.concat(s_yyyy, s_mm, s_dd) + ")");
	        	}
	        }
	        // 閏年エラーの無視処理 -- 情報政策課の要望 Start

	    } else if (!StrUtil.isEmpty(s_mm) && StrUtil.isEmpty(s_dd)) {
	        isOk = isYYYYMM(StrUtil.concat(s_yyyy, s_mm));
	    } else {
	        isOk = isYYYY(s_yyyy);
	    }

	    if (!isOk) {
	        return ""; // 日付ではないです。
	    }

	    // ----------------------------------------------------------------
	    // *** 返却タイプによって返却値の決定
	    // ----------------------------------------------------------------
	    if (CommonConsts.FMT_NYY.equals(format)) {
	        // 421
	        if (StrUtil.isEmpty(s_sno) || StrUtil.isEmpty(s_yy)) {
	            return ""; // format設定エラー
	        } else {
	            result = StrUtil.concat(s_sno, StrUtil.fillZero(s_yy, 2));
	        }
	    } else if (CommonConsts.FMT_NYYMM.equals(format)) {
	        // 42110
	        if (StrUtil.isEmpty(s_sno)  || StrUtil.isEmpty(s_yy) || StrUtil.isEmpty(s_mm)) {
	            return ""; // format設定エラー
	        } else {
	            result = StrUtil.concat(s_sno, StrUtil.fillZero(s_yy, 2), s_mm);
	        }
	    } else if (CommonConsts.FMT_NYYMMDD.equals(format)) {
	        // 4211022
	        if (StrUtil.isEmpty(s_sno) || StrUtil.isEmpty(s_yy) || StrUtil.isEmpty(s_mm) || StrUtil.isEmpty(s_dd)) {
	            return ""; // format設定エラー
	        } else {
	            result = StrUtil.concat(s_sno, StrUtil.fillZero(s_yy, 2), s_mm, s_dd);
	        }
	    } else if (CommonConsts.FMT_YYYY.equals(format)) {
	        // 2009
	        if (StrUtil.isEmpty(s_yyyy)) {
	            return ""; // format設定エラー
	        } else {
	            result = s_yyyy;
	        }
	    } else if (CommonConsts.FMT_YYYYMMDD.equals(format)) {
	        // 20090101
	        if (StrUtil.isEmpty(s_yyyy)  || StrUtil.isEmpty(s_mm) || StrUtil.isEmpty(s_dd)) {
	            return ""; // format設定エラー
	        } else {
	            result = StrUtil.concat(s_yyyy, s_mm, s_dd);
	        }

	    } else if (CommonConsts.FMT_MM.equals(format)) {
	        // 10
	        if (StrUtil.isEmpty(s_mm)) {
	            return ""; // format設定エラー
	        } else {
	            result = s_mm;
	        }
	    } else if (CommonConsts.FMT_DD.equals(format)) {
	        // 22
	        if (StrUtil.isEmpty(s_dd)) {
	            return ""; // format設定エラー
	        } else {
	            result = s_dd;
	        }
	    } else if (CommonConsts.FMT_YYYYMM.equals(format)) {
	        // 200910
	        if (StrUtil.isEmpty(s_yyyy) || StrUtil.isEmpty(s_mm)) {
	            return ""; // format設定エラー
	        } else {
	            result = StrUtil.concat(s_yyyy, s_mm);
	        }
	    } else if (CommonConsts.FMT_YYYY_MM_DD.equals(format)) {
	        // 2009/10/22
	        if (StrUtil.isEmpty(s_yyyy) || StrUtil.isEmpty(s_mm) || StrUtil.isEmpty(s_dd)) {
	            return ""; // format設定エラー
	        } else {
	            result = StrUtil.concat(s_yyyy, "/",  s_mm, "/", s_dd);
	        }
	    } else if (CommonConsts.FMT_YYYY_MM.equals(format)) {
	        // 2009/10
	        if (StrUtil.isEmpty(s_yyyy) || StrUtil.isEmpty(s_mm)) {
	            return ""; // format設定エラー
	        } else {
	            result = StrUtil.concat(s_yyyy, "/",  s_mm);
	        }
	    } else if (CommonConsts.FMT_JYY_MM_DD.equals(format)) {
	        // H21.10.22
	        if (StrUtil.isEmpty(s_j) || StrUtil.isEmpty(s_yy) || StrUtil.isEmpty(s_mm)||  StrUtil.isEmpty(s_dd) ) {
	            return ""; // format設定エラー
	        } else {
	            result = StrUtil.concat(s_j, StrUtil.fillZero(s_yy, 2) ,  ".", s_mm, ".", s_dd);
	        }
	    } else if (CommonConsts.FMT_JYY_MM.equals(format) ) {
	        // H21.10
	        if (StrUtil.isEmpty(s_j)|| StrUtil.isEmpty(s_yy)  || StrUtil.isEmpty(s_mm)) {
	            return ""; // format設定エラー
	        } else {
	            result = StrUtil.concat(s_j, StrUtil.fillZero(s_yy, 2) ,  ".", s_mm);
	        }
	    } else if (CommonConsts.FMT_JYY.equals(format)) {
	        // H21
	        if (StrUtil.isEmpty(s_j) || StrUtil.isEmpty(s_yy)) {
	            return ""; // format設定エラー
	        } else {
	            result = StrUtil.concat(s_j, StrUtil.fillZero(s_yy, 2));
	        }

	    } else if (CommonConsts.FMT_JCCYY_MM_DD.equals(format)) {
	        // 平成21年10月22日
	        if (StrUtil.isEmpty(s_j_cc) || StrUtil.isEmpty(s_yy) || StrUtil.isEmpty(s_mm) || StrUtil.isEmpty(s_dd)) {
	            return ""; // format設定エラー
	        } else {
				if ("01".equals(StrUtil.fillZero(s_yy, 2))) {
					result = StrUtil.concat(s_j_cc,  "元", "年",s_mm,  "月", s_dd, "日");
				} else {
					result = StrUtil.concat(s_j_cc, StrUtil.fillZero(s_yy, 2), "年",s_mm,  "月", s_dd, "日");
				}
	        }
	    } else if (CommonConsts.FMT_JCCYY_MM.equals(format)) {
	        // 平成21年10月
	        if (StrUtil.isEmpty(s_j_cc) || StrUtil.isEmpty(s_yy) || StrUtil.isEmpty(s_mm)) {
	            return ""; // format設定エラー
	        } else {
				if ("01".equals(StrUtil.fillZero(s_yy, 2))) {
					result = s_j_cc + "元" + "年" + s_mm + "月";
				} else {
		            result = s_j_cc + StrUtil.fillZero(s_yy, 2) + "年" + s_mm + "月";
				}
	        }
	    } else if (CommonConsts.FMT_JCCYY.equals(format)) {
	        // 平成21年
	        if (StrUtil.isEmpty(s_j_cc) || StrUtil.isEmpty(s_yy)) {
	            return ""; // format設定エラー
	        } else {
				if ("01".equals(StrUtil.fillZero(s_yy, 2))) {
					result = s_j_cc + "元" + "年";
				} else {
					result = s_j_cc + StrUtil.fillZero(s_yy, 2) + "年";
				}
	        }
	    } else {
	        return "";// format設定エラー
	    }

	    // --------- 返却処理
	    return result;
	}

	/**
	 * 日付の変換処理。<br/>
     * 指定したフォーマットへ変換処理を行う。
     * 但し、年、月、日が1桁の場合は、半角スペースで2桁Paddingし返却する。
     *
     * 引数の「format」が下記の場合のみ使用できる。下記以外はシステムエラーになる。<br/>
     * <ul>
     *  <li> jccyy_mm_dd      例) "平成 1年 2月 3日"</li>
     *  <li> jccyy_mm         例) "平成 1年 2月"</li>
     *  <li> jccyy            例) "平成 1年"</li>
     *  <li> yyyy_mm_dd       例) "1988/ 2/ 3"</li>
     *  <li> yyyy_mm          例) "1988/ 2"</li>
     *  <li> jyy_mm_dd        例) "H 1. 2. 3"</li>
     *  <li> jyy_mm           例) "H 1. 2"</li>
     *  <li> jyy              例) "H 1"</li>
     *  <li> mm               例) " 2"</li>
     *  <li> dd               例) " 3"</li>
     * </ul>
     * <br/>
     * 但し、①nullの場合はブランクを返却する。<br/>
     *       ②日付フォーマットが正しくない場合は、そのままを返却する。<br/>
	 * @param dateStr 日付文字列
	 * @param format 変換結果のフォーマット
	 * @return 変換結果
	 */
	public static String conv2DateZero2Space(String dateStr, String format) {
        String res = null;

        if (StrUtil.isEmpty(dateStr) ) {
        	return "";
        }

        String tmpDateStr = conv2Date(dateStr, format);

        String jcc = "";
        String j = "";
        String y = "";
        String m = "";
        String d = "";

        if (CommonConsts.FMT_JCCYY_MM_DD.equals(format)) {
        // 平成21年10月22日

			if (tmpDateStr.indexOf("元年") >= 0) {
				jcc = tmpDateStr.substring(0, tmpDateStr.indexOf("元年"));
		        y = "1";
			} else {
				jcc = tmpDateStr.substring(0, tmpDateStr.indexOf("年")-2);
		        y = String.valueOf(
		        		Integer.parseInt(tmpDateStr.substring(tmpDateStr.indexOf("年")-2, tmpDateStr.indexOf("年")))
		        	);
	        }

	        if (tmpDateStr.indexOf("月") > -1) {
	            m = String.valueOf(
		        		Integer.parseInt(tmpDateStr.substring(tmpDateStr.indexOf("月")-2, tmpDateStr.indexOf("月")))
	        		);
	        }
	        if (tmpDateStr.indexOf("日") > -1) {
	            d = String.valueOf(
		        		Integer.parseInt(tmpDateStr.substring(tmpDateStr.indexOf("日")-2, tmpDateStr.indexOf("日")))
	        	);
	        }
	        res = jcc + StrUtil.lpad(y, 2, ' ') + "年" + StrUtil.lpad(m, 2, ' ') + "月" + StrUtil.lpad(d, 2, ' ') + "日";

        } else if (CommonConsts.FMT_JCCYY_MM.equals(format)) {
	    // 平成21年10月
			if (tmpDateStr.indexOf("元年") >= 0) {
				jcc = tmpDateStr.substring(0, tmpDateStr.indexOf("元年"));
		        y = "1";
			} else {
				jcc = tmpDateStr.substring(0, tmpDateStr.indexOf("年")-2);
		        y = String.valueOf(
		        		Integer.parseInt(tmpDateStr.substring(tmpDateStr.indexOf("年")-2, tmpDateStr.indexOf("年")))
		        	);
	        }

	        if (tmpDateStr.indexOf("月") > -1) {
	            m = String.valueOf(
		        		Integer.parseInt(tmpDateStr.substring(tmpDateStr.indexOf("月")-2, tmpDateStr.indexOf("月")))
	        		);
	        }
	        res = jcc + StrUtil.lpad(y, 2, ' ') + "年" + StrUtil.lpad(m, 2, ' ') + "月";

		} else if (CommonConsts.FMT_JCCYY.equals(format)) {
	    // 平成21年
			if (tmpDateStr.indexOf("元年") >= 0) {
				jcc = tmpDateStr.substring(0, tmpDateStr.indexOf("元年"));
		        y = "1";
			} else {
				jcc = tmpDateStr.substring(0, tmpDateStr.indexOf("年")-2);
		        y = String.valueOf(
		        		Integer.parseInt(tmpDateStr.substring(tmpDateStr.indexOf("年")-2, tmpDateStr.indexOf("年")))
		        	);
	        }
	        res = jcc + StrUtil.lpad(y, 2, ' ') + "年";
	    } else if (CommonConsts.FMT_YYYY_MM_DD.equals(format)) {
	    // 2009/10/22
	    	String[] ymdArray = tmpDateStr.split("/");
	    	y = ymdArray[0];
	    	m = String.valueOf(Integer.parseInt(ymdArray[1]));
	    	d = String.valueOf(Integer.parseInt(ymdArray[2]));
	    	res = y + "/" + StrUtil.lpad(m, 2, ' ')  + "/" + StrUtil.lpad(d, 2, ' ');
	    } else if (CommonConsts.FMT_YYYY_MM.equals(format)) {
	    // 2009/10
	    	String[] ymdArray = tmpDateStr.split("/");
	    	y = ymdArray[0];
	    	m = String.valueOf(Integer.parseInt(ymdArray[1]));
	    	res = y + "/" + StrUtil.lpad(m, 2, ' ');
	    } else if (CommonConsts.FMT_JYY_MM_DD.equals(format)) {
	    // H21.10.22
	    	String[] ymdArray = tmpDateStr.split("[.]");
	    	j = ymdArray[0].substring(0, 1);
	    	y = String.valueOf(Integer.parseInt(ymdArray[0].substring(1)));
	    	m = String.valueOf(Integer.parseInt(ymdArray[1]));
	    	d = String.valueOf(Integer.parseInt(ymdArray[2]));
	    	res = j + StrUtil.lpad(y, 2, ' ') + "." + StrUtil.lpad(m, 2, ' ')  + "." + StrUtil.lpad(d, 2, ' ');
	    } else if (CommonConsts.FMT_JYY_MM.equals(format) ) {
	    // H21.10
	    	String[] ymdArray = tmpDateStr.split("[.]");
	    	j = ymdArray[0].substring(0, 1);
	    	y = String.valueOf(Integer.parseInt(ymdArray[0].substring(1)));
	    	m = String.valueOf(Integer.parseInt(ymdArray[1]));
	    	res = j + StrUtil.lpad(y, 2, ' ') + "." + StrUtil.lpad(m, 2, ' ');
	    } else if (CommonConsts.FMT_JYY.equals(format)) {
	    // H21
	    	j = tmpDateStr.substring(0, 1);
	    	y = String.valueOf(Integer.parseInt(tmpDateStr.substring(1)));
	    	res = j + StrUtil.lpad(y, 2, ' ') ;
        } else if (CommonConsts.FMT_MM.equals(format)) {
	    // 10
        	res = StrUtil.lpad(String.valueOf(Integer.parseInt(tmpDateStr)), 2, ' ') ;
	    } else if (CommonConsts.FMT_DD.equals(format)) {
	    // 22
	    	res = StrUtil.lpad(String.valueOf(Integer.parseInt(tmpDateStr)), 2, ' ') ;
	    } else {
	    	log.error(" 指定したタイプは変換できません。(dateFormat:" + format + ")");
	        throw new SystemException("指定したタイプは変換できません。(dateFormat:" + format + ")");
	    }

        if (StrUtil.isEmpty(res)) {
            return dateStr;
        } else {
            return res;
        }
	}

	/**
	 * 日付の変換処理。<br/>
     * 指定したフォーマットへ変換処理を行う。
     * 但し、年、月、日が1桁の場合は、Paddingしない。
     *
     * 引数の「format」が下記の場合のみ使用できる。下記以外はシステムエラーになる。<br/>
     * <ul>
     *  <li> jccyy_mm_dd      例) "平成1年2月3日"</li>
     *  <li> jccyy_mm         例) "平成1年2月"</li>
     *  <li> jccyy            例) "平成1年"</li>
     *  <li> yyyy_mm_dd       例) "1988/2/3"</li>
     *  <li> yyyy_mm          例) "1988/2"</li>
     *  <li> jyy_mm_dd        例) "H1.2.3"</li>
     *  <li> jyy_mm           例) "H1.2"</li>
     *  <li> jyy              例) "H1"</li>
     *  <li> mm               例) "2"</li>
     *  <li> dd               例) "3"</li>
     * </ul>
     * <br/>
     * 但し、①nullの場合はブランクを返却する。<br/>
     *       ②日付フォーマットが正しくない場合は、そのままを返却する。<br/>
	 * @param dateStr 日付文字列
	 * @param format 変換結果のフォーマット
	 * @return 変換結果
	 */
	public static String conv2DateZero2Blank(String dateStr, String format) {
        String res = null;

        if (StrUtil.isEmpty(dateStr) ) {
        	return "";
        }

        String tmpDateStr = conv2Date(dateStr, format);

        String jcc = "";
        String j = "";
        String y = "";
        String m = "";
        String d = "";

        if (CommonConsts.FMT_JCCYY_MM_DD.equals(format)) {
        // 平成21年10月22日

			if (tmpDateStr.indexOf("元年") >= 0) {
				jcc = tmpDateStr.substring(0, tmpDateStr.indexOf("元年"));
		        y = "1";
			} else {
				jcc = tmpDateStr.substring(0, tmpDateStr.indexOf("年")-2);
		        y = String.valueOf(
		        		Integer.parseInt(tmpDateStr.substring(tmpDateStr.indexOf("年")-2, tmpDateStr.indexOf("年")))
		        	);
	        }

	        if (tmpDateStr.indexOf("月") > -1) {
	            m = String.valueOf(
		        		Integer.parseInt(tmpDateStr.substring(tmpDateStr.indexOf("月")-2, tmpDateStr.indexOf("月")))
	        		);
	        }
	        if (tmpDateStr.indexOf("日") > -1) {
	            d = String.valueOf(
		        		Integer.parseInt(tmpDateStr.substring(tmpDateStr.indexOf("日")-2, tmpDateStr.indexOf("日")))
	        	);
	        }
	        res = jcc + y + "年" + m + "月" + d + "日";

        } else if (CommonConsts.FMT_JCCYY_MM.equals(format)) {
	    // 平成21年10月
			if (tmpDateStr.indexOf("元年") >= 0) {
				jcc = tmpDateStr.substring(0, tmpDateStr.indexOf("元年"));
		        y = "1";
			} else {
				jcc = tmpDateStr.substring(0, tmpDateStr.indexOf("年")-2);
		        y = String.valueOf(
		        		Integer.parseInt(tmpDateStr.substring(tmpDateStr.indexOf("年")-2, tmpDateStr.indexOf("年")))
		        	);
	        }

	        if (tmpDateStr.indexOf("月") > -1) {
	            m = String.valueOf(
		        		Integer.parseInt(tmpDateStr.substring(tmpDateStr.indexOf("月")-2, tmpDateStr.indexOf("月")))
	        		);
	        }
	        res = jcc + y + "年" + m + "月";

		} else if (CommonConsts.FMT_JCCYY.equals(format)) {
	    // 平成21年
			if (tmpDateStr.indexOf("元年") >= 0) {
				jcc = tmpDateStr.substring(0, tmpDateStr.indexOf("元年"));
		        y = "1";
			} else {
				jcc = tmpDateStr.substring(0, tmpDateStr.indexOf("年")-2);
		        y = String.valueOf(
		        		Integer.parseInt(tmpDateStr.substring(tmpDateStr.indexOf("年")-2, tmpDateStr.indexOf("年")))
		        	);
	        }
	        res = jcc + y + "年";
	    } else if (CommonConsts.FMT_YYYY_MM_DD.equals(format)) {
	    // 2009/10/22
	    	String[] ymdArray = tmpDateStr.split("/");
	    	y = ymdArray[0];
	    	m = String.valueOf(Integer.parseInt(ymdArray[1]));
	    	d = String.valueOf(Integer.parseInt(ymdArray[2]));
	    	res = y + "/" + m + "/" + d;
	    } else if (CommonConsts.FMT_YYYY_MM.equals(format)) {
	    // 2009/10
	    	String[] ymdArray = tmpDateStr.split("/");
	    	y = ymdArray[0];
	    	m = String.valueOf(Integer.parseInt(ymdArray[1]));
	    	res = y + "/" + m;
	    } else if (CommonConsts.FMT_JYY_MM_DD.equals(format)) {
	    // H21.10.22
	    	String[] ymdArray = tmpDateStr.split("[.]");
	    	j = ymdArray[0].substring(0, 1);
	    	y = String.valueOf(Integer.parseInt(ymdArray[0].substring(1)));
	    	m = String.valueOf(Integer.parseInt(ymdArray[1]));
	    	d = String.valueOf(Integer.parseInt(ymdArray[2]));
	    	res = j + y + "." + m  + "." + d;
	    } else if (CommonConsts.FMT_JYY_MM.equals(format) ) {
	    // H21.10
	    	String[] ymdArray = tmpDateStr.split("[.]");
	    	j = ymdArray[0].substring(0, 1);
	    	y = String.valueOf(Integer.parseInt(ymdArray[0].substring(1)));
	    	m = String.valueOf(Integer.parseInt(ymdArray[1]));
	    	res = j + y + "." + m;
	    } else if (CommonConsts.FMT_JYY.equals(format)) {
	    // H21
	    	j = tmpDateStr.substring(0, 1);
	    	y = String.valueOf(Integer.parseInt(tmpDateStr.substring(1)));
	    	res = j + y;
        } else if (CommonConsts.FMT_MM.equals(format)) {
	    // 10
        	res = String.valueOf(Integer.parseInt(tmpDateStr)) ;
	    } else if (CommonConsts.FMT_DD.equals(format)) {
	    // 22
	    	res = String.valueOf(Integer.parseInt(tmpDateStr));
	    } else {
	    	log.error(" 指定したタイプは変換できません。(dateFormat:" + format + ")");
	        throw new SystemException("指定したタイプは変換できません。(dateFormat:" + format + ")");
	    }

        if (StrUtil.isEmpty(res)) {
            return dateStr;
        } else {
            return res;
        }
	}




	/**
	 * 該当日付の和暦マスタ取得<br/>
	 * @param yyyy 西暦年度
	 * @param mm 月(2桁)
	 * @param dd 日(2桁)
	 * @return  該当日付の和暦マスタ
	 */
	private static Map getWarekiMap(String yyyy, String mm, String dd) {

		Map mstSNO = new HashMap();

        Iterator it = mstSNO.values().iterator();
        Map mst = null;
        while (it.hasNext()) {
            mst = (Map)it.next();
            if (!StrUtil.isEmpty(yyyy) && !StrUtil.isEmpty(mm) && !StrUtil.isEmpty(dd)) {
                if (Integer.parseInt((String)mst.get("from_dt"))   <= Integer.parseInt(yyyy + mm + dd)
                        && Integer.parseInt((String)mst.get("to_dt"))   >= Integer.parseInt(yyyy + mm + dd)) {

                    return mst;

                }
            } else if (!StrUtil.isEmpty(yyyy) && !StrUtil.isEmpty(mm)) {
                if (Integer.parseInt((String)mst.get("from_yymm"))   <= Integer.parseInt(yyyy + mm)
                        && Integer.parseInt((String)mst.get("to_yymm"))   >= Integer.parseInt(yyyy + mm)) {
                    return mst;
                }
            } else if (!StrUtil.isEmpty(yyyy)) {
                if (Integer.parseInt((String)mst.get("from_yy"))   <= Integer.parseInt(yyyy)
                        && Integer.parseInt((String)mst.get("to_yy"))   >= Integer.parseInt(yyyy)) {
                    return mst;
                }
            }
        }
        return new ListOrderedMap();
	}





	/**
	 * 日時の変換処理。<br/>
     * 指定したフォーマットへ変換処理を行う。
     * 引数の「dateStr」が下記の日付フォーマットであれば、指定したフォーマット「format」へ変換する。<br/>
     * <ul>
     *  <li> jyy_mm_dd_hh_mm_ss			例) H21.03.09 15:50:20</li>
     *  <li> nyymmddhhmmss				例) 4210309155020</li>
     *  <li> jccyy_mm_dd_hh_mm_ss		例) 平成21年03月09日15時50分20秒</li>
     *  <li> yyyymmddhhmmss				例) 20090309155020</li>
     *  <li> yyyy_mm_dd_hh_mm_ss		例) 2009/03/09 15:50:20</li>
     * </ul>
     * <br/>
     * 但し、①nullの場合は空白を返却する。<br/>
     *       ②日付フォーマットが正しくない場合は、空白を返却する。<br/>
	 * @param dateStr	日時文字列
	 * @param format	変換結果フォーマット
	 * @return 変換結果
	 */
	public static String conv2DateTime(String dateStr, String format) {

		String space  = "";		// 空白
	    String ymd    = "";		// 結果（年月日）
	    String hms    = "";		// 結果（時分秒）
	    String result = "";		// 結果日付文字列

	    // ------ 空の場合は空白を返却
	    if (StrUtil.isEmpty(dateStr)) {
	        return "";
	    }

	    // ------ 両端の空白を除去
	    String str = StrUtil.trim(dateStr);

	    // ------ 入力タイプによって計算
	    if (str.matches(REGEX_YYYYMMDDHHMMSS) || str.matches(REGEX_YYYY_MM_DD_HH_MM_SS)) {

	    	// ------ "/", ":", " "が含まれる場合は除去
	    	str = StrUtil.removeAll(str, "/: ");

	    	// ------ 各項目へ分割
	    	ymd = str.substring(0, 8);						// 年月日（例：20090309）
	    	hms = str.substring(8);							// 時分秒（例：155020）

	    } else if (str.matches(REGEX_JYY_MM_DD_HH_MM_SS)) {

	    	// ------ 各項目へ分割
	    	ymd = str.substring(0, 9);						// 年月日（例：H21.03.09）
	    	hms = str.substring(10);						// 時分秒（例：15:50:20）

	    } else if (str.matches(REGEX_NYYMMDDHHMMSS)) {

	    	// ------ 各項目へ分割
	    	ymd = str.substring(0, 7);						// 年月日（例：4210309）
	    	hms = str.substring(7);							// 時分秒（例：155020）

	    } else if (str.matches(REGEX_JCCYY_MM_DD_HH_MM_SS)) {

	    	// ------ 各項目へ分割
	    	ymd = str.substring(0, str.indexOf("日") + 1);	// 年月日（例：平成21年03月09日）
	    	hms = str.substring(str.indexOf("日") + 1);		// 時分秒（例：15時50分20秒）

	    } else {
	    	return "";		// (入力形式が間違っている)
	    }

	    // 西暦（yyyymmddhhmmss）変換
	    if (CommonConsts.FMT_YYYYMMDDHHMMSS.equals(format)) {

	    	// ------ 年月日の変換
	    	ymd = conv2Date(ymd, CommonConsts.FMT_YYYYMMDD);

	    	// ------ 時分秒の変換
	    	hms = conv2Time(hms, CommonConsts.FMT_HHMMSS);


	    } else if (CommonConsts.FMT_YYYY_MM_DD_HH_MM_SS.equals(format)) {

	    	// ------ 年月日の変換
	    	ymd = conv2Date(ymd, CommonConsts.FMT_YYYY_MM_DD);

		    // ------ 時分秒の変換
		    hms = conv2Time(hms, CommonConsts.FMT_HH_MM_SS);

		    // ------ 空白の設定
		    space = " ";

	    // 和暦（jyy_mm_dd_hh_mm_ss）変換
	    } else if (CommonConsts.FMT_JYY_MM_DD_HH_MM_SS.equals(format)) {

	    	// ------ 年月日の変換
	    	ymd = conv2Date(ymd, CommonConsts.FMT_JYY_MM_DD);

		    // ------ 時分秒の変換
		    hms = conv2Time(hms, CommonConsts.FMT_HH_MM_SS);

		    // ------ 空白の設定
		    space = " ";
	    }

	    // ------ 変換結果の設定
	    if (!StrUtil.isEmpty(ymd) && !StrUtil.isEmpty(hms)) {
	    	result = StrUtil.concat(ymd, space, hms);
	    }

		return result;
	}

	/**
	 * 時間の変換処理。<br/>
     * 指定したフォーマットへ変換処理を行う。
     * 引数の「timeStr」が下記の時間フォーマットであれば、指定したフォーマット「format」へ変換する。<br/>
     * <ul>
     *  <li> hhmmss			例) 155030</li>
     *  <li> jcchh_mm_ss	例) 15時50分30秒</li>
     *  <li> hh_mm_ss		例) 15:50:30</li>
     * </ul>
     * <br/>
     * 但し、①nullの場合は空白を返却する。<br/>
     *       ②時間フォーマットが正しくない場合は、空白を返却する。<br/>
	 * @param timeStr	時間文字列
	 * @param format	変換結果フォーマット
	 * @return 変換結果
	 */
	public static String conv2Time(String timeStr, String format) {

		String s_hh = "";		// 時
		String s_mm = "";		// 分
		String s_ss = "";		// 秒

		String result = "";		// 変換結果

	    // ------ 空の場合は空白を返却
	    if (StrUtil.isEmpty(timeStr)) {
	        return "";
	    }

	    // ------ 両端の空白を除去
	    String str = StrUtil.trim(timeStr);

	    // ------ 区切り文字の除去
	    if (str.matches(REGEX_HHMMSS) || str.matches(REGEX_HH_MM_SS)) {

	    	// ------ ":"が含まれる場合は除去
	    	str = StrUtil.removeAll(str, ":");

	    } else if (str.matches(REGEX_JCCHH_MM_SS)) {

	    	// ------ "時", "分", "秒"を除去
	    	str = StrUtil.removeAll(str, "時分秒");

	    }

	    // ------ 桁数チェック
	    if (str.matches(REGEX_HHMMSS) || str.matches(REGEX_HH_MM_SS) || str.matches(REGEX_JCCHH_MM_SS)) {

	    	// ------ 6桁(HHMMSS)チェック
	    	if (str.length() != 6) {
	    		return "";
	    	}

	    }

    	// ------ 各項目(時分秒)を設定
    	s_hh = str.length() >= 2 ? str.substring(0, 2) : "";
    	s_mm = str.length() >= 4 ? str.substring(2, 4) : "";
    	s_ss = str.length() == 6 ? str.substring(4) : "";

	    // ------ 時間妥当性チェック
	    if (str.matches(REGEX_HHMMSS) || str.matches(REGEX_HH_MM_SS) || str.matches(REGEX_JCCHH_MM_SS)) {
	    	if (!isHHMMSS(StrUtil.concat(s_hh, s_mm, s_ss))) {
	    		return "";
	    	}
	    }

	    // ------ 変換結果設定
	    if (CommonConsts.FMT_HH_MM_SS.equals(format)) {

	    	result = StrUtil.concat(StrUtil.fillZero(s_hh, 2), ":", StrUtil.fillZero(s_mm, 2), ":", StrUtil.fillZero(s_ss, 2));

	    } else if (CommonConsts.FMT_HHMMSS.equals(format)) {

	    	result = StrUtil.concat(StrUtil.fillZero(s_hh, 2), StrUtil.fillZero(s_mm, 2), StrUtil.fillZero(s_ss, 2));

	    } else if (CommonConsts.FMT_JCCHH_MM_SS.equals(format)) {

	    	result = StrUtil.concat(StrUtil.fillZero(s_hh, 2), "時", StrUtil.fillZero(s_mm, 2), "分", StrUtil.fillZero(s_ss, 2), "秒");

	    }

		return result;
	}


//	 ------------------------------------------  現在の日付、時間取得関連 Start


    /**
     * システム日付の取得。<br/>
     * システム日付を「yyyyMMdd」フォーマットで返却する。<br/>
     * @return システム日付(yyyyMMdd)
     */
    public static String getToday() {
    	SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
        return SDF_YYYYMMDD.format(new Date());
    }

    /**
     * システム日付を和暦で取得。<br/>
     * システム日付を「jyy_mm_dd」(H21.03.09)フォーマットで返却する。<br/>
     * @return システム日付(jyy_mm_dd)
     */
    public static String getTodayWareki() {
        return conv2Wareki(getToday());
    }

    /**
     * システム日付「MM」の取得。<br/>
     * システム月を「MM」(01～12)西暦で返却する。<br/>
     * @return システム月(MM)
     */
    public static String getThisMonth() {
    	SimpleDateFormat SDF_MM = new SimpleDateFormat("MM");
        return SDF_MM.format(new Date());
    }

    /**
     * システム日付「yyyy」の取得。<br/>
     * システム年を「yyyy」(2009)西暦で返却する。<br/>
     * @return システム年(yyyy)
     */
    public static String getThisYear() {
    	SimpleDateFormat SDF_YYYY = new SimpleDateFormat("yyyy");
        return SDF_YYYY.format(new Date());
    }

    /**
     * システム日付「jyy(和暦)」の取得。<br/>
     * システム年を「jyy」(例：H21)和暦で返却する。<br/>
     * @return システム年(jyy)
     */
    public static String getThisYearWareki() {
        return conv2Wareki(getThisYear());
    }

    /**
     * システム日付の"年度"「Fjyy(和暦)」を取得。<br/>
     * システム"年度"を「Fjyy」(例：H21)和暦で返却する。<br/>
     * 例）H22年04月→H22、H22年03月→H21
     *
     * @return システム年度(Fjyy)
     */
    public static String getThisFiscalYearWareki() {
    	if (3 < Integer.parseInt(getThisMonth())){
    		return conv2Wareki(getThisYear());
    	} else {
    		return conv2Wareki(getPreviousYear(getThisYear()));
    	}
    }

    /**
     * システム日付の"年度"「Fyy(西暦)」を取得。<br/>
     * システム"年度"を「Fyyyy」(例：2009)西暦で返却する。<br/>
     * 例）2010年04月→2010、2010年03月→2009
     *
     * @return システム年度(Fjyy)
     */
    public static String getThisFiscalYearSeireki() {
    	if (3 < Integer.parseInt(getThisMonth())){
    		return conv2Seireki(getThisYear());
    	} else {
    		return conv2Seireki(getPreviousYear(getThisYear()));
    	}
    }

    /**
     * システム日付「yyyyMM」の取得。<br/>
     * システム年月を「yyyyMM」(200906)西暦で返却する。<br/>
     * @return システム年月(yyyyMM)
     */
    public static String getThisYearMonth() {
    	SimpleDateFormat SDF_YYYYMM = new SimpleDateFormat("yyyyMM");
    	return SDF_YYYYMM.format(new Date());
    }

    /**
     * システム日付「jyy_mm(和暦)」の取得。<br/>
     * システム年月を「jyy.mm」(H21.06)和暦で返却する。<br/>
     * @return システム年月(yyyyMM)
     */
    public static String getThisYearMonthWareki() {
        return conv2Wareki(getThisYearMonth());
    }

	/**
	 * 現在のシステム時間「yyyyMMddhhmmss」の取得。<br/>
	 * システム時間を「yyyyMMddhhmmss」フォーマットで返却する。<br/>
	 * @return システム時間(yyyyMMddhhmmss)
	 */
	public static String getCurrentTimeStr() {
		SimpleDateFormat SDF_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
		return SDF_YYYYMMDDHHMMSS.format(new Date());
	}

	/**
	 * 現在のシステム時間「yyyy/MM/dd hh:mm:ss」の取得。<br/>
     * システム時間を「yyyy/MM/dd hh:mm:ss」フォーマットで返却する。<br/>
     * @return システム時間(yyyy/MM/dd hh:mm:ss)
     */
    public static String getCurrentTimeStr2() {
    	SimpleDateFormat SDF_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return SDF_YYYY_MM_DD_HH_MM_SS.format(new Date());
    }


// ------------------------------------------  現在の日付、時間取得関連 End

	/**
     * Date形式から日付文字列(YYYYMMDD)を取得<br/>
     * Dateを「yyyyMMdd」フォーマットの文字列で返却する。<br/>
     * @return yyyyMMdd文字列
     */
    public static String getYYYYMMDD(Date inDate) {
    	if (inDate == null){
    		return null;
    	}
    	SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
        return SDF_YYYYMMDD.format(inDate);
    }

    /**
     * 日付(YYYYMMDD)のチェック。<br/>
     * 日付(YYYYMMDD)のチェックを行います。<BR>
     * 正しい日付文字列であればTRUEを返却する。<BR>
     * @param  yyyymmdd 日付(YYYYMMDD)<BR>
     * @return boolean  チェック結果
     */
    public static boolean isYYYYMMDD(String yyyymmdd) {
        if (yyyymmdd == null || yyyymmdd.length() != 8) {
            return false;
        }

        Calendar cal = new GregorianCalendar();

        cal.setLenient(false);
        cal.set(
            Integer.parseInt(yyyymmdd.substring(0, 4)),
            Integer.parseInt(yyyymmdd.substring(4, 6)) - 1,
            Integer.parseInt(yyyymmdd.substring(6, 8)));
        try {
            Date dt = cal.getTime();
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * 日付(YYYYMM)のチェック。<br/>
     * 日付(YYYYMM)のチェックを行います。<BR>
     * 正しい日付文字列であればTRUEを返却する。<BR>
     * @param  yyyymm 日付(YYYYMM)<BR>
     * @return boolean  チェック結果
     */
    public static boolean isYYYYMM(String yyyymm) {
    	if (yyyymm == null || yyyymm.length() != 6) {
    		return false;
    	}

    	Calendar cal = new GregorianCalendar();

    	cal.setLenient(false);
    	cal.set(
    			Integer.parseInt(yyyymm.substring(0, 4)),
    			Integer.parseInt(yyyymm.substring(4, 6)) - 1,
    			1);
    	try {
    		Date dt = cal.getTime();
    	} catch (IllegalArgumentException e) {
    		return false;
    	}
    	return true;
    }


    /**
     * 日付(YYYY)のチェック。<br/>
     * 日付(YYYY)のチェックを行います。<BR>
     * 正しい日付文字列であればTRUEを返却する。<BR>
     * @param  yyyy 日付(YYYY)<BR>
     * @return boolean  チェック結果
     */
    public static boolean isYYYY(String yyyy) {
    	if (yyyy == null || yyyy.length() != 4) {
    		return false;
    	}

    	try {
        	Calendar cal = new GregorianCalendar();
        	cal.setLenient(false);
        	cal.set(Integer.parseInt(yyyy),
        			0,
        			1);
    		Date dt = cal.getTime();
    	} catch (Exception e) {
    		return false;
    	}
    	return true;
    }

    /**
     * 和暦日付(JYY_MM_DD)のチェック。<br/>
     * 和暦タイプJYY_MM_DDであるかチェックする。<br/>
     * ※参考）JYY_MM_DD : 例）H21.03.09
     * @param str チェック対象文字列
     * @return 正しい和暦日付であればtrueを返却
     */
    public static boolean isJYY_MM_DD (String str) {
    	if (str.matches(REGEX_JYY_MM_DD)) {
        // jyy_mm_dd

            String s_j = str.substring(0, 1);
            String s_yy = str.substring(1, 3);
            String s_mm = str.substring(4, 6);
            String s_dd = str.substring(7);

            Map mst = new HashMap();
            String s_yyyy = String.valueOf(Integer.parseInt((String) mst.get("from_yy"))
                    + Integer.parseInt(s_yy) - 1);

            return isYYYYMMDD(StrUtil.concat(s_yyyy, s_mm, s_dd));
    	} else {
    		return false;
    	}
    }

    /**
     * 和暦日付（JYY_MM）のチェック<br/>
     * 和暦タイプJYY_MMであるかチェックする。<br/>
     * ※参考）JYY_MM : 例）H21.03
     * @param str チェック対象文字列
     * @return 正しい和暦日付であればtrueを返却
     */
    public static boolean isJYY_MM (String str) {
    	if (str.matches(REGEX_JYY_MM)) {
    		// jyy_mm

    		String s_j = str.substring(0, 1);
    		String s_yy = str.substring(1, 3);
    		String s_mm = str.substring(4, 6);

    		Map mst = new HashMap();
    		String s_yyyy = String.valueOf(Integer.parseInt((String) mst.get("from_yy"))
    				+ Integer.parseInt(s_yy) - 1);

    		return isYYYYMM(StrUtil.concat(s_yyyy, s_mm));
    	} else {
    		return false;
    	}
    }

    /**
     * 和暦日付(JYY)のチェック。<br/>
     * 和暦タイプJYYであるかチェックする。<br/>
     * ※参考）JYY_MM : 例）H21
     * @param str チェック対象文字列
     * @return 正しい和暦日付であればtrueを返却
     */
    public static boolean isJYY (String str) {
    	if (str.matches(REGEX_JYY)) {
    		// jyy_mm

    		String s_j = str.substring(0, 1);
    		String s_yy = str.substring(1, 3);

    		Map mst = new HashMap();
    		String s_yyyy = String.valueOf(Integer.parseInt((String) mst.get("from_yy"))
    				+ Integer.parseInt(s_yy) - 1);
    		return isYYYY(s_yyyy);
    	} else {
    		return false;
    	}
    }

    /**
     * 時間(HHMMSS)の妥当性チェック<br/>
     * 正常な時間形式であればtrue、異常であればfalseを返す。<br/>
     * @param str チェック対象文字列
     * @return 正常：true、異常：false
     */
    public static boolean isHHMMSS(String str) {
    	if (str == null || str.length() != 6) {
    		return false;
    	}

    	String s_hh = str.substring(0, 2);		// 時
    	String s_mm = str.substring(2, 4);		// 分
    	String s_ss = str.substring(4);			// 秒

        Calendar cal = new GregorianCalendar();

        cal.setLenient(false);
        cal.set(1970, 1, 1, Integer.parseInt(s_hh), Integer.parseInt(s_mm), Integer.parseInt(s_ss));

        try {
            Date dt = cal.getTime();
        } catch (IllegalArgumentException e) {
            return false;
        }

    	return true;
    }

    /**
     * 日付の演算処理。<br/>
     * 指定された日付に指定された日数をプラスして西暦で返却する<br/>
     * 指定した日付が正しい日付ではない場合は””ブランクを返却する。<br/>
	 * 指定可能な日付フォーマットは下記のとおりである。<br/>
     * <ul>
     *  <li> jyy_mm_dd        例) H21.03.09</li>
     *  <li> nyymmdd          例) 4210309</li>
     *  <li> jccyy_mm_dd  例) 平成21年03月09日</li>
     *  <li> yyyymmdd         例) 20090309</li>
     *  <li> yyyy_mm_dd       例) 2009/03/09</li>
     * </ul>
     * 例↓↓↓
     * <pre>
     * <code>
     *     // 例）20090629の3日後の日付を計算する場合。
     *     String date1 = DateUtil.addDayWareki("H21.06.29", 3);
     *
     *     // 例）20090629の3日前の日付を計算する場合。
     *     String date2 = DateUtil.addDayWareki("20090309", -3);
     * </code>
     * </pre>
     * <br/>
     * 結果：①date1 ： "20090702" <br/>
     * 　　　②date2 ： "20090626" <br/>
     * @param date 日付文字列(年月日)
     * @param day プラスする日数
     * @return 指定された日数をプラスして西暦（YYYYMMDD）の日付
     */
    public static String addDay (String date, int day) {
    	// --- 西暦(YYYYMMDD)へ変換
    	String seirekiDT = conv2Seireki(date);

    	// --- 指定した日数をプラスする
    	try {
    		SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
			Date dt = SDF_YYYYMMDD.parse(seirekiDT);
			dt.setTime(dt.getTime() + (ONE_DAY_SEC * day));
			return SDF_YYYYMMDD.format(dt);
		} catch (ParseException e) {
			return "";
		}
    }

    /**
     * 日付の演算処理(和暦)。<br/>
     * 指定された日付に指定された日数をプラスして和暦(例：H21.10.11)で返却する<br/>
     * 指定した日付が正しい日付ではない場合は””ブランクを返却する。<br/>
	 * 指定可能な日付フォーマットは下記のとおりである。<br/>
     * <ul>
     *  <li> jyy_mm_dd        例) H21.03.09</li>
     *  <li> nyymmdd          例) 4210309</li>
     *  <li> jccyy_mm_dd  例) 平成21年03月09日</li>
     *  <li> yyyymmdd         例) 20090309</li>
     *  <li> yyyy_mm_dd       例) 2009/03/09</li>
     * </ul>
     * <br/>
     * 例↓↓↓
     * <pre>
     * <code>
     *     // 例）20090629の3日後の日付を計算する場合。
     *     String date1 = DateUtil.addDayWareki("H21.06.29", 3);
     *
     *     // 例）20090629の3日前の日付を計算する場合。
     *     String date2 = DateUtil.addDayWareki("20090309", -3);
     *
     * </code>
     * </pre>
     * <br/>
     * 結果：①date1 ： "H21.07.02" <br/>
     * 　　　②date2 ： "H21.06.26" <br/>
     *
     * @param date 日付文字列(年月日)
     * @param day プラスする日数
     * @return 指定された日数をプラスして和暦(例：H21.10.11)の日付
     */
    public static String addDayWareki (String date, int day) {
    	// --- 西暦(YYYYMMDD)へ変換
    	String seirekiDT = conv2Seireki(date);

    	// --- 指定した日数をプラスする
    	try {
    		SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
    		Date dt = SDF_YYYYMMDD.parse(seirekiDT);
    		dt.setTime(dt.getTime() + (ONE_DAY_SEC * day));
    		return conv2Wareki(SDF_YYYYMMDD.format(dt));
    	} catch (ParseException e) {
    		return "";
    	}
    }

	/**
	 * 指定した日付の週末（土日）であるかチェック<br/>
	 * 指定した日付（西暦YYYYMMDD）が週末（土日）であるかチェックする。<br/>
	 * @param yyyyMMdd 西暦(YYYYMMDD)
	 * @return true | false
	 */
	public static boolean isWeekend(String yyyyMMdd) {

		int week = getDayOfWeek(yyyyMMdd);

		if (Calendar.SUNDAY ==  week
				|| Calendar.SATURDAY == week) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * 曜日判定<br/>
	 *
	 * 指定した日付の曜日を返却する。
	 * 注意）指定した日付が正しくなければ-1を返却。
	 *
	 * @param yyyyMMdd
	 * @return Calendarクラスの曜日
	 * <ul>
	 *    <li>日曜日 : <code>Calendar.SUNDAY</code></li>
	 *    <li>月曜日 : <code>Calendar.MONDAY</code></li>
	 *    <li>火曜日 : <code>Calendar.TUESDAY</code></li>
	 *    <li>水曜日 : <code>Calendar.WEDNESDAY</code></li>
	 *    <li>木曜日 : <code>Calendar.THURSDAY</code></li>
	 *    <li>金曜日 : <code>Calendar.FRIDAY</code></li>
	 *    <li>土曜日 : <code>Calendar.SATURDAY</code></li>
	 * </ul>
	 */
	public static int getDayOfWeek(String yyyyMMdd) {
    	try {
    		SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
    		Date dt = SDF_YYYYMMDD.parse(yyyyMMdd);
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(dt);

    		return cal.get(Calendar.DAY_OF_WEEK);
    	} catch (ParseException e) {
    		return -1;
    	}
	}



	/**
	 * 誕生日から年齢取得<br/>
	 * 誕生日（西暦、和暦）から年齢を計算し返却する。<br/>
	 * 注意）誕生日前日に1歳あがる。（行政）<br/>
	 * @param birthday 誕生日（西暦、和暦）
	 * @return 年齢
	 */
	public static String getAge(String birthday) {

		String birthday_b1 = conv2Seireki(birthday);

		if (!isYYYYMMDD(birthday_b1)) {
			return "";
		}

		// ---- 誕生日1日前
		birthday_b1 = getPreviousDay(birthday_b1);

		String bYear = birthday_b1.substring(0, 4);
		String bMonth = birthday_b1.substring(4, 6);
		String bDay = birthday_b1.substring(6, 8);

		Calendar c = Calendar.getInstance();
		c.setTime( new Date());

		int yyyy = c.get(Calendar.YEAR);
		String mm = StrUtil.lpad(String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0');
		String dd = StrUtil.lpad(String.valueOf(c.get(Calendar.DATE)), 2, '0');


		int age = yyyy - Integer.parseInt(bYear);

		if ((StrUtil.concat(bMonth, bDay)).compareTo(StrUtil.concat(mm, dd)) > 0) {
			age--;
		}

		return String.valueOf(age);
	}

	/**
	 * 基準日からの年齢取得<br/>
	 * 基準日時点の年齢を計算し返却する。<br/>
	 * 注意）誕生日前日に1歳あがる。（行政）<br/>
	 *
	 * @param birthday 誕生日（西暦、和暦）
	 * @param pointDay 時点、基準日
	 * @return 年齢
	 */
	public static String getAge(String birthday, String pointDay) {

		String birthday_b1 = conv2Seireki(birthday);
		String pointDaySeireki = conv2Seireki(pointDay);

		if (!isYYYYMMDD(birthday_b1)) {
			return "";
		}
		if (!isYYYYMMDD(pointDaySeireki)) {
			return "";
		}

		// ---- 誕生日1日前
		birthday_b1 = getPreviousDay(birthday_b1);

		String bYear = birthday_b1.substring(0, 4);
		String bMonth = birthday_b1.substring(4, 6);
		String bDay = birthday_b1.substring(6, 8);

		Calendar c = Calendar.getInstance();
		c.setTime(getDate(pointDaySeireki));

		int yyyy = c.get(Calendar.YEAR);
		String mm = StrUtil.lpad(String.valueOf(c.get(Calendar.MONTH) + 1), 2, '0');
		String dd = StrUtil.lpad(String.valueOf(c.get(Calendar.DATE)), 2, '0');


		int age = yyyy - Integer.parseInt(bYear);

		if ((StrUtil.concat(bMonth, bDay)).compareTo(StrUtil.concat(mm, dd)) > 0) {
			age--;
		}

		return String.valueOf(age);
	}


	/**
	 * 指定した日付の前日取得<br/>
	 * 指定した日付の前日を取得する。<br/>
	 * @param yyyymmdd 西暦YYYYMMDD
	 * @return 指定した日付の前日（西暦）
	 */
	public static String getPreviousDay(String yyyymmdd) {

		if (!isYYYYMMDD(yyyymmdd)) {
			return "";
		}

		SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
		Date today = null;
		try {
			today = SDF_YYYYMMDD.parse(yyyymmdd);
			today.setTime(today.getTime() - ONE_DAY_SEC);
		} catch (ParseException e) {
			throw new SystemException("日付変換中エラー", e);
		}

		return SDF_YYYYMMDD.format(today);
	}


	/**
	 * 指定した日付の翌日取得<br/>
	 * 指定した日付の翌日を取得する。<br/>
	 * @param yyyymmdd 西暦YYYYMMDD
	 * @return 指定した日付の翌日（西暦）
	 */
	public static String getNextDay(String yyyymmdd) {

		if (!isYYYYMMDD(yyyymmdd)) {
			return "";
		}

		Date today = null;
		SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
		try {
			today = SDF_YYYYMMDD.parse(yyyymmdd);
			today.setTime(today.getTime() + ONE_DAY_SEC);
		} catch (ParseException e) {
			throw new SystemException("日付変換中エラー", e);
		}

		return SDF_YYYYMMDD.format(today);
	}

	/**
	 * 指定した年月(YYYYMMDD)の翌月取得<br/>
	 * 指定した年月の翌月を取得する。<br/>
	 * @param yyyymm 西暦YYYYMM
	 * @return 指定した年月のの翌月（西暦）
	 */
	public static String getNextMonth(String yyyymm) {

		if (!isYYYYMM(yyyymm)) {
			return "";
		}

		Date targetMonth = null;
		try {
			SimpleDateFormat SDF_YYYYMM = new SimpleDateFormat("yyyyMM");
			targetMonth = SDF_YYYYMM.parse(yyyymm);
			Calendar cal = Calendar.getInstance();
			cal.setTime(targetMonth);
			cal.add(Calendar.MONTH, 1);
			return SDF_YYYYMM.format(cal.getTime());
		} catch (ParseException e) {
			throw new SystemException("日付変換中エラー", e);
		}
	}

	/**
	 * 指定日付の翌年(YYYY)を西暦で取得。
	 * <br/>
	 * @param dateStr 指定年(西暦or和暦)
	 * @return 翌年(YYYY)
	 */
	public static String getNextYear(String dateStr) {

		String year = DateUtil.conv2Date(dateStr, CommonConsts.FMT_YYYY);

		if (StrUtil.isNotEmpty(year)) {
			year = String.valueOf(Integer.parseInt(year) + 1);
		}

		return year;
	}

	public static String getDate() {

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		java.util.Date currentdate = new java.util.Date();

		return formatter.format(currentdate);
	}

	public static String geCurrentDateStr2() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date currentdate = new java.util.Date();
		return formatter.format(currentdate);
	}

	/**
	 * 指定日付の前年(YYYY)を西暦で取得。
	 * <br/>
	 * @param dateStr 指定年(西暦or和暦)
	 * @return 前年(YYYY)
	 */
	public static String getPreviousYear(String dateStr) {

		String year = DateUtil.conv2Date(dateStr, CommonConsts.FMT_YYYY);

		if (StrUtil.isNotEmpty(year)) {
			year = String.valueOf(Integer.parseInt(year) - 1);
		} else {
			throw new SystemException("日付が正しくありません。(" + dateStr + ")");
		}

		return year;
	}

	/**
	 * 指定した年月(YYYYMMDD)の先月取得<br/>
	 * 指定した年月の先月を取得する。<br/>
	 * @param yyyymm 西暦YYYYMM
	 * @return 指定した年月のの先月（西暦）
	 */
	public static String getPreviousMonth(String yyyymm) {

		if (!isYYYYMM(yyyymm)) {
			return "";
		}

		Date targetMonth = null;
		try {
			SimpleDateFormat SDF_YYYYMM = new SimpleDateFormat("yyyyMM");
			targetMonth = SDF_YYYYMM.parse(yyyymm);
			Calendar cal = Calendar.getInstance();
			cal.setTime(targetMonth);
			cal.add(Calendar.MONTH, -1);
			return SDF_YYYYMM.format(cal.getTime());
		} catch (ParseException e) {
			throw new SystemException("日付変換中エラー", e);
		}
	}

	/**
	 * YYYYMMDDからDateクラスに変換<br/>
	 * 指定した日付をDateに変換して返却する。<br/>
	 * @param yyyymmdd 西暦YYYYMMDD
	 * @return Date
	 */
	public static Date getDate(String yyyymmdd) {

		if (!isYYYYMMDD(yyyymmdd)) {
			return null;
		}

		Date today = null;
		SimpleDateFormat SDF_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
		try {
			today = SDF_YYYYMMDD.parse(yyyymmdd);
		} catch (ParseException e) {
			throw new SystemException("日付変換中エラー", e);
		}

		return today;
	}

	/**
	 * 日付の順番チェック<br/>
	 * 日付１が日付２より前、若しくは同じ日付であるチェックする。<br/>
	 * <br/>
	 * 日付１ <=  日付２ : true<br/>
	 * 日付１　=  日付２ : true<br/>
	 * 日付１　>  日付２ : false<br/>
	 *
	 * @param date1 日付１ 年月日(和暦 or 西暦)
	 * @param date2 日付２ 年月日(和暦 or 西暦)
	 * @return true | false
	 */
	public static boolean validationDateSeq(String date1, String date2) {

		if (StrUtil.isEmpty(date1) || StrUtil.isEmpty(date2)) {
			return false;
		}

		String dateYYYYMMDD1 = conv2Seireki(date1);
		String dateYYYYMMDD2 = conv2Seireki(date2);

		if (StrUtil.isEmpty(dateYYYYMMDD1) || StrUtil.isEmpty(dateYYYYMMDD2)) {
			return false;
		}

		if (dateYYYYMMDD1.compareTo(dateYYYYMMDD2) <= 0) {
			return true;
		} else {
			return false;
		}
	}


    /**
     * 未来日チェック<br/>
     *
     * <pre>
     * システム日付より指定した日付が未来日であればtrueを返却
     * 未来日ではなければfalseを返却する。
     *
     * 注意）正しくない日付を指定した場合、システム例外にする。
     * </pre>
     * @param dateStr 和暦又は西暦の年月日
     * @return 判定結果
     */
    public static boolean isFuture(String dateStr) {
    	String date = conv2Seireki(dateStr);
    	if (isYYYYMMDD(date)) {
    		if (Integer.parseInt(getToday()) < Integer.parseInt(date)) {
    			return true;
    		} else {
    			return false;
    		}
//    	} else if (isYYYYMM(date)) {
//    		if (Integer.parseInt(getThisYearMonth()) < Integer.parseInt(date)) {
//    			return true;
//    		} else {
//    			return false;
//    		}
//    	} else if (isYYYY(date)) {
//    		if (Integer.parseInt(getThisYear()) < Integer.parseInt(date)) {
//    			return true;
//    		} else {
//    			return false;
//    		}
    	} else {
    		throw new SystemException("指定した日付は正しくありません。("+ dateStr + ")");
    	}
    }

    /**
     * 過去日チェック<br/>
     *
     * <pre>
     * システム日付より指定した日付が過去日であればtrueを返却
     * 過去日ではなければfalseを返却する。
     *
     * 注意）正しくない日付を指定した場合、システム例外にする。
     * </pre>
     * @param dateStr 和暦又は西暦の年月日
     * @return 判定結果
     */
    public static boolean isPast(String dateStr) {
    	String date = conv2Seireki(dateStr);
    	if (isYYYYMMDD(date)) {
    		if (Integer.parseInt(getToday()) > Integer.parseInt(date)) {
    			return true;
    		} else {
    			return false;
    		}
//    	} else if (isYYYYMM(date)) {
//    		if (Integer.parseInt(getThisYearMonth()) > Integer.parseInt(date)) {
//    			return true;
//    		} else {
//    			return false;
//    		}
//    	} else if (isYYYY(date)) {
//    		if (Integer.parseInt(getThisYear()) > Integer.parseInt(date)) {
//    			return true;
//    		} else {
//    			return false;
//    		}
    	} else {
    		throw new SystemException("指定した日付は正しくありません。("+ dateStr + ")");
    	}
    }

    /**
     * 現在日チェック<br/>
     * <pre>
     * 指定した日付がシステム日付と同じであればtrue
     * 未来日又は過去日であればfalseを返却
     * </pre>
     *
     * 注意）正しくない日付を指定した場合、システム例外にする。
     *
     * @param dateStr 和暦又は西暦の年月日
     * @return 判定結果
     */
    public static boolean isToday(String dateStr) {
    	String date = conv2Seireki(dateStr);
    	if (isYYYYMMDD(date)) {
    		if (getToday().equals(date)) {
    			return true;
    		} else {
    			return false;
    		}
    	} else {
    		throw new SystemException("指定した日付は正しくありません。("+ dateStr + ")");
    	}
    }

	/**
	 * 該当年度の開始日付を取得<br/>
	 * 指定した年度の開始日(例：YYYY0401)を返却する。<br/>
	 * @param yearStr 指定年(西暦or和暦)
	 * @return 年度開始日(YYYYMMDD)
	 */
	public static String getFiscalYearStartDate(String yearStr) {

		String year = DateUtil.conv2Date(yearStr, CommonConsts.FMT_YYYY);

		if (StrUtil.isEmpty(year)) {
			throw new SystemException("日付が正しくありません。(" + yearStr + ")");
		} else{
			return year + "0401";
		}
	}

	/**
	 * 該当年度の最終日付を取得<br/>
	 * 指定した年度の最終日(例：YYYY0331)を返却する。<br/>
	 * @param yearStr 指定年(西暦or和暦)
	 * @return 年度開始日(YYYYMMDD)
	 */
	public static String getFiscalYearEndDate(String yearStr) {

		String year = DateUtil.conv2Date(yearStr, CommonConsts.FMT_YYYY);

		if (StrUtil.isEmpty(year)) {
			throw new SystemException("日付が正しくありません。(" + yearStr + ")");
		} else{

			return getNextYear(yearStr) + "0331";
		}
	}

	/**
	 * 該当年度の開始年月を取得<br/>
	 * 指定した年度の開始年月(例：YYYY04)を返却する。<br/>
	 * @param yearStr 指定年(西暦or和暦)
	 * @return 年度開始年月(YYYYMM)
	 */
	public static String getFiscalYearStartMonth(String yearStr) {

		String year = DateUtil.conv2Date(yearStr, CommonConsts.FMT_YYYY);

		if (StrUtil.isEmpty(year)) {
			throw new SystemException("日付が正しくありません。(" + yearStr + ")");
		} else{
			return year + "04";
		}
	}

	/**
	 * 該当年度の最終年月を取得<br/>
	 * 指定した年度の最終年月(例：YYYY03)を返却する。<br/>
	 * @param yearStr 指定年(西暦or和暦)
	 * @return 年度開始年月(YYYYMM)
	 */
	public static String getFiscalYearEndMonth(String yearStr) {

		String year = DateUtil.conv2Date(yearStr, CommonConsts.FMT_YYYY);

		if (StrUtil.isEmpty(year)) {
			throw new SystemException("日付が正しくありません。(" + yearStr + ")");
		} else{
			return getNextYear(yearStr) + "03";
		}
	}

	/**
	 * 月の最終日取得<br/>
	 * 月の最終日を取得する<br/>
	 * @param yearMonth 年月（西暦又は和暦）
	 * @return 月の最終日
	 */
	public static String getLastDate(String yearMonth) {

		String yyyyMM = DateUtil.conv2Date(yearMonth, CommonConsts.FMT_YYYYMM);

		if (StrUtil.isEmpty(yyyyMM)) {
			throw new SystemException("日付が正しくありません。(" + yearMonth + ")");
		}

		Calendar cal = Calendar.getInstance();

		//Calendarに年と月をセット
		cal.set(Calendar.YEAR, Integer.parseInt(yyyyMM.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.parseInt(yyyyMM.substring(4)) - 1);

		//月の日数を取得
		int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		return yyyyMM + StrUtil.lpad(String.valueOf(lastDate), 2, '0');
	}



	/**
	 * 月の数を取得<br/>
	 * 「開始年月」から「終了年月」までの月の数を計算し返却する。
	 * @param startYearMonth 開始年月
	 * @param endYearMonth 終了年月
	 * @return 月の数
	 */
	public static int getNumberOfMohth(String startYearMonth, String endYearMonth) {

		String start = DateUtil.conv2Date(startYearMonth, CommonConsts.FMT_YYYYMM);
		String end = DateUtil.conv2Date(endYearMonth, CommonConsts.FMT_YYYYMM);

		if (StrUtil.isEmpty(start) || StrUtil.isEmpty(end) ) {
			throw new SystemException("日付が正しくありません。(start:" + startYearMonth + ", end:" + endYearMonth +  ")");
		}

		if (!validationDateSeq(start + "01", end + "01")) {
			throw new SystemException("日付の開始年月が終了年月より後。(start:" + startYearMonth + ", end:" + endYearMonth +  ")");
		}

		int startYear = Integer.parseInt(start.substring(0, 4));
		int startMonth =  Integer.parseInt(start.substring(4));
		int endYear =  Integer.parseInt(end.substring(0, 4));
		int endMonth =  Integer.parseInt(end.substring(4));

		return (((endYear - startYear) * 12) + (endMonth - startMonth)) + 1;
	}

}
