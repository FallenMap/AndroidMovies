package com.davilav.wigilabstest.data

class Result<out T : Any>(val status: Status, val data: T?) {
    enum class Status {
        SUCCESS,
        ERROR,
    }

    companion object {
        fun <T : Any> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data)
        }

        fun <T : Any> error(data: T? = null): Result<T> {
            return Result(Status.ERROR, data)
        }
    }
}