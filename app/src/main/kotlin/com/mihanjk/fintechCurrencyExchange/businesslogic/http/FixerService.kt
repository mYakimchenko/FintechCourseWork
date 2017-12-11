package com.mihanjk.fintechCurrencyExchange.businesslogic.http

import com.mihanjk.fintechCurrencyExchange.model.data.ForeignApi
import com.mihanjk.fintechCurrencyExchange.model.data.PathDate
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FixerService {
    @GET("latest")
    fun getLatestReference(@Query("base") base: String? = "USD",
                           @Query("symbols") symbols: String? = null): Observable<ForeignApi>

    @GET("{date}")
    fun getDateReference(@Path("date") date: PathDate,
                         @Query("base") base: String,
                         @Query("symbols") symbols: String? = null): Observable<ForeignApi>
}

fun FixerService.getLatestReference(base: String, vararg symbols: String) =
        getLatestReference(base, symbols.joinToString())

fun FixerService.getDateReference(date: PathDate, base: String, vararg symbols: String) =
        getDateReference(date, base, symbols.joinToString())
