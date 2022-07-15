package br.com.zup.firebaselogin.ui.register.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.zup.firebaselogin.R
import br.com.zup.firebaselogin.databinding.FragmentRegisterBinding
import br.com.zup.firebaselogin.domain.model.User
import br.com.zup.firebaselogin.ui.main.view.MainActivity
import br.com.zup.firebaselogin.ui.register.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = "Firebase App: Cadastro"

        binding.btnRegister.setOnClickListener {
            val user = getUserData()
            viewModel.validateUserData(user)
        }

        observers()
    }

    private fun getUserData(): User{
        return User(
            email = binding.etEmail.text.toString(),
            password = binding.etPassword.text.toString()
        )
    }

    private fun navigateToLoginFragment(user: User){
        val bundle = bundleOf("BUNDLE_KEY" to user)

        NavHostFragment.findNavController(this).navigate(R.id.action_registerFragment_to_loginFragment, bundle)
    }

    private fun observers(){
        viewModel.registerState.observe(this.viewLifecycleOwner){
            navigateToLoginFragment(it)
        }
        viewModel.errorState.observe(this.viewLifecycleOwner){
            Snackbar.make(binding.root,it,Snackbar.LENGTH_LONG).show()
        }
    }

}