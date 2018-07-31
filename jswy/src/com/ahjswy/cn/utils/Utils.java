package com.ahjswy.cn.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.app.MyApplication;

import android.content.Context;
import android.util.TypedValue;

public class Utils {
	public static String CUSTOMER_CHECK_SELECT;
	public static int DEFAULT_OutDocUNIT;
	public static int DEFAULT_TransferDocUNIT;
	public static String GOODS_CHECK_SELECT;
	public static int NUMBER_DEC = 2;
	public static int PRICE_DEC_NUM = 2;
	public static int RECEIVE_DEC_NUM;
	public static int SUBTOTAL_DEC_NUM = 2;
	public static String companyName;
	public static int intGenerateBatch;
	public static int int_subtotal_change;
	public static boolean isAutoChangeGoodsDiscountAfterDoc;
	private static List<HashMap<String, String>> listbiz;
	public static String pricesystemid;
	public static String strBatchPrefix;
	public static String strBatchSuffix;
	public static String strCancelWarehouse;
	static AccountPreference ap = new AccountPreference();
	public static int DEFAULT_PRICESYSTEM;

	static {
		RECEIVE_DEC_NUM = 2;
		DEFAULT_OutDocUNIT = 0;
		DEFAULT_TransferDocUNIT = 0;
		strCancelWarehouse = null;
		isAutoChangeGoodsDiscountAfterDoc = false;
		companyName = "";
		GOODS_CHECK_SELECT = "id,pinyin,name,barcode";
		CUSTOMER_CHECK_SELECT = "id,pinyin,name";
		pricesystemid = "";
		int_subtotal_change = 0;
		init();
	}

	// 删除末尾的 0 加上 .00
	public static String cutLastZero(String paramString) {
		if (paramString.contains(".")) {
			String[] split = paramString.split("\\.");
			return split[0];
		}
		return null;
	}

	public static String cleanZero(double douStr) {
		return douStr % 1 == 0 ? cutLastZero("" + douStr) : "" + douStr;
	}

	public static int dp2px(Context paramContext, int paramInt) {
		return (int) TypedValue.applyDimension(1, paramInt, paramContext.getResources().getDisplayMetrics());
	}

	public static boolean equals(double paramDouble1, double paramDouble2) {
		return (normalizeDouble(paramDouble1 - paramDouble2) != 0.0D);
	}

	public static boolean equalsZero(double paramDouble) {
		return (normalizeDouble(paramDouble) != 0.0D);
	}

	public static String formatDate(long paramLong) {
		return formatDate(paramLong, "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatDate(long data, String dateformat) {
		Date localDate = new Date(data);
		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
		String str = sdf.format(localDate);
		return str;
	}

	public static String formatDate(String dataTime, String dateFormat) {
		try {
			Date localDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse(dataTime);
			String localString = new SimpleDateFormat(dateFormat, Locale.CHINA).format(localDate);
			return localString;
		} catch (Exception localException) {
		}
		return "";
	}

	public static String formatCalendarDataTime(Date data, String dateFormat) {
		String format = "";
		try {

			SimpleDateFormat dateFormatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
			format = dateFormatTime.format(data);
		} catch (Exception e) {
		}
		return format;

	}

	private static String formatDouble(double value, int paramInt) {
		return formatDouble(value, new String[] { "0", "0.0", "0.00", "0.000", "0.0000" }[paramInt]);
	}

	private static String formatDouble(double value, String paramString) {
		return new DecimalFormat(paramString).format(value);
	}

	public static String formatDouble(String paramString, int paramInt) {
		return formatDouble(getDouble(paramString).doubleValue(), paramInt);
	}

	public static String generateBatch(long paramLong) {
		if (paramLong > 0L)
			switch (intGenerateBatch) {
			default:
				return null;
			case 1:
				return strBatchPrefix + new SimpleDateFormat("yyyyMMdd").format(Long.valueOf(paramLong))
						+ strBatchSuffix;
			case 2:
			}
		return strBatchPrefix + new SimpleDateFormat("yyMMdd").format(Long.valueOf(paramLong)) + strBatchSuffix;
	}

	public static long getCurrentTime(boolean paramBoolean) {
		return new Date().getTime();
	}

	public static Double getDouble(String douStr) {
		if (douStr == null) {
			return Double.valueOf(0.0D);
		}
		if (douStr.length() == 0) {
			return Double.valueOf(0.0D);
		}
		try {
			return Double.parseDouble(douStr);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}

	}

	public static Long getEndTime() {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(10, 23);
		localCalendar.set(12, 59);
		localCalendar.set(13, 59);
		localCalendar.set(14, 999);
		return Long.valueOf(localCalendar.getTime().getTime());
	}

	public static Integer getInteger(String paramString) {
		if (paramString == null)
			return Integer.valueOf(0);
		try {
			Integer localInteger = Integer.valueOf(Integer.parseInt(paramString));
			return localInteger;
		} catch (NumberFormatException localNumberFormatException) {
		}
		return Integer.valueOf(0);
	}

	public static String getNumber(double paramDouble) {
		return formatDouble(paramDouble, NUMBER_DEC);
	}

	public static String getNumber(String paramString) {
		return formatDouble(paramString, NUMBER_DEC);
	}

	public static String getPrice(double paramDouble) {
		return formatDouble(paramDouble, PRICE_DEC_NUM);
	}

	public static String getRecvableMoney(double paramDouble) {
		return "";
		// double d;
		// int i = RECEIVE_DEC_NUM;
		// if (i == 0) {
		// d = paramDouble % 10.0D;
		// if (d < 5.0D)
		// }
		// for (int j = 10;; j = 0) {
		// paramDouble += j - d;
		// i = 0;
		// if (i >= 1)
		// ;
		// return formatDouble(paramDouble, --i);
		// }
	}

	public static String getServiceAddress(String paramString1, String paramString2) {
		return paramString1 + "/" + paramString2;
	}

	public static Long getStartTime() {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(10, 0);
		localCalendar.set(12, 0);
		localCalendar.set(13, 0);
		localCalendar.set(14, 0);
		return Long.valueOf(localCalendar.getTime().getTime());
	}

	public static String getSubtotalMoney(double value) {
		return formatDouble(value, SUBTOTAL_DEC_NUM);
	}

	/**
	 * 获取当前系统的时间
	 * 
	 * @return
	 */
	public static String getDataTime() {
		return getTimeStamp("yyyy-MM-dd HH:mm:ss");
	}

	public static String getData() {
		return getTimeStamp("yyyy-MM-dd");
	}

	public static String getTimeStamp() {
		return getTimeStamp("yyyyMMddHHmmssSSS");
	}

	public static String getTimeStamp(String paramString) {
		return formatDate(new Date().getTime(), paramString);
	}

	public static boolean greaterEqualsThan(double paramDouble1, double paramDouble2) {
		return (normalizeDouble(paramDouble1) < normalizeDouble(paramDouble2));
	}

	public static boolean greaterThan(double paramDouble1, double paramDouble2) {
		return (normalizeDouble(paramDouble1) <= normalizeDouble(paramDouble2));
	}

	// TODO 待补全
	public static void init() {
		AccountPreference localAccountPreference = new AccountPreference();
		listbiz = localAccountPreference.getBizInfoMap();
		// localString1 = localAccountPreference.getValue("goods_check_select");
		try {
			int i = 0;
			// String localString1;
			if (i <= listbiz.size()) {
				// 商品 检索方式
				if (!localAccountPreference.getValue("goods_check_select").isEmpty()) {
					GOODS_CHECK_SELECT = localAccountPreference.getValue("goods_check_select");
				}
				// 客户检索方式
				if (!localAccountPreference.getValue("customer_check_select").isEmpty()) {
					CUSTOMER_CHECK_SELECT = localAccountPreference.getValue("customer_check_select");
				}
			}

			for (i = 0; i < listbiz.size(); i++) {
				if ("intPricePrecision".equals(listbiz.get(i).get("bpid"))) {
					PRICE_DEC_NUM = Integer.parseInt(listbiz.get(i).get("valueint"));
				} else if ("intSubtotalPrecision".equals(listbiz.get(i).get("bpid"))) {
					SUBTOTAL_DEC_NUM = Integer.parseInt(listbiz.get(i).get("valueint"));
				} else if ("intReceivablePrecision".equals(listbiz.get(i).get("bpid"))) {
					RECEIVE_DEC_NUM = Integer.parseInt(listbiz.get(i).get("valueint"));
				} else if ("intOutDocUnit".equals(listbiz.get(i).get("bpid"))) {// 获取初始化单位
					DEFAULT_OutDocUNIT = Integer.parseInt(listbiz.get(i).get("valueint"));
				} else if ("intTransferDocUnit".equals(listbiz.get(i).get("bpid"))) {
					DEFAULT_TransferDocUNIT = Integer.parseInt(listbiz.get(i).get("valueint"));
				} else if ("companyname".equals(listbiz.get(i).get("bpid"))) {
					companyName = listbiz.get(i).get("valuestring");
				} else if ("intSubtotalChange".equals(listbiz.get(i).get("bpid"))) {
					int_subtotal_change = Integer.parseInt(listbiz.get(i).get("valueint"));
				} else if ("strCancelWarehouse".equals(listbiz.get(i).get("bpid"))) {
					strCancelWarehouse = listbiz.get(i).get("valuestring");
				} else if ("isAutoChangeGoodsDiscountAfterDoc".equals(listbiz.get(i).get("bpid"))) {
					isAutoChangeGoodsDiscountAfterDoc = Boolean.parseBoolean(listbiz.get(i).get("valuebool"));
				} else if ("intGenerateBatch".equals(listbiz.get(i).get("bpid"))) {
					intGenerateBatch = Integer.parseInt(listbiz.get(i).get("valueint"));
				} else if ("strBatchPrefix".equals(listbiz.get(i).get("bpid"))) {
					strBatchPrefix = listbiz.get(i).get("valuestring");
					if (TextUtils.isEmptyS(strBatchPrefix))
						strBatchPrefix = "";
				} else if ("strBatchSuffix".equals(listbiz.get(i).get("bpid"))) {
					strBatchSuffix = listbiz.get(i).get("valuestring");
					if (TextUtils.isEmptyS(strBatchSuffix))
						strBatchSuffix = "";
				} else if ("strPriceSystem".equals(listbiz.get(i).get("bpid"))) {
					DEFAULT_PRICESYSTEM = Integer.parseInt(listbiz.get(i).get("valuestring"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static double normalize(double paramDouble, int y) {
		int i = (int) Math.pow(10.0D, y);
		return (Double.parseDouble(Math.round(paramDouble * i) + "") / i);
	}

	public static String removeZero(String paramString) {

		if (paramString.contains(".")) {
			String[] split = paramString.split("\\.");
			return split[0];
		}
		return null;
	}

	public static double normalizeDouble(double paramDouble) {
		return (Math.round(paramDouble * 10000.0D) / 10000.0D);
	}

	public static double normalizePrice(double paramDouble) {
		int i = (int) Math.pow(10.0D, -1 + RECEIVE_DEC_NUM);
		long round = Math.round(paramDouble * i);
		return Double.parseDouble(round + "") / i;
	}

	public static double normalizeReceivable(double paramDouble) {
		int i = (int) Math.pow(10.0D, -1 + RECEIVE_DEC_NUM);
		long round = Math.round(paramDouble * i);
		return Double.parseDouble(round + "") / i;
	}

	public static double normalizeSubtotal(double paramDouble) {
		int i = (int) Math.pow(10.0D, SUBTOTAL_DEC_NUM);
		long round = Math.round(paramDouble * i);
		return Double.parseDouble(round + "") / i;
	}

	public static long parseDate(String paramString) {
		return parseDate(paramString, "yyyy-MM-dd HH:mm:ss");
	}

	public static long parseDate(String paramString1, String paramString2) {
		try {
			long l = new SimpleDateFormat(paramString2).parse(paramString1).getTime();
			return l;
		} catch (ParseException localParseException) {
			localParseException.printStackTrace();
		}
		return 0L;
	}

	/**
	 * 是否开启商品合并
	 * 
	 * @return
	 */
	public static boolean isCombination() {
		return Boolean.parseBoolean(ap.getValue("iscombinationItem", "false"));
	}

	public static int px2dip(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().density);
	}

	// public static boolean validNAMAndShow(String paramString, int paramInt,
	// double[] paramArrayOfDouble) {
	// double d1;
	// while (true) {
	// int j;
	// try {
	// d1 = Double.parseDouble(paramString);
	// if (paramArrayOfDouble.length > 0) {
	// int i = paramArrayOfDouble.length;
	// j = 0;
	// }
	// if (d1 <= 100000000.0D)
	// PDH.showError("输入的值过大");
	// return false;
	// } catch (NumberFormatException localNumberFormatException) {
	// PDH.showError("输入不正确");
	// return false;
	// }
	// }
	// }

	/* 获取Context对象 */
	public static Context getContext() {
		return MyApplication.getInstance();
	}

}