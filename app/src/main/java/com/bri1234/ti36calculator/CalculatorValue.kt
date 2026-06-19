/*
 * Ti36Calculator - A TI-36 calculator simulator for Android.
 * Copyright (C) 2026 Torsten Brischalle <torsten@brischalle.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://gnu.org/licenses/>.
 */

package com.bri1234.ti36calculator

import com.bri1234.ti36calculator.contracts.INumericValue
import com.bri1234.ti36calculator.enums.Presentation

/**
 * An immutable value stored in a calculator register or memory cell.
 *
 * The numeric value and its presentation are kept separately. A [INumericValue.Fraction] therefore
 * retains its exact [Rational] value while it is temporarily shown as a decimal number. Decimal
 * values cannot be presented as fractions until an explicit conversion creates a rational value.
 *
 * @property number The decimal or exact rational numeric value.
 * @property presentation The format in which the value shall be displayed.
 * @throws IllegalArgumentException If a decimal number is assigned a fraction presentation.
 */
data class CalculatorValue(
    val number: INumericValue,
    val presentation: Presentation,
) {

    init {
        require(number is INumericValue.Fraction || presentation == Presentation.DECIMAL) {
            "A decimal value cannot use a fraction presentation"
        }
    }

    /**
     * Returns the value as a [Double].
     *
     * Exact rational values are converted using [Rational.toDouble].
     */
    fun toDouble(): Double = when (number) {
        is INumericValue.Decimal -> number.value
        is INumericValue.Fraction -> number.value.toDouble()
    }

    /**
     * Returns the exact rational value when one is available.
     *
     * A rational value remains available when [presentation] is [Presentation.DECIMAL].
     *
     * @return The exact value, or `null` for a floating-point decimal value.
     */
    fun rationalOrNull(): Rational? = when (number) {
        is INumericValue.Decimal -> null
        is INumericValue.Fraction -> number.value
    }

    /** Returns whether this value currently uses a fraction presentation. */
    fun isFractionDisplayed(): Boolean = presentation != Presentation.DECIMAL

    /**
     * Returns a copy of this value using [newPresentation].
     *
     * @param newPresentation The requested display presentation.
     * @throws IllegalArgumentException If a decimal value is requested in a fraction presentation.
     */
    fun withPresentation(newPresentation: Presentation): CalculatorValue =
        copy(presentation = newPresentation)

    companion object {
        /** A decimal calculator value representing zero. */
        val ZERO: CalculatorValue = decimal(0.0)

        /** A decimal calculator value representing one. */
        val ONE: CalculatorValue = decimal(1.0)

        /**
         * Creates a decimal calculator value.
         *
         * @param value The floating-point value to store.
         */
        fun decimal(value: Double): CalculatorValue = CalculatorValue(
            number = INumericValue.Decimal(value),
            presentation = Presentation.DECIMAL,
        )

        /**
         * Creates a calculator value backed by an exact [Rational].
         *
         * A decimal presentation may be selected without discarding the exact fraction.
         *
         * @param value The exact rational value to store.
         * @param presentation The initial display presentation.
         */
        fun fraction(
            value: Rational,
            presentation: Presentation = Presentation.FRACTION_MIXED,
        ): CalculatorValue = CalculatorValue(
            number = INumericValue.Fraction(value),
            presentation = presentation,
        )
    }
}
