package com.martin.customview.pkview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * author：Martin Nan on 2019/6/24
 */
public class CompetitionBackView extends View {
    private final int angle = 25;
    private final Paint paint;
    private int numberLeft = 0;
    private int numberRight = 10;
    private int leftColorStart;
    private int leftColorEnd;
    private int rightColorStart;
    private int rightColorEnd;

    public CompetitionBackView(Context context) {
        this(context, null);
    }

    public CompetitionBackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompetitionBackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (numberLeft == numberRight) {
//            initView(canvas);
//        } else if (numberLeft == 0) {
//            initViewZero(canvas, false);
//        } else if (numberRight == 0) {
//            initViewZero(canvas, true);
//        } else {
//            initView(canvas, numberLeft, numberRight);
//        }
        if (numberLeft == 0 && numberRight != 0) {
            initViewZero(canvas, false);
        } else if (numberRight == 0 && numberLeft != 0) {
            initViewZero(canvas, true);
        } else {
            initView(canvas, numberLeft, numberRight);
        }

    }

    //如果两个数据不相当的话
    private void initView(Canvas canvas, int numberLeft, int numberRight) {
        int sumWidth = getMeasuredWidth() - getMeasuredHeight();//- angle;//总长度
        int leftW = 0;
        if ((numberRight + numberLeft) == 0) {
            leftW = sumWidth / 2 + getMeasuredHeight() / 2;
        } else {
            leftW = (numberLeft * sumWidth / (numberLeft + numberRight)) + getMeasuredHeight() / 2;
        }
//        LinearGradient linearGradientLeft = new LinearGradient(0, 0, getMeasuredWidth(), 0, Color.parseColor("#CC3232"), Color.parseColor("#0fFF8F8F"), Shader.TileMode.CLAMP);
        LinearGradient linearGradientLeft = new LinearGradient(0, 0, getMeasuredWidth(), 0, leftColorStart, leftColorEnd, Shader.TileMode.CLAMP);
        paint.setShader(linearGradientLeft);
        RectF leftRectF = new RectF(0, 0, getMeasuredHeight(), getMeasuredHeight());//画扇形
        canvas.drawArc(leftRectF, 90, 180, true, paint);

        Path path = new Path();
        path.moveTo(getMeasuredHeight() / 2, 0);// 此点为多边形的起点A
        path.lineTo(leftW - angle < getMeasuredHeight() / 2 ? getMeasuredHeight() / 2 : leftW - angle, 0);//B
        path.lineTo(leftW + angle > (getMeasuredWidth() - getMeasuredHeight() / 2) ? getMeasuredWidth() - getMeasuredHeight() / 2 : leftW + angle, getMeasuredHeight());//C
        path.lineTo(getMeasuredHeight() / 2, getMeasuredHeight());//D
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint);


        paint.reset();
//        LinearGradient linearGradientRight = new LinearGradient(getMeasuredWidth(), 0, 0, 0, Color.parseColor("#3DA3FD"), Color.parseColor("#0f8CEBFF"), Shader.TileMode.CLAMP);
        LinearGradient linearGradientRight = new LinearGradient(getMeasuredWidth(), 0, 0, 0, rightColorStart, rightColorEnd, Shader.TileMode.CLAMP);
        paint.setShader(linearGradientRight);
        RectF rightRectF = new RectF(getMeasuredWidth() - getMeasuredHeight(), 0, getMeasuredWidth(), getMeasuredHeight());//画半圆
        canvas.drawArc(rightRectF, 270, 180, true, paint);
        Path path2 = new Path();
//        path2.moveTo(leftW-angle, 0);// 此点为多边形的起点
        path2.moveTo(leftW - angle < getMeasuredHeight() / 2 ? getMeasuredHeight() / 2 : leftW - angle, 0);//B
//        path2.lineTo(leftW + angle, getMeasuredHeight());//C
        path2.lineTo(leftW + angle > (getMeasuredWidth() - getMeasuredHeight() / 2) ? getMeasuredWidth() - getMeasuredHeight() / 2 : leftW + angle, getMeasuredHeight());//C
        path2.lineTo(getMeasuredWidth() - getMeasuredHeight() / 2, getMeasuredHeight());
        path2.lineTo(getMeasuredWidth() - getMeasuredHeight() / 2, 0);


        path2.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path2, paint);
    }

////    //两边相等的时候
//    private void initView(Canvas canvas) {
//        LinearGradient linearGradientLeft = new LinearGradient(0, 0, getMeasuredWidth(), 0, Color.parseColor("#CC3232"), Color.parseColor("#0fFF8F8F"), Shader.TileMode.CLAMP);
//        paint.setShader(linearGradientLeft);
//        RectF leftRectF = new RectF(0, 0, getMeasuredHeight(), getMeasuredHeight());//画扇形
//        canvas.drawArc(leftRectF, 90, 180, true, paint);
//
//        Path path = new Path();
//        path.moveTo(getMeasuredHeight() / 2, 0);// 此点为多边形的起点
//        path.lineTo(getMeasuredWidth() / 2 - angle, 0);
//        path.lineTo(getMeasuredWidth() / 2 + angle, getMeasuredHeight());
//        path.lineTo(getMeasuredHeight() / 2, getMeasuredHeight());
//        path.close(); // 使这些点构成封闭的多边形
//        canvas.drawPath(path, paint);
//
//        paint.reset();
//        LinearGradient linearGradientRight = new LinearGradient(getMeasuredWidth(), 0, 0, 0, Color.parseColor("#3DA3FD"), Color.parseColor("#0f8CEBFF"), Shader.TileMode.CLAMP);
//        paint.setShader(linearGradientRight);
//        RectF rightRectF = new RectF(getMeasuredWidth() - getMeasuredHeight(), 0, getMeasuredWidth(), getMeasuredHeight());//画扇形
//        canvas.drawArc(rightRectF, 270, 180, true, paint);
//        Path path2 = new Path();
//        path2.moveTo(getMeasuredWidth() / 2 - angle, 0);// 此点为多边形的起点
//        path2.lineTo(getMeasuredWidth() - getMeasuredHeight() / 2, 0);
//        path2.lineTo(getMeasuredWidth() - getMeasuredHeight() / 2, getMeasuredHeight());
//        path2.lineTo(getMeasuredWidth() / 2 + angle, getMeasuredHeight());
//        path2.close(); // 使这些点构成封闭的多边形
//        canvas.drawPath(path2, paint);
//    }

    //当一边是0 的时候
    private void initViewZero(Canvas canvas, boolean leftIsAbsolutely) {
        LinearGradient linearGradient;
        if (leftIsAbsolutely) {
//            linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, Color.parseColor("#CC3232"), Color.parseColor("#FF8F8F"), Shader.TileMode.CLAMP);
            linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, leftColorStart, leftColorEnd, Shader.TileMode.CLAMP);
        } else {
//            linearGradient = new LinearGradient(getMeasuredWidth(), 0, 0, 0, Color.parseColor("#3DA3FD"), Color.parseColor("#8CEBFF"), Shader.TileMode.CLAMP);
            linearGradient = new LinearGradient(getMeasuredWidth(), 0, 0, 0, rightColorStart, rightColorEnd, Shader.TileMode.CLAMP);
        }
        paint.setShader(linearGradient);
        //画扇形
        RectF leftRectF = new RectF(0, 0, getMeasuredHeight(), getMeasuredHeight());
        canvas.drawArc(leftRectF, 90, 180, true, paint);
        //画扇形

        Path path = new Path();
        path.moveTo(getMeasuredHeight() / 2, 0);// 此点为多边形的起点
        path.lineTo(getMeasuredWidth() - getMeasuredHeight() / 2, 0);
        path.lineTo(getMeasuredWidth() - getMeasuredHeight() / 2, getMeasuredHeight());
        path.lineTo(getMeasuredHeight() / 2, getMeasuredHeight());
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint);
        RectF rightRectF = new RectF(getMeasuredWidth() - getMeasuredHeight(), 0, getMeasuredWidth(), getMeasuredHeight());//画扇形
        canvas.drawArc(rightRectF, 270, 180, true, paint);
    }


    public void setNumberLeftRight(int numberLeft, int numberRight) {
        this.numberRight = numberRight;
        this.numberLeft = numberLeft;
        invalidate();
    }

    public void setLeftColorStart(int leftColorStart) {
        this.leftColorStart = leftColorStart;
    }

    public void setLeftColorEnd(int leftColorEnd) {
        this.leftColorEnd = leftColorEnd;
    }

    public void setRightColorStart(int rightColorStart) {
        this.rightColorStart = rightColorStart;
    }

    public void setRightColorEnd(int rightColorEnd) {
        this.rightColorEnd = rightColorEnd;
    }

    public int getNumberLeft() {
        return numberLeft;
    }

    public int getNumberRight() {
        return numberRight;
    }

}
