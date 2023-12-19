package com.session202312.app.network

import okhttp3.ResponseBody

//ErrorUtils.kt
class ErrorUtils {
    companion object {
        inline fun <reified T> getErrorResponse(errorBody: ResponseBody): T? {
            return RetrofitService.retrofit.responseBodyConverter<T>(
                T::class.java,
                T::class.java.annotations
            ).convert(errorBody)
        }
    }
}