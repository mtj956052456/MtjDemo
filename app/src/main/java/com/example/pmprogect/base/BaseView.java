package com.example.pmprogect.base;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.OverScroller;

import java.util.List;
import java.util.Map;

/**
 * Created by BOM on 2017/12/13 0013.
 */

/**
 * 主要实现了控件内容滑动和反弹效果
 */
public abstract class BaseView extends View {
    private static final String TAG = "BaseView";

    protected OverScroller mScroller; //用于辅助View拖动或滑行
    protected VelocityTracker mVelocityTracker; //速度追踪器
    protected int restoreWidth;  //恢复位置
    protected int screenWidth;  //手机屏幕宽度
    protected int screenHeight; //
    protected int minTextHeight;
    protected int defaultLineWidth;
    protected int defaultLineHeight;
    protected int space8;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDefaultValue(context);
    }

    protected void setRestoreWidth(int width) {
        this.restoreWidth = width;
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultValue(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initDefaultValue(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
    }

    int lastX;
    int currentX;
    int distanceX;


    protected boolean clickFlag = false;
    protected int touchX;
    protected int touchY;
    protected int putX;
    protected int screenX;


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mScroller != null) {
                    if (!mScroller.isFinished()) {  //如果滑动动画还未结束,则终止动画
                        mScroller.abortAnimation();
                    }
                }
                lastX = (int) event.getX();
                performClick();

                touchX = (int) event.getX()+getScrollX();
                touchY = (int) event.getY();
                screenX= (int) event.getY();
                putX = (int) event.getX();
                clickFlag = true;
                downClick();
                return true;
            case MotionEvent.ACTION_MOVE:
                //计算出两次动作间的滑动距离
                currentX = (int) event.getX();
                distanceX = currentX - lastX;
                distanceX = distanceX * -1;
                lastX = currentX;
                smoothScrollBy(distanceX, 0);
                getScaleX();
                int currX = mScroller.getCurrX();       //当手指接触滑动的时候获得滑动到的滑动总长度的当前x坐标
                moveFeedback(currX);

                if (Math.abs(distanceX) != 0) {
                    clickFlag = false;
                }

                return true;
            case MotionEvent.ACTION_UP:
                //根据触摸位置计算每像素的移动速率。
                //A value of 1 provides pixels per millisecond, 1000 provides pixels per second, etc.
                mVelocityTracker.computeCurrentVelocity(1000);
                //计算速率
                int velocityX = (int) mVelocityTracker.getXVelocity() * (-1);
                int velocityY = (int) mVelocityTracker.getYVelocity();
                //计算出两次动作间的滑动距离
                currentX = (int) event.getX();
                distanceX = currentX - lastX;
                distanceX = distanceX * -1;
                lastX = currentX;
                //如果速率大于最小速率要求，执行滑行，否则拖动到位置
                if (Math.abs(velocityX) > 500) {
                    if (!mScroller.isFinished()) {
                        mScroller.abortAnimation();
                    }
                    fling(velocityX, velocityY);
                } else {
                    smoothScrollBy(distanceX, 0);
                }

                int finalXUP = mScroller.getFinalX();           //当手指离开屏幕以后获得最后的坐标
                upFeedback(finalXUP);
                if (clickFlag) {
                    upClick(touchX, touchY,screenX);
                    clickFlag=false;
                }
                break;
        }
        return true;
    }

    protected abstract void upClick(int touchX, int touchY, int screenX);

    protected void downClick() {
    }



    /**
     * 手指离开屏幕控件内容滑动的最终目标
     *
     * @param finalXUP
     */
    protected abstract void upFeedback(int finalXUP);

    /**
     * 手指接触屏幕的时候接触的X坐标
     *
     * @param currX
     */
    protected abstract void moveFeedback(int currX);

    /**
     * 调用Scroller的startScroll后，Scroller会根据偏移量是时间计算当前的X坐标和Y坐标，执行invalidte会让View执行draw()方法，从而调用computeScroll()方法
     *
     * @param dx
     * @param dy
     */
    protected void smoothScrollBy(int dx, int dy) {
        if (mScroller.getFinalX() + dx > restoreWidth) {  //越界恢复,如果超出界面的时候,则回弹
            Log.e(TAG, "smoothScrollBy: "+mScroller.getFinalX() );
            dx = restoreWidth - mScroller.getFinalX();
        } else if (mScroller.getFinalX() + dx < 0) {
            dx = -1 * mScroller.getFinalX();       //如果会拉越界的时候,回弹
        }
        mScroller.startScroll(mScroller.getFinalX(), 0, dx, dy, 500);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    /**
     * 根据瞬时速度，让画布滑行
     *
     * @param velocityX X轴速度，有正负方向，正数画布左移
     * @param velocityY
     */
    protected void fling(int velocityX, int velocityY) {
        //最后两个是参数是允许的超过边界值的距离
        mScroller.fling(mScroller.getFinalX(), 0, velocityX, 0, 0, restoreWidth, 0, 0, 0, 0);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    private void initDefaultValue(Context context) {
        mScroller = new OverScroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        minTextHeight = dp2px(context, 16);
        defaultLineWidth = dp2px(context, 1);
        defaultLineHeight = dp2px(context, 12);
        space8 = dp2px(context, 8);
    }

    //工具类
    public static int dp2px(Context c, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context c, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

    public static float dp2pxF(Context c, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static float sp2pxF(Context c, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

    protected boolean isEmpty(List<?> list) {
        return null == list || list.isEmpty();
    }

    protected boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty();
    }
}
