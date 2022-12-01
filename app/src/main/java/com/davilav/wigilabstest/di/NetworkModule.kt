package com.davilav.wigilabstest.di

import com.davilav.wigilabstest.BuildConfig
import com.davilav.wigilabstest.data.remote.ApiInterface
import com.davilav.wigilabstest.data.remote.calladapter.NetworkResponseAdapterFactory
import com.davilav.wigilabstest.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL: String = Constants.BASE_URL

val networkModule = module {
    factory { provideInterceptor() }
    factory { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    factory { provideClientApi(get()) }
}

fun provideInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
    }
    return interceptor
}

fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .build()
}

fun provideClientApi(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)