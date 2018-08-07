package com.ahjswy.cn.bean;

import java.io.Serializable;

public class DocCG implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String _userid;// 用户id
	public String _departmentid;// 部门id
	public String _departmentName;// =========== 部门名称
	public String _warehouseid;// 仓库id
	public String _warehouseName;// =============仓库名称
	public String _customerid;// 客户id
	public String _customerName;// ===============客户名称
	public double _discountratio;// 折扣比
	public String _preference;// 优惠
	public String _deliverytime;// 交货日期
	public String _settletime;// 结算日期
	public String _printnum;// 打印数量
	public String _printtemplate;// 打印模板
	public String _customeraddress;// 客户地址
	public String _issettleup;// 结账
	public String _isposted;// 是否打印
	public String _financialcfid;// 财务id
	public String _summary;// 摘要
	public String _remark;// 备注
	public String _isdistribution;// 分销
	public String _distributionid;// 分配id
	public String _isavailable;// 是否可用
	public String _builderid;// 建造id
	public String _buildtime;// 建造时间
	public String _makerid;// 制造id
	public String _maketime;// 制造时间
	public String _goodsitem;// 商品集合
	public String _typelist;// 支付类型

	public String get_userid() {
		return _userid;
	}

	public void set_userid(String _userid) {
		this._userid = _userid;
	}

	public String get_departmentid() {
		return _departmentid;
	}

	public void set_departmentid(String _departmentid) {
		this._departmentid = _departmentid;
	}

	public String get_departmentName() {
		return _departmentName;
	}

	public void set_departmentName(String _departmentName) {
		this._departmentName = _departmentName;
	}

	public String get_warehouseid() {
		return _warehouseid;
	}

	public void set_warehouseid(String _warehouseid) {
		this._warehouseid = _warehouseid;
	}

	public String get_warehouseName() {
		return _warehouseName;
	}

	public void set_warehouseName(String _warehouseName) {
		this._warehouseName = _warehouseName;
	}

	public String get_customerid() {
		return _customerid;
	}

	public void set_customerid(String _customerid) {
		this._customerid = _customerid;
	}

	public String get_customerName() {
		return _customerName;
	}

	public void set_customerName(String _customerName) {
		this._customerName = _customerName;
	}

	public double get_discountratio() {
		return _discountratio;
	}

	public void set_discountratio(double _discountratio) {
		this._discountratio = _discountratio;
	}

	public String get_preference() {
		if (_preference == null) {
			this._preference = "0";
		}
		return _preference;
	}

	public void set_preference(String _preference) {
		this._preference = _preference;
	}

	public String get_deliverytime() {
		return _deliverytime;
	}

	public void set_deliverytime(String _deliverytime) {
		this._deliverytime = _deliverytime;
	}

	public String get_settletime() {
		return _settletime;
	}

	public void set_settletime(String _settletime) {
		this._settletime = _settletime;
	}

	public String get_printnum() {
		return _printnum;
	}

	public void set_printnum(String _printnum) {
		this._printnum = _printnum;
	}

	public String get_printtemplate() {
		return _printtemplate;
	}

	public void set_printtemplate(String _printtemplate) {
		this._printtemplate = _printtemplate;
	}

	public String get_customeraddress() {
		return _customeraddress;
	}

	public void set_customeraddress(String _customeraddress) {
		this._customeraddress = _customeraddress;
	}

	public String get_issettleup() {
		return _issettleup;
	}

	public void set_issettleup(String _issettleup) {
		this._issettleup = _issettleup;
	}

	public String get_isposted() {
		return _isposted;
	}

	public void set_isposted(String _isposted) {
		this._isposted = _isposted;
	}

	public String get_financialcfid() {
		return _financialcfid;
	}

	public void set_financialcfid(String _financialcfid) {
		this._financialcfid = _financialcfid;
	}

	public String get_summary() {
		return _summary;
	}

	public void set_summary(String _summary) {
		this._summary = _summary;
	}

	public String get_remark() {
		return _remark;
	}

	public void set_remark(String _remark) {
		this._remark = _remark;
	}

	public String get_isdistribution() {
		return _isdistribution;
	}

	public void set_isdistribution(String _isdistribution) {
		this._isdistribution = _isdistribution;
	}

	public String get_distributionid() {
		return _distributionid;
	}

	public void set_distributionid(String _distributionid) {
		this._distributionid = _distributionid;
	}

	public String get_isavailable() {
		return _isavailable;
	}

	public void set_isavailable(String _isavailable) {
		this._isavailable = _isavailable;
	}

	public String get_builderid() {
		return _builderid;
	}

	public void set_builderid(String _builderid) {
		this._builderid = _builderid;
	}

	public String get_buildtime() {
		return _buildtime;
	}

	public void set_buildtime(String _buildtime) {
		this._buildtime = _buildtime;
	}

	public String get_makerid() {
		return _makerid;
	}

	public void set_makerid(String _makerid) {
		this._makerid = _makerid;
	}

	public String get_maketime() {
		return _maketime;
	}

	public void set_maketime(String _maketime) {
		this._maketime = _maketime;
	}

	public String get_goodsitem() {
		return _goodsitem;
	}

	public void set_goodsitem(String _goodsitem) {
		this._goodsitem = _goodsitem;
	}

	public String get_typelist() {
		return _typelist;
	}

	public void set_typelist(String _typelist) {
		this._typelist = _typelist;
	}

	@Override
	public String toString() {
		return "DocCG [_userid=" + _userid + ", _departmentid=" + _departmentid + ", _departmentName=" + _departmentName
				+ ", _warehouseid=" + _warehouseid + ", _warehouseName=" + _warehouseName + ", _customerid="
				+ _customerid + ", _customerName=" + _customerName + ", _discountratio=" + _discountratio
				+ ", _preference=" + _preference + ", _deliverytime=" + _deliverytime + ", _settletime=" + _settletime
				+ ", _printnum=" + _printnum + ", _printtemplate=" + _printtemplate + ", _customeraddress="
				+ _customeraddress + ", _issettleup=" + _issettleup + ", _isposted=" + _isposted + ", _financialcfid="
				+ _financialcfid + ", _summary=" + _summary + ", _remark=" + _remark + ", _isdistribution="
				+ _isdistribution + ", _distributionid=" + _distributionid + ", _isavailable=" + _isavailable
				+ ", _builderid=" + _builderid + ", _buildtime=" + _buildtime + ", _makerid=" + _makerid
				+ ", _maketime=" + _maketime + ", _goodsitem=" + _goodsitem + ", _typelist=" + _typelist + "]";
	}

}
