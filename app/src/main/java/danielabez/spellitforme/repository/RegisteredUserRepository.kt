package danielabez.spellitforme.repository

import android.app.Application
import android.content.Context
import danielabez.spellitforme.db.AppDatabase
import danielabez.spellitforme.db.dao.RegisteredUserDao
import danielabez.spellitforme.model.RegisteredUser

object RegisteredUserRepository {
    //Variable del DAO correspondiente a los usuarios registrados, creada para poder realizar sus operaciones correspondientes
    private lateinit var registeredUserModel: RegisteredUserDao
    //Esta variable se utilizará para almacenar el contexto de la aplicación, requerido normalmente para las operaciones de recuperaciones de datos
    private lateinit var application: Application

    operator fun invoke(context: Context) {
        this.application = context.applicationContext as Application
        registeredUserModel = AppDatabase.getDatabase(application).registeredUserDao()
    }

    /*
    * Función para introducir nuevas entradas a la tabla de usuarios
    */
    fun addRegisteredUser(registeredUser: RegisteredUser) = registeredUserModel.addRegisteredUser(registeredUser)

    /*
    * Función para eliminar entradas ya existentes en la tabla de usuarios
    */
    fun deleteRegisteredUser(registeredUser: RegisteredUser) = registeredUserModel.deleteRegisteredUser(registeredUser)

    fun getRegisteredUser(pUsername: String) = registeredUserModel.getRegisteredUser(pUsername)

    fun checkRegisteredUser(pUsername: String, pPassword: String) = registeredUserModel.checkRegisteredUser(pUsername, pPassword)
}