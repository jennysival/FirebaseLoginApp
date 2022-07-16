package br.com.zup.firebaselogin.ui.home.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.firebaselogin.domain.repository.AuthRepository
import br.com.zup.firebaselogin.domain.repository.MessageRepository

class HomeViewModel: ViewModel() {
    private val authRepository = AuthRepository()
    private val messageRepository = MessageRepository()

    private val _msgState = MutableLiveData<String>()
    val msgState: LiveData<String> = _msgState

    fun getUserEmail() = authRepository.getUserEmail()

    fun logout() = authRepository.logout()

    fun saveUserMessage(message: String){
        val messagePath = getMessagePath(message)
        messageRepository.databaseRef().child("$messagePath")
            .setValue(message){
                error,reference ->
                if (error != null){
                    _msgState.value = error.message
                }
                _msgState.value = "Mensagem salva!"
            }
    }

    private fun getMessagePath(message: String): String?{
        val uri: Uri = Uri.parse(message)
        return uri.toString()
    }
}