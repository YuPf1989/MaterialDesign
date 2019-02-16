package com.rain.materialdesign.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.support.v7.app.AlertDialog
import com.afollestad.materialdialogs.color.ColorChooserDialog
import com.rain.materialdesign.R
import com.rain.materialdesign.ui.activity.SettingsActivity
import com.rain.materialdesign.util.CacheDataUtil
import com.rain.materialdesign.ext.showToast
import com.rain.materialdesign.widget.IconPreference

/**
 * Author:rain
 * Date:2019/1/22 10:21
 * Description:
 */
class SettingFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var context: SettingsActivity? = null
    private lateinit var colorPreview: IconPreference

    companion object {
        fun getInstance() = SettingFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_setting2)
        setHasOptionsMenu(true)
        context = activity as SettingsActivity
        colorPreview = findPreference("color") as IconPreference
        setDefaultText()

        // 夜间模式
        findPreference("auto_nightMode").setOnPreferenceClickListener {
            context?.startWithFragment(AutoNightModeFragment::class.java.name,null,null,0,null)
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

        // 清理缓存
        findPreference("clearCache").onPreferenceClickListener = Preference.OnPreferenceClickListener {
            CacheDataUtil.clearAllCache(context!!)
            context?.showToast(getString(R.string.clear_cache_successfully))
            setDefaultText()
            false
        }


        // 当前版本
        val version = "当前版本 " + context?.packageManager?.getPackageInfo(context?.packageName, 0)?.versionName
        findPreference("version").summary = version

        // 源代码
        findPreference("sourceCode").onPreferenceClickListener = Preference.OnPreferenceClickListener {
            context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.source_code_url))))
            false
        }

        // 版权声明
        findPreference("copyRight").onPreferenceClickListener = Preference.OnPreferenceClickListener {
            AlertDialog.Builder(context!!)
                .setTitle(R.string.copyright)
                .setMessage(R.string.copyright_content)
                .setCancelable(true)
                .show()
            false
        }
    }

    private fun setDefaultText() {
        // 获取缓存大小
        findPreference("clearCache").summary = CacheDataUtil.getTotalCacheSize(context!!)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        key ?: return
        if (key == "color") {
            colorPreview.setView()
        }
    }
}