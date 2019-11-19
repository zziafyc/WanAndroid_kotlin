package com.zziafyc.wanandroidkotlin.mvp.contract.common

import com.zziafyc.wanandroidkotlin.base.IModel
import com.zziafyc.wanandroidkotlin.base.IPresenter
import com.zziafyc.wanandroidkotlin.base.IView
import com.zziafyc.wanandroidkotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 *
 * @作者 zziafyc
 * @创建日期 2019/11/17 0017
 * @description 添加公共的契约接口
 */

interface CommonContract {

    interface View : IView {

        fun showCollectSuccess(success: Boolean)

        fun showCancelCollectSuccess(success: Boolean)
    }

    interface Presenter<in V : View> : IPresenter<V> {

        fun addCollectArticle(id: Int)

        fun cancelCollectArticle(id: Int)

    }

    interface Model : IModel {

        fun addCollectArticle(id: Int): Observable<HttpResult<Any>>

        fun cancelCollectArticle(id: Int): Observable<HttpResult<Any>>

    }

}