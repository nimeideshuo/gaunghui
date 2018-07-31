package com.ahjswy.cn.crash;

import java.lang.Thread.UncaughtExceptionHandler;

import com.ahjswy.cn.app.MyApplication;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.bean.bmob.ExceptionLog;
import com.ahjswy.cn.dao.Exception_logDAO;
import com.ahjswy.cn.ui.MAlertDialog;
import com.ahjswy.cn.utils.DocUtils;
import com.ahjswy.cn.utils.MLog;
import com.ahjswy.cn.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class CrashHandler implements UncaughtExceptionHandler {
	private static CrashHandler sHandler = null;
	private Context context;
	private UncaughtExceptionHandler mDefaultHandler;
	private Exception_logDAO logdao;
	private boolean crashing = false;

	public static CrashHandler getInstance() {
		if (sHandler == null) {
			sHandler = new CrashHandler();
		}
		return sHandler;
	}

	/**
	 * 初始化
	 *
	 * @param context
	 */
	public void init(Context context) {
		logdao = new Exception_logDAO();
		this.context = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	Throwable ex;
	Thread thread;

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (crashing) {
			return;
		}
		crashing = true;
		this.ex = ex;
		this.thread = thread;
		ex.printStackTrace();
		// 我们没有处理异常 并且默认异常处理不为空 则交给系统处理
		if (!handlelException(ex) && mDefaultHandler != null) {
			// 系统处理
			mDefaultHandler.uncaughtException(thread, ex);
		}
	}

	/**
	 * 处理异常消息
	 * 
	 * @param ex
	 * @return
	 */
	private boolean handlelException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		try {
			DocUtils.insertLog(ex, null);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static ExceptionLog getDefaultReqLog() {
		ExceptionLog log = new ExceptionLog();
		log.userid = SystemState.getUser().getId();
		log.username = SystemState.getUser().getName();
		log.model = android.os.Build.MODEL;
		log.versionname = MyApplication.getInstance().getVersionName();
		log.deviceid = MyApplication.getInstance().getAndroidId();
		log.datetime = Utils.getDataTime();
		log.accountset = SystemState.getAccountSet().getDatabase();
		return log;
	}

}
