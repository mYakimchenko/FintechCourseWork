package com.mihanjk.fintechCurrencyExchange.view.currencyList

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.mihanjk.fintechCurrencyExchange.CurrencyApplication
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.model.CurrencyEntity
import com.mihanjk.fintechCurrencyExchange.model.data.Currency
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_currency_list.*
import kotlinx.android.synthetic.main.fragment_currency_list.view.*

class CurrencyListFragment : CurrencyListView,
        MviFragment<CurrencyListView, CurrencyListPresenter>() {
    private lateinit var mAdapter: CurrencyListAdapter

    override fun createPresenter(): CurrencyListPresenter =
            (activity.application as CurrencyApplication).component.getCurrencyListPresenter()

    override fun loadCurrencyListIntent(): Observable<Boolean> =
            Observable.just(true)

    override fun toggleFavoriteIntent(): Observable<CurrencyEntity> =
            mAdapter.starClickSubject

    override fun changeCurrentCurrencyIntent(): Observable<CurrencyEntity> =
            Observable.just(CurrencyEntity(0, Currency.USD, false, 0))
//            mAdapter.longClickSubject

    override fun makeCurrencyExchange(): Observable<CurrencyEntity> =
            mAdapter.clickSubject

    override fun saveCurrencyListIntent(): Observable<List<CurrencyEntity>> =
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    override fun render(state: CurrencyListViewState) {
        if (!state.loading && state.error == null) {
            renderData(state)
        } else if (state.loading) {
            renderLoading()
        } else if (state.error != null) {
            renderError(state.error.localizedMessage)
        } else {
            throw IllegalStateException("Unknown view state $state")
        }
    }

    fun renderData(state: CurrencyListViewState) {
        progressBar.visibility = View.GONE
        state.currencyCurrency?.let { selectedCurrency.text = it.name.toString() }
        // todo why i need mutable list?
        mAdapter.mValues = state.data.toMutableList()
    }

    fun renderLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun renderError(message: String) {
        progressBar.visibility = View.GONE
        val snackbar = Snackbar.make(activity.root_view, message, Snackbar.LENGTH_LONG)
        val layoutParams = snackbar.view.layoutParams as FrameLayout.LayoutParams
        layoutParams.setMargins(0, 0, 0, activity.bottomNavigation.height)
        snackbar.view.layoutParams = layoutParams
        snackbar.show()
    }

//    override fun onLongClicked(item: CurrencyEntity): CurrencyEntity? {
//        selectedCurrency.text = getString(R.string.current_currency, item.name.toString())
//        val previousCurrency = mSelectedCurrency
//        mSelectedCurrency = item
//        return previousCurrency
//    }
//
//    override fun onClicked(name: Currency) {
//        val fragment = fragmentManager.findFragmentByTag(MainActivity.CURRENCY_EXCHANGE)
//        if (fragment == null) {
//            var firstCurrency = mSelectedCurrency?.name
//            if (firstCurrency == null) {
//                val entity = mAdapter.mValues.sortedBy { it.position }.find { it.name != name && it.isFavorite }
//                if (entity == null) {
//                    firstCurrency = when (name) {
//                        Currency.USD -> Currency.RUB
//                        else -> Currency.USD
//                    }
//                }
//            }
//            fragment = CurrencyExchangeFragment.newInstance(firstCurrency, name)
//        }
//        // todo what i need to do, if fragment already exists
//        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
//                CurrencyExchangeFragment.newInstance(
//                        mSelectedCurrency?.name ?:
//                                mAdapter.mValues
//                                        .sortedBy { it.isFavorite }
//                                        .find { !it.name.equals(name) }
//                                        .also { it == null }, name),
//                MainActivity.CURRENCY_EXCHANGE)
//                .addToBackStack(MainActivity.CURRENCY_EXCHANGE)
//                .commit()
//    }
//
//    override fun onStarImageClicked(item: CurrencyEntity) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_currency_list, container, false)

        val recycler = view.list
        recycler.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        mAdapter = CurrencyListAdapter(ArrayList())
        recycler.adapter = mAdapter
//        mDatabase.currencyDao().getAllRecords()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    mAdapter = CurrencyListAdapter(
//                            if (it.isNotEmpty()) it.sortedBy { it.position }.toMutableList()
//                            else Currency.values().mapIndexed { index, currency ->
//                                CurrencyEntity(null, currency, false, index)
//                            }.toMutableList(),
//                            this@CurrencyListFragment)
//                    recycler.adapter = mAdapter
//                }, { e ->
//                    val snackbar = Snackbar.make(activity.root_view, e.message.toString(), Snackbar.LENGTH_SHORT)
//                    val layoutParams = snackbar.view.layoutParams as FrameLayout.LayoutParams
//                    layoutParams.setMargins(0, 0, 0, activity.bottomNavigation.height)
//                    snackbar.view.layoutParams = layoutParams
//                    snackbar.show()
//                })
        return view
    }


//    override fun onPause() {
//        super.onPause()
//        mSelectedCurrency?.let { mAdapter.mValues.add(it.position, it) }
//        mAdapter.mValues.forEachIndexed { index, entity -> entity.position = index }
//        Completable.fromAction { mDatabase.currencyDao().insert(*(mAdapter.mValues.toTypedArray())) }
//                .subscribeOn(Schedulers.io())
//                .subscribe()
//    }
}
