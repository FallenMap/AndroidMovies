package com.davilav.wigilabstest.data.remote.calladapter

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class NetworkResponseCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<NetworkResponse<S, E>> {
    override fun enqueue(callback: Callback<NetworkResponse<S, E>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall, Response.success(
                                NetworkResponse.Success(body)
                            )
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall, Response.success(
                                NetworkResponse.UnknownError(null)
                            )
                        )
                    }
                } else {
                    processUnsuccessfulResponse(response.errorBody(), response.code())
                }
            }

            private fun processUnsuccessfulResponse(error: ResponseBody?, code: Int) {
                var exception: Throwable? = null
                val errorBody = when {
                    error == null -> null
                    error.contentLength() == 0L -> null
                    else -> try {
                        errorConverter.convert(error)
                    } catch (ex: Exception) {
                        exception = ex
                        null
                    }
                }
                if (errorBody != null) {
                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(NetworkResponse.ApiError(errorBody, code))
                    )
                } else {
                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(NetworkResponse.UnknownError(exception))
                    )
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                callback.onResponse(
                    this@NetworkResponseCall,
                    Response.success(getFailureNetworkResponse(throwable))
                )
            }
        })
    }

    private fun getFailureNetworkResponse(throwable: Throwable): NetworkResponse<Nothing, Nothing> {
        return when (throwable) {
            is IOException -> NetworkResponse.NetworkError(throwable)
            else -> NetworkResponse.UnknownError(throwable)
        }
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<S, E>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}