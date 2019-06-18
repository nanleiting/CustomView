package com.martin.customview.emojiView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.martin.customview.BezierEvaluator;
import com.martin.customview.BezierListener;
import com.martin.customview.R;

import java.util.Random;

/**
 * author：Martin Nan on 2019/6/14
 */
public class AcodeEmojiView extends RelativeLayout {
    private Context mContext;
    private int screenWidth;
    private int screenHeight;
    private static final int ERUPTION_ELEMENT_AMOUNT = 6;
    //动画插值器，其实就是几种动画效果
    private Interpolator[] interpolators = new Interpolator[4];
    //图片
    private int[] icons = new int[]{R.drawable.emoji1, R.drawable.emoji2, R.drawable.emoji3, R.drawable.emoji4, R.drawable.emoji5, R.drawable.emoji6, R.drawable.emoji7, R.drawable.emoji8, R.drawable.emoji9, R.drawable.emoji10, R.drawable.emoji11};
    //起始坐标X
    private float startX = -1;
    //起始坐标Y
    private float startY = -1;
    //点击次数 这个用于展示
    private int clickCount;
    //点击次数备份   这个用于真正的统计点击次数
    private int clickCountBackups;
    RelativeLayout rl_add_view;
    LikeTextView tv_content;
  int[] numberDrawableArray,levelDrawableArray;
    public void setIconArray(int[] emojiArray,int[] numberDrawableArray,int[] levelDrawableArray) {
        this.icons = emojiArray;
        tv_content.setNumberDrawableArray(numberDrawableArray);
        tv_content.setLevelDrawableArray(levelDrawableArray);
    }

    public AcodeEmojiView(Context context) {
        this(context, null);
    }

    public AcodeEmojiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setWillNotDraw(false);
        init();
    }
    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.layout_like_view, this, true);
        interpolators[0] = new AccelerateDecelerateInterpolator(); // 在动画开始与结束的地方速率改变比较慢，在中间的时候加速
        interpolators[1] = new AccelerateInterpolator();  // 在动画开始的地方速率改变比较慢，然后开始加速
        interpolators[2] = new DecelerateInterpolator(); // 在动画开始的地方快然后慢
        interpolators[3] = new LinearInterpolator();  // 以常量速率改变
        rl_add_view = findViewById(R.id.rl_add_view);
        tv_content = findViewById(R.id.tv_content);

    }
    /**
     * 添加emoji
     * 每次add创建5个emoji
     */
    public void addImageView(View view) {
        clickCount++;
        clickCountBackups += ERUPTION_ELEMENT_AMOUNT;
        startX = view.getX() + view.getWidth() / 2;
        startY = view.getY() - 130;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = 80;
        params.height = 80;
        for (int i = 0; i < ERUPTION_ELEMENT_AMOUNT; i++) {
            final ImageView iv1 = new ImageView(mContext);
            iv1.setLayoutParams(params);
            iv1.setImageResource(icons[new Random().nextInt(icons.length)]);
            rl_add_view.addView(iv1);
            // 开启动画，并且用完销毁
            AnimatorSet set = getAnimSet(iv1);
            set.start();
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    rl_add_view.removeView(iv1);
                    clickCountBackups--;
                    if (clickCountBackups == 0) {
                        Log.d("post", "动画全部执行完毕");
                        clickCount = 0;
                        tv_content.setVisibility(GONE);
                    }
                }
            });
        }
        if(clickCount!=0){
            tv_content.setVisibility(VISIBLE);
            tv_content.setClickCount(clickCount);
        }

    }

    /**
     * 动画集合
     * 缩放，透明渐变
     * 贝塞尔曲线的动画
     */
    private AnimatorSet getAnimSet(ImageView iv) {
        AnimatorSet set = new AnimatorSet();
        ValueAnimator bezier = getBezierValueAnimator(iv);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv, SCALE_X, 1f, 0.7f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv, SCALE_Y, 1f, 0.7f);
        set.setDuration(1000);
        set.playTogether(scaleX, scaleY, bezier);
        return set;
    }

    private ValueAnimator getBezierValueAnimator(View target) {
        //初始化一个贝塞尔计算器- - 传入
        BezierEvaluator evaluator = new BezierEvaluator(getPointF(), getPointF());

        //这里最好画个图 理解一下 传入了起点 和 终点
        PointF randomStartPoint = new PointF(startX, startY);
        PointF randomEndPoint = new PointF((float) (Math.random() * screenWidth), (float) (Math.random() * 50));
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, randomStartPoint, randomEndPoint);
        animator.addUpdateListener(new BezierListener(target));
        animator.setTarget(target);
        animator.setInterpolator(interpolators[new Random().nextInt(4)]);
        animator.setDuration(1000);
        return animator;
    }

    /**
     * 产生随机控制点
     *
     * @return
     */
    private PointF getPointF() {
        PointF pointF = new PointF();
        pointF.x = (float) (Math.random() * screenWidth);
        pointF.y = (float) (Math.random() * screenHeight / 4);
        return pointF;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        screenWidth = getMeasuredWidth();
        screenHeight = getMeasuredHeight();
    }


}
