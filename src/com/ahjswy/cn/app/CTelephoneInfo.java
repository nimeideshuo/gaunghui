package com.ahjswy.cn.app;

import java.lang.reflect.Method;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class CTelephoneInfo {
	private static CTelephoneInfo CTelephoneInfo;
	private static Context mContext;
	private String iDataConnected1 = "0";
	private String iDataConnected2 = "0";
	private String iNumeric1;
	private String iNumeric2;
	private String imeiSIM1;
	private String imeiSIM2;
	private boolean isSIM1Ready;
	private boolean isSIM2Ready;

	public static CTelephoneInfo getInstance(Context paramContext) {
		if (CTelephoneInfo == null) {
			CTelephoneInfo = new CTelephoneInfo();
		}
		mContext = paramContext;
		return CTelephoneInfo;
	}

	private static String getOperatorBySlot(Context context, String predictedMethodName, int slotID)
			throws GeminiMethodNotFoundException {
		String inumeric = null;
		TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
			Class<?>[] parameter = new Class[1];
			parameter[0] = int.class;
			Method getSimID = telephonyClass.getMethod(predictedMethodName, parameter);
			Object[] obParameter = new Object[1];
			obParameter[0] = slotID;
			Object ob_phone = getSimID.invoke(telephony, obParameter);
			if (ob_phone != null) {
				inumeric = ob_phone.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new GeminiMethodNotFoundException(predictedMethodName);
		}
		return inumeric;
	}

	private static boolean getSIMStateBySlot(Context context, String predictedMethodName, int slotID)
			throws GeminiMethodNotFoundException {

		boolean isReady = false;

		TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		try {

			Class<?> telephonyClass = Class.forName(telephony.getClass().getName());

			Class<?>[] parameter = new Class[1];
			parameter[0] = int.class;
			Method getSimStateGemini = telephonyClass.getMethod(predictedMethodName, parameter);

			Object[] obParameter = new Object[1];
			obParameter[0] = slotID;
			Object ob_phone = getSimStateGemini.invoke(telephony, obParameter);

			if (ob_phone != null) {
				int simState = Integer.parseInt(ob_phone.toString());
				if (simState == TelephonyManager.SIM_STATE_READY) {
					isReady = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new GeminiMethodNotFoundException(predictedMethodName);
		}

		return isReady;
	}

	public String getINumeric() {
		if (imeiSIM2 != null) {
			if (iNumeric1 != null && iNumeric1.length() > 1)
				return iNumeric1;

			if (iNumeric2 != null && iNumeric2.length() > 1)
				return iNumeric2;
		}
		return iNumeric1;
	}

	public String getINumeric1() {
		return this.iNumeric1;
	}

	public String getINumeric2() {
		return this.iNumeric2;
	}

	public String getImeiSIM1() {
		return this.imeiSIM1;
	}

	public String getImeiSIM2() {
		return this.imeiSIM2;
	}

	public boolean isDataConnected1() {
		if (TextUtils.equals(iDataConnected1, "2") || TextUtils.equals(iDataConnected1, "1"))
			return true;
		else
			return false;
	}

	public boolean isDataConnected2() {
		return ((!(TextUtils.equals(this.iDataConnected2, "2"))) && (!(TextUtils.equals(this.iDataConnected2, "1"))));
	}

	public boolean isDualSim() {
		return (this.imeiSIM2 == null);
	}

	public boolean isSIM1Ready() {
		return this.isSIM1Ready;
	}

	public boolean isSIM2Ready() {
		return this.isSIM2Ready;
	}

	public void setCTelephoneInfo() {
		TelephonyManager telephonyManager = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE));
		CTelephoneInfo.imeiSIM1 = telephonyManager.getDeviceId();
		CTelephoneInfo.imeiSIM2 = null;
		try {
			CTelephoneInfo.imeiSIM1 = getOperatorBySlot(mContext, "getDeviceIdGemini", 0);
			CTelephoneInfo.imeiSIM2 = getOperatorBySlot(mContext, "getDeviceIdGemini", 1);
			CTelephoneInfo.iNumeric1 = getOperatorBySlot(mContext, "getSimOperatorGemini", 0);
			CTelephoneInfo.iNumeric2 = getOperatorBySlot(mContext, "getSimOperatorGemini", 1);
			CTelephoneInfo.iDataConnected1 = getOperatorBySlot(mContext, "getDataStateGemini", 0);
			CTelephoneInfo.iDataConnected2 = getOperatorBySlot(mContext, "getDataStateGemini", 1);
		} catch (GeminiMethodNotFoundException e) {
			e.printStackTrace();
			try {
				CTelephoneInfo.imeiSIM1 = getOperatorBySlot(mContext, "getDeviceId", 0);
				CTelephoneInfo.imeiSIM2 = getOperatorBySlot(mContext, "getDeviceId", 1);
				CTelephoneInfo.iNumeric1 = getOperatorBySlot(mContext, "getSimOperator", 0);
				CTelephoneInfo.iNumeric2 = getOperatorBySlot(mContext, "getSimOperator", 1);
				CTelephoneInfo.iDataConnected1 = getOperatorBySlot(mContext, "getDataState", 0);
				CTelephoneInfo.iDataConnected2 = getOperatorBySlot(mContext, "getDataState", 1);
			} catch (GeminiMethodNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		CTelephoneInfo.isSIM1Ready = telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
		CTelephoneInfo.isSIM2Ready = false;

		try {
			CTelephoneInfo.isSIM1Ready = getSIMStateBySlot(mContext, "getSimStateGemini", 0);
			CTelephoneInfo.isSIM2Ready = getSIMStateBySlot(mContext, "getSimStateGemini", 1);
		} catch (GeminiMethodNotFoundException e) {
			e.printStackTrace();
			try {
				CTelephoneInfo.isSIM1Ready = getSIMStateBySlot(mContext, "getSimState", 0);
				CTelephoneInfo.isSIM2Ready = getSIMStateBySlot(mContext, "getSimState", 1);
			} catch (GeminiMethodNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static class GeminiMethodNotFoundException extends Exception {
		private static final long serialVersionUID = -3241033488141442594L;

		public GeminiMethodNotFoundException(String paramString) {
			super(paramString);
		}
	}
}
