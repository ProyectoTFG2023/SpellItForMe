package danielabez.spellitforme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.ItemGearOnSetBinding
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.tools.IconHelper

class GearOnSetAdapter : RecyclerView.Adapter<GearOnSetAdapter.GearOnSetViewHolder>() {
    var gearList: List<Gear?>? = null
    var onGearOnSetClickListener : OnGearOnSetClickListener? = null

    inner class GearOnSetViewHolder(val binding: ItemGearOnSetBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.ivGearOnSet.setOnClickListener(){
                val chosenGear = gearList?.get(this.adapterPosition)
                val chosenPosition = this.adapterPosition
                onGearOnSetClickListener?.onGearOnSetImageClick(chosenGear, chosenPosition)
            }

            binding.ivGearOnSetRemove.setOnClickListener(){
                onGearOnSetClickListener?.onGearOnSetRemoveClick(this.adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GearOnSetAdapter.GearOnSetViewHolder {
        val binding = ItemGearOnSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GearOnSetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GearOnSetAdapter.GearOnSetViewHolder, position: Int) {
        with(holder){
            with(gearList!![position]){
                if(gearList!![position] != null){
                    binding.llytGearOnSetNoDataBottomMarginDummy.visibility = View.GONE
                    binding.llytGearOnSetBottomMarginDummy.visibility = View.VISIBLE
                    binding.llytGearOnSetAttributes.visibility = View.VISIBLE
                    binding.ivGearOnSetRemove.visibility = View.VISIBLE

                    binding.tvGearOnSetName.text = this?.name
                    binding.tvGearOnSetPhysDefense.text = this?.physDefense.toString()
                    binding.tvGearOnSetFireDefense.text = this?.fireDefense.toString()
                    binding.tvGearOnSetIceDefense.text = this?.iceDefense.toString()
                    binding.tvGearOnSetThunderDefense.text = this?.thunderDefense.toString()

                    if(this?.slots!! > 0){
                        binding.clytGearOnSetSlots.visibility = View.VISIBLE
                        binding.tvGearOnSetNumberOfSlots.text = "x$slots"
                    }

                    if(skills.isNotEmpty()){
                        binding.llytGearOnSetSkills.visibility = View.VISIBLE
                        binding.tvGearOnSetSkillOne.text = skills[0].name
                        IconHelper.setSkillIcon(binding.ivGearOnSetSkillOne, skills[0].type)

                        if(skills.size == 2){
                            binding.clytGearOnSetSkillTwo.visibility = View.VISIBLE
                            binding.tvGearOnSetSkillTwo.text = skills[1].name
                            IconHelper.setSkillIcon(binding.ivGearOnSetSkillTwo, skills[1].type)
                        }
                    }

                    //Mostraremos un icono según el tipo de equipamiento y la rareza que tenga
                    IconHelper.setGearIcon(binding.ivGearOnSet, type, rarity)
                }else{
                    binding.clytGearOnSetSlots.visibility = View.GONE
                    binding.llytGearOnSetAttributes.visibility = View.GONE
                    binding.ivGearOnSetRemove.visibility = View.GONE
                    binding.llytGearOnSetBottomMarginDummy.visibility = View.GONE
                    binding.llytGearOnSetNoDataBottomMarginDummy.visibility = View.VISIBLE
                    //TODO: FALTA EL CONTROL DE LA IMAGEN SOLO
                    val piece = when(position){
                        0 -> "headgear"
                        1 -> "torso"
                        2 -> "handwear"
                        3 -> "belt"
                        else -> "footwear"
                    }
                    binding.tvGearOnSetName.text = "No $piece selected"
                    binding.ivGearOnSet.setImageResource(
                        when(position){
                            0 -> R.mipmap.ic_helmet_none_foreground
                            1 -> R.mipmap.ic_torso_none_foreground
                            2 -> R.mipmap.ic_handwear_none_foreground
                            3 -> R.mipmap.ic_belt_none_foreground
                            else -> R.mipmap.ic_footwear_none_foreground
                        }
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return gearList?.size?: 0
    }

    interface OnGearOnSetClickListener{
        fun onGearOnSetImageClick(gear: Gear?, gearPosition : Int)
        fun onGearOnSetRemoveClick(gearPosition: Int)
    }

    fun updateList(newList: List<Gear?>){
        gearList = newList
        notifyDataSetChanged()
    }
}