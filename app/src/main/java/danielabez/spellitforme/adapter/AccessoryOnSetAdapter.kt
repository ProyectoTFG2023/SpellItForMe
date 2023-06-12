package danielabez.spellitforme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.ItemAccessoryOnSetBinding
import danielabez.spellitforme.databinding.ItemGearOnSetBinding
import danielabez.spellitforme.model.Accessory
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.tools.IconHelper

class AccessoryOnSetAdapter : RecyclerView.Adapter<AccessoryOnSetAdapter.AccessoryOnSetViewHolder>() {
    var accessoryList: List<Accessory?>? = null
    var onAccessoryOnSetClickListener : OnAccessoryOnSetClickListener? = null

    inner class AccessoryOnSetViewHolder(val binding: ItemAccessoryOnSetBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.ivAccessoryOnSet.setOnClickListener(){
                val chosenAccessory = accessoryList?.get(this.adapterPosition)
                val chosenPosition = this.adapterPosition
                onAccessoryOnSetClickListener?.onAccessoryOnSetImageClick(chosenAccessory, chosenPosition)
            }

            binding.ivAccessoryOnSetRemove.setOnClickListener(){
                onAccessoryOnSetClickListener?.onAccessoryOnSetRemoveClick(this.adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccessoryOnSetAdapter.AccessoryOnSetViewHolder {
        val binding = ItemAccessoryOnSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccessoryOnSetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccessoryOnSetAdapter.AccessoryOnSetViewHolder, position: Int) {
        with(holder){
            with(accessoryList!![position]){
                if(accessoryList!![position] != null){
                    binding.llytAccessoryOnSetNoDataBottomMarginDummy.visibility = View.GONE
                    binding.llytAccessoryOnSetBottomMarginDummy.visibility = View.VISIBLE
                    binding.llytAccessoryOnSetAttributes.visibility = View.VISIBLE
                    binding.ivAccessoryOnSetRemove.visibility = View.VISIBLE

                    binding.tvAccessoryOnSetName.text = this?.accesoryGear?.name
                    binding.tvAccessoryOnSetPhysDefense.text = this?.accesoryGear?.physDefense.toString()
                    binding.tvAccessoryOnSetFireDefense.text = this?.accesoryGear?.fireDefense.toString()
                    binding.tvAccessoryOnSetIceDefense.text = this?.accesoryGear?.iceDefense.toString()
                    binding.tvAccessoryOnSetThunderDefense.text = this?.accesoryGear?.thunderDefense.toString()

                    if(this?.accesoryGear?.slots!! > 0){
                        binding.clytAccessoryOnSetSlots.visibility = View.VISIBLE
                        binding.tvAccessoryOnSetNumberOfSlots.text = "x${accesoryGear?.slots}"
                    }

                    if(this?.accesoryGear?.skills?.isNotEmpty()!!){
                        binding.llytAccessoryOnSetSkills.visibility = View.VISIBLE
                        binding.tvAccessoryOnSetSkillOne.text = this?.accesoryGear?.skills!![0].name
                        IconHelper.setSkillIcon(binding.ivAccessoryOnSetSkillOne, this?.accesoryGear?.skills!![0].type)

                        if(this?.accesoryGear?.skills?.size == 2){
                            binding.clytAccessoryOnSetSkillTwo.visibility = View.VISIBLE
                            binding.tvAccessoryOnSetSkillTwo.text = this?.accesoryGear?.skills!![1].name
                            IconHelper.setSkillIcon(binding.ivAccessoryOnSetSkillTwo, this?.accesoryGear?.skills!![1].type)
                        }
                    }

                    //Mostraremos un icono según el tipo de equipamiento y la rareza que tenga
                    IconHelper.setAccessoryIcon(binding.ivAccessoryOnSet, accesoryType, accesoryGear.rarity)
                }else{
                    binding.clytAccessoryOnSetSlots.visibility = View.GONE
                    binding.llytAccessoryOnSetAttributes.visibility = View.GONE
                    binding.ivAccessoryOnSetRemove.visibility = View.GONE
                    binding.llytAccessoryOnSetBottomMarginDummy.visibility = View.GONE
                    binding.llytAccessoryOnSetNoDataBottomMarginDummy.visibility = View.VISIBLE
                    binding.tvAccessoryOnSetName.text = "No accessory selected"
                    binding.ivAccessoryOnSet.setImageResource(R.mipmap.ic_ring_none_foreground)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return accessoryList?.size?: 0
    }

    interface OnAccessoryOnSetClickListener{
        fun onAccessoryOnSetImageClick(accessory: Accessory?, accessoryPosition : Int)
        fun onAccessoryOnSetRemoveClick(accessoryPosition: Int)
    }

    fun updateList(newList: List<Accessory?>){
        accessoryList = newList
        notifyDataSetChanged()
    }
}