package com.mihanjk.fintechCurrencyExchange.view.currencyExchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.mihanjk.fintechCurrencyExchange.CurrencyApplication
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyTransaction
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_currency_exchange.*
import kotlinx.android.synthetic.main.fragment_currency_exchange.view.*
import java.util.*
import java.util.concurrent.TimeUnit


class CurrencyExchangeFragment : CurrencyExchangeView,
        MviFragment<CurrencyExchangeView, CurrencyExchangePresenter>() {

    private lateinit var mFirstCurrency: String
    private lateinit var mSecondCurrency: String

    override fun createPresenter(): CurrencyExchangePresenter =
            (activity.application as CurrencyApplication).component.getCurrencyExchangePresenter()

    override fun initializeViewState(first: String, second: String): Observable<Pair<String, String>> =
            Observable.just(Pair(first, second))

    override fun modifyAmountFirstCurrencyIntent(): Observable<Double> =
            RxTextView.textChanges(firstCurrencyEditText)
                    .skipInitialValue()
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .map { it.toString().toDouble() }

    override fun modifyAmountSecondCurrencyIntent(): Observable<Double> =
            RxTextView.textChanges(secondCurrencyEditText)
                    .skipInitialValue()
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .map { it.toString().toDouble() }

    override fun getCurrencyCourseIntent(): Observable<Pair<String, String>> =
            // TODO
            Observable.just(Pair(mFirstCurrency, mSecondCurrency))

    override fun makeCurrencyExchangeIntent(): Observable<CurrencyTransaction> =
            RxView.clicks(exchangeButton).map {
                CurrencyTransaction(null, mFirstCurrency,
                        firstCurrencyEditText.text.toString().toDouble(),
                        mSecondCurrency, secondCurrencyEditText.text.toString().toDouble(), Date())
            }

    override fun render(state: CurrencyExchangeViewState) {
        // TODO
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mFirstCurrency = (arguments.getString(FIRST_CURRENCY))
            mSecondCurrency = (arguments.getString(SECOND_CURRENCY))
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_currency_exchange, container, false)
        view.firstCurrency.text = mFirstCurrency
        view.secondCurrency.text = mSecondCurrency
        return view
    }

    companion object {
        private val FIRST_CURRENCY = "first"
        private val SECOND_CURRENCY = "second"

        fun newInstance(firstCurrency: String, secondCurrency: String):
                CurrencyExchangeFragment {
            val fragment = CurrencyExchangeFragment()
            val args = Bundle()
            args.putString(FIRST_CURRENCY, firstCurrency)
            args.putString(SECOND_CURRENCY, secondCurrency)
            fragment.arguments = args
            return fragment
        }
    }
}
