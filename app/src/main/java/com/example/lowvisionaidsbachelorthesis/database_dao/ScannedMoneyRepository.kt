package com.example.lowvisionaidsbachelorthesis.database_dao

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScannedMoneyRepository() {
    companion object {
        suspend fun writeToDB(context: Context, value: ScannedMoney): String? {
            return withContext(Dispatchers.IO) {
                try {
                    val db = ScannedMoneyDatabase.getInstance(context)
                    db.scannedMoneyDao().insert(value)
                    return@withContext "success"
                } catch (error: Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }

        suspend fun fetchFromDB(context: Context): List<ScannedMoney>? {
            return withContext(Dispatchers.IO) {
                try {
                    val db = ScannedMoneyDatabase.getInstance(context)
                    return@withContext db.scannedMoneyDao().getTotalValue()
                } catch (error: Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }

        suspend fun updateDB(context: Context, value: ScannedMoney): String? {
            return withContext(Dispatchers.IO) {
                try {
                    val db = ScannedMoneyDatabase.getInstance(context)
                    db.scannedMoneyDao().update(value)
                    return@withContext "success"
                } catch (error: Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }

        suspend fun deleteByIdFromDB(context: Context, id: Long): String? {
            return withContext(Dispatchers.IO) {
                try {
                    val db = ScannedMoneyDatabase.getInstance(context)
                    db.scannedMoneyDao().deleteById(id)
                    return@withContext "success"
                } catch (error: Exception) {
                    error.printStackTrace()
                    return@withContext null
                }
            }
        }
    }
}


