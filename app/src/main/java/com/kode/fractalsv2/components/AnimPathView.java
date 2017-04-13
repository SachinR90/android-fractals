package com.kode.fractalsv2.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

public class AnimPathView extends View implements View.OnClickListener {

    private static final long animSpeedInMs        = 1000;
    private static final long animMsBetweenStrokes = 200;
    private Path        path;
    private Paint       paint;
    private long        animLastUpdate;
    private boolean     animRunning;
    private float       animCurrentPos;
    private Path        animPath;
    private PathMeasure animPathMeasure;

    public AnimPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDisplayView();
    }

    public AnimPathView(Context context) {
        super(context);
        initDisplayView();
    }

    private void initDisplayView() {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(0xff336699);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(2);

        path = new Path();
        animRunning = false;

        this.setOnClickListener(this);
    }

    public void setPath(Path p) {
        path = p;
    }

    @Override
    public void onClick(View v) {
        startAnimation();
    }

    public void startAnimation() {
        animRunning = true;
        animPathMeasure = null;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (animRunning) {
            drawAnimation(canvas);
        } else {
            drawStatic(canvas);
        }
    }

    private void drawAnimation(Canvas canvas) {
        if (animPathMeasure == null) {
            // Start of animation. Set it up.
            animPathMeasure = new PathMeasure(path, false);
            animPathMeasure.nextContour();
            animPath = new Path();
            animLastUpdate = System.currentTimeMillis();
            animCurrentPos = 0.0f;
        } else {
            // Get time since last frame
            long now = System.currentTimeMillis();
            long timeSinceLast = now - animLastUpdate;

            if (animCurrentPos == 0.0f) {
                timeSinceLast -= animMsBetweenStrokes;
            }

            if (timeSinceLast > 0) {
                // Get next segment of path
                float newPos = (float) (timeSinceLast) / animSpeedInMs + animCurrentPos;
                boolean moveTo = (animCurrentPos == 0.0f);
                animPathMeasure.getSegment(animCurrentPos, newPos, animPath, moveTo);
                animCurrentPos = newPos;
                animLastUpdate = now;

                // If this stroke is done, move on to next
                if (newPos > animPathMeasure.getLength()) {
                    animCurrentPos = 0.0f;
                    boolean more = animPathMeasure.nextContour();
                    // Check if finished
                    if (!more) {
                        animRunning = false;
                    }
                }
            }

            // Draw path
            canvas.drawPath(animPath, paint);
        }

        invalidate();
    }

    private void drawStatic(Canvas canvas) {
        canvas.drawPath(path, paint);
    }
}