package com.mihanjk.fintechCurrencyExchange.view.mainActivity

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable


interface MainView : MvpView {
    fun saveCurrencyExchangeIntent(): Observable<MainPartialState.saveCurrencyExchangeFragment>
    fun removeCurrencyExchangeIntent(): Observable<MainPartialState.removeCurrencyExchangeFragment>
    fun saveFilterFragmentIntent(): Observable<Boolean>
    fun removeHistoryFilterIntent(): Observable<Boolean>
    fun openFragmentTab(): Observable<MainPartialState>
    fun removeFragmentFromStack(): Observable<MainPartialState.removeFragmentFromStack>

    fun render(state: MainViewState)
}