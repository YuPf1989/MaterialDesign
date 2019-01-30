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
    fun getArticleList(@Path("page") num: Int): Observable<BaseResp<HomePageArticleBean>>

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<BaseResp<UserInfo>>

    /**
     * banner
     */
    @GET("banner/json")
    fun getBannerList(): Observable<BaseResp<List<Banner>>>

    /**
     * 获取 我的收藏列表
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollectionList(@Path("page") page: Int): Observable<BaseResp<CollectionResponseBody<CollectionArticle>>>

    /**
     * 收藏列表中取消收藏文章
     * http://www.wanandroid.com/lg/uncollect/2805/json
     * @param id
     * @param originId
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    fun removeCollectArticle(@Path("id") id: Int,
                             @Field("originId") originId: Int = -1): Observable<BaseResp<Any>>

    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     * @param id article id
     */
    @POST("lg/collect/{id}/json")
    fun addCollectArticle(@Path("id") id: Int): Observable<BaseResp<Any>>
}