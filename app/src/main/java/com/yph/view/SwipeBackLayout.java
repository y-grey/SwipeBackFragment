package com.yph.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;


public class SwipeBackLayout extends FrameLayout {

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

    private Paint mPaint;

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


        ImageView mImgView = new ImageView(mContext);
        mImgView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        mImgView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImgView.setBackgroundColor(getResources().getColor(android.R.color.white));
        addView(mImgView);//添加一层白色的imageview作为背景
        setClickable(true);
        setBackgroundColor(Color.argb(0, 0, 0, 0));//背景设为透明

        //初始化画笔 用来画阴影
        mPaint = new Paint();
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.DKGRAY);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        viewPagers.clear();
        findViewPager(SwipeBackLayout.this);
    }
    List<ViewPager> viewPagers = new ArrayList<>();
    private void findViewPager(ViewGroup viewGroup){
        if (viewGroup == null || viewGroup.getVisibility() != View.VISIBLE) {
            return ;
        }
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewPager) {
                viewPagers.add((ViewPager)view);
            }else if(view instanceof ViewGroup){
                findViewPager((ViewGroup)view);
            }
        }
    }

    private void startScroll(int startX, int startY, int dx, int dy, int duration) {
        mScroller.startScroll(startX, startY, dx, dy, duration);//传入参数
        invalidate();//重绘调用draw里面computeScroll方法
    }

    private void startScrollToFinish(int startX, int startY, int dx, int dy, int duration) {
        if (ComputeScrollFinish)
            startScroll(startX, startY, dx, dy, duration);
        isClose = true;
        hideShadow = true;
        invalidate();
        if (onSwipeFinishListener != null)
            onSwipeFinishListener.swipeStart();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mCurryX = (int) event.getX();
                mCurryY = (int) event.getY();
                mDelX = mCurryX - mLastDownX;
                mDelY = mCurryY - mLastDownY;
                invalidate();
                if (model == SWIPE_RIGHT && mDelX > 0) {// 右滑
                    scrollTo(-mDelX, 0);
                } else if (model == SWIPE_BOTTOM && mDelY > 0) {// 下滑
                    scrollTo(0, -mDelY);
                } else if (model == SWIPE_LEFT && mDelX < 0) {// 左滑
                    scrollTo(-mDelX, 0);
                } else if (model == SWIPE_TOP && mDelY < 0) {// 上滑
                    scrollTo(0, -mDelY);
                }
                break;
            case MotionEvent.ACTION_UP:
                mCurryX = (int) event.getX();
                mCurryY = (int) event.getY();
                mDelX = mCurryX - mLastDownX;
                mDelY = mCurryY - mLastDownY;
                if (model == SWIPE_RIGHT) {
                    if (Math.abs(mDelX) > mScreenWidth / 3 && mDelX > 0) { //右滑并且滑动距离大于屏幕宽度的1/3
                        startScrollToFinish(getScrollX(), 0, -mScreenWidth - getScrollX(), 0, 1000);//滑动至结束
                    } else {
                        startScroll(getScrollX(), 0, -getScrollX(), 0, 500);//滑动回初始状态
                    }
                } else if (model == SWIPE_BOTTOM) {
                    if (Math.abs(mDelY) > mScreenHeight / 8 && mDelY > 0) {
                        startScrollToFinish(0, getScrollY(), 0, -mScreenHeight - getScrollY(), 1000);
                    } else {
                        startScroll(0, getScrollY(), 0, -getScrollY(), 500);
                    }
                } else if (model == SWIPE_LEFT) {
                    if (Math.abs(mDelX) > mScreenWidth / 3 && mDelX < 0) {
                        startScrollToFinish(getScrollX(), 0, mScreenWidth - getScrollX(), 0, 1000);
                    } else {
                        startScroll(getScrollX(), 0, -getScrollX(), 0, 500);
                    }
                } else if (model == SWIPE_TOP) {
                    if (Math.abs(mDelY) > mScreenHeight / 8 && mDelY < 0) {
                        startScrollToFinish(0, getScrollY(), 0, mScreenHeight - getScrollY(), 1000);
                    } else {
                        startScroll(0, getScrollY(), 0, -getScrollY(), 500);
                    }
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!isEnabled())
            return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastDownX = (int) event.getX();
                mLastDownY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                final float eventY = event.getY();
                float xDiff = Math.abs(eventX - mLastDownX);
                float yDiff = Math.abs(eventY - mLastDownY);
                if (model == SWIPE_LEFT || model == SWIPE_RIGHT) {
                    if (xDiff > mTouchSlop && xDiff > yDiff) { //x滑动距离大于y滑动距离，并且大于mTouchSlop，认为是左滑
                        for(ViewPager vp : viewPagers){
                            if(inRangeOfView(vp,event)){//如果滑动的是viewPager
                                return false;//不拦截，事件交由viewPager处理
                            }
                        }
                        return true;//拦截处理事件
                    }
                } else if (model == SWIPE_TOP || model == SWIPE_BOTTOM) {
                    if (yDiff > mTouchSlop && xDiff < yDiff) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private boolean inRangeOfView(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (event.getX() < x || event.getX() > (x + view.getWidth()) || event.getY() < y || event.getY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    /**
     * computeScroll在View的draw方法里面是空实现，所以这里需要自己去实现，以下是较为标准的写法
     * 可以看到的是在computeScroll方法里面通过scrollTo方法来实现View的滑动，紧接着调用了postInvalidate重绘
     * 会再次进入此方法，如此循环从而实现滑动的效果
     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        } else {
            if (isClose) {
                setVisibility(View.GONE);
                if (onSwipeFinishListener != null)
                    onSwipeFinishListener.swipeFinish();
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawShadow(canvas);
        Log.d("", "------>dispatchDraw");
        super.dispatchDraw(canvas);
    }

    private boolean hideShadow = false;

    /**
     * 通过改变画笔的alpha值来改变阴影的深浅
     * RectF为限定画笔绘制的矩形区域，注意这里需要根据滑动值来调整区域，
     * 例如往右边滑动，其阴影左边界要相应向左偏移
     */
    private void drawShadow(Canvas canvas) {
        canvas.save();
        RectF rectF = new RectF(0, 0, mScreenWidth, mScreenHeight);
        int alpha = 0;
        if (!hideShadow) {
            if (model == SWIPE_RIGHT) {
                alpha = (int) (255 + getScrollX() / (float) (mScreenWidth / 2) * 255);
                rectF = new RectF(getScrollX(), 0, mScreenWidth, mScreenHeight);
            } else if (model == SWIPE_LEFT) {
                alpha = (int) (255 - getScrollX() / (float) (mScreenWidth / 2) * 255);
                rectF = new RectF(0, 0, mScreenWidth + getScrollX(), mScreenHeight);
            } else if (model == SWIPE_BOTTOM) {
                alpha = (int) (255 + getScrollY() / (float) (mScreenHeight / 2) * 255);
                rectF = new RectF(0, getScrollY(), mScreenWidth, mScreenHeight);
            } else {
                alpha = (int) (255 - getScrollY() / (float) (mScreenHeight / 2) * 255);
                rectF = new RectF(0, 0, mScreenWidth, mScreenHeight + getScrollY());
            }
            if (alpha < 0) alpha = 0;
        }
        mPaint.setAlpha(alpha);
        canvas.drawRect(rectF, mPaint);
        canvas.restore();
    }

    /**
     * 设置滑动方向
     *
     * @param model 如SwipeBackLayout.SWIPE_LEFT
     */

    public void setModel(int model) {
        this.model = model;
    }

    boolean ComputeScrollFinish = true;

    public void setComputeScrollFinish(boolean ComputeScrollFinish) {
        this.ComputeScrollFinish = ComputeScrollFinish;
    }

    private OnSwipeFinishListener onSwipeFinishListener;

    public interface OnSwipeFinishListener {
        void swipeFinish();

        void swipeStart();
    }

    /**
     * 设置自动滑动结束的监听
     */
    public void setOnSwipeFinishListener(OnSwipeFinishListener onSwipeFinishListener) {
        this.onSwipeFinishListener = onSwipeFinishListener;
    }
}
