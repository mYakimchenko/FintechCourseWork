package com.mihanjk.fintechCurrencyExchange.businesslogic.interactor

import android.util.Log
import com.mihanjk.fintechCurrencyExchange.businesslogic.http.FixerService
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
    fun getApiCurrency(): Observable<List<CurrencyEntity>>
    fun getMergeFunction(): BiFunction<List<CurrencyEntity>, List<CurrencyEntity>, List<CurrencyEntity>>
    fun saveCurrencyEntityIntoDatabase(currencyList: List<CurrencyEntity>): Completable
    fun saveCurrencyCoursesIntoDatabase(currencyCourse: List<CurrencyCourse>): Completable
    fun sortData(data: List<CurrencyEntity>): List<CurrencyEntity>
}

class CurrencyListInteractorImpl @Inject constructor(
        private val mDatabase: CurrencyDatabase,
        private val mApiService: FixerService) : CurrencyListInteractor {

    override fun sortData(data: List<CurrencyEntity>): List<CurrencyEntity> =
            data.sortedWith(compareByDescending<CurrencyEntity> { it.isFavorite }
                    .thenBy { it.lastUsed }
                    .thenBy { it.name })

    private val mergeDatabaseAndApiCurrencyEntity =
            // TODO working with position of new currencies
            BiFunction<List<CurrencyEntity>, List<CurrencyEntity>, List<CurrencyEntity>> { database, api ->
                (database + api.filter {
                    val base = it.name
                    !database.any { it.name == base }
                })
            }

    override fun getMergeFunction() = mergeDatabaseAndApiCurrencyEntity

    /**
     * get data from database then make api call, save info about currency courses and
     * merge with database data (do i need to update db in the end?)
     */
    override fun getCurrencyList(): Observable<List<CurrencyEntity>> {
        return Observable.concat(listOf(getDatabaseCurrency(), getApiCurrency()))
//        return Observable.zip(getDatabaseCurrency(), getApiCurrency(), mergeDatabaseAndApiCurrencyEntity)
                .doOnError { Log.d("Rx", "Error: $it") }
                .doOnNext {
                    Log.d("Rx", "Zip thread: ${Thread.currentThread().name}")
                    saveCurrencyEntityIntoDatabase(it).subscribe()
                }
    }

    override fun getDatabaseCurrency(): Observable<List<CurrencyEntity>> =
            mDatabase.currencyDao().getAllRecords().toObservable()
                    .doOnNext { Log.d("Rx", "Database thread: ${Thread.currentThread().name}") }

    override fun getApiCurrency(): Observable<List<CurrencyEntity>> =
            // todo decide why get MainThreadException when not subscribeOn here?
            mApiService.getLatestReference().map { it.toCurrencyEntityList() }.subscribeOn(Schedulers.io())
                    .doOnNext { Log.d("Rx", "Api thread: ${Thread.currentThread().name}") }

    override fun saveCurrencyEntityIntoDatabase(currencyList: List<CurrencyEntity>): Completable =
            Completable.fromAction { mDatabase.currencyDao().update(currencyList) }
                    .subscribeOn(Schedulers.io())
                    .doOnError { Log.d("Rx", "Error when saving into database: ${it.message}") }
                    .doOnComplete { Log.d("Rx", "Complete saving into database") }

    override fun saveCurrencyCoursesIntoDatabase(currencyCourse: List<CurrencyCourse>): Completable =
            Completable.fromAction { mDatabase.courseDao().insert(currencyCourse) }
}
