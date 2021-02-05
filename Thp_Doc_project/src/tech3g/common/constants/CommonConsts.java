package tech3g.common.constants;

/**
 * <pre>
 * CommonConstsクラス。
 * 共通定数を定義するクラス。
 * </pre>
 */
public class CommonConsts {

    /** ソートタイプ */
	public static enum SORT_TYPE {
		SORT_NONE,
		SORT_ASC,
		SORT_DESC
	}
	/** このシステムの文字Encoding */
    public static final String SYS_ENC          = "UTF-8";

	public static final String NEW_LINE = "\n";

    /** 処理が成功したときのForwardキー */
    public static final String FW_SUCCESS       = "success";
    /** 処理が失敗したときのForwardキー */
    public static final String FW_FAILURE       = "failure";
    /** 認証が失敗したときのForwardキー */
    public static final String FW_AUTHERR       = "autherr";
    /** ViewBeanのキーワード */
    public static final String VIEW_BEAN        = "viewBean";
    /** システム例外の格納キー */
    public static final String KEY_SYS_EXC      = "SYSTEM_EXCEPTION";



	// **************WHO列　初期データ区分**************
	/** 初期データ区分：オンライン */
	public static final String INIT_DATA_CL_ONLI = "O";
	/** 初期データ区分：バッチ */
	public static final String INIT_DATA_CL_BAT = "B";



    // **************ユーザーログイン情報**************
    /** *ユーザーログイン情報Sessionキー：自治体コード */
    public static final String USER_INF_ATNY_CD     = "atny_cd";
    /** *ユーザーログイン情報Sessionキー：ユーザーID */
    public static final String USER_INF_USER_ID     = "user_id";
    /** *ユーザーログイン情報Sessionキー：IP Address */
    public static final String USER_INF_HNDG_IP     = "hndg_ip";
    /** *ユーザーログイン情報Sessionキー：ユーザー名 */
    public static final String USER_INF_USER_NM     = "user_nm";
    /** *ユーザーログイン情報Sessionキー：部署ID */
    public static final String USER_INF_DEPT_ID     = "dept_id";
    /** *ユーザーログイン情報Sessionキー：部署名 */
    public static final String USER_INF_DEPT_NM     = "dept_nm";
    /** *ユーザーログイン情報Sessionキー：上位部署ID */
    public static final String USER_INF_UPP_DEPT_ID = "upp_dept_id";
    /** *ユーザーログイン情報Sessionキー：上位部署名 */
    public static final String USER_INF_UPP_DEPT_NM = "upp_dept_nm";
    /** *ユーザーログイン情報Sessionキー：職級 */
    public static final String USER_INF_POS         = "pos";
    /** *ユーザーログイン情報Sessionキー：電話番号 */
    public static final String USER_INF_TNO         = "tno";


    // **************Session格納キー**************
    /** 福祉システム用セッションのSession格納キー */
    public static final String KEY_FUKUSI_SESSION = "FukusiSession";
    /** 画面IDスタックのSession格納キー */
    public static final String KEY_PAGE_STACK     = "PageStack";
    /** 排他情報のSession格納キー */
    public static final String KEY_EXCLUSION_INFO = "ExclusionInfo";
    /** 帳票パラメータのSession格納キー */
	public static final String KEY_REPORT_PARAM   = "ReportParam";

    // **************Request格納キー**************
	/** 確認メッセージのRequest格納キー */
	public static final String KEY_REQ_CONF_MSG = "ConfirmMsg";

    // ************** 入力文字タイプ **************
    /** 入力タイプ：半角 */
    public static final String TY_HAN                  = "han";
    /** 入力タイプ：半角数字 */
    public static final String TY_HAN_SUJI             = "han_suji";
    /** 入力タイプ：半角英字 */
    public static final String TY_HAN_EIJI             = "han_eiji";
    /** 入力タイプ：半角英数 */
    public static final String TY_HAN_EISU             = "han_eisu";
    /** 入力タイプ：半角ｶﾅ */
    public static final String TY_HAN_KANA             = "han_kana";
    /** 入力タイプ：全角 */
    public static final String TY_ZEN                  = "zen";
    /** 入力タイプ：全角数字 */
    public static final String TY_ZEN_SUJI             = "zen_suji";
    /** 入力タイプ：全角英字 */
    public static final String TY_ZEN_EIJI             = "zen_eiji";
    /** 入力タイプ：全角英数 */
    public static final String TY_ZEN_EISU             = "zen_eisu";
    /** 入力タイプ：全角カナ */
    public static final String TY_ZEN_KANA             = "zen_kana";
    /** 入力タイプ：全角ひらがな */
    public static final String TY_ZEN_HIRA             = "zen_hira";
    /** 入力タイプ：数値、金額 */
    public static final String TY_DECIMAL              = "decimal";
//    /** 入力タイプ：郵便番号 */
//    public static final String TY_ZIP                  = "zip";
//    /** 入力タイプ：メールアドレス */
//    public static final String TY_MAIL                 = "mail";

    // ************** 日付フォーマット **************
    /** 和暦年度      例) 421 */
    public static final String FMT_NYY          = "nyy";
    /** 和暦年月      例) 42103*/
    public static final String FMT_NYYMM        = "nyymm";
    /** 和暦年月日    例) 4210309 */
    public static final String FMT_NYYMMDD      = "nyymmdd";
    /** 西暦年度      例) 2009 */
    public static final String FMT_YYYY         = "yyyy";
    /** 西暦年月      例) 200903*/
    public static final String FMT_YYYYMM       = "yyyymm";
    /** 西暦年月日    例) 20090309 */
    public static final String FMT_YYYYMMDD     = "yyyymmdd";
    /** 月            例) 03  */
    public static final String FMT_MM           = "mm";
    /** 日            例) 09  */
    public static final String FMT_DD           = "dd";

    /** 西暦年月日    例) 2009/03/09 */
    public static final String FMT_YYYY_MM_DD   = "yyyy_mm_dd";
    /** 西暦年月      例) 2009/03 */
    public static final String FMT_YYYY_MM      = "yyyy_mm";
    /** 和暦年月日    例) H21.03.09 */
    public static final String FMT_JYY_MM_DD    = "jyy_mm_dd";
    /** 和暦年月      例) H21.03 */
    public static final String FMT_JYY_MM       = "jyy_mm";
    /** 和暦年        例) H21 */
    public static final String FMT_JYY          = "jyy";
    /** 和暦年月日    例) 平成21年03月09日 */
    public static final String FMT_JCCYY_MM_DD  = "jccyy_mm_dd";
    /** 和暦年月      例) 平成21年03月 */
    public static final String FMT_JCCYY_MM     = "jccyy_mm";
    /** 和暦年        例) 平成21年 */
    public static final String FMT_JCCYY        = "jccyy";

    /** 西暦年月日時分秒	例) 2009/03/09 15:50:30 */
    public static final String FMT_YYYY_MM_DD_HH_MM_SS     = "yyyy_mm_dd_hh_mm_ss";
    /** 西暦年月日時分秒	例) 20090309155030 */
    public static final String FMT_YYYYMMDDHHMMSS     = "yyyymmddhhmmss";
    /** 和暦年月日時分秒	例) H21.03.09 15:50:30 */
    public static final String FMT_JYY_MM_DD_HH_MM_SS = "jyy_mm_dd_hh_mm_ss";
    /** 時分秒				例) 15:50:30 */
    public static final String FMT_HH_MM_SS = "hh_mm_ss";
    /** 時分秒				例) 155030 */
    public static final String FMT_HHMMSS = "hhmmss";
    /** 時分秒				例) 15時50分30秒 */
    public static final String FMT_JCCHH_MM_SS = "jcchh_mm_ss";


    // ************** ページング関連 **************
    /** ページのパラメータキー */
    public static final String KEY_PARAM_PAGENO ="pageNo";


    // ************** チェックボックス関連 **************
    /** チェックボックス：オン */
    public static final String CHECKBOX_ON  = "on";
    /** チェックボックス：オフ */
    public static final String CHECKBOX_OFF = "off";

    // ************** フラグ関連 **************
    /** フラグ：オン */
    public static final String FLG_ON  = "1";
    /** フラグ：オフ */
    public static final String FLG_OFF = "0";
    /** 削除区分：削除済 */
	public static final String DLT_CL_ON = "1";



    // ************** 個人ステータス関連 **************


    // ************** よく使うメッセージ **************
	/** メッセージキー：{0}処理が失敗しました */
	public static final String KEY_MSG_FAILURE = "err.CO055";
	/** メッセージキー：該当するデータが存在しない */
	public static final String KEY_MSG_NOT_FOUND_DATA  = "err.COO62";
	/** メッセージキー：検索条件を一つも入力しなかった場合 */
	public static final String KEY_MSG_NOT_EX_CONDI = "err.CO045";
	/** メッセージキー：処理が成功で終了した場合 */
	public static final String KEY_MSG_SUCCESS = "inf.CO001";


	// *************** バッチステータス ***************
	/** バッチステータス：待機中 */
	public static final String BT_STT_WAIT = "W";

	/** バッチステータス：実行中 */
	public static final String BT_STT_PROCESS = "P";

	/** バッチステータス：成功 */
	public static final String BT_STT_SUCCESS = "S";
	/** バッチステータス：失敗 */
	public static final String BT_STT_FAIL = "F";


	public static final String BT_FILE_UPLOAD = "U";

	public static final String BT_FILE_DWLOAD = "D";

}
