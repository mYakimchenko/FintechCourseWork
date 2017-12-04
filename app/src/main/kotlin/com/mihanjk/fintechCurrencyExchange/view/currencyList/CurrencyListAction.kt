package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.mihanjk.fintechCurrencyExchange.model.CurrencyEntity


sealed class CurrencyListAction {
    object LoadCurrencyListAction : CurrencyListAction()
    data class StarClickAction(val entity: CurrencyEntity) : CurrencyListAction()
    data class LongClickAction(val entity: CurrencyEntity) : CurrencyListAction()
    data class CurrencyExchangeAction(val first: CurrencyEntity,
                                      val second: CurrencyEntity) : CurrencyListAction()
    // TODO make save into database
}