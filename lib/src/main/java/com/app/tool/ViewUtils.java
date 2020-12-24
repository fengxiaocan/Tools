package com.app.tool;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.NumberKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.util
 * @创建者: Noah.冯
 * @时间: 16:16
 * @描述： 与view相关的工具类
 */
class ViewUtils {

    /**
     * 获取Listview的高度，然后设置ViewPager的高度
     *
     * @param listView
     * @return
     */
    public static int setListViewHeight(ListView listView) {
        if (listView == null) {
            return 0;
        }
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        return params.height;
    }


    /**
     * 修改整个界面所有控件的字体
     */
    public static void changeFonts(ViewGroup root, String path, Activity act) {
        //path是字体路径
        Typeface tf = Typeface.createFromAsset(act.getAssets(), path);
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(tf);
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(tf);
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(tf);
            } else if (v instanceof ViewGroup) {
                changeFonts((ViewGroup) v, path, act);
            }
        }
    }

    /**
     * 修改整个界面所有控件的字体大小
     */
    public static void changeTextSize(ViewGroup root, int size) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTextSize(size);
            } else if (v instanceof ViewGroup) {
                changeTextSize((ViewGroup) v, size);
            }
        }
    }

    /**
     * 不改变控件位置，修改控件大小
     */
    public static boolean changeSize(View view, int width, int height) {
        if (view == null) {
            return false;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            params.width = width;
            params.height = height;
            view.setLayoutParams(params);
            return true;
        }
        return false;
    }

    /**
     * 修改控件的高
     *
     * @return
     */
    public static boolean changeHeight(View v, int H) {
        ViewGroup.LayoutParams params = v.getLayoutParams();
        if (params != null) {
            params.height = H;
            v.setLayoutParams(params);
            return true;
        }
        return false;
    }

    /**
     * 修改控件的高
     *
     * @return
     */
    public static boolean changeWidth(View v, int w) {
        ViewGroup.LayoutParams params = v.getLayoutParams();
        if (params != null) {
            params.width = w;
            v.setLayoutParams(params);
            return true;
        }
        return false;
    }

    /**
     * 垂直方向上偏移
     *
     * @param v
     * @param offset
     */
    public static void offsetVertical(View v, int offset) {
        v.layout(v.getLeft(), v.getTop() + offset, v.getRight(), v.getBottom() + offset);
    }

    /**
     * 水平方向上偏移
     *
     * @param v
     * @param offset
     */
    public static void offsetOrientation(View v, int offset) {
        v.layout(v.getLeft() + offset, v.getTop(), v.getRight() + offset, v.getBottom());
    }

    /**
     * 获取全局坐标系的一个视图区域， 返回一个填充的Rect对象；该Rect是基于总整个屏幕的
     *
     * @param view
     * @return
     */
    public static Rect getGlobalVisibleRect(View view) {
        Rect r = new Rect();
        view.getGlobalVisibleRect(r);
        return r;
    }

    /**
     * 算该视图在全局坐标系中的x，y值，（注意这个值是要从屏幕顶端算起，也就是索包括了通知栏的高度）
     * 获取在当前屏幕内的绝对坐标
     *
     * @param view
     * @return
     */
    public static int[] getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    /**
     * 计算该视图在它所在的widnow的坐标x，y值，//获取在整个窗口内的绝对坐标
     *
     * @param view
     * @return
     */
    public static int[] getLocationInWindow(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location;
    }

    /**
     * 获取控件在其父窗口中的坐标X坐标
     *
     * @param view
     * @return
     */
    public static int getLocationXInWindow(View view) {
        if (view == null) {
            return 0;
        }
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location[0];
    }

    /**
     * 获取控件在其父窗口中的坐标Y坐标
     *
     * @param view
     * @return
     */
    public static int getLocationYInWindow(View view) {
        if (view == null) {
            return 0;
        }
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location[1];
    }

    /**
     * 获取控件在屏幕上的X坐标
     *
     * @param view
     * @return
     */
    public static int getLocationXInScreen(View view) {
        if (view == null) {
            return 0;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[0];
    }

    /**
     * 获取控件在屏幕上的Y坐标
     *
     * @param view
     * @return
     */
    public static int getLocationYInScreen(View view) {
        if (view == null) {
            return 0;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[1];
    }

    /**
     * 测量View的宽高
     *
     * @param view View
     */
    public static void measureWidthAndHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
    }


    /**
     * 把自身从父View中移除
     */
    public static void removeSelfFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }

    /**
     * 判断触点是否落在该View上
     */
    public static boolean isTouchInView(MotionEvent ev, View v) {
        int[] vLoc = new int[2];
        v.getLocationOnScreen(vLoc);
        float motionX = ev.getRawX();
        float motionY = ev.getRawY();
        return motionX >= vLoc[0] && motionX <= (vLoc[0] + v.getWidth()) && motionY >= vLoc[1] &&
                motionY <= (vLoc[1] + v.getHeight());
    }

    /**
     * @param view
     * @param isAll
     */
    public static void requestLayoutParent(View view, boolean isAll) {
        ViewParent parent = view.getParent();
        while (parent != null && parent instanceof View) {
            if (!parent.isLayoutRequested()) {
                parent.requestLayout();
                if (!isAll) {
                    break;
                }
            }
            parent = parent.getParent();
        }
    }


    /**
     * 给TextView设置下划线
     *
     * @param textView
     */
    public static void setUnderLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView.getPaint().setAntiAlias(true);
    }


    /**
     * 获取view的宽度
     *
     * @param view
     * @return
     */
    public static int getViewWidth(View view) {
        measureWidthAndHeight(view);
        return view.getMeasuredWidth();
    }

    /**
     * 获取view的高度
     *
     * @param view
     * @return
     */
    public static int getViewHeight(View view) {
        measureWidthAndHeight(view);
        return view.getMeasuredHeight();
    }

    /**
     * 获取view的上下文
     *
     * @param view
     * @return
     */
    public static Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

    /**
     * View是否可见
     *
     * @param view
     * @return
     */
    public static boolean isVisible(View view) {
        return view != null && view.isShown();
    }

    /**
     * View不可见
     *
     * @param view
     */
    public static void hideView(View view) {
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    public static void hideView(View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null)
                    view.setVisibility(View.GONE);
            }
        }
    }

    public static void invisibleView(View view) {
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * View可见
     *
     * @param view
     */
    public static void showView(View view) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void showView(View view, boolean isVisible) {
        if (view != null) {
            if (isVisible) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

    public static void showView(View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null)
                    view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * View可见
     *
     * @param view
     */
    public static void showViewWithAnim(View view, long animtime) {
        if (view != null) {
            if (view.getVisibility() != View.VISIBLE) {
                AlphaAnimation mShowAction = new AlphaAnimation(0f, 1f);
                mShowAction.setDuration(animtime);
                view.setAnimation(mShowAction);
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void showViewWithAnim(long animtime, View... views) {
        if (views != null) {
            AlphaAnimation mShowAction = new AlphaAnimation(0f, 1f);
            mShowAction.setDuration(animtime);
            for (View view : views) {
                if (view != null) {
                    if (view.getVisibility() != View.VISIBLE) {
                        view.setAnimation(mShowAction);
                    }
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public static void showViewWithAnim(View... views) {
        showViewWithAnim(250, views);
    }

    /**
     * View可见
     *
     * @param view
     */
    public static void showViewWithAnim(View view, Animation animation, long animtime) {
        if (view != null) {
            if (view.getVisibility() != View.VISIBLE) {
                animation.setDuration(animtime);
                view.setAnimation(animation);
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void showViewWithAnim(View view) {
        showViewWithAnim(view, 250);
    }

    /**
     * View可见
     *
     * @param view
     */
    public static void hideViewWithAnim(View view, long animtime) {
        if (view != null) {
            if (view.getVisibility() != View.GONE) {
                AlphaAnimation mShowAction = new AlphaAnimation(1f, 0f);
                mShowAction.setDuration(animtime);
                view.setAnimation(mShowAction);
                view.setVisibility(View.GONE);
            }
        }
    }

    public static void hideViewWithAnim(long animtime, View... views) {
        if (views != null) {
            AlphaAnimation mShowAction = new AlphaAnimation(1f, 0f);
            mShowAction.setDuration(animtime);
            for (View view : views) {
                if (view != null) {
                    if (view.getVisibility() != View.GONE) {
                        view.setAnimation(mShowAction);
                    }
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    public static void hideViewWithAnim(View... views) {
        hideViewWithAnim(250, views);
    }

    public static void hideViewWithAnim(View view) {
        hideViewWithAnim(view, 250);
    }

    /**
     * 设置View的PaddingLeft和paddingRight
     *
     * @param view
     * @param padding
     */
    public static void setViewPaddingVertical(View view, int padding) {
        if (view != null) {
            view.setPadding(padding, view.getPaddingTop(), padding, view.getPaddingBottom());
        }
    }

    /**
     * 设置View的PaddingTop和paddingBottom
     *
     * @param view
     * @param padding
     */
    public static void setViewPaddingHorizontal(View view, int padding) {
        if (view != null) {
            view.setPadding(view.getPaddingLeft(), padding, view.getPaddingRight(), padding);
        }
    }

    public static void setWidthAndHeight(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            params.width = width;
            params.height = height;
        } else {
            view.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        }
    }

    public static void matchScreen(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            params.width = ScreenUtils.screenWidth;
            params.height = ScreenUtils.screenHeight;
        } else {
            view.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtils.screenWidth,
                    ScreenUtils.screenHeight));
        }
    }

    public static void matchWidth(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            params.width = ScreenUtils.screenWidth;
        } else {
            view.setLayoutParams(new ViewGroup.LayoutParams(ScreenUtils.screenWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    public static void matchHeight(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params != null) {
            params.width = ScreenUtils.screenWidth;
        } else {
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ScreenUtils.screenHeight));
        }
    }

    /**
     * 隐藏或展示View
     *
     * @param view
     * @return 是否隐藏
     */
    public static boolean showOrHideView(View view) {
        if (view != null) {
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏或展示View
     *
     * @param view
     * @return 是否隐藏
     */
    public static boolean showOrHideViewWithAnim(View view, long animtime) {
        if (view != null) {
            if (view.getVisibility() == View.VISIBLE) {
                // 隐藏动画
                hideViewWithAnim(view, animtime);
            } else {
                showViewWithAnim(view, animtime);
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是粗体
     *
     * @param view
     * @return
     */
    public static boolean isBoldText(TextView view) {
        return view.getPaint().isFakeBoldText();
    }

    /**
     * 限制EditText最大字数
     *
     * @param editText
     */
    public static void restrictEditMaxLength(EditText editText, int maxLenght) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLenght)});
    }


    /**
     * 限制Edittext输入类型
     *
     * @param editText
     * @param inputType
     * @param restrict
     */
    public static void restrictEditInputType(EditText editText, final int inputType, final char... restrict) {
        editText.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return restrict;
            }

            @Override
            public int getInputType() {
                return inputType;
            }
        });
    }

    public static void changeEditSeePassword(EditText editText, boolean isCanSeePassword) {
        if (!isCanSeePassword) {
            //否则隐藏密码
            editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            //如果选中，显示密码
            editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    /**
     * 选择文本末尾
     *
     * @param editText
     */
    public static void selectionEnd(EditText editText) {
        if (editText != null) {
            editText.setSelection(editText.length());
        }
    }

    /**
     * 设置edittext 是否可以编辑
     *
     * @param editText
     * @param editable
     */
    public static void editable(EditText editText, boolean editable) {
        if (editText != null) {
            editText.setFocusable(editable);
            editText.setFocusableInTouchMode(editable);
        }
    }

    /**
     * 文本是否为空
     *
     * @param textView
     * @return
     */
    public static boolean isEmpty(TextView textView) {
        if (textView != null) {
            return TextUtils.isEmpty(textView.getText().toString().trim());
        }
        return true;
    }

    /**
     * 文本长度
     *
     * @param textView
     * @return
     */
    public static int textLength(TextView textView) {
        if (textView != null) {
            return textView.length();
        }
        return 0;
    }

    public static void removeFormParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(view);
                }
            }
        }
    }

    public static void recycleImageView(ImageView view) {
        if (view == null) {
            return;
        }
        Drawable drawable = view.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            Bitmap bmp = ((BitmapDrawable) drawable).getBitmap();
            if (bmp != null && !bmp.isRecycled()) {
                view.setImageBitmap(null);
                bmp.recycle();
            }
        }
    }
}
