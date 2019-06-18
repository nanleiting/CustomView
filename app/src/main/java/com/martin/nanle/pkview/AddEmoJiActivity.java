package com.martin.nanle.pkview;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.martin.customview.GiveALikeView.SuperLikeLayout;
import com.martin.customview.emojiView.AcodeEmojiView;

@SuppressLint("ClickableViewAccessibility")
public class AddEmoJiActivity extends AppCompatActivity {
    private Button btn3;
    private AcodeEmojiView view_point;
    private Handler handler;
    boolean isLongPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emo_ji);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                view_point.addImageView(btn3);
                return false;
            }
        });


        view_point = findViewById(R.id.view_point);
        view_point.setIconArray(new int[]{R.mipmap.emoji_1, R.mipmap.emoji_2, R.mipmap.emoji_3, R.mipmap.emoji_4, R.mipmap.emoji_5, R.mipmap.emoji_6,
                R.mipmap.emoji_7, R.mipmap.emoji_8, R.mipmap.emoji_9, R.mipmap.emoji_10, R.mipmap.emoji_11, R.mipmap.emoji_12, R.mipmap.emoji_13,
                R.mipmap.emoji_14, R.mipmap.emoji_15, R.mipmap.emoji_16, R.mipmap.emoji_17, R.mipmap.emoji_18, R.mipmap.emoji_19, R.mipmap.emoji_20}, new int[]{R.mipmap.multi_digg_num_0, R.mipmap.multi_digg_num_1, R.mipmap.multi_digg_num_2, R.mipmap.multi_digg_num_3,
                R.mipmap.multi_digg_num_4, R.mipmap.multi_digg_num_5, R.mipmap.multi_digg_num_6, R.mipmap.multi_digg_num_7,
                R.mipmap.multi_digg_num_8, R.mipmap.multi_digg_num_9}, new int[]{R.mipmap.multi_digg_word_level_1, R.mipmap.multi_digg_word_level_2, R.mipmap.multi_digg_word_level_3});
        btn3 = findViewById(R.id.btn3);
        btn3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isLongPress = true;
                        Thread thread = new Thread(new MyThread());
                        thread.start();
                        break;
                    case MotionEvent.ACTION_UP:
                        isLongPress = false;
                        break;
                }
                return false;
            }
        });
    }

    class MyThread implements Runnable {
        public void run() {
            while (!Thread.currentThread().isInterrupted() && isLongPress) {
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
