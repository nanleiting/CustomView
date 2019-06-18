package com.martin.nanle.pkview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.martin.customview.MyGiftView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void select(View view) {
        startActivity(new Intent(this, SelectSeatActivity.class));
    }

    public void circleBar(View view) {
        startActivity(new Intent(this, CircleBarActivity.class));
    }

    public void pkview(View view) {
        startActivity(new Intent(this, PkViewActivity.class));
    }

    public void addEmoJiView(View view) {
        startActivity(new Intent(this, AddEmoJiActivity.class));
    }

    public void superLikeLayout(View view) {
        startActivity(new Intent(this, SuperLikeLayoutActivity.class));
    }

    public void myGiftView(View view) {
        startActivity(new Intent(this, MyGiftViewActivity.class));
    }
}
