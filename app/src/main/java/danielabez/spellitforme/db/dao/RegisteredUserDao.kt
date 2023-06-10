package danielabez.spellitforme.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import danielabez.spellitforme.model.Accessory
import danielabez.spellitforme.model.RegisteredUser
import danielabez.spellitforme.model.relations.RegisteredUserWithAccessories

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

    @Transaction
    @Query("SELECT * FROM registered_user WHERE id = :pUserId")
    fun getRegisteredUserWithAccesories(pUserId: Long?) : List<RegisteredUserWithAccessories>
}