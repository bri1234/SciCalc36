package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.contracts.DisplayLabelsInterface

/**
 * A class representing the functions of the TI-36 calculator.
 * Each function corresponds to a button on the calculator and manipulates the display labels accordingly.
 */
class Ti36Functions(val labels: DisplayLabelsInterface,
    val computation: Ti36Computation) {

    private fun setError() {

    }

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

//                if (convert) {
//                    val value = stack.removeLast()
//                    stack.add(value * Math.PI / 180)
//                }
            }
            labels.hasLabel(DisplayLabels.RAD) -> {
                labels.removeLabel(DisplayLabels.RAD)
                labels.addLabel(DisplayLabels.GRAD)

//                if (convert) {
//                    val value = stack.removeLast()
//                    stack.add(value * 200 / Math.PI)
//                }
            }
            labels.hasLabel(DisplayLabels.GRAD) -> {
                labels.removeLabel(DisplayLabels.GRAD)
                labels.addLabel(DisplayLabels.DEG)

//                if (convert) {
//                    val value = stack.removeLast()
//                    stack.add(value * 180 / 200)
//                }
            }
        }
    }

    fun pi() {
        computation.setResult(Math.PI)
    }

    fun log() {}
    fun ln() {}
    fun ceC() {}

    fun sin() {
        val value = computation.getValue()
        val result = when {
            labels.hasLabel(DisplayLabels.HYP) -> Math.sinh(value)
            labels.hasLabel(DisplayLabels.DEG) -> Math.sin(Math.toRadians(value))
            labels.hasLabel(DisplayLabels.RAD) -> Math.sin(value)
            labels.hasLabel(DisplayLabels.GRAD) -> Math.sin(Math.toRadians(value * 0.9))
            else -> throw IllegalStateException("sin(): No angle unit label found")
        }
        computation.setResult(result)
    }

    fun cos() {
        val value = computation.getValue()
        val result = when {
            labels.hasLabel(DisplayLabels.HYP) -> Math.cosh(value)
            labels.hasLabel(DisplayLabels.DEG) -> Math.cos(Math.toRadians(value))
            labels.hasLabel(DisplayLabels.RAD) -> Math.cos(value)
            labels.hasLabel(DisplayLabels.GRAD) -> Math.cos(Math.toRadians(value * 0.9))
            else -> throw IllegalStateException("cos(): No angle unit label found")
        }
        computation.setResult(result)
    }

    fun tan() {}
    fun yPowX() {}
    fun xSwapY() {}
    fun oneDivX() {}
    fun xSquared() {}
    fun sqrtX() {}
    fun divide() {}
    fun sumPlus() {}
    fun leftParentheses() {}
    fun rightParentheses() {}
    fun multiply() {}
    fun store() {}
    fun minus() {}
    fun recall() {}
    fun plus() {}
    fun aBC() {}
    fun equal() {}
}