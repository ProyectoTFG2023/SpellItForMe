package danielabez.spellitforme.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SkillOnSet (
    var skill: Skill,
    var currentRank: Int
) : Parcelable {
    override fun toString(): String {
        return "${skill.name}: Current rank ->  $currentRank"
    }
}