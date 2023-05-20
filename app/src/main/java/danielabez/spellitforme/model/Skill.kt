package danielabez.spellitforme.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "skill")
@Parcelize
data class Skill (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var name: String,
    var type: String,
    var description: String,
    var maxRank: Int
): Parcelable {
    constructor() : this(null, "", "", "", 0)

    constructor(name: String, description: String, type: String, maxRank: Int) : this(null, name, description, type, maxRank)

    override fun equals(objeto: Any?): Boolean {
        return (objeto is Skill) && (this.id == objeto?.id)
    }

    override fun toString(): String {
        return "" + name + ": " + description + ", up to level " + maxRank
    }
}