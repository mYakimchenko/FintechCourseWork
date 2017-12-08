package com.mihanjk.fintechCurrencyExchange.model.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "currency")
data class CurrencyEntity(@PrimaryKey(autoGenerate = true)
                          val id: Long? = null,
                          val name: String,
                          var isFavorite: Boolean = false,
        // TODO which position i need to save first time???
                          var position: Int? = null)
