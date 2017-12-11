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
                intent(CurrencyListView::loadCachedDataThenNetwork)
                        .flatMap { mInteractor.getCurrencyList() }
//                        .flatMap { mInteractor.getDatabaseCurrency() }
                        .doOnNext { Log.d("Rx", "Load current list") }
//                        .filter { it.isNotEmpty() }
//                        .switchIfEmpty { mInteractor.getApiCurrency().error }
                        .map { mInteractor.sortData(it) }
                        .doOnNext { Log.d("Rx", "Sort current list") }
                        .map { PartialStateChanges.CurrencyListLoaded(it) as PartialStateChanges }
//                        .map { if (it.isEmpty()) PartialStateChanges.ShowStub else
//                            PartialStateChanges.FirstTimeListLoaded(it) }
                        .startWith(PartialStateChanges.Loading)
                        .onErrorReturn { PartialStateChanges.Error(it) }
                        .subscribeOn(Schedulers.io())

        val refreshCurrencyList =
                intent(CurrencyListView::loadNetworkData)
                        .doOnNext { Log.d("Rx", "Refresh") }
                        .flatMap { mInteractor.getCurrencyList() }
                        .map { CurrencyListLoaded(it) as PartialStateChanges }
                        .startWith(PartialStateChanges.Refreshing)
                        .onErrorReturn { PartialStateChanges.Error(it) }
                        .subscribeOn(Schedulers.io())

        val toggleFavorite =
                intent(CurrencyListView::toggleFavoriteIntent)
                        .doOnNext { Log.d("Rx", "Toggle favorite") }
                        .map { ToggleFavoriteCompleted(it) }

        val changeCurrentCurrency =
                intent(CurrencyListView::changeCurrentCurrencyIntent)
                        .doOnNext { Log.d("Rx", "change current currency") }
                        .map { CurrentCurrencyChanged(it) }

        val makeCurrencyExchange =
                intent(CurrencyListView::openCurrencyExchangeScreen)
                        .doOnNext { Log.d("Rx", "make exchange") }
                        .map { NavigateToCurrencyExchange(it) }

        val currencyExchangeOpened = intent(CurrencyListView::currencyExchangeOpened)
                .doOnNext { Log.d("Rx", "opened") }
                .map { PartialStateChanges.CurrencyExchangeOpened }

        intent(CurrencyListView::saveDataIntent)
                .doOnNext { Log.d("Rx", "Caching into database: $it") }
                .flatMapCompletable { mInteractor.saveCurrencyEntityIntoDatabase(it) }
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
                is Loading -> previousState.copy(loading = true, updating = false, error = null)
                is Refreshing -> previousState.copy(loading = false, updating = true, error = null)
                is Error -> previousState.copy(loading = false, updating = false, error = partialChanges.error)
                is CurrencyListLoaded -> previousState.copy(loading = false, data = partialChanges.data)
            // todo why don't called render method???
                is ToggleFavoriteCompleted -> {
                    Log.d("Rx", "Toggle favorite viewstate reducer")
                    val name = partialChanges.clicked.name
                    previousState.copy(data = previousState.data.apply {
                        val element = find { it.name == name }
                        element?.let { it.isFavorite = !it.isFavorite }
                    })
                }
                is NavigateToCurrencyExchange -> {
                    val name = partialChanges.clicked.name
                    val clickedCurrency = if (previousState.currentCurrency != null) name
                    else previousState.data.find { it.isFavorite && it.name != name }?.name
                            ?: if (name == "USD") "RUB" else "USD"
                    previousState.copy(currentCurrency = previousState.currentCurrency ?: partialChanges.clicked,
                            clickedCurrency = clickedCurrency)
                }
                is CurrentCurrencyChanged -> previousState.copy(currentCurrency = partialChanges.entity,
                        data = previousState.data.toMutableList().apply {
                            remove(partialChanges.entity)
                            previousState.currentCurrency?.let { add(it) }
                        }.toList())
                is CurrencyExchangeOpened -> previousState.copy(clickedCurrency = null, currentCurrency = null)
            }
}