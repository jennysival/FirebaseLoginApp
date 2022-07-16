package br.com.zup.firebaselogin.ui.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.zup.firebaselogin.databinding.MessageItemBinding

class MessageAdapter(
    private var messageList: MutableList<String>
    ): RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

        class ViewHolder(val binding: MessageItemBinding): RecyclerView.ViewHolder(binding.root){
            fun showMessage(message: String){
                binding.tvMsgCard.text = message
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val savedMessage = messageList[position]
        holder.showMessage(savedMessage)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun updateMessageList(newList: MutableList<String>){
        if (messageList.size == 0){
            messageList = newList
        }else{
            messageList = mutableListOf()
            messageList.addAll(newList)
        }
        notifyDataSetChanged()
    }

}