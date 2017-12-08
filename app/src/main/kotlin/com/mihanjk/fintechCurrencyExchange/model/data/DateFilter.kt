package com.mihanjk.fintechCurrencyExchange.model.data

import java.text.SimpleDateFormat
import java.util.*

sealed class DateFilter {
    object Week : DateFilter()
    object TwoWeek : DateFilter()
    object AllTime : DateFilter()
    data class FromTo(private val from: String, private val to: String) : DateFilter() {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val fromDate by lazy { dateFormat.parse(from) }
        val toDate by lazy { dateFormat.parse(to) }
    }
}