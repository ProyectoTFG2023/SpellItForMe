package danielabez.spellitforme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.ItemGearBinding
import danielabez.spellitforme.databinding.ItemWeaponBinding
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.model.Weapon

class WeaponAdapter : RecyclerView.Adapter<WeaponAdapter.WeaponViewHolder>() {
    var weaponList: List<Weapon>? = null

    //TODO: PENDIENTE DE AÑADIR FUNCIONALIDAD
    inner class WeaponViewHolder(val binding: ItemWeaponBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(){
                val chosenWeapon = weaponList?.get(this.adapterPosition)
            }
        }
    }

    /*
    * Este método permite al RecyclerView crear un nuevo ViewHolder cuando lo necesite. Tenemos que indicarle el layout que necesitará para "inflarlo", es decir,
    * el layout de los elementos que va a contener, que en nuestro caso es item_tarea.xml. Hay varias formas de hacerlo, pero nosotros usaremos el binding
    * de la siguiente forma:
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaponViewHolder {
        val binding = ItemWeaponBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeaponViewHolder(binding)
    }

    /*
    * Método al que llama el RecyclerView cada vez que necesita dibujar un elemento. Le pasamos un ViewHolder que tendremos que completar con los datos de la tarea,
    * indicada por el int que indica la posición de dicha tarea en la lista que esté contenida
    */
    override fun onBindViewHolder(holder: WeaponViewHolder, position: Int) {
        with(holder) {
            //Primero, obtenemos el objeto Gear de la posición que se pase por parámetro y extraeremos sus valores para poder colocarlos en el ViewHolder
            with(weaponList!!.get(position)) {
                binding.tvWeaponName.text = name
                binding.tvWeaponPhysAttack.text = physAttack.toString()
                binding.tvCritrate.text = "$critRate%"

                if(fireAttack != null){
                    binding.tvWeaponElementalAttack.text = fireAttack.toString()
                    binding.ivElementalAttack.setImageResource(R.mipmap.ic_fire_attribute_foreground)
                }else if(iceAttack != null){
                    binding.tvWeaponElementalAttack.text = iceAttack.toString()
                    binding.ivElementalAttack.setImageResource(R.mipmap.ic_ice_attribute_foreground)
                }else if (thunderAttack != null){
                    binding.tvWeaponElementalAttack.text = thunderAttack.toString()
                    binding.ivElementalAttack.setImageResource(R.mipmap.ic_thunder_attribute_foreground)
                }

                if(fireAttack != null || iceAttack != null || thunderAttack != null){
                    binding.clytElementalAttackAttribute.visibility = View.VISIBLE
                } else{
                    binding.clytElementalAttackAttribute.visibility = View.GONE
                }

                if(slots > 0){
                    binding.ivSlots.visibility = View.VISIBLE
                    binding.tvNumberOfSlots.visibility = View.VISIBLE
                    binding.tvNumberOfSlots.text = "x$slots"
                }

                //TODO: Hacer más iconos a cambiar
                if(skills.isNotEmpty()){
                    binding.llytWeaponBottomMarginDummy.visibility = View.GONE
                    binding.llytWeaponSkills.visibility = View.VISIBLE
                    binding.tvWeaponSkillOne.text = skills[0].name
                    //binding.clytGearSkillOne.visibility = View.VISIBLE
                    binding.ivWeaponSkillOne.setImageResource(
                        when(skills[0].type){
                            "PhysDefense" -> R.mipmap.ic_phys_defense_up_skill_foreground
                            else -> R.mipmap.ic_phys_attack_skill_foreground
                        }
                    )

                    if(skills.size == 2){
                        binding.clytWeaponSkillTwo.visibility = View.VISIBLE
                        binding.tvWeaponSkillTwo.text = skills[1].name
                        binding.ivWeaponSkillTwo.setImageResource(
                            when(skills[1].type){
                                "PhysDefense" -> R.mipmap.ic_phys_defense_up_skill_foreground
                                else -> R.mipmap.ic_phys_attack_skill_foreground
                            }
                        )
                    }
                }

                //TODO: CAMBIAR PARA QUE SALGAN ICONOS DISTINTOS SEGÚN EL TIPO DE EQUIPAMIENTO
                //Mostraremos un icono según el tipo de equipamiento y la rareza que tenga
                binding.ivWeapon.setImageResource(
                    when (rarity) {
                        "a_Common" -> R.mipmap.ic_helmet_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_helmet_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_helmet_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_helmet_mythical_foreground
                        else -> R.mipmap.ic_helmet_legendary_foreground
                    }
                )
            }
        }
    }

    /*
    * Con este método, permitimos al RecyclerView conocer el tamaño de la lista
    */
    override fun getItemCount(): Int {
        //Con el elemento ?: se asigna un valor por defecto a la variable en caso de que su previo valor fuese nulo, en este caso se iguala a 0 para evitar los NullPointerException
        return weaponList?.size?: 0
    }

    fun updateList(newList: List<Weapon>){
        weaponList = newList
        notifyDataSetChanged()
    }
}