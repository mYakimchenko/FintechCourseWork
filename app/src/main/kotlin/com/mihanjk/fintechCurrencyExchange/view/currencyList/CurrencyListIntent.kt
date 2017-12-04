package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.mihanjk.fintechCurrencyExchange.model.CurrencyEntity
import com.mihanjk.fintechCurrencyExchange.mvibase.MviIntent


sealed class CurrencyListIntent : MviIntent {
    object InitialIntent : CurrencyListIntent()
    data class StarClickIntent(val currencyEntity: CurrencyEntity) : CurrencyListIntent()
    data class CurrencyLongClickIntent(val currencyEntity: CurrencyEntity) : CurrencyListIntent()
    data class CurrencyClickIntent(val entity: CurrencyEntity) : CurrencyListIntent()
    object GetLastState : CurrencyListIntent()
}