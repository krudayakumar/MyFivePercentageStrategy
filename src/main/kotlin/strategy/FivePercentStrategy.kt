package strategy

import model.DailyDetails
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.function.Predicate
import kotlin.math.roundToInt

class FivePercentStrategy(val dataFile:String, val sipAmt:Double,val whenToInvestment:Double = 5.0):BaseStrategy(dataFile){

    fun processStrategy():List<DailyDetails>{
        var lastPurchasePrice:Double = filtereddata.get(0).close

        filtereddata.forEachIndexed { index, dailyDetails ->
            val diff = ((lastPurchasePrice - dailyDetails.close)/ dailyDetails.close)*100
            if(diff >= whenToInvestment || -whenToInvestment >= diff ){
                val noOfStocks = Math.floor(sipAmt/dailyDetails.close).roundToInt()
                totalStock += noOfStocks
                totalAmt += noOfStocks*dailyDetails.close
                noTimesPurchased++
                //println("last Price:" +  lastPurchasePrice + ", cur.Price:"+dailyDetails.close + ",diff.percentage" + diff+  ",noStockPurchase:" + noOfStocks )
                lastPurchasePrice = dailyDetails.close
                dailyDetails.isPurchased = true
            }
        }
        currValue = data.get(data.size-1).close * totalStock
        avgValue =  (totalAmt / totalStock)*totalStock
        return filtereddata
    }

    override fun printSummary() {
        super._printSummary()
    }
}