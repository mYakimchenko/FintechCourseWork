package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.mihanjk.fintechCurrencyExchange.model.CurrencyEntity

data class CurrencyListViewState(
        val data: List<CurrencyEntity> = emptyList(),
        val currencyCurrency: CurrencyEntity? = null,
        val clickedCurrency: CurrencyEntity? = null,
        val loading: Boolean = false,
        val error: Throwable? = null)
