package com.rain.materialdesign.ui.fragment

import android.os.Bundle
import android.preference.PreferenceFragment
import com.afollestad.materialdialogs.color.ColorChooserDialog
import com.rain.materialdesign.R
import com.rain.materialdesign.ui.activity.SettingsActivity
import com.rain.materialdesign.util.CacheDataUtil
import com.rain.materialdesign.util.widget.IconPreference

/**
 * Author:rain
 * Date:2019/1/22 10:21
 * Description:
 */
class SettingFragment : PreferenceFragment() {
    private var context: SettingsActivity? = null
    private lateinit var colorPreview: IconPreference

    companion object {
        fun getInstance() = SettingFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_setting)
        context = activity as SettingsActivity
        colorPreview = findPreference("color") as IconPreference
        setDefaultText()

        // 夜间模式
        findPreference("auto_nightMode").setOnPreferenceClickListener {
            // todo
            true
        }

        // 切换主题
        findPreference("color").setOnPreferenceClickListener {
            ColorChooserDialog.Builder(context!!, R.string.choose_theme_color)
                .backButton(R.string.back)
                .cancelButton(R.string.cancel)
                .doneButton(R.string.done)
                .customButton(R.string.custom)
                .presetsButton(R.string.back)
                .allowUserColorInputAlpha(false)
                .show()
            false
        }



        findPreference("")


    }


    private fun setDefaultText() {
        // 获取缓存大小
        findPreference("clearCache").summary = CacheDataUtil.getTotalCacheSize(context!!)

        // 当前版本
        val version = "当前版本 " + context?.packageManager?.getPackageInfo(context?.packageName, 0)?.versionName
        findPreference("version").summary = version
    }
}