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
import com.mihanjk.fintechCurrencyExchange.view.transactionHistory.HistoryFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : CurrencyExchangeFragment.OnFragmentInteractionListener, AppCompatActivity() {
    override fun onFragmentInteraction(uri: Uri) {
        Log.d(javaClass.name, "some")
    }

    private val disposables = CompositeDisposable()

    companion object {
        const val FIRST_SCREEN = "First"
        const val SECOND_SCREEN = "Second"
        const val THIRD_SCREEN = "Third"
        const val FOURTH_SCREEN = "Fourth"
        const val FIFTH_SCREEN = "Fifth"
    }

    @Inject
    lateinit var fixerService: FixerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as CurrencyApplication).component.inject(this)

        bottomNavigation.selectedItemId = R.id.action_exchange

        RxBottomNavigationView.itemSelections(bottomNavigation).subscribe {
            when (it.itemId) {
                R.id.action_exchange -> openScreen(FIRST_SCREEN)
                R.id.action_history -> openScreen(THIRD_SCREEN)
                R.id.action_analysis -> openScreen(FIFTH_SCREEN)
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
            FIRST_SCREEN -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,
                                supportFragmentManager.findFragmentByTag(FIRST_SCREEN) ?:
                                        CurrencyListFragment(), FIRST_SCREEN)
                        .addToBackStack(FIRST_SCREEN)
                        .commit()
            }
            THIRD_SCREEN -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,
                                supportFragmentManager.findFragmentByTag(THIRD_SCREEN) ?:
                                        HistoryFragment.newInstance("first", "second"), THIRD_SCREEN)
                        .addToBackStack(THIRD_SCREEN)
                        .commit()
            }
            FIFTH_SCREEN -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer,
                                supportFragmentManager.findFragmentByTag(FIFTH_SCREEN) ?:
                                        AnalysisFragment.newInstance("first", "second"), FIFTH_SCREEN)
                        .addToBackStack(THIRD_SCREEN)
                        .commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
