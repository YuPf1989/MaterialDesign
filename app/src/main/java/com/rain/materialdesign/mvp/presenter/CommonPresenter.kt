package com.rain.materialdesign.mvp.presenter

import com.rain.materialdesign.base.BasePresenter
import com.rain.materialdesign.base.IView
import com.rain.materialdesign.ext.ss
import com.rain.materialdesign.mvp.contract.CommonContract
import com.rain.materialdesign.mvp.model.api.ApiService
import com.rain.materialdesign.net.RetrofitHelper

/**
 * Created by chenxz on 2018/6/10.
 */
open class CommonPresenter<V : CommonContract.View>
    : BasePresenter<V>(), CommonContract.Presenter<V> {

    override fun addCollectArticle(id: Int) {
        RetrofitHelper.createApi(ApiService::class.java)
            .addCollectArticle(id)
            .ss(this as BasePresenter<IView>, mView, false) {
                mView?.showCollectSuccess(true)
            }
    }

    override fun cancelCollectArticle(id: Int) {
        RetrofitHelper.createApi(ApiService::class.java)
            .removeCollectArticle(id)
            .ss(this as BasePresenter<IView>, mView, false) {
                mView?.showCancelCollectSuccess(true)
            }
    }
}