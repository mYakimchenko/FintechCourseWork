package com.mihanjk.fintechCurrencyExchange.view.analysis

import com.github.mikephil.charting.data.LineData
import com.mihanjk.fintechCurrencyExchange.model.data.TimePeriod
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyEntity

data class AnalysisViewState(
        val currentCurrency: CurrencyEntity,
        val timePeriod: TimePeriod,
        val list: List<CurrencyEntity>,
        val chartData: LineData,
        val loading: Boolean,
        val error: Throwable
)

