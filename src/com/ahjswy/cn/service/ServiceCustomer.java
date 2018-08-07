package com.ahjswy.cn.service;

import java.util.LinkedHashMap;

import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.model.Customer;
import com.ahjswy.cn.request.ReqCustomerDingDoc;
import com.ahjswy.cn.request.ReqCustomerdebt;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.Service;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.utils.Utils_help;

public class ServiceCustomer {
	private String baseAddress = "customer";
	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

	public String cu_AddCustomerForSale(Customer paramCustomer, boolean isignorsamename, boolean isignorsametel) {
		String url = Service.getServiceAddress(this.baseAddress, "addcustomerforsale");
		map.put("parameter", JSONUtil.toJSONString(paramCustomer));
		map.put("isignorsamename", isignorsamename + "");
		map.put("isignorsametel", isignorsametel + "");
		return new Utils_help().getServiceInfor(url, map);
	}

	public String cu_GetNewCustomerID() {
		String url = Utils.getServiceAddress(this.baseAddress, "getnewcustomerid");
		return new Utils_help().getServiceInfor(url, map);
	}

	public String cu_GetCustomer() {
		String url = Utils.getServiceAddress(this.baseAddress, "getcustomer");
		return new Utils_help().getServiceInfor(url, map);

	}

	// 查询 客户欠款
	public String cu_queryCustomer(ReqCustomerdebt customer) {
		String url = Utils.getServiceAddress(this.baseAddress, "querycustomerdebt");
		map.put("parameter", JSONUtil.toJSONString(customer));
		return new Utils_help().getServiceInfor(url, map);
	}

	// 查询用的可冲抵预售款
	public String cu_queryCustomerPreReceived(ReqCustomerdebt customer) {
		String url = Utils.getServiceAddress(this.baseAddress, "QueryCustomerPreReceived");
		map.put("parameter", JSONUtil.toJSONString(customer));
		return new Utils_help().getServiceInfor(url, map);
	}

	// customer\querycustomerdingdoc
	// customer\querycustomerdingdocitem
	// 查询对应类型
	public String cu_queryCustomerDingdoc(ReqCustomerDingDoc dingDoc) {
		String url = Utils.getServiceAddress(this.baseAddress, "querycustomerdingdoc");
		map.put("parameter", JSONUtil.object2Json(dingDoc));
		return new Utils_help().getServiceInfor(url, map);
	}

	// 查询对应类型 详情
	public String cu_queryCustomerDingDocitem(ReqCustomerDingDoc dingDoc) {
		String url = Utils.getServiceAddress(this.baseAddress, "querycustomerdingdocitem");
		map.put("parameter", JSONUtil.object2Json(dingDoc));
		return new Utils_help().getServiceInfor(url, map);
	}

}