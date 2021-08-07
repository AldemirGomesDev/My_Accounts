package br.com.aldemir.myaccounts.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.aldemir.myaccounts.data.domain.model.Expense
import br.com.aldemir.myaccounts.data.domain.model.MonthlyPayment
import br.com.aldemir.myaccounts.util.DateTypeConverter


@Database(entities = [Expense::class, MonthlyPayment::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class ConfigDataBase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun monthlyPaymentDao(): MonthlyPaymentDao

    companion object {
        private lateinit var INSTANCE: ConfigDataBase

        fun getDataBase(context: Context): ConfigDataBase {
            if (!::INSTANCE.isInitialized) {
                synchronized(ConfigDataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(context, ConfigDataBase::class.java, "AccountDataBase")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}