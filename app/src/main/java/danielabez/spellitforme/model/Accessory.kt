package danielabez.spellitforme.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "accessory")
@Parcelize
data class Accessory (
    @PrimaryKey(autoGenerate = true)
    var accessoryId: Long? = null,
    var registeredUserOwnerId: Long? = null,
    var accesoryGear: Gear,
    var accesoryType: String
    ) : Parcelable {
        constructor(accesoryGear: Gear, accesoryType: String) : this(null, null, accesoryGear, accesoryType)

        constructor(registeredUserOwnerId: Long?, accessoryGear: Gear, accessoryType: String) : this(null, registeredUserOwnerId, accessoryGear, accessoryType)
    }