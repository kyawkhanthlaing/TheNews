package com.kkcoding.core.exts

fun String?.unknownErrorOnNull() = if (this.isNullOrEmpty()) "Something went wrong!" else this