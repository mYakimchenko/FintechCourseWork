package com.mihanjk.fintechCurrencyExchange.view.mainActivity

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable


interface MainView : MvpView {
    fun saveCurrencyExchangeIntent(): Observable<MainPartialState.saveCurrencyExchangeFragment>
    fun removeCurrencyExchangeIntent(): Observable<MainPartialState.removeCurrencyExchangeFragment>
    fun openLastExchangeFragmentIntent(): Observable<MainPartialState.openLastExchange>
    fun openAnalysisFragmentIntent(): Observable<MainPartialState.openAnalysisFragment>
    fun saveFilterFragmentIntent(): Observable<Boolean>
    fun removeHistoryFilterIntent(): Observable<Boolean>
    fun openLastHistoryFragment(): Observable<MainPartialState.openLastHistoryFragment>

    fun render(state: MainViewState)
}