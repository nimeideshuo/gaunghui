package com.ahjswy.cn;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.app.SystemState;
import com.ahjswy.cn.cldb.CloudDBBase;
import com.ahjswy.cn.dao.BaseDao;
import com.ahjswy.cn.dao.DepartmentDAO;
import com.ahjswy.cn.dao.WarehouseDAO;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.model.Department;
import com.ahjswy.cn.model.Warehouse;
import com.ahjswy.cn.print.BTPrintHelper;
import com.ahjswy.cn.print.BTPrintHelper.PrintOverCall;
import com.ahjswy.cn.print.BTPrinter;
import com.ahjswy.cn.print.BTdeviceListAct;
import com.ahjswy.cn.print.PrintData;
import com.ahjswy.cn.print.PrintMode;
import com.ahjswy.cn.request.ReqSynUpdateInfo;
import com.ahjswy.cn.service.ServicePayType;
import com.ahjswy.cn.service.ServiceSynchronize;
import com.ahjswy.cn.ui.AboutActivity;
import com.ahjswy.cn.utils.InfoDialog;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.PDH.ProgressCallBack;
import com.ahjswy.cn.utils.SwyUtils;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.UpdateUtils;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.Dialog_ed_message;
import com.ahjswy.cn.views.Dialog_message;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class PrefsFragment extends PreferenceFragment {
	private AccountPreference ap;
	final String[] net_setting = { "不允许(推荐)", "允许" };
	final String[] goods_select_more = { "自由输入", "统一输入" };
	final String[] orders = { "默认", "编号", "名称", "条码", "类别" };
	String[] printModeitems = { "模板58", "模板80" };
	private String netSelectTitle;
	private String lastTime;
	private String goodsSelectTitle;
	private String workDepartmentTitle;
	private String workWarehouseTitle;
	private String customerSearchTitle;
	private String docItemOrderTitile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting);
		ap = new AccountPreference();
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setProgressStyle(1);
		progressDialog.setCancelable(false);
		initView();
		intiValue();
	}

	private void initView() {
		getMyPreference(R.string.load_basic_data).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.clear_database).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.work_department).setOnPreferenceClickListener(this.preferenceClickListener);
		// getMyPreference(R.string.customer_check_select).setOnPreferenceClickListener(this.preferenceClickListener);
		// getMyPreference(R.string.goods_check_select).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.work_warehouse).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.net_setting).setOnPreferenceClickListener(this.preferenceClickListener);
		// getMyPreference(R.string.item_order).setOnPreferenceClickListener(this.preferenceClickListener);
		// getMyPreference(R.string.goods_select_more_add).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.instruckment).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.service_port).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.db_port).setOnPreferenceClickListener(this.preferenceClickListener);
		// 打印机
		getMyPreference(R.string.default_printer).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.printer_model).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.print_mode).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.clearlastzero).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.minustuihuo).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.printbarcode).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.clearlastzero).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.printer_model).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.bluetoothPrintIsShow).setOnPreferenceClickListener(this.preferenceClickListener);
		getMyPreference(R.string.combinationItem).setOnPreferenceClickListener(this.preferenceClickListener);
	}

	private void intiValue() {
		netSelectTitle = getResources().getString(R.string.net_select);
		lastTime = getResources().getString(R.string.xlistview_header_last_time);
		goodsSelectTitle = getResources().getString(R.string.goods_select_more_add_string);
		workDepartmentTitle = getResources().getString(R.string.work_department_set);
		workWarehouseTitle = getResources().getString(R.string.work_warehouse_nobatch);
		customerSearchTitle = getResources().getString(R.string.customer_search_key);
		docItemOrderTitile = getResources().getString(R.string.doc_item_order_key);
		getMyPreference(R.string.load_basic_data)
				.setSummary(TextUtils.setTextStyle(lastTime, ap.getValue("basic_data_updatitme", "未同步")));
		// 网络设置
		String netSelect = "1".equals(ap.getValue("net_setting")) ? "允许" : "不允许";
		getMyPreference(R.string.net_setting).setSummary(TextUtils.setTextStyle(netSelectTitle, netSelect));
		// 商品多选模式
		// StringBuilder sbGoodsCheck = new StringBuilder();
		// String goodsCheck = this.ap.getValue("goods_check_select");
		// if (TextUtils.isEmptyS(goodsCheck)) {
		// String[] goodItems = goodsCheck.split(",");
		// for (int v3 = 0; v3 < goodItems.length; ++v3) {
		// int v4;
		// for (v4 = 0; v4 < SystemState.goods_select_keys.length; ++v4) {
		// if (goodItems[v3].equals(SystemState.goods_select_keys[v4])) {
		// sbGoodsCheck.append(String.valueOf(SystemState.goods_select_items[v4])
		// + "、");
		// }
		// }
		// }
		// } else {
		// sbGoodsCheck.append("编号、名称、拼音、条码,");
		// }
		// getMyPreference(R.string.goods_check_select)
		// .setSummary(TextUtils.setTextStyle(getResources().getString(R.string.goods_search_key),
		// sbGoodsCheck.substring(0, sbGoodsCheck.length() - 1)));
		// 客户检索方式
		// StringBuilder sbCustomerCheck = new StringBuilder();
		// String customerCheck = this.ap.getValue("customer_check_select");
		// if (TextUtils.isEmptyS(customerCheck)) {
		// String[] customerItems = customerCheck.split(",");
		// for (int v3 = 0; v3 < customerItems.length; ++v3) {
		// for (int v4 = 0; v4 < SystemState.customer_select_keys.length; ++v4)
		// {
		// if (customerItems[v3].equals(SystemState.customer_select_keys[v4])) {
		// sbCustomerCheck.append(String.valueOf(SystemState.customer_select_items[v4])
		// + "、");
		// }
		// }
		// }
		// } else {
		// sbCustomerCheck.append("编号、名称、拼音,");
		// }
		//
		// getMyPreference(R.string.customer_check_select).setSummary(TextUtils.setTextStyle(customerSearchTitle,
		// sbCustomerCheck.substring(0, sbCustomerCheck.length() - 1)));
		// // 商品多选模式
		// String goodsSelect = "1".equals(this.ap.getValue("goods_select_more",
		// "0")) ? "统一输入" : "自由输入";
		// getMyPreference(R.string.goods_select_more_add)
		// .setSummary(TextUtils.setTextStyle(goodsSelectTitle, goodsSelect));
		// 工作部门
		getMyPreference(R.string.work_department)
				.setSummary(TextUtils.setTextStyle(workDepartmentTitle, SystemState.getDepartment().getDname()));

		if (SystemState.getWarehouse() != null) {
			this.getMyPreference(R.string.work_warehouse)
					.setSummary(TextUtils.setTextStyle(workWarehouseTitle, SystemState.getWarehouse().getName()));
		} else {
			this.getMyPreference(R.string.work_warehouse).setSummary(TextUtils.setTextStyle(workWarehouseTitle, "未设置"));
		}
		this.itemorderposition = Utils.getInteger(this.ap.getValue("item_order", "0")).intValue();

		// this.getMyPreference(R.string.item_order)
		// .setSummary(TextUtils.setTextStyle(docItemOrderTitile,
		// this.orders[this.itemorderposition]));
		BTPrinter printer = ap.getPrinter();
		if (printer != null) {
			getMyPreference(R.string.default_printer).setSummary(printer.getName() + "[" + printer.getAddress() + "]");
		}
		String clearlastzero = "0".equals(ap.getValue("clearlastzero", "0")) ? "不保留末尾零" : "保留末尾零";
		getMyPreference(R.string.clearlastzero)
				.setSummary(TextUtils.setTextStyle(getResources().getString(R.string.clear_last_zero), clearlastzero));
		// 打印数值
		String showText = ap.getValue("minustuihuo", "0").equals("0") ? "显示为负数" : "显示为正数";
		getMyPreference(R.string.minustuihuo)
				.setSummary(TextUtils.setTextStyle(getResources().getString(R.string.cancel_goods_show), showText));
		getMyPreference(R.string.combinationItem)
				.setSummary(TextUtils.setTextStyle(getResources().getString(R.string.combinationItem_on_off),
						Boolean.parseBoolean(ap.getValue("iscombinationItem")) == true ? "开启" : "关闭"));
		// 是否显示条码
		String isShowBarcode = ap.getValue("printbarcode", "0").equals("0") ? "不打印" : "打印";
		getMyPreference(R.string.printbarcode).setSummary(
				TextUtils.setTextStyle(getResources().getString(R.string.print_barcode_set), isShowBarcode));
		// 打印机型号
		String printerModel = ap.getValue("printermodel_default", "epson").equals("epson") ? "标准型号" : "打印机型号一";
		getMyPreference(R.string.printer_model).setSummary(
				TextUtils.setTextStyle(getResources().getString(R.string.printer_model_select), printerModel));
		// 选择打印模板
		int printModeType = Integer.parseInt(ap.getValue("printModeType", "0"));
		getMyPreference(R.string.print_mode).setSummary(TextUtils
				.setTextStyle(getResources().getString(R.string.printer_type_select), printModeitems[printModeType]));
		String isShow = Boolean.parseBoolean(ap.getValue("bluetoothPrintIsShow")) == true ? "显示" : "不显示";
		getMyPreference(R.string.bluetoothPrintIsShow)

		.setSummary(TextUtils.setTextStyle(getResources().getString(R.string.bluetoothPrint_on_off), isShow));

		getMyPreference(R.string.service_port)
				.setSummary(TextUtils.setTextStyle("端口设置:", ap.getCGServerPort().isEmpty() ? "未设置" : "已设置"));

		getMyPreference(R.string.db_port)
				.setSummary(TextUtils.setTextStyle("数据库端口设置:", ap.getDBServerPort().isEmpty() ? "未设置" : "已设置"));
	}

	public Preference getMyPreference(int id) {
		return getPreferenceManager().findPreference(getString(id));
	}

	List<Department> listDepartment;
	public List<Warehouse> warehouses;
	private Preference.OnPreferenceClickListener preferenceClickListener = new Preference.OnPreferenceClickListener() {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			if (preference.getKey().equals(getString(R.string.load_basic_data))) {
				loadData();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.clear_database))) {
				clearDataBase();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.work_department))) {
				listDepartment = new DepartmentDAO().getAllDepartment();
				departmentHandler.sendEmptyMessage(0);
				return false;
			}
			if (preference.getKey().equals(getString(R.string.customer_check_select))) {
				customerCheckSet();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.goods_check_select))) {
				goodsCheckSet();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.work_warehouse))) {
				warehouses = new WarehouseDAO().getAllWarehouses();
				warehouseHandler.sendEmptyMessage(0);
				return false;
			}
			if (preference.getKey().equals(getString(R.string.net_setting))) {
				netSet();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.item_order))) {
				docItemOrder();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.goods_select_more_add))) {
				goodsSelectMore();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.service_port))) {
				serviceProt();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.db_port))) {
				dbProt();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.combinationItem))) {
				ap.setValue("iscombinationItem", !Boolean.parseBoolean(ap.getValue("iscombinationItem")));
				String isShow = Boolean.parseBoolean(ap.getValue("iscombinationItem")) == true ? "开启" : "关闭";
				getMyPreference(R.string.combinationItem)

				.setSummary(TextUtils.setTextStyle(getResources().getString(R.string.combinationItem_on_off), isShow));
				return false;
			}
			// 是否显示蓝牙打印
			if (preference.getKey().equals(getString(R.string.bluetoothPrintIsShow))) {
				ap.setValue("bluetoothPrintIsShow", !Boolean.parseBoolean(ap.getValue("bluetoothPrintIsShow")));
				String isShow = Boolean.parseBoolean(ap.getValue("bluetoothPrintIsShow")) == true ? "显示" : "不显示";
				getMyPreference(R.string.bluetoothPrintIsShow)

				.setSummary(TextUtils.setTextStyle(getResources().getString(R.string.bluetoothPrint_on_off), isShow));
				return false;
			}
			if (preference.getKey().equals(getString(R.string.default_printer))) {
				printsetting();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.printer_model))) {
				printerModel();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.print_mode))) {
				printMode();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.clearlastzero))) {
				clearLastZero();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.minustuihuo))) {
				minustuihuo();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.printbarcode))) {
				printBarcode();
				return false;
			}
			if (preference.getKey().equals(getString(R.string.instruckment))) {
				startActivity(new Intent(PrefsFragment.this.getActivity(), AboutActivity.class));
				return false;
			}
			return false;
		}

	};

	/**
	 * 打印机型号
	 */
	protected void printerModel() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("设置打印机型号");
		String items[] = { "标准型号", "打印机型号一" };
		int checkedItem = Integer.parseInt(ap.getValue("printermodel_default", "epson").equals("epson") ? "0" : "1");
		builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ap.setValue("printermodel_default", which == 0 ? "epson" : "qunsuo");
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String printerModel = ap.getValue("printermodel_default", "epson").equals("epson") ? "标准型号" : "打印机型号一";
				getMyPreference(R.string.printer_model).setSummary(
						TextUtils.setTextStyle(getResources().getString(R.string.printer_model_select), printerModel));
			}
		});
		builder.show();
	}

	protected void dbProt() {
		final Dialog_ed_message serviceCGIp = new Dialog_ed_message(getActivity());
		serviceCGIp.setTitleMessage("请输入数据库服务器端口");
		serviceCGIp.setEdtext(ap.getDBServerPort());
		// 设置只能输入小数
		serviceCGIp.ed_service.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		serviceCGIp.setCancelListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				serviceCGIp.dismiss();
				if (serviceCGIp.getEdtext().isEmpty()) {
					Toast.makeText(getActivity(), "请输入数据库服务器端口", Toast.LENGTH_SHORT).show();
					return;
				}
				ap.setDBServerPort(serviceCGIp.getEdtext());
				PDH.show(getActivity(), "连接数据库中..", new ProgressCallBack() {

					@Override
					public void action() {
						CloudDBBase dbBase = new CloudDBBase();
						dbBase.executeQuery("");
						if (dbBase.conn != null) {
							upDatahandler.sendEmptyMessage(5);
							serviceCGIp.dismiss();
						} else {
							upDatahandler.sendEmptyMessage(3);
						}
					}
				});
			}
		});
		serviceCGIp.setComfirmListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				serviceCGIp.dismiss();
			}
		});
		serviceCGIp.show();
		serviceCGIp.ed_service.setInputType(InputType.TYPE_CLASS_NUMBER);

	}

	private void serviceProt() {

		final Dialog_ed_message serviceCGIp = new Dialog_ed_message(getActivity());
		serviceCGIp.setTitleMessage("请输入采购服务器端口");
		serviceCGIp.ed_service.setInputType(InputType.TYPE_CLASS_NUMBER);
		serviceCGIp.setEdtext(ap.getCGServerPort());
		serviceCGIp.setCancelListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				serviceCGIp.dismiss();
				if (serviceCGIp.getEdtext().isEmpty()) {
					Toast.makeText(getActivity(), "请输入采购服务器端口", Toast.LENGTH_SHORT).show();
					return;
				}
				serviceCGIp.dismiss();
				ap.setCGServerPort(serviceCGIp.getEdtext());
				PDH.show(getActivity(), "采购服务器连接中..", new ProgressCallBack() {

					@Override
					public void action() {
						try {

							List<DefDocPayType> payTypes = ServicePayType
									.PayTypes(ap.getServerIp() + ":" + serviceCGIp.getEdtext(), 0, 0);
							if (!payTypes.isEmpty()) {
								serviceCGIp.dismiss();
								upDatahandler.sendEmptyMessage(4);
								// 保存采购端口
								getMyPreference(R.string.service_port)
										.setSummary(TextUtils.setTextStyle("端口设置:", "已设置"));
								return;
							}
							upDatahandler.sendEmptyMessage(3);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		serviceCGIp.setComfirmListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				serviceCGIp.dismiss();
			}
		});
		serviceCGIp.show();
		serviceCGIp.ed_service.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	protected void printsetting() {
		if (ap.getPrinter() != null) {
			printOperation();
		} else {
			startActivityForResult(new Intent(getActivity(), BTdeviceListAct.class).putExtra("type", 2), 0);
		}

	}

	PrintMode printMode;

	protected void printOperation() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setItems(new String[] { "重新设定", "打印测试" }, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				BTPrintHelper printHelper = new BTPrintHelper(getActivity());
				if (which == 0) {
					startActivityForResult(new Intent(getActivity(), BTdeviceListAct.class).putExtra("type", 2), 0);
				} else if (which == 1) {
					printMode = PrintMode.getPrintMode();
					if (printMode != null) {
						PrintData printData = new PrintData();
						printMode.setDatainfo(printData.getTestData());
						printMode.setDocInfo(printData.getTestInfo());
						printHelper.setMode(printMode);
						printHelper.print(ap.getPrinter());
						printHelper.setPrintOverCall(new PrintOverCall() {

							@Override
							public void printOver() {
								printMode.pageinfo = null;
								printMode = null;
							}
						});

					}
				}

			}
		});
		builder.show();
	}

	/**
	 * 设置小票打印模板
	 */
	protected void printMode() {

		// 设置小票打印模板
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("设置小票打印模板");
		int checkedItem = Integer.parseInt(ap.getValue("printModeType", "0"));
		builder.setSingleChoiceItems(printModeitems, checkedItem, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				ap.setValue("printModeType", which);
				getMyPreference(R.string.print_mode).setSummary(TextUtils
						.setTextStyle(getResources().getString(R.string.printer_type_select), printModeitems[which]));
			}
		});
		builder.show();
	}

	/**
	 * 是否保留数值小数点后末尾的零
	 */
	protected void clearLastZero() {
		int checkedItem = 0;
		String[] items = new String[] { "不保留末尾零", "保留末尾零" };
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("数值打印");
		if (!"0".equals(PrefsFragment.this.ap.getValue("clearlastzero", "0"))) {
			checkedItem = 1;
		}
		builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ap.setValue("clearlastzero", which == 0 ? "0" : "1");
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String clearlastzero = "0".equals(ap.getValue("clearlastzero", "0")) ? "不保留末尾零" : "保留末尾零";
				getMyPreference(R.string.clearlastzero).setSummary(
						TextUtils.setTextStyle(getResources().getString(R.string.clear_last_zero), clearlastzero));
			}
		});
		builder.show();

	}

	/**
	 * 打印时退货商品数量显示为负数，销售退货单无效
	 */
	protected void minustuihuo() {
		// 打印时退货商品数量显示为负数，销售退货单无效
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("数值打印");
		String items[] = { "显示为负数", "显示为正数" };
		int checkedItem = Integer.parseInt(ap.getValue("minustuihuo", "0"));
		builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ap.setValue("minustuihuo", which);
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String showText = ap.getValue("minustuihuo", "0").equals("0") ? "显示为负数" : "显示为正数";
				getMyPreference(R.string.minustuihuo).setSummary(
						TextUtils.setTextStyle(getResources().getString(R.string.cancel_goods_show), showText));
			}
		});
		builder.show();

	}

	/**
	 * 打印商品条码和备注信息，商品名称单独一行打印时有效
	 */
	protected void printBarcode() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("条码打印");
		String items[] = { "不打印", "打印" };
		int checkedItem = Integer.parseInt(ap.getValue("printbarcode", "0"));
		builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ap.setValue("printbarcode", which);
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String isShowBarcode = ap.getValue("printbarcode", "0").equals("0") ? "不打印" : "打印";
				getMyPreference(R.string.printbarcode).setSummary(
						TextUtils.setTextStyle(getResources().getString(R.string.print_barcode_set), isShowBarcode));
			}
		});
		builder.show();
	}

	// 全部同步
	protected void loadData() {
		// if (!NetUtils.isConnected(getActivity())) {
		// PDH.showFail("当前无可用网络");
		// return;
		// }
		final Dialog_message dialog_massage = new Dialog_message(getActivity());
		dialog_massage.show();
		dialog_massage.setMessage("请选择同步方式");
		dialog_massage.setTitle("提示");
		dialog_massage.setTextTrue("全部同步");
		dialog_massage.setTextFalse("增量同步");
		dialog_massage.setCancelListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// if (!NetUtils.isConnected(getActivity())) {
				// PDH.showFail("当前无可用网络");
				// return;
				// }

				PDH.show(getActivity(), "正在检查更新....", new ProgressCallBack() {

					@Override
					public void action() {
						dialog_massage.dismiss();
						// 全部同步更新
						getMaxRVersion(true);
					}
				});
			}
		});
		dialog_massage.setComfirmListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog_massage.dismiss();
				// 全部同步更新
				PDH.show(getActivity(), "正在检查更新....", new ProgressCallBack() {

					@Override
					public void action() {
						// 全部同步更新
						getMaxRVersion(false);
					}
				});
			}
		});
	}

	private int departmentposition;

	private void selectDepartment(final List<Department> listDepartments) {
		departmentposition = -1;
		String departmentId = SystemState.getDepartment().getDid();
		String[] departments = new String[listDepartments.size()];
		for (int i = 0; i < listDepartments.size(); ++i) {
			departments[i] = listDepartments.get(i).getDname();
			if (listDepartments.get(i).getDid().equals(departmentId)) {
				departmentposition = i;
			}
		}
		AlertDialog.Builder departmentBuilder = new AlertDialog.Builder(getActivity());
		departmentBuilder.setTitle("工作部门");
		departmentBuilder.setSingleChoiceItems(departments, departmentposition, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				departmentposition = which;
			}
		});
		departmentBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (departmentposition >= 0) {
					SystemState.saveObject("department", listDepartments.get(departmentposition));
					getMyPreference(R.string.work_department)

					.setSummary(TextUtils.setTextStyle(workDepartmentTitle,
							listDepartments.get(departmentposition).getDname()));
				}
			}
		});
		departmentBuilder.show();
	}

	// 商品多选
	protected void goodsSelectMore() {
		int i = 0;
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
		localBuilder.setTitle("商品多选模式");
		if ("1".equals(ap.getValue("goods_select_more"))) {
			i = 1;
		}
		DialogInterface.OnClickListener local5 = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				ap.setValue("goods_select_more", which);
			}
		};
		localBuilder.setSingleChoiceItems(new String[] { "自由输入", "统一输入" }, i, local5);
		localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String select = "1".equals(ap.getValue("goods_select_more", "0")) ? "统一输入" : "自由输入";
				getMyPreference(R.string.goods_select_more_add)
						.setSummary(TextUtils.setTextStyle(goodsSelectTitle, select));
			}
		});
		localBuilder.show();
	}

	int itemorderposition = 0;

	// 单据排序
	protected void docItemOrder() {
		itemorderposition = Integer.parseInt(ap.getValue("item_order", "0"));
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
		localBuilder.setTitle("单据商品排序");
		localBuilder.setSingleChoiceItems(orders, itemorderposition, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				itemorderposition = which;
			}
		});
		localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				ap.setValue("item_order", itemorderposition);
				getMyPreference(R.string.item_order)
						.setSummary(TextUtils.setTextStyle(docItemOrderTitile, orders[itemorderposition]));
			}
		});
		localBuilder.show();
	}

	protected void netSet() {
		AlertDialog.Builder v0 = new AlertDialog.Builder(getActivity());
		v0.setTitle("网络设置");
		String[] items = new String[] { "不允许（推荐）", "允许" };
		int checkedItem = ("1".equals(ap.getValue("net_setting"))) ? 1 : 0;
		v0.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ap.setValue("net_setting", which);
			}
		});
		v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String isNet = "1".equals(ap.getValue("net_setting")) ? "允许" : "不允许";
				getMyPreference(R.string.net_setting).setSummary(TextUtils.setTextStyle(netSelectTitle, isNet));
			}
		});
		v0.show();
	}

	// 清除本地数据db
	protected void clearDataBase() {
		final Dialog_message dialog_massage = new Dialog_message(getActivity());
		dialog_massage.show();
		dialog_massage.setTitle("清空数据");
		dialog_massage.setMessage("清空平板所有基本数据");
		dialog_massage.setCancelListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog_massage.dismiss();
				// 删除本地SQL 所有表
				if (new BaseDao().deleteDataBase()) {
					handler.sendEmptyMessage(0);
				} else {
					handler.sendEmptyMessage(1);
				}
			}
		});

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				PDH.showSuccess("删除成功!");
				ap.setValue("max_rversion", "0");
				getMyPreference(R.string.load_basic_data).setSummary("未同步");
				break;
			case 1:
				PDH.showFail("删除失败!");
				break;
			case 2:
				PDH.showFail("获取数据失败");
				break;
			default:
				break;
			}
		};
	};

	protected void goodsCheckSet() {
		final boolean[] v1 = new boolean[4];
		String v5 = ap.getValue("goods_check_select");
		if (TextUtils.isEmptyS(v5)) {
			String[] v4 = v5.split(",");
			for (int i = 0; i < v4.length; ++i) {
				for (int j = 0; j < SystemState.goods_select_keys.length; ++j) {
					if (v4[i].equals(SystemState.goods_select_keys[j])) {
						v1[j] = true;
					}
				}
			}

		} else {
			v1[0] = true;
			v1[1] = true;
			v1[2] = true;
			v1[3] = true;
		}
		AlertDialog.Builder v0 = new AlertDialog.Builder(getActivity());
		v0.setTitle("商品检索方式");
		v0.setMultiChoiceItems(SystemState.goods_select_items, v1, new OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				v1[which] = isChecked;
			}
		});
		v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				StringBuilder v3 = new StringBuilder();
				StringBuilder v2 = new StringBuilder();
				for (int v4 = 0; v4 < v1.length; ++v4) {
					if (v1[v4]) {
						v2.append(String.valueOf(SystemState.goods_select_items[v4]) + "、");
						v3.append(String.valueOf(SystemState.goods_select_keys[v4]) + ",");
					}
				}
				if (TextUtils.isEmpty(v3.toString())) {
					PDH.showMessage("请至少选择一项检索关键字");
					try {
						Field v1 = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
						v1.setAccessible(true);
						v1.set(dialog, Boolean.valueOf(false));
					} catch (Exception v0) {
						v0.printStackTrace();
					}
					return;
				}
				try {
					Field v1 = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
					v1.setAccessible(true);
					v1.set(dialog, Boolean.valueOf(true));
				} catch (Exception v0) {
					v0.printStackTrace();
				}
				getMyPreference(R.string.goods_check_select)
						.setSummary(TextUtils.setTextStyle(getResources().getString(R.string.goods_search_key),
								v2.toString().substring(0, v2.length() - 1)));
				ap.setValue("goods_check_select", v3.substring(0, v3.length() - 1));
				Utils.GOODS_CHECK_SELECT = ap.getValue("goods_check_select");

			}
		});
		v0.show();
	}

	protected void customerCheckSet() {
		final boolean[] v1 = new boolean[3];
		String v5 = ap.getValue("customer_check_select");
		if (TextUtils.isEmptyS(v5)) {
			String[] v4 = v5.split(",");
			for (int v2 = 0; v2 < v4.length; ++v2) {
				for (int v3 = 0; v3 < SystemState.customer_select_keys.length; ++v3) {
					if (v4[v2].equals(SystemState.customer_select_keys[v3])) {
						v1[v3] = true;
					}
				}
			}
		} else {
			v1[0] = true;
			v1[1] = true;
			v1[2] = true;
		}
		AlertDialog.Builder v0 = new AlertDialog.Builder(getActivity());
		v0.setTitle("客户检索方式");
		v0.setMultiChoiceItems(SystemState.customer_select_items, v1, new OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				v1[which] = isChecked;
			}
		});
		v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				StringBuilder sbCustomerItems = new StringBuilder();
				StringBuilder sbCustomerKeys = new StringBuilder();
				for (int i = 0; i < v1.length; ++i) {
					if (v1[i]) {
						sbCustomerItems.append(String.valueOf(SystemState.customer_select_items[i]) + "、");
						sbCustomerKeys.append(String.valueOf(SystemState.customer_select_keys[i]) + ",");
					}
				}
				if (TextUtils.isEmpty(sbCustomerKeys.toString())) {
					PDH.showMessage("请至少选择一项检索关键字");
					try {
						Field v1 = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
						v1.setAccessible(true);
						v1.set(dialog, Boolean.valueOf(false));
					} catch (Exception v0) {
						v0.printStackTrace();
					}
					return;
				}
				try {
					Field v1 = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
					v1.setAccessible(true);
					v1.set(dialog, Boolean.valueOf(true));
				} catch (Exception v0) {
					v0.printStackTrace();
				}
				getMyPreference(R.string.customer_check_select)
						.setSummary(TextUtils.setTextStyle(getResources().getString(R.string.customer_search_key),
								sbCustomerItems.toString().substring(0, sbCustomerItems.length() - 1)));
				ap.setValue("customer_check_select", sbCustomerKeys.substring(0, sbCustomerKeys.length() - 1));
				Utils.GOODS_CHECK_SELECT = ap.getValue("customer_check_select");

			}
		});
		v0.show();
	}

	int warehouseposition;

	protected void selectWarehouse(List<Warehouse> listWarehouse) {
		warehouseposition = -1;
		String[] v2 = new String[listWarehouse.size()];
		String v3 = "";
		if (SystemState.getWarehouse() != null) {
			v3 = SystemState.getWarehouse().getId();
		}
		for (int i = 0; i < listWarehouse.size(); i++) {
			v2[i] = listWarehouse.get(i).getName();
			if (listWarehouse.get(i).getId().equals(v3)) {
				warehouseposition = i;
			}
		}
		AlertDialog.Builder v0 = new AlertDialog.Builder(getActivity());
		v0.setTitle("默认仓库");
		v0.setSingleChoiceItems(v2, warehouseposition, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				warehouseposition = which;
			}
		});
		v0.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (warehouseposition >= 0) {
					SystemState.saveObject("warehouse", warehouses.get(warehouseposition));
					getMyPreference(R.string.work_warehouse).setSummary(
							TextUtils.setTextStyle(workWarehouseTitle, warehouses.get(warehouseposition).getName()));
				}

			}
		});
		v0.show();

	}

	private Handler departmentHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				selectDepartment(listDepartment);
			} else {
				PDH.showFail("获取部门信息失败");
			}
		}
	};

	private Handler warehouseHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				selectWarehouse(warehouses);
				return;
			}
			PDH.showFail("获取仓库信息失败");
		}
	};

	private void getMaxRVersion(boolean allUpdata) {
		if (allUpdata) {
			new BaseDao().deleteDataBase();
			ap.setValue("max_rversion", "0");
		}
		long max_rversion = Long.parseLong(new AccountPreference().getValue("max_rversion", "0"));
		List<ReqSynUpdateInfo> listReqSyn = new ServiceSynchronize().syn_QueryUpdateInfo(max_rversion);
		if (listReqSyn != null) {
			long rversion = new SwyUtils().getPagesFromUpdateInfo(listReqSyn, "rversion");
			if (rversion != 0) {
				ap.setValue("basic_data_updatitme", Utils.formatDate(new Date().getTime()));
				if (new UpdateUtils().executeUpdate(handlerProgress, listReqSyn, max_rversion)) {
					this.ap.setValue("max_rversion", String.valueOf(rversion));
					upDatahandler.sendEmptyMessage(0);
				} else {
					upDatahandler.sendEmptyMessage(2);
				}
				this.handlerProgress.sendEmptyMessage(-3);
			}

		}
	}

	private ProgressDialog progressDialog;
	Handler handlerProgress = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case -1:
				progressDialog.setProgress(0);
				progressDialog.setMessage("数据同步中");
				progressDialog.setMax(Integer.parseInt(msg.obj.toString()));
				progressDialog.show();
				break;
			case -2:
				progressDialog.setProgress(0);
				progressDialog.setMessage("商品图片同步中");
				progressDialog.setMax(Integer.parseInt(msg.obj.toString()));
				progressDialog.show();
				break;
			case -3:
				progressDialog.cancel();
				break;

			}
			progressDialog.setProgress(msg.what);
		};
	};
	Handler upDatahandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				PDH.showSuccess("同步成功");
				getMyPreference(R.string.load_basic_data)
						.setSummary(TextUtils.setTextStyle("上次同步时间：", ap.getValue("basic_data_updatitme", "未同步")));
				break;
			case 2:
				InfoDialog.showError(getActivity(), "同步失败，请重试");
				break;
			case 3:
				PDH.showFail("设置失败! 服务器是否打开?");
				break;
			case 4:
				PDH.showSuccess("设置成功!");
				getMyPreference(R.string.service_port).setSummary(TextUtils.setTextStyle("数据库端口设置:", "已设置"));
				break;
			case 5:
				PDH.showSuccess("设置成功!");
				getMyPreference(R.string.db_port).setSummary(TextUtils.setTextStyle("数据库端口设置:", "已设置"));
				break;
			}
		};
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 0:
				BTPrinter printer = ap.getPrinter();
				getMyPreference(R.string.default_printer)
						.setSummary(printer.getName() + "[" + printer.getAddress() + "]");
				break;

			default:
				break;
			}
		}
	}

}
