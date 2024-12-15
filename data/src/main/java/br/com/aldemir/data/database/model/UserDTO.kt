package br.com.aldemir.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user",
    indices = [Index(value = ["user_name"], unique = true)]
)
data class UserDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "is_logged")
    val isLogged: Boolean = false,
)
