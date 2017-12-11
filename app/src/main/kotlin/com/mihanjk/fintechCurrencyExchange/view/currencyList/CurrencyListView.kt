package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyEntity
import io.reactivex.Observable


interface CurrencyListView : MvpView {
    fun loadCachedDataThenNetwork(): Observable<Boolean>
    fun loadNetworkData(): Observable<Any>
    fun toggleFavoriteIntent(): Observable<CurrencyEntity>
    fun changeCurrentCurrencyIntent(): Observable<CurrencyEntity>
    fun openCurrencyExchangeScreen(): Observable<CurrencyEntity>
    fun currencyExchangeOpened(): Observable<Boolean>
    fun saveDataIntent(): Observable<List<CurrencyEntity>>

    fun render(state: CurrencyListViewState)
}