package danielabez.spellitforme.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.repository.GearRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GearViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: GearRepository

    init {
        GearRepository(getApplication<Application>().applicationContext)
        repository = GearRepository
    }

    fun addGear(gear: Gear) = viewModelScope.launch (Dispatchers.IO){
        repository.addGear(gear)
    }

    fun getAllHeadgear() : List<Gear>? {
        var headgearList : List<Gear>? = null
        viewModelScope.launch (Dispatchers.IO){
            headgearList = repository.getAllHeadgear()
        }

        return headgearList
    }

    fun getAllTorso() : List<Gear>? {
        var torsoList : List<Gear>? = null
        viewModelScope.launch (Dispatchers.IO){
            torsoList = repository.getAllTorso()
        }

        return torsoList
    }

    fun getAllHandwear() : List<Gear>? {
        var handwearList : List<Gear>? = null
        viewModelScope.launch (Dispatchers.IO){
            handwearList = repository.getAllHandwear()
        }

        return handwearList
    }

    fun getAllBelts() : List<Gear>? {
        var beltsList : List<Gear>? = null
        viewModelScope.launch (Dispatchers.IO){
            beltsList = repository.getAllBelts()
        }

        return beltsList
    }

    fun getAllFootwear() : List<Gear>? {
        var footwearList : List<Gear>? = null
        viewModelScope.launch (Dispatchers.IO){
            footwearList = repository.getAllFootwear()
        }

        return footwearList
    }
}