package com.mihanjk.fintechCurrencyExchange.model

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import com.mihanjk.fintechCurrencyExchange.model.data.Currency
import io.reactivex.Flowable

@Entity(tableName = "currency")
data class CurrencyEntity(@PrimaryKey(autoGenerate = true) val id: Long?,
                          val name: Currency,
                          var isFavorite: Boolean,
                          var position: Int) : Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CurrencyEntity> = object : Parcelable.Creator<CurrencyEntity> {
            override fun createFromParcel(source: Parcel): CurrencyEntity = CurrencyEntity(source)
            override fun newArray(size: Int): Array<CurrencyEntity?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readValue(Long::class.java.classLoader) as Long?,
            Currency.values()[source.readInt()],
            1 == source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeInt(name.ordinal)
        writeInt((if (isFavorite) 1 else 0))
        writeInt(position)
    }
}

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg currency: CurrencyEntity)

    @Update
    fun update(vararg currencyEntity: CurrencyEntity)

    @Query("SELECT * FROM currency")
    fun getAllRecords(): Flowable<List<CurrencyEntity>>
}

@Entity(tableName = "transactions")
data class Transaction(@PrimaryKey(autoGenerate = true) val id: Long,
                       val from_id: Long,
                       val from_value: Double,
                       val to_id: Long,
                       val to_value: Double,
                       val date: Long)

@Dao
interface TransactionDao {
    @Insert
    fun insert(vararg transaction: Transaction)

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

@Entity
data class Course(@PrimaryKey(autoGenerate = true) val id: Long,
                  val from_id: Long,
                  val to_id: Long,
                  val course: Double,
                  val date: Long)

@Dao
interface CourseDao {
    @Insert
    fun insert(vararg course: Course)

    @Delete
    fun delete(vararg course: Course)
}

@Database(entities = arrayOf(Transaction::class, CurrencyEntity::class, Course::class), version = 1)
@TypeConverters(RoomConverters::class)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun courseDao(): CourseDao
}


