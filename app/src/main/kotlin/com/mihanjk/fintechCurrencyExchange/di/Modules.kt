package com.mihanjk.fintechCurrencyExchange.di

import android.arch.persistence.room.Room
import android.content.Context
import com.mihanjk.fintechCurrencyExchange.CurrencyApplication
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.model.CurrencyDatabase
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

    @Provides
    @Singleton
    fun provideApplicationContext() = currencyApplication.applicationContext
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

@Module
class StorageModule(val context: Context) {
    @Provides
    @Singleton
    fun provideSharedPreferences() = context.getSharedPreferences(
            context.getString(R.string.shared_preferences_key),
            Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideDatabase() = Room.databaseBuilder(context, CurrencyDatabase::class.java,
            "currency-database").build()
}
