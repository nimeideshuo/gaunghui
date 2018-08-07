package com.ahjswy.cn.scaner;

import android.content.BroadcastReceiver;
import android.content.Context;

public class Scaner {

	public Scaner() {
	}

	public static Scaner factory(Context context) {
		String model = android.os.Build.MODEL;
		switch (model) {
		case "MX-5020":
			return new ScanerMX5020(context);
		case "Data Terminal":
			return new ScanerMX5060(context);
		case "DataTerminal":
			return new ScanerMX5020(context);
		case "i6200S":
			return new ScanerI6200S(context);
		case "SQ43":
			return new ScanerI6200S(context);
		default:
			break;
		}
		return new ScanerMX5020(context);
	}

	/**
	 * 删除监听
	 */
	public boolean removeListener() {
		return false;
	}

	public void defaultSetting() {

	}

	public BroadcastReceiver getMScanReceiver() {
		return null;
	}

	ScanerBarcodeListener barcodeListener;

	public void setBarcodeListener(ScanerBarcodeListener barcodeListener) {
		this.barcodeListener = barcodeListener;
	}

	public interface ScanerBarcodeListener {
		public void setBarcode(String barcode);
	}

	/**
	 * 设置扫描引擎打开或关闭
	 * 
	 * @param enable
	 */
	public void setScanner(boolean enable) {
	}

	/**
	 * 获取扫码枪状态 是否打开
	 * 
	 * @return
	 */
	public boolean isScannerOn() {
		return false;
	}

	/**
	 * 获取扫码枪 后外扫描线是否 打开
	 * 
	 * @return
	 */
	public boolean isDecodeAimIlluminiation() {
		return false;
	}

	/**
	 * 设置十字光标灯状态 state值: 0 关闭 1 打开
	 * 
	 * @param state
	 */
	public void setDecodeAimIlluminiation(boolean state) {
	}

	/**
	 * 设置条码读取成功时的音量 vol值(0~10):音量大小
	 * 
	 * @param vol
	 */
	public void setVolume(int vol) {
	}

}
