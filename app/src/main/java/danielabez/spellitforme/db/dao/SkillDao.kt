package danielabez.spellitforme.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import danielabez.spellitforme.model.Skill

@Dao
interface SkillDao {
    @Insert
    fun addSkill(skill: Skill)

    @Query("SELECT * FROM skill")
    fun getAllSkills() : List<Skill>

    @Query("SELECT * FROM skill WHERE id = :pId")
    fun getSkillById(pId: Long) : Skill

    @Query("SELECT * FROM skill WHERE name = :pName")
    fun getSkillByName(pName: String) : Skill
}