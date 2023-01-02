package model

import com.google.gson.Gson
import yahoofinance.histquotes.HistoricalQuote
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class DataV2 {
   var symbol: String = ""
   var date: Calendar = Calendar.getInstance()
   var open: Double = 0.0
   var low: Double = 0.0
   var high: Double = 0.0
   var close: Double = 0.0
   var adjClose: Double = 0.0
   var volume: Long = 0
   var noStockPurchase = 0

   override fun toString():String{
      return Gson().toJson(this).replace("{","").replace("}","")
   }
}
