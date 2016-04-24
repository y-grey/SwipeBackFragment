package com.yph.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;


public class SwipeBackLayout extends RelativeLayout {

	private Context mContext;
	private Scroller mScroller;
	private int mTouchSlop;

	public static final int SWIPE_LEFT = 1;
	public static final int SWIPE_RIGHT = 2;
	public static final int SWIPE_TOP = 3;
	public static final int SWIPE_BOTTOM = 4;

	protected int model = SWIPE_RIGHT;

	private int mScreenWidth = 0;
	private int mScreenHeight = 0;

	private int mLastDownX = 0;
	private int mLastDownY = 0;

	private int mCurryX;
	private int mCurryY;

	private int mDelX;
	private int mDelY;

	private boolean isClose = false;

	private ImageView mImgView;

	public SwipeBackLayout(Context context) {
		super(context);
		mContext = context;
		setupView();
	}

	public SwipeBackLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setupView();
	}

	@SuppressLint("NewApi")
	private void setupView() {

		mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();

		mScroller = new Scroller(mContext);

		WindowManager wm = (WindowManager) (mContext.getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		mScreenHeight = dm.heightPixels;
		mScreenWidth = dm.widthPixels;


		mImgView = new ImageView(mContext);
		mImgView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		mImgView.setScaleType(ImageView.ScaleType.FIT_XY);
		mImgView.setBackgroundColor(getResources().getColor(android.R.color.white));
		addView(mImgView);
        setClickable(true);
		this.setBackgroundColor(Color.argb(0, 0, 0, 0));
	}
	private void startScroll(int startX,int startY,int dx,int dy, int duration) {
		mScroller.startScroll(startX, startY, dx, dy,duration);
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {

		case MotionEvent.ACTION_MOVE:
			mCurryX = (int) event.getX();
			mCurryY = (int) event.getY();
			mDelX = mCurryX - mLastDownX;
			mDelY = mCurryY - mLastDownY;

			if (model == SWIPE_RIGHT && mDelX > 0) {// 右滑
				scrollTo(-mDelX,0);
			}else if(model == SWIPE_BOTTOM && mDelY > 0){// 下滑
				scrollTo(0,-mDelY);
			}else if(model == SWIPE_LEFT && mDelX < 0){// 左滑
				scrollTo(-mDelX,0);
			}else if(model == SWIPE_TOP && mDelY < 0){// 上滑
				scrollTo(0,-mDelY);
			}
			break;
		case MotionEvent.ACTION_UP:
			mCurryX = (int) event.getX();
			mCurryY = (int) event.getY();
			mDelX = mCurryX - mLastDownX;
			mDelY = mCurryY - mLastDownY;
			if (model == SWIPE_RIGHT && mDelX > 0) {
				if (Math.abs(mDelX) > mScreenWidth / 3) {
					startScroll(this.getScrollX(),0, -mScreenWidth,0, 1000);
					isClose = true;
					if(onSwipeFinish != null)
					onSwipeFinish.swipeFinish();
				} else {
					startScroll(this.getScrollX(),0, -this.getScrollX(),0, 500);
				}

			}else if (model == SWIPE_BOTTOM && mDelY > 0) {
				if (Math.abs(mDelY) > mScreenHeight / 8) {
					startScroll(0,this.getScrollY(),0, -mScreenHeight, 1000);
					isClose = true;
					if(onSwipeFinish != null)
						onSwipeFinish.swipeFinish();
				} else {
					startScroll(0,this.getScrollY(),0, -this.getScrollY(), 500);
				}
			}else if (model == SWIPE_LEFT && mDelX < 0) {
				if (Math.abs(mDelX) > mScreenWidth / 3) {
					startScroll(this.getScrollX(),0, mScreenWidth,0, 1000);
					isClose = true;
					if(onSwipeFinish != null)
						onSwipeFinish.swipeFinish();
				} else {
					startScroll(this.getScrollX(),0, -this.getScrollX(),0, 500);
				}
			}else if (model == SWIPE_TOP && mDelY< 0) {
				if (Math.abs(mDelY) > mScreenHeight / 8) {
					startScroll(0,this.getScrollY(),0, mScreenHeight, 1000);
					isClose = true;
					if(onSwipeFinish != null)
						onSwipeFinish.swipeFinish();
				} else {
					startScroll(0,this.getScrollY(),0, -this.getScrollY(), 500);
				}
			}

			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (!isEnabled()) {
			return false;
		}
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastDownX = (int)event.getX();
				mLastDownY = (int)event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				final float eventX = event.getX();
				final float eventY = event.getY();
				float xDiff = Math.abs(eventX - mLastDownX);
				float yDiff = Math.abs(eventY - mLastDownY);
				if(model == SWIPE_LEFT || model == SWIPE_RIGHT) {
					if (xDiff > mTouchSlop && xDiff > yDiff) {
						return true;
					}
				}else if(model == SWIPE_TOP || model == SWIPE_BOTTOM) {
					if (yDiff > mTouchSlop && xDiff < yDiff ) {
						return true;
					}
				}
				break;
		}
		return false;
	}

	@Override
	public void computeScroll() {

		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			Log.i("scroller", "getCurrX()= " + mScroller.getCurrX()
					+ "     getCurrY()=" + mScroller.getCurrY()
					+ "  getFinalY() =  " + mScroller.getFinalY());
			postInvalidate();
		} else {
			if (isClose) {
				this.setVisibility(View.GONE);
			}
		}
	}
	public void setModel(int model){
		this.model = model;
	}
	private OnSwipeFinish onSwipeFinish;
	public interface OnSwipeFinish{
        void swipeFinish();
	}
	public void setOnSwipeFinish(OnSwipeFinish onSwipeFinish){
		this.onSwipeFinish = onSwipeFinish;
	}
}
