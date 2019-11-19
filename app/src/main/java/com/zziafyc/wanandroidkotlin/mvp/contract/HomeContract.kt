package com.zziafyc.wanandroidkotlin.mvp.contract

import com.zziafyc.wanandroidkotlin.mvp.contract.common.CommonContract
import com.zziafyc.wanandroidkotlin.mvp.model.bean.Article
import com.zziafyc.wanandroidkotlin.mvp.model.bean.ArticleResponseBody
import com.zziafyc.wanandroidkotlin.mvp.model.bean.Banner
import com.zziafyc.wanandroidkotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable

interface HomeContract {

    interface View : CommonContract.View {

        fun scrollToTop()

        fun setBanner(banners: List<Banner>)

        fun setArticles(articles: ArticleResponseBody)
    }

    interface Presenter : CommonContract.Presenter<View> {

        fun requestBanner()

        fun requestArticles(num: Int)

        fun requestHomeData()
    }

    interface Model : CommonContract.Model {

        fun requestBanner(): Observable<HttpResult<List<Banner>>>

        fun requestTopArticles(): Observable<HttpResult<MutableList<Article>>>

        fun requestArticles(num: Int): Observable<HttpResult<ArticleResponseBody>>
    }
}