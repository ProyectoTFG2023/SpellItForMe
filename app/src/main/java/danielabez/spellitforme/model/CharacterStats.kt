package danielabez.spellitforme.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CharacterStats (
    var health: Long,
    var spiritum: Long,
    var strength: Long,
    var attunement: Long,
    var cunning: Long
): Parcelable {
    override fun toString(): String {
        return "HP: $health SP: $spiritum STR: $strength Att: $attunement Cunning: $cunning"
    }
}