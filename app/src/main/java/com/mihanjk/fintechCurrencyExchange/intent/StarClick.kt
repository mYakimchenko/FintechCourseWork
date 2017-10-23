package com.mihanjk.fintechCurrencyExchange.intent

import com.mihanjk.fintechCurrencyExchange.model.data.Foreign

class StarClickEvent(val base: Foreign, val isFavorite: Boolean)

class StarClickModel() {
    private lateinit var inProgress: Boolean
    private lateinit var success: Boolean
    private lateinit var errorMessage: String

    private constructor(inProgress: Boolean, success: Boolean, errorMessage: String) {

    }

    companion object {
        fun inProgress() {

        }
        fun failure() {

        }
        fun success() {

        }
    }
}
