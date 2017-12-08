package com.mihanjk.fintechCurrencyExchange.di

import com.mihanjk.fintechCurrencyExchange.view.currencyList.CurrencyListFragment
import com.mihanjk.fintechCurrencyExchange.view.currencyList.CurrencyListPresenter
import com.mihanjk.fintechCurrencyExchange.view.mainActivity.MainActivity
import com.mihanjk.fintechCurrencyExchange.view.mainActivity.MainPresenter
import com.mihanjk.fintechCurrencyExchange.view.transactionHistory.HistoryPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetModule::class, StorageModule::class, PresenterModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(currencyListFragment: CurrencyListFragment)
    fun getCurrencyListPresenter(): CurrencyListPresenter
    fun getMainPresenter(): MainPresenter
    fun getHistoryPresenter(): HistoryPresenter
}