package com.mihanjk.fintechCurrencyExchange.view.currencyList

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.mihanjk.fintechCurrencyExchange.model.CurrencyDatabase
import com.mihanjk.fintechCurrencyExchange.view.currencyList.PartialStateChanges.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class CurrencyListPresenter @Inject constructor(
        val mDatabase: CurrencyDatabase
) : MviBasePresenter<CurrencyListView, CurrencyListViewState>() {

    override fun bindIntents() {
        val loadCurrencyList: Observable<PartialStateChanges> =
                intent(CurrencyListView::loadCurrencyListIntent)
                        .flatMap { mDatabase.currencyDao().getAllRecords().toObservable() }
                        .map { PartialStateChanges.CurrencyListLoaded(it) as PartialStateChanges }
                        .startWith ( PartialStateChanges.Loading )
                        .onErrorReturn { PartialStateChanges.Error(it) }
                        .subscribeOn(Schedulers.io())

        val toggleFavorite =
                intent(CurrencyListView::toggleFavoriteIntent)
                        .map { ToggleFavoriteCompleted(it) }

        val changeCurrentCurrency =
                intent(CurrencyListView::changeCurrentCurrencyIntent)
                        .map { CurrentCurrencyChanged(it) }

        val makeCurrencyExchange =
                intent(CurrencyListView::makeCurrencyExchange)
                        .map { NavigateToCurrencyExchange(it) }

        val allIntentObservable = Observable.merge(loadCurrencyList, toggleFavorite,
                changeCurrentCurrency, makeCurrencyExchange)
                .observeOn(AndroidSchedulers.mainThread())

        subscribeViewState(allIntentObservable.scan(CurrencyListViewState(),
                this::viewStateRecuder).distinctUntilChanged(),
                CurrencyListView::render)

//        TODO("How to save to database?")
//        intent(CurrencyListView::saveCurrencyListIntent)
//                .flatMap { mDatabase.currencyDao().insert(*(it.toTypedArray())).toObservable() }
//                .subscribeOn(Schedulers.io())
//                .subscribe()
    }

    fun viewStateRecuder(previousState: CurrencyListViewState, partialChanges: PartialStateChanges) =
            when (partialChanges) {
                is Loading -> previousState.copy(loading = true)
                is Error -> previousState.copy(loading = false, error = partialChanges.error)
                is CurrencyListLoaded -> previousState.copy(loading = false, data = partialChanges.data)
                is ToggleFavoriteCompleted -> previousState.copy(data = previousState.data.apply {
                    val element = find { it.id == partialChanges.entity.id }
                    element?.let { it.isFavorite = !it.isFavorite }
                })
                is NavigateToCurrencyExchange -> previousState.copy(clickedCurrency = partialChanges.entity)
                is CurrentCurrencyChanged -> previousState.copy(currencyCurrency = partialChanges.entity)
            }
}