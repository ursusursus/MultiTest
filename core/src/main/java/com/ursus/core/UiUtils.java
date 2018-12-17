package com.ursus.core;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * Created by Vlastimil Brečka (www.vlastimilbrecka.sk)
 * on 06-May-17.
 */
public class UiUtils {

    private static final int FADE_DURATION = 300;
    private static final int BOUNCE_DURATION = 250;
    private static OvershootInterpolator sOvershootInterpolator;

    public static int darkenColor(int color) {
        final float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.70F;

        return Color.HSVToColor(hsv);
    }

    public static int lightenColor(int color) {
        // Tu menim saturaciu, a nie brightness,
        // ako by som mal, ale neviem, s roznymi farbami
        // to sedi/nesedi
        final float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] *= 0.55;

        return Color.HSVToColor(hsv);
    }

    public static int alphaColor(int color, float ratio) {
        return Color.argb((int) (Color.alpha(color) * ratio), Color.red(color), Color.green(color), Color.blue(color));
    }

    public static Drawable setTint(Context context, @DrawableRes int drawableRes, @ColorInt int color) {
        return setTint(getDrawable(context, drawableRes), color);
    }

    public static Drawable setTint(Drawable drawable, @ColorInt int color) {
        if (drawable != null && color != 0) {
            drawable.mutate();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, color);
        }
        return drawable;
    }

    public static int getToolbarPixelSize(Context context) {
        final TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
    }

    public static int getStatusBarPixelSize(Resources res) {
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static float dpToPixels(Resources res, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

    public static int dpToPixelSize(Resources res, float dp) {
        return (int) dpToPixels(res, dp);
    }

    public static int getColor(Context context, @ColorRes int colorRes) {
        if (Utils.hasMarshmallow()) {
            return context.getResources().getColor(colorRes, context.getTheme());
        } else {
            return context.getResources().getColor(colorRes);
        }
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawableRes) {
        if (Utils.hasLollipop()) {
            return context.getResources().getDrawable(drawableRes, context.getTheme());
        } else {
            return context.getResources().getDrawable(drawableRes);
        }
    }

    public static int getColorControlNormal(Context context) {
        return getAttr(context, R.attr.colorControlNormal);
    }

    public static int getAttr(Context context, @AttrRes int attrRes) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, value, true);
        return value.data;
    }

    public static void crossfade(View fadeInView, View fadeOutView) {
        crossfade(fadeInView, fadeOutView, false, null);
    }

    public static void crossfade(final View fadeInView, final View fadeOutView, boolean immediate, final Runnable endAction) {
        if (immediate) {
            fadeInView.setVisibility(View.VISIBLE);
            fadeOutView.setVisibility(View.GONE);

        } else {
            fadeIn(fadeInView);
            fadeOut(fadeOutView, endAction);
        }
    }

    public static void fadeIn(View view) {
        fadeIn(view, null);
    }

    public static void fadeIn(View view, final Runnable endAction) {
        if (view.getVisibility() != View.VISIBLE) {
            view.animate().cancel();
            view.setAlpha(0F);
            view.setVisibility(View.VISIBLE);

            view.animate()
                    .alpha(1F)
                    .setDuration(FADE_DURATION)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override public void onAnimationEnd(Animator animation) {
                            if (endAction != null) {
                                endAction.run();
                            }
                        }
                    });
        }
    }

    public static void fadeOut(View view) {
        fadeOut(view, null);
    }

    public static void fadeOut(final View view, final Runnable endAction) {
        if (view.getVisibility() != View.GONE) {
            view.animate().cancel();
            view.setAlpha(1F);
            view.setVisibility(View.VISIBLE);
            view.animate()
                    .alpha(0F)
                    .setDuration(FADE_DURATION)
                    .setListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            view.setAlpha(1F);
                            view.setVisibility(View.GONE);
                            if (endAction != null) {
                                endAction.run();
                            }
                        }
                    });
        }
    }

    public static void bounceIn(final View view) {
        ensureOvershootInterpolator();
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0F);
        view.setScaleX(0.75F);
        view.setScaleY(0.75F);
        view.animate()
                .setDuration(BOUNCE_DURATION)
                .setInterpolator(sOvershootInterpolator)
                .alpha(1F)
                .scaleX(1F)
                .scaleY(1F)
                .setListener(null);
    }

    public static void bounceOut(final View view) {
        ensureOvershootInterpolator();
        view.setAlpha(1F);
        view.setScaleX(1F);
        view.setScaleY(1F);
        view.animate()
                .setDuration(BOUNCE_DURATION)
                .setInterpolator(sOvershootInterpolator)
                .alpha(0F)
                .scaleX(0F)
                .scaleY(0F)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private static void ensureOvershootInterpolator() {
        if (sOvershootInterpolator == null) {
            sOvershootInterpolator = new OvershootInterpolator();
        }
    }

}
