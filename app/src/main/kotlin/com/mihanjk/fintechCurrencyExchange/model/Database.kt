package com.mihanjk.fintechCurrencyExchange.model

import android.arch.persistence.room.*
import com.mihanjk.fintechCurrencyExchange.model.data.Currency
import io.reactivex.Flowable
import java.sql.Timestamp

@Entity
data class CurrencyEntity(@PrimaryKey val base: Currency,
                          val rate: Currency,
                          val course: Double,
                          val date: Timestamp)

@Dao
interface CurrencyDao {
    @Insert
    fun insert(currency: CurrencyEntity)
}

@Entity(tableName = "transactions")
data class Transaction(@PrimaryKey(autoGenerate = true) val uid: Long,
                       val from: Currency,
                       val from_value: Double,
                       val to: Currency,
                       val to_value: Double,
                       val date: Long)

@Dao
interface TransactionDao {
    @Insert
    fun insert(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate")
    fun getRecordsBetweenDates(startDate: Long, endDate: Long): Flowable<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE date >= :date")
    fun getRecordsFromDate(date: Long): Flowable<Transaction>

    @Query("SELECT * FROM transactions WHERE date >= datetime('now', '-1 month')")
    fun getRecordsForMonth(): Flowable<Transaction>

    @Query("SELECT * FROM transactions WHERE date >= datetime('now', '-7 days')")
    fun getRecordsForWeek(): Flowable<Transaction>

    @Query("SELECT * FROM transactions")
    fun getAllRecords(): Flowable<List<Transaction>>
}

@Database(entities = arrayOf(Transaction::class, CurrencyEntity::class), version = 1)
@TypeConverters(RoomConverters::class)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun currencyDao(): CurrencyDao
}


