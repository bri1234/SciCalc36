package com.bri1234.ti36calculator

class CalculatorDisplayData {
    var digitsLarge = CharArray(11) { ' ' }
    var decimalPointIndex = -1

    var digitsSmall = CharArray(3) { ' ' }

    var labelM = false
    var label2nd = false
    var label3rd = false
    var labelBin = false
    var labelOct = false
    var labelHex = false
    var labelStat = false
    var labelDeg = false
    var labelRad = false
    var labelGrad = false
    var labelX = false
    var labelR = false
    var LabelBracket = false
}
