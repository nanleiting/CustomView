package com.martin.customview.emojiView;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

import com.martin.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Martin Nan on 2019/6/18
 */
public class LikeTextView extends AppCompatTextView {
    private @DrawableRes
    int[] numberDrawableArray,levelDrawableArray;
    private int clickCount = 0;

    public LikeTextView(Context context) {
        this(context, null);
    }

    public LikeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        numberDrawableArray = new int[]{R.mipmap.multi_digg_num_0, R.mipmap.multi_digg_num_1, R.mipmap.multi_digg_num_2, R.mipmap.multi_digg_num_3,
                R.mipmap.multi_digg_num_4, R.mipmap.multi_digg_num_5, R.mipmap.multi_digg_num_6, R.mipmap.multi_digg_num_7,
                R.mipmap.multi_digg_num_8, R.mipmap.multi_digg_num_9};
        levelDrawableArray =new int[]{R.mipmap.multi_digg_word_level_1, R.mipmap.multi_digg_word_level_2, R.mipmap.multi_digg_word_level_3};

    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
        postInvalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(clickCount==0){
            return;
        }

        List<BitmapData> bitmaps = getNumberBitmap(clickCount);
        int textWidth=0;
        int textHeight=0;
        for (BitmapData bitmapData : bitmaps) {
            textWidth+=bitmapData.getBitmap().getWidth();
            if(textHeight<bitmapData.getBitmap().getHeight()){
                textHeight=bitmapData.getBitmap().getHeight();
            }
        }
        int canvasWidth = canvas.getWidth()/2;
        int canvasHeight = canvas.getHeight()/2;
        for (BitmapData bitmapData : bitmaps) {
            if (bitmapData != null) {
                Log.d("nanleiting","---clickCount--->"+clickCount+"--bitmaps->"+bitmaps.size());
                canvas.drawBitmap(bitmapData.getBitmap(), canvasWidth-(textWidth/2)+bitmapData.getX(), canvasHeight-(textHeight/2)-bitmapData.getY(), null);
            }
        }

    }

    private List<BitmapData> getNumberBitmap(int clickCount) {
        List<BitmapData> bitmaps = new ArrayList<>();
        String clickCountStr = clickCount + "";
        int offset_x = 0;
        int offset_y = 0;
        char[] chars = clickCountStr.toCharArray();
        if (chars != null && chars.length > 0) {
            for (int i = 0; i < chars.length; i++) {
                int index = Integer.parseInt(chars[i] + "");
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), numberDrawableArray[index]);
                if (i > 0) {
                    offset_x += bitmap.getWidth();
                    offset_y += 10;
                }
                BitmapData bitmapData = new BitmapData(bitmap, offset_x,offset_y);
                bitmaps.add(bitmapData);
            }
        }
        int level = clickCount /10;
        if(level > 2){
            level = 2;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), levelDrawableArray[level]);
//        offset_x += bitmap.getWidth();
        BitmapData bitmapData = new BitmapData(bitmap, offset_x+80,offset_y+20);
        bitmaps.add(bitmapData);
        return bitmaps;
    }

    class BitmapData {
        private Bitmap bitmap;
        private int x;
        private int y;

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public BitmapData(Bitmap bitmap, int x, int y) {
            this.bitmap = bitmap;
            this.x = x;
            this.y = y;
        }
    }

    public void setNumberDrawableArray(int[] numberDrawableArray) {
        this.numberDrawableArray = numberDrawableArray;
    }

    public void setLevelDrawableArray(int[] levelDrawableArray) {
        this.levelDrawableArray = levelDrawableArray;
    }
}
