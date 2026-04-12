package com.bri1234.ti36calculator.utils

import kotlin.math.abs
import kotlin.math.round

/**
 * Computes the factorial of a non-negative integer n.
 * Returns NaN for negative inputs.
 */
fun factorial(n: Int): Double {
    if (n < 0)
        return Double.NaN

    if (n == 0)
        return 1.0

    var result = 1.0

    for (i in 1..n) {
        result *= i
    }

    return result
}

fun getIntFromDouble(value: Double): Int {
    require(!value.isNaN() && !value.isInfinite()) { "Value must be a finite number" }

    val intValue = round(value).toInt()

    if (abs(value - intValue.toDouble()) > 1e-12)
        throw IllegalArgumentException("Value must be an integer")

    return intValue
}
