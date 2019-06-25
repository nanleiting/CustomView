package com.martin.nanle.pkview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.martin.customview.GiveALikeView.SuperLikeLayout;

public class PkViewActivity extends AppCompatActivity {

    @SuppressLint({"ClickableViewAccessibility", "HandlerLeak"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        init();
    }


//    private void init() {
//        LinearLayout layout=(LinearLayout) findViewById(R.id.root);
//        final DrawView view=new DrawView(this);
//        view.setMinimumHeight(1500);
//        view.setMinimumWidth(1300);
//        //通知view组件重绘
//        view.invalidate();
//        layout.addView(view);
//
//    }
}