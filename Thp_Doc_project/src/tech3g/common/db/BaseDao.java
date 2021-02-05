package tech3g.common.db;


import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;

import tech3g.common.exceptions.SystemException;
import tech3g.common.util.StrUtil;
import anyframe.core.idgen.IIdGenerationService;
import anyframe.core.properties.IPropertiesService;
import anyframe.core.query.IQueryService;
import anyframe.core.query.QueryServiceException;

/**
 * <pre>
 * BaseDAOImplクラス。
 * Daoクラスが基本的に継承しなければならないクラス。
 * DBアクセスの機能を提供する。
 * DBアクセスはAnyFrameのqueryServiceに処理を委譲する。
 * </pre>
 */
public class BaseDao {

    //---------------------------------------------------定数
    //---------------------------------------------------インスタンス変数
    /** DBアクセスを行うためのサービス */
    private IQueryService queryService;
    /** メッセージソース */
    protected MessageSource messageSource;
    /** log */
    protected Log log = LogFactory.getLog(this.getClass());
    /** loggerName */
    protected String loggerName = this.getClass().getName();
    /** propertiesService */
    protected IPropertiesService propertiesService;
    /** idGenerationService */
    protected IIdGenerationService idGenerationService;

    // -------------------------------------------------- コンストラクタ
    /**
     * このクラスのコンストラクタ<br/>
     * @version 修正履歴
     */
    public BaseDao() {
        super();
    }

    // -------------------------------------------------- SetGet Methods
    public String getLoggerName() {
        return loggerName;
    }
    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
        this.log = LogFactory.getLog(loggerName);
    }
    public MessageSource getMessageSource() {
        return messageSource;
    }
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    protected IQueryService getQueryService() {
        return queryService;
    }
    public void setQueryService(IQueryService queryService) {
        this.queryService = queryService;
    }
    public IPropertiesService getPropertiesService() {
        return propertiesService;
    }
    public void setPropertiesService(IPropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }
    public IIdGenerationService getIdGenerationService() {
        return idGenerationService;
    }
    public void setIdGenerationService(
            IIdGenerationService sequenceIdGenerationService) {
        this.idGenerationService = sequenceIdGenerationService;
    }
    //--------------------------------------------------- インスタンスメソッド
    /**
     * 検索(Select)を行う。<br/>
     * @param sqlid SQL ID
     * @param params パラメータ
     * @return
     */
    protected final List select(String sqlid, SqlParam params) {
        if (params == null) {
            return (List) this.find(sqlid, new Object[0]);
        } else {
            return (List) this.find(sqlid, params.convObjArray());
        }
    }

    /**
     * 更新(UPDATE)を行う。<br/>
     * @param sqlid SQL ID
     * @param params パラメータ
     * @return 更新件数
     */
    protected final int update(String sqlid, SqlParam params) {
        if (params == null) {
            return this.update(sqlid, new Object[0]);
        } else {
            return this.update(sqlid, params.convObjArray());
        }
    }

    /**
     * 登録(INSERT)を行う。<br/>
     * @param sqlid SQL ID
     * @param params パラメータ
     * @return 登録件数
     */
    protected final int insert(String sqlid, SqlParam params) {
        if (params == null) {
            return this.create(sqlid, new Object[0]);
        } else {
            return this.create(sqlid, params.convObjArray());
        }
    }

    /**
     * 削除(DELETE)を行う。<br/>
     * @param sqlid SQL ID
     * @param params パラメータ
     * @return 削除件数
     */
    protected final int delete(String sqlid, SqlParam params) {
        if (params == null) {
            return this.remove(sqlid, new Object[0]);
        } else {
            return this.remove(sqlid, params.convObjArray());
        }
    }



    /**
     * プロシージャの実行<br/>
     * @param queryId SQLID
     * @param values
     * @return 結果のMap
     */
    protected final Map execute (String queryId, CallSqlParam values) {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* SqlId 　    ：[" + queryId + "]\n"
            			+ "* パラメータ  ： " + toStringObj(values.convObjArray()));
            }
            // ----- 実行
            Map res = queryService.execute(queryId, values.getParamMap());
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* 実行結果 　： [" + res + "]");
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }






    protected final Collection selectBySQL(String sql, String[] types, Object[] values) {
        return this.findBySQL(sql, types, values);

    }




    /**
     * 検索(Select)を行う。<br/>
     * @param sqlid SQL ID
     * @param params パラメータ
     * @return
     */
    protected final List selectByDynaSql(String sql, Map properties) {

    	if (isVelocity(sql)) {
    		StringWriter writer = new StringWriter();
    		try {
    			Velocity.evaluate(new ExtMapSqlParameterSourceContext(
    					properties), writer, "QueryService", sql);
    		} catch (ParseErrorException e) {
    			// TODO 自動生成された catch ブロック
    			e.printStackTrace();
    		} catch (MethodInvocationException e) {
    			// TODO 自動生成された catch ブロック
    			e.printStackTrace();
    		} catch (ResourceNotFoundException e) {
    			// TODO 自動生成された catch ブロック
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO 自動生成された catch ブロック
    			e.printStackTrace();
    		}
    		sql = writer.toString();
    	}


    	String  runnableSQL = NamedParameterUtils.parseSqlStatementIntoString(sql);
    	Object[] params= NamedParameterUtils.buildValueArray(sql, properties);
    	List<String> typeList = new ArrayList<String>();

    	if (params != null) {
    		for (int i = 0; i < params.length; i++) {
    			typeList.add("VARCHAR");
    		}
    	}

    	// ------- 検索を行う。
    	return (List) findBySQL(runnableSQL, (String[])typeList.toArray(new String[0]), params);
    }



    protected final List selectByDynaSql(String sql, Map<String, Object> properties, int start, int end) {
//		sql = getRunnableSQL(sql, properties);

    	log.debug("start:" + start + " end:" + end);

    	String newSql = StrUtil.concat("SELECT * FROM ( SELECT   INNER_TABLE.* , ROWNUM AS ROW_SEQ FROM ( \n",
    									sql,
    									"\n) INNER_TABLE WHERE ROWNUM <= :rend )	WHERE ROW_SEQ BETWEEN :rstart AND :rend");

		properties.put("rstart", start);
		properties.put("rend", end);

		if (isVelocity(newSql)) {
			StringWriter writer = new StringWriter();
			try {
				Velocity.evaluate(new ExtMapSqlParameterSourceContext(
						properties), writer, "QueryService", newSql);
			} catch (ParseErrorException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (MethodInvocationException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (ResourceNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			newSql = writer.toString();
		}

//		ParsedSql ppp = anyframe.core.query.impl.util.NamedParameterUtils.parseSqlStatement(newSql);
//		Object[] obj = NamedParameterUtils.buildValueArray(newSql, properties);
//		String newSql1 = anyframe.core.query.impl.util.NamedParameterUtils.substituteNamedParameters(newSql, newSqlsource);
//		Object obj2 = anyframe.core.query.impl.util.NamedParameterUtils.buildValueArray(ppp, newSqlsource);
//		String newSql2 = anyframe.core.query.impl.util.NamedParameterUtils.parseSqlStatementIntoString(newSql);



		String  runnableSQL = NamedParameterUtils.parseSqlStatementIntoString(newSql);
		Object[] params= NamedParameterUtils.buildValueArray(newSql, properties);
		List<String> typeList = new ArrayList<String>();

		if (params != null) {
			for (int i = 0; i < params.length-3; i++) {
				typeList.add("VARCHAR");
			}
		}
		typeList.add("INTEGER");
		typeList.add("INTEGER");
		typeList.add("INTEGER");

		// ------- 検索を行う。
		return (List) findBySQL(runnableSQL, (String[])typeList.toArray(new String[0]), params);
    }

    /**
     * 必須パラメータチェック<br/>
     * String配列の値がNULL又はブランクである場合は例外をThrowする。<br/>
     * @param paramArray
     */
    protected void checkReqParam(Object[][] paramArray) {
    	for (int i = 0; i < paramArray.length; i++) {
    		if (paramArray[i][1]== null || "".equals(String.valueOf(paramArray[i][1]).trim())) {
    			log.error("* Daoの必須パラメータチェックエラー");
    			throw new SystemException(String.valueOf(paramArray[i][0])  + "は必須項目です。");
    		}
    	}
    }

    // ================================================================
    // ここからは…
    // IQueryServiceの純粋な機能に例外処理を実装する。
    // ================================================================
    // *** find --------------------------------------
    /**
     * Execute a SELECT query, Using object, which class is matched with table by XML files.<br/>
     * @param obj
     * @return
     */
    private final Collection find(Object obj) {
        try {
            return queryService.find(obj);
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    /**
     * <br/>
     * @param sqlid
     * @param params
     * @return
     */
    private final Collection find(String sqlid, Object[] params) {
        try {
        	// ----- SQLIDとパラメータを出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* SqlID  　  ： [" + sqlid + "] \n"
            			+ "* パラメータ ： " + toStringObj(params));
            }
            // ----- 実行
            Collection res = queryService.find(sqlid, params);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	if (res != null) {
                	log.debug("\n* 検索件数 　： [" + res.size() + "]");
            	} else {
            		log.debug("\n* 検索件数 　： [" + 0 + "]");
            	}
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    /**
     * <br/>
     * @param sqlid
     * @param arg2
     * @param arg3
     * @return
     */
    private final Collection find(String sqlid, Object[] params, int pageIndex) {
        try {
        	// ----- SQLIDとパラメータを出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* SqlID 　    ： [" + sqlid + "] \n"
            			+ "* パラメータ  ： " + toStringObj(params) + "\n"
            			+ "* pageIndex   :" + pageIndex);
            }
            // ----- 実行
            Collection res = queryService.find(sqlid, params, pageIndex);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	if (res != null) {
                	log.debug("\n* 検索件数 　： [" + res.size() + "]");
            	} else {
            		log.debug("\n* 検索件数 　： [" + 0 + "]");
            	}
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    /**
     * <br/>
     * @param sqlid
     * @param arg2
     * @param arg3
     * @param arg4
     * @return
     */
    private final Collection find(String sqlid, Object[] params, int pageIndex, int pageSize) {
        try {
        	// ----- SQLIDとパラメータを出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* SqlID 　    ： [" + sqlid + "] \n"
            			+ "* パラメータ  ： " + toStringObj(params) + "\n"
            			+ "* pageIndex   :" + pageIndex + "\n"
            			+ "* pageSize    :" + pageSize);
            }
            // ----- 実行
            Collection res = queryService.find(sqlid, params, pageIndex, pageSize);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	if (res != null) {
                	log.debug("\n* 検索件数 　： [" + res.size() + "]");
            	} else {
            		log.debug("\n* 検索件数 　： [" + 0 + "]");
            	}
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** findWithRowCount --------------------------------------
    /**
     * <br/>
     * @param arg1
     * @param arg2
     * @return
     */
    private final Map findWithRowCount(String queryId, Object[] params) {
        try {
        	// ----- SQLIDとパラメータを出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* SqlID 　    ： [" + queryId + "] \n"
            			+ "* パラメータ  ： " + toStringObj(params) + "\n");
            }
            // ----- 実行
            Map res = queryService.findWithRowCount(queryId, params);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	if (res != null) {
            		Collection  resLst = (Collection)  res.get(IQueryService.LIST);
            		if (resLst != null) {
            			log.debug("\n* 検索件数 　： [" + resLst.size() + "]\n" + "* Count    :" + res.get(IQueryService.COUNT));
            		} else {
            			log.debug("\n* 検索件数 　： [" + 0 + "]");
            		}
            	} else {
            		log.debug("\n* 検索件数 　： [" + 0 + "]");
            	}
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    /**
     * <br/>
     * @param arg1
     * @param arg2
     * @param arg3
     * @return
     */
    private final Map findWithRowCount(String queryId, Object[] values, int pageIndex) {
        try {
        	// ----- SQLIDとパラメータを出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* SqlID 　    ： [" + queryId + "] \n"
            			+ "* パラメータ  ： " + toStringObj(values) + "\n"
            			+ "* pageIndex   :" + pageIndex);
            }
            // ----- 実行
            Map res = queryService.findWithRowCount(queryId, values, pageIndex);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	if (res != null) {
            		Collection  resLst = (Collection)  res.get(IQueryService.LIST);
            		if (resLst != null) {
            			log.debug("\n* 検索件数 　： [" + resLst.size() + "]\n" + "* Count    :" + res.get(IQueryService.COUNT));
            		} else {
            			log.debug("\n* 検索件数 　： [" + 0 + "]");
            		}
            	} else {
            		log.debug("\n* 検索件数 　： [" + 0 + "]");
            	}
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    /**
     * <br/>
     * @param arg1
     * @param arg2
     * @param arg3
     * @param arg4
     * @return
     */
    private final Map findWithRowCount(String queryId, Object[] values, int pageIndex, int pageSize) {
        try {
        	// ----- SQLIDとパラメータを出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* SqlID 　    ： [" + queryId + "] \n"
            			+ "* パラメータ  ： " + toStringObj(values) + "\n"
            			+ "* pageIndex   :" + pageIndex + "\n"
            			+ "* pageSize    :" + pageSize);
            }
            // ----- 実行
            Map res = queryService.findWithRowCount(queryId, values, pageIndex, pageSize);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	if (res != null) {
            		Collection  resLst = (Collection)  res.get(IQueryService.LIST);
            		if (resLst != null) {
            			log.debug("\n* 検索件数 　： [" + resLst.size() + "]\n" + "* Count    :" + res.get(IQueryService.COUNT));
            		} else {
            			log.debug("\n* 検索件数 　： [" + 0 + "]");
            		}
            	} else {
            		log.debug("\n* 検索件数 　： [" + 0 + "]");
            	}
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** findBySQL --------------------------------------
    private final Collection findBySQL(String sql, String[] types, Object[] values) {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* types 　    ：" + toStringObj(types) + "\n"
            			+ "* パラメータ  ：" + toStringObj(values));
            }
            // ----- 実行
            Collection res = queryService.findBySQL(sql, types, values);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	if (res != null) {
            		log.debug("\n* 検索件数 　： [" + res.size() + "]\n");
            	} else {
            		log.debug("\n* 検索件数 　： [" + 0 + "]");
            	}
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    private final Collection findBySQL(String sql, String[] types, Object[] values, int pageIndex, int pageSize) {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* types 　    ：" + toStringObj(types) + "\n"
            			+ "* パラメータ  ： " + toStringObj(values) + "\n"
            			+ "* pageIndex   :" + pageIndex + "\n"
            			+ "* pageSize    :" + pageSize);
            }
            // ----- 実行
            Collection res = queryService.findBySQL(sql, types, values);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	if (res != null) {
            		log.debug("\n* 検索件数 　： [" + res.size() + "]\n");
            	} else {
            		log.debug("\n* 検索件数 　： [" + 0 + "]");
            	}
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** findBySQLWithRowCount --------------------------------------
    private final Map findBySQLWithRowCount(String sql, String[] types, Object[] values) {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* types 　    ：" + toStringObj(types) + "\n"
            			+ "* パラメータ  ： " + toStringObj(values) + "\n");
            }
            // ----- 実行
            Map res = queryService.findBySQLWithRowCount(sql, types, values);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	if (res != null) {
            		Collection  resLst = (Collection)  res.get(IQueryService.LIST);
            		if (resLst != null) {
            			log.debug("\n* 検索件数 　： [" + resLst.size() + "]\n" + "* Count    :" + res.get(IQueryService.COUNT));
            		} else {
            			log.debug("\n* 検索件数 　： [" + 0 + "]");
            		}
            	} else {
            		log.debug("\n* 検索件数 　： [" + 0 + "]");
            	}
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    private final Map findBySQLWithRowCount(String sql, String[] types, Object[] values, int pageIndex, int pageSize)  {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* types 　    ：" + toStringObj(types) + "\n"
            			+ "* パラメータ  ： " + toStringObj(values) + "\n"
            			+ "* pageIndex   :" + pageIndex + "\n"
            			+ "* pageSize    :" + pageSize);
            }
            // ----- 実行
            Map res = queryService.findBySQLWithRowCount(sql, types, values, pageIndex, pageSize);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	if (res != null) {
            		Collection  resLst = (Collection)  res.get(IQueryService.LIST);
            		if (resLst != null) {
            			log.debug("\n* 検索件数 　： [" + resLst.size() + "]\n" + "* Count    :" + res.get(IQueryService.COUNT));
            		} else {
            			log.debug("\n* 検索件数 　： [" + 0 + "]");
            		}
            	} else {
            		log.debug("\n* 検索件数 　： [" + 0 + "]");
            	}
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** create --------------------------------------
    private final int create(Object obj) {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	// TODO log.debug("* パラメータ  ： " + toStringObj(obj));
            }
            // ----- 実行
            int res = queryService.create(obj);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* 登録件数 　： [" + res + "]");
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    private final int create(String queryId, Object[] values)  {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* SqlId 　    ：[" + queryId + "]\n"
            			+ "* パラメータ  ： " + toStringObj(values));
            }
            // ----- 実行
            int res = queryService.create(queryId, values);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	if (!queryId.startsWith("co.mn")) {
            		log.debug("\n* 登録件数 　： [" + res + "]");
            	}
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** createBySQL --------------------------------------
    private final int createBySQL(String sql, String[] types, Object[] values)  {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* types 　    ：" + toStringObj(types) + "\n"
            			+ "* パラメータ  ： " + toStringObj(values));
            }
            // ----- 実行
            int res = queryService.createBySQL(sql, types, values);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* 登録件数 　： [" + res + "]");
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** update --------------------------------------
    private final int update(Object obj) {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	// TODO log.debug("* パラメータ  ： " + toStringObj(obj));
            }
            // ----- 実行
            int res = queryService.update(obj);

           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* 更新件数 　： [" + res + "]");
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    private final int update(String queryId, Object[] values)  {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* SqlId 　    ：[" + queryId + "]\n"
            			+ "* パラメータ  ： " + toStringObj(values));
            }
            // ----- 実行
            int res = queryService.update(queryId, values);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* 更新件数 　： [" + res + "]");
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** updateBySQL --------------------------------------
    private final int updateBySQL(String sql, String[] types, Object[] values)  {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* types 　    ：" + toStringObj(types) + "\n"
            			+ "* パラメータ  ： " + toStringObj(values));
            }
            // ----- 実行
            int res = queryService.updateBySQL(sql, types, values);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* 更新件数 　： [" + res + "]");
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** remove --------------------------------------
    private final int remove(Object obj) {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	// TODO log.debug("* パラメータ  ： " + toStringObj(obj));
            }
            // ----- 実行
            int res = queryService.remove(obj);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* 削除件数 　： [" + res + "]");
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }
    private final int remove(String queryId, Object[] values)  {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* SqlId 　    ：[" + queryId + "]\n"
            			+ "* パラメータ  ： " + toStringObj(values));
            }
            // ----- 実行
            int res = queryService.remove(queryId, values);

           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* 削除件数 　： [" + res + "]");
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** removeBySQL --------------------------------------
    private final int removeBySQL(String sql, String[] types, Object[] values)  {
        try {
        	// ----- SQLIDとパラメータをDebugログ出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* types 　    ：" + toStringObj(types) + "\n"
            			+ "* パラメータ  ： " + toStringObj(values));
            }
            // ----- 実行
            int res = queryService.removeBySQL(sql, types, values);
           	// ----- 検索結果出力
            if (log.isDebugEnabled()) {
            	log.debug("\n* 削除件数 　： [" + res + "]");
            }
            return res;
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** execute --------------------------------------
//    private final Map executeString (String queryId, Map values) {
//        try {
//        	// ----- SQLIDとパラメータをDebugログ出力
//            if (log.isDebugEnabled()) {
//            	log.debug("\n* SqlId 　    ：[" + queryId + "]\n"
//            			+ "* パラメータ  ： " + toStringMap(values));
//            }
//            // ----- 実行
//            Map res = queryService.execute(queryId, values);
//           	// ----- 検索結果出力
//            if (log.isDebugEnabled()) {
//            	// TODO log.debug("\n* 実行件数 　： [" + res + "]");
//            }
//            return res;
//        } catch (QueryServiceException e) {
//            throw new SystemException("SQL実行中エラーが発生しました。", e);
//        }
//    }

    private final Map execute(String queryId, Map values, int pageIndex)  {
        try {
            return queryService.execute(queryId, values, pageIndex);
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    private final Map execute(String queryId, Map values, int pageIndex, int pageSize)  {
        try {
            return queryService.execute(queryId, values, pageIndex, pageSize);
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** executeBySQL --------------------------------------
    private final Map executeBySQL(String sql, String[] types, String[] names, String[] bindings, Map values)  {
        try {
            return queryService.executeBySQL(sql, types, names, bindings, values);
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    private final Map executeBySQL(String sql, String[] types, String[] names, String[] bindings, Map values, int pageIndex, int pageSize) {
        try {
            return queryService.executeBySQL(sql, types, names, bindings,  values, pageIndex, pageSize);
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

//===============================================================================
// **** バッチ関連
//===============================================================================

    // *** batchCreate --------------------------------------
    private final int[] batchCreate(List targets) {
        try {
            return queryService.batchCreate(targets);
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** batchUpdate --------------------------------------
    private final int[] batchUpdate(List targets) {
        try {
            return queryService.batchUpdate(targets);
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    private final int[] batchUpdate(String queryId, List targets)  {
        try {
            return queryService.batchUpdate(queryId, targets);
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** batchUpdateBySQL --------------------------------------
    private final int[] batchUpdateBySQL(String sql, String[] types, List targets)  {
        try {
            return queryService.batchUpdateBySQL(sql, types, targets);
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // *** batchRemove --------------------------------------
    private final int[] batchRemove(List targets) {
        try {
            return queryService.batchRemove(targets);
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    // ===============================================================================
    // **** などなど
    // ===============================================================================
    /**
     *  Find specified SQL which query id equal to input parameter.<br/>
     * @param arg1
     * @return
     */
    private final String getStatement(String arg1) {
        try {
            return queryService.getStatement(arg1);
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    /**
     * Count all queries which defined in mapping xml files.<br/>
     * @return
     */
    private final int countQuery() {
        return queryService.countQuery();
    }

    /**
     * Find parameters for specified query.<br/>
     * @return
     */
    private final Map getQueryMap() {
        try {
            return queryService.getQueryMap();
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }

    /**
     * <br/>
     * @param arg1
     * @return
     */
    private final List getQueryParams(String arg1) {
        try {
            return queryService.getQueryParams(arg1);
        } catch (QueryServiceException e) {
            throw new SystemException("SQL実行中エラーが発生しました。", e);
        }
    }



    /**
     * Objectの内容を文字列で表して返却する。<br/>
     * @param obj objの配列
     * @return 結果文字列
     */
    private final static String toStringObj(Object[] obj){
    	StringBuffer sb = new StringBuffer();

    	if (obj instanceof Object[][]) {
    		Object[][] po = (Object[][]) obj;
    		for(int i = 0; i<  po.length; i++) {
        		sb.append(po[i][0]);
        		sb.append("={");
        		sb.append(po[i][1]);
        		sb.append("} ");
    		}
    	} else if (obj instanceof Object[]) {
    		Object[] po = obj;
    		for(int i = 0; i<  po.length; i++) {
        		sb.append("{");
        		sb.append(po[i]);
        		sb.append("} ");
    		}
    	}
    	return sb.toString();
    }

    /**
     * マップの内容を文字列で表して返却する。<br/>
     * @param paramMap マップ
     * @return 結果文字列
     */
    private final static String toStringMap(Map<String, Object> paramMap){
        StringBuffer sb = new StringBuffer();
        Iterator<String> it = paramMap.keySet().iterator();
        String key = null;
        while (it.hasNext()) {
            key = it.next();
            sb.append(key);
            sb.append("={");
            sb.append(paramMap.get(key));
            sb.append("} ");
        }
        return sb.toString();
    }




    /**
     * Velocity判定<br/>
     * 指定したSQLにVelocityのsyntaxが含まれているか判定する。
     * @param sql SQL
     * @return true | false
     */
    private boolean isVelocity(String sql) {
        return ((sql.indexOf("#if") > -1 || sql.indexOf("#foreach") > -1) && sql
            .indexOf("#end") > -1);
    }

}
