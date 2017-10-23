package com.mihanjk.fintechCurrencyExchange.di

import com.mihanjk.fintechCurrencyExchange.CurrencyApplication
import com.mihanjk.fintechCurrencyExchange.model.api.FixerService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule(val currencyApplication: CurrencyApplication) {
    @Provides
    @Singleton
    fun provideApplication() = currencyApplication
}

@Module
class NetModule(val baseUrl: String) {
    @Provides
    @Singleton
    fun provideFixerService() = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(FixerService::class.java)
}
