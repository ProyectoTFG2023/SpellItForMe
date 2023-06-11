package danielabez.spellitforme.tools

import android.graphics.drawable.Drawable
import android.widget.ImageView
import danielabez.spellitforme.R

class IconHelper {
    companion object {
        //TODO: Realizar los iconos correspondientes
        fun setRoleIcon(imageView: ImageView, role: String){
            imageView.setImageResource(
                when(role){
                    "Warrior" -> R.mipmap.ic_helmet_legendary_foreground
                    "Rogue" -> R.mipmap.ic_helmet_uncommon_foreground
                    else -> R.mipmap.ic_helmet_rare_foreground
                }
            )
        }

        //TODO: Realizar los iconos correspondientes
        fun setAttributeIcon(imageView: ImageView, role: String){

        }

        fun setWeaponIcon(imageView: ImageView, type: String, rarity: String){
            imageView.setImageResource(
                when(type){
                    "a_Sword" -> when (rarity) {
                        "a_Common" -> R.mipmap.ic_helmet_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_helmet_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_helmet_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_helmet_mythical_foreground
                        else -> R.mipmap.ic_helmet_legendary_foreground
                    }
                    else -> when (rarity) {
                        "a_Common" -> R.mipmap.ic_helmet_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_helmet_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_helmet_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_helmet_mythical_foreground
                        else -> R.mipmap.ic_helmet_legendary_foreground
                    }
                }
            )
        }

        fun setGearIcon(imageView: ImageView, type: String, rarity: String){
            imageView.setImageResource(
                when(type){
                    "Headgear" -> when (rarity) {
                        "a_Common" -> R.mipmap.ic_helmet_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_helmet_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_helmet_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_helmet_mythical_foreground
                        else -> R.mipmap.ic_helmet_legendary_foreground
                    }
                    "Torso" -> when (rarity) {
                        "a_Common" -> R.mipmap.ic_helmet_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_helmet_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_helmet_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_helmet_mythical_foreground
                        else -> R.mipmap.ic_helmet_legendary_foreground
                    }
                    "Handwear" -> when (rarity) {
                        "a_Common" -> R.mipmap.ic_helmet_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_helmet_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_helmet_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_helmet_mythical_foreground
                        else -> R.mipmap.ic_helmet_legendary_foreground
                    }
                    "Belt" -> when (rarity) {
                        "a_Common" -> R.mipmap.ic_helmet_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_helmet_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_helmet_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_helmet_mythical_foreground
                        else -> R.mipmap.ic_helmet_legendary_foreground
                    }
                    else -> when (rarity) {
                        "a_Common" -> R.mipmap.ic_helmet_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_helmet_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_helmet_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_helmet_mythical_foreground
                        else -> R.mipmap.ic_helmet_legendary_foreground
                    }
                }
            )
        }

        fun setAccessoryIcon(imageView: ImageView, type: String, rarity: String){
            imageView.setImageResource(
                when(type){
                    "a_Necklace" -> when(rarity){
                        "a_Common" -> R.mipmap.ic_helmet_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_helmet_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_helmet_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_helmet_mythical_foreground
                        else -> R.mipmap.ic_helmet_legendary_foreground
                    }
                    else -> when(rarity){
                        "a_Common" -> R.mipmap.ic_helmet_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_helmet_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_helmet_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_helmet_mythical_foreground
                        else -> R.mipmap.ic_helmet_legendary_foreground
                    }
                }
            )
        }

        fun setSkillIcon(imageView: ImageView, type: String){
            imageView.setImageResource(
                when(type){
                    "Attack" -> R.mipmap.ic_phys_attack_skill_foreground
                    "FireAttack" -> R.mipmap.ic_phys_defense_up_skill_foreground
                    "IceAttack" -> R.mipmap.ic_phys_defense_up_skill_foreground
                    "ThunderAttack" -> R.mipmap.ic_phys_defense_up_skill_foreground
                    "Critical" -> R.mipmap.ic_phys_defense_up_skill_foreground
                    "PhysDefense" -> R.mipmap.ic_phys_defense_up_skill_foreground
                    "FireDefense" -> R.mipmap.ic_phys_defense_up_skill_foreground
                    "IceDefense" -> R.mipmap.ic_phys_defense_up_skill_foreground
                    "ThunderDefense" -> R.mipmap.ic_phys_defense_up_skill_foreground
                    "Resistance" -> R.mipmap.ic_phys_defense_up_skill_foreground
                    "Harvest" -> R.mipmap.ic_phys_defense_up_skill_foreground
                    else -> R.mipmap.ic_phys_defense_up_skill_foreground //TODO: Crafting icon here
                })
        }

        fun setSkillRankIcon(imageView: ImageView, value: String){
            imageView.setImageResource(
                when(value){
                    "Checked" -> R.mipmap.ic_helmet_legendary_foreground
                    "Empty" -> R.mipmap.ic_helmet_common_foreground
                    else -> R.mipmap.ic_helmet_mythical_foreground
                }
            )
        }

        fun setSkillIconInfo(type: String) : Int{
            var iconToReturn = when(type){
                "Attack" -> R.mipmap.ic_phys_attack_skill_foreground
                "FireAttack" -> R.mipmap.ic_phys_defense_up_skill_foreground
                "IceAttack" -> R.mipmap.ic_phys_defense_up_skill_foreground
                "ThunderAttack" -> R.mipmap.ic_phys_defense_up_skill_foreground
                "Critical" -> R.mipmap.ic_phys_defense_up_skill_foreground
                "PhysDefense" -> R.mipmap.ic_phys_defense_up_skill_foreground
                "FireDefense" -> R.mipmap.ic_phys_defense_up_skill_foreground
                "IceDefense" -> R.mipmap.ic_phys_defense_up_skill_foreground
                "ThunderDefense" -> R.mipmap.ic_phys_defense_up_skill_foreground
                "Resistance" -> R.mipmap.ic_phys_defense_up_skill_foreground
                "Harvest" -> R.mipmap.ic_phys_defense_up_skill_foreground
                else -> R.mipmap.ic_phys_defense_up_skill_foreground //TODO: Crafting icon here
            }
            return iconToReturn
        }
    }
}