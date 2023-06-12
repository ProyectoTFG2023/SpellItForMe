package danielabez.spellitforme.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import danielabez.spellitforme.R
import danielabez.spellitforme.adapter.CharacterSetAdapter
import danielabez.spellitforme.databinding.FragmentHomeBinding
import danielabez.spellitforme.model.CharacterSet
import danielabez.spellitforme.viewModel.CharacterSetViewModel
import danielabez.spellitforme.viewModel.RegisteredUserViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val registeredUserViewModel : RegisteredUserViewModel by activityViewModels()
    private val characterSetViewModel : CharacterSetViewModel by activityViewModels()
    private lateinit var characterSetAdapter: CharacterSetAdapter
    //Con la siguiente variable, controlaremos el funcionamiento del botón de vuelta atrás de Android, y podremos decidir la función que realizará
    private val onBackPressedCallback : OnBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            confirmSignOff()
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
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.home_fragment_label, registeredUserViewModel.checkRegisteredUser.value?.username)

        initializeRecyclerView()
        initializeFloatingActionButton()

        characterSetAdapter.onCharacterSetClickListener = object : CharacterSetAdapter.OnCharacterSetClickListener {
            override fun onCharacterSetClick(characterSet: CharacterSet?) {
                val action = HomeFragmentDirections.actionHomeFragmentToEquipmentSetFragment(false, characterSet!!)
                findNavController().navigate(action)
            }

            override fun onCharacterSetClickRemove(characterSet: CharacterSet?) {
                deleteWarningDialog(characterSet)
            }
        }

        characterSetViewModel.characterSetListLiveData.observe(viewLifecycleOwner, Observer<List<CharacterSet>>{ list ->
            characterSetAdapter.updateList(list)
        })

        registeredUserViewModel.getRegisteredUserWithAllOwnedCharacterSets(characterSetViewModel.characterSetListLiveData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeRecyclerView(){
        characterSetAdapter = CharacterSetAdapter()

        with(binding.rvHomeUserSets){
            layoutManager = LinearLayoutManager(activity)
            adapter = characterSetAdapter
        }
    }

    private fun initializeFloatingActionButton(){
        binding.fabHomeCreateNewCharacter.setOnClickListener(){
            val action = HomeFragmentDirections.actionHomeFragmentToCharacterCreationFragment()
            findNavController().navigate(action)
        }
    }

    private fun deleteWarningDialog(characterSet: CharacterSet?){
        AlertDialog.Builder(activity as Context)
            .setTitle(getString(R.string.characterSetDeleteWarningDialogTitle))
            .setMessage(getString(R.string.characterSetDeleteWarningDialogMessage, characterSet!!.name))
            .setPositiveButton(getString(R.string.characterSetDeleteWarningDialogConfirm)) { v, _ ->
                characterSetViewModel.deleteCharacterSet(characterSet)
                Thread.sleep(100)
                registeredUserViewModel.getRegisteredUserWithAllOwnedCharacterSets(characterSetViewModel.characterSetListLiveData)
                v.dismiss()
            }
            .setNegativeButton(getString(R.string.characterSetDeleteWarningDialogCancel)) { v, _ ->
                v.dismiss()
            }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun confirmSignOff() {
        AlertDialog.Builder(activity as Context)
            .setTitle(getString(R.string.signOffConfirmDialogTitle))
            .setMessage(getString(R.string.signOffConfirmDialogMessage))
            .setPositiveButton(R.string.signOffConfirmDialogueConfirm) { v, _ ->
                registeredUserViewModel.emptyCheckRegisteredUser()
                Thread.sleep(100)
                findNavController().popBackStack()
                v.dismiss()
            }
            .setNegativeButton(R.string.signOffConfirmDialogueCancel) { v, _ ->
                v.dismiss()
            }
            .create()
            .show()
    }
}