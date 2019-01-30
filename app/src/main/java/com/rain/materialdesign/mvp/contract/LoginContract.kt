package com.rain.materialdesign.mvp.contract

import com.rain.materialdesign.base.IPresenter
import com.rain.materialdesign.base.IView
import com.rain.materialdesign.mvp.model.entity.UserInfo

/**
 * Created by chenxz on 2018/5/27.
 */
interface LoginContract {

    interface View : IView {

        fun loginSuccess(data: UserInfo)

        fun loginFail()

    }

    interface Presenter : IPresenter<View> {

        fun loginWanAndroid(username: String, password: String)

    }
}