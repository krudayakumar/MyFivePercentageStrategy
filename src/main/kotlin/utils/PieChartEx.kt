package utils

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.block.BlockBorder
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.chart.title.TextTitle
import org.jfree.data.xy.XYDataset
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.BasicStroke
import java.awt.Color
import java.awt.EventQueue
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JFrame


class LineChartEx2 : JFrame() {
    init {
        initUI()
    }

    private fun initUI() {
        val dataset = createDataset()
        val chart = createChart(dataset)
        val chartPanel = ChartPanel(chart)
        chartPanel.border = BorderFactory.createEmptyBorder(15, 15, 15, 15)
        chartPanel.background = Color.white
        add(chartPanel)
        pack()
        title = "Line chart"
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
    }

    private fun createDataset(): XYDataset {
        val series1 = XYSeries("2014")
        series1.add(18.0, 530.0)
        series1.add(20.0, 580.0)
        series1.add(25.0, 740.0)
        series1.add(30.0, 901.0)
        series1.add(40.0, 1300.0)
        series1.add(50.0, 2219.0)
        val series2 = XYSeries("2016")
        series2.add(18.0, 567.0)
        series2.add(20.0, 612.0)
        series2.add(25.0, 800.0)
        series2.add(30.0, 980.0)
        series2.add(40.0, 1210.0)
        series2.add(50.0, 2350.0)
        val dataset = XYSeriesCollection()
        dataset.addSeries(series1)
        dataset.addSeries(series2)
        return dataset
    }

    private fun createChart(dataset: XYDataset): JFreeChart {
        val chart = ChartFactory.createXYLineChart(
            "Average salary per age",
            "Age",
            "Salary (€)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        )
        val plot = chart.xyPlot
        val renderer = XYLineAndShapeRenderer()
        renderer.setSeriesPaint(0, Color.RED)
        renderer.setSeriesStroke(0, BasicStroke(2.0f))
        renderer.setSeriesPaint(1, Color.BLUE)
        renderer.setSeriesStroke(1, BasicStroke(2.0f))
        plot.renderer = renderer
        plot.backgroundPaint = Color.white
        plot.isRangeGridlinesVisible = false
        plot.isDomainGridlinesVisible = false
        chart.legend.frame = BlockBorder.NONE
        chart.title = TextTitle(
            "Average Salary per Age",
            Font("Serif", Font.BOLD, 18)
        )
        return chart
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            EventQueue.invokeLater {
                val ex = LineChartEx2()
                ex.isVisible = true
            }
        }
    }
}