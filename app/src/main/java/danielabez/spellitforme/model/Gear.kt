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
    var rarity: String,
    var physDefense: Long,
    var fireDefense: Long,
    var iceDefense: Long,
    var thunderDefense: Long
    //var skills: List<Skill>,
    //var slots: ???
    ): Parcelable {
        constructor(name: String, type: String, rarity: String, physDefense: Long, fireDefense: Long, iceDefense: Long, thunderDefense: Long):
                this(null, name, type, rarity, physDefense, fireDefense, iceDefense, thunderDefense)

        override fun equals(objeto: Any?): Boolean {
            return (objeto is Gear) && (this.id == objeto?.id)
        }

        override fun toString(): String {
            return "" + name + " " + type + " " + physDefense + " " + fireDefense + " " + iceDefense + " " + thunderDefense
        }
    }
