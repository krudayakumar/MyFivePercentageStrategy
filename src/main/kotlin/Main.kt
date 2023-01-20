import strategy.BaseStrategyV2
import strategy.PortfolioBackTesting
import strategy.Strategy
import utils.xlsReport
import yahoofinance.histquotes.Interval


fun main(args: Array<String>) {

    val startDt = "2018-01-01"
    val endDt = "2023-01-18"
    //val tickers= listOf<String>("SPY", "TSLA", "BRK-B", "INFY", "VXAZN")
    val tickers= listOf<String>("^NSEI", "WIPRO.NS", "INFY.NS", "TATASTEEL.NS", "ITC.NS")
    val reports = mutableListOf<BaseStrategyV2>()


    tickers.forEach {
        val report = PortfolioBackTesting.Builder()
            .ticker(it)
            .startDate(startDt)
            .endDate(endDt)
            .sipAmt(10000.00)
            .interval(Interval.DAILY)
            .strategy(Strategy.FIVE_PERCENT)
            .whenToInvestment(5.0)
            .build()
            .process()
            .toPrint()
        reports.add(report)
    }
    xlsReport(reports)
}