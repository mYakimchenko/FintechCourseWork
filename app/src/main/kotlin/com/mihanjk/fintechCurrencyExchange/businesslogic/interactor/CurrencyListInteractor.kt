package com.mihanjk.fintechCurrencyExchange.businesslogic.interactor

import android.util.Log
import com.mihanjk.fintechCurrencyExchange.businesslogic.http.FixerService
import com.mihanjk.fintechCurrencyExchange.model.data.ForeignApi
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyCourse
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyDatabase
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface CurrencyListInteractor {
    fun getCurrencyList(): Observable<List<CurrencyEntity>>
    fun getDatabaseCurrency(): Observable<List<CurrencyEntity>>
    fun getApiCurrency(): Observable<ForeignApi>
    fun saveCurrencyEntityIntoDatabase(currencyList: List<CurrencyEntity>): Completable
    fun saveCurrencyCoursesIntoDatabase(currencyCourse: List<CurrencyCourse>): Completable
}

class CurrencyListInteractorImpl @Inject constructor(
        val mDatabase: CurrencyDatabase,
        val mApiService: FixerService) : CurrencyListInteractor {

    private val mergeDatabaseAndApiCurrencyEntity =
            // TODO working with position of new currencies
            BiFunction<List<CurrencyEntity>, ForeignApi, List<CurrencyEntity>> { database, api ->
                (database + api.toCurrencyEntityList().filter {
                    val base = it.name
                    !database.any { it.name == base }
                }).apply {
                    forEachIndexed { index, entity -> entity.position = index }
                }
            }

    /**
     * get data from database then make api call, save info about currency courses and
     * merge with database data (do i need to update db in the end?)
     */
    override fun getCurrencyList(): Observable<List<CurrencyEntity>> {
        return Observable.zip(getDatabaseCurrency(), getApiCurrency(), mergeDatabaseAndApiCurrencyEntity)
                .doOnNext {
                    Log.d("Rx", "Zip thread: ${Thread.currentThread().name}")
                    saveCurrencyEntityIntoDatabase(it)
                }
    }

    override fun getDatabaseCurrency(): Observable<List<CurrencyEntity>> =
            mDatabase.currencyDao().getAllRecords().toObservable()
                    .doOnNext { Log.d("Rx", "Database thread: ${Thread.currentThread().name}") }

    override fun getApiCurrency(): Observable<ForeignApi> =
            // todo decide why get MainThreadException when not subscribeOn here?
            mApiService.getLatestReference().toObservable().subscribeOn(Schedulers.io())
                    .doOnNext { Log.d("Rx", "Api thread: ${Thread.currentThread().name}") }

    override fun saveCurrencyEntityIntoDatabase(currencyList: List<CurrencyEntity>): Completable =
            Completable.fromAction { mDatabase.currencyDao().insert(currencyList) }

    override fun saveCurrencyCoursesIntoDatabase(currencyCourse: List<CurrencyCourse>): Completable =
            Completable.fromAction { mDatabase.courseDao().insert(currencyCourse) }
}
