package com.ahjswy.cn.print;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.model.FieldSaleItemForPrint;
import com.ahjswy.cn.utils.Utils;

/**
 * 无单价的打印模板 有错误 等待修改
 * 
 * @author Administrator
 *
 */
public class PrintMode2 extends PrintMode {

	public PrintMode2(List<HashMap<String, String>> pageinfo) {
		super(pageinfo);
	}

	@Override
	protected boolean print() throws IOException {
		printHead();
		println("-------------------------------");
		print_base(this.print_left("商品名称"), 22, "数量");
		print_base(22, 32, "小计");
		smallText();
		for (int i = 0; i < this.datainfo.size(); i++) {
			this.printItem(this.datainfo.get(i));
		}
		this.normalText();
		this.println("-------------------------------");
		this.printFoot();
		this.tear_off();
		return true;
	}

	private void printItem(FieldSaleItemForPrint printItem) {// 打印每一个商品位置

		String itemtype = "";
		// 商品类型计算
		if (!printItem.getItemtype().equals("销") && !"销售退货单".equals(this.docInfo.getDoctype())) {
			itemtype = "[" + printItem.getItemtype() + "]";
		}

		String goodsName = itemtype + printItem.getGoodsname();// 商品类型+商品名称组合
		String num = Utils.getNumber(printItem.getNum());
		String discountsubtotal = Utils.getSubtotalMoney(printItem.getDiscountsubtotal());
		if ("0".equals(new AccountPreference().getValue("clearlastzero", "0"))) {
			num = Utils.cutLastZero(num);
			discountsubtotal = Utils.cutLastZero(discountsubtotal);
		}
		if (("0".equals(new AccountPreference().getValue("minustuihuo", "0")))
				&& ((printItem.getItemtype().equals("退")) || (printItem.getItemtype().equals("入")))
				&& !"销售退货单".equals(this.docInfo.getDoctype())) {
			num = "-" + Utils.cutLastZero(num);
			discountsubtotal = "-" + Utils.cutLastZero(discountsubtotal);
		}
		num = num + printItem.getUnitname();
		int leftPadding = 0;
		int v10 = goodsName.length() / 8;
		int v9 = goodsName.length() % 8 == 0 ? 0 : 1;
		int width = v10 + v9;// 计算需要几次打完
		for (int i = 0; i < width; i++) {
			int goodNameLenght = (i + 1) * 8;// 可以使用的空间宽度
			if (goodNameLenght > goodsName.length()) {// 判断是否能够打印字符长度
				goodNameLenght = goodsName.length();// 足够就把字符长度赋值，
			}

			try {
				leftPadding = this.print_left(goodsName.substring(i * 8, goodNameLenght));// 截取当前段的字符，字符长度
				if (i != width - 1) {// 判断是否打印到最后一行没有打印一次换行
					this.enter();
				}
				this.print_base(leftPadding, 22, num);
				this.print_base(22, 32, discountsubtotal);
				this.enter();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
