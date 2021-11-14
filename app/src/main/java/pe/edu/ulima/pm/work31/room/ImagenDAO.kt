package pe.edu.ulima.pm.work31.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pe.edu.ulima.pm.work31.model.ImagenURL

@Dao
interface ImagenDAO {

    @Query("SELECT * FROM ImagenURL ORDER BY codigo")
    fun findAll(): List<ImagenURL>

    @Query("SELECT * FROM ImagenURL WHERE codigo=:id")
    fun findById(id:Long) :ImagenURL

    @Insert
    fun insert(imagen: ImagenURL)


}