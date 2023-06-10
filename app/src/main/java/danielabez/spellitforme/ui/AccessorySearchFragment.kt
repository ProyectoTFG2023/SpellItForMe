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
import danielabez.spellitforme.adapter.AccessoryAdapter
import danielabez.spellitforme.adapter.WeaponAdapter
import danielabez.spellitforme.databinding.FragmentAccessorySearchBinding
import danielabez.spellitforme.model.Accessory
import danielabez.spellitforme.model.Weapon
import danielabez.spellitforme.viewModel.AccessoryViewModel
import danielabez.spellitforme.viewModel.RegisteredUserViewModel


class AccessorySearchFragment : Fragment() {
    private var _binding: FragmentAccessorySearchBinding? = null
    private val binding get() = _binding!!
    private val registeredUserViewModel: RegisteredUserViewModel by activityViewModels()
    private val accessoryViewModel: AccessoryViewModel by activityViewModels()
    lateinit var accessoryAdapter: AccessoryAdapter
    private val argPositionToBeSetOn : AccessorySearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccessorySearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()

        accessoryAdapter.onAccessoryClickListener = object : AccessoryAdapter.OnAccessoryClickListener {
            override fun onAccessoryClick(accessory: Accessory?) {
                accessoryViewModel.updateChosenAccessory(accessory!!, argPositionToBeSetOn.positionToBeSetOn)
                findNavController().popBackStack()
            }
        }

        accessoryViewModel.accessoryListLiveData.observe(viewLifecycleOwner, Observer<List<Accessory>>{ list ->
            accessoryAdapter.updateList(list)
        })

        binding.fabCreateAccessory.setOnClickListener(){
            val action = AccessorySearchFragmentDirections.actionAccessorySearchFragmentToAccessoryCreationFragment()
            findNavController().navigate(action)
        }

        registeredUserViewModel.getUserWithAllGeneralAndOwnedAccesories(accessoryViewModel.accessoryListLiveData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeRecyclerView(){
        accessoryAdapter = AccessoryAdapter()

        with(binding.rvAccessory){
            layoutManager = LinearLayoutManager(activity)
            adapter = accessoryAdapter
        }
    }
}