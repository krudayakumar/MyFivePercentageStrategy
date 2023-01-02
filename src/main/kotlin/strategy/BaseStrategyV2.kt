package strategy

import model.DataV2
import utils.StringAlignUtils
import utils.printTimes
import utils.toCalendar
import yahoofinance.YahooFinance
import yahoofinance.histquotes.Interval
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


open class BaseStrategyV2(private val builder: BaseBuilder) {
    var data = mutableListOf<DataV2>()

    init {
        val google = YahooFinance.get(builder.ticker)
        val result = google.getHistory(builder.startDt.toCalendar(), builder.endDt.toCalendar(), builder.interval)
        result?.let {
            it.forEach {
                var dataV2 = DataV2()
                with(it) {
                    dataV2.symbol = symbol
                    dataV2.date = date
                    dataV2.open = open.toDouble()
                    dataV2.low = low.toDouble()
                    dataV2.high = high.toDouble()
                    dataV2.close = close.toDouble()
                    dataV2.adjClose = adjClose.toDouble()
                    dataV2.volume = volume
                }
                data.add(dataV2)
            }
        }

    }

    fun totalNoStockPurchased(): Int {
        var totalStock = 0
        data.filter { it.noStockPurchase != 0 }.map { totalStock += it.noStockPurchase }
        return totalStock
    }

    fun totalValue(): Double {
        var totalAmt = 0.0
        data.filter { it.noStockPurchase != 0 }.map { totalAmt += it.noStockPurchase * it.close }
        return totalAmt
    }

    fun toPrint() {
        val df = DecimalFormat("#,##,###.##")
        df.roundingMode = RoundingMode.DOWN
        val maxColumn = 80

        "=".printTimes(maxColumn)
        println(
            StringAlignUtils(
                maxColumn,
                StringAlignUtils.Alignment.CENTER
            ).format(builder.ticker.toUpperCase() + " - Investment Details")
        )
        println(
            StringAlignUtils(
                maxColumn,
                StringAlignUtils.Alignment.CENTER
            ).format("(" + builder.startDt + "/" + builder.endDt + ")")
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
                    StringAlignUtils(20, StringAlignUtils.Alignment.RIGHT).format(totalNoStockPurchased()) +
                    StringAlignUtils(
                        20,
                        StringAlignUtils.Alignment.RIGHT
                    ).format(df.format(totalValue() / totalNoStockPurchased())) +
                    StringAlignUtils(
                        20,
                        StringAlignUtils.Alignment.RIGHT
                    ).format(df.format(data.get(data.size - 1).close))
        )
        println("");
        val leftPos = 50
        val rightPos = 30
        val currValue = data.get(data.size - 1).close * totalNoStockPurchased()
        val avgValue = totalValue() / totalNoStockPurchased() * totalNoStockPurchased()

        println(
            StringAlignUtils(leftPos, StringAlignUtils.Alignment.LEFT).format("Curr. Value") +
                    StringAlignUtils(1, StringAlignUtils.Alignment.LEFT).format(":") +
                    StringAlignUtils(rightPos, StringAlignUtils.Alignment.RIGHT).format(df.format(currValue))
        )

        println(
            StringAlignUtils(leftPos, StringAlignUtils.Alignment.LEFT).format("Avg. Value") +
                    StringAlignUtils(1, StringAlignUtils.Alignment.LEFT).format(":") +
                    StringAlignUtils(rightPos, StringAlignUtils.Alignment.RIGHT).format(df.format(avgValue))
        )

        println(
            StringAlignUtils(leftPos, StringAlignUtils.Alignment.LEFT).format("P/L") +
                    StringAlignUtils(1, StringAlignUtils.Alignment.LEFT).format(":") +
                    StringAlignUtils(rightPos, StringAlignUtils.Alignment.RIGHT).format(df.format(currValue - avgValue))
        )

        println(
            StringAlignUtils(leftPos, StringAlignUtils.Alignment.LEFT).format("P/L %") +
                    StringAlignUtils(1, StringAlignUtils.Alignment.LEFT).format(":") +
                    StringAlignUtils(
                        rightPos,
                        StringAlignUtils.Alignment.RIGHT
                    ).format(df.format(((currValue - avgValue) / avgValue) * 100))
        )


        println(
            StringAlignUtils(leftPos, StringAlignUtils.Alignment.LEFT).format("Tot No.Trad days") +
                    StringAlignUtils(1, StringAlignUtils.Alignment.LEFT).format(":") +
                    StringAlignUtils(rightPos, StringAlignUtils.Alignment.RIGHT).format(df.format(data.size))
        )

        println(
            StringAlignUtils(leftPos, StringAlignUtils.Alignment.LEFT).format("Tot No.Purchases") +
                    StringAlignUtils(1, StringAlignUtils.Alignment.LEFT).format(":") +
                    StringAlignUtils(
                        rightPos,
                        StringAlignUtils.Alignment.RIGHT
                    ).format(df.format(data.filter { it.noStockPurchase != 0 }.size))
        )
        "=".printTimes(maxColumn)
    }

    open class BaseBuilder(
        var ticker: String = "",
        var startDt: String = "",
        var endDt: String = "",
        var interval: Interval = Interval.DAILY
    )
}
