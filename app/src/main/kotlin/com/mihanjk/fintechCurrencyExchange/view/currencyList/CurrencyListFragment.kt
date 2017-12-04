package com.mihanjk.fintechCurrencyExchange.view.currencyList

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.mihanjk.fintechCurrencyExchange.CurrencyApplication
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.model.CurrencyDatabase
import com.mihanjk.fintechCurrencyExchange.model.CurrencyEntity
import com.mihanjk.fintechCurrencyExchange.mvibase.MviView
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_currency_list.view.*
import javax.inject.Inject

class CurrencyListFragment : MviView<CurrencyListIntent, CurrencyListViewState>, Fragment() {
    override fun intents(): Observable<CurrencyListIntent> {
        return Observable.merge(initialIntent(),
                starClickIntent(),
                currencyLongClickIntent(),
                currencyClickIntent())
    }

    private fun initialIntent() = Observable.just(CurrencyListIntent.InitialIntent)

    private fun starClickIntent() = mAdapter.starClickSubject.map { CurrencyListIntent.StarClickIntent(it) }
    private fun currencyLongClickIntent() = mAdapter.longClickSubject.map { CurrencyListIntent.CurrencyLongClickIntent(it) }
    private fun currencyClickIntent() = mAdapter.clickSubject.map { CurrencyListIntent.CurrencyClickIntent(it) }


    override fun render(state: CurrencyListViewState?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    private lateinit var mAdapter: CurrencyListAdapter
    //    private var mListener: OnListFragmentInteractionListener? = null
    private var mSelectedCurrency: CurrencyEntity? = null

    @Inject lateinit var mDatabase: CurrencyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as CurrencyApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_currency_list, container, false)

        view.selectedCurrency.text = getString(R.string.current_currency, "")

        val recycler = view.list
        recycler.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
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

    override fun onPause() {
        super.onPause()
        mSelectedCurrency?.let { mAdapter.mValues.add(it.position, it) }
        mAdapter.mValues.forEachIndexed { index, entity -> entity.position = index }
        Completable.fromAction { mDatabase.currencyDao().insert(*(mAdapter.mValues.toTypedArray())) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}
