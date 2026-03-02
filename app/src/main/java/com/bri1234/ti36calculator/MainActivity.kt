package com.bri1234.ti36calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bri1234.ti36calculator.ui.theme.Ti36CalculatorTheme

class MainActivity : ComponentActivity() {

    /**
     * The main activity of the TI-36 calculator app.
     * It sets up the UI using Jetpack Compose and displays the calculator buttons in a grid layout.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Ti36CalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CASE_COLOR
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .padding(innerPadding)
                                .background(CASE_COLOR)
                        ) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                // val value = "ABCDEF"
                                val value = "1234567890"

                                CalculatorDisplay(value, false,
                                    Modifier.fillMaxWidth()
                                        .weight(1.5f)
                                        .padding(12.dp, 12.dp, 12.dp, 12.dp))

                                CalculatorButtons(Modifier
                                    .fillMaxWidth()
                                    .weight(9f)
                                    .padding(8.dp, 0.dp, 8.dp, 8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
