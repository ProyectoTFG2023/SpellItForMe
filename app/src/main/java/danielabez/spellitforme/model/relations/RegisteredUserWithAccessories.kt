package danielabez.spellitforme.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import danielabez.spellitforme.model.Accessory
import danielabez.spellitforme.model.RegisteredUser

data class RegisteredUserWithAccessories (
    @Embedded val registeredUser : RegisteredUser,
    @Relation(
        parentColumn = "id",
        entityColumn = "registeredUserOwnerId")
    val accesories: List<Accessory>
    )