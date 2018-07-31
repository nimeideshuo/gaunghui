package com.ahjswy.cn.ui;

import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.request.ReqCustomerDingDoc;
import com.ahjswy.cn.service.ServiceCustomer;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.PDH;
import com.ahjswy.cn.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 查询客户单据列表
 * 
 * @author Administrator
 *
 */
public class QueryDocListActivity extends BaseActivity implements OnItemClickListener {
	private DocRecordListAdapter adapter;
	private ListView listView;
	private String doctype;// 查询的 单据类型
	private String customerid;// 查询的客户id
	// 订单类型，非空, 12 销售订单，02 采购订单
	public final static String TYPE_OUT_ORDER = "12";
	public final static String TYPE_OUT_PURCHASE = "02";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = new ListView(this);
		setContentView(listView);
		adapter = new DocRecordListAdapter(this, null);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		doctype = getIntent().getStringExtra("doctype");
		customerid = getIntent().getStringExtra("customerid");
		loadData();
	}

	private void loadData() {
		PDH.show(this, new PDH.ProgressCallBack() {
			public void action() {
				ReqCustomerDingDoc dingDoc = new ReqCustomerDingDoc();
				dingDoc.setDoctype("" + doctype);
				dingDoc.setCustomerid(customerid);
				String str = new ServiceCustomer().cu_queryCustomerDingdoc(dingDoc);
				handler.sendMessage(handler.obtainMessage(0, str));
			}
		});
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (RequestHelper.isSuccess(msg.obj.toString())) {
				String json = msg.obj.toString();
				List<ReqCustomerDingDoc> listDoc = JSONUtil.parseArray(json, ReqCustomerDingDoc.class);
				adapter.setItem(listDoc);
				adapter.notifyDataSetChanged();
			}
		};
	};

	public class DocRecordListAdapter extends BaseAdapter {
		List<ReqCustomerDingDoc> list;
		Context mContext;

		public DocRecordListAdapter(Context context, List<ReqCustomerDingDoc> listDoc) {
			super();
			mContext = context;
			this.list = listDoc;
		}

		@Override
		public int getCount() {
			return list == null ? 0 : list.size();
		}

		@Override
		public ReqCustomerDingDoc getItem(int position) {
			return list.get(position);
		}

		public void setItem(List<ReqCustomerDingDoc> listDoc) {
			this.list = listDoc;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Item vGroup = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.item_sale_record, null);
				vGroup = new Item(convertView);
				convertView.setTag(vGroup);
			} else {
				vGroup = (Item) convertView.getTag();
			}
			vGroup.tvId.setText(String.valueOf(position + 1));
			vGroup.setValue(list.get(position));
			return convertView;
		}

		public class Item {
			public TextView tvBuilder;
			public TextView tvCustomer;
			public TextView tvDate;
			public TextView tvDocType;
			public TextView tvId;
			public TextView tvShowId;
			public TextView tvStatus;

			public Item(View v) {
				this.tvId = (TextView) v.findViewById(R.id.tvId);
				this.tvShowId = (TextView) v.findViewById(R.id.tvShowId);
				this.tvDocType = (TextView) v.findViewById(R.id.tvDocType);
				this.tvBuilder = (TextView) v.findViewById(R.id.tvBuilder);
				this.tvDate = (TextView) v.findViewById(R.id.tvDate);
				this.tvCustomer = (TextView) v.findViewById(R.id.tvCustomer);
				this.tvStatus = (TextView) v.findViewById(R.id.tvStatus);
			}

			public void setValue(ReqCustomerDingDoc item) {
				this.tvShowId.setText(item.getShowid());
				this.tvBuilder.setText(item.getBuildername());
				this.tvDate.setText(Utils.formatDate(item.getBuildtime(), "yyyy-MM-dd HH:mm"));
				this.tvCustomer.setText("客户：" + item.getCustomername());
				// 订单类型，非空, 12 销售订单，02 采购订单
				if ("12".equals(item.getDoctype())) {
					this.tvDocType.setText("销售订单");
				} else if ("02".equals(item.getDoctype())) {
					this.tvDocType.setText("采购订单");
				}
			}

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ReqCustomerDingDoc item = adapter.getItem(position);
		Intent intent = new Intent();
		intent.putExtra("docid", item.getDocid());
		intent.putExtra("doctype", "" + item.getDoctype());
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public void setActionBarText() {
		switch (doctype) {
		case TYPE_OUT_ORDER:
			setTitle("销售订单");
			break;
		case TYPE_OUT_PURCHASE:
			setTitle("采购订单");
			break;
		default:
			break;
		}
	}
}
