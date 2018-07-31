package com.ahjswy.cn.ui.outpurchase;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.model.DefDocPayType;
import com.ahjswy.cn.ui.BaseActivity;
import com.ahjswy.cn.ui.outpurchase.OutpurPayAdapter.Amount;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.views.EditTextWithDel;
import com.ahjswy.cn.views.EditTextWithDel.Clean;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class OutpurDocPayAct extends BaseActivity implements Clean, OnClickListener, OnFocusChangeListener, Amount {
	// 折后合计
	private TextView tvDiscountSubtotal;
	// 优惠
	private EditTextWithDel etPreference;
	// InpurDocPayAct
	// 优惠后应收
	private TextView tvReceiveable;
	// 已收
	private TextView tvReceived;
	// 待收
	private TextView tvLeft;
	// 确定
	private Button btnSave;

	private ListView listView;
	// private boolean isReadOnly;
	// private boolean isReceive;
	private double discountsubtotal;
	private List<DefDocPayType> listPayType;
	private double preference;
	private OutpurPayAdapter adapter;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.act_outdoc_pay);
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
		listView = (ListView) findViewById(R.id.listView);
		// 已收
		// isReceive = getIntent().getBooleanExtra("isreceive", true);
		// isReadOnly = getIntent().getBooleanExtra("isreadonly", true);
		// 折扣合计
		discountsubtotal = getIntent().getDoubleExtra("discountsubtotal", 0.0D);
		// 优惠
		preference = getIntent().getDoubleExtra("preference", 0.0D);
		listPayType = JSONUtil.str2list(getIntent().getStringExtra("listpaytype"), DefDocPayType.class);
//		etPreference.setCleanDrawable(false);
		etPreference.setClean(this);
		btnSave.setOnClickListener(this);

	}

	private void initDate() {
		double d2 = 0.0D;
		for (int i = 0; i < listPayType.size(); i++) {
			d2 += ((DefDocPayType) this.listPayType.get(i)).getAmount();
		}
		// 折扣合计
		tvDiscountSubtotal.setText(discountsubtotal + "");
		// 优惠
		etPreference.setText(preference + "");
		etPreference.setTag(etPreference.getText());
		etPreference.setOnFocusChangeListener(this);
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
		adapter = new OutpurPayAdapter(this);
		adapter.setData(listPayType);
		listView.setAdapter(adapter);
		adapter.setAmount(this);

	}

	@Override
	public void clean() {
		this.tvReceiveable.setText(discountsubtotal + "");
		setPrice();
	}

	@Override
	public void onClick(View v) {
		Intent localIntent = new Intent();
		localIntent.putExtra("preference",
				Utils.normalize(Utils.getDouble(etPreference.getText().toString()).doubleValue(), 2));
		localIntent.putExtra("listpaytype", JSONUtil.toJSONString(listPayType));
		setResult(RESULT_OK, localIntent);
		finish();
	}

	private void setPrice() {
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// 添加文字
		menu.add(0, 0, 0, "单击显示菜单").setTitle("确定").setShowAsAction(1);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
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
		}
		return true;
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

	@Override
	public void AmountSetPrice() {
		setPrice();
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("付款");
	}
}
