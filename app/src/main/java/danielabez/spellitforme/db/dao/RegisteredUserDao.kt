package danielabez.spellitforme.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import danielabez.spellitforme.model.RegisteredUser
import kotlinx.coroutines.Deferred

@Dao
interface RegisteredUserDao {
    @Insert
    fun addRegisteredUser(registeredUser: RegisteredUser)

    @Delete
    fun deleteRegisteredUser(registeredUser: RegisteredUser)

    @Query("SELECT * FROM registered_users WHERE username = :pUsername")
    fun getRegisteredUser(pUsername: String) : RegisteredUser

    @Query("SELECT * FROM registered_users WHERE username = :pUsername AND password = :pPassword")
    fun checkRegisteredUser(pUsername: String, pPassword: String) : RegisteredUser
}