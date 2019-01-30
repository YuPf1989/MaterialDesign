package com.rain.materialdesign.mvp.contract

import com.rain.materialdesign.base.IPresenter
import com.rain.materialdesign.base.IView
import io.reactivex.Observable

/**
 * Created by chenxz on 2018/6/10.
 */
interface CommonContract {

    interface View : IView {

        fun showCollectSuccess(success: Boolean)

        fun showCancelCollectSuccess(success: Boolean)
    }

    interface Presenter<V : View> : IPresenter<V> {

        fun addCollectArticle(id: Int)

        fun cancelCollectArticle(id: Int)

    }

}