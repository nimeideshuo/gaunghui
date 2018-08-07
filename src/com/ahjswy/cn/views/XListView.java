package com.ahjswy.cn.views;

import com.ahjswy.cn.R;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class XListView extends SwipeMenuListView implements AbsListView.OnScrollListener {
	private static final float OFFSET_RADIO = 1.8F;
	private static final int PULL_LOAD_MORE_DELTA = 50;
	private static final int SCROLLBACK_FOOTER = 1;
	private static final int SCROLLBACK_HEADER = 0;
	private static final int SCROLL_DURATION = 400;
	private int firstvisibleposition = 0;
	private boolean mEnablePullLoad = true;
	private boolean mEnablePullRefresh = true;
	private XListViewFooter mFooterView;
	private XListViewHeader mHeaderView;
	private RelativeLayout mHeaderViewContent;
	private int mHeaderViewHeight;
	private boolean mIsFooterReady = false;
	private float mLastY = -1.0F;
	private IXListViewListener mListViewListener;
	private boolean mPullLoading = false;
	private boolean mPullRefreshing = false;
	private int mScrollBack;
	private AbsListView.OnScrollListener mScrollListener;
	private Scroller mScroller;
	private int mTotalItemCount = -1;

	public XListView(Context context) {
		super(context);
		initWithContext(context);
	}

	public XListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
	}

	public XListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWithContext(context);
	}

	private void initWithContext(Context context) {
		System.out.println("XListView>>initWithContext");
		mScroller = new Scroller(context, new DecelerateInterpolator());
		super.setOnScrollListener(this);
		mHeaderView = new XListViewHeader(context);
		mHeaderViewContent = ((RelativeLayout) mHeaderView.findViewById(R.id.xlistview_footer_content));
		mHeaderView.setVisibility(View.GONE);
		mFooterView = new XListViewFooter(context);
		mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				mHeaderViewHeight = mHeaderViewContent.getHeight();
				getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
	}

	private void invokeOnScrolling() {
		if ((this.mScrollListener instanceof OnXScrollListener)) {
			((OnXScrollListener) this.mScrollListener).onXScrolling(this);
		}
	}

	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
			invalidate();
		}
	}

	private void resetHeaderHeight() {
		int height = mHeaderView.getVisiableHeight();
		if (height == 0)
			return;
		if (mPullRefreshing && height <= mHeaderViewHeight) {
			return;
		}
		int finalHeight = 0;
		if (mPullRefreshing && height > mHeaderViewHeight) {
			finalHeight = mHeaderViewHeight;
		}
		mScrollBack = SCROLLBACK_HEADER;
		mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
		invalidate();
	}

	private void startLoadMore() {
		this.mPullLoading = true;
		this.mFooterView.setState(2);
		if (this.mListViewListener != null) {
			this.mListViewListener.onLoadMore();
		}
	}

	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) {
				mFooterView.setState(XListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(XListViewFooter.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);
	}

	private void updateHeaderHeight(float delta) {
		mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
		if (mEnablePullRefresh && !mPullRefreshing) {
			if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
				mHeaderView.setState(XListViewHeader.STATE_READY);
			} else {
				mHeaderView.setState(XListViewHeader.STATE_NORMAL);
			}
		}
		setSelection(0);
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (mScrollBack == SCROLLBACK_HEADER) {
				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			} else {
				mFooterView.setBottomMargin(mScroller.getCurrY());
			}
			postInvalidate();
			invokeOnScrolling();
		}
	}

	public XListViewFooter getFooterView() {
		return this.mFooterView;
	}

	public int getPosition() {
		return this.firstvisibleposition;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		mTotalItemCount = totalItemCount;
		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	public boolean onTouchEvent(MotionEvent ev) {
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltaY = ev.getRawY() - mLastY;
			mLastY = ev.getRawY();
			if (getFirstVisiblePosition() == 0 && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
				updateHeaderHeight(deltaY / OFFSET_RADIO);
				invokeOnScrolling();
			} else if (getLastVisiblePosition() == mTotalItemCount - 1
					&& (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
				updateFooterHeight(-deltaY / OFFSET_RADIO);
			}

			// this.mLastY = -1.0F;
			// if (getLastVisiblePosition() == -1 + this.mTotalItemCount) {
			// if (this.mFooterView.getBottomMargin() > 50) {
			// this.mPullLoading = true;
			// this.mFooterView.setState(2);
			// startLoadMore();
			// }
			// resetFooterHeight();
			// }

			break;
		case MotionEvent.ACTION_UP:
			mLastY = -1;
			if (getFirstVisiblePosition() == 0) {
				if (mEnablePullRefresh && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
					mPullRefreshing = true;
					mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
					if (mListViewListener != null) {
						mListViewListener.onRefresh();
					}
				}
				resetHeaderHeight();
			} else if (getLastVisiblePosition() == mTotalItemCount - 1) {
				if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
					startLoadMore();
				}
				resetFooterHeight();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	public void setAdapter(ListAdapter paramListAdapter) {
		if (!this.mIsFooterReady) {
			this.mIsFooterReady = true;
			addFooterView(mFooterView);
		}
		super.setAdapter(paramListAdapter);
	}

	public void setOnScrollListener(AbsListView.OnScrollListener paramOnScrollListener) {
		this.mScrollListener = paramOnScrollListener;
	}

	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.show();
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
			mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}

	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
		if (!mEnablePullRefresh) {
			mHeaderViewContent.setVisibility(View.INVISIBLE);
		} else {
			mHeaderViewContent.setVisibility(View.VISIBLE);
		}
	}

	public void setFootViewVisible(boolean visible) {
		if (visible) {
			this.mFooterView.setVisibility(View.VISIBLE);
		} else {
			this.mFooterView.setVisibility(View.GONE);
		}

	}

	public void setXListViewListener(IXListViewListener paramIXListViewListener) {
		this.mListViewListener = paramIXListViewListener;
	}

	public void stopLoadMore() {
		if (this.mPullLoading) {
			this.mPullLoading = false;
			this.mFooterView.setState(0);
		}
	}

	public void stopRefresh() {
		if (this.mPullRefreshing) {
			this.mPullRefreshing = false;
			resetHeaderHeight();
		}
	}

	public static abstract interface IXListViewListener {
		public abstract void onLoadMore();

		public abstract void onRefresh();
	}

	public static abstract interface OnXScrollListener extends AbsListView.OnScrollListener {
		public abstract void onXScrolling(View paramView);
	}
}
