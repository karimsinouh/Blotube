package com.example.blotube.util

fun <T> List<T>.getFive():List<T>{

    if(isEmpty())
        return emptyList()

    if (size>5)
        return subList(0,5)

    return this
}