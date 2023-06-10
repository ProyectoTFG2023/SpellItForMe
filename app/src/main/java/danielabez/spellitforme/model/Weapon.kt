package danielabez.spellitforme.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import danielabez.spellitforme.db.DataConverters
import kotlinx.parcelize.Parcelize

@Entity(tableName = "weapon")
@Parcelize
data class Weapon (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var name: String,
    var type: String,
    var rarity: String,
    var physAttack: Long,
    var fireAttack: Long?,
    var iceAttack: Long?,
    var thunderAttack: Long?,
    var critRate: Int,
    @TypeConverters(DataConverters::class)
    var skills: List<Skill>,
    var slots: Int
): Parcelable {
    constructor(name: String, type: String, rarity: String, physAttack: Long, critRate: Int):
            this(null, name, type, rarity, physAttack, null, null, null, critRate, listOf(), 0)

    constructor(name: String, type: String, rarity: String, physAttack: Long, fireAttack: Long?, iceAttack: Long?, thunderAttack: Long?, critRate: Int):
            this(null, name, type, rarity, physAttack, fireAttack, iceAttack, thunderAttack, critRate, listOf(), 0)

    constructor(name: String, type: String, rarity: String, physAttack: Long, critRate: Int, skills: List<Skill>):
            this(null, name, type, rarity, physAttack, null, null, null, critRate, skills, 0)

    constructor(name: String, type: String, rarity: String, physAttack: Long, fireAttack: Long?, iceAttack: Long?, thunderAttack: Long?, critRate: Int, skills: List<Skill>):
            this(null, name, type, rarity, physAttack, fireAttack, iceAttack, thunderAttack, critRate, skills, 0)

    constructor(name: String, type: String, rarity: String, physAttack: Long, critRate: Int, slots: Int):
            this(null, name, type, rarity, physAttack, null, null, null, critRate, listOf(), slots)

    constructor(name: String, type: String, rarity: String, physAttack: Long, fireAttack: Long?, iceAttack: Long?, thunderAttack: Long?, critRate: Int, slots: Int):
            this(null, name, type, rarity, physAttack, fireAttack, iceAttack, thunderAttack, critRate, listOf(), slots)

    constructor(name: String, type: String, rarity: String, physAttack: Long, critRate: Int, skills: List<Skill>, slots: Int):
            this(null, name, type, rarity, physAttack, null, null, null, critRate, skills, slots)

    constructor(name: String, type: String, rarity: String, physAttack: Long, fireAttack: Long?, iceAttack: Long?, thunderAttack: Long?, critRate: Int, skills: List<Skill>, slots: Int):
            this(null, name, type, rarity, physAttack, fireAttack, iceAttack, thunderAttack, critRate, skills, slots)

    override fun equals(objeto: Any?): Boolean {
        return (objeto is Weapon) && (this.id == objeto?.id)
    }

    override fun toString(): String {
        return "$name $type $physAttack $fireAttack $iceAttack $thunderAttack"
    }
}
