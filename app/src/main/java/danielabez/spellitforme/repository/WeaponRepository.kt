package danielabez.spellitforme.repository

import android.app.Application
import android.content.Context
import danielabez.spellitforme.db.AppDatabase
import danielabez.spellitforme.db.dao.WeaponDao
import danielabez.spellitforme.model.Weapon

object WeaponRepository {
    private lateinit var weaponModel: WeaponDao
    private lateinit var application: Application

    operator fun invoke(context: Context) {
        this.application = context.applicationContext as Application
        weaponModel = AppDatabase.getDatabase(application).weaponDao()
    }

    fun addWeapon(weapon: Weapon) = weaponModel.addWeapon(weapon)

    fun getAllWeapons() = weaponModel.getAllWeapons()

    fun getAllWeaponsLike(pWrittenString: String) = weaponModel.getAllWeaponsLike(pWrittenString)

    fun getAllWeaponsByRarity(pRarity: String) = weaponModel.getAllWeaponsByRarity(pRarity)

    fun getAllWeaponsByRarityLike(pRarity: String, pWrittenString: String) = weaponModel.getAllWeaponsByRarityLike(pRarity, pWrittenString)

    fun getAllWeaponsByType(pType: String) = weaponModel.getAllWeaponsByType(pType)

    fun getAllWeaponsByTypeLike(pType: String, pWrittenString: String) = weaponModel.getAllWeaponsByTypeLike(pType, pWrittenString)

    fun getAllWeaponsByTypeAndRarity(pType: String, pRarity: String) = weaponModel.getAllWeaponsByTypeAndRarity(pType, pRarity)

    fun getAllWeaponsByTypeAndRarityLike(pType: String, pRarity: String, pWrittenString: String) = weaponModel.getAllWeaponsByTypeAndRarityLike(pType, pRarity, pWrittenString)
}