package tech3g.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import tech3g.common.exceptions.SystemException;

/**
 * <pre>
 * BeanUtilクラス。
 *
 * 注意) インスタンス化せずにstatic methodを作成し使用すること。
 * </pre>
 */
public class BeanUtil {

    //--------------------------------------------------- 定数
    //--------------------------------------------------- インスタンス変数
	//--------------------------------------------------- コンストラクタ
    /**
	 * コンストラクタ<br/>
	 * このオブジェクトはインスタンス化する必要がない。
	 */
	private BeanUtil()	{
	}
    // -------------------------------------------------- SetGet Methods
    //--------------------------------------------------- インスタンスメソッド


	/**
	 * Beanコピー<br/>
	 * コピー元のBeanからコピー先のBeanへ、Property（項目）の値をコピーする。
	 *
	 * ただし、項目名が同一で、set getのメソッドが存在しなければいけない。
	 *
	 * @param copySaki コピー先
	 * @param copyMoto コピー元
	 */
	public static final void copyProperties(Object copySaki, Object copyMoto) {
		try {
			BeanUtils.copyProperties(copySaki, copyMoto);
		} catch (IllegalAccessException e) {
			throw new SystemException("BeanUtil使用中エラー", e);
		} catch (InvocationTargetException e) {
			throw new SystemException("BeanUtil使用中エラー", e);
		}
	}

	/**
	 * 文字列へ変換した後コピー<br/>
	 *
	 * コピー元のBeanの項目をコピー先のコピーする。<br/>
	 * コピーする際に、コピー元のデータをStringに変換しセット。
	 * @param copySaki コピー先
	 * @param copyMoto コピー元
	 */
	public static final void copyPropertiesToString(Object copySaki, Object copyMoto) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(copyMoto.getClass());
            PropertyDescriptor[] pd = beanInfo.getPropertyDescriptors();
            Object value = null;
            for (int i = 0; i < pd.length; i++) {
                Method method = pd[i].getReadMethod();
                if (pd[i].getName() != null && method != null) {
                	value = method.invoke(copyMoto, new Object[0]);

                	if (value instanceof Double) {
                		value = new DecimalFormat("0.000").format(value);
                	}

                	BeanUtils.setProperty(copySaki, pd[i].getName(), value != null ? String.valueOf(value) : null);
                }
            }

        } catch (IntrospectionException e) {
        	throw new SystemException("BeanUtil使用中エラー", e);
        } catch (IllegalArgumentException e) {
        	throw new SystemException("BeanUtil使用中エラー", e);
        } catch (IllegalAccessException e) {
        	throw new SystemException("BeanUtil使用中エラー", e);
        } catch (InvocationTargetException e) {
        	throw new SystemException("BeanUtil使用中エラー", e);
        }
	}


	/**
	 * CamelCaseしBeanコピー<br/>
	 *
	 * コピー元のBeanの項目をコピー先のCamelCaseに該当項目にコピーする。<br/>
	 * @param copySaki コピー先
	 * @param copyMoto コピー元
	 */
	public static final void copyCamelCaseProperties(Object copySaki, Object copyMoto) {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(copyMoto.getClass());
            PropertyDescriptor[] pd = beanInfo.getPropertyDescriptors();

            for (int i = 0; i < pd.length; i++) {
                Method method = pd[i].getReadMethod();
                if (pd[i].getName() != null && method != null) {
                	BeanUtils.setProperty(copySaki, toCamelCase(pd[i].getName()), method.invoke(copyMoto, new Object[0]));
                }
            }
        } catch (IntrospectionException e) {
        	throw new SystemException("BeanUtil使用中エラー", e);
        } catch (IllegalArgumentException e) {
        	throw new SystemException("BeanUtil使用中エラー", e);
        } catch (IllegalAccessException e) {
        	throw new SystemException("BeanUtil使用中エラー", e);
        } catch (InvocationTargetException e) {
        	throw new SystemException("BeanUtil使用中エラー", e);
        }
	}


	/**
	 * BeanをMapへ変換<br/>
     * bean のプロパティ名を key, プロパティのデータを value とした Map を返します.
     * プロパティのデータ型に関わらず, value は String になる。
	 * @param bean 対象Bean
	 * @return 変換後のMap
	 */
	public static final Map describe(Object bean) {

		try {
			return BeanUtils.describe(bean);
		} catch (IllegalAccessException e) {
			throw new SystemException("BeanUtil使用中エラー", e);
		} catch (InvocationTargetException e) {
			throw new SystemException("BeanUtil使用中エラー", e);
		} catch (NoSuchMethodException e) {
			throw new SystemException("BeanUtil使用中エラー", e);
		}
	}

	/**
	 * Beanの複製<br/>
	 * @param bean 対象Bean
	 * @return 複製されたBean
	 */
	public static final Object cloneBean(Object bean) {
		try {
			return BeanUtils.cloneBean(bean);
		} catch (IllegalAccessException e) {
			throw new SystemException("BeanUtil使用中エラー", e);
		} catch (InstantiationException e) {
			throw new SystemException("BeanUtil使用中エラー", e);
		} catch (InvocationTargetException e) {
			throw new SystemException("BeanUtil使用中エラー", e);
		} catch (NoSuchMethodException e) {
			throw new SystemException("BeanUtil使用中エラー", e);
		}
	}

	/**
	 * MapからBeanへのコピー<br/>
	 * Mapの内容をBeanに格納する。<br/>
	 * ただし、項目名が同一で、set getのメソッドが存在しなければいけない。
	 * @param bean Bean(コピー先)
	 * @param map Map(コピー元)
	 */
	public static final void populate(Object bean, Map map) {
		try {
			BeanUtils.populate(bean, map);
		} catch (IllegalArgumentException e) {
			throw new SystemException("BeanUtil使用中エラー", e);
		} catch (IllegalAccessException e) {
			throw new SystemException("BeanUtil使用中エラー", e);
		} catch (InvocationTargetException e) {
			throw new SystemException("BeanUtil使用中エラー", e);
		}
	}

	/**
	 * MapからBeanへのコピー(null又はブランクは無視)<br/>
	 * Mapの内容をStringへ変換してBeanに格納する。<br/>
	 * ただし、項目名が同一で、set getのメソッドが存在しなければいけない。<br/>
	 * <br/>
	 * 注意 )　・文字列に変換してコピーする。<br/>
	 * コピー元のMapに該当値がnull又はブランクの場合は無視する。<br/>
	 * @param bean Bean(コピー先)
	 * @param map Map(コピー元)
	 */
	public static final void populateIgnoreEmpty(Object bean, Map map) {

		if (map != null && !map.isEmpty()) {

    		try {
				BeanInfo beanInfo = null;
				beanInfo = Introspector.getBeanInfo(bean.getClass());
				PropertyDescriptor[] pd = beanInfo.getPropertyDescriptors();

				String propertyName = null;
				Object value = null;
				for (int i = 0; i < pd.length; i++) {

					propertyName = pd[i].getName();
					if (pd[i].getName() != null && map.containsKey(propertyName)) {
						value = map.get(propertyName);


						if (null != value && !"".equals(String.valueOf(value))) {
							BeanUtils.setProperty(bean, propertyName, String.valueOf(value));
						}


//						if (pd[i].getPropertyType() == String.class) {
//							if (!StrUtil.isEmpty((String)value)) {
//								BeanUtils.setProperty(bean, propertyName, map.get(propertyName));
//							}
//						} else {
//							if (null != value) {
//								BeanUtils.setProperty(bean, propertyName, map.get(propertyName));
//							}
//						}
					}
				}
			} catch (IllegalAccessException e) {
				throw new SystemException("BeanUtil使用中エラー", e);
			} catch (InvocationTargetException e) {
				throw new SystemException("BeanUtil使用中エラー", e);
			} catch (IntrospectionException e) {
				throw new SystemException("BeanUtil使用中エラー", e);
			}
		}
	}


	/**
	 * MapからBeanへのコピー(nullは無視)<br/>
	 * Mapの内容をStringへ変換してBeanに格納する。<br/>
	 * ただし、項目名が同一で、set getのメソッドが存在しなければいけない。<br/>
	 * <br/>
	 * 注意 )　・文字列に変換してコピーする。<br/>
	 * 　　　　・コピー元のMapに該当値がnull場合は無視する。<br/>
	 * @param bean Bean(コピー先)
	 * @param map Map(コピー元)
	 */
	public static final void populateIgnoreNull(Object bean, Map map) {

		if (map != null && !map.isEmpty()) {

    		try {
				BeanInfo beanInfo = null;
				beanInfo = Introspector.getBeanInfo(bean.getClass());
				PropertyDescriptor[] pd = beanInfo.getPropertyDescriptors();

				String propertyName = null;
				Object value = null;
				for (int i = 0; i < pd.length; i++) {

					propertyName = pd[i].getName();
					if (pd[i].getName() != null && map.containsKey(propertyName)) {
						value = map.get(propertyName);

						if (null != value) {
							BeanUtils.setProperty(bean, propertyName, String.valueOf(value));
						}

					}
				}
			} catch (IllegalAccessException e) {
				throw new SystemException("BeanUtil使用中エラー", e);
			} catch (InvocationTargetException e) {
				throw new SystemException("BeanUtil使用中エラー", e);
			} catch (IntrospectionException e) {
				throw new SystemException("BeanUtil使用中エラー", e);
			}
		}
	}










	/**
     * CamelCaseへ変換<br/>
     * 指定した文字列をCamelCaseへ変換する。<br/>
     *
     * @param str 処理対象文字列
     * @return CamelCase変換結果
     */
	private static String toCamelCase(String str) {
		StringBuffer sb = new StringBuffer();

		if (str == null) {
			return null;
		}

		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == '_') {
				i++;
				if (!(i < charArray.length)) {
					break;
				} else {
					sb.append(Character.toUpperCase(charArray[i]));
				}
			} else {
				sb.append(Character.toLowerCase(charArray[i]));
			}

		}

		return sb.toString();
	}
}
