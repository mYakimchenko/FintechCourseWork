package com.mihanjk.fintechCurrencyExchange.di

import com.mihanjk.fintechCurrencyExchange.view.MainActivity
import com.mihanjk.fintechCurrencyExchange.view.currencyList.CurrencyListFragment
import com.mihanjk.fintechCurrencyExchange.view.currencyList.CurrencyListPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetModule::class, StorageModule::class, PresenterModule::class))
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(currencyListFragment: CurrencyListFragment)
    fun getCurrencyListPresenter(): CurrencyListPresenter
}