package com.ahjswy.cn.ui.out_in_goods;

/**
 * 打印服务类
 * 
 * @author Administrator
 *
 */
public class PrintEntity {
	/*
	 * 单据参数
	 */
	String docdraftjson;
	/**
	 * 打印机的名称
	 */
	String printname;
	/**
	 * 打印的用户
	 */
	String userid;

	public String getDocdraftjson() {
		return docdraftjson;
	}

	public void setDocdraftjson(String docdraftjson) {
		this.docdraftjson = docdraftjson;
	}

	public String getPrintname() {
		return printname;
	}

	public void setPrintname(String printname) {
		this.printname = printname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "PrintEntity [docdraftjson=" + docdraftjson + ", printname=" + printname + ", userid=" + userid + "]";
	}

}
