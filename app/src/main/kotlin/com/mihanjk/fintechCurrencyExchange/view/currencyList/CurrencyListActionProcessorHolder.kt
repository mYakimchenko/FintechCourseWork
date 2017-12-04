package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.mihanjk.fintechCurrencyExchange.model.CurrencyDatabase
import com.mihanjk.fintechCurrencyExchange.view.currencyList.CurrencyListAction.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer


class CurrencyListActionProcessorHolder(private val mCurrencyListRepository: CurrencyDatabase) {

    val actionProcessor: ObservableTransformer<CurrencyListAction, CurrencyListResult> = ObservableTransformer { actions: Observable<CurrencyListAction> ->
        actions.publish { shared ->
            Observable.merge(
                    shared.ofType(StarClickAction::class.java).compose(starClickProcessor),
                    shared.ofType(LongClickAction::class.java).compose(longClickProcessor),
                    shared.ofType(CurrencyExchangeAction::class.java).compose(currencyExchangeProcessor),
                    shared.ofType(LoadCurrencyListAction::class.java).compose(loadCurrencyListProcessor)
            )
        }
    }


}