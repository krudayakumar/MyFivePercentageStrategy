package utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String.printTimes(times: Int){
    var ret:String =""
    for(i in 0..times  ) {
        ret+= this
    }
    println(ret)
}

fun String.toCalendar():Calendar{
    val cal = Calendar.getInstance()
    try {
        cal.time =  SimpleDateFormat("yyyy-MM-dd").parse(this) as Date
    } catch (e: ParseException) {
        println("Exception :$e")
    }
    return cal
}

