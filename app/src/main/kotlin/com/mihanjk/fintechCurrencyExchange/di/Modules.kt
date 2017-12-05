package com.mihanjk.fintechCurrencyExchange.di

import android.arch.persistence.room.Room
import android.content.Context
import com.mihanjk.fintechCurrencyExchange.CurrencyApplication
import com.mihanjk.fintechCurrencyExchange.R
import com.mihanjk.fintechCurrencyExchange.businesslogic.http.FixerService
import com.mihanjk.fintechCurrencyExchange.model.CurrencyDatabase
import com.mihanjk.fintechCurrencyExchange.view.currencyList.CurrencyListPresenter
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
    fun provideDatabase(): CurrencyDatabase = Room.databaseBuilder(context, CurrencyDatabase::class.java,
            "currency-database")
//            .addCallback(object : RoomDatabase.Callback() {
//                override fun onCreate(db: SupportSQLiteDatabase) {
//                    super.onCreate(db)
//                    // todo maybe exist more concise way to do this or change varargs to list???
//                    // todo first launch can't get data from db, cause initialization takes time
//                    Completable.fromAction {
//                    this@StorageModule.provideDatabase().currencyDao().insert(
//                            *(Currency.values().mapIndexed { index, currency ->
//                                CurrencyEntity(null, currency, false, index) }.toTypedArray()))}
//                            .subscribeOn(Schedulers.io())
//                            .subscribe()
//                }
//            })
            .build()
}

@Module(includes = arrayOf(StorageModule::class))
class PresenterModule {
    @Provides
    @Singleton
    fun provideCurrencyListPresenter(database: CurrencyDatabase) = CurrencyListPresenter(database)
}
