package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.contracts.DisplayLabelsInterface

class Ti36Functions(val labels: DisplayLabelsInterface) {

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

    fun log() {}
    fun ln() {}
    fun ceC() {}

    fun sin() {}
    fun cos() {}
    fun tan() {}
    fun yPowX() {}
    fun xSwapY() {}
    fun oneDivX() {}
    fun xSquared() {}
    fun sqrtX() {}
    fun divide() {}
    fun sumPlus() {}
    fun eE() {}
    fun leftParentheses() {}
    fun rightParentheses() {}
    fun multiply() {}
    fun store() {}
    fun seven() {}
    fun eight() {}
    fun nine() {}
    fun minus() {}
    fun recall() {}
    fun four() {}
    fun five() {}
    fun six() {}
    fun plus() {}
    fun aBC() {}
    fun one() {}
    fun two() {}
    fun three() {}
    fun equal() {}
    fun back() {}
    fun zero() {}
    fun dot() {}
    fun plusMinus() {}
}