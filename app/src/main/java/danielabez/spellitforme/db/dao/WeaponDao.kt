package danielabez.spellitforme.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import danielabez.spellitforme.model.Weapon

@Dao
interface WeaponDao {
    @Insert
    fun addWeapon(weapon: Weapon)

    @Query("SELECT * FROM weapon ORDER BY type, rarity, physAttack")
    fun getAllWeapons() : List<Weapon>

    @Query("SELECT * FROM weapon WHERE name LIKE '%' || :pWrittenString || '%' ORDER BY type, rarity, physAttack")
    fun getAllWeaponsLike(pWrittenString: String) : List<Weapon>

    @Query("SELECT * FROM weapon WHERE rarity = :pRarity ORDER BY type, rarity, physAttack")
    fun getAllWeaponsByRarity(pRarity: String) : List<Weapon>

    @Query("SELECT * FROM weapon WHERE rarity = :pRarity AND name LIKE '%' || :pWrittenString || '%' ORDER BY type, rarity, physAttack")
    fun getAllWeaponsByRarityLike(pRarity: String, pWrittenString: String) : List<Weapon>

    @Query("SELECT * FROM weapon WHERE type = :pType ORDER BY rarity, physAttack")
    fun getAllWeaponsByType(pType: String) : List<Weapon>

    @Query("SELECT * FROM weapon WHERE type = :pType AND name LIKE '%' || :pWrittenString || '%' ORDER BY rarity, physAttack")
    fun getAllWeaponsByTypeLike(pType: String, pWrittenString: String) : List<Weapon>

    @Query("SELECT * FROM weapon WHERE type = :pType AND rarity = :pRarity ORDER BY rarity, physAttack")
    fun getAllWeaponsByTypeAndRarity(pType: String, pRarity: String) : List<Weapon>

    @Query("SELECT * FROM weapon WHERE type = :pType AND rarity = :pRarity AND name LIKE '%' || :pWrittenString || '%' ORDER BY rarity, physAttack")
    fun getAllWeaponsByTypeAndRarityLike(pType: String, pRarity: String, pWrittenString: String) : List<Weapon>
}