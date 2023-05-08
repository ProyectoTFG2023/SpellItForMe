package danielabez.spellitforme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import danielabez.spellitforme.databinding.ItemGearBinding
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.viewHolder.GearViewHolder

class GearAdapter : RecyclerView.Adapter<GearViewHolder>() {
    var gearList: List<Gear>? = null

    /*
    * Este método permite al RecyclerView crear un nuevo ViewHolder cuando lo necesite. Tenemos que indicarle el layout que necesitará para "inflarlo", es decir,
    * el layout de los elementos que va a contener, que en nuestro caso es item_tarea.xml. Hay varias formas de hacerlo, pero nosotros usaremos el binding
    * de la siguiente forma:
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GearViewHolder {
        val binding = ItemGearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GearViewHolder(binding, gearList)
    }

    /*
    * Método al que llama el RecyclerView cada vez que necesita dubujar un elemento. Le pasamos un ViewHolder que tendremos que completar con los datos de la tarea,
    * indicada por el int que indica la posición de dicha tarea en la lista que esté contenida
    */
    override fun onBindViewHolder(holder: GearViewHolder, position: Int) {
        with(holder) {
            //Primero, obtenemos el objeto Gear de la posición que se pase por parámetro y extraeremos sus valores para poder colocarlos en el ViewHolder
            with(gearList!!.get(position)) {
                binding.tvGearName.text = name
                binding.tvGearPhysDefense.text = physDefense.toString()
                binding.tvGearFireDefense.text = fireDefense.toString()
                binding.tvGearIceDefense.text = iceDefense.toString()
                binding.tvGearThunderDefense.text = thunderDefense.toString()

                //Mostraremos un icono según el tipo de equipamiento y la rareza que tenga
                //TODO: PENDIENTE DE HACER
                /*
                binding.ivItemEstado.setImageResource(
                    when (estado) {
                        0 -> R.drawable.ic_tarea_abierta
                        1 -> R.drawable.ic_tarea_en_progreso
                        else -> R.drawable.ic_tarea_terminada
                    }
                )
                */
            }
        }
    }

    /*
    * Con este método, permitimos al RecyclerView conocer el tamaño de la lista
    */
    override fun getItemCount(): Int {
        //Con el elemento ?: se asigna un valor por defecto a la variable en caso de que su previo valor fuese nulo, en este caso se iguala a 0 para evitar los NullPointerException
        return gearList?.size?: 0
    }

    fun updateList(newList: List<Gear>){
        gearList = newList
        notifyDataSetChanged()
    }
}