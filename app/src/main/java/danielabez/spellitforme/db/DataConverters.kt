package danielabez.spellitforme.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import danielabez.spellitforme.model.Skill

class DataConverters {
    @TypeConverter
    fun skillListToString(value: List<Skill>) : String {
        val gson = Gson()
        val type = object: TypeToken<List<Skill>>(){}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToSkillList(value: String): List<Skill>{
        val gson = Gson()
        val type = object : TypeToken<List<Skill>>(){}.type
        return gson.fromJson(value, type)
    }
}