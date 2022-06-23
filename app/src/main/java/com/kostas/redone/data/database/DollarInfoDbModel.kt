package com.kostas.redone.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dollar_price",
    indices = [
        Index("date", unique = true)
    ]
)
data class DollarInfoDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val currency: String,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val date: String,
    val value: String,
    val nominal: Int
)