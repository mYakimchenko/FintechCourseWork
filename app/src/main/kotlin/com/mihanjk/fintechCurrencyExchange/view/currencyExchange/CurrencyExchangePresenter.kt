package com.mihanjk.fintechCurrencyExchange.view.currencyExchange

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.mihanjk.fintechCurrencyExchange.businesslogic.interactor.CurrencyExchangeInteractor
import javax.inject.Inject

class CurrencyExchangePresenter @Inject constructor(
        mInteractor: CurrencyExchangeInteractor
) : MviBasePresenter<CurrencyExchangeView, CurrencyExchangeViewState>() {
    override fun bindIntents() {
        TODO()
    }
}