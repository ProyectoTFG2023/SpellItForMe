package danielabez.spellitforme.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import danielabez.spellitforme.model.Accessory
import danielabez.spellitforme.model.CharacterSet
import danielabez.spellitforme.model.RegisteredUser
import danielabez.spellitforme.repository.RegisteredUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisteredUserViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: RegisteredUserRepository
    val checkRegisteredUser : MutableLiveData<RegisteredUser?> by lazy {
        MutableLiveData<RegisteredUser?>()
    }
    val checkMailInUse : MutableLiveData<RegisteredUser> by lazy {
        MutableLiveData<RegisteredUser>()
    }
    val checkUsernameInUse : MutableLiveData<RegisteredUser> by lazy {
        MutableLiveData<RegisteredUser>()
    }
    val comingBack : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        RegisteredUserRepository(getApplication<Application>().applicationContext)
        repository = RegisteredUserRepository
    }

    fun addRegisteredUser(registeredUser: RegisteredUser) = viewModelScope.launch(Dispatchers.IO) {
        repository.addRegisteredUser(registeredUser)
    }

    fun deleteRegisteredUser(registeredUser: RegisteredUser) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRegisteredUser(registeredUser)
    }

    fun checkRegisteredUserByUsernameAndPassword(pUsername: String, pPassword: String) : LiveData<RegisteredUser?> {
        viewModelScope.launch (Dispatchers.IO){
            val checkResult = repository.getRegisteredUserByUsernameAndPassword(pUsername, pPassword)
            checkRegisteredUser.postValue(checkResult)
        }
        return checkRegisteredUser
    }

    fun isMailInUse(pMail: String) : LiveData<RegisteredUser> {
        viewModelScope.launch (Dispatchers.IO){
            val checkResult = repository.getRegisteredUserByMail(pMail)
            checkMailInUse.postValue(checkResult)
        }
        return checkMailInUse
    }

    fun isUsernameInUse(pUsername: String) : LiveData<RegisteredUser> {
        viewModelScope.launch (Dispatchers.IO){
            val checkResult = repository.getRegisteredUserByUsername(pUsername)
            checkUsernameInUse.postValue(checkResult)
        }
        return checkUsernameInUse
    }

    fun emptyCheckRegisteredUser(){
        viewModelScope.launch (Dispatchers.IO) {
            checkRegisteredUser.postValue(null)
        }
    }

    fun getRegisteredUserWithAllOwnedCharacterSets(liveData: MutableLiveData<List<CharacterSet>>){
        viewModelScope.launch(Dispatchers.IO){
            liveData.postValue(repository.getRegisteredUserWithCharacterSets(checkRegisteredUser.value?.id!!).first().characterSets)
        }
    }

    fun getRegisteredUserWithAllOwnedAccesories(liveData: MutableLiveData<List<Accessory>>) {
        viewModelScope.launch (Dispatchers.IO){
            liveData.postValue(repository.getRegisteredUserWithAccessories(checkRegisteredUser.value?.id!!).first().accesories)
        }
    }
}