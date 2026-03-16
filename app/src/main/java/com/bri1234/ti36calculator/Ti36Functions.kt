package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.contracts.CalculatorStateInterface
import kotlin.math.*
import kotlin.math.pow

/**
 * A class representing the functions of the TI-36 calculator.
 * Each function corresponds to a button on the calculator and manipulates the display labels accordingly.
 */
class Ti36Functions(val labels: CalculatorStateInterface,
                    val computation: Ti36Computation) {

    fun hyp() {
        if (labels.hasState(CalculatorState.HYP))
            labels.removeState(CalculatorState.HYP)
        else if (
            !labels.hasState(CalculatorState.BIN) &&
            !labels.hasState(CalculatorState.OCT) &&
            !labels.hasState(CalculatorState.HEX)
        ) {
            labels.addState(CalculatorState.HYP)
        }
    }

    fun cycleAngleUnit(convert: Boolean) {
        when {
            labels.hasState(CalculatorState.DEG) -> {
                labels.removeState(CalculatorState.DEG)
                labels.addState(CalculatorState.RAD)

                if (convert) {
                    val value = computation.getValue()
                    computation.setResult(value * Math.PI / 180)
                }
            }
            labels.hasState(CalculatorState.RAD) -> {
                labels.removeState(CalculatorState.RAD)
                labels.addState(CalculatorState.GRAD)

                if (convert) {
                    val value = computation.getValue()
                    computation.setResult(value * 200 / Math.PI)
                }
            }
            labels.hasState(CalculatorState.GRAD) -> {
                labels.removeState(CalculatorState.GRAD)
                labels.addState(CalculatorState.DEG)

                if (convert) {
                    val value = computation.getValue()
                    computation.setResult(value * 180 / 200)
                }
            }
        }
    }

    fun pi() {
        computation.setResult(Math.PI)
    }

    fun log() {
        val value = computation.getValue()
        if (value <= 0.0)
            throw IllegalArgumentException("log(): Input value must be greater than 0")

        val result = log10(value)
        computation.setResult(result)
    }

    fun ln() {
        val value = computation.getValue()
        if (value <= 0.0)
            throw IllegalArgumentException("ln(): Input value must be greater than 0")

        val result = ln(value)
        computation.setResult(result)
    }

    fun ceC() {
        notImplemented()
    }

    fun sin() {
        val value = computation.getValue()
        val result = when {
            labels.hasState(CalculatorState.HYP) -> sinh(value)
            labels.hasState(CalculatorState.DEG) -> sin(Math.toRadians(value))
            labels.hasState(CalculatorState.RAD) -> sin(value)
            labels.hasState(CalculatorState.GRAD) -> sin(Math.toRadians(value * 0.9))
            else -> throw IllegalStateException("sin(): No angle unit label found")
        }
        computation.setResult(result)
    }

    fun cos() {
        val value = computation.getValue()
        val result = when {
            labels.hasState(CalculatorState.HYP) -> cosh(value)
            labels.hasState(CalculatorState.DEG) -> cos(Math.toRadians(value))
            labels.hasState(CalculatorState.RAD) -> cos(value)
            labels.hasState(CalculatorState.GRAD) -> cos(Math.toRadians(value * 0.9))
            else -> throw IllegalStateException("cos(): No angle unit label found")
        }
        computation.setResult(result)
    }

    fun tan() {
        val value = computation.getValue()
        val result = when {
            labels.hasState(CalculatorState.HYP) -> tanh(value)
            labels.hasState(CalculatorState.DEG) -> tan(Math.toRadians(value))
            labels.hasState(CalculatorState.RAD) -> tan(value)
            labels.hasState(CalculatorState.GRAD) -> tan(Math.toRadians(value * 0.9))
            else -> throw IllegalStateException("tan(): No angle unit label found")
        }
        computation.setResult(result)
    }

    fun yPowX() {
        notImplemented()
    }

    fun xSwapY() {
        notImplemented()
    }

    fun oneDivX() {
        val value = computation.getValue()
        if (value == 0.0)
            throw IllegalArgumentException("1 / x: Input value must be not equal 0")

        val result = 1.0 / value
        computation.setResult(result)
    }

    fun xSquared() {
        val value = computation.getValue()
        val result = value * value
        computation.setResult(result)
    }

    fun sqrtX() {
        val value = computation.getValue()
        if (value <= 0.0)
            throw IllegalArgumentException("sqrt(): Input value must be greater than 0")

        val result = sqrt(value)
        computation.setResult(result)
    }

    fun divide() {
        notImplemented()
    }

    fun sumPlus() {
        notImplemented()
    }

    fun leftParentheses() {
        notImplemented()
    }

    fun rightParentheses() {
        notImplemented()
    }

    fun multiply() {
        notImplemented()
    }

    fun store() {
        notImplemented()
    }

    fun minus() {
        notImplemented()
    }

    fun recall() {
        notImplemented()
    }

    fun plus() {
        notImplemented()
    }

    fun aBC() {
        notImplemented()
    }

    fun equal() {
        notImplemented()
    }

    fun exp() {
        val value = computation.getValue()
        val result = exp(value)
        computation.setResult(result)
    }

    fun tenPowX() {
        val value = computation.getValue()
        val result = 10.0.pow(value)
        computation.setResult(result)
    }

    fun notImplemented() {
        labels.printNotImplemented()
    }
}