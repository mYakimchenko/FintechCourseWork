package com.mihanjk.fintechCurrencyExchange.model.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "course")
data class CurrencyCourse(@PrimaryKey(autoGenerate = true) val id: Long? = null,
                          val from: String,
                          val to: String,
                          val course: Double,
                          val date: Date)

//@Entity(tableName = "course")
//data class CurrencyCourseDatabase(@PrimaryKey(autoGenerate = true) val id: Long? = null,
//                                  val from_id: Long,
//                                  val to_id: Long,
//                                  val course: Double,
//                                  val date: Long)

