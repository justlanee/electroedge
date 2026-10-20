package com.example.project1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ProgressCircleView extends View {

    private Paint circlePaint;
    private Paint progressPaint;
    private Paint textPaint;
    private Paint checkmarkPaint;

    private int progress = 0;
    private boolean showCheck = false;

    private RectF oval;

    public ProgressCircleView(Context context) {
        super(context);
        init();
    }

    public ProgressCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        circlePaint = new Paint();
        circlePaint.setColor(0xFFDDDDDD);
        circlePaint.setStrokeWidth(14f);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);

        progressPaint = new Paint();
        progressPaint.setColor(0xFFB71C1C); // deep red
        progressPaint.setStrokeWidth(14f);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setColor(0xFFB71C1C);
        textPaint.setTextSize(32f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);

        checkmarkPaint = new Paint();
        checkmarkPaint.setColor(0xFF2E7D32); // green checkmark
        checkmarkPaint.setStrokeWidth(10f);
        checkmarkPaint.setStyle(Paint.Style.STROKE);
        checkmarkPaint.setAntiAlias(true);
        checkmarkPaint.setStrokeCap(Paint.Cap.ROUND);

        oval = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float padding = 20f;
        float size = Math.min(getWidth(), getHeight()) - padding;
        float left = (getWidth() - size) / 2;
        float top = (getHeight() - size) / 2;

        oval.set(left, top, left + size, top + size);

        // Background circle
        canvas.drawArc(oval, 0, 360, false, circlePaint);

        if (showCheck) {
            //  Draw checkmark
            float cx = getWidth() / 2f;
            float cy = getHeight() / 2f;

            float startX = cx - size * 0.20f;
            float startY = cy + size * 0.05f;

            float midX = cx - size * 0.05f;
            float midY = cy + size * 0.20f;

            float endX = cx + size * 0.25f;
            float endY = cy - size * 0.15f;

            canvas.drawLine(startX, startY, midX, midY, checkmarkPaint);
            canvas.drawLine(midX, midY, endX, endY, checkmarkPaint);

        } else {
            //  Draw progress arc
            float sweepAngle = (progress / 100f) * 360f;
            canvas.drawArc(oval, -90, sweepAngle, false, progressPaint);

            // Draw % text inside
            String text = progress + "%";
            float x = getWidth() / 2f;
            float y = (getHeight() / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2);
            canvas.drawText(text, x, y, textPaint);
        }
    }

    //  Animate & update progress
    public void setProgress(int p) {
        showCheck = false;
        progress = Math.min(100, Math.max(0, p));
        invalidate();
    }

    // Show checkmark & remove text
    public void showCheckmark() {
        showCheck = true;
        invalidate();
    }
}
