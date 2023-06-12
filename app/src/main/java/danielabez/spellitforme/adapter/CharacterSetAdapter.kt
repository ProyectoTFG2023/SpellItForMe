package danielabez.spellitforme.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.ItemCharacterSetBinding
import danielabez.spellitforme.model.CharacterSet
import danielabez.spellitforme.tools.IconHelper

class CharacterSetAdapter : RecyclerView.Adapter<CharacterSetAdapter.CharacterSetViewHolder>() {
    var characterSetList : List<CharacterSet>? = null
    var onCharacterSetClickListener : OnCharacterSetClickListener? = null

    inner class CharacterSetViewHolder(val binding: ItemCharacterSetBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener(){
                val chosenCharacterSet = characterSetList?.get(this.adapterPosition)
                onCharacterSetClickListener?.onCharacterSetClick(chosenCharacterSet)
            }
            binding.ivCharacterSetRemove.setOnClickListener(){
                val chosenCharacterSet = characterSetList?.get(this.adapterPosition)
                onCharacterSetClickListener?.onCharacterSetClickRemove(chosenCharacterSet)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterSetViewHolder {
        val binding = ItemCharacterSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterSetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterSetViewHolder, position: Int) {
        with(holder){
            with(characterSetList!!.get(position)){
                binding.tvCharacterSetName.text = name
                binding.tvCharacterSetRole.text = role
                IconHelper.setRoleIcon(binding.ivCharacterSetRole, role)

                if(equippedWeapon != null){
                    binding.tvCharacterSetWeapon.text = equippedWeapon?.name
                    IconHelper.setWeaponIcon(binding.ivCharacterSetWeapon, equippedWeapon?.type!!, equippedWeapon?.rarity!!)
                }
                else{
                    binding.tvCharacterSetWeapon.text = "No weapon"
                    binding.ivCharacterSetWeapon.setImageResource(android.R.drawable.ic_menu_help)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return characterSetList?.size?: 0
    }

    fun updateList(newList: List<CharacterSet>){
        characterSetList = newList
        notifyDataSetChanged()
    }

    interface OnCharacterSetClickListener{
        fun onCharacterSetClick(characterSet: CharacterSet?)
        fun onCharacterSetClickRemove(characterSet: CharacterSet?)
    }
}