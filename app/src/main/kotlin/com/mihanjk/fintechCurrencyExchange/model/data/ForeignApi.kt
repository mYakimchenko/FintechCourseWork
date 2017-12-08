package com.mihanjk.fintechCurrencyExchange.model.data

import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyCourse
import com.mihanjk.fintechCurrencyExchange.model.database.CurrencyEntity
import java.text.SimpleDateFormat
import java.util.*

data class ForeignApi(val base: String = "",
                      val date: String = "",
                      val rates: Map<String, Double> = emptyMap()
) {
    companion object {
        // todo make better date formatting
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        fun parseDate(date: String) = dateFormatter.parse(date)
    }

    fun toCurrencyEntityList(): List<CurrencyEntity> =
            ArrayList<CurrencyEntity>().apply {
                add(CurrencyEntity(name = this@ForeignApi.base))
                rates.forEach { add(CurrencyEntity(name = it.key)) }
            }

    fun toCurrencyCoursesList(): List<CurrencyCourse> =
            ArrayList<CurrencyCourse>().apply {
                rates.forEach {
                    add(CurrencyCourse(from = base, to = it.key, course = it.value, date = parseDate(date)))
                    add(CurrencyCourse(from = it.key, to = base, course = 1 / it.value, date = parseDate(date)))
                }
            }
}
