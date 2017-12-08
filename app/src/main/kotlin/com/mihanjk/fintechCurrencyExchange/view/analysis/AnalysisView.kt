package com.mihanjk.fintechCurrencyExchange.view.analysis

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.mihanjk.fintechCurrencyExchange.model.data.TimePeriod
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyEntity
import io.reactivex.Observable

interface AnalysisView : MvpView {
    fun getCurrencyCourseForDate(time: TimePeriod): Observable<Pair<CurrencyEntity, TimePeriod>>
    fun changeCurrentCurrency(): Observable<CurrencyEntity>

    fun render(state: AnalysisViewState)
}

