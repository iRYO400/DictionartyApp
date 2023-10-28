package workshop.akbolatss.dictionartyapp.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import workshop.akbolatss.dictionartyapp.data.model.DefinitionEntity
import workshop.akbolatss.dictionartyapp.data.model.WordEntity
import workshop.akbolatss.dictionartyapp.data.model.WordWithDefinitions

@Dao
interface WordDao {

    @Transaction
    suspend fun insertWordsWithDefinitions(
        words: List<WordEntity>,
        definitions: List<DefinitionEntity>
    ) {
        insertWords(words)
        insertDefinition(definitions)
    }

    @Insert
    suspend fun insertWords(words: List<WordEntity>): List<Long>

    @Insert
    suspend fun insertDefinition(definitions: List<DefinitionEntity>)

    @Query("SELECT COUNT(wordId) FROM word")
    suspend fun wordsCount(): Int

    @Transaction
    @Query("SELECT * FROM Word WHERE word = :query COLLATE NOCASE")
    suspend fun queryWords(query: String): List<WordWithDefinitions>
}