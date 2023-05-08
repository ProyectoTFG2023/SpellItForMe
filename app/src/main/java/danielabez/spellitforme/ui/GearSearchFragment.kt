package danielabez.spellitforme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import danielabez.spellitforme.R
import danielabez.spellitforme.adapter.GearAdapter
import danielabez.spellitforme.databinding.FragmentGearSearchBinding
import danielabez.spellitforme.databinding.FragmentLoginBinding
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.viewModel.GearViewModel

class GearSearchFragment : Fragment() {
    private var _binding: FragmentGearSearchBinding? = null
    private val binding get() = _binding!!
    private val gearViewModel : GearViewModel by activityViewModels()
    lateinit var gearAdapter: GearAdapter

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
}