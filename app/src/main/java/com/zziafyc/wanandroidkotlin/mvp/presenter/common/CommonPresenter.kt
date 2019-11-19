package com.zziafyc.wanandroidkotlin.mvp.presenter.common

import com.zziafyc.wanandroidkotlin.base.BasePresenter
import com.zziafyc.wanandroidkotlin.extend.ss
import com.zziafyc.wanandroidkotlin.mvp.contract.common.CommonContract

/**
 * Created by chenxz on 2018/6/10.
 */
open class CommonPresenter<M : CommonContract.Model, V : CommonContract.View>
    : BasePresenter<M, V>(), CommonContract.Presenter<V> {

    override fun addCollectArticle(id: Int) {
        mModel?.addCollectArticle(id)?.ss(mModel, mView) {
            mView?.showCollectSuccess(true)
        }
    }

    override fun cancelCollectArticle(id: Int) {
        mModel?.cancelCollectArticle(id)?.ss(mModel, mView) {
            mView?.showCancelCollectSuccess(true)
        }
    }


}