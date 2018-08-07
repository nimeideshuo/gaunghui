package com.ahjswy.cn.print;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ahjswy.cn.model.FieldSaleForPrint;
import com.ahjswy.cn.model.FieldSaleItemForPrint;
import com.ahjswy.cn.utils.Utils;

public class PrintData {
	public List<FieldSaleItemForPrint> getTestData() {
		ArrayList<FieldSaleItemForPrint> localArrayList = new ArrayList<FieldSaleItemForPrint>();
		FieldSaleItemForPrint localFieldSaleItemForPrint = new FieldSaleItemForPrint();
		localFieldSaleItemForPrint.setItemtype("销");
		localFieldSaleItemForPrint.setGoodsid("00001");
		localFieldSaleItemForPrint.setGoodsname("电视机");
		localFieldSaleItemForPrint.setBarcode("6000007890123");
		localFieldSaleItemForPrint.setSpecification("1台");
		localFieldSaleItemForPrint.setUnitname("台");
		localFieldSaleItemForPrint.setNum(10.0D);
		localFieldSaleItemForPrint.setPrice(1200.0D);
		localFieldSaleItemForPrint.setDiscountratio(1.0D);
		localFieldSaleItemForPrint.setDiscountsubtotal(12000.0D);
		localFieldSaleItemForPrint.setRemark("测试电视机");
		localArrayList.add(localFieldSaleItemForPrint);
		localFieldSaleItemForPrint = new FieldSaleItemForPrint();
		localFieldSaleItemForPrint.setItemtype("赠");
		localFieldSaleItemForPrint.setGoodsid("00002");
		localFieldSaleItemForPrint.setGoodsname("空调");
		localFieldSaleItemForPrint.setBarcode("6000009012345");
		localFieldSaleItemForPrint.setSpecification("1台");
		localFieldSaleItemForPrint.setUnitname("台");
		localFieldSaleItemForPrint.setNum(1.0D);
		localFieldSaleItemForPrint.setPrice(0.0D);
		localFieldSaleItemForPrint.setDiscountratio(1.0D);
		localFieldSaleItemForPrint.setDiscountsubtotal(0.0D);
		localFieldSaleItemForPrint.setRemark("测试电视机");
		localArrayList.add(localFieldSaleItemForPrint);
		return localArrayList;
	}

	public FieldSaleForPrint getTestInfo() {
		FieldSaleForPrint print = new FieldSaleForPrint();
		print.setDoctype("销售单");
		print.setShowid("140101001");
		print.setCustomername("某某超市");
		print.setDepartmentname("销售部");
		print.setBuildername("张三");
		print.setBuildtime("2014-01-01 15:27:22");
		print.setSumamount("合计:" + Utils.getSubtotalMoney(12000.0D));
		print.setReceivable("应收:" + Utils.getSubtotalMoney(12000.0D));
		print.setReceived("已收:" + Utils.getSubtotalMoney(2000.0D));
		print.setNum("数量:" + String.valueOf(11.0D));
		print.setPreference("优惠:" + String.valueOf(0.0D));
		return print;
	}

	public HashMap<String, String> getTestItem(String name, String number, String price, String subtotal) {
		HashMap<String, String> localHashMap = new HashMap<String, String>();
		localHashMap.put("name", name);
		localHashMap.put("number", number);
		localHashMap.put("subtotal", subtotal);
		localHashMap.put("price", price);
		return localHashMap;
	}

	private List<FieldSaleItemForPrint> data;
	private HashMap<String, String> info;

	public List<FieldSaleItemForPrint> getData() {
		return this.data;
	}

	public HashMap<String, String> getInfo() {
		return this.info;
	}

	public void setData(List<FieldSaleItemForPrint> data) {
		this.data = data;
	}

	public void setInfo(HashMap<String, String> info) {
		this.info = info;
	}
}
