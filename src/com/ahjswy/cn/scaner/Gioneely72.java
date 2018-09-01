package com.ahjswy.cn.scaner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class Gioneely72 extends Scaner {
	private ScannerInerface sacnnerSetting;
	Context mContext;

	public Gioneely72(Context context) {
		mContext = context;
		sacnnerSetting = new ScannerInerface(context);
		sacnnerSetting.setOutputMode(1);
		IntentFilter filter = new IntentFilter("android.intent.action.SCANRESULT");
		context.registerReceiver(mScanReceiver, filter);
	}

	BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 获取扫描的内容
			if (barcodeListener != null) {
				String barocode = intent.getStringExtra("value");
				barcodeListener.setBarcode(barocode);
			}
		}

	};

	@Override
	public boolean removeListener() {
		if (mScanReceiver != null) {
			mContext.unregisterReceiver(mScanReceiver); // 反注册广播接收者
		}
		return true;
	}

}
