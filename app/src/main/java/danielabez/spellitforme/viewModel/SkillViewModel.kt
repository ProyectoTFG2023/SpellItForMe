package danielabez.spellitforme.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import danielabez.spellitforme.model.Skill
import danielabez.spellitforme.repository.SkillRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SkillViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: SkillRepository

    init {
        SkillRepository(getApplication<Application>().applicationContext)
        repository = SkillRepository
    }

    fun addSkill(skill: Skill) = viewModelScope.launch (Dispatchers.IO){
        repository.addSkill(skill)
    }

    fun getAllSkills() : List<Skill> {
        var recoveredSkills : List<Skill> = listOf()
        viewModelScope.launch(Dispatchers.IO) {
            recoveredSkills = repository.getAllSkills()
        }

        return recoveredSkills
    }

    fun getSkillById(pId: Long) : Skill {
        var recoveredSkill : Skill = Skill()
        viewModelScope.launch(Dispatchers.IO) {
            recoveredSkill = repository.getSkillById(pId)
        }

        return recoveredSkill
    }

    fun getSkillByName(pName: String) : Skill {
        var recoveredSkill : Skill = Skill()
        viewModelScope.launch(Dispatchers.IO) {
            recoveredSkill = repository.getSkillByName(pName)
        }

        return recoveredSkill
    }
}