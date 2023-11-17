package com.salakheev.shaderbuilderkt.builder.util

fun Float.str(): String {
    val r = "$this"
    return if (r.contains(".")) r else "$r.0"
}