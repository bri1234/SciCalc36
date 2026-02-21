package com.example.ti36calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ti36calculator.ui.theme.Ti36CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ti36CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        CalculatorButtons()
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(text1: String, text2: String, text3: String, text4: String, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (text4.isEmpty()) {
            // center text3 if text4 is empty
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text3,
                    maxLines = 1
                )
            }
        } else {
            // align text3 left and text4 right
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text3,
                    modifier = Modifier.weight(1f),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = text4,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    textAlign = TextAlign.End
                )
            }
        }
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF505050)),
            contentPadding = PaddingValues(2.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = text1)
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Text(text = text2)
            }
        }
    }
}

@Composable
fun CalculatorButtons() {
    Column(modifier = Modifier.fillMaxSize()) {
        var buttonNumber = 1
        for (row in 1..8) {
            Row(modifier = Modifier.weight(1f)) {
                Spacer(modifier = Modifier.width(4.dp))
                for (col in 1..5) {
                    CalculatorButton(
                        "$buttonNumber", "2nd", "3rd",
                        if (col == 3) "" else "4th",
                        Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    buttonNumber++
                }
            }
            if (row < 8) {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
