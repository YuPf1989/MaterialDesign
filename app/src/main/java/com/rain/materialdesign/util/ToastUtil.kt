package com.rain.materialdesign.util

import android.widget.Toast
import com.rain.materialdesign.App

/**
 * Author:rain
 * Date:2018/11/19 9:56
 * Description:
 */
class ToastUtil {

    companion object {
        private var mToast: Toast? = null

        fun showToast(charSequence: CharSequence) {
            val duration = Toast.LENGTH_SHORT
            val application = App.INSTANCE
            if (mToast == null) {
                mToast = Toast.makeText(application, charSequence, duration)
            } else {
                mToast!!.setText(charSequence)
                mToast!!.duration = duration
            }
            mToast!!.show()
        }
    }

}