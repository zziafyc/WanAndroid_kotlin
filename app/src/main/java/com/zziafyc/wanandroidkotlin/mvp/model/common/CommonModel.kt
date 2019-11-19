package com.zziafyc.wanandroidkotlin.mvp.model.common

import com.zziafyc.wanandroidkotlin.base.BaseModel
import com.zziafyc.wanandroidkotlin.http.RetrofitHelper
import com.zziafyc.wanandroidkotlin.mvp.contract.common.CommonContract
import com.zziafyc.wanandroidkotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable

open class CommonModel : BaseModel(), CommonContract.Model {
    override fun cancelCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.addCollectArticle(id)
    }

    override fun addCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.cancelCollectArticle(id)
    }
}