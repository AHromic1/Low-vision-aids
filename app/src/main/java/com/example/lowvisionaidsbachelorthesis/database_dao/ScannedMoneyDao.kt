package com.example.lowvisionaidsbachelorthesis.database_dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ScannedMoneyDao {

    @Query("SELECT * FROM scanned_money_table")
    fun getTotalValue(): List<ScannedMoney>

    @Insert
    suspend fun insert(newScan: ScannedMoney)

    @Update
    suspend fun update(newScan: ScannedMoney)

    @Query("DELETE FROM scanned_money_table WHERE id = :id")
    suspend fun deleteById(id: Long)
}
