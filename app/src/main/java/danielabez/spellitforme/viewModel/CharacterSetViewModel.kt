package danielabez.spellitforme.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import danielabez.spellitforme.model.CharacterSet
import danielabez.spellitforme.repository.CharacterSetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterSetViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: CharacterSetRepository
    val characterSetListLiveData : MutableLiveData<List<CharacterSet>> by lazy {
        MutableLiveData<List<CharacterSet>>()
    }

    init {
        CharacterSetRepository(getApplication<Application>().applicationContext)
        repository = CharacterSetRepository
    }

    fun addCharacterSet(characterSet: CharacterSet) = viewModelScope.launch (Dispatchers.IO){
        repository.addCharacterSet(characterSet)
    }

    fun getCharacterSetsByRegisteredUser(){
        viewModelScope.launch (Dispatchers.IO) {
            characterSetListLiveData.postValue(repository.getCharacterSetsByRegisteredUser())
        }
    }
}