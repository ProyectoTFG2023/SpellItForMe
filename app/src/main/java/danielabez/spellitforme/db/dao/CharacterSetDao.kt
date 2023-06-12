package danielabez.spellitforme.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import danielabez.spellitforme.model.CharacterSet

@Dao
interface CharacterSetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCharacterSet(characterSet: CharacterSet)

    @Delete
    fun deleteCharacterSet(characterSet: CharacterSet)
}