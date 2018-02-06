package com.codedao.menuonline.Guest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 02/02/2018.
 */

public class PointsOverlayView extends View {
    private PointF[] mPoints;
    private Paint mPaint;

    public PointsOverlayView(Context context) {
        super(context);
        init();
    }

    public PointsOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointsOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setmPoints(PointF[] mPoints) {
        this.mPoints = mPoints;
        invalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mPoints != null) {
            for (PointF pointF : mPoints) {
                canvas.drawCircle(pointF.x, pointF.y, 10, mPaint);
            }
        }
    }
}
