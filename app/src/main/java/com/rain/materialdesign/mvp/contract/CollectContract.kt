package com.rain.materialdesign.mvp.contract

import com.rain.materialdesign.base.IPresenter
import com.rain.materialdesign.base.IView
import com.rain.materialdesign.mvp.model.entity.CollectionArticle
import com.rain.materialdesign.mvp.model.entity.CollectionResponseBody

/**
 * Created by chenxz on 2018/6/9.
 */
interface CollectContract {

    interface View : IView {

        fun setCollectList(articles: CollectionResponseBody<CollectionArticle>)

        fun showRemoveCollectSuccess(success: Boolean)

        fun scrollToTop()

    }

    interface Presenter : IPresenter<View> {

        fun getCollectList(page: Int)

        fun removeCollectArticle(id: Int, originId: Int)

    }

}