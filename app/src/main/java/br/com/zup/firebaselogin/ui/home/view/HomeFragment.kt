package br.com.zup.firebaselogin.ui.home.view

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zup.firebaselogin.R
import br.com.zup.firebaselogin.databinding.FragmentHomeBinding
import br.com.zup.firebaselogin.ui.home.viewmodel.HomeViewModel
import br.com.zup.firebaselogin.ui.main.view.MainActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var messageList = mutableListOf<String>()

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private val messageAdapter: MessageAdapter by lazy {
        MessageAdapter(arrayListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.title = "Firebase App: Home"

        showUserEmail()

        binding.btnSalvarMsg.setOnClickListener {
            if(validateField()){
                viewModel.saveUserMessage(getMessage())
                clearField()
                viewModel.getSavedMessagesList()
                showRecycler()
            }
        }

        observer()

    }

    private fun showUserEmail(){
        val email = viewModel.getUserEmail()
        binding.tvEmail.text = email
    }

    private fun getMessage(): String{
        val message = binding.etMsg.text.toString()
        messageList.add(message)
        return message
    }

    private fun validateField(): Boolean{
        return if(binding.etMsg.text.isEmpty()){
            binding.etMsg.error = "Escreva sua mensagem"
            false
        }else{
            true
        }
    }

    private fun clearField(){
        binding.etMsg.text.clear()
    }

    private fun observer(){
        viewModel.msgState.observe(this.viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        viewModel.messageListState.observe(this.viewLifecycleOwner){
            messageAdapter.updateMessageList(it.toMutableList())
        }
    }

    private fun showRecycler(){
        binding.rvMsg.adapter = messageAdapter
        binding.rvMsg.layoutManager = LinearLayoutManager(context)
    }

    private fun navigateToLoginFragment(){
        NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_loginFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_exit -> {
                viewModel.logout()
                activity?.supportFragmentManager?.popBackStack()
                navigateToLoginFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}