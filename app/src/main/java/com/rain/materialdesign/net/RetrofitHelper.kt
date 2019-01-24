package com.rain.materialdesign.net

import android.annotation.SuppressLint
import com.rain.materialdesign.BaseApp
import com.rain.materialdesign.ext.getCookieJar
import com.rain.materialdesign.net.interceptor.CacheInterceptor
import com.rain.materialdesign.net.mygsonconvert.MyGsonConverterFactory
import com.rain.materialdesign.util.Constant
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager


/**
 * Author:rain
 * Date:2018/11/15 17:19
 * Description:
 * 默认单例模式
 */
object RetrofitHelper {
    const val DEFAULT_TIMEOUT: Long = 15
    const val MAX_CACHE_SIZE: Long = 1024 * 1024 * 50 // 50M 的缓存大小
    private val retrofit: Retrofit

    fun <T> createApi(service: Class<T>): T {
        return retrofit.create(service)
    }

    init {
        // 这里没有对客户端的证书进行校验，生产环境是不允许的
        val x509TrustManager = object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
        val trustManagerArray = arrayOf(x509TrustManager)
        val sslContext = SSLContext.getInstance("SSL").apply {
            init(null, trustManagerArray, SecureRandom())
        }

        // 设置请求的缓存的大小跟位置
        val cacheFile = File(BaseApp.INSTANCE.cacheDir, "cache")
        val cache = Cache(cacheFile, MAX_CACHE_SIZE)

        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .addInterceptor(CacheInterceptor())
            .cache(cache)
            .cookieJar(getCookieJar())
            .followRedirects(true)
            .retryOnConnectionFailure(true)
            .followSslRedirects(true)
            .sslSocketFactory(sslContext.socketFactory, trustManagerArray[0])
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true) // 错误重连
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MyGsonConverterFactory.create())
            .client(client)
            .build()
    }
}