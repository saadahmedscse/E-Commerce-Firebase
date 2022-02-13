package com.caffeine.ecommercefirebase.util

sealed class Task<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : Task<T>()
    class Success<T>(data: T? = null) : Task<T>(data = data)
    class Failed<T>(message: String? = null) : Task<T>(message = message)
}
