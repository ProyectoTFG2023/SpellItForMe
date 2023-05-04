package danielabez.spellitforme.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.FragmentHomeBinding
import danielabez.spellitforme.databinding.FragmentRegistrationBinding
import danielabez.spellitforme.viewModel.GearViewModel
import danielabez.spellitforme.viewModel.SkillViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val gearViewModel: GearViewModel by activityViewModels()
    private val skillViewModel: SkillViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btTest.setOnClickListener(){
            binding.textView.setText(skillViewModel.getAllSkills().toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}