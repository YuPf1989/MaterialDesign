package com.rain.materialdesign

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.rain.materialdesign.base.BaseActivity
import com.rain.materialdesign.util.ext.loge
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

/**
 * 专门用于material design风格的学习
 */
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        loge(TAG, "density:${resources.displayMetrics.density}")
        loge(TAG, "densityDpi:${resources.displayMetrics.densityDpi}")
        loge(TAG, "xdpi:${resources.displayMetrics.xdpi}")
        loge(TAG, "ydpi:${resources.displayMetrics.ydpi}")
        loge(TAG, "widthPixels:${resources.displayMetrics.widthPixels}")
        loge(TAG, "heightPixels:${resources.displayMetrics.heightPixels}")
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_camera -> {
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_setting -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
