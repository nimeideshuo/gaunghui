package com.ahjswy.cn.service;

import java.util.LinkedHashMap;

import com.ahjswy.cn.request.ReqVstAddVisitCustomerJobImage;
import com.ahjswy.cn.utils.JSONUtil;
import com.ahjswy.cn.utils.Utils;
import com.ahjswy.cn.utils.Utils_help;

public class ServiceVisit {
	private String baseAddress = "visit";
	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	
	public String vst_UploadVisitImage(ReqVstAddVisitCustomerJobImage image) {
		String url = Utils.getServiceAddress(this.baseAddress, "uploadvisitimage");
		map.put("parameter", JSONUtil.toJSONString(image));
		return new Utils_help().getServiceInfor(url, map);
	}
}
