
package com.shaobao.ts.view;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.shaobao.ts.DriverActivity;
import com.shaobao.ts.view.MagicScrollView.ScrollListener;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;



public class MagicTextView extends TextView implements ScrollListener {
    // view 自身高度
    private int mHeight;
    // view 距离scrollView最顶端高度
    private int locHeight;
    // 递减/递增 的变量值
    private double mRate;
    // view 设置的值
    private double mValue;
    // 当前显示的值
    private double mCurValue;
    // 当前变化后最终状态的目标值
    private double mGalValue;
    // 控制加减法
    private int rate = 1;
    // 当前变化状态(增/减/不变)
    private int mState = 0;
    private boolean refreshing;
    private static final int REFRESH = 1;
    private static final int SCROLL = 2;
    // 偏移量 主要用来进行校正距离。
    private static final int OFFSET = 20;
    DecimalFormat fnum = new DecimalFormat("0.00");

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {

                case REFRESH:
                    if (rate * mCurValue < mGalValue) {
                        refreshing = true;
                        setText(fnum.format(mCurValue));
                        mCurValue += mRate * rate;
                        mHandler.sendEmptyMessageDelayed(REFRESH, 50);
                    } else {
                        refreshing = false;
                        setText(fnum.format(mGalValue));
                    }
                    break;

                case SCROLL:
                    doScroll(msg.arg1, msg.arg2);
                    break;

                default:
                    break;
            }
        };
    };

    public MagicTextView(Context context) {
        super(context);
    }

    public MagicTextView(Context context, AttributeSet set) {
        super(context, set);
    }

    public MagicTextView(Context context, AttributeSet set, int defStyle) {
        super(context, set, defStyle);
    }

    public void setLocHeight(int height) {
        locHeight = height;
    }

    public void setValue(double value) {
        mCurValue = 0.00;
        mGalValue = isShown() ? value : 0;
        mValue = value;
        mRate = (double) (mValue / 20.00);
        BigDecimal b = new BigDecimal(mRate);
        mRate = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    public void onScrollChanged(int state, int scroll) {
        Message msg = mHandler.obtainMessage();
        msg.what = SCROLL;
        msg.arg1 = state;
        msg.arg2 = scroll;
        mHandler.sendMessage(msg);
    }

    private void doScroll(int state, int scroll) {
        if (mState == state && refreshing)
            return;

        mState = state;
        if (doMinus(scroll)) {
            rate = -1;
            mGalValue = 0;
        } else if (doPlus(scroll)) {
            rate = 1;
            mGalValue = mValue;
        }
        mHandler.sendEmptyMessage(REFRESH);
    }

    private boolean doPlus(int scroll) {
    	
        if (isShown() && (scroll + DriverActivity.mWinheight > locHeight + OFFSET)
                && (mState == MagicScrollView.UP))
            return true;
        if (isShown() && (scroll < locHeight) && mState == MagicScrollView.DOWN)
            return true;

        return false;
    }

    private boolean doMinus(int scroll) {
        if (isShown() && (scroll > locHeight) && (mState == MagicScrollView.UP))
            return true;

        if (isShown() && (scroll + DriverActivity.mWinheight - mHeight < locHeight - OFFSET)
                && (mState == MagicScrollView.DOWN))
            return true;

        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 执行完onMeasure后即可获得view的宽度
        mHeight = getMeasuredHeight();
    }
}
