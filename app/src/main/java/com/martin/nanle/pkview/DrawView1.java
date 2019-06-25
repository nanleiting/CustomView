package com.martin.nanle.pkview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

/**
 * Created by leiting on 2017/11/2.
 *
 * 个人中心的 上面的 ImageView
 */

public class DrawView1 extends AppCompatImageView {
    float left1,top1,  right1,  bottom1;
    float left2, top2,  right2,  bottom2;
    float left3, top3, right3,  bottom3;

    float w1,w2,w3;//三个线条的长度
    float h;//线条的高度
    float textSize;
    /*private float circleX = 40;
    private float circleY = 50;
    private float circleR = 15;*/
    String sex,grade,organization;
int color_red = Color.parseColor("#ef7c7c");
    int color_blue = Color.parseColor("#50c6d1");
    int color_green = Color.parseColor("#99d757");
    private float circleX;
    private float circleY;
    private float BallW;
    private int cr ,add1,add2,add3;
    private int ballColor;

    Paint paint;
    public DrawView1(Context context) {
        this(context,null);
    }

    public DrawView1(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width3 = dm.widthPixels;

//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawView);
//        //获取开始的X的位置属性值
//        circleY = a.getDimension(R.styleable.DrawView_BallStartY,10);
//        BallW = a.getDimension(R.styleable.DrawView_BallW,10);
//        circleX=width3/2;
//         cr = (int) a.getDimension(R.styleable.DrawView_BallRadius,10);
//
//        add1= (int) a.getDimension(R.styleable.DrawView_add_1,0);
//        add2= (int) a.getDimension(R.styleable.DrawView_add_2,0);
//        add3= (int) a.getDimension(R.styleable.DrawView_add_3,0);
//        MyLogger.d("nameSize--","circleY----"+circleY+"--------BallW---"+BallW);
//        ballColor = a.getColor(R.styleable.DrawView_BallColor,0x990000FF);
//        sex=a.getString(R.styleable.DrawView_textSex);
//        grade=a.getString(R.styleable.DrawView_textGrade);
//        organization=a.getString(R.styleable.DrawView_textOrganization);
        paint = new Paint();
//        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        int add150=(int) getResources().getDimension(R.dimen.d150);
//        int add20=(int) getResources().getDimension(R.dimen.d20);
//        int add100=(int) getResources().getDimension(R.dimen.d100);
        h=cr/2;//条形高度
        w1=cr+add1;
        w2=cr+add2;
        w3=cr+add3;
        textSize =h/2;
        canvas.translate(circleX, circleY);
        paint.setColor(ballColor);
        canvas.drawCircle(0,0,cr,paint);        // 11111111111111111111画大球

        left1=0;
        top1=(-h-cr)/2;
        right1=w1;//长条的长度
        bottom1=(h-cr)/2;

        left2 =0;
        top2 =0;
        right2=w2;//长条的长度
        bottom2 =h;

        left3 =-w3;
        top3 =(-h+cr)/2;
        right3 =0;//长条的长度
        bottom3 =(h+cr)/2;
        //222222222222222画第一个长条
        draw1(canvas,left1,top1,right1,bottom1,paint);
        //3333333333333333333333画第二个长条
        draw2(canvas, left2, top2,right2, bottom2,paint);
        //4444444444
        draw3(canvas, left3, top3, right3, bottom3,paint);



        {
            canvas.translate(-circleX, -circleY);
            Drawable drawable = getDrawable();
            if (null != drawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                Bitmap b = getCircleBitmap(bitmap);
                int wh =(int)(2*cr);
                final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());//如果要全圆的话 必须是这样
                final Rect rectDest = new Rect((int)(circleX+BallW)-cr,(int)(circleY+BallW)-cr, (int)(circleX-BallW)+cr, (int)(circleY-BallW)+cr);
                paint.reset();
                canvas.drawBitmap(b, rectSrc, rectDest, paint);

            } else {
                super.onDraw(canvas);
            }
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        circleX = event.getX();
//        circleY = event.getY();
//        this.invalidate();
//        return true;
//    }



    /**
     * 获取圆形图片方法
     * @param bitmap

     * @return Bitmap
     * @author caizhiming
     */
    private Bitmap getCircleBitmap(Bitmap bitmap ) {
       int w = bitmap.getWidth();
        int h=  bitmap.getHeight();
        if(h>w){
            h=w;
        }else{
            w=h;
        }
        Bitmap output = Bitmap.createBitmap(w,
                h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Rect rect = new Rect(0, 0, w, h);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int x = w;

        canvas.drawCircle(x / 2, x / 2, x / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;


    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        requestLayout();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
        requestLayout();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    private void draw1(Canvas canvas,float left,  float top,  float right,  float bottom, Paint paint){
        RectF rect = new RectF(left,top,right,bottom);
        paint.setColor(color_red);
        paint.setAlpha(180);
        canvas.drawRect(rect,paint);// 开始画矩形区域
        RectF rect1shanxing = new RectF(right-(h/2),top,right+(h/2),bottom);
        canvas.drawArc(rect1shanxing, 270, 180, true,paint);
        /// 画 字
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);

        if(null!=organization&&!"".equals(organization)){
            if(organization.length()>5){
                organization= organization.substring(0,5)+"...";
            }
        }
        canvas.drawText(organization,cr,(h-cr)/2-(h-textSize)/2,paint);
    }
    private void draw2(Canvas canvas,float left,  float top,  float right,  float bottom, Paint paint){
        RectF rect = new RectF(left,top,right,bottom);
        paint.setColor(color_blue);
        paint.setAlpha(180);
        canvas.drawRect(rect,paint);  //画第二个矩形区域

        RectF rect2shanxing = new RectF(right-(h/2),top,right+(h/2),bottom);//画扇形
        canvas.drawArc(rect2shanxing, 270, 180, true,paint);
        /// 画 字
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText(sex,cr,bottom-(h-textSize)/2,paint);
    }
    private void draw3(Canvas canvas,float left,  float top,  float right,  float bottom, Paint paint){
        RectF rect= new RectF(left,top,right,bottom);
        paint.setColor(color_green);
        paint.setAlpha(180);
        canvas.drawRect(rect,paint);
        RectF rectshanxing = new RectF(left-(h/2),top,left+(h/2),bottom);
        canvas.drawArc(rectshanxing, 90, 180, true,paint);

        /// 画 字
        paint.setColor(Color.WHITE);
        paint.setTextSize(textSize);
        canvas.drawText(grade,left,bottom-(h-textSize)/2,paint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(x>circleX-cr&& x<circleX+cr){
                    if(y>circleY-cr&& y<circleY+cr){
                        mViewClick.onClickImage();
                    }
                }
                if(x>circleX+cr&& x<circleX+w2+h/2){
                    if(y>circleY&& y<circleY+h){
                        mViewClick.onClickSex();
                    }
                }
                break;
        }
        return true;
    }


    public void setOnViewClick(onViewClick click) {
        this.mViewClick = click;
    }

    onViewClick mViewClick;
    public interface onViewClick {

        void onClickSex();
        void onClickImage();

    }
}