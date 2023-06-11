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
import danielabez.spellitforme.adapter.GearAdapter
import danielabez.spellitforme.databinding.FragmentGearSearchBinding
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.viewModel.GearViewModel

class GearSearchFragment : Fragment() {
    private var _binding: FragmentGearSearchBinding? = null
    private val binding get() = _binding!!
    private val gearViewModel : GearViewModel by activityViewModels()
    lateinit var gearAdapter: GearAdapter
    private val argGearType: GearSearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGearSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        gearViewModel.gearListLiveData.observe(viewLifecycleOwner, Observer<List<Gear>>{ list ->
            gearAdapter.updateList(list)
        })

        gearAdapter.onGearClickListener = object : GearAdapter.OnGearClickListener {
            override fun onGearClick(gear: Gear?) {
                gearViewModel.updateChosenGear(gear!!)
                findNavController().popBackStack()
            }
        }

        initializeSearchView()
        initializeRaritySpinner()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeRecyclerView(){
        gearAdapter = GearAdapter()

        with(binding.rvGear){
            layoutManager = LinearLayoutManager(activity)
            adapter = gearAdapter
        }
    }

    private fun initializeSearchView(){
        binding.svGearNameFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.svGearNameFilter.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var selectedRarity = binding.spGearSearchRarityFilter.selectedItemPosition
                if(selectedRarity != null && selectedRarity != 0){
                    when(selectedRarity){
                        1 -> gearViewModel.getAllGearByTypeAndRarityLike(argGearType.gearType, "a_Common", binding.svGearNameFilter.query.toString())
                        2 -> gearViewModel.getAllGearByTypeAndRarityLike(argGearType.gearType, "b_Uncommon", binding.svGearNameFilter.query.toString())
                        3 -> gearViewModel.getAllGearByTypeAndRarityLike(argGearType.gearType, "c_Rare", binding.svGearNameFilter.query.toString())
                        4 -> gearViewModel.getAllGearByTypeAndRarityLike(argGearType.gearType, "d_Mythical", binding.svGearNameFilter.query.toString())
                        5 -> gearViewModel.getAllGearByTypeAndRarityLike(argGearType.gearType, "e_Legendary", binding.svGearNameFilter.query.toString())
                    }
                }else{
                    gearViewModel.getAllGearByTypeLike(argGearType.gearType, binding.svGearNameFilter.query.toString())
                }
                return false
            }
        })
    }

    private fun initializeRaritySpinner(){
        ArrayAdapter.createFromResource(requireContext(), R.array.spGearSearchRarityFilter, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spGearSearchRarityFilter.adapter = adapter
            binding.spGearSearchRarityFilter.setSelection(0)

            //Con esto, se controlan los eventos activados a la hora de seleccionar distintos elementos del Spinner
            binding.spGearSearchRarityFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, v: View?, posicion: Int, id: Long) {
                    var writtenQuery = binding.svGearNameFilter.query.toString()
                    if(writtenQuery != null && writtenQuery != ""){
                        when(posicion){
                            0 -> gearViewModel.getAllGearByTypeLike(argGearType.gearType, writtenQuery)
                            1 -> gearViewModel.getAllGearByTypeAndRarityLike(argGearType.gearType, "a_Common", writtenQuery)
                            2 -> gearViewModel.getAllGearByTypeAndRarityLike(argGearType.gearType, "b_Uncommon", writtenQuery)
                            3 -> gearViewModel.getAllGearByTypeAndRarityLike(argGearType.gearType, "c_Rare", writtenQuery)
                            4 -> gearViewModel.getAllGearByTypeAndRarityLike(argGearType.gearType, "d_Mythical", writtenQuery)
                            5 -> gearViewModel.getAllGearByTypeAndRarityLike(argGearType.gearType, "e_Legendary", writtenQuery)
                        }
                    }
                    else{
                        when(posicion){
                            0 -> gearViewModel.getAllGearByType(argGearType.gearType)
                            1 -> gearViewModel.getAllGearByTypeAndRarity(argGearType.gearType, "a_Common")
                            2 -> gearViewModel.getAllGearByTypeAndRarity(argGearType.gearType, "b_Uncommon")
                            3 -> gearViewModel.getAllGearByTypeAndRarity(argGearType.gearType, "c_Rare")
                            4 -> gearViewModel.getAllGearByTypeAndRarity(argGearType.gearType, "d_Mythical")
                            5 -> gearViewModel.getAllGearByTypeAndRarity(argGearType.gearType, "e_Legendary")
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