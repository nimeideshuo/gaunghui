package com.ahjswy.cn.service;

import java.util.LinkedHashMap;

import com.ahjswy.cn.request.ReqCommonPara;
import com.ahjswy.cn.request.ReqSupQueryDepartment;
import com.ahjswy.cn.request.ReqSupQueryStockNum;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.Service;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.utils.Utils_help;

public class ServiceSupport {
	private String baseAddress = "support";
	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

	public String QueryRegionCustomers(String paramString) {
		String url = Service.getServiceAddress(this.baseAddress, "queryregioncustomers");
		ReqCommonPara localReqCommonPara = new ReqCommonPara();
		localReqCommonPara.setStringValue(paramString);
		map.put("parameter", JSONUtil.toJSONString(localReqCommonPara));
		return new Utils_help().getServiceInfor(url, map);
	}

	public String QueryVisitLineCustomers(String paramString) {
		String url = Utils.getServiceAddress(this.baseAddress, "queryvisitlinecustomers");
		ReqCommonPara localReqCommonPara = new ReqCommonPara();
		localReqCommonPara.setStringValue(paramString);
		map.put("parameter", JSONUtil.toJSONString(localReqCommonPara));
		return new Utils_help().getServiceInfor(url, map);
	}

	public String sup_QueryAccountSet() {
		String url = Utils.getServiceAddress(this.baseAddress, "querysaccountset");
		return new Utils_help().getServiceInfor(url, map);
	}

	// 部门选择
	public String sup_QueryDepartment(String paramString, boolean paramBoolean) {
		String url = Utils.getServiceAddress(this.baseAddress, "querydepartment");
		ReqSupQueryDepartment localReqSupQueryDepartment = new ReqSupQueryDepartment(paramString, paramBoolean);
		map.put("parameter", JSONUtil.toJSONString(localReqSupQueryDepartment));
		return new Utils_help().getServiceInfor(url, map);
	}

	public String sup_QueryGoodsPrice(String goodsid) {
		String url = Utils.getServiceAddress(this.baseAddress, "querygoodsprice");
		map.put("goodsid", goodsid);
		return new Utils_help().getServiceInfor(url, map);
	}
	/**
	 *  //TODO 查询商品库存
	 * @param goodsid
	 * @return
	 */
	public String sup_QueryGoodsWarehouseStock(String goodsid) {
		String url = Utils.getServiceAddress(this.baseAddress, "querygoodswarehousestock");
		map.put("goodsid", goodsid);
		return new Utils_help().getServiceInfor(url, map);
	}

	public String sup_QueryRegion() {
		String url = Utils.getServiceAddress(this.baseAddress, "queryregion");
		return new Utils_help().getServiceInfor(url, map);
	}
	/**
	 *  //TODO 查询商品库存
	 * @param goodsid
	 * @return
	 */
	public String sup_QueryStockNum(String goodsid, String warehouseid) {
		String url = Utils.getServiceAddress(this.baseAddress, "querystocknum");
		ReqSupQueryStockNum supQueryStockNum = new ReqSupQueryStockNum();
		supQueryStockNum.setGoodsId(goodsid);
		supQueryStockNum.setWarehouseId(warehouseid);
		supQueryStockNum.setIsIncludeTruck(false);
		map.put("parameter", JSONUtil.toJSONString(supQueryStockNum));
		return new Utils_help().getServiceInfor(url, map);
	}

	public String sup_QueryVisitLine() {
		String url = Utils.getServiceAddress(this.baseAddress, "queryvisitline");
		return new Utils_help().getServiceInfor(url, map);
	}
}