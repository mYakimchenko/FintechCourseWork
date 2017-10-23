package com.mihanjk.fintechCurrencyExchange.view.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.mihanjk.fintechCurrencyExchange.CurrencyApplication
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.model.api.FixerService
import com.mihanjk.fintechCurrencyExchange.model.api.getDateReference
import com.mihanjk.fintechCurrencyExchange.model.data.Currency
import com.mihanjk.fintechCurrencyExchange.model.data.ForeignItem
import com.mihanjk.fintechCurrencyExchange.model.data.PathDate
import com.mihanjk.fintechCurrencyExchange.view.currencyList.CurrencyListFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : CurrencyListFragment.OnListFragmentInteractionListener, AppCompatActivity() {
    private val disposables = CompositeDisposable()

    @Inject
    lateinit var fixerService: FixerService

    override fun onStarClicked(item: ForeignItem) {
        Toast.makeText(this, "Was clicked star on item ${item.base}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as CurrencyApplication).component.inject(this)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, CurrencyListFragment.newInstance()).commit()
        val value = fixerService.getDateReference(PathDate(GregorianCalendar(2017, 5, 23)),
                Currency.RUB, Currency.USD, Currency.BRL)
        disposables.add(
                value.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .delay(5, TimeUnit.SECONDS)
                        .subscribe({ Log.d("TEST", it.toString()) },
                                { Log.d("TEST", "FUCK ${it.message}") }))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
