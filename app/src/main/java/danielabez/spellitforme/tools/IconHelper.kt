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

        fun setWeaponIcon(imageView: ImageView, type: String, rarity: String){
            imageView.setImageResource(
                when(type){
                    "a_Sword" -> when (rarity) {
                        "a_Common" -> R.mipmap.ic_sword_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_sword_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_sword_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_sword_mythical_foreground
                        else -> R.mipmap.ic_sword_legendary_foreground
                    }
                    else -> when (rarity) {
                        "a_Common" -> R.mipmap.ic_spear_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_spear_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_spear_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_spear_mythical_foreground
                        else -> R.mipmap.ic_spear_legendary_foreground
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
                        "a_Common" -> R.mipmap.ic_torso_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_torso_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_torso_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_torso_mythical_foreground
                        else -> R.mipmap.ic_torso_legendary_foreground
                    }
                    "Handwear" -> when (rarity) {
                        "a_Common" -> R.mipmap.ic_handwear_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_handwear_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_handwear_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_handwear_mythical_foreground
                        else -> R.mipmap.ic_handwear_legendary_foreground
                    }
                    "Belt" -> when (rarity) {
                        "a_Common" -> R.mipmap.ic_belt_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_belt_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_belt_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_belt_mythical_foreground
                        else -> R.mipmap.ic_belt_legendary_foreground
                    }
                    else -> when (rarity) {
                        "a_Common" -> R.mipmap.ic_footwear_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_footwear_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_footwear_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_footwear_mythical_foreground
                        else -> R.mipmap.ic_footwear_legendary_foreground
                    }
                }
            )
        }

        fun setAccessoryIcon(imageView: ImageView, type: String, rarity: String){
            imageView.setImageResource(
                when(type){
                    "a_Necklace" -> when(rarity){
                        "a_Common" -> R.mipmap.ic_necklace_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_necklace_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_necklace_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_necklace_mythical_foreground
                        else -> R.mipmap.ic_necklace_legendary_foreground
                    }
                    else -> when(rarity){
                        "a_Common" -> R.mipmap.ic_ring_common_foreground
                        "b_Uncommon" -> R.mipmap.ic_ring_uncommon_foreground
                        "c_Rare" -> R.mipmap.ic_ring_rare_foreground
                        "d_Mythical" -> R.mipmap.ic_ring_mythical_foreground
                        else -> R.mipmap.ic_ring_legendary_foreground
                    }
                }
            )
        }

        fun setSkillIcon(imageView: ImageView, type: String){
            imageView.setImageResource(
                when(type){
                    "Attack" -> R.mipmap.ic_phys_attack_skill_foreground
                    "FireAttack" -> R.mipmap.ic_fire_attack_up_skill_foreground
                    "IceAttack" -> R.mipmap.ic_ice_attack_up_skill_foreground
                    "ThunderAttack" -> R.mipmap.ic_thunder_attack_up_skill_foreground
                    "Critical" -> R.mipmap.ic_critical_up_skill_foreground
                    "PhysDefense" -> R.mipmap.ic_phys_defense_up_skill_foreground
                    "FireDefense" -> R.mipmap.ic_fire_defense_up_skill_foreground
                    "IceDefense" -> R.mipmap.ic_ice_defense_up_skill_foreground
                    "ThunderDefense" -> R.mipmap.ic_thunder_defense_up_skill_foreground
                    "Resistance" -> R.mipmap.ic_resistance_skill_foreground
                    "Harvest" -> R.mipmap.ic_mining_skill_foreground
                    else -> R.mipmap.ic_harvest_skill_foreground
                })
        }

        fun setSkillRankIcon(imageView: ImageView, value: String){
            imageView.setImageResource(
                when(value){
                    "Checked" -> R.mipmap.ic_square_rank_full_foreground
                    "Empty" -> R.mipmap.ic_rank_square_empty_foreground
                    else -> R.mipmap.ic_rank_square_surpassed_foreground
                }
            )
        }

        fun setSkillIconInfo(type: String) : Int{
            var iconToReturn = when(type){
                "Attack" -> R.mipmap.ic_phys_attack_skill_foreground
                "FireAttack" -> R.mipmap.ic_fire_attack_up_skill_foreground
                "IceAttack" -> R.mipmap.ic_ice_attack_up_skill_foreground
                "ThunderAttack" -> R.mipmap.ic_thunder_attack_up_skill_foreground
                "Critical" -> R.mipmap.ic_critical_up_skill_foreground
                "PhysDefense" -> R.mipmap.ic_phys_defense_up_skill_foreground
                "FireDefense" -> R.mipmap.ic_fire_defense_up_skill_foreground
                "IceDefense" -> R.mipmap.ic_ice_defense_up_skill_foreground
                "ThunderDefense" -> R.mipmap.ic_thunder_defense_up_skill_foreground
                "Resistance" -> R.mipmap.ic_resistance_skill_foreground
                "Harvest" -> R.mipmap.ic_mining_skill_foreground
                else -> R.mipmap.ic_harvest_skill_foreground
            }
            return iconToReturn
        }
    }
}