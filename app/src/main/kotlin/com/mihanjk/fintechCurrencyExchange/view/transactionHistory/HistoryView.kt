package com.mihanjk.fintechCurrencyExchange.view.transactionHistory

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable

interface HistoryView : MvpView {
    fun loadTransactions(): Observable<Boolean>

    fun render(state: HistoryViewState)
}

