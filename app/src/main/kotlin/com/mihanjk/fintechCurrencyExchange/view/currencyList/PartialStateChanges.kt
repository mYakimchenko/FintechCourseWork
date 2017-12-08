package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyEntity

sealed class PartialStateChanges {
    object Loading : PartialStateChanges()
    object CurrencyExchangeOpened : PartialStateChanges()
    data class Error(val error: Throwable) : PartialStateChanges()
    data class CurrencyListLoaded(val data: List<CurrencyEntity>) : PartialStateChanges()
    data class ToggleFavoriteCompleted(val clicked: CurrencyEntity) : PartialStateChanges()
    data class NavigateToCurrencyExchange(val clicked: CurrencyEntity) : PartialStateChanges()
    data class CurrentCurrencyChanged(val entity: CurrencyEntity) : PartialStateChanges()
}