package danielabez.spellitforme.repository

import android.app.Application
import android.content.Context
import androidx.room.Query
import danielabez.spellitforme.db.AppDatabase
import danielabez.spellitforme.db.dao.GearDao
import danielabez.spellitforme.model.Gear

object GearRepository {
    private lateinit var gearModel: GearDao
    private lateinit var application: Application

    operator fun invoke(context: Context) {
        this.application = context.applicationContext as Application
        gearModel = AppDatabase.getDatabase(application).gearDao()
    }

    fun addGear(gear: Gear) = gearModel.addGear(gear)

    fun getAllGear() = gearModel.getAllGear()

    fun getAllGearByType(pType: String) = gearModel.getAllGearByType(pType)

    fun getAllGearByTypeLike(pType: String, pWrittenString: String) = gearModel.getAllGearByTypeLike(pType, pWrittenString)

    fun getAllGearByTypeAndRarity(pType: String, pRarity: String) = gearModel.getAllGearByTypeAndRarity(pType, pRarity)

    fun getAllGearByTypeAndRarityLike(pType: String, pRarity: String, pWrittenString: String) = gearModel.getAllGearByTypeAndRarityLike(pType, pRarity, pWrittenString)
}