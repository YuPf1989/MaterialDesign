package com.rain.materialdesign.base

/**
 * Created by chenxz on 2018/4/21.
 */
interface IPresenter< V : IView> {

    /**
     * 绑定 View
     */
    fun attachView(mView: V)

    /**
     * 解绑 View
     */
    fun detachView()

}