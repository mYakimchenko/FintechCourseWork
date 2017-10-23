package com.mihanjk.fintechCurrencyExchange.di

import com.mihanjk.fintechCurrencyExchange.view.main.MainActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class, NetModule::class))
interface AppComponent {
    fun inject(activity: MainActivity)
}