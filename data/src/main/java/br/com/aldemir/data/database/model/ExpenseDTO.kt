package br.com.aldemir.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "expense", indices = [Index(value = ["name"], unique = true) ])
data class ExpenseDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "created_at")
    var created_at: Date? = null,
    @ColumnInfo(name = "due_date")
    var due_date: Int = 0,
    @ColumnInfo(name = "status")
    var status: Boolean = false
)
