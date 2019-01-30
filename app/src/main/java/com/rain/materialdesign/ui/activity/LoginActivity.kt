package com.rain.materialdesign.ui.activity

import android.os.Bundle
import android.view.View
import com.rain.materialdesign.R
import com.rain.materialdesign.base.BaseMvpActivity
import com.rain.materialdesign.event.LoginEvent
import com.rain.materialdesign.ext.loge
import com.rain.materialdesign.mvp.contract.LoginContract
import com.rain.materialdesign.mvp.model.entity.UserInfo
import com.rain.materialdesign.mvp.presenter.LoginPresenter
import com.rain.materialdesign.util.Constant
import com.rain.materialdesign.util.DialogUtil
import com.rain.materialdesign.util.Preference
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

/**
 * Author:rain
 * Date:2019/1/28 15:04
 * Description:
 */
class LoginActivity : BaseMvpActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {


    /**
     * local username
     */
    private var user: String by Preference(Constant.USERNAME_KEY, "")

    /**
     * local password
     */
    private var pwd: String by Preference(Constant.PASSWORD_KEY, "")

    override fun createPresenter(): LoginContract.Presenter = LoginPresenter()

    override fun useEventBus(): Boolean = false

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.login_ing))
    }

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }

    override fun attachLayoutRes(): Int = R.layout.activity_login

    override fun start() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        et_username.setText(user)
        btn_login.setOnClickListener(onClickListener)
        tv_sign_up.setOnClickListener(onClickListener)
    }

    /**
     * OnClickListener
     */
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_login -> {
                login()
            }
            R.id.tv_sign_up -> {
//                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
//                startActivity(intent)
//                finish()
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    /**
     * Login
     */
    private fun login() {
        if (validate()) {
            mPresenter?.loginWanAndroid(et_username.text.toString(), et_password.text.toString())
        }
    }

    /**
     * Check UserName and PassWord
     */
    private fun validate(): Boolean {
        var valid = true
        val username: String = et_username.text.toString()
        val password: String = et_password.text.toString()

        if (username.isEmpty()) {
            et_username.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            et_password.error = getString(R.string.password_not_empty)
            valid = false
        }
        return valid

    }

    override fun loginSuccess(data: UserInfo) {
        loge(data.toString())
        isLogin = true
        user = data.username
        pwd = data.password

        EventBus.getDefault().post(LoginEvent(true))
        finish()
    }

    override fun loginFail() {
    }

}