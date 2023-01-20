package utils

import model.DataV2

object Statics {
    fun ema(data: Array<DataV2>, n: Double): DoubleArray {
        val results = DoubleArray(data.size)
        calculateEmasHelper(data, n, data.size - 1, results)
        return results
    }

    fun average(data: Array<DataV2>): Double {
        var results = 0.0
        data.forEach { results += it.close }
        return results / data.size
    }

    private fun calculateEmasHelper(candlesticks: Array<DataV2>, n: Double, i: Int, results: DoubleArray): Double {
        return if (i == 0) {
            results[0] = candlesticks[0].close.toDouble()
            results[0]
        } else {
            val close: Double = candlesticks[i].close.toDouble()
            val factor = 2.0 / (n + 1)
            val ema = close * factor + (1 - factor) * calculateEmasHelper(candlesticks, n, i - 1, results)
            results[i] = ema
            ema
        }
    }
}