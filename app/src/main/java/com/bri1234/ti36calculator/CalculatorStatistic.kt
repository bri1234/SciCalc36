package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.contracts.IStatistic
import kotlin.math.sqrt

class CalculatorStatistic(val state: CalculatorState,
                          val computation: CalculatorComputation) {

    private val statistic1 = Statistic1()
    private val statistic2 = Statistic2()
    private var currentStatistic: IStatistic = statistic1

    fun reset() {
        statistic1.clearStatistic()
        statistic2.clearStatistic()
        currentStatistic = statistic1
    }

    fun enableStatistic1() {
        state.calculatorStatisticMode = CalculatorStatisticMode.STAT1
        state.calculatorNumberMode = CalculatorNumberMode.DECIMAL
        currentStatistic = statistic1
        currentStatistic.clearStatistic()
    }

    fun enableStatistic2() {
        state.calculatorStatisticMode = CalculatorStatisticMode.STAT2
        state.calculatorNumberMode = CalculatorNumberMode.DECIMAL
        currentStatistic = statistic2
        currentStatistic.clearStatistic()
    }

    fun clearStatistic() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        currentStatistic.clearStatistic()
    }

    fun addValue(frequency: Int = 1) {
        val count = when (state.calculatorStatisticMode) {
            CalculatorStatisticMode.STAT1 -> {
                val x = computation.getValue()
                statistic1.add(x, frequency)
                statistic1.count
            }
            CalculatorStatisticMode.STAT2 -> {
                val (y, x) = computation.getTwoValues()
                statistic2.add(x, y, frequency)
                statistic2.count
            }
            else -> throw IllegalStateException("statisticAddValue called while statistic mode is off")
        }

        computation.setValue(count.toDouble())
    }

    fun subtractValue(frequency: Int = 1) {
        val count = when (state.calculatorStatisticMode) {
            CalculatorStatisticMode.STAT1 -> {
                val x = computation.getValue()
                statistic1.subtract(x, frequency)
                statistic1.count
            }
            CalculatorStatisticMode.STAT2 -> {
                val (y, x) = computation.getTwoValues()
                statistic2.subtract(x, y, frequency)
                statistic2.count
            }
            else -> throw IllegalStateException("statisticSubtractValue called while statistic mode is off")
        }

        computation.setValue(count.toDouble())
    }

    fun printSumX() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        computation.setValue(currentStatistic.sumX)
    }

    fun printSumX2() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        computation.setValue(currentStatistic.sumX2)
    }

    fun printSumY() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        computation.setValue(currentStatistic.sumY)
    }

    fun printSumY2() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        computation.setValue(currentStatistic.sumY2)
    }

    fun printSumXY() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        computation.setValue(currentStatistic.sumXY)
    }

    fun printCount() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        computation.setValue(currentStatistic.count.toDouble())
    }

    fun printMeanX() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        check(currentStatistic.count > 0)

        computation.setValue(currentStatistic.sumX / currentStatistic.count)
    }

    fun printMeanY() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        check(currentStatistic.count > 0)

        computation.setValue(currentStatistic.sumY / currentStatistic.count)
    }

    fun printPopulationStandardDeviationX() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        check(currentStatistic.count > 0)

        val meanX = currentStatistic.sumX / currentStatistic.count
        val varianceX = currentStatistic.sumX2 / currentStatistic.count - (meanX * meanX)
        computation.setValue(sqrt(varianceX))
    }

    fun printPopulationStandardDeviationY() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        check(currentStatistic.count > 0)

        val meanY = currentStatistic.sumY / currentStatistic.count
        val varianceY = currentStatistic.sumY2 / currentStatistic.count - (meanY * meanY)
        computation.setValue(sqrt(varianceY))
    }

    fun printSampleStandardDeviationX() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        check(currentStatistic.count > 1)

        val meanX = currentStatistic.sumX / currentStatistic.count
        val varianceX = (currentStatistic.sumX2 - (currentStatistic.sumX * meanX)) / (currentStatistic.count - 1)
        computation.setValue(sqrt(varianceX))
    }

    fun printSampleStandardDeviationY() {
        check(state.calculatorStatisticMode != CalculatorStatisticMode.OFF)
        check(currentStatistic.count > 1)

        val meanY = currentStatistic.sumY / currentStatistic.count
        val varianceY = (currentStatistic.sumY2 - (currentStatistic.sumY * meanY)) / (currentStatistic.count - 1)
        computation.setValue(sqrt(varianceY))
    }

    fun printCorrelationCoefficient() {
        check(state.calculatorStatisticMode == CalculatorStatisticMode.STAT2)
        computation.setValue(currentStatistic.correlationCoefficient())
    }

    fun printIntercept() {
        check(state.calculatorStatisticMode == CalculatorStatisticMode.STAT2)
        computation.setValue(currentStatistic.intercept())
    }

    fun printSlope() {
        check(state.calculatorStatisticMode == CalculatorStatisticMode.STAT2)
        computation.setValue(currentStatistic.slope())
    }

    fun printPredictedX() {
        check(state.calculatorStatisticMode == CalculatorStatisticMode.STAT2)

        val y = computation.getValue()
        val predictedX = (y - currentStatistic.intercept()) / currentStatistic.slope()
        computation.setValue(predictedX)
    }

    fun printPredictedY() {
        check(state.calculatorStatisticMode == CalculatorStatisticMode.STAT2)
        
        val x = computation.getValue()
        val predictedY = currentStatistic.intercept() + currentStatistic.slope() * x
        computation.setValue(predictedY)
    }
}
