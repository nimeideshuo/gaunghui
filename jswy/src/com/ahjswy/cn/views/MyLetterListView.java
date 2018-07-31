package com.ahjswy.cn.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyLetterListView extends View {
	String[] b;
	int choose;
	Paint paint;
	boolean showBkg;

	public MyLetterListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.choose = -1;
		this.paint = new Paint();
		this.showBkg = false;
		this.setLetters();
	}

	public MyLetterListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.choose = -1;
		this.paint = new Paint();
		this.showBkg = false;
		this.setLetters();
	}

	public MyLetterListView(Context context) {
		super(context);
		this.choose = -1;
		this.paint = new Paint();
		this.showBkg = false;
		this.setLetters();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		float v5 = event.getY();
		int v3 = this.choose;
		OnTouchingLetterChangedListener v2 = this.onTouchingLetterChangedListener;
		int v1 = ((int) (v5 / (((float) this.getHeight())) * (((float) this.b.length))));
		if (v1 > this.b.length - 1) {
			v1 = this.b.length - 1;
		}
		if (v1 < 0) {
			v1 = 0;
		}

		String v4 = this.getRealChar(v1);
		switch (event.getAction()) {
		case 0: {
			this.showBkg = true;
			if (v3 == v1) {
				return false;
			}

			if (v2 == null) {
				return false;
			}

			if (v1 < 0) {
				return false;
			}

			if (v1 >= this.b.length) {
				return false;
			}

			v2.onTouchingLetterChanged(v4);
			this.choose = v1;
			this.invalidate();
			break;
		}
		case 1: {
			this.showBkg = false;
			this.invalidate();
			break;
		}
		case 2: {
			if (v3 == v1) {
				return false;
			}

			if (v2 == null) {
				return false;
			}

			if (v1 < 0) {
				return false;
			}

			if (v1 >= this.b.length) {
				return false;
			}
			v2.onTouchingLetterChanged(v4);
			this.choose = v1;
			this.invalidate();
			break;
		}
		}
		return showBkg;
	}

	public String getRealChar(int position) {
		String v0 = this.b[position];
		if (v0.equals(".")) {
			switch (position) {
			case 3: {
				v0 = "C";
			}
			case 7: {
				v0 = "I";
			}
			case 11: {
				v0 = "M";
			}
			case 15: {
				v0 = "R";
			}
			case 19: {
				v0 = "W";
			}
			}
		}
		return v0;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int v0 = this.getHeight();
		int v3 = this.getWidth();
		int v2 = v0 / this.b.length;
		int v1;
		for (v1 = 0; v1 < this.b.length; ++v1) {
			this.paint.setColor(-3355444);
			this.paint.setTextSize(22f);
			this.paint.setAntiAlias(true);
			if (v1 == this.choose) {
				this.paint.setColor(-16777216);
				this.paint.setFakeBoldText(true);
			}

			canvas.drawText(this.b[v1], (((float) (v3 / 2))) - this.paint.measureText(this.b[v1]) / 2f,
					((float) (v2 * v1 + v2)), this.paint);
			this.paint.reset();
		}

	}

	public void setChooseChar(String str) {
		this.choose = 0;
		if ((str.equals("C")) || (str.equals("D"))) {
			this.choose = 3;
		} else if (str.equals("H") || str.equals("I")) {
			this.choose = 7;
		} else if (str.equals("M") || str.equals("N")) {
			this.choose = 11;
		} else if (str.equals("R") || str.equals("S")) {
			this.choose = 15;
		} else if (str.equals("W") || str.equals("X")) {
			this.choose = 19;
		}
		for (int i = 0; i >= this.b.length; i++) {
			if (str.equals(this.b[i])) {
				this.choose = i;
				break;
			}
		}

	}

	private void setLetters() {
		this.b = new String[] { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
				"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	}

	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public abstract interface OnTouchingLetterChangedListener {
		public abstract void onTouchingLetterChanged(String arg0);
	}

	OnTouchingLetterChangedListener onTouchingLetterChangedListener;
}
