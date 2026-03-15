package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.contracts.DisplayLabelsInterface
import kotlin.math.*
import kotlin.math.pow

/**
 * A class representing the functions of the TI-36 calculator.
 * Each function corresponds to a button on the calculator and manipulates the display labels accordingly.
 */
class Ti36Functions(val labels: DisplayLabelsInterface,
    val computation: Ti36Computation) {

    fun hyp() {
        if (labels.hasLabel(DisplayLabels.HYP))
            labels.removeLabel(DisplayLabels.HYP)
        else if (
            !labels.hasLabel(DisplayLabels.BIN) &&
            !labels.hasLabel(DisplayLabels.OCT) &&
            !labels.hasLabel(DisplayLabels.HEX)
        ) {
            labels.addLabel(DisplayLabels.HYP)
        }
    }

    fun cycleAngleUnit(convert: Boolean) {
        when {
            labels.hasLabel(DisplayLabels.DEG) -> {
                labels.removeLabel(DisplayLabels.DEG)
                labels.addLabel(DisplayLabels.RAD)

                if (convert) {
                    val value = computation.getValue()
                    computation.setResult(value * Math.PI / 180)
                }
            }
            labels.hasLabel(DisplayLabels.RAD) -> {
                labels.removeLabel(DisplayLabels.RAD)
                labels.addLabel(DisplayLabels.GRAD)

                if (convert) {
                    val value = computation.getValue()
                    computation.setResult(value * 200 / Math.PI)
                }
            }
            labels.hasLabel(DisplayLabels.GRAD) -> {
                labels.removeLabel(DisplayLabels.GRAD)
                labels.addLabel(DisplayLabels.DEG)

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
            labels.hasLabel(DisplayLabels.HYP) -> sinh(value)
            labels.hasLabel(DisplayLabels.DEG) -> sin(Math.toRadians(value))
            labels.hasLabel(DisplayLabels.RAD) -> sin(value)
            labels.hasLabel(DisplayLabels.GRAD) -> sin(Math.toRadians(value * 0.9))
            else -> throw IllegalStateException("sin(): No angle unit label found")
        }
        computation.setResult(result)
    }

    fun cos() {
        val value = computation.getValue()
        val result = when {
            labels.hasLabel(DisplayLabels.HYP) -> cosh(value)
            labels.hasLabel(DisplayLabels.DEG) -> cos(Math.toRadians(value))
            labels.hasLabel(DisplayLabels.RAD) -> cos(value)
            labels.hasLabel(DisplayLabels.GRAD) -> cos(Math.toRadians(value * 0.9))
            else -> throw IllegalStateException("cos(): No angle unit label found")
        }
        computation.setResult(result)
    }

    fun tan() {
        val value = computation.getValue()
        val result = when {
            labels.hasLabel(DisplayLabels.HYP) -> tanh(value)
            labels.hasLabel(DisplayLabels.DEG) -> tan(Math.toRadians(value))
            labels.hasLabel(DisplayLabels.RAD) -> tan(value)
            labels.hasLabel(DisplayLabels.GRAD) -> tan(Math.toRadians(value * 0.9))
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