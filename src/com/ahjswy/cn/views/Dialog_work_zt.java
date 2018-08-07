package com.ahjswy.cn.views;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.MyApplication;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.response.AccountSetEntity;
import com.ahjswy.cn.service.ServiceSupport;
import com.ahjswy.cn.ui.Dialog_work_zt_adapter;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class Dialog_work_zt extends Dialog {
	Context context;
	private TextView title_message;
	private ListView listview;
	private List<AccountSetEntity> listAccount;
	Dialog_work_zt_callBack callback;

	public Dialog_work_zt(Context context, int theme) {
		super(context, theme);
		this.context = context;
		init();
	}

	public Dialog_work_zt(Context context) {
		super(context);
		this.context = context;
		init();
	}

	private void init() {
		setContentView(R.layout.dialog_work_zt);
		title_message = (TextView) findViewById(R.id.tv_title_message);
		setTitle("工作帐套");
		adapter = new Dialog_work_zt_adapter(context);
		listview = (ListView) findViewById(R.id.lv_work_zt);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String accounts = (String) msg.obj;
				listAccount = JSONUtil.str2list(accounts, AccountSetEntity.class);
				if (listAccount.size() == 0 || listAccount == null) {
					PDH.showFail("不存在可用帐套");
					dismiss();
					return;
				}
				adapter.setDate(listAccount);
				listview.setAdapter(adapter);
				listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						dismiss();
						// 保存本地工作帐套
						SystemState.saveObject("accountset", listAccount.get(position));
						callback.itemClick();
					}
				});
				show();
				break;
			}

		};
	};
	private Dialog_work_zt_adapter adapter;



	/*
	 * 可以直接从 service 获取
	 */
	public void setListView(List<AccountSetEntity> list) {
	}

	public void setTitle(String message) {
		title_message.setText(message);
	}

	public void onItemCallBack(Dialog_work_zt_callBack callback) {
		this.callback = callback;
	}

	public interface Dialog_work_zt_callBack {
		public void itemClick();

	}

	public void showMe() {
		new Thread() {
			public void run() {
				String str = new ServiceSupport().sup_QueryAccountSet();
				handler.sendMessage(handler.obtainMessage(0, str));
			}
		}.start();
	}
}
