package danielabez.spellitforme.repository

import android.app.Application
import android.content.Context
import androidx.room.Query
import danielabez.spellitforme.db.AppDatabase
import danielabez.spellitforme.db.dao.SkillDao
import danielabez.spellitforme.model.Skill
import java.lang.Appendable

object SkillRepository {
    private lateinit var skillModel: SkillDao
    private lateinit var application: Application

    operator fun invoke(context: Context){
        this.application = context.applicationContext as Application
        skillModel = AppDatabase.getDatabase(application).skillDao()
    }

    fun addSkill(skill: Skill) = skillModel.addSkill(skill)



    fun getAllSkills() = skillModel.getAllSkills()

    fun getAllSkillsByNameLike(pWrittenString: String) = skillModel.getAllSkillsByNameLike(pWrittenString)

    fun getAllSkillsByType(pType: String) = skillModel.getAllSkillsByType(pType)

    fun getAllSkillsByTypeLike(pType: String) = skillModel.getAllSkillsByTypeLike(pType)

    fun getAllSkillsByTypeAndNameLike(pType: String, pWrittenString: String) = skillModel.getAllSkillsByTypeAndNameLike(pType, pWrittenString)

    fun getAllSkillsByTypeLikeAndNameLike(pType: String, pWrittenString: String) = skillModel.getAllSkillsByTypeLikeAndNameLike(pType, pWrittenString)
}