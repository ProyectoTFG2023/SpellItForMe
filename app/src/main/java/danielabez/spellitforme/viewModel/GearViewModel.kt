package danielabez.spellitforme.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.model.RegisteredUser
import danielabez.spellitforme.repository.GearRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GearViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: GearRepository
    val gearListLiveData : MutableLiveData<List<Gear>> by lazy {
        MutableLiveData<List<Gear>>()
    }

    init {
        GearRepository(getApplication<Application>().applicationContext)
        repository = GearRepository
        getAllHeadgear()
    }

    fun addGear(gear: Gear) = viewModelScope.launch (Dispatchers.IO){
        repository.addGear(gear)
    }

    fun getAllHeadgear() {
        viewModelScope.launch (Dispatchers.IO){
            gearListLiveData.postValue(repository.getAllHeadgear())
        }
    }

    fun getAllTorso() {
        viewModelScope.launch (Dispatchers.IO){
            gearListLiveData.postValue(repository.getAllTorso())
        }
    }

    fun getAllHandwear() {
        viewModelScope.launch (Dispatchers.IO){
            gearListLiveData.postValue(repository.getAllHandwear())
        }
    }

    fun getAllBelts() {
        viewModelScope.launch (Dispatchers.IO){
            gearListLiveData.postValue(repository.getAllBelts())
        }
    }

    fun getAllFootwear() {
        viewModelScope.launch (Dispatchers.IO){
            gearListLiveData.postValue(repository.getAllFootwear())
        }
    }
}