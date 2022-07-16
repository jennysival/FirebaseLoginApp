package br.com.zup.firebaselogin.ui.home.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.firebaselogin.domain.repository.AuthRepository
import br.com.zup.firebaselogin.domain.repository.MessageRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HomeViewModel: ViewModel() {
    private val authRepository = AuthRepository()
    private val messageRepository = MessageRepository()

    private var _messageListState = MutableLiveData<List<String>>()
    val messageListState: LiveData<List<String>> = _messageListState

    private var _msgState = MutableLiveData<String>()
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

    fun getSavedMessagesList(){
        messageRepository.getSavedMessagesList()
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot){
                    val messageList = mutableListOf<String>()
                    for (resultSnapshot in snapshot.children){
                        val messageResponse = resultSnapshot.getValue(String::class.java)
                        messageResponse?.let { messageList.add(it) }
                    }
                    _messageListState.value = messageList
                }
                override fun onCancelled(error: DatabaseError){
                    _msgState.value = error.message
                }

            })
    }
}