package com.bri1234.ti36calculator

/**
 * Represents the state of the calculator display, including the digits shown and any labels.
 *
 * @property digitsLarge An array of characters representing the large digits on the display.
 * @property decimalPointIndex The index of the decimal point in the large digits array, or -1 if not present.
 * @property digitsSmall An array of characters representing the small digits on the display.
 * @property displayLabels A set of labels that are currently displayed (e.g., "M", "E", "A", etc.).
 */
data class CalculatorDisplayState (
    val digitsLarge: CharArray = charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '0'),
    val decimalPointIndex: Int = 10,

    val digitsSmall: CharArray = charArrayOf(' ', ' ', ' '),

    val displayLabels: MutableSet<DisplayLabels> = mutableSetOf(DisplayLabels.DEG),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CalculatorDisplayState

        if (decimalPointIndex != other.decimalPointIndex) return false
        if (!digitsLarge.contentEquals(other.digitsLarge)) return false
        if (!digitsSmall.contentEquals(other.digitsSmall)) return false
        if (displayLabels != other.displayLabels) return false

        return true
    }

    override fun hashCode(): Int {
        var result = decimalPointIndex
        result = 31 * result + digitsLarge.contentHashCode()
        result = 31 * result + digitsSmall.contentHashCode()
        result = 31 * result + displayLabels.hashCode()
        return result
    }
}

