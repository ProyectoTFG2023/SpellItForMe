package danielabez.spellitforme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.ItemGearBinding
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.tools.IconHelper

class GearAdapter : RecyclerView.Adapter<GearAdapter.GearViewHolder>() {
    var gearList: List<Gear>? = null
    var onGearClickListener : OnGearClickListener? = null

    inner class GearViewHolder(val binding: ItemGearBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(){
                val chosenGear = gearList?.get(this.adapterPosition)
                onGearClickListener?.onGearClick(chosenGear)
            }
        }
    }

    /*
    * Este método permite al RecyclerView crear un nuevo ViewHolder cuando lo necesite. Tenemos que indicarle el layout que necesitará para "inflarlo", es decir,
    * el layout de los elementos que va a contener, que en nuestro caso es item_tarea.xml. Hay varias formas de hacerlo, pero nosotros usaremos el binding
    * de la siguiente forma:
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GearViewHolder {
        val binding = ItemGearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GearViewHolder(binding)
    }

    /*
    * Método al que llama el RecyclerView cada vez que necesita dibujar un elemento. Le pasamos un ViewHolder que tendremos que completar con los datos de la tarea,
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

                if(slots > 0){
                    binding.ivSlots.visibility = View.VISIBLE
                    binding.tvNumberOfSlots.visibility = View.VISIBLE
                    binding.tvNumberOfSlots.text = "x$slots"
                } else {
                    binding.tvNumberOfSlots.visibility = View.GONE
                    binding.ivSlots.visibility = View.GONE
                }

                if(skills.isNotEmpty()){
                    binding.llytGearBottomMarginDummy.visibility = View.GONE
                    binding.llytGearSkills.visibility = View.VISIBLE
                    binding.tvGearSkillOne.text = skills[0].name
                    IconHelper.setSkillIcon(binding.ivGearSkillOne, skills[0].type)

                    if(skills.size == 2){
                        binding.clytGearSkillTwo.visibility = View.VISIBLE
                        binding.tvGearSkillTwo.text = skills[1].name
                        IconHelper.setSkillIcon(binding.ivGearSkillTwo, skills[1].type)
                    } else {
                        binding.clytGearSkillTwo.visibility = View.GONE
                    }
                } else {
                    binding.llytGearSkills.visibility = View.GONE
                    binding.llytGearBottomMarginDummy.visibility = View.VISIBLE
                }

                //Mostraremos un icono según el tipo de equipamiento y la rareza que tenga
                IconHelper.setGearIcon(binding.ivGear, type, rarity)
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

    interface OnGearClickListener{
        fun onGearClick(gear: Gear?)
    }
}