package com.mihanjk.fintechCurrencyExchange.di

import android.arch.persistence.room.Room
import android.content.Context
import com.mihanjk.fintechCurrencyExchange.CurrencyApplication
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.businesslogic.http.FixerService
import com.mihanjk.fintechCurrencyExchange.businesslogic.interactor.*
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyDatabase
import com.mihanjk.fintechCurrencyExchange.view.currencyExchange.CurrencyExchangePresenter
import com.mihanjk.fintechCurrencyExchange.view.currencyList.CurrencyListPresenter
import com.mihanjk.fintechCurrencyExchange.view.mainActivity.MainPresenter
import com.mihanjk.fintechCurrencyExchange.view.transactionHistory.HistoryPresenter
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
    fun provideDatabase(): CurrencyDatabase = Room.databaseBuilder(context,
            CurrencyDatabase::class.java, "currency-database").build()
}

@Module(includes = [StorageModule::class, NetModule::class])
class InteractorModule {
    @Provides
    @Singleton
    fun provideCurrencyListInteractor(database: CurrencyDatabase, apiService: FixerService): CurrencyListInteractor =
            CurrencyListInteractorImpl(database, apiService)

    @Provides
    @Singleton
    fun provideCurrencyExchangeInteractor(database: CurrencyDatabase, apiService: FixerService): CurrencyExchangeInteractor =
            CurrencyExchangeInteractorImpl(database, apiService)

    @Provides
    @Singleton
    fun provideHistoryInteractor(database: CurrencyDatabase): HistoryInteractor =
            HistoryInteractorImpl(database)
}

@Module(includes = [InteractorModule::class])
class PresenterModule {
    @Provides
    @Singleton
    fun provideCurrencyListPresenter(interactor: CurrencyListInteractor) =
            CurrencyListPresenter(interactor)

    @Provides
    @Singleton
    fun provideCurrencyExchangePresenter(interactor: CurrencyExchangeInteractor) =
            CurrencyExchangePresenter(interactor)

    @Provides
    @Singleton
    fun provideHistoryPresenter(interactor: HistoryInteractor) =
            HistoryPresenter(interactor)

    @Provides
    @Singleton
    fun provideMainPresenter() = MainPresenter()
}

