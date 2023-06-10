package danielabez.spellitforme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.ItemSlotBinding
import danielabez.spellitforme.model.Skill
import danielabez.spellitforme.tools.IconHelper

class SlotOnSetAdapter : RecyclerView.Adapter<SlotOnSetAdapter.SlotOnSetViewHolder>(){
    var slottedSkillsList: List<Skill?>? = null
    var onSlotOnSetClickListener : OnSlotOnSetClickListener? = null

    inner class SlotOnSetViewHolder(val binding: ItemSlotBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.clytSlotOnSetInner.setOnClickListener(){
                onSlotOnSetClickListener?.onSlotOnSetClick(this.adapterPosition)

            }
            binding.ivSlotOnSetRemove.setOnClickListener(){
                onSlotOnSetClickListener?.onSlotOnSetRemoveClick(this.adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotOnSetAdapter.SlotOnSetViewHolder {
        val binding = ItemSlotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SlotOnSetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlotOnSetAdapter.SlotOnSetViewHolder, position: Int) {
        with(holder){
            with(slottedSkillsList!![position]){
                //TODO: Falta el control de la gema vacía o llena dependiendo de su estado
                if(slottedSkillsList!![position] != null){
                    binding.tvSlotOnSetNoSkill.visibility = View.GONE
                    binding.tvSlotOnSetArrow.visibility = View.VISIBLE
                    binding.ivSlotOnSetSkillIcon.visibility = View.VISIBLE
                    binding.tvSlotOnSetSkillName.visibility = View.VISIBLE
                    binding.ivSlotOnSetRemove.visibility = View.VISIBLE

                    binding.ivSlotOnSetIcon.setImageResource(R.mipmap.ic_gem_slot_foreground)
                    binding.tvSlotOnSetArrow.text = "-->"
                    binding.tvSlotOnSetSkillName.text = this?.name
                    IconHelper.setSkillIcon(binding.ivSlotOnSetSkillIcon, this?.type!!)
                }
                else {
                    binding.tvSlotOnSetArrow.visibility = View.GONE
                    binding.ivSlotOnSetSkillIcon.visibility = View.GONE
                    binding.tvSlotOnSetSkillName.visibility = View.GONE
                    binding.ivSlotOnSetRemove.visibility = View.GONE
                    binding.tvSlotOnSetNoSkill.visibility = View.VISIBLE
                    binding.ivSlotOnSetIcon.setImageResource(R.mipmap.ic_gem_slot_foreground)
                    //TODO: Cambiar icono de gema a vacía
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return slottedSkillsList?.size?: 0
    }

    interface OnSlotOnSetClickListener {
        fun onSlotOnSetClick(position: Int)
        fun onSlotOnSetRemoveClick(position: Int)
    }

    fun updateList(newList: List<Skill?>){
        slottedSkillsList = newList
        notifyDataSetChanged()
    }

    fun getList() : List<Skill?>? {
        return slottedSkillsList
    }
}