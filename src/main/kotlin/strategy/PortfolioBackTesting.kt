package strategy

import yahoofinance.histquotes.Interval
import kotlin.math.roundToInt
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.io.FileOutputStream


class PortfolioBackTesting constructor(private val fsbuilder: Builder) : BaseStrategyV2(fsbuilder.toBaseBuilder()) {


    fun process(): PortfolioBackTesting {
        when (fsbuilder.strategy) {
            Strategy.MONTHLY -> return strategy_monthly()
            Strategy.FIVE_PERCENT -> return strategy_fivepercent()
        }
    }

    fun xlsReport(): PortfolioBackTesting{
        val filepath = "./test.xlsx"
        //Instantiate Excel workbook:
        val xlWb = XSSFWorkbook()
        //Instantiate Excel worksheet:
        val xlWs = xlWb.createSheet()

        //Row index specifies the row in the worksheet (starting at 0):
        val rowNumber = 0
        //Cell index specifies the column within the chosen row (starting at 0):
        val columnNumber = 0

        //Write text value to cell located at ROW_NUMBER / COLUMN_NUMBER:
        xlWs.createRow(rowNumber).createCell(columnNumber).setCellValue("TEST")

        //Write file:
        val outputStream = FileOutputStream(filepath)
        xlWb.write(outputStream)
        xlWb.close()
        return this
    }

    fun plot(): PortfolioBackTesting {
        return this
    }

    private fun strategy_monthly(): PortfolioBackTesting {
        data.forEachIndexed { index, dailyDetails ->
            data[index].noStockPurchase = Math.floor(fsbuilder.sipAmt / dailyDetails.close).roundToInt()
        }
        return this
    }

    private fun strategy_fivepercent(): PortfolioBackTesting {
        var lastPurchasePrice: Double = data.get(0).close
        data.forEachIndexed { index, dailyDetails ->
            val diff = ((lastPurchasePrice - dailyDetails.close) / dailyDetails.close) * 100
            if (diff >= fsbuilder.whenToInvestment || -fsbuilder.whenToInvestment >= diff) {
                data[index].noStockPurchase = Math.floor(fsbuilder.sipAmt / dailyDetails.close).roundToInt()
                lastPurchasePrice = dailyDetails.close
            }
        }
        return this
    }

    data class Builder(
        var ticker: String = "",
        var startDt: String = "",
        var endDt: String = "",
        var interval: Interval = Interval.DAILY,
        var strategy: Strategy = Strategy.MONTHLY,
        var sipAmt: Double = 1000.0,
        var whenToInvestment: Double = 5.0
    ) {

        fun sipAmt(sip: Double) = apply { this.sipAmt = sip }
        fun whenToInvestment(amt: Double) = apply { this.whenToInvestment = amt }

        fun ticker(ticker: String) = apply { this.ticker = ticker }
        fun startDate(start: String) = apply { this.startDt = start }
        fun endDate(endDt: String) = apply { this.endDt = endDt }
        fun interval(interval: Interval) = apply { this.interval = interval }

        fun strategy(strategy: Strategy) = apply { this.strategy = strategy }

        fun build() = PortfolioBackTesting(this)
        fun toBaseBuilder(): BaseStrategyV2.BaseBuilder {
            var interval = if (strategy == Strategy.MONTHLY) Interval.MONTHLY else this.interval
            return BaseStrategyV2.BaseBuilder(this.ticker, this.startDt, this.endDt, interval)
        }
    }
}

