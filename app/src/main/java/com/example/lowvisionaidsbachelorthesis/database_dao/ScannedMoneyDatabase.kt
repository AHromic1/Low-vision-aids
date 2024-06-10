package com.example.lowvisionaidsbachelorthesis.database_dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ScannedMoney::class], version = 8)
abstract class ScannedMoneyDatabase : RoomDatabase() {

    abstract fun scannedMoneyDao(): ScannedMoneyDao

    companion object {
        private var INSTANCE: ScannedMoneyDatabase? = null
        fun getInstance(context: Context): ScannedMoneyDatabase {
            if (INSTANCE == null) {
                synchronized(ScannedMoneyDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ScannedMoneyDatabase::class.java,
                "scanned_money_table"
            ).fallbackToDestructiveMigration().build()
    }

}
