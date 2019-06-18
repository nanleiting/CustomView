package com.martin.nanle.pkview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.martin.customview.MyGiftView;

public class MyGiftViewActivity extends AppCompatActivity {
    private MyGiftView giftView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gift_view);
        giftView =findViewById(R.id.giftview);
    }


    public void like(View view) {
        giftView.addImageView();
    }
}
