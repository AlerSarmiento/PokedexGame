package pe.edu.ulima.pm.work31.room

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.edu.ulima.pm.work31.model.ImagenURL

@Database(entities = [ImagenURL::class],version=1)
abstract class IRAppDatabase : RoomDatabase() {
    abstract fun ImagenDAO(): ImagenDAO
}