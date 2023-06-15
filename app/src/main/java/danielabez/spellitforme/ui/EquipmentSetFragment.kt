package danielabez.spellitforme.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import danielabez.spellitforme.R
import danielabez.spellitforme.adapter.AccessoryOnSetAdapter
import danielabez.spellitforme.adapter.GearOnSetAdapter
import danielabez.spellitforme.adapter.SkillOnSetAdapter
import danielabez.spellitforme.adapter.SlotOnSetAdapter
import danielabez.spellitforme.adapter.WeaponOnSetAdapter
import danielabez.spellitforme.databinding.FragmentEquipmentSetBinding
import danielabez.spellitforme.model.Accessory
import danielabez.spellitforme.model.CharacterSet
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.model.Skill
import danielabez.spellitforme.model.SkillOnSet
import danielabez.spellitforme.model.Weapon
import danielabez.spellitforme.tools.IconHelper
import danielabez.spellitforme.viewModel.AccessoryViewModel
import danielabez.spellitforme.viewModel.CharacterSetViewModel
import danielabez.spellitforme.viewModel.GearViewModel
import danielabez.spellitforme.viewModel.RegisteredUserViewModel
import danielabez.spellitforme.viewModel.SkillViewModel
import danielabez.spellitforme.viewModel.WeaponViewModel

class EquipmentSetFragment : Fragment() {
    private var _binding: FragmentEquipmentSetBinding? = null
    private val binding get() = _binding!!
    private val onBackPressedCallback : OnBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            showExitWithoutSavingWarningDialog()
        }
    }
    private val registeredUserViewModel : RegisteredUserViewModel by activityViewModels()
    private val characterSetViewModel : CharacterSetViewModel by activityViewModels()
    private val weaponViewModel : WeaponViewModel by activityViewModels()
    private val gearViewModel : GearViewModel by activityViewModels()
    private val accessoryViewModel : AccessoryViewModel by activityViewModels()
    private val skillViewModel : SkillViewModel by activityViewModels()
    private var weaponOnSetAdapter = WeaponOnSetAdapter()
    private var gearOnSetAdapter = GearOnSetAdapter()
    private var accessoryOnSetAdapter = AccessoryOnSetAdapter()
    private var slotOnSetAdapter = SlotOnSetAdapter()
    private var skillOnSetAdapter = SkillOnSetAdapter()

    private val currentWeapon : MutableLiveData<Weapon?> by lazy {
        MutableLiveData<Weapon?>()
    }
    private val currentGearList : MutableLiveData<List<Gear?>> by lazy {
        MutableLiveData<List<Gear?>>()
    }
    private val currentAccessoryList : MutableLiveData<List<Accessory?>> by lazy {
        MutableLiveData<List<Accessory?>>()
    }
    private val currentSlottedSkills : MutableLiveData<List<Skill?>> by lazy {
        MutableLiveData<List<Skill?>>()
    }

    private val equipmentSetFragmentArgs : EquipmentSetFragmentArgs by navArgs()
    private lateinit var currentCharacterSet : CharacterSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
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
        currentCharacterSet = equipmentSetFragmentArgs.characterSet

        weaponViewModel.chosenWeapon.postValue(null)
        gearViewModel.chosenGear.postValue(null)
        accessoryViewModel.chosenAccessory.postValue(null)
        skillViewModel.chosenSkill.postValue(null)

        //Inicialización de los RecyclerViews y el comportamiento de los eventos de clicks en los elementos de los mismos
        initializeRecyclerViews()
        initializeFloatingActionButton()

        weaponOnSetAdapter.onWeaponOnSetClickListener = object : WeaponOnSetAdapter.OnWeaponOnSetClickListener {
            override fun onWeaponOnSetImageClick() {
                val action = EquipmentSetFragmentDirections.actionEquipmentSetFragmentToWeaponSearchFragment()
                findNavController().navigate(action)
            }

            override fun onWeaponOnSetRemoveClick(weaponPosition: Int) {
                currentCharacterSet.equippedWeapon = null
                currentWeapon.postValue(currentCharacterSet.equippedWeapon)
            }
        }

        gearOnSetAdapter.onGearOnSetClickListener = object : GearOnSetAdapter.OnGearOnSetClickListener  {
            override fun onGearOnSetImageClick(gear: Gear?, gearPosition: Int) {
                if(gear != null){
                    val action = EquipmentSetFragmentDirections.actionEquipmentSetFragmentToGearSearchFragment(gear.type)
                    findNavController().navigate(action)
                }
                else{
                    val switcharoo = when(gearPosition){
                        0 -> "Headgear"
                        1 -> "Torso"
                        2 -> "Handwear"
                        3 -> "Belt"
                        else -> "Footwear"
                    }

                    val action = EquipmentSetFragmentDirections.actionEquipmentSetFragmentToGearSearchFragment(switcharoo)
                    findNavController().navigate(action)
                }
            }

            override fun onGearOnSetRemoveClick(gearPosition: Int) {
                currentCharacterSet.equipmentPieces[gearPosition] = null
                currentGearList.postValue(currentCharacterSet.equipmentPieces)
                obtainCurrentSlots()
            }
        }

        accessoryOnSetAdapter.onAccessoryOnSetClickListener = object : AccessoryOnSetAdapter.OnAccessoryOnSetClickListener{
            override fun onAccessoryOnSetImageClick(accessory: Accessory?, accessoryPosition: Int) {
                val action = EquipmentSetFragmentDirections.actionEquipmentSetFragmentToAccessorySearchFragment(accessoryPosition)
                findNavController().navigate(action)
            }

            override fun onAccessoryOnSetRemoveClick(accessoryPosition: Int) {
                currentCharacterSet.equippedAccessories[accessoryPosition] = null
                currentAccessoryList.postValue(currentCharacterSet.equippedAccessories)
            }
        }

        slotOnSetAdapter.onSlotOnSetClickListener = object : SlotOnSetAdapter.OnSlotOnSetClickListener {
            override fun onSlotOnSetClick(position: Int) {
                val action = EquipmentSetFragmentDirections.actionEquipmentSetFragmentToSkillSearchFragment(position)
                findNavController().navigate(action)
            }

            override fun onSlotOnSetRemoveClick(position: Int) {
                currentCharacterSet.slottedSkills[position] = null
                currentSlottedSkills.postValue(currentCharacterSet.slottedSkills)
            }
        }

        skillOnSetAdapter.onSkillOnSetClickListener = object : SkillOnSetAdapter.OnSkillOnSetClickListener {
            override fun onSkillOnSetImageClick(skillOnSet: SkillOnSet?) {
                showSkillInfo(skillOnSet!!)
            }
        }


        //Declaración de observadores para mostrar los cambios en tiempo real realizados sobre el equipamiento
        weaponViewModel.chosenWeapon.observe(viewLifecycleOwner, Observer<Weapon?>{ weapon ->
            if(weapon != null){
                currentCharacterSet.equippedWeapon = weapon
                currentWeapon.postValue(currentCharacterSet.equippedWeapon)
            }
        })

        gearViewModel.chosenGear.observe(viewLifecycleOwner, Observer<Gear?>{ gear ->
            if(gear != null){
                when(gear.type){
                    "Headgear" -> currentCharacterSet.equipmentPieces[0] = gear
                    "Torso" -> currentCharacterSet.equipmentPieces[1] = gear
                    "Handwear" -> currentCharacterSet.equipmentPieces[2] = gear
                    "Belt" -> currentCharacterSet.equipmentPieces[3] = gear
                    else -> currentCharacterSet.equipmentPieces[4] = gear
                }
                currentGearList.postValue(currentCharacterSet.equipmentPieces)
            }
        })

        accessoryViewModel.chosenAccessory.observe(viewLifecycleOwner, Observer<Accessory>{accessory ->
            if(accessory != null){
                currentCharacterSet.equippedAccessories[accessoryViewModel.chosenPosition.value!!] = accessory
                currentAccessoryList.postValue(currentCharacterSet.equippedAccessories)
            }
        })

        skillViewModel.chosenSkill.observe(viewLifecycleOwner, Observer<Skill?> { skill ->
            if(skill != null){
                currentCharacterSet.slottedSkills[skillViewModel.chosenPosition.value!!] = skill
                currentSlottedSkills.postValue(currentCharacterSet.slottedSkills)
            }
        })

        currentWeapon.observe(viewLifecycleOwner, Observer<Weapon?>{weapon ->
            weaponOnSetAdapter.updateWeaponList(listOf(weapon))
            recalculateData()
        })

        currentGearList.observe(viewLifecycleOwner, Observer<List<Gear?>>{ list ->
            gearOnSetAdapter.updateList(list)
            recalculateData()
        })

        currentAccessoryList.observe(viewLifecycleOwner, Observer<List<Accessory?>>{ list ->
            accessoryOnSetAdapter.updateList(list)
            recalculateData()
        })

        currentSlottedSkills.observe(viewLifecycleOwner, Observer<List<Skill?>>{ list ->
            currentCharacterSet.slottedSkills = list as MutableList<Skill?>
            slotOnSetAdapter.updateList(list)
            recalculateData()
        })

        binding.tvEquipmentCharacterInfoName.text = currentCharacterSet.name
        binding.tvEquipmentCharacterInfoRole.text = currentCharacterSet.role
        IconHelper.setRoleIcon(binding.ivEquipmentCharacterRole, currentCharacterSet.role)

        //Mensajes enviados a los adaptadores para mostrar los datos correspondientes
        weaponOnSetAdapter.updateWeaponList(listOf(currentCharacterSet.equippedWeapon))
        gearOnSetAdapter.updateList(currentCharacterSet.equipmentPieces)
        accessoryOnSetAdapter.updateList(currentCharacterSet.equippedAccessories)
        slotOnSetAdapter.updateList(currentCharacterSet.slottedSkills)
        skillOnSetAdapter.updateList(listOf())
        calculateAttributes()
        calculateOffensiveStats()
        calculateDefensiveStats()
        obtainCurrentSkills()
        applySkills()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeRecyclerViews(){
        with(binding.rvEquipmentWeapon){
            layoutManager = LinearLayoutManager(activity)
            adapter = weaponOnSetAdapter
        }

        with(binding.rvEquipmentGear){
            layoutManager = LinearLayoutManager(activity)
            adapter = gearOnSetAdapter
        }

        with(binding.rvEquipmentAccessories){
            layoutManager = LinearLayoutManager(activity)
            adapter = accessoryOnSetAdapter
        }

        with(binding.rvEquipmentSlots){
            layoutManager = LinearLayoutManager(activity)
            adapter = slotOnSetAdapter
        }

        with(binding.rvActualSkills){
            layoutManager = LinearLayoutManager(activity)
            adapter = skillOnSetAdapter
        }
    }

    private fun initializeFloatingActionButton(){
        binding.fabEquipmentSetSaveAndExit.setOnClickListener {
            showSaveAndExitDialog(currentCharacterSet)
        }
    }

    private fun recalculateData()  {
        calculateAttributes()
        calculateOffensiveStats()
        calculateDefensiveStats()
        obtainCurrentSlots()
        obtainCurrentSkills()
        applySkills()
    }

    private fun calculateAttributes(){
        binding.tvEquipmentHealth.text = currentCharacterSet.stats.health.toString()
        binding.tvEquipmentSpiritum.text = currentCharacterSet.stats.spiritum.toString()
        binding.tvEquipmentStrength.text = currentCharacterSet.stats.strength.toString()
        binding.tvEquipmentAttunement.text = currentCharacterSet.stats.attunement.toString()
        binding.tvEquipmentCunning.text = currentCharacterSet.stats.cunning.toString()
    }

    private fun calculateOffensiveStats(){
        if(currentCharacterSet.equippedWeapon != null){
            binding.tvEquipmentPhysicalAttack.text = currentCharacterSet.equippedWeapon!!.physAttack.toString()

            if(currentCharacterSet.equippedWeapon!!.fireAttack != null){
                binding.clytEquipmentElementalAttack.visibility = View.VISIBLE
                binding.ivEquipmentElementalAttack.setImageResource(R.mipmap.ic_fire_attribute_foreground)
                binding.tvEquipmentElementalAttack.text = currentCharacterSet.equippedWeapon!!.fireAttack.toString()
            }else if (currentCharacterSet.equippedWeapon!!.iceAttack != null){
                binding.clytEquipmentElementalAttack.visibility = View.VISIBLE
                binding.tvEquipmentElementalAttack.text = currentCharacterSet.equippedWeapon!!.iceAttack.toString()
                binding.ivEquipmentElementalAttack.setImageResource(R.mipmap.ic_ice_attribute_foreground)
            }else if((currentCharacterSet.equippedWeapon!!.thunderAttack != null)){
                binding.clytEquipmentElementalAttack.visibility = View.VISIBLE
                binding.tvEquipmentElementalAttack.text = currentCharacterSet.equippedWeapon!!.thunderAttack.toString()
                binding.ivEquipmentElementalAttack.setImageResource(R.mipmap.ic_thunder_attribute_foreground)
            }else{
                binding.clytEquipmentElementalAttack.visibility = View.GONE
            }

            binding.tvEquipmentCriticalChance.text = "${currentCharacterSet.equippedWeapon!!.critRate}%"
        }
        else{
            binding.tvEquipmentPhysicalAttack.text = "None"
            binding.tvEquipmentCriticalChance.text = "None"
            binding.tvEquipmentElementalAttack.text = "None"
            binding.clytEquipmentElementalAttack.visibility = View.GONE
        }
    }

    private fun calculateDefensiveStats(){
        var physDef = 0L; var fireDef = 0L; var iceDef = 0L; var thunderDef = 0L

        for(i in 0 until currentCharacterSet.equipmentPieces.size){
            if(currentCharacterSet.equipmentPieces[i] != null){
                physDef += currentCharacterSet.equipmentPieces[i]?.physDefense!!
                fireDef += currentCharacterSet.equipmentPieces[i]?.fireDefense!!
                iceDef += currentCharacterSet.equipmentPieces[i]?.iceDefense!!
                thunderDef += currentCharacterSet.equipmentPieces[i]?.thunderDefense!!
            }
        }

        for(j in 0 until currentCharacterSet.equippedAccessories.size){
            if(currentCharacterSet.equippedAccessories[j] != null){
                physDef += currentCharacterSet.equippedAccessories[j]?.accesoryGear?.physDefense!!
                fireDef += currentCharacterSet.equippedAccessories[j]?.accesoryGear?.fireDefense!!
                iceDef += currentCharacterSet.equippedAccessories[j]?.accesoryGear?.iceDefense!!
                thunderDef += currentCharacterSet.equippedAccessories[j]?.accesoryGear?.thunderDefense!!
            }
        }

        binding.tvEquipmentPhysicalDefense.text = physDef.toString()
        binding.tvEquipmentFireDefense.text = fireDef.toString()
        binding.tvEquipmentIceDefense.text = iceDef.toString()
        binding.tvEquipmentThunderDefense.text = thunderDef.toString()
    }

    private fun obtainCurrentSlots(){
        var numberOfSlots = 0
        var newSlotList : MutableList<Skill?> = mutableListOf()

        if(currentCharacterSet.equippedWeapon != null){
            numberOfSlots += currentCharacterSet.equippedWeapon!!.slots
        }

        for(i in 0 until currentCharacterSet.equipmentPieces.size){
            if(currentCharacterSet.equipmentPieces[i] != null && currentCharacterSet.equipmentPieces[i]?.slots != null){
                numberOfSlots += currentCharacterSet.equipmentPieces[i]?.slots!!
            }
        }

        for (j in 0 until currentCharacterSet.equippedAccessories.size){
            if(currentCharacterSet.equippedAccessories[j] != null && currentCharacterSet.equippedAccessories[j]?.accesoryGear?.slots != null){
                numberOfSlots += currentCharacterSet.equippedAccessories[j]?.accesoryGear?.slots!!
            }
        }

        if(numberOfSlots > 0){
            val currentSlotsWithSkills = currentCharacterSet.slottedSkills
            if(currentSlotsWithSkills != null){
                if(currentSlotsWithSkills.size < numberOfSlots){
                    for(i in 0 until currentSlotsWithSkills.size){
                        newSlotList.add(currentSlotsWithSkills[i])
                    }
                    for(j in 0 until (numberOfSlots-newSlotList.size)){
                        newSlotList.add(null)
                    }
                } else if(currentSlotsWithSkills.size > numberOfSlots){
                    for(k in 0 until numberOfSlots){
                        newSlotList.add(currentSlotsWithSkills[k])
                    }
                } else {
                    newSlotList = currentSlotsWithSkills
                }
            } else {
                for(l in 0 until numberOfSlots){
                    newSlotList.add(null)
                }
            }
            currentCharacterSet.slottedSkills = newSlotList
            slotOnSetAdapter.updateList(newSlotList)
        }else{
            currentCharacterSet.slottedSkills = mutableListOf()
            slotOnSetAdapter.updateList(currentCharacterSet.slottedSkills)
        }
        //Log.d("Testeo_Recalculo_Huecos", "Huecos recalculados, son los siguientes: ${slotOnSetAdapter.getList().toString()}")
    }

    private fun obtainCurrentSkills(){
        val skillMap = mutableMapOf<Skill, Int>()
        val currentlyEquippedWeapon = currentCharacterSet.equippedWeapon
        val currentlyEquippedGear = currentCharacterSet.equipmentPieces
        val currentlyEquippedAccessories = currentCharacterSet.equippedAccessories
        val currentSlotsWithSkills = slotOnSetAdapter.getList()

        if(currentlyEquippedWeapon != null && currentlyEquippedWeapon.skills != null){
            for(i in 0 until currentlyEquippedWeapon.skills.size){
                skillMap.merge(currentlyEquippedWeapon.skills[i], 1, Int::plus)
            }
        }

        for(j in 0 until (currentlyEquippedGear.size)){
            if(currentlyEquippedGear[j] != null && currentlyEquippedGear[j]?.skills != null){
                for(k in 0 until (currentlyEquippedGear[j]?.skills!!.size)){
                    skillMap.merge(currentlyEquippedGear[j]?.skills!![k], 1, Int::plus)
                }
            }
        }

        for(l in 0 until (currentlyEquippedAccessories.size)){
            if(currentlyEquippedAccessories[l] != null && currentlyEquippedAccessories[l]?.accesoryGear?.skills != null){
                for(m in 0 until currentlyEquippedAccessories[l]?.accesoryGear?.skills!!.size){
                    skillMap.merge(currentlyEquippedAccessories[l]?.accesoryGear?.skills!![m], 1, Int::plus)
                }
            }
        }

        if (!currentSlotsWithSkills.isNullOrEmpty()){
            for(searchedSkill in currentSlotsWithSkills){
                if(searchedSkill != null){
                    skillMap.merge(searchedSkill, 1, Int::plus)
                }
            }
        }

        if(skillMap.isNotEmpty()){
            val skillList = mutableListOf<SkillOnSet>()
            skillMap.forEach{ skillWithRank ->
                val iterationSkill = SkillOnSet(skillWithRank.key, skillWithRank.value)
                skillList.add(iterationSkill)
            }
            skillOnSetAdapter.updateList(skillList)
        } else {
            skillOnSetAdapter.updateList(listOf())
        }
        //Log.d("Testeo_Recalculo_Habilidades", "Habilidades recalculadas, actualmente son las siguientes: ${skillOnSetAdapter.skillList.toString()}")
    }

    private fun applySkills(){
        var auxAtk = 0F
        var auxFireAtk = 0F
        var auxIceAtk = 0F
        var auxThunderAtk = 0F
        var auxCrit = 0F
        var auxPhysDef = 0F
        var auxFireDef = 0F
        var auxIceDef = 0F
        var auxThunderDef = 0F
        var rankToApply: Int

        val currentSkills = skillOnSetAdapter.skillList
        if(!currentSkills.isNullOrEmpty()){
            for(i in currentSkills){
                rankToApply = if (i.currentRank > i.skill.maxRank) (i.skill.maxRank-1) else (i.currentRank-1)
                when(i.skill.type){
                    "Attack" -> {
                        if(binding.tvEquipmentPhysicalAttack.text != "None"){
                            when(i.skill.name){
                                "Atk Up" -> {
                                    auxAtk += (binding.tvEquipmentPhysicalAttack.text.toString().toFloat()) * (i.skill.rankValueModifiers[rankToApply]/100)
                                }
                                "Peak Form" -> {
                                    auxAtk += i.skill.rankValueModifiers[rankToApply]
                                }
                            }
                        }
                    }
                    "FireAttack" -> {
                        if(currentCharacterSet.equippedWeapon != null && currentCharacterSet.equippedWeapon!!.fireAttack != null && binding.tvEquipmentElementalAttack.text != "None"){
                            auxFireAtk += (binding.tvEquipmentElementalAttack.text.toString().toFloat()) * (i.skill.rankValueModifiers[rankToApply]/100)
                        }
                    }
                    "IceAttack" -> {
                        if(currentCharacterSet.equippedWeapon != null && currentCharacterSet.equippedWeapon!!.iceAttack != null && binding.tvEquipmentElementalAttack.text != "None"){
                            auxIceAtk += (binding.tvEquipmentElementalAttack.text.toString().toFloat()) * (i.skill.rankValueModifiers[rankToApply]/100)
                        }
                    }
                    "ThunderAttack" -> {
                        if(currentCharacterSet.equippedWeapon != null && currentCharacterSet.equippedWeapon!!.thunderAttack != null && binding.tvEquipmentElementalAttack.text != "None"){
                            auxThunderAtk += (binding.tvEquipmentElementalAttack.text.toString().toFloat()) * (i.skill.rankValueModifiers[rankToApply]/100)

                        }
                    }
                    "Critical" -> {
                        auxCrit += i.skill.rankValueModifiers[rankToApply]
                    }
                    "PhysDefense" -> {
                        auxPhysDef += i.skill.rankValueModifiers[rankToApply]
                    }
                    "FireDefense" -> {
                        auxFireDef += i.skill.rankValueModifiers[rankToApply]
                    }
                    "IceDefense" -> {
                        auxIceDef += i.skill.rankValueModifiers[rankToApply]
                    }
                    "ThunderDefense" -> {
                        auxThunderDef += i.skill.rankValueModifiers[rankToApply]
                    }
                }
            }
        }

        if(currentCharacterSet.equippedWeapon != null){
            if(binding.tvEquipmentPhysicalAttack.text != "None" && auxAtk > 0){
                binding.tvEquipmentPhysicalAttack.text = (binding.tvEquipmentPhysicalAttack.text.toString().toFloat() + auxAtk).toString()
            }
            if(binding.tvEquipmentElementalAttack.text != "None" && (auxFireAtk > 0 || auxIceAtk > 0 || auxThunderAtk > 0)){
                if(currentCharacterSet.equippedWeapon?.fireAttack != null){
                    binding.tvEquipmentElementalAttack.text = (binding.tvEquipmentElementalAttack.text.toString().toFloat() + auxFireAtk).toString()
                } else if(currentCharacterSet.equippedWeapon?.iceAttack != null){
                    binding.tvEquipmentElementalAttack.text = (binding.tvEquipmentElementalAttack.text.toString().toFloat() + auxIceAtk).toString()
                } else if(currentCharacterSet.equippedWeapon?.thunderAttack != null){
                    binding.tvEquipmentElementalAttack.text = (binding.tvEquipmentElementalAttack.text.toString().toFloat() + auxThunderAtk).toString()
                }
            }

            if(binding.tvEquipmentCriticalChance.text != "None" && auxCrit > 0){
                binding.tvEquipmentCriticalChance.text = "${(binding.tvEquipmentCriticalChance.text.toString().substring(0, binding.tvEquipmentCriticalChance.text.toString().length-1).toFloat() + auxCrit)}%"
            }

            if(auxPhysDef > 0){
                binding.tvEquipmentPhysicalDefense.text = (binding.tvEquipmentPhysicalDefense.text.toString().toFloat() + auxPhysDef).toString()
            }
            if(auxFireDef > 0){
                binding.tvEquipmentFireDefense.text = (binding.tvEquipmentFireDefense.text.toString().toFloat() + auxFireDef).toString()
            }
            if(auxIceDef > 0){
                binding.tvEquipmentIceDefense.text = (binding.tvEquipmentIceDefense.text.toString().toFloat() + auxIceDef).toString()
            }
            if(auxThunderDef > 0){
                binding.tvEquipmentThunderDefense.text = (binding.tvEquipmentThunderDefense.text.toString().toFloat() + auxThunderDef).toString()
            }
        }
        //Log.d("Testeo_Recalculo_Formulas", "Fórmulas reaplicadas a los atributos")
    }

    private fun showSkillInfo(skillOnSet: SkillOnSet){
        var infoBody = ""
        val maxSkillRankToCheck = if(skillOnSet.currentRank > skillOnSet.skill.maxRank) skillOnSet.skill.maxRank else skillOnSet.currentRank
        for(i in 0 until skillOnSet.skill.maxRank){
            infoBody += if((i+1) == maxSkillRankToCheck){
                "Lvl. ${i+1}: [${skillOnSet.skill.specificDescription[i]}]"
            } else {
                "Lvl. ${i+1}: ${skillOnSet.skill.specificDescription[i]}"
            }

            if(i+1 != skillOnSet.skill.maxRank){
                infoBody+= "\n"
            }
        }

        AlertDialog.Builder(activity as Context)
            .setTitle(skillOnSet.skill.name)
            .setMessage(infoBody)
            .setIcon(IconHelper.setSkillIconInfo(skillOnSet.skill.type))
            .setPositiveButton(getString(R.string.skillInfoCloseAction)) { v, _ ->
                v.dismiss()
            }
            .create()
            .show()
    }

    private fun showSaveAndExitDialog(characterSet: CharacterSet){
        AlertDialog.Builder(activity as Context)
            .setTitle(getString(R.string.equipmentSetSaveAndExitDialogTitle))
            .setMessage(getString(R.string.equipmentSetSaveAndExitDialogMessage))
            .setPositiveButton(getString(R.string.equipmentSetSaveAndExitDialogConfirm)){ v, _ ->
                characterSetViewModel.addCharacterSet(characterSet)
                registeredUserViewModel.getRegisteredUserWithAllOwnedCharacterSets(characterSetViewModel.characterSetListLiveData)
                v.dismiss()
                if(equipmentSetFragmentArgs.isANewSet){
                    val action = EquipmentSetFragmentDirections.actionEquipmentSetFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
                else{
                    findNavController().popBackStack()
                }
            }
            .setNegativeButton(getString(R.string.equipmentSetSaveAndExitDialogCancel)){ v, _ ->
                v.dismiss()
            }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun showExitWithoutSavingWarningDialog(){
        AlertDialog.Builder(activity as Context)
            .setTitle(getString(R.string.equipmentSetExitWithoutSavingWarningDialogTitle))
            .setMessage(getString(R.string.equipmentSetExitWithoutSavingWarningDialogMessage))
            .setPositiveButton(getString(R.string.equipmentSetExitWithoutSavingWarningDialogConfirm)) { v, _ ->
                if(equipmentSetFragmentArgs.isANewSet){
                    val action = EquipmentSetFragmentDirections.actionEquipmentSetFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
                else{
                    findNavController().popBackStack()
                }
                v.dismiss()
            }
            .setNegativeButton(getString(R.string.equipmentSetExitWithoutSavingWarningDialogCancel)){ v, _ ->
                v.dismiss()
            }
            .create()
            .show()
    }
}