package com.kode.fractalsv2.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class FractalImageView extends AppCompatImageView {
    
    static final float TOUCH_TOLERANCE = 1.0f;
    Paint  paint;
    Bitmap mBitmap;
    Canvas mCanvas;
    Path   orig;
    Path   mirr;
    float  mx1, my1;
    int flag = 0;
    private Button button1;
    private float  midy;
    private float  midx;
    private float  my2;
    private float  mx2;
    private float  disx;
    private float  disy;
    
    public FractalImageView(Context context) {
        super(context);
    }
    
    public FractalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public FractalImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public Button getButton1() {
        return button1;
    }
    
    public void setButton1(Button button1) {
        this.button1 = button1;
        button1.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                initialize();
                invalidate();
            }
        });
    }
    
    private void initialize() {
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(3f);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setDither(true);
        orig = new Path();
        mirr = new Path();
        midx = getWidth() / 2;
        midy = getHeight() / 2;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        if (flag == 0) {//initialize the paint objects once in draw
            initialize();
            flag = 1;
        }
        canvas.drawBitmap(mBitmap, 0, 0, paint);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }
    
    private void touch_start(float x, float y) {
        Random rand = new Random();
        paint.setColor(Color.rgb(rand.nextInt((255 - 0) + 1) + 0,//random red
                                 rand.nextInt((255 - 0) + 1) + 0,//random green
                                 rand.nextInt((255 - 0) + 1) + 0));//random blue
        disx = midx - x;
        disy = midy - y;
        mx2 = disx + midx;
        my2 = y;
        orig.reset();
        mirr.reset();
        orig.moveTo(x, y);
        mirr.moveTo(mx2, my2);
        mx1 = x;
        my1 = y;
        
    }
    
    private void touch_move(float x, float y) {
        
        float dx = Math.abs(x - mx1);
        float dy = Math.abs(y - my1);
        disx = midx - x;
        disy = midy - y;
        float currmx = midx + disx;
        float currmy = y;
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            orig.quadTo(mx1, my1, (x + mx1) / 2, (y + my1) / 2);
            mirr.quadTo(mx2, my2, ((currmx) + mx2) / 2, ((currmy) + my2) / 2);
            mx2 = currmx;
            my2 = currmy;
            mx1 = x;
            my1 = y;
            // orig.lineTo(mx1, my1);
            // mirr.lineTo(mx2, my2);
            mCanvas.save();
            for (int i = 0; i < 8; i++) {
                mCanvas.rotate(45, getWidth() / 2, getHeight() / 2);
                mCanvas.drawPath(orig, paint);
                mCanvas.drawPath(mirr, paint);
            }
        }
        
    }
    
    private void touch_up() {
        mCanvas.restore();
        orig.reset();
        mirr.reset();
    }
}
