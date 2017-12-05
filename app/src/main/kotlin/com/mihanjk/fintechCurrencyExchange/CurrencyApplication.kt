package com.mihanjk.fintechCurrencyExchange

import android.app.Application
import com.facebook.stetho.Stetho
import com.mihanjk.fintechCurrencyExchange.di.AppComponent
import com.mihanjk.fintechCurrencyExchange.di.DaggerAppComponent
import com.mihanjk.fintechCurrencyExchange.di.NetModule
import com.mihanjk.fintechCurrencyExchange.di.StorageModule


class CurrencyApplication : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
                .netModule(NetModule("http://api.fixer.io/"))
                .storageModule(StorageModule(applicationContext))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}