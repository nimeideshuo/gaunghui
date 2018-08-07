package com.ahjswy.cn.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.request.ReqCommonPara;
import com.ahjswy.cn.request.ReqSynQueryRecords;
import com.ahjswy.cn.request.ReqSynQueryRecordsByID;
import com.ahjswy.cn.request.ReqSynUpdateInfo;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.utils.Utils_help;

public class ServiceSynchronize {
	// public static String tables =
	// "log_deleterecord,sz_department,sz_warehouse,sz_paytype,cu_customer,cu_customertype,sz_region,sz_visitline,sz_goods,sz_goodsunit,sz_goodsimage";
	private String baseAddress = "synchronize";
	private final int pagesize = 1000;
	// private final int pagesizeForID = 2000;
	private long rversion = 0L;

	public ServiceSynchronize() {
	}

	public ServiceSynchronize(long rversion) {
		this.rversion = rversion;
	}

	public int getPageSize() {
		return 1000;
	}

	public int getPageSizeForID() {
		return 2000;
	}

	public long setRVersion() {
		return this.rversion;
	}

	public void setRVersion(long rversion) {
		this.rversion = rversion;
	}

	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

	// 查询客户记录
	public List<HashMap<String, String>> syn_QueryCustomerRecords(int paramInt) {
		String url = Utils.getServiceAddress(this.baseAddress, "querycustomerrecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(paramInt);
		localReqSynQueryRecords.setPageSize(pagesize);
		localReqSynQueryRecords.setRVersion(this.rversion);
		map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor)) {
			return JSONUtil.parse2ListMap(infor);
		}
		return null;
	}

	// 查询经手人记录
	public List<HashMap<String, String>> syn_QueryAllCustomerRecords(int paramInt) {
		String url = Utils.getServiceAddress(this.baseAddress, "queryallcustomerrecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(paramInt);
		localReqSynQueryRecords.setPageSize(pagesize);
		localReqSynQueryRecords.setRVersion(this.rversion);
		map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor)) {
			return JSONUtil.parse2ListMap(infor);
		}
		return null;
	}

	// 查询客户类型记录
	public List<HashMap<String, String>> syn_QueryCustomerTypeRecords(int paramInt) {
		String url = Utils.getServiceAddress(this.baseAddress, "querycustomertyperecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(paramInt);
		localReqSynQueryRecords.setPageSize(pagesize);
		localReqSynQueryRecords.setRVersion(this.rversion);
		map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor))
			return JSONUtil.parse2ListMap(infor);
		return null;
	}

	// 删除记录
	public List<HashMap<String, String>> syn_QueryDeleteRecordRecords(int paramInt) {
		String url = Utils.getServiceAddress(this.baseAddress, "querydeleterecordrecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(paramInt);
		localReqSynQueryRecords.setPageSize(pagesize);
		localReqSynQueryRecords.setRVersion(this.rversion);
		map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor)) {
			return JSONUtil.parse2ListMap(infor);
		}
		return null;
	}

	// 查询 部门记录
	public List<HashMap<String, String>> syn_QueryDepartmentRecords(int pageindex) {
		String url = Utils.getServiceAddress(this.baseAddress, "querydepartmentrecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(pageindex);
		localReqSynQueryRecords.setPageSize(pagesize);
		localReqSynQueryRecords.setRVersion(this.rversion);
		map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor))
			return JSONUtil.parse2ListMap(infor);
		return null;
	}

	/**
	 * 查询商品类别
	 * 
	 * @param pageindex
	 */
	public List<HashMap<String, String>> syn_QueryGoodsClassrecords(int pageindex) {
		String url = Utils.getServiceAddress(this.baseAddress, "querygoodsclassrecords");
		ReqSynQueryRecords queryRecords = new ReqSynQueryRecords();
		queryRecords.setPageSize(pagesize);
		queryRecords.setRVersion(rversion);
		queryRecords.setPageIndex(pageindex);
		map.put("parameter", JSONUtil.object2Json(queryRecords));
		String serviceInfor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(serviceInfor))
			return JSONUtil.parse2ListMap(serviceInfor);
		return null;
	}

	/**
	 * 查询系统单价
	 * 
	 * @param pageindex
	 * @return
	 */
	public List<HashMap<String, String>> syn_QueryPricesystem(int pageindex) {
		String url = Utils.getServiceAddress(this.baseAddress, "querypricesystemrecords");
		ReqSynQueryRecords queryRecords = new ReqSynQueryRecords();
		queryRecords.setPageSize(pagesize);
		queryRecords.setRVersion(rversion);
		queryRecords.setPageIndex(pageindex);
		map.put("parameter", JSONUtil.object2Json(queryRecords));
		String serviceInfor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(serviceInfor))
			return JSONUtil.parse2ListMap(serviceInfor);
		return null;
	}

	public String syn_QueryGoodsImage(String paramString) {
		String url = Utils.getServiceAddress(this.baseAddress, "querygoodsimage");
		ReqCommonPara localReqCommonPara = new ReqCommonPara();
		localReqCommonPara.setStringValue(paramString);
		map.put("parameter", JSONUtil.object2Json(localReqCommonPara));
		return new Utils_help().getServiceInfor(url, map);
	}

	public List<HashMap<String, String>> syn_QueryGoodsImageRecords(int paramInt) {
		String url = Utils.getServiceAddress(this.baseAddress, "querygoodsimagerecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(paramInt);
		localReqSynQueryRecords.setPageSize(pagesize);
		localReqSynQueryRecords.setRVersion(this.rversion);
		map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor)) {
			return JSONUtil.parse2ListMap(infor);
		}
		return null;
	}

	public List<HashMap<String, String>> syn_QueryGoodsRecords(int paramInt) {
		String url = Utils.getServiceAddress(this.baseAddress, "querygoodsrecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(paramInt);
		localReqSynQueryRecords.setPageSize(pagesize);
		localReqSynQueryRecords.setRVersion(this.rversion);
		map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor))
			return JSONUtil.parse2ListMap(infor);
		return null;
	}

	public List<HashMap<String, String>> syn_QueryGoodsUnitRecords(int paramInt) {
		String url = Utils.getServiceAddress(this.baseAddress, "querygoodsunitrecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(paramInt);
		localReqSynQueryRecords.setPageSize(pagesize);
		localReqSynQueryRecords.setRVersion(rversion);
		map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor)) {
			return JSONUtil.parse2ListMap(infor);
		}
		return null;
	}

	// 付款方式
	public List<HashMap<String, String>> syn_QueryPayTypeRecords(int paramInt) {
		String url = Utils.getServiceAddress(this.baseAddress, "querypaytyperecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(paramInt);
		localReqSynQueryRecords.setPageSize(pagesize);
		localReqSynQueryRecords.setRVersion(this.rversion);
		map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor))
			return JSONUtil.parse2ListMap(infor);
		return null;
	}

	public List<HashMap<String, String>> syn_QueryRegionRecords(int paramInt) {
		String url = Utils.getServiceAddress(this.baseAddress, "queryregionrecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(paramInt);
		localReqSynQueryRecords.setPageSize(pagesize);
		localReqSynQueryRecords.setRVersion(this.rversion);
		map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor))
			return JSONUtil.parse2ListMap(infor);
		return null;
	}

	// 查询升级返回值
	public List<ReqSynUpdateInfo> syn_QueryUpdateInfo(long rversion) {
		String url = Utils.getServiceAddress(this.baseAddress, "queryupdateinfo");
		ArrayList<ReqSynUpdateInfo> localArrayList = new ArrayList<ReqSynUpdateInfo>();
		localArrayList.add(new ReqSynUpdateInfo("log_deleterecord", 0L));
		localArrayList.add(new ReqSynUpdateInfo("sz_department", 0L));
		localArrayList.add(new ReqSynUpdateInfo("sz_warehouse", 0L));
		localArrayList.add(new ReqSynUpdateInfo("sz_paytype", 0L));
		localArrayList.add(new ReqSynUpdateInfo("cu_customer", 0L));
		localArrayList.add(new ReqSynUpdateInfo("cu_allcustomer", 0L));
		localArrayList.add(new ReqSynUpdateInfo("cu_customertype", 0L));
		localArrayList.add(new ReqSynUpdateInfo("sz_region", 0L));
		localArrayList.add(new ReqSynUpdateInfo("sz_visitline", 0L));
		localArrayList.add(new ReqSynUpdateInfo("sz_goods", 0L));
		localArrayList.add(new ReqSynUpdateInfo("sz_goodsunit", 0L));
		localArrayList.add(new ReqSynUpdateInfo("sz_goodsclass", 0L));
		localArrayList.add(new ReqSynUpdateInfo("sz_pricesystem", 0L));
		localArrayList.add(new ReqSynUpdateInfo("sz_unit", 0L));
		localArrayList.add(new ReqSynUpdateInfo("cu_customerfieldsalegoods", 0L));
		localArrayList.add(new ReqSynUpdateInfo("sz_goodsprice", 0L));
		localArrayList.add(new ReqSynUpdateInfo("sz_goodsimage", 0L));
		localArrayList.add(new ReqSynUpdateInfo("rversion", rversion));
		localArrayList.add(new ReqSynUpdateInfo("pagesize", pagesize));
		map.put("parameter", JSONUtil.object2Json(localArrayList));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor)) {
			return JSONUtil.str2list(infor, ReqSynUpdateInfo.class);
		}
		return null;
	}

	public List<HashMap<String, String>> syn_QueryVisitLineRecords(int paramInt) {
		String url = Utils.getServiceAddress(this.baseAddress, "queryvisitlinerecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(paramInt);
		localReqSynQueryRecords.setPageSize(pagesize);
		localReqSynQueryRecords.setRVersion(this.rversion);
		map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor)) {
			return JSONUtil.parse2ListMap(infor);
		}
		return null;
	}

	public List<HashMap<String, String>> syn_QueryWarehouseRecords(int pageindex) {
		String url = Utils.getServiceAddress(this.baseAddress, "querywarehouserecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(pageindex);
		localReqSynQueryRecords.setPageSize(pagesize);
		localReqSynQueryRecords.setRVersion(rversion);
		map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor)) {
			return JSONUtil.parse2ListMap(infor);
		}
		return null;
	}

	public List<HashMap<String, String>> syn_QueryUnitrecords(int pageindex) {
		String url = Utils.getServiceAddress(this.baseAddress, "queryunitrecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(pageindex);
		localReqSynQueryRecords.setPageSize(pagesize);
		localReqSynQueryRecords.setRVersion(rversion);
		map.put("parameter", JSONUtil.object2Json(localReqSynQueryRecords));
		String infor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(infor)) {
			return JSONUtil.parse2ListMap(infor);
		}
		return null;
	}

	// TODO 查询商品列表客史
	public List<HashMap<String, String>> syn_Queryallcustomergoodsrecords(int pageindex) {
		String url = Utils.getServiceAddress(this.baseAddress, "queryallcustomergoodsrecords");
		ReqSynQueryRecordsByID recordsByID = new ReqSynQueryRecordsByID();
		// recordsByID.setId(customerid);
		recordsByID.setPageIndex(pageindex);
		recordsByID.setPageSize(pagesize);
		map.put("parameter", JSONUtil.object2Json(recordsByID));
		String serviceInfor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(serviceInfor))
			return JSONUtil.parse2ListMap(serviceInfor);
		return null;
	}

	/**
	 * 客户商品客史 增量同步 查询改变后的
	 * 
	 * @param pageindex
	 * @return
	 */
	public List<HashMap<String, String>> syn_Querycustomergoodsrecords(int pageindex, long rversion) {
		// querycustomergoodsrecords
		String url = Utils.getServiceAddress(this.baseAddress, "querycustomergoodsrecords");
		ReqSynQueryRecordsByID recordsByID = new ReqSynQueryRecordsByID();
		// recordsByID.setId(customerid);
		recordsByID.setPageIndex(pageindex);
		recordsByID.setPageSize(pagesize);
		recordsByID.setRVersion(rversion);
		try {
			map.put("parameter", JSONUtil.object2Json(recordsByID));
			String serviceInfor = new Utils_help().getServiceInfor(url, map);
			if (RequestHelper.isSuccess(serviceInfor))
				return JSONUtil.parse2ListMap(serviceInfor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<HashMap<String, String>> syn_QueryGoodsPriceRecords(int pageindex) {
		String url = Utils.getServiceAddress(this.baseAddress, "querygoodspricerecords");
		ReqSynQueryRecords localReqSynQueryRecords = new ReqSynQueryRecords();
		localReqSynQueryRecords.setPageIndex(pageindex);
		localReqSynQueryRecords.setPageSize(1000);
		localReqSynQueryRecords.setRVersion(this.rversion);
		// map.put("usergoodsclassid", new
		// AccountPreference().getValue("user_goodsclassid"));
		map.put("parameter", JSONUtil.toJSONString(localReqSynQueryRecords));
		String serviceInfor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(serviceInfor)) {
			return JSONUtil.parse2ListMap(serviceInfor);
		}
		return null;
	}

	public List<HashMap<String, String>> syn_QueryCustomerGoodsRecords(String customerid, int pageindex) {
		String url = Utils.getServiceAddress(this.baseAddress, "querycustomergoodsrecords");
		ReqSynQueryRecordsByID recordsByID = new ReqSynQueryRecordsByID();
		recordsByID.setId(customerid);
		recordsByID.setPageIndex(pageindex);
		recordsByID.setPageSize(pagesize);
		map.put("parameter", JSONUtil.toJSONString(recordsByID));

		String serviceInfor = new Utils_help().getServiceInfor(url, map);
		if (RequestHelper.isSuccess(serviceInfor))
			return JSONUtil.parse2ListMap(serviceInfor);
		return null;
	}

}