package danielabez.spellitforme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import danielabez.spellitforme.R
import danielabez.spellitforme.adapter.SkillAdapter
import danielabez.spellitforme.databinding.FragmentSkillSearchBinding
import danielabez.spellitforme.model.Skill
import danielabez.spellitforme.viewModel.SkillViewModel

class SkillSearchFragment : Fragment() {
    private var _binding: FragmentSkillSearchBinding? = null
    private val binding get() = _binding!!
    private val skillViewModel : SkillViewModel by activityViewModels()
    lateinit var skillAdapter: SkillAdapter
    private val argPositionToBeSetOn : SkillSearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSkillSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()

        skillViewModel.skillListLiveData.observe(viewLifecycleOwner, Observer<List<Skill>>{ list ->
            skillAdapter.updateList(list)
        })

        skillAdapter.onSkillClickListener = object : SkillAdapter.OnSkillClickListener {
            override fun onSkillClick(skill: Skill) {
                skillViewModel.updateChosenSkill(skill, argPositionToBeSetOn.positionToBeSetOn)
                findNavController().popBackStack()
            }
        }

        initializeSearchView()
        initializeTypeSpinner()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeRecyclerView(){
        skillAdapter = SkillAdapter()

        with(binding.rvSkills){
            layoutManager = LinearLayoutManager(activity)
            adapter = skillAdapter
        }
    }

    private fun initializeSearchView(){
        binding.svSkillNameFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.svSkillNameFilter.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var selectedTypePosition = binding.spSkillSearchTypeFilter.selectedItemPosition
                var selectedType = binding.spSkillSearchTypeFilter.selectedItem.toString()
                if(selectedTypePosition != null) {
                    when (selectedTypePosition) {
                        0 -> skillViewModel.getAllSkillsByNameLike(binding.svSkillNameFilter.query.toString())
                        else -> skillViewModel.getAllSkillsByTypeLikeAndNameLike(selectedType, binding.svSkillNameFilter.query.toString())
                    }
                }
                return false
            }
        })
    }

    private fun initializeTypeSpinner(){
        ArrayAdapter.createFromResource(requireContext(), R.array.spSkillSearchTypeFilter, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spSkillSearchTypeFilter.adapter = adapter
            binding.spSkillSearchTypeFilter.setSelection(0)

            //Con esto, se controlan los eventos activados a la hora de seleccionar distintos elementos del Spinner
            binding.spSkillSearchTypeFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, v: View?, posicion: Int, id: Long) {
                    var writtenQuery = binding.svSkillNameFilter.query.toString()
                    if(writtenQuery != null && writtenQuery != ""){
                        when(posicion){
                            0 -> skillViewModel.getAllSkillsByNameLike(writtenQuery)
                            else -> skillViewModel.getAllSkillsByTypeLikeAndNameLike(binding.spSkillSearchTypeFilter.selectedItem.toString(), writtenQuery)
                        }
                    }
                    else{
                        when(posicion){
                            0 -> skillViewModel.getAllSkills()
                            else -> skillViewModel.getAllSkillsByTypeLike(binding.spSkillSearchTypeFilter.selectedItem.toString())
                        }
                    }
                }

                //Evento lanzado cuando ningún elemento se encuentra seleccionado en el Spinner
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //Empty
                }
            }
        }
    }
}