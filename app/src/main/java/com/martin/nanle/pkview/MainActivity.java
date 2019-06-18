package com.martin.nanle.pkview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.martin.customview.emojiView.AcodeEmojiView;
import com.martin.customview.CircleBar;
import com.martin.customview.GiveALikeView.SuperLikeLayout;
import com.martin.customview.MyGiftView;
import com.martin.customview.SeatTable;

public class MainActivity extends AppCompatActivity {
    private CircleBar circleBarH;
    private CircleBar circleBarM;
    private CircleBar circleBarS;
    private TextView tvContentH;
    private TextView tvContentM;
    private TextView tvContentS;
    public SeatTable seatTableView;
    private Button btn3;
    private AcodeEmojiView view_point;
    boolean isLongPress = false;
    private Handler handler;
    private SuperLikeLayout superLikeLayout;
    private MyGiftView giftView;
    @SuppressLint({"ClickableViewAccessibility", "HandlerLeak"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleBarH = findViewById(R.id.circle_bar_h);
        circleBarH.start();
        circleBarM = findViewById(R.id.circle_bar_m);
        circleBarM.start();
        circleBarS = findViewById(R.id.circle_bar_s);
        circleBarS.start();
        tvContentH = findViewById(R.id.tv_content_h);
        tvContentM = findViewById(R.id.tv_content_m);
        tvContentS = findViewById(R.id.tv_content_s);

        circleBarH.setStopListener(new CircleBar.StopListener() {
            @Override
            public void onStopListener() {
                tvContentH.setText("结束");
            }
        });
        circleBarM.setStopListener(new CircleBar.StopListener() {
            @Override
            public void onStopListener() {
                tvContentM.setText("结束");
            }
        });
        circleBarS.setStopListener(new CircleBar.StopListener() {
            @Override
            public void onStopListener() {
                circleBarS.start();
                tvContentS.setText("结束");
            }
        });

        superLikeLayout = findViewById(R.id.super_like_layout);
        superLikeLayout.setProvider(BitmapProviderFactory.getHDProvider(this));




        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                view_point.addImageView(btn3);

//                showAnimator(btn3);
            }
        };
        seatTableView = (SeatTable) findViewById(R.id.seatView);
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (row == 0) {//设置第二排不可用
                    if (column == 5) {
                        return true;
                    } else {
                        return false;
                    }

                }
                if (column == 0) {//设置第二排不可用
                    if (row == 5) {
                        return true;
                    } else {
                        return false;
                    }

                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
//                if(row==6&&column==6){
//                    return true;
//                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(11, 11);

        view_point =  findViewById(R.id.view_point);
        view_point.setIconArray(new int[]{R.mipmap.emoji_1, R.mipmap.emoji_2, R.mipmap.emoji_3, R.mipmap.emoji_4, R.mipmap.emoji_5, R.mipmap.emoji_6,
                R.mipmap.emoji_7, R.mipmap.emoji_8, R.mipmap.emoji_9, R.mipmap.emoji_10, R.mipmap.emoji_11, R.mipmap.emoji_12, R.mipmap.emoji_13,
                R.mipmap.emoji_14, R.mipmap.emoji_15, R.mipmap.emoji_16, R.mipmap.emoji_17, R.mipmap.emoji_18, R.mipmap.emoji_19, R.mipmap.emoji_20},new int[]{R.mipmap.multi_digg_num_0, R.mipmap.multi_digg_num_1, R.mipmap.multi_digg_num_2, R.mipmap.multi_digg_num_3,
                R.mipmap.multi_digg_num_4, R.mipmap.multi_digg_num_5, R.mipmap.multi_digg_num_6, R.mipmap.multi_digg_num_7,
                R.mipmap.multi_digg_num_8, R.mipmap.multi_digg_num_9},new int[]{R.mipmap.multi_digg_word_level_1, R.mipmap.multi_digg_word_level_2, R.mipmap.multi_digg_word_level_3});
        btn3 = findViewById(R.id.btn3);
        btn3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isLongPress=true;
                        Thread thread =new Thread(new MyThread());
                        thread.start();
                        break;
                    case MotionEvent.ACTION_UP:
                        isLongPress=false;
                        break;
                }
                return false;
            }
        });


//        btn3.setOnClickListener(new View.OnClickListener() {
//            long duration = 2000;
//            long lastClickTime;
//            @Override
//            public void onClick(View v) {
//                if(System.currentTimeMillis() - lastClickTime > duration){ // 防抖
//                    v.setSelected(!v.isSelected());
//                }
//                lastClickTime = System.currentTimeMillis();
//                if(v.isSelected()){
//                    int x = (int)(v.getX() + v.getWidth() / 2);
//                    int y = (int)(v.getY() + v.getHeight() / 2);
//                    superLikeLayout.launch(x, y);
//                }
//
//            }
//        });
        giftView =findViewById(R.id.giftview);
    }

    private void showAnimator(View v) {
        int x = (int)(v.getX() + v.getWidth() / 2);
        int y = (int)(v.getY() + v.getHeight() / 2);
        superLikeLayout.launch(x, y);
    }

    public void like(View view) {
        giftView.addImageView();
    }

    class MyThread implements Runnable {
        public void run() {
            while (!Thread.currentThread().isInterrupted() && isLongPress) {
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}