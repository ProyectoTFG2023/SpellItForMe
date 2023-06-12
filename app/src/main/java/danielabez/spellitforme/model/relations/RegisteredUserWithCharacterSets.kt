package danielabez.spellitforme.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import danielabez.spellitforme.model.CharacterSet
import danielabez.spellitforme.model.RegisteredUser

data class RegisteredUserWithCharacterSets (
    @Embedded val registeredUser: RegisteredUser,
    @Relation(
        parentColumn = "id",
        entityColumn = "registeredUserOwnerId")
    val characterSets: List<CharacterSet>
    )