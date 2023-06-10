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
    var specificDescription: List<String>,
    var rankValueModifiers : List<Float>,
    var maxRank: Int
): Parcelable {
    constructor() : this(null, "", "", "", listOf(), listOf(), 0)

    constructor(name: String, type: String, description: String, specificDescription: List<String>, rankValueModifiers: List<Float>, maxRank: Int) :
            this(null, name, type, description, specificDescription, rankValueModifiers, maxRank)

    override fun equals(objeto: Any?): Boolean {
        return (objeto is Skill) && (this.id == objeto?.id)
    }

    override fun toString(): String {
        return "$name: $description, up to level $maxRank"
    }
}