package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.utils.factorial
import com.bri1234.ti36calculator.utils.getIntFromDouble

/**
 * This class implements the two parameter functions of the TI-36 calculator.
 */
class Ti36Functions2(val computation: Ti36Computation) {

    fun swap() {
        val (first, second) = computation.getTwoValues()
        computation.setTwoValues(second, first)
    }

    fun nCr() {
        val (rd, nd) = computation.getTwoValues()
        val n = getIntFromDouble(nd)
        val r = getIntFromDouble(rd)

        check(n >= 0 && r >= 0 && n >= r) { "nCr is only defined for non-negative integers with n >= r" }

        val result = factorial(n) / (factorial(r) * factorial(n - r))
        computation.setValue(result)
    }

    fun nPr() {
        val (rd, nd) = computation.getTwoValues()
        val n = getIntFromDouble(nd)
        val r = getIntFromDouble(rd)

        check(n >= 0 && r >= 0 && n >= r) { "nPr is only defined for non-negative integers with n >= r" }

        val result = factorial(n) / factorial(n - r)
        computation.setValue(result)
    }

}

