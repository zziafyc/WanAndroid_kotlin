package com.zziafyc.wanandroidkotlin.extend

import android.content.Context
import androidx.fragment.app.Fragment
import com.zziafyc.wanandroidkotlin.widget.CustomToast

/**
 *
 * @作者 zziafyc
 * @创建日期 2019/11/17 0017
 * @description kotlin扩展函数相关
 */

fun Fragment.showToast(content: String) {
    CustomToast(this?.activity?.applicationContext, content).show()
}

fun Context.showToast(content: String) {
    CustomToast(this, content).show()
}