package com.martin.customview;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;

/**
 * author：Martin Nan on 2019/6/17
 */
public class BezierListener implements ValueAnimator.AnimatorUpdateListener {

    private View target;

    public BezierListener(View target) {
        this.target = target;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
        PointF pointF = (PointF) animation.getAnimatedValue();
        target.setX(pointF.x);
        target.setY(pointF.y);
        // 这里顺便做一个alpha动画
        target.setAlpha(1 - animation.getAnimatedFraction());

        // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度
    }
}
