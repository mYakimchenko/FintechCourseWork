package com.mihanjk.fintechCurrencyExchange.view.mainActivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView
import com.mihanjk.fintechCurrencyExchange.CurrencyApplication
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.view.analysis.AnalysisFragment
import com.mihanjk.fintechCurrencyExchange.view.currencyExchange.CurrencyExchangeFragment
import com.mihanjk.fintechCurrencyExchange.view.transactionHistoryFilter.HistoryFilterFragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : MainView, MviActivity<MainView, MainPresenter>() {
    // TODO do i need manual disposable streams???
    private val disposables = CompositeDisposable()

    private val saveCurrencySubject = PublishSubject.create<MainPartialState.saveCurrencyExchangeFragment>()
    private val removeCurrencyExchangeSubject = PublishSubject.create<MainPartialState.removeCurrencyExchangeFragment>()
    private val openLastExchangeSubject = PublishSubject.create<MainPartialState.openLastExchange>()
    private val openAnalysisSubject = PublishSubject.create<MainPartialState.openAnalysisFragment>()
    private val openLastHistorySubject = PublishSubject.create<MainPartialState.openLastHistoryFragment>()
    private val removeHistoryFilterSubject = PublishSubject.create<MainPartialState.removeHistoryFilterFragment>()

    override fun createPresenter(): MainPresenter =
            (application as CurrencyApplication).component.getMainPresenter()

    override fun saveCurrencyExchangeIntent(): Observable<MainPartialState.saveCurrencyExchangeFragment> =
            saveCurrencySubject

    override fun removeCurrencyExchangeIntent(): Observable<MainPartialState.removeCurrencyExchangeFragment> =
            removeCurrencyExchangeSubject

    override fun openLastExchangeFragmentIntent(): Observable<MainPartialState.openLastExchange> =
            openLastExchangeSubject

    override fun openAnalysisFragmentIntent(): Observable<MainPartialState.openAnalysisFragment> =
            openAnalysisSubject

    override fun saveFilterFragmentIntent(): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeHistoryFilterIntent(): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openLastHistoryFragment(): Observable<MainPartialState.openLastHistoryFragment> =
            openLastHistorySubject

    override fun render(state: MainViewState) {
        Log.d("Test", "state: $state")
        when {
            state.exchange -> openFragment(state.exchangeFragments.last)
            state.history -> openFragment(state.historyFragments.last)
        // todo how can i do null safe call of this method in this case?
            state.analysis -> openFragment(state.analysisFragment ?: AnalysisFragment())
            else -> throw IllegalStateException("Can't process this state: $state")
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation.selectedItemId = R.id.action_exchange

        RxBottomNavigationView.itemSelections(bottomNavigation).subscribe {
            when (it.itemId) {
                R.id.action_exchange -> openLastExchangeSubject.onNext(MainPartialState.openLastExchange)
                R.id.action_history -> openLastHistorySubject.onNext(MainPartialState.openLastHistoryFragment)
                R.id.action_analysis -> openAnalysisSubject.onNext(MainPartialState.openAnalysisFragment)
            }
        }
    }

    fun openCurrencyExchangeScreen(first: String, second: String) =
            saveCurrencySubject.onNext(MainPartialState.saveCurrencyExchangeFragment(first, second))

    override fun onBackPressed() {
        // TODO why Fragment already added: CurrencyListFragment when call super.OnBackPressed()?
        when (supportFragmentManager.findFragmentById(R.id.fragmentContainer)) {
            is CurrencyExchangeFragment -> removeCurrencyExchangeSubject.onNext(MainPartialState.removeCurrencyExchangeFragment)
            is HistoryFilterFragment -> removeHistoryFilterSubject.onNext(MainPartialState.removeHistoryFilterFragment)
            else -> finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
