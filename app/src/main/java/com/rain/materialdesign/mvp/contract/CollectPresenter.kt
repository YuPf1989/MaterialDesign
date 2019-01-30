package com.rain.materialdesign.mvp.contract

import com.rain.materialdesign.base.BasePresenter
import com.rain.materialdesign.base.IView
import com.rain.materialdesign.ext.ss
import com.rain.materialdesign.mvp.model.api.ApiService
import com.rain.materialdesign.net.RetrofitHelper

/**
 * Author:rain
 * Date:2019/1/30 10:56
 * Description:
 */
class CollectPresenter:BasePresenter<CollectContract.View>(),CollectContract.Presenter {
    override fun getCollectList(page: Int) {
        RetrofitHelper.createApi(ApiService::class.java)
            .getCollectionList(page)
            .ss(this as BasePresenter<IView>,mView){
                mView?.setCollectList(it.data)
            }

    }

    override fun removeCollectArticle(id: Int, originId: Int) {
        RetrofitHelper.createApi(ApiService::class.java)
            .removeCollectArticle(id,originId)
            .ss(this as BasePresenter<IView>,mView){
                mView?.showRemoveCollectSuccess(true)
            }
    }
}