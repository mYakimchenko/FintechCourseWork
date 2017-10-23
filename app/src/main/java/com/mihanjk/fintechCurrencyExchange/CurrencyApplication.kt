package com.mihanjk.fintechCurrencyExchange

import android.app.Application
import com.mihanjk.fintechCurrencyExchange.di.AppComponent
import com.mihanjk.fintechCurrencyExchange.di.DaggerAppComponent
import com.mihanjk.fintechCurrencyExchange.di.NetModule


class CurrencyApplication : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
                .netModule(NetModule("http://api.fixer.io/"))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}