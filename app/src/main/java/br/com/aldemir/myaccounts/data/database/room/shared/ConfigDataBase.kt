package br.com.aldemir.myaccounts.data.database.room.shared

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.aldemir.myaccounts.data.database.room.expense.ExpenseDao
import br.com.aldemir.myaccounts.data.database.room.expense.MonthlyPaymentDao
import br.com.aldemir.myaccounts.data.database.room.recipe.RecipeDao
import br.com.aldemir.myaccounts.data.database.room.recipe.RecipeMonthlyDao
import br.com.aldemir.myaccounts.data.model.ExpenseDTO
import br.com.aldemir.myaccounts.data.model.ExpenseMonthlyDTO
import br.com.aldemir.myaccounts.data.model.RecipeDTO
import br.com.aldemir.myaccounts.data.model.RecipeMonthlyDTO
import br.com.aldemir.myaccounts.util.DateTypeConverter


@Database(
    entities = [
        ExpenseDTO::class,
        ExpenseMonthlyDTO::class,
        RecipeDTO::class,
        RecipeMonthlyDTO::class], version = 1
)
@TypeConverters(DateTypeConverter::class)
abstract class ConfigDataBase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    abstract fun monthlyPaymentDao(): MonthlyPaymentDao

    abstract fun recipeDao(): RecipeDao

    abstract fun recipeMonthlyDao(): RecipeMonthlyDao

}