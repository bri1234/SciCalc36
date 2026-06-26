/*
 * SciCalc 36 - A classic-style scientific calculator inspired by traditional handheld calculator workflows.
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
 * along with this program.  If not, see <https://gnu.org>.
 */

package com.bri1234.scicalc36.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.bri1234.scicalc36.BuildConfig
import com.bri1234.scicalc36.R

@Composable
fun AboutDialog(onDismiss: () -> Unit) {
    val appName = stringResource(R.string.app_name)
    val projectUrl = "https://github.com/bri1234/SciCalc36"
    val manualUrl = "https://www.aaabbb.de/SciCalc36/index.html"
    val uriHandler = LocalUriHandler.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = appName)
        },
        text = {
            Column {
                Text(text = "Version: ${BuildConfig.VERSION_NAME}")
                Text(text = "Author: Torsten Brischalle")
                Text(text = "License: GNU General Public License v3.0 (GPL3)")
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Hint: Long-press the display to copy the shown value.",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Manual: $manualUrl",
                    modifier = Modifier.clickable { uriHandler.openUri(projectUrl) },
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Project page: $projectUrl",
                    modifier = Modifier.clickable { uriHandler.openUri(projectUrl) },
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "OK")
            }
        }
    )
}
