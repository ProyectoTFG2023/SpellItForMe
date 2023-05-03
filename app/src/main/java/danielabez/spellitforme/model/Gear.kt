package danielabez.spellitforme.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "gear")
@Parcelize
data class Gear (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var name: String,
    var type: String,
    var physDefense: Long,
    var elemDefense: Long
    //var skills: List<Skill>,
    //var slots: ???
    ): Parcelable {
        constructor(name: String, type: String, physDefense: Long, elemDefense: Long):
                this(null, name, type, physDefense, elemDefense)

        override fun equals(objeto: Any?): Boolean {
            return (objeto is Gear) && (this.id == objeto?.id)
        }

        override fun toString(): String {
            return "" + name + " " + type + " " + physDefense + " " + elemDefense
        }
    }
