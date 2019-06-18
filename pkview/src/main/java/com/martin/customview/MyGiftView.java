package com.martin.customview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * author：Martin Nan on 2019/6/17
 */
public class MyGiftView extends RelativeLayout {
    private int screenWidth;
    private int screenHeight;
    private static final int ERUPTION_ELEMENT_AMOUNT = 5;
    private LayoutParams layoutParams;
    private Drawable[] drawables = new Drawable[5];

    public MyGiftView(Context context) {
        super(context);
        init();
    }

    public MyGiftView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        drawables[0] = ContextCompat.getDrawable(getContext(),R.drawable.emoji1);
        drawables[1] = ContextCompat.getDrawable(getContext(),R.drawable.emoji2);
        drawables[2] = ContextCompat.getDrawable(getContext(),R.drawable.emoji3);
        drawables[3] = ContextCompat.getDrawable(getContext(),R.drawable.emoji4);
        drawables[4] = ContextCompat.getDrawable(getContext(),R.drawable.emoji5);

        layoutParams = new LayoutParams(100,100);
        //代码设置布局方式，底部居中
        layoutParams.addRule(CENTER_HORIZONTAL,TRUE);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM,TRUE);

    }

    /**
     * 1
     */
    public void addImageView(){
        for (int i = 0; i <ERUPTION_ELEMENT_AMOUNT ; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(drawables[(int) (Math.random()*drawables.length)]);
            imageView.setLayoutParams(layoutParams);

            addView(imageView);
            setAnim(imageView).start();//启动放大出现动画
            getBezierValueAnimator(imageView).start();//贝塞尔漂流记动画。
        }
    }
    /**
     * 2
     * @param view
     * @return
     */
    private AnimatorSet setAnim(View view){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.2f, 1f);

        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(500);
        enter.setInterpolator(new LinearInterpolator());//线性变化
        enter.playTogether(scaleX,scaleY);
        enter.setTarget(view);

        return enter;
    }
    /**
     * 3
     * @param target
     * @return
     */
    private ValueAnimator getBezierValueAnimator(View target) {

        //初始化一个贝塞尔计算器- - 传入
        BezierEvaluator evaluator = new BezierEvaluator(getPointF(),getPointF());

        //这里最好画个图 理解一下 传入了起点 和 终点
//        PointF randomStartPoint =  new PointF(screenWidth / 2, screenHeight);
        PointF randomStartPoint =  new PointF(screenWidth / 2, screenHeight);
        PointF randomEndPoint = new PointF((float) (Math.random()*screenWidth), (float) (Math.random()*50));
        ValueAnimator animator = ValueAnimator.ofObject(evaluator,randomStartPoint, randomEndPoint);
        animator.addUpdateListener(new BezierListener(target));
        animator.setTarget(target);
        animator.setDuration(1000);
        return animator;
    }

    /**
     * 产生随机控制点
     * @return
     */
    private PointF getPointF() {
        PointF pointF = new PointF();
        pointF.x = (float) (Math.random()*screenWidth);
        pointF.y = (float) (Math.random()*screenHeight/4);
        return pointF;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        screenWidth = getMeasuredWidth();
        screenHeight = getMeasuredHeight();
    }
}
