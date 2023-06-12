package danielabez.spellitforme.repository

import android.app.Application
import android.content.Context
import danielabez.spellitforme.db.AppDatabase
import danielabez.spellitforme.db.dao.CharacterSetDao
import danielabez.spellitforme.model.CharacterSet

object CharacterSetRepository {
    private lateinit var characterSetModel: CharacterSetDao
    private lateinit var application: Application

    operator fun invoke(context: Context) {
        this.application = context.applicationContext as Application
        characterSetModel = AppDatabase.getDatabase(application).characterSetDao()
    }

    fun addCharacterSet(characterSet: CharacterSet) = characterSetModel.addCharacterSet(characterSet)

    fun deleteCharacterSet(characterSet: CharacterSet) = characterSetModel.deleteCharacterSet(characterSet)
}