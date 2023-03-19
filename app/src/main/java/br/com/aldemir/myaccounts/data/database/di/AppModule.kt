package br.com.aldemir.myaccounts.data.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.aldemir.myaccounts.MyApplication
import br.com.aldemir.myaccounts.data.database.room.shared.ConfigDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
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

    @Provides
    fun provideContext() = MyApplication.appContext

    @Singleton
    @Provides
    fun provideConfigDataBase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ConfigDataBase::class.java,
        "AccountDataBase"
    ).addMigrations(MIGRATION_1_2, MIGRATION_2_3)
        .build()

    @Singleton
    @Provides
    fun provideExpenseDao(db: ConfigDataBase) = db.expenseDao()

    @Singleton
    @Provides
    fun provideMonthlyPaymentDao(db: ConfigDataBase) = db.monthlyPaymentDao()

    @Singleton
    @Provides
    fun provideRecipeDao(db: ConfigDataBase) = db.recipeDao()

    @Singleton
    @Provides
    fun provideRecipeMonthlyDao(db: ConfigDataBase) = db.recipeMonthlyDao()
}