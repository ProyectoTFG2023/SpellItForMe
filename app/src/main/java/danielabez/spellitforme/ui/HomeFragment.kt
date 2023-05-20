package danielabez.spellitforme.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.FragmentHomeBinding
import danielabez.spellitforme.viewModel.GearViewModel
import danielabez.spellitforme.viewModel.RegisteredUserViewModel
import danielabez.spellitforme.viewModel.SkillViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val registeredUserViewModel : RegisteredUserViewModel by activityViewModels()
    //TODO: NO FUNCIONAL, PENDIENTE DE REVISIÓN
    //Con la siguiente variable, controlaremos el funcionamiento del botón de vuelta atrás de Android, y podremos decidir la función que realizará
    private val onBackPressedCallback : OnBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            confirmSignOff()
            Log.d("SpellItForMe_Debug", " -> " + registeredUserViewModel.checkRegisteredUser.value.toString())
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener(){
            val action = HomeFragmentDirections.actionHomeFragmentToEquipmentSetFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //TODO: NO FUNCIONAL, PENDIENTE DE REVISIÓN
    fun confirmSignOff() {
        AlertDialog.Builder(activity as Context)
            .setTitle("No Funcional")
            .setMessage("Intento de volver a login")
            .setPositiveButton(android.R.string.ok) { v, _ ->
                registeredUserViewModel.emptyCheckRegisteredUser()
            }
            .setNegativeButton(android.R.string.cancel) { v, _ ->
                v.dismiss()
            }
            .create()
            .show()
    }
}