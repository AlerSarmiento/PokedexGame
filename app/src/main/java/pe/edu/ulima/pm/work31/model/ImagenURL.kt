package pe.edu.ulima.pm.work31.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImagenURL(
    @PrimaryKey
    val codigo: Long,
    @ColumnInfo(name="URL")
    val URL : String
)
