package com.ahjswy.cn.app;

import java.util.Date;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.BaseDao;
import com.ahjswy.cn.dao.DepartmentDAO;
import com.ahjswy.cn.dao.WarehouseDAO;
import com.ahjswy.cn.model.Department;
import com.ahjswy.cn.model.Warehouse;
import com.ahjswy.cn.request.ReqSynUpdateInfo;
import com.ahjswy.cn.service.ServiceSynchronize;
import com.ahjswy.cn.ui.AboutActivity;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.SwyMain;
import com.ahjswy.cn.utils.Dialog_utils;
import com.ahjswy.cn.utils.Dialog_utils.setDuoXuanCallBack;
import com.ahjswy.cn.utils.Dialog_utils.setTingCallBack;
import com.ahjswy.cn.utils.InfoDialog;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.PDH.ProgressCallBack;
import com.ahjswy.cn.utils.SwyUtils;
import com.ahjswy.cn.utils.UpdateUtils;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.Dialog_message;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main_Setting extends BaseActivity implements OnClickListener {
	private RelativeLayout setTing_bumen;
	private RelativeLayout setting_tbsj;
	private RelativeLayout setTing_dele_all;
	private RelativeLayout setTing_cangku;
	private RelativeLayout setTing_jiansuo_kh;
	private RelativeLayout setTing_jiansuo_shop;
	private RelativeLayout setTing_wifi;
	private RelativeLayout setTing_paixu;
	private RelativeLayout setTing_shuru;
	private AccountPreference ap;
	private TextView tv_bumen;
	private TextView tv_tbsj;
	private TextView tv_cangku;
	private TextView tv_jskh;
	private TextView tv_jssp;
	private TextView tv_wifi;
	private TextView tv_paixu;
	private TextView tv_sr;
	private final String[] khjs = { "编号", "拼音", "名称" };
	private List<Department> departments;
	final String[] net_setting = { "不允许(推荐)", "允许" };
	final String[] goods_select_more = { "自由输入", "统一输入" };
	final String[] orders = { "默认", "编号", "名称", "条码", "类别" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_setting);
		inintView();
		inintDate();
	}

	private void inintView() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(1);
		progressDialog.setCancelable(false);
		setting_tbsj = (RelativeLayout) findViewById(R.id.setting_tbsj);
		setTing_dele_all = (RelativeLayout) findViewById(R.id.setTing_dele_all);
		setTing_bumen = (RelativeLayout) findViewById(R.id.setTing_bumen);
		setTing_cangku = (RelativeLayout) findViewById(R.id.setTing_cangku);
		setTing_jiansuo_kh = (RelativeLayout) findViewById(R.id.setTing_jiansuo_kh);
		setTing_jiansuo_shop = (RelativeLayout) findViewById(R.id.setTing_jiansuo_shop);
		setTing_wifi = (RelativeLayout) findViewById(R.id.setTing_wifi);
		setTing_paixu = (RelativeLayout) findViewById(R.id.setTing_paixu);
		setTing_shuru = (RelativeLayout) findViewById(R.id.setTing_shuru);
		RelativeLayout setTing_guanyu = (RelativeLayout) findViewById(R.id.setTing_guanyu);
		setting_tbsj.setOnClickListener(this);
		setTing_dele_all.setOnClickListener(this);
		setTing_bumen.setOnClickListener(this);
		setTing_cangku.setOnClickListener(this);
		setTing_jiansuo_kh.setOnClickListener(this);
		setTing_jiansuo_shop.setOnClickListener(this);
		setTing_wifi.setOnClickListener(this);
		setTing_paixu.setOnClickListener(this);
		setTing_shuru.setOnClickListener(this);
		setTing_guanyu.setOnClickListener(this);
	}

	private void inintDate() {
		ap = new AccountPreference();
		// 上次同步时间
		tv_tbsj = (TextView) setting_tbsj.findViewById(R.id.tv_title3);
		// 清空数据
		// tv_dele = (TextView) setTing_dele_all.findViewById(R.id.tv_title3);
		// 设置部门
		tv_bumen = (TextView) setTing_bumen.findViewById(R.id.tv_title3);
		// 设置仓库
		tv_cangku = (TextView) setTing_cangku.findViewById(R.id.tv_title3);
		// 客户检索方式
		tv_jskh = (TextView) setTing_jiansuo_kh.findViewById(R.id.tv_title3);
		// 商品检索方式
		tv_jssp = (TextView) setTing_jiansuo_shop.findViewById(R.id.tv_title3);
		// 网络设置
		tv_wifi = (TextView) setTing_wifi.findViewById(R.id.tv_title3);
		// 单据商品排序
		tv_paixu = (TextView) setTing_paixu.findViewById(R.id.tv_title3);
		// 商品多选模式
		tv_sr = (TextView) setTing_shuru.findViewById(R.id.tv_title3);
		String time = ap.getValue("basic_data_updatitme", "未同步");
		tv_tbsj.setText(time);
		main_set();
	}

	public void main_set() {
		// department
		// 初始化部门
		String department = SystemState.getDepartment().getDname();
		tv_bumen.setText(department);
		// 初始化仓库
		Warehouse warehouse = SystemState.getWarehouse();
		if (warehouse == null) {
			tv_cangku.setText("未设置");
		} else {
			tv_cangku.setText(warehouse.getName());
		}
		// 网络设置net_setting
		tv_wifi.setText(net_setting[Integer.parseInt(ap.getValue("net_setting", "0"))]);
		// 单据商品排序
		tv_paixu.setText(orders[Integer.parseInt(ap.getValue("item_order", "0"))]);
		// 输入方式
		tv_sr.setText(goods_select_more[Integer.parseInt(ap.getValue("goods_select_more", "0"))]);
		// 客户检索方式 customer_check_select
		StringBuilder sb = new StringBuilder();
		if (ap.getValue("customer_check_select").length() > 0) {
			String[] kh = ap.getValue("customer_check_select").split(",");
			for (int i = 0; i < SystemState.customer_select_keys.length; i++) {
				for (int j = 0; j < kh.length; j++) {
					if (SystemState.customer_select_keys[i].toString().equals(kh[j].toString())) {
						sb.append(SystemState.customer_select_items[i] + "、");
						break;
					}
				}
			}
			tv_jskh.setText(sb.substring(0, sb.length() - 1));
		} else {
			tv_jskh.setText("编号、名称、拼音");
			ap.setValue("customer_check_select", "id,pinyin,name");
		}
		// 商品检索方式goods_check_select
		StringBuilder goods_check = new StringBuilder();
		if (ap.getValue("goods_check_select").length() > 0) {
			String[] split = ap.getValue("goods_check_select").split(",");
			for (int i = 0; i < SystemState.goods_select_keys.length; i++) {

				for (int j = 0; j < split.length; j++) {
					if (SystemState.goods_select_keys[i].toString().equals(split[j].toString())) {
						goods_check.append(SystemState.goods_select_items[i] + "、");
						break;
					}
				}
			}
			tv_jssp.setText(goods_check.substring(0, goods_check.length() - 1));

		} else {
			tv_jssp.setText("编号、拼音、名称、条形码");
			ap.setValue("goods_check_select", "id,pinyin,name,barcode");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_tbsj:
			setShowDialog();
			break;
		case R.id.setTing_dele_all:
			setTing_dele_all();
			break;
		case R.id.setTing_bumen:
			// 查找本地部门信息
			departments = new DepartmentDAO().getAllDepartment();
			this.departmentHandler.sendEmptyMessage(0);
			// setTing_bumen();
			break;
		case R.id.setTing_cangku:
			setTing_cangku();
			break;
		case R.id.setTing_jiansuo_kh:
			// 检索客户
			setTing_jiansuo_kh();
			break;
		case R.id.setTing_jiansuo_shop:
			// 设置检索 商品
			setTing_jiansuo_shop();
			break;
		case R.id.setTing_wifi:
			// 设置 wifi 开关
			setTing_wifi();
			break;
		case R.id.setTing_paixu:
			// 设置排序 样式
			docItemOrder();
			break;
		case R.id.setTing_shuru:
			// 设置 输入方式
			setTing_shuru();
			break;
		case R.id.setTing_guanyu:
			// 关于 竸商勿忧 介绍
			setTing_guanyu();
			break;

		default:
			break;
		}
	}

	// 清空平板所有基本数据
	private void setTing_dele_all() {
		final Dialog_message dialog_massage = new Dialog_message(this);
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
				tv_tbsj.setText("未同步");
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
				tv_tbsj.setText(ap.getValue("basic_data_updatitme", "2017-01-01 00:00:00"));
				break;
			case 2:
				InfoDialog.showError(Main_Setting.this, "同步失败，请重试");
				break;
			}
		};
	};

	private ProgressDialog progressDialog;

	// 关于商勿忧
	private void setTing_guanyu() {
		startActivity(new Intent(this, AboutActivity.class));
	}

	private Handler departmentHandler = new Handler() {
		public void handleMessage(Message paramAnonymous2Message) {
			if (paramAnonymous2Message.what == 0) {
				selectDepartment(departments);
				return;
			}
			PDH.showFail("获取部门信息失败");
		}
	};

	private int departmentposition = 0;

	private void selectDepartment(final List<Department> paramAnonymousList) {
		String localString = SystemState.getDepartment().getDid();
		String[] arrayOfString = new String[paramAnonymousList.size()];
		departmentposition = -1;
		for (int i = 0; i < paramAnonymousList.size(); i++) {
			arrayOfString[i] = ((Department) paramAnonymousList.get(i)).getDname();
			if (((Department) paramAnonymousList.get(i)).getDid().equals(localString)) {
				departmentposition = i;
			}

		}
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setTitle("工作部门");
		localBuilder.setSingleChoiceItems(arrayOfString, departmentposition, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
				departmentposition = paramAnonymous2Int;
			}
		});
		localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
				paramAnonymous2DialogInterface.dismiss();
				if (departmentposition >= 0) {
					SystemState.saveObject("department", paramAnonymousList.get(departmentposition));
					tv_bumen.setText(paramAnonymousList.get(departmentposition).getDname());
				}
			}
		});
		localBuilder.show();
		return;

	}

	// 设置 工作部门
	public void setTing_bumen() {
		// 获取SQL 部门全部 id name
		departments = new DepartmentDAO().getAllDepartment();
		// 创建 数组 设置
		String[] bumen = new String[departments.size()];
		// list 转换成 数组
		final Object[] arr = departments.toArray();
		for (int i = 0; i < arr.length; i++) {
			Department dep = (Department) arr[i];
			bumen[i] = dep.getDname();
		}
		int id = 0;
		if (SystemState.getDepartment() != null) {
			id = Integer.parseInt(ap.getValue("departmentPosition"));
		}

		Dialog_utils.setSingleChoiceItems(this, "工作部门", bumen, id, new setTingCallBack() {

			@Override
			public void settingCallBack(Integer set) {
				Department de = (Department) arr[set];
				// 保存 的对象里面 也 保存了 id
				SystemState.saveObject("department", de);
				ap.setValue("departmentPosition", set);
				tv_bumen.setText(de.getDname());
			}
		});
	}

	// 设置 默认仓库
	private void setTing_cangku() {
		List<Warehouse> warehouses = new WarehouseDAO().getAllWarehouses();
		if (warehouses.size() == 0) {
			PDH.showMessage("不存在仓库信息\n请服务器添加后同步数据");
			return;
		}
		String[] ware = new String[warehouses.size()];
		final Object[] arr = warehouses.toArray();
		for (int i = 0; i < arr.length; i++) {
			Warehouse war = (Warehouse) arr[i];
			ware[i] = war.getName();
		}
		int wa_weizhi = 0;
		if (SystemState.getWarehouse() != null) {
			wa_weizhi = Integer.parseInt(ap.getValue("warehousePosition"));
		}
		Dialog_utils.setSingleChoiceItems(this, "默认仓库", ware, wa_weizhi, new setTingCallBack() {
			@Override
			public void settingCallBack(Integer set) {
				Warehouse wa = (Warehouse) arr[set];
				SystemState.saveObject("warehouse", wa);
				ap.setValue("warehousePosition", set);
				tv_cangku.setText(wa.getName());
			}
		});
	}

	// 设置 检索客户方式 多 customer_check_select
	public void setTing_jiansuo_kh() {

		final StringBuilder sb = new StringBuilder();
		final StringBuilder tv_kh = new StringBuilder();
		boolean weizhi[] = { true, true, true };
		if (ap.getValue("customer_check_select").length() > 0) {
			String[] split = ap.getValue("customer_check_select").split(",");
			for (int i = 0; i < SystemState.customer_select_keys.length; i++) {
				for (int j = 0; j < split.length; j++) {
					if (SystemState.customer_select_keys[i].toString().equals(split[j].toString())) {
						weizhi[i] = true;
						break;
					} else {
						weizhi[i] = false;
					}
				}
			}
		}
		Dialog_utils.setMultiChoiceItems(this, "客户检索方式", khjs, weizhi, new setDuoXuanCallBack() {

			@Override
			public void setDuoXuan(boolean[] weizh) {
				for (int i = 0; i < weizh.length; i++) {
					if (weizh[i]) {
						tv_kh.append(SystemState.customer_select_items[i] + "、");
						sb.append(SystemState.customer_select_keys[i] + ",");
					}
				}
				tv_jskh.setText(tv_kh.substring(0, tv_kh.length() - 1));
				ap.setValue("customer_check_select", sb.substring(0, sb.length() - 1));
			}
		});
	}

	// 设置 检索商品方式 多
	private void setTing_jiansuo_shop() {
		final StringBuilder sb = new StringBuilder();
		final StringBuilder goods_check = new StringBuilder();

		boolean weizhi[] = { true, true, true, true };
		if (ap.getValue("goods_check_select").length() > 0) {
			String[] split = ap.getValue("goods_check_select").split(",");
			for (int i = 0; i < SystemState.goods_select_keys.length; i++) {
				for (int j = 0; j < split.length; j++) {
					if (SystemState.goods_select_keys[i].toString().equals(split[j].toString())) {
						weizhi[i] = true;
						break;
					} else {
						weizhi[i] = false;
					}
				}
			}
		}
		Dialog_utils.setMultiChoiceItems(this, "商品检索方式", SystemState.goods_select_items, weizhi,
				new setDuoXuanCallBack() {

					@Override
					public void setDuoXuan(boolean[] weizh) {
						for (int i = 0; i < weizh.length; i++) {
							if (weizh[i]) {
								goods_check.append(SystemState.goods_select_items[i] + "、");
								sb.append(SystemState.goods_select_keys[i] + ",");
							}
						}
						tv_jssp.setText(goods_check.substring(0, goods_check.length() - 1));
						ap.setValue("goods_check_select", sb.substring(0, sb.length() - 1));
					}
				});
	}

	// 设置 wifi 是否开启 单
	private void setTing_wifi() {
		int isNet = 0;
		if (!"0".equals(ap.getValue("net_setting"))) {
			isNet = 1;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("网络设置");
		builder.setSingleChoiceItems(net_setting, isNet, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ap.setValue("net_setting", which);
				tv_wifi.setText(net_setting[which]);
				dialog.dismiss();
			}
		});
		builder.show();
	}

	int itemorderposition = 0;

	// 商品排序 方式 单
	private void docItemOrder() {
		// int weizhi = 0;
		final String[] orders = { "默认", "编号", "名称", "条码", "类别" };
		// if (ap.getValue("item_order").length() != 0) {
		// weizhi = Integer.parseInt(ap.getValue("item_order"));
		// }
		// //
		// Dialog_utils.setSingleChoiceItems(this, "单据商品排序", orders, weizhi, new
		// setTingCallBack() {
		//
		// @Override
		// public void settingCallBack(Integer set) {
		// ap.setValue("item_order", set);
		// tv_paixu.setText(orders[set]);
		// }
		// });
		if (!ap.getValue("item_order", "0").equals("0")) {
			itemorderposition = Integer.parseInt(ap.getValue("item_order", "0"));
		}
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
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
				tv_paixu.setText(orders[itemorderposition]);
			}
		});
		localBuilder.show();

	}

	// 设置 输入模式 单
	private void setTing_shuru() {
		int goods_select = 0;
		if (!ap.getValue("goods_select_more", "0").equals("0")) {
			goods_select = 1;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("商品多选择模式");
		builder.setSingleChoiceItems(net_setting, goods_select, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ap.setValue("goods_select_more", which);
				tv_sr.setText(goods_select_more[which]);
				dialog.dismiss();
			}
		});
		builder.show();
	}

	public void setShowDialog() {
		final Dialog_message dialog_massage = new Dialog_message(this);
		dialog_massage.show();
		dialog_massage.setMessage("请选择同步方式");
		dialog_massage.setTitle("提示");
		dialog_massage.setTextTrue("全部同步");
		dialog_massage.setTextFalse("增量同步");
		dialog_massage.setCancelListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// if (!NetUtils.isConnected(Main_Setting.this)) {
				// PDH.showFail("当前无可用网络");
				// return;
				// }

				PDH.show(Main_Setting.this, "正在检查更新....", new ProgressCallBack() {

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
				PDH.show(Main_Setting.this, "正在检查更新....", new ProgressCallBack() {

					@Override
					public void action() {
						dialog_massage.dismiss();
						// 增量同步更新
						getMaxRVersion(false);
					}
				});
			}
		});
	}

	public void getMaxRVersion(boolean allUpdata) {
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
				if (new UpdateUtils().executeUpdate(this.handlerProgress, listReqSyn, max_rversion)) {
					this.ap.setValue("max_rversion", String.valueOf(rversion));
					upDatahandler.sendEmptyMessage(0);
				} else {
					upDatahandler.sendEmptyMessage(2);
				}
				this.handlerProgress.sendEmptyMessage(-3);
			}

		}

	}

	/**
	 * * 监听Back键按下事件,方法2: * 注意: * 返回值表示:是否能完全处理该事件 * 在此处返回false,所以会继续传播该事件. *
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			startActivity(new Intent(Main_Setting.this, SwyMain.class));
			finish();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void setActionBarText() {
		getActionBar().setIcon(R.drawable.menu_btn_back);
		getActionBar().setTitle("设置");
	}
}
