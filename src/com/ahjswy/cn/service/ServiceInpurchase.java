package com.ahjswy.cn.service;

import java.util.LinkedHashMap;

public class ServiceInpurchase {
	public static String URL = "MyServices/MyTestService/Jweb";

	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

	// 采购入库 过账
	// public String str_CheckCGDoc(boolean isprint) {
	// String url = Utils.getServiceAddress(this.baseAddress, "采购入库 接口地址");
	// map.put("proposerid", SystemState.getUser().getId());
	// map.put("parameter", JSONUtil.toJSONString(""));
	// map.put("isprint", isprint + "");// 是否打印
	// return new Utils_help().getServiceInfor(url, map);
	//
	// }

	// // 过账打印
	// public String str_CheckCG_Doc(boolean isprint) {
	// map.put("userid", SystemState.getUser().getId());
	// map.put("proposerid", SystemState.getUser().getId());
	// map.put("parameter", JSONUtil.toJSONString(""));
	// map.put("isprint", isprint + "");// 是否打印
	// return new HttpUtils().sendGet(url, map);
	// }

	// 采购入库过账
	// public String savePurchase(DocCG doccg, List<DefDocItemCG> docitemcg,
	// List<DefDocPayType> defdocpaytype,
	// List<Long> deleteitem, Boolean isprint) {
	// // doccg._goodsitem = JSONUtil.toJSONString(docitemcg);
	// // doccg._typelist = JSONUtil.toJSONString(defdocpaytype);
	// return new HttpRequestUtils().sendGet(Utils.getServiceAddress(URL,
	// "SavePurchase"), "");
	// }

}
