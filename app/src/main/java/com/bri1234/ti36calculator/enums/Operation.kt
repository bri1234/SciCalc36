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
 * along with this program.  If not, see <http://gnu.org>.
 */

package com.bri1234.ti36calculator.enums

/**
 * These are the supported stack operations, ordered by precedence (lower order means higher precedence).
 */
enum class Operation(val order: Int, val caption: String) {
    NONE(-1, "none"),
    Y_POW_X(3, "^"),
    Y_ROOT_X(3, "sqrt"),
    MULTIPLICATION(4, "*"),
    DIVISION(4, "/"),
    ADDITION(5, "+"),
    SUBTRACTION(5, "-"),
    BITWISE_AND(6, "and"),
    BITWISE_OR(7, "or"),
    BITWISE_XOR(7, "xor"),
    BITWISE_XNOR(7, "xnor"),
}
