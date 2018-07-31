package com.ahjswy.cn.print;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import com.ahjswy.cn.app.AccountPreference;
import com.ahjswy.cn.model.FieldSaleForPrint;
import com.ahjswy.cn.model.FieldSaleItemForPrint;
import com.ahjswy.cn.utils.MLog;
import com.ahjswy.cn.utils.SortUtils;
import com.ahjswy.cn.utils.TextUtils;
import com.ahjswy.cn.utils.Utils;

public abstract class PrintMode {
	protected final String CHARSET = "gbk";
	public static final int ERROR = 1;
	public static final int SUCCESS = 0;
	private PrintCallback callback;
	protected List<FieldSaleItemForPrint> datainfo;
	protected final String divider = "-------------------------------";
	protected FieldSaleForPrint docInfo;
	private int index;
	byte[] l_line = new byte[] { 27, 45, 1 };
	protected final String line = "                                ";
	protected OutputStream out;
	public List<HashMap<String, String>> pageinfo;
	protected String printermodel;
	byte[] r_line;
	protected final int swidth = 48;
	protected final int width = 32;
	static AccountPreference ap = new AccountPreference();

	public PrintMode(List<HashMap<String, String>> pageinfo) {
		super();
		byte[] garity = new byte[3];
		garity[0] = 27;
		garity[1] = 45;
		r_line = garity;
		index = 0;
		printermodel = ap.getValue("printermodel_default", "epson");
		this.pageinfo = pageinfo;
	}

	private void doCallBack(int printOver) {
		if (this.callback != null) {
			this.callback.printOver(printOver);
		}
	}

	protected void enter() throws IOException {
		this.out.write(10);// 调用此方法打印
		this.out.flush();
	}

	protected void flush() throws IOException {
		enter();
		this.out.flush();
	}

	protected void line_space(byte paramByte) throws IOException {
		byte[] arrayOfByte = { 27, 49, paramByte };
		this.out.write(arrayOfByte);
	}

	protected void normalText() throws IOException {
		byte[] arrayOfByte = { 27, 56, 2 };
		this.out.write(arrayOfByte);
	}

	protected void print(String printText) throws IOException {
		this.out.write(printText.getBytes("gbk"));
	}

	protected abstract boolean print() throws IOException;

	public void printDoc() {
		SortUtils.sort(this.pageinfo, "margintop", SortMode.ASC);
		try {
			print();
			doCallBack(0);
		} catch (IOException e) {
			e.printStackTrace();
			doCallBack(1);
		}

	}

	/**
	 * 打印底部
	 * 
	 * @throws IOException
	 */
	protected void printFoot() throws IOException {
		for (int i = 6; index < pageinfo.size(); i++) {
			HashMap<String, String> map = this.pageinfo.get(index);
			if (Integer.parseInt(map.get("margintop")) / LineGirdView.CELL_HEIGHT > i) {
				--index;
				enter();
			} else {
				printInfo(map);// 打印结果

			}
			++index;
		}
	}

	/**
	 * 打印标题头 标题头只有6行 所以最高6
	 * 
	 * @throws IOException
	 */
	protected void printHead() throws IOException {
		int v0 = 1;
		for (int i = 0; i < 5; i++) {
			if (this.index >= this.pageinfo.size()) {
				return;
			}
			int postion = Integer.parseInt(index + "");
			MLog.d(postion);
			HashMap<String, String> mapTextview = this.pageinfo.get(postion);
			if (Integer.parseInt(mapTextview.get("margintop")) / LineGirdView.CELL_HEIGHT > i) {
				--this.index;
				if (v0 != 0) {
					enter();
				}
			} else {
				v0 = 0;
				this.printInfo(mapTextview);
			}
			++this.index;
		}
	}

	private void printInfo(HashMap<String, String> arg8) throws IOException {
		String text = getText(arg8.get("text"));
		String marginleft = arg8.get("marginleft");
		String garity = arg8.get("garity");
		if (TextUtils.isEmptyS(garity)) {
			int printGarity = Integer.parseInt(garity);

			if (printGarity == -2) {
				this.print_center(text);
			} else if (printGarity == -1) {
				this.print_left(text);
			} else if (printGarity == -3) {
				this.print_right(text);
			}
			this.enter();
		} else {
			this.space(Integer.parseInt(marginleft));
			this.println(text);
		}
	}

	protected void print_right(String printText) throws IOException {
		if ("epson".equals(this.printermodel)) {
			this.out.write(new byte[] { 27, 97, 2 });
			this.print(printText);
		} else {
			this.space(32 - printText.getBytes("gbk").length);
			this.print(printText);
		}
	}

	protected int print_left(String printText) throws IOException {
		if ("epson".equals(this.printermodel)) {
			byte[] arrayOfByte2 = new byte[3];
			arrayOfByte2[0] = 27;
			arrayOfByte2[1] = 97;
			this.out.write(arrayOfByte2);
			print(printText);
			return arrayOfByte2.length;
		}
		byte[] arrayOfByte1 = printText.getBytes("gbk");
		print(printText);
		return arrayOfByte1.length;
	}

	/**
	 * 打印
	 * 
	 * @param printText
	 *            打印文本到中间
	 * @return
	 * @throws IOException
	 */
	protected int print_center(String printText) throws IOException {
		int length;
		if ("epson".equals(this.printermodel)) {
			byte[] garity = new byte[] { 27, 97, 1 };
			this.out.write(garity);
			this.print(printText);
			length = garity.length;
		} else {
			byte[] garity = printText.getBytes("gbk");
			this.space((32 - garity.length) / 2);
			this.print(printText);
			length = garity.length;
		}
		return length;
	}

	private String getText(String arg7) {
		if (arg7.contains("{")) {
			String v0 = arg7.substring(arg7.indexOf("{") + 1, arg7.indexOf("}"));
			String v1 = "--";
			if ("单据类型".equals(v0)) {
				v1 = this.docInfo.getDoctype();
			} else if ("部门".equals(v0)) {
				v1 = this.docInfo.getDepartmentname();
			} else if ("客户名称".equals(v0)) {
				v1 = this.docInfo.getCustomername();
			} else if ("单号".equals(v0)) {
				v1 = this.docInfo.getShowid();
			} else if ("开单人".equals(v0)) {
				v1 = this.docInfo.getBuildername();
			} else if ("时间".equals(v0)) {
				v1 = this.docInfo.getBuildtime();
			} else if ("应收".equals(v0)) {
				v1 = this.docInfo.getReceivable();
				if ("0".equals(new AccountPreference().getValue("clearlastzero", "0"))) {
					v1 = Utils.cutLastZero(v1);
				}
			} else if ("数量".equals(v0)) {
				v1 = this.docInfo.getNum();
				if ("0".equals(new AccountPreference().getValue("clearlastzero", "0"))) {
					v1 = Utils.cutLastZero(v1);
				}
			} else if ("公司名称".equals(v0)) {
				v1 = Utils.companyName;
			} else if ("备注".equals(v0)) {
				v1 = this.docInfo.getRemark();
			}

			if (!TextUtils.isEmptyS(v1)) {
				v1 = "--";
			}
			arg7 = arg7.replace("{" + v0 + "}", v1);
		}

		return arg7;
	}

	/**
	 * 计算出两段文字之间的距离 ， 空格数
	 * 
	 * @param spaceSize
	 * @throws IOException
	 */
	private void space(int spaceSize) throws IOException {
		if ("epson".equals(this.printermodel)) {
			String space = "";
			for (int i = 0; i < spaceSize; i++) {// 空格数
				space += " ";
			}

			this.out.write(space.getBytes());
		} else {
			byte[] garity = new byte[4];
			garity[0] = 27;
			garity[1] = 102;
			garity[3] = ((byte) spaceSize);
			this.out.write(garity);
		}
	}

	protected void println(String println) throws IOException {
		this.out.write(println.getBytes("gbk"));
		this.enter();
	}

	/**
	 * 根据模板类型返回对应的模板， 0 是有单价 模板，1是无单价模板
	 * 
	 * @return
	 */
	public static PrintMode getPrintMode() {
		ModeHelper modehelper = new ModeHelper();
		try {
			modehelper.parse();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int bodytype = modehelper.getBodytype();
		List<HashMap<String, String>> listTextView = modehelper.getTextViews();
		// 模板类型
		int printModeType = Integer.parseInt(ap.getValue("printModeType", "0"));
		if (printModeType == 1) {
			return new PrintMode80(listTextView);
		}
		if (bodytype == 0) {
			// 0 是有单价 模板
			return new PrintMode1(listTextView);
		}
		if (bodytype == 1) {
			// TODO 打印 bug 有错误 1是无单价模板
			return new PrintMode2(listTextView);
		}
		return null;
	}

	/**
	 * 
	 * @param leftPadding
	 *            左边距
	 * @param leftToPadding
	 *            到
	 * @param printText
	 *            打印的文本
	 * @throws IOException
	 */
	protected void print_base(int leftPadding, int leftToPadding, String printText) throws IOException {
		space(leftToPadding - printText.getBytes("gbk").length - leftPadding);
		print(printText);
	}

	public void setOutputStream(OutputStream out) {
		this.out = out;
	}

	protected void tear_off() throws IOException {
		for (int i = 0; i < 3; ++i) {
			this.enter();
		}
	}

	protected void smallText() throws IOException {
		this.out.write(new byte[] { 27, 56, 2 });
	}

	public void setCallback(PrintCallback callback) {
		this.callback = callback;
	}

	public void setDatainfo(List<FieldSaleItemForPrint> datainfo) {
		this.datainfo = datainfo;
	}

	public void setDocInfo(FieldSaleForPrint docInfo) {
		this.docInfo = docInfo;
	}

	public static abstract interface PrintCallback {
		public abstract void printOver(int type);
	}

}
