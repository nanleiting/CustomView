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

public class SuperLikeLayoutActivity extends AppCompatActivity {
    private SuperLikeLayout superLikeLayout;
    private Button btn3;
    private Handler handler;
    boolean isLongPress = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_like_layout);
        superLikeLayout = findViewById(R.id.super_like_layout);
        superLikeLayout.setProvider(BitmapProviderFactory.getHDProvider(this));
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
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                showAnimator(btn3);
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
    private void showAnimator(View v) {
        int x = (int)(v.getX() + v.getWidth() / 2);
        int y = (int)(v.getY() + v.getHeight() / 2);
        superLikeLayout.launch(x, y);
    }

}
