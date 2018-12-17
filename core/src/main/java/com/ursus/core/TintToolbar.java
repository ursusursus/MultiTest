package com.ursus.core;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 06-May-17.
 */
public class TintToolbar extends Toolbar {

    private static final long SLIDE_DURATION = 200;
    private static final Interpolator sInterpolator = new DecelerateInterpolator();
    private OnMenuInvalidatedListener mMenuInvalidatedListener;

    public interface OnMenuInvalidatedListener {

        void onMenuInvalidated(Menu menu);
    }

    public TintToolbar(Context context) {
        super(context);
    }

    public TintToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TintToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isShowing() {
        return getTranslationY() >= 0;
    }

    public void show() {
        setVisibility(VISIBLE);
        animate().translationY(0)
                .alpha(1)
                .setDuration(SLIDE_DURATION)
                .setInterpolator(sInterpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override public void onAnimationEnd(Animator animation) {
                        setVisibility(VISIBLE);
                    }
                });
    }

    public void hide() {
        setVisibility(VISIBLE);
        animate().translationY(-getMeasuredHeight())
                .alpha(0)
                .setDuration(SLIDE_DURATION)
                .setInterpolator(sInterpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override public void onAnimationEnd(Animator animation) {
                        setVisibility(GONE);
                    }
                });
    }

    public void showImmediate() {
        setTranslationY(0);
        setAlpha(1);
        setVisibility(View.VISIBLE);
    }

    private void doHideImmediate() {
        setTranslationY(-getMeasuredHeight());
        setAlpha(0);
        setVisibility(GONE);
    }

    @Override
    public void setNavigationIcon(@Nullable Drawable icon) {
        super.setNavigationIcon(UiUtils.setTint(icon, UiUtils.getColorControlNormal(getContext())));
    }

    @Override
    public void inflateMenu(int resId) {
        super.inflateMenu(resId);

        final Menu menu = getMenu();
        if (mMenuInvalidatedListener != null) {
            mMenuInvalidatedListener.onMenuInvalidated(menu);
        }

        colorMenu(menu, UiUtils.getColorControlNormal(getContext()));
    }

    public void setOnMenuInvalidatedListener(OnMenuInvalidatedListener listener) {
        mMenuInvalidatedListener = listener;
    }

    public void invalidateMenu() {
        if (mMenuInvalidatedListener != null) {
            mMenuInvalidatedListener.onMenuInvalidated(getMenu());
        }
        colorMenu(getMenu(), UiUtils.getColorControlNormal(getContext()));
    }

    private void colorMenu(Menu menu, int color) {
        for (int i = 0, size = menu.size(); i < size; i++) {
            final MenuItem menuItem = menu.getItem(i);
            final Drawable d = menuItem.getIcon();
            if (d != null) {
                menuItem.setIcon(UiUtils.setTint(d, color));
            }
        }
    }

}
