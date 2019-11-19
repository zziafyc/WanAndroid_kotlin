package com.zziafyc.wanandroidkotlin.api

import com.zziafyc.wanandroidkotlin.mvp.model.bean.Article
import com.zziafyc.wanandroidkotlin.mvp.model.bean.ArticleResponseBody
import com.zziafyc.wanandroidkotlin.mvp.model.bean.Banner
import com.zziafyc.wanandroidkotlin.mvp.model.bean.HttpResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 *
 * @作者 zziafyc
 * @创建日期 2019/11/12 0012
 * @description
 */
interface ApiService {
    /**
     * 获取轮播图
     */
    @GET("banner/json")
    fun getBanners(): Observable<HttpResult<List<Banner>>>

    /**
     * 获取首页置顶文章
     */
    @GET("article/top/json")
    fun getTopArticles(): Observable<HttpResult<MutableList<Article>>>

    /**
     * 获取文章列表
     */
    @GET("article/list/{pageNum}/json")
    fun getArticles(@Path("pageNum") pageNum: Int): Observable<HttpResult<ArticleResponseBody>>

    /**
     * 收藏站内文章
     * @param id
     */
    @POST("lg/collect/{id}/json")
    fun addCollectArticle(@Path("id") id: Int): Observable<HttpResult<Any>>

    /**
     * 取消收藏文章
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun cancelCollectArticle(@Path("id") id: Int): Observable<HttpResult<Any>>
}