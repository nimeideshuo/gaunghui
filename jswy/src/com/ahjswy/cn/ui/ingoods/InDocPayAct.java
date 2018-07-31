package com.ahjswy.cn.ui.ingoods;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.EditTextWithDel;
import com.ahjswy.cn.views.EditTextWithDel.Clean;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class InDocPayAct extends BaseActivity implements OnFocusChangeListener, Clean, OnClickListener {
	// 折后合计
	private TextView tvDiscountSubtotal;
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
	// private boolean isReadOnly;
	private boolean isReceive;
	private double discountsubtotal;
	private List<DefDocPayType> listPayType;
	private double preference;
	private InDocPayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_indoc_pay);
		initView();
		initDate();
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
		isReceive = getIntent().getBooleanExtra("isreceive", true);
		// this.isReadOnly = getIntent().getBooleanExtra("isreadonly", true);
		this.discountsubtotal = getIntent().getDoubleExtra("discountsubtotal", 0.0D);
		// 折扣合计
		tvDiscountSubtotal.setText(discountsubtotal + "");
		// 优惠
		preference = getIntent().getDoubleExtra("preference", 0.0D);
		etPreference.setFocusable(false);
		etPreference.setClean(this);
		etPreference.setText(preference + "");
		etPreference.setTag(etPreference.getText());
		etPreference.setOnFocusChangeListener(this);
		this.listPayType = JSONUtil.str2list(getIntent().getStringExtra("listpaytype"), DefDocPayType.class);
		adapter = new InDocPayAdapter(this, isReceive);
		adapter.setData(listPayType);
		listView.setAdapter(adapter);
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
		if (isReceive) {

		}
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
				localIntent.putExtra("listpaytype", JSONUtil.toJSONString(listPayType));
				setResult(RESULT_OK, localIntent);
				finish();

				break;
			default:
				break;
			}

		};
	};

	@Override
	public boolean onCreateOptionsMenu(Menu paramMenu) {
		paramMenu.add(0, 0, 0, "单击显示菜单").setTitle("确定").setShowAsAction(1);
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
		// etPreference.getText().toString();
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
		getActionBar().setTitle("付款");
	}
}
