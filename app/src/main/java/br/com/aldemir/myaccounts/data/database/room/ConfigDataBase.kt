package br.com.aldemir.myaccounts.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.aldemir.myaccounts.data.database.room.expense.ExpenseDao
import br.com.aldemir.myaccounts.data.database.room.expense.MonthlyPaymentDao
import br.com.aldemir.myaccounts.data.database.room.recipe.RecipeDao
import br.com.aldemir.myaccounts.data.database.room.recipe.RecipeMonthlyDao
import br.com.aldemir.myaccounts.data.model.Expense
import br.com.aldemir.myaccounts.data.model.MonthlyPayment
import br.com.aldemir.myaccounts.data.model.Recipe
import br.com.aldemir.myaccounts.data.model.RecipeMonthly
import br.com.aldemir.myaccounts.util.DateTypeConverter


@Database(
    entities = [
        Expense::class,
        MonthlyPayment::class,
        Recipe::class,
        RecipeMonthly::class], version = 1
)
@TypeConverters(DateTypeConverter::class)
abstract class ConfigDataBase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    abstract fun monthlyPaymentDao(): MonthlyPaymentDao

    abstract fun recipeDao(): RecipeDao

    abstract fun recipeMonthlyDao(): RecipeMonthlyDao

}