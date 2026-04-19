package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.contracts.IStatistic

class Statistic1: IStatistic {

    override var count: Int = 0
        private set

    override var sumX: Double = 0.0
        private set

    override var sumX2: Double = 0.0
        private set

    override val sumY: Double
        get() = throw UnsupportedOperationException("sumY is not supported in Statistic1")
    override val sumY2: Double
        get() = throw UnsupportedOperationException("sumY2 is not supported in Statistic1")
    override val sumXY: Double
        get() = throw UnsupportedOperationException("sumXY is not supported in Statistic1")

    init {
            clearStatistic()
    }

    override fun clearStatistic() {
        count = 0
        sumX = 0.0
        sumX2 = 0.0
    }

    fun add(value: Double) {
        count++
        sumX += value
        sumX2 += value * value
    }

    fun subtract(value: Double) {
        check(count > 0) { "No data to subtract" }

        count--
        sumX -= value
        sumX2 -= value * value
    }

    override fun correlationCoefficient(): Double {
        throw UnsupportedOperationException("correlationCoefficient is not supported in Statistic1")
    }

    override fun intercept(): Double {
        throw UnsupportedOperationException("intercept is not supported in Statistic1")
    }

    override fun slope(): Double {
        throw UnsupportedOperationException("slope is not supported in Statistic1")
    }
}
