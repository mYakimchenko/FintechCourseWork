package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.mihanjk.fintechCurrencyExchange.model.CurrencyEntity
import com.mihanjk.fintechCurrencyExchange.mvibase.MviViewState


data class CurrencyListViewState(
        val currentCurrency: CurrencyEntity?,
        val data: List<CurrencyEntity>,
        val error: Throwable?) : MviViewState {
    companion object {
        fun idle() = CurrencyListViewState(
                currentCurrency = null,
                data = emptyList(),
                error = null
        )
    }
}