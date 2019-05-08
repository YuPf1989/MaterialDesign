package com.rain.materialdesign.mvp.model.entity

/**
 * Author:rain
 * Date:2018/11/15 17:31
 * Description:
 * 基本响应数据类
 * 对于restFul Api的处理有两种方式：
 * 1. 在Gson转换器中直接剥离内层数据，好处是api接口中不用加太多泛型，只关心最内层的数据
 * 2. 直接对整个数据进行处理，每个javabean都必须是完整的数据模型
 * 这里采用的是第二种方式
 */
data class BaseResp<T>(val data: T) : BaseBean() {
     fun isOK() = errorCode == 0
}

// 文章
data class HomePageArticleBean(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: List<ArticleBean>
)

// 文章
data class ArticleBean(
    val apkLink: String,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    var collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: MutableList<Tag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int,
    var top: String
)

data class Tag(
    val name: String,
    val url: String
)

// 轮播图
data class Banner(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)

// 登录数据
data class UserInfo(
    val chapterTops: MutableList<String>,
    val collectIds: MutableList<String>,
    val email: String,
    val icon: String,
    val id: Int,
    val password: String,
    val token: String,
    val type: Int,
    val username: String
)

// 收藏
data class CollectionResponseBody<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class CollectionArticle(
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val originId: Int,
    val publishTime: Long,
    val title: String,
    val userId: Int,
    val visible: Int,
    val zan: Int
)




