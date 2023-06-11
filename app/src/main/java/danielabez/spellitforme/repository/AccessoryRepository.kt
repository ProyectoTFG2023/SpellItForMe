package danielabez.spellitforme.repository

import android.app.Application
import android.content.Context
import danielabez.spellitforme.db.AppDatabase
import danielabez.spellitforme.db.dao.AccessoryDao
import danielabez.spellitforme.model.Accessory

object AccessoryRepository {
    private lateinit var accessoryModel: AccessoryDao
    private lateinit var application: Application

    operator fun invoke(context: Context) {
        this.application = context.applicationContext as Application
        accessoryModel = AppDatabase.getDatabase(AccessoryRepository.application).accessoryDao()
    }

    fun addAccessory(accessory: Accessory) = accessoryModel.addAccessory(accessory)

    fun deleteAccessory(accessory: Accessory) = accessoryModel.deleteAccessory(accessory)

    fun getAllGeneralAccessories() = accessoryModel.getAllGeneralAccessories()
}