package com.ahjswy.cn.print;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.ahjswy.cn.utils.MLog;
import com.ahjswy.cn.utils.PDH;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

public class BTPrintHelper {
	private Activity activity;
	private Handler handler;
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private BluetoothSocket bluetoothSocket;
	private ConnectThread connectThread;
	private InputStream mmInputStream;
	private OutputStream mmOutputStream;
	private PrintMode mode;
	private PrintOverCall printOverCall;

	public BTPrintHelper(Activity activity) {
		this.activity = activity;
		this.handler = new Handler();
	}

	public void setMode(PrintMode mode) {
		this.mode = mode;
	}

	public void setPrintOverCall(PrintOverCall printOverCall) {
		this.printOverCall = printOverCall;
	}

	public void print(final BTPrinter printer) {
		if (printer == null) {
			PDH.showMessage("未知设备");
			return;
		}
		PDH.show(this.activity, "正在连接打印机...", new PDH.ProgressCallBack() {

			@Override
			public void action() {
				try {
					connecting(printer);
					doPrint();
				} catch (Exception e) {
					e.printStackTrace();
					handler.post(new Runnable() {

						@Override
						public void run() {
							PDH.showFail("无法连接到打印机，请在列表中选择打印机");
						}
					});
				}
			}

		});

	}

	synchronized protected void doPrint() throws IOException {
		this.mmOutputStream = this.bluetoothSocket.getOutputStream();
		this.mmInputStream = this.bluetoothSocket.getInputStream();
		this.connectThread = new ConnectThread();
		this.connectThread.start();
		this.mode.setOutputStream(this.mmOutputStream);
		this.mode.setCallback(printCallback);
		this.mode.printDoc();
		MLog.d(getClass().getName() + ":doPrint()");
	}

	protected void connecting(BTPrinter printer) throws IOException {
		BluetoothAdapter.getDefaultAdapter().enable();
		this.bluetoothSocket = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(printer.getAddress())
				.createInsecureRfcommSocketToServiceRecord(MY_UUID);
		this.bluetoothSocket.connect();
	}

	class ConnectThread extends Thread {

		@Override
		public void run() {
			super.run();
			byte[] arrayByte = new byte[1024];
			try {
				while (true) {
					int i = BTPrintHelper.this.mmInputStream.read(arrayByte);
					MLog.d("received" + new String(arrayByte, 0, i));
				}
			} catch (Exception e) {
				MLog.d("connect Thread:" + e.getMessage());
			}
		}
	}

	private PrintMode.PrintCallback printCallback = new PrintMode.PrintCallback() {
		public void printOver(final int type) {
			handler.postDelayed(new Runnable() {
				public void run() {
					closePrinter();
					if (type == 0) {
						PDH.showSuccess("打印完成");
						if (BTPrintHelper.this.printOverCall != null)
							BTPrintHelper.this.printOverCall.printOver();
						return;
					}
					PDH.showError("无法连接到打印机，请重新选择");
				}
			}, 1000L);
		}
	};

	private void closePrinter() {
		MLog.d("close...");
		if (this.connectThread != null)
			this.connectThread = null;
		try {
			if (this.bluetoothSocket != null)
				this.bluetoothSocket.close();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static abstract interface PrintOverCall {
		public abstract void printOver();
	}
}
