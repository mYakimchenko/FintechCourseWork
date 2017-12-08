package com.mihanjk.fintechCurrencyExchange.view.currencyExchange

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyEntity
import io.reactivex.Observable

interface CurrencyExchangeView : MvpView {
    fun getCurrencyCourseIntent(): Observable<Pair<CurrencyEntity, CurrencyEntity>>
    fun makeCurrencyExchangeIntent(): Observable<CurrencyEntity>
    fun updateCourseIntent(): Observable<CurrencyEntity>

    fun render(state: CurrencyExchangeViewState)
}

