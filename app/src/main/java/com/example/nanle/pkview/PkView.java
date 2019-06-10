package com.example.nanle.pkview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * author：Martin Nan on 2019/6/10
 * ceshi 修改 分支1
 */
public class PkView extends LinearLayout {
    private Context context;
    private TextView tvLeft, ivLeft, ivRight, tvRight;
    ImageView iv_yes, iv_no;
    private int left_num;
    private int right_num;
    private int left_color;
    private int right_color;

    public PkView(Context context) {
        this(context, null);
    }

    public PkView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PkView, 0, 0);
        left_num = ta.getInteger(R.styleable.PkView_left_num, 0);
        right_num = ta.getInteger(R.styleable.PkView_right_num, 0);
        left_color = ta.getColor(R.styleable.PkView_left_color, Color.parseColor("#ff0000"));
        right_color = ta.getColor(R.styleable.PkView_right_color, Color.parseColor("#00ff00"));
        initView();
        ta.recycle();
    }

    private void initView() {
        if (isInEditMode())
            return;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.layout_pk_view, this);
        ivLeft = (TextView) v.findViewById(R.id.iv_left);
        ivRight = (TextView) v.findViewById(R.id.iv_right);

        tvLeft = (TextView) v.findViewById(R.id.tv_left);
        tvRight = (TextView) v.findViewById(R.id.tv_right);
        iv_yes = (ImageView) v.findViewById(R.id.iv_yes);
        iv_no = (ImageView) v.findViewById(R.id.iv_no);
        setLeftAndRightNum(left_num, right_num);

        iv_yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                left_num = left_num + 1;
                setLeftAndRightNum(left_num, right_num);
            }
        });
        iv_no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                right_num = right_num + 1;
                setLeftAndRightNum(left_num, right_num);
            }
        });
    }

    public void setLeftAndRightNum(int leftNum, int rightNum) {
        left_num = leftNum;
        right_num = rightNum;
        tvLeft.setText(leftNum + "");
        tvRight.setText(rightNum + "");
        if (leftNum == 0 && rightNum == 0) {
            ivLeft.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 0.5f));//设置赞成的权重
            ivRight.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 0.5f));//设置反对的权重
            ivLeft.setBackgroundColor(left_color);
            ivRight.setBackgroundColor(right_color);
            tvLeft.setBackgroundColor(left_color);
            tvRight.setBackgroundColor(right_color);
            return;
        }
        float leftProportion = (float) leftNum / (leftNum + rightNum);

        ivLeft.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, leftProportion));//设置赞成的权重
        ivRight.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1 - leftProportion));//设置反对的权重

        if (leftNum == 0 && rightNum > 0) {
            tvLeft.setBackgroundColor(right_color);
            tvRight.setBackgroundColor(right_color);

            ivLeft.setBackgroundColor(right_color);
            ivRight.setBackgroundColor(right_color);
            return;
        }
        if (rightNum == 0 && leftNum > 0) {
            tvLeft.setBackgroundColor(left_color);
            tvRight.setBackgroundColor(left_color);

            ivLeft.setBackgroundColor(left_color);
            ivRight.setBackgroundColor(left_color);
            return;
        }
        tvLeft.setBackgroundColor(left_color);
        tvRight.setBackgroundColor(right_color);

        ivLeft.setBackgroundColor(left_color);
        ivRight.setBackgroundColor(right_color);

    }
}
