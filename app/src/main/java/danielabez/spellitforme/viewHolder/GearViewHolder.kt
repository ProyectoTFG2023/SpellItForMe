package danielabez.spellitforme.viewHolder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import danielabez.spellitforme.databinding.ItemGearBinding
import danielabez.spellitforme.model.Gear

class GearViewHolder(val binding: ItemGearBinding, val gearList : List<Gear>?): RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener(){
            val chosenGear = gearList?.get(this.adapterPosition)
            Log.d("SpellItForMe_Debug", "Check funcionamiento")
        }
    }
}