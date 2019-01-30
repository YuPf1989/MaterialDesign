package com.rain.materialdesign.mvp.presenter

import com.rain.materialdesign.base.BasePresenter
import com.rain.materialdesign.base.IView
import com.rain.materialdesign.ext.loge
import com.rain.materialdesign.ext.ss
import com.rain.materialdesign.mvp.contract.LoginContract
import com.rain.materialdesign.mvp.model.api.ApiService
import com.rain.materialdesign.net.RetrofitHelper

/**
 * Created by chenxz on 2018/5/27.
 */
class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun loginWanAndroid(username: String, password: String) {
        loge("loginWanAndroid")
        RetrofitHelper.createApi(ApiService::class.java)
            .login(username, password)
            .ss(this as BasePresenter<IView>,mView){
                mView?.loginSuccess(it.data)
            }
    }
}