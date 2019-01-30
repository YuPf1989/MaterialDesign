package com.rain.materialdesign.mvp.presenter

import com.rain.materialdesign.base.BasePresenter
import com.rain.materialdesign.base.IView
import com.rain.materialdesign.ext.ss
import com.rain.materialdesign.mvp.contract.CommonContract
import com.rain.materialdesign.mvp.contract.HomeContract
import com.rain.materialdesign.mvp.model.api.ApiService
import com.rain.materialdesign.net.RetrofitHelper

/**
 * Author:rain
 * Date:2019/1/30 15:44
 * Description:
 */
class HomePresenter : CommonPresenter<HomeContract.View>(), HomeContract.Presenter {

    override fun requestBanner() {
        RetrofitHelper.createApi(ApiService::class.java)
            .getBannerList()
            .ss(this as BasePresenter<IView>, mView) {
                mView?.setBanner(it.data)
            }
    }

    override fun requestHomeData() {
        requestBanner()
        requestArticles(0)
    }

    override fun requestArticles(num: Int) {
        RetrofitHelper.createApi(ApiService::class.java)
            .getArticleList(num)
            .ss(this as BasePresenter<IView>, mView, num == 0) {
                mView?.setArticles(it.data)
            }
    }
}