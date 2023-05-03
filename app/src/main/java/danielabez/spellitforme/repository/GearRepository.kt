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

    fun getAllHeadgear() = gearModel.getAllHeadgear()

    fun getAllTorso() = gearModel.getAllTorso()

    fun getAllHandwear() = gearModel.getAllHandwear()

    fun getAllBelts() = gearModel.getAllBelts()

    fun getAllFootwear() = gearModel.getAllFootwear()

    fun getAllAccessories() = gearModel.getAllAccessories()
}