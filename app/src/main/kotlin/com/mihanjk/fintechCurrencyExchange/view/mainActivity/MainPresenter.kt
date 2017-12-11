package com.mihanjk.fintechCurrencyExchange.view.mainActivity

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.mihanjk.fintechCurrencyExchange.view.analysis.AnalysisFragment
import com.mihanjk.fintechCurrencyExchange.view.currencyExchange.CurrencyExchangeFragment
import com.mihanjk.fintechCurrencyExchange.view.currencyList.CurrencyListFragment
import com.mihanjk.fintechCurrencyExchange.view.transactionHistory.HistoryFragment
import io.reactivex.Observable

class MainPresenter : MviBasePresenter<MainView, MainViewState>() {
    override fun bindIntents() {
        val allIntents = Observable.merge(intent(MainView::saveCurrencyExchangeIntent),
                intent(MainView::removeCurrencyExchangeIntent),
                intent(MainView::openFragmentTab))

        subscribeViewState(allIntents.scan(MainViewState(), this::reducer),
//                .distinctUntilChanged()
                MainView::render)
    }

    fun reducer(previous: MainViewState, changes: MainPartialState) =
            when (changes) {
                is MainPartialState.openLastExchange -> previous.copy(exchange = true, history = false,
                        analysis = false,
                        exchangeFragments = previous.exchangeFragments.apply {
                            peekLast() ?: addFirst(CurrencyListFragment())
                        })

                is MainPartialState.saveCurrencyExchangeFragment -> previous.copy(exchange = true,
                        history = false, analysis = false, exchangeFragments =
                previous.exchangeFragments.apply { add(CurrencyExchangeFragment.newInstance(changes.first, changes.second)) })

                is MainPartialState.removeCurrencyExchangeFragment -> previous.copy(exchange = true,
                        history = false, analysis = false, exchangeFragments =
                previous.exchangeFragments.apply { pollLast() })

                is MainPartialState.openAnalysisFragment -> previous.copy(analysis = true,
                        exchange = false, history = false, analysisFragment =
                previous.analysisFragment ?: AnalysisFragment())

                is MainPartialState.openLastHistoryFragment -> previous.copy(history = true,
                        analysis = false, exchange = false, historyFragments =
                previous.historyFragments.apply { peekLast() ?: addFirst(HistoryFragment()) })

                is MainPartialState.removeHistoryFilterFragment -> previous.copy(history = true,
                        analysis = false, exchange = false, historyFragments =
                previous.historyFragments.apply { pollLast() })

                is MainPartialState.removeFragmentFromStack -> when (changes.tag) {
                    CurrencyListFragment.TAG -> previous.copy(exchange = true, history = false,
                            analysis = true, historyFragments = previous.exchangeFragments.apply {
                        remove(find { it?.tag == changes.tag })
                    })
                    else -> throw IllegalStateException("Unknown fragment TAG")
                }
//                    previous.copy(exchange = true,
//                            analysis = false, history = false, historyFragments =
//                    previous.historyFragments.apply {
//                        remove(find { it?.TAG == changes.TAG })
//                    })
            }
}
