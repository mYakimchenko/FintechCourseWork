package com.mihanjk.fintechCurrencyExchange.view.currencyExchange

import com.mihanjk.fintechCurrencyExchange.model.CurrencyEntity

data class CurrencyExchangeViewState(
        val firstCurrency: CurrencyEntity,
        val firstCurrencyAmount: Double,

        val secondCurrency: CurrencyEntity,
        val secondCurrencyAmount: Double,

        val course: Double,
        val courseTimeStamp: Long,
        val isCourseFresh: Boolean,

        val loading: Boolean,
        val error: Throwable
)

