package com.martin.customview.pkview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import com.martin.customview.R;

/**
 * author：Martin Nan on 2019/6/25
 * 倒计时
 */
public class TimeCountTextView extends AppCompatTextView {
    private CountDownTimer countDownTimer;
    private StateListener stateListener;
    private long sumTime;
    private long countDownTime;
    private boolean isSetUnit;

    public TimeCountTextView(Context context) {
        this(context, null);
    }

    public TimeCountTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeCountTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimeCountTextView, 0, 0);
        sumTime = ta.getInteger(R.styleable.TimeCountTextView_sum_time, 0);
        countDownTime = ta.getInteger(R.styleable.TimeCountTextView_count_down_time, 0);
        boolean is_start = ta.getBoolean(R.styleable.TimeCountTextView_is_start, false);
        initView(sumTime * 1000, countDownTime * 1000);
        if(is_start){
            start();
        }
        ta.recycle();
    }

    private void initView(long sumTime, long countDownTime) {
        countDownTimer = new CountDownTimer(sumTime, countDownTime) {
            @Override
            public void onTick(long millisUntilFinished) {
                long number = millisUntilFinished / 1000;
                if (isSetUnit) {
                    if (stateListener != null) {
                        stateListener.onTick(number);
                    }
                } else {
                    TimeCountTextView.this.setText(number + "");
                }
            }

            @Override
            public void onFinish() {
                if (stateListener != null) {
                    stateListener.onFinish();
                }
            }
        };
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility != View.VISIBLE) {
            countDownTimer.cancel();
        }
    }

    public void start() {
        if (countDownTimer != null) {
            countDownTimer.start();
        }
    }

    public void setSumTime(long sumTime, long countDownTime) {
        this.sumTime = sumTime;
        this.countDownTime = countDownTime;
    }

    interface StateListener {
        void onTick(long number);

        void onFinish();
    }

}
