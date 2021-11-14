package pe.edu.ulima.pm.work31.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name="name")
    val nombre : String,
    @ColumnInfo(name="favoritos")
    val favoritos : List<Long>
)
