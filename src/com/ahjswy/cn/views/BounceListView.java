package com.ahjswy.cn.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class BounceListView extends ListView implements Runnable{
	private int mDistance = 0;
	private float mLastDownY = 0.0F;
	private boolean mPositive = false;
	private int mStep = 10;

	public BounceListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public BounceListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BounceListView(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		boolean v0 = false;
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (this.mLastDownY != 0f) {
				return super.onTouchEvent(ev);
			}
			if (this.mDistance != 0) {
				return super.onTouchEvent(ev);
			}
			this.mLastDownY = ev.getY();
			break;
		case MotionEvent.ACTION_UP:
			if (this.mDistance == 0) {
				this.mLastDownY = 0f;
				this.mDistance = 0;
			}
			this.mStep = 1;
			if (this.mDistance >= 0) {
				v0 = true;
			}
			this.mPositive = v0;
			this.post(((Runnable) this));
			break;
		case MotionEvent.ACTION_MOVE:
			if (this.mLastDownY != 0f && this.getChildAt(0) != null) {
				this.mDistance = ((int) (this.mLastDownY - ev.getY()));
				if (this.mDistance >= 0 || this.getFirstVisiblePosition() != 0 || this.getChildAt(0).getTop() != 0) {
					if (this.mDistance > 0) {
						if (this.getLastVisiblePosition() == this.getCount() - 1) {
							this.mDistance /= 2;
							this.scrollTo(0, this.mDistance);
						}
						this.mDistance = 0;
					} else {
						this.mDistance = 0;
					}
				}
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	public void run() {
		int v0;
		int v1 = this.mDistance;
		if (this.mDistance > 0) {
			v0 = -this.mStep;
		} else {
			v0 = this.mStep;
		}
		this.mDistance = v0 + v1;
        this.scrollTo(0, this.mDistance);
        if(!this.mPositive || this.mDistance > 0) {
            if(!this.mPositive && this.mDistance >= 0) {
                this.scrollTo(0, 0);
                this.mDistance = 0;
                this.mLastDownY = 0f;
                return;
            }
            ++this.mStep;
            this.postDelayed(((Runnable)this), 10);
        } else {
        	 this.scrollTo(0, 0);
             this.mDistance = 0;
             this.mLastDownY = 0f;
        }
		
		
	}
}
