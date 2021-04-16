package com.example.blotube.util

import kotlin.math.abs

object Formatter {

    fun statistics(n:Int?):String{
        val number=n?:0
        return when {
            abs(number/1000)>1 ->abs(number / 1000).toString()+"K"
            abs(number/1000000)>1 -> abs(number/1000000).toString()+"M"
            abs(number/1000000000)>1 ->  abs(number/1000000000).toString()+"B"
            else -> number.toString()
        }
    }


}