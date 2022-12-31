package strategy

import com.google.gson.Gson
import model.Daily
import model.DailyDetails
import utils.StringAlignUtils
import utils.printTimes
import java.io.File
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat

abstract class BaseStrategy {
    protected var data = arrayListOf<DailyDetails>()
    protected var filtereddata = listOf<DailyDetails>()
    protected var totalAmt = 0.0
    protected var totalStock = 0
    protected var noTimesPurchased = 0
    protected var currValue = 0.0
    protected var avgValue = 0.0
    protected var startDt = ""
    protected var endDt = ""

    constructor(fileName: String) {
        readFileAsTextUsingInputStream(fileName)
    }

    private fun readFileAsTextUsingInputStream(fileName: String) {
        val fileContent = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)
        val daily = Gson().fromJson(fileContent, Daily::class.java)
        data = daily.data
        data.forEach {
            it.orgDate = SimpleDateFormat("yyyy-MM-dd").parse(it.date)
        }
        val dt = data.sortedBy { it.orgDate }
        startDt = dt.get(0).date
        endDt = dt.get(dt.size - 1).date
        filter(startDt, endDt)
    }

    fun filter(start:String, end:String):BaseStrategy{
        startDt = start
        endDt = endDt
        val startDt = SimpleDateFormat("yyyy-MM-dd").parse(startDt)
        val endDt= SimpleDateFormat("yyyy-MM-dd").parse(endDt)
        filtereddata = data.stream().filter {
            it.orgDate.after(startDt) && it.orgDate.before(endDt)
        }.toList()
        return  this
    }

    fun getData() = filtereddata

    abstract fun printSummary()

    protected fun _printSummary() {
        val df = DecimalFormat("#,##,###.##")
        df.roundingMode = RoundingMode.DOWN
        val maxColumn = 80

        "=".printTimes(maxColumn)
        println(StringAlignUtils(maxColumn, StringAlignUtils.Alignment.CENTER).format("Investment Details"))
        println(
            StringAlignUtils(
                maxColumn,
                StringAlignUtils.Alignment.CENTER
            ).format("(" + startDt + "/" + endDt + ")")
        )
        "=".printTimes(maxColumn)
        println(
            StringAlignUtils(20, StringAlignUtils.Alignment.CENTER).format("  ") +
                    StringAlignUtils(20, StringAlignUtils.Alignment.RIGHT).format("Stock.Pur") +
                    StringAlignUtils(20, StringAlignUtils.Alignment.RIGHT).format("Avg.Price") +
                    StringAlignUtils(20, StringAlignUtils.Alignment.RIGHT).format("Curr.Price")
        )

        println(
            StringAlignUtils(20, StringAlignUtils.Alignment.CENTER).format("  ") +
                    StringAlignUtils(20, StringAlignUtils.Alignment.RIGHT).format(totalStock) +
                    StringAlignUtils(20, StringAlignUtils.Alignment.RIGHT).format(df.format(totalAmt / totalStock)) +
                    StringAlignUtils(
                        20,
                        StringAlignUtils.Alignment.RIGHT
                    ).format(df.format(data.get(data.size - 1).close))
        )
        println("");
        val leftPos = 50
        val rightPos = 30
        println(
            StringAlignUtils(leftPos, StringAlignUtils.Alignment.RIGHT).format("Curr. Value") +
                    StringAlignUtils(1, StringAlignUtils.Alignment.LEFT).format(":") +
                    StringAlignUtils(rightPos, StringAlignUtils.Alignment.RIGHT).format(df.format(currValue))
        )

        println(
            StringAlignUtils(leftPos, StringAlignUtils.Alignment.RIGHT).format("Avg. Value") +
                    StringAlignUtils(1, StringAlignUtils.Alignment.LEFT).format(":") +
                    StringAlignUtils(rightPos, StringAlignUtils.Alignment.RIGHT).format(df.format(avgValue))
        )

        println(
            StringAlignUtils(leftPos, StringAlignUtils.Alignment.RIGHT).format("P/L") +
                    StringAlignUtils(1, StringAlignUtils.Alignment.LEFT).format(":") +
                    StringAlignUtils(rightPos, StringAlignUtils.Alignment.RIGHT).format(df.format(currValue - avgValue))
        )

        println(
            StringAlignUtils(leftPos, StringAlignUtils.Alignment.RIGHT).format("P/L %") +
                    StringAlignUtils(1, StringAlignUtils.Alignment.LEFT).format(":") +
                    StringAlignUtils(
                        rightPos,
                        StringAlignUtils.Alignment.RIGHT
                    ).format(df.format(((currValue - avgValue) / avgValue) * 100))
        )


        println(
            StringAlignUtils(leftPos, StringAlignUtils.Alignment.RIGHT).format("Tot No.Trad days") +
                    StringAlignUtils(1, StringAlignUtils.Alignment.LEFT).format(":") +
                    StringAlignUtils(rightPos, StringAlignUtils.Alignment.RIGHT).format(df.format(filtereddata.size))
        )

        println(
            StringAlignUtils(leftPos, StringAlignUtils.Alignment.RIGHT).format("Tot No.Purchases") +
                    StringAlignUtils(1, StringAlignUtils.Alignment.LEFT).format(":") +
                    StringAlignUtils(rightPos, StringAlignUtils.Alignment.RIGHT).format(df.format(noTimesPurchased))
        )
        "=".printTimes(maxColumn)
    }
}