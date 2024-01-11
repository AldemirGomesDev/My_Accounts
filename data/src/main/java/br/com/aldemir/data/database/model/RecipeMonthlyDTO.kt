package br.com.aldemir.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_monthly", foreignKeys = [ForeignKey(
    entity = RecipeDTO::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("id_recipe"),
    onDelete = ForeignKey.CASCADE
)]
)
data class RecipeMonthlyDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "id_recipe")
    var id_recipe: Int = 0,
    @ColumnInfo(name = "year")
    var year: String = "",
    @ColumnInfo(name = "month")
    var month: String = "",
    @ColumnInfo(name = "value")
    var value: Double = 0.0,
    @ColumnInfo(name = "status")
    var status: Boolean = false
)
