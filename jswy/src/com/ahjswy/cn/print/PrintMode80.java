package com.ahjswy.cn.print;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.model.FieldSaleItemForPrint;
import com.ahjswy.cn.utils.Utils;

/**
 * 80小票打印机
 * 
 * @author Administrator
 *
 */
public class PrintMode80 extends PrintMode {

	public PrintMode80(List<HashMap<String, String>> pageinfo) {
		super(pageinfo);
	}

	protected boolean print() throws IOException {
		printHead();
		println("----------------------------------------------");
		// 同一行 文字 设置 指定 位置 中间 空格 代替
		print_base(this.print_left(" "), 10, "数量");
		print_base(10, 28, "单价");
		print_base(28, 46, "小计");
		enter();
		for (int i = 0; i < this.datainfo.size(); i++) {
			this.printItem(this.datainfo.get(i));
		}
		println("----------------------------------------------");
		this.printFoot();
		this.tear_off();
		datainfo.clear();
		return true;
	}

	// TODO
	private void printItem(FieldSaleItemForPrint itemforprint) {
		int leftToPadding = 30;
		int leftPadding = 12;
		String itemtype = "[" + itemforprint.getItemtype() + "]";
		// }
		String goodsname = String.valueOf(itemtype) + itemforprint.getGoodsname();
		// 计件单位
		String bigNumber = itemforprint.getNum() % 1 == 0 ? (int) itemforprint.getNum() + ""
				: itemforprint.getNum() + "";
		// 单价
		String price = Utils.getSubtotalMoney(itemforprint.getPrice());
		// 折扣小计
		String discountsubtotal = Utils.getSubtotalMoney(itemforprint.getDiscountsubtotal());
		if ("0".equals(new AccountPreference().getValue("clearlastzero", "0"))) {
			bigNumber = Utils.cutLastZero(bigNumber);
			price = Utils.cutLastZero(price);
			discountsubtotal = Utils.cutLastZero(discountsubtotal);
		}
		if (("0".equals(new AccountPreference().getValue("minustuihuo", "0")))
				&& ((itemforprint.getItemtype().equals("退")) || (itemforprint.getItemtype().equals("入")))
				&& !"销售退货单".equals(this.docInfo.getDoctype())) {
			bigNumber = "-" + Utils.cutLastZero(bigNumber);
			discountsubtotal = "-" + Utils.cutLastZero(discountsubtotal);
		}
		bigNumber = String.valueOf(bigNumber) + itemforprint.getUnitname();
		try {
			this.print_left(goodsname);
			this.enter();
			this.print_base(this.print_left(""), leftPadding, bigNumber);
			this.print_base(leftPadding, leftToPadding, price);
			this.print_base(leftToPadding, 48, discountsubtotal);
			this.enter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
