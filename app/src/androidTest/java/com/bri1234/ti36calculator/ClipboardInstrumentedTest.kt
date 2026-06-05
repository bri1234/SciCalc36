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

package com.bri1234.ti36calculator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.bri1234.ti36calculator.views.CALCULATOR_DISPLAY_TEST_TAG
import org.junit.Assert.assertEquals
import org.junit.Assume.assumeTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ClipboardInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val clipboardManager: ClipboardManager
        get() = composeRule.activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    @Before
    fun clearClipboard() {
        setClipboardText("")
    }

    @Test
    fun longPressDisplayCopiesDisplayTextToClipboard() {
        assumeClipboardCanBeWrittenAndRead()

        assertEquals("0", composeRule.activity.calculatorViewModel.displayState.value.toClipboardText())

        composeRule.onNodeWithTag(CALCULATOR_DISPLAY_TEST_TAG)
            .performTouchInput {
                down(center)
                advanceEventTime(1_500)
                up()
            }

        composeRule.waitForIdle()

        assertEquals("0", getClipboardText())
    }

    private fun setClipboardText(text: String) {
        composeRule.activity.runOnUiThread {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("test", text))
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
    }

    private fun assumeClipboardCanBeWrittenAndRead() {
        setClipboardText("clipboard-test")
        assumeTrue(
            "This emulator/device does not allow the instrumentation test to read back clipboard text.",
            getClipboardText() == "clipboard-test"
        )
        setClipboardText("")
    }

    private fun getClipboardText(): String? {
        var clipboardText: String? = null
        composeRule.activity.runOnUiThread {
            clipboardText = clipboardManager.primaryClip
                ?.takeIf { it.itemCount > 0 }
                ?.getItemAt(0)
                ?.text
                ?.toString()
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        return clipboardText
    }
}
