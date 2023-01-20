package utils

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import strategy.BaseStrategyV2
import strategy.PortfolioBackTesting
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String.printTimes(times: Int){
    var ret:String =""
    for(i in 0..times  ) {
        ret+= this
    }
    println(ret)
}

fun String.toCalendar():Calendar{
    val cal = Calendar.getInstance()
    try {
        cal.time =  SimpleDateFormat("yyyy-MM-dd").parse(this) as Date
    } catch (e: ParseException) {
        println("Exception :$e")
    }
    return cal
}

fun xlsReport(reports:List<BaseStrategyV2>) {
    val filepath = "./test.xlsx"
    //Instantiate Excel workbook:
    val xlWb = XSSFWorkbook()
    //Instantiate Excel worksheet:
    val xlWs = xlWb.createSheet()

    val headings = listOf<String>(
        "Stock.Pur",
        "Avg.Price",
        "Curr.Price",
        "Curr. Value",
        "Avg. Value",
        "P/L",
        "P/L %",
        "Tot No.Trad days ",
        "Tot No.Purchases"

    )

    //Row index specifies the row in the worksheet (starting at 0):
    val rowNumber = 0
    //Cell index specifies the column within the chosen row (starting at 0):
    val columnNumber = 0

    for ((index, value) in headings.withIndex()) {
        xlWs.createRow(index + 1).createCell(0).setCellValue(value)
    }

    xlWs.createRow(1).createCell(1).setCellValue(reports.get(0).data.get(0).symbol)
    xlWs.createRow(1).createCell(2).setCellValue(reports.get(1).data.get(0).symbol)
    xlWs.createRow(3).createCell(3).setCellValue(reports.get(2).data.get(0).symbol)

    //Write text value to cell located at ROW_NUMBER / COLUMN_NUMBER:
    for((index, value) in reports.withIndex()) {
        val totalStockPurchase = value.totalNoStockPurchased()
        val AvgPrice = value.totalNoStockPurchased()
        val CurrPrice = value.totalValue() /value.totalNoStockPurchased()
        val CurrValue =value.data.get(value.data.size - 1).close
        val AvgValue =  value.totalValue() / value.totalNoStockPurchased() * value.totalNoStockPurchased()
        val PL= CurrValue - AvgValue
        val PLPercentage=((CurrValue - AvgValue) / AvgValue) * 100
        val TotNoTraddays=value.data.size
        val TotNoPurchases=value.data.filter { it.noStockPurchase != 0 }.size
        var myindex = 1
        xlWs.createRow(1).createCell(myindex).setCellValue(value.data.get(0).symbol)
        xlWs.createRow(2).createCell(myindex).setCellValue(totalStockPurchase.toString())
        xlWs.createRow(3).createCell(myindex).setCellValue(AvgPrice.toString())
        xlWs.createRow(4).createCell(myindex).setCellValue(CurrPrice.toString())
        xlWs.createRow(5).createCell(myindex).setCellValue(CurrValue.toString())
        xlWs.createRow(6).createCell(myindex).setCellValue(AvgValue.toString())
        xlWs.createRow(7).createCell(myindex).setCellValue(PL)
        xlWs.createRow(8).createCell(myindex).setCellValue(PLPercentage)
        xlWs.createRow(9).createCell(myindex).setCellValue(TotNoTraddays.toString())
        xlWs.createRow(10).createCell(myindex).setCellValue(TotNoPurchases.toString())
        myindex++


        //xlWs.createRow(1).createCell(index+1).setCellValue(value.data.get(0).symbol)

    }

    //Write file:
    val outputStream = FileOutputStream(filepath)
    xlWb.write(outputStream)
    xlWb.close()
}
