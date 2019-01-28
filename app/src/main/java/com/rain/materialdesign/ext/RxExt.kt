package com.rain.materialdesign.ext

import com.rain.materialdesign.App
import com.rain.materialdesign.R
import com.rain.materialdesign.base.BasePresenter
import com.rain.materialdesign.base.IView
import com.rain.materialdesign.mvp.model.entity.BaseResp
import com.rain.materialdesign.net.exception.ErrorStatus
import com.rain.materialdesign.net.exception.ExceptionHandle
import com.rain.materialdesign.net.function.RetryWithDelay
import com.rain.materialdesign.rx.SchedulerUtils
import com.rain.materialdesign.util.NetWorkUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author chenxz
 * @date 2018/11/22
 * @desc
 */

fun <T : BaseResp<T>> Observable<T>.ss(
    presenter: BasePresenter<IView>?,
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit
) {
    this.compose(SchedulerUtils.ioToMain())
            .retryWhen(RetryWithDelay())
            .subscribe(object : Observer<T> {
                override fun onComplete() {
                    view?.hideLoading()
                }

                override fun onSubscribe(d: Disposable) {
                    if (isShowLoading) {
                        view?.showLoading()
                    }
                    presenter?.addDisposable(d)
                    if (!NetWorkUtil.isNetworkConnected(App.INSTANCE)) {
                        view?.showDefaultMsg(App.INSTANCE.resources.getString(R.string.network_unavailable_tip))
                        onComplete()
                    }
                }

                override fun onNext(t: T) {
                    when {
                        t.errorCode == ErrorStatus.SUCCESS -> onSuccess.invoke(t)
                        t.errorCode == ErrorStatus.TOKEN_INVAILD -> {
                            // Token 过期，重新登录
                        }
                        else -> view?.showDefaultMsg(t.errorMsg)
                    }
                }

                override fun onError(t: Throwable) {
                    view?.hideLoading()
                    view?.showError(ExceptionHandle.handleException(t))
                }
            })
}

fun <T : BaseResp<T>> Observable<T>.sss(
        view: IView?,
        isShowLoading: Boolean = true,
        onSuccess: (T) -> Unit
): Disposable {
    if (isShowLoading) {
        view?.showLoading()
    }
    return this.compose(SchedulerUtils.ioToMain())
            .retryWhen(RetryWithDelay())
            .subscribe({
                when {
                    it.errorCode == ErrorStatus.SUCCESS -> onSuccess.invoke(it)
                    it.errorCode == ErrorStatus.TOKEN_INVAILD -> {
                        // Token 过期，重新登录
                    }
                    else -> view?.showDefaultMsg(it.errorMsg)
                }
                view?.hideLoading()
            }, {
                view?.hideLoading()
                view?.showError(ExceptionHandle.handleException(it))
            })
}

