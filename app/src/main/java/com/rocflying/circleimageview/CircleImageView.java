package com.rocflying.circleimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by liupeng on 2019/8/3.
 */
public class CircleImageView extends AppCompatImageView {

    private Paint mPaint;
    private Paint borderPaint;
    private BitmapShader bitmapShader;
    private Bitmap bitmap;
    private Drawable drawable;
    private int mWidth;
    private int mHeight;
    private int shape = 0;
    private float radis = 24;
    private float borderWidth = 0;
    private boolean isHaveBorder = false;

    private final int CIRCLE = 0;
    private final int RECTANGL = 1;
    private final int ROUNRECTANGLE = 2;
    private String TAG = CircleImageView.class.getSimpleName();

    public CircleImageView(Context context) {
        super(context);

    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setFilterBitmap(true);

        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            drawable = array.getDrawable(R.styleable.CircleImageView_src);
            shape = array.getInteger(R.styleable.CircleImageView_shape, CIRCLE);
            borderWidth = array.getDimension(R.styleable.CircleImageView_borderWidth, 0);
            if (borderWidth > 0) {
                isHaveBorder = true;
            }

            if (shape == ROUNRECTANGLE) {
                radis = array.getDimension(R.styleable.CircleImageView_radis, 0);
            }
            borderPaint = new Paint();
            borderPaint.setColor(Color.RED);
            borderPaint.setStrokeWidth(borderWidth);
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setAntiAlias(true);

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = widthMeasureSpec;
        mHeight = heightMeasureSpec;
//
//        if (isHaveBorder) {
//            setMeasuredDimension((int) (widthMeasureSpec + borderWidth * 2), (int) (heightMeasureSpec + borderWidth * 2));
//        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (drawable != null) {
            mWidth = drawable.getIntrinsicWidth();
            mHeight = drawable.getIntrinsicHeight();
        } else {
            drawable = getDrawable();
            try {
                setImageDrawable(null);

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

        }

        bitmap = drawableToBitmap(drawable);
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        float scaleWidth = (float) mWidth / bitmap.getWidth();
        float scaleHeight = (float) mHeight / bitmap.getHeight();
        float scale = Math.max(scaleWidth, scaleHeight);
        matrix.setScale(scale, scale);
        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);
        int halfSize = getWidth() / 2;


        RectF rect = new RectF(0, 0, getWidth(), getWidth());

        switch (shape) {

            case CIRCLE:
                canvas.drawCircle(halfSize, halfSize, halfSize-borderWidth, mPaint);
                if (isHaveBorder) {
                    canvas.drawCircle(halfSize, halfSize, halfSize-borderWidth, borderPaint);
                }
                break;
            case RECTANGL:
                canvas.drawRect(new Rect(0, 0, getWidth(), getWidth()), mPaint);
                if (isHaveBorder) {
                    canvas.drawRect(new Rect(0, 0, getWidth(), getWidth()), borderPaint);
                }
                break;

            case ROUNRECTANGLE:
                canvas.drawRoundRect(rect, radis, radis, mPaint);
                if (isHaveBorder) {
                    canvas.drawRoundRect(rect, radis, radis, borderPaint);
                }
                break;

            default:
                break;


        }

    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri);
    }

    /**
     * drawable转bitmap
     */

    private Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (drawable != null) {
            Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, mWidth, mHeight);
            drawable.draw(canvas);
            return bitmap;
        }
        return null;
    }

}
