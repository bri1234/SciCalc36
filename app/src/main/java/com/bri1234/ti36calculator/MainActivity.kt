package com.bri1234.ti36calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.bri1234.ti36calculator.ui.theme.Ti36CalculatorTheme

class MainActivity : ComponentActivity() {

    val calculatorViewModel = CalculatorViewModel()

    /**
     * The main activity of the TI-36 calculator app.
     * It sets up the UI using Jetpack Compose and displays the calculator buttons in a grid layout.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        System.arraycopy(charArrayOf('-', '9', '8', '7', '6', '5', '4', '3', '2', '1', '0'), 0,
            calculatorViewModel.displayState.digitsLarge, 0, 11)

        System.arraycopy(charArrayOf('-', '8', '9'), 0,
            calculatorViewModel.displayState.digitsSmall, 0, 3)

        calculatorViewModel.displayState.decimalPointIndex = 1

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
                            CalculatorView(calculatorViewModel)
                        }
                    }
                }
            }
        }
    }
}
