package com.ahjswy.cn.scaner;

import android.content.Context;
import mexxen.mx5010.barcode.BarcodeConfig;
import mexxen.mx5010.barcode.BarcodeEvent;
import mexxen.mx5010.barcode.BarcodeListener;
import mexxen.mx5010.barcode.BarcodeManager;

public class ScanerMX5020 extends Scaner {
	BarcodeConfig bc;
	BarcodeManager bm;

	public ScanerMX5020(Context context) {
		bc = new BarcodeConfig(context);
		bm = new BarcodeManager(context);
		bm.addListener(bl);
		defaultSetting();
	}

	/**
	 * 设置输出模式
	 * 
	 * BarcodeConfig.PLAYTONE_MODE_NONE
	 * 
	 * 0 复制粘贴模式
	 * 
	 * BarcodeConfig.PLAYTONE_MODE_ONLY_SOUND
	 * 
	 * 1 模拟键盘模式
	 * 
	 * BarcodeConfig.PLAYTONE_MODE_ONLY_VIBRATE
	 * 
	 * 2 不显示模式(复制在粘贴板)
	 * 
	 * BarcodeConfig.PLAYTONE_MODE_SOUND_AND_VIBRATE
	 * 
	 * @param mode
	 */
	public void setOutputMode(int mode) {
		bc.setOutputMode(mode);
	}

	@Override
	public void defaultSetting() {
		if (bc == null) {
			return;
		}
		if (!bc.isScannerOn()) {
			bc.setScanner(true);
		}
		bc.setOutputMode(BarcodeConfig.PLAYTONE_MODE_SOUND_AND_VIBRATE);
		setScanner(true);
	}

	BarcodeListener bl = new BarcodeListener() {

		@Override
		public void barcodeEvent(BarcodeEvent event) {
			if (event.getOrder().equals("SCANNER_READ")) {
				if (barcodeListener != null) {
					barcodeListener.setBarcode(bm.getBarcode().toString().trim());
				}
			}
		}
	};

	@Override
	public boolean removeListener() {
		try {
			if (bm == null) {
				return false;
			}
			bm.dismiss();
			bm.removeListener(bl);
			bm = null;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 设置扫描引擎打开或关闭
	 * 
	 * @param enable
	 */
	public void setScanner(boolean enable) {
		bc.setScanner(enable);
	}

	/**
	 * 获取扫码枪状态 是否打开
	 * 
	 * @return
	 */
	public boolean isScannerOn() {
		return bc.isScannerOn();
	}

	/**
	 * 获取扫码枪 后外扫描线是否 打开
	 * 
	 * @return
	 */
	public boolean isDecodeAimIlluminiation() {
		return bc.isDecodeAimIlluminiation();
	}

	/**
	 * 设置十字光标灯状态 state值: 0 关闭 1 打开
	 * 
	 * @param state
	 */
	public void setDecodeAimIlluminiation(boolean state) {
		bc.setDecodeAimIlluminiation(state);
	}

	/**
	 * 设置条码读取成功时的音量 vol值(0~10):音量大小
	 * 
	 * @param vol
	 */
	public void setVolume(int vol) {
		bc.setVolume(vol);
	}

}
