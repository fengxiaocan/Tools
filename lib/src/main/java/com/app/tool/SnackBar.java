package com.app.tool;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class SnackBar {
    private static final int GREEN = 0xFF64D48C;
    private static final int BLUE = 0xFF61B3FF;
    private static final int RED = 0xFFFF7070;
    private static final int YELLOW = 0xFFFDCA58;
    private static final int GRAY = 0xFF666666;

    private Snackbar snackbar;

    public SnackBar(View view) {
        snackbar = Snackbar.make(view, "", 5000);
    }

    public static SnackBar make(View view) {
        return new SnackBar(view);
    }

    public static SnackBar make(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        return new SnackBar(view);
    }

    public SnackBar longDuration() {
        return setDuration(10000);
    }

    public SnackBar shortDuration() {
        return setDuration(2000);
    }


    public SnackBar blueTheme() {
        return blueBackground().whiteTextColor();
    }

    public SnackBar redTheme() {
        return redBackground().whiteTextColor();
    }

    public SnackBar yellowTheme() {
        return yellowBackground().whiteTextColor();
    }

    public SnackBar grayTheme() {
        return grayBackground().whiteTextColor();
    }

    public SnackBar greenTheme() {
        return greenBackground().whiteTextColor();
    }

    public SnackBar blackTheme() {
        return blackBackground().whiteTextColor();
    }

    public SnackBar errorTheme() {
        return blackBackground().redTextColor();
    }

    public SnackBar blueBackground() {
        return setBackgroundColor(BLUE);
    }

    public SnackBar redBackground() {
        return setBackgroundColor(RED);
    }

    public SnackBar yellowBackground() {
        return setBackgroundColor(YELLOW);
    }

    public SnackBar grayBackground() {
        return setBackgroundColor(GRAY);
    }

    public SnackBar greenBackground() {
        return setBackgroundColor(GREEN);
    }

    public SnackBar blackBackground() {
        return setBackgroundColor(Color.BLACK);
    }

    public SnackBar whiteTextColor() {
        return setTextColor(Color.WHITE);
    }

    public SnackBar blackTextColor() {
        return setTextColor(Color.BLACK);
    }

    public SnackBar redTextColor() {
        return setTextColor(Color.RED);
    }

    public SnackBar yellowTextColor() {
        return setTextColor(Color.YELLOW);
    }

    public SnackBar greenTextColor() {
        return setTextColor(Color.GREEN);
    }

    public void show() {
        snackbar.show();
    }

    public void show(CharSequence text) {
        snackbar.setText(text);
        snackbar.show();
    }

    public void dismiss() {
        snackbar.dismiss();
    }

    public boolean isShown() {
        return snackbar.isShown();
    }

    public SnackBar setText(@NonNull CharSequence message) {
        snackbar.setText(message);
        return this;
    }

    public SnackBar setText(int resId) {
        snackbar.setText(resId);
        return this;
    }

    public SnackBar setAction(int resId, View.OnClickListener listener) {
        snackbar.setAction(resId, listener);
        return this;
    }

    public SnackBar setAction(@Nullable CharSequence text, @Nullable View.OnClickListener listener) {
        snackbar.setAction(text, listener);
        return this;
    }

    public SnackBar setTextColor(ColorStateList colors) {
        snackbar.setTextColor(colors);
        return this;
    }

    public SnackBar setTextColor(int color) {
        snackbar.setTextColor(color);
        return this;
    }

    public SnackBar setActionTextColor(ColorStateList colors) {
        snackbar.setActionTextColor(colors);
        return this;
    }

    public SnackBar setMaxInlineActionWidth(int width) {
        snackbar.setMaxInlineActionWidth(width);
        return this;
    }

    public SnackBar setActionTextColor(int color) {
        snackbar.setActionTextColor(color);
        return this;
    }

    public SnackBar setBackgroundColor(int color) {
        snackbar.getView().setBackgroundColor(color);
        return this;
    }

    public SnackBar setBackgroundResource(@DrawableRes int resid) {
        snackbar.getView().setBackgroundResource(resid);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public SnackBar setBackground(Drawable background) {
        snackbar.getView().setBackground(background);
        return this;
    }

    public SnackBar setBackgroundTint(int color) {
        snackbar.setBackgroundTint(color);
        return this;
    }

    public SnackBar setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        snackbar.setBackgroundTintList(colorStateList);
        return this;
    }

    public SnackBar setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        snackbar.setBackgroundTintMode(mode);
        return this;
    }

    public SnackBar setCallback(@Nullable Snackbar.Callback callback) {
        snackbar.setCallback(callback);
        return this;
    }

    public SnackBar setDuration(int duration) {
        snackbar.setDuration(duration);
        return this;
    }

    public SnackBar setGestureInsetBottomIgnored(boolean gestureInsetBottomIgnored) {
        snackbar.setGestureInsetBottomIgnored(gestureInsetBottomIgnored);
        return this;
    }

    public SnackBar setAnimationMode(int animationMode) {
        snackbar.setAnimationMode(animationMode);
        return this;
    }

    public SnackBar setAnchorView(@Nullable View anchorView) {
        snackbar.setAnchorView(anchorView);
        return this;
    }

    public SnackBar setAnchorView(int anchorViewId) {
        snackbar.setAnchorView(anchorViewId);
        return this;
    }

    public SnackBar setAnchorViewLayoutListenerEnabled(boolean anchorViewLayoutListenerEnabled) {
        snackbar.setAnchorViewLayoutListenerEnabled(anchorViewLayoutListenerEnabled);
        return this;
    }

    public SnackBar setBehavior(BaseTransientBottomBar.Behavior behavior) {
        snackbar.setBehavior(behavior);
        return this;
    }

    public SnackBar addCallback(@Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        snackbar.addCallback(callback);
        return this;
    }

    public SnackBar removeCallback(@Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        snackbar.removeCallback(callback);
        return this;
    }
}
