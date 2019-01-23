package com.rain.materialdesign.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * 界面跳转 工具类
 *
 * out关键字 表示是某个类的子类
 * in关键字 表示是某个类的父类
 *
 */

object JumpUtil {

    /**
     * 不带参数的跳转
     *
     * @param context
     * @param targetClazz
     */
    fun overlay(context: Context, targetClazz: Class<out Activity>) {
        val mIntent = Intent(context, targetClazz)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(mIntent)
    }

    /**
     * 带参数不带动画的跳转
     *
     * @param context
     * @param targetClazz
     * @param bundle
     */
    fun overlay(context: Context, targetClazz: Class<out Activity>, bundle: Bundle?) {
        val mIntent = Intent(context, targetClazz)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (bundle != null) {
            mIntent.putExtras(bundle)
        }
        context.startActivity(mIntent)
    }

    /**
     * 带参数,共享元素跳转
     *
     * @param context
     * @param targetClazz
     * @param bundle
     */
    fun overlay(context: Context, targetClazz: Class<out Activity>, bundle: Bundle?, options: Bundle) {
        val mIntent = Intent(context, targetClazz)
        if (bundle != null) {
            mIntent.putExtras(bundle)
        }
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(mIntent, options)
    }

    /**
     *
     *
     * @param context
     * @param targetClazz
     * @param bundle
     * @param flags
     */
    fun overlay(context: Context, targetClazz: Class<out Activity>, bundle: Bundle?, flags: Int?) {
        val mIntent = Intent(context, targetClazz)
        if (bundle != null) {
            mIntent.putExtras(bundle)
        }
        if (flags != null) {
            mIntent.flags = flags
        }
        context.startActivity(mIntent)
    }


    /**
     * 界面跳转带 result
     *
     * @param context
     * @param targetClazz
     * @param requestCode
     * @param bundle
     */
    fun startForResult(context: Activity, targetClazz: Class<out Activity>, requestCode: Int, bundle: Bundle?) {
        val mIntent = Intent(context, targetClazz)
        if (bundle != null) {
            mIntent.putExtras(bundle)
        }
        context.startActivityForResult(mIntent, requestCode)
    }

    /**
     * fragment 界面跳转 带result
     *
     * @param fragment
     * @param targetClazz
     * @param requestCode
     * @param bundle
     */
    fun startForResult(fragment: Fragment, targetClazz: Class<out Activity>, requestCode: Int, bundle: Bundle?) {
        val mIntent = Intent(fragment.activity, targetClazz)
        if (bundle != null) {
            mIntent.putExtras(bundle)
        }
        fragment.startActivityForResult(mIntent, requestCode)
    }

}
