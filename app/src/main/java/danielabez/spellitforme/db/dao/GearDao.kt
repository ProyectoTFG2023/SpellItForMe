package danielabez.spellitforme.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import danielabez.spellitforme.model.Gear

@Dao
interface GearDao {
    @Insert
    fun addGear(gear: Gear)

    @Query("SELECT * FROM gear WHERE type = 'Headgear'")
    fun getAllHeadgear() : List<Gear>

    @Query("SELECT * FROM gear WHERE type = 'Torso'")
    fun getAllTorso() : List<Gear>

    @Query("SELECT * FROM gear WHERE type = 'Handwear'")
    fun getAllHandwear() : List<Gear>

    @Query("SELECT * FROM gear WHERE type = 'Belt'")
    fun getAllBelts() : List<Gear>

    @Query("SELECT * FROM gear WHERE type = 'Footwear'")
    fun getAllFootwear() : List<Gear>

    @Query("SELECT * FROM gear WHERE type = 'Accesory'")
    fun getAllAccessories() : List<Gear>
}