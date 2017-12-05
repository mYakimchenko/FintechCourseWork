package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.mihanjk.fintechCurrencyExchange.model.CurrencyEntity
import io.reactivex.Observable


interface CurrencyListView : MvpView {
    fun loadCurrencyListIntent(): Observable<Boolean>
    fun toggleFavoriteIntent(): Observable<CurrencyEntity>
    fun changeCurrentCurrencyIntent(): Observable<CurrencyEntity>
    fun makeCurrencyExchange(): Observable<CurrencyEntity>
    fun saveCurrencyListIntent(): Observable<List<CurrencyEntity>>

    fun render(state: CurrencyListViewState)
}