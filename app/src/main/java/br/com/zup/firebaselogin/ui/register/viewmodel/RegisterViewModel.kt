package br.com.zup.firebaselogin.ui.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.firebaselogin.domain.model.User
import br.com.zup.firebaselogin.domain.repository.AuthRepository

class RegisterViewModel: ViewModel() {
    private val authRepository = AuthRepository()

    private var _registerState = MutableLiveData<User>()
    val registerState: LiveData<User> = _registerState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun validateUserData(user:User){
        when{
            user.email.isEmpty() -> {
                _errorState.value = "E-mail é obrigatório"
            }
            user.password.isEmpty() -> {
                _errorState.value = "Senha é obrigatória"
            }
            else -> {
                registerUser(user)
            }
        }
    }

    private fun registerUser(user: User){
        try {
            authRepository.register(user.email,user.password)
                .addOnSuccessListener {
                _registerState.value = user
            }
                .addOnFailureListener{
                _errorState.value = "Erro no cadastro"
            }
        }catch (e: Exception){
            _errorState.value = e.message
        }
    }
}