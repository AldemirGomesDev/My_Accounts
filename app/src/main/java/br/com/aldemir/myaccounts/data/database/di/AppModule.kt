package br.com.aldemir.myaccounts.data.database.di

import android.content.Context
import androidx.room.Room
import br.com.aldemir.myaccounts.data.database.ConfigDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideConfigDataBase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ConfigDataBase::class.java,
        "AccountDataBase"
    ).build()

    @Singleton
    @Provides
    fun provideExpenseDao(db: ConfigDataBase) = db.expenseDao()

    @Singleton
    @Provides
    fun provideMonthlyPaymentDao(db: ConfigDataBase) = db.monthlyPaymentDao()

}