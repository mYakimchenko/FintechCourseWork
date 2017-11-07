package com.mihanjk.fintechCurrencyExchange.model.data

data class ForeignApi(val base: Currency,
                      val date: String,
                      val rates: Map<Currency, Double>
)

data class ForeignItem(val base: Currency,
                       var isFavorite: Boolean)
