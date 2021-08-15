package br.com.aldemir.myaccounts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.aldemir.myaccounts.domain.model.Expense
import br.com.aldemir.myaccounts.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.util.DateTypeConverter


@Database(entities = [Expense::class, MonthlyPayment::class], version = 2)
@TypeConverters(DateTypeConverter::class)
abstract class ConfigDataBase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    abstract fun monthlyPaymentDao(): MonthlyPaymentDao

}