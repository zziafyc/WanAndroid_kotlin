package com.zziafyc.wanandroidkotlin.rx

import com.zziafyc.wanandroidkotlin.App
import com.zziafyc.wanandroidkotlin.base.IView
import com.zziafyc.wanandroidkotlin.http.exception.ErrorStatus
import com.zziafyc.wanandroidkotlin.http.exception.ExceptionHandle
import com.zziafyc.wanandroidkotlin.mvp.model.bean.BaseBean
import com.zziafyc.wanandroidkotlin.utils.NetWorkUtil
import io.reactivex.observers.ResourceObserver

/**
 * Created by chenxz on 2018/6/11.
 */
abstract class BaseObserver<T : BaseBean> : ResourceObserver<T> {

    private var mView: IView? = null
    private var mErrorMsg = ""
    private var bShowLoading = true

    constructor(view: IView) {
        this.mView = view
    }

    constructor(view: IView, bShowLoading: Boolean) {
        this.mView = view
        this.bShowLoading = bShowLoading
    }

    /**
     * 成功的回调
     */
    protected abstract fun onSuccess(t: T)

    /**
     * 错误的回调
     */
    protected fun onError(t: T) {}

    override fun onStart() {
        super.onStart()
        if (bShowLoading) mView?.showLoading()
        if (!NetWorkUtil.isNetworkConnected(App.instance)) {
            mView?.showDefaultMsg("当前网络不可用，请检查网络设置")
            onComplete()
        }
    }

    override fun onNext(t: T) {
        mView?.hideLoading()
        when {
            t.errorCode == ErrorStatus.SUCCESS -> onSuccess(t)
            t.errorCode == ErrorStatus.TOKEN_INVALID -> {
                // TODO Token 过期，重新登录
            }
            else -> {
                onError(t)
                if (t.errorMsg.isNotEmpty())
                    mView?.showDefaultMsg(t.errorMsg)
            }
        }
    }

    override fun onError(e: Throwable) {
        mView?.hideLoading()
        if (mView == null) {
            throw RuntimeException("mView can not be null")
        }
        if (mErrorMsg.isEmpty()) {
            mErrorMsg = ExceptionHandle.handleException(e)
        }
        mView?.showDefaultMsg(mErrorMsg)
    }

    override fun onComplete() {
        mView?.hideLoading()
    }
}

