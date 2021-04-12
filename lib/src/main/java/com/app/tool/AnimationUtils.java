package com.app.tool;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.lang.reflect.Field;

class AnimationUtils {

    /**
     * 如果动画被禁用，则重置动画缩放时长
     */
    public static void resetDurationScaleIfDisable() {
        if (getDurationScale() == 0) {
            resetDurationScale();
        }
    }

    /**
     * 重置动画缩放时长
     */
    public static void resetDurationScale() {
        try {
            Field field = ValueAnimator.class.getDeclaredField("sDurationScale");
            field.setAccessible(true);
            field.setFloat(null, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static float getDurationScale() {
        try {
            Field field = ValueAnimator.class.getDeclaredField("sDurationScale");
            field.setAccessible(true);
            return field.getFloat(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static ObjectAnimator translationY(View view, float... values) {
        return ObjectAnimator.ofFloat(view, "translationY", values);
    }

    public static ObjectAnimator translationX(View view, float... values) {
        return ObjectAnimator.ofFloat(view, "translationX", values);
    }

    public static ObjectAnimator rotation(View view, float... values) {
        return ObjectAnimator.ofFloat(view, "rotation", values);
    }

    public static ObjectAnimator scaleX(View view, float... values) {
        return ObjectAnimator.ofFloat(view, "scaleX", values);
    }

    public static ObjectAnimator scaleY(View view, float... values) {
        return ObjectAnimator.ofFloat(view, "scaleY", values);
    }

    public static ObjectAnimator alpha(View view, float... values) {
        return ObjectAnimator.ofFloat(view, "alpha", values);
    }

    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return
     */
    public static TranslateAnimation moveToBottom(long duration, boolean isFill) {
        final int relativeToSelf = Animation.RELATIVE_TO_SELF;
        TranslateAnimation ani = new TranslateAnimation(relativeToSelf,
                0.0f,
                relativeToSelf,
                0.0f,
                relativeToSelf,
                0.0f,
                relativeToSelf,
                1.0f);
        ani.setFillEnabled(isFill);//使其可以填充效果从而不回到原地
        ani.setFillAfter(!isFill);//不回到起始位置
        //如果不添加setFillEnabled和setFillAfter则动画执行结束后会自动回到远点
        ani.setDuration(duration);
        return ani;
    }

    /**
     * 从控件所在位置移动到控件的顶部
     *
     * @return
     */
    public static TranslateAnimation moveToTop(long duration, boolean isFill) {
        final int relativeToSelf = Animation.RELATIVE_TO_SELF;
        TranslateAnimation ani = new TranslateAnimation(relativeToSelf,
                0.0f,
                relativeToSelf,
                0.0f,
                relativeToSelf,
                0.0f,
                relativeToSelf,
                -1.0f);
        ani.setFillEnabled(isFill);//使其可以填充效果从而不回到原地
        ani.setFillAfter(!isFill);//不回到起始位置
        //如果不添加setFillEnabled和setFillAfter则动画执行结束后会自动回到远点
        ani.setDuration(duration);
        return ani;
    }

    /**
     * 从控件所在位置移动到控件的左边
     *
     * @return
     */
    public static TranslateAnimation moveToLeft(long duration, boolean isFill) {
        final int relativeToSelf = Animation.RELATIVE_TO_SELF;
        TranslateAnimation ani = new TranslateAnimation(relativeToSelf,
                0.0f,
                relativeToSelf,
                -1.0f,
                relativeToSelf,
                0.0f,
                relativeToSelf,
                0.0f);
        ani.setFillEnabled(isFill);//使其可以填充效果从而不回到原地
        ani.setFillAfter(!isFill);//不回到起始位置
        //如果不添加setFillEnabled和setFillAfter则动画执行结束后会自动回到远点
        ani.setDuration(duration);
        return ani;
    }

    /**
     * 从控件所在位置移动到控件的右边
     *
     * @return
     */
    public static TranslateAnimation moveToRight(long duration, boolean isFill) {
        final int relativeToSelf = Animation.RELATIVE_TO_SELF;
        TranslateAnimation ani = new TranslateAnimation(relativeToSelf,
                0.0f,
                relativeToSelf,
                1.0f,
                relativeToSelf,
                0.0f,
                relativeToSelf,
                0.0f);
        ani.setFillEnabled(isFill);//使其可以填充效果从而不回到原地
        ani.setFillAfter(!isFill);//不回到起始位置
        //如果不添加setFillEnabled和setFillAfter则动画执行结束后会自动回到远点
        ani.setDuration(duration);
        return ani;
    }

    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveFromBottomToLocation(long duration, boolean isFill) {
        final int relativeToSelf = Animation.RELATIVE_TO_SELF;
        TranslateAnimation ani = new TranslateAnimation(relativeToSelf,
                0.0f,
                relativeToSelf,
                0.0f,
                relativeToSelf,
                1.0f,
                relativeToSelf,
                0.0f);
        ani.setFillEnabled(isFill);//使其可以填充效果从而不回到原地
        ani.setFillAfter(!isFill);//不回到起始位置
        ani.setDuration(duration);
        return ani;
    }

    /**
     * 从控件的上面移动到控件所在位置
     */
    public static TranslateAnimation moveFromTopToLocation(long duration, boolean isFill) {
        final int relativeToSelf = Animation.RELATIVE_TO_SELF;
        TranslateAnimation ani = new TranslateAnimation(relativeToSelf,
                0.0f,
                relativeToSelf,
                0.0f,
                relativeToSelf,
                -1.0f,
                relativeToSelf,
                0.0f);
        ani.setFillEnabled(isFill);//使其可以填充效果从而不回到原地
        ani.setFillAfter(!isFill);//不回到起始位置
        ani.setDuration(duration);
        return ani;
    }


    /**
     * 从控件的左边移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveFromLeftToLocation(long duration, boolean isFill) {
        final int relativeToSelf = Animation.RELATIVE_TO_SELF;
        TranslateAnimation ani = new TranslateAnimation(relativeToSelf,
                -1.0f,
                relativeToSelf,
                0.0f,
                relativeToSelf,
                0.0f,
                relativeToSelf,
                0.0f);
        ani.setFillEnabled(isFill);//使其可以填充效果从而不回到原地
        ani.setFillAfter(!isFill);//不回到起始位置
        ani.setDuration(duration);
        return ani;
    }

    /**
     * 从控件的右边移动到当前位置
     *
     * @param duration
     * @param isFill
     * @return
     */
    public static TranslateAnimation moveFromRightToLocation(long duration, boolean isFill) {
        final int relativeToSelf = Animation.RELATIVE_TO_SELF;
        TranslateAnimation ani = new TranslateAnimation(relativeToSelf,
                1.0f,
                relativeToSelf,
                0.0f,
                relativeToSelf,
                0.0f,
                relativeToSelf,
                0.0f);
        ani.setFillEnabled(isFill);//使其可以填充效果从而不回到原地
        ani.setFillAfter(!isFill);//不回到起始位置
        ani.setDuration(duration);
        return ani;
    }


    /**
     * 隐藏
     */
    public static AlphaAnimation hideAlpha(long duration) {
        AlphaAnimation mHiddenAction = new AlphaAnimation(1.0f, 0.0f);
        mHiddenAction.setDuration(duration);
        return mHiddenAction;
    }

    /**
     * 隐藏
     */
    public static AlphaAnimation showAlpha(long duration) {
        AlphaAnimation mHiddenAction = new AlphaAnimation(0.0f, 1.0f);
        mHiddenAction.setDuration(duration);
        return mHiddenAction;
    }

    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return
     */
    public static void moveViewToBottom(final View view, final int visibility, long duration) {
        if (view != null) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "TranslationY", 0, view.getMeasuredHeight());
            objectAnimator.setDuration(duration);
            objectAnimator.addListener(new AnimationImpl() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    view.setVisibility(visibility);
                }
            });
            objectAnimator.start();
        }
    }

    public static void moveViewToTop(final View view, final int visibility, long duration) {
        if (view != null) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "TranslationY", 0, -view.getMeasuredHeight());
            objectAnimator.setDuration(duration);
            objectAnimator.addListener(new AnimationImpl() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(visibility);
                }
            });
            objectAnimator.start();
        }
    }


    /**
     * 隐藏
     */
    public static void hideAlpha(View view, long duration) {
        if (view != null) {
            view.setVisibility(View.GONE);
            view.setAnimation(hideAlpha(duration));
        }
    }

    /**
     * 隐藏
     */
    public static void showAlpha(View view, long duration) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            view.setAnimation(showAlpha(duration));
        }
    }

    /**
     * 给视图添加点击效果,让背景变深
     */
    public static void addTouchDrak(View view, View.OnClickListener listener) {
        view.setOnTouchListener(new ViewTouchDark());
        if (listener != null) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 给视图添加点击效果,让背景变暗
     */
    public static void addTouchLight(View view, View.OnClickListener listener) {
        view.setOnTouchListener(new ViewTouchLight());
        if (listener != null) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 让控件点击时，颜色变深
     */
    public static class ViewTouchDark implements View.OnTouchListener {

        public final float[] BT_SELECTED = new float[]{1, 0, 0, 0, -50, 0, 1, 0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0};
        public final float[] BT_NOT_SELECTED = new float[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};

        @SuppressWarnings("deprecation")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            }
            return false;
        }
    }

    /**
     * 让控件点击时，颜色变暗
     */
    public static class ViewTouchLight implements View.OnTouchListener {
        public final float[] BT_SELECTED = new float[]{1, 0, 0, 0, 50, 0, 1, 0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0};
        public final float[] BT_NOT_SELECTED = new float[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};

        @SuppressWarnings("deprecation")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView) v;
                    iv.setDrawingCacheEnabled(true);

                    iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            }
            return false;
        }
    }


    static class AnimationImpl implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {

        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }
}
