package com.mihanjk.fintechCurrencyExchange.view.currencyExchange

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.mihanjk.fintechCurrencyExchange.businesslogic.http.FixerService
import com.mihanjk.fintechCurrencyExchange.model.CurrencyDatabase
import javax.inject.Inject

class CurrencyExchangePresenter @Inject constructor(
        val mDatabase: CurrencyDatabase,
        val mApiService: FixerService
) : MviBasePresenter<CurrencyExchangeView, CurrencyExchangeViewState>() {
    override fun bindIntents() {
        val getCurrencyCourse = intent(CurrencyExchangeView::getCurrencyCourseIntent)
                .flatMap { mApiService.getLatestReference(it.first.name) }
                .startWith { PartialStateChanges. }
    }`
}