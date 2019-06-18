package com.martin.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

/***
 *@author Martin
 * If you can't fly,then run,
 * if you can't run,then walk,
 * if you can't walk,then crawl,
 * but whatever you do you have to keep moving forward.
 * Stay hungry. Stay foolish
 *
 * 圆形 自动 绘制进度条  
 */
public class CircleBar extends RelativeLayout {

    /*绘制圆周的画笔*/
    private Paint backCirclePaint;
    /*绘制文字的画笔*/
    private Paint textPaint;
    /*绘制上层的画笔*/
    private Paint upCirclePaint;

    /*字体大小*/
    //    private int width, height;//宽高
    private int initDataKeep;//初始默认数字；
    private int initData;//初始默认数字；
    private int nowDataStr;//记录当前 绘制的 秒数
    private int nowData;//进度条
    private  int intervalTimeCircle =0;
    private  int intervalTimeText =1000/250;

    private Handler handler;
    private Runnable runnableCircle, runnableText;
    private StopListener stopListener;
    private String unit="";

    public void setStopListener(StopListener stopListener) {
        this.stopListener = stopListener;
    }

    public CircleBar(Context context) {
        this(context, null);
    }

    public CircleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        handler = new Handler();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleBar);
         float textSize = (int) a.getDimension(R.styleable.CircleBar_textSize, 0);
         int color = a.getColor(R.styleable.CircleBar_color, 0x990000FF);;//整个默认颜色
         int progressColor = a.getColor(R.styleable.CircleBar_pcolor, 0x990000FF);//加载上的颜色
         int textColor = a.getColor(R.styleable.CircleBar_textColor, 0x990000FF);//字体颜色
//        width = (int) a.getDimension(R.styleable.CircleBar_width, 0);
//        high = (int) a.getDimension(R.styleable.CircleBar_high, 0);
        initData = a.getInteger(R.styleable.CircleBar_tvNum, 0);
        initDataKeep=initData;
        intervalTimeText = a.getInteger(R.styleable.CircleBar_intervalTime, 0);
        intervalTimeCircle=intervalTimeText*1000/250;
        unit =a.getString(R.styleable.CircleBar_unit);
        nowDataStr = initData;
        initData=initData*intervalTimeText;
         int circleBorderWidth = (int) a.getDimension(R.styleable.CircleBar_c_border_width, 0);//条圆环的宽度
        backCirclePaint = new Paint();
        backCirclePaint.setStyle(Paint.Style.STROKE);
        backCirclePaint.setAntiAlias(true);
        backCirclePaint.setColor(color);
        backCirclePaint.setStrokeWidth(circleBorderWidth);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);

        upCirclePaint = new Paint();
        upCirclePaint.setStyle(Paint.Style.STROKE);
        upCirclePaint.setAntiAlias(true);
        upCirclePaint.setColor(progressColor);
        upCirclePaint.setStrokeWidth(circleBorderWidth);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(measureWidth, measureHeight), Math.min(measureWidth, measureHeight));
    }

    /*内边距*/
    private float circlePadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(
                new RectF(circlePadding * 2, circlePadding * 2,
                        getMeasuredWidth() - circlePadding * 2, getMeasuredHeight() - circlePadding * 2), -90, 360, false, backCirclePaint);


        //4.绘制文字
        //X轴中点坐标
        int centerX = getMeasuredWidth() / 2;


        float textWidth = textPaint.measureText(nowDataStr + unit);
        int textHeight = (int) (Math.ceil(textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent) + 2);
        canvas.drawText(nowDataStr + unit, centerX - textWidth / 2, centerX + textHeight / 4, textPaint);


        canvas.drawArc(
                new RectF(circlePadding * 2, circlePadding * 2,
                        getMeasuredWidth() - circlePadding * 2, getMeasuredHeight() - circlePadding * 2), -90, (nowData * 360 / (initData * 1000)), false, upCirclePaint);


    }

    public int getInitData() {
        return initData;
    }

    /**
     * 设置百分比

     */
    public void setNowData(int data) {
        if (data < 0) {
            data = 0;
        } else if (data > initData * 1000) {
            data = initData * 1000;
        }

        this.nowData = data;
        postInvalidate();
    }

    /**
     * 设置百分比

     */
    public void setNowDataStr(int nowDatastr) {
        if (nowDatastr < 0) {
            nowDatastr = 0;
        } else if (nowDatastr > initData) {
            nowDatastr = initData;
        }

        this.nowDataStr = nowDatastr;
        postInvalidate();
    }

    public void start() {
        setNowData(initDataKeep);
        setNowDataStr(initDataKeep);
        runnableCircle = new Runnable() {
            @Override
            public void run() {
                // handler自带方法实现定时器
                try {
                    if (nowData < initData * 1000) {
                        handler.postDelayed(this, initData * intervalTimeCircle);
                        nowData += initData * intervalTimeCircle;
                        setNowData(nowData);
                    } else {
                        if (stopListener != null) {
                            stop();
                            stopListener.onStopListener();
                        }
                    }

                } catch (Exception e) {

                }
            }
        };

        handler.postDelayed(runnableCircle, initData * intervalTimeCircle); //每隔initData * 1000 / 25ms执行

        runnableText = new Runnable() {
            @Override
            public void run() {
                // handler自带方法实现定时器
                try {
                    if (nowDataStr != 0) {
                        handler.postDelayed(this, intervalTimeText*1000);
                        nowDataStr -= 1;
                        setNowDataStr(nowDataStr);
                    }

                } catch (Exception e) {

                }
            }
        };

        handler.postDelayed(runnableText, intervalTimeText*1000); //每隔1s执行
    }
    private void stop() {
        try {
            handler.removeCallbacks(runnableText);
            handler.removeCallbacks(runnableCircle);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface StopListener {
        void onStopListener();
    }
}
