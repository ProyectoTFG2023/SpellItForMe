package danielabez.spellitforme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.ItemGearOnSetBinding
import danielabez.spellitforme.databinding.ItemSkillOnSetBinding
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.model.Skill
import danielabez.spellitforme.model.SkillOnSet
import danielabez.spellitforme.tools.IconHelper

class SkillOnSetAdapter : RecyclerView.Adapter<SkillOnSetAdapter.SkillOnSetViewHolder>() {
    var skillList : List<SkillOnSet>? = null
    var onSkillOnSetClickListener : OnSkillOnSetClickListener? = null


    inner class SkillOnSetViewHolder(val binding: ItemSkillOnSetBinding) : RecyclerView.ViewHolder(binding.root){
        init {
           binding.cvSkillOnSet.setOnClickListener(){
               val chosenSkill = skillList?.get(this.adapterPosition)
               onSkillOnSetClickListener?.onSkillOnSetImageClick(chosenSkill)
           }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillOnSetViewHolder {
        val binding = ItemSkillOnSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SkillOnSetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SkillOnSetViewHolder, position: Int) {
        with(holder){
            with(skillList!![position]){
                var listOfSquares = listOf(binding.ivSkillOnSetLevelOne, binding.ivSkillOnSetLevelTwo, binding.ivSkillOnSetLevelThree, binding.ivSkillOnSetLevelFour, binding.ivSkillOnSetLevelFive)
                for(it in listOfSquares){
                    it.visibility = View.INVISIBLE
                }
                val isCurrentRankHigherThanMax = (currentRank > skill.maxRank)
                binding.tvSkillOnSetName.text = skill.name
                binding.tvSkillOnSetCurrentRank.text = "Lvl. $currentRank/${skill.maxRank}"
                IconHelper.setSkillIcon(binding.ivSkillOnSetIcon, skill.type)

                for(i in 0 until skill.maxRank){
                    listOfSquares[i].visibility = View.VISIBLE
                    if(isCurrentRankHigherThanMax){
                        IconHelper.setSkillRankIcon(listOfSquares[i], "Surpassed")
                    }else{
                        if((currentRank-i) > 0){
                            IconHelper.setSkillRankIcon(listOfSquares[i], "Checked")
                        } else {
                            IconHelper.setSkillRankIcon(listOfSquares[i], "Empty")
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return skillList?.size?: 0
    }

    interface OnSkillOnSetClickListener {
        fun onSkillOnSetImageClick(skillOnSet: SkillOnSet?)
    }

    fun updateList(newList: List<SkillOnSet>){
        skillList = newList
        notifyDataSetChanged()
    }
}