package com.ursus.core;

/**
 * Created by Vlastimil Brečka on 28.10.2015.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 06-May-17.
 */
public class ForegroundLinearLayout extends LinearLayout {

    private static final boolean IS_LEGACY = Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1;
    private Drawable mForeground;

    public ForegroundLinearLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ForegroundLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ForegroundLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (IS_LEGACY) {
            if (attrs != null) {
                TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ForegroundView);
                try {
                    Drawable d = a.getDrawable(R.styleable.ForegroundView_android_foreground);
                    if (d != null) {
                        setForegroundCompat(d);
                    }
                } finally {
                    a.recycle();
                }
            }
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        if (!IS_LEGACY)
            return super.verifyDrawable(who);
        else
            return super.verifyDrawable(who) || (who == mForeground);
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (IS_LEGACY) {
            if (mForeground != null) mForeground.jumpToCurrentState();
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (IS_LEGACY) {
            if (mForeground != null && mForeground.isStateful()) {
                mForeground.setState(getDrawableState());
            }
        }
    }

    public void setForegroundCompat(Drawable drawable) {
        if (!IS_LEGACY) {
            setForeground(drawable);

        } else {
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
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (IS_LEGACY) {
            if (mForeground != null) {
                mForeground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
                invalidate();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (IS_LEGACY) {
            if (mForeground != null) {
                mForeground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
                invalidate();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (IS_LEGACY) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (mForeground != null) {
                    mForeground.setHotspot(x, y);
                }
            }
        }
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        if (IS_LEGACY) {
            if (mForeground != null) {
                mForeground.draw(canvas);
            }
        }
    }
}
