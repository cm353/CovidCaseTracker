package com.example.covidcasetracker.utilFunctions

object UtilFunctions {

    fun caseFatalityRate(totalCases: Double, totalDeaths:Double) : Double {
        val cfrRaw = (totalDeaths / totalCases * 100.0)
        val cfr = Math.round(cfrRaw * 100)/100.0
        return cfr
    }
}