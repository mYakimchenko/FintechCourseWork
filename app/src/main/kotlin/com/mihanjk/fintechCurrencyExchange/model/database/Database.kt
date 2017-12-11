package com.mihanjk.fintechCurrencyExchange.model.database

import android.arch.persistence.room.*
import io.reactivex.Flowable
import java.util.*

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg currency: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyList: List<CurrencyEntity>)

    @Update
    fun update(vararg currencyEntity: CurrencyEntity)

    @Update
    fun update(currencyList: List<CurrencyEntity>)

    @Query("SELECT * FROM currency")
    fun getAllRecords(): Flowable<List<CurrencyEntity>>
}

@Dao
interface TransactionDao {
    // TODO check if this work with object else write extension function
    @Insert
    fun insert(vararg transaction: CurrencyTransaction)

    @Query("SELECT * FROM transactions " +
            "WHERE date BETWEEN :startDate AND :endDate")
    fun getRecordsBetweenDates(startDate: Date, endDate: Date): Flowable<List<CurrencyTransaction>>

    @Query("SELECT * FROM transactions WHERE date >= :date")
    fun getRecordsFromDate(date: Long): Flowable<List<CurrencyTransaction>>

    @Query("SELECT * FROM transactions WHERE date >= datetime('now', '-1 month')")
    fun getRecordsForMonth(): Flowable<List<CurrencyTransaction>>

    @Query("SELECT * FROM transactions WHERE date >= datetime('now', '-7 days')")
    fun getRecordsForWeek(): Flowable<List<CurrencyTransaction>>

    @Query("SELECT * FROM transactions WHERE date >= datetime('now', '-14 days')")
    fun getRecordsForTwoWeek(): Flowable<List<CurrencyTransaction>>

    @Query("SELECT * FROM transactions")
    fun getAllRecords(): Flowable<List<CurrencyTransaction>>
}

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg course: CurrencyCourse)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(courseList: List<CurrencyCourse>)

    @Delete
    fun delete(vararg course: CurrencyCourse)
}

@Database(entities = [CurrencyTransaction::class, CurrencyEntity::class,
    CurrencyCourse::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun courseDao(): CourseDao
}

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long) = Date(value)

    @TypeConverter
    fun toTimestamp(date: Date) = date.time
}
