package com.mihanjk.fintechCurrencyExchange.businesslogic.http

import com.mihanjk.fintechCurrencyExchange.model.data.Currency
import com.mihanjk.fintechCurrencyExchange.model.data.ForeignApi
import com.mihanjk.fintechCurrencyExchange.model.data.PathDate
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FixerService {
    @GET("latest")
    fun getLatestReference(@Query("base") base: Currency,
                           @Query("symbols") symbols: String): Observable<ForeignApi>

    @GET("{date}")
    fun getDateReference(@Path("date") date: PathDate,
                         @Query("base") base: Currency,
                         @Query("symbols") symbols: String): Observable<ForeignApi>
}

fun FixerService.getLatestReference(base: Currency, vararg symbols: Currency) =
        getLatestReference(base, symbols.joinToString())

fun FixerService.getDateReference(date: PathDate, base: Currency, vararg symbols: Currency) =
        getDateReference(date, base, symbols.joinToString())
