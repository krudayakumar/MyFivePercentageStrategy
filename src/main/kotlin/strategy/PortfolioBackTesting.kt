package strategy

import yahoofinance.histquotes.Interval
import kotlin.math.roundToInt

class PortfolioBackTesting constructor (private val fsbuilder:Builder): BaseStrategyV2(fsbuilder.toBaseBuilder())  {



    fun process():PortfolioBackTesting {
        when(fsbuilder.strategy){
            Strategy.MONTHLY -> return strategy_monthly()
            Strategy.FIVE_PERCENT -> return strategy_fivepercent()
        }
    }

    private fun strategy_monthly():PortfolioBackTesting {
        data.forEachIndexed { index, dailyDetails ->
           data[index].noStockPurchase = Math.floor(fsbuilder.sipAmt / dailyDetails.close).roundToInt()
        }
        return this
    }

    private fun strategy_fivepercent():PortfolioBackTesting {
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

    data class Builder (
    var ticker: String = "",
    var startDt: String = "",
    var endDt: String = "",
    var interval: Interval = Interval.DAILY,
    var strategy: Strategy = Strategy.MONTHLY,
    var sipAmt: Double = 1000.0,
    var whenToInvestment: Double = 5.0) {

        fun sipAmt(sip: Double) = apply { this.sipAmt = sip }
        fun whenToInvestment(amt: Double) = apply { this.whenToInvestment = amt }

        fun ticker(ticker: String) = apply { this.ticker = ticker }
        fun startDate(start: String) = apply { this.startDt = start }
        fun endDate(endDt: String) = apply { this.endDt = endDt }
        fun interval(interval: Interval) = apply { this.interval = interval }

        fun strategy(strategy: Strategy) = apply { this.strategy = strategy }

        fun build() = PortfolioBackTesting(this)
        fun toBaseBuilder(): BaseStrategyV2.BaseBuilder {
            var interval = if(strategy == Strategy.MONTHLY) Interval.MONTHLY else this.interval
            return BaseStrategyV2.BaseBuilder(this.ticker, this.startDt, this.endDt, interval)
        }
    }
}

