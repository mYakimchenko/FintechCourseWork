package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.mihanjk.fintechCurrencyExchange.model.CurrencyEntity

sealed class PartialStateChanges {
    object Loading : PartialStateChanges()
    data class Error(val error: Throwable) : PartialStateChanges()
    data class CurrencyListLoaded(val data: List<CurrencyEntity>) : PartialStateChanges()
    data class ToggleFavoriteCompleted(val entity: CurrencyEntity) : PartialStateChanges()
    data class NavigateToCurrencyExchange(val entity: CurrencyEntity) : PartialStateChanges()
    data class CurrentCurrencyChanged(val entity: CurrencyEntity) : PartialStateChanges()
}