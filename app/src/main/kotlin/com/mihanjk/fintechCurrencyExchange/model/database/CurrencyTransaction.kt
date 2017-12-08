package com.mihanjk.fintechCurrencyExchange.model.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "transactions")
data class CurrencyTransaction(@PrimaryKey(autoGenerate = true)
                               val id: Long? = null,
                               val from: String,
                               val fromValue: Double,
                               val to: String,
                               val toValue: Double,
                               val date: Date)

//@Entity(tableName = "transactions")
//data class CurrencyTransactionDatabase(@PrimaryKey(autoGenerate = true) val id: Long,
//                                       val from_id: Long,
//                                       val from_value: Double,
//                                       val to_id: Long,
//                                       val to_value: Double,
//                                       val date: Long)
