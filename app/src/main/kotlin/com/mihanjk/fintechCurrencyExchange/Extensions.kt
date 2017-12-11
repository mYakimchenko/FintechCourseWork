package com.mihanjk.fintechCurrencyExchange

import android.util.Log
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyEntity
import io.reactivex.Observable

fun <T> Observable<T>.log(tag: String = "Rx") =
        this.apply {
            doOnNext { Log.d(tag, "OnNext: $this") }
            doOnError { Log.d(tag, "OnError: $this") }
            doOnComplete { Log.d(tag, "OnComplete: $this") }
            doOnSubscribe { Log.d(tag, "OnSubscribe: $this") }
            doOnDispose { Log.d(tag, "OnDispose: $this") }
        }

fun List<CurrencyEntity>.sortByFavoriteLastUsedAndName() {

}
