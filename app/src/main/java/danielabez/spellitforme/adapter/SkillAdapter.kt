package danielabez.spellitforme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.ItemSkillBinding
import danielabez.spellitforme.model.Skill
import danielabez.spellitforme.tools.IconHelper

class SkillAdapter : RecyclerView.Adapter<SkillAdapter.SkillViewHolder>() {
    var skillList : List<Skill>? = null
    var onSkillClickListener : OnSkillClickListener ? = null

    inner class SkillViewHolder(val binding: ItemSkillBinding) : RecyclerView.ViewHolder(binding.root){
        init{
            binding.cvSkill.setOnClickListener(){
                val chosenSkill = skillList!![this.adapterPosition]
                onSkillClickListener?.onSkillClick(chosenSkill)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val binding = ItemSkillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SkillViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        with(holder){
            with(skillList!![position]){
                binding.tvSkillName.text = name
                binding.tvSkillBriefDescription.text = description
                binding.tvSkillMaxLevel.text = "Up to level$maxRank"
                IconHelper.setSkillIcon(binding.ivSkillIcon, type)
            }
        }
    }

    override fun getItemCount(): Int {
        return skillList?.size?: 0
    }

    interface OnSkillClickListener{
        fun onSkillClick(skill: Skill)
    }

    fun updateList(newList: List<Skill>){
        skillList = newList
        notifyDataSetChanged()
    }
}