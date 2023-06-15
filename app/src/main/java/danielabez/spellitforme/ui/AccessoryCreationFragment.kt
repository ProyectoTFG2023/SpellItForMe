
package danielabez.spellitforme.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.FragmentAccessoryCreationBinding
import danielabez.spellitforme.model.Accessory
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.model.Skill
import danielabez.spellitforme.viewModel.AccessoryViewModel
import danielabez.spellitforme.viewModel.RegisteredUserViewModel
import danielabez.spellitforme.viewModel.SkillViewModel

class AccessoryCreationFragment : Fragment() {
    private var _binding: FragmentAccessoryCreationBinding? = null
    private val binding get() = _binding!!
    private val onBackPressedCallback : OnBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            showExitWithoutSavingWarningDialog()
        }
    }
    private val registeredUserViewModel : RegisteredUserViewModel by activityViewModels()
    private val skillViewModel : SkillViewModel by activityViewModels()
    private val accessoryViewModel : AccessoryViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccessoryCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeSpinners()
        initializeFloatingActionButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeSpinners(){
        initializeTypeSpinner()
        initializeRaritySpinner()
        initializeSlotNumberSpinner()
        initializeSkillNumberSpinner()
        initializeSkillsSpinners()
    }

    private fun initializeTypeSpinner(){
        ArrayAdapter.createFromResource(requireContext(), R.array.spAccessoryCreationType, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spAccessoryCreationType.adapter = adapter
            binding.spAccessoryCreationType.setSelection(0)
        }
    }

    private fun initializeRaritySpinner(){
        ArrayAdapter.createFromResource(requireContext(), R.array.spAccessoryCreationRarity, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spAccessoryCreationRarity.adapter = adapter
            binding.spAccessoryCreationRarity.setSelection(0)
        }
    }

    private fun initializeSlotNumberSpinner(){
        ArrayAdapter.createFromResource(requireContext(), R.array.spAccessoryCreationNumberOfSlots, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spAccessoryCreationNumberOfSlots.adapter = adapter
            binding.spAccessoryCreationNumberOfSlots.setSelection(0)
        }
    }

    private fun initializeSkillNumberSpinner(){
        ArrayAdapter.createFromResource(requireContext(), R.array.spAccessoryCreationNumberOfSkills, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spAccessoryCreationNumberOfSkills.adapter = adapter
            binding.spAccessoryCreationNumberOfSkills.setSelection(0)

            binding.spAccessoryCreationNumberOfSkills.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, v: View?, posicion: Int, id: Long) {
                    when(posicion) {
                        1 -> {
                            binding.spAccessoryCreationFirstSkill.isEnabled = true
                            binding.spAccessoryCreationSecondSkill.isEnabled = false
                        }
                        2 -> {
                            binding.spAccessoryCreationFirstSkill.isEnabled = true
                            binding.spAccessoryCreationSecondSkill.isEnabled = true
                        }
                        else -> {
                            binding.spAccessoryCreationFirstSkill.isEnabled = false
                            binding.spAccessoryCreationSecondSkill.isEnabled = false
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //Empty
                }
            }
        }
    }

    private fun initializeSkillsSpinners(){
        ArrayAdapter.createFromResource(requireContext(), R.array.spAccessoryCreationSkills, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spAccessoryCreationFirstSkill.adapter = adapter
            binding.spAccessoryCreationFirstSkill.setSelection(0)
            binding.spAccessoryCreationFirstSkill.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, v: View?, posicion: Int, id: Long) {
                    skillViewModel.getSkillByName(binding.spAccessoryCreationFirstSkill.selectedItem.toString(), true)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //Empty
                }
            }

            binding.spAccessoryCreationSecondSkill.adapter = adapter
            binding.spAccessoryCreationSecondSkill.setSelection(0)
            binding.spAccessoryCreationSecondSkill.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, v: View?, posicion: Int, id: Long) {
                    skillViewModel.getSkillByName(binding.spAccessoryCreationSecondSkill.selectedItem.toString(), false)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //Empty
                }
            }
        }
    }

    private fun initializeFloatingActionButton(){
        binding.fabAccessoryCreationSaveAndExit.setOnClickListener(){
            val skillList : MutableList<Skill> = mutableListOf()

            if(binding.spAccessoryCreationNumberOfSkills.selectedItem.toString() != "0"){
                when(binding.spAccessoryCreationNumberOfSkills.selectedItem.toString()){
                    "1" -> {
                        if(skillViewModel.firstSkillForAccessoryCreation.value != null){
                            skillList.add(skillViewModel.firstSkillForAccessoryCreation.value!!)
                        }
                    }
                    "2" -> {
                        if(skillViewModel.firstSkillForAccessoryCreation.value != null){
                            skillList.add(skillViewModel.firstSkillForAccessoryCreation.value!!)
                        }
                        if(skillViewModel.secondSkillForAccessoryCreation.value != null){
                            skillList.add(skillViewModel.secondSkillForAccessoryCreation.value!!)
                        }
                    }
                }
            }

            accessoryViewModel.addAccessory(
                Accessory(
                    registeredUserViewModel.checkRegisteredUser.value?.id,
                    Gear(if(binding.tietAccessoryCreationName.text.toString().isNotEmpty()) binding.tietAccessoryCreationName.text.toString() else "New Accessory",
                        "Accessory",
                        obtainRarityFilter(binding.spAccessoryCreationRarity.selectedItem.toString()),
                        if(binding.tietAccessoryCreationPhysDefense.text.toString().isNotEmpty()) binding.tietAccessoryCreationPhysDefense.text.toString().toLong() else 0L,
                        if(binding.tietAccessoryCreationFireDefense.text.toString().isNotEmpty()) binding.tietAccessoryCreationFireDefense.text.toString().toLong() else 0L,
                        if(binding.tietAccessoryCreationIceDefense.text.toString().isNotEmpty()) binding.tietAccessoryCreationIceDefense.text.toString().toLong() else 0L,
                        if(binding.tietAccessoryCreationThunderDefense.text.toString().isNotEmpty()) binding.tietAccessoryCreationThunderDefense.text.toString().toLong() else 0L,
                        skillList,
                        binding.spAccessoryCreationNumberOfSlots.selectedItem.toString().toInt()
                    ),
                    obtainTypeFilter(binding.spAccessoryCreationType.selectedItem.toString())
                )
            )
            findNavController().popBackStack()
        }
    }

    private fun obtainTypeFilter(pType: String) : String {
        var result = when (pType){
            "Necklace" -> "a_Necklace"
            else -> "b_Ring"
        }
        return result
    }

    private fun obtainRarityFilter(pRarity : String) : String {
        val result = when(pRarity) {
            "Common" -> "a_Common"
            "Uncommon" -> "b_Uncommon"
            "Rare" -> "c_Rare"
            "Mythical" -> "d_Mythical"
            else -> "e_Legendary"
        }
        return result
    }

    private fun showExitWithoutSavingWarningDialog(){
        AlertDialog.Builder(activity as Context)
            .setTitle(getString(R.string.accessoryCreationExitWithoutSavingWarningDialogTitle))
            .setMessage(getString(R.string.accessoryCreationExitWithoutSavingWarningDialogMessage))
            .setPositiveButton(getString(R.string.accessoryCreationExitWithoutSavingWarningDialogConfirm)) { v, _ ->
                findNavController().popBackStack()
                v.dismiss()
            }
            .setNegativeButton(getString(R.string.accessoryCreationExitWithoutSavingWarningDialogCancel)){ v, _ ->
                v.dismiss()
            }
            .create()
            .show()
    }
}

