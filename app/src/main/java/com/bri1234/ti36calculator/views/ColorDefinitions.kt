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

package com.bri1234.ti36calculator.views

import androidx.compose.ui.graphics.Color

val CASE_COLOR = Color(0xFF4B565A)
val CASE_COLOR_LIGHT = Color(0xFF4B565A).lighten(0.2f)
val CASE_COLOR_DARK = Color(0xFF4B565A).darken(0.2f)

//val BUTTON_COLOR_1 = Color(0xFF142924)
//val BUTTON_COLOR_2 = Color(0xFF545D5C)
//val BUTTON_COLOR_3 = Color(0xFFAAAFAB)

val BUTTON_COLOR_1 = Color(0xFF213228)
val BUTTON_COLOR_2 = Color(0xFF6E86C6)
val BUTTON_COLOR_3 = Color(0xFF8E8E8E)

val TEXT_1ST_COLOR_1 = Color(0xFFE3EDEE)
val TEXT_1ST_COLOR_2 = Color(0xFF2D3834)

val TEXT_2ND_COLOR = Color(0xFFF9BF6B)

val TEXT_3RD_COLOR = Color(0xFFC7D1D3)

val TEXT_4TH_COLOR = Color(0xFF55ADD5)

// val DISPLAY_BACKGROUND_COLOR = Color(0xFF808B7F)
//val DISPLAY_BACKGROUND_COLOR = Color(0xFFBBC0A9)
val DISPLAY_BACKGROUND_COLOR = Color(0xFFD4DCC5)

val DISPLAY_DIGIT_COLOR = Color(0xFF132326)

fun Color.lighten(factor: Float): Color =
    Color(
        red = red + (1 - red) * factor,
        green = green + (1 - green) * factor,
        blue = blue + (1 - blue) * factor,
        alpha = alpha
    )

fun Color.darken(factor: Float): Color =
    Color(
        red = red * (1 - factor),
        green = green * (1 - factor),
        blue = blue * (1 - factor),
        alpha = alpha
    )
