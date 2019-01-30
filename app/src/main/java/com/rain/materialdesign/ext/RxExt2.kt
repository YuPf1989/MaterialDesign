package com.rain.materialdesign.ext

import com.rain.materialdesign.App
import com.rain.materialdesign.R
import com.rain.materialdesign.base.BasePresenter
import com.rain.materialdesign.base.IView
import com.rain.materialdesign.mvp.model.entity.BaseBean
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
 * 针对直接处理内层数据的情况
 */
fun <T> Observable<T>.ss2(
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
                    onSuccess.invoke(t)
                }

                override fun onError(t: Throwable) {
                    view?.hideLoading()
                    view?.showError(ExceptionHandle.handleException(t))
                }
            })
}

fun <T > Observable<T>.sss2(
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
                onSuccess.invoke(it)
                view?.hideLoading()
            }, {
                view?.hideLoading()
                view?.showError(ExceptionHandle.handleException(it))
            })
}

