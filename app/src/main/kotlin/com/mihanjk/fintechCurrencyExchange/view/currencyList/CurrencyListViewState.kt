package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyEntity

data class CurrencyListViewState(
        val data: List<CurrencyEntity> = emptyList(),
        val currencyCurrency: String? = null,
        val clickedCurrency: String? = null,
        val loading: Boolean = false,
        val returnFromExchange: Boolean = false,
        val error: Throwable? = null)
