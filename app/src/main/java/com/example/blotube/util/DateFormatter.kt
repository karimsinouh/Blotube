package com.example.blotube.util

import android.annotation.SuppressLint
import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun String?.asDate():String{
    if(this==null)
        return ""
    val prettyTime=PrettyTime()
    val dateFormatter=SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val time:Date=try{
        dateFormatter.parse(this)
    }catch (e: ParseException){
        return this
    }
    return prettyTime.format(time)
}