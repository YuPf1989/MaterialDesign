package com.rain.materialdesign.mvp.model.api

import com.rain.materialdesign.mvp.model.entity.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Author:rain
 * Date:2019/1/24 16:40
 * Description:
 */
interface ApiService {
    /**
     * 主页
     */
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") num: Int): Observable<HomePageArticleBean>

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<UserInfo>

    /**
     * banner
     */
    @GET("banner/json")
    fun getBannerList(): Observable<List<Banner>>

    /**
     * 获取 我的收藏列表
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollectionList(@Path("page") page: Int): Observable<CollectionResponseBody<CollectionArticle>>
}