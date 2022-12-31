package model

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyDetails (
    @SerializedName( "Date")
    val date: String,

    @SerializedName("Open")
    val open: Double,

    @SerializedName("High")
    val high: Double,

    @SerializedName( "Low")
    val low: Double,

    @SerializedName("Close")
    val close: Double,

    @SerializedName( "Adj Close")
    val adjClose: Double,

    @SerializedName("Volume")
    val volume: Long
){
    var orgDate: Date = Date()
    var isPurchased: Boolean = false
}