package danielabez.spellitforme.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "registered_user")
@Parcelize
data class RegisteredUser (
    //Declaramos los atributos que tendrá la tabla, así como propiedades adicionales de cada uno de ellos, cuando sea necesario
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var mail: String,
    var username: String,
    var password: String
): Parcelable {
    /*
    * Constructor de la entidad, creará una entrada con los datos introducidos y el identificador autogenerado
    */
    constructor(mail: String, username: String, password: String): this(null, mail, username, password)

    /*
    * Sobreescritura del método comparador de objetos, que comprobará si dos RegisteredUsers son el mismo
    */
    override fun equals(objeto: Any?): Boolean {
        return (objeto is RegisteredUser) && (this.id == objeto?.id)
    }
}