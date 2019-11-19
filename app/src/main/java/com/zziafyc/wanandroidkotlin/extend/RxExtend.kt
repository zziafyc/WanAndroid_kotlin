package com.zziafyc.wanandroidkotlin.extend

import com.zziafyc.wanandroidkotlin.base.IModel
import com.zziafyc.wanandroidkotlin.base.IView
import com.zziafyc.wanandroidkotlin.http.exception.ErrorStatus
import com.zziafyc.wanandroidkotlin.http.exception.ExceptionHandle
import com.zziafyc.wanandroidkotlin.http.function.RetryWithDelay
import com.zziafyc.wanandroidkotlin.mvp.model.bean.BaseBean
import com.zziafyc.wanandroidkotlin.rx.SchedulerUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author chenxz
 * @date 2018/11/22
 * @desc
 */

fun <T : BaseBean> Observable<T>.ss(
    model: IModel?,
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
                    if (isShowLoading) view?.showLoading()
                    model?.addDisposable(d)
                }

                override fun onNext(t: T) {
                    when {
                        t.errorCode == ErrorStatus.SUCCESS -> onSuccess.invoke(t)
                        t.errorCode == ErrorStatus.TOKEN_INVALID -> {
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

fun <T : BaseBean> Observable<T>.sss(
        view: IView?,
        isShowLoading: Boolean = true,
        onSuccess: (T) -> Unit
): Disposable {
    if (isShowLoading) view?.showLoading()
    return this.compose(SchedulerUtils.ioToMain())
            .retryWhen(RetryWithDelay())
            .subscribe({
                when {
                    it.errorCode == ErrorStatus.SUCCESS -> onSuccess.invoke(it)
                    it.errorCode == ErrorStatus.TOKEN_INVALID -> {
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

