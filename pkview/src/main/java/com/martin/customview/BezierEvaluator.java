package com.martin.customview;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * author：Martin Nan on 2019/6/17
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {
    private PointF pointF1;
    private PointF pointF2;
    public BezierEvaluator(PointF pointF1,PointF pointF2){
        this.pointF1 = pointF1;
        this.pointF2 = pointF2;
    }
    @Override
    public PointF evaluate(float time, PointF startValue,
                           PointF endValue) {

//        float timeLeft = 1.0f - time;
//        PointF point = new PointF();//结果
//
//        point.x = timeLeft * timeLeft * timeLeft * (startValue.x)
//                + 3 * timeLeft * timeLeft * time * (pointF1.x)
//                + 3 * timeLeft * time * time * (pointF2.x)
//                + time * time * time * (endValue.x);
//
//        point.y = timeLeft * timeLeft * timeLeft * (startValue.y)
//                + 3 * timeLeft * timeLeft * time * (pointF1.y)
//                + 3 * timeLeft * time * time * (pointF2.y)
//                + time * time * time * (endValue.y);
//        return point;
        PointF pointF = new PointF();
        pointF.x = startValue.x + time *12* (endValue.x - startValue.x);// x方向匀速移动
        pointF.y = startValue.y + time  *2* (endValue.y - startValue.y);// y方向抛物线加速移动
        return pointF;
    }
}