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

/**
 * Accumulates the values required for one-variable statistics.
 *
 * Samples may have a frequency greater than one. The class tracks the total sample count, the sum
 * of all values, and the sum of their squares. Paired-variable values and regression calculations
 * are not supported.
 */
class Statistic1: IStatistic {

    /** Total number of samples, including repeated samples represented by frequencies. */
    override var count: Int = 0
        private set

    /** Sum of all `x` sample values, including their frequencies. */
    override var sumX: Double = 0.0
        private set

    /** Sum of all squared `x` sample values, including their frequencies. */
    override var sumX2: Double = 0.0
        private set

    /**
     * Paired-variable `y` sums are unavailable in one-variable statistics.
     *
     * @throws UnsupportedOperationException Always.
     */
    override val sumY: Double
        get() = throw UnsupportedOperationException("sumY is not supported in Statistic1")

    /**
     * Paired-variable squared `y` sums are unavailable in one-variable statistics.
     *
     * @throws UnsupportedOperationException Always.
     */
    override val sumY2: Double
        get() = throw UnsupportedOperationException("sumY2 is not supported in Statistic1")

    /**
     * Paired-variable product sums are unavailable in one-variable statistics.
     *
     * @throws UnsupportedOperationException Always.
     */
    override val sumXY: Double
        get() = throw UnsupportedOperationException("sumXY is not supported in Statistic1")

    init {
            clearStatistic()
    }

    /** Resets the sample count and all accumulated sums to zero. */
    override fun clearStatistic() {
        count = 0
        sumX = 0.0
        sumX2 = 0.0
    }

    /**
     * Adds [value] to the accumulated statistics [frequency] times.
     *
     * @param value The `x` sample value to add.
     * @param frequency The positive number of occurrences represented by [value].
     * @throws IllegalArgumentException If [frequency] is not positive.
     */
    fun add(value: Double, frequency: Int = 1) {
        require(frequency > 0) { "Frequency must be positive" }

        count += frequency
        sumX += value * frequency
        sumX2 += value * value * frequency
    }

    /**
     * Subtracts the contribution of [value] from the statistics [frequency] times.
     *
     * @param value The `x` sample value whose contribution is removed.
     * @param frequency The positive number of occurrences to remove.
     * @throws IllegalArgumentException If [frequency] is not positive.
     * @throws IllegalStateException If fewer than [frequency] samples are currently counted.
     */
    fun subtract(value: Double, frequency: Int = 1) {
        require(frequency > 0) { "Frequency must be positive" }
        check(count >= frequency) { "Not enough data to subtract" }

        count -= frequency
        sumX -= value * frequency
        sumX2 -= value * value * frequency
    }

    /**
     * Correlation is unavailable for one-variable statistics.
     *
     * @throws UnsupportedOperationException Always.
     */
    override fun correlationCoefficient(): Double {
        throw UnsupportedOperationException("correlationCoefficient is not supported in Statistic1")
    }

    /**
     * A regression intercept is unavailable for one-variable statistics.
     *
     * @throws UnsupportedOperationException Always.
     */
    override fun intercept(): Double {
        throw UnsupportedOperationException("intercept is not supported in Statistic1")
    }

    /**
     * A regression slope is unavailable for one-variable statistics.
     *
     * @throws UnsupportedOperationException Always.
     */
    override fun slope(): Double {
        throw UnsupportedOperationException("slope is not supported in Statistic1")
    }
}
