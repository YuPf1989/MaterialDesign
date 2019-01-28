package com.rain.materialdesign.ui.activity

import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import com.afollestad.materialdialogs.color.ColorChooserDialog
import com.rain.materialdesign.R
import com.rain.materialdesign.base.BaseActivity
import com.rain.materialdesign.event.ColorEvent
import com.rain.materialdesign.ui.setting.SettingFragment
import com.rain.materialdesign.widget.SettingUtil
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

/**
 * Author:rain
 * Date:2019/1/22 10:02
 * Description:
 */
class SettingsActivity:BaseActivity(),ColorChooserDialog.ColorCallback {


    private val EXTRA_SHOW_FRAGMENT = "show_fragment"
    private val EXTRA_SHOW_FRAGMENT_ARGUMENTS = "show_fragment_args"
    private val EXTRA_SHOW_FRAGMENT_TITLE = "show_fragment_title"

    override fun start() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        val initFragment: String = intent.getStringExtra(EXTRA_SHOW_FRAGMENT) ?: ""
        val initArguments: Bundle = intent.getBundleExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS) ?: Bundle()
        val initTitle: String = intent.getStringExtra(EXTRA_SHOW_FRAGMENT_TITLE) ?: "设置"
        if (initFragment.isEmpty()) {
            setupFragment(SettingFragment::class.java.name, initArguments)
        } else {
            setupFragment(initFragment, initArguments)
        }
        initToolbar(toolbar,initTitle,true)
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_settings
    }

    private fun setupFragment(fragmentName: String, args: Bundle) {
        val fragment = Fragment.instantiate(this, fragmentName, args)
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.replace(R.id.container, fragment)
        transaction.commitAllowingStateLoss()
    }

    fun startWithFragment(fragmentName: String, args: Bundle?,
                          resultTo: Fragment?, resultRequestCode: Int, title: String?) {
        val intent = onBuildStartFragmentIntent(fragmentName, args, title)
        if (resultTo == null) {
            startActivity(intent)
        } else {
            resultTo.startActivityForResult(intent, resultRequestCode)
        }
    }

    private fun onBuildStartFragmentIntent(fragmentName: String, args: Bundle?, title: String?): Intent {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.setClass(this, javaClass)
        intent.putExtra(EXTRA_SHOW_FRAGMENT, fragmentName)
        intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, args)
        intent.putExtra(EXTRA_SHOW_FRAGMENT_TITLE, title)
        return intent
    }

    override fun onColorSelection(dialog: ColorChooserDialog, selectedColor: Int) {
        if (!dialog.isAccentMode) {
            SettingUtil.setColor(selectedColor)
        }
        initThemeColor()
        EventBus.getDefault().post(ColorEvent(true))
    }

    override fun onColorChooserDismissed(dialog: ColorChooserDialog) {
    }

}