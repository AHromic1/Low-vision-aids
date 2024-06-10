package com.example.lowvisionaidsbachelorthesis.database_dao

import android.content.Context
import com.example.lowvisionaidsbachelorthesis.database_dao.ScannedMoney
import com.example.lowvisionaidsbachelorthesis.database_dao.ScannedMoneyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScannedMoneyRepository() {
    companion object {
        //val totalValue: List<ScannedMoney> = scannedMoneyDao.getTotalValue()

        /* suspend fun writeToDB(newValue: ScannedMoney) {
        scannedMoneyDao.insert(newValue)
    }*/

        suspend fun writeToDB(context: Context, value: ScannedMoney): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var db = ScannedMoneyDatabase.getInstance(context)
                    db!!.scannedMoneyDao().insert(value)
                    return@withContext "success"
                } catch (error: Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }
    }
}


