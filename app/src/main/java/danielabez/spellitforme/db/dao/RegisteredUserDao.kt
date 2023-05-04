package danielabez.spellitforme.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import danielabez.spellitforme.model.RegisteredUser

@Dao
interface RegisteredUserDao {
    @Insert
    fun addRegisteredUser(registeredUser: RegisteredUser)

    @Delete
    fun deleteRegisteredUser(registeredUser: RegisteredUser)

    @Query("SELECT * FROM registered_user WHERE username = :pUsername")
    fun getRegisteredUserByUsername(pUsername: String) : RegisteredUser

    @Query("SELECT * FROM registered_user WHERE mail = :pMail")
    fun getRegisteredUserByMail(pMail: String) : RegisteredUser

    @Query("SELECT * FROM registered_user WHERE username = :pUsername AND password = :pPassword")
    fun getRegisteredUserByUsernameAndPassword(pUsername: String, pPassword: String) : RegisteredUser
}