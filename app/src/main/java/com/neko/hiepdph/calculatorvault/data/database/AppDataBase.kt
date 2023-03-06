package com.neko.hiepdph.calculatorvault.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.neko.hiepdph.calculatorvault.data.converter.Converter
import com.neko.hiepdph.calculatorvault.data.database.dao.HistoryDao
import com.neko.hiepdph.calculatorvault.data.database.entity.HistoryEntity


@Database(entities = [HistoryEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val historyDao: HistoryDao
}
