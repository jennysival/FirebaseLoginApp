package br.com.zup.firebaselogin.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import br.com.zup.firebaselogin.domain.repository.AuthRepository

class HomeViewModel: ViewModel() {
    private val authRepository = AuthRepository()

    fun getUserEmail() = authRepository.getUserEmail()
}