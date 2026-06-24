package com.bri1234.scicalc36.contracts

interface IStatistic {

    val count: Int
    val sumX: Double
    val sumX2: Double
    val sumY: Double
    val sumY2: Double
    val sumXY: Double

    fun clearStatistic()

    fun correlationCoefficient(): Double
    fun intercept(): Double
    fun slope(): Double
}

