package com.mihanjk.fintechCurrencyExchange.model.data

import java.text.SimpleDateFormat
import java.util.*


class PathDate(val date: Calendar) {
    companion object {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    }

    override fun toString(): String {
        return dateFormat.format(date.time)
    }
}