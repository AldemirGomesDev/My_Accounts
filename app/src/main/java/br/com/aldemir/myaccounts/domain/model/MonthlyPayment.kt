package br.com.aldemir.myaccounts.domain.model

import androidx.room.*

@Entity(tableName = "monthly_payment", foreignKeys = [ForeignKey(
    entity = Expense::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("id_expense"),
    onDelete = ForeignKey.CASCADE
)]
)
data class MonthlyPayment(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "id_expense")
    var id_expense: Int = 0,
    @ColumnInfo(name = "year")
    var year: String = "",
    @ColumnInfo(name = "month")
    var month: String = "",
    @ColumnInfo(name = "value")
    var value: Double,
    @ColumnInfo(name = "situation")
    var situation: Boolean = false
)
