package tech3g.fk.dao.doc.impl;

import java.util.List;
import java.util.Map;

import tech3g.common.db.BaseDao;
import tech3g.common.db.DAOHelper;
import tech3g.common.db.DynaSqlParam;
import tech3g.common.db.StaticSqlParam;
import tech3g.common.util.StrUtil;
import tech3g.fk.apps.doc.TechDocUtil;
import tech3g.fk.dao.doc.Tech3gUserDao;
import tech3g.fk.dao.doc.dto.Tech3gUserManagerDTO;

public class Tech3gUserDaoImpl extends BaseDao implements Tech3gUserDao {

	/** オブジェクト名 */
	private final String OBJECT_NAME = "TECH3G_USER_INFO";

	/** キー項目 */
	private final String[] KEY_FIELD = { "USER_ID"};

	private static final String serach_user_info_sql = "co.doc.searchUserInfo";

	public String getObjectName() {
		return OBJECT_NAME;
	}

	public String[] getKeyField() {
		return KEY_FIELD;
	}

	public Map searchUserInfo(String user_id) {

		checkReqParam(new String[][] { { "USER ID", user_id } });

		// ------- パラメータを設定する。
		StaticSqlParam ssp = new StaticSqlParam();
		ssp.addParam(user_id);

		// -------検索を行う。
		return DAOHelper.getMap(select(serach_user_info_sql, ssp));
	}

	public List searchUserList(String user_id, int startNo, int endNo) {

        // ------- パラメータを設定する。
        DynaSqlParam dsp = new DynaSqlParam();
        dsp.addParam("user_id", user_id);
        dsp.addParam("startNo", startNo);
        dsp.addParam("endNo", 	endNo);

		return select("user.searchUserList", dsp);
	}

	public int insertUserInfo(String user_id, Tech3gUserManagerDTO dto) {

		checkReqParam(new String[][] {
							{ "USER ID", 	dto.getUser_id() }
						,	{ "USER PASS", 	dto.getUser_pass() }
		});

        // ------- パラメータを設定する。
        DynaSqlParam dsp = new DynaSqlParam();
        dsp.addParam("user_id", 		dto.getUser_id());
        dsp.addParam("regi_user_id", 	user_id);
        dsp.addParam("user_name",		dto.getUser_name());
        dsp.addParam("user_pass",		TechDocUtil.encode(dto.getUser_pass()));
        dsp.addParam("user_mail",		dto.getUser_mail());
        dsp.addParam("user_admin",		dto.getUser_admin());
        dsp.addParam("user_tel_no",		dto.getUser_tel_no());
        dsp.addParam("user_ip",			dto.getUser_ip());

		return insert("doc.insertUserInfo", dsp);
	}

	public int updateUserInfo(String user_id, Tech3gUserManagerDTO dto) {

		checkReqParam(new String[][] {
				{ "USER ID", 	dto.getUser_id() }
		});

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("user_id", 	dto.getUser_id());
        dsp.addParam("regi_user_id", 	user_id);
        dsp.addParam("user_name",	dto.getUser_name());
        dsp.addParam("user_pass",	StrUtil.isEmpty(dto.getUser_pass())?"":TechDocUtil.encode(dto.getUser_pass()));
        dsp.addParam("user_mail",	dto.getUser_mail());

        if ("1".equals(dto.getLogin_user_admin())) {
        	dsp.addParam("user_admin",	dto.getUser_admin());
        }
        dsp.addParam("user_tel_no",	dto.getUser_tel_no());
        dsp.addParam("user_ip",		dto.getUser_ip());

		return update("doc.updateUserInfo", dsp);
	}

	public int searchUserCnt(String user_id) {
        // ------- パラメータを設定する。
        DynaSqlParam dsp = new DynaSqlParam();
        dsp.addParam("user_id", user_id);

		return DAOHelper.getCnt(select("user.searchUserCnt", dsp));
	}

	public int deleteUserInfo(String user_id) {

		checkReqParam(new String[][] {
				{ "USER ID", 	user_id }
		});

		// ------- パラメータを設定する。
		DynaSqlParam dsp = new DynaSqlParam();
		dsp.addParam("user_id", user_id);

		return delete("doc.deleteUserInfo", dsp);
	}
}
