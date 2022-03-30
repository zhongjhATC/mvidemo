package com.zhongjh.mvidemo.data.http.retrofit

import com.blankj.utilcode.util.Utils
import com.zhongjh.mvilibrary.BuildConfig
import com.zhongjh.mvilibrary.http.HttpsUtils.sslSocketFactory
import com.zhongjh.mvilibrary.http.cookie.CookieJarImpl
import com.zhongjh.mvilibrary.http.cookie.store.PersistentCookieStore
import com.zhongjh.mvilibrary.http.interceptor.BaseInterceptor
import com.zhongjh.mvilibrary.http.interceptor.CacheInterceptor
import com.zhongjh.mvilibrary.http.log.Level
import com.zhongjh.mvilibrary.http.log.LoggingInterceptor
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform.Companion.INFO
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 * @author zhongjh
 * @date 2022/3/30
 *
 * 实现网络请求
 *
 * @param url     服务端根路径
 * @param headers 请求头
 */
class RetrofitClient private constructor(
    url: String = "https://www.wanandroid.com/",
    headers: Map<String, String>? = null
) {
    /**
     * retrofit本身
     */
    private val retrofit: Retrofit

    /**
     * 缓存
     */
    private var httpCacheDirectory: File? = null

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the `service` interface.
     */
    fun <T> create(service: Class<T>?): T {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit.create(service)
    }

    companion object {
        /**
         * 超时时间
         */
        private const val DEFAULT_TIMEOUT = 20

        private var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field = RetrofitClient()
                }
                return field
            }

        fun get(): RetrofitClient {
            return instance!!
        }

        /**
         * / **
         * execute your customer API
         * For example:
         * MyApiService service =
         * RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);
         *
         *
         * RetrofitClient.getInstance(MainActivity.this)
         * .execute(service.lgon("name", "password"), subscriber)
         * * @param subscriber
         */
        fun <T> execute(observable: Observable<T>, subscriber: Observer<T>?): T? {
            observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber!!)
            return null
        }
    }


    init {
        if (httpCacheDirectory == null) {
            httpCacheDirectory = File(Utils.getApp().cacheDir, "http_cache")
        }
        val okHttpClientBuild: OkHttpClient.Builder = OkHttpClient.Builder()
            .cookieJar(CookieJarImpl(PersistentCookieStore(Utils.getApp())))
            .addInterceptor(BaseInterceptor(headers))
            .addInterceptor(CacheInterceptor(Utils.getApp()))
            .addInterceptor(
                LoggingInterceptor.Builder() // 是否开启日志打印
                    .loggable(BuildConfig.DEBUG) // 打印的等级
                    .setLevel(Level.BASIC) // 打印类型
                    .log(INFO) // request的Tag
                    .request("Request") // Response的Tag
                    .response("Response") // 添加打印头, 注意 key 和 value 都不能是中文
                    .addHeader("log-header", "I am the log request header.")
                    .build()
            )
            .retryOnConnectionFailure(false)
            .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为15s
            .connectionPool(
                ConnectionPool(
                    8,
                    15,
                    TimeUnit.SECONDS
                )
            )
        val sslParams = sslSocketFactory
        if (sslParams.trustManager != null) {
            okHttpClientBuild.sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager!!)
        }
        retrofit = Retrofit.Builder()
            .client(okHttpClientBuild.build())
            //.addConverterFactory(CustomConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(url)
            .build()
    }
}