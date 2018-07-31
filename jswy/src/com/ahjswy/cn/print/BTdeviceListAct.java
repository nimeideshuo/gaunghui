package com.ahjswy.cn.print;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.dao.FieldSaleDAO;
import com.ahjswy.cn.model.FieldSaleForPrint;
import com.ahjswy.cn.model.FieldSaleItemForPrint;
import com.ahjswy.cn.print.BTPrintHelper.PrintOverCall;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.PDH;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BTdeviceListAct extends BaseActivity implements OnItemClickListener {
	private ListView listView;
	private BluetoothAdapter mBtAdapter;
	private int type;// 1是测试打印，0 正常打印
	private List<FieldSaleItemForPrint> items;
	private FieldSaleForPrint doc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = new ListView(this);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, 600);
		this.listView.setLayoutParams(params);
		setContentView(this.listView);
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		type = getIntent().getIntExtra("type", 0);
		// doc = ((FieldSaleForPrint) getIntent().getSerializableExtra("doc"));
		// items = JSONUtil.str2list(getIntent().getStringExtra("items"),
		// FieldSaleItemForPrint.class);
		if (this.mBtAdapter == null) {
			PDH.showError("蓝牙不存在或未开启");
			return;
		}
		if (!mBtAdapter.isEnabled()) {
			mBtAdapter.enable();
		}
		IntentFilter localIntentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
		registerReceiver(this.mReceiver, localIntentFilter);
		devicesAdapter = new ArrayAdapter<BTPrinter>(this, R.layout.item_textview);
		listView.setAdapter(devicesAdapter);
		listView.setOnItemClickListener(this);
		mBtAdapter.startDiscovery();
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if ("android.bluetooth.device.action.FOUND".equals(intent.getAction())) {
				BluetoothDevice device = (BluetoothDevice) intent
						.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
				BTPrinter printer = new BTPrinter(device.getName(), device.getAddress());
				devicesAdapter.add(printer);
			}
		}
	};

	private ArrayAdapter<BTPrinter> devicesAdapter;

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		cancelScan();
		BTPrinter printer = devicesAdapter.getItem(position);
		new AccountPreference().savePrinter(printer);
		if (type == 2) {
			setResult(RESULT_OK, new Intent());
			finish();
			return;
		}
		BTPrintHelper printHelper = new BTPrintHelper(this);
		PrintMode printMode = PrintMode.getPrintMode();
		if (printMode == null) {
			PDH.showFail("未发现打印模版");
			return;
		}
		if (this.type == 1) {
			PrintData printData = new PrintData();
			printMode.setDatainfo(printData.getTestData());
			printMode.setDocInfo(printData.getTestInfo());
		} else {
			printMode.setDatainfo(this.items);
			printMode.setDocInfo(this.doc);
			printHelper.setPrintOverCall(new PrintOverCall() {
				public void printOver() {
					BTdeviceListAct.this.finish();
					new FieldSaleDAO().updateDocValue(doc.getId(), "printnum", "1");
				}
			});
		}
		printHelper.setMode(printMode);
		printHelper.print(printer);
	}

	private void cancelScan() {
		if ((this.mBtAdapter != null) && (this.mBtAdapter.isDiscovering()))
			this.mBtAdapter.cancelDiscovery();
	}

	protected void onDestroy() {
		mBtAdapter.cancelDiscovery();
		unregisterReceiver(this.mReceiver);
		super.onDestroy();
	}

	public void setActionBarText() {
		setTitle("设定打印机");
	}
}
