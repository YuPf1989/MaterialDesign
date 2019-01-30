package com.rain.materialdesign.mvp.contract

import com.rain.materialdesign.mvp.model.entity.Banner
import com.rain.materialdesign.mvp.model.entity.HomePageArticleBean

/**
 * Created by chenxz on 2018/4/21.
 */
interface HomeContract {

    interface View : CommonContract.View {

        fun scrollToTop()

        fun setBanner(banners: List<Banner>)

        fun setArticles(articles: HomePageArticleBean)

    }

    interface Presenter : CommonContract.Presenter<View> {

        fun requestBanner()

        fun requestHomeData()

        fun requestArticles(num: Int)

    }

}