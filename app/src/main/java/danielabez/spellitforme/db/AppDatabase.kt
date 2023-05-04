package danielabez.spellitforme.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import danielabez.spellitforme.db.dao.GearDao
import danielabez.spellitforme.db.dao.RegisteredUserDao
import danielabez.spellitforme.db.dao.SkillDao
import danielabez.spellitforme.model.Gear
import danielabez.spellitforme.model.RegisteredUser
import danielabez.spellitforme.model.Skill
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//En esta anotación, se incluyen todas las clases que tengan que verse plasmadas en forma de tablas
@Database(entities = arrayOf(RegisteredUser::class, Skill::class, Gear::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun registeredUserDao(): RegisteredUserDao
    abstract fun skillDao(): SkillDao
    abstract fun gearDao(): GearDao

    //Mediante un patrón Singleton, se genera la base de datos e inicializa
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
                        initializeDatabase(database.registeredUserDao(), database.skillDao(), database.gearDao())
                    }
                }
            }

            /*
            * Método que introduce una serie de valores por defecto a las tablas cuando la base de datos es generada por primera vez
            */
            suspend fun initializeDatabase(registeredUserDao: RegisteredUserDao, skillDao: SkillDao, gearDao: GearDao){
                lateinit var registeredUser: RegisteredUser
                registeredUser = RegisteredUser("correo@gmail.com", "paquito", "paquitoPwd")
                registeredUserDao.addRegisteredUser(registeredUser)

                lateinit var gear: Gear
                gear = Gear("Iron Helmet", "Headgear", 100L, 20L)
                gearDao.addGear(gear)
                gear = Gear("Sack Vest", "Torso", 10L, 50L)
                gearDao.addGear(gear)
                gear = Gear("Bronze Bracelet", "Handwear", 0L, 60L)
                gearDao.addGear(gear)
                gear = Gear("Tattered Rope", "Belt", 0L, 0L)
                gearDao.addGear(gear)
                gear = Gear("Sand Sandals", "Footwear", 50L, 20L)
                gearDao.addGear(gear)

                lateinit var skill: Skill
                skill = Skill("Attack Up", "Increases physical attack damage by 5%/10%/15%/30%", 4)
                skillDao.addSkill(skill)
                skill = Skill("Defense Up", "Increases physical defense by 10/20/35/60/100", 5)
                skillDao.addSkill(skill)
            }
        }
    }
}