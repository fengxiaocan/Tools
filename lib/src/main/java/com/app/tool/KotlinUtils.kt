package com.app.baselib.util

import android.text.TextUtils

/**
 * kotlin判断工具类
 */
class KotlinUtils {
    companion object {
        open fun judge(boolean:Boolean?):Boolean = boolean == true

        open fun judge(content:String?):Boolean = ! TextUtils.isEmpty(content)

        open fun <T> judge(collection:Collection<T>?):Boolean = collection?.size ?: 0 > 0

        open fun <T,K> judge(map:Map<T,K>?):Boolean = map?.size ?: 0 > 0

        open fun <T> judge(value:T?,judge:T):Boolean = value == judge
    }
}