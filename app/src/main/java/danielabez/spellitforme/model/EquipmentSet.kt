/*
package danielabez.spellitforme.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "equipment_set")
@Parcelize
class EquipmentSet (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var name: String,
    var role: String,

): Parcelable {
    constructor(name: String) : this(null, name)

    override fun equals(objeto: Any?): Boolean {
        return (objeto is EquipmentSet) && (this.id == objeto?.id)
    }
}
*/