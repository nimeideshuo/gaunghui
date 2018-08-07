package com.ahjswy.cn.ui.inpurchase;

import com.ahjswy.cn.R;
import com.ahjswy.cn.ui.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class InpurchaseDocPayAct extends BaseActivity implements OnClickListener {
	// // 折后合计
	// private TextView tvDiscountSubtotal;
	// // 优惠
	// private EditTextWithDel etPreference;
	// // 优惠后应收
	// private TextView tvReceiveable;
	// // 已收
	// private TextView tvReceived;
	// // 代收
	// private TextView tvLeft;
	// // 确定
	// private Button btnSave;
	// private ListView listView;
	// // 折后合计值
	// private double discountsubtotal;
	// private List<DefDocPayType> listPayType;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.act_outdoc_pay);
		initView();
	}

	private void initView() {
		// tvDiscountSubtotal = (TextView)
		// findViewById(R.id.tvDiscountSubtotal);
		// etPreference = (EditTextWithDel) findViewById(R.id.etPreference);
		// tvReceiveable = (TextView) findViewById(R.id.tvReceiveable);
		// tvReceived = (TextView) findViewById(R.id.tvReceived);
		// tvLeft = (TextView) findViewById(R.id.tvLeft);
		// btnSave = (Button) findViewById(R.id.btnSave);
		// discountsubtotal = getIntent().getDoubleExtra("discountsubtotal",
		// 0.0D);
		// btnSave.setOnClickListener(this);
		// listView = (ListView) findViewById(R.id.listView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case 1:

			break;

		default:
			break;
		}
	}

}
