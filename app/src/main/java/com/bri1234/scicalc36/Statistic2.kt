/*
 * SciCalc 36 - A classic-style scientific calculator inspired by traditional handheld calculator workflows.
 * Copyright (C) 2026 Torsten Brischalle <torsten@brischalle.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://gnu.org>.
 */

package com.bri1234.scicalc36

import com.bri1234.scicalc36.contracts.IStatistic
import kotlin.math.sqrt

/**
 * Accumulates the values required for paired-variable statistics and linear regression.
 *
 * Samples may have a frequency greater than one. The class tracks the sums of `x`, `y`, their
 * squares, and their products, allowing correlation and least-squares regression to be calculated.
 */
class Statistic2: IStatistic {

    /** Total number of sample pairs, including pairs represented by frequencies. */
    override var count: Int = 0
        private set

    /** Sum of all `x` values, including their frequencies. */
    override var sumX: Double = 0.0
        private set

    /** Sum of all squared `x` values, including their frequencies. */
    override var sumX2: Double = 0.0
        private set

    /** Sum of all `y` values, including their frequencies. */
    override var sumY: Double = 0.0
        private set

    /** Sum of all squared `y` values, including their frequencies. */
    override var sumY2: Double = 0.0
        private set

    /** Sum of all `x * y` products, including their frequencies. */
    override var sumXY: Double = 0.0
        private set

    init {
        clearStatistic()
    }

    /** Resets the sample count and all accumulated sums to zero. */
    override fun clearStatistic() {
        count = 0
        sumX = 0.0
        sumX2 = 0.0
        sumY = 0.0
        sumY2 = 0.0
        sumXY = 0.0
    }

    /**
     * Adds the sample pair ([x], [y]) to the accumulated statistics [frequency] times.
     *
     * @param x The independent-variable sample value.
     * @param y The dependent-variable sample value.
     * @param frequency The positive number of occurrences represented by the pair.
     * @throws IllegalArgumentException If [frequency] is not positive.
     */
    fun add(x: Double, y: Double, frequency: Int = 1) {
        require(frequency > 0) { "Frequency must be positive" }

        count += frequency
        sumX += x * frequency
        sumX2 += x * x * frequency
        sumY += y * frequency
        sumY2 += y * y * frequency
        sumXY += x * y * frequency
    }

    /**
     * Subtracts the contribution of the sample pair ([x], [y]) [frequency] times.
     *
     * @param x The independent-variable value whose contribution is removed.
     * @param y The dependent-variable value whose contribution is removed.
     * @param frequency The positive number of occurrences to remove.
     * @throws IllegalArgumentException If [frequency] is not positive.
     * @throws IllegalStateException If fewer than [frequency] sample pairs are currently counted.
     */
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

    /**
     * Calculates the Pearson correlation coefficient for the accumulated sample pairs.
     *
     * @return The correlation coefficient in the range `[-1, 1]`, subject to floating-point
     * precision.
     * @throws IllegalStateException If no samples are stored or the coefficient is undefined
     * because either variable has zero variance.
     */
    override fun correlationCoefficient(): Double {
        check(count > 0) { "No data to calculate correlation coefficient" }

        val numerator = count * sumXY - sumX * sumY
        val denominator = sqrt((count * sumX2 - sumX * sumX) * (count * sumY2 - sumY * sumY))
        if (denominator == 0.0)
            throw IllegalStateException("Cannot calculate correlation coefficient when denominator is zero")

        return numerator / denominator
    }

    /**
     * Calculates the intercept of the least-squares regression line `y = intercept + slope * x`.
     *
     * @return The regression-line intercept.
     * @throws IllegalStateException If no samples are stored or the regression slope is undefined.
     */
    override fun intercept(): Double {
        check(count > 0) { "No data to calculate intercept" }

        return (sumY - slope() * sumX) / count
    }

    /**
     * Calculates the slope of the least-squares regression line for predicting `y` from `x`.
     *
     * @return The regression-line slope.
     * @throws IllegalStateException If no samples are stored or all `x` values are equal, making
     * the denominator zero.
     */
    override fun slope(): Double {
        check(count > 0) { "No data to calculate slope" }

        val numerator = count * sumXY - sumX * sumY
        val denominator = count * sumX2 - sumX * sumX
        if (denominator == 0.0)
            throw IllegalStateException("Cannot calculate correlation coefficient when denominator is zero")

        return numerator / denominator
    }
}
