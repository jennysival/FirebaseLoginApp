package br.com.zup.firebaselogin.ui.home.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.zup.firebaselogin.R
import br.com.zup.firebaselogin.databinding.FragmentHomeBinding
import br.com.zup.firebaselogin.ui.home.viewmodel.HomeViewModel
import br.com.zup.firebaselogin.ui.main.view.MainActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
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

    }

    private fun showUserEmail(){
        val email = viewModel.getUserEmail()
        binding.tvEmail.text = email
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