package danielabez.spellitforme.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import danielabez.spellitforme.R
import danielabez.spellitforme.adapter.GearAdapter
import danielabez.spellitforme.adapter.WeaponAdapter
import danielabez.spellitforme.databinding.FragmentGearSearchBinding
import danielabez.spellitforme.databinding.FragmentLoginBinding
import danielabez.spellitforme.databinding.FragmentWeaponSearchBinding
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.model.Weapon
import danielabez.spellitforme.viewModel.GearViewModel
import danielabez.spellitforme.viewModel.WeaponViewModel

class WeaponSearchFragment : Fragment() {
    private var _binding: FragmentWeaponSearchBinding? = null
    private val binding get() = _binding!!
    private val weaponViewModel : WeaponViewModel by activityViewModels()
    lateinit var weaponAdapter: WeaponAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeaponSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        weaponViewModel.weaponListLiveData.observe(viewLifecycleOwner, Observer<List<Weapon>>{ list ->
            weaponAdapter.updateList(list)
        })
        initializeSearchView()
        initializeTypeSpinner()
        initializeRaritySpinner()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeRecyclerView(){
        weaponAdapter = WeaponAdapter()

        with(binding.rvWeapon){
            layoutManager = LinearLayoutManager(activity)
            adapter = weaponAdapter
        }
    }

    private fun initializeSearchView(){
        binding.svWeaponNameFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.svWeaponNameFilter.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var selectedType = binding.spWeaponSearchTypeFilter.selectedItem.toString()
                var selectedRarity = binding.spWeaponSearchRarityFilter.selectedItem.toString()
                var dbType : String
                var dbRarity : String

                if(selectedRarity != "All"){
                    dbRarity = obtainRarityFilter(selectedRarity)
                    if(selectedType != "All"){
                        dbType = obtainTypeFilter(selectedType)
                        weaponViewModel.getAllWeaponsByTypeAndRarityLike(dbType, dbRarity, binding.svWeaponNameFilter.query.toString())
                    }else{
                        weaponViewModel.getAllWeaponsByRarityLike(dbRarity, binding.svWeaponNameFilter.query.toString())
                    }
                }else{
                    if(selectedType != "All"){
                        dbType = obtainTypeFilter(selectedType)
                        weaponViewModel.getAllWeaponsByTypeLike(dbType, binding.svWeaponNameFilter.query.toString())
                    }else{
                        weaponViewModel.getAllWeaponsLike(binding.svWeaponNameFilter.query.toString())
                    }
                }
                return false
            }
        })
    }

    private fun initializeTypeSpinner() {
        ArrayAdapter.createFromResource(requireContext(), R.array.spSearchTypeFilter, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spWeaponSearchTypeFilter.adapter = adapter
            binding.spWeaponSearchTypeFilter.setSelection(0)

            //Con esto, se controlan los eventos activados a la hora de seleccionar distintos elementos del Spinner
            binding.spWeaponSearchTypeFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, v: View?, posicion: Int, id: Long) {
                        var writtenQuery = binding.svWeaponNameFilter.query.toString()
                        var selectedRarity = binding.spWeaponSearchRarityFilter.selectedItem.toString()
                        var dbRarity: String

                        if(writtenQuery != ""){
                            if(selectedRarity != "All"){
                                dbRarity = obtainRarityFilter(selectedRarity)
                                when(posicion){
                                    0 -> weaponViewModel.getAllWeaponsByRarityLike(dbRarity, writtenQuery)
                                    1 -> weaponViewModel.getAllWeaponsByTypeAndRarityLike("a_Sword", dbRarity, writtenQuery)
                                    2 -> weaponViewModel.getAllWeaponsByTypeAndRarityLike("b_Spear", dbRarity, writtenQuery)
                                }
                            } else{
                                when(posicion){
                                    0 -> weaponViewModel.getAllWeaponsLike(writtenQuery)
                                    1 -> weaponViewModel.getAllWeaponsByTypeLike("a_Sword", writtenQuery)
                                    2 -> weaponViewModel.getAllWeaponsByTypeLike( "b_Spear", writtenQuery)
                                }
                            }
                        }else{
                            if(selectedRarity != "All"){
                                dbRarity = obtainRarityFilter(selectedRarity)
                                when(posicion){
                                    0 -> weaponViewModel.getAllWeaponsByRarity(dbRarity)
                                    1 -> weaponViewModel.getAllWeaponsByTypeAndRarity("a_Sword", dbRarity)
                                    2 -> weaponViewModel.getAllWeaponsByTypeAndRarity("b_Spear", dbRarity)
                                }
                            }else{
                                when(posicion){
                                    0 -> weaponViewModel.getAllWeapons()
                                    1 -> weaponViewModel.getAllWeaponsByType("a_Sword")
                                    2 -> weaponViewModel.getAllWeaponsByType("b_Spear")
                                }
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

    private fun initializeRaritySpinner(){
        ArrayAdapter.createFromResource(requireContext(), R.array.spSearchRarityFilter, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spWeaponSearchRarityFilter.adapter = adapter
            binding.spWeaponSearchRarityFilter.setSelection(0)

            //Con esto, se controlan los eventos activados a la hora de seleccionar distintos elementos del Spinner
            binding.spWeaponSearchRarityFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, v: View?, posicion: Int, id: Long) {
                    var writtenQuery = binding.svWeaponNameFilter.query.toString()
                    var selectedType = binding.spWeaponSearchTypeFilter.selectedItem.toString()
                    var dbType : String

                    if(writtenQuery != ""){
                        if(selectedType != "All"){
                            dbType = obtainTypeFilter(selectedType)
                            when(posicion){
                                0 -> weaponViewModel.getAllWeaponsByTypeLike(dbType, writtenQuery)
                                1 -> weaponViewModel.getAllWeaponsByTypeAndRarityLike(dbType, "a_Common", writtenQuery)
                                2 -> weaponViewModel.getAllWeaponsByTypeAndRarityLike(dbType, "b_Uncommon", writtenQuery)
                                3 -> weaponViewModel.getAllWeaponsByTypeAndRarityLike(dbType, "c_Rare", writtenQuery)
                                4 -> weaponViewModel.getAllWeaponsByTypeAndRarityLike(dbType, "d_Mythical", writtenQuery)
                                5 -> weaponViewModel.getAllWeaponsByTypeAndRarityLike(dbType, "e_Legendary", writtenQuery)
                            }
                        } else{
                            when(posicion){
                                0 -> weaponViewModel.getAllWeaponsLike(writtenQuery)
                                1 -> weaponViewModel.getAllWeaponsByRarityLike("a_Common", writtenQuery)
                                2 -> weaponViewModel.getAllWeaponsByRarityLike( "b_Uncommon", writtenQuery)
                                3 -> weaponViewModel.getAllWeaponsByRarityLike( "c_Rare", writtenQuery)
                                4 -> weaponViewModel.getAllWeaponsByRarityLike("d_Mythical", writtenQuery)
                                5 -> weaponViewModel.getAllWeaponsByRarityLike( "e_Legendary", writtenQuery)
                            }
                        }
                    }else{
                        if(selectedType != "All"){
                            dbType = obtainTypeFilter(selectedType)
                            when(posicion){
                                0 -> weaponViewModel.getAllWeaponsByType(dbType)
                                1 -> weaponViewModel.getAllWeaponsByTypeAndRarity(dbType, "a_Common")
                                2 -> weaponViewModel.getAllWeaponsByTypeAndRarity(dbType, "b_Uncommon")
                                3 -> weaponViewModel.getAllWeaponsByTypeAndRarity(dbType, "c_Rare")
                                4 -> weaponViewModel.getAllWeaponsByTypeAndRarity(dbType, "d_Mythical")
                                5 -> weaponViewModel.getAllWeaponsByTypeAndRarity(dbType, "e_Legendary")
                            }
                        }else{
                            when(posicion){
                                0 -> weaponViewModel.getAllWeapons()
                                1 -> weaponViewModel.getAllWeaponsByRarity("a_Common")
                                2 -> weaponViewModel.getAllWeaponsByRarity("b_Uncommon")
                                3 -> weaponViewModel.getAllWeaponsByRarity("c_Rare")
                                4 -> weaponViewModel.getAllWeaponsByRarity("d_Mythical")
                                5 -> weaponViewModel.getAllWeaponsByRarity("e_Legendary")
                            }
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

    private fun obtainRarityFilter(pRarity : String) : String {
        val result = when(pRarity) {
            "Common" -> "a_Common"
            "Uncommon" -> "b_Common"
            "Rare" -> "c_Rare"
            "Mythical" -> "d_Mythical"
            else -> "e_Legendary"
        }
        return result
    }

    private fun obtainTypeFilter(pType: String) : String {
        var result = when (pType){
            "Sword" -> "a_Sword"
            else -> "b_Spear"
        }

        return result
    }
}