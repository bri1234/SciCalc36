package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.contracts.DisplayLabelsInterface
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.log10

private const val MAX_MANTISSA_DIGITS = 11
private const val MAX_EXPONENT_DIGITS = 3

enum class DisplayNumberFormat {
    FLOAT,
    SCIENTIFIC,
    ENGINEERING,
    OCTAL,
    HEXADECIMAL,
    BINARY
}

class Ti36InputOutput : DisplayLabelsInterface {

    private val displayMantissa  = CharArray(MAX_MANTISSA_DIGITS) { ' ' }
    private var displayDecimalPointIndex: Int = -1
    private val displayExponent =  CharArray(MAX_EXPONENT_DIGITS) { ' ' }
    private var isEditMode: Boolean = false
    private val displayLabels: MutableSet<DisplayLabels> = mutableSetOf()

    private var displayNumberFormat: DisplayNumberFormat = DisplayNumberFormat.FLOAT

    fun getDisplayState(): CalculatorDisplayState {
        // Return a dummy display state for now
        return CalculatorDisplayState(
            digitsLarge = displayMantissa.copyOf(),
            decimalPointIndex = displayDecimalPointIndex,
            digitsSmall = displayExponent.copyOf(),
            displayLabels = displayLabels.toSet(),
        )
    }

    fun displayViewAllSegments() {
        "-8888888888".toCharArray().copyInto(displayMantissa)
        displayDecimalPointIndex = 1
        "-88".toCharArray().copyInto(displayExponent)
    }

    fun reset() {
        displayLabels.clear()
        displayLabels.add(DisplayLabels.DEG)

        displayMantissa.fill(' ')
        displayDecimalPointIndex = -1
        displayExponent.fill(' ')
    }

    override fun isSecondFunctionActive() = DisplayLabels.SECOND in displayLabels

    override fun isThirdFunctionActive() = DisplayLabels.THIRD in displayLabels

    override fun hasLabel(label: DisplayLabels) = label in displayLabels

    override fun addLabel(label: DisplayLabels) {
        displayLabels.add(label)
    }

    override fun removeLabel(label: DisplayLabels) {
        displayLabels.remove(label)
    }

    fun printValue(value: Double) {
        if (value.isNaN()) {
            printNan()
            return
        }

        if (value.isInfinite()) {
            printInf(value > 0)
            return
        }

        when (displayNumberFormat) {
            DisplayNumberFormat.FLOAT -> printValueFloat(value)
            DisplayNumberFormat.SCIENTIFIC -> printValueScientific(value)
            DisplayNumberFormat.ENGINEERING -> printValueEngineering(value)
            DisplayNumberFormat.OCTAL -> printValueOct(value)
            DisplayNumberFormat.HEXADECIMAL -> printValueHex(value)
            DisplayNumberFormat.BINARY -> printValueBin(value)
        }
    }

    private fun printValueOct(value: Double) {
    }

    private fun printValueHex(value: Double) {
    }

    private fun printValueBin(value: Double) {
    }

    private fun printValueScientific(value: Double) {
    }

    private fun printValueEngineering(value: Double) {
    }

    private fun printValueFloat(value: Double) {
        val exponent = if (value != 0.0) floor(log10(abs(value))) else 1.0

        if ((exponent >= 10) || (exponent <= -10)) {
            printValueScientific(value)
            return
        }

        var valueStr = value.toBigDecimal().stripTrailingZeros().toPlainString()
        val decimalPointPos = valueStr.indexOf('.')

        if (decimalPointPos != -1) {
            displayDecimalPointIndex = decimalPointPos - 1
            valueStr = valueStr.removeRange(decimalPointPos, decimalPointPos + 1)
        } else {
            displayDecimalPointIndex = valueStr.length - 1
        }

        check(displayDecimalPointIndex < MAX_MANTISSA_DIGITS)

        if (valueStr.length < MAX_MANTISSA_DIGITS) {
            val lenBefore = valueStr.length
            valueStr = valueStr.padStart(MAX_MANTISSA_DIGITS, ' ')
            displayDecimalPointIndex += (valueStr.length - lenBefore)
        }

        check(displayDecimalPointIndex < MAX_MANTISSA_DIGITS)

        if (valueStr.length > MAX_MANTISSA_DIGITS)
            valueStr = valueStr.substring(0, MAX_MANTISSA_DIGITS)

        valueStr.toCharArray().copyInto(displayMantissa)
        displayExponent.fill(' ')
    }

    fun printError() {
        "    Error  ".toCharArray().copyInto(displayMantissa)
        displayDecimalPointIndex = -1
        displayExponent.fill(' ')
    }

    fun printNan() {
        "    nAn    ".toCharArray().copyInto(displayMantissa)
        displayDecimalPointIndex = -1
        displayExponent.fill(' ')
    }

    fun printInf(isPositive: Boolean) {
        val infStr = if (isPositive) "    InF    " else "   -InF    "
        infStr.toCharArray().copyInto(displayMantissa)

        displayDecimalPointIndex = -1
        displayExponent.fill(' ')
    }

    fun setNumberFormat(numberFormat: DisplayNumberFormat) {
        displayNumberFormat = numberFormat
    }
}