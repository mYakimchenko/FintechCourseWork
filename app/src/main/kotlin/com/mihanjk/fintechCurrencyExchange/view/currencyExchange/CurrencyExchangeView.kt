package com.mihanjk.fintechCurrencyExchange.view.currencyExchange

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyTransaction
import io.reactivex.Observable

interface CurrencyExchangeView : MvpView {
    fun initializeViewState(first: String, second: String): Observable<Pair<String, String>>
    fun getCurrencyCourseIntent(): Observable<Pair<String, String>>
    fun makeCurrencyExchangeIntent(): Observable<CurrencyTransaction>
    fun modifyAmountFirstCurrencyIntent(): Observable<Double>
    fun modifyAmountSecondCurrencyIntent(): Observable<Double>

    fun render(state: CurrencyExchangeViewState)
}

