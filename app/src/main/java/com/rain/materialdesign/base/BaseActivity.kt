package com.rain.materialdesign.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar


/**
 * Author:rain
 * Date:2018/11/16 9:58
 * Description:
 */
abstract class BaseActivity : AppCompatActivity() {
    protected val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView(savedInstanceState)
    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun getLayoutId(): Int

    protected fun initToolbar(toobar: Toolbar, title:String, homeAsUpEnabled:Boolean) {
        toobar.title = title
        setSupportActionBar(toobar)
        // 设置toolbar是否有返回按钮
        supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
        toobar.setNavigationOnClickListener { finish() }
    }

}