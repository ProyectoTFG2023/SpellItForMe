package danielabez.spellitforme.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import danielabez.spellitforme.model.Weapon
import danielabez.spellitforme.repository.WeaponRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeaponViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: WeaponRepository
    val weaponListLiveData : MutableLiveData<List<Weapon>> by lazy {
        MutableLiveData<List<Weapon>>()
    }

    init {
        WeaponRepository(getApplication<Application>().applicationContext)
        repository = WeaponRepository
    }

    fun addWeapon(weapon: Weapon) = viewModelScope.launch (Dispatchers.IO){
        repository.addWeapon(weapon)
    }

    fun getAllWeapons(){
        viewModelScope.launch (Dispatchers.IO) {
            weaponListLiveData.postValue(repository.getAllWeapons())
        }
    }

    fun getAllWeaponsLike(pWrittenString: String){
        viewModelScope.launch (Dispatchers.IO) {
            weaponListLiveData.postValue(repository.getAllWeaponsLike(pWrittenString))
        }
    }

    fun getAllWeaponsByRarity(pRarity: String) {
        viewModelScope.launch (Dispatchers.IO){
            weaponListLiveData.postValue(repository.getAllWeaponsByRarity(pRarity))
        }
    }

    fun getAllWeaponsByRarityLike(pRarity: String, pWrittenString: String) {
        viewModelScope.launch (Dispatchers.IO){
            weaponListLiveData.postValue(repository.getAllWeaponsByRarityLike(pRarity, pWrittenString))
        }
    }

    fun getAllWeaponsByType(pType: String) {
        viewModelScope.launch (Dispatchers.IO){
            weaponListLiveData.postValue(repository.getAllWeaponsByType(pType))
        }
    }

    fun getAllWeaponsByTypeLike(pType: String, pWrittenString: String) {
        viewModelScope.launch (Dispatchers.IO){
            weaponListLiveData.postValue(repository.getAllWeaponsByTypeLike(pType, pWrittenString))
        }
    }

    fun getAllWeaponsByTypeAndRarity(pType: String, pRarity: String){
        viewModelScope.launch (Dispatchers.IO){
            weaponListLiveData.postValue(repository.getAllWeaponsByTypeAndRarity(pType, pRarity))
        }
    }

    fun getAllWeaponsByTypeAndRarityLike(pType: String, pRarity: String, pWrittenString: String){
        viewModelScope.launch (Dispatchers.IO){
            weaponListLiveData.postValue(repository.getAllWeaponsByTypeAndRarityLike(pType, pRarity, pWrittenString))
        }
    }
}