package com.ahjswy.cn.scaner;

import com.ahjswy.cn.utils.MLog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.device.ScanManager;
import android.media.AudioManager;
import android.media.SoundPool;

public class ScanerI6200S extends Scaner {
	private ScanManager mScanManager;
	private int soundid;
	private static final String SCAN_ACTION = ScanManager.ACTION_DECODE;
	Context context;

	public ScanerI6200S(Context context) {
		this.context = context;
		initScan();
		defaultSetting();
	}

	/**
	 * 初始化 I6000S
	 */
	@SuppressWarnings("deprecation")
	private void initScan() {
		MLog.tag("initScan");
		try {
			mScanManager = new ScanManager();
			// mScanManager.openScanner();
			soundpool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100);
			IntentFilter filter = new IntentFilter();
			filter.addAction(SCAN_ACTION);
			context.registerReceiver(mScanReceiver, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public BroadcastReceiver getMScanReceiver() {
		return mScanReceiver;
	}

	BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (soundpool != null) {
				// 播放试音
				soundpool.play(soundid, 1, 1, 0, 0, 1);
				byte[] barcode = intent.getByteArrayExtra(ScanManager.DECODE_DATA_TAG);//
				// 获得数据数组长度
				int barcodelen = intent.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0);
				String barcodeStr = new String(barcode, 0, barcodelen);
				if (barcodeListener != null) {
					barcodeListener.setBarcode(barcodeStr);
				}

			}

		}
	};
	private SoundPool soundpool;

	@Override
	public void defaultSetting() {
		if (mScanManager == null) {
			return;
		}
		mScanManager.switchOutputMode(0);
		soundid = soundpool.load("/etc/Scan_new.ogg", 1);
		setScanner(true);
	}

	@Override
	public boolean removeListener() {
		try {
			if (mScanManager != null) {
				mScanManager.stopDecode();
			}
			context.unregisterReceiver(mScanReceiver);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void setBarcodeListener(ScanerBarcodeListener barcodeListener) {
		super.setBarcodeListener(barcodeListener);
	}

	@Override
	public void setScanner(boolean enable) {
		if (enable) {
			if (!isScannerOn()) {
				mScanManager.openScanner();
			}
		} else {
			if (isScannerOn()) {
				mScanManager.closeScanner();
			}
		}
	}

	@Override
	public boolean isScannerOn() {
		return mScanManager.getScannerState();
	}

	@Override
	public boolean isDecodeAimIlluminiation() {
		return super.isDecodeAimIlluminiation();
	}

	@Override
	public void setDecodeAimIlluminiation(boolean state) {
		super.setDecodeAimIlluminiation(state);
	}

	@Override
	public void setVolume(int vol) {

	}

}
