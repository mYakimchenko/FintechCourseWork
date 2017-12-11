package com.mihanjk.fintechCurrencyExchange.view.currencyList

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.mihanjk.fintechCurrencyExchange.CurrencyApplication
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyEntity
import com.mihanjk.fintechCurrencyExchange.view.mainActivity.MainActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_currency_list.*
import kotlinx.android.synthetic.main.fragment_currency_list.view.*

class CurrencyListFragment : CurrencyListView,
        MviFragment<CurrencyListView, CurrencyListPresenter>() {

    companion object {
        const val TAG = "CurrencyList"
    }

    private lateinit var mAdapter: CurrencyListAdapter
    private val currencyExchangeSubject = PublishSubject.create<Boolean>()
    private val cachingSubject = PublishSubject.create<List<CurrencyEntity>>()

    override fun currencyExchangeOpened(): Observable<Boolean> = currencyExchangeSubject

    override fun createPresenter(): CurrencyListPresenter =
            (activity.application as CurrencyApplication).component.getCurrencyListPresenter()

    override fun loadCachedDataThenNetwork(): Observable<Boolean> =
            Observable.just(true)

    override fun loadNetworkData(): Observable<Any> =
            RxSwipeRefreshLayout.refreshes(swipeRefresh)

    override fun toggleFavoriteIntent(): Observable<CurrencyEntity> =
            mAdapter.starClickSubject

    override fun changeCurrentCurrencyIntent(): Observable<CurrencyEntity> =
            mAdapter.longClickSubject

    override fun openCurrencyExchangeScreen(): Observable<CurrencyEntity> =
            mAdapter.clickSubject

    override fun saveDataIntent(): Observable<List<CurrencyEntity>> =
            cachingSubject

    override fun render(state: CurrencyListViewState) {
        Log.d("Rx", "CurrencyListFragment state: $state")
        if (state.clickedCurrency != null) {
            openExchangeFragment(state.currentCurrency?.name, state.clickedCurrency)
            currencyExchangeSubject.onNext(true)
        } else if (!state.loading && state.error == null) {
            renderData(state)
        } else if (state.loading) {
            renderLoading()
        } else if (state.error != null) {
            Log.d("Error", "Some error happened: ", state.error)
            renderError(state.error.localizedMessage ?: "Unknown error")
        } else if (state.updating) {
            renderUpdate()
        } else {
            throw IllegalStateException("Unknown view state $state")
        }
    }

    private fun openExchangeFragment(first: String?, second: String) {
        (activity as MainActivity).openCurrencyExchangeScreen(first ?: second, second)
    }

    fun renderData(state: CurrencyListViewState) {
        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        state.currentCurrency?.let { selectedCurrency.text = it.name }
        mAdapter.updateValues(state.data)
    }

    fun renderLoading() {
        progressBar.visibility = View.VISIBLE
        swipeRefresh.isRefreshing = false
    }

    fun renderUpdate() {
        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = true
    }

    fun renderError(message: String) {
        progressBar.visibility = View.GONE
        swipeRefresh.isRefreshing = false
        val snackbar = Snackbar.make(activity.root_view, message, Snackbar.LENGTH_LONG)
        val layoutParams = snackbar.view.layoutParams as FrameLayout.LayoutParams
        layoutParams.setMargins(0, 0, 0, activity.bottomNavigation.height)
        snackbar.view.layoutParams = layoutParams
        snackbar.show()
    }

    override fun onPause() {
        super.onPause()
        cachingSubject.onNext(mAdapter.mValues)
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).removeFromStack(TAG)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_currency_list, container, false)

        val recycler = view.list
        recycler.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        mAdapter = CurrencyListAdapter(emptyList())
        recycler.adapter = mAdapter
        return view
    }

}
