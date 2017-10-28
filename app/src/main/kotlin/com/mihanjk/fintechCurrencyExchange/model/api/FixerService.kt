package com.mihanjk.fintechCurrencyExchange.model.api

import com.mihanjk.fintechCurrencyExchange.model.data.Currency
import com.mihanjk.fintechCurrencyExchange.model.data.Foreign
import com.mihanjk.fintechCurrencyExchange.model.data.PathDate
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FixerService {
    @GET("latest")
    fun getLatestReference(@Query("base") base: Currency,
                           @Query("symbols") symbols: String): Observable<Foreign>

    @GET("{date}")
    fun getDateReference(@Path("date") date: PathDate,
                         @Query("base") base: Currency,
                         @Query("symbols") symbols: String): Observable<Foreign>

}

fun FixerService.getLatestReference(base: Currency, vararg symbols: Currency) =
        getLatestReference(base, symbols.joinToString())

fun FixerService.getDateReference(date: PathDate, base: Currency, vararg symbols: Currency) =
        getDateReference(date, base, symbols.joinToString())
