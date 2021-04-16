package com.example.blotube.util


/**
this function rturns the first five items from a list,
 if it contains less than 5 items it will just return them
 */
fun <T> List<T>.getFive():List<T>{

    if(isEmpty())
        return emptyList()

    if (size>5)
        return subList(0,5)

    return this
}