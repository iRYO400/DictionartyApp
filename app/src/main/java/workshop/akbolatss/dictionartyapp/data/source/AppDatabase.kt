package workshop.akbolatss.dictionartyapp.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import workshop.akbolatss.dictionartyapp.data.model.DefinitionEntity
import workshop.akbolatss.dictionartyapp.data.model.WordEntity

@Database(
    entities = [
        WordEntity::class,
        DefinitionEntity::class
    ],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val wordDao: WordDao
}