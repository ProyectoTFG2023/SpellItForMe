package danielabez.spellitforme.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import danielabez.spellitforme.model.Skill
import danielabez.spellitforme.repository.SkillRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.FieldPosition

class SkillViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: SkillRepository
    val skillListLiveData : MutableLiveData<List<Skill>> by lazy {
        MutableLiveData<List<Skill>>()
    }
    val chosenSkill : MutableLiveData<Skill?> by lazy {
        MutableLiveData<Skill?>()
    }
    val chosenPosition : MutableLiveData<Int?> by lazy {
        MutableLiveData<Int?>()
    }

    init {
        SkillRepository(getApplication<Application>().applicationContext)
        repository = SkillRepository
    }

    fun addSkill(skill: Skill) = viewModelScope.launch (Dispatchers.IO){
        repository.addSkill(skill)
    }

    fun getAllSkills() {
        viewModelScope.launch(Dispatchers.IO) {
            skillListLiveData.postValue(repository.getAllSkills())
        }
    }

    fun getAllSkillsByNameLike(pWrittenString: String){
        viewModelScope.launch(Dispatchers.IO){
            skillListLiveData.postValue(repository.getAllSkillsByNameLike(pWrittenString))
        }
    }

    fun getAllSkillsByType(pType: String){
        viewModelScope.launch(Dispatchers.IO){
            skillListLiveData.postValue(repository.getAllSkillsByType(pType))
        }
    }

    fun getAllSkillsByTypeLike(pType: String){
        viewModelScope.launch(Dispatchers.IO){
            skillListLiveData.postValue(repository.getAllSkillsByTypeLike(pType))
        }
    }

    fun getAllSkillsByTypeAndNameLike(pType: String, pWrittenString: String){
        viewModelScope.launch(Dispatchers.IO){
            skillListLiveData.postValue(repository.getAllSkillsByTypeAndNameLike(pType, pWrittenString))
        }
    }

    fun getAllSkillsByTypeLikeAndNameLike(pType: String, pWrittenString: String){
        viewModelScope.launch(Dispatchers.IO){
            skillListLiveData.postValue(repository.getAllSkillsByTypeLikeAndNameLike(pType, pWrittenString))
        }
    }

    fun updateChosenSkill(skill: Skill?, position: Int?){
        chosenSkill.postValue(skill)
        chosenPosition.postValue(position)
    }
}