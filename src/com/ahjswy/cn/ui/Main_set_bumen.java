package com.ahjswy.cn.ui;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.model.Department;
import com.ahjswy.cn.service.ServiceSupport;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Main_set_bumen extends Dialog implements OnItemClickListener {

	private TextView tv_title_message;
	Context context;
	BumenCall bumenCall;

	public Main_set_bumen(Context context, int theme, BumenCall bumenCall) {
		super(context, theme);
		this.context = context;
		this.bumenCall = bumenCall;
		setCanceledOnTouchOutside(false);
		initView();
	}

	public Main_set_bumen(Context context) {
		super(context);
		initView();
	}

	private ListView lv_work_zt;

	private void initView() {
		setContentView(R.layout.dialog_work_zt);
		tv_title_message = (TextView) findViewById(R.id.tv_title_message);
		lv_work_zt = (ListView) findViewById(R.id.lv_work_zt);
		tv_title_message.setText("部门选择");
		// 设置item 背景 被点击的颜色
		lv_work_zt.setSelector(R.drawable.hide_listview_yellow_selector);
		lv_work_zt.setOnItemClickListener(this);
		new AccountPreference();
	}

	private List<Department> listBm;
	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String bumen = msg.obj.toString();
				listBm = JSONUtil.str2list(bumen, Department.class);
				List<String> list = new ArrayList<String>();
				if (listBm.size() != 0) {
					for (int i = 0; i < listBm.size(); i++) {
						list.add(listBm.get(i).getDname());
					} // android.R.layout.simple_list_item_1
					ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(context,
							android.R.layout.simple_list_item_1, list);
					lv_work_zt.setAdapter(myAdapter);
					show();
				} else {
					PDH.showFail("不存在部门,请在服务器上添加");
					dismiss();
				}
				break;
			}
		};
	};

	public void showMe() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				String str = new ServiceSupport().sup_QueryDepartment(null, false);
				handler.sendMessage(handler.obtainMessage(0, str));
			}

		}.start();
	}

	// 保存 部门 选择
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 保存部门
		SystemState.saveObject("department", listBm.get(position));
		dismiss();
		bumenCall.callBack();
	}

	public interface BumenCall {
		public void callBack();
	}
}
