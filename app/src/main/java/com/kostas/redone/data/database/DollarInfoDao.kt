package com.kostas.redone.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DollarInfoDao {
    @Query("SELECT * FROM dollar_price ORDER BY date DESC")
    fun getDollarPriceList(): List<DollarInfoDbModel>?

    @Query("SELECT * FROM dollar_price WHERE  date == :date LIMIT 1")
    fun getPriceInfoAboutDollar(date: String): DollarInfoDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDollarPriceList(dollarList: List<DollarInfoDbModel>)

    @Query("DELETE FROM dollar_price")
    fun deleteDollarPriceList()
}