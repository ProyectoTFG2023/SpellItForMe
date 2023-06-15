package danielabez.spellitforme.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import danielabez.spellitforme.db.dao.AccessoryDao
import danielabez.spellitforme.db.dao.CharacterSetDao
import danielabez.spellitforme.db.dao.GearDao
import danielabez.spellitforme.db.dao.RegisteredUserDao
import danielabez.spellitforme.db.dao.SkillDao
import danielabez.spellitforme.db.dao.WeaponDao
import danielabez.spellitforme.model.Accessory
import danielabez.spellitforme.model.CharacterSet
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.model.RegisteredUser
import danielabez.spellitforme.model.Skill
import danielabez.spellitforme.model.Weapon
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//En esta anotación, se incluyen todas las clases que tengan que verse plasmadas en forma de tablas
@Database(entities = arrayOf(RegisteredUser::class, CharacterSet::class, Skill::class, Gear::class, Weapon::class, Accessory::class), version = 1, exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun registeredUserDao(): RegisteredUserDao
    abstract fun characterSetDao(): CharacterSetDao
    abstract fun skillDao(): SkillDao
    abstract fun gearDao(): GearDao
    abstract fun weaponDao(): WeaponDao

    abstract fun accessoryDao(): AccessoryDao

    //Mediante un patrón Singleton, se genera e inicializa la base de datos
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /*
        * Función encargada de recuperar la base de datos o de crearla, en caso de ser llamada por primera vez por la aplicación
        */
        fun getDatabase(context: Context) : AppDatabase {
            //Si hay una instancia generada, se devuelve. En caso contrario, se genera y se devuelve
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database").addCallback(StartDbCallBack()).build()
                INSTANCE = instance
                instance
            }
        }

        //Clase personalizada creada a partir de una plantilla aportada por Room para inicializar la base de datos de forma específica
        private class StartDbCallBack(): RoomDatabase.Callback() {
            /*
            * Método que crea la base de datos cuando es llamado a la hora de crear una instancia de la clase
            */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                //Si la base de datos ya se encuentra generada, se inicializa con unos valores por defecto
                INSTANCE?.let { database ->
                    GlobalScope.launch {
                        initializeDatabase(database.registeredUserDao(), database.characterSetDao(), database.skillDao(), database.gearDao(), database.weaponDao(), database.accessoryDao())
                    }
                }
            }

            /*
            * Método que introduce una serie de valores por defecto a las tablas cuando la base de datos es generada por primera vez
            */
            suspend fun initializeDatabase(registeredUserDao: RegisteredUserDao, characterSetDao: CharacterSetDao, skillDao: SkillDao, gearDao: GearDao, weaponDao: WeaponDao, accessoryDao: AccessoryDao){
                registeredUserDao.addRegisteredUser(RegisteredUser("correo@gmail.com", "admin", "admin"))
                registeredUserDao.addRegisteredUser(RegisteredUser("Mikey_Snow_Player73@gmail.com", "Mike", "McMike"))
                registeredUserDao.addRegisteredUser(RegisteredUser("Sunny_Winter2@gmail.com", "Sunny Smiles", "Cheyenne"))

                skillDao.addSkill(Skill("Atk Up",
                    "Attack",
                    "Increases the weapon's base physical attack damage",
                    listOf("Increases physical damage by 5%", "Increases physical damage by 10%", "Increases physical damage by 25%"),
                    listOf(5.0F, 10.0F, 25.0F),
                    3))
                skillDao.addSkill(Skill("Peak Form",
                    "Attack",
                    "Increases physical attack damage when at full HP",
                    listOf("Increases physical attack by 10 points", "Increases physical attack by 20 points", "Increases physical attack by 40 points"),
                    listOf(10.0F, 20.0F, 40.0F),
                    3))
                skillDao.addSkill(Skill("Fire Atk Up",
                    "FireAttack",
                    "Increases the weapon's fire attack damage",
                    listOf("Increases fire damage by 5%", "Increases fire damage by 15%", "Increases fire damage by 30%", "Increases fire damage by 50%", "Increases fire damage by 75%"),
                    listOf(5.0F, 15.0F, 30.0F, 50.0F, 75.0F),
                    5))
                skillDao.addSkill(Skill("Ice Atk Up",
                    "IceAttack",
                    "Increases the weapon's ice attack damage",
                    listOf("Increases ice damage by 5%", "Increases ice damage by 15%", "Increases ice damage by 30%", "Increases ice damage by 50%", "Increases ice damage by 75%"),
                    listOf(5.0F, 15.0F, 30.0F, 50.0F, 75.0F),
                    5))
                skillDao.addSkill(Skill("Thunder Atk Up",
                    "ThunderAttack",
                    "Increases the weapon's thunder attack damage",
                    listOf("Increases thunder damage by 5%", "Increases thunder damage by 15%", "Increases thunder damage by 30%", "Increases thunder damage by 50%", "Increases thunder damage by 75%"),
                    listOf(5.0F, 15.0F, 30.0F, 50.0F, 75.0F),
                    5))
                skillDao.addSkill(Skill("Critical Up",
                    "Critical",
                    "Increases critical chance",
                    listOf("Increases critical chance by 2%", "Increases critical chance by 5%", "Increases critical chance by 10%", "Increases critical chance by 18%", "Increases critical chance by 30%"),
                    listOf(2.0F, 5.0F, 10.0F, 18.0F, 30.0F),
                    5))
                skillDao.addSkill(Skill("Physical Def Up",
                    "PhysDefense",
                    "Increases physical defense",
                    listOf("Increases physical defense by 10 points", "Increases physical defense by 20 points", "Increases physical defense by 35 points", "Increases physical defense by 60 points", "Increases physical defense by 100 points"),
                    listOf(10F, 20F, 35F, 60F, 100F),
                    5))
                skillDao.addSkill(Skill("Fire Def Up",
                    "FireDefense",
                    "Increases resistance to fire damage",
                    listOf("Increases fire defense by 20 points", "Increases fire defense by 40 points", "Increases fire defense by 65 points", "Increases fire defense by 100 points", "Increases fire defense by 150 points"),
                    listOf(20F, 40F, 65F, 100F, 150F),
                    5))
                skillDao.addSkill(Skill("Ice Def Up",
                    "IceDefense",
                    "Increases resistance to ice damage",
                    listOf("Increases ice defense by 20 points", "Increases ice defense by 40 points", "Increases ice defense by 65 points", "Increases ice defense by 100 points", "Increases ice defense by 150 points"),
                    listOf(20F, 40F, 65F, 100F, 150F),
                    5))
                skillDao.addSkill(Skill("Thunder Def Up",
                    "ThunderDefense",
                    "Increases resistance to thunder damage",
                    listOf("Increases thunder defense by 20 points", "Increases thunder defense by 40 points", "Increases thunder defense by 65 points", "Increases thunder defense by 100 points", "Increases thunder defense by 150 points"),
                    listOf(20F, 40F, 65F, 100F, 150F),
                    5))
                skillDao.addSkill(Skill("Stun Resistance",
                    "Resistance",
                    "Increases resistance to the stun ailment",
                    listOf("Increased resistance to stun by 20%", "Increased resistance to stun by 50%", "The next stun ailment is nullified. Cooldown of 15 seconds"),
                    listOf(),
                    3))
                skillDao.addSkill(Skill("Geologist's Touch",
                    "Harvest",
                    "Increases number of times an ore vein can be mined for materials",
                    listOf("Allows for one more mining try per ore vein", "Allows for two more mining tries per ore vein", "Allows for three more mining tries per ore vein"),
                    listOf(),
                    3))
                skillDao.addSkill(Skill("Herbalist's Wit",
                    "Crafting",
                    "Decreases number of herbs required on medicinal recipes",
                    listOf("Decreases by a fifth the herbs required for recipes", "Decreases by a third the herbs required for recipes", "Decreases to a half the herbs required for recipes"),
                    listOf(),
                    3))

                weaponDao.addWeapon(Weapon("Rusted Sword", "a_Sword", "a_Common", 30L, 0))
                weaponDao.addWeapon(Weapon("Refined Steel Sword", "a_Sword", "b_Uncommon", 55L, 10))
                weaponDao.addWeapon(Weapon("Thunder's Cry", "a_Sword", "c_Rare", 70L, null, null, 100L, 10))
                weaponDao.addWeapon(Weapon("Volcanic Edge", "a_Sword", "d_Mythical", 90L, 200L, 0L, 0L, 30))
                weaponDao.addWeapon(Weapon("Weep's Lament", "a_Sword", "e_Legendary", 150L, null, 150L, null, 15,
                    listOf(skillDao.getSkillByName("Ice Def Up")))
                )

                weaponDao.addWeapon(Weapon("Iron Spear", "b_Spear", "a_Common", 25L, 0))
                weaponDao.addWeapon(Weapon("Fiery Pike", "b_Spear", "b_Uncommon", 35L, 65L, null, null, 5))
                weaponDao.addWeapon(Weapon("Armorpiercer", "b_Spear", "c_Rare", 70L, 10, 1))
                weaponDao.addWeapon(Weapon("Frozen Knight's Icicle", "b_Spear", "d_Mythical", 120L, null, 200L, null, 0))
                weaponDao.addWeapon(Weapon("Keraunos", "b_Spear", "e_Legendary", 120L, null, null, 300L, 10,
                    listOf(skillDao.getSkillByName("Peak Form"),
                        skillDao.getSkillByName("Thunder Def Up")),
                    1
                ))

                gearDao.addGear(Gear("Iron Helmet", "Headgear", "a_Common", 20L, 10L, 10L, 0L,
                    listOf(skillDao.getSkillByName("Physical Def Up"))
                ))
                gearDao.addGear(Gear("Steel Helmet", "Headgear", "b_Uncommon", 30L, 15L, 10L, 5L,
                    listOf(skillDao.getSkillByName("Physical Def Up")),
                    1
                ))
                gearDao.addGear(Gear("Reinforced Steel Helmet", "Headgear", "c_Rare", 45L, 30L, 20L, 10L,
                    listOf(skillDao.getSkillByName("Physical Def Up"),
                    skillDao.getSkillByName("Stun Resistance")),
                    2
                ))
                gearDao.addGear(Gear("Miner's Impeccable Goggles", "Headgear", "d_Mythical", 20L, 50L, 30L, 35L,
                    listOf(skillDao.getSkillByName("Atk Up"),
                    skillDao.getSkillByName("Geologist's Touch")),
                    2
                ))
                gearDao.addGear(Gear("Dragon's Circlet", "Headgear", "e_Legendary", 20L, 150L, 100L, 100L,
                    listOf(skillDao.getSkillByName("Fire Atk Up"), skillDao.getSkillByName("Fire Def Up")),
                    2
                ))

                gearDao.addGear(Gear("Sack Vest", "Torso", "a_Common", 0L, 0L, 30L, 20L, 1))
                gearDao.addGear(Gear("Bone Armor", "Torso", "b_Uncommon", 50L, 40L, 0L, 10L,
                    listOf(skillDao.getSkillByName("Atk Up"))
                ))
                gearDao.addGear(Gear("Plated Armor", "Torso", "c_Rare", 100L, 60L, 50L, 50L,
                    listOf(skillDao.getSkillByName("Critical Up"))
                ))
                gearDao.addGear(Gear("Aurora's Mantle", "Torso", "d_Mythical", 0L, 40L, 120L, 60L,
                    listOf(skillDao.getSkillByName("Ice Atk Up"),
                    skillDao.getSkillByName("Ice Def Up")),
                    1
                ))
                gearDao.addGear(Gear("Cloth from the Old Ones", "Torso", "e_Legendary", 25L, 150L, 150L, 150L,
                    listOf(skillDao.getSkillByName("Atk Up"),
                        skillDao.getSkillByName("Peak Form"))
                ))

                gearDao.addGear(Gear("Plain Gloves", "Handwear", "a_Common", 0L, 0L, 40L, 20L, 1))
                gearDao.addGear(Gear("Bronze Bracelet", "Handwear", "b_Uncommon", 0L, 60L, 60L, 60L))
                gearDao.addGear(Gear("Pugilist's Gloves", "Handwear", "c_Rare", 20L, 10L, 40L, 10L,
                    listOf(skillDao.getSkillByName("Atk Up"), skillDao.getSkillByName("Critical Up")),
                    1
                ))
                gearDao.addGear(Gear("The Ancient's Handwraps", "Handwear", "d_Mythical", 10L, 80L, 80L, 80L,
                    listOf(skillDao.getSkillByName("Peak Form")),
                    2
                ))
                gearDao.addGear(Gear("Un'ael's Negotiators", "Handwear", "e_Legendary", 150L, 130L, 130L, 130L,
                    listOf(skillDao.getSkillByName("Physical Def Up"), skillDao.getSkillByName("Fire Def Up"))
                ))

                gearDao.addGear(Gear("Tattered Rope", "Belt", "a_Common", 0L, 0L, 0L, 0L, 1))
                gearDao.addGear(Gear("Reinforced Leather Belt", "Belt", "b_Uncommon", 10L, 30L, 30L, 40L,
                    listOf(skillDao.getSkillByName("Stun Resistance"))
                ))
                gearDao.addGear(Gear("Gardener's Pride", "Belt", "c_Rare", 20L, 20L, 60L, 50L,
                    listOf(skillDao.getSkillByName("Herbalist's Wit")),
                    1
                ))
                gearDao.addGear(Gear("Prisoner's Chain", "Belt", "d_Mythical", 50L, 60L, 20L, 50L,
                    listOf(skillDao.getSkillByName("Critical Up"), skillDao.getSkillByName("Stun Resistance")),
                    2
                ))
                gearDao.addGear(Gear("Cosmic Chain", "Belt", "e_Legendary", 70L, 140L, 140L, 140L,
                    listOf(skillDao.getSkillByName("Physical Def Up"), skillDao.getSkillByName("Geologist's Touch")),
                    3
                ))

                gearDao.addGear(Gear("Simple Boots", "Footwear", "a_Common", 20L, 5L, 10L, 5L))
                gearDao.addGear(Gear("Desert Sandals", "Footwear", "b_Uncommon", 15L, 40L, 0L, 20L,
                    listOf(skillDao.getSkillByName("Fire Def Up"))
                ))
                gearDao.addGear(Gear("Royal Guard Boots", "Footwear", "c_Rare", 50L, 40L, 30L, 40L, 1))
                gearDao.addGear(Gear("Lava-forged Boots", "Footwear", "c_Rare", 50L, 120L, 20L, 30L,
                    listOf(skillDao.getSkillByName("Fire Atk Up"), skillDao.getSkillByName("Fire Def Up"))
                ))
                gearDao.addGear(Gear("Un'ael's Skull Crushers", "Footwear", "e_Legendary", 125L, 100L, 100L, 100L,
                    listOf(skillDao.getSkillByName("Fire Def Up"), skillDao.getSkillByName("Fire Def Up")),
                    1
                ))

                accessoryDao.addAccessory(Accessory(
                    2L,
                    Gear("Steeled Necklace", "Accessory", "a_Common", 30L, 10L, 5L, 5L, 1),
                    "a_Necklace"
                ))
                accessoryDao.addAccessory(Accessory(
                    2L,
                    Gear("Elemental Ring", "Accessory", "b_Uncommon", 0L, 40L, 40L, 40L,1),
                    "b_Ring"
                ))
                accessoryDao.addAccessory(Accessory(
                    2L,
                    Gear("Storm's Ring", "Accessory", "d_Mythical", 0L, 30L, 15L, 150L,
                        listOf(skillDao.getSkillByName("Thunder Atk Up"), skillDao.getSkillByName("Thunder Atk Up")),
                        1),
                    "b_Ring"
                ))
                accessoryDao.addAccessory(Accessory(
                    3L,
                    Gear("Accursed's Locket", "Accessory", "c_Rare", 50L, 30L, 15L, 20L,
                        listOf(skillDao.getSkillByName("Stun Resistance")),
                        1),
                    "a_Necklace"
                ))
                accessoryDao.addAccessory(Accessory(
                    3L,
                    Gear("Un'ael's Trophy", "Accessory", "e_Legendary", 60L, 60L, 60L, 60L,
                        listOf(skillDao.getSkillByName("Atk Up"), skillDao.getSkillByName("Critical Up")),
                        3),
                    "b_Ring"
                ))
            }
        }
    }
}