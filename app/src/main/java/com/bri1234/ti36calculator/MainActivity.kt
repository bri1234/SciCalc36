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
import com.bri1234.ti36calculator.viewModels.CalculatorViewModel
import com.bri1234.ti36calculator.views.CASE_COLOR
import com.bri1234.ti36calculator.views.CalculatorView

class MainActivity : ComponentActivity() {

    val calculatorViewModel = CalculatorViewModel()

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
                            CalculatorView(calculatorViewModel)
                        }
                    }
                }
            }
        }
    }
}
