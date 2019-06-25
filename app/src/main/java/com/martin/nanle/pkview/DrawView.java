package com.martin.nanle.pkview;

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
import android.view.View;
import android.widget.LinearLayout;

/**
 * author：Martin Nan on 2019/6/24
 */
public class DrawView extends View {
    private final int angle = 25;
    private final Paint paint;
    private int numberLeft = 0;
    private int numberRight = 10;
//    LinearGradient linearGradientLeft, linearGradientRight;

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();

     }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (numberLeft == numberRight) {
            initView(canvas);
        } else if (numberLeft == 0) {
            initViewS(canvas, false);
        } else if (numberRight == 0) {
            initViewS(canvas, true);
        } else {

        }

    }

    //当两边 都为0 或者 两边相等的时候
    private void initView(Canvas canvas) {
        LinearGradient linearGradientLeft = new LinearGradient(0, 0, getMeasuredWidth(), 0, Color.parseColor("#CC3232"), Color.parseColor("#0fFF8F8F"), Shader.TileMode.CLAMP);
        paint.setShader(linearGradientLeft);
        RectF leftRectF = new RectF(0, 0, getMeasuredHeight(), getMeasuredHeight());//画扇形
        canvas.drawArc(leftRectF, 90, 180, true, paint);
        Path path = new Path();
        path.moveTo(getMeasuredHeight() / 2, 0);// 此点为多边形的起点
        path.lineTo(getMeasuredWidth() / 2 - angle, 0);
        path.lineTo(getMeasuredWidth() / 2 + angle, getMeasuredHeight());
        path.lineTo(getMeasuredHeight() / 2, getMeasuredHeight());
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint);

        paint.reset();
        LinearGradient   linearGradientRight = new LinearGradient(getMeasuredWidth(), 0, 0, 0, Color.parseColor("#3DA3FD"), Color.parseColor("#0f8CEBFF"), Shader.TileMode.CLAMP);
        paint.setShader(linearGradientRight);
        RectF rightRectF = new RectF(getMeasuredWidth() - getMeasuredHeight(), 0, getMeasuredWidth(), getMeasuredHeight());//画扇形
        canvas.drawArc(rightRectF, 270, 180, true, paint);
        Path path2 = new Path();
        path2.moveTo(getMeasuredWidth() / 2 - angle, 0);// 此点为多边形的起点
        path2.lineTo(getMeasuredWidth() - getMeasuredHeight() / 2, 0);
        path2.lineTo(getMeasuredWidth() - getMeasuredHeight() / 2, getMeasuredHeight());
        path2.lineTo(getMeasuredWidth() / 2 + angle, getMeasuredHeight());
        path2.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path2, paint);
    }

    //当一边是0 的时候
    private void initViewS(Canvas canvas, boolean leftIsAbsolutely) {
        LinearGradient linearGradient;
        if (leftIsAbsolutely) {
            linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, Color.parseColor("#CC3232"), Color.parseColor("#0fFF8F8F"), Shader.TileMode.CLAMP);
        }else{
            linearGradient = new LinearGradient(getMeasuredWidth(), 0, 0, 0, Color.parseColor("#3DA3FD"), Color.parseColor("#0f8CEBFF"), Shader.TileMode.CLAMP);
        }
        paint.setShader( linearGradient);
        //画扇形
        RectF leftRectF = new RectF(0, 0, getMeasuredHeight(), getMeasuredHeight());
        canvas.drawArc(leftRectF, 90, 180, true, paint);
        //画扇形

        Path path = new Path();
        path.moveTo(getMeasuredHeight() / 2, 0);// 此点为多边形的起点
        path.lineTo(getMeasuredWidth() - getMeasuredHeight()/2, 0);
        path.lineTo(getMeasuredWidth()- getMeasuredHeight()/2, getMeasuredHeight());
        path.lineTo(getMeasuredHeight() / 2, getMeasuredHeight());
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint);
        RectF rightRectF = new RectF(getMeasuredWidth() - getMeasuredHeight(), 0, getMeasuredWidth(), getMeasuredHeight());//画扇形
        canvas.drawArc(rightRectF, 270, 180, true, paint);

//        paint.reset();
//        paint.setShader(linearGradientRight);
//        RectF rect2shanxing2 = new RectF(getMeasuredWidth() - getMeasuredHeight(), 0, getMeasuredWidth(), getMeasuredHeight());//画扇形
//        canvas.drawArc(rect2shanxing2, 270, 180, true, paint);
//        Path path2 = new Path();
//        path2.moveTo(getMeasuredWidth() / 2 - angle, 0);// 此点为多边形的起点
//        path2.lineTo(getMeasuredWidth() - getMeasuredHeight() / 2, 0);
//        path2.lineTo(getMeasuredWidth() - getMeasuredHeight() / 2, getMeasuredHeight());
//        path2.lineTo(getMeasuredWidth() / 2 + angle, getMeasuredHeight());
//        path2.close(); // 使这些点构成封闭的多边形
//        canvas.drawPath(path2, paint);
    }
}
