package com.dsige.appapplus.data.local.repository

import com.dsige.appapplus.helper.MessageError
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

class ApiError(retrofit: Retrofit) {
    val errorConverter: Converter<ResponseBody, MessageError> =
        retrofit.responseBodyConverter(MessageError::class.java, arrayOfNulls<Annotation>(0))
}