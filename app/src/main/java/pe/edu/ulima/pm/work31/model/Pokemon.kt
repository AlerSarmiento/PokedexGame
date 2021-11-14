package pe.edu.ulima.pm.work31.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
    @PrimaryKey
    var id : Long,
    @ColumnInfo(name="codigo")
    var codigo :Int,
    @ColumnInfo(name="imagen")
    var imagen: String,
    @ColumnInfo(name="name")
    var name: String,
    @ColumnInfo(name="hp")
    var hp : Int,
    @ColumnInfo(name="attack")
    var attack : Int,
    @ColumnInfo(name="defense")
    var defense: Int,
    @ColumnInfo(name="special_attack")
    var special_attack : Int,
    @ColumnInfo(name="special_defense")
    var special_defense : Int,
)
