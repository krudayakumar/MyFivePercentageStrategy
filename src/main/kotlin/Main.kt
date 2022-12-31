
import strategy.FivePercentStrategy
import javax.swing.JFrame


fun main(args: Array<String>) {

    val frame = JFrame()
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    frame.setSize(400, 400)
    frame.setLocation(200, 200)
    frame.isVisible = true

    //val daily = readFileAsTextUsingInputStream("/home/krishna/IdeaProjects/MyFivePercentageStrategy/src/main/kotlin/tsla.json")
    //val lastYear = daily.between(SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01"),SimpleDateFormat("yyyy-MM-dd").parse("2022-12-20") )
    val percentage = arrayListOf<Double>(5.0,6.0,7.0,8.0,9.0,10.0)
    percentage.forEach {
        val str:FivePercentStrategy = FivePercentStrategy("/home/krishna/IdeaProjects/MyFivePercentageStrategy/src/main/kotlin/spy.json",1000.00,it)
        str.filter("2017-01-01","2022-12-29")
        str.processStrategy()
        str.printSummary()
    }

    //println(fivstr.getData().filter { it.isPurchased })
}