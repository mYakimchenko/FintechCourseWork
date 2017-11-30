package com.mihanjk.fintechCurrencyExchange.view

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView
import com.mihanjk.fintechCurrencyExchange.CurrencyApplication
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.model.api.FixerService
import com.mihanjk.fintechCurrencyExchange.view.analysis.AnalysisFragment
import com.mihanjk.fintechCurrencyExchange.view.currencyExchange.CurrencyExchangeFragment
import com.mihanjk.fintechCurrencyExchange.view.currencyList.CurrencyListFragment
import com.mihanjk.fintechCurrencyExchange.view.transactionHistory.HistoryFilterFragment
import com.mihanjk.fintechCurrencyExchange.view.transactionHistory.HistoryFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : CurrencyExchangeFragment.OnFragmentInteractionListener,
        HistoryFragment.OnFragmentInteractionListener,
        HistoryFilterFragment.OnFragmentInteractionListener,
        AppCompatActivity() {

    override fun onFragmentInteraction() {
        openScreen(CURRENCY_FILTER)
    }

    private val disposables = CompositeDisposable()

    companion object {
        const val CURRENCY_LIST = "CurrencyList"
        const val CURRENCY_EXCHANGE = "CurrencyExchange"
        const val CURRENCY_HISTORY = "CurrencyHistory"
        const val CURRENCY_FILTER = "CurrencyFilter"
        const val CURRENCY_ANALYSIS = "CurrencyAnalysis"
    }

    @Inject
    lateinit var fixerService: FixerService

    override fun onFragmentInteraction(uri: Uri) {
        Log.d(javaClass.name, "some")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as CurrencyApplication).component.inject(this)

        bottomNavigation.selectedItemId = R.id.action_exchange

        RxBottomNavigationView.itemSelections(bottomNavigation).subscribe {
            when (it.itemId) {
                R.id.action_exchange -> openScreen(CURRENCY_LIST)
                R.id.action_history -> openScreen(CURRENCY_HISTORY)
                R.id.action_analysis -> openScreen(CURRENCY_ANALYSIS)
            }
        }

//        val value = fixerService.getDateReference(PathDate(GregorianCalendar(2017, 5, 23)),
//                Currency.RUB, Currency.USD, Currency.BRL)
//        disposables.add(
//                value.observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                        .delay(5, TimeUnit.SECONDS)
//                        .subscribe({ Log.d("TEST", it.toString()) },
//                                { Log.d("TEST", "FUCK ${it.message}") }))
    }

    private fun openScreen(name: String) {
        when (name) {
            CURRENCY_LIST -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,
                                supportFragmentManager.findFragmentByTag(CURRENCY_LIST) ?:
                                        CurrencyListFragment(), CURRENCY_LIST)
                        .addToBackStack(CURRENCY_LIST)
                        .commit()
            }
            CURRENCY_HISTORY -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,
                                supportFragmentManager.findFragmentByTag(CURRENCY_HISTORY) ?:
                                        HistoryFragment.newInstance("first", "second"), CURRENCY_HISTORY)
                        .addToBackStack(CURRENCY_HISTORY)
                        .commit()
            }
            CURRENCY_FILTER -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,
                                supportFragmentManager.findFragmentByTag(CURRENCY_FILTER) ?:
                                        HistoryFilterFragment.newInstance("first", "second"), CURRENCY_FILTER)
                        .addToBackStack(CURRENCY_FILTER)
                        .commit()
            }
            CURRENCY_ANALYSIS -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,
                                supportFragmentManager.findFragmentByTag(CURRENCY_ANALYSIS) ?:
                                        AnalysisFragment.newInstance("first", "second"), CURRENCY_ANALYSIS)
                        .addToBackStack(CURRENCY_HISTORY)
                        .commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
