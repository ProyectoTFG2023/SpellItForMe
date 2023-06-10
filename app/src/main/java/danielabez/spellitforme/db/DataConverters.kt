package danielabez.spellitforme.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import danielabez.spellitforme.model.Accessory
import danielabez.spellitforme.model.CharacterStats
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.model.Skill
import danielabez.spellitforme.model.Weapon
import danielabez.spellitforme.model.relations.RegisteredUserWithAccessories
import kotlinx.coroutines.GlobalScope
import java.lang.reflect.Type

class DataConverters {
    @TypeConverter
    fun skillListToString(value: List<Skill>) : String {
        val gson = Gson()
        val type = object: TypeToken<List<Skill>>(){}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToSkillList(value: String): List<Skill> {
        val gson = Gson()
        val type = object : TypeToken<List<Skill>>(){}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun weaponToString(value: Weapon) : String {
        val gson = Gson()
        val type = object : TypeToken<Weapon>(){}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToWeapon(value: String) : Weapon {
        val gson = Gson()
        val type = object : TypeToken<Weapon>(){}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun stringListToString(value: List<String>) : String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>(){}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToStringList(value: String) : List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>(){}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun floatListToString(value: List<Float>) : String {
        val gson = Gson()
        val type = object : TypeToken<List<Float>>(){}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToFloatList(value: String) : List<Float> {
        val gson = Gson()
        val type = object : TypeToken<List<Float>>(){}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun gearToString(value : Gear) : String {
        val gson = Gson()
        val type = object: TypeToken<Gear>(){}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToGear(value: String) : Gear {
        val gson = Gson()
        val type = object: TypeToken<Gear>(){}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun gearMutableListToString(value: MutableList<Gear>) : String {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Gear>>(){}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToGearMutableList(value: String) : MutableList<Gear> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Gear>>(){}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun accessoryToString(value: Accessory) : String {
        val gson = Gson()
        val type = object : TypeToken<Accessory>(){}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToAccessory(value: String) : Accessory {
        val gson = Gson()
        val type = object : TypeToken<Accessory>(){}.type
        return gson.fromJson(value, type)
    }

    /*
    @TypeConverter
    fun accessoryListToString(value: List<Accessory>) : String {
        val gson = Gson()
        val type = object : TypeToken<List<Accessory>>(){}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToAccessoryList(value: String) : List<Accessory> {
        val gson = Gson()
        val type = object : TypeToken<List<Accessory>>(){}.type
        return gson.fromJson(value, type)
    }

     */

    @TypeConverter
    fun accessoryMutableListToString(value: MutableList<Accessory>) : String {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Accessory>>(){}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToAccessoryMutableList(value: String) : MutableList<Accessory> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Accessory>>(){}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun registeredUserWithAccesoriesListToString(value: List<RegisteredUserWithAccessories>) : String {
        val gson = Gson()
        val type = object : TypeToken<List<RegisteredUserWithAccessories>>(){}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToRegisteredUserWithAccessoriesList(value: String) : List<RegisteredUserWithAccessories> {
        val gson = Gson()
        val type = object : TypeToken<List<RegisteredUserWithAccessories>>(){}.type
        return gson.fromJson(value, type)
    }
    @TypeConverter
    fun characterStatsToString(value: CharacterStats) : String {
        val gson = Gson()
        val type = object : TypeToken<CharacterStats>(){}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToCharacterStats(value: String) : CharacterStats {
        val gson = Gson()
        val type = object : TypeToken<CharacterStats>(){}.type
        return gson.fromJson(value, type)
    }
}