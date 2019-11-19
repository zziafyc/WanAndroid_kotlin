package com.zziafyc.wanandroidkotlin.mvp.model

import com.zziafyc.wanandroidkotlin.http.RetrofitHelper
import com.zziafyc.wanandroidkotlin.mvp.contract.HomeContract
import com.zziafyc.wanandroidkotlin.mvp.model.bean.Article
import com.zziafyc.wanandroidkotlin.mvp.model.bean.ArticleResponseBody
import com.zziafyc.wanandroidkotlin.mvp.model.bean.Banner
import com.zziafyc.wanandroidkotlin.mvp.model.bean.HttpResult
import com.zziafyc.wanandroidkotlin.mvp.model.common.CommonModel
import io.reactivex.Observable

class HomeModel : CommonModel(), HomeContract.Model {
    override fun requestBanner(): Observable<HttpResult<List<Banner>>> {
        return RetrofitHelper.service.getBanners()
    }

    override fun requestTopArticles(): Observable<HttpResult<MutableList<Article>>> {
        return RetrofitHelper.service.getTopArticles()
    }

    override fun requestArticles(pageNum: Int): Observable<HttpResult<ArticleResponseBody>> {
        return RetrofitHelper.service.getArticles(pageNum)
    }

}