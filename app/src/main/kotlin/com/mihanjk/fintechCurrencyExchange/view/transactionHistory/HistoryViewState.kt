package com.mihanjk.fintechCurrencyExchange.view.transactionHistory

import com.mihanjk.fintechCurrencyExchange.model.data.DateFilter
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyTransaction

data class HistoryViewState(
        val filter: DateFilter = DateFilter.AllTime,
        val transactions: List<CurrencyTransaction> = emptyList()
)

