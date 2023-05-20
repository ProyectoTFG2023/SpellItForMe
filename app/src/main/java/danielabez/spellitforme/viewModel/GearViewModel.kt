package danielabez.spellitforme.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import danielabez.spellitforme.model.Gear
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
    }

    fun addGear(gear: Gear) = viewModelScope.launch (Dispatchers.IO){
        repository.addGear(gear)
    }

    fun getAllGear(){
        viewModelScope.launch (Dispatchers.IO) {
            gearListLiveData.postValue(repository.getAllGear())
        }
    }

    fun getAllGearByType(pType: String) {
        viewModelScope.launch (Dispatchers.IO){
            //Log.d("SpellItForMe_Debug", repository.getAllGearByType(pType).toString())
            gearListLiveData.postValue(repository.getAllGearByType(pType))
        }
    }

    fun getAllGearByTypeLike(pType: String, pWrittenString: String) {
        viewModelScope.launch (Dispatchers.IO){
            //Log.d("SpellItForMe_Debug", repository.getAllGearByTypeLike(pType, pWrittenString).toString())
            gearListLiveData.postValue(repository.getAllGearByTypeLike(pType, pWrittenString))
        }
    }

    fun getAllGearByTypeAndRarity(pType: String, pRarity: String){
        viewModelScope.launch (Dispatchers.IO){
            //Log.d("SpellItForMe_Debug", repository.getAllGearByTypeAndRarity(pType, pRarity).toString())
            gearListLiveData.postValue(repository.getAllGearByTypeAndRarity(pType, pRarity))
        }
    }

    fun getAllGearByTypeAndRarityLike(pType: String, pRarity: String, pWrittenString: String){
        viewModelScope.launch (Dispatchers.IO){
            //Log.d("SpellItForMe_Debug", repository.getAllGearByTypeAndRarityLike(pType, pRarity, pWrittenString).toString())
            gearListLiveData.postValue(repository.getAllGearByTypeAndRarityLike(pType, pRarity, pWrittenString))
        }
    }

}