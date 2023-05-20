package danielabez.spellitforme.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import danielabez.spellitforme.db.dao.GearDao
import danielabez.spellitforme.db.dao.RegisteredUserDao
import danielabez.spellitforme.db.dao.SkillDao
import danielabez.spellitforme.db.dao.WeaponDao
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.model.RegisteredUser
import danielabez.spellitforme.model.Skill
import danielabez.spellitforme.model.Weapon
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//En esta anotación, se incluyen todas las clases que tengan que verse plasmadas en forma de tablas
@Database(entities = arrayOf(RegisteredUser::class, Skill::class, Gear::class, Weapon::class), version = 1, exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun registeredUserDao(): RegisteredUserDao
    abstract fun skillDao(): SkillDao
    abstract fun gearDao(): GearDao
    abstract fun weaponDao(): WeaponDao

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
                        initializeDatabase(database.registeredUserDao(), database.skillDao(), database.gearDao(), database.weaponDao())
                    }
                }
            }

            /*
            * Método que introduce una serie de valores por defecto a las tablas cuando la base de datos es generada por primera vez
            */
            suspend fun initializeDatabase(registeredUserDao: RegisteredUserDao, skillDao: SkillDao, gearDao: GearDao, weaponDao: WeaponDao){
                lateinit var registeredUser: RegisteredUser
                registeredUser = RegisteredUser("correo@gmail.com", "admin", "admin")
                registeredUserDao.addRegisteredUser(registeredUser)

                lateinit var skill: Skill
                skill = Skill("Attack Up", "Increases physical attack damage by 5%/10%/15%/30%", "Attack", 4)
                skillDao.addSkill(skill)
                skill = Skill("Defense Up", "Increases physical defense by 10/20/35/60/100", "PhysDefense", 5)
                skillDao.addSkill(skill)

                lateinit var weapon: Weapon
                weapon = Weapon("Rusted Sword", "a_Sword", "a_Common", 100L, 0)
                weaponDao.addWeapon(weapon)
                weapon = Weapon("Weep's Lament", "a_Sword", "e_Legendary", 300L, 25)
                weapon.modifyIceAttack(200L)
                weaponDao.addWeapon(weapon)
                weapon = Weapon("Thunder's Cry", "a_Sword", "c_Rare", 100L, 10)
                weapon.modifyThunderAttack(150L)
                weaponDao.addWeapon(weapon)

                weapon = Weapon("Fiery Pike", "b_Spear", "b_Uncommon", 120L, 5)
                weapon.modifyFireAttack(60)
                weaponDao.addWeapon(weapon)
                weapon = Weapon("Iron Spear", "b_Spear", "a_Common", 130L, 0)
                weaponDao.addWeapon(weapon)

                lateinit var gear: Gear
                gear = Gear("Iron Helmet", "Headgear", "a_Common", 20L, 10L, 10L, 0L,listOf(skillDao.getSkillById(1)))
                gearDao.addGear(gear)
                gear = Gear("Steel Helmet", "Headgear", "a_Common", 30L, 15L, 10L, 5L, listOf(skillDao.getSkillById(2)), 1)
                gearDao.addGear(gear)
                gear = Gear("Reinforced Steel Helmet", "Headgear", "c_Rare", 45L, 30L, 20L, 10L, listOf(skillDao.getSkillById(2)), 2)
                gearDao.addGear(gear)
                gear = Gear("Dragon's Circlet", "Headgear", "e_Legendary", 20L, 150L, 100L, 100L, listOf(skillDao.getSkillById(1)))
                gearDao.addGear(gear)

                gear = Gear("Sack Vest", "Torso", "a_Common", 10L, 50L, 50L, 50L, listOf(skillDao.getSkillById(1)))
                gearDao.addGear(gear)
                gear = Gear("Cloth from the Ancients", "Torso", "e_Legendary", 25L, 150L, 150L, 150L, listOf(skillDao.getSkillById(1), skillDao.getSkillById(2)))
                gearDao.addGear(gear)
                gear = Gear("Bone Armor", "Torso", "b_Uncommon", 70L, 40L, 0L, 10L, listOf(skillDao.getSkillById(2)))
                gearDao.addGear(gear)

                gear = Gear("Bronze Bracelet", "Handwear", "b_Uncommon", 0L, 60L, 60L, 60L)
                gearDao.addGear(gear)
                gear = Gear("Plain Gloves", "Handwear", "a_Common", 0L, 0L, 40L, 20L)
                gearDao.addGear(gear)

                gear = Gear("Tattered Rope", "Belt", "a_Common", 0L, 0L, 0L, 0L)
                gearDao.addGear(gear)
                gear = Gear("Reinforced Leather Belt", "Belt", "b_Uncommon", 10L, 30L, 30L, 40L)
                gearDao.addGear(gear)
                gear = Gear("Scholar`s Cry", "Belt", "d_Mythical", 20L, 100L, 150L, 130L)
                gearDao.addGear(gear)

                gear = Gear("Desert Sandals", "Footwear", "b_Uncommon", 50L, 20L, 0L, 50L)
                gearDao.addGear(gear)
                gear = Gear("Lava-forged Boots", "Footwear", "d_Mythical", 150L, 250L, 0L, 80L)
                gearDao.addGear(gear)
                gear = Gear("Un'ael's Skull Crushers", "Footwear", "e_Legendary", 300L, 150L, 130L, 100L)
                gearDao.addGear(gear)
                gear = Gear("Simple Boots", "Footwear", "a_Common", 30L, 5L, 20L, 10L)
                gearDao.addGear(gear)
            }
        }
    }
}