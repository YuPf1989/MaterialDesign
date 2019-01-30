package com.rain.materialdesign.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.rain.materialdesign.R
import com.rain.materialdesign.base.BaseSwipeBackActivity
import com.rain.materialdesign.event.ColorEvent
import com.rain.materialdesign.ui.fragment.CollectFragment
import com.rain.materialdesign.ui.setting.SettingFragment
import com.rain.materialdesign.util.Constant
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

class CommonActivity : BaseSwipeBackActivity() {

    override fun attachLayoutRes(): Int = R.layout.activity_common

    override fun initView(savedInstanceState: Bundle?) {
        val extras = intent.extras
        val type = extras.getString(Constant.TYPE_KEY, "")

        initToolbar(toolbar,getString(R.string.app_name),true)

        val fragment = when (type) {
            Constant.Type.COLLECT_TYPE_KEY -> {
                toolbar.title = getString(R.string.collect)
                CollectFragment.getInstance(extras)
            }
            Constant.Type.ABOUT_US_TYPE_KEY -> {
//                toolbar.title = getString(R.string.about_us)
//                AboutFragment.getInstance(extras)
            }
            Constant.Type.SETTING_TYPE_KEY -> {
//                toolbar.title = getString(R.string.setting)
//                SettingFragment.getInstance(extras)
            }
            Constant.Type.SEARCH_TYPE_KEY -> {
//                toolbar.title = extras.getString(Constant.SEARCH_KEY, "")
//                SearchListFragment.getInstance(extras)
            }
            Constant.Type.ADD_TODO_TYPE_KEY -> {
//                toolbar.title = getString(R.string.add)
//                AddTodoFragment.getInstance(extras)
            }
            Constant.Type.EDIT_TODO_TYPE_KEY -> {
//                toolbar.title = getString(R.string.edit)
//                AddTodoFragment.getInstance(extras)
            }
            Constant.Type.SEE_TODO_TYPE_KEY -> {
//                toolbar.title = getString(R.string.see)
//                AddTodoFragment.getInstance(extras)
            }
            else -> {
                null
            }
        }
        fragment ?: return
        supportFragmentManager.beginTransaction()
                .replace(R.id.common_frame_layout, fragment as Fragment, Constant.Type.COLLECT_TYPE_KEY)
                .commit()
    }

    override fun start() {
    }

    override fun initColor() {
        super.initColor()
        EventBus.getDefault().post(ColorEvent(true, mThemeColor))
    }

}
