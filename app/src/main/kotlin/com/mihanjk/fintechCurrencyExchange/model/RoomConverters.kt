package com.mihanjk.fintechCurrencyExchange.model

import android.arch.persistence.room.TypeConverter
import com.mihanjk.fintechCurrencyExchange.model.data.Currency
import java.sql.Timestamp

class RoomConverters {
    @TypeConverter
    fun fromCurrency(value: Currency?) = value?.toString()

    @TypeConverter
    fun toCurrency(value: String?) = value?.let { Currency.valueOf(it) }

    @TypeConverter
    fun fromTimestamp(value: Timestamp?) = value?.time

    @TypeConverter
    fun toTimestamp(value: Long?) = value?.let { Timestamp(it) }
}