package com.mihanjk.fintechCurrencyExchange.businesslogic.interactor

import com.mihanjk.fintechCurrencyExchange.model.data.DateFilter
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyDatabase
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyTransaction
import io.reactivex.Flowable
import javax.inject.Inject

interface HistoryInteractor {
    fun getTransactions(filter: DateFilter): Flowable<List<CurrencyTransaction>>
}

class HistoryInteractorImpl @Inject constructor(
        val mDatabase: CurrencyDatabase
) : HistoryInteractor {
    override fun getTransactions(filter: DateFilter) =
            when (filter) {
                DateFilter.AllTime -> mDatabase.transactionDao().getAllRecords()
                DateFilter.TwoWeek -> mDatabase.transactionDao().getRecordsForTwoWeek()
                DateFilter.Week -> mDatabase.transactionDao().getRecordsForWeek()
                is DateFilter.FromTo -> mDatabase.transactionDao().getRecordsBetweenDates(filter.fromDate,
                        filter.toDate)
            }
}