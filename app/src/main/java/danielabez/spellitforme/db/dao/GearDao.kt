package danielabez.spellitforme.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import danielabez.spellitforme.model.Gear

@Dao
interface GearDao {
    @Insert
    fun addGear(gear: Gear)

    @Query("SELECT * FROM gear ORDER BY rarity, physDefense")
    fun getAllGear() : List<Gear>

    @Query("SELECT * FROM gear WHERE type = :pType ORDER BY rarity, physDefense")
    fun getAllGearByType(pType: String) : List<Gear>

    @Query("SELECT * FROM gear WHERE type = :pType AND name LIKE '%' || :pWrittenString || '%' ORDER BY rarity, physDefense")
    fun getAllGearByTypeLike(pType: String, pWrittenString: String) : List<Gear>

    @Query("SELECT * FROM gear WHERE type = :pType AND rarity = :pRarity ORDER BY physDefense")
    fun getAllGearByTypeAndRarity(pType: String, pRarity: String) : List<Gear>

    @Query("SELECT * FROM gear WHERE type = :pType AND rarity = :pRarity AND name LIKE '%' || :pWrittenString || '%' ORDER BY physDefense")
    fun getAllGearByTypeAndRarityLike(pType: String, pRarity: String, pWrittenString: String) : List<Gear>
}