package br.com.aldemir.data.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.aldemir.data.database.model.ExpenseDTO
import br.com.aldemir.data.database.model.ExpenseMonthlyDTO
import br.com.aldemir.data.database.model.RecipeDTO
import br.com.aldemir.data.database.model.RecipeMonthlyDTO
import br.com.aldemir.data.database.model.UserDTO
import br.com.aldemir.data.database.room.authentication.AuthenticationDao
import br.com.aldemir.data.database.room.expense.ExpenseDao
import br.com.aldemir.data.database.room.expense.MonthlyPaymentDao
import br.com.aldemir.data.database.room.recipe.RecipeDao
import br.com.aldemir.data.database.room.recipe.RecipeMonthlyDao


@Database(
    entities = [
        ExpenseDTO::class,
        ExpenseMonthlyDTO::class,
        RecipeDTO::class,
        RecipeMonthlyDTO::class,
        UserDTO::class],
    version = 1
)
@TypeConverters(DateTypeConverter::class)
abstract class ConfigDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    abstract fun monthlyPaymentDao(): MonthlyPaymentDao

    abstract fun recipeDao(): RecipeDao

    abstract fun recipeMonthlyDao(): RecipeMonthlyDao

    abstract fun authenticationDao(): AuthenticationDao

    companion object {

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS `user` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL,
                `user_name` TEXT NOT NULL,
                `password` TEXT NOT NULL,
                `is_logged` INTEGER NOT NULL,
                UNIQUE(`name`)
            )
        """)
            }
        }

        private val MIGRATION_1_24 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Expense ADD COLUMN created_at INTEGER")
                database.execSQL("ALTER TABLE Expense ADD COLUMN due_date INTEGER")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `recipe` (" +
                        "`id` INTEGER, " +
                        "`name` TEXT, " +
                        "`description` TEXT, " +
                        "`created_at` INTEGER, " +
                        "`due_date` INTEGER, " +
                        "`status` INTEGER, " +
                        "PRIMARY KEY(`id`))")
                database.execSQL("CREATE TABLE IF NOT EXISTS `recipe_monthly` (`id` INTEGER, PRIMARY KEY(`id`))")
            }
        }
        fun createDataBase(context: Context) = Room.databaseBuilder(
                context,
                ConfigDatabase::class.java,
                "AccountDataBase"
            ).build()
    }

}