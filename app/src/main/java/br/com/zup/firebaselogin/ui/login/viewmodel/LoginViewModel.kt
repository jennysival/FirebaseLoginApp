package br.com.zup.firebaselogin.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.firebaselogin.domain.model.User
import br.com.zup.firebaselogin.domain.repository.AuthRepository

class LoginViewModel: ViewModel() {
    private val authRepository = AuthRepository()

    private var _loginState = MutableLiveData<User>()
    val loginState: LiveData<User> = _loginState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun validateUser(user: User){
        when{
            user.email.isEmpty() -> {
                _errorState.value = "E-mail é obrigatório"
            }
            user.password.isEmpty() -> {
                _errorState.value = "Senha é obrigatória"
            }
            else -> {
                login(user)
            }
        }
    }

    private fun login(user: User){
        try {
            authRepository.login(user.email,user.password).addOnSuccessListener { _loginState.value = user }
        }
    }

}