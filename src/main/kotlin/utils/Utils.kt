package utils

import com.google.gson.Gson
import model.Daily
import java.io.File
import java.text.SimpleDateFormat


fun String.printTimes(times: Int){
    var ret:String =""
    for(i in 0..times  ) {
        ret+= this
    }
    println(ret)
}

