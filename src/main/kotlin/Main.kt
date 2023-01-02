import strategy.PortfolioBackTesting
import strategy.Strategy
import yahoofinance.histquotes.Interval


fun main(args: Array<String>) {

//    val frame = JFrame()
//    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
//
//    frame.setSize(400, 400)
//    frame.setLocation(200, 200)
//    frame.isVisible = true
//
//    //val daily = readFileAsTextUsingInputStream("/home/krishna/IdeaProjects/MyFivePercentageStrategy/src/main/kotlin/tsla.json")
//    //val lastYear = daily.between(SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01"),SimpleDateFormat("yyyy-MM-dd").parse("2022-12-20") )
//    val percentage = arrayListOf<Double>(5.0)
//    //val ticker= arrayListOf<String>("spy","brk_b","tsla")
//    val ticker= arrayListOf<String>("spy")
////    ticker.forEach { tick->
////        percentage.forEach {
////            val str: FivePercentStrategy = FivePercentStrategy(tick,
////                "/home/krishna/IdeaProjects/MyFivePercentageStrategy/src/main/kotlin/${tick}.json",
////                1000.00,
////                it
////            )
////            str.filterByFirstTradingDay("2017-01-01", "2022-12-29")
////            str.processStrategy()
////            str.printSummary()
////        }
////    }
//    val str: FivePercentStrategy = FivePercentStrategy("spy",
//        "/home/krishna/IdeaProjects/MyFivePercentageStrategy/src/main/kotlin/spy.json",
//        2000.00,
//        5.0
//    )
//    str.filter("2022-12-01", "2022-12-29")
//    println(str.processStrategy().filter { it.isPurchased })
//    //str.tostring()
//    //str.printSummary()
//
//   // println(str.getData())
//
//    //println(fivstr.getData().filter { it.isPurchased })

//    val from = Calendar.getInstance()
//    val to = Calendar.getInstance()
//    from.add(Calendar.YEAR, -1) // from 1 year ago
//
//
//    val google = YahooFinance.get("GOOG")
//    val googleHistQuotes = google.getHistory(from, to, Interval.DAILY)
//    println(googleHistQuotes.)

    val five = PortfolioBackTesting.Builder()
        .ticker("spy")
        .startDate("2022-01-01")
        .endDate("2022-12-31")
        .sipAmt(1000.00)
        .interval(Interval.DAILY)
        .strategy(Strategy.FIVE_PERCENT)
        .whenToInvestment(5.0)
        .build()
        .process()
        .toPrint()

    PortfolioBackTesting.Builder()
        .ticker("spy")
        .startDate("2022-01-01")
        .endDate("2022-12-31")
        .sipAmt(1000.00)
        .interval(Interval.MONTHLY)
        .whenToInvestment(5.0)
        .build()
        .process()
        .toPrint()

    /*five.data.forEach {
        println(it.toString())
    }*/

    /*//println(five.data)
    println("Total Stock:"+ five.totalNoStockPurchased())
    println("Total Value:"+ five.totalValue().toDouble())*/


}