package com.martin.customview.pkview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.martin.customview.R;

/**
 * author：Martin Nan on 2019/6/24
 */
public class CompetitionView extends RelativeLayout {
    private int leftTextSize;
    private int leftPercentTextSize;
    private int rightTextSize;
    private int rightPercentTextSize;
    private Context mContext;
    private boolean canClick;//是否可以点击
    private int leftNum;
    private int rightNum;
    private int leftColorStart;
    private int leftColorEnd;
    private int rightColorStart;
    private int rightColorEnd;
    private int leftTextColor;
    private int leftPercentColor;
    private int rightTextColor;
    private int rightPercentColor;

    private String leftStr;
    private String rightStr;
    private TextView btnLeft, btnRight;
    private TextView tvLeft, tvRight;
    private CompetitionBackView competitionBackView;
    private OnClickListeners onClickListeners;
//    private TimeCountTextView timeCountTextView;

    public void setOnClickListeners(OnClickListeners onClickListeners) {
        this.onClickListeners = onClickListeners;
    }

    public CompetitionView(Context context) {
        this(context, null);
    }

    public CompetitionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompetitionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CompetitionView, 0, 0);
        canClick = ta.getBoolean(R.styleable.CompetitionView_can_click, false);
        leftNum = ta.getInteger(R.styleable.CompetitionView_left_num_c, 0);
        rightNum = ta.getInteger(R.styleable.CompetitionView_right_num_c, 0);
        leftStr = ta.getString(R.styleable.CompetitionView_left_str);
        rightStr = ta.getString(R.styleable.CompetitionView_right_str);
        leftColorStart = ta.getColor(R.styleable.CompetitionView_left_color_start, Color.parseColor("#CC3232"));
        leftColorEnd = ta.getColor(R.styleable.CompetitionView_left_color_end, Color.parseColor("#0fFF8F8F"));
        rightColorStart = ta.getColor(R.styleable.CompetitionView_right_color_start, Color.parseColor("#3DA3FD"));
        rightColorEnd = ta.getColor(R.styleable.CompetitionView_right_color_end, Color.parseColor("#8CEBFF"));
        leftTextColor = ta.getColor(R.styleable.CompetitionView_left_text_color, Color.parseColor("#FFFFFF"));
        leftPercentColor = ta.getColor(R.styleable.CompetitionView_left_percent_text_color, Color.parseColor("#FFFFFF"));
        rightTextColor = ta.getColor(R.styleable.CompetitionView_right_text_color, Color.parseColor("#FFFFFF"));
        rightPercentColor = ta.getColor(R.styleable.CompetitionView_right_percent_text_color, Color.parseColor("#FFFFFF"));
        rightTextSize = (int) ta.getDimension(R.styleable.CompetitionView_right_text_size, 30);
        rightPercentTextSize = (int) ta.getDimension(R.styleable.CompetitionView_right_percent_text_size, 30);
        leftTextSize = (int) ta.getDimension(R.styleable.CompetitionView_left_text_size, 30);
        leftPercentTextSize = (int) ta.getDimension(R.styleable.CompetitionView_left_percent_text_size, 30);
        initView();
        ta.recycle();
    }

    private void initView() {
        if (isInEditMode())
            return;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_competition_view, this);
        btnLeft = view.findViewById(R.id.btn_left);
        btnRight = view.findViewById(R.id.btn_right);
        tvLeft = view.findViewById(R.id.tv_left);
        tvRight = view.findViewById(R.id.tv_right);
        competitionBackView = view.findViewById(R.id.competition_back_view);
        competitionBackView.setLeftColorStart(leftColorStart);
        competitionBackView.setLeftColorEnd(leftColorEnd);
        competitionBackView.setRightColorStart(rightColorStart);
        competitionBackView.setRightColorEnd(rightColorEnd);
        onChoose(leftNum, rightNum);
        tvLeft.setTextColor(leftPercentColor);
        tvRight.setTextColor(rightPercentColor);
        btnLeft.setTextColor(leftTextColor);
        btnRight.setTextColor(rightTextColor);

        tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftPercentTextSize);
        tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightPercentTextSize);
        btnLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
        btnRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);

        btnRight.setText(rightStr);
        btnLeft.setText(leftStr);
        if (canClick) {
            btnLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListeners != null) {
                        onClickListeners.onClickLeft();
                    }
//                    leftNum = leftNum + 1;
//                    onChoose(leftNum, rightNum);
                }
            });
            btnRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListeners != null) {
                        onClickListeners.onClickRight();
                    }
//                    rightNum = rightNum + 1;
//                    onChoose(leftNum, rightNum);
                }
            });
        }
    }

    public void onChoose(int leftNum, int rightNum) {
        this.leftNum = leftNum;
        this.rightNum = rightNum;
        competitionBackView.setNumberLeftRight(leftNum, rightNum);
        if (leftNum == 0 && rightNum == 0) {
            tvLeft.setText("");
            tvRight.setText("");
        } else {
            float leftPercentF = leftNum * 100f / (rightNum + leftNum);
            int leftPercent = (int) Math.rint(leftPercentF);
            float rightPercentF = rightNum * 100f / (rightNum + leftNum);
            int rightPercent = (int) Math.rint(rightPercentF);

            if (leftPercent == 0 && leftPercentF != 0f) {
                leftPercent = 1;
            }
            if (leftPercent == 100 && leftPercentF != 100f) {
                leftPercent = 99;
            }
            if (rightPercent == 0 && rightPercentF != 0) {
                rightPercent = 1;
            }
            if (rightPercent == 100 && rightPercentF != 100f) {
                rightPercent = 99;
            }

            tvLeft.setText(leftPercent + "%");
            tvRight.setText(rightPercent + "%");
        }
    }

    public void setLeftAndRightStr(String leftStr, String rightStr) {
        btnLeft.setText(leftStr);
        btnRight.setText(rightStr);
    }

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }

    public interface OnClickListeners {
        void onClickLeft();

        void onClickRight();
    }

    public void setLeftTextSize(int leftTextSize) {
        this.leftTextSize = leftTextSize;
        btnLeft.setTextSize(leftTextSize);
    }

    public void setLeftPercentTextSize(int leftPercentTextSize) {
        this.leftPercentTextSize = leftPercentTextSize;
        tvLeft.setTextSize(leftPercentTextSize);
    }

    public void setRightTextSize(int rightTextSize) {
        this.rightTextSize = rightTextSize;
       btnRight.setTextSize(rightTextSize);
    }

    public void setRightPercentTextSize(int rightPercentTextSize) {
        this.rightPercentTextSize = rightPercentTextSize;
        tvRight.setTextSize(rightPercentTextSize);
    }

    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
        btnLeft.setTextColor(leftTextColor);
    }

    public void setLeftPercentColor(int leftPercentColor) {
        this.leftPercentColor = leftPercentColor;
        tvLeft.setTextColor(leftPercentColor);
    }

    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
        btnRight.setTextColor(rightTextColor);
    }

    public void setRightPercentColor(int rightPercentColor) {
        this.rightPercentColor = rightPercentColor;
        tvRight.setTextColor(rightPercentColor);
    }
}
