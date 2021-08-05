package br.com.aldemir.myaccounts.data.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Account", indices = [Index(value = ["name"], unique = true) ])
data class Account(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "name")
    @NonNull var name: String,
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "value")
    @NonNull var value: String,
    @ColumnInfo(name = "type")
    var type: Boolean = false
)
