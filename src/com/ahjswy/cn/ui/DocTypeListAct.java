package com.ahjswy.cn.ui;

import com.ahjswy.cn.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DocTypeListAct extends BaseActivity implements OnItemClickListener {
	private String[] doctypes = { "销售单", "销售退货单" };// , "销售换货单"
	private ListView listView;

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.act_department);
		listView = ((ListView) findViewById(R.id.lvDepartments));
		listView.setOnItemClickListener(this);
		ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				doctypes);
		listView.setAdapter(localArrayAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_FIRST_USER);
			finish();
			break;

		default:
			break;
		}

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent localIntent = new Intent();
		switch (position) {
		case 0:
			localIntent.putExtra("doctypeid", "13");
			break;
		case 1:
			localIntent.putExtra("doctypeid", "14");
			break;
		case 2:
			localIntent.putExtra("doctypeid", "15");
			break;
		}
		localIntent.putExtra("doctypename", doctypes[position]);
		setResult(RESULT_OK, localIntent);
		finish();
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("单据类型");
	}
}
