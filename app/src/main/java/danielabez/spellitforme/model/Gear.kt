package danielabez.spellitforme.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import danielabez.spellitforme.db.DataConverters
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
    var thunderDefense: Long,
    @TypeConverters(DataConverters::class)
    var skills: List<Skill>,
    var slots: Int
    ): Parcelable {
        constructor(name: String, type: String, rarity: String, physDefense: Long, fireDefense: Long, iceDefense: Long, thunderDefense: Long):
                this(null, name, type, rarity, physDefense, fireDefense, iceDefense, thunderDefense, listOf(), 0)

        constructor(name: String, type: String, rarity: String, physDefense: Long, fireDefense: Long, iceDefense: Long, thunderDefense: Long, slots: Int):
            this(null, name, type, rarity, physDefense, fireDefense, iceDefense, thunderDefense, listOf(), slots)

        constructor(name: String, type: String, rarity: String, physDefense: Long, fireDefense: Long, iceDefense: Long, thunderDefense: Long, skills: List<Skill>):
            this(null, name, type, rarity, physDefense, fireDefense, iceDefense, thunderDefense, skills, 0)

        constructor(name: String, type: String, rarity: String, physDefense: Long, fireDefense: Long, iceDefense: Long, thunderDefense: Long, skills: List<Skill>, slots: Int):
            this(null, name, type, rarity, physDefense, fireDefense, iceDefense, thunderDefense, skills, slots)

        override fun equals(objeto: Any?): Boolean {
            return (objeto is Gear) && (this.id == objeto?.id)
        }

        override fun toString(): String {
            return "" + name + " " + type + " " + physDefense + " " + fireDefense + " " + iceDefense + " " + thunderDefense
        }
    }
