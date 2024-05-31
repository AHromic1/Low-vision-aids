
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lowvisionaidsbachelorthesis.database.ScannedMoney
import kotlinx.coroutines.flow.Flow


@Dao
interface ScannedMoneyDao {

    @Query("SELECT * FROM scanned_money_table")
    fun getTotalValue(): List<ScannedMoney>

    @Insert
    suspend fun insert(newScan: ScannedMoney)

    @Update
    suspend fun update(newScan: ScannedMoney)
}
