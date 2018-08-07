package com.ahjswy.cn.model;

import java.io.Serializable;
import java.sql.Timestamp;
/*
 * 作废  不能使用
 * *
 */

public class Goods implements Serializable {
	private static final long serialVersionUID = 1L;

	public String bigstocknumber;
	public Timestamp getstocktime;
	public String goodsclassname;
	public String id;
	public String model;
	public String name;// 商品名称
	public String pinyin;// 商品拼音
	public String barcode;// 条码
	public String specification;
	public int guaranteeperiod;
	public int guaranteeearlier;
	public String goodsclassid;
	public int costpricetype;
	public int property;
	public boolean isdiscount;
	public String salecue;
	public boolean isusebatch;
	public String info1;
	public String info2;
	public String info3;
	public String imagepath;
	public String remark;// 备注
	public boolean isavailable;// 是否可用
	public boolean isselected;
	public String builderid;// 创建人id
	public String buildtime;// 创建时间
	public String modifierid;
	public String modifytime;
	public String rversion;
	public String stocknumber;

	public String getBigstocknumber() {
		return bigstocknumber;
	}

	public void setBigstocknumber(String bigstocknumber) {
		this.bigstocknumber = bigstocknumber;
	}

	public Timestamp getGetstocktime() {
		return getstocktime;
	}

	public void setGetstocktime(Timestamp getstocktime) {
		this.getstocktime = getstocktime;
	}

	public String getGoodsclassname() {
		return goodsclassname;
	}

	public void setGoodsclassname(String goodsclassname) {
		this.goodsclassname = goodsclassname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public int getGuaranteeperiod() {
		return guaranteeperiod;
	}

	public void setGuaranteeperiod(int guaranteeperiod) {
		this.guaranteeperiod = guaranteeperiod;
	}

	public int getGuaranteeearlier() {
		return guaranteeearlier;
	}

	public void setGuaranteeearlier(int guaranteeearlier) {
		this.guaranteeearlier = guaranteeearlier;
	}

	public String getGoodsclassid() {
		return goodsclassid;
	}

	public void setGoodsclassid(String goodsclassid) {
		this.goodsclassid = goodsclassid;
	}

	public int getCostpricetype() {
		return costpricetype;
	}

	public void setCostpricetype(int costpricetype) {
		this.costpricetype = costpricetype;
	}

	public int getProperty() {
		return property;
	}

	public void setProperty(int property) {
		this.property = property;
	}

	public boolean isIsdiscount() {
		return isdiscount;
	}

	public void setIsdiscount(boolean isdiscount) {
		this.isdiscount = isdiscount;
	}

	public String getSalecue() {
		return salecue;
	}

	public void setSalecue(String salecue) {
		this.salecue = salecue;
	}

	public boolean isIsusebatch() {
		return isusebatch;
	}

	public void setIsusebatch(boolean isusebatch) {
		this.isusebatch = isusebatch;
	}

	public String getInfo1() {
		return info1;
	}

	public void setInfo1(String info1) {
		this.info1 = info1;
	}

	public String getInfo2() {
		return info2;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
	}

	public String getInfo3() {
		return info3;
	}

	public void setInfo3(String info3) {
		this.info3 = info3;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isIsavailable() {
		return isavailable;
	}

	public void setIsavailable(boolean isavailable) {
		this.isavailable = isavailable;
	}

	public boolean isIsselected() {
		return isselected;
	}

	public void setIsselected(boolean isselected) {
		this.isselected = isselected;
	}

	public String getBuilderid() {
		return builderid;
	}

	public void setBuilderid(String builderid) {
		this.builderid = builderid;
	}

	public String getBuildtime() {
		return buildtime;
	}

	public void setBuildtime(String buildtime) {
		this.buildtime = buildtime;
	}

	public String getModifierid() {
		return modifierid;
	}

	public void setModifierid(String modifierid) {
		this.modifierid = modifierid;
	}

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	public String getRversion() {
		return rversion;
	}

	public void setRversion(String rversion) {
		this.rversion = rversion;
	}

	public String getStocknumber() {
		return stocknumber;
	}

	public void setStocknumber(String stocknumber) {
		this.stocknumber = stocknumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Goods [bigstocknumber=" + bigstocknumber + ", getstocktime=" + getstocktime + ", goodsclassname="
				+ goodsclassname + ", id=" + id + ", model=" + model + ", name=" + name + ", pinyin=" + pinyin
				+ ", barcode=" + barcode + ", specification=" + specification + ", guaranteeperiod=" + guaranteeperiod
				+ ", guaranteeearlier=" + guaranteeearlier + ", goodsclassid=" + goodsclassid + ", costpricetype="
				+ costpricetype + ", property=" + property + ", isdiscount=" + isdiscount + ", salecue=" + salecue
				+ ", isusebatch=" + isusebatch + ", info1=" + info1 + ", info2=" + info2 + ", info3=" + info3
				+ ", imagepath=" + imagepath + ", remark=" + remark + ", isavailable=" + isavailable + ", isselected="
				+ isselected + ", builderid=" + builderid + ", buildtime=" + buildtime + ", modifierid=" + modifierid
				+ ", modifytime=" + modifytime + ", rversion=" + rversion + ", stocknumber=" + stocknumber + "]";
	}

}