package danielabez.spellitforme.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import danielabez.spellitforme.databinding.FragmentEquipmentSetBinding

class EquipmentSetFragment : Fragment() {
    private var _binding: FragmentEquipmentSetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEquipmentSetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btTest1.setOnClickListener(){
            val action = EquipmentSetFragmentDirections.actionEquipmentSetFragmentToGearSearchFragment("Headgear")
            findNavController().navigate(action)
        }
        binding.btTest2.setOnClickListener(){
            val action = EquipmentSetFragmentDirections.actionEquipmentSetFragmentToGearSearchFragment("Torso")
            findNavController().navigate(action)
        }
        binding.btTest3.setOnClickListener(){
            val action = EquipmentSetFragmentDirections.actionEquipmentSetFragmentToGearSearchFragment("Handwear")
            findNavController().navigate(action)
        }
        binding.btTest4.setOnClickListener(){
            val action = EquipmentSetFragmentDirections.actionEquipmentSetFragmentToGearSearchFragment("Belt")
            findNavController().navigate(action)
        }
        binding.btTest5.setOnClickListener(){
            val action = EquipmentSetFragmentDirections.actionEquipmentSetFragmentToGearSearchFragment("Footwear")
            findNavController().navigate(action)
        }
        binding.btTest6.setOnClickListener(){
            val action = EquipmentSetFragmentDirections.actionEquipmentSetFragmentToWeaponSearchFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}