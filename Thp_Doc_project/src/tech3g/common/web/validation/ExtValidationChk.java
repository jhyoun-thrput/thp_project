package tech3g.common.web.validation;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.FieldChecks;
import org.apache.struts.validator.Resources;

import tech3g.common.constants.CommonConsts;
import tech3g.common.exceptions.SystemException;
import tech3g.common.util.DateUtil;
import tech3g.common.util.ExceptionUtil;
import tech3g.common.util.StrUtil;
import tech3g.common.util.ValidationUtil;

/**
 * <pre>
 * ExtValidationChkクラス。
 * Struts基本バリデータ以外のバリデーションチェックをため、<br/>
 * <code>org.apache.struts.validator.FieldChecks</code>を継承し、<br/>
 * 拡張バリデータメソッドを実装するクラス。<br/>
 * <b>実際のチェックロジックは<code>liveany.common.util.ValidationUtil</code></b>に委譲する。
 * </pre>
 *
 */
public class ExtValidationChk extends FieldChecks {


	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** log */
	private final static Log log = LogFactory.getLog(ExtValidationChk.class);

	/** 例外キャラクタのパラメータ名 */
	private final static String PARAM_CHA_EXT = "chaExt";

	/** 数値（小数点）のフォーマットのパラメータ名 */
	private final static String PARAM_DEC_FMT = "decFmt";

	/**
	 * 半角ｶﾅのみであるかチェック。<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。
	 *
	 * 注意) 例外文字を除去してからチェックする
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateHankakuKana(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isHankakuKanaOnly(chkStr)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}
		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}

				if (!ValidationUtil.isHankakuKanaOnly(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}

	/**
	 * 半角ｶﾅ、半角スペースのみであるかチェック。<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateHankakuKanaSpace(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!(ValidationUtil.isHankakuKanaAlphaSpace(chkStr))) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}
		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!(ValidationUtil.isHankakuKanaAlphaSpace(StrUtil.removeAll(value[i], chaExt)))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}

	/**
	 * 半角ｶﾅを除く半角のみであるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateAscii(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {


			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isAscii(chkStr)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isAscii(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}

	/**
	 * 半角数字のみであるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateNumber(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isNum(chkStr)) {

				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isNum(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}

	/**
	 * 数値および、大文字のアルファベットのみであるかチェック。<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateNumberAlphabet(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isNumberAlphabet(chkStr)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isNumberAlphabet(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 数値および、大文字のアルファベットのみであるかチェック。<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateNumberUpper(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isNumberUpper(chkStr)) {
				actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());
			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isNumberUpper(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 数値および、小文字のアルファベットのみであるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateNumberLower(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isNumberLower(chkStr)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());
			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);
			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isNumberLower(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction, field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 大文字または小文字のアルファベットのみであるかチェック。<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateAlphabet(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isAlphabet(chkStr)) {
				actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());
			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isAlphabet(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;

	}
	/**
	 * 大文字のアルファベットのみであるかチェック。<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateUpperCharacters(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isUpperCharacters(chkStr)) {
				actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isUpperCharacters(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 小文字のアルファベットのみであるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateLowerCharacters(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isLowerChacracters(chkStr)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isLowerChacracters(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 正の数値のみであるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validatePositive(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isPositive((String) chkObj)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isPositive(value[i])) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 1以下の小数のみであるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validatePercent(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isPercent((String) chkObj)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isPercent(value[i])) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 小数点の桁数が指定された桁数以上であるかチェック。<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateMinScale(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isMinScale((String) chkObj, Integer.parseInt(field
					.getVarValue("minscale")))) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isMinScale(value[i], Integer.parseInt(field
						.getVarValue("minscale")))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 小数点の桁数が指定された桁数以下であるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateMaxScale(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isMaxScale((String) chkObj, Integer.parseInt(field
					.getVarValue("maxscale")))) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isMaxScale(value[i], Integer.parseInt(field
						.getVarValue("maxscale")))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 全角のみであるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateZenkaku(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isZenkaku(chkStr)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isZenkaku(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 全角数字のみであるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateZenNumber(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isZenNumber(chkStr)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isZenNumber(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}

	/**
	 * 全角英字のみであるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateZenAlphabet(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isZenAlphabet(chkStr)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isZenAlphabet(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}

	/**
	 * 全角英字のみであるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateZenNumAlphabet(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isZenNumAlphabet(chkStr)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isZenNumAlphabet(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}



	/**
	 * 全角カナのみであるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateZenKana(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isZenKana(chkStr)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isZenKana(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}





	/**
	 * 全角ひらがなのみであるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateZenHira(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isZenHira(chkStr)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isZenHira(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}



	/**
	 * 数値タイプであるかチェック<br/>
	 * 数字の中で","も許す。<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateDecimal(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			// ----- Decimalチェック
			if (!ValidationUtil.isDecimalType(chkStr)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isDecimalType(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 数値タイプであるかチェック<br/>
	 * 数字の中で","も許す。<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateDecimalFormat(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String decFmt = field.getVarValue(PARAM_DEC_FMT); // DecimalFormat


		if (StrUtil.isEmpty(decFmt) || !decFmt.matches("[0-9]*[\\.][0-9]*")) {
			throw new SystemException("decFmt指定が間違っています。");
		}


		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// ----- フォーマットチェック
			if (ValidationUtil.isOkDecimalFormat((String)chkObj, decFmt)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}

			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isOkDecimalFormat(value[i], decFmt)) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 指定された日付がYYYYMMDDの形式であるかチェックを行う。
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateYYYYMMDD(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null)
			return true;

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isYYYYMMDD(DateUtil.conv2Seireki((String) chkObj))) {
				actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isYYYYMMDD(DateUtil.conv2Seireki(value[i]))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}

	/**
	 * 指定された日付がYYYYMMの形式であるかチェックを行う。
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateYYYYMM(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null)
			return true;

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isYYYYMM(DateUtil.conv2Seireki((String) chkObj))) {
				actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isYYYYMM(DateUtil.conv2Seireki(value[i]))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}

	/**
	 * 指定された日付がYYYYMMの形式であるかチェックを行う。
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateYYYY(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null)
			return true;

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isYYYY((String) chkObj)) {
				actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isYYYY(DateUtil.conv2Seireki(value[i]))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}




	/**
	 * 指定された日付が和暦(年月日)の形式であるかチェックを行う。
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateJYYMMDD(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null)
			return true;

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isJYYMMDD(DateUtil.conv2Wareki((String)chkObj))) {

				actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isJYYMMDD(DateUtil.conv2Wareki(value[i]))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 指定された日付が和暦(年月)の形式であるかチェックを行う。
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateJYYMM(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null)
			return true;

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isJYYMM(DateUtil.conv2Wareki((String)chkObj))) {
				actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isJYYMM(DateUtil.conv2Wareki(value[i]))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 指定された日付が和暦(年)の形式であるかチェックを行う。
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateJYY(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null)
			return true;

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isJYY(DateUtil.conv2Wareki((String)chkObj))) {
				actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());
			if (temp == null) {
				return true;
			}

			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isJYY(DateUtil.conv2Wareki(value[i]))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}

	/**
	 * "1"または"0"であるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateFlagType(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {


		if (chkObj == null)
			return true;

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isFlagType((String) chkObj)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isFlagType(value[i])) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * 指定されたByte数のみであるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateSpecifiedLength(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null)
			return true;

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isSpecifiedLength((String) chkObj, Integer.parseInt(field
					.getVarValue("full")))) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isSpecifiedLength(value[i], Integer.parseInt(field
						.getVarValue("full")))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}





	/**
	 * 半角ｶﾅを含むすべての半角を入力可能。<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * 注意) 例外文字を除去してからチェックする。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateHankaku(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

		String chaExt = field.getVarValue(PARAM_CHA_EXT); // チェック例外文字


		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {

			// 例外キャラクタを削除
			String chkStr = StrUtil.removeAll((String) chkObj, chaExt);

			if (!ValidationUtil.isHankaku(chkStr)) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isHankaku(StrUtil.removeAll(value[i], chaExt))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * バイト数が指定された値以上であるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateMinBytes(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null)
			return true;

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isMinBytes((String) chkObj, Integer.parseInt(field
					.getVarValue("minbytes")))) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isMinBytes(value[i], Integer.parseInt(field
						.getVarValue("minbytes")))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


	/**
	 * バイト数が指定された値以下であるかチェック<br/>
	 * 但し、値がnullの場合はそのまま"true"を返す。<br/>
	 * エラーの場合、エラーメッセージを格納しfalseを返却する。<br/>
	 * @param chkObj チェック対象Object
	 * @param validAction 呼びもとのValidatorAction
	 * @param field バリデーションの対象になるField
	 * @param actionMsg エラー格納するActionMessages
	 * @param request HttpServletRequest
	 * @return チェック結果 true | false
	 */
	public static boolean validateMaxBytes(Object chkObj, ValidatorAction validAction, Field field,
			ActionMessages actionMsg, Validator validator, HttpServletRequest request) {

		if (chkObj == null)
			return true;

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) && !"".equals(chkObj)) {
			if (!ValidationUtil.isMaxBytes((String) chkObj, Integer.parseInt(field
					.getVarValue("maxbytes")))) {
				actionMsg.add(field.getKey(), Resources
						.getActionMessage(validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (!ValidationUtil.isMaxBytes(value[i], Integer.parseInt(field
						.getVarValue("maxbytes")))) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction,
							field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}









    /**
     * @param chkObj
     * @param validAction
     * @param field
     * @param actionMsg
     * @param validator
     * @param request
     * @return
     */
    public static boolean validateRequired(Object chkObj,
			ValidatorAction validAction, Field field, ActionMessages actionMsg,
			Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			actionMsg.add(field.getKey(), Resources.getActionMessage(validator,
					request, validAction, field));
			return false;
		}

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj)) {
			if (GenericValidator.isBlankOrNull((String) chkObj)) {
				actionMsg.add(field.getKey(), Resources.getActionMessage(
						validator, request, validAction, field));
				return false;
			}
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils
					.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				actionMsg.add(field.getKey(), Resources.getActionMessage(
						validator, request, validAction, field));
				return false;
			}
			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (GenericValidator.isBlankOrNull((String) value[i])) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(
							validator, request, validAction, field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
	}


    /**
     * @param chkObj
     * @param validAction
     * @param field
     * @param actionMsg
     * @param validator
     * @param request
     * @return
     */
    public static boolean validateMask(Object chkObj,
			ValidatorAction validAction, Field field, ActionMessages actionMsg,
			Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

        String mask = Resources.getVarValue("mask", field, validator, request, true);

		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) &&  !"".equals(chkObj)) {

            if (((String) chkObj) != null && ((String) chkObj).length() > 0
					&& !GenericValidator.matchRegexp(((String) chkObj), mask)) {
				actionMsg.add(field.getKey(), Resources.getActionMessage(
						validator, request, validAction, field));
				return false;
			} else {
				return true;
			}
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils
					.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}

			String[] value = getValueArray(temp);

			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}
				if (value[i] != null && value[i].length() > 0
						&& !GenericValidator.matchRegexp(value[i], mask)) {
					actionMsg.add(field.getKey(), Resources.getActionMessage(
							validator, request, validAction, field));
					return false;
				}
			}
		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;
    }





    /**
     * @param chkObj
     * @param validAction
     * @param field
     * @param actionMsg
     * @param validator
     * @param request
     * @return
     */
    public static boolean validateMaxLength(Object chkObj,
			ValidatorAction validAction, Field field, ActionMessages actionMsg,
			Validator validator, HttpServletRequest request) {

		if (chkObj == null) {
			return true;
		}

        String maxVar = Resources.getVarValue("maxlength", field, validator, request, true);
        String endLth = Resources.getVarValue("lineEndLength", field, validator, request, false);

        int max = Integer.parseInt(maxVar);
        boolean isValid = false;
		// ---------- chkObjが文字列ある場合
		if (isString(chkObj) &&  !"".equals(chkObj)) {

            if (GenericValidator.isBlankOrNull(endLth)) {
                isValid = GenericValidator.maxLength((String)chkObj, max);
            } else {
                isValid = GenericValidator.maxLength((String)chkObj, max,
                    Integer.parseInt(endLth));
            }
            if (!isValid) {
            	actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction, field));
                return false;
            }
			return true;
		}

		// ---------- chkObjが文字列以外のObjectである場合
		try {
			Object temp = PropertyUtils.getProperty(chkObj, field.getProperty());

			if (temp == null) {
				return true;
			}
			String[] value = getValueArray(temp);


			for (int i = 0; i < value.length; i++) {
				if (StrUtil.isEmpty(value[i])) {
					continue;
				}

	            isValid = false;
	            if (GenericValidator.isBlankOrNull(endLth)) {
	                isValid = GenericValidator.maxLength(value[i], max);
	            } else {
	                isValid = GenericValidator.maxLength(value[i], max,
	                    Integer.parseInt(endLth));
	            }
	            if (!isValid) {
	            	actionMsg.add(field.getKey(), Resources.getActionMessage(validator, request, validAction, field));
	                return false;
	            }
			}

		} catch (Exception e) {
			processException(e, field, request, actionMsg);
			return false;
		}
		return true;

    }


	/**
	 * objが文字列の場合は文字列の配列を作成し返却する。<br/>
	 * もし、String[]の場合はそのまま返却する。<br/>
	 * @param obj 処理対象OBJECT
	 * @return 文字列配列
	 */
	private static String[] getValueArray(Object obj) {
		 return isString(obj) ? new String[] { (String) obj } : (String[]) obj;
	}


	/**
	 * 例外を処理するメソッド<br/>
	 * @version 修正履歴
	 * @param e Throwable（例外）
	 * @param field 項目
	 */
	private static void processException(Throwable e, Field field, HttpServletRequest request, ActionMessages actionMsg) {

		String servletPath = request.getServletPath();

		String msg = null;
		if (e instanceof NoSuchMethodException) {
			 msg = "Struts-ConfigのDynaActionFormに「" + field.getProperty() + "」Propertyが正しく定義されていません。(Servlet Path:" + servletPath + ")";
			log.error( msg, e);
			request.setAttribute(CommonConsts.KEY_SYS_EXC,
					ExceptionUtil.getErrorInfoVO(request.getRequestURI(), e,
							msg));
		} else {
			msg = "「" + field.getProperty() + "」項目Validation処理中例外が発生しました。(Servlet Path:" + servletPath + ")";
			log.error(msg , e);
			request.setAttribute(CommonConsts.KEY_SYS_EXC,
					ExceptionUtil.getErrorInfoVO(request.getRequestURI(), e,
							msg));
		}

		actionMsg.add(field.getKey(), new ActionMessage(msg, false));

	}
}
