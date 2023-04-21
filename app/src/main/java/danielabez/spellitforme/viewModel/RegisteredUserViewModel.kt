package danielabez.spellitforme.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import danielabez.spellitforme.db.dao.RegisteredUserDao
import danielabez.spellitforme.model.RegisteredUser
import danielabez.spellitforme.repository.RegisteredUserRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisteredUserViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: RegisteredUserRepository
    val foundRegisteredUser : MutableLiveData<RegisteredUser> by lazy {
        MutableLiveData<RegisteredUser>()
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

    fun checkRegisteredUser(pUsername: String, pPassword: String) : LiveData<RegisteredUser> {
        viewModelScope.launch (Dispatchers.IO){
            val checkResult = repository.checkRegisteredUser(pUsername, pPassword)
            foundRegisteredUser.postValue(checkResult)
        }
        return foundRegisteredUser
    }
}