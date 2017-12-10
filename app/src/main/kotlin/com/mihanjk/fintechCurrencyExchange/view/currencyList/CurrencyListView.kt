package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyEntity
import io.reactivex.Observable


interface CurrencyListView : MvpView {
    fun loadCurrencyListIntent(): Observable<Boolean>
    fun refreshCurrencyListIntent(): Observable<Boolean>
    fun toggleFavoriteIntent(): Observable<CurrencyEntity>
    fun changeCurrentCurrencyIntent(): Observable<CurrencyEntity>
    fun makeCurrencyExchange(): Observable<CurrencyEntity>
    fun currencyExchangeOpened(): Observable<Boolean>
    fun saveCurrencyListIntent(): Observable<List<CurrencyEntity>>

    fun render(state: CurrencyListViewState)
}