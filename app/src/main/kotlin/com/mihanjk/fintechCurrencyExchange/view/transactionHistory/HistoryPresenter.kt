package com.mihanjk.fintechCurrencyExchange.view.transactionHistory

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.mihanjk.fintechCurrencyExchange.businesslogic.interactor.HistoryInteractor
import javax.inject.Inject

class HistoryPresenter @Inject constructor(
        val mInteractor: HistoryInteractor
) : MviBasePresenter<HistoryView, HistoryViewState>() {
    override fun bindIntents() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

