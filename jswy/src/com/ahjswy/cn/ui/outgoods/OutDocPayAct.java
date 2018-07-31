package com.ahjswy.cn.ui.outgoods;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.request.ReqCustomerdebt;
import com.ahjswy.cn.service.ServiceCustomer;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.EditTextWithDel;
import com.ahjswy.cn.views.EditTextWithDel.Clean;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class OutDocPayAct extends BaseActivity implements OnFocusChangeListener, Clean, OnClickListener {
	// 折后合计
	private TextView tvDiscountSubtotal;
	// InpurDocPayAct
	// 优惠后应收
	private TextView tvReceiveable;
	// 已收
	private TextView tvReceived;
	// 待收
	private TextView tvLeft;
	// 确定
	private Button btnSave;
	// 优惠
	private EditTextWithDel etPreference;
	private ListView listView;
	private boolean isReadOnly;
	private boolean isReceive;
	// 折后合计
	private double discountsubtotal;
	private List<DefDocPayType> listPayType;
	private double preference;
	private OutDocPayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_outdoc_pay);
		initView();
		initDate();
		getNet();
	}

	private void initView() {
		tvDiscountSubtotal = (TextView) findViewById(R.id.tvDiscountSubtotal);
		etPreference = (EditTextWithDel) findViewById(R.id.etPreference);
		tvReceiveable = (TextView) findViewById(R.id.tvReceiveable);
		tvReceived = (TextView) findViewById(R.id.tvReceived);
		tvLeft = (TextView) findViewById(R.id.tvLeft);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.listView);
		tvInBuy = (TextView) findViewById(R.id.tvInBuy);
		isReceive = getIntent().getBooleanExtra("isreceive", true);
		isReadOnly = getIntent().getBooleanExtra("isreadonly", true);
		discountsubtotal = getIntent().getDoubleExtra("discountsubtotal", 0.0D);
		// 折扣合计
		tvDiscountSubtotal.setText(discountsubtotal + "");
		// 优惠
		preference = getIntent().getDoubleExtra("preference", 0.0D);
		// etPreference.setCleanDrawable(false);
		etPreference.setClean(this);
		etPreference.setText(preference + "");
		etPreference.setTag(etPreference.getText());
		etPreference.setOnFocusChangeListener(this);
		this.listPayType = JSONUtil.str2list(getIntent().getStringExtra("listpaytype"), DefDocPayType.class);
		adapter = new OutDocPayAdapter(this, isReadOnly);
		adapter.setData(listPayType);
		listView.setAdapter(adapter);
		if (isReceive) {
			((TextView) findViewById(R.id.tvReceiveableLabel)).setText("优惠后应收：");
			((TextView) findViewById(R.id.tvReceivedLabel)).setText("已收：");
			((TextView) findViewById(R.id.tvLeftLabel)).setText("待收：");
		} else {
			((TextView) findViewById(R.id.tvReceiveableLabel)).setText("优惠后应付：");
			((TextView) findViewById(R.id.tvReceivedLabel)).setText("已付：");
			((TextView) findViewById(R.id.tvLeftLabel)).setText("待付：");
		}
		setHeight(listView, adapter);
	}

	// 重新绘制 item高度
	public void setHeight(ListView listView, Adapter adapter) {
		int height = 0;
		int count = adapter.getCount();
		for (int i = 0; i < count; i++) {
			View temp = adapter.getView(i, null, listView);
			temp.measure(0, 0);
			height += temp.getMeasuredHeight();
		}
		LayoutParams params = (LayoutParams) listView.getLayoutParams();
		params.width = LayoutParams.MATCH_PARENT;
		params.height = height + 20;
		listView.setLayoutParams(params);
	}

	private void initDate() {
		double d2 = 0.0D;
		for (int i = 0; i < listPayType.size(); i++) {
			d2 += ((DefDocPayType) this.listPayType.get(i)).getAmount();
		}
		// 金额显示不正确
		// 总金额 -- 优惠
		double d1 = Utils.normalizeReceivable(this.discountsubtotal - preference);
		// 优惠后应收
		this.tvReceiveable.setText(d1 + "");
		double d3 = Utils.normalize(d2, 2);
		double d4 = Utils.normalize(d1 - d3, 2);
		// 已收
		this.tvReceived.setText(d3 + "");
		// 待收
		this.tvLeft.setText(d4 + "");
		if (!isReadOnly) {
			this.etPreference.setCursorVisible(false);
			this.etPreference.setFocusable(false);
			this.etPreference.setFocusableInTouchMode(false);
			this.btnSave.setVisibility(View.GONE);
		}
	}

	// 查询用的可冲抵预售款
	private void getNet() {
		final String customerid = getIntent().getStringExtra("customerid");
		if (TextUtils.isEmpty(customerid)) {
			return;
		}
		PDH.show(this, new PDH.ProgressCallBack() {

			public void action() {
				String localString = new ServiceCustomer()
						.cu_queryCustomerPreReceived(new ReqCustomerdebt().setCustomerid(customerid).setIscustomer(true));
				handler.sendMessage(handler.obtainMessage(2, localString));
			}
 
		});
	}
 
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				setResult(RESULT_FIRST_USER, new Intent());
				finish();
				break;
			case 1:
				Intent localIntent = new Intent();
				localIntent.putExtra("preference",
						Utils.normalize(Utils.getDouble(etPreference.getText().toString()).doubleValue(), 2));
				localIntent.putExtra("received", tvReceived.getText().toString());
				localIntent.putExtra("listpaytype", JSONUtil.toJSONString(listPayType));
				setResult(RESULT_OK, localIntent);
				finish();
				break;
			case 2:
				if (RequestHelper.isSuccess(msg.obj.toString())) {
					// 显示查询用的可冲抵预售款
					ReqCustomerdebt parseObject = JSONUtil.parseObject(msg.obj.toString(), ReqCustomerdebt.class);
					tvInBuy.setText("" + parseObject.getAmount());
				}
				break;

			}

		};
	};
	private TextView tvInBuy;

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		if (isReadOnly) {
			paramMenu.add(0, 0, 0, "单击显示菜单").setTitle("确定").setShowAsAction(1);
		}
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER, new Intent());
			finish();
			break;
		case 0:
			Intent localIntent = new Intent();
			localIntent.putExtra("preference",
					Utils.normalize(Utils.getDouble(etPreference.getText().toString()).doubleValue(), 2));
			localIntent.putExtra("listpaytype", JSONUtil.toJSONString(listPayType));
			setResult(RESULT_OK, localIntent);
			finish();
			break;

		default:
			break;
		}

		return true;
	}

	public void setPrice() {
		double d1 = 0.0D;
		double d2 = 0.0D;
		if (tvReceiveable.getText().toString().length() > 0) {
			d1 = Utils.normalizeReceivable(Utils.getDouble(this.tvReceiveable.getText().toString()).doubleValue());
		}
		List<DefDocPayType> data = adapter.getData();
		// 获取修改后的金额，总额
		for (int i = 0; i < data.size(); i++) {
			d2 += data.get(i).getAmount();
		}
		double d3 = Utils.normalize(d2, 2);
		double d4 = Utils.normalize(d1 - d3, 2);
		// 已收
		this.tvReceived.setText(d3 + "");
		// 待收
		this.tvLeft.setText(d4 + "");
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		double d1 = 0.0D;
		double d2 = 0.0D;
		double d3 = 0.0D;
		if (!hasFocus) {
			// 优惠
			if (etPreference.getText().toString().length() > 0) {
				d1 = Utils.getDouble(etPreference.getText().toString()).doubleValue();
			}
			d2 = Utils.normalizeReceivable(this.discountsubtotal - d1);
			List<DefDocPayType> data = adapter.getData();
			for (int i = 0; i < data.size(); i++) {
				d3 += data.get(i).getAmount();
			}
			double d4 = Utils.normalize(d3, 2);
			double d5 = Utils.normalize(d2 - d4, 2);
			// 优惠后应收
			this.tvReceiveable.setText(d2 + "");
			// 已收
			this.tvReceived.setText(d4 + "");
			// 待收
			this.tvLeft.setText(d5 + "");
			this.etPreference.setTag(this.etPreference.getText());
			return;
		}
	}

	/**
	 * * 监听Back键按下事件,方法2: * 注意: * 返回值表示:是否能完全处理该事件 * 在此处返回false,所以会继续传播该事件. *
	 * 
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			handler.sendEmptyMessage(0);
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void clean() {
		this.tvReceiveable.setText(discountsubtotal + "");
		setPrice();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSave:
			Intent localIntent = new Intent();
			localIntent.putExtra("preference",
					Utils.normalize(Utils.getDouble(etPreference.getText().toString()).doubleValue(), 2));
			localIntent.putExtra("listpaytype", JSONUtil.toJSONString(listPayType));
			setResult(RESULT_OK, localIntent);
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void setActionBarText() {
		if (this.isReceive) {
			getActionBar().setTitle("收款");
			return;
		}
		getActionBar().setTitle("付款");
	}

}
