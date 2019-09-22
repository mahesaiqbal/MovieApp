package com.mahesaiqbal.movieapp.data.source.remote

import com.mahesaiqbal.movieapp.data.source.remote.StatusResponse.*

class ApiResponse<T>(
    val status: StatusResponse,
    val body: T?,
    val message: String?
) {
    companion object {

        fun <T> success(body: T?): ApiResponse<T> {
            return ApiResponse(SUCCESS, body, null)
        }

        fun <T> error(msg: String, body: T?): ApiResponse<T> {
            return ApiResponse(ERROR, body, msg)
        }
    }
}
