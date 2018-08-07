package com.ahjswy.cn.utils;

import java.util.List;

import com.ahjswy.cn.bean.bmob.ExceptionLog;
import com.ahjswy.cn.bean.bmob.bo_swy_user;
import com.ahjswy.cn.dao.Exception_logDAO;

import android.util.Log;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SQLQueryListener;

public class BmobUtils {
	final static Exception_logDAO logDAO = new Exception_logDAO();
	private static BmobUtils bmobutils = new BmobUtils();
	private List<BmobObject> listexception;

	public static BmobUtils getInstance() {
		return bmobutils;
	}

	/**
	 * 长传日志
	 */
	public void updata() {
		listexception = logDAO.queryBmobAll();
		if (listexception.isEmpty()) {
			return;
		}
		new BmobBatch().insertBatch(listexception).doBatch(new QueryListListener<BatchResult>() {

			@Override
			public void done(List<BatchResult> o, BmobException e) {
				if (e == null) {
					if (o.size() == 0) {
						return;
					}
					for (int i = 0; i < o.size(); i++) {
						BatchResult result = o.get(i);
						ExceptionLog objectlog = (ExceptionLog) listexception.get(i);
						BmobException ex = result.getError();
						if (ex == null) {
							if (logDAO.deleteRow(objectlog.id)) {
								MLog.d("删除" + objectlog.id);
							}
							// 删除成功的
							MLog.d("第" + i + "个数据批量添加成功：" + result.getCreatedAt() + "," + result.getObjectId() + ","
									+ result.getUpdatedAt());
						} else {
							MLog.d("第" + i + "个数据批量添加失败：" + ex.getMessage() + "," + ex.getErrorCode());
						}

					}

				} else {
					MLog.d("上传云日志 失败 ");
				}
			}
		});
	}

	public void queryUser(bo_swy_user user, final BmobListener listener) {
		String bql = "select * from bo_swy_user where deviceid ='" + user.deviceid + "'";

		BmobQuery<bo_swy_user> query = new BmobQuery<bo_swy_user>();

		query.doSQLQuery(bql, new SQLQueryListener<bo_swy_user>() {

			@Override
			public void done(BmobQueryResult<bo_swy_user> arg0, BmobException e) {
				if (e == null) {
					if (listener != null) {
						if (arg0.getResults().size() == 0) {
							bo_swy_user boSwyUser = bo_swy_user.factory();
							boSwyUser.registerDate = Utils.getData();
							boSwyUser.save();
						} else {
							MLog.d(JSONUtil.object2Json(arg0.getResults().get(0)));
							listener.result(arg0.getResults().get(0));
						}

					}
					MLog.d("查询成功" + arg0.getResults().get(0).toString());
				} else {
					MLog.d("查询失败 ");
					MLog.d(e.getMessage());

				}
			}
		});

		// new BmobQuery<bo_swy_user>().doSQLQuery(bql, new
		// SQLQueryListener<bo_swy_user>() {
		//
		// @Override
		// public void done(BmobQueryResult<bo_swy_user> result, BmobException
		// e) {
		// if (e == null) {
		// if (listener != null) {
		// listener.result(result);
		// }
		// } else {
		// bo_swy_user boSwyUser = bo_swy_user.factory();
		// boSwyUser.registerDate = Utils.getData();
		// boSwyUser.save();
		// MLog.d("上传成功!");
		// MLog.d(e.getMessage());
		// }
		// }
		// });
	}

	BmobListener listeners;

	public void setListener(BmobListener listener) {
		this.listeners = listener;
	}

	public interface BmobListener {
		<T> void result(T object);
	}
}
