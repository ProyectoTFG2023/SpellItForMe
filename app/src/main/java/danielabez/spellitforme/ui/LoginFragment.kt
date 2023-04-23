package danielabez.spellitforme.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import danielabez.spellitforme.databinding.FragmentLoginBinding
import danielabez.spellitforme.db.dao.RegisteredUserDao
import danielabez.spellitforme.model.RegisteredUser
import danielabez.spellitforme.viewModel.RegisteredUserViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val registeredUserViewModel: RegisteredUserViewModel by activityViewModels()

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registeredUserViewModel.foundRegisteredUser.observe(viewLifecycleOwner, Observer<RegisteredUser>{ foundRegisteredUser ->
            if(foundRegisteredUser != null){

            }
            else{

            }
        })

        binding.btLoginLog.setOnClickListener(){
            registeredUserViewModel.checkRegisteredUser("paquito", "paquitoPwd")
        }

        binding.tvLoginRegisterNow.setOnClickListener(){
            val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}