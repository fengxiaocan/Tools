package com.app.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.View;

class PaintUtils{
    private Paint mPaint;

    public PaintUtils(){
        this.mPaint = new TextPaint();
        init();
    }

    public PaintUtils(Paint paint){
        this.mPaint = paint;
        init();
    }

    public static float getTextWidth(Paint paint,CharSequence text){
        if(text == null){
            return 0f;
        } else{
            return paint.measureText(text.toString());
        }
    }

    public static String cropTextEnd(Paint paint,String text,int maxWidth){
        float textWidth = getTextWidth(paint,text);
        if(textWidth <= maxWidth){
            return text;
        } else{
            float endWidth = getTextWidth(paint,"...");
            char[] chars = text.toCharArray();
            float width = endWidth;
            for(int i = 0;i < chars.length;i++){
                String value = String.valueOf(chars[i]);
                float widthTemp = paint.measureText(value);
                width += widthTemp;
                if(width > maxWidth){
                    return text.substring(0,i) + "...";
                }
            }
        }
        return text;
    }

    /**
     * 把文字绘制在中间
     *
     * @param canvas
     * @param text
     * @param view
     * @param paint
     */
    public static void drawTextOnCenter(Canvas canvas,String text,View view,Paint paint){
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float v = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;

        float x = view.getMeasuredWidth() / 2f;
        float y = view.getMeasuredHeight() / 2 + v;
        canvas.drawText(text,x,y,paint);
    }

    public static void drawTextOnCenter(Canvas canvas,String text,Rect rect,Paint paint){
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float v = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float x = rect.centerX();
        float y = rect.centerY() + v;
        canvas.drawText(text,x,y,paint);
    }

    public static void drawTextOnCenter(Canvas canvas,String text,RectF rect,Paint paint){
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float v = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float x = rect.centerX();
        float y = rect.centerY() + v;
        canvas.drawText(text,x,y,paint);
    }

    /**
     * 绘制drawable在中间
     *
     * @param canvas
     * @param drawable
     * @param view
     */
    public static void drawableCenter(Canvas canvas,Drawable drawable,View view){
        int dx = view.getMeasuredWidth() / 2 - drawable.getBounds().width() / 2;
        int dy = view.getMeasuredHeight() / 2 - drawable.getBounds().height() / 2;
        canvas.save();
        canvas.translate(dx,dy);
        drawable.draw(canvas);
        canvas.restore();
    }


    private void init(){
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    public void setBold(boolean isbold){
        if(isbold){
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        } else{
            mPaint.setTypeface(Typeface.DEFAULT);
        }
    }

    public void setColor(int color){
        mPaint.setColor(color);
    }

    public Paint getPaint(){
        return mPaint;
    }

    /**
     * 测量文字的宽度以及高度大小
     *
     * @param text
     * @param bound
     */
    public void getTextBounds(CharSequence text,Rect bound){
        if(text == null){
            bound.setEmpty();
        } else{
            mPaint.getTextBounds(text.toString(),0,text.length(),bound);
        }
    }

    /**
     * 测量文字的宽度
     *
     * @param text
     * @return
     */
    public float getTextWidth(CharSequence text){
        if(text == null){
            return 0f;
        } else{
            return mPaint.measureText(text.toString());
        }
    }

    /**
     * 单行裁剪有最大宽度限制的字
     *
     * @param text
     * @param maxWidth
     * @return
     */
    public String cropText(String text,int maxWidth){
        float textWidth = getTextWidth(text);
        if(textWidth <= maxWidth){
            return text;
        } else{
            char[] chars = text.toCharArray();
            int width = 0;
            for(int i = 0;i < chars.length;i++){
                String value = String.valueOf(chars[i]);
                float widthTemp = mPaint.measureText(value);
                width += widthTemp;
                if(width > maxWidth){
                    return text.substring(0,i);
                }
            }
        }
        return text;
    }

    /**
     * 单行裁剪有最大宽度限制的字 并且在后面加上...省略
     *
     * @param text
     * @param maxWidth
     * @return
     */
    public String cropTextEnd(String text,int maxWidth){
        float textWidth = getTextWidth(text);
        if(textWidth <= maxWidth){
            return text;
        } else{
            float endWidth = getTextWidth("...");
            char[] chars = text.toCharArray();
            float width = endWidth;
            for(int i = 0;i < chars.length;i++){
                String value = String.valueOf(chars[i]);
                float widthTemp = mPaint.measureText(value);
                width += widthTemp;
                if(width > maxWidth){
                    return text.substring(0,i) + "...";
                }
            }
        }
        return text;
    }

    /**
     * 绘制居中的文字
     *
     * @param canvas
     * @param text
     * @param view
     */
    public void drawTextOnCenter(Canvas canvas,String text,View view){
        mPaint.setTextAlign(Paint.Align.CENTER);
        float x = view.getMeasuredWidth() / 2f;
        float y = view.getMeasuredHeight() / 2 + getFontDistanceAhalf();
        canvas.drawText(text,x,y,mPaint);
    }

    /**
     * @param canvas
     * @param text
     * @param yTop   y坐标的字体最顶点
     */
    public void drawTextOnCenterX(Canvas canvas,String text,View view,int yTop){
        mPaint.setTextAlign(Paint.Align.CENTER);
        float x = view.getMeasuredWidth() / 2;
        float y = yTop + getFontDistance();
        canvas.drawText(text,x,y,mPaint);
    }

    public void drawTextOnCenterY(Canvas canvas,String text,View view,int x){
        mPaint.setTextAlign(Paint.Align.CENTER);
        float y = view.getMeasuredHeight() / 2 + getFontDistanceAhalf();
        canvas.drawText(text,x,y,mPaint);
    }

    /**
     * 获取文字的实际绘制高度
     *
     * @return
     */
    public float getFontDistance(){
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        return (fontMetrics.bottom - fontMetrics.top);
    }

    /**
     * 获取文字的实际绘制高度的二分之一
     *
     * @return
     */
    public float getFontDistanceAhalf(){
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        return (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
    }

    public float getFontTop(){
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        return fontMetrics.top;
    }

    public float getFontBottom(){
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        return fontMetrics.bottom;
    }

    public float getFontAscent(){
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        return fontMetrics.ascent;
    }

    public float getFontDescent(){
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        return fontMetrics.descent;
    }

    public float getFontLeading(){
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        return fontMetrics.leading;
    }

    public void setSpTextSize(Context context,float spPx){
        mPaint.setTextSize(SizeUtils.sp2px(context,spPx));
    }

    public void setDpTextSize(Context context,float dpPx){
        mPaint.setTextSize(SizeUtils.dp2px(context,dpPx));
    }
}
