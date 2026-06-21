/*
 * Ti36Calculator - A TI-36 calculator simulator for Android.
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

package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.contracts.IStatistic
import com.bri1234.ti36calculator.enums.CalculatorNumberMode
import com.bri1234.ti36calculator.enums.CalculatorStatisticMode
import kotlin.math.sqrt

/**
 * Coordinates one-variable and paired-variable statistical calculations for the calculator.
 *
 * Statistical input is read from [computation], accumulated in the selected statistic model, and
 * requested results are written back to the current computation register.
 *
 * @property state The calculator state that selects the active statistic and number modes.
 * @property computation The computation stack used to read samples and publish results.
 */
class CalculatorStatistic(val state: CalculatorState,
                          val computation: CalculatorComputation) {

    private val statistic1 = Statistic1()
    private val statistic2 = Statistic2()
    private var currentStatistic: IStatistic = statistic1

    /**
     * Clears both statistic data sets and selects the one-variable model as the internal default.
     *
     * This function does not change the statistic mode stored in [state].
     */
    fun reset() {
        statistic1.clearStatistic()
        statistic2.clearStatistic()
        currentStatistic = statistic1
    }

    /**
     * Enables one-variable statistics, switches to decimal number mode, and clears its data set.
     */
    fun enableStatistic1() {
        state.calculatorStatisticMode = CalculatorStatisticMode.STAT1
        state.calculatorNumberMode = CalculatorNumberMode.DECIMAL
        currentStatistic = statistic1
        currentStatistic.clearStatistic()
    }

    /**
     * Enables paired-variable statistics, switches to decimal number mode, and clears its data set.
     */
    fun enableStatistic2() {
        state.calculatorStatisticMode = CalculatorStatisticMode.STAT2
        state.calculatorNumberMode = CalculatorNumberMode.DECIMAL
        currentStatistic = statistic2
        currentStatistic.clearStatistic()
    }

    /**
     * Clears the currently selected statistic data set.
     *
     * @throws IllegalStateException If statistic mode is off.
     */
    fun clearStatistic() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        currentStatistic.clearStatistic()
    }

    /**
     * Adds the current sample to the active statistic data set and displays the new sample count.
     *
     * In one-variable mode the current register supplies `x`. In paired-variable mode the previous
     * register supplies `x` and the current register supplies `y`.
     *
     * @param frequency The positive number of occurrences represented by the sample.
     * @throws IllegalArgumentException If [frequency] is not positive.
     * @throws IllegalStateException If statistic mode is off.
     */
    fun addValue(frequency: Int = 1) {
        val count = when (state.calculatorStatisticMode) {
            CalculatorStatisticMode.STAT1 -> {
                val x = computation.getDoubleValue()
                statistic1.add(x, frequency)
                statistic1.count
            }
            CalculatorStatisticMode.STAT2 -> {
                val (y, x) = computation.getTwoDoubleValues()
                statistic2.add(x, y, frequency)
                statistic2.count
            }
            else -> throw IllegalStateException("statisticAddValue called while statistic mode is off")
        }

        computation.setDoubleValue(count.toDouble())
    }

    /**
     * Removes the current sample from the active statistic data set and displays the new count.
     *
     * In one-variable mode the current register supplies `x`. In paired-variable mode the previous
     * register supplies `x` and the current register supplies `y`.
     *
     * @param frequency The positive number of occurrences to remove.
     * @throws IllegalArgumentException If [frequency] is not positive.
     * @throws IllegalStateException If statistic mode is off or fewer than [frequency] samples are
     * stored.
     */
    fun subtractValue(frequency: Int = 1) {
        val count = when (state.calculatorStatisticMode) {
            CalculatorStatisticMode.STAT1 -> {
                val x = computation.getDoubleValue()
                statistic1.subtract(x, frequency)
                statistic1.count
            }
            CalculatorStatisticMode.STAT2 -> {
                val (y, x) = computation.getTwoDoubleValues()
                statistic2.subtract(x, y, frequency)
                statistic2.count
            }
            else -> throw IllegalStateException("statisticSubtractValue called while statistic mode is off")
        }

        computation.setDoubleValue(count.toDouble())
    }

    /**
     * Replaces the current value with the sum of all `x` samples.
     *
     * @throws IllegalStateException If statistic mode is off.
     */
    fun printSumX() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        computation.setDoubleValue(currentStatistic.sumX)
    }

    /**
     * Replaces the current value with the sum of the squared `x` samples.
     *
     * @throws IllegalStateException If statistic mode is off.
     */
    fun printSumX2() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        computation.setDoubleValue(currentStatistic.sumX2)
    }

    /**
     * Replaces the current value with the sum of all `y` samples.
     *
     * @throws IllegalStateException If statistic mode is off.
     * @throws UnsupportedOperationException If one-variable statistic mode is active.
     */
    fun printSumY() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        computation.setDoubleValue(currentStatistic.sumY)
    }

    /**
     * Replaces the current value with the sum of the squared `y` samples.
     *
     * @throws IllegalStateException If statistic mode is off.
     * @throws UnsupportedOperationException If one-variable statistic mode is active.
     */
    fun printSumY2() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        computation.setDoubleValue(currentStatistic.sumY2)
    }

    /**
     * Replaces the current value with the sum of all `x * y` products.
     *
     * @throws IllegalStateException If statistic mode is off.
     * @throws UnsupportedOperationException If one-variable statistic mode is active.
     */
    fun printSumXY() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        computation.setDoubleValue(currentStatistic.sumXY)
    }

    /**
     * Replaces the current value with the number of accumulated samples including frequencies.
     *
     * @throws IllegalStateException If statistic mode is off.
     */
    fun printCount() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        computation.setDoubleValue(currentStatistic.count.toDouble())
    }

    /**
     * Replaces the current value with the arithmetic mean of all `x` samples.
     *
     * @throws IllegalStateException If statistic mode is off or no samples are stored.
     */
    fun printMeanX() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        check(currentStatistic.count > 0)

        computation.setDoubleValue(currentStatistic.sumX / currentStatistic.count)
    }

    /**
     * Replaces the current value with the arithmetic mean of all `y` samples.
     *
     * @throws IllegalStateException If statistic mode is off or no samples are stored.
     * @throws UnsupportedOperationException If one-variable statistic mode is active.
     */
    fun printMeanY() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        check(currentStatistic.count > 0)

        computation.setDoubleValue(currentStatistic.sumY / currentStatistic.count)
    }

    /**
     * Replaces the current value with the population standard deviation of the `x` samples.
     *
     * @throws IllegalStateException If statistic mode is off or no samples are stored.
     */
    fun printPopulationStandardDeviationX() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        check(currentStatistic.count > 0)

        val meanX = currentStatistic.sumX / currentStatistic.count
        val varianceX = currentStatistic.sumX2 / currentStatistic.count - (meanX * meanX)
        computation.setDoubleValue(sqrt(varianceX))
    }

    /**
     * Replaces the current value with the population standard deviation of the `y` samples.
     *
     * @throws IllegalStateException If statistic mode is off or no samples are stored.
     * @throws UnsupportedOperationException If one-variable statistic mode is active.
     */
    fun printPopulationStandardDeviationY() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        check(currentStatistic.count > 0)

        val meanY = currentStatistic.sumY / currentStatistic.count
        val varianceY = currentStatistic.sumY2 / currentStatistic.count - (meanY * meanY)
        computation.setDoubleValue(sqrt(varianceY))
    }

    /**
     * Replaces the current value with the sample standard deviation of the `x` samples.
     *
     * @throws IllegalStateException If statistic mode is off or fewer than two samples are stored.
     */
    fun printSampleStandardDeviationX() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        check(currentStatistic.count > 1)

        val meanX = currentStatistic.sumX / currentStatistic.count
        val varianceX = (currentStatistic.sumX2 - (currentStatistic.sumX * meanX)) / (currentStatistic.count - 1)
        computation.setDoubleValue(sqrt(varianceX))
    }

    /**
     * Replaces the current value with the sample standard deviation of the `y` samples.
     *
     * @throws IllegalStateException If statistic mode is off or fewer than two samples are stored.
     * @throws UnsupportedOperationException If one-variable statistic mode is active.
     */
    fun printSampleStandardDeviationY() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        check(currentStatistic.count > 1)

        val meanY = currentStatistic.sumY / currentStatistic.count
        val varianceY = (currentStatistic.sumY2 - (currentStatistic.sumY * meanY)) / (currentStatistic.count - 1)
        computation.setDoubleValue(sqrt(varianceY))
    }

    /**
     * Replaces the current value with the Pearson correlation coefficient of paired samples.
     *
     * @throws IllegalStateException If paired-variable mode is inactive, no samples are stored, or
     * the coefficient is undefined because its denominator is zero.
     */
    fun printCorrelationCoefficient() {
        check(state.calculatorStatisticMode == CalculatorStatisticMode.STAT2)
        computation.setDoubleValue(currentStatistic.correlationCoefficient())
    }

    /**
     * Replaces the current value with the intercept of the least-squares regression line.
     *
     * @throws IllegalStateException If paired-variable mode is inactive, no samples are stored, or
     * the regression slope is undefined.
     */
    fun printIntercept() {
        check(state.calculatorStatisticMode == CalculatorStatisticMode.STAT2)
        computation.setDoubleValue(currentStatistic.intercept())
    }

    /**
     * Replaces the current value with the slope of the least-squares regression line.
     *
     * @throws IllegalStateException If paired-variable mode is inactive, no samples are stored, or
     * the slope is undefined because its denominator is zero.
     */
    fun printSlope() {
        check(state.calculatorStatisticMode == CalculatorStatisticMode.STAT2)
        computation.setDoubleValue(currentStatistic.slope())
    }

    /**
     * Predicts `x` from the current `y` value using the least-squares regression line.
     *
     * @throws IllegalStateException If paired-variable mode is inactive or the regression line
     * cannot be calculated.
     */
    fun printPredictedX() {
        check(state.calculatorStatisticMode == CalculatorStatisticMode.STAT2)

        val y = computation.getDoubleValue()
        val predictedX = (y - currentStatistic.intercept()) / currentStatistic.slope()
        computation.setDoubleValue(predictedX)
    }

    /**
     * Predicts `y` from the current `x` value using the least-squares regression line.
     *
     * @throws IllegalStateException If paired-variable mode is inactive or the regression line
     * cannot be calculated.
     */
    fun printPredictedY() {
        check(state.calculatorStatisticMode == CalculatorStatisticMode.STAT2)
        
        val x = computation.getDoubleValue()
        val predictedY = currentStatistic.intercept() + currentStatistic.slope() * x
        computation.setDoubleValue(predictedY)
    }
}
