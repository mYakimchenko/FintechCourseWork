package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.mihanjk.fintechCurrencyExchange.mvibase.MviViewModel
import com.mihanjk.fintechCurrencyExchange.view.currencyList.CurrencyListAction.*
import com.mihanjk.fintechCurrencyExchange.view.currencyList.CurrencyListIntent.*
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject


class CurrencyListViewModel(val mActionProcessorHolder: CurrencyListActionProcessorHolder) :
        MviViewModel<CurrencyListIntent, CurrencyListViewState> {
    val mIntentSubject = PublishSubject.create<CurrencyListIntent>()
    val mStatesObservable = compose()

    override fun processIntents(intents: Observable<CurrencyListIntent>) =
            intents.subscribe(mIntentSubject)

    override fun states(): Observable<CurrencyListViewState> = mStatesObservable

    private fun compose() =
            mIntentSubject
                    .scan(initialIntentFilter)
                    .map { this.actionFromIntent(it) }
                    .compose(mActionProcessorHolder.actionProcessor)
                    .scan(CurrencyListViewState.idle(), reducer)

    val initialIntentFilter = BiFunction { _: CurrencyListIntent, newIntent: CurrencyListIntent ->
        if (newIntent is InitialIntent) GetLastState
        else newIntent
    }


    fun actionFromIntent(intent: CurrencyListIntent) =
            when (intent) {
                is InitialIntent -> LoadCurrencyListAction
                is StarClickIntent -> StarClickAction(intent.currencyEntity)
                is CurrencyClickIntent -> {
                    TODO("How to get current currency from viewstate?")
                    CurrencyExchangeAction(intent.entity, intent.entity)
                }
                is CurrencyLongClickIntent -> LongClickAction(intent.currencyEntity)
            }

    val reducer = BiFunction<CurrencyListViewState, CurrencyListResult, CurrencyListViewState> { previousState: CurrencyListViewState, result: CurrencyListResult ->
        when (result) {
            is
        }
    }
}
