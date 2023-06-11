package danielabez.spellitforme.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import danielabez.spellitforme.model.Skill

@Dao
interface SkillDao {
    @Insert
    fun addSkill(skill: Skill)

    @Query("SELECT * FROM skill WHERE id = :pId")
    fun getSkillById(pId: Long) : Skill

    @Query("SELECT * FROM skill WHERE name = :pName")
    fun getSkillByName(pName: String) : Skill

    @Query("SELECT * FROM skill")
    fun getAllSkills() : List<Skill>

    @Query("SELECT * FROM skill WHERE name LIKE '%' || :pWrittenString || '%' ORDER BY type, name")
    fun getAllSkillsByNameLike(pWrittenString: String) : List<Skill>

    @Query("SELECT * FROM skill WHERE type = :pType ORDER BY name")
    fun getAllSkillsByType(pType: String) : List<Skill>

    @Query("SELECT * FROM skill WHERE type LIKE '%' || :pType || '%' ORDER BY name")
    fun getAllSkillsByTypeLike(pType: String) : List<Skill>

    @Query("SELECT * FROM skill WHERE type = :pType AND name LIKE '%' || :pWrittenString || '%' ORDER BY name")
    fun getAllSkillsByTypeAndNameLike(pType: String, pWrittenString: String) : List<Skill>

    @Query("SELECT * FROM skill WHERE type LIKE '%' || :pType || '%' AND name LIKE '%' || :pWrittenString || '%' ORDER BY name")
    fun getAllSkillsByTypeLikeAndNameLike(pType: String, pWrittenString: String) : List<Skill>
}