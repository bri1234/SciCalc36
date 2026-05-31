package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.contracts.IStatistic
import kotlin.math.sqrt

class Statistic2: IStatistic {

    override var count: Int = 0
        private set
    override var sumX: Double = 0.0
        private set
    override var sumX2: Double = 0.0
        private set
    override var sumY: Double = 0.0
        private set
    override var sumY2: Double = 0.0
        private set
    override var sumXY: Double = 0.0
        private set

    init {
        clearStatistic()
    }

    override fun clearStatistic() {
        count = 0
        sumX = 0.0
        sumX2 = 0.0
        sumY = 0.0
        sumY2 = 0.0
        sumXY = 0.0
    }

    fun add(x: Double, y: Double, frequency: Int = 1) {
        require(frequency > 0) { "Frequency must be positive" }

        count += frequency
        sumX += x * frequency
        sumX2 += x * x * frequency
        sumY += y * frequency
        sumY2 += y * y * frequency
        sumXY += x * y * frequency
    }

    fun subtract(x: Double, y: Double, frequency: Int = 1) {
        require(frequency > 0) { "Frequency must be positive" }
        check(count >= frequency) { "Not enough data to subtract" }

        count -= frequency
        sumX -= x * frequency
        sumX2 -= x * x * frequency
        sumY -= y * frequency
        sumY2 -= y * y * frequency
        sumXY -= x * y * frequency
    }

    override fun correlationCoefficient(): Double {
        check(count > 0) { "No data to calculate correlation coefficient" }

        val numerator = count * sumXY - sumX * sumY
        val denominator = sqrt((count * sumX2 - sumX * sumX) * (count * sumY2 - sumY * sumY))
        if (denominator == 0.0)
            throw IllegalStateException("Cannot calculate correlation coefficient when denominator is zero")

        return numerator / denominator
    }

    override fun intercept(): Double {
        check(count > 0) { "No data to calculate intercept" }

        return (sumY - slope() * sumX) / count
    }

    override fun slope(): Double {
        check(count > 0) { "No data to calculate slope" }

        val numerator = count * sumXY - sumX * sumY
        val denominator = count * sumX2 - sumX * sumX
        if (denominator == 0.0)
            throw IllegalStateException("Cannot calculate correlation coefficient when denominator is zero")

        return numerator / denominator
    }
}
