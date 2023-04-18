package danielabez.spellitforme.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import danielabez.spellitforme.model.RegisteredUser

@Dao
interface RegisteredUserDao {
    @Insert
    fun addRegisteredUser(registeredUser: RegisteredUser)

    @Delete
    fun deleteRegisteredUser(registeredUser: RegisteredUser)
}