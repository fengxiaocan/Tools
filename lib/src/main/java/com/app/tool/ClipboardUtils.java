package com.app.tool;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * <pre>
 *
 *
 *     time  : 2016/09/25
 *     desc  : 剪贴板相关工具类
 * </pre>
 */
class ClipboardUtils extends Util {


    /**
     * 获取粘贴板管理器
     */
    public static ClipboardManager getManager() {
        return (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
    }

    /**
     * 复制文本
     *
     * @param text
     */
    public static void copyText(CharSequence text) {
        getManager().setPrimaryClip(ClipData.newPlainText("text", text));
    }

    /**
     * 复制文本
     *
     * @param data
     */
    public static void copyData(ClipData data) {
        getManager().setPrimaryClip(data);
    }

    /**
     * 获取剪贴板的文本
     *
     * @return 剪贴板的文本
     */
    public static CharSequence getText() {
        ClipData clip = getManager().getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).coerceToText(getContext());
        }
        return null;
    }

    /**
     * 复制uri到剪贴板
     *
     * @param uri uri
     */
    public static void copyUri(Uri uri) {
        getManager().setPrimaryClip(ClipData.newUri(getContext().getContentResolver(), "uri", uri));
    }

    /**
     * 获取剪贴板的uri
     *
     * @return 剪贴板的uri
     */
    public static Uri getUri() {
        ClipData clip = getManager().getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getUri();
        }
        return null;
    }

    /**
     * 复制意图到剪贴板
     *
     * @param intent 意图
     */
    public static void copyIntent(Intent intent) {
        getManager().setPrimaryClip(ClipData.newIntent("intent", intent));
    }

    /**
     * 获取剪贴板的意图
     *
     * @return 剪贴板的意图
     */
    public static Intent getIntent() {
        ClipData clip = getManager().getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            return clip.getItemAt(0).getIntent();
        }
        return null;
    }
}
