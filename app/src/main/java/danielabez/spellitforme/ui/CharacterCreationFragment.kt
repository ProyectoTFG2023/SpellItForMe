
package danielabez.spellitforme.ui


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.FragmentAccessoryCreationBinding
import danielabez.spellitforme.databinding.FragmentCharacterCreationBinding
import danielabez.spellitforme.model.CharacterSet
import danielabez.spellitforme.model.CharacterStats
import danielabez.spellitforme.tools.IconHelper
import danielabez.spellitforme.viewModel.RegisteredUserViewModel

class CharacterCreationFragment : Fragment() {
    private var _binding: FragmentCharacterCreationBinding? = null
    private val binding get() = _binding!!
    private val onBackPressedCallback : OnBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            showExitWithoutSavingWarningDialog()
        }
    }
    private val registeredUserViewModel : RegisteredUserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRoleSpinner()
        initializeAttributeFields()
        initializeFloatingActionButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeRoleSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(), R.array.spCharacterCreationRole, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spCharacterCreationRole.adapter = adapter
            binding.spCharacterCreationRole.setSelection(0)
            binding.spCharacterCreationRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, v: View?, posicion: Int, id: Long) {
                    IconHelper.setRoleIcon(binding.ivCharacterCreationRole, binding.spCharacterCreationRole.selectedItem.toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //Empty
                }
            }
        }
    }

    private fun initializeAttributeFields(){
        binding.tietCharacterCreationHealth.setText("100")
        binding.tietCharacterCreationSpiritum.setText("100")
        binding.tietCharacterCreationStrength.setText("50")
        binding.tietCharacterCreationCunning.setText("50")
        binding.tietCharacterCreationAttunement.setText("50")
    }

    private fun initializeFloatingActionButton(){
        val loggedUserId = registeredUserViewModel.checkRegisteredUser.value?.id

        binding.fabCharacterCreationToEditor.setOnClickListener(){
            if(areAllAttributeFieldsFilled() == true){
                val action = CharacterCreationFragmentDirections.actionCharacterCreationFragmentToEquipmentSetFragment(
                    true,
                    CharacterSet(
                        loggedUserId!!,
                        if(binding.tietCharacterCreationName.text?.isNotEmpty() == true) binding.tietCharacterCreationName.text.toString() else "Somebody",
                        binding.spCharacterCreationRole.selectedItem.toString(),
                        CharacterStats(
                            binding.tietCharacterCreationHealth.text.toString().toLong(),
                            binding.tietCharacterCreationSpiritum.text.toString().toLong(),
                            binding.tietCharacterCreationStrength.text.toString().toLong(),
                            binding.tietCharacterCreationAttunement.text.toString().toLong(),
                            binding.tietCharacterCreationCunning.text.toString().toLong(),
                        )
                    )
                )
                findNavController().navigate(action)
            }else{
                Toast.makeText(context, getString(R.string.emptyFieldsWarning), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun areAllAttributeFieldsFilled() : Boolean? {
        return (binding.tietCharacterCreationHealth.text?.isNotEmpty() == true
                && binding.tietCharacterCreationSpiritum.text?.isNotEmpty() == true
                && binding.tietCharacterCreationStrength.text?.isNotEmpty() == true
                && binding.tietCharacterCreationCunning.text?.isNotEmpty() == true
                && binding.tietCharacterCreationAttunement.text?.isNotEmpty() == true)
    }

    private fun showExitWithoutSavingWarningDialog(){
        AlertDialog.Builder(activity as Context)
            .setTitle(getString(R.string.characterCreationExitWithoutSavingWarningDialogTitle))
            .setMessage(getString(R.string.characterCreationExitWithoutSavingWarningDialogMessage))
            .setPositiveButton(getString(R.string.characterCreationExitWithoutSavingWarningDialogConfirm)) { v, _ ->
                findNavController().popBackStack()
                v.dismiss()
            }
            .setNegativeButton(getString(R.string.characterCreationExitWithoutSavingWarningDialogCancel)){ v, _ ->
                v.dismiss()
            }
            .create()
            .show()
    }
}