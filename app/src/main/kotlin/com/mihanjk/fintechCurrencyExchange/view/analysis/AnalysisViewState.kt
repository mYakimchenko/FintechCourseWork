package com.mihanjk.fintechCurrencyExchange.view.analysis

import com.mihanjk.fintechCurrencyExchange.model.CurrencyEntity
import com.mihanjk.fintechCurrencyExchange.model.data.TimePeriod

data class AnalysisViewState(
        val currentCurrency: CurrencyEntity,
        val timePeriod: TimePeriod,
        val list: List<CurrencyEntity>,
        val chardData: List<>,
        val loading: Boolean,
        val error: Throwable
)

