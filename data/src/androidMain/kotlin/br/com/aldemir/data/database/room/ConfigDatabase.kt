package br.com.aldemir.data.database.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
    version = 2
)
@TypeConverters(DateTypeConverter::class)
abstract class ConfigDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    abstract fun monthlyPaymentDao(): MonthlyPaymentDao

    abstract fun recipeDao(): RecipeDao

    abstract fun recipeMonthlyDao(): RecipeMonthlyDao

    abstract fun authenticationDao(): AuthenticationDao

    companion object {
        fun createDataBase(context: Context) = Room.databaseBuilder(
                context,
                ConfigDatabase::class.java,
                "AccountDataBase"
            ).build()
    }

}