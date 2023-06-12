package danielabez.spellitforme.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.FragmentLoginBinding
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

        binding.btLoginLog.setOnClickListener(){
            verifyLogin()
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

    /*
    * Método que se encarga de comprobar si existe un usuario con el nombre y contraseña escritos por el usuario.
    */
    private fun verifyLogin() {
        if (!binding.tietLoginUsername.text.isNullOrEmpty() && !binding.tietLoginPassword.text.isNullOrEmpty()) {
            val userFound = registeredUserViewModel.checkRegisteredUserByUsernameAndPassword(
                binding.tietLoginUsername.text.toString(),
                binding.tietLoginPassword.text.toString()
            )
            Thread.sleep(100)
            if(userFound != null){
                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                findNavController().navigate(action)
            }
            else{
                failedLoginWarning()
            }
        } else {
            Toast.makeText(context, getString(R.string.emptyFieldsWarning), Toast.LENGTH_SHORT).show()
        }
    }

    /*
    * Método que muestra un mensaje en una ventana de diálogo cuando se produce un error al iniciar sesión.
    */
    private fun failedLoginWarning() {
        AlertDialog.Builder(activity as Context)
            .setTitle(R.string.failedLoginWarningTitle)
            .setMessage(getString(R.string.failedLoginWarningMessage))
            .setPositiveButton(android.R.string.ok) { v, _ ->
                v.dismiss()
            }
            .setCancelable(false)
            .create()
            .show()
    }
}