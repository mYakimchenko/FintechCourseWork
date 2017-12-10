package com.mihanjk.fintechCurrencyExchange.view.currencyList

import android.util.Log
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.mihanjk.fintechCurrencyExchange.businesslogic.interactor.CurrencyListInteractor
import com.mihanjk.fintechCurrencyExchange.view.currencyList.PartialStateChanges.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class CurrencyListPresenter @Inject constructor(
        val mInteractor: CurrencyListInteractor
) : MviBasePresenter<CurrencyListView, CurrencyListViewState>() {

    override fun bindIntents() {
        val loadCurrencyList =
                intent(CurrencyListView::loadCurrencyListIntent)
                        .flatMap { mInteractor.getCurrencyList() }
                        .doOnNext { Log.d("Test", "Load current list") }
                        .map { PartialStateChanges.CurrencyListLoaded(it) as PartialStateChanges }
                        .startWith(PartialStateChanges.Loading)
                        .onErrorReturn { PartialStateChanges.Error(it) }
                        .subscribeOn(Schedulers.io())

        val refreshCurrencyList =
                intent(CurrencyListView::refreshCurrencyListIntent)
                        .flatMap { mInteractor.getCurrencyList() }
                        .map { CurrencyListLoaded(it) as PartialStateChanges }

        val toggleFavorite =
                intent(CurrencyListView::toggleFavoriteIntent)
                        .doOnNext { Log.d("Test", "Toggle favorite") }
                        .map { ToggleFavoriteCompleted(it) }

        val changeCurrentCurrency =
                intent(CurrencyListView::changeCurrentCurrencyIntent)
                        .doOnNext { Log.d("Test", "change current currency") }
                        .map { CurrentCurrencyChanged(it) }

        val makeCurrencyExchange =
                intent(CurrencyListView::makeCurrencyExchange)
                        .doOnNext { Log.d("Test", "make exchange") }
                        .map { NavigateToCurrencyExchange(it) }

        val currencyExchangeOpened = intent(CurrencyListView::currencyExchangeOpened)
                .doOnNext { Log.d("Test", "opened") }
                .map { PartialStateChanges.CurrencyExchangeOpened }

        intent(CurrencyListView::saveCurrencyListIntent)
                .doOnNext { Log.d("Rx", "Caching into database: $it") }
                .map { mInteractor.saveCurrencyEntityIntoDatabase(it) }
                .subscribeOn(Schedulers.io())
                .subscribe()

        val allIntentObservable = Observable.merge(loadCurrencyList, toggleFavorite,
                changeCurrentCurrency, makeCurrencyExchange)
                .mergeWith(currencyExchangeOpened)
                .mergeWith(refreshCurrencyList)
                .observeOn(AndroidSchedulers.mainThread())


        subscribeViewState(allIntentObservable.scan(CurrencyListViewState(),
                this::viewStateRecuder),
//                .distinctUntilChanged(),
                CurrencyListView::render)

    }

    fun viewStateRecuder(previousState: CurrencyListViewState, partialChanges: PartialStateChanges) =
            when (partialChanges) {
                is Loading -> previousState.copy(loading = true)
                is Error -> previousState.copy(loading = false, error = partialChanges.error)
                is CurrencyListLoaded -> previousState.copy(loading = false, data = partialChanges.data)
            // todo why don't called render method???
                is ToggleFavoriteCompleted -> {
                    val name = partialChanges.clicked.name
                    previousState.copy(data = previousState.data.apply {
                        val element = find { it.name == name }
                        element?.let { it.isFavorite = !it.isFavorite }
                    })
                }
                is NavigateToCurrencyExchange -> {
                    val name = partialChanges.clicked.name
                    val clickedCurrency = if (previousState.currencyCurrency != null) name
                    else previousState.data.find { it.isFavorite && it.name != name }?.name
                            ?: if (name == "USD") "RUB" else "USD"
                    previousState.copy(returnFromExchange = true, currencyCurrency = previousState.currencyCurrency ?: name,
                            clickedCurrency = clickedCurrency)
                }
                is CurrentCurrencyChanged -> previousState.copy(currencyCurrency = partialChanges.entity.name)
                is CurrencyExchangeOpened -> previousState.copy(clickedCurrency = null, currencyCurrency = null)
            }
}