package com.ahjswy.cn.print;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LineGirdView extends View {
	public static int CELL_HEIGHT = 50;
	public static int CELL_WIDTH = 0;
	public static int COLOUMNS = 32;
	public static int ROWS = 13;
	public static int WIDTH = 0;
	private Paint paint;


	public LineGirdView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public LineGirdView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LineGirdView(Context context) {
		super(context);
	}

}
