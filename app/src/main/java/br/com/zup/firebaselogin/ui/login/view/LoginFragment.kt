package br.com.zup.firebaselogin.ui.login.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.zup.firebaselogin.R
import br.com.zup.firebaselogin.databinding.FragmentLoginBinding
import br.com.zup.firebaselogin.domain.model.User
import br.com.zup.firebaselogin.ui.login.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNewAccount.setOnClickListener {
            navigateToRegisterFragment()
        }

        binding.btnLogin.setOnClickListener {
            val user = getUserData()
            viewModel.validateUser(user)
        }

        observers()
    }

    private fun getUserData(): User{
        return User(
            email = binding.etEmailLogin.text.toString(),
            password = binding.etPasswordLogin.text.toString()
        )
    }

    private fun navigateToRegisterFragment(){
        NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun navigateToHomeFragment(user: User){
        val bundle = bundleOf("HOME_KEY" to user)
        NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_homeFragment, bundle)
    }

    private fun observers(){
        viewModel.loginState.observe(this.viewLifecycleOwner){
            navigateToHomeFragment(it)
        }
        viewModel.errorState.observe(this.viewLifecycleOwner){
            Snackbar.make(binding.root,it,Snackbar.LENGTH_LONG).show()
        }

    }
}