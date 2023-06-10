package danielabez.spellitforme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import danielabez.spellitforme.R
import danielabez.spellitforme.databinding.ItemAccessoryBinding
import danielabez.spellitforme.databinding.ItemGearBinding
import danielabez.spellitforme.model.Accessory
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.tools.IconHelper

class AccessoryAdapter : RecyclerView.Adapter<AccessoryAdapter.AccessoryViewHolder>() {
    var accessoryList: List<Accessory>? = null
    var onAccessoryClickListener : OnAccessoryClickListener? = null

    inner class AccessoryViewHolder(val binding: ItemAccessoryBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener(){
                val chosenAccessory = accessoryList?.get(this.adapterPosition)
                onAccessoryClickListener?.onAccessoryClick(chosenAccessory)
            }
        }
    }

    /*
    * Este método permite al RecyclerView crear un nuevo ViewHolder cuando lo necesite. Tenemos que indicarle el layout que necesitará para "inflarlo", es decir,
    * el layout de los elementos que va a contener, que en nuestro caso es item_tarea.xml. Hay varias formas de hacerlo, pero nosotros usaremos el binding
    * de la siguiente forma:
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccessoryViewHolder {
        val binding = ItemAccessoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccessoryViewHolder(binding)
    }

    /*
    * Método al que llama el RecyclerView cada vez que necesita dibujar un elemento. Le pasamos un ViewHolder que tendremos que completar con los datos de la tarea,
    * indicada por el int que indica la posición de dicha tarea en la lista que esté contenida
    */
    override fun onBindViewHolder(holder: AccessoryViewHolder, position: Int) {
        with(holder) {
            //Primero, obtenemos el objeto Gear de la posición que se pase por parámetro y extraeremos sus valores para poder colocarlos en el ViewHolder
            with(accessoryList!!.get(position)) {
                binding.tvAccessoryName.text = accesoryGear.name
                binding.tvAccessoryPhysDefense.text = accesoryGear.physDefense.toString()
                binding.tvAccessoryFireDefense.text = accesoryGear.fireDefense.toString()
                binding.tvAccessoryIceDefense.text = accesoryGear.iceDefense.toString()
                binding.tvAccessoryThunderDefense.text = accesoryGear.thunderDefense.toString()

                if(accesoryGear.slots > 0){
                    binding.ivAccessorySlots.visibility = View.VISIBLE
                    binding.tvAccessoryNumberOfSlots.visibility = View.VISIBLE
                    binding.tvAccessoryNumberOfSlots.text = "x${accesoryGear.slots}"
                } else {
                    binding.tvAccessoryNumberOfSlots.visibility = View.GONE
                    binding.ivAccessorySlots.visibility = View.GONE
                }

                if(accesoryGear.skills.isNotEmpty()){
                    binding.llytAccessoryBottomMarginDummy.visibility = View.GONE
                    binding.llytAccessorySkills.visibility = View.VISIBLE
                    binding.tvAccessorySkillOne.text = accesoryGear.skills[0].name
                    IconHelper.setSkillIcon(binding.ivAccessorySkillOne, accesoryGear.skills[0].type)

                    if(accesoryGear.skills.size == 2){
                        binding.clytAccessorySkillTwo.visibility = View.VISIBLE
                        binding.tvAccessorySkillTwo.text = accesoryGear.skills[1].name
                        IconHelper.setSkillIcon(binding.ivAccessorySkillTwo, accesoryGear.skills[1].type)
                    } else {
                        binding.clytAccessorySkillTwo.visibility = View.GONE
                    }
                } else {
                    binding.llytAccessorySkills.visibility = View.GONE
                    binding.llytAccessoryBottomMarginDummy.visibility = View.VISIBLE
                }

                //Mostraremos un icono según el tipo de equipamiento y la rareza que tenga
                IconHelper.setAccessoryIcon(binding.ivAccessory, accesoryType, accesoryGear.rarity)
            }
        }
    }

    /*
    * Con este método, permitimos al RecyclerView conocer el tamaño de la lista
    */
    override fun getItemCount(): Int {
        //Con el elemento ?: se asigna un valor por defecto a la variable en caso de que su previo valor fuese nulo, en este caso se iguala a 0 para evitar los NullPointerException
        return accessoryList?.size?: 0
    }

    fun updateList(newList: List<Accessory>){
        accessoryList = newList
        notifyDataSetChanged()
    }

    interface OnAccessoryClickListener{
        fun onAccessoryClick(accessory: Accessory?)
    }
}