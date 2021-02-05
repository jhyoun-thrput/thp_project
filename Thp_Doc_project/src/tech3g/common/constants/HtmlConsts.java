package tech3g.common.constants;

/**
 * <pre>
 * TagConstsクラス。
 * カスタムタグ用の定数を定義するクラス。
 * </pre>
 *
 * @version 修正履歴
 *          <ul>
 *          <li>2009/05/26 : 新規作成 (revised by Noh.S.Y)</li>
 *          </ul>
 * @author Noh.S.Y
 */
public class HtmlConsts {

    // ************** CSSクラス名定義 **************
    /** スタイルクラス名：ime-mode 活性 */
    public static final String NM_CLASS_IME_ACT        = "ime_act";
    /** スタイルクラス名：ime-mode 非活性 */
    public static final String NM_CLASS_IME_DIS        = "ime_dis";
    /** スタイルクラス名：入力項目のDefaultクラス名 */
    public static final String NM_CLASS_INPUT          = "input";
    /** スタイルクラス名：入力項目のDefaultクラス名（右寄せ） */
    public static final String NM_CLASS_INPUT_R          = "input_r";
    /** スタイルクラス名：入力項目のDefaultクラス名 */
    public static final String NM_CLASS_INPUT_READONLY = "input_readonly";
    /** スタイルクラス名：入力項目のDefaultクラス名 */
    public static final String NM_CLASS_INPUT_READONLY_R = "input_readonlyr";

    /** Align：右寄せ */
    public static final String ALIGN_RIGHT = "r";
    /** Align：左寄せ */
    public static final String ALIGN_LEFT = "l";
    /** Align: 真中 */
    public static final String ALIGN_CENTER = "c";



    // ************** JavaScript関数 **************
    /** javaScript 半角変換関数 */
    public static final String FUN_CONVERT2HAN         = "convert2Han";
    /** javaScript 全角変換関数 */
    public static final String FUN_CONVERT2ZEN         = "convert2Zen";
    /** javaScript 全角ひらがな変換関数 */
    public static final String FUN_CONVERT2ZEN_HIRA         = "convert2ZenHira";
    /** javaScript 全角カナ変換関数 */
    public static final String FUN_CONVERT2ZEN_KANA         = "convert2ZenKana";

    /** javaScript 数値タイプ変換関数 */
    public static final String FUN_FMT_DECIMAL         = "formatDecimal";
    /** javaScript 数値タイプ解除関数 */
    public static final String FUN_UN_FMT_DECIMAL         = "unformatDecimal";
    /** javaScript 入力チェック関数 */
    public static final String FUN_CHK_INPUT         = "chk_onblur_input";
    /** javaScript 入力制御関数 */
    public static final String FUN_CTL_INPUT         = "ctl_onkeydown";

    /** javaScript カレンダーポップアップ画面を開く関数 */
    public static final String FUN_OPN_CAL = "openCalen";

    /** javaScript カレンダーポップアップ画面を開く関数 2*/
    public static final String INIT_CAL = "initCal";

    /** javaScript  日付をフォーマット解除する関数*/
    public static final String FUN_CONV_2_DT_UNFMT = "conv2DateOnFocus";

    /** javaScript  日付をフォーマットオンする関数*/
    public static final String FUN_CONV_2_DT_FMT = "conv2DateOnBlur";

}
