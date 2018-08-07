package com.ahjswy.cn.ui;

import java.io.Serializable;
import java.util.List;

import com.ahjswy.cn.R;
import com.ahjswy.cn.dao.DepartmentDAO;
import com.ahjswy.cn.model.Department;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DepartmentSearchAct extends BaseActivity implements OnItemClickListener {
	private List<Department> departments;
	private ListView lvDepartments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_department);
		lvDepartments = (ListView) findViewById(R.id.lvDepartments);
		lvDepartments.setOnItemClickListener(this);
		departments = new DepartmentDAO().getAllDepartment();
		if ((departments == null) || (departments.size() == 0)) {
			return;
		} else {
			String[] arrayOfString = new String[departments.size()];
			for (int i = 0; i <= departments.size(); i++) {
				if (i >= this.departments.size()) {
					ArrayAdapter<String> localArrayAdapter = new ArrayAdapter<String>(this,
							android.R.layout.simple_list_item_1, arrayOfString);
					lvDepartments.setAdapter(localArrayAdapter);
					return;
				}
				arrayOfString[i] = ((Department) this.departments.get(i)).getDname();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent localIntent = new Intent();
		localIntent.putExtra("department", (Serializable) this.departments.get(position));
		setResult(RESULT_OK, localIntent);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			setResult(RESULT_FIRST_USER, new Intent());
			finish();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void setActionBarText() {
		getActionBar().setTitle("部门");
	}

}
