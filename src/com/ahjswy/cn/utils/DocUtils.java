package com.ahjswy.cn.utils;

import java.util.ArrayList;
import java.util.List;

import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.app.RequestHelper;
import com.ahjswy.cn.bean.bmob.ExceptionLog;
import com.ahjswy.cn.cldb.Sz_stockwarn;
import com.ahjswy.cn.crash.CrashHandler;
import com.ahjswy.cn.dao.CustomerDAO;
import com.ahjswy.cn.dao.CustomerFieldsaleGoodsDAO;
import com.ahjswy.cn.dao.Exception_logDAO;
import com.ahjswy.cn.dao.GoodsPriceDAO;
import com.ahjswy.cn.dao.GoodsUnitDAO;
import com.ahjswy.cn.dao.WarehouseDAO;
import com.ahjswy.cn.model.CustomerRecords;
import com.ahjswy.cn.model.DefDocItem;
import com.ahjswy.cn.model.DefDocItemPD;
import com.ahjswy.cn.model.DefDocItemXS;
import com.ahjswy.cn.model.DefDocPD;
import com.ahjswy.cn.model.DefDocTransfer;
import com.ahjswy.cn.model.DefDocXS;
import com.ahjswy.cn.model.GoodsThin;
import com.ahjswy.cn.model.GoodsUnit;
import com.ahjswy.cn.model.UnitidPrice;
import com.ahjswy.cn.model.Warehouse;
import com.ahjswy.cn.request.ReqStrGetGoodsPrice;
import com.ahjswy.cn.response.RespGoodsPriceEntity;
import com.ahjswy.cn.response.RespGoodsWarehouse;
import com.ahjswy.cn.service.ServiceGoods;

import android.text.TextUtils;

public class DocUtils {
	// private static GoodsUnitDAO unitDAO = new GoodsUnitDAO();
	// private static CustomerDAO customerdao = new CustomerDAO();
	// private static GoodsPriceDAO goodspricedao = new GoodsPriceDAO();
	// private static AccountPreference ap = new AccountPreference();
	// private static Sz_stockwarn stockwarn = new Sz_stockwarn();
	// private static FileUtils fileutils = new FileUtils();
	// private final static String XSDOC_NAME = "XSdoc.txt";
	// private static final String THDOC_NAME = "THdoc.txt";
	public static final int MAXITEM = 100;
	// private final static WarehouseDAO warehousedao = new WarehouseDAO();

	public static ReqStrGetGoodsPrice GetMultiGoodsPrice(String customerid, DefDocItemXS item) {
		if (item == null) {
			return null;
		}
		ArrayList<ReqStrGetGoodsPrice> list = new ArrayList<ReqStrGetGoodsPrice>();
		ReqStrGetGoodsPrice rsp = new ReqStrGetGoodsPrice();
		rsp.setType(1);
		rsp.setCustomerid(customerid);
		rsp.setWarehouseid(item.getWarehouseid());
		rsp.setGoodsid(item.getGoodsid());
		rsp.setUnitid(item.getUnitid());
		rsp.setPrice(0.0D);
		rsp.setIsdiscount(false);
		list.add(rsp);
		String goodsPrice = new ServiceGoods().gds_GetMultiGoodsPrice(list, true, item.isIsusebatch());
		if (RequestHelper.isSuccess(goodsPrice)) {
			List<ReqStrGetGoodsPrice> str2list = JSONUtil.parseArray(goodsPrice, ReqStrGetGoodsPrice.class);
			return str2list.size() == 0 ? null : str2list.get(0);
		}
		return null;
	}

	public static ReqStrGetGoodsPrice GetMultiGoodsPrice(String customerid, String warehouseid, String goodsid,
			String unitid) {
		ArrayList<ReqStrGetGoodsPrice> list = new ArrayList<ReqStrGetGoodsPrice>();
		ReqStrGetGoodsPrice rsp = new ReqStrGetGoodsPrice();
		rsp.setType(1);
		rsp.setCustomerid(customerid);
		rsp.setWarehouseid(warehouseid);
		rsp.setGoodsid(goodsid);
		rsp.setUnitid(unitid);
		rsp.setPrice(0.0D);
		rsp.setIsdiscount(false);
		list.add(rsp);
		String goodsPrice = new ServiceGoods().gds_GetMultiGoodsPrice(list, true, false);
		if (com.ahjswy.cn.utils.TextUtils.isEmptyS(goodsPrice)) {
			List<ReqStrGetGoodsPrice> str2list = JSONUtil.parseArray(goodsPrice, ReqStrGetGoodsPrice.class);
			return str2list.get(0);
		}
		return null;
	}

	/**
	 * 商品 库存单位转换
	 * 
	 * @param stocknumber
	 *            库存数量
	 * @param goodsid
	 *            商品id
	 * @return
	 */
	public static String Stocknum(double stocknumber, GoodsUnit goodsUnit) {
		GoodsUnitDAO unitDAO = new GoodsUnitDAO();
		String stockbigName = null;
		if (goodsUnit == null) {
			return "";
		}
		// 查询商品比例
		double ratio = unitDAO.getGoodsUnitRatio(goodsUnit.getGoodsid(), goodsUnit.getUnitid());
		// 判断是否整除
		double num = stocknumber % ratio;
		if (num == 0) {
			return (int) (stocknumber / ratio) + goodsUnit.getUnitname();
		}
		GoodsUnit baseBaseUnit = unitDAO.queryBaseUnit(goodsUnit.getGoodsid());
		if (baseBaseUnit == null) {
			return "";
		}
		stockbigName = (int) (stocknumber / ratio) == 0 ? "" : (int) (stocknumber / ratio) + goodsUnit.getUnitname();
		if (num % baseBaseUnit.getRatio() == 0) {
			return stockbigName + (int) num + baseBaseUnit.getUnitname();
		}
		return stockbigName + Utils.normalize(num * baseBaseUnit.getRatio(), 2) + baseBaseUnit.getUnitname();
	}

	public static String Stocknum(double stocknumber, String goodsid, String unitid, String unitname) {
		GoodsUnitDAO unitDAO = new GoodsUnitDAO();
		String stockbigName = null;
		if (TextUtils.isEmpty(goodsid) || TextUtils.isEmpty(unitid) || TextUtils.isEmpty(unitname)) {
			return "";
		}
		// 查询商品比例
		double ratio = unitDAO.getGoodsUnitRatio(goodsid, unitid);
		// 判断是否整除
		double num = stocknumber % ratio;
		if (num == 0) {
			return (int) (stocknumber / ratio) + unitname;
		}
		GoodsUnit baseBaseUnit = unitDAO.queryBaseUnit(goodsid);
		if (baseBaseUnit == null) {
			return "";
		}
		stockbigName = (int) (stocknumber / ratio) == 0 ? "" : (int) (stocknumber / ratio) + unitname;
		if (num % baseBaseUnit.getRatio() == 0) {
			return stockbigName + (int) num + baseBaseUnit.getUnitname();
		}
		return stockbigName + Utils.normalize(num * baseBaseUnit.getRatio(), 2) + baseBaseUnit.getUnitname();
	}

	// 库存数量测试可用
	public static String setItemStockUnit(int stockNum, GoodsUnit unit) {
		if (unit == null) {
			return "";
		}
		GoodsUnitDAO unitDAO = new GoodsUnitDAO();
		String stocknum = "";
		GoodsUnit goodsRatio = unitDAO.queryBigUnitRatio(unit.getGoodsid(), unit.getUnitid());
		GoodsUnit queryBaseUnit = unitDAO.queryBaseUnit(unit.getGoodsid());
		if (goodsRatio == null || queryBaseUnit == null) {
			return "";
		}
		int zs = (int) (stockNum / goodsRatio.getRatio());
		int xs = (int) Utils.normalizePrice(stockNum % goodsRatio.getRatio());
		if (zs != 0) {
			stocknum = stocknum + zs + unit.getUnitname();
		}
		if (xs != 0) {
			stocknum = stocknum + xs + queryBaseUnit.getUnitname();
		}
		if (stocknum.length() == 0) {
			stocknum = "0" + unit.getUnitname();
		}
		return stocknum;
	}

	private static CustomerFieldsaleGoodsDAO customerfield = new CustomerFieldsaleGoodsDAO();

	/**
	 * 查询客户商品客史价格
	 * 
	 * @param customerid
	 *            客户id
	 * @param goodsid
	 *            商品id
	 * @param unitid
	 *            单位id
	 * @return
	 */
	public static CustomerRecords getCustomerGoodsHistoryPrice(String customerid, String goodsid) {
		if (TextUtils.isEmpty(customerid) || TextUtils.isEmpty(goodsid)) {
			return null;
		}
		return customerfield.queryUnitidPrice(customerid, goodsid);
	}

	/**
	 * 查询多个商品客史价格
	 * 
	 * @param price
	 * @return
	 */
	public static List<UnitidPrice> getCustomerGoodsHistoryPrice(List<UnitidPrice> price) {
		if (price == null || price.size() == 0) {
			return null;
		}
		return customerfield.queryUnitidPriceList(price);
	}

	/**
	 * 查询商品 客史价格 或者 价格体系预设价格
	 * 
	 * @param customerid
	 * @param docItem
	 * @return
	 */
	public synchronized static double getGoodsPrice(String customerid, DefDocItemXS docItem) {
		if (customerid == null || docItem == null) {
			return 0;
		}
		CustomerDAO customerdao = new CustomerDAO();
		GoodsUnitDAO unitDAO = new GoodsUnitDAO();
		double ratio = unitDAO.getGoodsUnitRatio(docItem.getGoodsid(), docItem.getUnitid());
		//用户是否使用客史
		if (customerdao.isUseCustomerprice(customerid)) {
			CustomerRecords historyPrice = getCustomerGoodsHistoryPrice(customerid, docItem.getGoodsid());
			//根据用户的客史查询转换
			if (historyPrice != null) {
				double historyratio = unitDAO.getGoodsUnitRatio(docItem.getGoodsid(), historyPrice.getUnitid());
				// 除以 自身比例 * 与 要换算的比例
				return Utils.normalize(historyPrice.getPrice() / historyratio * ratio, 2);
			}
		}
		GoodsPriceDAO goodspricedao = new GoodsPriceDAO();
		// 客户价格体系 中的设置
		String pricesystemid = customerdao.queryPricesystemid(customerid);
		if (!TextUtils.isEmpty(pricesystemid)) {
			// 使用客户的 价格体系的计件单价
			double bigPrice = goodspricedao.queryBasicPrice(docItem.getGoodsid(), docItem.getUnitid(), pricesystemid);
			if (bigPrice != 0) {
				return bigPrice;
			}

			double price = goodspricedao.queryBasicPrice(docItem.getGoodsid(), pricesystemid);
			return price * ratio;
		}
		// 使用默认的的 价格体系的计件单价
		double bigPrice = goodspricedao.queryBasicPrice(docItem.getGoodsid(), docItem.getUnitid(),
				Utils.DEFAULT_PRICESYSTEM + "");
		if (bigPrice != 0) {
			return bigPrice;
		}
		// 价格体系默认设置
		double basicPrice = getGoodsBasicPrice(docItem);
		return Utils.normalize(basicPrice * ratio, 2);
	}

	/**
	 * 根据单位查询 查询商品 客史价格 或者 价格体系预设价格
	 * 
	 * @param customerid
	 * @param docItem
	 * @return
	 */
	public static double getGoodsPrice(String customerid, String goodsid, String unitid) {
		CustomerRecords historyPrice = getCustomerGoodsHistoryPrice(customerid, goodsid);
		GoodsUnitDAO unitDAO = new GoodsUnitDAO();
		CustomerDAO customerdao = new CustomerDAO();
		double ratio = unitDAO.getGoodsUnitRatio(goodsid, unitid);
		if (customerdao.isUseCustomerprice(customerid)) {
			// 是否使用客户客史单价
			if (historyPrice != null) {
				double historyratio = unitDAO.getGoodsUnitRatio(goodsid, historyPrice.getUnitid());
				return Utils.normalize(historyPrice.getPrice() / historyratio * ratio, 2);
				// 除以 自身比例 * 与 要换算的比例
			}
		}
		GoodsPriceDAO goodspricedao = new GoodsPriceDAO();

		// 使用客户价格体系
		String pricesystemid = customerdao.queryPricesystemid(customerid);
		if (!TextUtils.isEmpty(pricesystemid)) {// 查询客户 价格体系 比对 基本单位的价格，计件单位 比例*
			// 使用客户的 价格体系的计件单价
			double bigPrice = goodspricedao.queryBasicPrice(goodsid, unitid, pricesystemid);
			if (bigPrice != 0) {
				return bigPrice;
			}
			double price = goodspricedao.queryBasicPrice(goodsid, pricesystemid);
			return price * ratio;
		}
		// 使用默认的的 价格体系的计件单价
		double bigPrice = goodspricedao.queryBasicPrice(goodsid, unitid, Utils.DEFAULT_PRICESYSTEM + "");
		if (bigPrice != 0) {
			return bigPrice;
		}
		// 客户 价格体系
		double basicPrice = getGoodsBasicPrice(goodsid, unitid);
		return Utils.normalize(basicPrice * ratio, 2);
	}
	
	
	/**
	 * 根据商品的单位查询 转换比例  获取价格
	 * 
	 * @param customerid
	 * @param docItem
	 * @return
	 */
	public static double getGoodsCGPrice(String goodsid, String unitid,double price) {
		GoodsUnitDAO unitDAO = new GoodsUnitDAO();
		//获取到对应的商品单位比例
		double ratio = unitDAO.getGoodsUnitRatio(goodsid, unitid);
		return Utils.normalize(price * ratio, 2);
	}

	/**
	 * 查询商品基本 价格
	 * 
	 * @param customerid
	 * @param docItem
	 * @return
	 */
	public static double getGoodsBasicPrice(DefDocItemXS docItem) {
		GoodsPriceDAO goodspricedao = new GoodsPriceDAO();
		double basicPrice = goodspricedao.queryBasicPrice(docItem.getGoodsid(), docItem.getUnitid(),
				Utils.DEFAULT_PRICESYSTEM + "");
		if (basicPrice != 0) {
			return basicPrice;
		}
		// 显示价格体系查询的默认价格
		return goodspricedao.queryBasicPrice(docItem.getGoodsid(), Utils.DEFAULT_PRICESYSTEM + "");

	}

	/**
	 * 查询商品基本 价格
	 * 
	 * @param customerid
	 * @param goodsid
	 * @return
	 */
	public static double getGoodsBasicPrice(String goodsid, String unitid) {
		GoodsPriceDAO goodspricedao = new GoodsPriceDAO();
		double basicPrice = goodspricedao.queryBasicPrice(goodsid, unitid, Utils.DEFAULT_PRICESYSTEM + "");
		if (basicPrice != 0) {
			return basicPrice;
		}
		// 显示价格体系查询的默认价格
		return goodspricedao.queryBasicPrice(goodsid, Utils.DEFAULT_PRICESYSTEM + "");
	}

	// 获取仓库
	public static Warehouse getWarehouse(String warehouseid) {
		WarehouseDAO warehousedao = new WarehouseDAO();
		return warehousedao.getWarehouse(warehouseid);
	}

	public static GoodsUnit queryBigUnit(String goodsid) {
		return new GoodsUnitDAO().queryBigUnit(goodsid);
	}

	/**
	 * 查询商品价格体系
	 * 
	 * @param goodsid
	 * @return
	 */
	public static List<RespGoodsPriceEntity> queryGoodsPriceList(String goodsid) {
		GoodsPriceDAO goodspricedao = new GoodsPriceDAO();

		return goodspricedao.queryPriceList(goodsid);
	}

	/**
	 * 
	 * 获取商品的辅助计算件数
	 * 
	 * @param goodsid
	 *            商品 id
	 * @param num
	 *            商品数量
	 * @return
	 */
	// public static double getAssistnum(String goodsid, String unitid, double
	// num) {
	// double unitRatio = unitDAO.getGoodsUnitRatio(goodsid, unitid);
	// double bigUnitRatio = unitDAO.getBigUnitRatio(goodsid);
	// return Utils.normalize((num * unitRatio) / bigUnitRatio, 4);
	// }

	/**
	 * 蓝牙开关是否打开
	 * 
	 * @return
	 */
	public static boolean isBluetoothPrint() {
		return Boolean.parseBoolean(new AccountPreference().getValue("bluetoothPrintIsShow", "false"));
	}

	/**
	 * 合并相同商品
	 * 
	 * @param item
	 */
	public void combinationItem(List<DefDocItemXS> item, List<Long> listItemDelete) {
		int size = item.size();
		for (int i = 0; i < item.size(); i++) {
			DefDocItemXS items1 = item.get(i);
			for (int j = 0; j < item.size(); j++) {
				if (i == j) {
					continue;
				}
				DefDocItemXS itemxs2 = item.get(j);
				boolean isgoodsid = items1.getGoodsid().equals(itemxs2.getGoodsid());
				boolean isunid = items1.getUnitid().equals(itemxs2.getUnitid());
				boolean isprice = items1.getPrice() == itemxs2.getPrice();
				boolean isratio = items1.getDiscountratio() == itemxs2.getDiscountratio();
				boolean isWarehouseid = items1.getWarehouseid().equals(itemxs2.getWarehouseid());
				if (isgoodsid && isunid && isprice && isratio && isWarehouseid) {
					items1.setNum(items1.getNum() + itemxs2.getNum());
					items1.setBignum(
							new GoodsUnitDAO().getBigNum(items1.getGoodsid(), items1.getUnitid(), items1.getNum()));
					items1.setSubtotal(items1.getNum() * items1.getPrice());
					items1.setDiscountsubtotal(items1.getNum() * items1.getPrice() * items1.getDiscountratio());
					if (itemxs2.getItemid() > 0) {
						listItemDelete.add(itemxs2.getItemid());
					}
					item.remove(j);
					break;
				}
			}
		}
		if (size != item.size()) {
			combinationItem(item, listItemDelete);
		}
	}

	/**
	 * 合并相同商品
	 * 
	 * @param item
	 * @param listItemDelete
	 */
	public void combinationItem(List<DefDocItemPD> item, ArrayList<Long> listItemDelete) {
		int size = item.size();

		for (int i = 0; i < item.size(); i++) {
			DefDocItemPD items1 = item.get(i);
			for (int j = 0; j < item.size(); j++) {
				if (i == j) {
					continue;
				}
				DefDocItemPD itemxs2 = item.get(j);
				boolean isgoodsid = items1.getGoodsid().equals(itemxs2.getGoodsid());
				boolean isunid = items1.getUnitid().equals(itemxs2.getUnitid());
				if (isgoodsid && isunid) {
					items1.setNum(itemxs2.getNum() + items1.getNum());
					items1.setBignum(
							new GoodsUnitDAO().getBigNum(itemxs2.getGoodsid(), itemxs2.getUnitid(), itemxs2.getNum()));
					items1.setNetnum(Utils.normalize(itemxs2.getNum() - itemxs2.getStocknum(), 2));
					if (items1.getItemid() > 0) {
						listItemDelete.add(itemxs2.getItemid());
					}
					item.remove(j);
					break;
				}
			}
		}
		if (size != item.size()) {
			combinationItem(item, listItemDelete);
		}

	}

	public DefDocItemPD fillItem(DefDocPD doc, GoodsThin goodsThin, double stocknum, double costprice, double number) {
		GoodsUnitDAO unitDAO = new GoodsUnitDAO();
		DefDocItemPD defDocItemPD = new DefDocItemPD();
		defDocItemPD.setItemid(0L);
		defDocItemPD.setDocid(doc.getDocid());
		defDocItemPD.setGoodsid(goodsThin.getId());
		defDocItemPD.setGoodsname(goodsThin.getName());
		defDocItemPD.setBarcode(goodsThin.getBarcode());
		defDocItemPD.setSpecification(goodsThin.getSpecification());
		defDocItemPD.setModel(goodsThin.getModel());
		GoodsUnit localGoodsUnit;
		if (Utils.DEFAULT_TransferDocUNIT == 0) {
			localGoodsUnit = unitDAO.queryBaseUnit(goodsThin.getId());
		} else {
			localGoodsUnit = unitDAO.queryBigUnit(goodsThin.getId());
		}
		defDocItemPD.setUnitid(localGoodsUnit.getUnitid());
		defDocItemPD.setUnitname(localGoodsUnit.getUnitname());
		defDocItemPD.setStocknum(Utils.normalize(stocknum, 2));
		defDocItemPD.setBigstocknum(
				unitDAO.getBigNum(defDocItemPD.getGoodsid(), defDocItemPD.getUnitid(), defDocItemPD.getStocknum()));
		defDocItemPD.setNum(number);
		defDocItemPD.setBignum(
				unitDAO.getBigNum(defDocItemPD.getGoodsid(), defDocItemPD.getUnitid(), defDocItemPD.getNum()));
		defDocItemPD.setNetnum(Utils.normalize(defDocItemPD.getNum() - defDocItemPD.getStocknum(), 2));
		defDocItemPD.setBignetnum(
				unitDAO.getBigNum(defDocItemPD.getGoodsid(), defDocItemPD.getUnitid(), defDocItemPD.getNetnum()));
		defDocItemPD.setCostprice(costprice);
		defDocItemPD.setNetamount(Utils.normalizeSubtotal(defDocItemPD.getNetnum() * defDocItemPD.getCostprice()));
		defDocItemPD.setRemark("");
		defDocItemPD.setRversion(0L);
		defDocItemPD.setIsusebatch(goodsThin.isIsusebatch());
		return defDocItemPD;
	}

	public DefDocItem fillItem(DefDocTransfer doc, GoodsThin goodsThin, double num, double price) {
		GoodsUnitDAO unitDAO = new GoodsUnitDAO();
		DefDocItem item = new DefDocItem();
		item.setItemid(0L);
		item.setDocid(doc.getDocid());
		item.setGoodsid(goodsThin.getId());
		item.setGoodsname(goodsThin.getName());
		item.setBarcode(goodsThin.getBarcode());
		item.setSpecification(goodsThin.getSpecification());
		item.setModel(goodsThin.getModel());
		item.setWarehouseid(doc.getOutwarehouseid());
		item.setWarehousename(doc.getOutwarehousename());
		GoodsUnit goodsunit = null;
		if (Utils.DEFAULT_TransferDocUNIT == 0) {
			goodsunit = unitDAO.queryBaseUnit(goodsThin.getId());
		} else {
			goodsunit = unitDAO.queryBigUnit(goodsThin.getId());
		}
		item.setUnitid(goodsunit.getUnitid());
		item.setUnitname(goodsunit.getUnitname());
		item.setNum(Utils.normalize(num, 2));
		item.setBignum(unitDAO.getBigNum(item.getGoodsid(), item.getUnitid(), item.getNum()));
		item.setPrice(Utils.normalizePrice(price));
		item.setSubtotal(Utils.normalizeSubtotal(item.getNum() * item.getPrice()));
		item.setDiscountratio(1.0D);
		item.setDiscountprice(0.0D);
		item.setDiscountsubtotal(0.0D);
		item.setIsgift(false);
		item.setCostprice(0.0D);
		item.setRemark("");
		item.setRversion(0L);
		item.setIsdiscount(false);
		item.setIsusebatch(goodsThin.isIsusebatch());
		return item;
	}

	public DefDocItemXS fillItem(DefDocXS doc, GoodsThin paramGoodsThin, double num, double price, long tempitemid) {
		GoodsUnitDAO unitDAO = new GoodsUnitDAO();
		DefDocItemXS itemxs = new DefDocItemXS();
		itemxs.setItemid(0L);
		itemxs.setTempitemid(tempitemid);
		itemxs.setDocid(doc.getDocid());
		itemxs.setGoodsid(paramGoodsThin.getId());
		itemxs.setGoodsname(paramGoodsThin.getName());
		itemxs.setBarcode(paramGoodsThin.getBarcode());
		itemxs.setSpecification(paramGoodsThin.getSpecification());
		itemxs.setModel(paramGoodsThin.getModel());
		itemxs.setWarehouseid(doc.getWarehouseid());
		itemxs.setWarehousename(doc.getWarehousename());
		GoodsUnit localGoodsUnit = null;
		if (Utils.DEFAULT_OutDocUNIT == 0) {
			localGoodsUnit = unitDAO.queryBaseUnit(paramGoodsThin.getId());
		} else {
			localGoodsUnit = unitDAO.queryBigUnit(paramGoodsThin.getId());
		}
		if (localGoodsUnit == null) {
			return null;
		}
		itemxs.setUnitid(localGoodsUnit.getUnitid());
		itemxs.setUnitname(localGoodsUnit.getUnitname());
		itemxs.unit = localGoodsUnit;
		itemxs.setNum(Utils.normalize(num, 2));
		itemxs.setBignum(unitDAO.getBigNum(itemxs.getGoodsid(), itemxs.getUnitid(), itemxs.getNum()));
		// 价格
		itemxs.setPrice(Utils.normalizePrice(price));
		// 小计
		itemxs.setSubtotal(Utils.normalizeSubtotal(itemxs.getNum() * itemxs.getPrice()));
		// 折扣率
		itemxs.setDiscountratio(doc.getDiscountratio());
		// 折扣价格
		itemxs.setDiscountprice(Utils.normalizePrice(itemxs.getPrice() * doc.getDiscountratio()));
		// 折扣小计
		itemxs.setDiscountsubtotal(itemxs.getNum() * itemxs.getDiscountprice());
		if (itemxs.getPrice() == 0.0D) {
			itemxs.setIsgift(true);
			itemxs.setCostprice(0.0D);
			itemxs.setRemark("");
			itemxs.setRversion(0L);
			itemxs.setIsdiscount(false);
			itemxs.setIsexhibition(false);
			itemxs.setIspromotion(false);
			itemxs.setParentitemid(0L);
			itemxs.setPromotiontype(-1);
			itemxs.setPromotiontypename(null);
			itemxs.setOutorderdocid(0L);
			itemxs.setOutorderdocshowid(null);
			itemxs.setOutorderitemid(0L);
		}
		itemxs.setIsusebatch(paramGoodsThin.isIsusebatch());
		return itemxs;
	}

	public void setPDAddItem(DefDocPD doc, DefDocItemPD itemPD) {
		Sz_stockwarn stockwarn = new Sz_stockwarn();
		GoodsUnitDAO unitDAO = new GoodsUnitDAO();
		itemPD.setCostprice(
				stockwarn.queryGoodsCostprice(doc.getWarehouseid(), itemPD.getGoodsid(), itemPD.getUnitid()));
		itemPD.setStocknum(queryPDSumStock(itemPD.getGoodsid(), itemPD.getUnitid(), doc.getWarehouseid()));
		itemPD.setBigstocknum(unitDAO.getBigNum(itemPD.getGoodsid(), itemPD.getUnitid(), itemPD.getStocknum()));
		itemPD.setNetnum(Utils.normalize(itemPD.getNum() - itemPD.getStocknum(), 2));
		itemPD.setBignetnum(unitDAO.getBigNum(itemPD.getGoodsid(), itemPD.getUnitid(), itemPD.getNetnum()));
		itemPD.setNetamount(Utils.normalizeSubtotal(itemPD.getNetnum() * itemPD.getCostprice()));
	}

	public void setDBAddItem(DefDocTransfer doc, DefDocItem item) {
		GoodsUnitDAO unitDAO = new GoodsUnitDAO();
		Warehouse warehouse = getDBOutWarehouse(doc.getInwarehouseid(), doc.getOutwarehouseid());
		item.setWarehouseid(warehouse.getId());
		item.setWarehousename(warehouse.getName());
		double goodscostprice = queryGoodsCostprice(item.getWarehouseid(), item.getGoodsid(), item.getUnitid());
		item.setPrice(goodscostprice);
		item.setBignum(unitDAO.getBigNum(item.getGoodsid(), item.getUnitid(), item.getNum()));
		item.setSubtotal(Utils.normalizeSubtotal(item.getNum() * item.getPrice()));
	}

	/**
	 * 保存销售数据
	 * 
	 * @param t
	 */
	// public static <T> void saveXSData(T t) {
	// fileutils.saveStorageObject(t, XSDOC_NAME);
	// }

	/**
	 * 获取销售数据
	 * 
	 */
	// public static <T> Object getXSData() {
	// return fileutils.getStorageEntitiesObject(XSDOC_NAME);
	// }

	/**
	 * 保存销售数据
	 * 
	 * @param t
	 */
	// public static <T> void saveTHData(T t) {
	// fileutils.saveStorageObject(t, THDOC_NAME);
	// }

	/**
	 * 获取销售数据
	 * 
	 */
	// public static <T> Object getTHData() {
	// return fileutils.getStorageEntitiesObject(THDOC_NAME);
	// }

	// public static boolean deleteTHDoc() {
	// return fileutils.deleteFile(THDOC_NAME);
	// }

	// public static boolean deleteXSDoc() {
	// return fileutils.deleteFile(XSDOC_NAME);
	// }

	public static int getDefaultNum() {
		if (Boolean.parseBoolean(new AccountPreference().getValue("iscombinationItem", "false"))) {
			return 1;
		}
		return 0;

	}

	/**
	 * 查询商品成本价格
	 * 
	 * @param warehouseid
	 * @param goodsid
	 * @param unitid
	 * @return
	 */
	public double queryGoodsCostprice(String warehouseid, String goodsid, String unitid) {
		Sz_stockwarn stockwarn = new Sz_stockwarn();
		return stockwarn.queryGoodsCostprice(warehouseid, goodsid, unitid);
	}

	/**
	 * 查询调拨仓库
	 * 
	 * @param goodsid
	 * @param warehouseid
	 * @param string
	 * @return
	 */
	public Warehouse getDBOutWarehouse(String inWarehouseid, String outWarehouseid) {
		WarehouseDAO warehousedao = new WarehouseDAO();
		if (!TextUtils.isEmpty(outWarehouseid)) {
			return warehousedao.getWarehouse(outWarehouseid);
		}

		return warehousedao.getDBOutWarehouse(inWarehouseid);
	}

	public double queryPDSumStock(String goodsid, String unitid, String warehouseid) {
		Sz_stockwarn stockwarn = new Sz_stockwarn();
		GoodsUnitDAO unitDAO = new GoodsUnitDAO();
		double ratio = unitDAO.getGoodsUnitRatio(goodsid, unitid);
		return Utils.normalizeDouble(stockwarn.queryStockNum(warehouseid, goodsid) / ratio);
	}

	/**
	 * 查询商品所有仓库 库存
	 * 
	 * @param goodsid
	 * @return
	 */
	public static List<RespGoodsWarehouse> queryStockwarnAll(String goodsid) {
		Sz_stockwarn stockwarn = new Sz_stockwarn();
		return stockwarn.queryStockwarnAll(goodsid);
	}

	/**
	 * 错误日志本地记录
	 * 
	 * @param ex
	 */
	public static void insertLog(Throwable e, Object data) {
		StackTraceElement[] trace = e.getStackTrace();
		StringBuffer buffer = new StringBuffer();
		for (StackTraceElement s : trace) {
			buffer.append(s.toString()).append("\n");
		}
		ExceptionLog log = CrashHandler.getDefaultReqLog();
		log.setMessage(e.getMessage());
		log.setLog(buffer.toString());
		log.setData(data == null ? null : data.toString());
		new Exception_logDAO().insertLog(log);
	}

	public static void insertLog(String message, String data) {
		ExceptionLog log = CrashHandler.getDefaultReqLog();
		log.setMessage(message);
		log.setLog(data);
		new Exception_logDAO().insertLog(log);
	}

	public static void insertLog(Throwable e) {
		insertLog(e, null);
	}

	public static List<Warehouse> getAllWarehouses() {
		WarehouseDAO warehousedao = new WarehouseDAO();
		return warehousedao.getAllWarehouses();
	}

	public static String getBigNum(String goodsid, String unitid, double num) {
		GoodsUnitDAO unitDAO = new GoodsUnitDAO();
		return unitDAO.getBigNum(goodsid, unitid, num);
	}

}
