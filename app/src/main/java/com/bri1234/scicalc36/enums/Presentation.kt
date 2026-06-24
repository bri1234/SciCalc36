package com.bri1234.scicalc36.enums

/** Defines how a calculator value is shown on the numeric display. */
enum class Presentation {
    /** Shows the value using the calculator's decimal number format. */
    DECIMAL,

    /** Shows a fraction with a whole-number part when its absolute value is at least one. */
    FRACTION_MIXED,

    /** Shows a fraction as a single, potentially improper numerator over its denominator. */
    FRACTION_IMPROPER,
}


