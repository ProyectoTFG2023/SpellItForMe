package danielabez.spellitforme.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import danielabez.spellitforme.databinding.FragmentRegistrationBinding
import danielabez.spellitforme.model.RegisteredUser
import danielabez.spellitforme.viewModel.RegisteredUserViewModel
import kotlinx.coroutines.runBlocking

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val registeredUserViewModel: RegisteredUserViewModel by activityViewModels()
    private var isMailFree : Boolean = false
    private var isUsernameFree : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registeredUserViewModel.checkMailInUse.observe(viewLifecycleOwner, Observer<RegisteredUser>{ checkUserByMail ->
            if(checkUserByMail != null){
                isMailFree = false
                //mailInUseWarning()
            }
            else{
                isMailFree = true
            }
        })

        registeredUserViewModel.checkUsernameInUse.observe(viewLifecycleOwner, Observer<RegisteredUser>{ checkUserByUsername ->
            if(checkUserByUsername != null){
                isUsernameFree = false
                //usernameInUseWarning()
            }
            else{
                isUsernameFree = true
            }
        })

        initializeCheckbox()
        initializeRegisterButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeCheckbox(){
        binding.ckbRegisterAcceptTerms.setOnCheckedChangeListener(){_, isChecked ->
            if(binding.ckbRegisterAcceptTerms.isChecked){
                binding.btRegisterRegister.setEnabled(true)
            }
            else {
                binding.btRegisterRegister.setEnabled(false)
            }
        }
    }

    //TODO: COMPROBAR LOS PARÁMETROS ANTES DE REGISTRAR NADA, POR AHORA SÓLO SE COMPRUEBA SU FUNCIONAMIENTO
    private fun initializeRegisterButton(){
        binding.btRegisterRegister.setOnClickListener(){
            runBlocking {
                registeredUserViewModel.addRegisteredUser(RegisteredUser(binding.tietRegisterMail.text.toString(), binding.tietRegisterUsername.text.toString(), binding.tietRegisterPassword.text.toString()))
                findNavController().popBackStack()
            }
        }
    }

    //TODO: PREGUNTAR EN LA REVISIÓN, DUDAS SOBRE EL PLANTEAMIENTO DEL FUNCIONAMIENTO
    private fun verifyRegister(){
        if(!binding.tietRegisterMail.text.toString().isNullOrEmpty()){
            registeredUserViewModel.isMailInUse(binding.tietRegisterMail.text.toString())
            registeredUserViewModel.isNameInUse(binding.tietRegisterMail.text.toString())
        }
    }
}