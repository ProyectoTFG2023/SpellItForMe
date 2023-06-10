package danielabez.spellitforme.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "character_set")
@Parcelize
class CharacterSet (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var name: String,
    var role: String,
    var stats: CharacterStats,
    var equippedWeapon : Weapon? = null,
    val equipmentPieces: MutableList<Gear?> = mutableListOf(null, null, null, null, null),
    val equippedAccessories : MutableList<Accessory?> = mutableListOf(null, null),
    var slottedSkills : MutableList<Skill?> = mutableListOf()

): Parcelable {
    constructor(name: String, role: String, stats: CharacterStats) : this(null, name, role, stats)

    override fun equals(objeto: Any?): Boolean {
        return (objeto is CharacterSet) && (this.id == objeto?.id)
    }
}