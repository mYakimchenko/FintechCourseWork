package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.mihanjk.fintechCurrencyExchange.mvibase.MviResult


sealed class CurrencyListResult : MviResult {
    data class LoadCurrencyResult()

}