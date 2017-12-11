package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyEntity

data class CurrencyListViewState(
        val data: List<CurrencyEntity> = emptyList(),
        val currentCurrency: CurrencyEntity? = null,
        val clickedCurrency: String? = null,
        val loading: Boolean = false,
        val updating: Boolean = false,
        val firstTimeLoaded: Boolean = false,
        val error: Throwable? = null)
