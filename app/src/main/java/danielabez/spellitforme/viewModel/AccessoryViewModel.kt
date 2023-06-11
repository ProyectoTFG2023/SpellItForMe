package danielabez.spellitforme.viewModel

import android.app.Application
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import danielabez.spellitforme.model.Accessory
import danielabez.spellitforme.model.CharacterSet
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.repository.AccessoryRepository
import danielabez.spellitforme.repository.GearRepository
import danielabez.spellitforme.repository.RegisteredUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccessoryViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: AccessoryRepository
    val accessoryListLiveData : MutableLiveData<List<Accessory>> by lazy {
        MutableLiveData<List<Accessory>>()
    }
    val chosenAccessory : MutableLiveData<Accessory> by lazy {
        MutableLiveData<Accessory>()
    }
    val chosenPosition : MutableLiveData<Int?> by lazy {
        MutableLiveData<Int?>()
    }

    init {
        AccessoryRepository(getApplication<Application>().applicationContext)
        repository = AccessoryRepository
    }

    fun addAccessory(accessory: Accessory) = viewModelScope.launch (Dispatchers.IO){
        repository.addAccessory(accessory)
    }

    fun deleteAccessory(accessory: Accessory) = viewModelScope.launch (Dispatchers.IO){
        repository.deleteAccessory(accessory)
    }

    fun updateChosenAccessory(accessory: Accessory, accessoryPosition: Int) {
        chosenAccessory.postValue(accessory)
        chosenPosition.postValue(accessoryPosition)
    }
}