package com.kkcoding.data.util

sealed class GenericResponse<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : GenericResponse<T>(data = data)

    class Error<T>(errorMessage: String) : GenericResponse<T>(message = errorMessage)

}
