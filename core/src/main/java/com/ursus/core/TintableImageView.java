package com.ursus.core;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 06-May-17.
 */
public class TintableImageView extends ImageView {

    private Drawable mForeground;
    private int mTintColor;

    public TintableImageView(Context context) {
        super(context);
        init(context, null);
    }

    public TintableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TintableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TintableView);
            try {
                int tintColor = a.getColor(R.styleable.TintableView_tintColor, 0xFF000000);
                setTintColor(tintColor);
                mTintColor = tintColor;
            } finally {
                a.recycle();
            }

            TypedArray a2 = context.obtainStyledAttributes(attrs, R.styleable.ForegroundView);
            try {
                Drawable d = a2.getDrawable(R.styleable.ForegroundView_android_foreground);
                if (d != null) {
                    setForegroundd(d);
                }
            } finally {
                a2.recycle();
            }
        }
    }

    @Override
    public void setImageResource(int resId) {
        boolean hasImage = getDrawable() != null; // Tu by to chcelo lepsie ci je zmena drawably
        super.setImageResource(resId);
        setTintColor(mTintColor, !hasImage);
    }

    public void setTintColor(@ColorInt int color) {
        setTintColor(color, false);
    }

    public void setTintColor(@ColorInt int color, boolean force) {
        if (force || mTintColor != color) {
            setImageDrawable(UiUtils.setTint(getDrawable(), color));
            mTintColor = color;
            invalidate();
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || (who == mForeground);
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (mForeground != null) mForeground.jumpToCurrentState();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mForeground != null && mForeground.isStateful()) {
            mForeground.setState(getDrawableState());
        }
    }

    public void setForegroundd(Drawable drawable) {
        if (mForeground != drawable) {
            if (mForeground != null) {
                mForeground.setCallback(null);
                unscheduleDrawable(mForeground);
            }

            mForeground = drawable;

            if (drawable != null) {
                setWillNotDraw(false);
                drawable.setCallback(this);
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
            } else {
                setWillNotDraw(true);
            }
            requestLayout();
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mForeground != null) {
            mForeground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mForeground != null) {
            mForeground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            invalidate();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mForeground != null) {
                mForeground.setHotspot(x, y);
            }
        }
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);

        if (mForeground != null) {
            mForeground.draw(canvas);
        }
    }

}
