package com.ahjswy.cn.service;

import java.util.LinkedHashMap;

import com.ahjswy.cn.app.A;
import com.ahjswy.cn.app.MyApplication;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.request.ReqCommonPara;
import com.ahjswy.cn.request.TerminalEntity;
import com.ahjswy.cn.response.VersionEntity;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.Service;
import com.ahjswy.cn.utils.Utils_help;

import android.os.Build;

public class ServiceSystem {
	private String baseAddress = "system";
	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

	public String sys_CheckRegister(String ip) {
		String url = Service.getServiceAddress(this.baseAddress, "checkregister");
		TerminalEntity localTerminalEntity = new TerminalEntity(MyApplication.getInstance().getUniqueCode(),
				"mmendianban");
		map.put("parameter", JSONUtil.toJSONString(localTerminalEntity));
		return new Utils_help().getServiceInfor(ip, url, map);
	}

	public VersionEntity sys_CheckVersion() {
		String url = Service.getServiceAddress(this.baseAddress, "checkversion");
		ReqCommonPara localReqCommonPara = new ReqCommonPara();
		localReqCommonPara.setStringValue("mmendianban");
		map.put("parameter", JSONUtil.toJSONString(localReqCommonPara));
		String version = new Utils_help().getServiceInfor(url, map);
		if (A.isFail(version)) {
			return null;
		}
		return (VersionEntity) JSONUtil.readValue(version, VersionEntity.class);
	}

	/**
	 * 获取系统默认设置
	 * 
	 * @return
	 */
	public String sys_GetBizParameter() {
		String url = Service.getServiceAddress(this.baseAddress, "getbizparameter");
		ReqCommonPara localReqCommonPara = new ReqCommonPara();
		localReqCommonPara.setStringValue(
				"'intPricePrecision','intSubtotalPrecision','intReceivablePrecision','intSubtotalChange','intOutDocUnit','intTransferDocUnit','strCancelWarehouse','isAutoChangeGoodsDiscountAfterDoc','companyname','intGenerateBatch','strBatchPrefix','strBatchSuffix','strPriceSystem'");
		map.put("parameter", JSONUtil.toJSONString(localReqCommonPara));
		return new Utils_help().getServiceInfor(url, map);
	}

	public long sys_QueryMaxRVersion(String paramString) {
		int i = 0;
		String str3 = null;
		String url = Service.getServiceAddress(this.baseAddress, "querymaxrversion");
		ReqCommonPara localReqCommonPara = new ReqCommonPara();
		localReqCommonPara.setStringValue(paramString);

		map.put("parameter", JSONUtil.toJSONString(localReqCommonPara));
		String infor = new Utils_help().getServiceInfor(url, map);
		// 判断加密狗?
		if (RequestHelper.isSuccess(infor))
			str3 = (String) JSONUtil.parse2Map(infor).get("maxrversion");
		try {
			int j = Integer.parseInt(str3);
			i = j;
			return i;
		} catch (NumberFormatException localNumberFormatException) {
		}
		return i;
	}

	// 注册 终端
	public String sys_Register(String regis_id, String padUser) {
		String url = Service.getServiceAddress(this.baseAddress, "register");
		TerminalEntity localTerminalEntity = new TerminalEntity(MyApplication.getInstance().getUniqueCode(),
				"mmendianban");
		localTerminalEntity.setIdentifier(MyApplication.getInstance().getUniqueCode());
		localTerminalEntity.setVersionKey("mmendianban");
		localTerminalEntity.setLoginName(null);
		localTerminalEntity.setMac(MyApplication.getInstance().getMac());
		localTerminalEntity.setModel(Build.MODEL);
		localTerminalEntity.setRegistrationCode(regis_id);
		localTerminalEntity.setOwner(padUser);
		localTerminalEntity.setIsPC(false);
		map.put("parameter", JSONUtil.toJSONString(localTerminalEntity));
		return new Utils_help().getServiceInfor(url, map);
	}

	/**
	 * 获取数据库的账号密码
	 * 
	 * @return
	 */
	public String sys_getdbuserinfo() {
		String url = Service.getServiceAddress(this.baseAddress, "getdbuserinfo");
		return new Utils_help().getServiceInfor(url, map);
	}
}