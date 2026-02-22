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

/**
 * Displays a single calculator button with its properties.
 * The button has two lines of text: text1st and text2nd.
 * Above the button, there are two additional lines of text: text3rd and text4th.
 * If text4th is empty, text3rd is centered. Otherwise, text3rd is left-aligned and text4th is right-aligned.
 * @param buttonProperties The properties of the calculator button, including text and colors.
 * @param modifier The modifier to be applied to the button layout.
 */
@Composable
fun CalculatorButton(buttonProperties : CalculatorButtonProperties, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (buttonProperties.text4th.isEmpty()) {
            // center text3 if text4 is empty
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buttonProperties.text3rd,
                    fontSize = 16.sp,
                    color = buttonProperties.test3rdColor,
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
                    text = buttonProperties.text3rd,
                    fontSize = 16.sp,
                    color = buttonProperties.test3rdColor,
                    modifier = Modifier.weight(1f).padding(start = 5.dp),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = buttonProperties.text4th,
                    fontSize = 16.sp,
                    color = buttonProperties.test4thColor,
                    modifier = Modifier.weight(1f).padding(end = 5.dp),
                    maxLines = 1,
                    textAlign = TextAlign.End
                )
            }
        }
        Button(
            onClick = {},
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonProperties.buttonColor),
            contentPadding = PaddingValues(2.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buttonProperties.text2nd,
                    fontSize = 16.sp,
                    color = buttonProperties.test2ndColor,
                    maxLines = 1
                )
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Text(
                    text = buttonProperties.text1st,
                    fontSize = 18.sp,
                    color = buttonProperties.test1stColor,
                    maxLines = 1
                )
            }
        }
    }
}

/**
 * Displays the calculator buttons in a grid layout.
 * The grid has 5 columns and fills the available space.
 * If a button's text1st is empty, the grid cell is empty.
 */
@Composable
fun CalculatorButtons() {
    GridLayout(
        columns = 5,
        rows = 9,
        modifier = Modifier.fillMaxSize(),
        gridCellInfos = CALCULATOR_BUTTON_LIST,
    ) {
        CALCULATOR_BUTTON_LIST.forEach { buttonProperties ->
            if (buttonProperties.text1st.isEmpty()) {
                Spacer(modifier = Modifier.padding(0.dp))
            } else {
                CalculatorButton(
                    buttonProperties = buttonProperties,
                    Modifier.padding(4.dp).fillMaxSize()
                )
            }
        }
    }
}
