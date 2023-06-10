package danielabez.spellitforme.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import danielabez.spellitforme.model.CharacterSet

@Dao
interface CharacterSetDao {
    @Insert
    fun addCharacterSet(characterSet: CharacterSet)

    //TODO: CAMBIAR LA QUERY, SÓLO DEBE SER RELACIONADA SEGÚN EL USUARIO LOGEADO
    @Query("SELECT * FROM character_set")
    fun getCharacterSetsByRegisteredUser() : List<CharacterSet>
}