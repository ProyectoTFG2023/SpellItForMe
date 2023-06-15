package danielabez.spellitforme.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import danielabez.spellitforme.databinding.FragmentRegistrationBinding
import danielabez.spellitforme.model.RegisteredUser
import danielabez.spellitforme.viewModel.RegisteredUserViewModel
import kotlinx.coroutines.runBlocking
import java.util.Timer
import java.util.TimerTask

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val registeredUserViewModel: RegisteredUserViewModel by activityViewModels()
    private var isMailFree: Boolean = false
    private var isUsernameFree: Boolean = false
    private var doPasswordsMatch: Boolean = false
    private val onBackPressedCallback : OnBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            registeredUserViewModel.comingBack.postValue(true)
            Thread.sleep(200)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registeredUserViewModel.checkMailInUse.observe(viewLifecycleOwner, Observer<RegisteredUser> { checkUserByMail ->
            isMailFree = checkUserByMail == null
            binding.tvRegisterEmailInUseWarning.isVisible = !isMailFree
        })

        registeredUserViewModel.checkUsernameInUse.observe(viewLifecycleOwner, Observer<RegisteredUser> { checkUserByUsername ->
            isUsernameFree = checkUserByUsername == null
            binding.tvRegisterUsernameInUseWarning.isVisible = !isUsernameFree
        })

        registeredUserViewModel.comingBack.observe(viewLifecycleOwner, Observer<Boolean> { readyToGoBack ->
            if(readyToGoBack == true){
                findNavController().popBackStack()
            }
        })

        binding.tietRegisterMail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if (!binding.tietRegisterMail.text.toString().isNullOrEmpty() &&
                    (binding.tietRegisterMail.text.toString().contains("@") && binding.tietRegisterMail.text.toString().contains("."))) {
                    registeredUserViewModel.isMailInUse(binding.tietRegisterMail.text.toString())
                }
            }
        })

        binding.tietRegisterUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if (!binding.tietRegisterUsername.text.toString().isNullOrEmpty()) {
                    registeredUserViewModel.isUsernameInUse(binding.tietRegisterUsername.text.toString())
                }
            }
        })

        binding.tietRegisterPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                verifyPasswords()
            }

        })

        binding.tietRegisterRepeatPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                verifyPasswords()
            }
        })

        initializeCheckbox()
        initializeRegisterButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun verifyPasswords() {
        if (!binding.tietRegisterPassword.text.toString().isNullOrEmpty() && !binding.tietRegisterRepeatPassword.text.toString().isNullOrEmpty()) {
            doPasswordsMatch = binding.tietRegisterPassword.text.toString().equals(binding.tietRegisterRepeatPassword.text.toString())
            binding.tvRegisterNonMatchingPasswordsWarning.isVisible = !doPasswordsMatch

            if (doPasswordsMatch) {
                //Nothing
            }
        }
    }

    private fun registerNewUser() {
        if(isMailFree && isUsernameFree && doPasswordsMatch){
            runBlocking {
                registeredUserViewModel.addRegisteredUser(RegisteredUser(
                    binding.tietRegisterMail.text.toString(),
                    binding.tietRegisterUsername.text.toString(),
                    binding.tietRegisterPassword.text.toString()
                ))
                registeredUserViewModel.comingBack.postValue(true)
                Thread.sleep(200)
            }
        } else {
            Toast.makeText(context, "One or more fields are wrong, try again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initializeCheckbox() {
        binding.ckbRegisterAcceptTerms.setOnCheckedChangeListener() { _, isChecked ->
            if (binding.ckbRegisterAcceptTerms.isChecked) {
                binding.btRegisterRegister.setEnabled(true)
            } else {
                binding.btRegisterRegister.setEnabled(false)
            }
        }
    }

    private fun initializeRegisterButton() {
        binding.btRegisterRegister.setOnClickListener() {
            registerNewUser()
        }
    }
}