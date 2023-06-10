package danielabez.spellitforme.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import danielabez.spellitforme.model.Accessory

@Dao
interface AccessoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAccessory(accessory: Accessory)

    @Query("SELECT * FROM accessory WHERE registeredUserOwnerId IS NULL")
    fun getAllGeneralAccessories() : List<Accessory>
}