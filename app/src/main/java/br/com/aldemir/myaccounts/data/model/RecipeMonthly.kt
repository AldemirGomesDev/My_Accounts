package br.com.aldemir.myaccounts.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_monthly", foreignKeys = [ForeignKey(
    entity = Recipe::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("id_recipe"),
    onDelete = ForeignKey.CASCADE
)]
)
data class RecipeMonthly(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "id_recipe")
    var id_expense: Int = 0,
    @ColumnInfo(name = "year")
    var year: String = "",
    @ColumnInfo(name = "month")
    var month: String = "",
    @ColumnInfo(name = "value")
    var value: Double = 0.0,
    @ColumnInfo(name = "status")
    var status: Boolean = false
)
