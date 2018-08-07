package com.ahjswy.cn.bean;

/**
 * 采购入库商品item
 * 
 * @author Administrator
 *
 */
public class DocItemCG {
	// 条码
	private String _barcode;
	// 商品id
	private String _goodsid;
	// 交货时间
	private String _productiondate;
	// 规格
	private String _specification;
	// 手机
	private String _model;
	// 批次
	private String _batch;
	// 商品名称
	private String _goodsname;
	// 单位id
	private String _unitid;
	// 单位名称
	private String _unitname;
	// 仓库id
	private String _warehouseid;
	// 仓库名称
	private String _warehousename;
	// 数量
	private String _num;
	// 整单折扣
	private String _discountratio;
	// 备注
	private String _remark;
	// 是否打印
	private String _isgift;
	// 单位数量
	private String _bignum;
	// 成本费
	private String _price;
	private String _subtotal;
	private String _discountprice;
	private String _discountsubtotal;
	private String _costprice;
	private Long _docid;
	private Long _itemid;
	private Long _rversion;
	private Long _isdiscount;
	private Long inorderdocid;
	private String _inorderdocshowid;
	private Long _inorderitemid;
	private boolean _isusebatch;

	public String get_barcode() {
		return _barcode;
	}

	public void set_barcode(String _barcode) {
		this._barcode = _barcode;
	}

	public String get_goodsid() {
		return _goodsid;
	}

	public void set_goodsid(String _goodsid) {
		this._goodsid = _goodsid;
	}

	public String get_productiondate() {
		return _productiondate;
	}

	public void set_productiondate(String _productiondate) {
		this._productiondate = _productiondate;
	}

	public String get_specification() {
		return _specification;
	}

	public void set_specification(String _specification) {
		this._specification = _specification;
	}

	public String get_model() {
		return _model;
	}

	public void set_model(String _model) {
		this._model = _model;
	}

	public String get_batch() {
		return _batch;
	}

	public void set_batch(String _batch) {
		this._batch = _batch;
	}

	public String get_goodsname() {
		return _goodsname;
	}

	public void set_goodsname(String _goodsname) {
		this._goodsname = _goodsname;
	}

	public String get_unitid() {
		return _unitid;
	}

	public void set_unitid(String _unitid) {
		this._unitid = _unitid;
	}

	public String get_unitname() {
		return _unitname;
	}

	public void set_unitname(String _unitname) {
		this._unitname = _unitname;
	}

	public String get_warehouseid() {
		return _warehouseid;
	}

	public void set_warehouseid(String _warehouseid) {
		this._warehouseid = _warehouseid;
	}

	public String get_warehousename() {
		return _warehousename;
	}

	public void set_warehousename(String _warehousename) {
		this._warehousename = _warehousename;
	}

	public String get_num() {
		return _num;
	}

	public void set_num(String _num) {
		this._num = _num;
	}

	public String get_discountratio() {
		return _discountratio;
	}

	public void set_discountratio(String _discountratio) {
		this._discountratio = _discountratio;
	}

	public String get_remark() {
		return _remark;
	}

	public void set_remark(String _remark) {
		this._remark = _remark;
	}

	public String get_isgift() {
		return _isgift;
	}

	public void set_isgift(String _isgift) {
		this._isgift = _isgift;
	}

	public String get_bignum() {
		return _bignum;
	}

	public void set_bignum(String _bignum) {
		this._bignum = _bignum;
	}

	public String get_price() {
		return _price;
	}

	public void set_price(String _price) {
		this._price = _price;
	}

	public String get_subtotal() {
		return _subtotal;
	}

	public void set_subtotal(String _subtotal) {
		this._subtotal = _subtotal;
	}

	public String get_discountprice() {
		return _discountprice;
	}

	public void set_discountprice(String _discountprice) {
		this._discountprice = _discountprice;
	}

	public String get_discountsubtotal() {
		return _discountsubtotal;
	}

	public void set_discountsubtotal(String _discountsubtotal) {
		this._discountsubtotal = _discountsubtotal;
	}

	public String get_costprice() {
		return _costprice;
	}

	public void set_costprice(String _costprice) {
		this._costprice = _costprice;
	}

	public Long get_docid() {
		return _docid;
	}

	public void set_docid(Long _docid) {
		this._docid = _docid;
	}

	public Long get_itemid() {
		return _itemid;
	}

	public void set_itemid(Long _itemid) {
		this._itemid = _itemid;
	}

	public Long get_rversion() {
		return _rversion;
	}

	public void set_rversion(Long _rversion) {
		this._rversion = _rversion;
	}

	public Long get_isdiscount() {
		return _isdiscount;
	}

	public void set_isdiscount(Long _isdiscount) {
		this._isdiscount = _isdiscount;
	}

	public Long getInorderdocid() {
		return inorderdocid;
	}

	public void setInorderdocid(Long inorderdocid) {
		this.inorderdocid = inorderdocid;
	}

	public String get_inorderdocshowid() {
		return _inorderdocshowid;
	}

	public void set_inorderdocshowid(String _inorderdocshowid) {
		this._inorderdocshowid = _inorderdocshowid;
	}

	public Long get_inorderitemid() {
		return _inorderitemid;
	}

	public void set_inorderitemid(Long _inorderitemid) {
		this._inorderitemid = _inorderitemid;
	}

	public boolean is_isusebatch() {
		return _isusebatch;
	}

	public void set_isusebatch(boolean _isusebatch) {
		this._isusebatch = _isusebatch;
	}

	@Override
	public String toString() {
		return "DocItemCG [_barcode=" + _barcode + ", _goodsid=" + _goodsid + ", _productiondate=" + _productiondate
				+ ", _specification=" + _specification + ", _model=" + _model + ", _batch=" + _batch + ", _goodsname="
				+ _goodsname + ", _unitid=" + _unitid + ", _unitname=" + _unitname + ", _warehouseid=" + _warehouseid
				+ ", _warehousename=" + _warehousename + ", _num=" + _num + ", _discountratio=" + _discountratio
				+ ", _remark=" + _remark + ", _isgift=" + _isgift + ", _bignum=" + _bignum + ", _price=" + _price
				+ ", _subtotal=" + _subtotal + ", _discountprice=" + _discountprice + ", _discountsubtotal="
				+ _discountsubtotal + ", _costprice=" + _costprice + ", _docid=" + _docid + ", _itemid=" + _itemid
				+ ", _rversion=" + _rversion + ", _isdiscount=" + _isdiscount + ", inorderdocid=" + inorderdocid
				+ ", _inorderdocshowid=" + _inorderdocshowid + ", _inorderitemid=" + _inorderitemid + ", _isusebatch="
				+ _isusebatch + ", get_barcode()=" + get_barcode() + ", get_goodsid()=" + get_goodsid()
				+ ", get_productiondate()=" + get_productiondate() + ", get_specification()=" + get_specification()
				+ ", get_model()=" + get_model() + ", get_batch()=" + get_batch() + ", get_goodsname()="
				+ get_goodsname() + ", get_unitid()=" + get_unitid() + ", get_unitname()=" + get_unitname()
				+ ", get_warehouseid()=" + get_warehouseid() + ", get_warehousename()=" + get_warehousename()
				+ ", get_num()=" + get_num() + ", get_discountratio()=" + get_discountratio() + ", get_remark()="
				+ get_remark() + ", get_isgift()=" + get_isgift() + ", get_bignum()=" + get_bignum() + ", get_price()="
				+ get_price() + ", get_subtotal()=" + get_subtotal() + ", get_discountprice()=" + get_discountprice()
				+ ", get_discountsubtotal()=" + get_discountsubtotal() + ", get_costprice()=" + get_costprice()
				+ ", get_docid()=" + get_docid() + ", get_itemid()=" + get_itemid() + ", get_rversion()="
				+ get_rversion() + ", get_isdiscount()=" + get_isdiscount() + ", getInorderdocid()=" + getInorderdocid()
				+ ", get_inorderdocshowid()=" + get_inorderdocshowid() + ", get_inorderitemid()=" + get_inorderitemid()
				+ ", is_isusebatch()=" + is_isusebatch() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
