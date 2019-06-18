package com.martin.nanle.pkview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.martin.customview.CircleBar;

public class CircleBarActivity extends AppCompatActivity {
    private CircleBar circleBarH;
    private CircleBar circleBarM;
    private CircleBar circleBarS;
    private TextView tvContentH;
    private TextView tvContentM;
    private TextView tvContentS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_bar);
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
    }
}
