package danielabez.spellitforme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.ItemWeaponOnSetBinding
import danielabez.spellitforme.model.Weapon
import danielabez.spellitforme.tools.IconHelper

class WeaponOnSetAdapter : RecyclerView.Adapter<WeaponOnSetAdapter.WeaponOnSetViewHolder>() {
    var weaponList: List<Weapon?>? = null
    var onWeaponOnSetClickListener: OnWeaponOnSetClickListener? = null

    inner class WeaponOnSetViewHolder(val binding: ItemWeaponOnSetBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.ivWeaponOnSet.setOnClickListener(){
                onWeaponOnSetClickListener?.onWeaponOnSetImageClick()
            }
            binding.ivWeaponOnSetRemove.setOnClickListener(){
                onWeaponOnSetClickListener?.onWeaponOnSetRemoveClick(this.adapterPosition)
            }
        }
    }

    /*
    * Este método permite al RecyclerView crear un nuevo ViewHolder cuando lo necesite. Tenemos que indicarle el layout que necesitará para "inflarlo", es decir,
    * el layout de los elementos que va a contener, que en nuestro caso es item_tarea.xml. Hay varias formas de hacerlo, pero nosotros usaremos el binding
    * de la siguiente forma:
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaponOnSetViewHolder {
        val binding = ItemWeaponOnSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeaponOnSetViewHolder(binding)
    }

    /*
    * Método al que llama el RecyclerView cada vez que necesita dibujar un elemento. Le pasamos un ViewHolder que tendremos que completar con los datos de la tarea,
    * indicada por el int que indica la posición de dicha tarea en la lista que esté contenida
    */
    override fun onBindViewHolder(holder: WeaponOnSetViewHolder, position: Int) {
        with(holder) {
            //Primero, obtenemos el objeto Gear de la posición que se pase por parámetro y extraeremos sus valores para poder colocarlos en el ViewHolder
            with(weaponList!![position]) {
                if(weaponList!![position] != null){
                    binding.llytWeaponOnSetNoDataBottomMarginDummy.visibility = View.GONE
                    binding.llytWeaponOnSetBottomMarginDummy.visibility = View.VISIBLE
                    binding.llytWeaponOnSetAttributes.visibility = View.VISIBLE
                    binding.ivWeaponOnSetRemove.visibility = View.VISIBLE

                    binding.tvWeaponOnSetName.text = this?.name
                    binding.tvWeaponOnSetPhysDamage.text = this?.physAttack.toString()
                    binding.tvWeaponOnSetCritChance.text = "${this?.critRate}%"

                    if(this?.fireAttack != null || this?.iceAttack != null || this?.thunderAttack != null){
                        binding.clytWeaponOnSetElementalDamage.visibility = View.VISIBLE
                        if(this?.fireAttack != null){
                            binding.tvWeaponOnSetElementalDamage.text = fireAttack.toString()
                            binding.ivWeaponOnSetElementalDamage.setImageResource(R.mipmap.ic_fire_attribute_foreground)
                        }else if(this?.iceAttack != null){
                            binding.tvWeaponOnSetElementalDamage.text = iceAttack.toString()
                            binding.ivWeaponOnSetElementalDamage.setImageResource(R.mipmap.ic_ice_attribute_foreground)
                        }else if (this?.thunderAttack != null){
                            binding.tvWeaponOnSetElementalDamage.text = thunderAttack.toString()
                            binding.ivWeaponOnSetElementalDamage.setImageResource(R.mipmap.ic_thunder_attribute_foreground)
                        }
                    } else{
                        binding.clytWeaponOnSetElementalDamage.visibility = View.GONE
                    }

                    if(this?.slots!! > 0){
                        binding.ivWeaponOnSetSlots.visibility = View.VISIBLE
                        binding.tvWeaponOnSetNumberOfSlots.visibility = View.VISIBLE
                        binding.tvWeaponOnSetNumberOfSlots.text = "x$this?.slots"
                    }

                    if(skills?.isNotEmpty() == true){
                        binding.llytWeaponOnSetBottomMarginDummy.visibility = View.GONE
                        binding.llytWeaponOnSetSkills.visibility = View.VISIBLE
                        binding.tvWeaponOnSetSkillOne.text = skills[0].name
                        IconHelper.setSkillIcon(binding.ivWeaponOnSetSkillOne, skills[0].type)

                        if(skills.size == 2){
                            binding.clytWeaponOnSetSkillTwo.visibility = View.VISIBLE
                            binding.tvWeaponOnSetSkillTwo.text = skills[1].name
                            IconHelper.setSkillIcon(binding.ivWeaponOnSetSkillTwo, skills[1].type)
                        }
                    }

                    //Mostraremos un icono según el tipo de equipamiento y la rareza que tenga
                    IconHelper.setWeaponIcon(binding.ivWeaponOnSet, type, rarity)
                }else {
                    binding.clytWeaponOnSetSlots.visibility = View.GONE
                    binding.llytWeaponOnSetAttributes.visibility = View.GONE
                    binding.ivWeaponOnSetRemove.visibility = View.GONE
                    binding.llytWeaponOnSetBottomMarginDummy.visibility = View.GONE
                    binding.llytWeaponOnSetNoDataBottomMarginDummy.visibility = View.VISIBLE
                    binding.tvWeaponOnSetName.text = "No weapon selected"
                    binding.ivWeaponOnSet.setImageResource(R.mipmap.ic_sword_none_foreground)
                }
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

    fun updateWeaponList(newWeaponList : List<Weapon?>){
        weaponList = newWeaponList
        notifyDataSetChanged()
    }

    interface OnWeaponOnSetClickListener{
        fun onWeaponOnSetImageClick()
        fun onWeaponOnSetRemoveClick(weaponPosition: Int)
    }
}