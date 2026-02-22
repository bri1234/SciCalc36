package com.example.ti36calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ti36calculator.ui.theme.Ti36CalculatorTheme
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

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
                            CalculatorButtons()
                        }
                    }
                }
            }
        }
    }
}

