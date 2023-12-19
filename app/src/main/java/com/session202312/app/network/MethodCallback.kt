package com.session202312.app.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//MethodCallback.kt
class MethodCallback {

    companion object {
        //GeneralResponse Interface
        //OkResponse
        //ErrorResponse
        inline fun <G, O : G, reified E : G> generalCallback(crossinline callback: (response: G?) -> Unit): Callback<O> {

            return object : Callback<O> {
                override fun onFailure(call: Call<O>, t: Throwable) {
                    Log.e("MethodCallback", "Failed API call with call: $call exception: $t")
                    callback(null)
                }

                override fun onResponse(call: Call<O>, response: Response<O>) {

                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        if (response.errorBody() != null) {

                            when (response.code()) {
                                400 -> {
                                    val errorBody: E? =
                                        ErrorUtils.getErrorResponse<E>(response.errorBody()!!)

                                    if (errorBody != null) {
                                        callback(errorBody)
                                    } else {
                                        callback(null)
                                    }
                                }
                                else -> {
                                    callback(null)
                                }
                            }
                        } else {
                            callback(null)
                        }
                    }
                }
            }
        }
    }

}