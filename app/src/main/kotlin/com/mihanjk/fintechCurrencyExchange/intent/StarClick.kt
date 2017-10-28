package com.mihanjk.fintechCurrencyExchange.intent

import com.mihanjk.fintechCurrencyExchange.model.data.Foreign

class StarClickEvent(val base: Foreign, val isFavorite: Boolean)

class StarClickModel() {
    private var inProgress: Boolean = false
    private var success: Boolean = false
    private lateinit var errorMessage: String

    private constructor(inProgress: Boolean, success: Boolean, errorMessage: String) : this() {
        this.inProgress = inProgress
        this.success = success
        this.errorMessage = errorMessage
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
